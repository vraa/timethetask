/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.controllers;

import com.timethetask.dtos.ActivityReportDto;
import com.timethetask.dtos.DayDto;
import com.timethetask.dtos.ReportDto;
import com.timethetask.dtos.TaskReportDto;
import com.timethetask.models.Activity;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author veera
 */
public class Reports extends HttpServlet implements Constants {

    private static final long serialVersionUID = -3107107125418416278L;
    private static final Logger logger = Logger.getLogger(Reports.class.getSimpleName());
    private static final ActivityService activityService = ActivityService.INSTANCE;
    private static final TaskService taskService = TaskService.INSTANCE;
    private static final Helper helper = Helper.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());

        String groupBy = req.getParameter(GROUPBY);
        if (helper.isEmpty(groupBy)) {
            req.getRequestDispatcher("/WEB-INF/pages/reports.jsp").forward(req, resp);
            return;
        }

        String historyFor = req.getParameter(HISTORYFOR);
        historyFor = (helper.isEmpty(historyFor)) ? DEFAULT_HISTORY_FOR : historyFor;
        int historyForDays = Integer.parseInt(historyFor);
        Date startDate = helper.getPastDate(helper.getNow(), historyForDays);

        List<Activity> activities = activityService.read(user, startDate);
        Set<Long> taskIdSet = new HashSet<Long>();
        DayDto dayDto = null;
        ReportDto reportDto = new ReportDto();

        Date dateMarker = null;
        for (Activity activity : activities) {
            taskIdSet.add(activity.getTaskKey().getId());
            if (!helper.isSameDay(dateMarker, activity.getStart())) {
                dateMarker = activity.getStart();
                if (dayDto != null && dayDto.getActivities().size() > 0) {
                    reportDto.addDay(dayDto);
                }
                dayDto = new DayDto();
                dayDto.setDate(helper.formateDateToString(user.getTimeZone(), dateMarker, DATE_FORMAT));
            }
            dayDto.addActivity(new ActivityReportDto(activity, user));
        }
        if (dayDto != null) {
            reportDto.addDay(dayDto);
        }


        if (groupBy.equalsIgnoreCase(GRP_ACTIVITY)) {
            /* if grouped by activity, then no need to process further. */
        } else if (groupBy.equalsIgnoreCase(GRP_TASK)) {
            /* iterate the activities and group them based on task id */
            for (DayDto day : reportDto.getDays()) {
                day.groupActivitiesByTask();
            }

        }

        for (Long taskId : taskIdSet) {
            reportDto.addTask(new TaskReportDto(taskService.read(String.valueOf(taskId)), user));
        }



        String jsonOutput = helper.toJson(reportDto);
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonOutput);
        writer.close();
        logger.log(Level.FINE, LEAVING, user.getEmail());

    }
}

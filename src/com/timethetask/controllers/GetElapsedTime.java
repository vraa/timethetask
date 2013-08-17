/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.controllers;

import com.timethetask.dtos.ActivityReportDto;
import com.timethetask.models.Activity;
import com.timethetask.models.Task;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import com.timethetask.utils.ResponseJson;
import java.io.IOException;
import java.io.Writer;
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
public class GetElapsedTime extends HttpServlet implements Constants {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GetElapsedTime.class.getSimpleName());
    private ActivityService activityService = ActivityService.INSTANCE;
    private static TaskService taskService = TaskService.INSTANCE;
    private static final Helper helper = Helper.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        ResponseJson responseJson = new ResponseJson();

        logger.log(Level.FINE, ENTERING, user.getEmail());
        String taskId = req.getParameter(TASKID);
        if (!(helper.isEmpty(taskId))) {
            Task task = taskService.read(taskId);
            Activity activity = activityService.getLastActivity(task);
            if (activity != null) {
                responseJson.setStatus(SUCCESS);
                responseJson.addActivity(new ActivityReportDto(activity, user));

                String jsonOutput = helper.toJson(responseJson);
                resp.setContentType("application/json");
                Writer writer = resp.getWriter();
                writer.write(jsonOutput);
                writer.close();
            }
        }
        logger.log(Level.FINE, LEAVING, user.getEmail());
    }
}

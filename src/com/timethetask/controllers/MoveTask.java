package com.timethetask.controllers;

import com.timethetask.dtos.ActivityReportDto;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.timethetask.dtos.TaskDto;
import com.timethetask.models.Activity;
import com.timethetask.models.Task;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import com.timethetask.utils.ResponseJson;
import java.util.List;
import java.util.logging.Level;

public class MoveTask extends HttpServlet implements Constants {

    private static final long serialVersionUID = -3411222728311394600L;
    private static final TaskService taskService = TaskService.INSTANCE;
    private static final ActivityService activityService = ActivityService.INSTANCE;
    private static Helper helper = Helper.INSTANCE;
    private static final Logger logger = Logger.getLogger(MoveTask.class.getSimpleName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());

        ResponseJson responseJson = new ResponseJson();

        String taskId = req.getParameter(TASKID);
        String toStatus = req.getParameter(STATUS);
        if (!helper.isEmpty(taskId) && !helper.isEmpty(toStatus)) {
            Task task = taskService.read(taskId);
            if (task != null) {
                if (task.getStatus().equalsIgnoreCase(INPROGRESS)) {
                    Activity lastActivity = activityService.getLastActivity(task);
                    lastActivity.setEnd(helper.getNow());
                    activityService.update(lastActivity, user);
                }
                if (toStatus.equalsIgnoreCase(INPROGRESS)) {
                    Activity newActivity = new Activity();
                    newActivity.setStart(helper.getNow());
                    newActivity.setUserKey(user.getKey());
                    newActivity.setTaskKey(task.getKey());
                    activityService.create(newActivity, user);
                }
                task.setStatus(toStatus);
                task.setLastActivityOn(helper.getNow());
                taskService.update(task, user);

                responseJson.setStatus(SUCCESS);
                responseJson.setMessage("Task " + task.getKey().getId() + " was updated successfully");
                responseJson.addTask(new TaskDto(task, user));

                List<Activity> activities = activityService.read(task, DEFAULT_ACTIVITY_LIMIT);
                for (Activity activity : activities) {
                    responseJson.addActivity(new ActivityReportDto(activity, user));
                }

            }
        } else {
            responseJson.setStatus(ERROR);
            responseJson.setMessage("Either task ID or status is empty");
        }

        String jsonOutput = helper.toJson(responseJson);
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonOutput);
        writer.close();

        logger.log(Level.FINE, LEAVING, user.getEmail());
    }
}

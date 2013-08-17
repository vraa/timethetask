package com.timethetask.controllers;

import com.timethetask.dtos.ActivityReportDto;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
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
import java.util.logging.Level;

public class GetTask extends HttpServlet implements Constants {

    private static final long serialVersionUID = -1245547995780814685L;
    private static final Logger logger = Logger.getLogger(GetTask.class.getSimpleName());
    private static final Helper helper = Helper.INSTANCE;
    private TaskService taskService = TaskService.INSTANCE;
    private ActivityService activityService = ActivityService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());

        ResponseJson responseJson = new ResponseJson();

        String taskId = req.getParameter(TASKID);
        if (!helper.isEmpty(taskId)) {
            /* if task id present, then just return that task only */
            Task task = taskService.read(taskId);
            if (task != null) {
                responseJson.setStatus(SUCCESS);
                responseJson.setMessage("Successfully retrieved the task[" + taskId + "]");
                responseJson.addTask(new TaskDto(task, user));
            }
        } else {
            /* no task id. get all the tasks */
            List<Task> taskList = null;
            String statusParam = req.getParameter(STATUS);
            if (!helper.isEmpty(statusParam)) {
                taskList = taskService.read(user, statusParam, false);
            } else {
                taskList = taskService.read(user, false);
            }
            responseJson.setStatus(SUCCESS);
            responseJson.setMessage("Totally " + taskList.size() + " task(s) are retrieved.");
            for (Task task : taskList) {
                responseJson.addTask(new TaskDto(task, user));
                List<Activity> activities = activityService.read(task, DEFAULT_ACTIVITY_LIMIT);
                for (Activity activity : activities) {
                    responseJson.addActivity(new ActivityReportDto(activity, user));
                }
            }
        }

        String jsonOutput = helper.toJson(responseJson);
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonOutput);
        writer.close();

        logger.log(Level.FINE, LEAVING, user.getEmail());

    }
}

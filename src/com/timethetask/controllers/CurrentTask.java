package com.timethetask.controllers;

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
import java.util.logging.Level;
import org.datanucleus.cache.Level1Cache;

public class CurrentTask extends HttpServlet implements Constants {

    private static final long serialVersionUID = 2649438568507483238L;
    private static final Logger logger = Logger.getLogger(CurrentTask.class.getSimpleName());
    private static final TaskService taskService = TaskService.INSTANCE;
    private static final ActivityService activityService = ActivityService.INSTANCE;
    private Helper helper = Helper.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());

        String taskId = req.getParameter(TASKID);
        String newTaskId = req.getParameter(NEWTASKID);

        if (helper.isEmpty(taskId) || helper.isEmpty(newTaskId)) {
            throw new IllegalArgumentException("Either one of the task id is null or empty");
        }

        Task oldCurrentTask = taskService.read(taskId);
        oldCurrentTask.setStatus(ONHOLD);
        oldCurrentTask.setLastActivityOn(helper.getNow());
        taskService.update(oldCurrentTask, user);

        Activity lastActivity = activityService.getLastActivity(oldCurrentTask);
        lastActivity.setEnd(helper.getNow());
        activityService.update(lastActivity, user);

        Task newCurrentTask = taskService.read(newTaskId);
        newCurrentTask.setStatus(INPROGRESS);
        newCurrentTask.setLastActivityOn(helper.getNow());
        taskService.update(newCurrentTask, user);

        Activity newActivity = new Activity();
        newActivity.setStart(helper.getNow());
        newActivity.setUserKey(user.getKey());
        newActivity.setTaskKey(newCurrentTask.getKey());
        activityService.create(newActivity, user);

        ResponseJson responseJson = new ResponseJson();
        responseJson.setStatus(SUCCESS);
        responseJson.setMessage("Successfully updated two tasks [" + taskId + "," + newTaskId + "]");
        responseJson.addTask(new TaskDto(oldCurrentTask, user));
        responseJson.addTask(new TaskDto(newCurrentTask, user));

        String jsonOutput = helper.toJson(responseJson);
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonOutput);
        writer.close();

        logger.log(Level.FINE, LEAVING, user.getEmail());
    }
}

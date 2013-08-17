package com.timethetask.controllers;

import com.timethetask.models.Task;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import com.timethetask.utils.ResponseJson;
import java.util.logging.Level;

public class DeleteTask extends HttpServlet implements Constants {

    private static final long serialVersionUID = -893734026474791972L;
    private static final TaskService taskService = TaskService.INSTANCE;
    private static final ActivityService activityService = ActivityService.INSTANCE;
    private static final Helper helper = Helper.INSTANCE;
    private static final Logger logger = Logger.getLogger(DeleteTask.class.getSimpleName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());

        String taskId = req.getParameter(TASKID);
        if (helper.isEmpty(taskId)) {
            throw new IllegalArgumentException("Task ID is empty. You must provide a value");
            // TODO add proper error message
        }

        Task task = taskService.read(taskId);
        activityService.delete(task, user);
        taskService.delete(task, user);

        ResponseJson responseJson = new ResponseJson();
        responseJson.setStatus(SUCCESS);
        responseJson.setMessage("The task was deleted");

        String jsonOutput = helper.toJson(responseJson);
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonOutput);
        writer.close();

        logger.log(Level.FINE, LEAVING, user.getEmail());

    }
}

package com.timethetask.controllers;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.timethetask.models.Task;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import com.timethetask.utils.ResponseJson;
import java.util.logging.Level;

public class CloseTask extends HttpServlet implements Constants {

    private static final long serialVersionUID = -8717419188318676693L;
    private static final Logger logger = Logger.getLogger(CloseTask.class.getSimpleName());
    TaskService taskService = TaskService.INSTANCE;
    private Helper helper = Helper.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.INFO, ENTERING, user.getEmail());

        String taskId = req.getParameter(TASKID);
        if (!helper.isEmpty(taskId)) {
            Task task = taskService.read(taskId);
            task.setClosed(true);
            taskService.update(task, user);

            ResponseJson responseJson = new ResponseJson();
            responseJson.setStatus(SUCCESS);
            responseJson.setMessage("The task [" + taskId + "] was successfully closed");

            String jsonOutput = helper.toJson(responseJson);
            resp.setContentType("application/json");
            Writer writer = resp.getWriter();
            writer.write(jsonOutput);
            writer.close();

            logger.log(Level.FINE, LEAVING, user.getEmail());
        }
    }
}

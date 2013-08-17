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
import com.timethetask.models.Task;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import com.timethetask.utils.ResponseJson;
import java.util.logging.Level;
import org.datanucleus.cache.Level1Cache;

public class EditTask extends HttpServlet implements Constants {

    private static final long serialVersionUID = 7781114895140572122L;
    private static final Logger logger = Logger.getLogger(EditTask.class.getSimpleName());
    private static final TaskService taskService = TaskService.INSTANCE;
    private static final UserService userService = UserService.INSTANCE;
    private static final Helper helper = Helper.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());
        ResponseJson responseJson = new ResponseJson();

        String taskId = req.getParameter(TASKID);
        String taskName = req.getParameter(TASKNAME);
        if (!helper.isEmpty(taskId) && !helper.isEmpty(taskName)) {

            Task task = taskService.read(taskId);
            task.setName(taskName);
            task.setStatus(req.getParameter(STATUS));
            task.setIcon(req.getParameter(ICON));
            task.setColor(req.getParameter(COLOR));
            task.setProject(req.getParameter(PROJECT));
            task.setPriority(req.getParameter(PRIORITY));
            task.setColor(req.getParameter(COLOR));

            taskService.update(task, user);

            user.addProject(task.getProject());
            userService.update(user);

            responseJson.setStatus(SUCCESS);
            responseJson.setMessage("The task was edited successfully");
            responseJson.addTask(new TaskDto(task, user));
            for (String prj : user.getProjects()) {
                responseJson.addProject(prj);
            }

        } else {
            /* TODO error scenario */
        }

        String jsonOutput = helper.toJson(responseJson);
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonOutput);
        writer.close();

        logger.fine(LEAVING + user.getEmail());
    }
}

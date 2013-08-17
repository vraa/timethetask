/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.controllers;

import com.timethetask.dtos.ActivityDto;
import com.timethetask.dtos.DownloadDataDto;
import com.timethetask.dtos.TakActivityDto;
import com.timethetask.dtos.UserDto;
import com.timethetask.models.Activity;
import com.timethetask.models.Task;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
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
public class DownloadData extends HttpServlet implements Constants {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DownloadData.class.getSimpleName());
    private static final TaskService taskService = TaskService.INSTANCE;
    private static final ActivityService activityService = ActivityService.INSTANCE;
    private static final Helper helper = Helper.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());
        DownloadDataDto downloadDataDto = new DownloadDataDto();

        downloadDataDto.setUser(new UserDto(user));

        List<Task> tasks = taskService.readAll(user);
        for (Task task : tasks) {
            TakActivityDto takActivityDto = new TakActivityDto(task, user);
            List<Activity> activities = activityService.read(task);
            for (Activity activity : activities) {
                takActivityDto.addActivity(new ActivityDto(activity, user));
            }
            downloadDataDto.addTask(takActivityDto);
        }
        String jsonOutput = helper.toJson(downloadDataDto);
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonOutput);
        writer.close();
        logger.log(Level.INFO, LEAVING, user.getEmail());
    }
}

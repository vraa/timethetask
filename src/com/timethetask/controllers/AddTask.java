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
import java.util.Date;
import java.util.logging.Level;

public class AddTask extends HttpServlet implements Constants {

    private static final long serialVersionUID = 2116510017506564815L;
    private TaskService taskService = TaskService.INSTANCE;
    private UserService userService = UserService.INSTANCE;
    private ActivityService activityService = ActivityService.INSTANCE;
    private static Helper helper = Helper.INSTANCE;
    private static final Logger logger = Logger.getLogger(AddTask.class.getSimpleName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());

        String taskName = req.getParameter(TASKNAME);

        ResponseJson responseJson = new ResponseJson();
        if (!helper.isEmpty(taskName)) {

            Task task = new Task();
            task.setName(taskName);
            task.setStatus(req.getParameter(STATUS));
            task.setCreatedOn(helper.getNow());
            task.setLastActivityOn(helper.getNow());
            task.setPriority(LOW);
            task.setUserKey(user.getKey());
            task.setProject(req.getParameter(PROJECT));
            task.setClosed(false);
            task.setColor(req.getParameter(COLOR));
            task.setIcon(req.getParameter(ICON));
            taskService.create(task, user);

            if (task.getStatus().equalsIgnoreCase(INPROGRESS)) {
                Activity activity = new Activity();
                activity.setStart(helper.getNow());
                activity.setUserKey(user.getKey());
                activity.setTaskKey(task.getKey());
                activityService.create(activity, user);
            }

            String activities = req.getParameter(ACTIVITIES);
            if (!helper.isEmpty(activities)) {
                String[] activityArray = activities.split(COMMA);
                for (String activityData : activityArray) {
                    String[] parsedActivityData = activityData.split(AMPERSAND);
                    if (parsedActivityData.length == 3) {
                        String id = parsedActivityData[0];
                        Date startDate = helper.formateStringToDate(user.getTimeZone(), parsedActivityData[1], DATE_PICKER_FORMAT);
                        Date endDate = helper.formateStringToDate(user.getTimeZone(), parsedActivityData[2], DATE_PICKER_FORMAT);
                        if (startDate != null & endDate != null) {
                            Activity newActivity = new Activity();
                            newActivity.setStart(startDate);
                            newActivity.setEnd(endDate);
                            newActivity.setTaskKey(task.getKey());
                            newActivity.setUserKey(user.getKey());
                            activityService.create(newActivity, user);

                        } else {
                            /* TODO error occured while parsing */
                        }
                    } else {
                        /* TODO just ignore this */
                    }
                }
            }

            user.addProject(task.getProject());
            userService.update(user);

            responseJson.setStatus(SUCCESS);
            responseJson.setMessage("Your task was added");
            responseJson.addTask(new TaskDto(task, user));
            for (String prj : user.getProjects()) {
                responseJson.addProject(prj);
            }
            logger.fine("Task was added to datastore");
        } else {
            responseJson.setStatus(FAIL);
            responseJson.setMessage("Could not add an empty task.");
            logger.fine("Task name was empty. Didn't add task to datastore.");
        }

        String jsonOutput = helper.toJson(responseJson);
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonOutput);
        writer.close();

        logger.log(Level.FINE, LEAVING, user.getEmail());
    }
}

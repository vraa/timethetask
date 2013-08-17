package com.timethetask.dtos;

import com.timethetask.models.Task;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;

public class TaskDto implements Constants {

    private static final long serialVersionUID = 1L;
    private static final Helper helper = Helper.INSTANCE;

    public TaskDto() {
    }

    public TaskDto(Task task, User user) {
        this.taskId = helper.xssClean(String.valueOf(task.getKey().getId()));
        this.lastActivityOn = helper.formateDateToString(user.getTimeZone(), task.getLastActivityOn(), DATE_FORMAT_WITH_TIME);
        this.priority = helper.xssClean(task.getPriority());
        this.status = helper.xssClean(task.getStatus());
        this.taskName = helper.xssClean(task.getName());
        this.project = helper.xssClean(task.getProject());
        this.color = helper.xssClean(task.getColor());
        this.icon = helper.xssClean(task.getIcon());
    }
    private String taskId;
    private String taskName;
    private String lastActivityOn;
    private String priority;
    private String status;
    private String project;
    private String color;
    private String icon;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastActivityOn() {
        return lastActivityOn;
    }

    public void setLastActivityOn(String lastActivityOn) {
        this.lastActivityOn = lastActivityOn;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

package com.timethetask.utils;

import com.timethetask.dtos.ActivityReportDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.timethetask.dtos.TaskDto;

public class ResponseJson implements Serializable {

    private static final long serialVersionUID = 6725782472008290526L;
    private String status;
    private String message;
    private List<TaskDto> tasks;
    private List<String> projects;
    private List<ActivityReportDto> activities = new ArrayList<ActivityReportDto>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    public void addTask(TaskDto taskDto) {
        if (this.tasks == null) {
            this.tasks = new ArrayList<TaskDto>();
        }

        this.tasks.add(taskDto);
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public void addProject(String project) {
        if (this.projects == null) {
            this.projects = new ArrayList<String>();
        }
        this.projects.add(project);
    }

    public List<ActivityReportDto> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityReportDto> activities) {
        this.activities = activities;
    }

    public void addActivity(ActivityReportDto activityDto) {
        this.activities.add(activityDto);
    }
}

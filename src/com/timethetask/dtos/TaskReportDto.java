/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.dtos;

import com.timethetask.models.Task;
import com.timethetask.models.User;

/**
 *
 * @author veera
 */
public class TaskReportDto extends TaskDto {

    private static final long serialVersionUID = 1L;
    private String elapsedTime;

    public TaskReportDto() {
    }

    public TaskReportDto(Task task, User user) {
        super(task, user);

    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}

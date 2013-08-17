/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.dtos;

import com.timethetask.utils.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author veera
 */
public class DownloadDataDto implements Constants, Serializable {

    private static final long serialVersionUID = 1L;
    private UserDto user;
    private List<TakActivityDto> tasks = new ArrayList<TakActivityDto>();

    public List<TakActivityDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TakActivityDto> tasks) {
        this.tasks = tasks;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void addTask(TakActivityDto taskActivityDto) {
        this.tasks.add(taskActivityDto);
    }
}

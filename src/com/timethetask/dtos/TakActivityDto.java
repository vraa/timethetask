/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.dtos;

import com.timethetask.models.Task;
import com.timethetask.models.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author veera
 */
public class TakActivityDto extends TaskDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ActivityDto> activities = new ArrayList<ActivityDto>();

    public TakActivityDto() {
    }

    public TakActivityDto(Task task, User user) {
        super(task, user);
    }

    public List<ActivityDto> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityDto> activities) {
        this.activities = activities;
    }

    public void addActivity(ActivityDto activityDto) {
        this.activities.add(activityDto);
    }
}

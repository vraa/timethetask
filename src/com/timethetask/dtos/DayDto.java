/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author veera
 */
public class DayDto {

    private String date;
    private List<ActivityReportDto> activities = new ArrayList<ActivityReportDto>();

    public List<ActivityReportDto> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityReportDto> activities) {
        this.activities = activities;
    }


    public void addActivity(ActivityReportDto activityReportDto) {
        this.activities.add(activityReportDto);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void groupActivitiesByTask() {
        Map<String, ActivityReportDto> taskActMap = new HashMap<String, ActivityReportDto>();
        for (ActivityReportDto activityReportDto : this.getActivities()) {
            ActivityReportDto activity = taskActMap.get(activityReportDto.getTaskId());
            if (activity != null) {
                activity.setElapsedTime(activity.getElapsedTime() + activityReportDto.getElapsedTime());
                if (activityReportDto.getStart().compareTo(activity.getStart()) < 0) {
                    activity.setStart(activityReportDto.getStart());
                }
                if (activityReportDto.getEnd().compareTo(activity.getStart()) > 0) {
                    activity.setEnd(activityReportDto.getEnd());
                }
            } else {
                taskActMap.put(activityReportDto.getTaskId(), activityReportDto);
            }
        }
        this.setActivities(new ArrayList<ActivityReportDto>(taskActMap.values()));
    }
}

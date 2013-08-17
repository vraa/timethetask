/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.dtos;

import com.timethetask.models.Activity;
import com.timethetask.models.User;
import com.timethetask.utils.Helper;

/**
 *
 * @author veera
 */
public class ActivityReportDto extends ActivityDto {

    private static final long serialVersionUID = 1L;
    private static final Helper helper = Helper.INSTANCE;
    private long elapsedTime;

    public ActivityReportDto() {
    }

    public ActivityReportDto(Activity activity, User user) {
        super(activity, user);
        this.elapsedTime = helper.getTimeDiff(activity.getEnd(), activity.getStart());
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @Override
    public String toString() {
        return this.getTaskId();
    }
}

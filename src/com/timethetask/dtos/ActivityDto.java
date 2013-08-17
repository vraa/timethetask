package com.timethetask.dtos;

import java.util.Date;

import com.timethetask.models.Activity;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import javax.persistence.Transient;

public class ActivityDto implements Constants {

    private static final long serialVersionUID = 1L;
    @Transient
    private Date start;
    @Transient
    private Date end;
    private String taskId;
    private String startTime;
    private String endTime;
    private static final Helper helper = Helper.INSTANCE;

    public ActivityDto() {
    }

    public ActivityDto(Activity activity, User user) {
        this.start = activity.getStart();
        this.end = activity.getEnd();
        this.startTime = helper.formateDateToString(user.getTimeZone(), this.start, DATE_FORMAT_WITH_TIME);
        this.endTime = helper.formateDateToString(user.getTimeZone(), this.end, DATE_FORMAT_WITH_TIME);
        this.taskId = String.valueOf(activity.getTaskKey().getId());

    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}

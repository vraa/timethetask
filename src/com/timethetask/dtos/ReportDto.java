/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author veera
 */
public class ReportDto {

    List<DayDto> days = new ArrayList<DayDto>();
    List<TaskReportDto> tasks = new ArrayList<TaskReportDto>();

    public List<DayDto> getDays() {
        return days;
    }

    public void setDays(List<DayDto> days) {
        this.days = days;
    }

    public void addDay(DayDto daysDto) {
        this.days.add(daysDto);
    }

    public List<TaskReportDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskReportDto> tasks) {
        this.tasks = tasks;
    }

    public void addTask(TaskReportDto task) {
        this.tasks.add(task);
    }
}

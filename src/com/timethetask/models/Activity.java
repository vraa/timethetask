package com.timethetask.models;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.timethetask.utils.Constants;

@PersistenceCapable
public class Activity implements Serializable, Constants {

    private static final long serialVersionUID = -192294152666307007L;
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @PrimaryKey
    private Key key;
    @Persistent
    private Key userKey;
    @Persistent
    private Key taskKey;
    @Persistent
    private Date start;
    @Persistent
    private Date end;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
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

    public Key getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(Key taskKey) {
        this.taskKey = taskKey;
    }

    public Key getUserKey() {
        return userKey;
    }

    public void setUserKey(Key userKey) {
        this.userKey = userKey;
    }

    @Override
    public String toString() {
        return getTaskKey().toString() + COLON + getUserKey().toString() + COLON + getKey().toString();
    }
}

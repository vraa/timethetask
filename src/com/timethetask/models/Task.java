package com.timethetask.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;

@PersistenceCapable
public class Task implements Serializable, Constants {

    private static final long serialVersionUID = 7947466038035009278L;
    private static final Helper helper = Helper.INSTANCE;
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @PrimaryKey
    private Key key;
    @Persistent
    private Key userKey;
    @Persistent
    private String name;
    @Persistent
    private Date createdOn;
    @Persistent
    private Date lastActivityOn;
    @Persistent
    private String priority;
    @Persistent
    private String status;
    @Persistent
    private String project;
    @Persistent
    private Boolean isClosed;
    @Persistent
    private String color;
    @Persistent
    private String icon;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return helper.isEmpty(status) ? Constants.DEFAULT_STATUS : status;
    }

    public void setStatus(String status) {
        this.status = helper.isEmpty(status) ? Constants.DEFAULT_STATUS : status;
    }

    public void setUserKey(Key userKey) {
        this.userKey = userKey;
    }

    public Key getUserKey() {
        return userKey;
    }

    public Date getLastActivityOn() {
        return lastActivityOn;
    }

    public void setLastActivityOn(Date lastActivityOn) {
        this.lastActivityOn = lastActivityOn;
    }

    public String getProject() {
        return (helper.isEmpty(project)) ? Constants.DEFAULT_PROJECT : project;
    }

    public void setProject(String project) {
        this.project = (helper.isEmpty(project)) ? Constants.DEFAULT_PROJECT : project;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public void setClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getColor() {
        return helper.isEmpty(color) ? Constants.DEFAULT_COLOR : color;
    }

    public void setColor(String color) {
        this.color = helper.isEmpty(color) ? Constants.DEFAULT_COLOR : color;
    }

    public String getIcon() {
        return helper.isEmpty(icon) ? Constants.DEFAULT_ICON : icon;
    }

    public void setIcon(String icon) {
        this.icon = helper.isEmpty(icon) ? Constants.DEFAULT_ICON : icon;
    }

    @Override
    public String toString() {
        return getKey().toString() + COLON + getName();
    }
}

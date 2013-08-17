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

@PersistenceCapable
public class User implements Serializable, Constants {

    private static final long serialVersionUID = 5630816809239732675L;
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @PrimaryKey
    private Key key;
    @Persistent
    private String email;
    @Persistent
    private String password;
    @Persistent
    private Date joinedOn;
    @Persistent
    private Date lastLogin;
    @Persistent
    private String timeZone;
    @Persistent
    private boolean emailVerified;
    @Persistent
    private String plan;
    @Persistent
    private String auth;
    @Persistent
    private List<String> projects;

    @Override
    public String toString() {
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("{Email:").append(getEmail()).append(",");
        userInfo.append("Plan:").append(getPlan()).append(",");
        userInfo.append("Auth mode:").append(getAuth()).append("}");
        return userInfo.toString();
    }

    public String getFormattedAuth() {
        String formattedAuth = "";
        if (auth.equalsIgnoreCase(GOOGLE)) {
            formattedAuth = "Google";
        } else if (auth.equalsIgnoreCase(TWITTER)) {
            formattedAuth = "Twitter";
        } else if (auth.equalsIgnoreCase(FACEBOOK)) {
            formattedAuth = "Facebook";
        } else if (auth.equalsIgnoreCase(TIMETHETASK)) {
            formattedAuth = "TimeTheTask";
        }
        return formattedAuth;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Date getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
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
        if (!(isProjectExists(project))) {
            this.projects.add(project);
        }
    }

    public boolean isProjectExists(String project) {
        for (String prj : this.projects) {
            if (prj.equalsIgnoreCase(project)) {
                return true;
            }
        }
        return false;
    }
}

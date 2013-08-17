package com.timethetask.dtos;

import com.timethetask.models.User;
import java.io.Serializable;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 2384771155073302535L;
    private String email;
    private String joinedOn;
    private String timeZone;
    private String emailVerified;
    private String auth;
    private String plan;

    public UserDto() {
    }

    public UserDto(User user) {
        this.email = user.getEmail();
        this.joinedOn = user.getJoinedOn().toString();
        this.timeZone = user.getTimeZone();
        this.auth = user.getAuth();
        this.plan = user.getPlan();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(String joinedOn) {
        this.joinedOn = joinedOn;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}

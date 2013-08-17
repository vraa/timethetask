<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="com.timethetask.models.User"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>

<%
            User user = (User) session.getAttribute("user");
            UserService userService = UserServiceFactory.getUserService();
%>
<div class="outer-wrapper top">
    <div class="header clearfix inner-wrapper">
        <a href=""javascript:void(0);"><h1>timethetask</h1></a>
        <div class="server-message rounded">
            <span id="message" class="message rounded">Updating your task to server</span>
        </div>
        <ul class="user-info menu">
            <li><%= user.getEmail()%></li>
            <li><a href='<%= userService.createLogoutURL("/index.jsp")%>'>Logout</a></li>
        </ul>
    </div>
</div>
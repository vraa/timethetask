package com.timethetask.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;

public class Login extends HttpServlet implements Constants {

    private static final long serialVersionUID = -7925946334926907224L;
    private UserService userService = UserService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Boolean isLoggedIn = (Boolean) session.getAttribute(ISLOGGEDIN);
        if (isLoggedIn) {
            String email = (String) session.getAttribute(USEREMAIL);
            String auth = (String) req.getParameter(AUTH);
            String password = (String) req.getParameter(USERPASSWORD);
            User user = userService.get(email);
            if (user == null) {
                user = UserFactory.createNewUser(email, auth, password);
                userService.create(user);
            }
            session.setAttribute(USER, user);
            user.setLastLogin(Helper.INSTANCE.getNow());
            userService.update(user);
            resp.sendRedirect(resp.encodeRedirectURL("/user/home"));
        }
    }
}

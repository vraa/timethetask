package com.timethetask.controllers;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import com.timethetask.utils.ResponseJson;
import java.util.logging.Level;
import org.datanucleus.cache.Level1Cache;

public class Profile extends HttpServlet implements Constants {

    private static final long serialVersionUID = -6937873612796122582L;
    private static final Logger logger = Logger.getLogger(Profile.class.getSimpleName());
    private UserService userService = UserService.INSTANCE;
    private static final Helper helper = Helper.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());

        String timeZone = req.getParameter(TIMEZONE);
        timeZone = (helper.isEmpty(timeZone)) ? DEFAULT_TIME_ZONE : timeZone;

        user.setTimeZone(timeZone);
        userService.update(user);

        session.setAttribute(USER, user);

        logger.log(Level.FINE, LEAVING, user.getEmail());
        req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);

    }
}

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

public class GetProjects extends HttpServlet implements Constants {

    private static final long serialVersionUID = -1144756751084410937L;
    private static final Logger logger = Logger.getLogger(GetProjects.class.getSimpleName());
    private Helper helper = Helper.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        logger.log(Level.FINE, ENTERING, user.getEmail());

        ResponseJson responseJson = new ResponseJson();
        for (String project : user.getProjects()) {
            responseJson.addProject(project);
        }

        responseJson.setStatus(SUCCESS);
        responseJson.setMessage("Succesfully retrieved projects");

        String jsonOutput = helper.toJson(responseJson);
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        writer.write(jsonOutput);
        writer.close();

        logger.log(Level.FINE, LEAVING, user.getEmail());

    }
}

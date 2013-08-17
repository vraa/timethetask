package com.timethetask.controllers;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;

public class LoginFilter implements Filter, Constants {

	private FilterConfig filterConfig = null;
	private Helper helper = Helper.INSTANCE;
	private UserService userService = UserServiceFactory.getUserService();

	@Override
	public void destroy() {
		this.filterConfig = null;

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		if (filterConfig == null) {
			return;
		}

		boolean isLoggedIn = false;
		String email = "";
		String auth = req.getParameter(AUTH);
		if (!helper.isEmpty(auth)) {
			auth = auth.trim();
			if (auth.equalsIgnoreCase(GOOGLE)) {
				User gUser = userService.getCurrentUser();
				if (gUser != null) {
					isLoggedIn = true;
					email = gUser.getEmail();
				}
			} else if (auth.equalsIgnoreCase(TWITTER)) {
				/* TODO implement */
			} else if (auth.equalsIgnoreCase(TIMETHETASK)) {
				/* TODO implement */
			}

			if (isLoggedIn) {
				HttpSession session = ((HttpServletRequest) req).getSession();
				if (session == null) {
					req.getRequestDispatcher("/index.jsp").forward(req, resp);
				}
				session.setAttribute(USEREMAIL, email);
				session.setAttribute(ISLOGGEDIN, true);
				chain.doFilter(req, resp);
			} else {
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}

		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}

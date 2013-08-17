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

import com.timethetask.models.User;
import com.timethetask.utils.Constants;

public class AuthFilter implements Filter, Constants {

	private FilterConfig filterConfig = null;

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

		HttpSession session = ((HttpServletRequest) req).getSession();
		if (session != null) {
			User user = (User) session.getAttribute(USER);
			if (user != null) {
				chain.doFilter(req, resp);
				return;
			}
		}

		req.getRequestDispatcher("/index.jsp").forward(req, resp);

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}

}

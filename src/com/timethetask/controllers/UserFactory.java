package com.timethetask.controllers;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;

public final class UserFactory implements Constants {

	private static Helper helper = Helper.INSTANCE;
	private static final Logger logger = Logger.getLogger(UserFactory.class.getSimpleName());

	private UserFactory() {

	}

	public static User createNewUser(String email, String auth, String password) {
		logger.info(ENTERING + email);
		
		User user = new User();

		user.setEmail(email);
		user.setAuth(auth);
		if (auth.equalsIgnoreCase(GOOGLE) || auth.equalsIgnoreCase(TWITTER) || auth.equalsIgnoreCase(FACEBOOK)) {
			user.setEmailVerified(true);
			user.setPassword("");
		} else {
			user.setEmailVerified(false);
			user.setPassword(password);
		}
		user.setJoinedOn(helper.getNow());
		user.setTimeZone(DEFAULT_TIME_ZONE);
		user.setPlan(BASIC);
		user.setProjects(new ArrayList<String>());

		logger.info("Completed creating new user." + user);
		logger.fine(LEAVING + email);
		return user;
	}

}

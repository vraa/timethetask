package com.timethetask.utils;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public enum PMF {
	INSTANCE;
	public PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
}

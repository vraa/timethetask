package com.timethetask.controllers;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.timethetask.models.User;
import com.timethetask.utils.PMF;

public enum UserService {

	INSTANCE;

	private PersistenceManager pm = PMF.INSTANCE.pmf.getPersistenceManager();

	public User get(String email) {
		Key key = KeyFactory.createKey(User.class.getSimpleName(), email);
		User user = null;
		try {
			user = pm.getObjectById(User.class, key);
		} catch (Exception e) {
			user = null;
		}
		return user;
	}

	public void create(User user) {
		Key key = KeyFactory.createKey(User.class.getSimpleName(), user.getEmail());
		user.setKey(key);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

	}

	public void update(User user) {
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

	}

}

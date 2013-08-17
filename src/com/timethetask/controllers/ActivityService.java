/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timethetask.controllers;

import com.timethetask.models.Activity;
import com.timethetask.models.Task;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.Helper;
import com.timethetask.utils.PMF;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author veera
 */
public enum ActivityService implements Constants {

    INSTANCE;
    private PersistenceManager pm = PMF.INSTANCE.pmf.getPersistenceManager();
    private static final Logger logger = Logger.getLogger(ActivityService.class.getSimpleName());
    private static final Helper helper = Helper.INSTANCE;

    public List<Activity> read(User user, Date startDateParam) {
        List<Activity> activities = new ArrayList<Activity>();
        Query query = pm.newQuery(Activity.class);
        query.setFilter("userKey == userKeyParam && start >= startDateParam");
        query.setOrdering("start descending");
        query.declareParameters("com.google.appengine.api.datastore.Key userKeyParam, java.util.Date startDateParam");
        try {
            activities = (List<Activity>) query.execute(user.getKey(), startDateParam);
        } catch (Exception e) {
            logger.log(Level.WARNING, EXCEPTION, user.getEmail() + COLON + e.getMessage());
        } finally {
            query.closeAll();
        }
        return activities;
    }

    public List<Activity> read(Task task) {
        List<Activity> result = new ArrayList<Activity>();
        Query query = pm.newQuery(Activity.class);
        query.setFilter("taskKey == taskIdParam");
        query.setOrdering("start desc");
        query.declareParameters("com.google.appengine.api.datastore.Key taskIdParam");

        try {
            result = (List<Activity>) query.execute(task.getKey());
        } catch (Exception e) {
            logger.log(Level.WARNING, EXCEPTION, e.getMessage());
        } finally {
            query.closeAll();
        }
        return result;
    }

    public List<Activity> read(Task task, long limit) {
        List<Activity> result = new ArrayList<Activity>();
        Query query = pm.newQuery(Activity.class);
        query.setFilter("taskKey == taskIdParam");
        query.setOrdering("start desc");
        query.declareParameters("com.google.appengine.api.datastore.Key taskIdParam");
        query.setRange(0, limit);

        try {
            result = (List<Activity>) query.execute(task.getKey());
        } catch (Exception e) {
            logger.log(Level.WARNING, EXCEPTION, e.getMessage());
        } finally {
            query.closeAll();
        }
        return result;
    }

    public void create(Activity activity, User user) {
        update(activity, user);
    }

    public void update(Activity activity, User user) {
        if (activity.getUserKey().equals(user.getKey())) {
            Transaction tx = pm.currentTransaction();
            try {
                tx.begin();
                pm.makePersistent(activity);
                tx.commit();
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error occured: {0}", e.getMessage());
            } finally {
                if (tx.isActive()) {
                    tx.rollback();
                }
            }
        }
    }

    public Activity getLastActivity(Task task) {
        Activity lastActivity = null;
        Query query = pm.newQuery(Activity.class);
        query.setFilter("taskKey == taskKeyParam");
        query.declareParameters("com.google.appengine.api.datastore.Key taskKeyParam");
        query.setOrdering("start descending");
        List<Activity> result = (List<Activity>) query.execute(task.getKey());
        if (result.size() > 0) {
            lastActivity = result.get(0);
        }
        return lastActivity;
    }

    public void delete(Activity activity) {
    }

    public void delete(Task task, User user) {
        if (task.getUserKey().equals(user.getKey())) {
            List<Activity> activities = read(task);
            for (Activity activity : activities) {
                Transaction tx = pm.currentTransaction();
                try {
                    tx.begin();
                    pm.deletePersistent(activity);
                    tx.commit();
                } catch (Exception e) {
                    logger.log(Level.WARNING, EXCEPTION, e.getMessage());
                } finally {
                    if (tx.isActive()) {
                        tx.rollback();
                    }
                }
            }
        }
    }
}

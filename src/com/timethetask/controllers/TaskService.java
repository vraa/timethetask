package com.timethetask.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.timethetask.models.Task;
import com.timethetask.models.User;
import com.timethetask.utils.Constants;
import com.timethetask.utils.PMF;
import java.util.logging.Level;

public enum TaskService implements Constants {

    INSTANCE;
    private PersistenceManager pm = PMF.INSTANCE.pmf.getPersistenceManager();
    private static final Logger logger = Logger.getLogger(TaskService.class.getSimpleName());

    public void create(Task task, User user) {
        update(task, user);
    }

    @SuppressWarnings("unchecked")
    /**
     * this method will return all the tasks of the given user that satisfies the passed
     * parameters.
     */
    public List<Task> read(final User user, String status, boolean isClosed) {
        List<Task> taskList = new ArrayList<Task>();
        Query query = pm.newQuery(Task.class);
        query.setFilter("userKey == userKeyParam && status == statusParam && isClosed == closedParam");
        query.declareParameters("com.google.appengine.api.datastore.Key userKeyParam, String statusParam, boolean closedParam");
        query.setOrdering("lastActivityOn ascending");
        try {
            taskList = (List<Task>) query.execute(user.getKey(), status, isClosed);
        } catch (Exception e) {
            taskList = null;
        } finally {
            query.closeAll();
        }
        return taskList;
    }

    public Task read(String taskId) {
        Task task = null;
        try {
            Key taskKey = KeyFactory.createKey(Task.class.getSimpleName(), Long.parseLong(taskId));
            task = pm.getObjectById(Task.class, taskKey);
        } catch (Exception e) {
            logger.fine("The task was not found on the data store!");
        }
        return task;
    }

    @SuppressWarnings("unchecked")
    public List<Task> read(User user, boolean isClosed) {
        List<Task> taskList = new ArrayList<Task>();
        Query query = pm.newQuery(Task.class);
        query.setFilter("userKey == userKeyParam && isClosed == closedParam");
        query.declareParameters("com.google.appengine.api.datastore.Key userKeyParam, boolean closedParam");
        query.setOrdering("lastActivityOn ascending");
        try {
            taskList = (List<Task>) query.execute(user.getKey(), isClosed);
        } catch (Exception e) {
            logger.log(Level.WARNING, EXCEPTION, e.getMessage());
        }
        return taskList;
    }

    public List<Task> readAll(User user) {
        List<Task> taskList = new ArrayList<Task>();
        taskList.addAll(read(user, true));
        taskList.addAll(read(user, false));
        return taskList;
    }

    public void update(Task task, User user) {
        if (task.getUserKey().equals(user.getKey())) {
            Transaction tx = pm.currentTransaction();
            try {
                tx.begin();
                pm.makePersistent(task);
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

    public void delete(Task task, User user) {
        if (task.getUserKey().equals(user.getKey())) {
            Transaction tx = pm.currentTransaction();
            try {
                tx.begin();
                pm.deletePersistent(task);
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

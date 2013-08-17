package com.timethetask.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public enum Helper implements Constants {

    INSTANCE;
    private Gson gson = new Gson();

    public boolean isEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    public Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    public Date getPastDate(Date fromDate, int numOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.add(Calendar.DATE, -(numOfDays));
        return cal.getTime();
    }

    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public void echo(Object obj) {
        System.out.println(obj);
    }

    public String formateDateToString(String tzId, Date date, String format) {
        date = (date == null) ? getNow() : date;
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setTimeZone(TimeZone.getTimeZone(tzId));
        return fmt.format(date);
    }

    public Date formateStringToDate(String tzId, String date, String format) {
        if (isEmpty(date)) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setTimeZone(TimeZone.getTimeZone(tzId));
        Date parsedDate = null;
        try {
            parsedDate = fmt.parse(date);
        } catch (Exception e) {
            parsedDate = null;
        }
        return parsedDate;

    }

    public boolean isSameDay(Date dateOne, Date dateTwo) {
        if (dateOne == null || dateTwo == null) {
            return false;
        }
        Calendar calOne = Calendar.getInstance();
        Calendar calTwo = Calendar.getInstance();
        calOne.setTime(dateOne);
        calTwo.setTime(dateTwo);
        if (calOne.get(Calendar.DAY_OF_MONTH) == calTwo.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    /* TODO fix the dplicate XSS cleaning issue */
    public String xssClean(String str) {
        if (isEmpty(str)) {
            return "";
        }

        StringBuilder cleanStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '&':
                    cleanStr.append("&amp;");
                    break;
                case '<':
                    cleanStr.append("&lt;");
                    break;
                case '>':
                    cleanStr.append("&gt;");
                    break;
                case '"':
                    cleanStr.append("&quot;");
                    break;
                case '\'':
                    cleanStr.append("&#x27;");
                    break;
                case '/':
                    cleanStr.append("&#x2F;");
                    break;
                default:
                    cleanStr.append(c);
                    break;
            }
        }
        return cleanStr.toString();
    }

    public long getTimeDiff(Date dateOne, Date dateTwo) {
        dateOne = (dateOne == null) ? getNow() : dateOne;
        dateTwo = (dateTwo == null) ? getNow() : dateTwo;
        return Math.abs(dateOne.getTime() - dateTwo.getTime());
    }

    /**
     * return the difference between two dates in a formatted string. for example 1 hours and 5 minutes.
     */
    public String diff(Date dateOne, Date dateTwo) {
        dateOne = (dateOne == null) ? getNow() : dateOne;
        dateTwo = (dateTwo == null) ? getNow() : dateTwo;
        String diff = "";
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        diff = String.format("%d hour(s) %d min(s)", TimeUnit.MILLISECONDS.toHours(timeDiff),
                TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        return diff;
    }

    public static void main(String[] args) {
        Helper helper = Helper.INSTANCE;

    }
}

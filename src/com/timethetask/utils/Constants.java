package com.timethetask.utils;

public interface Constants {

    /* task status values */
    public static final String DEFINED = "d";
    public static final String INPROGRESS = "i";
    public static final String ONHOLD = "o";
    public static final String COMPLETED = "c";
    public static final String ARCHIVED = "a";

    /* task priority values */
    public static final String HIGH = "h";
    public static final String LOW = "l";

    /* user plan value */
    public static final String BASIC = "b";
    public static final String PRO = "p";

    /* user authentication systems */
    public static final String TIMETHETASK = "tt";
    public static final String GOOGLE = "g";
    public static final String TWITTER = "t";
    public static final String FACEBOOK = "f";

    /* Reports related variables */
    public static final String GRP_ACTIVITY = "a";
    public static final String GRP_TASK = "t";
    public static final String GRP_PROJECT = "p";

    /* system defaults */
    public static final String DEFAULT_TIME_ZONE = "Asia/Calcutta";
    public static final String DEFAULT_NUM_OF_TASKS = "10";
    public static final String DEFAULT_PROJECT = "General";
    public static final String DEFAULT_ICON = "ico_task";
    public static final String DEFAULT_COLOR = "white";
    public static final String DEFAULT_STATUS = DEFINED;
    public static final String DEFAULT_GROUP_BY = GRP_ACTIVITY;
    public static final String DEFAULT_HISTORY_FOR = "10";
    public static final long DEFAULT_ACTIVITY_LIMIT = 10;
    public static final String DATE_FORMAT_WITH_TIME = "h:mm a : EEE, d-MMM, yyyy";
    public static final String DATE_FORMAT = "EEE, d-MMM, yyyy";
    public static final String DATE_PICKER_FORMAT = "MM/dd/yyyy HH:mm";

    /* global variable names */
    public static final String USER = "user";
    public static final String AUTH = "auth";
    public static final String USEREMAIL = "useremail";
    public static final String USERPASSWORD = "userpassword";
    public static final String ISLOGGEDIN = "isloggedin";
    public static final String TASKNAME = "taskName";
    public static final String TASKID = "taskId";
    public static final String STATUS = "status";
    public static final String NUMOFTASKS = "numoftasks";
    public static final String NEWTASKID = "newTaskId";
    public static final String PROJECT = "project";
    public static final String TIMEZONE = "timezone";
    public static final String ICON = "icon";
    public static final String COLOR = "color";
    public static final String PRIORITY = "priority";
    public static final String GROUPBY = "groupBy";
    public static final String HISTORYFOR = "historyFor";
    public static final String ACTIVITIES = "activities";

    /* operation statuses */
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String ERROR = "error";

    /* log message frequent strings */
    public static final String ENTERING = "[Entering] {0}";
    public static final String LEAVING = "[Leaving] {0}";
    public static final String ABORTED = "[Aborted] ";
    public static final String EXCEPTION = "[Exception] {0}";

    /* symbols */
    public static final String AMPERSAND = "&";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String[] TIMEZONES = {"Pacific/Midway", "US/Hawaii", "US/Alaska", "US/Pacific",
        "America/Tijuana", "US/Arizona", "America/Chihuahua", "US/Mountain", "America/Guatemala", "US/Central",
        "America/Mexico_City", "Canada/Saskatchewan", "America/Bogota", "US/Eastern", "US/East-Indiana",
        "Canada/Eastern", "America/Caracas", "America/Manaus", "America/Santiago", "Canada/Newfoundland",
        "Brazil/East", "America/Buenos_Aires", "America/Godthab", "America/Montevideo", "Atlantic/South_Georgia",
        "Atlantic/Azores", "Atlantic/Cape_Verde", "Africa/Casablanca", "Europe/London", "Europe/Berlin",
        "Europe/Belgrade", "Europe/Brussels", "Europe/Warsaw", "Africa/Algiers", "Asia/Amman", "Europe/Athens",
        "Asia/Beirut", "Africa/Cairo", "Africa/Harare", "Europe/Helsinki", "Asia/Jerusalem", "Europe/Minsk",
        "Africa/Windhoek", "Asia/Baghdad", "Asia/Kuwait", "Europe/Moscow", "Africa/Nairobi", "Asia/Tbilisi",
        "Asia/Tehran", "Asia/Muscat", "Asia/Baku", "Asia/Yerevan", "Asia/Kabul", "Asia/Yekaterinburg",
        "Asia/Karachi", "Asia/Calcutta", "Asia/Colombo", "Asia/Katmandu", "Asia/Novosibirsk", "Asia/Dhaka",
        "Asia/Rangoon", "Asia/Bangkok", "Asia/Krasnoyarsk", "Asia/Hong_Kong", "Asia/Irkutsk", "Asia/Kuala_Lumpur",
        "Australia/Perth", "Asia/Taipei", "Asia/Tokyo", "Asia/Seoul", "Asia/Yakutsk", "Australia/Adelaide",
        "Australia/Darwin", "Australia/Brisbane", "Australia/Sydney", "Pacific/Guam", "Australia/Hobart",
        "Asia/Vladivostok", "Asia/Magadan", "Pacific/Auckland", "Pacific/Fiji", "Pacific/Tongatapu"};
}

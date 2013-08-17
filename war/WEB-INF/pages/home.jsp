<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page import="com.timethetask.models.User"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%><html>
    <head>
        <jsp:include page="htmlhead.jsp"/>
    </head>
    <%
                User user = (User) session.getAttribute("user");
                UserService userService = UserServiceFactory.getUserService();
    %>
    <body class="home-page">

        <jsp:include page="header.jsp"/>

        <div class="main inner-wrapper clearfix">

            <jsp:include page="appMenu.jsp"/>

            <div class="board drop-shadow rounded">
                <div class="current clearfix">
                    <h2>Current Task <a class="create-new-task rounded" rel="i" href="javascript:void(0);" id="create-new-task">New task</a></h2>
                    <div class="task-clock-wrapper clearfix">
                        <ul class="task-list rounded" id="none"></ul>
                    </div>

                </div>

                <div class="panels-wrapper clearfix">

                    <div class="panel defined">
                        <h2>Defined</h2>
                        <ul class="task-list"></ul>
                    </div>

                    <div class="panel onhold">
                        <h2>on hold</h2>
                        <ul class="task-list"></ul>
                    </div>

                    <div class="panel completed">
                        <h2>Completed</h2>
                        <ul class="task-list"></ul>
                    </div>

                </div>
            </div>
        </div>

        <!-- END OF PAGE LAYOUT -->

        <div id="task-template" class="hide">
            <a href="javascript:void(0);" title="Close this task. Note: This will *not* delete the task" class="close"><img alt="x" src="../../img/close.png"/></a>
            <p title="project name" class="task-project">%taskproject%</p>
            <p class="task-name">%taskname%</p>
            <div class="task-meta">
                <span class="task-info">%taskinfo%</span>
                <span class="controls">
                    <a href="javascript:void(0);" title="Edit this task" class="edit"><img alt="*" src="../../img/edit.gif"/></a>
                    <a href="javascript:void(0);" title="Delete this task" class="delete"><img alt="-" src="../../img/trash.gif"/></a>
                </span>
            </div>
        </div>

        <div id="task-form" class="hide" title="Task form">
            <form method="post">
                <input type="hidden" name="task-id" value=""/>
                <ol>
                    <li>
                        <fieldset class="task-fields">
                            <label class="taskFormLabel">Task name and status</label>
                            <input type="text" name="task-name" id="task-name" class="big" />
                            <div id="task-type-radio">
                                <input type="radio" id="radio-i" name="task-type" value="i" checked="checked"/><label for="radio-i">Work now</label>
                                <input type="radio" id="radio-d" name="task-type" value="d"/><label for="radio-d">Define</label>
                                <input type="radio" id="radio-o" name="task-type" value="o"/><label for="radio-o">On Hold</label>
                                <input type="radio" id="radio-c" name="task-type" value="c"/><label for="radio-c">Completed</label>
                            </div>
                        </fieldset>
                    </li>
                    <li class="task-grouping-fields">
                        <fieldset>
                            <div>
                                <label for="project" class="taskFormLabel">Project</label>
                                <input type="text" id="project" name="project" class="project smallInput" />
                            </div>

                            <div>
                                <label for="color" class="taskFormLabel">Color</label>
                                <select id="color" name="color" class="white">
                                    <option value="white" class="white">White</option>
                                    <option value="red" class="red">Red</option>
                                    <option value="green" class="green">Green</option>
                                    <option value="blue" class="blue">Blue</option>
                                    <option value="yellow" class="yellow">Yellow</option>
                                    <option value="purple" class="purple">Purple</option>
                                </select>
                            </div>
                            <div>
                                <label for="icon" class="taskFormLabel">Icon</label>
                                <select id="icon" name="icon">
                                    <option value="0">-- Icon --</option>
                                </select>
                            </div>
                        </fieldset>
                    </li>
                    <li class="task-activities">
                        <label class="taskFormLabel">Past Activities (optional)</label>
                        <div class="add-task-activity clearfix">
                            <div class="column act-date">
                                <label class="taskFormLabel">Date:</label>
                                <input type="text" name="actDate" id="actDate" class="smallInput column" readonly="readonly" placeholder="choose date" style="background-color:#fff;"/>
                            </div>
                            <div class="column act-start-time">
                                <label class="taskFormLabel">Start Time:</label>
                                <select id="start" disabled="disabled"></select>
                            </div>
                            <div class="column act-end-time">
                                <label class="taskFormLabel">End Time:</label>
                                <select id="end" disabled="disabled"></select>
                            </div>
                            <a href="javascript:void(0);" class="column add-activity-btn">+ Add</a>
                        </div>

                        <div class="task-activities-list"></div>
                    </li>
                </ol>
            </form>
        </div>

        <jsp:include page="footer.jsp"/>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#home-icon").addClass("active");
            });
        </script>
        <script type="text/javascript" src="../../js/jquery.sparkline.min.js"></script>
        <script type="text/javascript" src="../../js/timethetask.js"></script>

    </body>
</html>
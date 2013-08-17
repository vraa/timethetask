<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%><html>
    <head>
        <jsp:include page="WEB-INF/pages/htmlhead.jsp"/>        
        <link rel="stylesheet" href="js/fancybox/jquery.fancybox-1.3.4.css" type="text/css" media="screen"/>
    </head>
    <body class="index-page">
        <%
                    UserService userService = UserServiceFactory.getUserService();
        %>

        <div class="intro-box clearfix">
            <div class="about-panel">
                <h1>timethetask<sup>*</sup></h1>
                <p>Your tasks, managed and tracked.</p>
                <div class="screenshots">
                    <h2>Screenshots</h2>
                    <a href="img/screenshot-1.png" title="Visually group the tasks into different statuses, assign colors and project codes" rel="screenshot-group"><img src="img/screenshot-1.png" height="100px" alt="task manager in timethetask"/></a>
                    <a href="img/screenshot-2.png" title="Drag the task to change its status" rel="screenshot-group"><img src="img/screenshot-2.png" height="100px" alt="task manager in timethetask"/></a>
                    <a href="img/screenshot-3.png" title="See detailed reports on your work" rel="screenshot-group"><img src="img/screenshot-3.png" height="100px" alt="task manager in timethetask"/></a>
                </div>
                <div class="under-construction">
                    *This application is still under <strong>active development</strong>.
                </div>
            </div>
            <div class="login-panel">
                <ul>
                    <li class="cta">
                        <a href='<%= userService.createLoginURL("/user/login?auth=g")%>'>Click to login with <img src="img/google-logo.png" alt="Google"/></a>
                    </li>
                    <li class="inactive">
                        Coming Soon <img src="img/facebook-logo.png" alt="Facebook Login"/>
                    </li>
                    <li class="inactive">
                        Coming soon <img src="img/twitter-logo.png" alt="Twitter Login"/>
                    </li>
                </ul>
            </div>
        </div>

        <jsp:include page="WEB-INF/pages/footer.jsp"/>
        <script type="text/javascript" src="js/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
        <script type="text/javascript" src="js/fancybox/jquery.easing-1.3.pack.js"></script>

        <script type="text/javascript" src="js/intro.js"></script>
    </body>
</html>
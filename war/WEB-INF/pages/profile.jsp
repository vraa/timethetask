
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="com.timethetask.models.User"%>
<%
            User user = (User) session.getAttribute("user");
%>

<%@page import="java.util.TimeZone"%>
<%@page import="com.timethetask.utils.Constants"%>

<!DOCTYPE html>

<html>
    <head>
        <jsp:include page="htmlhead.jsp"/>
    </head>
    <body>

        <jsp:include page="header.jsp"/>

        <div class="main inner-wrapper clearfix">

            <jsp:include page="appMenu.jsp"/>

            <div class="board profile drop-shadow rounded">
                <div class="content">
                    <div class="section">
                        <form name="profile-form" id="profile-form" method="POST" action="/user/home/profile">
                            <h2>Personal Settings</h2>
                            <ul>
                                <li>
                                    <label>Email</label>
                                    <span class="field"><%=user.getEmail()%></span>
                                </li>

                                <li>
                                    <label>Authentication</label>
                                    <span class="field"><%=user.getFormattedAuth()%></span>
                                </li>

                                <li>
                                    <label>Time zone</label>
                                    <span class="field">
                                        <select name="timezone" id="timezone">
                                            <%
                                                        TimeZone zone = null;
                                                        for (String id : Constants.TIMEZONES) {
                                                            zone = TimeZone.getTimeZone(id);
                                                            int offset = zone.getRawOffset() / 1000;
                                                            int hour = offset / 3600;
                                                            int minutes = (offset % 3600) / 60;

                                                            if (user.getTimeZone().equals(id)) {
                                            %>
                                            <option selected="selected" value="<%= id%>"><%= String.format("(GMT%+d:%02d) %s", hour, minutes, id)%></option>
                                            <%
                                                                                                    } else {
                                            %>
                                            <option value="<%= id%>"><%= String.format("(GMT%+d:%02d) %s", hour, minutes, id)%></option>
                                            <%
                                                            }
                                                        }
                                            %>
                                        </select>
                                    </span>
                                </li>
                            </ul>
                            <input type="submit" value="Save Changes" />
                        </form>
                    </div>
                    <div class="section">
                        <h2>Download your data</h2>
                        <div class="download-data">
                            <p>If you wish, you can download your data. The file will be in JSON format and will include the task details, project names and all activities.</p>
                            <a href="/user/home/download-data">Download now</a>
                        </div>
                    </div>

                </div>

            </div>
        </div>

        <!-- END OF PAGE LAYOUT -->


        <jsp:include page="footer.jsp"/>
        <script type="text/javascript" src="../../js/profile.js"></script>
    </body>
</html>






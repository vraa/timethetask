<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <jsp:include page="htmlhead.jsp"/>
    </head>
    <body class="reports-page">

        <jsp:include page="header.jsp"/>

        <div class="main inner-wrapper clearfix">

            <jsp:include page="appMenu.jsp"/>

            <div class="board drop-shadow rounded report">
                <div class="content">
                    <div class="report-panel clearfix">
                        <span id="report-group">
                            Group by:
                            <input type="radio" id="group-task" name="groupBy" value="t" checked="checked"/><label for="group-task">Task</label>
                            <input type="radio" id="group-activity" name="groupBy" value="a"/><label for="group-activity">Activity</label>
                        </span>
                        <span id="report-days">
                            Period:
                            <input type="radio" id="radio-one" name="historyFor" value="7" checked="checked"/><label for="radio-one">1 week</label>
                            <input type="radio" id="radio-two" name="historyFor" value="14"/><label for="radio-two">2 weeks</label>
                            <input type="radio" id="radio-three" name="historyFor" value="21"/><label for="radio-three">3 weeks</label>
                            <input type="radio" id="radio-four" name="historyFor" value="28"/><label for="radio-four">4 weeks</label>
                        </span>
                    </div>
                    <ul id="report" class="report">
                        
                    </ul>
                </div>
            </div>
        </div>

        <!-- END OF PAGE LAYOUT -->

        <jsp:include page="footer.jsp"/>
        <script type="text/javascript" src="../../js/jquery.flot.min.js"></script>
        <script type="text/javascript" src="../../js/reports.js"></script>
    </body>
</html>
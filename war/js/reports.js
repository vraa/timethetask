var daysArray = [];
var taskArray = [];
var tttReports = {
    showReport:function(groupBy, days){
        tttCommon.showMessage("Downloading your reports", "progress");

        var reportHolder = $("#report");
        reportHolder.empty();
        
        $.get("/user/home/reports",{
            groupBy:groupBy,
            historyFor:days
        },function(reply){
            tttCommon.showMessage("Preparing your reports", "progress");
            /* build the task array for referring later. */
            var tasks = reply.tasks;
            for(var i=0; i<tasks.length;i++){
                taskArray[tasks[i].taskId] = tasks[i];
            }

            for(var dayIndex = 0; dayIndex < reply.days.length; dayIndex ++){
                var day = reply.days[dayIndex];
                var dayEffortSum = 0;
                var actEffortMap = [];
                var activityArray = [];
                for(var activityIndex = 0; activityIndex < day.activities.length; activityIndex++){
                    var activity = day.activities[activityIndex];
                    dayEffortSum += activity.elapsedTime;
                    actEffortMap.push([activityIndex+1, activity.elapsedTime]);
                    activityArray.push(activity);
                }
                daysArray["day"+dayIndex] = activityArray;

                /* store the original number of activities before padding up to 10 */
                var activitiesCount = actEffortMap.length;
                
                /* pad the effors array with dummy values to maintain a minimum 10 vertical bars on the chart*/
                for(var j = actEffortMap.length; j < 10; j++){
                    var dummyEffort = [j+1, 0];
                    actEffortMap.push(dummyEffort);
                }

                /* start building the report charts */
                
                if(groupBy == "a") {
                    reportHolder.addClass("activity-report");
                }else if(groupBy == "t"){
                    reportHolder.addClass("task-report");
                }else if(groupBy == "p"){
                    reportHolder.addClass("project-report");
                }
                
                var chartItem = $("<li>").attr("id", "day"+dayIndex).appendTo(reportHolder);
                var chartMeta = $("<span>").appendTo(chartItem).addClass("meta");

                var dateMeta = $("<dl>").addClass("date").appendTo(chartMeta);
                $("<dt>").html("Date").appendTo(dateMeta);
                $("<dd>").html(day.date).appendTo(dateMeta);

                var countMeta = $("<dl>").addClass("count").appendTo(chartMeta);
                if(groupBy == "a") {
                    $("<dt>").html("Activities").appendTo(countMeta);
                }else if(groupBy == "t"){
                    $("<dt>").html("Tasks").appendTo(countMeta);
                }else if(groupBy == "p"){
                    $("<dt>").html("Projects").appendTo(countMeta);
                }
                
                $("<dd>").html(activitiesCount + "&nbsp;<a class='show-all' href='javascript:void(0);'>show</a>").appendTo(countMeta);

                var effortMeta = $("<dl>").addClass("effort").appendTo(chartMeta);
                $("<dt>").html("Time Spent").appendTo(effortMeta);
                $("<dd>").html(tttCommon.formattedTime(dayEffortSum)).appendTo(effortMeta);

                var chartCanvas = $("<span>").css("height", "150px").css("width", "100%").appendTo(chartItem).addClass("canvas");

                $.plot(chartCanvas,[{
                    color:"#AFCEFF",
                    data:actEffortMap,
                    bars:{
                        show:true,
                        barWidth:.4
                    }
                }],{
                    yaxis:{
                        show:false
                    },
                    xaxis:{
                        show:false
                    },
                    grid:{
                        borderWidth:1,
                        borderColor:"#AFCEFF",
                        backgroundColor:"#EFF5FF",
                        hoverable:true,
                        clickable:true
                    }
                });
            }
            tttCommon.hideMessage();
        });

    },

    init:function(){
        tttReports.showReport("t",10);
    }
};






$(document).ready(function(){
    $("#reports-icon").addClass("active");
    $("#report-group").buttonset();
    $("#report-days").buttonset();
    tttReports.init();

    $("[name='groupBy']").click(function(){
        tttReports.showReport($(this).val(), $("[name='historyFor']:checked").val());
    });
    $("[name='historyFor']").click(function(){
        tttReports.showReport($("[name='groupBy']:checked").val(), $(this).val());
    });
    $("#report .meta .count .show-all").live("click", function(){
        var dayId = $(this).parents("li").attr("id");
        var dateText = $("#"+dayId).find(".date").children("dd").html();
        var totalEffort = $("#"+dayId).find(".effort").children("dd").html();
        var activities = daysArray[dayId];
        var dayReport = $("<div id='day-report'>");
        dayReport.attr("title", "Report for the day: " + dateText);
        var reportTable = $("<table width='100%'>");
        for(var actIndex=0; actIndex < activities.length; actIndex++){
            var row = $("<tr>");
            if((actIndex + 1) % 2 == 0){
                row.addClass("even");
            }else{
                row.addClass("odd");
            }
            var act = activities[actIndex];
            var task = taskArray[act.taskId];
            var taskName = task.taskName;
            var actHtml = "<td>" + (actIndex + 1) + "</td>";
            actHtml+= "<td class='a-name'>" + taskName + "</td>";
            actHtml += "<td class='a-effort'>" + tttCommon.formattedTime(act.elapsedTime) + "</td>";
            row.html(actHtml).appendTo(reportTable);
        }
        $("<td colspan='3' align='right' class='sum'>").html("Total: " + totalEffort).appendTo(reportTable);
        reportTable.appendTo(dayReport);
        
        $(dayReport).dialog({
            autoOpen:false,
            width:700,
            modal:true,
            resizable:false,
            buttons:{
                "Close":function(){
                    $(this).dialog("close");
                }
            },
            close:function(){
                $("div#day-report").remove();
            }
        }).dialog("open");

    });

    $("#report").live("plothover", function(event, pos, item){
        if(item){
            if($("#tooltip").length == 0){
                var dayId = $(event.target).parent("li").attr("id");
                var day = daysArray[dayId];
                var activity = day[item.dataIndex];
                var task = taskArray[activity.taskId];
                var content = "<em>"+task.taskName + "</em>";
                content += "<br/>You spent <em>" + tttCommon.formattedTime(activity.elapsedTime) + "</em>.";
                content += "<br/>Started on: <em>" + activity.startTime + "</em>";
                if(activity.end){
                    content += "<br/>Ended on: <em>" + activity.endTime + "</em>";
                }
                tttCommon.showTooltip(item.pageX+40, item.pageY, content);
            }
        }else{
            $("#tooltip").remove();
        }
    });
});

function echo(obj){
    console.log(obj);
}

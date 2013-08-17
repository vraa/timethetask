var definedTaskList = $(".defined .task-list");
var	onholdTaskList = $(".onhold .task-list");
var	completedTaskList = $(".completed .task-list");
var	currentTaskList = $(".current .task-list");
var projects = new Array();
var colorClasses = "";
	
var timethetask = {
	
    emptyMessage: "There are no tasks.",
    init:function(){
        this.downloadProjects();
        this.downloadTasks();
        definedTaskList.data("status", "d");
        onholdTaskList.data("status", "o");
        completedTaskList.data("status", "c");
        currentTaskList.data("status", "i");
		
        $("#color > option").each(function(){
            colorClasses += $(this).attr("value") + " ";
        })

    },
	
    downloadProjects:function(){
        $.get("/user/home/get-projects",function(reply){
            if(reply.projects){
                projects = new Array();
                for(var i=0; i< reply.projects.length; i++){
                    projects.push(reply.projects[i]);
                }
                $("#project").autocomplete({
                    source:projects
                });
            }
        });
    },
	
    newTask:function(){
        var tf = $("#task-form");
        var tt= $(tf).find("input[name='task-type']:checked").val();
        var ttParam = (tt == "i")?"d":tt;
        var taskBox = $(tf).find("input[name='task-name']");
        var tn= $(taskBox).val();
        var taskProject = $(tf).find("#project").val();
        var cp = $(tf).find("#color").val();
        var activities = "";
        $.each($("#task-form .task-activities-list p"), function(){
            var act = $(this);
            var dt = act.find(".date").html();
            activities += "0&" + dt + " " + act.find(".start").html() + "&" + dt + " " + act.find(".end").html() + ",";
        });
        tttCommon.showMessage("Creating a new task", "progress");
        $.post("/user/home/add-task",{
            taskName:tn,
            status:ttParam,
            project:taskProject,
            color:cp,
            activities:activities
        }, function(responseJson){
            tttCommon.hideMessage();
            $(taskBox).val("");
            var taskList;
            var task = responseJson.tasks[0];
            if(tt == "i"){
                timethetask.replaceCurrentTask(task.taskId);
            }else{
                if(tt == "o") taskList = onholdTaskList;
                else if(tt == "d") taskList = definedTaskList;
                else if(tt == "c") taskList = completedTaskList;
                timethetask.addTasksToList(taskList, task);
            }
			
            projects = new Array();
            for(var i=0; i< responseJson.projects.length; i++){
                projects.push(responseJson.projects[i]);
            }
			
            $("#project").autocomplete({
                source:projects
            });
        });
    },
	
    editTask:function(){
        var tf = $("#task-form");
        var tt= $(tf).find("input[name='task-type']:checked").val();
        var tid = $(tf).find("[name='task-id']").val();
        var tn = $(tf).find("input[name='task-name']").val();
        var tp = $(tf).find("#project").val();
        var cp = $(tf).find("#color").val();
        tttCommon.showMessage("Saving your changes", "progress");
        var task = $("#"+tid);
        $.post("/user/home/edit-task",{
            taskId:tid,
            taskName:tn,
            status:tt,
            project:tp,
            color:cp
        }, function(reply){
            tttCommon.hideMessage();
            if(reply.status == "success"){
                if(reply.projects){
                    projects = new Array();
                    for(var i=0; i< reply.projects.length; i++){
                        projects.push(reply.projects[i]);
                    }
                    $("#project").autocomplete({
                        source:projects
                    });
                }
                var oldTaskStatus = $(task).data("status");
                var taskList;
                var editedTask = reply.tasks[0];
				
                if(tt == oldTaskStatus){
                    timethetask.updateTask(tid, editedTask);
                }else{
                    $(task).fadeOut("slow", function(){
                        $(this).remove();
                    });
                    if(tt == "i"){
                        timethetask.replaceCurrentTask(editedTask.taskId);
                    }else{
                        if(tt == "o") taskList = onholdTaskList;
                        else if(tt == "d") taskList = definedTaskList;
                        else if(tt == "c") taskList = completedTaskList;
                        timethetask.addTasksToList(taskList, editedTask);
                    }
					
                }
            }
        });
		
    },
	
    downloadTasks:function(taskList, statusParam, limitParam){
        tttCommon.showMessage("Downloading your tasks from server", "progress");
        $.get("/user/home/get-task", function(reply){

            if(reply.status == "success"){
                tttCommon.hideMessage();
                var tasks = reply.tasks;
                if(tasks == undefined || tasks == null || tasks.length <=0){
                    timethetask.checkForEmptyList();
                    return;
                }

                var taskList;
                for(var i=0; i<tasks.length;i++){
                    var task = tasks[i];
                    if(task.status == "o"){
                        taskList = onholdTaskList;
                    }else if(task.status == "d"){
                        taskList = definedTaskList;
                    }else if (task.status == "c") {
                        taskList = completedTaskList;
                    }else if (task.status == "i") {
                        taskList = currentTaskList;
                    }

                    if(reply.activities){
                        task.activities = new Array();
                        var activities = reply.activities;
                        for(var j=0; j<activities.length;j++){
                            var activity = activities[j];
                            if(activity.taskId === task.taskId){
                                task.activities.push(activity);
                            }
                        }
                    }
                    
                    timethetask.addTasksToList(taskList, task);
                }

                if(!$(".current ul").hasClass("empty")){
                    var ct = $(".current ul li")[0];
                    var ctId = $(ct).attr("id");
                    $.get("/user/home/get-elapsed-time", {
                        taskId:ctId
                    }, function(reply){
                        var crntTask = $(".current ul li .task-name")[0];
                        $(crntTask).prepend("<div class='clock'>");
                        if(reply.status == "success"){
                            var activity = reply.activities[0];
                            tttCommon.startClock(activity.elapsedTime);
                        }
                    });
                }
				
            }
            timethetask.checkForEmptyList();
        });
    },
		
    addTasksToList:function(taskList, task){
        if($(taskList).hasClass("empty")){
            $(taskList).empty();
            $(taskList).removeClass("empty");
        }
        var newTask = $("<li class='task' id=" + task.taskId + "></li>");
        $(newTask).draggable({
            revert:"invalid",
            helper:"clone",
            cursor:"move",
            cursorAt:{
                top:0,
                left:0
            },
            helper:function(event){
                return $("<div class='drag-helper'> Move <strong>" + $(event.currentTarget).find(".task-name").html() + "</strong>.</div>");
            }
        });
		
        $(newTask).prependTo(taskList);
        timethetask.updateTask(task.taskId, task);
        timethetask.drawSparkLine(task);
    },
	
    updateTask:function(taskId, task){
        var taskItem = $("#"+taskId);
        var taskTemplate = $("#task-template").html();
        taskTemplate = taskTemplate.replace("%taskname%", task.taskName);
        taskTemplate = taskTemplate.replace("%taskinfo%", task.lastActivityOn);
        taskTemplate = taskTemplate.replace("%taskproject%", task.project);
        taskItem.html(taskTemplate);
        taskItem.removeClass(colorClasses).addClass(task.color);
        taskItem.data("taskId", task.taskId);
        taskItem.data("taskName", task.taskName);
        taskItem.data("project", task.project);
        taskItem.data("status", task.status);
        taskItem.data("priority", task.priority);
        taskItem.data("lastActivityOn", task.lastActivityOn);
        taskItem.data("color", task.color);
        if(task.activities){
            taskItem.data("activities", task.activities);
        }
    },

    drawSparkLine:function(task){
        if(task.activities){
            var taskItem = $("#" + task.taskId);
            var efforts = new Array();
            for(var i=0; i<task.activities.length;i++){
                efforts.push(task.activities[i].elapsedTime);
            }
            var spark = $("<span class='spark'>");
            spark.attr("title", "a glimpse of your last activities in this task");
            spark.prependTo(taskItem);
            spark.sparkline(efforts, {
                type:'bar',
                barColor:'#3B5998'
            });
        }

    },
	
    moveTask:function(taskIdParam, toList){
        tttCommon.showMessage("Moving your tasks", "progress");
        $.post("/user/home/move-task",{
            taskId:taskIdParam,
            status:toList.data("status")
        }, function(reply){
            tttCommon.hideMessage();
            var task = reply.tasks[0];
            var taskId = "#"+taskIdParam;
            $(taskId).find(".task-info").html(task.lastActivityOn);
            /* returned activities are for this task alone. so add them directly */
            task.activities = reply.activities;
            timethetask.addTasksToList(toList, task);
            if(toList.data("status")== "i"){
                $(".current ul li .task-name").prepend("<div class='clock'>");
                tttCommon.startClock(1);
            }
        });
    },
	
    deleteTask:function(taskIdParam){
        tttCommon.showMessage("Deleting your task", "progress");
        $.post("/user/home/delete-task", {
            taskId:taskIdParam
        }, function(reply){
            tttCommon.hideMessage();
            if(reply.status == "success"){
                $("#"+taskIdParam).hide("slow").remove();
                timethetask.checkForEmptyList();
            }
        });
    },
	
    closeTask:function(taskIdParam){
        tttCommon.showMessage("Closing your task", "progress");
        $.post("/user/home/close-task", {
            taskId:taskIdParam
        }, function(reply){
            tttCommon.hideMessage();
            $("#"+taskIdParam).hide("slow").remove();
            timethetask.checkForEmptyList();
        });
    },
	
    showEmptyMessage:function(taskList){
        $(taskList).addClass("empty");
        $(taskList).empty().append("<li>" + this.emptyMessage + "</li>");
    },
	
    checkForEmptyList:function(list){
        if(list == undefined || list == null){
            if(parseInt(definedTaskList.children().size()) <= 0){
                this.showEmptyMessage(definedTaskList);
            }
            if(parseInt(onholdTaskList.children().size()) <= 0){
                this.showEmptyMessage(onholdTaskList);
            }
            if(parseInt(completedTaskList.children().size()) <= 0){
                this.showEmptyMessage(completedTaskList);
            }
            if(parseInt(currentTaskList.children().size()) <= 0){
                this.showEmptyMessage(currentTaskList);
            }
        }else{
            if(parseInt($(list).children().size()) <= 0){
                this.showEmptyMessage(list);
            }
            if(list.data("status") == "i"){
                tttCommon.stopClock();
            }
        }
		
    },
	
    replaceCurrentTask:function(newTaskIdParam){
        if(!currentTaskList.hasClass("empty")){
            var task = currentTaskList.children("li")[0];
            var taskIdParam = $(task).attr("id");
            tttCommon.showMessage("Changing your current task", "progress");
            $.post("/user/home/current-task",{
                taskId:taskIdParam,
                newTaskId:newTaskIdParam
            }, function(reply){
                tttCommon.hideMessage();
                var newTask = $("#"+newTaskIdParam);
                $(newTask).remove();
                $(task).remove();
                timethetask.addTasksToList(onholdTaskList, reply.tasks[0]);
                timethetask.addTasksToList(currentTaskList, reply.tasks[1]);
                timethetask.checkForEmptyList();
                $(".current ul li .task-name").prepend("<div class='clock'>");
                tttCommon.startClock(1);
            });
        }else{
            currentTaskList.removeClass("empty");
            currentTaskList.empty();
            this.moveTask(newTaskIdParam, currentTaskList);
        }
    },
	
    resetTaskForm:function(){
        var tf = $("#task-form");
        tf.find("[type='text']").val("");
        tf.find("[name='task-type']").removeAttr("checked");
        tf.find("[name='task-type']").filter("[value='i']").attr("checked", "checked");
        $("#task-type-radio").buttonset();
        $("#color").val($("#color option:first").val());
        $("#color").attr("class", "white");
        tf.find(".task-activities-list").empty();
    }
	
}

function echo(msg){
    console.log(msg);
}

$(document).ready(function(){
	
    var ttt  = timethetask;
	
    ttt.init();
		

    $(".current").droppable({
        accept:".defined li, .onhold li, .completed li",
        activeClass:"ui-state-highlight",
        drop:function(event, ui){
            var task = ui.draggable;
            var fromList = task.closest(".task-list");
            task.fadeOut("fast", function(){
                task.remove();
                ttt.checkForEmptyList(fromList);
                ttt.replaceCurrentTask(task.attr("id"));
            });
        }
    });
	
    $(".onhold").droppable({
        accept: ".defined li, .completed li, .current li",
        activeClass: "ui-state-highlight",
        drop:function(event, ui){
            var toList = $(this).find(".task-list");
            if(toList.hasClass("empty")){
                toList.empty();
                toList.removeClass("empty");
            }
            var task = ui.draggable;
            var fromList = task.closest(".task-list");
            task.fadeOut("fast", function(){
                task.remove();
                ttt.checkForEmptyList(fromList);
                ttt.moveTask(task.attr("id"), toList);
            });
			
        }
    });
	
    $(".defined").droppable({
        accept: ".onhold li, .completed li, .current li",
        activeClass: "ui-state-highlight",
        drop:function(event, ui){
            var toList = $(this).find(".task-list");
            if(toList.hasClass("empty")){
                toList.empty();
                toList.removeClass("empty");
            }
            var task = ui.draggable;
            var fromList = task.closest(".task-list");
            task.fadeOut("fast", function(){
                task.remove();
                ttt.checkForEmptyList(fromList);
                ttt.moveTask(task.attr("id"), toList);
            });
			
        }
    });
	
    $(".completed").droppable({
        accept: ".onhold li, .defined li, .current li",
        activeClass: "ui-state-highlight",
        drop:function(event, ui){
			
            var toList = $(this).find(".task-list");
            if(toList.hasClass("empty")){
                toList.empty();
                toList.removeClass("empty");
            }
            var task = ui.draggable;
            var fromList = task.closest(".task-list");
            task.fadeOut("fast", function(){
                task.remove();
                ttt.checkForEmptyList(fromList);
                ttt.moveTask(task.attr("id"), toList);
            });
        }
    });
	
	
    $(".create-new-task").bind("click", function(){
        ttt.resetTaskForm();
        var taskForm = $("#task-form");
        $(taskForm).dialog({
            autoOpen:false,
            width:400,
            modal:true,
            resizable:false,
            buttons:{
                "Create":function(){
                    ttt.newTask();
                    $(this).dialog("close");
                }
            },
            close:function(){
            }
        });
        $("#task-form").dialog("open");
    });
    $("#task-form form").live("submit", function(){
        ttt.newTask();
    });

    /* start of START-END-INPUT-SECTION functions */
    $("#actDate").datepicker({
        onClose:function(){
            if($("#actDate").val() != ""){
                var startList = $("#start");
                var endList = $("#end");
                $.each(tttCommon.getTimeListForDay(), function(index, value){
                    var h = value["h"] > 12?value["h"]-12:value["h"];
                    var m = value["m"];
                    var sfx = value["h"]>=12?"PM":"AM";
                    $(startList).append("<option>" + h + ":" + m + " " + sfx + "</option>");
                    $(endList).append("<option>" + h + ":" + m + " " + sfx + "</option>");
                });
                startList.removeAttr("disabled");
                endList.removeAttr("disabled");
            }
        }
    });

    /* adds an activity to the list. */
    $(".add-task-activity a").live("click", function(){
        var taskActivitiesList = $(".task-activities-list");
        var dateBox = $("#actDate");
        var timeBox = $("#task-form .act-time label span");
        var newActHtml = "<p><span class='date'>" + dateBox.val() + "</span>";
        newActHtml += "<span class='start'>" + timeBox.data("startTime") + "</span> to ";
        newActHtml += "<span class='end'>" +timeBox.data("endTime") + "</span>";
        newActHtml += "<span class='diff'>" + timeBox.data("timeDiff") + "</span><span class='remove'>x</span></p>";
        taskActivitiesList.prepend(newActHtml);
    });

    /* removes an activitiy from the list. */
    $(".task-activities-list .remove").live("click", function(){
        $(this).parent("p").remove();
    });


    /* end of START-END-INPUT-SECTION functions */
		
    $(".task .controls .delete").live("click", function(){
        if(confirm("Are you sure to delete this task?")){
            var task = $(this).closest(".task");
            var taskId = task.attr("id");
            ttt.deleteTask(taskId);
        }
		
    });
	
    $(".task .controls .edit").live("click", function(){
        var task = $(this).closest(".task");
        var taskId =  task.data("taskId");
        var taskProject = task.data("project");
        var taskName = task.data("taskName");
        var taskStatus = task.data("status");
        var taskColor = task.data("color");
		
        var taskForm = $("#task-form");
        taskForm.addClass("edit-mode");
        taskForm.find("input[name='task-name']").val(taskName);
        taskForm.find("[name='task-type']").filter("[value='" + taskStatus + "']").attr("checked", "checked");
        taskForm.find("[name='project']").val(taskProject);
        taskForm.find("[name='task-id']").val(taskId);
        taskForm.find("#color").val(taskColor).attr("class", taskColor);
        $("#task-type-radio").buttonset();
		
        $(taskForm).dialog({
            autoOpen:false,
            width:400,
            modal:true,
            resizable:false,
            buttons:{
                "Save":function(){
                    ttt.editTask();
                    $(this).dialog("close");
                }
            },
            close:function(){
                taskForm.removeClass("edit-mode");
            }
        });
        $("#task-form").dialog("open");
		
    });
	
    $(".task .close").live("click", function(){
        var task = $(this).closest(".task");
        var taskId = task.attr("id");
        ttt.closeTask(taskId);
    });
	
	
    $("#task-form #color").live("change", function(){
        $(this).removeClass(function(){
            return $(this).attr("class")
        }).addClass($(this).val());
    });
});

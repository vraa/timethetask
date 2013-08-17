var tttCommon = {
    clockTimer:"",
    sessionTimer:"",
    showMessage:function(msg, type){
        var serverMessage = $(".server-message");
        serverMessage.find("#message").html(msg);
        serverMessage.css("visibility", "visible");
        serverMessage.addClass(type);
    },

    hideMessage:function(){
        var serverMessage = $(".server-message");
        serverMessage.find("#message").html("");
        serverMessage.css("visibility", "hidden");
        serverMessage.removeClass("progress error");
    },

    showTooltip:function(x, y, contents) {
        $('<div id="tooltip">' + contents + '</div>').css( {
            position: 'absolute',
            display: 'none',
            top: y + 5,
            left: x + 5,
            maxWidth:'300px',
            padding: '5px',
            'background-color': '#3B5998',
            color:"#fff",
            'font-size':".9em",
            'box-shadow':"0 0 2px #777"
        }).appendTo("body").show("fast");
    },

    formattedTimeArray:function(milliseconds){
        var timeArray = new Array();
        timeArray["h"] = timeArray["m"] = timeArray["s"] = "0";
        if(milliseconds <= 0){
            return timeArray;
        }
        var ms = milliseconds/1000;
        timeArray["s"] = Math.floor(ms%60);
        ms/=60;
        timeArray["m"] = Math.floor(ms % 60)===0?"00":Math.floor(ms % 60);
        ms/=60;
        timeArray["h"] = Math.floor(ms % 24);
        return timeArray;
        
    },

    /* returns the time list in an array to display in a select box */
    getTimeListForDay:function(){
        var timeList = new Array();
        for(var ms = 0; ms <= 85500000; ms += 900000){
            timeList.push(tttCommon.formattedTimeArray(ms));
        }
        return timeList;
    },

    formattedTime:function(milliseconds){
        if(milliseconds <= 0){
            return "0 second";
        }
        var ms = milliseconds/1000;
        var seconds = ms%60;
        ms/=60;
        var minutes = ms % 60;
        ms/=60;
        var hours = ms % 24;

        var fmtTime = "";
        if(hours >= 1){
            fmtTime = (hours = Math.floor(hours)) + " hour";
            if(hours > 1){
                fmtTime += "s"
            }
        }
        if(minutes >= 1){
            fmtTime += " " + (minutes = Math.floor(minutes)) + " minute";
            if(minutes > 1){
                fmtTime += "s";
            }
        }
        if(seconds >= 1){
            fmtTime += " " + Math.floor(seconds) + " second";
            if(seconds > 1){
                fmtTime += "s";
            }
        }
        return fmtTime;
    },

    shortFormattedTime:function(milliseconds){
        var timeArray = tttCommon.formattedTimeArray(milliseconds);
        return timeArray["h"] + "h:" + timeArray["m"] + "m:" + timeArray["s"] + "s";
    },

    startClock:function(time){
        tttCommon.stopClock();
        var clock = $(".clock");
        clock.html(tttCommon.clockHtml(tttCommon.formattedTimeArray(time)));
        clock.data("time", time);
        tttCommon.clockTimer = window.setInterval("tttCommon.tickClock()", 1000);
    },

    stopClock:function(){
        window.clearInterval(tttCommon.clockTimer);
        $(".clock").html("");
    },

    tickClock:function(){
        var clock = $(".clock")[0];
        if(typeof clock == "undefined"){
            return;
        }
        var time = $(clock).data("time");
        time += 1000;
        $(clock).html(tttCommon.clockHtml(tttCommon.formattedTimeArray(time)));
        $(clock).data("time", time);
    },

    clockHtml:function(timeArray){
        var clockHtml = "<span class='h'>" + timeArray["h"] + "<label>h</label></span>";
        clockHtml += "<span class='m'>" + timeArray["m"] + "<label>m</label></span>";
        clockHtml += "<span class='s'>" + timeArray["s"] + "<label>s</label></span>";
        return clockHtml;
    },

    setupSessionTimer:function(){
        if(tttCommon.sessionTimer){
            window.clearTimeout(tttCommon.sessionTimer);
        }
        tttCommon.sessionTimer = window.setTimeout(tttCommon.showSessionExpired, 30*60*1000);
    },

    showSessionExpired:function(){

        tttCommon.stopClock();

        var deadBox = $("<div class='dead-box'>");
        deadBox.attr("title", "Oops...");
        var deadHdr = $("<h2>").html("Session expired");
        var deadMsg = $("<p>").html("You must login to continue.");
        deadBox.append(deadHdr);
        deadBox.append(deadMsg);
        deadBox.dialog({
            width:300,
            modal:true,
            resizable:false,
            buttons:{
                "Login":function(){
                    tttCommon.redirectToLogin();
                }
            },
            close:function(){
                tttCommon.redirectToLogin();
            }
        }).dialog("open");
    },

    redirectToLogin:function(){
        window.location.href="http://www.timethetask.com";
    }
    
}

/* setup the global AJAX error handler */
$(document).ready(function(){
    tttCommon.setupSessionTimer();
    $(document).ajaxSend(tttCommon.setupSessionTimer);

});

function echo(msg){
    console.log(msg);
}
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="wechat"/>
    <title>预约下户</title>
</head>

<body>

<div class="message-box">
    <div class="helpMsg hide"></div>
    <div id="create-activity" class="content scaffold-create" role="main">
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${this.activity}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.activity}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
    </div>
</div>

<header class="appointment-title">
    请选择您预约下户时间
</header>
<g:form action="wxCreate" name="" resource="${this.activity}" class="wxCreate2Form">
    <input type="hidden" name="id" value="${opportunity?.id}"/>
    <input type="hidden" name="openId" id="openId" value="${openId}"/>
    <input type="hidden" name="startTime" id="startTime" value="${this.activity.startTime}">
    <input type="hidden" name="endTime" id="endTime" value="${this.activity.endTime}">
    %{--界面数据--}%
    <input type="hidden" name="activityDay" id="activityDay" value="">
    <input type="hidden" name="activityPeriod" id="activityPeriod" value="">
    <input type="hidden" id="nowHour" name="nowHour" value="<g:formatDate date="${this.nowDate}" format="HH"></g:formatDate>">

    <div class="bgcWhite">
        <ul class="time-flowList flex-box">
            <li data-id="1" class="flex1 active">
                %{--${this.nowDate}--}%
                <g:formatDate format="yyyy-MM-dd" date="${this.nowDate}"/>
            </li>
            <li data-id="2" class="flex1">
                <g:formatDate format="yyyy-MM-dd" date="${this.nowDate + 1}"/>

                %{--<g:formatDate format="yyyy-MM-dd" date="${this.nowDate + 1}"/>--}%
            </li>
            <li data-id="3" class="flex1">其他</li>
        </ul>

        <div class="recent-date">
            <div class="appointmentTime am-time">
                <h3>上午</h3>
                <ul class="time-period clearfix">
                    <li class="time-item">
                        <span>09:00-10:00</span>
                    </li>
                    <li class="time-item">
                        <span>10:00-11:00</span></li>
                    <li class="time-item">
                        <span>11:00-12:00</span>
                    </li>
                </ul>

            </div>

            <div class="appointmentTime pm-time">
                <h3>下午</h3>

                <ul class="time-period clearfix">
                    <li class="time-item"><span>12:00-13:00</span></li>

                    <li class="time-item"><span>13:00-14:00</span></li>
                    <li class="time-item"><span>14:00-15:00</span></li>

                    <li class="time-item"><span>15:00-16:00</span></li>
                    <li class="time-item"><span>16:00-17:00</span></li>

                </ul>

            </div>
        <h3 class="helpMsg2 hide " style="text-align: center;padding: 10px 0;">请选择明天预约下户时间</h3>
        </div>

        <div class="tomorrow-date hide">
            <div class="appointmentTime am-time">
                <h3>上午</h3>
                 <ul class="time-period clearfix">
                    <li class="time-item">
                        <span>09:00-10:00</span>
                    </li>
                    <li class="time-item">
                        <span>10:00-11:00</span>
                    </li>
                    <li class="time-item">
                        <span>11:00-12:00</span>
                    </li>
                </ul>

            </div>

            <div class="appointmentTime pm-time">
                <h3>下午</h3>

                <ul class="time-period clearfix">
                    <li class="time-item"><span>12:00-13:00</span></li>
                    <li class="time-item"><span>13:00-14:00</span></li>
                    <li class="time-item"><span>14:00-15:00</span></li>

                    <li class="time-item"><span>15:00-16:00</span></li>
                    <li class="time-item"><span>16:00-17:00</span></li>

                </ul>

            </div>
        </div>

        <div class="future-date hide">
            <div class="weui_cell weui_cell_select" style="padding-left: 15px">
                <div class="weui_cell_hd"><label class="weui_label" style="width: 6rem">下户日期</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <select class="weui_select" name="date" id="select1">
                        <option value="<g:formatDate format="yyyy-MM-dd" date="${this.nowDate + 2}"/>"><g:formatDate
                                format="yyyy-MM-dd" date="${this.nowDate + 2}"/></option>
                        <option value="<g:formatDate format="yyyy-MM-dd" date="${this.nowDate + 3}"/>"><g:formatDate
                                format="yyyy-MM-dd" date="${this.nowDate + 3}"/></option>
                        <option value="<g:formatDate format="yyyy-MM-dd" date="${this.nowDate + 4}"/>"><g:formatDate
                                format="yyyy-MM-dd" date="${this.nowDate + 4}"/></option>
                        <option value="<g:formatDate format="yyyy-MM-dd" date="${this.nowDate + 5}"/>"><g:formatDate
                                format="yyyy-MM-dd" date="${this.nowDate + 5}"/></option>
                        <option value="<g:formatDate format="yyyy-MM-dd" date="${this.nowDate + 6}"/>"><g:formatDate
                                format="yyyy-MM-dd" date="${this.nowDate + 6}"/></option>
                    </select>

                </div>
            </div>

            <div class="weui_cell weui_cell_select" style="padding-left: 15px">
                <div class="weui_cell_hd"><label class="weui_label" style="width: 6rem">时间段</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:select class="weui_select" name="time-period" id="select2"
                              from="${['09:00-10:00', '10:00-11:00', "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"]}"
                              value=""/>
                </div>
            </div>
        </div>
    </div>
    <div class="bigBtn">
        <button type="button" class="linkBtn weui_btn weui_btn_plain_default">确定</button>
    </div>
</g:form>
<div class="wjwoo-hotline">
    <a href="tel:${opportunity?.contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${opportunity?.contact?.city?.telephone}</span>
    </a>
</div>
<script>
    $(function () {

//        判断时间
        var time_item = $(".recent-date .time-item");
       /* var now = new Date();
        var nowHour = now.getHours();*/
        var nowHour = $("#nowHour").val();
        if (nowHour >= 9) {
            time_item.eq(0).addClass("hide");
        }
        if (nowHour >= 10) {
            time_item.eq(1).addClass("hide");
        }
        if (nowHour >= 11) {
            $(".recent-date .am-time").addClass("hide");
        }
        if (nowHour >= 12) {
            time_item.eq(3).addClass("hide");
        }
        if (nowHour >= 13) {
            time_item.eq(4).addClass("hide");
        }
        if (nowHour >= 14) {
            time_item.eq(5).addClass("hide");

        }
        if (nowHour >= 15) {
            time_item.eq(6).addClass("hide");
        }
        if (nowHour >= 16) {
            $(".recent-date .am-time").addClass("hide");
            $(".recent-date .pm-time").addClass("hide");
            $(".helpMsg2").removeClass("hide");
        }


//切换日期
        $(".time-flowList li").click(function () {
            //变更时间，清除已选时间段
              $(".time-item").removeClass('active');
        

            var obj = $(this);
            var dataId = obj.attr("data-id");
          
            if (dataId == "1") {
                $(".recent-date").removeClass("hide");
                $(".tomorrow-date ").addClass("hide");
                $(".future-date").addClass("hide");

            }
            if (dataId == "2") {
                $(".tomorrow-date").removeClass("hide");
                $(".recent-date").addClass("hide");
                $(".future-date").addClass("hide");
            }
            if (dataId == "3") {
                $(".future-date").removeClass("hide")
                $(".recent-date").addClass("hide");
                $(".tomorrow-date").addClass("hide");
            }
                var active = obj.hasClass('active');
            if (active) {
                return;
            } else {
                obj.addClass('active').siblings().removeClass('active');
            }
        });


        $(document).delegate(".time-item", "click", function () {
            var obj = $(this);
            var active = obj.hasClass('active');
            var timeItem = $(".time-item");
            if (active) {
                return;
            } else {
                timeItem.removeClass("active");
                $(this).addClass("active");
            }
        });


////拼接选择时间 提交表单
        $(".linkBtn").click(function () {
            var day;
            var timePeriod;
            var hourStart;
            var hourEnd;
            var startTime;
            var endTime;
            var activityTime;
            if (!$(".recent-date").hasClass("hide") || !$(".tomorrow-date").hasClass("hide") ) {
                day = $(".time-flowList li.active").text().trim();
                timePeriod = $(".time-item.active span").text().trim();
            } else {
                day = $("#select1").val().trim();
                timePeriod = $("#select2").val().trim()
            }
            hourStart = timePeriod.substring(0, 5)+":00";
            hourEnd = timePeriod.substring(6, 11)+":00";
            startTime = day + " " + hourStart;
            endTime = day + " " + hourEnd;

           $("#startTime").val(startTime);
           $("#endTime").val(endTime);
            $("#activityDay").val(day);
            $("#activityPeriod").val(timePeriod);
/*
            alert($("#startTime").val());
            alert($("#endTime").val());*/
            if (!timePeriod) {
                $(".helpMsg").text("请选择预约时间段").fadeIn(200);
                timeOut();
                return;
            }
            $(".wxCreate2Form").submit();
        });

        function timeOut() {
            setTimeout(function () {
                $(".helpMsg").fadeOut(200);
            }, 2000);
        }


    });
</script>
</body>
</html>

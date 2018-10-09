<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="wechat"/>
    <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
    <title>预约下户</title>
</head>
<body>
<div class="message-box">
    <div class="helpMsg hide"></div>
    <g:if test="${flash.message}">
        <div class="message" role="status">
            ${flash.message}
        </div>
    </g:if>
</div>
<div class="appointment">
    <g:form action="wxMakeAppointmentByContact" class="acivityForm">
        <input type="hidden" name="startTime" id="startTime" value="${startTime}"/>
        <input type="hidden" name="endTime" id="endTime" value="${endTime}"/>
        <input type="hidden" name="opportunityId" id="opportunityId" value="${this.activity?.opportunity?.id}"/>
        <input type="hidden" name="openId" id="openId" value="${openId}"/>
        <div class="item">
            <h3 class="step-title">STEP1</h3>
            <g:link action="wxBringData" id="${this.activity?.opportunity?.id}" params="[activityDay:activityDay, activityPeriod:activityPeriod, openId: openId]" class="handleLink">查看面审时携带资料</g:link>
        </div>
        <div class="item">
            <h3 class="step-title">STEP2</h3>
            <g:link action="wxCreate2" id="${this.activity?.opportunity?.id}" params="[openId: openId]" class="handleLink">
                <label id="select-label">请选择预约下户时间</label>
                <span>
                    <em id="activityDay" style="margin:0 3px 0 5px">${this.activityDay}</em>
                    <em id="activityPeriod">${this.activityPeriod}</em>
                </span>
            </g:link>
        </div>

        <div class="bigBtn">
            <button type="button" class="linkBtn weui_btn weui_btn_plain_default" id="activityBtn">预约下户</button>
        </div>
        <div class="footerMsg">
            <h5>提示</h5>
            <h5>预约成功后，工作人员会及时与您确定下户时间</h5>
        </div>
    </g:form>
</div>
<div class="wjwoo-hotline">
    <a href="tel:${contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${contact?.city?.telephone}</span>
    </a>
</div>
<script>
    $(function(){
        var activityDay = $("#activityDay").text().trim();
        var activityPeriod = $("#activityPeriod").text().trim();
        if(activityDay&&activityPeriod){
            $("#select-label").text("预约下户时间");
        }
        $("#activityBtn").click(function(){
            if(!activityDay &&!activityPeriod){
                $(".helpMsg").text("请选择预约时间").fadeIn(200);
                timeOut();
                return;
            }
            var periodList = activityPeriod.split("-")
            var startTime = activityDay + " " + periodList[0] + ":00"
            var endTime = activityDay + " " + periodList[1] + ":00"
            $("#startTime").val(startTime);
            $("#endTime").val(endTime);
            $(".acivityForm").submit();
        });

        function timeOut() {
            setTimeout(function () {
                $(".helpMsg").fadeOut(200);
            }, 2000);
        }
        setTimeout(function () {
            $(".message").fadeOut(200);
        }, 2000);
    });
</script>
</body>

</html>

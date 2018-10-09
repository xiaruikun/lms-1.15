<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="wechat"/>
    <title>预约下户</title>
</head>

<body>
    <div class="info-msg">
         <i class="weui_icon_success weui_icon_msg"></i>
        <h2>恭喜你，预约成功</h2>
        <h3>工作人员会及时与您确定下户时间</h3>
    </div>

    <div class="bigBtn">
        <g:link controller="opportunity" action="wxShow" id="${this.activity?.opportunity?.id}" class="linkBtn2 weui_btn weui_btn_primary">查看订单进度</g:link>
    </div>
<div class="wjwoo-hotline">
    <a href="tel:${this.activity?.opportunity?.contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${this.activity?.opportunity?.contact?.city?.telephone}</span>
    </a>
</div>
</body>
</html>

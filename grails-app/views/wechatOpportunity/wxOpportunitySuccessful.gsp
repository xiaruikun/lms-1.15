<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="wechat"/>
    <title>报单成功</title>

</head>

<body>
<header class="create-header">
    <ul class="creat-flowList flex-box">
        <li data-id="01" class="flex1 active">
            <span></span>

            <h3>借款信息</h3>
        </li>
        <li data-id="02" class="flex1 active">
            <span></span>

            <h3>上传材料</h3>
        </li>
        <li data-id="03" class="flex1 active">
            <span></span>

            <h3>报单成功</h3>
        </li>
    </ul>
</header>

<div class="info-msg">
    <i class="weui_icon_success weui_icon_msg"></i>

    <h2>恭喜您，报单成功</h2>
</div>

<div class="bigBtn">
    <g:link controller="wechatOpportunity" action="wxShow" id="${this.opportunity?.id}"
            class="linkBtn2 weui_btn weui_btn_primary">查看详情</g:link>
</div>

<div class="hjwoo-hotline2">
    <a href="tel:${this.opportunity?.contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${this.opportunity?.contact?.city?.telephone}</span>
    </a>
</div>
</body>
</html>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="wechat"/>
    <title>登录成功</title>
</head>

<body>
<div class="info-msg">
    <i class="weui_icon_msg weui_icon_success"></i>

    <h2>恭喜您，登录成功！</h2>
</div>

<div class="bigBtn">
    <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2FwechatOpportunity%2FwxCreate2Step1&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect"
       class="linkBtn weui_btn weui_btn_primary">立即评房</a>
    %{--TODO--}%
    %{--测试--}%
    %{-- <g:link controller="wechatOpportunity" action="wxCreate2Step1" class="linkBtn weui_btn weui_btn_primary">立即评房测试</g:link> --}%
</div>

<div class="wjwoo-hotline">
    <a href="tel:${this.contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${this.contact?.city?.telephone}</span>
    </a>
</div>
</body>
</html>
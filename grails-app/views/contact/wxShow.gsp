<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="wechat"/>
    <title>个人中心</title>
</head>

<body>
<header class="wxShow-header">
    <div class="header-info" style="padding:15px">
        <h3 class="nav">
            <span style="font-size: 1.2rem">欢迎您，</span>
            <span style=" font-size: 1.5rem">${this.contact.fullName}</span>
            <span>!</span>
            <g:link class="fr person-edit" controller="contact" action="wxEdit"
                    params="[cellphone: this.contact.cellphone]">
                <g:img dir="images" file="button-editor@2x.png"/>
            </g:link>
        </h3>

        <h3 class="user-info">
            <span style="margin-right: 1rem">${this.contact.city?.name}</span>
            <span>${this.contact.cellphone}</span>
        </h3>
    </div>
</header>

<div class="person-center">
    <p class="order-title">订单记录</p>

    <div class="bgcWhite order-list" style="padding-left: 15px">
        <div class="order-item">
            <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2FwechatOpportunity%2FwxIndex?stage=allItems&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">全部订单(${opportunityCounts})</a>
        </div>

        <div class="order-item">
            <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2FwechatOpportunity%2FwxIndex?stage=waitSH&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">待审核(${waitSHCounts})</a>
        </div>

        <div class="order-item">
            <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2FwechatOpportunity%2FwxIndex?stage=alreadyCS&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">已初审(${alreadyCSCounts})</a>
        </div>

        %{-- <div class="order-item">
            <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2Fserver-1.6%2Fopportunity%2FwxIndex?stage=alreadyYY&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">已预约(${alreadyYYCounts})</a>
        </div> --}%

        <div class="order-item">
            <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2FwechatOpportunity%2FwxIndex?stage=alreadySP&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">已审批(${alreadySPCounts})</a>
        </div>

        <div class="order-item">
            <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2FwechatOpportunity%2FwxIndex?stage=alreadyFD&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">已返点(${alreadyFDCounts})</a>
        </div>

        <div class="order-item">
            <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2FwechatOpportunity%2FwxIndex?stage=alreadySB&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">已失败(${alreadySBCounts})</a>
        </div>

        %{--TODO--}%
        %{--测试--}%
        %{-- <div class="order-item">
            <g:link controller="opportunity" action="wxIndex" params="[stage: 'allItems']">全部订单(${opportunityCounts})</g:link>
        </div>

        <div class="order-item">
            <g:link controller="opportunity" action="wxIndex" params="[stage: 'alreadyPF']">已评房(${alreadyPFCounts})</g:link>
        </div>

        <div class="order-item">
            <g:link controller="opportunity" action="wxIndex" params="[stage: 'waitSH']">待审核(${waitSHCounts})</g:link>
        </div>

        <div class="order-item">
            <g:link controller="opportunity" action="wxIndex" params="[stage: 'alreadyCS']">已初审(${alreadyCSCounts})</g:link>
        </div>

        <div class="order-item">
            <g:link controller="opportunity" action="wxIndex" params="[stage: 'alreadySP']">已审批(${alreadySPCounts})</g:link>
        </div>

        <div class="order-item">
            <g:link controller="opportunity" action="wxIndex" params="[stage: 'alreadyFD']">已返点(${alreadyFDCounts})</g:link>
        </div>

        <div class="order-item">
            <g:link controller="opportunity" action="wxIndex" params="[stage: 'alreadySB']">已失败(${alreadySBCounts})</g:link>
        </div> --}%
    </div>
</div>

<div class="hjwoo-hotline2">
    <a href="tel:${this.contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${this.contact?.city?.telephone}</span>
    </a>
</div>
</body>
</html>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="wechat"/>
    <title>欢迎页</title>
    <style>
    .wxWelcome {
        /*background: url("../static/images/image-background.jpg") no-repeat;*/
        background: url('${resource(dir: "images", file: "image-background.jpg")}') no-repeat;
        background-size: 100% 100%;
    }

    </style>
</head>

<body>
<div class="wxWelcome">
    <div class="hjw-desc">
        <p>绑定黄金屋账号</p>

        <p>第一时间接收报单结果</p>

        <p>省时又省心</p>
    </div>

    <div class="wxWelocmeBtn">
        <a class="btn-link register-btn"
           href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2Fcontact%2FwxRegister&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">注册</a>
        <a class="btn-link  login-btn"
           href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2Fcontact%2FwxLogin&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">登录</a>
        %{--TODO--}%
        %{--测试用--}%
        %{--<g:link action="wxRegister" class="btn-link register-btn">注册测试</g:link>--}%
        %{--<g:link action="wxLogin" class="btn-link login-btn">登录测试</g:link>--}%
    </div>
</div>
<div class="wjwoo-hotline">
    <a href="tel:4008882006" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span>4008882006</span>
    </a>
</div>
<script>
    $(function () {
        $(".register-btn").click(function () {
            $(this).css("backgroundColor", "#FBC92D");

        });
        $(".login-btn").click(function () {
            $(this).css("backgroundColor", "#8C89FF");

        });
    });
</script>
</body>
</html>
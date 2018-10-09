<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="wechat"/>
    <title>登录</title>
</head>

<body>
<header id="header" class="header row">
    <span class="header-info">绑定黄金屋账号，第一时间接受评房、报单结果</span>
</header>

<div class="wxLogin">
    <form action="wxLoginVerifyCode" id="loginForm" method="post">
        <g:field class="weui_input hide" name="openId" type="text" value="${this.contact.openId}"/>
        <div class="bgcWhite weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">手&nbsp;&nbsp机&nbsp;&nbsp;号</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:field class="weui_input" name="cellphone" id="cellphone" type="number"
                              autofocus="autofocus" placeholder="请输入手机号"
                             value="${this.contact.cellphone}"/>
                </div>

                <div class="weui_cell_ft">
                    <input id="btnSendVerifiedCode" type="button"
                           class="vCode-btn weui_btn weui_btn_mini weui_btn_plain_default" value="获取验证码"/>
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">验&nbsp;&nbsp;证&nbsp;&nbsp;码</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:field class="weui_input" type="number" placeholder="请输入验证码" name="verifiedCode" id="verifiedCode"/>
                </div>
            </div>
        </div>

        <div class="bigBtn">
            <button type="button" class="linkBtn weui_btn" id="submitBtn">立即登录</button>
        </div>
    </form>
</div>

<div class="footerMsg">
    <span>未有注册账号</span>
    <a class="create"
       href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2Fcontact%2FwxRegister&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">立即注册</a>
</div>
%{--热线--}%
<div class="hjwoo-hotline2">
    <a href="tel:4008882006" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span>4008882006</span>
    </a>
</div>

<div class="message-box">
    <div class="helpMsg hide"></div>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <div class="row">
        <g:hasErrors bean="${this.contact}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.contact}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
    </div>
</div>
<g:javascript>
    $(function () {
        var time = 30;
        var interval;
        $('#btnSendVerifiedCode').click(function () {
            if ($("#cellphone").val().length == 0) {
                $(".helpMsg").text("请输入手机号").fadeIn(200);
                $('#btnSendVerifiedCode').addClass("vCodeActive").attr('disabled', 'disabled');
                setTimeout(function () {
                    $(".helpMsg").fadeOut(200);
                    $('#btnSendVerifiedCode').removeClass("vCodeActive").removeAttr('disabled');
                }, 2000);
                return;
            }
            if (/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test($("#cellphone").val())) {
                $.ajax({
                    type: "POST",
                    url: "https://" + window.location.host + "/contact/sendVerifiedCode",
                    data: {
                        cellphone: $("#cellphone").val(),
                        operation: "login"
                    },
                    beforeSend:function(){
                        interval = setInterval(function () {
                                if (time > 0) {
                                    $('#btnSendVerifiedCode').attr('disabled', 'disabled').addClass("vCodeActive");
                                    $('#btnSendVerifiedCode').val("" + (time--) + '秒后重试');
                                } else {
                                    $('#btnSendVerifiedCode').removeAttr('disabled').removeClass("vCodeActive");
                                    $('#btnSendVerifiedCode').val("获取验证码");
                                    time = 60;
                                    clearInterval(interval);
                                }
                            }, 1000);

                    },
                    success: function (data) {
                        if (data.status == "success") {
                             helpMessage("验证码发送成功");
                        }
                        if (data.status == "error") {
                            $(".helpMsg").text(data.errorMessage).fadeIn(200);
                            setTimeout(function () {
                                $(".helpMsg").fadeOut(200);
                                window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2Fcontact%2FwxWelcome&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
                            }, 2000);
                        }
                    }
                });
            }
            else {
                helpMessage("请输入正确的手机号");
            }
        });
        setTimeout(function () {
            $(".message").fadeOut(200);
        }, 2000);

        $("#submitBtn").click(function () {
            // 参数合法性校验
            var cellphoneStr = $("#cellphone").val().trim();
            var verifiedCodeStr = $("#verifiedCode").val().trim();
            if (cellphoneStr.length == 0) {
                helpMessage("请输入手机号");
                return;
            }
            if (!(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(cellphoneStr))) {
                helpMessage("请输入正确的手机号");
                return;
            }
            if (!verifiedCodeStr) {
                helpMessage("请输入验证码");
                return;
            }
            $("#loginForm").submit();
        });

        function helpMessage(message) {
            $(".helpMsg").text(message).fadeIn(200);
            setTimeout(function () {
                $(".helpMsg").fadeOut(200);
            }, 2000);
        }
    });
</g:javascript>
</body>
</html>

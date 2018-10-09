<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="wechat"/>
    <title></title>
    <title>账号注册</title>
</head>

<body>
<header id="header" class="header">
    请完善如下信息，立刻拥有黄金屋账号
</header>

<div class="wxRegister">
    <form action="wxRegisterVerifyCode" id="registerForm" method="post">
        <g:textField class="weui_input hide" type="text" name="openId" value="${this.contact.openId}"></g:textField>
        <div class="bgcWhite weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label
                        class="weui_label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:textField class="weui_input" type="text" name="fullName" id="fullName"
                                 value="${this.contact.fullName}" autofocus="autofocus"/>
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">手&nbsp;&nbsp;机&nbsp;&nbsp;号</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:field class="weui_input" type="number" name="cellphone" id="cellphone"
                             value="${this.contact.cellphone}" />
                </div>

                <div class="weui_cell_ft">
                    <input id="btnSendVerifiedCode" type="button"
                           class="vCode-btn weui_btn weui_btn_mini weui_btn_plain_default" value="获取验证码"/>
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">验&nbsp;&nbsp;证&nbsp;&nbsp;码</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:field class="weui_input" type="number" placeholder="请输入手机收到的验证码" name="verifiedCode"
                             id="verifiedCode" />
                </div>
            </div>

            <div class="weui_cell weui_cell_select pl-15">
                <div class="weui_cell_hd"><label class="weui_label">所在城市</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:select class="weui_select" name="city" id="city" optionKey="id" optionValue="name"
                              from="${com.next.City.list()}" value="${this.contact?.city?.id}"/>
                    <input type="hidden" name="invitationCode" id="invitationCode" value="true" />
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">邀&nbsp;&nbsp;请&nbsp;&nbsp;码</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:field type="number" class="weui_input" name="userCode"  id="userCode" value="${this.contact.userCode}"/>
                </div>
            </div>
        </div>
        <h5 class="hjwoo-deal">
            <input type="checkbox" style="vertical-align: middle" id="checkbox" checked>
            <span style="color: #AAA">同意黄金屋</span>
            <span class="blue-link" id="dealLink">“用户协议”</span>
        </h5>

        <div class="bigBtn">
            <button type="button" class="linkBtn weui_btn" id="submitBtn">立即注册</button>
        </div>
    </form>

    <div class="footerMsg">
        <span>已有黄金屋账号</span>
        <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2Fcontact%2FwxLogin&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect">立即登录</a>
    </div>
</div>

<div class="register-deal hide">
    <h3>用户协议</h3>

    <div class="deal-content">用户协议内容</div>

    <h3 id="dealClose">同意协议并关闭</h3>
</div>
<div class="hjwoo-hotline2">
    <a href="tel:4008882006" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span>4008882006</span>
    </a>
</div>
%{--提示信息和验证--}%
<div class="message-box">
    <div class="helpMsg hide"></div>
    <g:if test="${flash.message}">
        <div class="message" role="status">
            ${flash.message}
        </div>
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
        getInvitationCode();
        $("#city").change(function () {
            getInvitationCode()
        });

        //协议选中
        $("#checkbox").click(function () {
            if ($(this)[0].checked) {
                $("#submitBtn").removeAttr("disabled").removeClass('btn_disabled');
            } else {
                $("#submitBtn").attr("disabled", "disabled").addClass('btn_disabled');
                helpMessage("请同意“黄金屋”用户协议");
            }
        });

        var time = 30;
        var interval;
        $('#btnSendVerifiedCode').click(function () {
            if (!$("#cellphone").val().trim()) {
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
                        operation: "register"
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
                            helpMessage(data.errorMessage);
                        }
                    },
                });
            } else {
                $('#cellphone').val('');
                helpMessage("请输入正确的手机号");
            }
        });
        setTimeout(function () {
            $(".message").fadeOut(200);
        }, 2000);

        // 邀请码校验
        $("#userCode").focus(function () {
            $('#submitBtn').removeClass("btn_disabled").removeAttr("disabled");
        });
        $("#userCode").blur(function () {
            var invitationCode = $("#invitationCode").val().trim();
            var userCode = $("#userCode").val().trim();
            if (invitationCode == "true") {
                verifiedUserCode();
            }
            if (invitationCode == "false" && userCode) {
                verifiedUserCode();
            }
        });

        function verifiedUserCode() {
            var userCode = $("#userCode").val().trim();
            if (!(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(userCode))) {
                helpMessage("请输入正确的邀请码");
                $('#submitBtn').addClass("btn_disabled").attr('disabled', 'disabled');
                return;
            }

            $.ajax({
                type: "POST",
                url: "https://" + window.location.host + "/contact/wxVerifiedUserCode",
                data: {
                    userCode: userCode,
                },
                success: function (data) {
                    if (data.status == "success") {
                        if (data.flag == false) {
                            helpMessage("对不起，您输入的邀请码不存在");
                            $('#submitBtn').addClass("btn_disabled").attr('disabled', 'disabled');
                            return;
                        }
                    }
                },
            });
        }

        function getInvitationCode() {
            var city = $("#city").val().trim()
            $.ajax({
                type: "POST",
                url: "https://" + window.location.host + "/contact/wxGetInvitationCode",
                data: {
                    city: city,
                },
                success: function (data) {
                    if (data.status == "success") {
                        $("#invitationCode").val(data.invitationCode);
                    }
                },
            });
        }

        // 参数合法性校验
        $("#submitBtn").click(function () {
            var fullNameStr = $("#fullName").val().trim();
            var cellphoneStr = $("#cellphone").val().trim();
            var verifiedCodeStr = $("#verifiedCode").val().trim();
            var userCodeStr = $("#userCode").val().trim();

            if (!fullNameStr) {
                helpMessage("请输入姓名");
                return;
            }
            if (!(/^[\u2190-\u9fff]{1,10}$|^[\dA-Za-z]{1,20}$/.test(fullNameStr))) {
                helpMessage("姓名格式不正确");
                return;
            }
            if (!cellphoneStr) {
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
            var invitationCode = $("#invitationCode").val().trim();
            if (invitationCode == "true") {
                if (!userCodeStr) {
                    helpMessage("请输入邀请码");
                    return;
                }
                if (!(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(userCodeStr))) {
                    helpMessage("请输入正确的邀请码");
                    return;
                }
            }
            if (invitationCode == "false" && userCodeStr) {
                if (!(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(userCodeStr))) {
                    helpMessage("请输入正确的邀请码");
                    return;
                }
            }
            
            $("#registerForm").submit();
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

<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>中佳信</title>
    <link rel="icon" href="${createLinkTo(dir: "images", file: "favicon.ico")}" type="image/x-icon">
    <link rel="shortcut icon" href="${createLinkTo(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <asset:stylesheet src="homer/vendor/fontawesome/css/font-awesome.css"/>
    <asset:stylesheet src="homer/vendor/bootstrap/dist/css/bootstrap.css"/>

    <asset:stylesheet src="homer/style.css"/>
    <asset:stylesheet src="homer/vendor/slideUnlock/drag.css"/>
    <asset:javascript src="homer/vendor/jquery/dist/jquery.min.js"/>

    <style type="text/css">
    body {
        font-family: "Microsoft YaHei", "Microsoft Sans Serif", "Helvetica Neue", Helvetica, Arial, "Hiragino Sans GB", sans-serif;
    }

    .form-control, .btn {
        border-radius: 0 !important;
    }
    </style>

</head>

<body class="blank">

<!-- Simple splash screen-->
<div class="splash"><div class="color-line"></div>

    <div class="splash-title"><h1>中佳信LMS</h1>

        <p></p>

        <div class="spinner"><div class="rect1"></div>

            <div class="rect2"></div>

            <div class="rect3"></div>

            <div class="rect4"></div>

            <div class="rect5"></div></div></div></div>

<div class="color-line"></div>

<div class="login-container">
    <div class="row">
        <div class="col-md-12">
            <div class="text-center m-b-md">
                <h3>请登录</h3>
                %{--<span>中佳信LMS</span>--}%
            </div>

            <div class="hpanel">
                <div class="panel-body">
                    <div class="helpMsg"></div>
                    <g:if test='${flash.message}'>
                        <div class='alert alert-dismissable alert-warning'>
                            <h4>Warning!</h4>

                            <p>${flash.message}</p>
                        </div>
                    </g:if>

                    <form action="/login/authenticate" method='POST' id='loginForm'>
                        <div class="form-group">
                            <label class="control-label" for="username">用户名</label>
                            <input type="text" title="Please enter you username" required="" value="" name="username"
                                   id="username" class="form-control" autofocus="autofocus">
                            <span class="help-block small" id="nameHelp">请输入用户名</span>
                        </div>

                        %{--<div class="form-group ">
                            <label class="control-label" for="password">密码</label>
                            <div class="input-group">
                                <input type="password" title="Please enter your password" placeholder="************"
                                       required="" value="" name="password" id="password" class="form-control">

                                <span class="input-group-addon btn btn-success" id="btnSendVerifiedCode">获取验证码</span>
                            </div>
                            <span class="help-block small" id="pwdHelp">请输入密码</span>
                        </div>
--}%
                        <div class="form-group">
                            <label class="control-label" for="password">密码</label>

                            <div class="row">
                                <div class="col-md-9" style="padding-right: 0">
                                    <input type="password" title="Please enter your password" placeholder="************"
                                           required="" value="" name="password" id="password" class="form-control">
                                </div>

                                <div class="col-md-3" style="padding-left: 0;">
                                    <input id="btnSendVerifiedCode" type="button" style="border-left:none"
                                           class="btn-success form-control" value="获取验证码"/>
                                </div>
                            </div>


                            <span class="help-block small" id="pwdHelp">请输入密码</span>
                        </div>

                        <div class="form-group" id="code-group">
                            <div id="drag"></div>
                            <span class="help-block small" id="codeHelp">请滑动解锁</span>
                        </div>

                        <button type="button" id="submitBtn" class="btn btn-success btn-block">登录</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    %{--<div class="row">
        <div class="col-md-12 text-center">
            <strong>中佳信 Loan Management System</strong><br/>Copyright © 北京中佳信科技发展有限公司-2016
        </div>
    </div>--}%

    <asset:javascript src="homer/vendor/bootstrap/dist/js/bootstrap.min.js"/>
    <asset:javascript src="homer/vendor/metisMenu/dist/metisMenu.min.js"/>
    <asset:javascript src="homer/vendor/iCheck/icheck.min.js"/>
    <asset:javascript src="homer/homer.js"/>
    <asset:javascript src="homer/vendor/slideUnlock/drag.js"/>
    <script>
        $(function () {
            $('#drag').drag();
            var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
            if (isMobile) {
                $("#code-group").remove();
            }
            $("#submitBtn").click(function () {
                var username = $("#username").val();
                var password = $("#password").val();
                var drag_text = $(".drag_text").text();
                if (!username) {
                    $("#nameHelp").addClass("text-danger");
                    setTimeout(function () {
                        $("#nameHelp").removeClass("text-danger");
                    }, 3000);
                    return;
                }
                if (!password) {
                    $("#pwdHelp").addClass("text-danger");
                    setTimeout(function () {
                        $("#pwdHelp").removeClass("text-danger");
                    }, 3000);
                    return;
                }
                if (!isMobile) {
                    if (drag_text != "验证通过") {
                        $("#codeHelp").addClass("text-danger");
                        setTimeout(function () {
                            $("#codeHelp").removeClass("text-danger");
                        }, 3000);
                        return;
                    }
                }


                $("#loginForm").submit();

            });

            var time = 30;
            var interval;
            $('#btnSendVerifiedCode').click(function () {
                $.ajax({
                    type: "POST",
                    url: "/user/sendVerifiedCode",
                    data: {
                        username: $("#username").val(),
                    },
                    success: function (data) {
                        if (data.status == "success") {
                            helpMessage("验证码发送成功");
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
                        }
                        if (data.status == "error") {
                            helpMessage(data.errorMessage);
                        }
                    },
                });
            });
            setTimeout(function () {
                $(".message").fadeOut(200);
            }, 2000);

            function helpMessage(message) {
                $(".helpMsg").text(message).fadeIn(200);
                setTimeout(function () {
                    $(".helpMsg").fadeOut(200);
                }, 2000);
            }

        })
    </script>

</body>
</html>

<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><g:layoutTitle default="Some Title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection"/>
    <asset:stylesheet src="wechat/weui.min.css"/>
    <asset:stylesheet src="wechat/wechat.css"/>
    <g:layoutHead/>
    <asset:javascript src="jquery-2.1.3.js"/>
</head>

<body>

<g:layoutBody/>
<script>
    $(function () {
        var $input = $("input");
        for (var i = 0; i < $input.length; i++) {
            $input.eq(i).attr("autocomplete", "off");
        }
        $(".hjwoo-hotline2 a").click(function () {
            $(this).addClass("active");
        });
    });
</script>
</body>
</html>

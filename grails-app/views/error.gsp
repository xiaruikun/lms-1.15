<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>中佳信</title>

    <asset:stylesheet src="homer/fonts/pe-icon-7-stroke/css/pe-icon-7-stroke.css"/>
    <asset:stylesheet src="homer/style.css"/>


    <style type="text/css">
    body {
        font-family: "Microsoft YaHei", "Microsoft Sans Serif", "Helvetica Neue", Helvetica, Arial, "Hiragino Sans GB", sans-serif;
    }
    </style>

</head>

<body class="blank">
<div class="color-line"></div>

<div class="error-container">
    <i class="pe-7s-way text-success big-icon"></i>

    <h1>500</h1>

    <g:if test="${Throwable.isInstance(exception)}">
        <g:renderException exception="${exception}"/>
    </g:if>
    <g:elseif test="${request.getAttribute('javax.servlet.error.exception')}">
        <g:renderException exception="${request.getAttribute('javax.servlet.error.exception')}"/>
    </g:elseif>
    <g:else>
        <ul class="errors">
            <li>An error has occurred</li>
            <li>Exception: ${exception}</li>
            <li>Message: ${message}</li>
            <li>Path: ${path}</li>
        </ul>
    </g:else>

</div>
</body>
</html>
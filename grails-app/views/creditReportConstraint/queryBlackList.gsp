<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'opportunity')}"/>
    <title>查询中佳信黑名单</title>
    <style>
    dd, dt {
        line-height: 2;
    }
    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="opportunity" action="show" id="${this.opportunity?.id}">订单</g:link>
                        </li>
                        <li class="active">
                            <span>中佳信黑名单</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs" style="text-transform:none;">
                    中佳信黑名单
                </h2>
            </div>
        </div>
    </div>
    <g:if test="${flash.message}">
        <div class="message alert alert-info" role="status">${flash.message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">×</span></button>
        </div>
    </g:if>
    <g:hasErrors bean="${this.json}">
        <ul class="errors" role="alert">
            <g:eachError bean="${this.json}" var="error">
                <li>
                    <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>
                    <g:message error="${error}"/>
                </li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <div class="animate-panel">
        <div class="hpanel hyellow ">
            <div class="panel-body">
                <dl class="dl-horizontal">
                    <g:if test="${this?.json}">
                        <dt class="">风控处理意见</dt>
                        <dd class="">${this?.json?.result}</dd>
                        <g:each in="${this?.json?.rList}">
                            <dt>身份证号</dt>
                            <dd>${it?.idNumber}</dd>
                            <dt>命中项</dt>
                            <dd>${it?.check_list}</dd>
                        </g:each>
                    </g:if>
                    <g:else>
                        <dt>暂无中佳信黑名单</dt>
                    </g:else>
                </dl>
            </div>
        </div>
    </div>
</body>
</html>

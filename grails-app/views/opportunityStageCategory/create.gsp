<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityStageCategory.label', default: 'OpportunityStageCategory')}"/>
    <title>新增订单阶段分类</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="OpportunityStageCategory" action="index">订单阶段</g:link>
                    </li>
                    <li class="active">
                        <span>新增订单阶段分类</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                订单阶段
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增订单阶段
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityStageCategory}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityStageCategory}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">阶段分类名称</label>

                        <div class="col-md-3">
                            <g:textField name="name" value="${this.opportunityStageCategory?.name}"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

</body>

</html>

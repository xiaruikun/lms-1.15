<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'position.label', default: 'Position')}" />
    <title>新增岗位</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="position" action="index">岗位列表</g:link>
                    </li>
                    <li class="active">
                        <span>新增岗位</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增岗位
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增岗位
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">${flash.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">×</span></button>
                        </div>
                    </g:if>
                    <g:hasErrors bean="${this.position}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.position}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">岗位名称</label>

                        <div class="col-md-3">
                            <g:textField name="name" value="${this.position?.name}" required="" maxlength="16" class="form-control"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton name="create" class="btn btn-info" value="保存" />
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>

</html>



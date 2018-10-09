<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'contactLevel.label', default: 'contactLevel')}"/>
    <title>新增客户级别</title>
</head>

<body>

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="contactLevel" action="index">客户级别</g:link></li>
                    <li class="active">
                        <span>新增客户级别</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增客户级别
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                新增客户级别
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.contactLevel}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.contactLevel}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form action="save" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-4 control-label">名称</label>

                        <div class="col-md-3"><g:textField name="name" value="${this.contactLevel?.name}"
                                                           class="form-control"/></div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">描述</label>

                        <div class="col-md-3"><g:textField name="description"
                                                           value="${this.contactLevel?.description}"
                                                           class="form-control"/></div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">是否启用</label>

                        <div class="col-md-3">
                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="active" value="true" checked="">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="active" value="false">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-4">
                            <g:submitButton class="btn btn-info" name="create" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>


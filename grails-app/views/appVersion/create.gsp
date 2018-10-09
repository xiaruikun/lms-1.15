<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'appVersion.label', default: 'AppVersion')}" />
        <title>新增版本</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">

            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="appVersion" action="index">app版本控制</g:link>
                    </li>
                    <li class="active">
                        <span>新增版本</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                新增版本
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增版本
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                   <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.appVersion}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.appVersion}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">名称</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="appName" value = "${this.appVersion?.appName}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">版本号</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="appVersion" value = "${this.appVersion?.appVersion}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">强制更新</label>

                        <div class="col-md-3">
                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="mustUpdate"  value="true">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="mustUpdate"  value="false" checked="">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">描述</label>

                        <div class="col-md-3">
                            <g:textArea name="description" value="${this.appVersion?.description}" rows="10" cols="60"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
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

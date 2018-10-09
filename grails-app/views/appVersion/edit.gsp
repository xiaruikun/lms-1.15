<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'appVersion.label', default: 'AppVersion')}" />
        <title>App版本编辑</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
<div class="small-header">
        <div class="hpanel">
            <div class="panel-body">

                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="appVersion" action="index">版本控制</g:link>
                        </li>
                        <li class="active">
                            <span>版本编辑</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    版本: ${this.appVersion?.appVersion}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    App版本编辑
                </div>
                <div class="panel-body">
                    <g:form resource="${this.appVersion}" method="PUT" class="form-horizontal">
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
                                <g:textField name="appName" value="${this.appVersion?.appName}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">版本</label>
                            <div class="col-md-3">
                                <g:textField name="appVersion" value="${this.appVersion?.appVersion}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">强制更新</label>
                            <div class="col-md-3">
                                <g:radioGroup name="mustUpdate" value="${this.appVersion?.mustUpdate}" labels="['true','false']" values="[true,false]">
                                    ${it.radio} ${it.label}
                                </g:radioGroup>
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
                                <g:submitButton class="btn btn-info" name="update" value="保存" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

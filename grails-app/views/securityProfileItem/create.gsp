<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'securityProfileItem.label', default: 'SecurityProfileItem')}" />
        <title>新增拦截项</title>
    </head>
<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="securityProfile" action="show" id="${this.securityProfileItem?.profile?.id}">权限</g:link>
                    </li>
                    <li class="active">
                        <span>新增拦截</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                拦截项
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增拦截
            </div>
            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.securityProfileItem}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.securityProfileItem}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
                    <div class="form-group">
                    <g:textField name="profile.id" id="profile" value="${this.securityProfileItem?.profile?.id}" class="hide" />
                        <label class="col-md-3 control-label">控制器名</label>
                        <div class="col-md-3">
                            <g:textField name="controllerName" value="${this.securityProfileItem?.controllerName}" class="form-control" />
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">方法名</label>
                        <div class="col-md-3">
                            <g:textField name="actionName" value="${this.securityProfileItem?.actionName}" class="form-control" />
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">拦截</label>
                        <div class="col-md-3 checkbox-inline">
                                <g:radioGroup class="checkbox-inline" name="deny" value="${this.securityProfileItem?.deny}" labels="['true','false']" values="[true,false]">
                                    ${it.radio} ${it.label}
                                </g:radioGroup>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="create" value="保存" />
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
%{-- 
        <a href="#create-securityProfileItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-securityProfileItem" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.securityProfileItem}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.securityProfileItem}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                    <f:all bean="securityProfileItem"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div> --}%
    </body>
</html>

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'securityProfile.label', default: 'SecurityProfile')}" />
        <title>修改权限</title>
    </head>
    <body>
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="securityProfile" action="index">权限</g:link>
                        </li>
                        <li class="active">
                            <span>${this.securityProfile?.name}</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    权限: ${this.securityProfile?.name}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    权限编辑
                </div>
                <div class="panel-body">
                    <g:form resource="${this.securityProfile}" method="PUT" class="form-horizontal">
                        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.securityProfile}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.securityProfile}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">权限名称</label>
                            <div class="col-md-3">
                                <g:textField name="name" value="${this.securityProfile?.name}" class="form-control" />
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
%{-- 

        <a href="#edit-securityProfile" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-securityProfile" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.securityProfile}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.securityProfile}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.securityProfile}" method="PUT">
                <g:hiddenField name="version" value="${this.securityProfile?.version}" />
                <fieldset class="form">
                    <f:all bean="securityProfile"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div> --}%
    </body>
</html>

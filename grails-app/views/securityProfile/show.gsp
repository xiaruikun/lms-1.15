<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'securityProfile.label', default: 'SecurityProfile')}" />
        <title>权限详情</title>
    </head>
<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="securityProfile" action="index">权限</g:link></li>
                    <li class="active">
                        <span>${this.securityProfile.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                权限: ${this.securityProfile.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit"
                            resource="${this.securityProfile}"><i class="fa fa-edit"></i>编辑</g:link>
                </div>
                权限基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12"><h4><a href=>${this.securityProfile?.name}</a></h4></div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-xs btn-info" controller="securityProfileItem" action="create"
                            params="[profile: this.securityProfile?.id]"><i class="fa fa-plus"></i>新增</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                拦截操作
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="controllerName"
                                              title="${message(code: 'securityProfileItem.controllerName.label', default: '控制器名称')}"/>
                            <g:sortableColumn property="actionName"
                                              title="${message(code: 'securityProfileItem.actionName.label', default: '方法名')}"/>
                            <g:sortableColumn property="deny"
                                              title="${message(code: 'securityProfileItem.deny.label', default: '权限')}"/>
                            <g:sortableColumn property="operation"
                                              title="${message(code: '操作')}"/>
                        </thead>
                        <tbody>
                        <g:each in="${this?.securityProfile?.items}">
                            <div class="col-md-4">
                                <tr>

                                    <td>${it?.controllerName}</td>
                                    <td>${it.actionName}</td>
                                    <td>
                                        ${it?.deny}
                                    </td>

                                    <td>
                                        <g:form controller="securityProfileItem" action="delete" id="${it.id}" method="DELETE">
                                                <button class="deleteBtn btn btn-danger btn-xs" type="button">删除</button>
                                            </g:form>
                                    </td>
                                </tr>
                            </div>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

       %{--  <a href="#show-securityProfile" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-securityProfile" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="securityProfile" />
            <g:form resource="${this.securityProfile}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.securityProfile}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div> --}%
    </body>
</html>

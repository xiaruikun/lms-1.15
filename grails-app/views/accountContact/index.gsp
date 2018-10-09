<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'accountContact.label', default: 'AccountContact')}" />
        <title>离职记录</title>
    </head>
    <body>
    <div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>离职记录</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                离职记录
            </h2>

        </div>
    </div>
</div>

<div class="content animate-panel">

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                所有记录
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="account"
                                              title="${message(code: 'account.label', default: '公司名称')}"/>
                            <g:sortableColumn property="contact"
                                              title="${message(code: 'contact.label', default: '员工姓名')}"/>
                            <g:sortableColumn property="accountExternalId"
                                              title="${message(code: 'accountExternalId.label', default: '公司编号')}"/>
                            <g:sortableColumn property="contactExternalId"
                                              title="${message(code: 'contactExternalId.label', default: '员工编号')}"/>
                            <g:sortableColumn property="hiredate"
                                              title="${message(code: 'hiredate.label', default: '雇佣时间')}"/>
                            <g:sortableColumn property="leavedate"
                                              title="${message(code: 'leavedate.label', default: '离职时间')}"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${accountContactList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it.account?.name}</g:link></td>
                                <td>${it.contact?.fullName}</td>
                                <td>${it.accountExternalId}</td>
                                <td>${it.contactExternalId}</td>
                                <td>${it.hiredate}</td>
                                <td>${it.leavedate}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${accountContactCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>

    </div>
</div>
%{-- 

        <a href="#list-accountContact" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-accountContact" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${accountContactList}" />

            <div class="pagination">
                <g:paginate total="${accountContactCount ?: 0}" />
            </div>
        </div> --}%
    </body>
</html>
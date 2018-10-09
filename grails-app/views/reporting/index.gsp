<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'reporting.label', default: 'reporting')}"/>
    <title>汇报关系列表</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right m-t-lg">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>汇报关系列表</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                汇报关系列表
            </h2>
        </div>
    </div>
</div>


<div class="row">
    <div class="container">
        <p>
        </p>
    </div>
</div>
<g:if test="${flash.message}">
    <div class="row">
        <div class="hpanel">
            <div class="panel-body">
                <div class="alert alert-info" role="alert">${flash.message}</div>
            </div>
        </div>
    </div>
</g:if>
<div class="row">
    <div class="container">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                </div>
                所有汇报关系列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="manager"
                                              title="${message(code: 'reporting.manager.name.label', default: '上级员工')}"></g:sortableColumn>
                            <g:sortableColumn property="user"
                                              title="${message(code: 'reporting.user.stage.label', default: '下属员工')}"></g:sortableColumn>
                            <g:sortableColumn property="createdDate"
                                              title="${message(code: 'reporting.createdDate.label', default: '申请时间')}"></g:sortableColumn>
                            <g:sortableColumn property="modifiedDate"
                                              title="${message(code: 'reporting.modifiedDate.label', default: '修改时间')}"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${reportingList}">
                            <tr>
                                <td><g:link controller="user" action="show" id="${it.manager?.id}"
                                            class="firstTd">${it.manager?.fullName}</g:link></td>
                                <td><g:link controller="user" action="show" id="${it.user?.id}"
                                            class="firstTd">${it.user?.fullName}</g:link></td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.createdDate}"/></td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.modifiedDate}"/></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${reportingCount ?: 0}"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
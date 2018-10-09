<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'creditReportProvider.label', default: 'CreditReportProvider')}"/>
    <title>征信配置管理</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>征信配置管理</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                征信配置管理
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有征信配置</small>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <g:if test="${flash.message}">
        <div class="alert alert-success alert-dismissible" creditReportProvider="alert">
            ${flash.message}
        </div>
    </g:if>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                </div>
                全部征信配置
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority" title="${message(code: 'creditReportProvider.authority.label', default: '名称')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'creditReportProvider.authority.label', default: '代码')}"/>
                            <g:sortableColumn property="authority" title="${message(code: 'creditReportProvider.authority.label', default: '连接')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${creditReportProviderList}">
                            <tr>
                            <td><g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link></td>
                            <td>${it.code}</td>
                            <td>${it.apiUrl}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${creditReportProviderCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
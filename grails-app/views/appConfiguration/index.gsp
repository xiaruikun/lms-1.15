<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'appConfiguration.label', default: 'AppConfiguration')}" />
        <title>邀请码配置列表</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>邀请码配置</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    邀请码配置
                </h2>
                <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>所有邀请码列表</small>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <g:if test="${flash.message}">
            <div class="alert alert-success alert-dismissible" role="alert">
                ${flash.message}
            </div>
        </g:if>
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    邀请码配置
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="name" title="名称"/>
                                <g:sortableColumn property="value" title="内容"/>
                                <g:sortableColumn property="appVersion" title="版本"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${appConfigurationList}">
                                <tr>
                                    <td><g:link controller="appConfiguration" action="show" id="${it?.id}"
                                                class="firstTd">${it?.key?.name}</g:link></td>
                                    <td>${it?.value}</td>
                                    <td>${it?.appVersion?.appName} ${it?.appVersion?.appVersion}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>

                </div>
                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${appConfigurationCount ?: 0}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'appVersion.label', default: 'AppVersion')}" />
        <title>App版本控制</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">

            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>App版本控制</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                App版本控制
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有版本</small>
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
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新增</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    全部版本
                </div>

                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="appName"
                                                  title="名称"/>
                                <g:sortableColumn property="appVersion"
                                                  title="版本"/>
                                <g:sortableColumn property="description"
                                                  title="描述"/>
                                <g:sortableColumn property="mustUpdate"
                                                  title="强制更新"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${appVersionList}">
                                <tr>
                                    <td><g:link action="show" id="${it?.id}"
                                                class="firstTd">${it?.appName}</g:link></td>
                                    <td>${it?.appVersion}</td>
                                    <td>${it?.description}</td>
                                    <td>${it?.mustUpdate}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${appVersionCount ?: 0}" params="${params}"/>
                    </div>
                </div>
            </div>
        </div>
</div>
    </body>
</html>
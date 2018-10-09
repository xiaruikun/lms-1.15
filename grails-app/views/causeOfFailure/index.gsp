<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'causeOfFailure.label', default: 'CauseOfFailure')}"/>
    <title>未成交归类</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>未成交归类</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                未成交归类
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
                所有归类
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name" title="原因"/>
                            <g:sortableColumn property="description" title="描述"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${causeOfFailureList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link></td>
                                <td>${it.description}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${causeOfFailureCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>
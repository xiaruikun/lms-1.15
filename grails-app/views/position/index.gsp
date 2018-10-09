
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'position.label', default: 'Position')}" />
    <title>岗位列表</title>
</head>
<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>岗位列表</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                岗位列表
            </h2>
        </div>
    </div>
</div>
%{--<f:table collection="${documentList}" />--}%
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                </div>
                岗位列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name" title="岗位名称"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${positionList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it?.name}</g:link></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${positionCount ?: 0}" />
                </div>
            </div>
        </div>

    </div>
</div>
</body>

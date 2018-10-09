<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'document.label', default: 'Document')}" />
        <title>帮助文档列表</title>
    </head>
<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>帮助文档列表</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                帮助文档列表
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
                帮助文档列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="title" title="标题"/>
                            %{--<g:sortableColumn property="document" title="document"/>--}%
                            <g:sortableColumn property="createdDate" title="创建时间"/>
                            <g:sortableColumn property="modifiedDate" title="修改时间"/>
                            <g:sortableColumn property="active" title="是否使用"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${documentList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it?.title}</g:link></td>
                                %{--<td>${it?.document}</td>--}%
                                <td>${it?.createdDate}</td>
                                <td>${it?.modifiedDate}</td>
                                <td>${it?.active}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${documentCount ?: 0}" />
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>


<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'externalDataMapping.label', default: 'ExternalDataMapping')}" />
    <title>外部数据映射列表</title>
</head>
<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>外部数据映射列表</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                外部数据映射列表
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
                </div>
                外部数据映射列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="systemName" title="systemName"/>
                            <g:sortableColumn property="categoryName" title="categoryName"/>
                            <g:sortableColumn property="value1" title="value1"/>
                            <g:sortableColumn property="value2" title="value2"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${externalDataMappingList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it?.systemName}</g:link></td>
                                <td>${it?.categoryName}</td>
                                <td>${it?.value1}</td>
                                <td>${it?.value2}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${externalDataMappingCount ?: 0}" />
                </div>
            </div>
        </div>

    </div>
</div>
</body>


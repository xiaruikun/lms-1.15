<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'department.label', default: 'Department')}"/>
    <title>部门管理</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>部门管理</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                部门管理
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有部门</small>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <g:if test="${flash.message}">
        <div class="alert alert-success alert-dismissible" department="alert">
            ${flash.message}
        </div>
    </g:if>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                </div>
                全部部门
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="authority" title="${message(code: 'department.authority.label', default: '部门')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${departmentList}">
                            <tr>
                            <td><g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${departmentCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
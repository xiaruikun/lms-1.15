<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'territory.label', default: 'Territory')}"/>
    <title>区域管理 | 区域</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>区域</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                区域
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">

        <g:form method="POST" action="searchTerritory">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="reset" id="resetBtn"><i class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>

                <div class="panel-body">
                    <div class="col-sm-4">
                        <input type="text" class="form-control" placeholder="区域名称" name="name" id="name"
                               value="${territory?.name}">
                    </div>
                </div>
            </div>
        </g:form>
        <g:if test="${flash.message}">
            <div class="row">
                <div class="hpanel">
                    <div class="panel-body">
                        <div class="alert alert-info" role="alert">${flash.message}</div>
                    </div>
                </div>
            </div>
        </g:if>

    </div>

    <div class="row">

        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                所有区域
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'territory.name.label', default: '区域名称')}"/>
                            <g:sortableColumn property="parent"
                                              title="${message(code: 'territory.parent.label', default: '上级区域名称')}"/>
                            <g:sortableColumn property="inheritTeam"
                                              title="${message(code: 'territory.inheritTeam.label', default: '继承团队')}"/>
                            <g:sortableColumn property="inheritRole"
                                              title="${message(code: 'territory.inheritRole.label', default: '继承权限')}"/>
                            <g:sortableColumn property="inheritNotification"
                                              title="${message(code: 'territory.inheritNotification.label', default: '继承消息')}"/>
                            <g:sortableColumn property="inheritFlow"
                                              title="${message(code: 'territory.inheritFlow.label', default: '继承工作流')}"/>
                            %{--<g:sortableColumn property="liquidityRiskReportTemplate"
                                              title="${message(code: 'territory.liquidityRiskReportTemplate.label', default: '流动性评分模板')}"/>--}%
                            <g:sortableColumn property="operation"
                                                                title="复制工作流"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${territoryList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link></td>
                                <td>${it.parent?.name}</td>
                                <td><g:if test="${it?.inheritTeam == true}"><span class="text-success">是</span></g:if><g:else><span class="text-danger">否</span></g:else></td>
                                <td><g:if test="${it?.inheritRole == true}"><span class="text-success">是</span></g:if><g:else><span class="text-danger">否</span></g:else></td>
                                <td><g:if test="${it?.inheritNotification == true}"><span class="text-success">是</span></g:if><g:else><span class="text-danger">否</span></g:else></td>
                                <td><g:if test="${it?.inheritFlow == true}"><span class="text-success">是</span></g:if><g:else><span class="text-danger">否</span></g:else></td>
                                %{--<td>${it.liquidityRiskReportTemplate?.name}</td>--}%
                                <td>
                                  <g:link action="prepareCopyFlow" params="[territory: it?.id]">复制</g:link>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${territoryCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>

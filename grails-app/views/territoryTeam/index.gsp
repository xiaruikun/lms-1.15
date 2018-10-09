<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryTeam.label', default: 'TerritoryTeam')}" />
    <title>区域-团队</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>区域-团队</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    区域-团队
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    所有区域-团队
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <g:sortableColumn property="territory" title="${message(code: 'territoryTeam.territory.name.label', default: '区域名称')}" />
                                    <g:sortableColumn property="user" title="${message(code: 'territoryTeam.user.name.label', default: '用户名')}" />
                                    <g:sortableColumn property="user" title="${message(code: 'territoryTeam.user.name.label', default: '布局')}" />
                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${territoryTeamList}">
                                    <tr>
                                        <td>${it.territory?.name}</td>
                                        <td>${it.user}</td>
                                        <td>${it?.opportunityLayout?.description}</td>
                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${territoryTeamCount ?: 0}" params="${params}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

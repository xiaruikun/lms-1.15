<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryNotification.label', default: 'TerritoryNotification')}" />
    <title>区域-消息</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>区域-消息</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    区域-消息
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    所有区域-消息
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <g:sortableColumn property="territory" title="${message(code: 'territoryNotification.territory.name.label', default: '区域名称')}" />
                                    <g:sortableColumn property="user" title="${message(code: 'territoryNotification.user.name.label', default: '用户名')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'territoryNotification.stage.label', default: '订单阶段')}" />
                                    <g:sortableColumn property="message" title="${message(code: 'territoryNotification.teamRole.name.label', default: '消息模板')}" />
                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${territoryNotificationList}">
                                    <tr>
                                        <td>${it.territory?.name}</td>
                                        <td>${it.user?.fullName}</td>
                                        <td>${it.stage?.name}</td>
                                        <td>${it.messageTemplate?.text}</td>
                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${territoryNotificationCount ?: 0}" params="${params}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

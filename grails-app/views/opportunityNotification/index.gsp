<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'opportunityNotification.label', default: 'OpportunityNotification')}" />
    <title>订单-消息推送</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>订单-消息推送</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    订单-消息推送
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    所有订单-消息推送
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <g:sortableColumn property="opportunity" title="${message(code: 'opportunityNotification.opportunity.label', default: '订单编号')}" />
                                    <g:sortableColumn property="user" title="${message(code: 'opportunityNotification.user.name.label', default: '用户名')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'opportunityNotification.stage.label', default: '订单阶段')}" />
                                    <g:sortableColumn property="message" title="${message(code: 'opportunityNotification.teamRole.name.label', default: '消息模板')}" />
                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${opportunityNotificationList}">
                                    <tr>
                                        <td>${it.opportunity?.serialNumber}</td>
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
                        <g:paginate total="${opportunityNotificationCount ?: 0}" params="${params}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityWorkflow.label', default: 'OpportunityWorkflow')}"/>
    <title>工作流</title>
</head>

<body>
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link class="list" action="index">工作流</g:link>
                    </li>
                    <li class="active">
                        <span>${this.opportunityWorkflow?.opportunityType?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                工作流: ${this.opportunityWorkflow?.opportunityType?.name}
            </h2>
            <small>
                <span class="glyphicon glyphicon-cog"
                      aria-hidden="true"></span>${this.opportunityWorkflow?.opportunityType?.name}的信息</small>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.opportunityWorkflow}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                        </sec:ifNotGranted>
                </div>
                基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12">
                        <h4>
                            <a href=>${this.opportunityWorkflow?.opportunityType?.name}</a>
                        </h4>
                    </div>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">

                    <div class="col-md-4 border-right">

                        <div class="contact-stat">
                            <span>名称</span>
                            <strong>${this.opportunityWorkflow?.name}</strong>
                        </div>

                    </div>

                    <div class="col-md-4 border-right">

                        <div class="contact-stat">
                            <span>类型</span>
                            <strong>${this.opportunityWorkflow?.opportunityType}</strong>
                        </div>

                    </div>

                    <div class="col-md-4 border-right">

                        <div class="contact-stat">
                            <span>是否启用</span>
                            <strong>${this.opportunityWorkflow?.active}</strong>
                        </div>

                    </div>

                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="hpanel hyellow collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                    <g:link class="btn btn-info btn-xs" controller="opportunityWorkflowRole" action="create"
                            params="[workflow: this.opportunityWorkflow?.id]">
                        <i class="fa fa-plus"></i>新增</g:link>
                        </sec:ifNotGranted>

                    <a class="showhide">
                        <i class="fa fa-chevron-up"></i>
                    </a>

                </div>
                权限
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="user"
                                              title="${message(code: 'opportunityWorkflowRole.user.label', default: '用户名')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityWorkflowRole.stage.label', default: '订单阶段')}"/>
                            <g:sortableColumn property="teamRole"
                                              title="${message(code: 'opportunityWorkflowRole.teamRole.label', default: '权限')}"/>
                            <g:sortableColumn property="opportunityLayout"
                                              title="${message(code: 'opportunityWorkflowRole.opportunityLayout.label', default: '布局')}"/>
                            <g:sortableColumn width="8%" class="text-center" property="operation"
                                              title="${message(code: 'opportunityWorkflowRole.operation.label', default: '操作')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityWorkflowRoles}">
                            <tr>
                                <td><g:link controller="opportunityWorkflowRole" action="show"
                                        id="${it?.id}">${it.user}</g:link></td>
                                <td>${it.stage?.name}</td>
                                <td>${it.teamRole?.name}</td>
                                <td>
                                  ${it?.opportunityLayout?.description}
                                </td>
                                <td class="text-center">
                                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                                    <g:form controller="opportunityWorkflowRole" action="delete" id="${it.id}"
                                            method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                        </sec:ifNotGranted>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                    <g:link class="btn btn-info btn-xs" controller="opportunityWorkflowNotification" action="create"
                            params="[workflow: this.opportunityWorkflow?.id]">
                        <i class="fa fa-plus"></i>新增</g:link>
                        </sec:ifNotGranted>
                    <a class="showhide">
                        <i class="fa fa-chevron-up"></i>
                    </a>

                </div>
                消息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="user"
                                              title="${message(code: 'opportunityWorkflowNotification.user.label', default: '用户名')}"/>

                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityWorkflowNotification.stage.label', default: '订单阶段')}"/>
                            <g:sortableColumn property="messageTemplate"
                                              title="${message(code: 'opportunityWorkflowNotification.messageTemplate.label', default: '消息模板')}"/>
                            <g:sortableColumn property="toManager"
                                              title="${message(code: 'opportunityWorkflowNotification.toManager.label', default: '推送主管')}"/>
                            <g:sortableColumn property="cellphone"
                                              title="${message(code: 'opportunityWorkflowNotification.cellphone.label', default: '手机号')}"/>
                            <g:sortableColumn width="8%" property="operation"
                                              title="${message(code: 'opportunityWorkflowNotification.operation.label', default: '操作')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityWorkflowNotifications}">
                            <tr>
                                <td>${it?.user}</td>
                                <td>${it?.stage?.name}</td>
                                <td>${it?.messageTemplate}</td>
                                <td>${it?.toManager}</td>
                                <td>
                                  <pre>
                                    <code>${it?.cellphone}</code>
                                </pre>
                                </td>
                                <td class="text-center">
                                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                                    <g:form controller="opportunityWorkflowNotification" action="delete" id="${it?.id}"
                                            method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                        </sec:ifNotGranted>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                    <g:link class="btn btn-info btn-xs" controller="opportunityWorkflowStage" action="create"
                            params="[workflow: this.opportunityWorkflow?.id]">
                        <i class="fa fa-plus"></i>新增</g:link>
                        </sec:ifNotGranted>
                    <a class="showhide">
                        <i class="fa fa-chevron-up"></i>
                    </a>

                </div>
                工作流
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityWorkflowStage.executionSequence.label', default: '执行次序')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityWorkflowStage.stage.label', default: '订单阶段')}"/>
                            <g:sortableColumn property="canReject"
                                              title="${message(code: 'opportunityWorkflowStage.canReject.label', default: '能否回退')}"/>
                            <g:sortableColumn property="opportunityLayout"
                                              title="${message(code: 'opportunityWorkflowStage.opportunityLayout.label', default: '布局')}"/>
                            <g:sortableColumn property="document"
                                                                title="${message(code: 'opportunityWorkflowStage.document.label', default: '操作说明')}"/>
                            <g:sortableColumn width="8%" property="operation"
                                              title="${message(code: 'opportunityWorkflowStage.operation.label', default: '操作')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityWorkflowStages}">
                            <tr>
                                <td>
                                    <g:link controller="opportunityWorkflowStage" action="show"
                                            id="${it?.id}">${it.executionSequence}</g:link>
                                </td>
                                <td>${it?.stage?.name}</td>
                                <td>${it?.canReject}</td>

                                <td>${it?.opportunityLayout?.description}</td>
                                <td>${it?.document?.title}</td>
                                <td class="text-center">
                                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                                    <g:form controller="opportunityWorkflowStage" action="delete" id="${it.id}"
                                            method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                        </sec:ifNotGranted>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>
<script>
    $(function () {
        var listGroup = $(".panel-body .list-group");
        listGroup.each(function () {
            if ($(this).children().length > 0) {
                $(this).parent().prev(".panel-heading").removeClass("hbuilt");
                $(this).parent().parent(".hpanel").removeClass("collapsed");
            }
        });

    });
</script>
</body>
</html>

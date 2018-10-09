<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'workflowStage.label', default: 'workflowStage')}"/>
    <title>工作流2.0基本信息</title>
</head>

<body>
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="workflow" class="list" action="index">工作流2.0</g:link>
                    </li>
                    <li class="active">
                        <span>${this.workflowStage?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                工作流2.0
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel horange contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.workflowStage}">
                            <i class="fa fa-edit"></i>编辑</g:link>
                    </sec:ifNotGranted>
                </div>
                工作流2.0基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12">
                        <h4>
                            工作流2.0名称：<a href=>${this.workflowStage?.name}</a>
                        </h4>
                    </div>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">

                    <div class="col-md-4 border-right">

                        <div class="contact-stat">
                            <span>名称</span>
                            <strong>${this.workflowStage?.name}</strong>
                        </div>

                    </div>

                    <div class="col-md-4 border-right">

                        <div class="contact-stat">
                            <span>执行次序</span>
                            <strong>${this.workflowStage?.executionSequence}</strong>
                        </div>

                    </div>

                    <div class="col-md-4 border-right">

                        <div class="contact-stat">
                            <span>是否回退</span>
                            <strong>${this.workflowStage?.canReject}</strong>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel horange">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION">
                        <g:link class="btn btn-info btn-xs" controller="workflowCondition"
                                action="create"
                                params="[stage: this.workflowStage.id]">
                            <i class="fa fa-plus"></i>新增
                        </g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                约束条件
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <th>执行次序</th>
                        <th>组件</th>
                        <th>分支</th>
                        <th>描述</th>
                        <th>操作</th>
                        </thead>
                        <tbody>
                        <g:each in="${this.workflowConditions}">
                            <tr>

                                <td>
                                    ${it?.executeSequence}
                                </td>
                                <td>
                                    ${it?.component?.name}
                                </td>
                                <td>
                                    ${it?.nextStage?.name}
                                </td>
                                <td>
                                    ${it?.message}
                                </td>
                                <td width="10%" class="text-center">
                                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION">
                                        <g:link class="btn btn-primary btn-xs btn-outline" style="display: inline-block"
                                                controller="workflowCondition" action="edit"
                                                id="${it?.id}">
                                            <i class="fa fa-edit"></i>
                                            编辑
                                        </g:link>
                                        <g:form controller="workflowCondition" action="delete" style="display: inline-block"
                                                style="display: inline-block"
                                                id="${it.id}"
                                                method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                                <i class="fa fa-trash-o"></i>
                                                删除
                                            </button>
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
        <div class="hpanel hviolet">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_CONDITION_RULEENGINE">
                        <g:link class="btn btn-info btn-xs" controller="workflowEvent" action="create"
                                params="[stage: this.workflowStage.id]">
                            <i class="fa fa-plus"></i>新增
                        </g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                事件
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="executeSequence"
                                              title="${message(code: 'opportuniryWorkflowEvent.executeSequence.label', default: '执行次序')}"/>
                            <g:sortableColumn property="isSynchronous"
                                              title="${message(code: 'opportuniryWorkflowEvent.isSynchronous.label', default: '同步执行')}"/>

                            <g:sortableColumn property="component"
                                              title="${message(code: 'opportuniryWorkflowEvent.component.label', default: '组件')}"/>

                            <g:sortableColumn width="10%" property="operation" class="text-center" title="操作"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.workflowEvents}">
                            <tr>
                                <td>
                                    ${it?.executeSequence}
                                </td>
                                <td>
                                    ${it?.isSynchronous}
                                </td>
                                <td>
                                    ${it?.component?.name}
                                </td>

                                <td width="10%" class="text-center">
                                    <sec:ifNotGranted roles="ROLE_CONDITION_RULEENGINE">
                                        <g:link class="btn btn-primary btn-xs btn-outline" style="display: inline-block"
                                                controller="workflowEvent" action="edit"
                                                id="${it?.id}">
                                            <i class="fa fa-edit"></i>
                                            编辑
                                        </g:link>
                                        <g:form controller="workflowEvent" action="delete" id="${it.id}" style="display: inline-block"
                                                method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                                <i class="fa fa-trash-o"></i>
                                                删除
                                            </button>
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
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                        <g:link class="btn btn-info btn-xs" controller="workflowUser" action="create"
                                params="[stage: this.workflowStage.id]">
                            <i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>

                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>

                </div>
                权限
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="position"
                                              title="岗位"/>
                            <g:sortableColumn property="authority"
                                              title="权限"/>
                            <g:sortableColumn width="10%" class="text-center" property="operation"
                                              title="操作"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.workflowUsers}">
                            <tr>
                                <td>${it?.position?.name}</td>
                                <td>${it?.authority?.name}</td>
                                <td class="text-center">
                                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">

                                    <g:link class="btn btn-primary btn-xs btn-outline" controller="workflowUser" action="edit" style="display:inline-block"
                                            id="${it?.id}"><i class="fa fa-edit"></i> 编辑</g:link>
                                              <g:form controller="workflowUser" action="delete" id="${it.id}" style="display:inline-block"
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
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                        <g:link class="btn btn-info btn-xs" controller="workflowNotification" action="create"
                                params="[stage: this.workflowStage.id]">
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
                            <g:sortableColumn property="position"
                                              title="岗位"/>
                            <g:sortableColumn property="messageTemplate"
                                              title="${message(code: 'workflowNotification.messageTemplate.label', default: '消息模板')}"/>
                            <g:sortableColumn property="toManager"
                                              title="${message(code: 'workflowNotification.toManager.label', default: '推送主管')}"/>
                            <g:sortableColumn property="cellphone"
                                              title="${message(code: 'workflowNotification.cellphone.label', default: '手机号')}"/>
                            <g:sortableColumn width="10%" class="text-center" property="operation"
                                                                title="操作"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.workflowNotifications}">
                            <tr>
                                <td>${it?.position?.name}</td>
                                <td>${it?.messageTemplate?.text}</td>
                                <td>${it?.toManager}</td>
                                <td>
                                  <pre>
                                      <code>${it?.cellphone}</code>
                                  </pre>
                                </td>
                                <td class="text-center">
                                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                                    <g:link class="btn btn-primary btn-xs btn-outline" controller="workflowNotification" action="edit" style="display:inline-block"
                                            id="${it?.id}"><i class="fa fa-edit"></i> 编辑</g:link>
                                        <g:form controller="workflowNotification" action="delete" id="${it?.id}" style="display:inline-block"
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

</body>
</html>

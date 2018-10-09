<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityFlow.label', default: 'OpportunityFlow')}"/>
    <title>工作流</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunityFlow" action="index">工作流</g:link>
                    </li>
                    <li class="active">
                        <span>${this.opportunityFlow.executionSequence}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                工作流
            </h2>
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
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                        <g:link class="btn btn-info btn-xs" controller="opportunityFlowNextStage"
                                params="[flow: this.opportunityFlow?.id]" action="create"><i
                                class="fa fa-plus"></i>新增</g:link>
                    </sec:ifAllGranted>
                </div>
                分支
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="nextStage"
                                              title="${message(code: 'opportunityFlow.name.label', default: '分支')}"/>
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR" width="10%" class="text-center">
                                <g:sortableColumn width="10%" class="text-center" property="operation" title="操作"></g:sortableColumn>
                            </sec:ifAllGranted>
                        </tr>

                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityFlow?.nextStages}">
                            <tr>

                                <td>
                                    ${it?.nextStage?.stage?.name}
                                </td>
                                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                    <td width="15%" class="text-center">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifAllGranted>
                            </tr>

                        </g:each>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                        <g:link class="btn btn-info btn-xs" controller="opportunityFlowCondition"
                                params="[flow: this.opportunityFlow?.id]" action="create"><i
                                class="fa fa-plus"></i>新增</g:link>
                    </sec:ifAllGranted>
                </div>
                约束条件
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="executeSequence"
                                              title="${message(code: 'opportunityFlowCondition.executeSequence.label', default: '执行次序')}"/>
                            <g:sortableColumn property="condition"
                                              title="${message(code: 'opportunityFlowCondition.condition.label', default: '校验')}"/>
                            <g:sortableColumn property="message"
                                              title="${message(code: 'opportunityFlowCondition.message.label', default: '提示')}"/>
                            <g:sortableColumn property="component"
                                              title="${message(code: 'opportunityFlowCondition.component.label', default: '组件')}"/>
                            <g:sortableColumn property="nextStage"
                                              title="${message(code: 'opportunityFlowCondition.nextStage.label', default: '分支')}"/>
                            <g:sortableColumn property="log"
                                              title="${message(code: 'opportunityFlowCondition.log.label', default: '日志')}"/>
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                <g:sortableColumn property="operation" title="操作" width="10%"
                                                  class="text-center"></g:sortableColumn>
                            </sec:ifAllGranted>
                        </tr>

                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityFlowConditions}">
                            <tr>
                                <td width="5%">
                                    ${it?.executeSequence}
                                </td>
                                <td width="40%">
                                    <g:if test="${it?.condition}">
                                        <pre>
                                            <code>${it?.condition}</code>
                                        </pre>
                                    </g:if>

                                </td>
                                <td width="15%">
                                    ${it?.message}
                                </td>
                                <td width="10%">
                                    ${it?.component?.name}
                                </td>
                                <td width="10%">
                                    ${it?.nextStage?.stage}
                                </td>
                                <td width="10%">
                                    ${it?.log}
                                </td>
                                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                    <td width="10%" class="text-center">
                                        <g:link class="btn btn-primary btn-xs btn-outline m-b-xs inlineBlock" controller="opportunityFlowCondition"                                                action="edit"
                                                id="${it?.id}">
                                            <i class="fa fa-edit"></i>
                                            编辑
                                        </g:link>
                                        <g:form resource="${it}" method="DELETE" class="inlineBlock">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline m-b-xs" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifAllGranted>
                            </tr>

                        </g:each>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                        <g:link class="btn btn-info btn-xs" controller="opportunityFlowAttachmentType"
                                params="[stage: this.opportunityFlow?.id]" action="create"><i
                                class="fa fa-plus"></i>新增</g:link>
                    </sec:ifAllGranted>
                </div>
                附件类型
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'opportunityFlow.name.label', default: '名称')}"/>
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                <g:sortableColumn property="operation" title="操作" width="10%"
                                                  class="text-center"></g:sortableColumn>
                            </sec:ifAllGranted>
                        </tr>

                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityFlowAttachmentTypes}">
                            <tr>
                                <td>
                                    ${it?.attachmentType?.name}
                                </td>
                                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                    <td class="text-center">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger m-b-xs btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifAllGranted>
                            </tr>

                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                        <g:link class="btn btn-info btn-xs" controller="opportunityEvent"
                                params="[stage: this.opportunityFlow?.id]" action="create"><i
                                class="fa fa-plus"></i>新增</g:link>
                    </sec:ifAllGranted>
                </div>
                EVENT
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'opportunityFlow.name.label', default: '名称')}"/>
                            <g:sortableColumn property="isSynchronous"
                                              title="${message(code: 'opportunityFlow.isSynchronous.label', default: '同步执行')}"/>
                            <g:sortableColumn property="executeSequence"
                                              title="${message(code: 'opportunityFlow.executeSequence.label', default: '执行次序')}"/>
                            <g:sortableColumn property="script"
                                              title="${message(code: 'opportunityFlow.script.label', default: '脚本')}"/>
                            <g:sortableColumn property="component"
                                              title="${message(code: 'opportunityFlow.component.label', default: '组件')}"/>
                            <g:sortableColumn property="log"
                                              title="${message(code: 'opportunityFlow.log.label', default: '日志')}"/>
                            <g:sortableColumn property="startTime"
                                              title="${message(code: 'opportunityFlow.startTime.label', default: '开始时间')}"/>
                            <g:sortableColumn property="endTime"
                                              title="${message(code: 'opportunityFlow.endTime.label', default: '结束时间')}"/>
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                <g:sortableColumn property="operation" title="操作" width="10%" class="text-center"></g:sortableColumn>
                            </sec:ifAllGranted>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityEvents}">
                            <tr>
                                <td>
                                    ${it?.name}
                                </td>
                                <td>
                                    ${it?.isSynchronous}
                                </td>
                                <td>
                                    ${it?.executeSequence}
                                </td>
                                <td>
                                    <g:if test="${it?.script}">
                                        <pre>
                                            <code>${it?.script}</code>
                                        </pre>
                                    </g:if>
                                </td>
                                <td>
                                    ${it?.component?.name}
                                </td>
                                <td>
                                    <g:if test="${it?.log}">
                                        <pre>
                                            <code>${it?.log}</code>
                                        </pre>
                                    </g:if>
                                </td>
                                <td>
                                    ${it?.startTime}
                                </td>
                                <td>
                                    ${it?.endTime}
                                </td>
                                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                    <td class="text-center">
                                        <g:link class="btn btn-primary btn-xs btn-outline m-b-xs" controller="opportunityEvent"
                                                action="edit"
                                                id="${it?.id}">
                                            <i class="fa fa-edit"></i>
                                            编辑
                                        </g:link>
                                        <g:form resource="${it}" method="DELETE" class="m-b-xs">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                        <g:link class="btn btn-success btn-xs btn-outline" controller="opportunityEvent"
                                                action="eventEvaluate" id="${it?.id}"><i
                                                class="fa fa-hand-o-right"></i> 执行</g:link>
                                    </td>

                                </sec:ifAllGranted>
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

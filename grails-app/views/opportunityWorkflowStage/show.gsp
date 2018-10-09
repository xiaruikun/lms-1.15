<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityWorkflowStage.label', default: 'OpportunityWorkflowStage')}"/>
    <title>工作流基本信息</title>
</head>

<body>
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunityWorkflow" class="list" action="index">工作流</g:link>
                    </li>
                    <li class="active">
                        <span>${this.opportunityWorkflowStage?.stage?.name}</span>
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
        <div class="hpanel horange contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.opportunityWorkflowStage}">
                            <i class="fa fa-edit"></i>编辑</g:link>
                    </sec:ifNotGranted>
                </div>
                工作流基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12">
                        <h4>
                            <a href=>${this.opportunityWorkflowStage?.stage?.name}</a>
                        </h4>
                    </div>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">

                    <div class="col-md-2 border-right">

                        <div class="contact-stat">
                            <span>阶段</span>
                            <strong>${this.opportunityWorkflowStage?.stage?.name}</strong>
                        </div>

                    </div>

                    <div class="col-md-2 border-right">

                        <div class="contact-stat">
                            <span>执行次序</span>
                            <strong>${this.opportunityWorkflowStage?.executionSequence}</strong>
                        </div>

                    </div>

                    <div class="col-md-2 border-right">

                        <div class="contact-stat">
                            <span>是否回退</span>
                            <strong>${this.opportunityWorkflowStage?.canReject}</strong>
                        </div>

                    </div>

                    <div class="col-md-3 border-right">

                        <div class="contact-stat">
                            <span>布局</span>
                            <strong>${this.opportunityWorkflowStage?.opportunityLayout?.description}</strong>
                        </div>

                    </div>

                    <div class="col-md-3 border-right">

                        <div class="contact-stat">
                            <span>操作说明</span>
                            <strong>${this.opportunityWorkflowStage?.document?.title}</strong>
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
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                        <g:link class="btn btn-info btn-xs" controller="opportunityWorkflowStageNextStage"
                                action="create"
                                params="[stage: this.opportunityWorkflowStage.id]"><i class="fa fa-plus"></i>添加</g:link>
                    </sec:ifNotGranted>
                </div>
                分支
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <tbody>
                        <g:each in="${this.opportunityWorkflowStage?.nextStages}">
                            <tr>

                                <td width="92%">
                                    ${it?.nextStage?.stage?.name}
                                </td>
                                <td width="8%" class="text-center">
                                    <g:form controller="opportunityWorkflowStageNextStage" action="delete" id="${it.id}"
                                            method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i>
                                            删除
                                        </button>
                                    </g:form>
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
        <div class="hpanel horange">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION">
                        <g:link class="btn btn-info btn-xs" controller="opportunityWorkflowStageCondition"
                                action="create"
                                params="[stage: this.opportunityWorkflowStage.id]">
                            <i class="fa fa-plus"></i>添加
                        </g:link>
                    </sec:ifNotGranted>
                </div>
                约束条件
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <th>执行次序</th>
                        <th>条件内容</th>
                        <th>条件描述</th>
                        <th>组件</th>
                        <th>分支</th>
                        <th>操作</th>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityWorkflowStageConditions}">
                            <tr>
                              <td width="5%">
                                  ${it?.executeSequence}
                              </td>
                                <td width="45%">
                                    <g:if test="${it?.condition}">
                                        <pre>
                                            <code>${it?.condition}</code>
                                        </pre>
                                    </g:if>

                                </td>
                                <td width="20%">
                                    ${it?.message}
                                </td>
                                <td width="10%">
                                    ${it?.component?.name}
                                </td>
                                <td width="10%">
                                    ${it?.nextStage?.stage}
                                </td>
                                <td width="10%" class="text-center">
                                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION">
                                        <g:link class="btn btn-primary btn-xs btn-outline m-b-xs" 
                                                controller="opportunityWorkflowStageCondition" action="edit"
                                                id="${it?.id}">
                                            <i class="fa fa-edit"></i>
                                            编辑
                                        </g:link>
                                        <g:form controller="opportunityWorkflowStageCondition" action="delete"
                                                
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
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                        <g:link class="btn btn-info btn-xs" controller="opportunityWorkflowStageAttachmentType"
                                action="create"
                                params="[stage: this.opportunityWorkflowStage.id]">
                            <i class="fa fa-plus"></i>添加
                        </g:link>
                    </sec:ifNotGranted>
                </div>
                附件类型
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <tbody>
                        <g:each in="${this.opportunityWorkflowStageAttachmentTypes}">
                            <tr>

                                <td width="92%">
                                    ${it?.attachmentType?.name}
                                </td>
                                <td width="8%" class="text-center">
                                    <g:form controller="opportunityWorkflowStageAttachmentType" action="delete"
                                            id="${it.id}"
                                            method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i>
                                            删除
                                        </button>
                                    </g:form>
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
                        <g:link class="btn btn-info btn-xs" controller="opportunityWorkflowEvent" action="create"
                                params="[stage: this.opportunityWorkflowStage.id]">
                            <i class="fa fa-plus"></i>添加
                        </g:link>
                    </sec:ifNotGranted>
                </div>
                EVENT
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'opportuniryWorkflowEvent.name.label', default: '名称')}"/>
                            <g:sortableColumn property="isSynchronous"
                                              title="${message(code: 'opportuniryWorkflowEvent.isSynchronous.label', default: '同步执行')}"/>
                            <g:sortableColumn property="executeSequence"
                                              title="${message(code: 'opportuniryWorkflowEvent.executeSequence.label', default: '执行次序')}"/>
                            <g:sortableColumn property="script"
                                              title="${message(code: 'opportuniryWorkflowEvent.script.label', default: '脚本')}"/>
                            <g:sortableColumn property="component"
                                              title="${message(code: 'opportuniryWorkflowEvent.component.label', default: '组件')}"/>

                            <g:sortableColumn property="operation" class="text-center" title="操作"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityWorkflowStageEvents}">
                            <tr>
                                <td>
                                    <sec:ifNotGranted roles="ROLE_CONDITION_RULEENGINE">
                                        <g:link class="text-info" controller="opportunityWorkflowEvent" action="edit"
                                                id="${it?.id}">${it?.name}</g:link>
                                    </sec:ifNotGranted>
                                    <sec:ifAllGranted roles="ROLE_CONDITION_RULEENGINE">
                                        ${it?.name}
                                    </sec:ifAllGranted>
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

                                <td width="10%" class="text-center">
                                    <sec:ifNotGranted roles="ROLE_CONDITION_RULEENGINE">
                                        <g:link class="btn btn-primary btn-xs btn-outline m-b-xs" 
                                                controller="opportunityWorkflowEvent" action="edit"
                                                id="${it?.id}">
                                            <i class="fa fa-edit"></i>
                                            编辑
                                        </g:link>
                                        <g:form controller="opportunityWorkflowEvent" action="delete" id="${it.id}" 
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
</div>

</body>
</html>

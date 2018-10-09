<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territory.label', default: 'Territory')}" />
    <title>区域：${territory.name}</title>
    <style media="screen">

    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territory" action="index">区域管理</g:link>
                        </li>
                        <li class="active">
                            <span>${this.territory.name}</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    区域: ${this.territory.name}
                </h2>
                <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>${this.territory.name}的信息</small>
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
            <div class="hpanel hyellow contact-panel">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.territory}"><i class="fa fa-edit"></i>编辑</g:link>
                    </div>
                    区域基本信息
                </div>
                <div class="panel-body">
                    <div class="text-muted font-bold m-b-xs ol-md-6">
                        <div class="col-md-12">
                            <h4><a href=>${this.territory.name}</a></h4></div>
                    </div>
                    %{--<div class="text-muted font-bold m-b-xs ol-md-6">
                        <div class="col-md-12">
                        <h5><g:link controller="liquidityRiskReportTemplate" action="show" id="${this.territory?.liquidityRiskReportTemplate?.id}">流动性评分模板：${this.territory?.liquidityRiskReportTemplate?.name}</g:link></h4>
                        </div>
                    </div>--}%
                </div>
                <div class="panel-footer contact-footer">
                    <div class="row">
                        <div class="col-md-3 border-right" >
                            <g:if test="${this.territory.inheritTeam}">
                                <g:link action="show" id="${this.territory?.parent?.id}">
                                    <div class="contact-stat"><span>继承团队</span> <strong>${this.territory?.inheritTeam}</strong></div>
                                </g:link>
                            </g:if>
                            <g:else>
                                <div class="contact-stat"><span>继承团队</span> <strong>${this.territory?.inheritTeam}</strong></div>
                            </g:else>
                        </div>
                        <div class="col-md-3 border-right" >
                            <g:if test="${this.territory.inheritRole}">
                                <g:link action="show" id="${this.territory?.parent?.id}">
                                    <div class="contact-stat"><span>继承权限</span> <strong>${this.territory?.inheritRole}</strong></div>
                                </g:link>
                            </g:if>
                            <g:else>
                                <div class="contact-stat"><span>继承权限</span> <strong>${this.territory?.inheritRole}</strong></div>
                            </g:else>
                        </div>
                        <div class="col-md-3 border-right" >
                            <g:if test="${this.territory.inheritNotification}">
                                <g:link action="show" id="${this.territory?.parent?.id}">
                                    <div class="contact-stat"><span>继承消息</span> <strong>${this.territory?.inheritNotification}</strong></div>
                                </g:link>
                            </g:if>
                            <g:else>
                                <div class="contact-stat"><span>继承消息</span> <strong>${this.territory?.inheritNotification}</strong></div>
                            </g:else>
                        </div>
                        <div class="col-md-3 border-right" >
                            <g:if test="${this.territory.inheritFlow}">
                                <g:link action="show" id="${this.territory?.parent?.id}">
                                    <div class="contact-stat"><span>继承工作流</span> <strong>${this.territory?.inheritFlow}</strong></div>
                                </g:link>
                            </g:if>
                            <g:else>
                                <div class="contact-stat"><span>继承工作流</span> <strong>${this.territory?.inheritFlow}</strong></div>
                            </g:else>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="hpanel hyellow collapsed">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="territoryAccount" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </div>
                    机构
                </div>
                <div class="panel-body no-padding">
                    <ul class="list-group">
                        <g:each in="${this.territoryAccounts}">
                            <li class="list-group-item">
                                <div class="pull-right">
                                    <g:form controller="territoryAccount" action="delete" id="${it.id}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                </div>
                                <g:link controller="account" action="show" id="${it.account?.id}">${it.account?.name}</g:link>
                            </li>
                        </g:each>
                    </ul>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="hpanel hyellow collapsed">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="territoryCity" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </div>
                    城市
                </div>
                <div class="panel-body no-padding">
                    <ul class="list-group">
                        <g:each in="${this.territoryCities}">
                            <li class="list-group-item">
                                <div class="pull-right">
                                    <g:form controller="territoryCity" action="delete" id="${it.id}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                </div>
                                <g:link controller="city" action="show" id="${it.city?.id}">${it.city?.name}</g:link>
                            </li>
                        </g:each>
                    </ul>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="hpanel hyellow collapsed">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="territoryProduct" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </div>
                    产品
                </div>
                <div class="panel-body no-padding">
                    <ul class="list-group">
                        <g:each in="${this.territoryProducts}">
                            <li class="list-group-item">
                                <div class="pull-right">
                                    <g:form controller="territoryProduct" action="delete" id="${it.id}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                </div>
                                <g:link controller="product" action="show" id="${it.product?.id}">${it.product?.name}</g:link>
                            </li>
                        </g:each>
                    </ul>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="hpanel hyellow collapsed">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="territoryTeam" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    团队
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>用户</th>
                                <th>布局</th>
                                <th width ="8%" class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.territoryTeams}">
                                <tr>
                                    <td>
                                        <g:link controller="territoryTeam" action="show" id="${it?.id}">${it.user}</g:link>
                                    </td>
                                    <td>${it?.opportunityLayout?.description}</td>
                                    <td width ="8%" class="text-center">
                                        <g:form controller="territoryTeam" action="delete" id="${it.id}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
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
            <div class="hpanel hyellow collapsed">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="territoryRole" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    权限
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <g:sortableColumn property="user" title="${message(code: 'territoryRole.user.label', default: '用户名')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'territoryRole.stage.label', default: '订单阶段')}" />
                                    <g:sortableColumn property="teamRole" title="${message(code: 'territoryRole.teamRole.label', default: '权限')}" />
                                    <g:sortableColumn width ="8%" class="text-center" property="operation" title="${message(code: 'territoryRole.operation.label', default: '操作')}" />
                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${this.territoryRoles}">
                                    <tr>
                                        <td>${it.user}</td>
                                        <td>${it.stage?.name}</td>
                                        <td>${it.teamRole?.name}</td>
                                        <td width ="8%" class="text-center">
                                            <g:form controller="territoryRole" action="delete" id="${it.id}" method="DELETE">
                                                <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
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
            <div class="hpanel hyellow collapsed">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">
                        <g:if test="${this.territory.inheritNotification == false}">
                            <g:link class="btn btn-info btn-xs" controller="territoryNotification" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </g:if>
                    </div>
                    消息
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <g:sortableColumn property="user" title="${message(code: 'territoryNotification.user.label', default: '用户名')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'territoryNotification.stage.label', default: '订单阶段')}" />
                                    <g:sortableColumn property="messageTemplate" title="${message(code: 'territoryNotification.messageTemplate.label', default: '消息模板')}" />
                                    <g:sortableColumn property="toManager" title="${message(code: 'territoryNotification.toManager.label', default: '推送主管')}" />

                                    <g:if test="${this.territory.inheritNotification == false}">
                                        <g:sortableColumn width ="8%" class="text-center" property="operation" title="${message(code: 'territoryNotification.operation.label', default: '操作')}" />
                                    </g:if>
                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${this.territoryNotifications}">
                                    <tr>
                                        <td>${it.user}</td>
                                        <td>${it.stage?.name}</td>
                                        <td>${it.messageTemplate?.text}</td>
                                        <td>${it.toManager}</td>
                                        <g:if test="${this.territory.inheritNotification == false}">
                                            <td width ="8%" class="text-center">
                                                <g:form controller="territoryNotification" action="delete" id="${it.id}" method="DELETE">
                                                    <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
                                                </g:form>
                                            </td>
                                        </g:if>
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
                        <g:if test="${this.territory.inheritFlow == false}">
                            <g:link class="btn btn-info btn-xs" controller="territoryFlow" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </g:if>
                    </div>
                    工作流
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <g:sortableColumn property="executionSequence" title="${message(code: 'territoryFlow.executionSequence.label', default: '次序')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'territoryFlow.stage.label', default: '订单阶段')}" />
                                    <g:sortableColumn property="canReject" title="${message(code: 'territoryFlow.canReject.label', default: '能否回退')}" />
                                    <g:sortableColumn property="opportunityLayout" title="${message(code: 'territoryFlow.opportunityLayout.label', default: '布局')}" />
                                    <g:if test="${this.territory.inheritFlow == false}">
                                        <g:sortableColumn width ="8%" class="text-center" property="operation" title="${message(code: 'territoryNotification.operation.label', default: '操作')}" />
                                    </g:if>
                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${this.territoryFlows}">
                                    <tr>
                                    <td><g:link controller="territoryFlow" action="show" id="${it?.id}">${it.executionSequence}</g:link></td>
                                        <td>${it.stage?.name}</td>
                                        <td>${it.canReject}</td>
                                        <td>${it?.opportunityLayout?.description}</td>
                                        <g:if test="${this.territory.inheritFlow == false}">
                                            <td width ="8%" class="text-center">
                                                <g:form controller="territoryFlow" action="delete" id="${it.id}" method="DELETE">
                                                    <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
                                                </g:form>
                                            </td>
                                        </g:if>
                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- <div class="row">
            <div class="hpanel hyellow collapsed">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">

                            <g:link class="btn btn-info btn-xs" controller="OpportunityWorkflow" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>

                    </div>
                    类型
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>


                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${this.workFlows}">
                                    <tr>
                                    <td>
                                      <g:link controller="opportunityWorkflow" action="show" id="${it?.id}">${it?.opportunityType?.name}</g:link>

                                  </td>

                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div> -->
        <div class="row">
            <div class="hpanel hyellow collapsed">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">
                        %{--<g:if test="${this.territory.inheritFlow == false}">--}%
                        <g:link class="btn btn-info btn-xs" controller="territoryFlexFieldCategory" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        %{--</g:if>--}%
                    </div>
                    弹性域模块
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="id" title="${message(code: 'territoryFlow.executionSequence.label', default: '序号')}" />
                                <g:sortableColumn property="opportunityType.name" title="${message(code: 'territoryFlow.stage.label', default: '订单类型')}" />
                                <g:sortableColumn property="name" title="${message(code: 'territoryFlow.stage.label', default: '名称')}" />
                                %{--<g:if test="${this.territory.inheritFlow == false}">--}%
                                <g:sortableColumn width ="8%" class="text-center" property="operation" title="${message(code: 'territoryNotification.operation.label', default: '操作')}" />
                                %{--</g:if>--}%
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.territoryFlexFieldCategories}">
                                <tr>
                                    %{--<td><g:link controller="territoryFlexFieldCategory" action="show" id="${it?.id}">${it?.id}</g:link></td>--}%
                                    <td>${it?.id}</td>
                                    <td>${it.opportunityType?.name}</td>
                                    <td>${it.flexFieldCategory?.name}</td>
                                    %{--<g:if test="${this.territory.inheritFlow == false}">--}%
                                    <td width ="8%" class="text-center">
                                        <g:form controller="territoryFlexFieldCategory" action="delete" id="${it.id}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                    %{--</g:if>--}%
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
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    下属区域
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                         <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'territory.name.label', default: '区域名称')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${subTerritories}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link></td>
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
                        <g:link class="btn btn-info btn-xs" controller="territoryOpportunityWorkflow" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    区域工作流
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                              <g:sortableColumn property="name" title="${message(code: 'territoryOpportunityWorkflow.name', default: '名称')}" />
                                <g:sortableColumn property="workflow" title="${message(code: 'territoryOpportunityWorkflow.workflow', default: '类型')}" />
                                <g:sortableColumn width ="8%" class="text-center" property="operation" title="${message(code: 'territoryNotification.operation.label', default: '操作')}" />
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.territoryOpportunityWorkflows}">
                                <tr>
                                    <td><g:link controller="territoryOpportunityWorkflow" action="show" id="${it?.id}">${it?.workflow?.name}</g:link></td>
                                    <td>${it?.workflow?.opportunityType?.name}</td>

                                    <td width ="8%" class="text-center">
                                        <g:form controller="territoryOpportunityWorkflow" action="delete" id="${it.id}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
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
            <div class="hpanel hyellow collapsed">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="territoryWorkflow" action="create" params="[territory: this.territory.id]"><i class="fa fa-plus"></i>新增</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    区域工作流2.0
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                              <g:sortableColumn property="name" title="${message(code: 'territoryWorkflow.workflow.name', default: '名称')}" />
                                <g:sortableColumn width ="8%" class="text-center" property="operation" title="${message(code: 'territoryNotification.operation.label', default: '操作')}" />
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.territoryWorkflows}">
                                <tr>
                                    <td>${it?.workflow?.name}</td>
                                    <td width ="8%" class="text-center">
                                        <g:form controller="territoryWorkflow" action="delete" id="${it.id}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i class="fa fa-trash-o"></i> 删除</button>
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
    </div>
<script>
    $(function () {
        var listGroup = $(".panel-body .list-group");
        listGroup.each(function(){
            if($(this).children().length > 0){
                $(this).parent().prev(".panel-heading").removeClass("hbuilt");
                $(this).parent().parent(".hpanel").removeClass("collapsed");
            }
        });

    });
</script>
</body>

</html>

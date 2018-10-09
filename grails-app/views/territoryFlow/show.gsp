<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'territoryFlow.label', default: 'TerritoryFlow')}"/>
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
                        <g:link controller="territoryFlow" action="index">工作流</g:link>
                    </li>
                    <li class="active">
                        <span>${this.territoryFlow?.stage?.name}</span>
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
        <div class="hpanel hyellow contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.territoryFlow}">
                        <i class="fa fa-edit"></i>编辑</g:link>
                </div>
                工作流基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12">
                        <h4>
                            <a href=>${this.territoryFlow?.stage?.name}</a>
                        </h4>
                    </div>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-3 border-right">

                        <div class="contact-stat">
                            <span>区域</span>
                            <strong>${this.territoryFlow?.territory?.name}</strong>
                        </div>

                    </div>

                    <div class="col-md-3 border-right">

                        <div class="contact-stat">
                            <span>阶段</span>
                            <strong>${this.territoryFlow?.stage?.name}</strong>
                        </div>

                    </div>

                    <div class="col-md-3 border-right">

                        <div class="contact-stat">
                            <span>执行次序</span>
                            <strong>${this.territoryFlow?.executionSequence}</strong>
                        </div>

                    </div>

                    <div class="col-md-1 border-right">

                        <div class="contact-stat">
                            <span>是否回退</span>
                            <strong>${this.territoryFlow?.canReject}</strong>
                        </div>

                    </div>

                    <div class="col-md-2 border-right">

                        <div class="contact-stat">
                            <span>布局</span>
                            <strong>${this.territoryFlow?.opportunityLayout?.description}</strong>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="territoryFlowNextStage" action="create"
                            params="[flow: this.territoryFlow.id]">
                        <i class="fa fa-plus"></i>添加</g:link>
                </div>
                分支
            </div>

            <div class="panel-body no-padding">
                <table class="table table-striped table-bordered table-hover">
                    <tbody>
                    <g:each in="${this.territoryFlow?.nextStages}">
                        <tr>

                            <td width="90%">
                                ${it?.nextStage?.stage?.name}
                            </td>
                            <td width="10%" class="text-center">
                                <g:form controller="territoryFlowNextStage" action="delete" id="${it.id}"
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

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="territoryFlowCondition" action="create"
                            params="[flow: this.territoryFlow.id]">
                        <i class="fa fa-plus"></i>
                        添加
                    </g:link>
                </div>
                约束条件
            </div>

            <div class="panel-body no-padding">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <th>条件内容</th>
                    <th>条件描述</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                    <g:each in="${this.territoryFlow?.conditions}">
                        <tr>

                            <td width="70%">
                                <pre>
                                    <code>${it?.condition}</code>
                                </pre>
                            </td>
                            <td width="20%">
                                ${it?.message}
                            </td>
                            <td width="10%" class="text-center">
                                <g:link class="btn btn-primary btn-xs btn-outline" controller="territoryFlowCondition"
                                        action="edit"
                                        id="${it?.id}">
                                    <i class="fa fa-edit"></i>
                                    编辑
                                </g:link>
                                <g:form controller="territoryFlowCondition" action="delete" id="${it.id}" style="display: inline-block"
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
</body>
</html>

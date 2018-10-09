<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityWorkflow.label', default: 'OpportunityWorkflow')}"/>
    <title>工作流</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>工作流</span>
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
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                    <g:link class="btn btn-info btn-xs" controller="OpportunityWorkflow" action="create"><i
                            class="fa fa-plus"></i>新增</g:link>
                        </sec:ifNotGranted>
                </div>
                工作流
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'opportuniryWorkflow.name.label', default: '名称')}"/>
                            <g:sortableColumn property="opportunityType"
                                              title="${message(code: 'opportuniryWorkflow.opportunityType.name.label', default: '类型')}"/>
                            <g:sortableColumn property="operation"
                                              title="复制工作流"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${opportunityWorkflowList}">
                            <tr>
                                <td><g:link controller="opportunityWorkflow" action="show"
                                            id="${it?.id}">${it?.name}</g:link></td>
                                <td>${it?.opportunityType}</td>
                                <td>
                                  <g:link controller="territory" action="prepareCopyFlow" params="[preOpportunityWorkflow: it?.id]">复制</g:link>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${opportunityWorkflowCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

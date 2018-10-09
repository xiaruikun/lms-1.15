<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityWorkflow.label', default: 'OpportunityWorkflow')}"/>
    <title>工作流2.0</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>工作流2.0</span>
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
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                    <g:link class="btn btn-info btn-xs" controller="workflow" action="create"><i
                            class="fa fa-plus"></i>新增</g:link>
                        </sec:ifNotGranted>
                </div>
                工作流2.0
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name" title="名称"/>
                            <g:sortableColumn property="active" title="是否启用"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${workflowList}">
                            <tr>
                                <td><g:link controller="workflow" action="show"
                                            id="${it?.id}">${it?.name}</g:link></td>
                                <td>${it?.active}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${workflowCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

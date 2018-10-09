<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'workflow.label', default: 'workflow')}"/>
    <title>工作流2.0</title>
</head>

<body>
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link class="list" action="index">工作流2.0</g:link>
                    </li>
                    <li class="active">
                        <span>${this.workflow?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                工作流2.0: ${this.workflow?.name}
            </h2>
            <small>
                <span class="glyphicon glyphicon-cog"
                      aria-hidden="true"></span>${this.workflow?.name}的信息</small>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.workflow}"><i
                                class="fa fa-edit"></i>编辑</g:link>
                    </sec:ifNotGranted>
                </div>
                基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12">
                        <h4>
                            <a href=>${this.workflow?.name}</a>
                        </h4>
                    </div>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">

                    <div class="col-md-6 border-right">

                        <div class="contact-stat">
                            <span>名称</span>
                            <strong>${this.workflow?.name}</strong>
                        </div>

                    </div>

                    <div class="col-md-6">

                        <div class="contact-stat">
                            <span>是否启用</span>
                            <strong>${this.workflow?.active}</strong>
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
                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                        <g:link class="btn btn-info btn-xs" controller="workflowStage" action="create"
                                params="[workflow: this.workflow?.id]">
                            <i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
                    <a class="showhide">
                        <i class="fa fa-chevron-up"></i>
                    </a>

                </div>
                工作流2.0
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                          <g:sortableColumn property="executionSequence"
                          title="${message(code: 'workflowStage.executionSequence.label', default: '执行次序')}"/>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'workflowStage.executionSequence.label', default: '名称')}"/>
                            <g:sortableColumn property="canReject"
                                              title="${message(code: 'workflowStage.canReject.label', default: '能否回退')}"/>
                            <g:sortableColumn class="text-center" width="8%" property="operation"
                                              title="${message(code: 'workflowStage.operation.label', default: '操作')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.workflowStages}">
                            <tr>
                              <td>
                                <g:link controller="workflowStage" action="show"
                                        id="${it?.id}">${it?.executionSequence}</g:link>
                              </td>
                                <td>
                                    ${it?.name}
                                </td>
                                <td>${it?.canReject}</td>
                                <td class="text-center">
                                    <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                                        <g:form controller="workflowStage" action="delete" id="${it.id}"
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

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'workflowStage.label', default: 'OpportunityWorkflowStage')}"/>
    <title>工作流信息编辑</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="workflow" class="list" action="index">工作流</g:link>
                    </li>
                    <li class="active">
                        <span>工作流信息编辑</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                工作流信息编辑
            </h2>

        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                工作流信息编辑
            </div>

            <div class="panel-body">
                <g:form resource="${this.workflowStage}" method="PUT" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.workflowStage}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.workflowStage}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <div class="form-group">
                        <g:textField name="workflow.id" required="required" id="workflow"
                                     value="${this.workflowStage?.workflow?.id}" class="hide"/>

                        <label class="col-md-4 control-label">name</label>

                        <div class="col-md-3">
                            <g:textField name="name"
                                         value="${this.workflowStage?.name}"
                                         class="form-control"/>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">执行次序</label>

                        <div class="col-md-3">
                            <g:textField name="executionSequence"
                                         value="${this.workflowStage?.executionSequence}"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">

                        <label class="col-md-4 control-label">能否退回</label>

                        <div class="col-md-3">
                            <input type="hidden" value="${this.workflowStage?.canReject}" id="canReject">

                            <div class="radio radio-info radio-inline ">
                                <input type="radio" name="canReject" value="true" id="radio1">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline ">
                                <input type="radio" name="canReject" value="false" id="radio2">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                    </div>


                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-4">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        if ($("#canReject").val() == "true") {
            $("#radio1")[0].checked = true;
        } else {
            $("#radio2")[0].checked = true;
        }
    })
</script>
</body>
</html>

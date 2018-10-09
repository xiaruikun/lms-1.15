<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'workflowStage.label', default: 'workflowStage')}"/>
    <title>新增区域-工作流</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="territoryFlow" action="index">区域-工作流</g:link>
                    </li>
                    <li class="active">
                        <span>新增区域-工作流</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增区域-工作流
            </h2>
        </div>
    </div>
</div>
%{--<f:all bean="workflowStage"/>--}%

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增区域工作流
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
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

                        <label class="col-md-4 control-label">名称</label>

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
                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="canReject" value="true" checked="">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="canReject" value="false">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>


                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-4">
                            <g:submitButton class="btn btn-info" id="btn" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

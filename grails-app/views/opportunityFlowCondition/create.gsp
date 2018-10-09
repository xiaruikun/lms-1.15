<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityFlowCondition.label', default: 'OpportunityFlowCondition')}"/>
    <title>新增工作流条件</title>
    <asset:stylesheet src="homer/vendor/codemirror/codemirror.css"/>

</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        工作流-条件
                    </li>
                    <li class="active">
                        <span>新增工作流-条件</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增工作流条件
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增工作流-条件
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal flowForm">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityFlowCondition}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityFlowCondition}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-4 control-label">组件</label>

                        <div class="col-md-4">
                            <g:textField name="flow.id" id="flow" value="${this.opportunityFlowCondition?.flow?.id}"
                                         class="hide"/>
                            <g:select class="form-control" name="component.id" id="component"
                                      value="${this.opportunityFlowCondition?.component?.id}"
                                      from="${this.componentList}" optionKey="id" optionValue="name"
                                      noSelection="['': '--请选择--']"></g:select>

                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">执行次序</label>

                        <div class="col-md-4">
                            <g:textField name="executeSequence"
                                         value="${this.opportunityFlowCondition?.executeSequence}"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">分支</label>

                        <div class="col-md-4">
                            <g:select class="form-control" name="nextStage.id" id="nextStage"
                                      value="${this.opportunityFlowCondition?.nextStage?.id}"
                                      from="${this.nextStages}" optionKey="id" optionValue="stage"
                                      noSelection="['': '--请选择--']"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-4 col-md-offset-5">
                            <button class="submitBtn btn btn-info" type="submit">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

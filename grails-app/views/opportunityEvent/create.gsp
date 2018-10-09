<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityEvent.label', default: 'OpportunityEvent')}"/>
    <title>新增工作流-Event</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        工作流-Event
                    </li>
                    <li class="active">
                        <span>新增工作流-Event</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增Event
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增工作流-EVENT
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal flowForm">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityEvent}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityEvent}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">名称</label>

                        <div class="col-md-4">
                            <g:textField name="stage.id" required="required" id="stage"
                                         value="${this.opportunityEvent?.stage?.id}" class="hide"></g:textField>
                            <g:textField class="form-control" name="name"
                                         value="${this.opportunityEvent?.name}"></g:textField>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">执行次序</label>

                        <div class="col-md-4">
                            <g:textField name="executeSequence" required="required"
                                         value="${this.opportunityEvent?.executeSequence}"
                                         class="form-control"></g:textField>
                        </div>

                    </div>


                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">组件</label>

                        <div class="col-md-4">
                            <g:select class="form-control" name="component.id"  id="component"
                                      value="${this.opportunityEvent?.component?.id}" from="${this.componentList}"
                                      optionKey="id" optionValue="name" noSelection="['': '--请选择--']"></g:select>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">同步执行</label>

                        <div class="col-md-4">
                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="isSynchronous" value="true" checked="">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="isSynchronous" value="false">
                                <label for="radio2">false</label>
                            </div>
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

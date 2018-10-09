<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <g:set var="entityName" value="${message(code: 'workflowNotification.label', default: 'OpportunityWorkflowNotification')}"/>
        <title><g:message code="default.edit.label" args="[entityName]"/></title>
    </head>
    <body>
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li>
                                区域消息
                            </li>
                            <li class="active">
                                <span>新增区域消息</span>
                            </li>
                        </ol>
                    </div>

                    <h2 class="font-light m-b-xs">
                        新增区域消息
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        新增区域消息
                    </div>

                    <div class="panel-body form-horizontal">

                        <g:form resource="${this.workflowNotification}" method="PUT">
                            <g:if test="${flash.message}">
                                <div class="message" role="status">${flash.message}</div>
                            </g:if>
                            <g:hasErrors bean="${this.workflowNotification}">
                                <ul class="errors" role="alert">
                                    <g:eachError bean="${this.workflowNotification}" var="error"><li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-4 control-label">岗位</label>

                        <div class="col-md-4">
                            <input type="hidden" name="stage.id" value="${this.workflowNotification?.stage?.id}">
                                <g:select class="form-control" name="position.id" required="required" id="position" value="${this.workflowNotification?.position?.id}" from="${com.next.Position.list()}" optionKey="id" optionValue="name" noSelection="['': '--请选择--']"></g:select>

                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">消息模板</label>

                            <div class="col-md-4">
                                <g:select class="form-control" name="messageTemplate.id" required="required" id="messageTemplate" value="${this.workflowNotification?.messageTemplate}" from="${com.next.MessageTemplate.list()}" optionKey="id"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group ">
                            <label class="col-md-4 control-label">推送主管</label>
                            <div class="col-md-4 checkbox-inline">
                                <g:radioGroup class="checkbox-inline" name="toManager" value="${this.workflowNotification?.toManager}" labels="['true','false']" values="[true,false]">
                                    ${it.radio} ${it.label}
                                </g:radioGroup>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-5">
                                <button class="submitBtn btn btn-info" type="submit">保存</button>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

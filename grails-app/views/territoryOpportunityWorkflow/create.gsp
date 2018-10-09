<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'territoryOpportunityWorkflow.label', default: 'TerritoryOpportunityWorkflow')}"/>
    <title>新增区域工作流</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="territory" action="show" id="${this.territoryOpportunityWorkflow?.territory?.id}">区域</g:link></li>
                    <li class="active">
                        <span>新增区域工作流</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增区域工作流
            </h2>
        </div>
    </div>
</div>

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
                    <g:hasErrors bean="${this.territoryOpportunityWorkflow}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.territoryOpportunityWorkflow}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">工作流:</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="workflow.id" required="required" id="workflow"
                                      value="${this.territoryOpportunityWorkflow?.workflow?.id}"
                                      from="${com.next.OpportunityWorkflow.list()}" optionKey="id" optionValue="name"
                                      noSelection="${['null': '请选择']}"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">区域</label>

                        <div class="col-md-3">
                        <g:textField name="territory.id" required="required" id="territory" value="${this.territoryOpportunityWorkflow?.territory?.id}" class="hide" />
                            <g:textField class="form-control" name="territoryName" readonly="readonly"
                                         value="${this.territoryOpportunityWorkflow?.territory?.name}"></g:textField>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

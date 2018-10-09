<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'opportunityFlow.label', default: 'OpportunityFlow')}" />
    <title>订单工作流</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="opportunityFlow" action="index">订单工作流</g:link>
                        </li>
                        <li class="active">
                            <span>新增订单工作流</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    新增订单工作流
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增订单工作流
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.opportunityFlow}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.opportunityFlow}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">订单编号</label>
                            <div class="col-md-3">
                                <g:if test="${this.opportunityFlow?.opportunity != null && this.opportunityFlow?.opportunity != ''}">
                                    <g:textField name="opportunity.id" required="required" id="opportunity" value="${this.opportunityFlow?.opportunity?.id}" class="hide" />
                                    <g:textField name="opportunitySerialNumber" value="${this.opportunityFlow?.opportunity?.serialNumber}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select name="opportunity.id" class="form-control" required="required" id="opportunity" value="${this.opportunityFlow?.opportunity}" from="${com.next.Opportunity.list()}" optionKey="id" optionValue="serialNumber" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">订单阶段</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="stage.id" required="required" id="stage" value="${this.opportunityFlow?.stage}" from="${this.opportunityStages}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">工作流</label>
                            <div class="col-md-3">
                              <g:textField name="executionSequence"
                                           value="${this.opportunityFlow?.executionSequence}"
                                           class="form-control"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">布局</label>

                            <div class="col-md-3">
                                <g:select class="form-control m-b" name="opportunityLayout.id" id="opportunityLayout" from="${com.next.OpportunityLayout.findAllByActive(true)}"
                                          optionKey="id" optionValue="description" noSelection="[null: '---请选择---']"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton class="btn btn-info" name="update" value="保存" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

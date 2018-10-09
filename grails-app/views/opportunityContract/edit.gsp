<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityContract.label', default: 'OpportunityContract')}"/>
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
                        <g:link controller="opportunity" action="show"
                                id="${this.opportunityContract?.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>修改合同</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                修改合同
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                修改合同
            </div>

            <div class="panel-body">
                <g:form resource="${this.opportunityContract}" method="PUT" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${flash.message}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityContract}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityContract}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <input class="form-control" name="opportunity.id" type="hidden" id="opportunity"
                           value="${this.opportunityContract?.opportunity?.id}">

                    <div class="form-group">
                        <label class="col-md-3 control-label">合同编号</label>

                        <div class="col-md-3">
                            <g:textField name="contract.serialNumber"
                                         value="${this.opportunityContract?.contract?.serialNumber}"
                                         class="form-control"/>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">合同类型</label>

                        <div class="col-md-3">
                            <g:select name="contract.type.id" id="contractType"
                                      value="${this.opportunityContract?.contract?.type?.id}"
                                      from="${com.next.ContractType.list()}" optionKey="id"
                                      optionValue="name" class="form-control" disabled="disabled"/>
                        </div>
                    </div>
                    <g:each in="${this.contractItems}">
                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">${it?.name}</label>
                            <g:if test="${it?.options?.size() > 0}">
                              <div class="col-md-3">
                                  %{--<g:textField name="${it?.name}" value="${it?.value}" class="form-control disabled"/>--}%
                                  <g:select class="form-control" name="${it?.name}" value="${it?.value}" from="${it?.options}" noSelection="['': '-请选择-']" optionKey="value" optionValue="value"></g:select>
                              </div>
                            </g:if>
                            <g:else>
                              <div class="col-md-3">
                                  %{--<g:textField name="${it?.name}" value="${it?.value}" class="form-control disabled"/>--}%
                                  <g:textArea name="${it?.name}" rows="5" value="${it?.value}" class="form-control"></g:textArea>

                              </div>
                            </g:else>

                        </div>
                    </g:each>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="修改"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

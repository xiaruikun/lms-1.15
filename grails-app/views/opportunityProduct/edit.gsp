<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityProduct.label', default: 'opportunityProduct')}"/>
    <title>费用修改：${this.opportunityProduct?.productInterestType?.name}</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show" id="${this.opportunityProduct?.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>费用修改</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                费用
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <div class="hpanel hblue">
            <div class="panel-heading">
                费用修改
            </div>

            <div class="panel-body">
                <div class="form-horizontal">
                    <g:hasErrors bean="${this.opportunityProduct}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityProduct}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <g:form resource="${this.opportunityProduct}"  method="PUT" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-4 control-label">订单编号</label>
                            <div class="col-md-4">
                                <g:textField class="form-control" name="serialNumber" readOnly="true" value="${this.opportunityProduct?.opportunity?.serialNumber}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">费率类型</label>
                            <div class="col-md-4">
                                <g:textField class="form-control" name="name" readOnly="true" value="${this.opportunityProduct?.productInterestType?.name}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">费率</label>
                            <div class="col-md-4 input-group">
                                <input class="form-control" name="rate" value="<g:formatNumber number="${this.opportunityProduct?.rate}" maxFractionDigits="9"/>">
                                <span class="input-group-addon">%</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <g:if test="${this.opportunityProduct?.opportunity?.interestPaymentMethod?.name == "下扣息"}">
                            <div class="form-group">
                                <label class="col-md-4 control-label">下扣息月份数</label>
                                <div class="col-md-4 input-group">
                                    <g:select name="lastPayMonthes" from="${1}" id="lastPayMonthes" value="${this.opportunityProduct?.lastPayMonthes}" class="form-control"/>
                                    <span class="input-group-addon"> 月</span>
                                </div>
                            </div>
                        </g:if>
                        <g:else>
                            <div class="form-group">
                                <label class="col-md-4 control-label">上扣息月份数</label>
                                <div class="col-md-4 input-group">
                                    <g:select name="firstPayMonthes" from="${0..12}" id="firstPayMonthes" value="${this.opportunityProduct?.firstPayMonthes}" class="form-control"
                                              noSelection="['':'请选择上扣息月份数']"/>
                                    <span class="input-group-addon">月</span>
                                </div>
                            </div>
                        </g:else>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                           <!--  <label class="col-md-4 control-label">分期付款</label>
                           <div class="col-md-1">
                               <g:checkBox name="installments" value="${this.opportunityProduct?.installments}"
                                           class="form-control" style="height:16px;margin-top: 10px"/>
                           </div> -->
                            <label class="col-md-4 control-label">分期付款</label>
                            <div class="col-md-5 checkbox-inline">
                                <g:radioGroup class="i-checks" name="installments" value="${this.opportunityProduct?.installments}" labels="['按月收','一次性收']" values="[true,false]">
                                    ${it.radio} ${it.label}
                                </g:radioGroup>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">合同类型</label>

                            <div class="col-md-4">
                                %{--<g:textField class="form-control" name="contractType" readOnly="true" value="${this.opportunityProduct?.contractType?.name}"></g:textField>--}%
                                <g:select name="contractType" id="contractType" readonly="true" from="${com.next.ContractType.findById(this.opportunityProduct?.contractType?.id)}" optionKey="id" optionValue="name"
                                          value="${this.opportunityProduct?.contractType?.id}" class="form-control"/>
                            </div>
                        </div>
                        %{--<div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">基数息费类型</label>

                            <div class="col-md-4">
                                <!-- <g:select class="form-control m-b" name="radixProductInterestType" id="radixProductInterestType"
                                          value="${this.opportunityProduct?.radixProductInterestType?.id}" noSelection="['': '-请选择-']"
                                          from="${com.next.ProductInterestType.list()}"
                                          optionKey="id" optionValue="name"></g:select> -->
                                <select name="radixProductInterestType" value="${this.opportunityProduct?.radixProductInterestType?.id}"
                                        class="form-control">
                                    <g:each in="${com.next.ProductInterestType.list()}">
                                    <g:if test="${this.opportunityProduct?.radixProductInterestType?.id && it?.id == this.opportunityProduct?.radixProductInterestType?.id}">
                                        <option value="${it?.id}" selected="selected">${it?.name}</option>
                                    </g:if>
                                    <g:elseif test="${!(this.opportunityProduct?.radixProductInterestType?.id) && it?.name == '本金'}">
                                        <option value="${it?.id}" selected="selected">${it?.name}</option>
                                    </g:elseif>
                                    <g:else>
                                        <option value="${it?.id}">${it?.name}</option>
                                    </g:else>

                                    </g:each>

                                </select>
                            </div>
                        </div>--}%
                         <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-4 col-md-offset-4">
                                <g:submitButton class="btn btn-info" name="update" value="保存"/>
                            </div>
                        </div>
                    </g:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

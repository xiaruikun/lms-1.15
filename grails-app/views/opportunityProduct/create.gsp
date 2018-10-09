<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityProduct.label', default: 'opportunityProduct')}"/>
    <title>费用新增</title>

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
                                id="${this.opportunityProduct?.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>费用新增</span>
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
                费用新增
            </div>

            <div class="panel-body">
                <div class="form-horizontal">

                    <g:hasErrors bean="${this.opportunityProduct}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityProduct}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <g:form action="save" class="form-horizontal">
                        <input type="hidden" name="opportunity.id" value="${this.opportunityProduct?.opportunity?.id}">
                        <input type="hidden" name="product.id"
                               value="${this.opportunityProduct?.opportunity?.productAccount?.id}">
                        <g:textField name="createBy.id" value="${this.opportunityProduct?.createBy?.id}"
                                     class="hidden"/>
                        <g:textField name="modifyBy.id" value="${this.opportunityProduct?.createBy?.id}"
                                     class="hidden"/>
                        <div class="form-group">
                            <label class="col-md-4 control-label">费率类型</label>

                            <div class="col-md-4">
                                <g:select class="form-control m-b" name="productInterestType" id="productInterestType"
                                          value="${this.opportunityProduct?.productInterestType?.id}"
                                          from="${com.next.ProductInterestType.findAllByIsUsed(true)}" noSelection="['': '-请选择-']"
                                          optionKey="id" optionValue="name"></g:select>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">费率</label>

                            <div class="col-md-4 input-group">
                                <g:textField class="form-control" name="rate" id="rate"
                                             value="${this.opportunityProduct?.rate}"></g:textField>
                                <span class="input-group-addon">%</span>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">分期付款</label>
                            <div class="col-md-5 checkbox-inline">
                                <g:radioGroup class="i-checks" name="installments" value="${this.opportunityProduct?.installments}" labels="['按月收','一次性收']" values="[true,false]">
                                    ${it.radio}  ${it.label}
                                </g:radioGroup>
                            </div>
<!-- 
                            <div class="col-md-1">
                                <g:checkBox name="installments" value="${this.opportunityProduct?.installments}"
                                            class="form-control" id="installments"
                                            style="height:16px;margin-top: 10px"/>

                            </div> -->
                        </div>
                        %{--contractType--}%
                        <div class="hr-line-dashed"></div>
                        <g:if test="${this.opportunityProduct?.opportunity?.interestPaymentMethod?.name == "下扣息"}">
                            <div class="form-group">
                                <label class="col-md-4 control-label">下扣息月份数</label>

                                <div class="col-md-4 input-group">
                                    <g:select name="lastPayMonthes" from="${1}" id="lastPayMonthes"
                                              value="${this.opportunityProduct?.lastPayMonthes}" class="form-control"/>
                                    <span class="input-group-addon"> 月</span>

                                </div>
                            </div>
                        </g:if>
                        <g:else>
                            <div class="form-group">
                                <label class="col-md-4 control-label">上扣息月份数</label>

                                <div class="col-md-4 input-group">
                                    <g:select name="firstPayMonthes" from="${0..12}" id="firstPayMonthes"
                                              value="${this.opportunityProduct?.firstPayMonthes}" class="form-control"
                                              noSelection="['': '请选择上扣息月份数']"/>
                                    <span class="input-group-addon">月</span>

                                </div>
                            </div>
                        </g:else>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">合同类型</label>

                            <div class="col-md-4">
                                <g:textField id="contractType1" name="contractType1" class="form-control" value="借款合同"/>
                               %{-- <g:select name="contractType" id="contractType" from="${com.next.ContractType.list()}"
                                          optionKey="id" optionValue="name" value="${this.opportunityProduct?.contractType}" class="form-control"/>--}%
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
                                <select name="radixProductInterestType" id="radixProductInterestType" value="${this.opportunityProduct?.radixProductInterestType?.id}"
                                    class="form-control">
                                  <g:each in="${com.next.ProductInterestType.list()}">
                                      <g:if test="${it?.name == '本金'}">
                                          <option value="${it?.id}" selected="selected">${it?.name}</option>
                                      </g:if>
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
                                <g:submitButton class="btn btn-info" name="create" value="保存"/>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        /*对新息费规则的修改*/
        $("#productInterestType").click(function () {
            $("#contractType1").attr("readOnly",true);
            var rateType = $("#productInterestType").val();
            if(rateType==1||rateType==4||rateType==5||rateType==5){
                $("#contractType1").val("借款合同");
            }else if(rateType==2||rateType==3){
                $("#contractType1").val("委托借款代理服务合同");
            }else {
                $("#contractType1").val("");
            }
        });
        window.onload = function () {
            $("#contractType1").attr("readOnly",true);
            var rateType = $("#productInterestType").val();
            if(rateType==1||rateType==4||rateType==5||rateType==5){
                $("#contractType1").val("借款合同");
            }else if(rateType==2||rateType==3){
                $("#contractType1").val("委托借款代理服务合同");
            }else {
                $("#contractType1").val("");
            }
        }
        /*$("#installments").click(function(){
            if($(this)[0].checked) {
                $("#fixedRate")[0].checked = false;
            } else {
                $("#fixedRate")[0].checked = true;
            }

        })
        $("#fixedRate").click(function(){
            if($(this)[0].checked) {
                $("#installments")[0].checked = false;
            } else {
                $("#installments")[0].checked = true;
            }
        })*/

    })
</script>
</body>
</html>

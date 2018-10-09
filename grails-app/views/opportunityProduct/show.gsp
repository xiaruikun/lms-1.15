<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityProduct.label', default: 'OpportunityProduct')}" />
        <title>费用: ${this.opportunityProduct?.productInterestType?.name}</title>
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
                            <li>费用</li>
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
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" action="edit"
                                    resource="${this.opportunityProduct}"><i class="fa fa-edit"></i>编辑</g:link>
                        </div>
                        费用信息
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                            </g:if>
                            <div class="form-group">
                                <label class="col-md-4 control-label">订单编号：</label>
                                <div class="col-md-4">
                                    <span class="cont">${this.opportunityProduct?.opportunity?.serialNumber}</span>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-md-4 control-label">费率类型：</label>
                                <div class="col-md-4">
                                    <span class="cont">${this.opportunityProduct?.productInterestType?.name}</span>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-md-4 control-label">费率：</label>
                                <div class="col-md-4">
                                    <span class="cont"><g:formatNumber number="${this.opportunityProduct?.rate}" maxFractionDigits="9"/>%</span>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-md-4 control-label">分期付款：</label>
                                <div class="col-md-4">
                                    <span class="cont">${this.opportunityProduct?.installments}</span>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-md-4 control-label">上扣息月份数：</label>

                                <div class="col-md-4">
                                    <span class="cont">${this.opportunityProduct?.firstPayMonthes}月</span>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-md-4 control-label">合同类型</label>

                                <div class="col-md-4">
                                  <span class="cont">${this.opportunityProduct?.contractType?.id}</span>
                                </div>
                            </div>
                            %{--<div class="form-group">
                                <label class="col-md-4 control-label">基数息费类型</label>

                                <div class="col-md-4">
                                   <span class="cont">${this.opportunityProduct?.radixProductInterestType?.name}</span>
                                </div>
                            </div>--}%

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

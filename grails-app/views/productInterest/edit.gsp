<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'productInterest.label', default: 'ProductInterest')}"/>
    <title>编辑产品费率</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="product" action="index">产品管理</g:link></li>
                    <li class="active">
                        产品费率
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs" style="text-transform:none;">
                产品: ${this.productInterest?.product?.product?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                ${this.productInterest?.product?.product?.name}-产品费率
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.productInterest}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.productInterest}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form resource="${this.productInterest}" method="PUT" class="form-horizontal">

                    <div class="form-group">
                        <label class="col-md-3 control-label">最小费率</label>

                        <div class="col-md-3">
                            <input class="form-control" type="text" name="minimumRate" value="<g:formatNumber number="${this.productInterest?.minimumRate}" maxFractionDigits="4"/>">
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">最大费率</label>

                        <div class="col-md-3">
                            <input class="form-control" type="text" name="maximumRate"  value="<g:formatNumber number="${this.productInterest?.maximumRate}" maxFractionDigits="4"/>">
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">起始月份</label>

                        <div class="col-md-3">
                            <g:select name="monthesOfStart" from="${[1,2,3,4,5,6,7,8,9,10,11,12,24,36]}" value="${this.productInterest.monthesOfStart}" class="form-control"
                                      noSelection="['':'请选择起始月份']"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">结束月份</label>

                        <div class="col-md-3">
                            <g:select name="monthesOfEnd" from="${[1,2,3,4,5,6,7,8,9,10,11,12,24,36]}" value="${this.productInterest.monthesOfEnd}" class="form-control"
                                      noSelection="['':'请选择结束月份']"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">上扣息月份数</label>

                        <div class="col-md-3">
                            <g:select name="firstPayMonthes" from="${0..12}" id="firstPayMonthes" value="${this.productInterest?.firstPayMonthes}" class="form-control"
                                      noSelection="['':'请选择上扣息月份数']"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">客户级别</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="contactLevel" id="contactLevel"
                                      value="${this.productInterest?.contactLevel?.id}"
                                      from="${com.next.ContactLevel.list()}" noSelection="['': '-请选择-']"
                                      optionKey="id" optionValue="name"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">费率类型</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="productInterestType" id="productInterestType"
                                      value="${this.productInterest?.productInterestType?.id}"
                                      from="${com.next.ProductInterestType.list()}" noSelection="['': '-请选择-']"
                                      optionKey="id" optionValue="name"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">分期付款</label>

                        <div class="col-md-1">
                            <g:checkBox name="installments" value="${this.productInterest?.installments}"
                                        class="form-control" style="height:16px;margin-top: 10px"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">固定费率</label>

                        <div class="col-md-1">
                            <g:checkBox name="fixedRate" value="${this.productInterest?.fixedRate}" class="form-control"
                                        style="height:16px;margin-top: 10px"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-lg-offset-3">
                            <g:submitButton name="update" class="btn btn-info" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'productExtension.label', default: 'ProductExtension')}" />
        <title>修改产品展期</title>
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
                                产品展期
                            </li>
                        </ol>
                    </div>

                    <h2 class="font-light m-b-xs" style="text-transform:none;">
                        产品: ${this.productExtension?.product?.product?.name}
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hgreen">
                    <div class="panel-heading">
                            ${this.productExtension?.product?.product?.name}-产品展期
                    </div>
                    <div class="panel-body">
                        <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.productExtension}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.productExtension}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                        </g:hasErrors>
                        <g:form resource="${this.productExtension}" method="PUT" class="form-horizontal">
                            <input type="hidden" name="product" value="${this.productExtension?.product?.id}">
                            <div class="form-group">
                                <label class="col-md-3 control-label">月份数</label>
                                <div class="col-md-3">
                                    <g:select name="numberOfMonths" from="${0..12}" value="${this.productExtension?.numberOfMonths}" class="form-control"
                                              noSelection="['':'请选择月份数']"/>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">每月最大服务费率</label>
                                <div class="col-md-3">
                                    <g:textField class="form-control" type="text" name="maximumServiceChargePerMonth" id="maximumServiceChargePerMonth" value="${this.productExtension?.maximumServiceChargePerMonth}"/>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">总服务费率</label>
                                <div class="col-md-3">
                                    <g:textField class="form-control" type="text" name="totalServiceCharge" id="totalServiceCharge" value="${this.productExtension?.totalServiceCharge}"/>
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

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'productInterest.label', default: 'ProductInterest')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
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
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" action="edit" resource="${this.productInterest}" id="${it?.product?.id}">
                                <i class="fa fa-edit"></i>编辑
                            </g:link>
                        </div>
                        ${this.productInterest.product.product.name}-产品费率
                    </div>
                    <div class="panel-body">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="minimumRate" title="最小费率"/>
                                <g:sortableColumn property="maximumRate" title="最大费率"/>
                                <g:sortableColumn property="monthesOfStart" title="起始月份"/>
                                <g:sortableColumn property="monthesOfEnd" title="结束月份"/>
                                <g:sortableColumn property="contactLevelname" title="客户级别"/>
                                <g:sortableColumn property="InterestTypename" title="费率类型"/>
                                <g:sortableColumn property="fixedRate" title="固定费率"/>
                                <g:sortableColumn property="installments" title="分期付款"/>
                                <g:sortableColumn property="firstPayMonthes" title="上扣息月份数"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${productInterest}">
                                <div class="col-md-4">
                                    <tr>
                                        <td><g:formatNumber number="${it.minimumRate}" minFractionDigits="2"
                                                            maxFractionDigits="4"/></td>
                                        <td><g:formatNumber number="${it.maximumRate}" minFractionDigits="2"
                                                            maxFractionDigits="4"/></td>
                                        <td>${it.monthesOfStart}</td>
                                        <td>${it.monthesOfEnd}</td>
                                        <td>${it.contactLevel?.name}</td>
                                        <td>${it.productInterestType?.name}</td>
                                        <td>${it.fixedRate}</td>
                                        <td>${it.installments}</td>
                                        <td>${it.firstPayMonthes}</td>
                                    </tr>
                                </div>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

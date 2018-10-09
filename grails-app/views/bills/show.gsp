<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bills.label', default: 'Bills')}"/>
    <title>还款告知书</title>
    <style>
    .sw-12 {
        width: 12%;
    }

    .table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
        border-bottom-width: 0;
    }

    .v-align {
        vertical-align: middle !important;
    }
    </style>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <g:link controller="bills" action="index">还款计划</g:link>
                    </li>
                    <li class="active">
                        <span>还款告知书</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                还款告知书
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <g:if test="${flash.message}">
        <div class="alert alert-success alert-dismissible" role="alert">
            ${flash.message}
        </div>
    </g:if>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                %{-- <div class="panel-tools">
                       <g:link class="btn btn-info btn-xs" action="edit" resource="${this.bills}"><i class="fa fa-edit"></i>编辑</g:link>
                   </div>--}%
                还款告知书
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    %{--<table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <td width="16%"><strong>借款人</strong></td>
                            <td class="sw-12">${this.bills?.opportunity.fullName}</td>
                            <td class="sw-12"><strong>借款金额（元）</strong></td>
                            <td class="sw-12">${this.bills?.principal}</td>
                            <td class="sw-12"><strong>借款期限</strong></td>
                            <td class="sw-12">${this.bills?.opportunity?.loanDuration}</td>
                            <td class="sw-12"><strong>合同编号</strong></td>
                            <td class="sw-12">${this.bills?.opportunity.serialNumber}</td>
                        </tr>
                        <tr>
                            <td width="16%"><strong>借款日期</strong></td>
                            <td class="sw-12"><g:formatDate format="yyyy-MM-dd"
                                                            date="${this.bills?.opportunity.startTime}"/></td>
                            <td class="sw-12"><strong>还款日期</strong></td>
                            <td class="sw-12"><g:formatDate format="yyyy-MM-dd"
                                                            date="${this.bills?.opportunity.endTime}"/></td>
                            <td class="sw-12"><strong>已收意向金</strong></td>
                            <td class="sw-12">${this.bills?.prepaidAmount}</td>
                            <td class="sw-12"><strong>收息方式</strong></td>
                            <td class="sw-12">${this.bills?.opportunity.interestPaymentMethod}</td>
                        </tr>
                        <tr>
                            <td width="16%"><strong>每月还息费金额（元）</strong></td>
                            <td class="sw-12">${this.bills?.opportunity.monthlyInterest}</td>
                            <td class="sw-12"><strong>借款利率/月</strong></td>
                            <td class="sw-12">${this.bills?.opportunity.monthlyInterest}</td>
                            <td class="sw-12"><strong>借款服务费/月</strong></td>
                            <td class="sw-12">${this.bills?.serviceCharge}</td>
                            <td class="sw-12"><strong>渠道服务费/月</strong></td>
                            <td class="sw-12">${this.bills?.channelCharge}</td>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${billsList}">
                            <tr>
                                <td>
                                    <strong><g:link action="show" id="${it?.id}">${it?.period}</g:link></strong>
                                </td>
                                <td>${it?.bankAccount}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>--}%

                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th rowspan="2" width="16%" class="v-align">期数</th>
                            <th rowspan="2" class="v-align sw-12">还款日</th>
                            <th rowspan="2" colspan="4" width="22%" class="v-align" style="text-align: center;">每期还款时间</th>
                            <th colspan="2" width="24%" style="text-align: center;">计息费月/天</th>
                            <th rowspan="2" class="v-align">金额</th>
                        </tr>
                        <tr>
                            <th rowspan="2" class="v-align">月</th>
                            <th rowspan="2" class="v-align">天</th>
                        </tr>
                        </thead>
                        <tbody>
                        %{--<g:each in="${this.billsItems}">
                            <tr>
                                <td>第${it?.period}期</td>
                                <td class="sw-12"><g:formatDate format="yyyy-MM-dd" date="${it?.repaymentTime}"/></td>
                                <td>收</td>
                                <td class="sw-12"><g:formatDate format="yyyy-MM-dd" date="${it?.startTime}"/></td>
                                <td>至</td>
                                <td class="sw-12"><g:formatDate format="yyyy-MM-dd" date="${it?.endTime}"/></td>
                                <td>${it?.calculationByTheMonth}</td>
                                <td>${it?.calculationByTheDay}</td>
                                <td>${it?.totalRepayment}</td>

                            </tr>
                        </g:each>--}%
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                   %{-- <g:paginate total="${billsCount ?: 0}"/>--}%
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

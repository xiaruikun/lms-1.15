<!DOCTYPE html>
<html>

<head>
    %{--<meta name="layout" content="main"/>--}%
    <g:set var="entityName" value="${message(code: 'billsItem.label', default: 'BillsItem')}" />
    <title>还款告知书</title>
    <asset:stylesheet src="homer/vendor/fontawesome/css/font-awesome.min.css"/>
    <asset:stylesheet src="homer/vendor/bootstrap/dist/css/bootstrap.min.css"/>
    <asset:stylesheet src="homer/style.css"/>
    <style>
    .panel-body.active {
        border: none;
    }

    .table thead th, .table > tbody > tr > td {
        border: 1px solid #000 !important;
        color: #000;
        text-align: center;
        vertical-align: middle;
    }

    tr.bold td {
        font-weight: bold;
        font-size: 14px;
    }

    .panel-heading {
        text-align: center;
        font-size: 18px;
        color: #000 !important;
    }

    .nav-tabs li.active {
        font-weight: bold;
    }
    .nav button{
        margin-top: 6px;
    }
    </style>
</head>

<body>

<div class="content animate-panel">
    <div class="hpanel">
        <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#tab-1">中佳信客户还款告知书</a></li>
            <li class=""><a data-toggle="tab" href="#tab-2">还款告知书</a></li>
            <li class=""><a data-toggle="tab" href="#tab-3">服务费收取告知书</a></li>
            %{--<g:if test="${serviceTimes==2}">
                <li class=""><a data-toggle="tab" href="#tab-4">平台费收取告知书</a></li>
            </g:if>--}%
            <li><button id="printBtn" class="btn btn-sm btn-primary"><i class="fa fa-print"></i> 打印</button></li>
        </ul>
        %{--<button class="btn btn-primary m-l-sm pull-right" onclick="javascript:window.print();">打印</button>--}%

        <div class="tab-content">
            <div id="tab-1" class="tab-pane active">
                <div class="hpanel">
                    <div class="panel-heading">
                        中佳信客户还款告知书
                    </div>

                    <div class="panel-body no-padding active">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <tr>
                                    <td width="10%">借款人姓名</td>
                                    <td width="15%">${bills?.fullName}</td>
                                    <td width="15%" colspan="2">借款金额（万元）</td>
                                    <td width="10%">${bills?.actualAmountOfCredit}</td>
                                    <td width="15%">借款期限（月）</td>
                                    <td width="10%">${bills?.actualLoanDuration}</td>
                                    <td width="10%">合同编号</td>
                                    <td width="15%">${bills?.serialNumber}</td>
                                </tr>
                                <tr>
                                    <td width="10%">收息方式</td>
                                    <td width="15%">${bills?.interestPaymentMethod}</td>
                                    <td width="15%" colspan="2">借款利率/月</td>
                                    <td width="10%"><g:formatNumber number="${bills?.interestRate}" maxFractionDigits="4"></g:formatNumber>%</td>
                                    <td width="10%">借款日期</td>
                                    <td width="10%">${bills?.actualLendingDate}</td>
                                    <td width="10%">还本日期</td>
                                    <td width="15%">${bills?.endTime}</td>
                                </tr>
                                <tr class="bold">
                                    <td width="10%" rowspan="2">期数</td>
                                    <td width="15%" rowspan="2">还款日</td>
                                    <td width="40%" colspan="4" rowspan="2">每期还款期间</td>
                                    <td width="20%" colspan="2" rowspan="1">计息费月/天</td>
                                    <td width="15%" rowspan="2">金额（元）</td>
                                </tr>
                                <tr class="bold">
                                    <td width="10%" rowspan="1">月</td>
                                    <td width="10%" rowspan="1">天</td>
                                </tr>
                                <g:each in="${items}">
                                    <g:if test="${it?.money}">
                                        <tr>
                                            <td width="10%">第${it?.period}期</td>
                                            <td width="15%">${it?.repaymentDate}</td>
                                            <td width="5%">收</td>
                                            <td width="10%">${it?.startTime}</td>
                                            <td width="10%">至</td>
                                            <td width="15%">${it?.endTime}</td>
                                            <td width="10%">${it?.monthes}</td>
                                            <td width="10%">${it?.days}</td>
                                            <td width="15%"><g:formatNumber number="${it?.money}" maxFractionDigits="2"></g:formatNumber></td>
                                        </tr>
                                    </g:if>
                                </g:each>
                            </table>
                        </div>

                    </div>
                </div>
            </div>

            <div id="tab-2" class="tab-pane">
                <div class="hpanel">
                    <div class="panel-heading">
                        还款告知书
                    </div>

                    <div class="panel-body no-padding active">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <tr>
                                    <td width="10%">借款人姓名</td>
                                    <td width="15%">${bills?.fullName}</td>
                                    <td width="15%" colspan="2">借款金额（万元）</td>
                                    <td width="10%">${bills?.actualAmountOfCredit}</td>
                                    <td width="15%">收息方式</td>
                                    <td width="10%">${bills?.interestPaymentMethod}</td>
                                    <td width="10%">合同编号</td>
                                    <td width="15%">${bills?.serialNumber}</td>
                                </tr>
                                <tr>
                                    <td width="10%">借款期限（月）</td>
                                    <td width="15%">${bills?.actualLoanDuration}</td>
                                    <td width="15%" colspan="2">借款日期</td>
                                    <td width="10%">${bills?.actualLendingDate}</td>
                                    <td width="10%">还款日期</td>
                                    <td width="10%">${bills?.endTime}</td>
                                    <g:if test="${serviceTimes==2}">
                                        <td width="10%">平台费</td>
                                        <td width="15%"><g:formatNumber number="${bills?.platformCharge}" maxFractionDigits="2"></g:formatNumber>%</td>
                                    </g:if>
                                    <g:else>
                                        <td width="10%">已收意向金（元）</td>
                                        <td width="15%">${bills?.advancePayment}</td>
                                    </g:else>
                                </tr>
                                <tr>
                                    <td width="10%">借款利率/月</td>
                                    <td width="10%"><g:formatNumber number="${bills?.interestRate}" maxFractionDigits="4"></g:formatNumber>%</td>
                                    <td width="25%" colspan="2">借款服务费一/月</td>
                                    <td width="20%" colspan="2"><g:formatNumber number="${bills?.serviceChargeOne}" maxFractionDigits="4"></g:formatNumber>%</td>
                                    <td width="10%">借款服务费二/月</td>
                                    <td width="25%" colspan="2"><g:formatNumber number="${bills?.serviceChargeTwo}" maxFractionDigits="4"></g:formatNumber>%</td>
                                </tr>
                                <tr class="bold">
                                    <td width="10%" rowspan="2">期数</td>
                                    <td width="15%" rowspan="2">还款日</td>
                                    <td width="40%" colspan="4" rowspan="2">每期还款期间</td>
                                    <td width="20%" colspan="2" rowspan="1">计息费月/天</td>
                                    <td width="15%" rowspan="2">金额（元）</td>
                                </tr>
                                <tr class="bold">
                                    <td width="10%" rowspan="1">月</td>
                                    <td width="10%" rowspan="1">天</td>
                                </tr>
                                <g:each in="${itemsAll}">
                                    <g:if test="${it?.period == 0&&!it?.installmentsService}">
                                        <tr>
                                            <td width="10%">第${it?.period}期</td>
                                            <td width="15%">${it?.repaymentDate1}</td>
                                            <td width="5%">收</td>
                                            <td width="10%">${it?.startTime1}</td>
                                            <td width="10%">至</td>
                                            <td width="15%">${it?.endTime1}</td>
                                            <td width="10%">${it?.monthes1}</td>
                                            <td width="10%">${it?.days1}</td>
                                            <td width="15%"><g:formatNumber number="${it?.serviceMoney}" maxFractionDigits="2"></g:formatNumber></td>
                                        </tr>
                                        <tr>
                                            <td width="10%">第${it?.period}期</td>
                                            <td width="15%">${it?.repaymentDate}</td>
                                            <td width="5%">收</td>
                                            <td width="10%">${it?.startTime}</td>
                                            <td width="10%">至</td>
                                            <td width="15%">${it?.endTime}</td>
                                            <td width="10%">${it?.monthes}</td>
                                            <td width="10%">${it?.days}</td>
                                            <td width="15%"><g:formatNumber number="${it?.money1}" maxFractionDigits="2"></g:formatNumber></td>
                                        </tr>
                                    </g:if>
                                    <g:if test="${it?.period != 0||it?.installmentsService}">
                                        <g:if test="${it?.money}">
                                            <tr>
                                                <td width="10%">第${it?.period}期</td>
                                                <td width="15%">${it?.repaymentDate}</td>
                                                <td width="5%">收</td>
                                                <td width="10%">${it?.startTime}</td>
                                                <td width="10%">至</td>
                                                <td width="15%">${it?.endTime}</td>
                                                <td width="10%">${it?.monthes}</td>
                                                <td width="10%">${it?.days}</td>
                                                <td width="15%"><g:formatNumber number="${it?.money}" maxFractionDigits="2"></g:formatNumber></td>
                                            </tr>
                                        </g:if>
                                    </g:if>
                                </g:each>
                            </table>
                        </div>

                    </div>
                </div>
            </div>

            <div id="tab-3" class="tab-pane">
                <div class="hpanel">
                    <div class="panel-heading">
                        服务费收取告知书
                    </div>

                    <div class="panel-body no-padding active">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <tr>
                                    <td width="10%">借款人姓名</td>
                                    <td width="15%">${bills?.fullName}</td>
                                    <td width="15%" colspan="2">借款金额（万元）</td>
                                    <td width="10%">${bills?.actualAmountOfCredit}</td>
                                    <td width="15%">收息方式</td>
                                    <td width="10%">${bills?.interestPaymentMethod}</td>
                                    <td width="10%">合同编号</td>
                                    <td width="15%">${bills?.serialNumber}</td>
                                </tr>
                                <tr>
                                    <td width="10%">已收意向金</td>
                                    <td width="15%">${bills?.advancePayment}</td>
                                    <td width="15%" colspan="2">借款服务费一/月</td>
                                    <td width="10%"><g:formatNumber number="${bills?.serviceChargeOne}" maxFractionDigits="4"></g:formatNumber>%</td>
                                    <td width="10%">借款服务费二/月</td>
                                    <td width="10%"><g:formatNumber number="${bills?.serviceChargeTwo}" maxFractionDigits="4"></g:formatNumber>%</td>
                                    <td width="10%">还款日期</td>
                                    <td width="15%">${bills?.endTime}</td>
                                </tr>
                                <tr class="bold">
                                    <td width="10%" rowspan="2">期数</td>
                                    <td width="15%" rowspan="2">还款日</td>
                                    <td width="40%" colspan="4" rowspan="2">每期还款期间</td>
                                    <td width="20%" colspan="2" rowspan="1">计息费月/天</td>
                                    <td width="15%" rowspan="2">金额（元）</td>
                                </tr>
                                <tr class="bold">
                                    <td width="10%" rowspan="1">月</td>
                                    <td width="10%" rowspan="1">天</td>
                                </tr>
                                <g:each in="${itemsService}">
                                    <g:if test="${it?.period == 0&&!it?.installmentsService}">
                                        <g:if test="${it?.serviceMoney}">
                                            <tr>
                                                <td width="10%">第${it?.period}期</td>
                                                <td width="15%">${it?.repaymentDate}</td>
                                                <td width="5%">收</td>
                                                <td width="10%">${it?.startTime1}</td>
                                                <td width="10%">至</td>
                                                <td width="15%">${it?.endTime1}</td>
                                                <td width="10%">${it?.monthes1}</td>
                                                <td width="10%">${it?.days1}</td>
                                                <td width="15%"><g:formatNumber number="${it?.serviceMoney}" maxFractionDigits="2"></g:formatNumber></td>
                                            </tr>
                                        </g:if>
                                        <g:if test="${it?.money1}">
                                            <tr>
                                                <td width="10%">第${it?.period}期</td>
                                                <td width="15%">${it?.repaymentDate}</td>
                                                <td width="5%">收</td>
                                                <td width="10%">${it?.startTime}</td>
                                                <td width="10%">至</td>
                                                <td width="15%">${it?.endTime}</td>
                                                <td width="10%">${it?.monthes}</td>
                                                <td width="10%">${it?.days}</td>
                                                <td width="15%"><g:formatNumber number="${it?.money1}" maxFractionDigits="2"></g:formatNumber></td>
                                            </tr>
                                        </g:if>
                                    </g:if>
                                    <g:if test="${it?.period != 0||it?.installmentsService}">
                                        <tr>
                                            <td width="10%">第${it?.period}期</td>
                                            <td width="15%">${it?.repaymentDate}</td>
                                            <td width="5%">收</td>
                                            <td width="10%">${it?.startTime}</td>
                                            <td width="10%">至</td>
                                            <td width="15%">${it?.endTime}</td>
                                            <td width="10%">${it?.monthes}</td>
                                            <td width="10%">${it?.days}</td>
                                            <td width="15%"><g:formatNumber number="${it?.money}" maxFractionDigits="2"></g:formatNumber></td>
                                        </tr>
                                    </g:if>
                                </g:each>
                            </table>
                        </div>

                    </div>
                </div>
            </div>

            <div id="tab-4" class="tab-pane">
                <div class="hpanel">
                    <div class="panel-heading">
                        平台费收取告知书
                    </div>

                    <div class="panel-body no-padding active">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <tr>
                                    <td width="10%">借款人姓名</td>
                                    <td width="15%">${bills?.fullName}</td>
                                    <td width="15%" colspan="2">借款金额（万元）</td>
                                    <td width="10%">${bills?.actualAmountOfCredit}</td>
                                    <td width="15%">收息方式</td>
                                    <td width="10%">${bills?.interestPaymentMethod}</td>
                                    <td width="10%">合同编号</td>
                                    <td width="15%">${bills?.serialNumber}</td>
                                </tr>
                                <tr>
                                    <td width="10%">借款期限（月）</td>
                                    <td width="15%">${bills?.actualLoanDuration}</td>
                                    <td width="25%" colspan="3">平台费/月</td>
                                    <td width="20%" colspan="2">${bills?.platformCharge}%</td>
                                    <td width="10%">还款日期</td>
                                    <td width="15%">${bills?.endTime}</td>
                                </tr>
                                <tr class="bold">
                                    <td width="10%" rowspan="2">期数</td>
                                    <td width="15%" rowspan="2">还款日</td>
                                    <td width="40%" colspan="4" rowspan="2">每期还款期间</td>
                                    <td width="20%" colspan="2" rowspan="1">计息费月/天</td>
                                    <td width="15%" rowspan="2">金额（元）</td>
                                </tr>
                                <tr class="bold">
                                    <td width="10%" rowspan="1">月</td>
                                    <td width="10%" rowspan="1">天</td>
                                </tr>
                                <g:each in="${itemsPlat}">
                                    <g:if test="${it?.money}">
                                        <tr>
                                            <td width="10%">第${it?.period}期</td>
                                            <td width="15%">${it?.repaymentDate}</td>
                                            <td width="5%">收</td>
                                            <td width="10%">${it?.startTime}</td>
                                            <td width="10%">至</td>
                                            <td width="15%">${it?.endTime}</td>
                                            <td width="10%">${it?.monthes}</td>
                                            <td width="10%">${it?.days}</td>
                                            <td width="15%"><g:formatNumber number="${it?.money}" maxFractionDigits="2"></g:formatNumber></td>
                                        </tr>
                                    </g:if>
                                </g:each>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<asset:javascript src="homer/vendor/jquery/dist/jquery.min.js"/>
<asset:javascript src="homer/vendor/bootstrap/dist/js/bootstrap.min.js"/>
<script>
    $(function(){
        $("#printBtn").click(function(){
            $(this).closest("ul.nav").addClass("hide");
            javascript:window.print();
            $(this).closest("ul.nav").removeClass("hide");
        })
    })
</script>

</body>

</html>

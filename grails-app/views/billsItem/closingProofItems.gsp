<!DOCTYPE html>
<html>
<head>
    <g:set var="entityName" value="${message(code: 'billsItem.label', default: 'BillsItem')}" />
    <title>结清记录</title>
    <asset:stylesheet src="homer/vendor/fontawesome/css/font-awesome.min.css"/>
    <asset:stylesheet src="homer/vendor/bootstrap/dist/css/bootstrap.min.css"/>
    <asset:stylesheet src="homer/style.css"/>
    <style>
    body{
        color:#333;
    }
    .underline{
        text-decoration: underline;
    }
    .tab-content{
        line-height: 34px;
        font-size: 18px;
    }
    .footer-left{
        margin-right: 200px;
    }
    .tab-content p{
        text-indent:20px;
    }
    </style>
</head>
<body>
<div class="content animate-panel">
    <div class="hpanle-container">
        <div class="hpanel">
            <ul class="nav nav-tabs">
                <li><button id="printBtn" class="btn btn-sm btn-primary"><i class="fa fa-print"></i> 打印</button></li>
            </ul>
            <div class="tab-content">
                <h2 class="text-center">中佳信-（<span class="font-uppercase">${map?.city}</span>）客户结清记录</h2>
                <p class="m-t-lg">
                    借款人
                    <span class="underline">${map?.fullName}</span>于
                    <span class="underline">${map?.actualLendingDate}</span>向我司申请贷款人民币<span class="underline">${map?.principal}</span>整，
                借款期限<span class="underline">${map?.actualLoanDuration}个月</span>，合同编号：<span class="underline">${map?.serialNumber}</span> ，
                应收和实际还款情况如下表：
                </p>
                <div class="panel-body no-padding active">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <tr class="bold">
                                <td width="8%" rowspan="2">项目</td>
                                <td width="8%" rowspan="2">计算基数（元）</td>
                                <td width="8%" rowspan="2">计算起始日</td>
                                <td width="8%" rowspan="2">计算结束日</td>
                                <td width="8%" colspan="2" rowspan="1">计算时间</td>
                                <td width="8%" colspan="2" rowspan="2">息费率</td>
                                <td width="8%" rowspan="2">减免金额（元）</td>
                                <td width="8%" rowspan="2">应收金额（元）</td>
                            </tr>
                            <tr class="bold">
                                <td width="4%" rowspan="1">月</td>
                                <td width="4%" rowspan="1">天</td>
                            </tr>
                            <g:each in="${receivableList}">
                                <tr>
                                    <td width="8%">${it?.program}</td>
                                    <td width="8%">${it?.radix}</td>
                                    <td width="8%">${it?.startTime}</td>
                                    <td width="8%">${it?.endTime}</td>
                                    <td width="4%">${it?.monthes}</td>
                                    <td width="4%">${it?.days}</td>
                                    <g:if test="${it?.rate}">
                                        <td width="4%"><g:formatNumber number="${it?.rate}" maxFractionDigits="2"/>%</td>
                                    </g:if>
                                    <g:else>
                                        <td width="4%">${it?.rate}%</td>
                                    </g:else>
                                    <td width="4%">${it?.unit}</td>
                                    <td width="8%">${it?.fee}</td>
                                    <td width="8%">${it?.receivable}</td>
                                </tr>
                            </g:each>
                            <tr class="bold">
                                <td width="10%" colspan="9">合计</td>
                                <td width="10%" colspan="1">${map?.receivable}</td>
                            </tr>
                        </table>
                    </div>

                </div>

                <div class="panel-body no-padding m-t-md active">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <tr class="bold">
                                <td width="8%">交款人</td>
                                <td width="8%">收款日期</td>
                                <td width="8%">本金</td>
                                <td width="8%">意向金</td>
                                <td width="8%">利息</td>
                                <td width="8%">服务费</td>
                                <td width="8%">渠道返费</td>
                                <td width="8%">违约金</td>
                                <td width="8%">减免金额</td>
                                <td width="8%">实收小计（元）</td>
                            </tr>
                            <g:each in="${receiptsList}">
                                <tr>
                                    <td width="8%">${it?.fullName}</td>
                                    <td width="8%">${it?.repaymentDate}</td>
                                    <td width="8%">${it?.principal}</td>
                                    <td width="8%">${it?.advance}</td>
                                    <td width="8%">${it?.interest}</td>
                                    <td width="8%">${it?.serviceCharge}</td>
                                    <td width="8%">${it?.channelCharge}</td>
                                    <td width="8%">${it?.penalty}</td>
                                    <td width="8%">${it?.derate}</td>
                                    <td width="8%">${it?.receipts}</td>
                                </tr>
                            </g:each>
                            <tr class="bold">
                                <td width="10%" colspan="9">合计</td>
                                <td width="10%" colspan="1">${map?.receipts}</td>
                            </tr>
                            <tr class="bold">
                                <td width="10%" colspan="9">（${map?.channel}）实收-应收</td>
                                <td width="10%" colspan="1">${map?.receipts-map?.receivable}</td>
                            </tr>
                        </table>
                    </div>
                </div>

                <p class="m-t-lg">
                    现借款人已就该笔贷款于<span class="underline">${map?.actuaRepaymentDate}</span>向我司办理了结清手续，财务部查实该笔贷款本金、应付息费已到我公司指定账户。
                </p>
                <p class="m-t-lg">
                    区域权证部可就该笔业务抵押房产办理解除抵押手续。
                </p>
                <h3 class="m-t-xxxl">
                    <span class="footer-left">制表人：</span>
                    <span class="">审核人：</span>
                    <span class="footer-right">财务部负责人：</span>
                </h3>
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

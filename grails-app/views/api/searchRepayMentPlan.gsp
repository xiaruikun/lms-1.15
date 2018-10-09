<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityTeam.label', default: 'OpportunityTeam')}"/>
    <title>中航还款计划</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>中航还款计划</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                中航还款计划
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <g:if test="${flash.message}">
        <div class="row">
            <div class="hpanel">
                <div class="panel-body">
                    <div class="alert alert-info" role="alert">${flash.message}</div>
                </div>
            </div>
        </div>
    </g:if>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                </div>
                还款计划:${mentPlan["loanNo"]}
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="phaseNum" title="期数"></g:sortableColumn>
                            <g:sortableColumn property="startDate" title="开始日期"></g:sortableColumn>
                            <g:sortableColumn property="startRateDate" title="起息日期"></g:sortableColumn>
                            <g:sortableColumn property="endDate" title="到期日期"></g:sortableColumn>
                            <g:sortableColumn property="phaseAmount" title="期供金额"></g:sortableColumn>
                            %{-- <g:sortableColumn property="level" title="客户级别"></g:sortableColumn> --}%
                            <g:sortableColumn property="principal" title="本金"></g:sortableColumn>
                            <g:sortableColumn property="normalInterest" title="正常利息"></g:sortableColumn>
                            <g:sortableColumn property="remainPrincipal" title="剩余本金"></g:sortableColumn>
                            <g:sortableColumn property="payment" title="逾期罚息"></g:sortableColumn>
                            <g:sortableColumn property="compoundInterest" title="复利"></g:sortableColumn>
                            <g:sortableColumn property="outRateDate" title="截息日期"></g:sortableColumn>
                            <g:sortableColumn property="normalAccInterest" title="正常利息积数"></g:sortableColumn>
                            <g:sortableColumn property="trunNormInterest" title="截计正常利息"></g:sortableColumn>
                            <g:sortableColumn property="accPayment" title="逾期罚息积数"></g:sortableColumn>
                            <g:sortableColumn property="planPayment" title="截计逾期罚息"></g:sortableColumn>
                            %{-- <g:sortableColumn property="opportunityFlexField" title="放款渠道"></g:sortableColumn>
                            <g:sortableColumn property="opportunityFlexField" title="放款账号"></g:sortableColumn> --}%
                            <g:sortableColumn property="normCompAccInterest" title="正常利息复利积数"></g:sortableColumn>
                            <g:sortableColumn property="normInterestCompSectMeter" title="截计正常利息复利"></g:sortableColumn>
                            <g:sortableColumn property="paymentAccCompInterest" title="逾期罚息复利积数"></g:sortableColumn>
                            <g:sortableColumn property="cutMeterPaymentCompInterest" title="截计逾期罚息复利"></g:sortableColumn>
                            <g:sortableColumn property="compInterestAccCompInterest" title="复利的复利积数"></g:sortableColumn>
                            <g:sortableColumn property="compCompInterest" title="截计复利的复利"></g:sortableColumn>
                            <g:sortableColumn property="recePrincipal" title="已收本金"></g:sortableColumn>
                            <g:sortableColumn property="normInterestRece" title="已收正常利息"></g:sortableColumn>
                            <g:sortableColumn property="RecePayment" title="已收逾期罚息"></g:sortableColumn>
                            <g:sortableColumn property="receCompInterest" title="已收复利"></g:sortableColumn>
                            <g:sortableColumn property="minusPrincipal" title="减免本金"></g:sortableColumn>
                            <g:sortableColumn property="breakNormInterest" title="减免正常利息"></g:sortableColumn>
                            <g:sortableColumn property="creditpayment" title="减免逾期罚息"></g:sortableColumn>
                            <g:sortableColumn property="reduCompInterest" title="减免复利"></g:sortableColumn>
                            <g:sortableColumn property="needDelayPayment" title="应还滞纳金"></g:sortableColumn>
                            <g:sortableColumn property="alsoDelayPayment" title="已还滞纳金"></g:sortableColumn>
                            <g:sortableColumn property="reduDelayPayment" title="减免滞纳金"></g:sortableColumn>
                            <g:sortableColumn property="loanExecuteRate" title="贷款执行利率"></g:sortableColumn>
                            <g:sortableColumn property="paymentExecuteRate" title="罚息执行利率"></g:sortableColumn>
                            <g:sortableColumn property="Commission" title="本期应还手续费"></g:sortableColumn>
                            <g:sortableColumn property="otherSum" title="本期应还其他应收"></g:sortableColumn>
                            <g:sortableColumn property="Status" title="状态"></g:sortableColumn>
                            <g:sortableColumn property="isPayOff" title="是否结清"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${mentPlan["list"]}">
                            <tr>
                                <td>${it?.phaseNum}</td>
                                <td>${it?.startDate}</td>
                                <td>${it?.startRateDate}</td>
                                <td>${it?.endDate}</td>
                                <td>${it?.phaseAmount}</td>
                                %{-- <td>${it?.lender?.level?.description}</td> --}%
                                <td>${it?.principal}</td>
                                <td>${it?.normalInterest}</td>
                                <td>${it?.remainPrincipal}</td>
                                <td>${it?.payment}</td>
                                <td>${it?.compoundInterest}</td>
                                <td>${it?.outRateDate}</td>
                                <td>${it?.normalAccInterest}</td>
                                <td>${it?.trunNormInterest}</td>
                                <td>${it?.accPayment}</td>
                                <td>${it?.planPayment}</td>
                                %{-- <td>${com.next.OpportunityFlexField.findByCategoryAndName(com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it, com.next.FlexFieldCategory.findByName("资金渠道")), "放款通道")?.value}</td> --}%
                                %{-- <td>${com.next.OpportunityFlexField.findByCategoryAndName(com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it, com.next.FlexFieldCategory.findByName("资金渠道")), "放款账号")?.value}</td> --}%
                                <td>${it?.normCompAccInterest}</td>
                                <td>${it?.normInterestCompSectMeter}</td>
                                <td>${it?.paymentAccCompInterest}</td>
                                <td>${it?.cutMeterPaymentCompInterest}</td>
                                <td>${it?.compInterestAccCompInterest}</td>
                                <td>${it?.compCompInterest}</td>
                                <td>${it?.recePrincipal}</td>
                                <td>${it?.normInterestRece}</td>
                                <td>${it?.RecePayment}</td>
                                <td>${it?.receCompInterest}</td>
                                <td>${it?.minusPrincipal}</td>
                                <td>${it?.breakNormInterest}</td>
                                <td>${it?.creditpayment}</td>
                                <td>${it?.reduCompInterest}</td>
                                <td>${it?.needDelayPayment}</td>
                                <td>${it?.alsoDelayPayment}</td>
                                <td>${it?.reduDelayPayment}</td>
                                <td>${it?.loanExecuteRate}</td>
                                <td>${it?.paymentExecuteRate}</td>
                                <td>${it?.Commission}</td>
                                <td>${it?.otherSum}</td>
                                <td>${it?.Status}</td>
                                <td>${it?.isPayOff}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${opportunityTeamCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>

</div>
<g:javascript>
    // $(function () {

    //     function DateDifference(StartDate,EndDate)
    //     {

    //        var myStartDate = new Date(StartDate);
    //        var myEndDate = new Date(EndDate);

    //        // 天數，86400000是24*60*60*1000，除以86400000就是有幾天
    //          return (myEndDate - myStartDate)/86400000;


    //     }
    //     // $("#submitBtn").click(function () {
    //         // var startTime = $("#startTime").val();
    //         // var endTime = $("#endTime").val();

    //         // var timeDiff = DateDifference(startTime,endTime)
    //         // if(startTime ==null || startTime.trim() == ""){
    //         //     $("#alert").text("请输入开始时间").removeClass("hide");
    //         //     return;
    //         // }
    //         // if(endTime ==null || endTime.trim() == ""){
    //         //     $("#alert").removeClass("hide").text("请输入结束时间");
    //         //     return;
    //         // }
    //         //  if (timeDiff < 0) {
    //         //     $("#alert").removeClass("hide").text("开始时间不能大于结束时间");
    //         //     return;
    //         // }
    //         //  if (timeDiff > 30) {
    //         //     $("#alert").removeClass("hide").text("时间区间不能大于30天");
    //         //     return;
    //         // }
    //         // $(".searchOpportunityForm").submit();
    //     // })



    // })
    $("#resetBtn").click(function () {
        $("#serialNumber").val("");
        $("#contact").val("");
        $("#fullName").val("");
        $("#user").val("");
        $("#city").val("");
        // $("#flexField").val("");
        $("#mortgageCertificateType").val("");
        $("#startTime").val("");
        $("#endTime").val("");
        // $("#estimatedTime").val("");
        // $("#contactLevel").val("");
        $("#actualAmountOfCreditMin").val("");
        $("#actualAmountOfCreditMax").val("");
        // $("#approvalStartTime").val("");
        // $("#approvalEndTime").val("");
    })
</g:javascript>
</body>
</html>

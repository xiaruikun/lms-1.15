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
                还款计划:${theAmount["loanNo"]}
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="needPrincipal" title="应归还本金"></g:sortableColumn>
                            <g:sortableColumn property="needInterest" title="应归还利息"></g:sortableColumn>
                            <g:sortableColumn property="needPayment" title="应归还罚息"></g:sortableColumn>
                            <g:sortableColumn property="needCompInterest" title="应归还复利"></g:sortableColumn>
                            <g:sortableColumn property="needCost" title="应还费用"></g:sortableColumn>
                            <g:sortableColumn property="totalAmt" title="还款总金额"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${theAmount?.needPrincipal}</td>
                                <td>${theAmount?.needInterest}</td>
                                <td>${theAmount?.needPayment}</td>
                                <td>${theAmount?.needCompInterest}</td>
                                <td>${theAmount?.needCost}</td>
                                <td>${theAmount?.totalAmt}</td>
                            </tr>
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

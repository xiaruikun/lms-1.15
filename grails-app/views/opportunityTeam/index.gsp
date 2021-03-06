<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityTeam.label', default: 'OpportunityTeam')}"/>
    <title>订单列表</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>订单管理</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                订单管理
            </h2>
            <small><span class="glyphicon glyphicon-th-large" aria-hidden="true"></span> ${stage?.name} ${status}
            </small>

        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="message alert alert-info hide" role="status" id="alert">

        </div>

        <g:form method="POST" action="searchOpportunity">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>
                <input type="text" id="stage" name="stage" value="${this.stage?.code}" class="hide">
                <input type="text" id="status" name="status" value="${status}" class="hide">

                <div class="panel-body seach-group">
                    <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="开始时间" type="text" name="startTime" id="startTime" value="${params?.startTime}" readonly
                                   class="form-control daily-b" placeholder="开始时间">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="结束时间" type="text" name="endTime" id="endTime" placeholder="结束时间"
                                   value="${params?.endTime}" readonly class="form-control daily-b">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="订单编号" id="serialNumber" name="serialNumber"
                               value="${params?.serialNumber}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="借款人姓名" id="fullName" name="fullName"
                               value="${params?.fullName}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="经纪人姓名" id="contact" name="contact"
                               value="${params?.contact}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="支持经理姓名" id="user" name="user"
                               value="${params?.user}">
                    </div>

                    <div class="col-md-3">
                        <g:select class="form-control" name="city" id="city"
                                  from="${com.next.City.list()}"
                                  valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.city}"
                                  noSelection="${['null': '请选择城市']}"/>
                    </div>

                    %{-- <div class="col-md-3">
                        <g:select class="form-control" name="flexField" id="flexField"
                                  from="${com.next.FlexFieldValue.findAllByField(com.next.FlexField.findByNameAndCategory("放款通道", com.next.FlexFieldCategory.findByName("资金渠道")))}"
                                  valueMessagePrefix="stage" optionKey="value" optionValue="value" value="${params?.flexField}"
                                  noSelection="${['null': '请选择放款渠道']}"/>
                    </div>

                    <div class="col-md-3">
                        <g:select class="form-control" name="flexFieldBankAccount" id="flexFieldBankAccount"
                                  from="${com.next.FlexFieldValue.findAll("from FlexFieldValue where field.id = ? order by displayOrder asc",[com.next.FlexField.findByNameAndCategory('放款账号', com.next.FlexFieldCategory.findByName('资金渠道'))?.id])}"
                                  valueMessagePrefix="stage" optionKey="value" optionValue="value" value="${params?.flexFieldBankAccount}"
                                  noSelection="${['null': '请选择放款账号']}"/>
                    </div> --}%

                    <div class="col-md-3">
                        <g:select class="form-control" name="mortgageCertificateType" id="mortgageCertificateType"
                                  from="${com.next.MortgageCertificateType.list()}"
                                  valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.mortgageCertificateType}"
                                  noSelection="${['null': '请选择抵押凭证类型']}"/>
                    </div>

                    %{-- <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="放款账号发放时间依据" type="text" name="estimatedTime" id="estimatedTime" placeholder="放款账号发放时间依据"
                                   value="${params?.estimatedTime}" readonly class="form-control daily-b">
                        </div>
                    </div> --}%

                    %{-- <div class="col-md-3">
                        <g:select class="form-control" name="contactLevel" id="contactLevel"
                                  from="${com.next.ContactLevel.list()}"
                                  valueMessagePrefix="stage" optionKey="name" optionValue="description" value="${params?.contactLevel}"
                                  noSelection="${['null': '请选择客户级别']}"/>
                    </div> --}%

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="审核可贷最小值（万元）" id="actualAmountOfCreditMin" name="actualAmountOfCreditMin"
                               value="${params?.actualAmountOfCreditMin}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="审核可贷最大值（万元）" id="actualAmountOfCreditMax" name="actualAmountOfCreditMax"
                               value="${params?.actualAmountOfCreditMax}">
                    </div>

                    %{-- <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="审批开始时间" type="text" name="approvalStartTime" id="approvalStartTime" placeholder="审批开始时间"
                                   value="${params?.approvalStartTime}" readonly class="form-control daily-b">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="审批结束时间" type="text" name="approvalEndTime" id="approvalEndTime" placeholder="审批结束时间"
                                   value="${params?.approvalEndTime}" readonly class="form-control daily-b">
                        </div>
                    </div> --}%

                    %{-- <div class="col-md-3">
                      <g:select class="form-control" name="complianceChecking" id="complianceChecking"
                                from="${[true, false]}"
                                valueMessagePrefix="complianceChecking" value="${params?.complianceChecking}"
                                noSelection="${['': '请选择合规审查']}"/>
                    </div> --}%
                    <div class="col-md-3">
                      <g:select class="form-control" name="currentStage" id="currentStage"
                                from="${com.next.OpportunityStage.findAllByActive(true)}"
                                valueMessagePrefix="currentStage" value="${params?.currentStage}" optionKey="id" optionValue="name"
                                noSelection="${['': '请选择订单阶段']}"/>
                    </div>
                    <div class="col-md-3">
                      <g:select class="form-control" name="type" id="type"
                                from="${com.next.OpportunityType.list()}"
                                valueMessagePrefix="type" value="${this.type}" optionKey="code" optionValue="name"
                                noSelection="${['': '请选择订单类型']}"/>
                    </div>
                    <div class="col-md-3">
                        <g:select class="form-control" name="numberOfAccount" id="numberOfAccount"
                                  from="${com.next.ManagerAccountNumber.list()}"
                                  valueMessagePrefix="type" value="${this.numberOfAccount}" optionKey="name" optionValue="name"
                                  noSelection="${['null': '请选择资方放款账号']}"/>
                    </div>
                    <div class="col-sm-3">
                        <g:select class="form-control" name="rongShuStatus" id="rongShuStatus"
                                  value="${this.rongShuStatus}"
                                  from="${["请选择资方审核状态","未审核","通过","未通过"]}"/>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group date form_datetime3">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="资方审批时间" type="text" name="rongShuApprovalDate" id="rongShuApprovalDate"
                                   placeholder="资方审批时间" value="${this?.rongShuApprovalDate}"
                                   class="form-control daily-b ">
                        </div>
                    </div>
                </div>
            </div>
        </g:form>
    </div>

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
                  <sec:ifNotGranted roles="ROLE_COO">
                    <g:link controller="opportunity" action="create" class="btn btn-info btn-xs"><i
                            class="fa fa-plus"></i>新建</g:link>
                          </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                全部订单
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="serialNumber" title="订单编号"></g:sortableColumn>
                            <g:sortableColumn property="serialNumber" title="订单类型"></g:sortableColumn>
                            <g:sortableColumn property="stage" title="订单状态"></g:sortableColumn>
                            <g:sortableColumn property="status" title="状态描述"></g:sortableColumn>
                            <g:sortableColumn property="fullName" title="借款人姓名"></g:sortableColumn>
                            %{-- <g:sortableColumn property="level" title="客户级别"></g:sortableColumn> --}%
                            <g:sortableColumn property="fullName" title="经纪人姓名"></g:sortableColumn>
                            <g:sortableColumn property="fullName" title="支持经理姓名"></g:sortableColumn>
                            <g:sortableColumn property="account" title="渠道名称"></g:sortableColumn>
                            <g:sortableColumn property="product" title="产品类型"></g:sortableColumn>
                            <g:sortableColumn property="loanAmount" title="询值总价（万元）"></g:sortableColumn>
                            <g:sortableColumn property="requestedAmount" title="申请总价（万元）"></g:sortableColumn>
                            <g:sortableColumn property="actualAmountOfCredit" title="审核可贷（万元）"></g:sortableColumn>
                            <g:sortableColumn property="loanDuration" title="拟贷款期限（月）"></g:sortableColumn>
                            <g:sortableColumn property="actualLoanDuration" title="实际贷款期限（月）"></g:sortableColumn>
                            <g:sortableColumn property="approvalTime" title="审批日期"></g:sortableColumn>
                            %{-- <g:sortableColumn property="opportunityFlexField" title="放款渠道"></g:sortableColumn>
                            <g:sortableColumn property="opportunityFlexField" title="放款账号"></g:sortableColumn> --}%
                            <g:sortableColumn property="mortgageCertificateType" title="抵押凭证类型"></g:sortableColumn>
                            <g:sortableColumn property="causeOfFailure" title="未成交归类"></g:sortableColumn>
                            <g:sortableColumn property="lastAction" title="上一步操作"></g:sortableColumn>
                            <g:sortableColumn property="createdDate" title="申请时间"></g:sortableColumn>
                            <g:sortableColumn property="rongShuStatus" title="资方放款账号"></g:sortableColumn>
                            <g:sortableColumn property="rongShuStatus" title="资方审批状态"></g:sortableColumn>
                            <g:sortableColumn property="rongShuStatus" title="资方审批时间"></g:sortableColumn>
                            <g:sortableColumn property="memo" title="备注"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${opportunityList}">
                            <tr>
                                <td><g:link controller="opportunity" action="show" id="${it?.id}" class="firstTd">${it?.serialNumber}</g:link></td>
                                <td>${it?.type?.name}</td>
                                <td>${it?.stage?.name}</td>
                                <td>${it?.status}</td>
                                <td>${it?.fullName}</td>
                                %{-- <td>${it?.lender?.level?.description}</td> --}%
                                <td>${it?.contact?.fullName}</td>
                                <td>${it?.user?.fullName}</td>
                                <td>${it?.account?.name}</td>
                                <td>${it?.product?.name}</td>
                                <td><g:formatNumber number="${it?.loanAmount}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                <td>${it?.requestedAmount}</td>
                                <td>${it?.actualAmountOfCredit}</td>
                                <td>${it?.loanDuration}</td>
                                <td>${it?.actualLoanDuration}</td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${com.next.OpportunityFlow.findByOpportunityAndStage(it, com.next.OpportunityStage.findByCode("08"))?.startTime}"/></td>
                                %{-- <td>${com.next.OpportunityFlexField.findByCategoryAndName(com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it, com.next.FlexFieldCategory.findByName("资金渠道")), "放款通道")?.value}</td> --}%
                                %{-- <td>${com.next.OpportunityFlexField.findByCategoryAndName(com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it, com.next.FlexFieldCategory.findByName("资金渠道")), "放款账号")?.value}</td> --}%
                                <td>${it?.mortgageCertificateType?.name}</td>
                                <td>${it?.causeOfFailure?.name}</td>
                                <td>${it?.lastAction?.name}</td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.createdDate}"/></td>
                                <td>${com.next.OpportunityFlexField.executeQuery("select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it.id} and c.flexFieldCategory.id = 13) and o.name = '放款账号'")[0]}</td>
                                <td>${it?.rongShuStatus}</td>
                                <td>${it?.rongShuApprovalDate?.format("yyyy-MM-dd")}</td>
                                <td>${it?.memo}</td>

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

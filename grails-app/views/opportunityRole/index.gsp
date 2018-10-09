<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'opportunityRole.label', default: 'OpportunityRole')}" />
    <title>订单-权限</title>
    <style>
    .seach-group .col-md-3 {
        margin-bottom: 6px;
    }
    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header transition animated fadeIn">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>订单-权限</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    订单-权限
                </h2>
            </div>
        </div>
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

            <input type="text" id="status" name="status" value="${status}" class="hide">
            <input type="text" id="prepareCity" name="prepareCity" value="${params?.prepareCity}" class="hide">

            <div class="panel-body seach-group">
                <div class="col-md-3">
                    <input title="开始时间" type="text" name="startTime" id="startTime" placeholder="起始时间" value="${params?.startTime}" readonly class="form-control daily-b form_datetime">
                </div>
                <div class="col-md-3">
                    <input title="结束时间" type="text" name="endTime" id="endTime" placeholder="截止时间" value="${params?.endTime}" readonly class="form-control daily-b form_datetime">
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
                              valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.city}" noSelection="${['null':'请选择城市']}"/>
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
                    <input title="放款账号发放时间依据" type="text" name="estimatedTime" id="estimatedTime" placeholder="放款账号发放时间依据" value="${params?.estimatedTime}" readonly class="form-control daily-b form_datetime">
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
                        <input title="审批开始时间" type="text" name="approvalStartTime" id="approvalStartTime" placeholder="2016-10-20 00:00:00"
                               value="${params?.approvalStartTime}" readonly class="form-control daily-b">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="input-group date form_datetime">
                        <span class="input-group-addon">
                            <span class="fa fa-calendar"></span>
                        </span>
                        <input title="审批结束时间" type="text" name="approvalEndTime" id="approvalEndTime" placeholder="2016-10-20 00:00:00"
                               value="${params?.approvalEndTime}" readonly class="form-control daily-b">
                    </div>
                </div> --}%

                <div class="col-md-3">
                    <g:select class="form-control" name="type" id="type"
                              from="${com.next.OpportunityType.list()}"
                              valueMessagePrefix="type" optionKey="code" optionValue="name" value="${this?.type}" noSelection="${['':'请选择订单类型']}"/>
                </div>

            </div>
        </div>
    </g:form>
</div>
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    所有订单-权限
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <g:sortableColumn property="opportunity" title="订单编号" />
                                    <g:sortableColumn property="type" title="订单类型" />
                                    <g:sortableColumn property="lender" title="借款人姓名" />
                                    %{-- <g:sortableColumn property="level" title="客户级别" /> --}%
                                    <g:sortableColumn property="contact" title="经纪人姓名" />
                                    <g:sortableColumn property="user" title="支持经理姓名" />
                                    <g:sortableColumn property="account" title="渠道名称"></g:sortableColumn>
                                    <g:sortableColumn property="requestedAmount" title="申请总价（万元）"></g:sortableColumn>
                                    <g:sortableColumn property="actualAmountOfCredit" title="审核可贷（万元）"></g:sortableColumn>
                                    <g:sortableColumn property="approvalTime" title="审批日期"></g:sortableColumn>
                                    %{-- <g:sortableColumn property="opportunityFlexField" title="放款渠道"></g:sortableColumn> --}%
                                    %{-- <g:sortableColumn property="opportunityFlexField" title="放款账号"></g:sortableColumn> --}%
                                    <g:sortableColumn property="mortgageCertificateType" title="抵押凭证类型"></g:sortableColumn>
                                    <g:sortableColumn property="stage" title="订单阶段" />
                                    <g:sortableColumn property="teamRole" title="权限" />
                                    <g:sortableColumn property="lastAction" title="上一步操作"></g:sortableColumn>
                                    <g:sortableColumn property="createdDate" title="申请时间"></g:sortableColumn>
                                    <g:sortableColumn property="memo" title="备注"></g:sortableColumn>
                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${opportunityRoleList}">
                                    <tr>
                                        <td><g:link controller="opportunity" action="show" id="${it?.opportunity?.id}">${it?.opportunity?.serialNumber}</g:link></td>
                                        <td>${it?.opportunity?.type?.name}</td>
                                        <td>${it?.opportunity?.fullName}</td>
                                        %{-- <td>${it?.opportunity?.lender?.level?.description}</td> --}%
                                        <td>${it?.opportunity?.contact?.fullName}</td>
                                        <td>${it?.opportunity?.user?.fullName}</td>
                                        <td>${it?.opportunity?.account?.name}</td>
                                        <td>${it?.opportunity?.requestedAmount}</td>
                                        <td>${it?.opportunity?.actualAmountOfCredit}</td>
                                        <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${com.next.OpportunityFlow.findByOpportunityAndStage(it?.opportunity, com.next.OpportunityStage.findByCode("08"))?.startTime}"/></td>
                                        %{-- <td>${com.next.OpportunityFlexField.findByCategoryAndName(com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it?.opportunity, com.next.FlexFieldCategory.findByName("资金渠道")), "放款通道")?.value}</td> --}%
                                        %{-- <td>${com.next.OpportunityFlexField.findByCategoryAndName(com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it?.opportunity, com.next.FlexFieldCategory.findByName("资金渠道")), "放款账号")?.value}</td> --}%
                                        <td>${it?.opportunity?.mortgageCertificateType?.name}</td>
                                        <td>${it?.opportunity?.stage?.name}</td>
                                        <td>${it?.teamRole?.name}</td>
                                        <td>${it?.opportunity?.lastAction?.name}</td>
                                        <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.opportunity?.createdDate}"/></td>
                                        <td>${it?.opportunity?.memo}</td>
                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${opportunityRoleCount ?: 0}" params="${params}" />
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
    //     $("#submitBtn").click(function () {
    //         var startTime = $("#startTime").val();
    //         var endTime = $("#endTime").val();
           
    //         var timeDiff = DateDifference(startTime,endTime)
    //         if(startTime ==null || startTime.trim() == ""){
    //             $("#alert").text("请输入开始时间").removeClass("hide");
    //             return;
    //         }
    //         if(endTime ==null || endTime.trim() == ""){
    //             $("#alert").removeClass("hide").text("请输入结束时间");
    //             return;
    //         }
    //          if (timeDiff < 0) {
    //             $("#alert").removeClass("hide").text("开始时间不能大于结束时间");
    //             return;
    //         }
    //          if (timeDiff > 30) {
    //             $("#alert").removeClass("hide").text("时间区间不能大于30天");
    //             return;
    //         }
    //         $(".searchOpportunityForm").submit();
    //     })



    // })
    $("#resetBtn").click(function () {
        $("#serialNumber").val("");
        $("#contact").val("");
        $("#fullName").val("");
        $("#user").val("");
        $("#city").val("");
        $("#startTime").val("");
        $("#endTime").val("");
        // $("#flexField").val("");
        $("#mortgageCertificateType").val("");
        // $("#estimatedTime").val("");
        $("#contactLevel").val("");
        $("#actualAmountOfCreditMin").val("");
        $("#actualAmountOfCreditMax").val("");
        // $("#approvalStartTime").val("");
        // $("#approvalEndTime").val("");
    })
</g:javascript>
</body>
</html>

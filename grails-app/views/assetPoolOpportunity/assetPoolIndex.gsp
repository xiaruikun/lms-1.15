<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityTeam.label', default: 'OpportunityTeam')}"/>
    <title>资产池订单列表</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                %{--<ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="assetPool" action="show">资产池</g:link>
                    </li>
                    <li class="active">
                        <span>订单列表</span>
                    </li>
                </ol>--}%
            </div>
            <h2 class="font-light m-b-xs">
                资产池订单列表
            </h2>

        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="message alert alert-info hide" role="status" id="alert">

        </div>

        <g:form method="POST" action="searchAssetPoolOpportunity">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>
                <input type="text" id="assetPool" name="assetPool" value="${assetPool?.id}" class="hide">

                <div class="panel-body seach-group">
                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="订单编号" id="serialNumber" name="serialNumber"
                               value="${params?.serialNumber}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="借款人姓名" id="fullName" name="fullName"
                               value="${params?.fullName}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="合同编号" id="contractSerialNumber" name="contractSerialNumber"
                               value="${params?.contractSerialNumber}">
                    </div>

                    <div class="col-md-3">
                        <g:select class="form-control" name="city" id="city"
                                  from="${com.next.City.list()}"
                                  valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.city}"
                                  noSelection="${['null': '请选择城市']}"/>
                    </div>

                     <div class="col-md-3">
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
                  %{--<sec:ifNotGranted roles="ROLE_COO">
                    <g:link action="create" controller="assetPoolOpportunity" params="[assetPool: this.assetPool.id]" class="btn btn-info btn-xs"><i
                            class="fa fa-plus"></i>新建</g:link>
                          </sec:ifNotGranted>--}%
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
                            <g:sortableColumn property="memo" title="备注"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${assetPoolOpportunityList}">
                            <tr>
                                <td><g:link controller="assetPoolOpportunity" action="assetPoolShow" id="${it?.opportunity?.id}" class="firstTd">${it?.opportunity?.serialNumber}</g:link></td>
                                <td>${it?.opportunity?.type?.name}</td>
                                <td>${it?.opportunity?.stage?.name}</td>
                                <td>${it?.opportunity?.status}</td>
                                <td>${it?.opportunity?.fullName}</td>
                                <td>${it?.opportunity?.contact?.fullName}</td>
                                <td>${it?.opportunity?.user?.fullName}</td>
                                <td>${it?.opportunity?.account?.name}</td>
                                <td>${it?.opportunity?.product?.name}</td>
                                <td><g:formatNumber number="${it?.opportunity?.loanAmount}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                <td>${it?.opportunity?.requestedAmount}</td>
                                <td>${it?.opportunity?.actualAmountOfCredit}</td>
                                <td>${it?.opportunity?.loanDuration}</td>
                                <td>${it?.opportunity?.actualLoanDuration}</td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${com.next.OpportunityFlow.findByOpportunityAndStage(it?.opportunity, com.next.OpportunityStage.findByCode("08"))?.startTime}"/></td>
                                %{-- <td>${com.next.OpportunityFlexField.findByCategoryAndName(com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it, com.next.FlexFieldCategory.findByName("资金渠道")), "放款通道")?.value}</td> --}%
                                %{-- <td>${com.next.OpportunityFlexField.findByCategoryAndName(com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it, com.next.FlexFieldCategory.findByName("资金渠道")), "放款账号")?.value}</td> --}%
                                <td>${it?.opportunity?.mortgageCertificateType?.name}</td>
                                <td>${it?.opportunity?.causeOfFailure?.name}</td>
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
                    <g:paginate total="${assetPoolOpportunityCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>

</div>
<g:javascript>
    $("#resetBtn").click(function () {
        $("#serialNumber").val("");
        $("#contact").val("");
        $("#fullName").val("");
        $("#user").val("");
        $("#city").val("");
        $("#flexField").val("");
        $("#mortgageCertificateType").val("");
        $("#startTime").val("");
        $("#endTime").val("");
        $("#estimatedTime").val("");
    })
</g:javascript>
</body>
</html>

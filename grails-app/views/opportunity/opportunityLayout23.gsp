<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>（北京-上海）主单页3.0-23</title>
</head>

<body>
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb m-t-none">
                    <li class="sameCollaterals" style="float: left;"><span class="btn btn-xs btn-info collateralNum"></span></li>
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunityTeam" action="index">订单管理</g:link>
                    </li>
                    <li class="active">
                        <span>信息查看</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light">
                订单号: ${this.opportunity.serialNumber}
            <span style="font-weight: normal" class="text-info">
                【${this.opportunity?.stage?.description}】
                【保护期： <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                ${this.opportunity?.protectionEndTime - this.opportunity?.protectionStartTime}天
                </g:if>】
                <g:if test="${this.opportunity?.status == 'Failed'}">【订单结果：<span
                        class="text-danger">失败</span>】</span></g:if>
                <g:elseif test="${this.opportunity?.status == 'Completed'}">【订单结果：成功】</g:elseif>
                <g:else>【订单结果：进行中】</g:else>
            </span>
                <g:link target="_blank" class="btn btn-xs btn-warning" controller="opportunity"
                        action="opportunityLayout24"
                        id="${this.opportunity?.id}">订单推进</g:link>
                <g:if test="${this.opportunity?.isTest}">
                    <span class="label label-danger"><i class="fa fa-star"></i> 测试 <i class="fa fa-star"></i></span>
                </g:if>
            </h2>

        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                业务概要
                <g:if test="${this.opportunity?.status == 'Failed'}">
                    <span class="failReason text-danger">订单失败：${this.opportunity?.causeOfFailure?.name}</span>
                </g:if>
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-center">
                        <thead>
                        <tr>
                            <th>业务区域</th>
                            <th>业务来源</th>
                            <th>抵押类型</th>
                            <th>
                                房产总价（万元）
                            </th>
                            <th>拟借款金额（万元）</th>
                            <th>拟借款期数（月）</th>
                            <th>渠道返费(万元)</th>
                            <th>产品类型</th>
                            <th>共同借款人</th>
                            %{--<th>实际月息（%）</th>--}%
                            <th>付息方式</th>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <th>上扣息月份数（月）</th>
                            </g:if>
                            <th>资金渠道</th>
                            <th>客户评级</th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.collaterals[0]?.city?.name}</td>
                            <td>${this.opportunity?.contact?.account?.name}</td>
                            <td>
                                <g:each in="${this.collaterals}">
                                    ${it?.mortgageType?.name}、
                                </g:each>
                            </td>
                            <td>
                                <g:formatNumber number="${this.opportunity?.loanAmount}" minFractionDigits="2"
                                                maxFractionDigits="2"/>
                            </td>
                            <td>
                                <g:formatNumber number="${this.opportunity?.requestedAmount}" minFractionDigits="2"
                                                maxFractionDigits="2"/>
                            </td>
                            <td>
                                ${this.opportunity?.loanDuration}
                            </td>
                            <td>
                                <g:each in="${opportunityProduct}">
                                    <g:if test="${it?.productInterestType?.name == '渠道返费费率'}">
                                        <g:if test="${!it?.installments}">一次性;<g:formatNumber
                                                number="${it.rate * this.opportunity.actualAmountOfCredit / 100}"
                                                minFractionDigits="2"
                                                maxFractionDigits="9"/></g:if>
                                        <g:else>月月返;<g:formatNumber
                                                number="${it.rate * this.opportunity.actualAmountOfCredit * this.opportunity.actualLoanDuration / 100}"
                                                minFractionDigits="2"
                                                maxFractionDigits="9"/></g:else>
                                    </g:if>
                                </g:each>
                            </td>
                            <td>${this.opportunity?.product?.name}</td>

                            <td>
                                <g:each in="${this.opportunityContacts}"><g:if
                                        test="${!(it?.type?.name == '曾用名')}">${it?.contact?.fullName}、</g:if></g:each>
                            </td>
                            %{--<td><g:formatNumber number="${this.opportunity?.monthlyInterest}" maxFractionDigits="9"/></td>--}%
                            <td>
                                ${this.opportunity?.interestPaymentMethod?.name}
                            </td>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <td>${this.opportunity?.monthOfAdvancePaymentOfInterest}</td>
                            </g:if>
                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:if test="${it?.flexFieldCategory?.name == '资金渠道'}">
                                        <g:each in="${it?.fields}" var="field">
                                            <g:if test="${field?.name == '放款通道'}">
                                                ${field?.value}、
                                            </g:if>
                                        </g:each>
                                        <g:each in="${it?.fields}" var="field">
                                            <g:if test="${field?.name == '放款账号'}">
                                                ${field?.value}
                                            </g:if>
                                        </g:each>
                                    </g:if>

                                </g:each>
                            </td>
                            <td>${this.opportunity?.lender?.level?.description}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                经纪人信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-center">
                        <thead>
                        <tr>
                            <th>经纪人姓名</th>
                            <th>经纪人电话</th>
                            <th>支持经理姓名</th>
                            <th>
                                支持经理电话
                            </th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.contact?.fullName}</td>
                            <td>${this.opportunity?.contact?.cellphone}</td>
                            <td>${this.opportunity?.user}</td>
                            <td>${this.opportunity?.user?.cellphone}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <g:render template="/layouts/opportunityTemplate/collateralsTemplate2"/>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.opportunity.contacts?.size() > 0}">
                        <g:if test="${this.canCreditReportShow}">

                            <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                    action="creditReportShow" id="${this.opportunity.id}"><i
                                    class="fa fa-database"></i>大数据</g:link>
                        </g:if>
                    </g:if>
                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence >= com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                        <g:link class="btn btn-info btn-xs" controller="opportunityContact" action="create"
                                params="[opportunity: this.opportunity.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </g:if>

                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                共同借款人
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="type" title="联系人类型"></g:sortableColumn>
                            <g:sortableColumn property="fullName" title="姓名"></g:sortableColumn>
                            <g:sortableColumn property="idNumber" title="身份证号"></g:sortableColumn>
                            <g:sortableColumn property="maritalStatus" title="年龄（岁）"></g:sortableColumn>
                            <g:sortableColumn property="cellphone" title="手机号"></g:sortableColumn>
                            <g:sortableColumn property="maritalStatus" title="婚姻状态"></g:sortableColumn>

                            <g:sortableColumn property="country" title="国籍"></g:sortableColumn>
                            <g:sortableColumn property="identityType" title="身份证件类型"></g:sortableColumn>
                            <g:sortableColumn property="profession" title="职业"></g:sortableColumn>
                            <g:sortableColumn property="connnectedContact" title="关联人"></g:sortableColumn>
                            <g:sortableColumn property="connectedType" title="关联关系"></g:sortableColumn>
                            %{--<sec:ifAllGranted roles="ROLE_ADMINISTRATOR">--}%
                            <g:sortableColumn width="10%" class="text-center" colspan="2" property="operation"
                                              title="操作"></g:sortableColumn>
                            %{--</sec:ifAllGranted>--}%
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityContacts}">
                            <tr>
                                <td>
                                    <g:link controller="opportunityContact" action="show"
                                            id="${it?.id}">${it?.type?.name}</g:link>
                                </td>
                                <td>
                                    ${it?.contact?.fullName}
                                </td>
                                <td>
                                    ${it?.contact?.idNumber}
                                </td>
                                <td>
                                    <g:if test="${it?.contact?.idNumber}">
                                        ${new Date().format("yyyy").toInteger().minus(it?.contact?.idNumber[6..9].toInteger())}
                                    </g:if>
                                    <g:else>
                                        ${it?.contact?.idNumber}
                                    </g:else>
                                </td>
                                <td>
                                    ${it?.contact?.cellphone}
                                </td>
                                <td>
                                    ${it?.contact?.maritalStatus}
                                </td>

                                <td>
                                    ${it?.contact?.country?.name}
                                </td>
                                <td>
                                    ${it?.contact?.identityType?.name}
                                </td>
                                <td>
                                    ${it?.contact?.profession?.name}
                                </td>
                                <td>
                                    ${it?.connectedContact?.fullName}
                                </td>
                                <td>
                                    ${it?.connectedType?.name}
                                </td>
                                %{--<sec:ifAllGranted roles="ROLE_ADMINISTRATOR">--}%
                                <td width="5%" class="text-center">
                                  <g:link class="btn btn-primary btn-outline btn-xs" controller="opportunityContact" action="show"
                                          id="${it?.id}">央行征信</g:link>

                                </td>
                                <td width="5%" class="text-center">
                                    <g:form resource="${it}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                </td>
                                %{--</sec:ifAllGranted>--}%
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">

                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                规则引擎小结
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn width="80%" property="fullName" title="内容"></g:sortableColumn>
                            <g:sortableColumn width="20%" property="type" title="创建时间"></g:sortableColumn>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityComments}">
                            <tr>

                                <td>
                                    ${it?.comment}
                                </td>
                                <td>
                                    ${it?.createdDate}
                                </td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <g:render template="/layouts/opportunityTemplate/opportunityProductTemplate"/>

    <div class="row">
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '产调结果'}">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        产调信息
                    </div>

                    <div class="panel-body no-padding">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover text-center">
                                <thead>
                                <tr>
                                    <g:sortableColumn width="30%" property="executionSequence"
                                                      title="${message(code: 'opportunityFlexField.name.label', default: '产调核查内容')}"/>
                                    <g:sortableColumn width="70%" property="stage"
                                                      title="${message(code: 'opportunityFlexField.value.label', default: '审核结果')}"/>
                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${it?.fields}">
                                    <tr>
                                        <td>
                                            <g:link controller="opportunityFlexField" action="show" id="${it?.id}"
                                                    class="firstTd">${it?.name}</g:link>
                                        </td>
                                        <td>${it?.value}</td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </g:if>
        </g:each>
    </div>

    <g:render template="/layouts/opportunityTemplate/opportunityContractsTemplate"/>

    <g:if test="${this.canAttachmentsShow}">
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="attachments" action="create"
                                id="${this.opportunity.id}"><i class="fa fa-upload"></i>上传附件</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    附件信息
                </div>

                <div class="panel-body float-e-margins">
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '基础材料']">基础材料</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '其他资料']">其他资料</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '征信']">征信</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '大数据']">大数据</g:link>

                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '产调结果']">产调结果</g:link>
                    <g:link target="_blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '签呈']">签呈</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="opportunityFlexField"
                            id="${this.opportunity?.id}"
                            action="opportunityFlexField01" target=" _blank"><i class="fa"></i>外访报告</g:link>
                    <g:link target=" _blank" controller="attachments"
                            action="riskManageShow"
                            id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '抵押合同全委']"
                            class="btn btn-outline btn-primary">抵押、合同、全委</g:link>

                    <g:link target=" _blank" controller="attachments" action="riskManageShow" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '其他放款要求资料']"
                            class="btn btn-outline btn-primary">其他放款要求资料</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '意向申请单']">意向申请单</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '公证书']">公证书</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '银行卡复印件']">银行卡复印件</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '被执情况']">被执情况</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="riskManageShow" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '二抵：抵押物银行按揭贷款合同']">二抵按揭贷款合同</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '企业信息截图']">企业信息截图</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '外贸借款申请表']">外贸借款申请表</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '外贸代扣授权书']">外贸代扣授权书</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '融数审批通知书']">融数审批通知书</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '外贸还款计划表']">外贸还款计划表</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '外贸征信查询授权书']">外贸征信查询授权书</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '签约录像']">签约录像</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '借款用途证明']">借款用途证明</g:link>

                </div>
            </div>
        </div>
    </g:if>


    <div class="row">
        <div class="hpanel horange">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link params="[flag: 'shibai']" id="${this.opportunity?.id}" controller="opportunity"
                            action="edit" class="btn btn-info btn-xs"><i
                            class="fa fa-edit"></i>编辑</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>

                </div>
                失败原因
            </div>

            <div class="panel-body form-horizontal p-xs">
                <div class="form-group">
                    <label class="col-md-3 control-label">未成交归类：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.causeOfFailure?.name}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel horange">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                抵押公证信息
            </div>

            <div class="panel-body form-horizontal p-xs">
                <div class="form-group m-b-none">
                    <label class="col-md-3 control-label">是否抵押：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            <g:if test="${this.opportunity?.mortgagingStatus == true}">是</g:if>
                            <g:if test="${this.opportunity?.mortgagingStatus == false}">否</g:if>
                        </span>
                    </div>
                    <label class="col-md-3 control-label">是否公证：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            <g:if test="${this.opportunity?.notarizingStatus == true}">是</g:if>
                            <g:if test="${this.opportunity?.notarizingStatus == false}">否</g:if>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <g:render template="/layouts/opportunityTemplate/opportunityTeamTemlpate"/>
    <g:render template="/layouts/opportunityTemplate/activitiesTemplate"/>
    <g:render template="/layouts/opportunityTemplate/opportunityFlowsTemplate"/>
    <div class="row commentsRow">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                审批意见
            </div>

            <div class="panel-body p-xs">
                <div class="hpanel">
                    <div class="v-timeline vertical-container animate-panel commentsContent">
                        <g:each in="${this.opportunityFlows}">
                            <g:if test="${it?.comments}">

                                <div class="vertical-timeline-block">
                                    <div class="vertical-timeline-icon navy-bg">
                                        <i class="fa fa-calendar"></i>
                                    </div>

                                    <div class="vertical-timeline-content">
                                        <div class="p-sm">
                                            <g:each in="${this.opportunityRoles}" var="role">
                                                <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval'}">

                                                    <span class="vertical-date pull-right">${role?.user}</span>
                                                </g:if>
                                            </g:each>

                                            <h2>${it?.stage?.description}<span class="m-l-xs"><g:formatDate
                                                    format="yyyy-MM-dd HH:mm:ss"
                                                    date="${it?.startTime}"></g:formatDate></span></h2>

                                            <p>${it?.comments}</p>
                                        </div>
                                    </div>
                                </div>
                            </g:if>
                        </g:each>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<footer class="footer bg-success">
    <g:render template="/layouts/opportunityTemplate/footerLeftTemplate"/>
    <g:render template="/layouts/opportunityTemplate/footerRightTemplate"/>
</footer>

<script>
    $(function () {
        $("body").addClass("fixed-small-header");
        if ($(".commentsContent").children().length < 1) {
            $(".commentsContent").closest(".commentsRow").remove();
        }
        searchSameCollaterals();
        function searchSameCollaterals() {
            $.ajax({
                type: "POST",
                data: {
                    opportunityId: $("#opportunityId").val(),
                },
                cache: false,
                url: "/collateral/searchOldOpportunityNumbers",
                dataType: "JSON",
                success: function (data) {
                    if (data.size >0) {
                        $(".sameCollaterals").css('display',"block");
                        $(".collateralNum").html("报单记录（"+data.size+"）")
                    } else {
                        $(".sameCollaterals").css('display',"none");
                    }
                },
                error: function () {
                    swal("获取相同房产证订单失败，请稍后重试", "", "error");
                }
            });
        }
        $(".sameCollaterals").click(function () {
                $.ajax({
                    type: "POST",
                    data: {
                        opportunityId: $("#opportunityId").val(),
                    },
                    cache: false,
                    url: "/collateral/searchOldOpportunityNumbers",
                    dataType: "JSON",
                    success: function (data) {
                        if (data.size >0) {
                            var texts = "";
                            for (i = 0 ;i<=data.size-1;i++){
                                texts+= "编号："+data.list[i].serialNumber+"--------借款人姓名："+data.list[i].fullName+"\n";
                            }
                            swal({
                                title: "LMS存在"+data.size+"笔订单房产证编号与此订单相同",
                                confirmButtonColor: "#1787dd",
                                text:texts,
                                confirmButtonText: "我知道了"
                            });
                        }
                    },
                    error: function () {
                        swal("获取相同房产证订单失败，请稍后重试", "", "error");
                    }
                });
            }
        );
    })
</script>
</body>

</html>

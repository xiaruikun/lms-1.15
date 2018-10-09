<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>（北京-上海）资金部页面3.0-29</title>
</head>

<body>
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb m-t-none">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunityTeam" action="index">订单管理</g:link>
                    </li>
                    <li class="active">
                        <span>资金信息</span>
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
        <div class="hpanel hyellow">
            <div class="panel-heading">
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
                            <th>城市</th>
                            <th>业务类型</th>
                            <th>产品类型</th>
                            <th>合同提前还款罚息约定</th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.collaterals[0]?.city?.name}</td>
                            <td>${this.opportunity?.type?.name}</td>
                            <td>${this.opportunity?.product?.name}</td>
                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:if test="${it?.flexFieldCategory?.name == '罚息约定'}">
                                        <g:each in="${it?.fields}" var="field">
                                            <g:if test="${field?.name == '罚息约定'}">

                                                ${field?.value}
                                            </g:if>
                                        </g:each>

                                    </g:if>
                                </g:each>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                借款用途
            </div>

            <div class="panel-body">
                <p>借款用途：${this.opportunity?.loanUsage}</p>
                    <g:if test="${this.opportunity?.otherLoanUsage}">
                    <p>
                        借款用途说明：${this.opportunity?.otherLoanUsage}
                    </p>
                    </g:if>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="hpanel hviolet">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                共同借款人
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="country" title="国籍"></g:sortableColumn>
                            <g:sortableColumn property="fullName" title="姓名"></g:sortableColumn>

                            <g:sortableColumn property="type" title="联系人类型"></g:sortableColumn>
                            <g:sortableColumn property="maritalStatus" title="年龄（岁）"></g:sortableColumn>

                            <g:sortableColumn property="maritalStatus" title="婚姻状态"></g:sortableColumn>
                            <g:sortableColumn property="connectedType" title="关联关系"></g:sortableColumn>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityContacts}">
                            <tr>
                                <td>
                                    ${it?.contact?.country?.name}
                                </td>
                                <td>
                                    ${it?.contact?.fullName}
                                </td>
                                <td>
                                    ${it?.type?.name}
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
                                    ${it?.contact?.maritalStatus}
                                </td>
                                <td>
                                    ${it?.connectedType?.name}
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                业务概要2
            </div>

            <div class="panel-body no-padding">
                <table class="table table-striped table-bordered text-center">
                    <thead>
                    <tr>
                        <th>借款期数（月）</th>
                        <th>审批金额（万元）</th>

                        <th>综合息费（%）</th>
                        <th>基础月息费（%）</th>

                    </tr>
                    </thead>
                    <tbody>
                    <tr>

                        <td>
                            ${this.opportunity?.actualLoanDuration}
                        </td>
                        <td>
                            <g:formatNumber number="${this.opportunity?.actualAmountOfCredit}" minFractionDigits="2"
                                            maxFractionDigits="2"/>
                        </td>
                        <th><g:formatNumber number="${this.opportunity?.ompositeMonthlyInterest}" maxFractionDigits="9"/></th>

                        <td>
                            <g:formatNumber number="${this.opportunity?.monthlyInterest}" maxFractionDigits="9"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hviolet">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                抵押物信息
            </div>

            <div class="panel-body collateral no-padding">

                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <th>房产序号</th>
                            <g:sortableColumn property="address" title="抵押物地址"></g:sortableColumn>
                            <g:sortableColumn property="mortgageType" title="抵押状态"></g:sortableColumn>
                            <g:sortableColumn property="loanToValue" title="抵押率（%）"></g:sortableColumn>
                            <g:sortableColumn property="region" title="房屋坐落范围"></g:sortableColumn>
                            <g:sortableColumn property="totalPrice" title="评估总价（万元）"></g:sortableColumn>

                            <g:sortableColumn property="buildTime" title="房龄(年)"></g:sortableColumn>
                            <g:sortableColumn property="assetType" title="产权类型"></g:sortableColumn>

                            <g:sortableColumn property="projectType" title="规划用途"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.collaterals}">
                            <tr>
                                <td>第<span class="collateralOrder"></span>套</td>
                                <td>
                                    ${it.address}
                                </td>
                                <td>
                                    ${it?.mortgageType?.name}
                                </td>
                                <td>
                                    <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>

                                </td>
                                <td>${it.region?.name}</td>
                                <td>
                                    <g:formatNumber number="${it?.totalPrice}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>
                                </td>
                                <td>
                                    <g:if test="${it?.buildTime}">
                                        ${new Date().format("yyyy").toInteger().minus(it?.buildTime.format("yyyy").toInteger())}
                                    </g:if>
                                    <g:else>
                                        ${it?.buildTime}
                                    </g:else>
                                </td>
                                <td>
                                    ${it.assetType}
                                </td>


                                <td>
                                    ${it?.projectType?.name}
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>


    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                放款计划信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <th>放款凭证</th>
                            <th>预计公证时间</th>
                            <th>预计抵押时间</th>
                            <th>预计回他项时间</th>
                            <th>预计放款时间</th>
                            <th>是否通过京东预审</th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.mortgageCertificateType?.name}</td>
                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:each in="${it?.fields}">
                                        <g:if test="${it?.name == '预计公证时间'}">
                                            ${it?.value}
                                        </g:if>
                                    </g:each>

                                </g:each>
                            </td>

                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:each in="${it?.fields}">
                                        <g:if test="${it?.name == '预计抵押登记时间'}">
                                            ${it?.value}
                                        </g:if>
                                    </g:each>

                                </g:each>

                            </td>
                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:each in="${it?.fields}">
                                        <g:if test="${it?.name == '预计出他项时间'}">
                                            ${it?.value}
                                        </g:if>
                                    </g:each>

                                </g:each>

                            </td>

                            <td>
                                <g:formatDate format="yyyy-MM-dd" date="${this.opportunity?.estimatedLendingDate}"/>
                            </td>
                            <td>****</td>

                        </tr>
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
                    <g:each in="${this.opportunityFlexFieldCategorys}">
                        <g:if test="${it?.flexFieldCategory?.name == '资金渠道'}">
                            <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" action="batchEdit"
                                    id="${it?.id}"><i class="fa fa-edit"></i>编辑</g:link>
                        </g:if>
                    </g:each>

                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                资金渠道
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>放款通道</th>
                            <th>抵押权人</th>
                            <th>放款账号</th>
                            <th>放款帐号有效截止时间</th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:if test="${it?.flexFieldCategory?.name == '资金渠道'}">
                                        <g:each in="${it?.fields}" var="field">
                                            <g:if test="${field?.name == '放款通道'}">

                                                ${field?.value}
                                            </g:if>
                                        </g:each>

                                    </g:if>
                                </g:each>
                            </td>
                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:if test="${it?.flexFieldCategory?.name == '资金渠道'}">
                                        <g:each in="${it?.fields}" var="field">
                                            <g:if test="${field?.name == '抵押权人'}">

                                                ${field?.value}
                                            </g:if>
                                        </g:each>
                                    </g:if>
                                </g:each>
                            </td>
                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:if test="${it?.flexFieldCategory?.name == '资金渠道'}">
                                        <g:each in="${it?.fields}" var="field">
                                            <g:if test="${field?.name == '放款账号'}">

                                                ${field?.value}
                                            </g:if>
                                        </g:each>
                                    </g:if>
                                </g:each>
                            </td>
                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:if test="${it?.flexFieldCategory?.name == '资金渠道'}">
                                        <g:each in="${it?.fields}" var="field">
                                            <g:if test="${field?.name == '放款帐号有效截止时间'}">

                                                ${field?.value}
                                            </g:if>
                                        </g:each>
                                    </g:if>
                                </g:each>
                            </td>

                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</div>
<footer class="footer bg-success">
    <g:render template="/layouts/opportunityTemplate/footerLeftTemplate"/>
   %{-- <div class="pull-left m-b-xs" style="margin-left:5px">
        <g:form controller="opportunity" action="changeOpportunityStage">
            <input class="form-control" name="opportunity" value="${this.opportunity?.id}" type="hidden">
            <g:select name="opportunityStage" from="${com.next.OpportunityStage.list()}" optionKey="id"
                      optionValue="name" value="${this.opportunity?.stage?.id}"/>
            <button class="btn btn-sm btn-danger m-l-sm" type="submit">订单阶段跳转</button>
        </g:form>
    </div>--}%
    <g:render template="/layouts/opportunityTemplate/footerRightTemplate"/>
</footer>
<script>
    $(function () {
        $("body").addClass("fixed-small-header");
    })
</script>
</body>

</html>

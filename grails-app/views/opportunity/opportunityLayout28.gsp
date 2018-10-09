<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>（上海-北京）合规2.0-28</title>
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
                        <span>信息查看</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light">
                订单号: ${this.opportunity.serialNumber}
            <span style="font-weight: normal" class="text-info">
                【${this.opportunity?.stage?.description}】 【保护期：
                <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                ${this.opportunity?.protectionEndTime - this.opportunity?.protectionStartTime}天
                </g:if>】
                <g:if test="${this.opportunity?.status == 'Failed'}">【订单结果：<span class="text-danger">失败</span>】</span>
                </g:if>
                <g:elseif test="${this.opportunity?.status == 'Completed'}">【订单结果：成功】</g:elseif>
                <g:else>【订单结果：进行中】</g:else>
            </span>
                <g:if test="${this.opportunity?.isTest}">
                    <span class="label label-danger">
                        <i class="fa fa-star"></i>
                        测试
                        <i class="fa fa-star"></i>
                    </span>
                </g:if>
            </h2>

        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
        </g:if>
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide">
                        <i class="fa fa-chevron-up"></i>
                    </a>
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
                            <th>
                                客户级别
                            </th>
                            <th>
                                抵押凭证类型
                            </th>
                            <th>
                                审核可贷金额(万元)
                            </th>
                            <th>
                                实际贷款期限(月)
                            </th>
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
                                                minFractionDigits="2" maxFractionDigits="9"/></g:if>
                                        <g:else>月月返;<g:formatNumber
                                                number="${it.rate * this.opportunity.actualAmountOfCredit * this.opportunity.actualLoanDuration / 100}"
                                                minFractionDigits="2" maxFractionDigits="9"/></g:else>
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
                            <td>
                                ${this.opportunity?.lender?.level?.description}
                            </td>
                            <td>
                                ${this.opportunity?.mortgageCertificateType?.name}
                            </td>
                            <td>
                                ${this.opportunity?.actualAmountOfCredit}
                            </td>
                            <td>
                                ${this.opportunity?.actualLoanDuration}
                            </td>
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
                    <a class="showhide">
                        <i class="fa fa-chevron-up"></i>
                    </a>
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

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                房产信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="externalId" title="外部唯一ID"></g:sortableColumn>
                            <g:sortableColumn property="propertySerialNumber" title="房产证编号"></g:sortableColumn>
                            <g:sortableColumn property="city" title="城市"></g:sortableColumn>
                            <g:sortableColumn property="district" title="区县"></g:sortableColumn>
                            <g:sortableColumn property="district" title="所在区域"></g:sortableColumn>

                            <g:sortableColumn property="projectName" title="小区名称"></g:sortableColumn>
                            <g:sortableColumn property="address" title="地址"></g:sortableColumn>
                            <g:sortableColumn property="orientation" title="朝向"></g:sortableColumn>
                            <g:sortableColumn property="area" title="面积（m2）"></g:sortableColumn>
                            <g:sortableColumn property="building" title="楼栋"></g:sortableColumn>
                            <g:sortableColumn property="unit" title="单元"></g:sortableColumn>
                            <g:sortableColumn property="roomNumber" title="户号"></g:sortableColumn>
                            <g:sortableColumn property="floor" title="所在楼层"></g:sortableColumn>
                            <g:sortableColumn property="totalFloor" title="总楼层"></g:sortableColumn>
                            <g:sortableColumn property="assetType" title="房产类型"></g:sortableColumn>
                            <g:sortableColumn property="houseType" title="物业类型"></g:sortableColumn>
                            <g:sortableColumn property="specialFactors" title="特殊因素"></g:sortableColumn>
                            <g:sortableColumn property="unitPrice" title="批贷房产单价（元）"></g:sortableColumn>
                            <g:sortableColumn property="totalPrice" title="批贷房产总价（万元）"></g:sortableColumn>
                            <g:sortableColumn property="status" title="状态"></g:sortableColumn>
                            <g:sortableColumn property="loanToValue" title="抵押率（%）"></g:sortableColumn>
                            <g:sortableColumn property="firstMortgageAmount" title="一抵金额(万元)"></g:sortableColumn>
                            <g:sortableColumn property="secondMortgageAmount" title="二抵金额(万元)"></g:sortableColumn>
                            <g:sortableColumn property="typeOfFirstMortgage" title="一抵性质"></g:sortableColumn>
                            <g:sortableColumn property="mortgageType" title="抵押类型"></g:sortableColumn>
                            <g:sortableColumn property="projectType" title="立项类型"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="建成时间"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="房龄(年)"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="最新总价(万元)"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="估值等级"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.collaterals}">
                            <tr>
                                <td class="collateralsExternalId">
                                    ${it.externalId}
                                </td>
                                <td>
                                    ${it?.propertySerialNumber}
                                </td>
                                <td>
                                    ${it.city?.name}
                                </td>

                                <td>
                                    ${it.district}
                                </td>
                                <td>${it.region?.name}</td>
                                <td>
                                    ${it.projectName}
                                </td>
                                <td>
                                    ${it.address}
                                </td>
                                <td>
                                    ${it.orientation}
                                </td>
                                <td class="area">
                                    ${it.area}
                                </td>
                                <td>
                                    ${it.building}
                                </td>
                                <td>
                                    ${it.unit}
                                </td>
                                <td>
                                    ${it.roomNumber}
                                </td>
                                <td>
                                    ${it.floor}
                                </td>
                                <td>
                                    ${it.totalFloor}
                                </td>
                                <td>
                                    ${it.assetType}
                                </td>
                                <td>
                                    ${it.houseType}
                                </td>
                                <td>
                                    ${it.specialFactors}
                                </td>
                                <td>
                                    <g:formatNumber number="${it?.unitPrice}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>

                                </td>
                                <td>
                                    <g:formatNumber number="${it?.totalPrice}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>
                                </td>
                                <td>
                                    ${it.status}
                                </td>
                                <td>
                                    <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>

                                </td>
                                <td>
                                    ${it?.firstMortgageAmount}
                                </td>
                                <td>
                                    ${it?.secondMortgageAmount}
                                </td>
                                <td>
                                    ${it?.typeOfFirstMortgage?.name}
                                </td>
                                <td>
                                    ${it?.mortgageType?.name}
                                </td>
                                <td>
                                    ${it?.projectType?.name}
                                </td>
                                <td>
                                    <g:formatDate format="yyyy" date="${it.buildTime}"/>
                                </td>
                                <td>
                                    <g:if test="${it?.buildTime}">
                                        ${new Date().format("yyyy").toInteger().minus(it?.buildTime.format("yyyy").toInteger())}
                                    </g:if>
                                    <g:else>
                                        ${it?.buildTime}
                                    </g:else>
                                </td>
                                <td class="latestCollateralPrice"></td>
                                <td class="valuationReliability"></td>
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
                    <g:if test="${this.opportunity.contacts?.size() > 0}">

                            <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                    action="creditReportShow2" id="${this.opportunity.id}"><i
                                    class="fa fa-database"></i>大数据</g:link>
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
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityContacts}">
                            <tr>
                                <td>
                                    ${it?.type?.name}
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
                                <td class="">
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
                费用
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '费率类型')}"/>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '费率（%）')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.provider.label', default: '分期付款')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.createdDate.label', default: '上扣息月份数')}"/>
                            <g:sortableColumn property="contractType" title="合同类型"/>
                            <g:sortableColumn property="createBy"
                                              title="${message(code: 'component.createBy.label', default: '创建人')}"/>
                            <g:sortableColumn property="modifyBy"
                                              title="${message(code: 'component.modifyBy.label', default: '修改人')}"/>
                            <g:sortableColumn property="createdDate" title="创建时间"></g:sortableColumn>
                            <g:sortableColumn property="modifiedDate" title="修改时间"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>

                        <g:each in="${opportunityProduct}">
                            <tr>
                                <td>
                                    ${it?.productInterestType?.name}
                                </td>
                                <td><g:formatNumber number="${it.rate}"
                                                    maxFractionDigits="9"/></td>
                                <td><g:if test="${it?.installments}">
                                    是
                                </g:if>
                                <g:else>
                                    否
                                </g:else>
                                <td>${it?.firstPayMonthes}</td>
                                <td>${it?.contractType?.name}</td>
                                <td>
                                    ${it?.createBy}
                                </td>
                                <td>
                                    ${it?.modifyBy}
                                </td>
                                <td><g:formatDate date="${it?.createdDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate></td>
                                <td><g:formatDate date="${it?.modifiedDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '产调结果'}">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <a class="showhide">
                                <i class="fa fa-chevron-up"></i>
                            </a>
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
                                            ${it?.name}
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

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                合同信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="contract.serialNumber" title="合同编号"></g:sortableColumn>
                            <g:sortableColumn property="contract.type" title="合同类别"></g:sortableColumn>
                            <g:sortableColumn property="contract.createdDate" title="创建时间"></g:sortableColumn>
                            <g:sortableColumn property="contract.modifiedDate" title="修改时间"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunity?.contracts}">
                            <tr>
                                <td>
                                    ${it?.contract?.serialNumber}
                                </td>
                                <td>
                                    ${it?.contract?.type?.name}
                                </td>
                                <td>
                                    <g:formatDate date="${it?.createdDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate>

                                </td>
                                <td>
                                    <g:formatDate date="${it?.modifiedDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
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
                附件信息
            </div>

            <div class="panel-body float-e-margins">
                <g:each in="${com.next.AttachmentType.list()}">
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="showByInvestor" id="${this.opportunity?.id}"
                            params="[type: it.id]">${it?.name}</g:link>
                </g:each>

            </div>
        </div>
    </div>

    <g:each in="${this.opportunityFlexFieldCategorys}">
        <g:if test="${it?.flexFieldCategory?.name in ['抵押物评估值', '抵押物其他情况', '外访预警']}">

            <div class="row" id="">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">

                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body no-padding">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <g:sortableColumn property="executionSequence"
                                                      title="${message(code: 'opportunityFlexField.name.label', default: '名称')}"/>
                                    <g:sortableColumn property="stage"
                                                      title="${message(code: 'opportunityFlexField.value.label', default: '值')}"/>

                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${it?.fields}" var="field">
                                    <tr>
                                        <td width="20%">
                                            ${field?.name}
                                        </td>
                                        <td width="72%">${field?.value}</td>

                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </g:if>
    </g:each>
    <div class="row">
        <div class="hpanel horange">
            <div class="panel-heading">
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
                    <a class="showhide">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
                抵押公证信息
            </div>

            <div class="panel-body form-horizontal p-xs">
                <div class="form-group">
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

                <div class="form-group m-b-none">
                    <label class="col-md-3 control-label">抵押时间：</label>

                    <div class="col-md-4">
                        <span class="cont">
                            <g:formatDate format="yyyy-MM-dd" date="${this.opportunity?.dateOfMortgage}"/>
                        </span>
                    </div>

                    <label class="col-md-1 control-label">公证时间：</label>

                    <div class="col-md-4">
                        <span class="cont">
                            <g:formatDate format="yyyy-MM-dd" date="${this.opportunity?.dateOfNotarization}"/>
                        </span>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <g:render template="/layouts/opportunityTemplate/opportunityTeamTemlpate"/>
    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">

                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                任务指派
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>

                            <g:sortableColumn property="type"
                                              title="${message(code: 'activity.type.label', default: '活动类型')}"/>
                            <g:sortableColumn property="subtype"
                                              title="${message(code: 'activity.subtype.label', default: '子类型')}"/>
                            <g:sortableColumn property="startTime"
                                              title="${message(code: 'activity.startTime.label', default: '开始时间')}"/>
                            <g:sortableColumn property="endTime"
                                              title="${message(code: 'activity.endTime.label', default: '结束时间')}"/>
                            <g:sortableColumn property="actualStartTime"
                                              title="${message(code: 'activity.actualStartTime.label', default: '实际开始时间')}"/>
                            <g:sortableColumn property="actualEndTime"
                                              title="${message(code: 'activity.actualEndTime.label', default: '实际结束时间')}"/>
                            <g:sortableColumn property="contact"
                                              title="${message(code: 'activity.contact.label', default: '抵押人姓名')}"/>
                            <g:sortableColumn property="user"
                                              title="${message(code: 'activity.user.label', default: '所有者')}"/>
                            <g:sortableColumn property="assignedTo"
                                              title="${message(code: 'activity.assignedTo.label', default: '下户人')}"/>
                            <g:sortableColumn property="status"
                                              title="${message(code: 'activity.status.label', default: '状态')}"/>
                            <g:sortableColumn property="city"
                                              title="${message(code: 'activity.city.label', default: '城市')}"/>
                            <g:sortableColumn property="address"
                                              title="${message(code: 'activity.address.label', default: '地址')}"/>
                            <g:sortableColumn property="longitude"
                                              title="${message(code: 'activity.longitude.label', default: '经度')}"/>
                            <g:sortableColumn property="latitude"
                                              title="${message(code: 'activity.latitude.label', default: '维度')}"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.activities}">
                            <tr>
                                <td>
                                    ${it?.type?.name}
                                </td>
                                <td>${it?.subtype?.name}</td>
                                <td>
                                    <g:formatDate class="weui_input" date="${it?.startTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="startTime" autocomplete="off"
                                                  readonly="true"></g:formatDate>
                                </td>
                                <td>
                                    <g:formatDate class="weui_input" date="${it?.endTime}" format="yyyy-MM-dd HH:mm:ss"
                                                  name="endTime" autocomplete="off" readonly="true"></g:formatDate>
                                </td>
                                <td>
                                    <g:formatDate class="weui_input" date="${it?.actualStartTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="actualStartTime" autocomplete="off"
                                                  readonly="true"></g:formatDate>
                                </td>
                                <td>
                                    <g:formatDate class="weui_input" date="${it?.actualEndTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="actualEndTime" autocomplete="off"
                                                  readonly="true"></g:formatDate>
                                </td>
                                <td>${it?.contact?.fullName}</td>
                                <td>${it?.user}</td>
                                <td>${it?.assignedTo}</td>
                                <td>${it?.status}</td>
                                <td>${it?.city}</td>
                                <td>${it?.address}</td>
                                <td>${it?.longitude}</td>
                                <td>${it?.latitude}</td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>


    <g:render template="/layouts/opportunityTemplate/opportunityFlowsTemplate"/>

    <div class="row commentsRow">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide">
                        <i class="fa fa-chevron-up"></i>
                    </a>
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

                                            <h2>${it?.stage?.description}<span class="m-l-xs">
                                                <g:formatDate format="yyyy-MM-dd HH:mm:ss"
                                                              date="${it?.startTime}"></g:formatDate>
                                            </span>
                                            </h2>

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

    <div class="row">
        <div class="hpanel horange">
            <div class="panel-heading">
                <div class="panel-tools">
                    <button class="btn btn-info btn-xs" data-toggle="modal"
                            data-target="#complianceChecking"><i class="fa fa-edit"></i>合规检查编辑</button>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                合规检查
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-3 control-label">是否合规检查：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            <g:if test="${this.opportunity?.complianceChecking == true}">是</g:if>
                            <g:if test="${this.opportunity?.complianceChecking == false}">否</g:if>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <g:render template="/layouts/opportunityTemplate/riskInvestigationReportTemplate"/>
</div>

<footer class="footer bg-success">
    <g:render template="/layouts/opportunityTemplate/footerRightTemplate"/>
</footer>

<div class="modal fade" id="complianceChecking" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <g:form class="form-horizontal" resource="${this.opportunity}" method="PUT">
                <div class="color-line"></div>

                <div class="modal-header">
                    <h4 class="modal-title">合规检查编辑</h4>
                </div>

                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-md-3 control-label">是否合规检查：</label>

                        <div class="col-md-2">
                            <span class="cont">
                                <g:checkBox class="i-checks" name="complianceChecking"
                                            value="${this.opportunity?.complianceChecking}"/>
                            </span>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </g:form>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("body").addClass("fixed-small-header");
        if ($(".commentsContent").children().length < 1) {
            $(".commentsContent").closest(".commentsRow").remove();
        }

    })
</script>
</body>

</html>

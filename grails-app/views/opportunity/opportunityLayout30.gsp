<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>放款审批页(审计)-30</title>
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
                        <g:link controller="opportunity" action="indexByInvestor">订单管理</g:link>
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
                <g:if test="${this.opportunity?.isTest}">
                    <span class="label label-danger"><i class="fa fa-star"></i> 测试 <i class="fa fa-star"></i></span>
                </g:if>
            </h2>

        </div>
    </div>
</div>

<div class="content animate-panel" style="padding-bottom: 16px">
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
                            <th>借款金额（万元）</th>
                            <th>实际贷款期限（月）</th>

                            <th>产品类型</th>
                            <th>共同借款人</th>
                            <th>客户类型</th>
                            %{--<th>实际月息（%）</th>--}%
                            <th>付息方式</th>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <th>上扣息月份数（月）</th>
                            </g:if>
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
                                <g:formatNumber number="${this.opportunity?.actualAmountOfCredit}" minFractionDigits="2"
                                                maxFractionDigits="2"/>
                            </td>
                            <td>
                                ${this.opportunity?.actualLoanDuration}
                            </td>
                            <td>${this.opportunity?.product?.name}</td>
                            <td>
                                <g:each in="${this.opportunityContacts}"><g:if
                                        test="${!(it?.type?.name == '曾用名')}">${it?.contact?.fullName}、</g:if></g:each>
                            </td>
                            <td>${this.opportunity?.lender?.level?.description}</td>

                            %{--<td><g:formatNumber number="${this.opportunity?.monthlyInterest}" maxFractionDigits="9"/></td>--}%
                            <td>${this.opportunity?.interestPaymentMethod?.name}</td>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <td>${this.opportunity?.monthOfAdvancePaymentOfInterest}</td>
                            </g:if>
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
                        <g:if test="${this.canCreditReportShow}">

                            <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                    action="creditReportShow" id="${this.opportunity.id}"><i
                                    class="fa fa-database"></i>大数据</g:link>
                        </g:if>
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
                            <g:if test="${this.canInterestEdit}">
                            </g:if>
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
                               %{-- <td>${it?.radixProductInterestType?.name}</td>--}%
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
        <div class="hpanel hgreen">
            <div class="panel-heading">
                账户信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="type" title="账户类别"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.bank" title="银行"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.paymentChannel" title="支付通道"></g:sortableColumn>

                            <g:sortableColumn property="bankAccount.numberOfAccount" title="卡号"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.name" title="账户名"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.cellphone" title="银行预留手机号"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.certificateType" title="证件类型"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.numberOfCertificate" title="证件号"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.active" title="是否验卡成功"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.bankBranch" title="支行"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.bankAddress" title="支行地址"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.createdBy" title="创建人"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.modifiedBy" title="修改人"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.createdDate" title="创建时间"></g:sortableColumn>
                            <g:sortableColumn property="bankAccount.modifiedDate" title="修改时间"></g:sortableColumn>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.bankAccounts}">
                            <tr>
                                <td>
                                    ${it?.type?.name}
                                </td>
                                <td>
                                    ${it?.bankAccount?.bank?.name}
                                </td>
                                <td>
                                    ${it?.bankAccount?.paymentChannel?.name}
                                </td>

                                <td>
                                    ${it?.bankAccount?.numberOfAccount}
                                </td>
                                <td>
                                    ${it?.bankAccount?.name}
                                </td>
                                <td>
                                    ${it?.bankAccount?.cellphone}
                                </td>
                                <td>
                                    ${it?.bankAccount?.certificateType?.name}
                                </td>
                                <td>
                                    ${it?.bankAccount?.numberOfCertificate}
                                </td>
                                <td>
                                    <g:if test="${it?.bankAccount?.active == true}">是</g:if>
                                    <g:if test="${it?.bankAccount?.active == false}">否</g:if>
                                </td>
                                <td>
                                    ${it?.bankAccount?.bankBranch}
                                </td>
                                <td>
                                    ${it?.bankAccount?.bankAddress}
                                </td>
                                <td>
                                    ${it?.bankAccount?.createdBy}
                                </td>
                                <td>
                                    ${it?.bankAccount?.modifiedBy}
                                </td>
                                <td>
                                    <g:formatDate date="${it?.bankAccount?.createdDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
                                </td>
                                <td>
                                    <g:formatDate date="${it?.bankAccount?.modifiedDate}"
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
                        <div class="table-responsive ">
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
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                主审结果
            </div>

            <div class="panel-body no-padding form-horizontal">
                <div class="form-group">
                    <label class="col-md-3 control-label">审核可贷金额：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.actualAmountOfCredit}
                        <g:if test="${this.opportunity?.actualAmountOfCredit != null}">
                            万元
                        </g:if>
                        </span>
                    </div>
                    <label class="col-md-3 control-label">付息方式：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.interestPaymentMethod?.name}</span>
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">渠道返费：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            <g:each in="${opportunityProduct}">
                                <g:if test="${it?.productInterestType?.name == '渠道返费费率'}">
                                    <g:if test="${!it?.installments}">一次性;<g:formatNumber
                                            number="${it.rate * this.opportunity.actualAmountOfCredit / 100}"
                                            minFractionDigits="2"
                                            maxFractionDigits="9"/>万元</g:if>
                                    <g:else>月月返;<g:formatNumber
                                            number="${it.rate * this.opportunity.actualAmountOfCredit * this.opportunity.actualLoanDuration / 100}"
                                            minFractionDigits="2"
                                            maxFractionDigits="9"/>万元</g:else>
                                </g:if>
                            </g:each>
                        </span>
                    </div>

                    <label class="col-md-3 control-label">客户级别：</label>

                    <div class="col-md-3">
                        <span class="cont">
                            ${this.opportunity?.lender?.level?.description}
                        </span>

                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">实际贷款期限：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.actualLoanDuration}月</span>
                    </div>
                    <label class="col-md-3 control-label">抵押凭证类型：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            ${this.opportunity?.mortgageCertificateType?.name}
                        </span>
                    </div>
                </div>
                <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                    <div class="form-group">
                        <label class="col-md-3 control-label">上扣息月份数：</label>

                        <div class="col-md-2">
                            <span class="cont">
                                ${this.opportunity?.monthOfAdvancePaymentOfInterest}月
                            </span>
                        </div>
                    </div>

                </g:if>

            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                放款落实条件
            </div>

            <div class="panel-body p-xs">
                <div class="comment-area">
                    <g:each in="${this.opportunityFlows}">
                        <g:each in="${this.opportunityRoles}" var="role">
                            <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval' && it?.stage?.name == "贷款审批已完成（CRO）"}">
                                <g:formatDate format="yyyy-MM-dd" date="${it?.endTime}"/></span>
                            </g:if>
                        </g:each>
                    </g:each> 经审批委员会全数通过，同意给予共同借款人：
                    <span>
                        <g:each in="${this.opportunityContacts}">
                            <g:if test="${it?.type?.name == '借款人'}"></g:if>
                            <g:if test="${!(it?.type?.name == '曾用名')}">${it?.contact?.fullName}</g:if>
                        </g:each>
                    </span>贷款。<br/>
                    贷款金额：<g:formatNumber number="${this.opportunity?.actualAmountOfCredit}" minFractionDigits="2"
                                         maxFractionDigits="2"/>万元<br/>
                    贷款期限：${this.opportunity?.actualLoanDuration}月<br/>
                    放款前要求：
                    <g:each in="${this.opportunityFlexFieldCategorys}">
                        <g:if test="${it?.flexFieldCategory?.name == '放款前要求'}">
                            <g:each in="${it?.fields}" var="field">
                                ${field?.value}
                            </g:each>
                        </g:if>
                    </g:each>
                    <br/>
                    <g:each in="${this.opportunityFlows}">
                        <g:each in="${this.opportunityRoles}" var="role">
                            <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval' && it?.stage?.name == "抵押已完成"}">
                                <g:formatDate format="yyyy-MM-dd" date="${it?.endTime}"/></span>
                            </g:if>
                        </g:each>
                    </g:each>
                    共同借款人提供放款前材料，具备放款条件。
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
                权利入库单情况
            </div>

            <div class="panel-body p-xs">
                <div class="comment-area">
                    <g:if test="${this.opportunity?.mortgageCertificateType?.name == '他项证明'}">
                        <dl class="dl-horizontal">
                            <dt>见他项放款：</dt>
                            <dd>他项权利证已落实并收押</dd>
                            <dd>强制执行、售房公证已落实收押</dd>
                            <dd>房产证件已收押</dd>
                        </dl>
                    </g:if>
                    <g:else>
                        <dl class="dl-horizontal">
                            <dt>见收件收据放款：</dt>
                            <dd>强制执行公证已落实</dd>
                            <dd>售房公证已落实</dd>
                            <dd>抵押登记已落实，受理单已收押</dd>
                        </dl>
                    </g:else>

                </div>

                <div class="pull-right m-t-sm">
                    <g:each in="${this.opportunityFlows}">
                        <g:each in="${this.opportunityRoles}" var="role">
                            <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval' && it?.stage?.name == "放款路径已选择"}">

                                <span>审批人：${role?.user}</span>

                                <span>日期：<g:formatDate format="yyyy-MM-dd HH:mm:ss"
                                                       date="${it?.endTime}"/></span>
                            </g:if>
                        </g:each>
                    </g:each>
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
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
                风险调查报告
            </div>

            <div class="panel-body form-horizontal field">
                <g:each in="${this.opportunityFlexFieldCategorys}" var="category">
                    <g:if test="${category?.flexFieldCategory?.name in ['抵押物情况', '借款人资质小结', '征信小结', '大数据小结', '借款用途', '还款来源', '风险结论', '放款前要求', '罚息约定']}">
                        <div class="fieldBox border-bottom">
                            <h5>${category?.flexFieldCategory?.name}</h5>
                            <g:each in="${category.fields}" var="field">
                                <div class="form-group">
                                    <label class="col-md-2 control-label">${field?.name}</label>

                                    <div class="col-md-8">
                                        <p class="form-control-static">${field?.value}</p>
                                    </div>
                                </div>
                            </g:each>
                        </div>
                    </g:if>
                </g:each>

            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                用户
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="user"
                                              title="${message(code: 'opportunityTeam.user.label', default: '用户名')}"></g:sortableColumn>
                            <g:sortableColumn property="createDate"
                                              title="${message(code: 'opportunityTeam.createDate.label', default: '创建时间')}"></g:sortableColumn>

                            <g:sortableColumn property="modifiedDate"
                                              title="${message(code: 'opportunityTeam.modifiedDate.label', default: '修改时间')}"></g:sortableColumn>
                            <g:sortableColumn property="opportunityLayout"
                                              title="${message(code: '布局')}"></g:sortableColumn>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityTeams}">
                            <tr>
                                <td>${it.user}</td>
                                <td>
                                    <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/>
                                </td>
                                <td>
                                    <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.modifiedDate}"/>
                                </td>
                                <td>${it?.opportunityLayout?.description}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                权限
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="user"
                                              title="${message(code: 'opportunityRole.user.label', default: '用户名')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityRole.stage.label', default: '订单阶段')}"/>
                            <g:sortableColumn property="opportunityLayout"
                                              title="${message(code: '布局')}"></g:sortableColumn>
                            <g:sortableColumn property="teamRole"
                                              title="${message(code: 'opportunityRole.teamRole.label', default: '权限')}"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityRoles}">
                            <tr>
                                <td>${it.user}</td>
                                <td>${it.stage?.name}</td>
                                <td>
                                    ${it?.opportunityLayout?.description}
                                </td>
                                <td>${it.teamRole?.name}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                消息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="user"
                                              title="${message(code: 'opportunityNotification.user.label', default: '用户名')}"/>

                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityNotification.stage.label', default: '订单阶段')}"/>
                            <g:sortableColumn property="messageTemplate"
                                              title="${message(code: 'opportunityNotification.messageTemplate.label', default: '消息模板')}"/>
                            <g:sortableColumn property="toManager"
                                              title="${message(code: 'opportunityNotification.toManager.label', default: '推送主管')}"/>
                            <g:sortableColumn property="cellphone"
                                              title="${message(code: 'opportunityNotification.cellphone.label', default: '手机号脚本')}"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityNotifications}">
                            <tr>
                                <td>${it.user}</td>
                                <td>${it.stage?.name}</td>
                                <td>${it.messageTemplate}</td>
                                <td>${it.toManager}</td>
                                <td>
                                    <g:if test="${it?.cellphone}">
                                        <pre>
                                            <code>${it?.cellphone}</code>
                                        </pre>
                                    </g:if>

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
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                工作流
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn width="5%" property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '次序')}"/>
                            <g:sortableColumn width="10%" property="stage"
                                              title="${message(code: 'opportunityFlow.stage.label', default: '订单阶段')}"/>
                            <g:sortableColumn width="5%" property="canReject"
                                              title="${message(code: 'opportunityFlow.canReject.label', default: '能否回退')}"/>
                            <g:sortableColumn width="10%" property="startTime"
                                              title="${message(code: 'opportunityFlow.startTime.label', default: '开始时间')}"/>
                            <g:sortableColumn width="10%" property="endTime"
                                              title="${message(code: 'opportunityFlow.endTime.label', default: '结束时间')}"/>
                            <g:sortableColumn width="45%" property="comments"
                                              title="${message(code: 'opportunityFlow.comments.label', default: '备注')}"/>
                            <g:sortableColumn width="7%" property="opportunityLayout"
                                              title="${message(code: 'opportunityFlow.opportunityLayout.label', default: '布局')}"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityFlows}">
                            <tr>
                                <td width="5%">
                                    <g:link controller="opportunityFlow" action="show"
                                            id="${it.id}">${it?.executionSequence}</g:link>
                                </td>
                                <td width="10%">${it?.stage?.name}</td>
                                <td width="5%">${it?.canReject}</td>
                                <td width="10%">
                                    <g:formatDate class="weui_input" date="${it?.startTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="startTime" autocomplete="off"
                                                  readonly="true"></g:formatDate>
                                </td>
                                <td width="10%">
                                    <g:formatDate class="weui_input" date="${it?.endTime}"
                                                  format="yyyy-MM-dd HH:mm:ss"
                                                  name="endTime" autocomplete="off" readonly="true"></g:formatDate>
                                </td>
                                <td width="45%">
                                    <p class="text-comment">${it?.comments}</p>
                                </td>
                                <td width="5%">
                                    <p class="text-comment">${it?.opportunityLayout?.description}</p>
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
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                订单跟踪
            </div>

            <div class="panel-body no-padding">
                <div class="v-timeline vertical-container animate-panel" data-child="vertical-timeline-block"
                     data-delay="1">
                    <g:each in="${this.opportunityFlows}">
                        <g:each in="${this.opportunityRoles}" var="role">
                            <g:if test="${it?.executionSequence < currentFlow?.executionSequence && it?.endTime}">
                                <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval'}">
                                    <div class="vertical-timeline-block">
                                        <div class="vertical-timeline-icon navy-bg">
                                            <i class="fa fa-calendar text-primary"></i>
                                        </div>

                                        <div class="vertical-timeline-content">
                                            <div class="p-sm">
                                                <span class="vertical-date pull-right">${role?.user} <br/> <small><g:formatDate
                                                        format="yyyy-MM-dd HH:mm:ss" date="${it?.endTime}"/></small>
                                                </span>

                                                <h2>${it?.stage?.name}</h2>
                                            </div>
                                        </div>
                                    </div>
                                </g:if>
                            </g:if>
                        </g:each>

                    </g:each>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>

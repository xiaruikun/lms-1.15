<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>原订单详情页-PLM07</title>
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
                【${this.opportunity?.stage?.description}】
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
                            <th>借款期数（月）</th>

                            <th>产品类型</th>
                            <th>共同借款人</th>
                            <th>客户类型</th>
                            <th>实际月息（%）</th>
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
                                ${this.opportunity?.loanDuration}
                            </td>
                            <td>${this.opportunity?.product?.name}</td>
                            <td>
                                <g:each in="${this.opportunityContacts}">${it?.contact?.fullName}、</g:each>
                            </td>
                            <td>${this.opportunity?.lender?.level?.description}</td>

                            <td><g:formatNumber number="${this.opportunity?.monthlyInterest}"
                                                maxFractionDigits="9"/>%</td>
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
                            <td class="cellphoneFormat">${this.opportunity?.contact?.cellphone}</td>
                            <td>${this.opportunity?.user}</td>
                            <td class="cellphoneFormat">${this.opportunity?.user?.cellphone}</td>
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
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                房产信息
            </div>

            <div class="panel-body collateral">
                <g:each in="${this.collaterals}">

                    <div class="item">
                        <p>
                            第<span class="collateralOrder"></span>套房产信息（ 评估单价：
                            <span class="text-info"><g:formatNumber number="${it?.unitPrice}" type="number"
                                                                    maxFractionDigits="2"
                                                                    minFractionDigits="2"/></span>元 ；
                        评估总价： <span class="text-info"><g:formatNumber number="${it?.totalPrice}" type="number"
                                                                      maxFractionDigits="2"
                                                                      minFractionDigits="2"/></span>万元；
                        询值状态：<span class="text-info collateralStatus">${it?.status}</span> ）
                        </p>

                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover text-center">
                                <thead>
                                <tr>
                                    <g:sortableColumn property="externalId" title="外部唯一ID"></g:sortableColumn>
                                    <g:sortableColumn property="city" title="城市"></g:sortableColumn>
                                    <g:sortableColumn property="district" title="区县"></g:sortableColumn>
                                    <g:sortableColumn property="region" title="所在区域"></g:sortableColumn>
                                    <g:sortableColumn property="projectName" title="小区名称"></g:sortableColumn>
                                    <g:sortableColumn property="address" title="房本地址"></g:sortableColumn>
                                    <g:sortableColumn property="building" title="楼栋"></g:sortableColumn>
                                    <g:sortableColumn property="unit" title="单元"></g:sortableColumn>
                                    <g:sortableColumn property="roomNumber" title="户号"></g:sortableColumn>
                                    <g:sortableColumn property="floor" title="所在楼层"></g:sortableColumn>
                                    <g:sortableColumn property="totalFloor" title="总楼层"></g:sortableColumn>

                                    <g:sortableColumn property="area" title="面积（m2）"></g:sortableColumn>
                                    <g:sortableColumn property="orientation" title="朝向"></g:sortableColumn>

                                    <g:sortableColumn property="houseType" title="物业类型"></g:sortableColumn>
                                    <g:sortableColumn property="assetType" title="房产类型"></g:sortableColumn>
                                    <g:sortableColumn property="specialFactors" title="特殊因素"></g:sortableColumn>
                                    <g:sortableColumn property="mortgageType" title="抵押类型"></g:sortableColumn>
                                    <g:sortableColumn property="typeOfFirstMortgage" title="一抵性质"></g:sortableColumn>
                                    <g:sortableColumn property="firstMortgageAmount"
                                                      title="一抵金额(万元)"></g:sortableColumn>
                                    <g:sortableColumn property="secondMortgageAmount"
                                                      title="二抵金额(万元)"></g:sortableColumn>
                                    <g:sortableColumn property="loanToValue" title="抵押率（%）"></g:sortableColumn>
                                    <g:sortableColumn property="loanToValue" title="房产编号"></g:sortableColumn>

                                    <g:sortableColumn property="projectType" title="立项类型"></g:sortableColumn>
                                    <g:sortableColumn property="buildTime" title="建成时间"></g:sortableColumn>
                                    <g:sortableColumn property="buildTime" title="房龄(年)"></g:sortableColumn>
                                </tr>
                                </thead>
                                <tbody>

                                <tr>
                                    <td>
                                        ${it.externalId}
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
                                        ${it.area}
                                    </td>
                                    <td>
                                        ${it.orientation}
                                    </td>

                                    <td>
                                        ${it.houseType}
                                    </td>
                                    <td>
                                        ${it.assetType}
                                    </td>
                                    <td>
                                        ${it.specialFactors}
                                    </td>
                                    <td>
                                        ${it?.mortgageType?.name}
                                    </td>
                                    <td>
                                        ${it?.typeOfFirstMortgage?.name}
                                    </td>
                                    <td>
                                        ${it?.firstMortgageAmount}
                                    </td>
                                    <td>
                                        ${it?.secondMortgageAmount}
                                    </td>
                                    <td>
                                        <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                                        minFractionDigits="2"/>

                                    </td>
                                    <td>
                                        ${it?.propertySerialNumber}
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
                                </tr>

                                </tbody>
                            </table>
                        </div>

                    </div>

                </g:each>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.opportunity.contacts?.size() > 0}">
                        <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                action="creditReportShow" id="${this.opportunity.id}"><i
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
                                <td class="cellphoneFormat">
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
                                </td>
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
    <g:if test="${this.canAttachmentsShow}">
        <div class="row">
            <div class="hpanel hyellow">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    附件信息
                </div>

                <div class="panel-body float-e-margins">
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '基础材料']">基础材料</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '其他资料']">其他资料</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '征信']">征信</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '大数据']">大数据</g:link>

                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '产调结果']">产调结果</g:link>
                    <g:link target="_blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '签呈']">签呈</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="opportunityFlexField"
                            id="${this.opportunity?.id}"
                            action="opportunityFlexField01" target=" _blank"><i class="fa"></i>外访报告</g:link>
                    <g:link target=" _blank" controller="attachments"
                            action="show"
                            id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '抵押合同全委']"
                            class="btn btn-outline btn-primary">抵押、合同、全委</g:link>

                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '其他放款要求资料']"
                            class="btn btn-outline btn-primary">其他放款要求资料</g:link>
                </div>
            </div>
        </div>
        <</g:if>


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
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
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
                                    ${it?.createdDate}
                                </td>
                                <td>
                                    ${it?.modifiedDate}
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
                                <td class="cellphoneFormat">
                                    ${it?.bankAccount?.cellphone}
                                </td>
                                <td>
                                    ${it?.bankAccount?.certificateType?.name}
                                </td>
                                <td>
                                    ${it?.bankAccount?.numberOfCertificate}
                                </td>
                                <td>
                                    ${it?.bankAccount?.active}
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
                                    ${it?.bankAccount?.createdDate}
                                </td>
                                <td>
                                    ${it?.bankAccount?.modifiedDate}
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
        <div class="hpanel collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                风险调查报告
            </div>

            <div class="panel-body no-padding" style="border-top: 2px solid #3498db">
                <input type="hidden" name="opportunity" id="opportunity" value="${this.opportunity?.id}">
                <g:each in="${this.opportunityFlexFieldCategorys}" var="category">
                    <g:if test="${category?.flexFieldCategory?.name in ['抵押物情况', '借款人资质小结', '征信小结', '大数据小结', '借款用途', '还款来源', '风险结论', '放款前要求', '罚息约定']}">
                        <div class="hpanel">
                            <div class="panel-heading hbuilt" style="border-width:1px 0">
                                ${category?.flexFieldCategory?.name}
                            </div>

                            <div class="panel-body" style="border-width:1px 0;padding: 10px 20px">
                                <g:each in="${category.fields}" var="field">
                                    <div style="padding: 2px 0">${field?.name}：${field?.value}</div>
                                </g:each>
                            </div>
                        </div>
                    </g:if>
                </g:each>

            </div>

        </div>
    </div>
</div>

</div>

</body>

</html>

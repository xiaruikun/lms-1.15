<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>（北京-上海）贷款放款详情页3.0-27</title>
    <style>
    .borderActive {
        border-width: 1px 0 0 0 !important;
        border-color: #e4e5e7 !important;
        padding: 10px !important;
    }

    .checkboxGroup {
        display: inline-block;
        padding: 7px 10px;
    }
    </style>
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
                            <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                            </g:if>
                            <g:else>
                                <g:sortableColumn width="8%" class="text-center" property="operation"
                                                  title="操作"></g:sortableColumn>
                            </g:else>

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
                                %{--<sec:ifAllGranted roles="ROLE_ADMINISTRATOR">--}%
                                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,it.opportunity.stage)?.executionSequence}">
                                </g:if>
                                <g:else>
                                    <td width="8%" class="text-center">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs  btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </g:else>

                                %{--</sec:ifAllGranted>--}%
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
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence >= com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                        <g:link class="btn btn-info btn-xs" controller="opportunityBankAccount" action="create"
                                params="[opportunity: this.opportunity.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </g:if>

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
                            <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                            </g:if>
                            <g:else>
                                <g:sortableColumn property="bankBranch" title="操作"></g:sortableColumn>
                            </g:else>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.bankAccounts}">
                            <tr>
                                <td>
                                    <g:link controller="opportunityBankAccount" action="show"
                                            id="${it?.id}">${it?.type?.name}</g:link>
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
                                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,it.opportunity.stage)?.executionSequence}">
                                </g:if>
                                <g:else>
                                    <td>
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </g:else>
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
    <g:if test="${this.canAttachmentsShow}">
        <div class="row">
            <div class="hpanel hyellow">
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
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '意向申请单']">意向申请单</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '银行卡复印件']">银行卡复印件</g:link>

                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '公证书']">公证书</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '被执情况']">被执情况</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '二抵：抵押物银行按揭贷款合同']">二抵按揭贷款合同</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '企业信息截图']">企业信息截图</g:link>
                    <g:if test="${ com.next.OpportunityFlexField.executeQuery("select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${this.opportunity.id} and c.flexFieldCategory.id = 13) and o.name = '放款通道'")[0]=="外贸信托"}">
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
                    </g:if>
                    <g:else>
                        <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                action="show" id="${this.opportunity.id}"
                                params="[attachmentTypeName: '签约录像']">签约录像</g:link>
                        <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                action="show" id="${this.opportunity.id}"
                                params="[attachmentTypeName: '借款用途证明']">借款用途证明</g:link>
                    </g:else>
                </div>
            </div>
        </div>
    </g:if>
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

    <g:render template="/layouts/opportunityTemplate/opportunityContractsTemplate"/>
    <g:render template="/layouts/opportunityTemplate/riskInvestigationReportTemplate"/>
    <g:render template="/layouts/opportunityTemplate/opportunityFlowsTemplate"/>
</div>

</div>
<script>
    $(function () {
        $.each($(".order1"), function (i, obj) {
            $(obj).text(i + 1)
        });
        $.each($(".order2"), function (i, obj) {
            $(obj).text(i + 1)
        });
    })
</script>
</body>

</html>

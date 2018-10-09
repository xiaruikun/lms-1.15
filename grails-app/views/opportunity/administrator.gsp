<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>administrator</title>
    <style>
    .select2-container .select2-choice {
        height: 30px;
    !important;
        line-height: 30px;
    }

    .select2-chosen, .select2-choice > span:first-child, .select2-container .select2-choices .select2-search-field input {
        padding: 0 12px !important;
    }
    </style>
</head>

<body>
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body clearfix">
            <h2 class="font-light pull-left">
                订单号: ${this.opportunity.serialNumber}
            </h2>

            <div id="navbar-example" class="pull-right href-link">
                <ul class="nav navbar-nav" role="tablist">
                    %{--<li class="sameCollaterals"><span class="btn btn-xs btn-info collateralNum"></span></li>--}%
                    <li class="active"><a class="btn-link page-scroll" href="#first">订单基本信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#second">报单人信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#third">房产信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fourth">借款人及抵押人信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fifth">费用</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#sixth">附件信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#seventh">任务指派</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>

<div class="content active animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <g:hasErrors bean="${this.opportunityTeam}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunityTeam}" var="error">
                    <li>
                        <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>
                        <g:message error="${error}"/>
                    </li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <div id="hbreadcrumb" class="pull-left">
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

        <div class="pull-right">
            <g:if test="${this.currentFlow?.document && this?.currentFlow?.document?.active}">
                <input type="hidden" value="${this?.currentFlow?.document?.document}" id="document">
                <button class="btn btn-success btn-xs" data-toggle="modal" data-target="#myModaDocument">帮助文档</button>
            </g:if>
            <g:else>
                <button class="btn btn-default btn-xs" disabled="disabled">帮助文档</button>
            </g:else>
        </div>

    </div>

    <div class="row" id="first">
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="workflowInstance"
                                params="[opportunity: this.opportunity?.id]"
                                action="index"></i>查看工作流2.0</g:link>
                        <g:link class="btn btn-info btn-xs" controller="workflowInstance"
                                params="[opportunity: this.opportunity?.id]"
                                action="create"><i class="fa fa-plus"></i>新增工作流2.0</g:link>
                        <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                                action="edit"><i class="fa fa-edit"></i>编辑</g:link>
                    </sec:ifNotGranted>
                </div>
                订单基本信息
            </div>

            <div class="panel-body">
                <h4>
                    ${this.opportunity.serialNumber}
                    (<g:if test="${this.opportunity?.type}">${this.opportunity?.type?.name}</g:if>
                    <g:else>${com.next.OpportunityType.findByCode('10')?.name}</g:else>)
                    <g:if test="${this.opportunity?.isTest}">
                        <span class="label label-danger pull-right"><i class="fa fa-star"></i> 测试 <i
                                class="fa fa-star"></i>
                        </span>
                    </g:if>
                </h4>

                <div class="col-md-1">
                    <strong>
                        <span class="glyphicon glyphicon-user"
                              aria-hidden="true"></span> ${this.opportunity?.fullName}
                    </strong>
                </div>
                <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                    <div class="col-md-1">
                        <span class="fa fa-chain"></span>${this.opportunity?.protectionEndTime - this.opportunity?.protectionStartTime}天
                    </div>
                </g:if>

                <div class="col-md-2">
                    <strong>${this.opportunity?.stage?.description}</strong>
                </div>

                <div class="col-md-2">
                    <strong>抵押凭证类型：${this.opportunity?.mortgageCertificateType?.name}</strong>
                </div>
                <g:if test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('普通签呈'))}">
                    <div class="col-md-2">
                        <strong>
                            签呈类型：普通签呈
                        </strong>
                    </div>

                </g:if>
                <g:elseif
                        test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('特批签呈'))}">
                    <div class="col-md-2">
                        <strong>
                            签呈类型：特批签呈
                        </strong>
                    </div>
                </g:elseif>
                <div class="col-md-1">
                    <strong>是否合规检查：<g:if test="${this.opportunity?.complianceChecking == true}">是</g:if>
                        <g:if test="${this.opportunity?.complianceChecking == false}">否</g:if></strong>
                </div>

                <g:if test="${this.opportunity?.jqStatus != null && this.opportunity?.jqStatus == '手动结清'}">
                    <div class="col-md-2">
                        <strong>结清状态：${this.opportunity?.jqStatus}</strong>
                    </div>
                </g:if>

                <g:if test="${this.opportunity?.importDate}">
                    <div class="col-md-2">
                        <strong>导入时间：<g:formatDate format="yyyy-MM-dd HH:mm:ss "
                                                   date="${this.opportunity?.importDate}"/></strong>
                    </div>
                </g:if>
                <g:if test="${this.opportunity?.externalModifiedDate}">
                    <div class="col-md-2">
                        <strong>外部修改时间：<g:formatDate format="yyyy-MM-dd HH:mm:ss "
                                                     date="${this.opportunity?.externalModifiedDate}"/></strong>
                    </div>
                </g:if>

                <g:if test="${this.opportunity?.status == 'Failed'}"><span
                        class="label label-danger pull-right">订单结果：失败</span></g:if>
                <g:elseif test="${this.opportunity?.status == 'Completed'}"><span
                        class="label label-success pull-right">订单结果：成功</span></g:elseif>
                <g:else><span class="label label-info pull-right">订单结果：进行中</span></g:else>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>拟贷款期限</span>
                            <strong>${this.opportunity?.loanDuration}月</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>实际贷款期限</span>
                            <strong>${this.opportunity?.actualLoanDuration}月</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>评房（可贷）</span>
                            <strong><g:formatNumber number="${this.opportunity?.loanAmount}" minFractionDigits="2"
                                                    maxFractionDigits="2"/>万元</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>初审</span>
                            <strong>${this.opportunity?.lender?.level?.description}</strong>
                        </div>
                    </div>

                    <div class="col-md-4 border-right">
                        <div class="contact-stat">
                            <span>审核可贷金额</span>
                            <strong>${this.opportunity?.actualAmountOfCredit}万元；${this.opportunity?.interestPaymentMethod?.name}；
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                上扣息月份数:${this.opportunity?.monthOfAdvancePaymentOfInterest}个月；
                            </g:if>
                            <!-- 借款服务费<g:formatNumber
                                    number="${this.opportunity?.serviceCharge}"
                                    maxFractionDigits="9"/>% -->
                            渠道服务费<g:formatNumber
                                    number="${this.opportunity?.commissionRate}"
                                    maxFractionDigits="9"/>%；${this.opportunity?.commissionPaymentMethod}；
                            <!-- 实际月息<g:formatNumber number="${this.opportunity?.monthlyInterest}"
                                                     maxFractionDigits="9"/>% -->

                            </strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>产品类型:</span>
                            <strong>${this.opportunity?.product?.name}</strong>
                        </div>
                    </div>


                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>一抵金额</span>
                            <strong>${this.opportunity?.firstMortgageAmount}万元</strong>
                        </div>
                    </div>

                    <div class="col-md-1">
                        <div class="contact-stat">
                            <span>二抵金额</span>
                            <strong>${this.opportunity?.secondMortgageAmount}万元</strong>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div class="row" id="second">
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                报单人信息
            </div>

            <div class="panel-body">
                <div class="row">
                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>姓名</span>
                            <strong>
                                ${this.opportunity?.contact?.fullName}
                            </strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>手机号</span>
                            <strong class="cellphoneFormat">${this.opportunity?.contact?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>所属公司</span>
                            <strong>${this.opportunity?.contact?.account?.name}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>销售姓名</span>
                            <strong>${this.opportunity?.user?.fullName}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>销售组别</span>
                            <strong>${this.opportunity?.user?.account?.name}</strong>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="contact-stat">
                            <span>外部唯一ID</span>
                            <strong>${this.opportunity?.contact?.externalId}</strong>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <g:render template="/layouts/opportunityTemplate/collateralsTemplate1"/>
    <div class="row" id="fourth">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:if test="${this.opportunity.contacts?.size() > 0}">
                            <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                    action="creditReportShow" id="${this.opportunity.id}"><i
                                    class="fa fa-database"></i>大数据</g:link>
                        </g:if>
                    %{--  <g:if test="${this.opportunity.contacts?.size() > 0}"
                             <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                     action="creditReportShow" id="${this.opportunity.id}"><i
                                     class="fa fa-database"></i>大数据</g:link>
                         </g:if>--}%
                        <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence >= com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                            <g:link class="btn btn-info btn-xs" controller="opportunityContact" action="create"
                                    params="[opportunity: this.opportunity.id]"><i class="fa fa-plus"></i>新增</g:link>
                        </g:if>

                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                借款人及抵押人信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="type" title="联系人"></g:sortableColumn>
                            <g:sortableColumn property="fullName" title="姓名"></g:sortableColumn>
                            <g:sortableColumn property="idNumber" title="身份证号"></g:sortableColumn>
                            <g:sortableColumn property="cellphone" title="手机号"></g:sortableColumn>
                            <g:sortableColumn property="maritalStatus" title="婚否"></g:sortableColumn>
                            <g:sortableColumn property="age" title="年龄(岁)"></g:sortableColumn>
                            <g:sortableColumn property="profession" title="职业"></g:sortableColumn>
                            <g:sortableColumn property="country" title="国籍"></g:sortableColumn>
                            <g:sortableColumn property="identityType" title="身份证件类型"></g:sortableColumn>
                            <g:sortableColumn property="connnectedContact" title="关联人"></g:sortableColumn>
                            <g:sortableColumn property="connectedType" title="关联关系"></g:sortableColumn>
                            <sec:ifNotGranted roles="ROLE_COO">
                                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                                    <g:sortableColumn width="8%" class="text-center" property="operation"
                                                      title="操作"></g:sortableColumn>
                                </g:if>
                                <g:else>
                                    <g:sortableColumn width="8%" class="text-center" property="operation" colspan="2"
                                                      title="操作"></g:sortableColumn>
                                </g:else>
                            </sec:ifNotGranted>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityContacts}">
                            <tr>
                                <td>
                                    <sec:ifNotGranted roles="ROLE_COO">
                                        <g:link controller="opportunityContact" action="show"
                                                id="${it?.id}">${it?.type?.name}</g:link>
                                    </sec:ifNotGranted>
                                    <sec:ifAllGranted roles="ROLE_COO">
                                        ${it?.type?.name}
                                    </sec:ifAllGranted>
                                </td>
                                <td>
                                    ${it?.contact?.fullName}
                                </td>
                                <td>
                                    ${it?.contact?.idNumber}
                                </td>
                                <td class="cellphoneFormat">
                                    ${it?.contact?.cellphone}
                                </td>
                                <td>
                                    ${it?.contact?.maritalStatus}
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
                                    ${it?.contact?.profession?.name}
                                </td>
                                <td>
                                    ${it?.contact?.country?.name}
                                </td>
                                <td>
                                    ${it?.contact?.identityType?.name}
                                </td>
                                <td>
                                    ${it?.connectedContact?.fullName}
                                </td>
                                <td>
                                    ${it?.connectedType?.name}
                                </td>
                                <sec:ifNotGranted roles="ROLE_COO">
                                    <td class="text-center">
                                      <g:link class="btn btn-primary btn-outline btn-xs" controller="opportunityContact" action="show"
                                              id="${it?.id}">央行征信</g:link>

                                    </td>
                                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,it.opportunity.stage)?.executionSequence}">
                                    </g:if>
                                    <g:else>
                                        <td class="text-center">
                                            <g:form resource="${it}" method="DELETE">
                                                <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                        class="fa fa-trash-o"></i> 删除</button>
                                            </g:form>
                                        </td>
                                    </g:else>

                                </sec:ifNotGranted>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <g:render template="/layouts/opportunityTemplate/opportunityProductTemplate"/>
    <g:render template="/layouts/opportunityTemplate/billsItemsTemplate"/>


    <g:render template="/layouts/opportunityTemplate/bankAccountsTemplate"/>
    <g:render template="/layouts/opportunityTemplate/opportunityContractsTemplate"/>
    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR,ROLE_POST_LOAN_MANAGER">
        <div class="row" id="sixth">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <div class="btn btn-info btn-xs" data-toggle="modal" data-target="#isJieqing">
                            <i class="fa fa-edit"></i>编辑
                        </div>
                    </div>
                    结清状态
                </div>
                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover text-center">
                            <thead>
                            <tr>
                                <g:sortableColumn property="type" title="是否结清"></g:sortableColumn>

                                <g:sortableColumn property="time" title="结清时间"></g:sortableColumn>
                            </tr>
                            </thead>
                            <tbody>
                            <g:if test="${this.opportunity?.jqStatus ||this.opportunity?.jqDate?.format("yyyy-MM-dd")}">
                                <tr>
                                    <td>
                                        ${this.opportunity?.jqStatus}
                                    </td>
                                    <td>
                                        ${this.opportunity?.jqDate?.format("yyyy-MM-dd")}
                                    </td>
                                </tr>
                            </g:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </sec:ifAnyGranted>
    <g:render template="/layouts/opportunityTemplate/transactionRecordsTemplate"/>

    <div class="row" id="sixth">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="attachments" action="create"
                                id="${this.opportunity.id}"><i class="fa fa-upload"></i>上传</g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                附件信息
            </div>

            <div class="panel-body no-padding">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="horange">
                            <div class="panel-body float-e-margins" style="border-top: none;">
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '房产证']">房产证</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '身份证']">身份证</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '户口本']">户口本</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '婚姻证明']">婚姻证明</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '被执情况']">被执情况</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '产调结果']">产调结果</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '征信报告']">征信报告</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '大数据']">大数据</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '借款合同']">借款合同</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '公证书']">公证书</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '他项证明']">他项证明</g:link>

                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '其他资料']">其他资料</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '抵押登记受理单']">抵押登记受理单</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '特批签呈']">特批签呈</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '普通签呈']">普通签呈</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '委托借款代理服务合同']">委托借款代理服务合同</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '银行卡复印件']">银行卡复印件</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '意向申请单']">意向申请单</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '公证受理单']">公证受理单</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '还款告知书']">还款告知书</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '抵押合同']">抵押合同</g:link>
                                <g:link target=" _blank" controller="attachments" action="show"
                                        id="${this.opportunity?.id}"
                                        params="[attachmentTypeName: '其他放款要求资料']"
                                        class="btn btn-outline btn-primary">其他放款要求资料</g:link>
                                <g:link target=" _blank" controller="attachments" action="show"
                                        id="${this.opportunity?.id}"
                                        params="[attachmentTypeName: '放款回单']"
                                        class="btn btn-outline btn-primary">放款回单</g:link>
                                <g:link class="btn btn-outline btn-primary" controller="opportunityFlexField"
                                        id="${this.opportunity?.id}"
                                        action="opportunityFlexField01" target=" _blank"></i>外访报告</g:link>
                                <g:link target=" _blank" controller="attachments" action="show"
                                        id="${this.opportunity?.id}"
                                        params="[attachmentTypeName: '央行征信（中佳信）']"
                                        class="btn btn-outline btn-primary">央行征信（中佳信）</g:link>
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
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
                                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                        action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '结清证明']">结清证明</g:link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="opportunityTeam" action="create"
                                params="[opportunity: this.opportunity?.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
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
                            <sec:ifNotGranted roles="ROLE_COO">
                                <g:sortableColumn width="8%" class="text-center" property="option"
                                                  title="${message(code: '操作')}"></g:sortableColumn>
                            </sec:ifNotGranted>

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
                                <sec:ifNotGranted roles="ROLE_COO">
                                    <td width="8%" class="text-center">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifNotGranted>
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
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="opportunityRole" action="create"
                                params="[opportunity: this.opportunity?.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
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
                            <sec:ifNotGranted roles="ROLE_COO">
                                <g:sortableColumn width="8%" class="text-center" property="option"
                                                  title="${message(code: '操作')}"></g:sortableColumn>
                            </sec:ifNotGranted>

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
                                <sec:ifNotGranted roles="ROLE_COO">
                                    <td width="8%" class="text-center">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifNotGranted>
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
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="opportunityNotification" action="create"
                                params="[opportunity: this.opportunity?.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
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
                            <sec:ifNotGranted roles="ROLE_COO">
                                <g:sortableColumn width="8%" class="text-center" property="option"
                                                  title="${message(code: '操作')}"></g:sortableColumn>
                            </sec:ifNotGranted>

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
                                <sec:ifNotGranted roles="ROLE_COO">
                                    <td width="8%" class="text-center">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifNotGranted>
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
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="opportunityFlow" action="create"
                                params="[opportunity: this.opportunity?.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
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
                            <sec:ifNotGranted roles="ROLE_COO">
                                <g:sortableColumn colspan="2" class="text-center" width="8%" property="comments"
                                                  title="操作"/>
                            </sec:ifNotGranted>

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
                                <sec:ifNotGranted roles="ROLE_COO">
                                    <td>
                                        <g:link controller="opportunityFlow" action="edit"
                                                class="btn btn-xs btn-primary btn-outline"
                                                id="${it.id}">
                                            <i class="fa fa-edit"></i> 编辑
                                        </g:link>
                                    </td>
                                    <td>
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifNotGranted>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>


    <g:render template="/layouts/opportunityTemplate/activitiesTemplate"/>

    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="opportunityFlexFieldCategory"
                                params="[opportunity: this.opportunity?.id]" action="create"><i
                                class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                修改弹性域
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="flexFieldCategory"
                                              title="${message(code: 'opportunityFlexFieldCategory.flexFieldCategory.label', default: '弹性域')}"/>
                            <sec:ifNotGranted roles="ROLE_COO">
                                <g:sortableColumn class="text-center" width="10%" property="operation"
                                                  title="操作"></g:sortableColumn>
                            </sec:ifNotGranted>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityFlexFieldCategorys}">
                            <tr>
                                <td>
                                    <g:link controller="opportunityFlexFieldCategory" action="show" id="${it.id}"
                                            class="firstTd">${it?.flexFieldCategory?.name}</g:link>
                                </td>
                                <sec:ifNotGranted roles="ROLE_COO">
                                    <td class="text-center" width="10%">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifNotGranted>
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
                操作记录
            </div>

            <div class="panel-body no-padding">
                <div class="v-timeline vertical-container animate-panel" data-child="vertical-timeline-block"
                     data-delay="1">
                    <g:each in="${this.history}">
                        <div class="vertical-timeline-block">
                            <div class="vertical-timeline-icon navy-bg">
                                <i class="fa fa-calendar text-primary"></i>
                            </div>

                            <div class="vertical-timeline-content">
                                <div class="p-sm">
                                    <span class="vertical-date pull-right">${it?.modifiedBy?.fullName} <br/> <small><g:formatDate
                                            format="yyyy-MM-dd HH:mm:ss" date="${it.modifiedDate}"/></small></span>

                                    <h2><g:link controller="opportunity" action="historyShow"
                                                id="${it.id}"><p>${it?.stage?.name}</p></g:link></h2>
                                </div>
                            </div>
                        </div>
                    </g:each>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="footer bg-success">
    <sec:ifNotGranted roles="ROLE_COO">
        <g:render template="/layouts/opportunityTemplate/footerLeftTemplate"/>
        <div class="pull-left m-b-xs">
            <g:form controller="opportunity" action="replaceFlow">
                <input class="form-control" name="opportunity" value="${this.opportunity?.id}" type="hidden">
                <g:select name="workflow" from="${this.opportunityWorkflows}" optionKey="id" optionValue="name"/>
                <button class="btn btn-sm btn-warning m-l-sm" type="submit" name="operation"
                        value="replace">替换工作流</button>
                <button class="btn btn-sm btn-danger m-l-sm" type="submit" name="operation"
                        value="reset">重置工作流</button>
            </g:form>
        </div>

        <div class="pull-left m-b-xs m-l-xs">
            <g:form controller="opportunity" action="changeOpportunityStage">
                <input class="form-control" name="opportunity" value="${this.opportunity?.id}" type="hidden">
                <g:select name="opportunityStage" from="${com.next.OpportunityStage.list()}" optionKey="id"
                          optionValue="name" value="${this.opportunity?.stage?.id}"/>
                <button class="btn btn-sm btn-danger m-l-sm" type="submit">订单阶段跳转</button>
            </g:form>
        </div>

        <div class="pull-left m-b-xs m-l-xs">
            <g:form controller="opportunity" action="changeOpportunityStatus">
                <input class="form-control" name="opportunity" value="${this.opportunity?.id}" type="hidden">
                <g:select name="opportunityStatus" from="${['Pending', 'Completed', 'Failed']}"
                          value="${this.opportunity?.status}"/>
                <button class="btn btn-sm btn-success m-l-sm" type="submit">订单状态修改</button>
            </g:form>
        </div>

        %{-- <div class="pull-left m-b-xs m-l-xs">
            <g:form controller="opportunity" action="exportCommissionInfo">
                <button class="btn btn-sm btn-success m-l-sm" type="submit">导出佣金基础信息</button>
            </g:form>
        </div>

        <div class="pull-left m-b-xs m-l-xs">
            <g:form controller="opportunity" action="exportCommissionInfo2">
                <button class="btn btn-sm btn-success m-l-sm" type="submit">导出返费基础信息</button>
            </g:form>
        </div> --}%

        <g:render template="/layouts/opportunityTemplate/footerRightTemplate"/>
    </sec:ifNotGranted>

</footer>


<script>
    $(function () {
        $("body").addClass("fixed-small-header");
        /*searchSameCollaterals();*/
    });
    /*function searchSameCollaterals() {
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
                            confirmButtonText: "确认"
                        });
                    }
                },
                error: function () {
                    swal("获取相同房产证订单失败，请稍后重试", "", "error");
                }
            });
        }
    );*/
</script>
</body>

</html>

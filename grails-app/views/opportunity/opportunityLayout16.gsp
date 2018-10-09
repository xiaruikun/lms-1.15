<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>普通页3.0-16</title>
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
                    <li class="active"><a class="btn-link page-scroll" href="#first">订单基本信息</a></li>
                    <li><a class="btn-link page-scroll" href="#second">报单人信息</a></li>
                    <li><a class="btn-link page-scroll" href="#third">房产信息</a></li>
                    <li><a class="btn-link page-scroll" href="#fourth">借款人及抵押人信息</a></li>
                    <li><a class="btn-link page-scroll" href="#fifth">费用</a></li>
                    <g:if test="${this.canAttachmentsShow}">
                        <li><a class="btn-link page-scroll" href="#sixth">附件信息</a></li>
                    </g:if>
                    <li><a class="btn-link page-scroll" href="#seventh">任务指派</a></li>
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
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="edit01"><i class="fa fa-edit"></i>编辑</g:link>
                </div>
                订单基本信息
            </div>

            <div class="panel-body">
                <h4>${this.opportunity.serialNumber}(<g:if
                        test="${this.opportunity?.type}">${this.opportunity?.type?.name}</g:if>
                    <g:else>${com.next.OpportunityType.findByCode('10')?.name}</g:else>)
                    <g:if test="${this.opportunity?.isTest}">
                        <span class="label label-danger pull-right"><i class="fa fa-star"></i> 测试 <i
                                class="fa fa-star"></i></span>
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

                <g:if test="${this.opportunity?.jqStatus != null && this.opportunity?.jqStatus == "手动结清" }">
                    <div class="col-md-2">
                        <strong>结清状态：${this.opportunity?.jqStatus}</strong>
                    </div>
                </g:if>


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

                    <div class="col-md-3 border-right">
                        <div class="contact-stat">
                            <span>审核可贷金额</span>
                            <strong>${this.opportunity?.actualAmountOfCredit}万元；${this.opportunity?.interestPaymentMethod?.name}；
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                上扣息月份数:${this.opportunity?.monthOfAdvancePaymentOfInterest}个月;
                            </g:if>
                            <!-- 实际月息<g:formatNumber number="${this.opportunity?.monthlyInterest}"
                                                     maxFractionDigits="9"/>% -->
                            </strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
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

                    <div class="col-md-1 border-right">
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
                            <g:sortableColumn property="age" title="年龄"></g:sortableColumn>
                            <g:sortableColumn property="profession" title="职业"></g:sortableColumn>
                            <g:sortableColumn property="country" title="国籍"></g:sortableColumn>
                            <g:sortableColumn property="identityType" title="身份证件类型"></g:sortableColumn>
                            <g:sortableColumn property="connnectedContact" title="关联人"></g:sortableColumn>
                            <g:sortableColumn property="connectedType" title="关联关系"></g:sortableColumn>
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

                                <!-- <td class="cellphoneFormat">
                                        ${it?.contact?.cellphone}
                                </td> -->
                                <sec:ifAnyGranted roles="ROLE_BRANCH_OFFICE_CASHIER">
                                    <td>
                                        ${it?.contact?.cellphone}
                                    </td>

                                </sec:ifAnyGranted>
                                <sec:ifNotGranted roles="ROLE_BRANCH_OFFICE_CASHIER">
                                    <td class="cellphoneFormat">
                                        ${it?.contact?.cellphone}
                                    </td>

                                </sec:ifNotGranted>

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
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <g:render template="/layouts/opportunityTemplate/opportunityProductTemplate"/>
    <g:if test="${this.canbillsShow}">
      <g:render template="/layouts/opportunityTemplate/billsItemsTemplate"/>
      <g:render template="/layouts/opportunityTemplate/transactionRecordsTemplate"/>
    </g:if>
    <g:render template="/layouts/opportunityTemplate/bankAccountsTemplate"/>
    <g:if test="${this.canAttachmentsShow}">
        <div class="row" id="sixth">
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
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '房产证']">房产证</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '身份证']">身份证</g:link>

                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '产调结果']">产调结果</g:link>
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
                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '其他放款要求资料']"
                            class="btn btn-outline btn-primary">其他放款要求资料</g:link>
                    <g:link target=" _blank" controller="attachments" action="show"
                            id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '放款回单']"
                            class="btn btn-outline btn-primary">放款回单</g:link>
                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '央行征信（中佳信）']"
                            class="btn btn-outline btn-primary">央行征信（中佳信）</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '二抵：抵押物银行按揭贷款合同']">二抵按揭贷款合同</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="opportunityFlexField"
                            id="${this.opportunity?.id}"
                            action="opportunityFlexField01" target=" _blank"></i>外访报告</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '企业信息截图']">企业信息截图</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '结清证明']">结清证明</g:link>
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
                            params="[attachmentTypeName: '签约录像']">签约录像</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '借款用途证明']">借款用途证明</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '外贸征信查询授权书']">外贸征信查询授权书</g:link>
                </div>
            </div>
        </div>
    </g:if>
    <g:elseif test="${this.canPhotosShow}">
        <div class="row" id="sixth">
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
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '房产证']">房产证</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '身份证']">身份证</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '意向申请单']">意向申请单</g:link>
                    <g:if test="${this.canLoanReceiptShow}">
                      <g:link target=" _blank" controller="attachments" action="show"
                      id="${this.opportunity?.id}"
                      params="[attachmentTypeName: '还款告知书']"
                      class="btn btn-outline btn-primary">还款告知书</g:link>
                    </g:if>
                </div>
            </div>
        </div>
    </g:elseif>
    <g:elseif test="${this.canLoanReceiptShow}">
      <div class="row" id="sixth">
          <div class="hpanel hgreen">
              <div class="panel-heading">
                  <div class="panel-tools">
                      <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                  </div>
                  附件信息
              </div>

              <div class="panel-body float-e-margins">
                <g:link target=" _blank" controller="attachments" action="show"
                        id="${this.opportunity?.id}"
                        params="[attachmentTypeName: '还款告知书']"
                        class="btn btn-outline btn-primary">还款告知书</g:link>
              </div>
          </div>
      </div>
    </g:elseif>
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


    <g:each in="${this.opportunityFlexFieldCategorys}">
        <g:if test="${!(it?.flexFieldCategory?.name in ['抵押物评估值', '抵押物其他情况', '外访预警', '抵押物情况', '借款人资质小结', '征信小结', '大数据小结', '借款用途', '还款来源', '风险结论', '下户信息', '放款前要求'])}">

            <div class="row" id="">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">

                            <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}"
                                    action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>

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
                                            <g:link controller="opportunityFlexField" action="show" id="${field?.id}"
                                                    class="firstTd">${field?.name}</g:link>
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
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                放款邮件信息
            </div>

            <div class="panel-body">
                <g:each in="${this.opportunityFlexFieldCategorys}">

                    <g:if test="${it?.flexFieldCategory?.name in ['抵押物情况', '借款用途', '放款前要求']}">
                        <div class="form form-horizontal">

                            <div class="form-group">
                                <label class="col-md-2 control-label">${it?.flexFieldCategory?.name}：</label>

                                <div class="col-md-10">
                                    <g:each in="${it?.fields}" var="field">
                                        <span class="cont text-comment">${field?.name}:${field?.value}</span>
                                    </g:each>
                                </div>
                            </div>

                        </div>
                    </g:if>

                </g:each>
            </div>
        </div>
    </div>

    <g:render template="/layouts/opportunityTemplate/activitiesTemplate"/>
    <g:render template="/layouts/opportunityTemplate/opportunityFlowsTemplate"/>

</div>
<footer class="footer bg-success">
    <g:render template="/layouts/opportunityTemplate/footerLeftTemplate"/>
    <g:render template="/layouts/opportunityTemplate/footerRightTemplate"/>
</footer>


<script>
    $(function () {
        $("body").addClass("fixed-small-header");
    });
</script>
</body>

</html>

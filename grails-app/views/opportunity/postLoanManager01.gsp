<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>postLoanManager订单详情</title>
    <style>

    </style>
</head>

<body>
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body clearfix">
            <h2 class="font-light pull-left">
                合同编号：${this.opportunity?.externalId}
                <span>/</span>
                订单号: ${this.opportunity.serialNumber}
            </h2>

            <div id="navbar-example" class="pull-right href-link">
                <ul class="nav navbar-nav" role="tablist">
                    <li class="active"><a class="btn-link page-scroll" href="#first">订单基本信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#second">报单人信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#third">房产信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fourth">借款人及抵押人信息</a></li>

                    <g:if test="${this.canAttachmentsShow}">
                        <li><a class="btn-link page-scroll" page-scroll="" href="#fifth">附件信息</a></li>
                    </g:if>
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
                  <g:if test="${this.opportunity?.stage?.code == '20'}">
                    <g:link class="btn btn-success btn-xs" controller="opportunity"
                            params="[parentOpportunity: this.opportunity.id, type: '30']"
                            action="preparePostLoan"><i class="fa fa-hand-pointer-o"></i> 结清申请</g:link>
                    <g:link class="btn btn-warning2 btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                        action="prepareRenewal"><i class="fa fa-hand-pointer-o"></i> 展期申请</g:link>
                    <!-- <g:link class="btn btn-primary btn-xs" controller="opportunity"
                        params="[parentOpportunity: this.opportunity.id, type: '50']"
                        action="preparePostLoan"><i class="fa fa-hand-pointer-o"></i> 逾期申请</g:link>
                    <g:link class="btn btn-primary2 btn-xs" controller="opportunity"
                            params="[parentOpportunity: this.opportunity.id, type: '40']"
                            action="preparePrepayment"><i class="fa fa-hand-pointer-o"></i> 早偿申请</g:link> -->
                    </g:if>
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="postLoanManagerEdit09"><i class="fa fa-edit"></i>编辑</g:link>
                </div>
                订单基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12">
                        <h4>${this.opportunity.serialNumber}(<g:if
                                test="${this.opportunity?.type}">${this.opportunity?.type?.name}</g:if>
                            <g:else>${com.next.OpportunityType.findByCode('10')?.name}</g:else>)
                            <g:if test="${this.opportunity?.isTest}">
                                <span class="label label-danger pull-right"><i class="fa fa-star"></i> 测试 <i
                                        class="fa fa-star"></i>
                                </span>
                            </g:if>
                        </h4>

                    </div>

                    <div class="col-md-2">
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
                    %{--<div class="col-md-3">
                        <strong>主管：${this.opportunity?.user?.reportTo?.toString()}</strong>
                    </div>--}%

                    <div class="col-md-2">
                        <strong>
                            <g:if test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('普通签呈'))}">签呈类型：普通签呈</g:if>
                            <g:elseif
                                    test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('特批签呈'))}">签呈类型：特批签呈</g:elseif>
                        </strong>
                    </div>

                    <g:if test="${this.opportunity?.status == 'Failed'}"><span
                            class="label label-danger pull-right">订单结果：失败</span></g:if>
                    <g:elseif test="${this.opportunity?.status == 'Completed'}"><span
                            class="label label-success pull-right">订单结果：成功</span></g:elseif>
                    <g:else><span class="label label-info pull-right">订单结果：进行中</span></g:else>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>拟贷款期限</span>
                            <strong>${this.opportunity?.loanDuration}月</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
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

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>初审</span>
                            <strong>${this.opportunity?.lender?.level?.description}；${this.opportunity?.dealRate}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>审核可贷金额</span>
                            <strong>${this.opportunity?.actualAmountOfCredit}万元
                            </strong>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="contact-stat border-right">
                            <span>付息方式</span>
                            <strong>
                                ${this.opportunity?.interestPaymentMethod?.name}
                                <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                    ;上扣息月份数:${this.opportunity?.monthOfAdvancePaymentOfInterest}个月
                                </g:if>
                            </strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>产品类型:</span>
                            <strong>${this.opportunity?.product?.name}</strong>
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

    <div class="row" id="third">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">

                    <g:if test="${this.opportunity?.collaterals?.size() <= 0 && this.opportunity?.city?.name}">
                        <g:link class="btn btn-info btn-xs" controller="collateral" action="recoveryCollateral"
                                params="[opportunity: this.opportunity?.id]"><i class="fa fa-refresh"></i>刷新</g:link>
                    </g:if>
                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence >= com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                        <g:link class="btn btn-info btn-xs" controller="collateral" action="create"
                                params="[opportunity: this.opportunity?.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </g:if>



                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                房产信息
            </div>

            <div class="panel-body form-horizontal no-padding">
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
                            %{--<g:sortableColumn property="requestStartTime" title="评房开始时间"></g:sortableColumn>
                            <g:sortableColumn property="requestEndTime" title="评房结束时间"></g:sortableColumn>--}%
                            <g:sortableColumn property="projectType" title="立项类型"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="建成时间"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="房龄(年)"></g:sortableColumn>
                            <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                                <g:sortableColumn colspan="2" property="buildTime" title="操作"></g:sortableColumn>
                            </g:if>
                            <g:else>
                                <g:sortableColumn colspan="3" property="buildTime" title="操作"></g:sortableColumn>
                            </g:else>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.collaterals}">
                            <tr>
                                <td>
                                    <g:link controller="collateral" action="show"
                                            id="${it.id}">${it.externalId}</g:link>
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
                                <td>
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

                                %{--<td>
                                    ${it.requestStartTime}
                                </td>
                                <td>
                                    ${it.requestEndTime}
                                </td>--}%
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
                                
                                <td>
                                    <g:link class="btn btn-primary btn-xs btn-outline" controller="opportunity"
                                            action="historyShow2"
                                            id="${it?.id}">查询明细</g:link>
                                    <<button class="m-t-xs btn btn-primary btn-xs btn-outline" data-toggle="modal" data-target="#applyForAmendment">申请修改</button>
                                </td>
                                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,it.opportunity.stage)?.executionSequence}">
                                </g:if>
                                <g:else>
                                    <td>
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline"
                                                    type="button">删除</button>
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

    <div class="row" id="fourth">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.opportunity.contacts?.size() > 0}">
                        <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                action="creditReportShow" id="${this.opportunity.id}"><i
                                class="fa fa-database"></i>大数据</g:link>
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
                            <g:sortableColumn property="age" title="年龄（岁）"></g:sortableColumn>
                            %{--<g:sortableColumn property="industry" title="行业"></g:sortableColumn>
                            <g:sortableColumn property="company" title="公司"></g:sortableColumn>
                            <g:sortableColumn property="companyCode" title="工商&机构代码"></g:sortableColumn>--}%
                            <g:sortableColumn property="profession" title="职业"></g:sortableColumn>
                            <g:sortableColumn property="country" title="国籍"></g:sortableColumn>
                            <g:sortableColumn property="identityType" title="身份证件类型"></g:sortableColumn>
                            <g:sortableColumn property="connnectedContact" title="关联人"></g:sortableColumn>
                            <g:sortableColumn property="connectedType" title="关联关系"></g:sortableColumn>
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                                </g:if>
                                <g:else>
                                    <g:sortableColumn width="8%" class="text-center" property="operation"
                                                      title="操作"></g:sortableColumn>
                                </g:else>

                            </sec:ifAllGranted>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityContacts}">
                            <tr>
                                <td>
                                    %{--<g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.endTime != null}">
                                        ${it?.type?.name}
                                    </g:if>
                                    <g:else>
                                        <g:link controller="opportunityContact" action="show"
                                                id="${it?.id}">${it?.type?.name}</g:link>
                                    </g:else>--}%
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
                                %{--<td>
                                    ${it?.contact?.industry?.name}
                                </td>
                                <td>
                                    ${it?.contact?.company}
                                </td>
                                <td>
                                    ${it?.contact?.companyCode}
                                </td>--}%
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
                                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,it.opportunity.stage)?.executionSequence}">
                                    </g:if>
                                    <g:else>
                                        <td width="8%" class="text-center">
                                            <g:form resource="${it}" method="DELETE">
                                                <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                        class="fa fa-trash-o"></i> 删除</button>
                                            </g:form>
                                        </td>
                                    </g:else>
                                </sec:ifAllGranted>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <g:render template="/layouts/opportunityTemplate/bankAccountsTemplate"/>

    <g:render template="/layouts/opportunityTemplate/opportunityContractsTemplate"/>
    <g:render template="/layouts/opportunityTemplate/transactionRecordsTemplate"/>

    <g:render template="/layouts/opportunityTemplate/billsItemsTemplate"/>

    <g:render template="/layouts/opportunityTemplate/opportunityProductTemplate"/>


    <g:if test="${this.canAttachmentsShow}">
        <div class="row" id="fifth">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="attachments" action="create"
                                id="${this.opportunity.id}"><i class="fa fa-upload"></i>上传</g:link>
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
                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '其他放款要求资料']"
                            class="btn btn-outline btn-primary">其他放款要求资料</g:link>
                    <g:link target=" _blank" controller="attachments" action="show"
                            id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '放款回单']"
                            class="btn btn-outline btn-primary">放款回单</g:link>

                </div>
            </div>
        </div>
    </g:if>



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

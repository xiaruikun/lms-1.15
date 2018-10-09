<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>普通页2.0-14</title>
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
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fifth">附件信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#sixth">费用</a></li>
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
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="edit"><i class="fa fa-edit"></i>编辑</g:link>
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
                <g:if test="${this.opportunity?.jqStatus != null && this.opportunity?.jqStatus == "手动结清" }">
                    <div class="col-md-2">
                        <strong>结清状态：${this.opportunity?.jqStatus}</strong>
                    </div>
                </g:if>
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

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>评房（可贷）</span>
                            <strong><g:formatNumber number="${this.opportunity?.loanAmount}" minFractionDigits="2"
                                                    maxFractionDigits="2"/>万元</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>初审</span>
                            <strong>${this.opportunity?.lender?.level?.description}；${this.opportunity?.dealRate}</strong>
                        </div>
                    </div>

                    <div class="col-md-5 border-right">
                        <div class="contact-stat">
                            <span>审核可贷金额</span>
                            <strong>${this.opportunity?.actualAmountOfCredit}万元；${this.opportunity?.interestPaymentMethod?.name}；
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                上扣息月份数:${this.opportunity?.monthOfAdvancePaymentOfInterest}个月；
                            </g:if>
                            综合息费<g:formatNumber
                                    number="${this.opportunity?.monthlyInterest + this.opportunity?.serviceCharge}"
                                    
                                    maxFractionDigits="9"/>%
                            （月息 <g:formatNumber
                                    number="${this.opportunity?.monthlyInterest}"
                                    
                                    maxFractionDigits="9"/>%；
                            借款服务费<g:formatNumber
                                    number="${this.opportunity?.serviceCharge}"
                                    
                                    maxFractionDigits="9"/>%）；
                            渠道服务费<g:formatNumber number="${this.opportunity?.commissionRate}" 
                                        maxFractionDigits="9"/>%；${this.opportunity?.commissionPaymentMethod}；
                            意向金<g:if test="${this.opportunity?.advancePayment}">
                                ${this.opportunity?.advancePayment}
                            </g:if>
                            <g:else>
                                0
                            </g:else>元
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
                            <g:sortableColumn property="unitPrice" title="单价（元）"></g:sortableColumn>
                            <g:sortableColumn property="totalPrice" title="总价（万元）"></g:sortableColumn>
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
                                    <g:link class="btn btn-primary btn-outline btn-xs" controller="collateral"
                                            action="pvQuery"
                                            id="${it?.id}">询值</g:link>

                                </td>
                                <td>
                                    <g:link class="btn btn-primary btn-xs btn-outline" controller="opportunity"
                                            action="historyShow2"
                                            id="${it?.id}">查询明细</g:link>
                                    <button class="btn btn-info btn-xs" data-toggle="modal" data-target="#applyForAmendment"><i
                                            class="fa fa-edit"></i>申请修改</button>
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
                        <!-- <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                     action="blackListShow" id="${this.opportunity.id}"><i
                                class="fa fa-database"></i>中佳信黑名单</g:link> -->
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
                            <g:sortableColumn property="age" title="年龄"></g:sortableColumn>
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
                                    <g:link controller="opportunityContact" action="show"
                                            id="${it?.id}">${it?.type?.name}</g:link>
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
                                                <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
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
                                    <!-- ${it?.type?.name} -->
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
                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                        action="show" id="${this.opportunity.id}"
                        params="[attachmentTypeName: '结清证明']">结清证明</g:link>

            </div>
        </div>
    </div>
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
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence >= com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                        <g:link class="btn btn-info btn-xs" controller="opportunityContract" action="create"
                                params="[opportunity: this.opportunity.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </g:if>

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
                            <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                            </g:if>
                            <g:else>
                                <g:sortableColumn property="operation" title="操作"></g:sortableColumn>
                            </g:else>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunity?.contracts}">
                            <tr>
                                <td>
                                    <g:link controller="opportunityContract" action="show"
                                            id="${it?.id}">${it?.contract?.serialNumber}</g:link>
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

    <div class="row" id="sixth">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="opportunityProduct" id="${this.opportunity?.id}"
                            action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="initOpportunityProduct"><i class="fa fa-calculator"></i>初始化费用</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                费用
            </div>

            <div class="panel-body no-padding productInterest">
                <g:each in="${opportunityProduct}">
                    <div class="col-md-2 border-right border-bottom">
                        <div class="contact-stat">
                            <span>${it?.productInterestType?.name}</span>
                            <strong>费率 ${it?.rate}；上扣息月份数 ${it?.firstPayMonthes}</strong>
                            <g:form resource="${it}" method="DELETE">
                                <button class="deleteBtn btn btn-danger btn-xs m-t-sm" type="button"><i
                                        class="fa fa-trash-o"></i> 删除</button>
                            </g:form>
                        </div>
                    </div>
                </g:each>
            </div>

        </div>
    </div>

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
                                    %{--<g:sortableColumn property="executionSequence" title="${message(code: 'opportunityFlexField.description.label', default: '描述')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'opportunityFlexField.dataType.label', default: '数据类型')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'opportunityFlexField.defaultValue.label', default: '默认值')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'opportunityFlexField.valueConstraints.label', default: '约束')}" />--}%
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
                                        %{--<td width="15%">${it?.description}</td>
                                        <td width="15%">${it?.dataType}</td>
                                        <td width="15%">${it?.defaultValue}</td>
                                        <td width="20%">${it?.valueConstraints}</td>--}%
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

    <div class="row" id="seventh">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <!-- <g:if test="${this.opportunity?.contacts.size() > 0}">
                        <g:link class="btn btn-info btn-xs" controller="activity"
                                params="[opportunityId: this.opportunity.id]"
                                action="create"><i class="fa fa-plus"></i>新增</g:link></g:if> -->
                    <g:link class="btn btn-info btn-xs" controller="activity"
                            params="[opportunityId: this.opportunity.id]" action="create"><i
                            class="fa fa-plus"></i>新增</g:link>
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
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                <g:sortableColumn class="text-center" width="5%" property="operation"
                                                  title="操作"></g:sortableColumn>
                            </sec:ifAllGranted>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.activities}">
                            <tr>
                                <td>
                                    <g:link controller="activity" action="show" id="${it.id}"
                                            class="firstTd">${it?.type?.name}</g:link>
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
                                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                    <td class="text-center" width="5%">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifAllGranted>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row" id="eighth">
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
                <!-- hi红i后
                    <g:each in="${this.history}">
                    <div class="vertical-timeline-block">
                        <div class="vertical-timeline-icon navy-bg">
                            <i class="fa fa-calendar text-primary"></i>
                        </div>

                        <div class="vertical-timeline-content">
                            <div class="p-sm">
                                <span class="vertical-date pull-right">${it.modifiedBy?.fullName} <br/> <small><g:formatDate
                            format="yyyy-MM-dd HH:mm:ss" date="${it.modifiedDate}"/></small></span>

                                    <h2><g:link controller="opportunityTeam" action="historyShow"
                                                id="${it.id}"><p>${it.stage?.name}</p></g:link></h2>
                                </div>
                            </div>
                        </div>
                </g:each> -->
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="footer bg-success">
    <div class="row">
        <div class="pull-left m-b-xs">
            <g:if test="${this.subUsers?.size() > 0}">
                <g:form controller="opportunity" action="transferRole">
                    <input class="form-control" name="opportunity" value="${this.opportunity?.id}" type="hidden">
                    <g:select name="user" id="user" from="${this.subUsers}" optionKey="id"/>
                    <button class="btn btn-sm btn-success m-l-sm" type="submit" name="type" value="temp"><i
                            class="fa fa-exchange"></i> 临时委派</button>
                    <button class="btn btn-sm btn-info m-l-sm" type="submit" name="type" value="total"><i
                            class="fa fa-exchange"></i> 完全委派</button>
                </g:form>
            </g:if>
        </div>

        <div class="pull-right text-right">
            <button class="btn btn-sm btn-info" data-toggle="modal" data-target="#rejectReason">
                <i class="fa fa-arrow-up"></i> 上一步</button>
            <g:if test="${this.currentFlow?.nextStages?.size() > 0}">
                <g:each in="${this.currentFlow?.nextStages}">
                    <div class="footBtn">
                        <select name="nextOperators" class="selectRole">
                            <g:each in="${this.opportunityFlows}" var="flow">
                                <g:if test="${flow == it?.nextStage}">
                                    <g:each var="role" in="${this.opportunityRoles}">
                                        <g:if test="${role?.stage == flow?.stage && role.teamRole?.name == 'Approval'}">
                                            <option value="${role?.id}">${role?.user}</option>
                                        </g:if>
                                    </g:each>
                                </g:if>
                            </g:each>
                        </select>
                        <button class="btn btn-sm btn-info nextStepBtn" data-toggle="modal" data-target="#nextStep"
                                value="${it?.nextStage?.id}">
                            <i class="fa fa-arrow-down"></i> ${it?.nextStage?.stage?.description}</button>
                    </div>

                </g:each>
            </g:if>
            <g:else>
                <div class="footBtn">
                    <select name="nextOperators" class="selectRole pull-left">
                        <g:each in="${this.opportunityFlows}" var="flow">
                            <g:if test="${flow == com.next.OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and executionSequence > ? order by executionSequence asc", [this.opportunity?.id, this.currentFlow?.executionSequence])}">
                                <g:each var="role" in="${this.opportunityRoles}">
                                    <g:if test="${role?.stage == flow?.stage && role.teamRole?.name == 'Approval'}">
                                        <option value="${role?.id}">${role?.user}</option>
                                    </g:if>
                                </g:each>
                            </g:if>
                        </g:each>
                    </select>
                    <button class="btn btn-sm btn-info nextStepBtn" data-toggle="modal" data-target="#nextStep">
                        <i class="fa fa-arrow-down"></i> 下一步</button>
                </div>

            </g:else>
            <g:link class="btn btn-sm btn-success" controller="opportunity" id="${this.opportunity?.id}"
                    action="complete"><i class="fa fa-check"></i> 完成</g:link>
            <g:if test="${this.canSpecialApproval}">
                <g:link class="btn btn-sm btn-success" controller="opportunity" id="${this.opportunity?.id}"
                        action="specialApprove"><i class="fa fa-check"></i> 特批</g:link>
            </g:if>
            
            <button class="btn  btn-sm btn-danger" data-toggle="modal" data-target="#myModal">
                <i class="fa fa-exclamation-circle"></i> 失败</button>
        </div>
    </div>
</footer>


<script>
    $(function () {
        $("body").addClass("fixed-small-header");

        $(".text-comment").each(function () {
            var text = $(this).text();
            if (text) {
                $(this).parent().parent().removeClass("collapsed");
                $(this).parent().prev(".panel-heading").removeClass("hbuilt");
            } else {
                $(this).parent().parent().addClass("collapsed");
                $(this).parent().prev(".panel-heading").addClass("hbuilt");
            }
        })
    });
</script>
</body>

</html>

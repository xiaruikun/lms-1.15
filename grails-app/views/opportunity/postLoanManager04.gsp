<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>展期主单页2.0-PLM04</title>
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

                <g:link target="_blank" class="btn btn-xs btn-success" controller="opportunity"
                        action="postLoanManager05"
                        id="${this.opportunity?.id}"><i class="fa fa-link"></i> 订单推进</g:link>
                <g:link target="_blank" class="btn btn-xs btn-info" controller="opportunity"
                        action="postLoanManager07"
                        id="${this.opportunity?.parent?.id}"><i class="fa fa-external-link"></i> 原订单信息</g:link>
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
                            <th>业务类型</th>
                            <th>业务来源</th>
                            <th>
                                房产总价（万元）
                            </th>
                            <th>拟展期金额（万元）</th>
                            <th>拟展期期数（月）</th>

                            <th>产品类型</th>
                            <th>共同借款人</th>
                            <th>实际月息（%）</th>
                            <th>付息方式</th>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <th>上扣息月份数（月）</th>
                            </g:if>
                            <th>资金渠道</th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.type?.name}</td>
                            <td>${this.opportunity?.contact?.account?.name}</td>
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
                            <td><g:formatNumber number="${this.opportunity?.monthlyInterest}"
                                                maxFractionDigits="9"/></td>
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
                                                      title="一抵金额（万元）"></g:sortableColumn>
                                    <g:sortableColumn property="secondMortgageAmount"
                                                      title="二抵金额（万元）"></g:sortableColumn>
                                    <g:sortableColumn property="loanToValue" title="抵押率（%）"></g:sortableColumn>
                                    <g:sortableColumn property="loanToValue" title="房产编号"></g:sortableColumn>

                                    <g:sortableColumn property="projectType" title="立项类型"></g:sortableColumn>
                                    <g:sortableColumn property="buildTime" title="建成时间"></g:sortableColumn>
                                    <g:sortableColumn property="buildTime" title="房龄(年)"></g:sortableColumn>
                                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                                        <g:sortableColumn colspan="1" property="buildTime" title="操作"></g:sortableColumn>
                                    </g:if>
                                    <g:else>
                                        <g:sortableColumn colspan="2" property="buildTime" title="操作"></g:sortableColumn>
                                    </g:else>
                                </tr>
                                </thead>
                                <tbody>

                                <tr>
                                    <td>
                                        <g:link controller="collateral" action="show"
                                                id="${it.id}">${it.externalId}</g:link>
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
                                    
                                    <td>
                                        <g:link class="btn btn-primary btn-xs btn-outline" controller="opportunity"
                                                action="historyShow2"
                                                id="${it?.id}">明细</g:link>
                                        <button class="btn m-t-xs btn-primary btn-outline btn-xs" data-toggle="modal" data-target="#applyForAmendment">申请修改</button>
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
                            <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                            </g:if>
                            <g:else>
                                <g:sortableColumn width="8%" class="text-center" property="operation"
                                                  title="操作"></g:sortableColumn>
                            %{--</sec:ifAllGranted>--}%
                            </g:else>

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
                                %{--<sec:ifAllGranted roles="ROLE_ADMINISTRATOR">--}%
                                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,it.opportunity.stage)?.executionSequence}">
                                </g:if>
                                <g:esle>
                                    <td width="8%" class="text-center">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </g:esle>

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
    <g:render template="/layouts/opportunityTemplate/billsItemsTemplate"/>
    <g:render template="/layouts/opportunityTemplate/opportunityContractsTemplate"/>
    <g:render template="/layouts/opportunityTemplate/bankAccountsTemplate"/>
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
                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '客户还款记录']"
                            class="btn btn-outline btn-primary">客户还款记录</g:link>
                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '减免签呈']"
                            class="btn btn-outline btn-primary">减免签呈</g:link>
                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '结清证明']"
                            class="btn btn-outline btn-primary">结清证明</g:link>
                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '广银联客户电子回单']"
                            class="btn btn-outline btn-primary">广银联客户电子回单</g:link>
                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '交款记录']"
                            class="btn btn-outline btn-primary">交款记录</g:link>
                    <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '网银流水单']"
                            class="btn btn-outline btn-primary">网银流水单</g:link>
                </div>
            </div>
        </div>
    </g:if>
    <g:render template="/layouts/opportunityTemplate/activitiesTemplate"/>

    <g:render template="/layouts/opportunityTemplate/opportunityFlowsTemplate"/>

    <div class="row commentsRow">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                展期审批意见
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
    })
</script>
</body>

</html>

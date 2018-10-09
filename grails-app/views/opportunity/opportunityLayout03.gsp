<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>贷款放款详情页1.0-03</title>
    <style>
    .summary {
        border-top: 1px solid #ddd;
        margin-bottom: 7px !important;
        padding: 10px 0;
    }

    table.text-center th {
        text-align: center;
    }

    @media (max-width: 720px) {
        .content {
            padding: 140px 40px 60px 40px !important;
        }
    }

    </style>
</head>

<body class="fixed-small-header">
<div class="">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body clearfix">
                <div id="navbar-example" class="pull-right href-link">
                    <ul class="nav navbar-nav" role="tablist">
                        <li class="active"><a class="btn-link page-scroll" href="#first">订单基本信息</a></li>
                        <li><a class="btn-link page-scroll" href="#second ">借款信息</a></li>
                        <li><a class="btn-link page-scroll" href="#third">房产信息</a></li>
                        <g:each in="${this.opportunityFlexFieldCategorys}">
                            <g:if test="${it?.flexFieldCategory?.name == '产调结果'}">
                                <li><a class="btn-link page-scroll" href="#fourth">产调结果</a></li>
                            </g:if>
                        </g:each>

                        <li><a class="btn-link page-scroll" href="#fifth">共同借款人及抵押人信息</a></li>

                        <g:if test="${this.opportunity?.stage?.code == '09'}">
                            <li><a class="btn-link page-scroll" href="#sixth">外访调查报告</a></li>
                        </g:if>
                        <li><a class="btn-link page-scroll" href="#seventh ">订单跟踪</a></li>

                    </ul>
                </div>

            </div>
        </div>
    </div>
</div>


<div class="content animate-panel" style="padding-top: 60px">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
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
                订单基本信息
            </div>

            <div class="panel-body">
                <h4>${this.opportunity?.serialNumber}
                    <g:if test="${this.opportunity?.isTest}">
                        <span class="label label-danger pull-right"><i class="fa fa-star"></i> 测试 <i class="fa fa-star"></i></span>
                    </g:if>
                </h4>

                <div class="col-md-2">
                    <strong><span class="glyphicon glyphicon-user"
                                  aria-hidden="true"></span> ${this.opportunity?.fullName}</strong>
                </div>
                <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                    <div class="col-md-1"><span
                            class="fa fa-chain"></span>${this.opportunity?.protectionEndTime - this.opportunity?.protectionStartTime}天
                    </div>
                </g:if>
                <div class="col-md-2"><strong>${this.opportunity?.stage?.name}</strong></div>
                <g:if test="${this.opportunity?.status == 'Failed'}"><span
                        class="label label-danger pull-right">订单结果：失败</span></g:if>
                <g:elseif test="${this.opportunity?.status == 'Completed'}"><span
                        class="label label-success pull-right">订单结果：成功</span></g:elseif>
                <g:else><span class="label label-info pull-right">订单结果：进行中</span></g:else>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>共同借款人</span><strong>${this.opportunity?.fullName}</strong> <strong class="cellphoneFormat">${this.opportunity?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-3 border-right">
                        <div class="contact-stat"><span>经纪人</span><strong>${this.opportunity?.contact?.fullName}</strong><strong class="cellphoneFormat"> ${this.opportunity?.contact?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-3 border-right">
                        <div class="contact-stat"><span>支持经理：</span><strong>${this.opportunity?.user} ${this.opportunity?.user?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>评房总价（万元）</span><strong><g:formatNumber
                                number="${this.opportunity?.loanAmount}" minFractionDigits="2"
                                maxFractionDigits="2"/></strong>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="contact-stat"><span>贷款金额（万元）</span><strong>${this.opportunity?.actualAmountOfCredit}</strong>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div class="row" id="second">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                拟借款信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="requestedAmount" title="拟贷款金额（万元）"></g:sortableColumn>
                            <g:sortableColumn property="loanDuration" title="拟贷款期限（月）"></g:sortableColumn>
                            <g:sortableColumn property="actualLoanDuration" title="实际贷款期限（月）"></g:sortableColumn>

                            <g:sortableColumn property="productName" title="产品类型"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                ${this.opportunity?.actualAmountOfCredit}
                            </td>
                            <td>
                                ${this.opportunity?.loanDuration}
                            </td>
                            <td>
                                ${this.opportunity?.actualLoanDuration}
                            </td>

                            <td>
                                ${this.opportunity?.product?.name}
                            </td>
                        </tr>
                        </tbody>
                    </table>
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
                                    <g:formatNumber number="${it.unitPrice}" minFractionDigits="2"
                                                    maxFractionDigits="2"/>
                                </td>
                                <td>
                                    <g:formatNumber number="${it.totalPrice}" minFractionDigits="2"
                                                    maxFractionDigits="2"/>
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
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '产调结果'}">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <g:link target=" _blank" class="btn btn-info btn-xs" id="${this.opportunity?.id}"
                                    params="[attachmentTypeName: '产调结果']"
                                    controller="attachments" action="show"><i class="fa fa-file-o"></i>产调附件</g:link>
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
                                                      title="${message(code: 'opportunityFlexField.name.label', default: '评估参数')}"/>
                                    <g:sortableColumn property="stage"
                                                      title="${message(code: 'opportunityFlexField.value.label', default: '已选参数')}"/>
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
    <g:each in="${this.opportunityFlexFieldCategorys}">
        <g:if test="${it?.flexFieldCategory?.name == '抵押物情况' && it?.fields?.size() > 0}">
            <div class="row">

                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">

                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body">
                        <g:each in="${it?.fields}" var="field">
                            <p class="text-comment">${field?.name}:${field?.value}</p>
                        </g:each>
                    </div>
                </div>

            </div>
        </g:if>
    </g:each>

    <div class="row" id="fifth">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="attachments" action="create"
                            id="${this.opportunity.id}"><i class="fa fa-upload"></i>补充附件</g:link>
                    <g:link class="btn btn-info btn-xs" controller="opportunityFlexField"
                            id="${this.opportunity?.id}"
                            action="opportunityFlexField01" target=" _blank"><i class="fa"></i>外访报告</g:link>
                    <g:link target="_blank" class="btn btn-info btn-xs" controller="attachments"
                            action="show"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '签呈']"><i class="fa fa-file-image-o"></i>签呈</g:link>
                    <g:link class="btn btn-info btn-xs" controller="attachments"
                            action="show"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '基础材料']"><i class="fa fa-file-image-o"></i>基础材料</g:link>
                    <g:link target=" _blank" class="btn btn-info btn-xs" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '其他资料']"><i class="fa fa-file-image-o"></i>其他资料</g:link>
                    <g:link class="btn btn-info btn-xs" controller="attachments"
                            action="show"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '征信']"><i class="fa fa-file-o"></i>征信</g:link>
                    <g:if test="${this.opportunity.contacts?.size() > 0}">
                        <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                action="creditReportShow" id="${this.opportunity.id}"><i
                                class="fa fa-database"></i>大数据</g:link>
                    </g:if>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                共同借款人及抵押人信息
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
                            <g:sortableColumn property="maritalStatus" title="年龄"></g:sortableColumn>
                            %{--<g:sortableColumn property="industry" title="行业"></g:sortableColumn>
                            <g:sortableColumn property="company" title="公司"></g:sortableColumn>
                            <g:sortableColumn property="companyCode" title="工商&机构代码"></g:sortableColumn>--}%
                            <g:sortableColumn property="profession" title="职业"></g:sortableColumn>
                            <g:sortableColumn property="country" title="国籍"></g:sortableColumn>
                            <g:sortableColumn property="identityType" title="身份证件类型"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityContacts}">
                            <tr>
                                <td><g:link controller="opportunityContact" action="show"
                                            id="${it?.id}">${it?.type?.name}</g:link></td>
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
            <g:if test="${it?.flexFieldCategory?.name == '借款人资质小结'}">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body">
                        <g:each in="${it?.fields}" var="field">
                            <p class="text-comment">${field?.name}:${field?.value}</p>
                        </g:each>
                    </div>
                </div>
            </g:if>
        </g:each>
    </div>

    <g:each in="${this.opportunityFlexFieldCategorys}">
        <g:if test="${it?.flexFieldCategory?.name == '征信小结' || it?.flexFieldCategory?.name == '大数据小结'}">
            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body">
                        <g:each in="${it?.fields}" var="field">
                            <p class="text-comment">${field?.name}:${field?.value}</p>
                        </g:each>
                    </div>
                </div>
            </div>
        </g:if>
    </g:each>

    <g:each in="${this.opportunityFlexFieldCategorys}">
        <g:if test="${it?.flexFieldCategory?.name == '借款用途' || it?.flexFieldCategory?.name == '还款来源' || it?.flexFieldCategory?.name == '风险结论'}">
            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body">
                        <g:each in="${it?.fields}" var="field">
                            <p class="text-comment">${field?.name}:${field?.value}</p>
                        </g:each>
                    </div>
                </div>
            </div>
        </g:if>
    </g:each>

    <g:each in="${this.opportunityFlexFieldCategorys}">
        <g:if test="${it?.flexFieldCategory?.name == '放款前要求'}">
            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <g:link target=" _blank" class="btn btn-info btn-xs" controller="attachments" action="show"
                                    id="${this.opportunity.id}"
                                    params="[attachmentTypeName: '担保资料']"><i
                                    class="fa fa-file-image-o"></i>担保资料</g:link>
                            <g:link target=" _blank" class="btn btn-info btn-xs" controller="attachments" action="show"
                                    id="${this.opportunity.id}"
                                    params="[attachmentTypeName: '产调结果']"><i class="fa fa-file-image-o"></i>产调</g:link>
                            <g:link target=" _blank" class="btn btn-info btn-xs" controller="attachments" action="show"
                                    id="${this.opportunity.id}"
                                    params="[attachmentTypeName: '其他资料']"><i
                                    class="fa fa-file-image-o"></i>其他资料</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body">
                        <g:each in="${it?.fields}" var="field">
                            <p class="text-comment">${field?.value}</p>
                        </g:each>
                    </div>
                </div>
            </div>
        </g:if>
    </g:each>
    <g:if test="${this.opportunity?.stage?.code == '09'}">
        <div class="row" id="sixth">
            <div class="hpanel horange">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link controller="attachments"
                                action="show"
                                id="${this.opportunity?.id}"
                                params="[attachmentTypeName: '下户信息']" class="btn btn-info btn-xs">外访附件</g:link>
                    </div>
                    外访调查报告
                </div>

                <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="requestedAmount" title="共同借款人"></g:sortableColumn>
                                <g:sortableColumn property="loanDuration" title="外访人员"></g:sortableColumn>
                                <g:sortableColumn property="mortgageType" title="计划开始时间"></g:sortableColumn>
                                <g:sortableColumn property="firstMortgageAmount" title="计划结束时间"></g:sortableColumn>
                                <g:sortableColumn property="typeOfFirstMortgage" title="实际开始时间"></g:sortableColumn>
                                <g:sortableColumn property="typeOfFirstMortgage" title="实际结束时间"></g:sortableColumn>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.activities}">
                                <tr>
                                    <td>
                                        ${this.opportunity?.fullName}
                                    </td>
                                    <td>
                                        ${it?.assignedTo}
                                    </td>
                                    <td>
                                        ${it?.startTime}
                                    </td>
                                    <td>
                                        ${it?.endTime}
                                    </td>
                                    <td>
                                        ${it?.actualStartTime}
                                    </td>
                                    <td>
                                        ${it?.actualEndTime}
                                    </td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </g:if>
    <div class="row" id="seventh">
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

</div>

<script>
    $(function () {
        $("body").addClass("fixed-small-header");
    });
</script>
</body>

</html>

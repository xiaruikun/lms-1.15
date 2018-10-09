<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>展期订单详情-Show</title>
    <style>
    .summary {
        border-top: 1px solid #ddd;
        margin-bottom: 7px !important;
        padding: 10px 0;
    }

    table.text-center th {
        text-align: center;
    }

    </style>
</head>

<body class="fixed-small-header">
<div class="">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body clearfix">
                <!-- <h2 class="font-light pull-left">
                订单号: ${this.opportunity?.serialNumber}
            </h2> -->
                <div id="navbar-example" class="pull-right href-link">
                    <ul class="nav navbar-nav" role="tablist">
                        <li class="active"><a class="btn-link page-scroll" href="#first">订单基本信息</a></li>
                        <li><a class="btn-link page-scroll" href="#second ">展期信息</a></li>
                        <li><a class="btn-link page-scroll" href="#third">房产信息</a></li>
                        <li><a class="btn-link page-scroll" href="#fourth">产调结果</a></li>
                        <li><a class="btn-link page-scroll" href="#fifth">共同借款人及抵押人信息</a></li>
                        <li><a class="btn-link page-scroll" href="#sixth ">主审结果</a></li>
                        <g:if test="${this.opportunity?.stage?.code == '09'}">
                            <li><a class="btn-link page-scroll" href="#seventh">外访调查报告</a></li>
                        </g:if>
                        <li><a class="btn-link page-scroll" href="#eighth">订单跟踪</a></li>
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
        <g:hasErrors bean="${this.opportunity}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunity}" var="error">
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
                展期订单基本信息
            </div>

            <div class="panel-body">
                <h4>${this.opportunity?.serialNumber}
                    <g:if test="${this.opportunity?.isTest}">
                        <span class="label label-danger pull-right m-t-n-xs"><i class="fa fa-star"></i> 测试 <i
                                class="fa fa-star"></i></span>
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

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>经纪人</span><strong>${this.opportunity?.contact?.fullName}</strong><strong class="cellphoneFormat"> ${this.opportunity?.contact?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>支持经理：</span><strong>${this.opportunity?.user?.fullName} </strong>
                                <strong class="cellphoneFormat"> ${this.opportunity?.user?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>评房总价（万元）</span><strong><g:formatNumber
                                number="${this.opportunity?.loanAmount}" minFractionDigits="2"
                                maxFractionDigits="2"/></strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>贷款金额（万元）</span><strong>${this.opportunity?.parent?.actualAmountOfCredit}</strong>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="contact-stat"><span>展期金额（万元）</span><strong>${this.opportunity?.actualAmountOfCredit}</strong>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div class="row" id="second">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                展期申请信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="requestedAmount" title="拟展期金额（万元）"></g:sortableColumn>
                            <g:sortableColumn property="loanDuration" title="拟展期期限（月）"></g:sortableColumn>
                            <g:sortableColumn property="actualLoanDuration" title="实际展期期限（月）"></g:sortableColumn>
                            <g:sortableColumn property="mortgageType" title="抵押类型"></g:sortableColumn>
                            <g:sortableColumn property="firstMortgageAmount" title="一抵金额（万元）"></g:sortableColumn>
                            <g:sortableColumn property="typeOfFirstMortgage" title="一抵性质"></g:sortableColumn>
                            <g:sortableColumn property="productName" title="产品类型"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                ${this.opportunity?.requestedAmount}
                            </td>
                            <td>
                                ${this.opportunity?.loanDuration}
                            </td>
                            <td>
                                ${this.opportunity?.actualLoanDuration}
                            </td>
                            <td>
                                ${this.opportunity?.mortgageType?.name}
                            </td>
                            <td>
                                ${this.opportunity?.firstMortgageAmount}
                            </td>
                            <td>
                                ${this.opportunity?.typeOfFirstMortgage?.name}
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
                    <g:link class="btn btn-info btn-xs" controller="opportunity" action="historyShow2"
                            params="[id: this.opportunity?.id]"><i class="fa fa-search"></i>查询明细</g:link>
                </div>
                房产信息
            </div>

            <div class="panel-body form-horizontal no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="city"
                                              title="城市"></g:sortableColumn>
                            <g:sortableColumn property="district" title="区县"></g:sortableColumn>
                            <g:sortableColumn property="district" title="所在区域"></g:sortableColumn>
                            <g:sortableColumn property="projectName"
                                              title="小区名称"></g:sortableColumn>
                            <g:sortableColumn property="address"
                                              title="地址"></g:sortableColumn>
                            <g:sortableColumn property="orientation"
                                              title="朝向"></g:sortableColumn>
                            <g:sortableColumn property="area"
                                              title="面积（m2）"></g:sortableColumn>
                            <g:sortableColumn property="building"
                                              title="楼栋"></g:sortableColumn>
                            <g:sortableColumn property="unit"
                                              title="单元"></g:sortableColumn>
                            <g:sortableColumn property="roomNumber"
                                              title="户号"></g:sortableColumn>
                            <g:sortableColumn property="floor"
                                              title="所在楼层"></g:sortableColumn>
                            <g:sortableColumn property="totalFloor"
                                              title="总楼层"></g:sortableColumn>
                            <g:sortableColumn property="assetType"
                                              title="房产类型"></g:sortableColumn>
                            <g:sortableColumn property="houseType"
                                              title="物业类型"></g:sortableColumn>
                            <g:sortableColumn property="specialFactors"
                                              title="特殊因素"></g:sortableColumn>
                            <g:sortableColumn property="unitPrice"
                                              title="批贷房产单价（元）"></g:sortableColumn>
                            <g:sortableColumn property="totalPrice"
                                              title="批贷房产总价（万元）"></g:sortableColumn>
                            %{--<g:sortableColumn property="status"
                                              title="状态"></g:sortableColumn>--}%
                            %{--<g:sortableColumn property="requestStartTime"
                                              title="评房开始时间"></g:sortableColumn>
                            <g:sortableColumn property="requestEndTime"
                                              title="评房结束时间"></g:sortableColumn>--}%
                            <g:sortableColumn property="projectType"
                                              title="立项类型"></g:sortableColumn>
                            <g:sortableColumn property="buildTime"
                                              title="建成时间"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.collaterals}">
                            <tr>
                                %{--<td>${it.externalId}</td>--}%
                                <td>
                                    ${it?.city?.name}
                                </td>
                                <td>
                                    ${it?.district}
                                </td>
                                <td>${it.region?.name}</td>
                                <td>
                                    ${it?.projectName}
                                </td>
                                <td>
                                    ${it?.address}
                                </td>
                                <td>
                                    ${it?.orientation}
                                </td>
                                <td>
                                    ${it?.area}
                                </td>
                                <td>
                                    ${it?.building}
                                </td>
                                <td>
                                    ${it?.unit}
                                </td>
                                <td>
                                    ${it?.roomNumber}
                                </td>
                                <td>
                                    ${it?.floor}
                                </td>
                                <td>
                                    ${it?.totalFloor}
                                </td>
                                <td>
                                    ${it?.assetType}
                                </td>
                                <td>
                                    ${it?.houseType}
                                </td>
                                <td>
                                    ${it?.specialFactors}
                                </td>
                                <td>
                                    <g:formatNumber number="${it?.unitPrice}" minFractionDigits="2"
                                                    maxFractionDigits="2"/>
                                </td>
                                <td>
                                    <g:formatNumber number="${it?.totalPrice}" minFractionDigits="2"
                                                    maxFractionDigits="2"/>
                                </td>
                                %{--<td>
                                    ${it?.requestStartTime}
                                </td>
                                <td>
                                    ${it?.requestEndTime}
                                </td>--}%
                                <td>
                                    ${it?.projectType?.name}
                                </td>
                                <td>
                                    <g:formatDate format="yyyy" date="${this.opportunity?.collaterals[0]?.buildTime}"/>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row" id="third">
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '产调结果'}">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            %{--<g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}" action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>--}%
                            <g:link target="_blank" class="btn btn-info btn-xs" id="${this.opportunity?.id}"
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
    %{--<div class="row" id="fourth">
        <div class="hpanel hyellow collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '产调结果']"
                            controller="attachments" action="show"><i class="fa fa-file-o"></i>产调附件</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                产调结果
            </div>

            <div class="panel-body no-padding">
                <ul class="list-group">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'liquidityRiskReport.name.label', default: '评估参数')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'liquidityRiskReport.name.label', default: '已选参数')}"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.liquidityRiskReport[0]?.items}">
                            <tr>
                                <td><g:link controller="liquidityRiskReportItem" action="show" id="${it?.id}"
                                            class="firstTd">${it?.name}</g:link></td>
                                <td>${it?.optionName}</td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </ul>
            </div>
        </div>
    </div>--}%
    <div class="row">
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '抵押物情况'}">
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

    <div class="row" id="fourth">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link target="_blank" class="btn btn-info btn-xs" controller="opportunityFlexField"
                            action="showOpportunityFlexField"
                            id="${this.opportunity?.id}"><i class="fa"></i>外访报告</g:link>
                    <g:link target="_blank" class="btn btn-info btn-xs" controller="attachments" action="create"
                            id="${this.opportunity.id}"><i class="fa fa-upload"></i>补充附件</g:link>
                    <g:link target="_blank" class="btn btn-info btn-xs" controller="attachments"
                            action="show"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '基础材料']"><i class="fa fa-file-image-o"></i>基础材料</g:link>
                    <g:link target=" _blank" class="btn btn-info btn-xs" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '其他资料']"><i class="fa fa-file-image-o"></i>其他资料</g:link>
                    <g:link target="_blank" class="btn btn-info btn-xs" controller="attachments"
                            action="show"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '征信']"><i class="fa fa-file-o"></i>征信</g:link>
                    <g:if test="${this.opportunity.contacts?.size() > 0}">
                        <!-- <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                     action="blackListShow" id="${this.opportunity.id}"><i
                                class="fa fa-database"></i>中佳信黑名单</g:link> -->
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
%{--<g:each in="${this.opportunityFlows}">
    <g:if test="${it?.stage?.code == '26'}">
        <div class="row">
            <div class="hpanel hyellow">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    放款前要求：
                </div>

                <div class="panel-body">
                    <p class="text-comment">${it?.comments}</p>
                </div>
            </div>
        </div>
    </g:if>
</g:each>--}%
%{--<div class="row" id="sixth">
    <div class="hpanel hblue">
        <div class="panel-heading">
            主审结果
        </div>

        <div class="panel-body form-horizontal">

            <div class="form-group">
                <label class="col-md-3 control-label">审核可贷金额：</label>

                <div class="col-md-2">
                    <span class="cont">${this.opportunity?.actualAmountOfCredit}
                    <g:if test="${this.opportunity?.actualAmountOfCredit}">
                        万元
                    </g:if>
                    </span>
                </div>

                <label class="col-md-3 control-label">月息：</label>

                <div class="col-md-2">
                    <span class="cont">${this.opportunity?.monthlyInterest}
                    <g:if test="${this.opportunity?.monthlyInterest != null}">
                        %
                    </g:if>
                    </span>
                </div>

            </div>


            <div class="form-group">
                <label class="col-md-3 control-label">借款服务费：</label>

                <div class="col-md-2">
                    <span class="cont"> <g:formatNumber number="${this.opportunity?.serviceCharge}" 
                                                maxFractionDigits="9"/></span>
                    <g:if test="${this.opportunity?.serviceCharge != null}">
                        %
                    </g:if>
                    </span>
                </div>
                <label class="col-md-3 control-label">付息方式：</label>

                <div class="col-md-2">
                    <span class="cont">${this.opportunity?.interestPaymentMethod?.name}</span>
                </div>



            </div>

            <div class="form-group">
                <label class="col-md-3 control-label">渠道服务费：</label>

                <div class="col-md-2">
                    <span class="cont">${this.opportunity?.commissionRate}
                    <g:if test="${this.opportunity?.commissionRate != null}">
                        %
                    </g:if>
                    </span>
                </div>
                <label class="col-md-3 control-label">返佣方式：</label>

                <div class="col-md-2">
                    <span class="cont">${this.opportunity?.commissionPaymentMethod?.name}</span>
                </div>

            </div>


            <div class="form-group">
                <label class="col-md-3 control-label">渠道返费：</label>

                <div class="col-md-2">
                    <span class="cont">${this.opportunity?.commission}
                    <g:if test="${this.opportunity?.commission != null}">
                        万元
                    </g:if>
                    </span>
                </div>
                <label class="col-md-3 control-label">客户级别：</label>

                <div class="col-md-3">
                    <span class="cont">
                        <g:if test="${this.opportunity?.lender?.level?.name == 'A'}">优质</g:if>
                        <g:if test="${this.opportunity?.lender?.level?.name == 'B'}">次优</g:if>
                        <g:if test="${this.opportunity?.lender?.level?.name == 'C'}">疑难</g:if>
                        <g:if test="${this.opportunity?.lender?.level?.name == 'D'}">不良</g:if>
                        <g:if test="${this.opportunity?.lender?.level?.name == '待评级'}">待评级</g:if>
                    </span>

                </div>
            </div>

            <div class="form-group">

                <label class="col-md-3 control-label">借款合同单号：</label>

                <div class="col-md-2">
                    <span class="cont">${this.opportunity?.externalId}</span>
                </div>
                <label class="col-md-3 control-label">意向金：</label>

                <div class="col-md-2">
                    <span class="cont">${this.opportunity?.advancePayment}
                    <g:if test="${this.opportunity?.advancePayment != null}">
                        元
                    </g:if>
                    </span>
                </div>

            </div>
            <div class="form-group">

                <label class="col-md-3 control-label">抵押率：</label>

                <div class="col-md-2">
                    <span class="cont">（抵押率假值）</span>
                </div>
            </div>

            <g:each in="${this.opportunityFlexFieldCategorys}">
                    <g:if test="${it?.flexFieldCategory?.name == '借款用途' || it?.flexFieldCategory?.name == '还款来源' || it?.flexFieldCategory?.name == '风险结论'}">
                    <div class="form-group summary">
                    <label class="col-md-3 control-label">${it?.flexFieldCategory?.name}：</label>
                    <div class="col-md-9">
                    <g:each in="${it?.fields}" var="field">
                                                <span class="cont text-comment">${field?.name}:${field?.value}</span>
                                            </g:each>
                </div>


                            </div>
                    </g:if>
                    </g:each>





            <g:each in="${this.opportunityFlows}">
                <g:if test="${it?.stage?.code == '26'}">

                    <div class="form-group summary">
                        <label class="col-md-3 control-label">主审意见：</label>

                        <div class="col-md-9">
                            <span class="cont text-comment">${it?.comments}</span>
                        </div>
                    </div>

                </g:if>

            </g:each>

        </div>

    </div>

</div>--}%
    <g:if test="${this.opportunity?.stage?.code == '09'}">
        <div class="row" id="seventh">
            <div class="hpanel horange">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link target="_blank" controller="attachments"
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
                                        ${it?.assignedTo?.fullName}
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
                                                <span class="vertical-date pull-right">${role?.user?.fullName} <br/> <small><g:formatDate
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
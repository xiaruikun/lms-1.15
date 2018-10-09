<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>展期订单详情-6</title>
    <style>
    .select2-container .select2-choice {
        height: 30px;
    !important;
        line-height: 30px;
    }

    .select2-chosen, .select2-choice > span:first-child, .select2-container .select2-choices .select2-search-field input {
        padding: 0 12px !important;
    }

    table.text-center th {
        text-align: center;
    }
    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body clearfix">
            <!-- <h2 class="font-light pull-left">
                订单号: ${this.opportunity?.serialNumber}
            </h2> -->
            <div id="navbar-example" class="pull-right href-link">
                <ul class="nav navbar-nav" role="tablist">
                    <li class="active"><a class="btn-link page-scroll" href="#first">订单基本信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#second">房产信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#third">产调结果</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fourth">借款人及抵押人信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fifth">展期信息</a></li>
                    %{--<li><a class="btn-link page-scroll" page-scroll="" href="#sixth">初审意见</a></li>--}%
                    <li><a class="btn-link page-scroll" page-scroll="" href="#seventh">主审结果</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#eighth">失败原因</a></li>
                    <g:if test="${this.opportunity?.stage?.code == '09'}">
                        <li><a class="btn-link page-scroll" page-scroll="" href="#ninth">外访调查报告</a></li>
                        <li><a class="btn-link page-scroll" page-scroll="" href="#tenth">抵押公证信息</a></li>
                    </g:if>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fifteenth">任务指派</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#sixteenth">订单跟踪</a></li>
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
                <div class="panel-tools">
                    %{--<g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="edit"><i class="fa fa-edit"></i>编辑</g:link>--}%
                </div>
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
                <div class="col-md-2"><strong>${this.opportunity?.stage?.description}</strong></div>
                <g:if test="${this.opportunity?.status == 'Failed'}"><span
                        class="label label-danger pull-right">订单结果：失败</span></g:if>
                <g:elseif test="${this.opportunity?.status == 'Completed'}"><span
                        class="label label-success pull-right">订单结果：成功</span></g:elseif>
                <g:else><span class="label label-info pull-right">订单结果：进行中</span></g:else>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>借款人</span><strong>${this.opportunityContacts[0]?.contact?.fullName} ${this.opportunityContacts[0]?.contact?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-3 border-right">
                        <div class="contact-stat"><span>经纪人</span><strong>${this.opportunity?.contact?.fullName}</strong><strong class="cellphoneFormat"> ${this.opportunity?.contact?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-3 border-right">
                        <div class="contact-stat"><span>支持经理：</span><strong>${this.opportunity?.user?.fullName}</strong>
                            <strong class="cellphoneFormat"> ${this.opportunity?.user?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>评房总价（万元）</span><strong><g:formatNumber
                                number="${this.opportunity?.loanAmount}" minFractionDigits="2"
                                maxFractionDigits="2"/></strong>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="contact-stat"><span>拟展期金额（万元）</span><strong><g:formatNumber
                                number="${this.opportunity?.requestedAmount}" minFractionDigits="2"
                                maxFractionDigits="2"/></strong>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div class="row" id="second">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="opportunity" action="historyShow2"
                            params="[id: this.opportunity?.id]"><i class="fa fa-search"></i>查询明细</g:link>
                    <g:if test="${this.opportunity?.collaterals?.size() <= 0}">
                        <g:link class="btn btn-info btn-xs" controller="collateral" action="create"
                                params="[opportunity: this.opportunity?.id]"><i class="fa fa-plus"></i>添加</g:link>
                    </g:if>
                    <g:if test="${this.opportunity?.collaterals?.size() > 0 && this.opportunity?.stage?.code == '52' && this.opportunity?.collaterals[0]?.status != 'Completed'}">
                        <g:link class="btn btn-info btn-xs" controller="collateral" action="pvQuery"
                                id="${this.opportunity?.collaterals[0]?.id}"><i
                                class="fa fa-search-plus"></i>询值</g:link>
                    </g:if>
                </div>
                房产信息
            </div>

            <div class="panel-body form-horizontal no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            %{--<g:sortableColumn property="externalId"
                                              title="外部唯一ID"></g:sortableColumn>--}%
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
                                              title="单价（元）"></g:sortableColumn>
                            <g:sortableColumn property="totalPrice"
                                              title="总价（万元）"></g:sortableColumn>
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
                                    <g:link controller="collateral" action="show"
                                            id="${it?.id}">${it?.city?.name}</g:link>
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
                                    <g:formatNumber number="${it?.unitPrice}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>
                                </td>
                                <td>
                                    <g:formatNumber number="${it?.totalPrice}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>
                                </td>
                                %{--<td>
                                    ${it.status}
                                </td>--}%
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
                                    <g:formatDate format="yyyy"
                                                  date="${this.opportunity?.collaterals[0]?.buildTime}"/>
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
            <g:if test="${it?.flexFieldCategory?.name == '抵押物情况'}">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}"
                                    action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>

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

    <div class="row" id="third">
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '产调结果'}">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            %{--<g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}" action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>--}%
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
    %{--<div class="row" id="third">
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

    <div class="row" id="fourth">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="attachments" action="create"
                            id="${this.opportunity.id}"><i class="fa fa-upload"></i>补充附件</g:link>
                    <g:link target=" _blank" class="btn btn-info btn-xs" controller="attachments"
                            action="show"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '基础材料']"><i class="fa fa-file-image-o"></i>基础材料</g:link>
                    <g:link target=" _blank" class="btn btn-info btn-xs" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '其他资料']"><i class="fa fa-file-image-o"></i>其他资料</g:link>
                    <g:link target=" _blank" class="btn btn-info btn-xs" controller="attachments"
                            action="show"
                            id="${this.opportunity.id}"
                            params="[attachmentTypeName: '征信']"><i class="fa fa-file-o"></i>征信</g:link>
                    <g:if test="${this.opportunity.contacts?.size() > 0}">
                        <!-- <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                                     action="blackListShow"
                                     id="${this.opportunity.id}"><i class="fa fa-database"></i>中佳信黑名单</g:link> -->
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
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}"
                                    action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>
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
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}"
                                    action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>
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
                展期申请信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="requestedAmount" title="贷款金额（万元）"></g:sortableColumn>
                            <g:sortableColumn property="loanDuration" title="贷款期限（月）"></g:sortableColumn>
                            <g:sortableColumn property="actualLoanDuration" title="拟展期金额（万元）"></g:sortableColumn>
                            <g:sortableColumn property="actualLoanDuration" title="拟展期期限（月）"></g:sortableColumn>
                            <g:sortableColumn property="mortgageType" title="抵押类型"></g:sortableColumn>
                            <g:sortableColumn property="firstMortgageAmount" title="一抵金额（万元）"></g:sortableColumn>
                            <g:sortableColumn property="typeOfFirstMortgage" title="一抵性质"></g:sortableColumn>
                            <g:sortableColumn property="productName" title="产品类型"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunity}">
                            <tr>
                                <td>
                                    ${it?.parent?.actualAmountOfCredit}
                                </td>
                                <td>
                                    ${it?.parent?.actualLoanDuration}
                                </td>
                                <td>
                                    ${it?.requestedAmount}
                                </td>
                                <td>
                                    ${it?.loanDuration}
                                </td>
                                <td>
                                    ${it?.mortgageType?.name}
                                </td>
                                <td>
                                    ${it?.firstMortgageAmount}
                                </td>
                                <td>
                                    ${it?.typeOfFirstMortgage?.name}
                                </td>
                                <td>
                                    ${it?.product?.name}
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <g:each in="${this.opportunityFlexFieldCategorys}">
        <g:if test="${it?.flexFieldCategory?.name == '展期资料变更小结'}">
            <div class="row">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}"
                                    action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>
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

%{-- <div class="row" id="sixth">
     <div class="hpanel hgreen">
         <div class="panel-heading">
             初审意见
         </div>

         <div class="panel-body no-padding">
             <div class="table-responsive">
                 <table class="table table-striped table-bordered table-hover">
                     <thead>
                     <g:sortableColumn property="requestedAmount" title="贷款可贷金额（万）"></g:sortableColumn>
                     <g:sortableColumn property="loanDuration" title="贷款期限（月）"></g:sortableColumn>
                     <g:sortableColumn property="productName" title="产品类型"></g:sortableColumn>
                     <g:sortableColumn property="firstMortgageAmount" title="初审提示"></g:sortableColumn>
                     </thead>
                     <tbody>
                     <g:each in="${this.opportunity}">
                         <tr>
                             <td width="12%">
                                 ${it?.maximumAmountOfCredit}
                             </td>
                             <td width="15%">
                                 ${it?.loanDuration}
                             </td>
                             <td width="15%">
                                 ${it?.product?.name}
                             </td>
                             <td width="53%">
                                 <g:each in="${this.opportunityFlows}">
                                     <g:if test="${it?.stage?.code == '07'}">
                                         <p class="text-comment">${it?.comments}</p>
                                     </g:if>

                                 </g:each>
                             </td>
                         </tr>
                     </g:each>
                     </tbody>
                 </table>
             </div>
         </div>
     </div>
 </div>--}%

    <div class="row" id="seventh">
        <div class="hpanel hblue">
            <div class="panel-heading">
                主审结果
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table  table-bordered">
                        <tbody>
                        <tr>
                            <td width="92%">
                                <div class="form form-horizontal">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">审核可展期金额：</label>

                                        <div class="col-md-2">
                                            <span class="cont">${this.opportunity?.actualAmountOfCredit}
                                            <g:if test="${this.opportunity?.actualAmountOfCredit != null}">
                                                万元
                                            </g:if>
                                            </span>
                                        </div>

                                        <label class="col-md-3 control-label">审核可展期期限</label>

                                        <div class="col-md-2">
                                            <span class="cont">${this.opportunity?.actualLoanDuration}
                                            <g:if test="${this.opportunity?.actualLoanDuration != null}">
                                                月
                                            </g:if>
                                            </span>
                                        </div>

                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">展期需先还本金：</label>

                                        <div class="col-md-2">
                                            <span class="cont">${this.opportunity?.advancePayment}
                                            <g:if test="${this.opportunity?.advancePayment != null}">
                                                万元
                                            </g:if>
                                            </span>
                                        </div>

                                        <label class="col-md-3 control-label">展期费率</label>

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
                                            <span class="cont"><g:formatNumber number="${this.opportunity?.commissionRate}" 
  824                                                  maxFractionDigits="9"/>
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

                                        <label class="col-md-3 control-label">抵押率：</label>

                                        <div class="col-md-2">
                                            %{--<span class="cont">
                                                <g:formatNumber number="${this.opportunity?.collaterals[0]?.loanToValue}" minFractionDigits="2" maxFractionDigits="2"/>%
                                            </span>--}%
                                            <span class="cont"><g:formatNumber
                                                    number="${this.opportunity?.collaterals[0]?.loanToValue}"
                                                    minFractionDigits="2" maxFractionDigits="2"/>
                                            <g:if test="${this.opportunity?.collaterals[0]?.loanToValue != null}">
                                                %
                                            </g:if>
                                            </span>
                                        </div>
                                        <label class="col-md-3 control-label">抵押凭证类型：</label>

                                        <div class="col-md-2">
                                            <span class="cont">
                                                ${this.opportunity?.mortgageCertificateType?.name}
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td width="8%" class="text-center">
                                <g:link params="[flag: 'zhushen']" id="${this.opportunity?.id}" controller="opportunity"
                                        action="opportunityEdit01" class="btn btn-info btn-xs">
                                    <i class="fa fa-edit"></i>
                                    编辑
                                </g:link>
                            </td>
                        </tr>
                        <g:each in="${this.opportunityFlexFieldCategorys}">
                            <g:if test="${it?.flexFieldCategory?.name == '借款用途' || it?.flexFieldCategory?.name == '还款来源' || it?.flexFieldCategory?.name == '风险结论'}">
                                <tr>
                                    <td width="92%">
                                        <div class="form form-horizontal">

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">${it?.flexFieldCategory?.name}：</label>

                                                <div class="col-md-9">
                                                    <g:each in="${it?.fields}" var="field">
                                                        <span class="cont text-comment">${field?.name}:${field?.value}</span>
                                                    </g:each>
                                                </div>
                                            </div>

                                        </div>
                                    </td>
                                    <td width="8%" class="text-center">
                                        <g:link class="btn btn-info btn-xs" controller="opportunityFlexField"
                                                id="${it?.id}"
                                                action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>
                                    </td>
                                </tr>
                            </g:if>
                        </g:each>

                        %{--<g:each in="${this.opportunityFlows}">
                            <g:if test="${it?.stage?.code == '53'}">
                                <tr>

                                    <td width="92%">
                                        <div class="form form-horizontal">

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">放款前要求：</label>

                                                <div class="col-md-9">
                                                    <span class="cont text-comment">${it?.comments}</span>
                                                </div>
                                            </div>

                                        </div>
                                    </td>
                                    <td width="8%" class="text-center">
                                        <g:link controller="opportunityFlow" action="edit" class="btn btn-info btn-xs"
                                                id="${it?.id}">
                                            <i class="fa fa-edit"></i>
                                            编辑
                                        </g:link>
                                    </td>

                                </tr>
                            </g:if>

                        </g:each>--}%
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row" id="eighth">
        <div class="hpanel horange">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link params="[flag: 'shibai']" id="${this.opportunity?.id}" controller="opportunity"
                            action="edit" class="btn btn-info btn-xs"><i
                            class="fa fa-edit"></i>编辑</g:link>
                </div>
                失败原因
            </div>

            <div class="panel-body form-horizontal p-xs">
                <div class="form-group">
                    <label class="col-md-3 control-label">未成交归类：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.causeOfFailure?.name}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row" id="ninth">
        <div class="hpanel horange">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${this.opportunity?.id}"
                            action="showOpportunityFlexField" target=" _blank"><i class="fa"></i>外访报告</g:link>
                    <g:link target=" _blank" controller="attachments"
                            action="show"
                            id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '下户信息']" class="btn btn-info btn-xs">外访附件</g:link>
                </div>
                外访调查报告
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="requestedAmount" title="借款人"></g:sortableColumn>
                            <g:sortableColumn property="assignedTo" title="外访人员"></g:sortableColumn>
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
                                    ${this.opportunityContacts[0]?.contact?.fullName}
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

    %{--<g:each in="${this.opportunityFlexFieldCategorys}">
    <g:if test="${it?.flexFieldCategory?.name == '抵押物评估值' || it?.flexFieldCategory?.name == '抵押物其他情况' || it?.flexFieldCategory?.name == '外访预警'}">
    <div class="row" id="">
        <div class="hpanel hgreen">
            <div class="panel-heading ">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="opportunityFlexField" id="${it?.id}"
                            action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>
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
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlexField.description.label', default: '描述')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlexField.dataType.label', default: '数据类型')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlexField.defaultValue.label', default: '默认值')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlexField.valueConstraints.label', default: '约束')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlexField.value.label', default: '值')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${it?.fields}">
                            <tr>
                                <td width="15%"><g:link controller="opportunityFlexField" action="show"
                                                        id="${it?.id}"
                                                        class="firstTd">${it?.name}</g:link></td>
                                <td width="15%">${it?.description}</td>
                                <td width="15%">${it?.dataType}</td>
                                <td width="15%">${it?.defaultValue}</td>
                                <td width="20%">${it?.valueConstraints}</td>
                                <td width="20%">${it?.value}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</g:if>
</g:each>--}%

    <div class="row" id="tenth">
        <div class="hpanel horange">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link target=" _blank" controller="attachments"
                            action="show"
                            id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '借款合同']" class="btn btn-info btn-xs">借款合同</g:link>
                    <g:link target=" _blank" controller="attachments"
                            action="show"
                            id="${this.opportunity?.id}"
                            params="[attachmentTypeName: '抵押登记受理单']" class="btn btn-info btn-xs">抵押登记受理单</g:link>
                </div>
                抵押公证信息
            </div>

            <div class="panel-body form-horizontal p-xs">
                <div class="form-group m-b-none">
                    <label class="col-md-3 control-label">是否抵押：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            <g:if test="${this.opportunity?.mortgagingStatus == true}">是</g:if>
                            <g:if test="${this.opportunity?.mortgagingStatus == false}">否</g:if>
                        </span>
                    </div>
                    <label class="col-md-3 control-label">是否公证：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            <g:if test="${this.opportunity?.notarizingStatus == true}">是</g:if>
                            <g:if test="${this.opportunity?.notarizingStatus == false}">否</g:if>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row" id="eleventh">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    %{--<g:link class="btn btn-info btn-xs" controller="opportunityTeam" action="create"
                            id="${this.opportunity.id}"><i class="fa fa-plus"></i>新增</g:link>--}%
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
                            <g:sortableColumn property="option" title="${message(code: '操作')}"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityTeams}">
                            <tr>
                                <td>${it.user?.fullName}</td>
                                <td>
                                    <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/>
                                </td>
                                <td>
                                    <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.modifiedDate}"/>
                                </td>
                                <td>${it?.opportunityLayout?.description}</td>
                                <td>
                                    <g:form resource="${it}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">

        <div class="row " id="twelfth">
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
                                <g:sortableColumn property="teamRole"
                                                  title="${message(code: 'opportunityRole.teamRole.label', default: '权限')}"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.opportunityRoles}">
                                <tr>
                                    <td>${it.user?.username}</td>
                                    <td>${it.stage?.name}</td>
                                    <td>${it.teamRole?.name}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" id="thirteenth">
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
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.opportunityNotifications}">
                                <tr>
                                    <td>${it.user?.username}</td>
                                    <td>${it.stage?.name}</td>
                                    <td>${it.messageTemplate?.text}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" id="fourteenth">
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
                                <g:sortableColumn width="50%" property="comments"
                                                  title="${message(code: 'opportunityFlow.comments.label', default: '备注')}"/>
                                <g:sortableColumn width="5%" property="opportunityLayout"
                                                  title="${message(code: 'opportunityFlow.opportunityLayout.label', default: '布局')}"/>
                                <g:sortableColumn width="5%" property="comments"
                                                  title="操作"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.opportunityFlows}">
                                <tr>
                                    <td width="5%"><g:link controller="opportunityFlow" action="show"
                                                           id="${it.id}">${it?.executionSequence}</g:link></td>
                                    <td width="10%">${it?.stage?.name}</td>
                                    <td width="5%">${it?.canReject}</td>
                                    <td width="10%"><g:formatDate class="weui_input" date="${it?.startTime}"
                                                                  format="yyyy-MM-dd HH:mm:ss"
                                                                  name="startTime" autocomplete="off"
                                                                  readonly="true"></g:formatDate></td>
                                    <td width="10%"><g:formatDate class="weui_input" date="${it?.endTime}"
                                                                  format="yyyy-MM-dd HH:mm:ss"
                                                                  name="endTime" autocomplete="off"
                                                                  readonly="true"></g:formatDate></td>
                                    <td width="50%">
                                        <p class="text-comment">${it?.comments}</p>
                                    </td>
                                    <td width="5%">
                                        <p class="text-comment">${it?.opportunityLayout?.description}</p>
                                    </td>
                                    <td width="5%"><g:link controller="opportunityFlow" action="edit"
                                                           class="btn btn-xs btn-default"
                                                           id="${it.id}">编辑</g:link></td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </sec:ifAllGranted>




    <div class="row" id="fifteenth">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <g:if test="${this.opportunity?.contacts.size() > 0}">
                        <g:link class="btn btn-info btn-xs" controller="activity"
                                params="[opportunityId: this.opportunity.id]"
                                action="create"><i class="fa fa-plus"></i>新增</g:link></g:if>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                任务指派
            </div>

            <div class="panel-body">
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
                                              title="${message(code: 'activity.contact.label', default: '经纪人姓名')}"/>
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
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.activities}">
                            <tr>
                                <td><g:link controller="activity" action="show" id="${it.id}"
                                            class="firstTd">${it?.type?.name}</g:link></td>
                                <td>${it?.subtype?.name}</td>
                                <td><g:formatDate class="weui_input" date="${it?.startTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="startTime" autocomplete="off"
                                                  readonly="true"></g:formatDate></td>
                                <td><g:formatDate class="weui_input" date="${it?.endTime}" format="yyyy-MM-dd HH:mm:ss"
                                                  name="endTime" autocomplete="off" readonly="true"></g:formatDate></td>
                                <td><g:formatDate class="weui_input" date="${it?.actualStartTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="actualStartTime" autocomplete="off"
                                                  readonly="true"></g:formatDate></td>
                                <td><g:formatDate class="weui_input" date="${it?.actualEndTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="actualEndTime" autocomplete="off"
                                                  readonly="true"></g:formatDate></td>
                                <td>${it?.contact?.fullName}</td>
                                <td>${it?.user?.fullName}</td>
                                <td>${it?.assignedTo?.fullName}</td>
                                <td>${it?.status}</td>
                                <td>${it?.city}</td>
                                <td>${it?.address}</td>
                                <td>${it?.longitude}</td>
                                <td>${it?.latitude}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>


    <div class="row" id="sixteenth">
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
<footer class="footer bg-success">
    <div class="row">
        %{--<div class="pull-left col-md-7 btn-sm">
            <span class="pull-left m-r-xs font-bold">${this.opportunity?.stage?.name}</span>

            <div class="progress full m-n">

                <div style="width: <g:formatNumber number="${this.progressPercent}" maxFractionDigits="2"/>%"
                     aria-valuemax="100" aria-valuemin="0"
                     aria-valuenow="<g:formatNumber number="${this.progressPercent}" maxFractionDigits="2"/>"
                     role="progressbar"
                     class=" progress-bar progress-bar-info">
                    <g:formatNumber number="${this.progressPercent}" maxFractionDigits="2"/>%
                </div>
            </div>
        </div>--}%
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
            <button class="btn  btn-sm btn-info" data-toggle="modal" data-target="#rejectReason">
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
            <button class="btn  btn-sm btn-danger" data-toggle="modal" data-target="#myModal">
                <i class="fa fa-exclamation-circle"></i> 失败</button>
        </div>
    </div>
</footer>

<script>
    $(function () {
        $("body").addClass("fixed-small-header");
    });
</script>
</body>

</html>

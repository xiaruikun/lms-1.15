<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>订单详情</title>
    <style>
    .footer {
        z-index: 10;
        position: fixed;
        bottom: 0;
        left: 181px;
        background-color: #dff0d8;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .small-header {
        position: fixed;
        top: 62px;
        z-index: 100;
        right: 0;
        left: 181px;
    }

    @media screen and (max-width: 768px) {
        .footer {
            left: 0;
        }

        .small-header {
            left: 0;
        }
    }

    .href-link .page-scroll {
        font-weight: 600;
        font-size: 14px;
        padding: 0 10px;
        color: #455463;
    }

    .href-link li:hover .page-scroll, .href-link li.active .page-scroll {
        color: #5bc0de;
    }

    .panel-body.active {
        border: none;
    }

    .small-header .panel-body {
        position: relative;
    }

    .small-header .panel-body h2 {
        height: 20px;
        line-height: 20px;
    }

    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body clearfix">
            %{--<div class="font-light pull-left">
                订单号: ${this.opportunity.serialNumber}
            </div>--}%
            <h2 class="font-light pull-left">
                订单号: ${this.opportunity.serialNumber}
            </h2>

            <div id="" class="pull-right href-link">
                <ul class="list-inline clearfix" style="margin-bottom: 0">
                    <li class="active"><a class="btn-link page-scroll" href="#first">订单基本信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#second">报单人信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#third">房产信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fourth">借款人及抵押人信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fifth">附件信息</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#fifsixth">息费</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#sixth">征信记录</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#seventh">产调结果</a></li>
                    <li><a class="btn-link page-scroll" page-scroll="" href="#twelfth">任务指派</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>

<div class="content animate-panel" style="padding-top: 80px;">
    <div class="row">
        <div id="hbreadcrumb" class="pull-left">
            <ol class="hbreadcrumb breadcrumb" style="background-color: transparent;margin-top: 0;">
                <li>中佳信LMS</li>
                <li>
                    <g:link controller="opportunityTeam" action="index">订单管理</g:link>
                </li>
                <li class="active">
                    <span>信息查看</span>
                </li>
            </ol>
        </div>
    </div>

    <div class="row" id="first">
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
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="edit"><i class="fa fa-edit"></i>编辑</g:link>
                </div>
                订单基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12">
                        <h4>${this.opportunity.serialNumber}</h4></div>

                    <div class="col-md-1">
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
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-1 border-right">
                        <div class="contact-stat"><span>贷款金额</span><strong>${this.opportunity?.requestedAmount}万元</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat"><span>贷款期限</span><strong>${this.opportunity?.loanDuration}月</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat"><span>评房（可贷）</span><strong>${this.opportunity?.loanAmount}万元</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>初审（可贷）</span><strong>${this.opportunity?.maximumAmountOfCredit}万元；${this.opportunity?.lender?.level?.name}；${this.opportunity?.dealRate}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>审核可贷金额</span><strong>${this.opportunity?.actualAmountOfCredit}万元；${this.opportunity?.interestPaymentMethod?.name}；月息${this.opportunity?.monthlyInterest}%；
                        渠道服务费${this.opportunity?.commissionRate}%；${this.opportunity?.commissionPaymentMethod}
                        </div>
                    </div>

                    <div class="col-md-1">
                        <div class="contact-stat"><span>产品类型:</span> <strong>${this.opportunity?.product?.name}
                        </div>
                    </div>

                    <div class="col-md-1">
                        <div class="contact-stat"><span>抵押类型:</span> <strong>${this.opportunity?.mortgageType?.name}
                        </div>
                    </div>

                    <div class="col-md-1">
                        <div class="contact-stat"><span>一抵金额:</span> <strong>${this.opportunity?.firstMortgageAmount}万元
                        </div>
                    </div>

                    <div class="col-md-1">
                        <div class="contact-stat"><span>二抵金额:</span> <strong>${this.opportunity?.secondMortgageAmount}万元
                        </div>
                    </div>

                    <div class="col-md-1">
                        <div class="contact-stat"><span>一抵性质:</span> <strong>${this.opportunity?.typeOfFirstMortgage?.name}
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
                    <div class="col-md-3 border-right">
                        <div class="contact-stat">
                            <span>姓名</span>
                            <strong>
                                ${this.opportunity?.contact?.fullName}
                            </strong>
                        </div>
                    </div>

                    <div class="col-md-3 border-right">
                        <div class="contact-stat">
                            <span>手机号</span>
                            <strong class="cellphoneFormat">${this.opportunity?.contact?.cellphone}</strong>
                        </div>
                    </div>

                    <div class="col-md-3 border-right">
                        <div class="contact-stat">
                            <span>所属公司</span>
                            <strong>${this.opportunity?.contact?.account?.name}</strong>
                        </div>
                    </div>

                    <div class="col-md-3">
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
                    <g:if test="${this.opportunity?.collaterals.size() <= 0}">
                        <g:link class="btn btn-info btn-xs" controller="collateral" action="create"
                                params="[opportunity: this.opportunity?.id]"><i class="fa fa-plus"></i>添加</g:link>
                    </g:if>
                    <g:if test="${this.opportunity?.collaterals.size() > 0 && this.opportunity?.stage?.code == '04' && this.opportunity?.collaterals[0].status != 'Completed'}">
                        <g:link class="btn btn-info btn-xs" controller="collateral" action="pvQuery"
                                id="${this.opportunity?.collaterals[0].id}"><i class="fa fa-search-plus"></i>询值</g:link>
                    </g:if>

                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                房产信息
            </div>

            <div class="panel-body form-horizontal no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="externalId"
                                              title="外部唯一ID"></g:sortableColumn>
                            <g:sortableColumn property="city"
                                              title="城市"></g:sortableColumn>
                            <g:sortableColumn property="district"
                                              title="区县"></g:sortableColumn>
                            <g:sortableColumn property="projectName"
                                              title="小区名称"></g:sortableColumn>
                            <g:sortableColumn property="address"
                                              title="地址"></g:sortableColumn>
                            <g:sortableColumn property="orientation"
                                              title="朝向"></g:sortableColumn>
                            <g:sortableColumn property="area"
                                              title="面积"></g:sortableColumn>
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
                                              title="单价"></g:sortableColumn>
                            <g:sortableColumn property="totalPrice"
                                              title="总价"></g:sortableColumn>
                            <g:sortableColumn property="status"
                                              title="状态"></g:sortableColumn>
                            <g:sortableColumn property="requestStartTime"
                                              title="评房开始时间"></g:sortableColumn>
                            <g:sortableColumn property="requestEndTime"
                                              title="评房结束时间"></g:sortableColumn>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunity.collaterals}">
                            <tr>
                                <td><g:link controller="collateral" action="show"
                                            id="${it.id}">${it.externalId}</g:link></td>
                                <td>
                                    ${it.city?.name}
                                </td>
                                <td>
                                    ${it.district}
                                </td>
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
                                    ${it.unitPrice}
                                </td>
                                <td>
                                    ${it.totalPrice}
                                </td>
                                <td>
                                    ${it.status}
                                </td>
                                <td>
                                    ${it.requestStartTime}
                                </td>
                                <td>
                                    ${it.requestEndTime}
                                </td>

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
                    <g:link class="btn btn-info btn-xs" controller="opportunityContact" action="create"
                            params="[opportunity: this.opportunity.id]"><i class="fa fa-plus"></i>新增</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                借款人及抵押人信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="type"
                                              title="${message(code: 'opportunityContact.type.label', default: '联系人')}"></g:sortableColumn>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'opportunityContact.fullName.label', default: '姓名')}"></g:sortableColumn>
                            <g:sortableColumn property="idNumber"
                                              title="${message(code: 'opportunityContact.idNumber.label', default: '身份证号')}"></g:sortableColumn>
                            <g:sortableColumn property="cellphone"
                                              title="${message(code: 'opportunityContact.cellphone.label', default: '手机号')}"></g:sortableColumn>
                            <g:sortableColumn property="maritalStatus"
                                              title="${message(code: 'opportunityContact.maritalStatus.label', default: '婚否')}"></g:sortableColumn>
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

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="type"
                                              title="${message(code: 'attachments.opportunity.type.name.label', default: '文件类型')}"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '房产证']">房产证</g:link></td>
                        </tr>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '身份证']">身份证</g:link></td>
                        </tr>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '产调结果']">产调结果</g:link></td>
                        </tr>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '征信报告']">征信报告</g:link></td>
                        </tr>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '下户信息']">下户信息</g:link></td>
                        </tr>
                        <!-- <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '返点证明']">返点证明</g:link></td>
                        </tr>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '放款证明']">放款证明</g:link></td>
                        </tr> -->
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '他项证明']">他项证明</g:link></td>
                        </tr>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '公证书']">公证书</g:link></td>
                        </tr>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '借款合同']">借款合同</g:link></td>
                        </tr>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '面谈笔录']">面谈笔录</g:link></td>
                        </tr>
                        <tr>
                            <td><g:link controller="attachments" action="show" id="${this.opportunity.id}"
                                        params="[attachmentTypeName: '被执情况']">被执情况</g:link></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row" id="fifsixth">
        <div class="hpanel hgreen">
            <div class="panel-heading ">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="opportunityProduct" id="${this.opportunity?.id}"
                            action="batchEdit">编辑</g:link>
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="initOpportunityProduct">计算息费</g:link>
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="refreshOpportunityProduct">重新计算</g:link>
                </div>
                息费
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '费率类型')}"/>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '费率')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.score.label', default: '贷款期限')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.provider.label', default: '分期付款')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.createdDate.label', default: '固定费率')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.createdDate.label', default: '起始交费月')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityProduct}">
                            <tr>
                                <td><g:link controller="opportunityProduct" action="batchEdit" id="${this.opportunity?.id}"
                                            class="firstTd">${it?.productInterestType?.name}</g:link></td>
                                <td>${it?.rate}</td>
                                <td>${it?.monthes}</td>
                                <td>${it?.installments}</td>
                                <td>${it?.fixedRate}</td>
                                <td>${it?.firstPayMonthes}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row" id="sixth">
        <div class="hpanel hgreen">
            <div class="panel-heading ">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="tongdun">同盾</g:link>
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="bairong">百融</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                征信记录
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '征信人员姓名')}"/>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '征信人员类型')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.score.label', default: '征信值')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.provider.label', default: '征信渠道')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.createdDate.label', default: '征信查询时间')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.creditReport}">
                            <tr>
                                <td><g:link controller="creditReport" action="show" id="${it.id}"
                                            class="firstTd">${it.contact?.fullName}</g:link></td>
                                <td>${com.next.OpportunityContact.findByOpportunityAndContact(it.opportunity, it.contact)?.type?.name}</td>
                                <td>${it.score}</td>
                                <td>${it.provider?.name}</td>
                                <td><g:formatDate class="weui_input" date="${it.createdDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"
                                                  name="createdDate" autocomplete="off"
                                                  readonly="true"></g:formatDate></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row" id="seventh">
        <div class="hpanel hgreen">
            <div class="panel-heading ">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="initReport">初始化</g:link>
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="scoring">计算总分</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                产调结果
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '订单编号')}"/>
                            <g:sortableColumn property="executionSequence"
                                              title="${message(code: 'opportunityFlow.executionSequence.label', default: '评估分数')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunityFlow.score.label', default: '评估时间')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.liquidityRiskReport}">
                            <tr>
                                <td><g:link controller="liquidityRiskReport" action="show" id="${it.id}"
                                            class="firstTd">${it?.opportunity?.serialNumber}</g:link></td>
                                <td>${it.score}</td>
                                <td><g:formatDate class="weui_input" date="${it.modifiedDate}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="modifiedDate" autocomplete="off"
                                                  readonly="true"></g:formatDate></td>
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
                    <g:link class="btn btn-info btn-xs" controller="opportunityTeam" action="create"
                            id="${this.opportunity.id}"><i class="fa fa-plus"></i>添加</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                用户
            </div>

            <div class="panel-body">
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

        <div class="row " id="ninth">
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

        <div class="row" id="tenth">
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

        <div class="row" id="eleventh">
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
                                <g:sortableColumn property="executionSequence"
                                                  title="${message(code: 'opportunityFlow.executionSequence.label', default: '次序')}"/>
                                <g:sortableColumn property="stage"
                                                  title="${message(code: 'opportunityFlow.stage.label', default: '订单阶段')}"/>
                                <g:sortableColumn property="canReject"
                                                  title="${message(code: 'opportunityFlow.canReject.label', default: '能否回退')}"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.opportunityFlows}">
                                <tr>
                                    <td><g:link controller="opportunityFlow" action="show"
                                                id="${it.id}">${it.executionSequence}</g:link></td>
                                    <td>${it.stage?.name}</td>
                                    <td>${it.canReject}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </sec:ifAllGranted>




    <div class="row" id="twelfth">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <g:if test="${this.opportunity?.contacts.size()>0}">
                    <g:link class="btn btn-info btn-xs" controller="activity"
                            params="[opportunityId: this.opportunity.id]"
                            action="create"><i class="fa fa-plus"></i>添加</g:link></g:if>
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


    <div class="row" id="thirteenth">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                订单跟踪
            </div>

            <div class="panel-body">
                <div class="v-timeline vertical-container animate-panel" data-child="vertical-timeline-block"
                     data-delay="1">
                    <g:each in="${this.history}">
                        <div class="vertical-timeline-block">
                            <div class="vertical-timeline-icon navy-bg">
                                <i class="fa fa-calendar"></i>
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
                    </g:each>
                </div>
            </div>
        </div>
    </div>
    <footer class="footer alert-success">
        <div class="row">
            <div class="pull-left col-md-7 btn-sm">
                <span class="pull-left" style="margin-right: 5px;">${this.opportunity?.stage?.name}</span>
                <div class="progress full" style="margin: 0">

                    <div style="width: <g:formatNumber number="${this.progressPercent}" maxFractionDigits="2"/>%"
                         aria-valuemax="100" aria-valuemin="0"
                         aria-valuenow="<g:formatNumber number="${this.progressPercent}" maxFractionDigits="2"/>"
                         role="progressbar"
                         class=" progress-bar progress-bar-info">
                        <g:formatNumber number="${this.progressPercent}" maxFractionDigits="2"/>%
                    </div>
                </div>
            </div>
            <div class="btnList pull-right col-md-5 text-right">
                <g:link class="btn btn-sm btn-primary" controller="opportunity" id="${this.opportunity?.id}"
                        action="reject"><i class="fa fa-arrow-up"></i> 上一步</g:link>
                <g:link class="btn btn-sm btn-primary" controller="opportunity" id="${this.opportunity?.id}"
                        action="approve"><i class="fa fa-arrow-down"></i> 下一步</g:link>
                <g:link class="btn btn-sm btn-success" controller="opportunity" id="${this.opportunity?.id}"
                        action="complete"><i class="fa fa-check"></i> 完成</g:link>
                <g:link class="btn  btn-sm btn-danger" controller="opportunity"
                        id="${this.opportunity?.id}" action="cancel"><i
                        class="fa fa-exclamation-circle"></i> 失败</g:link>
            </div>
        </div>

    </footer>
</div>

<script>
    $(function () {
        $(".table-responsive").parent(".panel-body").addClass("active");

    });
</script>
</body>

</html>

<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>贷款审批页1.0-01</title>
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

<body class="fixed-navbar fixed-sidebar">
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div class="pull-right">
                <g:if test="${this.currentFlow?.document && this?.currentFlow?.document?.active}">
                    <input type="hidden" value="${this?.currentFlow?.document?.document}" id="document">
                    <button class="btn btn-success btn-xs" data-toggle="modal"
                            data-target="#myModaDocument">帮助文档</button>
                </g:if>
                <g:else>
                    <button class="btn btn-default btn-xs" disabled="disabled">帮助文档</button>
                </g:else>
            </div>
            <h2 class="font-light">
                合同编号：${this.opportunity?.externalId}
                <span>/</span>
                订单号: ${this.opportunity?.serialNumber}
                <g:if test="${this.opportunity?.isTest}">
                    <span class="label label-danger"><i class="fa fa-star"></i> 测试 <i class="fa fa-star"></i></span>
                </g:if>
            </h2>


        </div>
    </div>
</div>

<div class="content animate-panel" style="padding-bottom:80px">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link id="${this.opportunity?.id}" action="applicationForm"
                            class="btn btn-info btn-xs">
                        <i class="fa fa-send-o"></i>查看详情
                    </g:link>

                </div>
                业务概要%{--${this.opportunity?.externalId}--}%
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-center">
                        <thead>
                        <tr>
                            <th>业务区域</th>
                            <th>业务来源</th>
                            <th>
                                签呈类型
                            </th>
                            <th>客户类型</th>
                            <th>产品类型</th>
                            %{--<th>委托途径</th>--}%
                            <th>共同借款人</th>
                            <th>借款金额（万元）</th>
                            <th>借款期数（月）</th>

                            <th>综合息费（%）</th>

                            <th>月息费（%）</th>

                            <th>借款服务费（%）</th>

                            <th>付息方式</th>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <th>上扣息月份数（月）</th>
                            </g:if>

                            <th>
                                房产总价（万元）
                            </th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.collaterals[0]?.city?.name}</td>
                            <td>${this.opportunity?.contact?.account?.name}</td>
                            <td>
                                <g:if test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('普通签呈'))}">普通签呈</g:if>
                                <g:elseif test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('特批签呈'))}">特批签呈</g:elseif>
                            </td>
                            <td>${this.opportunity?.lender?.level?.description}</td>
                            <td>${this.opportunity?.product?.name}</td>
                            <td>
                                <g:each in="${this.opportunityContacts}">${it?.contact?.fullName}、</g:each>
                            </td>
                            <td>
                                <g:formatNumber number="${this.opportunity?.actualAmountOfCredit}" minFractionDigits="2"
                                                maxFractionDigits="2"/>
                            </td>
                            <td>
                                ${this.opportunity?.loanDuration}
                            </td>

                            <td>

                                <g:formatNumber
                                        number="${this.opportunity?.monthlyInterest + this.opportunity?.serviceCharge}"
                                        
                                        maxFractionDigits="9"/>
                            </td>

                            <td>
                                <g:formatNumber number="${this.opportunity?.monthlyInterest}" 
                                                maxFractionDigits="9"/>
                            </td>

                            <td>
                                <g:formatNumber number="${this.opportunity?.serviceCharge}" 
                                                maxFractionDigits="9"/>
                            </td>
                            <td>
                                ${this.opportunity?.interestPaymentMethod?.name}
                            </td>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <td>${this.opportunity?.monthOfAdvancePaymentOfInterest}</td>
                            </g:if>
                            <td>
                                <g:formatNumber number="${this.opportunity?.loanAmount}" minFractionDigits="2"
                                                maxFractionDigits="2"/>
                            </td>

                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="hpanel hyellow">
            <div class="panel-heading">
                返费信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-center">
                        <thead>
                        <tr>
                            <th>渠道服务费（%）</th>
                            <th>渠道返费（万元）</th>
                            <th>返佣方式</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><g:formatNumber number="${this.opportunity?.commissionRate}" 
                                                maxFractionDigits="9"/></td>
                            <td>${this.opportunity?.commission}</td>
                            <td>${this.opportunity?.commissionPaymentMethod?.name}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                评估信息
            </div>
            <g:each in="${this.opportunity?.collaterals}">
                <div class="panel-body p-xs">
                    <div class="row">
                        <div class="col-md-6 comment-area">
                            评估机构：中佳信评估机构;<br/>
                            评估时间：<g:formatDate date="${it?.createdDate}"
                                               format="yyyy-MM-dd HH:mm:ss"></g:formatDate>;<br/>

                            评估总价：<g:formatNumber number="${it?.totalPrice}" minFractionDigits="2"
                                                 maxFractionDigits="2"/>万元，面积为：${it?.area}m<sup>2</sup>，单价为<g:formatNumber
                                number="${it?.unitPrice}" minFractionDigits="2"
                                maxFractionDigits="2"/>元/m<sup>2</sup>，房产性质：${it?.assetType}，<br>
                            <span class="font-bold">
                                抵押率：<g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>%
                            </span><br/>

                            立项类型：${it?.projectType?.name};<br/>
                            建成时间：<g:formatDate format="yyyy" date="${it?.buildTime}"/>;<br/>


                            物业地址：${it?.address}，小区名称：${it?.projectName}；<br/>
                            <g:if test="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人'))}">
                                <g:each in="${this.opportunityContacts}" var="opportunityContact">
                                    <g:if test="${opportunityContact?.type?.name == '抵押人'}">
                                        抵押人：${opportunityContact?.contact?.fullName}，电话：<span class="cellphoneFormat">${opportunityContact?.contact?.cellphone}</span>；
                                    </g:if></g:each>
                            </g:if>
                            <g:else>
                                抵押人：${this.opportunity?.fullName}，电话：<span class="cellphoneFormat">${this.opportunity?.cellphone}</span>；
                            </g:else><br/>
                        </div>

                        <div class="col-md-6 comment-area border-left">
                            <g:if test="${com.next.CollateralAuditTrail.findAllByParent(it)?.size() > 0}">
                                <g:each in="${com.next.CollateralAuditTrail.findAllByParent(it)}">
                                    评房总价:<g:formatNumber number="${it?.totalPrice}" minFractionDigits="2"
                                                         maxFractionDigits="2"/>万元；
                            评房单价:<g:formatNumber number="${it?.unitPrice}" minFractionDigits="2" maxFractionDigits="2"/> 元；
                            评房时间：<g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.createdDate}"/> <br>
                                </g:each>
                            </g:if>
                        </div>
                    </div>
                </div>
            </g:each>
        </div>
    </div>

    <g:each in="${this.opportunityFlows}">
        <g:if test="${it?.comments && it?.stage?.description == "区域主单"}">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.stage?.description}
                    </div>

                    <div class="panel-body p-xs">

                    %{--<h5>意见</h5>--}%

                        <g:each in="${this.opportunityFlexFieldCategorys}" var="opportunityFlexField">
                            <g:if test="${opportunityFlexField?.flexFieldCategory?.name == '风险结论'}">
                                <g:each in="${opportunityFlexField?.fields}" var="field">
                                    <p class="comment-area">${field?.name}:${field?.value}</p>
                                </g:each>
                            </g:if>
                        </g:each>

                    <p class="comment-area">
                        <g:each in="${this.opportunityFlexFieldCategorys}" var="opportunityFlexField">
                            <g:if test="${opportunityFlexField?.flexFieldCategory?.name == '放款前要求'}">
                                <g:each in="${opportunityFlexField?.fields}" var="field">
                                    <p class="comment-area">${field?.name}:${field?.value}</p>
                                </g:each>
                            </g:if>
                        </g:each>
                    %{--${it?.comments}--}%
                    </p>

                        <div class="pull-right m-t-sm">
                            <span class="m-r-sm">审批人：
                            <g:each in="${this.opportunityRoles}" var="role">
                                <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval'}">
                                    ${role?.user}
                                </g:if>
                            </g:each>
                            </span>
                            日期：<g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.startTime}"></g:formatDate>
                        </div>
                    </div>

                </div>
            </div>
        </g:if>
    </g:each>

    <g:each in="${this.opportunityFlows}">
        <g:if test="${it?.comments && it?.stage?.description != "区域主单"}">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.stage?.description}
                    </div>

                    <div class="panel-body p-xs">

                        <h5>意见</h5>

                        %{--<g:if test="${it?.stage?.description == "区域主单"}">
                            <g:each in="${this.opportunityFlexFieldCategorys}" var="opportunityFlexField">
                                <g:if test="${opportunityFlexField?.flexFieldCategory?.name == '风险结论'}">
                                    <g:each in="${opportunityFlexField?.fields}" var="field">
                                        <p class="comment-area">${field?.name}:${field?.value}</p>
                                    </g:each>
                                </g:if>
                            </g:each>
                        </g:if>--}%

                        <p class="comment-area">
                            ${it?.comments}
                        </p>

                        <div class="pull-right m-t-sm">
                            <span class="m-r-sm">审批人：
                            <g:each in="${this.opportunityRoles}" var="role">
                                <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval'}">
                                    ${role?.user}
                                </g:if>
                            </g:each>
                            </span>
                            日期：<g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.startTime}"></g:formatDate>
                        </div>
                    </div>

                </div>
            </div>
        </g:if>
    </g:each>
    <div class="row">
        <div class="hpanel hyellow">
            <g:each in="${this.opportunityFlows}" var="it">

                <g:if test="${it?.stage == this.opportunity?.stage && it?.stage?.description != "区域主单"}">

                    <g:each in="${this.opportunityRoles}" var="role">

                        <g:if test="${(it?.stage == role?.stage) && (role?.teamRole?.name == 'Approval' || role?.teamRole?.name == 'Edit') && (role?.user?.username == sec.loggedInUserInfo(field: 'username').toString())}">

                            <g:form controller="opportunityFlow" method="PUT" resource="${it}" class="form-horizontal">
                                <div class="panel-heading" style="font-weight: 600">
                                    <div class="panel-tools">
                                        <g:submitButton class="btn btn-info btn-xs" name="update" value="保存"/>
                                    </div>
                                    审批意见编辑
                                </div>

                                <div class="panel-body">
                                    <g:textArea name="comments" value="${it?.comments}" class="form-control" rows="4"/>
                                </div>
                            </g:form>
                        </g:if>

                    </g:each>
                </g:if>
            </g:each>
        </div>
    </div>
</div>

<footer class="footer bg-success">
    <div class="row">
        <div class="pull-left m-b-xs">
            <g:if test="${this.subUsers?.size() > 0}">
                <g:form controller="opportunity" action="transferRole">
                    <input class="form-control" name="opportunity" value="${this.opportunity?.id}" type="hidden">
                    <g:select name="user" id="user" from="${this.subUsers}" optionKey="id"
                              />
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
                        <select name="nextOperators" class="selectRole" >
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
                    <select name="nextOperators" class="selectRole pull-left" >
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
            <button class="btn btn-sm btn-danger" data-toggle="modal" data-target="#myModal">
                <i class="fa fa-exclamation-circle"></i> 失败</button>
        </div>
    </div>
</footer>
</body>
</html>

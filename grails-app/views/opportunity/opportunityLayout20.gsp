<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>放款审批页3.0-20</title>
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
            <span style="font-weight: normal" class="text-info">
                【${this.opportunity?.stage?.description}】
                【保护期： <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                ${this.opportunity?.protectionEndTime - this.opportunity?.protectionStartTime}天
                </g:if>】
                <g:if test="${this.opportunity?.status == 'Failed'}">【订单结果：<span
                        class="text-danger">失败</span>】</span></g:if>
                <g:elseif test="${this.opportunity?.status == 'Completed'}">【订单结果：成功】</g:elseif>
                <g:else>【订单结果：进行中】</g:else>
            </span>
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
                    %{--<button class="btn btn-info btn-xs" data-toggle="modal" data-target="#actualAmountOfCredit"><i--}%
                            %{--class="fa fa-edit"></i>编辑</button>--}%
                    <g:link id="${this.opportunity?.id}" action="opportunityLayout21"
                            class="btn btn-info btn-xs"><i class="fa fa-external-link"></i>查看详情
                    </g:link>
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
                            <th>业务来源</th>
                            <th>业务类型</th>
                            <th>签呈类型</th>
                            <th>客户类型</th>
                            <th>产品类型</th>
                            <th>共同借款人</th>
                            <th>收款人</th>
                            <th>收款人身份证号</th>
                            <th>收款账号</th>
                            <th>审批可贷金额（万元）</th>
                            <th>实际贷款期限（月）</th>
                            <th>业务区域及出借人</th>
                            <th>放款账号</th>
                            %{--<th>实际月息（%）</th>--}%
                            <th>
                                房产总价（万元）
                            </th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.contact?.account?.name}</td>
                            <td>${this.opportunity?.type?.name}</td>
                            <td>
                                <g:if test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('普通签呈'))}">普通签呈</g:if>
                                <g:elseif
                                        test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('特批签呈'))}">特批签呈</g:elseif>
                            </td>
                            <td>${this.opportunity?.lender?.level?.description}</td>
                            <td>${this.opportunity?.product?.name}</td>
                            <td>
                                <g:each in="${this.opportunityContacts}"><g:if
                                        test="${!(it?.type?.name == '曾用名')}">${it?.contact?.fullName}、</g:if></g:each>
                            </td>
                            <td>
                                <g:each in="${this.bankAccounts}">
                                    <g:if test="${it?.type?.name == "收款账号"}">
                                        ${it?.bankAccount?.name}
                                    </g:if>
                                </g:each>
                            </td>
                            <td>
                                <g:each in="${this.bankAccounts}">
                                    <g:if test="${it?.type?.name == "收款账号"}">
                                        ${it?.bankAccount?.numberOfCertificate}
                                    </g:if>
                                </g:each>
                            </td>
                            <td>
                                <g:each in="${this.bankAccounts}">
                                    <g:if test="${it?.type?.name == '收款账号'}">
                                        ${it?.bankAccount?.numberOfAccount}
                                    </g:if>
                                </g:each>
                            </td>
                            <td>
                                <g:formatNumber number="${this.opportunity?.actualAmountOfCredit}" minFractionDigits="2"
                                                maxFractionDigits="2"/>
                            </td>
                            <td>
                                ${this.opportunity?.actualLoanDuration}
                            </td>
                            <td>
                                ${this.opportunity?.collaterals[0]?.city?.name}
                                <g:each in="${this.opportunityFlexFieldCategorys}" var="opportunityFlexField">

                                    <g:if test="${opportunityFlexField?.flexFieldCategory?.name == '资金渠道'}">
                                        <g:each in="${opportunityFlexField?.fields}" var="field">
                                            <g:if test="${field?.name == '放款通道'}">
                                                ${field?.value}
                                            </g:if>
                                        </g:each>
                                    </g:if>
                                </g:each>
                            </td>
                            <td>
                                <g:each in="${this.opportunityFlexFieldCategorys}">
                                    <g:if test="${it?.flexFieldCategory?.name == '资金渠道'}">
                                        <g:each in="${it?.fields}" var="field">
                                            <g:if test="${field?.name == '放款账号'}">
                                                <p class="text-comment">${field?.value}</p>
                                            </g:if>
                                        </g:each>
                                    </g:if>
                                </g:each>
                            </td>
                            %{--<td><g:formatNumber number="${this.opportunity?.monthlyInterest}" maxFractionDigits="9"/></td>--}%
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
    </div>

    <div class="row">
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                抵押物信息
            </div>

            <div class="panel-body collateral no-padding">

                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <th>房产序号</th>
                            <g:sortableColumn property="address" title="抵押物地址"></g:sortableColumn>
                            <g:sortableColumn property="propertySerialNumber"
                                              title="抵押物房产证号"></g:sortableColumn>
                            <g:sortableColumn property="totalPrice" title="房产总价(万元)"></g:sortableColumn>
                            <g:sortableColumn property="loanToValue" title="抵押率（%）"></g:sortableColumn>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.collaterals}">
                            <tr>
                                <td>第<span class="collateralOrder"></span>套</td>
                                <td>
                                    ${it?.address}
                                </td>
                                <td>${it?.propertySerialNumber}</td>

                                <td>
                                <g:formatNumber number="${it?.totalPrice}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>
                                </td>
                                <td>
                                    <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                                    minFractionDigits="2"/>

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
        <div class="hpanel hred">
            <div class="panel-heading">
                返费信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-center">
                        <thead>
                        <tr>
                            <th>付息方式</th>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <th>上扣息月份数（月）</th>
                            </g:if>
                            <th>渠道返费（万元）</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.interestPaymentMethod?.name}</td>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <td>${this.opportunity?.monthOfAdvancePaymentOfInterest}</td>
                            </g:if>
                            <td>
                                <g:each in="${opportunityProduct}">
                                    <g:if test="${it?.productInterestType?.name == '渠道返费费率'}">
                                        <g:if test="${!it?.installments}">一次性;<g:formatNumber
                                                number="${it.rate * this.opportunity.actualAmountOfCredit / 100}"
                                                minFractionDigits="2"
                                                maxFractionDigits="9"/></g:if>
                                        <g:else>月月返;<g:formatNumber
                                                number="${it.rate * this.opportunity.actualAmountOfCredit * this.opportunity.actualLoanDuration / 100}"
                                                minFractionDigits="2"
                                                maxFractionDigits="9"/></g:else>
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
    <g:render template="/layouts/opportunityTemplate/opportunityProductTemplate"/>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link target=" _blank" class="btn btn-info btn-xs" controller="opportunityFlexField"
                            id="${this.opportunity?.id}"
                            action="opportunityFlexField01"><i class="fa"></i>外访报告</g:link>
                    <g:if test="${this.canAttachmentsShow}">
                        <g:link target=" _blank" class="btn btn-info btn-xs" controller="attachments"
                                action="show" id="${this.opportunity.id}"
                                params="[attachmentTypeName: '产调结果']">产调结果</g:link>
                        <g:link target=" _blank" controller="attachments"
                                action="show"
                                id="${this.opportunity?.id}"
                                params="[attachmentTypeName: '抵押合同全委']" class="btn btn-info btn-xs">抵押、合同、全委</g:link>
                        <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                                params="[attachmentTypeName: '其他放款要求资料']"
                                class="btn btn-info btn-xs">其他放款要求资料</g:link>

                    </g:if>
                </div>
                放款落实条件
            </div>

            <div class="panel-body p-xs">
                <div class="comment-area">
                    <g:each in="${this.opportunityFlows}">
                        <g:each in="${this.opportunityRoles}" var="role">
                            <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval' && it?.stage?.name == "贷款审批已完成（CRO）"}">
                                <g:formatDate format="yyyy-MM-dd" date="${it?.endTime}"/></span>
                            </g:if>
                        </g:each>
                    </g:each> 经审批委员会全数通过，同意给予共同借款人：
                    <span>
                        <g:each in="${this.opportunityContacts}">
                            <g:if test="${it?.type?.name == '借款人'}"></g:if>
                            <g:if test="${!(it?.type?.name == '曾用名')}">${it?.contact?.fullName}</g:if>
                        </g:each>
                    </span>贷款。<br/>
                    贷款金额：<g:formatNumber number="${this.opportunity?.actualAmountOfCredit}" minFractionDigits="2"
                                         maxFractionDigits="2"/>万元<br/>
                    贷款期限：${this.opportunity?.actualLoanDuration}月<br/>
                    放款前要求：
                    <g:each in="${this.opportunityFlexFieldCategorys}">
                        <g:if test="${it?.flexFieldCategory?.name == '放款前要求'}">
                            <g:each in="${it?.fields}" var="field">
                                ${field?.value}
                            </g:each>
                        </g:if>
                    </g:each>
                    <br/>
                    <g:each in="${this.opportunityFlows}">
                        <g:each in="${this.opportunityRoles}" var="role">
                            <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval' && it?.stage?.name == "抵押已完成"}">
                                <g:formatDate format="yyyy-MM-dd" date="${it?.endTime}"/></span>
                            </g:if>
                        </g:each>
                    </g:each>
                    共同借款人提供放款前材料，具备放款条件。
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                权利入库单情况
            </div>
            <g:if test="${notarizationsList.size()>0}">
                <div class="panel-body p-xs">

                    <dl class="dl-horizontal">
                        <dt>见收件收据放款：</dt>
                        <g:each in="${notarizationsList}">
                            <dd>${it}</dd>
                        </g:each>


                    </dl>

                </div>
            </g:if>
            <g:else>
                <div class="panel-body p-xs">
                    <div class="comment-area">
                        <g:if test="${this.opportunity?.mortgageCertificateType?.name == '他项证明'}">
                            <dl class="dl-horizontal">
                                <dt>见他项放款：</dt>
                                <dd>他项权利证已落实并收押</dd>
                                <dd>强制执行、售房公证已落实收押</dd>
                                <dd>房产证件已收押</dd>
                            </dl>
                        </g:if>
                        <g:else>
                            <dl class="dl-horizontal">
                                <dt>见收件收据放款：</dt>
                                <dd>强制执行公证已落实</dd>
                                <dd>售房公证已落实</dd>
                                <dd>抵押登记已落实，受理单已收押</dd>
                            </dl>
                        </g:else>

                    </div>

                    <div class="pull-right m-t-sm">
                        <g:each in="${this.opportunityFlows}">
                            <g:each in="${this.opportunityRoles}" var="role">
                                <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval' && it?.stage?.name == "放款路径已选择"}">

                                    <span>审批人：${role?.user}</span>

                                    <span>日期：<g:formatDate format="yyyy-MM-dd HH:mm:ss"
                                                           date="${it?.endTime}"/></span>
                                </g:if>
                            </g:each>
                        </g:each>
                    </div>
                </div>
            </g:else>

        </div>
    </div>

    <div class="row">

        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                审批意见
            </div>

            <div class="panel-body p-xs">
                <div class="col-md-6">
                    <div class="hpanel">
                        <div class="v-timeline vertical-container animate-panel">
                            <g:each in="${this.opportunityFlows}">

                                <g:if test="${it?.executionSequence >= opportunityLoanFlow?.executionSequence && it?.executionSequence <= currentFlow?.executionSequence && it?.comments}">
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

                <div class="col-md-6">
                    <g:each in="${this.opportunityFlows}" var="it">

                        <g:if test="${it?.stage == this.opportunity?.stage}">

                            <g:each in="${this.opportunityRoles}" var="role">

                                <g:if test="${(it?.stage == role?.stage) && (role?.teamRole?.name == 'Approval' || role?.teamRole?.name == 'Edit') && (role?.user?.username == sec.loggedInUserInfo(field: 'username').toString())}">
                                    <g:form controller="opportunityFlow" method="PUT" resource="${it}">

                                        <div class="hpanel">
                                            <div class="panel-heading">
                                                <div class="panel-tools">
                                                    <button class="btn btn-xs btn-info pull-right">保存</button>
                                                </div>
                                                您的审批意见
                                            </div>
                                            <g:textArea name="comments" class="form-control"
                                                        rows="4"/>
                                        </div>
                                    </g:form>
                                </g:if>
                            </g:each>
                        </g:if>
                    </g:each>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="footer bg-success">
    <g:render template="/layouts/opportunityTemplate/footerLeftTemplate"/>
   %{-- <div class="pull-left m-b-xs" style="margin-left:5px">
        <g:form controller="opportunity" action="changeOpportunityStage">
            <input class="form-control" name="opportunity" value="${this.opportunity?.id}" type="hidden">
            <g:select name="opportunityStage" from="${com.next.OpportunityStage.list()}" optionKey="id"
                      optionValue="name" value="${this.opportunity?.stage?.id}"/>
            <button class="btn btn-sm btn-danger m-l-sm" type="submit">订单阶段跳转</button>
        </g:form>
    </div>--}%
    <g:render template="/layouts/opportunityTemplate/footerRightTemplate"/>
</footer>

<div class="modal fade" tabindex="-1" id="actualAmountOfCredit" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <g:form class="form-horizontal" resource="${this.opportunity}" method="PUT">
                <div class="color-line"></div>

                <div class="modal-header">
                    <h4 class="modal-title">审核可贷金额</h4>
                </div>

                <div class="modal-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">审核可贷金额</label>

                        <div class="col-md-7 input-group">
                            <g:if test="${this.opportunity?.actualAmountOfCredit}">
                                <g:textField class="form-control" name="actualAmountOfCredit"
                                             required="required" id="actualAmountOfCredit"
                                             value="${this.opportunity?.actualAmountOfCredit}"/>
                            </g:if>
                            <g:else>
                                <g:textField class="form-control" name="actualAmountOfCredit"
                                             value="${this.opportunity?.maximumAmountOfCredit}"/>
                            </g:else>
                            <span class="input-group-addon">万元</span>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">提交</button>
                </div>
            </g:form>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("body").addClass("fixed-small-header");
    })
</script>
</body>

</html>

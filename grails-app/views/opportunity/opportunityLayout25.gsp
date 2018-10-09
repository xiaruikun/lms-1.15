<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>贷款审批页3.0（北京-上海）</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div class="pull-right">
                <li class="sameCollaterals" style="float: left;"><span class="btn btn-xs btn-info collateralNum"></span></li>
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
                订单号: ${this.opportunity?.serialNumber}
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
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link id="${this.opportunity?.id}" action="opportunityLayout27"
                            class="btn btn-info btn-xs"><i class="fa fa-external-link"></i>查看详情
                    </g:link>

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
                            <th>业务区域</th>
                            <th>业务来源</th>
                            <th>签呈类型</th>
                            <th>抵押类型</th>
                            <th>客户类型</th>
                            <th>产品类型</th>
                            %{--<th>委托途径</th>--}%
                            <th>共同借款人</th>
                            <th>借款金额（万元）</th>
                            <th>实际贷款期限（月）</th>
                            %{--<th>实际月息（%）</th>--}%
                            <th>付息方式</th>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <th>上扣息月份数（月）</th>
                            </g:if>
                            <th>
                                房产总价（万元）
                            </th>

                            <th>渠道返费（万元）</th>
                            <th>放款通道</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.collaterals[0]?.city?.name}</td>
                            <td>${this.opportunity?.contact?.account?.name}</td>
                            <td>
                                <g:if test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('普通签呈'))}">普通签呈</g:if>
                                <g:elseif
                                        test="${com.next.Attachments.findByOpportunityAndType(this.opportunity, com.next.AttachmentType.findByName('特批签呈'))}">特批签呈</g:elseif>
                            </td>
                            <td>
                                <g:each in="${this.collaterals}">
                                    ${it?.mortgageType?.name}、
                                </g:each>
                            </td>
                            <td>${this.opportunity?.lender?.level?.description}</td>
                            <td>${this.opportunity?.product?.name}</td>
                            <td>
                                <g:each in="${this.opportunityContacts}"><g:if
                                        test="${!(it?.type?.name == '曾用名')}">${it?.contact?.fullName}、</g:if></g:each>
                            </td>
                            <td>
                                <g:formatNumber number="${this.opportunity?.actualAmountOfCredit}" minFractionDigits="2"
                                                maxFractionDigits="2"/>
                            </td>
                            <td>
                                ${this.opportunity?.actualLoanDuration}
                            </td>
                            %{--<td><g:formatNumber number="${this.opportunity?.monthlyInterest}" maxFractionDigits="9"/></td>--}%
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
                            <td>
                                ${ com.next.OpportunityFlexField.findByNameAndCategory("放款通道",com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(this.opportunity,com.next.FlexFieldCategory.findByName("资金渠道")))?.value==null ? "": com.next.OpportunityFlexField.findByNameAndCategory("放款通道",com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(this.opportunity,com.next.FlexFieldCategory.findByName("资金渠道")))?.value}
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
                            建成时间：
                            <g:if test="${it?.buildTime}">
                                <g:formatDate format="yyyy" date="${it?.buildTime}"/>年;
                            </g:if><br/>


                            物业地址：${it?.address}，小区名称：${it?.projectName}；<br/>
                            <g:if test="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人'))}">
                                <g:each in="${this.opportunityContacts}" var="opportunityContact">
                                    <g:if test="${opportunityContact?.type?.name == '抵押人'}">
                                        抵押人：${opportunityContact?.contact?.fullName}，电话：<span>${opportunityContact?.contact?.cellphone}</span>；
                                    </g:if></g:each>
                            </g:if>
                            <g:else>
                                抵押人：${this.opportunity?.fullName}，电话：<span>${this.opportunity?.cellphone}</span>；
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

    <div class="row commentsRow">

        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                主单结论
            </div>

            <div class="panel-body p-xs comment-area">
                优势：${this.opportunity?.advantages}<br>

                劣势：${this.opportunity?.disadvantages}<br>
                <g:if test="${this.opportunity?.additionalComment}">
                    补充说明：${this.opportunity?.additionalComment}<br>
                </g:if>

                <h5>授信建议</h5>

                授信金额：${this.opportunity?.actualAmountOfCredit}万元<br>

                抵押率：
                <g:each in="${this.collaterals}">
                    <span class="m-r font-bold">
                        <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                        minFractionDigits="2"/>%
                    </span>
                </g:each> <br>
                实际贷款期限：${this.opportunity?.actualLoanDuration}月<br>
                签约主体：
                <g:each in="${this.opportunityContacts}">
                    <span class="m-r">
                        ${it?.contact?.fullName}
                    </span>
                </g:each><br>
                月利息：${this.opportunity?.monthlyInterest}%，月服务费率：${this.opportunity?.serviceCharge}%，是否要求面审：${this.opportunity?.interviewRequired}
                <h5>放款前要求</h5>
                <g:if test="${this.opportunity?.notarizationMatters}">
                    公证事项：${this.opportunity?.notarizationMatters}<br>
                </g:if>
                抵押凭证：
                <g:if test="${this.opportunity?.mortgageCertification == 'true'}">
                    抵押登记受理单 <br>
                </g:if>
                <g:if test="${this.opportunity?.mortgageCertification == 'false'}">
                    他项权利证书 <br>
                </g:if>
                <g:if test="${this.opportunity?.signedDocuments}">
                    签署文件：${this.opportunity?.signedDocuments} <br>
                </g:if>
                <g:if test="${this.opportunity?.mortgageMaterials}">
                    收押材料：${this.opportunity?.mortgageMaterials} <br>
                </g:if>
                <g:if test="${this.opportunity?.otherIssues}">
                    其他：${this.opportunity?.otherIssues}<br>
                </g:if>
                <g:if test="${this.opportunity?.prePaymentAdditionalComment}">
                    补充说明：${this.opportunity?.prePaymentAdditionalComment}
                </g:if>
            </div>
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
                                            <g:textArea name="comments" class="form-control textarea"
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
        searchSameCollaterals();
        function searchSameCollaterals() {
            $.ajax({
                type: "POST",
                data: {
                    opportunityId: $("#opportunityId").val(),
                },
                cache: false,
                url: "/collateral/searchOldOpportunityNumbers",
                dataType: "JSON",
                success: function (data) {
                    if (data.size >0) {
                        $(".sameCollaterals").css('display',"block");
                        $(".collateralNum").html("报单记录（"+data.size+"）")
                    } else {
                        $(".sameCollaterals").css('display',"none");
                    }
                },
                error: function () {
                    swal("获取相同房产证订单失败，请稍后重试", "", "error");
                }
            });
        }
        $(".sameCollaterals").click(function () {
                $.ajax({
                    type: "POST",
                    data: {
                        opportunityId: $("#opportunityId").val(),
                    },
                    cache: false,
                    url: "/collateral/searchOldOpportunityNumbers",
                    dataType: "JSON",
                    success: function (data) {
                        if (data.size >0) {
                            var texts = "";
                            for (i = 0 ;i<=data.size-1;i++){
                                texts+= "编号："+data.list[i].serialNumber+"--------借款人姓名："+data.list[i].fullName+"\n";
                            }
                            swal({
                                title: "LMS存在"+data.size+"笔订单房产证编号与此订单相同",
                                confirmButtonColor: "#1787dd",
                                text:texts,
                                confirmButtonText: "我知道了"
                            });
                        }
                    },
                    error: function () {
                        swal("获取相同房产证订单失败，请稍后重试", "", "error");
                    }
                });
            }
        );
    })
</script>

</body>
</html>

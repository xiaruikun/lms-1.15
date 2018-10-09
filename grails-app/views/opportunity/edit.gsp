<!DOCTYPE html>
<html lang="en">

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>订单编辑</title>
    <style>
    .input-group[class*="col-"] {
        float: left;
        padding-right: 15px;
        padding-left: 15px;
    }

    .panel-heading h4 {
        margin-top: 10px;
        margin-bottom: 0;
    }
    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show" id="${this.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>信息编辑</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light">
                订单号: ${this.opportunity.serialNumber}
            </h2>
            <small>保护期&nbsp;<g:formatDate format="yyyy-MM-dd HH:mm:ss"
                                          date="${this.opportunity?.protectionStartTime}"/> - <g:formatDate
                    format="yyyy-MM-dd HH:mm:ss" date="${this.opportunity?.protectionEndTime}"/></small>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message" role="status">
                ${flash.message}
            </div>
        </g:if>
        <g:hasErrors bean="${this.opportunity}">
            <ul class="message" role="alert">
                <g:eachError bean="${this.opportunity}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                        <g:message error="${error}"/>
                    </li>
                </g:eachError>
            </ul>
        </g:hasErrors>
    </div>
    <g:form resource="${this.opportunity}" method="PUT" name="myForm" class="form-horizontal">
        <g:if test="${this.flag}">
            <g:if test="${this.flag == 'zhushen'}">
                <div class="row">
                    <div class="hpanel hblue">
                        <div class="panel-heading">
                            审核阶段
                        </div>

                        <div class="panel-body form-horizontal">
                            <div class="form-group">
                                <label class="col-md-3 control-label">审核可贷金额</label>

                                <div class="col-md-2 input-group">
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

                                <label class="col-md-3 control-label">月息</label>

                                <div class="col-md-2 input-group">
                                    <g:textField class="form-control" name="monthlyInterest"
                                                 value="${this.opportunity?.monthlyInterest}"/>
                                    <span class="input-group-addon">%</span>
                                </div>

                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">借款服务费</label>

                                <div class="col-md-2 input-group">
                                    <g:textField class="form-control" name="serviceCharge"
                                                 value="${this.opportunity?.serviceCharge}"/>
                                    <span class="input-group-addon">%</span>
                                </div>
                                <label class="col-md-3 control-label">付息方式</label>

                                <div class="col-md-2">
                                    <g:select class="form-control interestPaymentMethod " optionKey="id"
                                              optionValue="name"
                                              name="interestPaymentMethod"
                                              value="${this.opportunity?.interestPaymentMethod?.id}"
                                              from="${com.next.InterestPaymentMethod.list()}"/>
                                </div>

                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">渠道服务费</label>

                                <div class="col-md-2 input-group">
                                    <g:textField class="form-control" name="commissionRate" required="required"
                                                 id="commissionRate"
                                                 value="${this.opportunity?.commissionRate}"/>
                                    <span class="input-group-addon">%</span>
                                </div>
                                <label class="col-md-3 control-label">返佣方式</label>

                                <div class="col-md-2 input-group">
                                    <g:select class="form-control" optionKey="id" optionValue="name"
                                              name="commissionPaymentMethod" required="required"
                                              id="commissionPaymentMethod"
                                              value="${this.opportunity?.commissionPaymentMethod?.id}"
                                              from="${com.next.CommissionPaymentMethod.list()}"/>
                                </div>

                            </div>


                            <div class="form-group">
                                <!-- <label class="col-md-3 control-label">本金支付方式</label>

                            <div class="col-md-2 input-group">
                                <g:select class="form-control" name="principalPaymentMethod"
                                          value="${this.opportunity?.principalPaymentMethod?.id}"
                                          noSelection="${['null': '无']}"
                                          from="${com.next.PrincipalPaymentMethod.list()}" optionKey="id"
                                          optionValue="name"></g:select>
                            </div> -->
                                <label class="col-md-3 control-label">渠道返费</label>

                                <div class="col-md-2 input-group">
                                    <g:textField class="form-control" name="commission" required="required"
                                                 id="commission"
                                                 value="${this.opportunity?.commission}"/>
                                    <span class="input-group-addon">万元</span>
                                </div>


                                <label class="col-md-3 control-label">客户级别</label>

                                <div class="col-md-3 checkbox-inline">
                                    <g:radioGroup class="checkbox-inline" name="level"
                                                  value="${this.opportunity?.lender?.level?.name}"
                                                  labels="['优质', '次优', '疑难', '不良', '待评级']"
                                                  values="['A', 'B', 'C', 'D', '待评级']">
                                        ${it.radio} ${it.label}
                                    </g:radioGroup>
                                </div>

                            </div>

                            <div class="form-group">

                                <label class="col-md-3 control-label">借款合同单号</label>

                                <div class="col-md-2">
                                    <g:textField class="form-control" name="externalId"
                                                 value="${this.opportunity?.externalId}"/>
                                </div>
                                <label class="col-md-3 control-label">意向金</label>

                                <div class="col-md-2 input-group">
                                    <g:textField class="form-control" name="advancePayment"
                                                 value="${this.opportunity?.advancePayment}"/>
                                    <span class="input-group-addon">元</span>
                                </div>

                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">抵押凭证类型</label>

                                <div class="col-md-2 input-group">
                                    <g:select class="form-control" name="mortgageCertificateType"
                                              value="${this.opportunity?.mortgageCertificateType?.id}"
                                              noSelection="${['null': '无']}"
                                              from="${com.next.MortgageCertificateType.list()}" optionKey="id"
                                              optionValue="name"></g:select>
                                </div>

                                <div class="infoPaymentOfInterest hide">
                                    <label class="col-md-3 control-label">上扣息月份数：</label>

                                    <div class="col-md-2 input-group">
                                        <g:textField class="form-control" name="monthOfAdvancePaymentOfInterest"
                                                     value="${this.opportunity?.monthOfAdvancePaymentOfInterest}"/>
                                        <span class="input-group-addon">月</span>

                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="hpanel hyellow">
                        <div class="panel-heading">
                            初审阶段
                        </div>

                        <div class="panel-body form-horizontal">
                            <div class="form-group">
                                %{-- <label class="col-md-3 control-label">初审可贷金额</label>

                                 <div class="col-md-2 input-group">
                                     <g:textField class="form-control" name="maximumAmountOfCredit"
                                                  value="${this.opportunity?.maximumAmountOfCredit}"/>
                                     <span class="input-group-addon">万元</span>
                                 </div>--}%
                                <label class="col-md-3 control-label">拟贷款期限</label>

                                <div class="col-md-2 input-group">
                                    <g:textField class="form-control" name="loanDuration"
                                                 value="${this.opportunity?.loanDuration}"/>
                                    <span class="input-group-addon">月</span>
                                </div>
                                <label class="col-md-3 control-label">产品类型</label>

                                <div class="col-md-2">
                                    <g:select class="form-control" optionKey="id" optionValue="name"
                                              name="product"
                                              value="${this.opportunity?.product?.id}"
                                              from="${com.next.Product.findAllByActive(true)}"/>
                                </div>
                            </div>

                            <div class="form-group">

                                <label class="col-md-3 control-label">实际贷款期限</label>

                                <div class="col-md-2 input-group">
                                    <g:textField class="form-control" name="actualLoanDuration" required="required"
                                                 id="actualLoanDuration"
                                                 value="${this.opportunity?.actualLoanDuration}"/>
                                    <span class="input-group-addon">月</span>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="hpanel horange">
                        <div class="panel-heading">
                            报单信息
                        </div>

                        <div class="panel-body form-horizontal">
                            <div class="form-group">
                                <label class="col-md-3 control-label">贷款金额</label>

                                <div class="col-md-2 input-group">
                                    <g:textField class="form-control" name="requestedAmount"
                                                 value="${this.opportunity?.requestedAmount}"/>
                                    <span class="input-group-addon">万元</span>
                                </div>
                                <label class="col-md-3 control-label">成交利率</label>

                                <div class="col-md-2 input-group">
                                    <g:textField class="form-control" name="dealRate"
                                                 value="${this.opportunity?.dealRate}"/>
                                    <span class="input-group-addon">%</span>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </g:if>
            <g:if test="${this.flag == 'shibai'}">
                <div class="row">
                    <div class="hpanel horange">
                        <div class="panel-heading">
                            失败原因
                        </div>

                        <div class="panel-body form-horizontal">
                            <div class="form-group m-b-none">
                                <label class="col-md-3 control-label">未成交归类</label>

                                <div class="col-md-2">
                                    <g:select class="form-control" name="causeOfFailure"
                                              value="${this.opportunity?.causeOfFailure?.id}"
                                              noSelection="${['null': '无']}"
                                              from="${com.next.CauseOfFailure.list()}" optionKey="id"
                                              optionValue="name"></g:select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </g:if>
        </g:if>

        <g:else>
            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        订单详情
                    </div>

                    <div class="panel-body form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">订单状态：</label>

                            <div class="col-md-2">
                                <span class="cont">${this.opportunity?.stage?.name}</span>
                            </div>
                            <label class="col-md-3 control-label">状态描述：</label>

                            <div class="col-md-2">
                                <span class="cont">
                                    <g:if test="${this.opportunity?.status == 'Pending'}">进行中</g:if>
                                    <g:if test="${this.opportunity?.status == 'Failed'}">失败</g:if>
                                    <g:if test="${this.opportunity?.status == 'Completed'}">成功</g:if>
                                </span>
                            </div>
                        </div>
                        %{--<div class="form-group m-b-none">
                            <label class="col-md-3 control-label">主管：</label>

                            <div class="col-md-3">
                                <g:select class="form-control" name="reportTo" required="required" id="reportTo" optionValue="" optionKey="id"
                                          from="${com.next.User.list()}" value ="${this.opportunity?.user?.reportTo?.id}"
                                          noSelection="${['null': '--请选择--']}"></g:select>

                            </div>
                        </div>--}%
                    </div>

                </div>
            </div>

            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        评房阶段
                    </div>

                    <div class="panel-body form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">房产评估单价：</label>

                            <div class="col-md-2">
                                <span class="cont">${this.opportunity?.unitPrice}元</span>
                            </div>
                            <label class="col-md-3 control-label">房产评估总价：</label>

                            <div class="col-md-2">
                                <span class="cont">${this.opportunity?.loanAmount}万元</span>
                            </div>
                        </div>

                    </div>

                </div>
            </div>

            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        初审阶段
                    </div>

                    <div class="panel-body form-horizontal">
                        <div class="form-group">
                            %{-- <label class="col-md-3 control-label">初审可贷金额</label>

                             <div class="col-md-2 input-group">
                                 <g:textField class="form-control" name="maximumAmountOfCredit"
                                              value="${this.opportunity?.maximumAmountOfCredit}"/>
                                 <span class="input-group-addon">万元</span>
                             </div>--}%
                            <label class="col-md-3 control-label">拟贷款期限</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="loanDuration"
                                             value="${this.opportunity?.loanDuration}"/>
                                <span class="input-group-addon">月</span>
                            </div>
                            <label class="col-md-3 control-label">产品类型</label>

                            <div class="col-md-2">
                                <g:select class="form-control" optionKey="id" optionValue="name"
                                          name="product"
                                          value="${this.opportunity?.product?.id}"
                                          from="${com.next.Product.findAllByActive(true)}"/>
                            </div>
                        </div>

                        <div class="form-group">

                            <label class="col-md-3 control-label">实际贷款期限</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="actualLoanDuration"
                                             value="${this.opportunity?.actualLoanDuration}"/>
                                <span class="input-group-addon">月</span>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        审核阶段
                    </div>

                    <div class="panel-body form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">审核可贷金额</label>

                            <div class="col-md-2 input-group">
                                <g:if test="${this.opportunity?.actualAmountOfCredit}">
                                    <g:textField class="form-control" name="actualAmountOfCredit"
                                                 value="${this.opportunity?.actualAmountOfCredit}"/>
                                </g:if>
                                <g:else>
                                    <g:textField class="form-control" name="actualAmountOfCredit"
                                                 value="${this.opportunity?.maximumAmountOfCredit}"/>
                                </g:else>
                                <span class="input-group-addon">万元</span>
                            </div>

                            <label class="col-md-3 control-label">月息</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="monthlyInterest"
                                             value="${this.opportunity?.monthlyInterest}"/>
                                <span class="input-group-addon">%</span>
                            </div>

                        </div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">借款服务费</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="serviceCharge"
                                             value="${this.opportunity?.serviceCharge}"/>
                                <span class="input-group-addon">%</span>
                            </div>

                            <label class="col-md-3 control-label">渠道服务费</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="commissionRate"
                                             value="${this.opportunity?.commissionRate}"/>
                                <span class="input-group-addon">%</span>
                            </div>

                        </div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">付息方式</label>

                            <div class="col-md-2">
                                <g:select class="form-control interestPaymentMethod" optionKey="id" optionValue="name"
                                          name="interestPaymentMethod"
                                          value="${this.opportunity?.interestPaymentMethod?.id}"
                                          from="${com.next.InterestPaymentMethod.list()}"/>
                            </div>
                            <label class="col-md-3 control-label">返佣方式</label>

                            <div class="col-md-2 input-group">
                                <g:select class="form-control" optionKey="id" optionValue="name"
                                          name="commissionPaymentMethod"
                                          value="${this.opportunity?.commissionPaymentMethod?.id}"
                                          from="${com.next.CommissionPaymentMethod.list()}"/>
                            </div>

                        </div>


                        <div class="form-group">
                            <label class="col-md-3 control-label">渠道返费</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="commission"
                                             value="${this.opportunity?.commission}"/>
                                <span class="input-group-addon">万元</span>
                            </div>
                            <label class="col-md-3 control-label">客户级别</label>

                            <div class="col-md-3 checkbox-inline">
                                <g:radioGroup class="checkbox-inline" name="level"
                                              labels="['优质', '次优', '疑难', '不良', '待评级']"

                                              value="${this.opportunity?.lender?.level?.name}"
                                              values="['A', 'B', 'C', 'D', '待评级']">
                                    ${it.radio} ${it.label}
                                </g:radioGroup>
                            </div>

                        </div>

                        <div class="form-group ">

                            <label class="col-md-3 control-label">借款合同单号</label>

                            <div class="col-md-2">
                                <g:textField class="form-control" name="externalId"
                                             value="${this.opportunity?.externalId}"/>
                            </div>
                            <label class="col-md-3 control-label">意向金</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="advancePayment"
                                             value="${this.opportunity?.advancePayment}"/>
                                <span class="input-group-addon">元</span>
                            </div>

                        </div>

                        <div class="infoPaymentOfInterest hide form-group m-b-none">
                            <label class="col-md-3 control-label">上扣息月份数：</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="monthOfAdvancePaymentOfInterest"
                                             value="${this.opportunity?.monthOfAdvancePaymentOfInterest}"/>
                                <span class="input-group-addon">月</span>

                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <div class="row">
                <div class="hpanel horange">
                    <div class="panel-heading">
                        失败原因
                    </div>

                    <div class="panel-body form-horizontal">
                        <div class="form-group m-b-none">
                            <label class="col-md-3 control-label">未成交归类</label>

                            <div class="col-md-2">
                                <g:select class="form-control" name="causeOfFailure"
                                          value="${this.opportunity?.causeOfFailure?.id}"
                                          noSelection="${['null': '无']}"
                                          from="${com.next.CauseOfFailure.list()}" optionKey="id"
                                          optionValue="name"></g:select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="hpanel horange">
                    <div class="panel-heading">
                        是否已抵押、公证
                    </div>

                    <div class="panel-body form-horizontal">
                        <div class="form-group m-b-none">
                            <label class="col-md-3 control-label">已抵押</label>

                            <div class="col-md-2 checkbox">
                                <g:checkBox name="mortgagingStatus" value="${this.opportunity?.mortgagingStatus}"/>
                            </div>
                            <label class="col-md-3 control-label">已公证</label>

                            <div class="col-md-2 checkbox">
                                <g:checkBox name="notarizingStatus" value="${this.opportunity?.notarizingStatus}"/>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="hpanel horange">
                    <div class="panel-heading">
                        报单信息
                    </div>

                    <div class="panel-body form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">贷款金额</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="requestedAmount"
                                             value="${this.opportunity?.requestedAmount}"/>
                                <span class="input-group-addon">万元</span>
                            </div>
                            <label class="col-md-3 control-label">成交利率</label>

                            <div class="col-md-2 input-group">
                                <g:textField class="form-control" name="dealRate"
                                             value="${this.opportunity?.dealRate}"/>
                                <span class="input-group-addon">%</span>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <div class="row">
                <div class="hpanel horange">
                    <div class="panel-heading">
                        实际放款日期
                    </div>

                    <div class="panel-body form-horizontal">
                        <div class="form-group m-b-none">
                            <label class="col-md-3 control-label">实际放款日期</label>

                            <div class="col-md-8">
                                <g:datePicker precision="day" default="none"  noSelection="['':'-请选择-']" name="actualLendingDate" years="[2016,2017,2018,2019,2020]" value="${this.opportunity?.actualLendingDate}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </g:else>
        <div class="form-group">
            <div class="col-md-2 col-md-offset-5">
                <g:submitButton class="btn btn-info col-md-3" name="update" value="保存"/>
            </div>
        </div>
    </g:form>
</div>
<g:javascript>
    $(function () {
        if ($(".interestPaymentMethod option:selected").text() == "上扣息") {
            $(".infoPaymentOfInterest").removeClass("hide");
        } else {
            $(".infoPaymentOfInterest").addClass("hide");
            $(".infoPaymentOfInterest").find("input").val("0.0");
        }
        $(".interestPaymentMethod").change(function () {
            if ($(".interestPaymentMethod option:selected").text() == "上扣息") {
                $(".infoPaymentOfInterest").removeClass("hide");
            } else {
                $(".infoPaymentOfInterest").addClass("hide");
                $(".infoPaymentOfInterest").find("input").val("0.0");
            }
        });


        $("#commissionPaymentMethod").change(function () {
            $.ajax({
                type: "POST",
                data: {commissionPaymentMethod: $("#commissionPaymentMethod").val(),
                    actualAmountOfCredit: $("#actualAmountOfCredit").val(), commissionRate: $("#commissionRate").val(),
                    actualLoanDuration: $("#actualLoanDuration").val()},
                cache: false,
                url: "/opportunity/calculateCommission",
                //url: "http://s53.zhongjiaxin.com/territoryFlexFieldCategory/selectFlexFieldCategory",
                dataType: "JSON",
                success: function (datas) {
                    var data = datas["commission"];
                    var com = $("#commission");
                    $("#commission").val(data);
                },
            });
            var myTime = setTimeout("function()", 1000);
        });
    });
</g:javascript>
</body>
</html>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>administratorEdit</title>
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
    <g:form resource="${this.opportunity}" method="PUT" name="myForm" class="form-horizontal formSubmit">
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

        <div class="row">
            <div class="hpanel hyellow">
                <div class="panel-heading">
                    订单详情
                </div>

                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-2 control-label">订单状态：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunity?.stage?.name}</span>
                        </div>
                        <label class="col-md-2 control-label">状态描述：</label>

                        <div class="col-md-3">
                            <span class="cont">
                                <g:if test="${this.opportunity?.status == 'Pending'}">进行中</g:if>
                                <g:if test="${this.opportunity?.status == 'Failed'}">失败</g:if>
                                <g:if test="${this.opportunity?.status == 'Completed'}">成功</g:if>
                            </span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">支持经理</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="user" id="user" required="required"
                                      optionKey="id"
                                      from="${com.next.User.list()}" value="${this.opportunity?.user?.id}"
                                      noSelection="${['null': '--请选择--']}"></g:select>
                        </div>

                        <label class="col-md-2 control-label">审批类型</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="subtype" id="subtype"
                                      optionValue="name" optionKey="id"
                                      from="${com.next.OpportunitySubtype.list()}"
                                      value="${this.opportunity?.subtype?.id}"></g:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">经纪人</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="contact" id="contact"
                                      optionKey="id" optionValue="fullName"
                                      from="${com.next.Contact.findAllByType("Agent")}"
                                      value="${this.opportunity?.contact?.id}"
                                      noSelection="${['': '---请选择---']}"></g:select>
                        </div>

                        <label class="col-md-2 control-label">是否为测试单</label>

                        <div class="col-md-3">
                            <span class="cont">
                                <g:checkBox class="i-checks" name="isTest" value="${this.opportunity?.isTest}"/>
                            </span>

                        </div>

                    </div>

                    <div class="form-group m-b-none">
                        <label class="col-md-2 control-label">外部ID</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" name="externalId"
                                         value="${this.opportunity?.externalId}"/>
                        </div>

                    </div>

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
                        <label class="col-md-2 control-label">房产评估单价：</label>

                        <div class="col-md-3">
                            <span class="cont">
                                <g:formatNumber number="${this.opportunity?.unitPrice}" type="number"
                                                maxFractionDigits="2"
                                                minFractionDigits="2"/>
                                元</span>
                        </div>
                        <label class="col-md-2 control-label">房产评估总价：</label>

                        <div class="col-md-3">
                            <span class="cont">
                                <g:formatNumber number="${this.opportunity?.loanAmount}" type="number"
                                                maxFractionDigits="2"
                                                minFractionDigits="2"/>万元
                        </div></div>

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
                        %{-- <label class="col-md-2 control-label">初审可贷金额</label>

                         <div class="col-md-3 input-group">
                             <g:textField class="form-control" name="maximumAmountOfCredit"
                                          value="${this.opportunity?.maximumAmountOfCredit}"/>
                             <span class="input-group-addon">万元</span>
                         </div>--}%
                        <label class="col-md-2 control-label">拟贷款期限</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="loanDuration"
                                         value="${this.opportunity?.loanDuration}"/>
                            <span class="input-group-addon">月</span>
                        </div>
                        <label class="col-md-2 control-label">产品类型</label>

                        <div class="col-md-3">
                            <g:select class="form-control" optionKey="id" optionValue="name"
                                      name="product"
                                      value="${this.opportunity?.product?.id}"
                                      from="${com.next.Product.findAllByActive(true)}"/>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-md-2 control-label">实际贷款期限</label>

                        <div class="col-md-3 input-group">
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
                        <label class="col-md-2 control-label">审核可贷金额</label>

                        <div class="col-md-3 input-group">
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

                        <label class="col-md-2 control-label">月息</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="monthlyInterest"
                                         value="${this.opportunity?.monthlyInterest}"/>
                            <span class="input-group-addon">%</span>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">借款服务费</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="serviceCharge"
                                         value="${this.opportunity?.serviceCharge}"/>
                            <span class="input-group-addon">%</span>
                        </div>

                        <label class="col-md-2 control-label">渠道服务费</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="commissionRate"
                                         value="${this.opportunity?.commissionRate}"/>
                            <span class="input-group-addon">%</span>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">付息方式</label>

                        <div class="col-md-3 interestPaymentMethod">
                            <g:select class="form-control" optionKey="id" optionValue="name"
                                      name="interestPaymentMethod"
                                      value="${this.opportunity?.interestPaymentMethod?.id}"
                                      from="${com.next.InterestPaymentMethod.list()}"/>
                        </div>
                        <label class="col-md-2 control-label">返佣方式</label>

                        <div class="col-md-3 input-group">
                            <g:select class="form-control" optionKey="id" optionValue="name"
                                      name="commissionPaymentMethod"
                                      value="${this.opportunity?.commissionPaymentMethod?.id}"
                                      from="${com.next.CommissionPaymentMethod.list()}"/>
                        </div>

                    </div>


                    <div class="form-group">
                        <label class="col-md-2 control-label">渠道返费</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="commission" id="commission"
                                         value="${this.opportunity?.commission}"/>
                            <span class="input-group-addon">万元</span>
                        </div>
                        <label class="col-md-2 control-label">客户级别</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="lender.level.id"
                                      value="${this.opportunity?.lender?.level?.id}"
                                      noSelection="${['null': '-请选择-']}"
                                      from="${com.next.ContactLevel.list()}" optionKey="id"
                                      optionValue="description"></g:select>
                        </div>

                    </div>

                    <div class="form-group">

                        <!-- <label class="col-md-2 control-label">借款合同单号</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" name="externalId"
                                         value="${this.opportunity?.externalId}"/>
                        </div> -->
                        <label class="col-md-2 control-label">意向金</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="advancePayment"
                                         value="${this.opportunity?.advancePayment}"/>
                            <span class="input-group-addon">元</span>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">抵押凭证类型</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="mortgageCertificateType"
                                      value="${this.opportunity?.mortgageCertificateType?.id}"
                                      noSelection="${['null': '无']}"
                                      from="${com.next.MortgageCertificateType.list()}" optionKey="id"
                                      optionValue="name"></g:select>
                        </div>

                        <div class="infoPaymentOfInterest hide form-group m-b-none">
                            <label class="col-md-2 control-label">上扣息月份数：</label>

                            <div class="col-md-3 input-group">
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
            <div class="hpanel horange">
                <div class="panel-heading">
                    失败原因
                </div>

                <div class="panel-body form-horizontal">
                    <div class="form-group m-b-none">
                        <label class="col-md-2 control-label">未成交归类</label>

                        <div class="col-md-3">
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
                    <div class="form-group">
                        <label class="col-md-2 control-label">已抵押</label>

                        <div class="col-md-3 checkbox">
                            <g:checkBox class="i-checks" name="mortgagingStatus"
                                        value="${this.opportunity?.mortgagingStatus}"/>
                        </div>
                        <label class="col-md-2 control-label">已公证</label>

                        <div class="col-md-3 checkbox">
                            <g:checkBox class="i-checks" name="notarizingStatus"
                                        value="${this.opportunity?.notarizingStatus}"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">抵押时间</label>

                        <div class="col-md-4">
                            <g:datePicker precision="day" default="none" noSelection="['': '请选择']"
                                          name="dateOfMortgage" years="[2016, 2017, 2018, 2019, 2020]"
                                          value="${this.opportunity?.dateOfMortgage}"/>
                        </div>

                        <label class="col-md-1 control-label">公证时间</label>

                        <div class="col-md-4">
                            <g:datePicker precision="day" default="none" noSelection="['': '请选择']"
                                          name="dateOfNotarization" years="[2016, 2017, 2018, 2019, 2020]"
                                          value="${this.opportunity?.dateOfNotarization}"/>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="hpanel horange">
                <div class="panel-heading">
                    权利入库单情况
                </div>
                <input type="hidden" name="notarizations" id="notarizations" value="${this.opportunity?.notarizations}">

                <div class="panel-body form-horizontal">
                    <div class="form-group m-b-none">
                        <label class="col-md-2 control-label">强制执行公证</label>

                        <div class="col-md-1 checkbox">
                            <g:checkBox class="i-checks" name="notarization" value="强制执行公证" checked="false"/>
                        </div>
                        <label class="col-md-2 control-label">委托解押公证</label>

                        <div class="col-md-1 checkbox">
                            <g:checkBox class="i-checks" name="notarization" value="委托解押公证" checked="false"/>
                        </div>
                        <label class="col-md-2 control-label">单身声明公证</label>

                        <div class="col-md-1 checkbox">
                            <g:checkBox class="i-checks" name="notarization" value="单身声明公证" checked="false"/>
                        </div>
                        <label class="col-md-2 control-label">抵押登记受理单</label>

                        <div class="col-md-1 checkbox">
                            <g:checkBox class="i-checks" name="notarization" value="抵押登记受理单" checked="false"/>
                        </div>
                        <label class="col-md-2 control-label">放款前 产调</label>

                        <div class="col-md-1 checkbox">
                            <g:checkBox class="i-checks" name="notarization" value="放款前 产调" checked="false"/>
                        </div>
                        <label class="col-md-2 control-label">他项权利证书</label>

                        <div class="col-md-1 checkbox">
                            <g:checkBox class="i-checks" name="notarization" value="他项权利证书" checked="false"/>
                        </div>
                        <label class="col-md-2 control-label">律师见证</label>

                        <div class="col-md-1 checkbox">
                            <g:checkBox class="i-checks" name="notarization" value="律师见证" checked="false"/>
                        </div>
                        <label class="col-md-2 control-label">证件一致公证</label>

                        <div class="col-md-1 checkbox">
                            <g:checkBox class="i-checks" name="notarization" value="证件一致公证" checked="false"/>
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
                    <div class="form-group m-b-none">
                        <label class="col-md-2 control-label">贷款金额</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="requestedAmount"
                                         value="${this.opportunity?.requestedAmount}"/>
                            <span class="input-group-addon">万元</span>
                        </div>
                        <label class="col-md-2 control-label">成交利率</label>

                        <div class="col-md-3 input-group">
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
                        <label class="col-md-2 control-label">实际放款日期</label>

                        <div class="col-md-4">
                            <g:datePicker precision="day" default="none" noSelection="['': '-请选择-']"
                                          name="actualLendingDate" years="[2016, 2017, 2018, 2019, 2020]"
                                          value="${this.opportunity?.actualLendingDate}"/>
                        </div>
                        <label class="col-md-1 control-label">实际放款金额</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="actualLoanAmount"
                                         value="${this.opportunity?.actualLoanAmount}"/>
                            <span class="input-group-addon">万元</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="form-group">
            <div class="col-md-3 col-md-offset-4">
                %{--<g:submitButton class="btn btn-info col-md-3"  name="update" value="保存"/>--}%
                <input type="button" class="btn btn-info col-md-3" name="update" onclick="beforeSubmit()" value="保存"/>
            </div>
        </div>
    </g:form>
</div>
<g:javascript>
    $(function () {
        getCommission();
        function getCommission() {
            $.ajax({
                type: "POST",
                data: {
                    commissionPaymentMethod: $("#commissionPaymentMethod").val(),
                    actualAmountOfCredit: $("#actualAmountOfCredit").val(),
                    commissionRate: $("#commissionRate").val(),
                    actualLoanDuration: $("#actualLoanDuration").val()
                },
                cache: false,
                url: "/opportunity/calculateCommission",
                dataType: "JSON",
                success: function (datas) {
                    var data = datas["commission"];
                    $("#commission").val(data);
                },
            });
        }

        $("#commissionPaymentMethod").change(function () {
            getCommission();
        });

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
        $("input[name='notarization']:checkbox").each(function () {

            var notarization = $(this).val();
            var notarizations = $("#notarizations").val();
            var sear=new RegExp(notarization);
            if(sear.test(notarizations))
            {
                this.checked="checked";
            }

        });
    });
    function beforeSubmit() {
        var arr = "";
        $("input[name='notarization']:checkbox").each(function () {
            if ($(this).prop("checked") === true) {
                var a = $(this).val();
                arr += a + ",";
            }
        });
        $("#notarizations").val(arr);
        $(".formSubmit").submit();
    }
</g:javascript>
</body>
</html>

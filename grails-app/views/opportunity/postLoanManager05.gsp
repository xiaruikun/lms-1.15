<!DOCTYPE html>
<html lang="en">

<head>
    <meta name="layout" content="main2"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>展期主单订单推进2.0-PLM05</title>
    <style>
    .input-group[class*="col-"] {
        float: left;
        padding-right: 15px;
        padding-left: 15px;
    }

    .field label {
        font-weight: normal;
    }
    .collateralInfo {
        display: none;
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
            <span style="font-weight: normal" class="text-info">
                【${this.opportunity?.stage?.description}】
                <g:if test="${this.opportunity?.status == 'Failed'}">【订单结果：<span
                        class="text-danger">失败</span>】</span></g:if>
                <g:elseif test="${this.opportunity?.status == 'Completed'}">【订单结果：成功】</g:elseif>
                <g:else>【订单结果：进行中】</g:else>
            </span>
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
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
    <div class="row">

        <g:form action="ajaxUpdate" name="myForm" class="form_1" id="${this.opportunity?.id}">
            <div class="hpanel hyellow">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-xs btn-info" type="submit" id="submitButton">
                            <i class="fa fa-check"></i> 保存
                        </button>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    第一步：完善展期主审结果
                </div>

                <div class="panel-body">

                    <div class="form-group col-md-4">
                        <label for="actualAmountOfCredit">审核可展期金额（万元）</label>

                        <g:if test="${this.opportunity?.actualAmountOfCredit}">
                            <g:textField class="form-control" name="actualAmountOfCredit"
                                         id="actualAmountOfCredit"
                                         value="${this.opportunity?.actualAmountOfCredit}"/>
                        </g:if>
                        <g:else>
                            <g:textField class="form-control" name="actualAmountOfCredit"
                                         value="${this.opportunity?.maximumAmountOfCredit}"/>
                        </g:else>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="actualLoanDuration">审核可展期期限（月）</label>

                        <g:textField class="form-control" name="actualLoanDuration"
                                     value="${this.opportunity?.actualLoanDuration}"/>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="product">展期产品类型</label>
                        <g:select class="form-control" optionKey="id" optionValue="name"
                                  name="product"
                                  value="${this.opportunity?.product?.id}"
                                  from="${com.next.Product.findAllByActive(true)}"/>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="interestPaymentMethod">展期付息方式</label>

                        <g:select class="form-control interestPaymentMethod" optionKey="id" optionValue="name"
                                  name="interestPaymentMethod"
                                  value="${this.opportunity?.interestPaymentMethod?.id}"
                                  from="${com.next.InterestPaymentMethod.list()}"/>
                    </div>
                    <div class="infoPaymentOfInterest hide form-group col-md-4">
                        <label for="monthOfAdvancePaymentOfInterest">上扣息月份数（月）：</label>

                        <g:textField class="form-control" name="monthOfAdvancePaymentOfInterest"
                                     value="${this.opportunity?.monthOfAdvancePaymentOfInterest}"/>
                    </div>
                </div>

            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                第二步：完善房产信息
            </div>

            <div class="panel-body p-xs loanToValue" style="border-top:2px solid #ffb606">
                <g:each in="${this.collaterals}">
                    <g:form controller="collateral" action="ajaxUpdate" id="${it?.id}" class="form_2">
                        <div class="panel-heading">
                            <div class="panel-tools">
                                <button class="btn btn-xs btn-info" type="submit">
                                    <i class="fa fa-check"></i> 保存
                                </button>
                            </div>
                            第<span class="loanToValueOrder"></span>套房产
                        </div>

                        <div class="panel-body">
                            <div class="form-group col-md-4">
                                <label for="loanToValue">展期抵押率（%）</label>
                                <input class="form-control" name="loanToValue" type="text" id="loanToValue"
                                       value="${it?.loanToValue}">
                            </div>

                            <div class="form-group col-md-4">
                                <label for="region.id">所在区域</label>
                                <g:select id="region" name='region.id' noSelection="${['': '请选择']}"
                                          value="${it?.region?.id}" from='${com.next.CollateralRegion.list()}'
                                          optionKey="id" optionValue="name" class="form-control"></g:select>

                            </div>

                            <div class="form-group col-md-4">
                                <label for="buildTime">建成时间</label>

                                <div class="">
                                    <g:datePicker name="buildTime" value="${it?.buildTime}" precision="year"/>
                                </div>
                            </div>

                            <div class="collateralInfo">
                                <div class="form-group col-md-4">
                                    <label for="mortgageType">抵押类型</label>
                                    <g:select id="mortgageType" name='mortgageType.id'
                                              value="${it?.mortgageType?.id}" from="${com.next.MortgageType.list()}"
                                              optionKey="id" optionValue="name" class="form-control"></g:select>
                                </div>

                                <div class="form-group col-md-4">
                                    <label for="typeOfFirstMortgage">一抵性质</label>
                                    <g:select id="typeOfFirstMortgage" name='typeOfFirstMortgage.id'
                                              value="${it?.typeOfFirstMortgage?.id}" noSelection="['': '无']"
                                              from="${com.next.TypeOfFirstMortgage.list()}" optionKey="id"
                                              optionValue="name" class="form-control"></g:select>
                                </div>

                                <div class="form-group col-md-4">
                                    <label for="firstMortgageAmount">一抵金额（万元）</label>
                                    <input class="form-control" name="firstMortgageAmount" type="text"
                                           value="${it?.firstMortgageAmount}">
                                </div>

                                <div class="form-group col-md-4">
                                    <label for="secondMortgageAmount">二抵金额</label>
                                    <input class="form-control" name="secondMortgageAmount" type="text"
                                           value="${it?.secondMortgageAmount}">
                                </div>
                            </div>

                            <div class="form-group">
                                <button type="button" class="btn btn-sm btn-success morebtn">编辑更多</button>
                            </div>
                        </div>
                    </g:form>
                </g:each>
            </div>
        </div>
    </div>

    <div class="row">
        <g:form controller="opportunityFlexFieldCategory" action="ajaxRiskReport" class="form_3">

            <div class="hpanel hgreen">

                <div class="panel-heading">
                    <div class="panel-tools">

                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    第三步：完善展期风险调查报告
                </div>

                <div class="panel-body form-horizontal field">

                    <input type="hidden" name="opportunity" id="opportunity" value="${this.opportunity?.id}">
                    <g:each in="${this.opportunityFlexFieldCategorys}" var="category">
                        <g:if test="${category?.flexFieldCategory?.name in ['展期基本情况', '展期抵押物情况', '展期征信小结', '展期大数据小结', '展期用途', '展期还款来源', '展期要求']}">
                            <div class="fieldBox border-bottom">
                                <h5>${category?.flexFieldCategory?.name}</h5>
                                <g:each in="${category.fields}" var="field">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">${field?.name}</label>

                                        <div class="col-md-8">
                                            <g:if test="${field.values}">
                                                <g:select class="form-control" name="${field?.name}"
                                                          id="${field?.name}"
                                                          value="${field?.value}" optionKey="value"
                                                          optionValue="value"
                                                          from="${field?.values}"
                                                          noSelection="['': '-请选择-']"></g:select>
                                            </g:if>
                                            <g:else>
                                                <g:textArea name="${field?.name}" id="${field?.name}"
                                                            value="${field?.value}" class="form-control" rows="4"/>
                                            </g:else>
                                        </div>
                                    </div>

                                    <div class="hr-line-dashed"></div>
                                </g:each>
                            </div>
                        </g:if>
                    </g:each>

                </div>
                <div class="panel-footer text-center">
                    <button class="btn btn-sm btn-info" type="button" id="form3Btn">
                        <i class="fa fa-check"></i> 保存
                    </button>
                </div>
            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                第四步：检查左侧附件是否完全
            </div>
        </div>

    </div>
</div>
</div>
<asset:javascript src="homer/vendor/jquery-validation/jquery.validate.min.js"/>
<script>

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

        $(".morebtn").click(function(){
            $(this).parent().prev(".collateralInfo").slideDown(200);
            $(this).hide();
        })

        $(".form_1").validate({
            rules: {
                actualAmountOfCredit: {
                    required: true,
                    number: true,
                    isGtZero: true
                },
                actualLoanDuration: {
                    required: true,
                    digits: true,
                    isGtZero: true
                },
            },
            messages: {
                actualAmountOfCredit: {
                    required: "请输入审核可贷金额",
                    number: "请输入有效的数字",
                    isGtZero: "数值大于0"
                },
                actualLoanDuration: {
                    required: "请输入实际贷款期限",
                    digits: "请输入整数",
                    isGtZero: "数值大于0"
                },
            },
            submitHandler: function (form) {
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: $(".form_1").attr('action'),
                    data: $(".form_1").serialize(),
                    success: function (data) {
                        if (data.status == "success") {
                            swal("修改成功", "", "success");
                        }
                        else {
                            swal(data.errorMessage, "", "error");
                            // alert(data.errorMessage)
                        }

                    },
                    error: function () {
                        swal("获取失败，请稍后重试", "", "error");
                    }
                })
            },
            errorPlacement: function (error, element) {
                $(element)
                        .closest("form")
                        .find("label[for='" + element.attr("id") + "']")
                        .append(error);
            },
            errorElement: "span",
        });
        $.each($(".form_2"), function (i, obj) {
            $(obj).find(".loanToValueOrder").text(i + 1)
            $(obj).validate({
                rules: {
                    loanToValue: {
                        required: true,
                    },
                    buildTime: {
                        required: true
                    }
                },
                messages: {
                    loanToValue: {
                        required: "请输入抵押率",
                    },
                    buildTime: {
                        required: "请选择建成时间"
                    }
                },
                submitHandler: function () {
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: $(obj).attr('action'),
                        data: $(obj).serialize(),
                        success: function (data) {
                            if (data.status == "success") {
                                swal("修改成功", "", "success");
                            }
                            else {
                                swal(data.errorMessage, "", "error");
                            }

                        },
                        error: function () {
                            swal("获取失败，请稍后重试", "", "error");
                        }
                    })
                },
                errorPlacement: function (error, element) {
                    $(element)
                            .closest("form")
                            .find("label[for='" + element.attr("id") + "']")
                            .append(error);
                },
                errorElement: "span",
            });
        });

        $("#form3Btn").click(function () {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: $(".form_3").attr('action'),
                data: $(".form_3").serialize(),
                success: function (data) {
                    if (data.status == "success") {
                        swal("风险调查报告修改成功", "", "success");
                    }
                    else {
                        swal(data.errorMessage, "", "error");
                    }

                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            })
        });
    });
</script>
</body>
</html>

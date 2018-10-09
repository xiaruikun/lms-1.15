<!DOCTYPE html>
<html lang="en">

<head>
    <meta name="layout" content="main2"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>面谈已完成（继续审批）</title>
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
    textarea {
        resize: none;
    }

    .field label {
        font-weight: normal;
    }

    .fieldBox {
        border-bottom: 1px solid #e4e5e7;
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
                【保护期： <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                ${this.opportunity?.protectionEndTime - this.opportunity?.protectionStartTime}天
                </g:if>】
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
                    第一步：完善主审结果
                </div>

                <div class="panel-body">

                    <div class="form-group col-md-4">
                        <label for="actualAmountOfCredit">审核可贷金额（万元）</label>

                        <g:if test="${this.opportunity?.actualAmountOfCredit}">
                            <g:textField class="form-control" name="actualAmountOfCredit"
                                         id="actualAmountOfCredit"
                                         value="${this.opportunity?.actualAmountOfCredit}"/>
                        </g:if>
                        <g:else>
                            <g:textField class="form-control" name="actualAmountOfCredit"  id="actualAmountOfCredit"
                                         value="${this.opportunity?.maximumAmountOfCredit}"/>
                        </g:else>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="actualLoanDuration">实际贷款期限（月）</label>

                        <g:textField class="form-control" name="actualLoanDuration" id="actualLoanDuration"
                                     value="${this.opportunity?.actualLoanDuration}"/>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="monthlyInterest">月息（%）</label>
                       <!--  <g:textField class="form-control" name="monthlyInterest"
                                    value="${this.opportunity?.monthlyInterest}"/> -->
                        <input class="form-control" name="monthlyInterest" value="<g:formatNumber number="${this.opportunity?.monthlyInterest}"  maxFractionDigits="9"/>">

                    </div>

                    <div class="form-group col-md-4">
                        <label for="serviceCharge">借款服务费（%）</label>
                         <input class="form-control" name="serviceCharge" value="<g:formatNumber number="${this.opportunity?.serviceCharge}"  maxFractionDigits="9"/>">

                       <!--  <g:textField class="form-control" name="serviceCharge"
                                    value="${this.opportunity?.serviceCharge}"/> -->
                    </div>

                    <div class="form-group col-md-4">
                        <label for="commissionRate">渠道服务费（%）</label>
                        <input class="form-control" id="commissionRate" name="commissionRate" value="<g:formatNumber number="${this.opportunity?.commissionRate}"  maxFractionDigits="9"/>">

                     <!--    <g:textField class="form-control" name="commissionRate"
                                  id="commissionRate"
                                  value="${this.opportunity?.commissionRate}"/> -->
                    </div>

                    <div class="form-group col-md-4">
                        <label for="interestPaymentMethod">付息方式</label>

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

                    <div class="form-group col-md-4">
                        <label for="commissionPaymentMethod">返佣方式</label>

                        <g:select class="form-control" optionKey="id" optionValue="name"
                                  name="commissionPaymentMethod"
                                  id="commissionPaymentMethod"
                                  value="${this.opportunity?.commissionPaymentMethod?.id}"
                                  from="${com.next.CommissionPaymentMethod.list()}"/>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="commission">渠道总返费（万元）</label>

                        <g:textField class="form-control" name="commission"
                                     id="commission"
                                     value="${this.opportunity?.commission}"/>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="level">客户级别</label>

                        <div class="form-control" style="border:none;padding-left: 0">
                            <g:radioGroup class="checkbox-inline" name="level"
                                          value="${this.opportunity?.lender?.level?.name}"
                                          labels="['优质', '次优', '疑难', '不良', '待评级']"
                                          values="['A', 'B', 'C', 'D', '待评级']">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="advancePayment">意向金（元）</label>
                        <g:textField class="form-control" name="advancePayment"
                                     value="${this.opportunity?.advancePayment}"/>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="mortgageCertificateType">抵押类凭证</label>

                        <g:select class="form-control" name="mortgageCertificateType"
                                  value="${this.opportunity?.mortgageCertificateType?.id}"
                                  noSelection="${['null': '无']}"
                                  from="${com.next.MortgageCertificateType.list()}" optionKey="id"
                                  optionValue="name"></g:select>
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
                                <label for="loanToValue">抵押率（%）</label>
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
                        <button class="btn btn-xs btn-info" type="button" id="form3Btn">
                            <i class="fa fa-check"></i> 保存
                        </button>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    第三步：完善风险调查报告
                </div>

                <div class="panel-body form-horizontal field">

                    <input type="hidden" name="opportunity" id="opportunity" value="${this.opportunity?.id}">
                    <g:each in="${this.opportunityFlexFieldCategorys}" var="category">
                        <g:if test="${category?.flexFieldCategory?.name in ['抵押物情况', '借款人资质小结', '征信小结', '大数据小结', '借款用途', '还款来源', '风险结论', '放款前要求','罚息约定']}">
                            <div class="fieldBox">
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

            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.opportunity?.contacts.size() > 0}">
                        <g:link class="btn btn-info btn-xs" controller="activity"
                                params="[opportunityId: this.opportunity.id]"
                                action="create2"><i class="fa fa-plus"></i>新增</g:link></g:if>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                第四步：配置下户信息
            </div>

            <div class="panel-body no-padding">
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
                                              title="${message(code: 'activity.contact.label', default: '抵押人姓名')}"/>
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
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                <g:sortableColumn class="text-center" width="5%" property="operation"
                                                  title="操作"></g:sortableColumn>
                            </sec:ifAllGranted>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.activities}">
                            <tr>
                                <td>
                                    <g:link controller="activity" action="show" id="${it.id}"
                                            class="firstTd">${it?.type?.name}</g:link>
                                </td>
                                <td>${it?.subtype?.name}</td>
                                <td>
                                    <g:formatDate class="weui_input" date="${it?.startTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="startTime" autocomplete="off"
                                                  readonly="true"></g:formatDate>
                                </td>
                                <td>
                                    <g:formatDate class="weui_input" date="${it?.endTime}" format="yyyy-MM-dd HH:mm:ss"
                                                  name="endTime" autocomplete="off" readonly="true"></g:formatDate>
                                </td>
                                <td>
                                    <g:formatDate class="weui_input" date="${it?.actualStartTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="actualStartTime" autocomplete="off"
                                                  readonly="true"></g:formatDate>
                                </td>
                                <td>
                                    <g:formatDate class="weui_input" date="${it?.actualEndTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="actualEndTime" autocomplete="off"
                                                  readonly="true"></g:formatDate>
                                </td>
                                <td>${it?.contact?.fullName}</td>
                                <td>${it?.user}</td>
                                <td>${it?.assignedTo}</td>
                                <td>${it?.status}</td>
                                <td>${it?.city}</td>
                                <td>${it?.address}</td>
                                <td>${it?.longitude}</td>
                                <td>${it?.latitude}</td>
                                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                    <td class="text-center" width="5%">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </sec:ifAllGranted>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>

    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                第五步：检查左侧附件是否完全
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
                monthlyInterest: {
                    required: true,
                    number: true,
                    isGtZero: true
                },
                serviceCharge: {
                    required: true,
                    number: true,
                },
                commissionRate: {
                    required: true,
                    number: true
                },
                commission: {
                    number: true,
                },
                advancePayment: {
                    number: true,
                }
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
                monthlyInterest: {
                    required: "请输入月息",
                    number: "请输入有效的数字",
                    isGtZero: "数值大于0"
                },
                serviceCharge: {
                    required: "请输入借款服务费",
                    number: "请输入有效的数字"
                },
                commissionRate: {
                    required: "请输入渠道服务费",
                    number: "请输入有效的数字"
                },
                commission: {
                    number: "请输入有效的数字",
                },
                advancePayment: {
                    number: "请输入有效的数字",
                }

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
        getCommission();
        function getCommission(){
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

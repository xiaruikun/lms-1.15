<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="wechat"/>
    <asset:stylesheet src="jquery-weui.css"/>
    <title>立即报单</title>
    <style>
    .report-bill .weui_cells_form .weui_cell {
        padding-left: 25px;
    }

    .reportBill-radio {
        color: #4A4A4A;
        font-size: 1.2rem;
        margin-bottom: 10px;
    }

    .radio {
        margin: 0 4px 0 20px;
        width: 1.5rem;
        height: 1.5rem;
        vertical-align: middle;
    }

    .report-bill .weui_cell_hd label {
        width: 7rem;
    }

    .sep-line {
        border-top: 6px solid #f4f4ef;
    }

    .spouseInfo, .mortgageSum {
        padding: 0 15px;
    }

    .requiredItem:before {
        content: "*";
        color: #CD001A;
        position: absolute;
        left: 15px;
    }

    .wxShow-warmTip {
        padding: 15px 15px 0;
        color: #9b9b9b;
        font-size: 1rem;
    }
    </style>
</head>

<body>
<div class="report-bill">
    <div class="reportBill-radio weui_cells ">
        <div class="weui_cell" class="">
            <div style="">借款人和抵押人是同一人</div>
            <input type="hidden" id="radioBtn" value="yes"/>

            <div class="weui_cell_bd weui_cell_primary">
                <input id="yes" type="radio" name="myGroup" value="1" checked="true" class="radio"/>是
                <input id="no" type="radio" name="myGroup" value="0" class="radio"/>否
            </div>
        </div>
    </div>

    <g:form action="wxUpload2" name="reportBillForm">
        <input name="id" value="${this.opportunity.id}" type="hidden">
        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="requiredItem weui_cell_hd"><label class="weui_label">贷款金额</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:field class="weui_input" type="number" id="requestedAmount" name="requestedAmount" value=""
                             autofocus="autofocus"/>
                </div>

                <div class="weui_cell_hd">
                    <label class="weui_label" style="margin-right: 0;text-align: right">（万元）</label>
                </div>
            </div>

            <div class="weui_cell">
                <div class="requiredItem weui_cell_hd"><label class="weui_label">贷款期限</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:field class="weui_input" type="number" id="loanDuration" name="loanDuration" value=""/>
                </div>

                <div class="weui_cell_hd">
                    <label class="weui_label" style="margin-right: 0;text-align: right">（月）</label>
                </div>
            </div>

            %{--抵押类型--}%
            <div class="weui_cell weui_cell_select" style="padding-left: 25px">
                <div class="requiredItem weui_cell_hd"><label class="weui_label">抵押类型</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:select class="weui_select" name="mortgageType" id="mortgageType" optionKey="id" optionValue="name" from="${com.next.MortgageType.list()}" value="${this.opportunity?.mortgageType?.id}"/>
                </div>
            </div>

            <div class="mortgageSum"></div>

            <div class="hide mortgageSum" id="fMortgageAmount">
                <div class="weui_cell" style="border-top: 1px solid #ECECEC;padding-right: 0">
                    <div class="weui_cell_hd"><label class="weui_label">一抵金额</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:field class="weui_input" type="number" id="firstMortgageAmount" name="firstMortgageAmount"
                                 value="${this.opportunity?.firstMortgageAmount}"/>
                    </div>

                    <div class="weui_cell_hd"><label class="weui_label"
                                                     style="margin-right: 0;text-align: right">（万元）</label></div>
                </div>
            </div>

            <div class="hide mortgageSum" id="sMortgageAmount">
                <div class="weui_cell" style=" border-top: 1px solid #ECECEC;padding-right: 0">
                    <div class="weui_cell_hd"><label class="weui_label">二抵金额</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:field class="weui_input" type="number" id="secondMortgageAmount" name="secondMortgageAmount"
                                 value="${this.opportunity?.secondMortgageAmount}"/>
                    </div>

                    <div class="weui_cell_hd"><label class="weui_label"
                                                     style="margin-right: 0;text-align: right">（万元）</label></div>
                </div>
            </div>

            <div class="hide mortgageSum" id="typeOfFirstMortgage">
                <div class="weui_cell weui_cell_select" style=" border-top: 1px solid #ECECEC;padding-right: 0">
                    <div class="weui_cell_hd"><label class="weui_label">一抵性质</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:select class="weui_select" name="typeOfFirstMortgage" id="typeOfFirstMortgage" optionKey="id" optionValue="name" from="${com.next.TypeOfFirstMortgage.list()}" value="${this.opportunity?.typeOfFirstMortgage?.id}"/>
                    </div>
                </div>
            </div>

            %{--借款人信息--}%
            <div id="borrowerInfo" class="sep-line">
                <div class="weui_cell">
                    <div class="requiredItem weui_cell_hd"><label class="weui_label">借款人姓名</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:textField class="weui_input" type="text" id="fullName" name="fullName"
                                     value="${this.opportunity?.fullName}"/>
                    </div>
                </div>

                <div class="weui_cell">
                    <div class="requiredItem weui_cell_hd"><label class="weui_label">借款人身份证</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:textField class="weui_input" type="text" id="idNumberTemp" name="idNumberTemp"
                                     value="${this.opportunity?.idNumber}" maxLength="18"/>
                    </div>
                </div>

                <div class="weui_cell weui_cell_select" style="padding-left: 25px">
                    <div class="requiredItem weui_cell_hd"><label class="weui_label">借款人婚姻状态</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:select class="weui_select" name="maritalStatus" id="maritalStatus"
                                  from="${["未婚", "已婚", "再婚", "离异", "丧偶"]}"
                                  valueMessagePrefix="maritalStatus" value="${this.opportunity?.maritalStatus}"/>
                    </div>
                </div>

                <div class="hide spouseInfo" id="maritalSpouseInfo">
                    <div class="weui_cell" style=" border-top: 1px solid #ECECEC">

                        <div class="weui_cell_hd"><label class="requiredItem weui_label">配偶姓名</label></div>

                        <div class="weui_cell_bd weui_cell_primary">
                            <g:textField class="weui_input" type="text" id="spouseFullName" name="spouseFullName"
                                         value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('借款人配偶'))?.contact?.fullName}"/>
                        </div>
                    </div>

                    <div class="weui_cell">

                        <div class="weui_cell_hd"><label class="requiredItem weui_label">配偶身份证</label></div>

                        <div class="weui_cell_bd weui_cell_primary">
                            <g:textField class="weui_input" type="text" id="spouseIdNumber" name="spouseIdNumber"
                                         value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('借款人配偶'))?.contact?.idNumber}"
                                         maxLength="18"/>
                        </div>
                    </div>

                    <div class="weui_cell">

                        <div class="weui_cell_hd"><label class="weui_label">配偶手机号</label></div>

                        <div class="weui_cell_bd weui_cell_primary">
                            <g:field class="weui_input" type="number" id="spouseCellphone" name="spouseCellphone"
                                     placeholder="选填"
                                     value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('借款人配偶'))?.contact?.cellphone}"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">借款人手机号</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:field class="weui_input" type="number" id="cellphone" name="cellphone" placeholder="   选填"
                             value="${this.opportunity?.cellphone}"/>
                </div>
            </div>

            %{--抵押人信息--}%
            <div id="mortgagorInfo" class=" sep-line hide">
                <div class="weui_cell">
                    <div class="requiredItem weui_cell_hd"><label class="weui_label">抵押人姓名</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:textField class="weui_input" type="text" id="mortgagorFullName" name="mortgagorFullName"
                                     value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人'))?.contact?.fullName}"/>
                    </div>
                </div>

                <div class="weui_cell">
                    <div class="requiredItem weui_cell_hd"><label class="weui_label">抵押人身份证</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:textField class="weui_input" type="text" id="mortgagorIdNumber" name="mortgagorIdNumber"
                                     value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人'))?.contact?.idNumber}"
                                     maxLength="18"/>
                    </div>
                </div>

                <div class="weui_cell weui_cell_select" style="padding-left: 25px">
                    <div class="requiredItem weui_cell_hd"><label class="weui_label">抵押人婚姻状态</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:select class="weui_select" name="mortgagorMaritalStatus" id="mortgagorMaritalStatus"
                                  from="${["未婚", "已婚", "再婚", "离异", "丧偶"]}"
                                  valueMessagePrefix="mortgagorMaritalStatus"
                                  value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人'))?.contact?.maritalStatus}"/>
                    </div>
                </div>

                <div class="spouseInfo hide" id="mortgagorSpouseInfo">
                    <div class="weui_cell" style=" border-top: 1px solid #ECECEC">
                        <div class="weui_cell_hd"><label class="requiredItem weui_label">配偶姓名</label></div>

                        <div class="weui_cell_bd weui_cell_primary">
                            <g:textField class="weui_input" type="text" id="mortgagorSpouseFullName"
                                         name="mortgagorSpouseFullName"
                                         value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人配偶'))?.contact?.fullName}"/>
                        </div>
                    </div>

                    <div class="weui_cell">
                        <div class="weui_cell_hd"><label class="requiredItem weui_label">配偶身份证</label></div>

                        <div class="weui_cell_bd weui_cell_primary">
                            <g:textField class="weui_input" type="text" id="mortgagorSpouseIdNumber"
                                         name="mortgagorSpouseIdNumber"
                                         value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人配偶'))?.contact?.idNumber}"
                                         maxLength="18"/>
                        </div>
                    </div>

                    <div class="weui_cell">
                        <div class="weui_cell_hd"><label class="weui_label">配偶手机号</label></div>

                        <div class="weui_cell_bd weui_cell_primary">
                            <g:field class="weui_input" type="number" id="mortgagorSpouseCellphone"
                                     name="mortgagorSpouseCellphone" placeholder="选填"
                                     value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人配偶'))?.contact?.cellphone}"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="hide" id="mortgagorCellphoneId">
                <div class="weui_cell" style="border-top: 1px solid #ECECEC;left: 15px;padding-left: 10px">
                    <div class="weui_cell_hd"><label class="weui_label">抵押人手机号</label></div>

                    <div class="weui_cell_bd weui_cell_primary">
                        <g:field class="weui_input" type="number" id="mortgagorCellphone" name="mortgagorCellphone"
                                 placeholder="选填"
                                 value="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人'))?.contact?.cellphone}"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="wxShow-warmTip">
            <h3 class="warm-tit" style="color: #9b9b9b;font-size: 1.2rem">温馨提示：</h3>

            <p style="color:#4A4A4A;margin-bottom: 4px">
                <span>完善手机号，即可增加保护期时长，最长可达</span>
                <span style="color:#ca61cd">30</span>天！
            </p>
            <h4>您当前的保护期时长
                <span style="color:#CD001A" id="protectDay">10</span>
                天！</h4>
        </div>

        <div class="bigBtn" style="margin-bottom: 20px">
            <input class="linkBtn weui_btn weui_btn_plain_default" value="确认提交" id="reportBillBtn" type="button"/>
        </div>

    </g:form>
</div>

<div class="hjwoo-hotline2">
    <a href="tel:${this.opportunity?.contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${this.opportunity?.contact?.city?.telephone}</span>
    </a>
</div>


%{--提示信息和验证--}%
<div class="message-box">
    <div class="helpMsg hide"></div>
    <g:if test="${flash.message}">
        <div class="message" role="status">
            ${flash.message}
        </div>
    </g:if>
    <div class="row">
        <g:hasErrors bean="${this.opportunity}">
            <ul class="message" role="alert">
                <g:eachError bean="${this.opportunity}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
    </div>
</div>
<g:javascript>
    $(function () {
        $(".hjwoo-hotline2 a").click(function () {
            $(this).addClass("active");
        });

        var $input = $("input");
        for (var i = 0; i < $input.length; i++) {
            $input.eq(i).attr("autocomplete", "off");
        }

        // 页面初始化：借款人、抵押人是否为同一人
        var mortgagorInfo = $("#mortgagorInfo");
        var mortgagorCellphoneId = $("#mortgagorCellphoneId");
        if ($("#mortgagorIdNumber").val()) {
            $("#yes")[0].checked = "false";
            $("#no")[0].checked = "true";
            mortgagorInfo.show();
            mortgagorCellphoneId.show();
        }

        // 借款人婚姻状态
        var maritalStatus = $("#maritalStatus");
        var maritalSpouseInfo = $("#maritalSpouseInfo");
        // 页面初始化：借款人婚姻状态
        if (maritalStatus.val() == "已婚" || maritalStatus.val() == "再婚") {
            maritalSpouseInfo.show();
        }
        maritalStatus.change(function () {
            if (maritalStatus.val() == "未婚" || maritalStatus.val() == "离异" || maritalStatus.val() == "丧偶") {
                $("#spouseFullName").val("");
                $("#spouseIdNumber").val("");
                $("#spouseCellphone").val("");
                maritalSpouseInfo.hide();
            }
            if (maritalStatus.val() == "已婚" || maritalStatus.val() == "再婚") {
                maritalSpouseInfo.show();
            }
        });
        // 抵押人婚姻状态
        var mortgagorMaritalStatus = $("#mortgagorMaritalStatus");
        var mortgagorSpouseInfo = $("#mortgagorSpouseInfo");
        // 页面初始化：抵押人婚姻状态
        if (mortgagorMaritalStatus.val() == "已婚" || mortgagorMaritalStatus.val() == "再婚") {
            mortgagorSpouseInfo.show();
        }
        mortgagorMaritalStatus.change(function () {
            if (mortgagorMaritalStatus.val() == "未婚" || mortgagorMaritalStatus.val() == "离异" || mortgagorMaritalStatus.val() == "丧偶") {
                $("#mortgagorSpouseFullName").val("");
                $("#mortgagorSpouseIdNumber").val("");
                $("#mortgagorSpouseCellphone").val("");
                mortgagorSpouseInfo.hide();
            }
            if (mortgagorMaritalStatus.val() == "已婚" || mortgagorMaritalStatus.val() == "再婚") {
                mortgagorSpouseInfo.show();
            }
        });

        // 抵押类型
        var mortgageType = $("#mortgageType");
        var fMortgageAmount = $("#fMortgageAmount");
        var sMortgageAmount = $("#sMortgageAmount");
        var typeOfFirstMortgage = $("#typeOfFirstMortgage");
        // 页面初始化：抵押类型赋值
        if (mortgageType.val() == 2 || mortgageType.val() == 3) {
            fMortgageAmount.show();
            typeOfFirstMortgage.show();
        }
        if (mortgageType.val() == 4) {
            fMortgageAmount.show();
            sMortgageAmount.show();
            typeOfFirstMortgage.show();
        }
        mortgageType.change(function () {
            $("#firstMortgageAmount").val("");
            $("#secondMortgageAmount").val("");
            if (mortgageType.val() == 1) {
                fMortgageAmount.hide();
                sMortgageAmount.hide();
                typeOfFirstMortgage.hide();
            }
            if (mortgageType.val() == 2 || mortgageType.val() == 3) {
                fMortgageAmount.show();
                typeOfFirstMortgage.show();
                sMortgageAmount.hide();
            }
            if (mortgageType.val() == 4) {
                fMortgageAmount.show();
                typeOfFirstMortgage.show();
                sMortgageAmount.show();
            }
        });

        var mortgagorInfo = $("#mortgagorInfo");
        var mortgagorCellphoneId = $("#mortgagorCellphoneId");
        $("#yes").click(function () {
            $("#radioBtn").val("yes");
            mortgagorInfo.hide();
            mortgagorCellphoneId.hide();
        })
        $("#no").click(function () {
            $("#radioBtn").val("no");
            mortgagorInfo.show();
            mortgagorCellphoneId.show();
        })

        $("#reportBillBtn").click(function () {

            // 贷款金额
            var requestedAmount = $("#requestedAmount").val().trim();
            if (!requestedAmount) {
                $(".helpMsg").text("请输入贷款金额").fadeIn(200);
                timeOut();
                return;
            }
            if (requestedAmount <= 0) {
                $(".helpMsg").text("贷款金额必须大于零").fadeIn(200);
                timeOut();
                return;
            }
            // 贷款期限
            var loanDuration = $("#loanDuration").val().trim();
            if (!loanDuration) {
                $(".helpMsg").text("请输入贷款期限").fadeIn(200);
                timeOut();
                return;
            }
            if (loanDuration <= 0) {
                $(".helpMsg").text("贷款期限必须大于零").fadeIn(200);
                timeOut();
                return;
            }
            if (!(/^-?[1-9]\d*$/.test(loanDuration))) {
                $(".helpMsg").text("贷款期限必须为整数").fadeIn(200);
                timeOut();
                return;
            }
            // 抵押类型:二抵金额
            // 抵押类型
            var firstMortgageAmount = $("#firstMortgageAmount").val().trim();
            var secondMortgageAmount = $("#secondMortgageAmount").val().trim();
            if (mortgageType.val() == 1) {
                $("#firstMortgageAmount").val(0);
                $("#secondMortgageAmount").val(0);
            }
            if (mortgageType.val() == 2 || mortgageType.val() == 3) {
                if (!firstMortgageAmount) {
                    $(".helpMsg").text("请输入一抵金额").fadeIn(200);
                    timeOut();
                    return;
                }
                if (firstMortgageAmount <= 0) {
                    $(".helpMsg").text("一抵金额必须大于零").fadeIn(200);
                    timeOut();
                    return;
                }
                $("#secondMortgageAmount").val(0);
            }
            if (mortgageType.val() == 4) {
                if (!firstMortgageAmount) {
                    $(".helpMsg").text("请输入一抵金额").fadeIn(200);
                    timeOut();
                    return;
                }
                if (firstMortgageAmount <= 0) {
                    $(".helpMsg").text("一抵金额必须大于零").fadeIn(200);
                    timeOut();
                    return;
                }
                if (!secondMortgageAmount) {
                    $(".helpMsg").text("请输入二抵金额").fadeIn(200);
                    timeOut();
                    return;
                }
                if (secondMortgageAmount <= 0) {
                    $(".helpMsg").text("二抵金额必须大于零").fadeIn(200);
                    timeOut();
                    return;
                }
            }
            // // 借款人姓名
            var fullName = $("#fullName").val().trim();
            if (!fullName) {
                $(".helpMsg").text("请输入借款人姓名").fadeIn(200);
                timeOut();
                return;
            }
            if (!(/^[\u2190-\u9fff]{1,10}$|^[\dA-Za-z]{1,20}$/.test(fullName))) {
                $(".helpMsg").text("借款人姓名格式不正确").fadeIn(200);
                timeOut();
                return;
            }
            // 借款人身份证
            var idNumber = $("#idNumberTemp").val().trim();
            if (!idNumber) {
                $(".helpMsg").text("请输入借款人身份证").fadeIn(200);
                timeOut();
                return;
            }
            if (!verifiedIdNumber(idNumber, "请输入正确的借款人身份证")) {
                return;
            }

            // 借款人婚姻状态：已婚
            if (maritalStatus.val() == "已婚" || maritalStatus.val() == "再婚") {
                // 借款人配偶姓名
                var spouseFullName = $("#spouseFullName").val().trim();
                if (!spouseFullName) {
                    $(".helpMsg").text("请输入借款人配偶姓名").fadeIn(200);
                    timeOut();
                    return;
                }
                // 借款人配偶身份证
                var spouseIdNumber = $("#spouseIdNumber").val().trim();
                if (!spouseIdNumber) {
                    $(".helpMsg").text("请输入借款人配偶身份证").fadeIn(200);
                    timeOut();
                    return;
                }
                if (!verifiedIdNumber(spouseIdNumber, "请输入正确的借款人配偶身份证")) {
                    return;
                }
            }

            // 借款人配偶手机号
            var spouseCellphone = $("#spouseCellphone").val().trim();
            if (spouseCellphone.length != 0 && !(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(spouseCellphone))) {
                $(".helpMsg").text("请输入正确的借款人配偶手机号").fadeIn(200);
                timeOut();
                return;
            }

            if (maritalStatus.val() == "未婚" || maritalStatus.val() == "离异" || maritalStatus.val() == "丧偶") {
                $("#spouseFullName").val("");
                $("#spouseIdNumber").val("");
                $("#spouseCellphone").val("");
            }

            if (mortgagorMaritalStatus.val() == "未婚" || mortgagorMaritalStatus.val() == "离异" || mortgagorMaritalStatus.val() == "丧偶") {
                $("#mortgagorSpouseFullName").val("");
                $("#mortgagorSpouseIdNumber").val("");
                $("#mortgagorSpouseCellphone").val("");
            }

            // 抵贷一致
            if ($("#yes")[0].checked) {
                $("#mortgagorFullName").val("");
                $("#mortgagorIdNumber").val("");
                $("#mortgagorSpouseFullName").val("");
                $("#mortgagorSpouseIdNumber").val("");
                $("#mortgagorSpouseCellphone").val("");
                $("#mortgagorCellphone").val("");
            }

            // 抵贷不一
            if ($("#no")[0].checked) {
                // 抵押人姓名
                var mortgagorFullName = $("#mortgagorFullName").val().trim();
                if (mortgagorFullName.length == 0) {
                    $(".helpMsg").text("请输入抵押人姓名").fadeIn(200);
                    timeOut();
                    return;
                }
                if (!(/^[\u2190-\u9fff]{1,10}$|^[\dA-Za-z]{1,20}$/.test(mortgagorFullName))) {
                    $(".helpMsg").text("抵押人姓名格式不正确").fadeIn(200);
                    timeOut();
                    return;
                }
                // 抵押人身份证
                var mortgagorIdNumber = $("#mortgagorIdNumber").val().trim();
                if (!mortgagorIdNumber) {
                    $(".helpMsg").text("请输入抵押人身份证").fadeIn(200);
                    timeOut();
                    return;
                }
                if (!verifiedIdNumber(mortgagorIdNumber, "请输入正确的抵押人身份证")) {
                    return;
                }

                // 抵押人婚姻状态：已婚
                if (mortgagorMaritalStatus.val() == "已婚" || mortgagorMaritalStatus.val() == "再婚") {
                    // 抵押人配偶姓名
                    var mortgagorSpouseFullName = $("#mortgagorSpouseFullName").val().trim();
                    if (!mortgagorSpouseFullName) {
                        $(".helpMsg").text("请输入抵押人配偶姓名").fadeIn(200);
                        timeOut();
                        return;
                    }
                    // 抵押人配偶身份证
                    var mortgagorSpouseIdNumber = $("#mortgagorSpouseIdNumber").val().trim();
                    if (!mortgagorSpouseIdNumber) {
                        $(".helpMsg").text("请输入抵押人配偶身份证").fadeIn(200);
                        timeOut();
                        return;
                    }
                    if (!verifiedIdNumber(mortgagorSpouseIdNumber, "请输入正确的抵押人配偶身份证")) {
                        return;
                    }
                }

                // 抵押人配偶手机号
                var mortgagorSpouseCellphone = $("#mortgagorSpouseCellphone").val().trim();
                if (mortgagorSpouseCellphone.length != 0 && !(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(mortgagorSpouseCellphone))) {
                    $(".helpMsg").text("请输入正确的抵押人配偶手机号").fadeIn(200);
                    timeOut();
                    return;
                }
            }

            // 借款人手机号
            var cellphone = $("#cellphone").val().trim();
            if (cellphone.length != 0 && !(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(cellphone))) {
                $(".helpMsg").text("请输入正确的借款人手机号").fadeIn(200);
                timeOut();
                return;
            }
            // 抵押人手机号
            var mortgagorCellphone = $("#mortgagorCellphone").val().trim();
            if (mortgagorCellphone.length != 0 && !(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(mortgagorCellphone))) {
                $(".helpMsg").text("请输入正确的抵押人手机号").fadeIn(200);
                timeOut();
                return;
            }
            $("#reportBillForm").submit();
        });
        setTimeout(function () {
            $(".helpMsg").fadeOut(200);
        }, 2000);
        setTimeout(function () {
            $(".message").fadeOut(200);
        }, 2000);

        // 验证借款人是否处于保护期
        $("#idNumberTemp").blur(function () {
            verifyProtectDay();
        });
        $("#idNumberTemp").focus(function () {
            $('#reportBillBtn').removeClass("btn_disabled").removeAttr("disabled");
        });
        // 动态显示保护期
        var n = 10;
        $("#protectDay").text(n);
        $("#cellphone").blur(function () {
            ajaxDay();
        });
        $("#spouseCellphone").blur(function () {
            ajaxDay();
        });
        $("#mortgagorCellphone").blur(function () {
            ajaxDay();
        });
        $("#mortgagorSpouseCellphone").blur(function () {
            ajaxDay();
        });
    });
    function verifyProtectDay() {
        var idNumber = $("#idNumberTemp").val().trim();
        if (!idNumber) {
            $(".helpMsg").text("请输入借款人身份证").fadeIn(200);
            timeOut();
            $('#reportBillBtn').removeClass("btn_disabled").removeAttr("disabled");
            return;
        }
        if (!verifiedIdNumber(idNumber, "请输入正确的借款人身份证")) {
            $('#reportBillBtn').removeClass("btn_disabled").removeAttr("disabled");
            return;
        }
        $.ajax({
            type: "POST",
            url: "https://" + window.location.host + "/wechatOpportunity/wxVerifyProtectDay2",
            data: {
                idNumber: $("#idNumberTemp").val()
            },
            success: function (data) {
                if (data.status == "success") {
                    if (data.flag == true) {
                        $(".helpMsg").text("借款人处于保护期，不可以报单").fadeIn(200);
                        $('#reportBillBtn').addClass("btn_disabled").attr('disabled', 'disabled');
                        timeOut();
                        return;
                    }
                }
            },
        });
    }
    function ajaxDay() {
        $.ajax({
            type: "POST",
            url: "https://" + window.location.host + "/wechatOpportunity/wxGetProtectTime",
            data: {
                cellphone: $("#cellphone").val(),
                spouseCellphone: $("#spouseCellphone").val(),
                mortgagorCellphone: $("#mortgagorCellphone").val(),
                mortgagorSpouseCellphone: $("#mortgagorSpouseCellphone").val(),
            },
            success: function (data) {
                if (data.status == "success") {
                    $("#protectDay").text(data.protectDays);
                }
            },
        });
    }
    function verifiedIdNumber(idNum, errorMessage) {
        var city = {11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海",
            32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东",
            45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海",
            64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外 "};

        if (!(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/.test(idNum))) {
            $(".helpMsg").text(errorMessage).fadeIn(200);
            timeOut();
            return false;
        } else if (!city[idNum.substr(0, 2)]) {
            $(".helpMsg").text(errorMessage).fadeIn(200);
            timeOut();
            return false;
        } else {
            //18位身份证需要验证最后一位校验位
            if (idNum.length == 18) {
                idNum = idNum.split('');
                //加权因子
                var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                //校验位
                var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++) {
                    ai = idNum[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var index = sum % 11;
                if (index == 2 && idNum[17] != 'x' && idNum[17] != 'X')
                {
                    $(".helpMsg").text(errorMessage).fadeIn(200);
                    timeOut();
                    return false;
                }
                if (index != 2 && parity[sum % 11] != idNum[17]) {
                    $(".helpMsg").text(errorMessage).fadeIn(200);
                    timeOut();
                    return false;
                }
            }
        }
        return true;
    }
    function timeOut() {
        setTimeout(function () {
            $(".helpMsg").fadeOut(200);
        }, 2000);
    }
</g:javascript>
</body>
</html>
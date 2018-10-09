<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="wechat"/>
    <title>个人中心</title>
</head>

<body>
%{--提示信息和验证--}%
<div class="message-box">
    <div class="helpMsg hide"></div>
    <g:if test="${flash.message}">
        <div class="message" role="status">
            ${flash.message}
        </div>
    </g:if>
    <div class="row">
        <g:hasErrors bean="${this.contact}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.contact}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
    </div>
</div>

<div class="wxEdit">
    <g:form action="wxUpdate" name="wxEditForm" resource="${this.contact}">
        <div class="wxEdit-sepLine weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">姓&nbsp;&nbsp;&nbsp;&nbsp;名</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:textField class="weui_input" type="text" name="fullName" id="fullName"
                                 value="${this.contact.fullName}" readonly="readonly"/>
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">手&nbsp;&nbsp;&nbsp;&nbsp;机</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:textField class="weui_input" type="text" id="cellphone" name="cellphone"
                                 value="${this.contact.cellphone}" readonly="readonly"/>
                </div>
            </div>

            <div class="weui_cell weui_cell_select" style="padding-left: 15px">
                <div class="weui_cell_hd"><label class="weui_label">城&nbsp;&nbsp;&nbsp;&nbsp;市</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:select class="weui_select" name="city" id="city" optionKey="id" optionValue="name"
                              from="${com.next.City.list()}" value="${this.contact?.city?.id}"/>
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">身&nbsp;份&nbsp;证</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:textField class="red-placeholder weui_input" type="text" name="idNumber" id="idNumber"
                                 placeholder="未完善" value="${this.contact.idNumber}" maxLength="18"/>
                </div>
            </div>
        </div>

        <div class="wxEdit-sepLine weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">银行卡类别</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:textField class="weui_input" type="text" name="bankName" placeholder="（如：工商银行长安街支行）"
                                 value="${this.contact.bankName}"/>
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">银行卡卡号</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:textField class="red-placeholder weui_input" type="text" name="bankAccount" placeholder="未完善"
                                 value="${this.contact.bankAccount}" onkeyup="formatBankNo(this)"
                                 onkeydown="formatBankNo(this)"/>
                </div>
            </div>
        </div>

        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">支持经理姓名</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <g:textField class="red-placeholder weui_input" type="text" name="managername" placeholder="暂无"
                                 readonly="readonly" value="${this.contact.user?.fullName}"/>
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">支持经理手机</label></div>

                <div class="weui_cell_bd weui_cell_primary">
                    <a href="tel:${this.contact.user?.cellphone}">
                        <g:textField class="red-placeholder weui_input" type="text" name="managerphone" placeholder="暂无"
                                     readonly="readonly" style="color: #488EDE"
                                     value="${this.contact.user?.cellphone}"/>
                    </a>
                </div>
            </div>
        </div>

        <div class="bigBtn">
            <button type="button" class="linkBtn weui_btn weui_btn_plain_default" id="wxEditBtn">提交信息</button>
        </div>
    </g:form>
    <div class="hjwoo-hotline2">
        <a href="tel:${this.contact?.city?.telephone}" class="hotline">
            <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
            <span class="city-hotline">${this.contact?.city?.telephone}</span>
        </a>
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
        $("#wxEditBtn").click(function () {
            // 参数合法性校验
            var code = $("#idNumber").val().trim();
            if (code) {
                if (!verifiedIdNumber(code, "请输入正确的身份证号")) {
                    return;
                }
            }
            var bankName = $("#bankName").val().trim();
            if (bankName) {
                if (!(/^[\u2190-\u9fff]+$/.test(bankName))) {
                    $(".helpMsg").text("银行名称输入必须为汉字").fadeIn(200);
                    timeOut();
                    return;
                }
            }
            var bankAccount = $("#bankAccount").val().trim();
            if (bankAccount) {
                if (!(bankAccount.length > 14 && bankAccount.length < 20)) {
                    $(".helpMsg").text("银行卡号长度为15~19位").fadeIn(200);
                    timeOut();
                    return;
                }
            }
            $("#wxEditForm").submit();
        });
    });
    function timeOut() {
        setTimeout(function () {
            $(".helpMsg").fadeOut(150);
        }, 2000);
    }

    function verifiedIdNumber(idNum, errorMessage) {
        var city = {
            11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海",
            32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东",
            45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海",
            64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外 "
        };
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
</g:javascript>

<script>
    function formatBankNo(BankNo) {
        if (BankNo.value == "") return;
        var account = new String(BankNo.value);
        account = account.substring(0, 23);
        /*帐号的总数, 包括空格在内 */
        if (account.match(".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null) {
            /* 对照格式 */
            if (account.match(".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" +
                            ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null) {
                var accountNumeric = accountChar = "", i;
                for (i = 0; i < account.length; i++) {
                    accountChar = account.substr(i, 1);
                    if (!isNaN(accountChar) && (accountChar != " ")) accountNumeric = accountNumeric + accountChar;
                }
                account = "";
                for (i = 0; i < accountNumeric.length; i++) {    /* 可将以下空格改为-,效果也不错 */
                    if (i == 4) account = account + " ";
                    /* 帐号第四位数后加空格 */
                    if (i == 8) account = account + " ";
                    /* 帐号第八位数后加空格 */
                    if (i == 12) account = account + " ";
                    /* 帐号第十二位后数后加空格 */
                    if (i == 16) account = account + " ";
                    /* 帐号第十二位后数后加空格 */
                    account = account + accountNumeric.substr(i, 1)
                }
            }
        }
        else {
            account = " " + account.substring(1, 5) + " " + account.substring(6, 10) + " " + account.substring(14, 18) + "-" + account.substring(18, 25);
        }
        if (account != BankNo.value) BankNo.value = account;
    }
</script>

</body>
</html>

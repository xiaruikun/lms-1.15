<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="wechat"/>
    <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}"/>
    <title>预约下户所带资料</title>
    <style>
    .infoList {
        padding: .5rem 1.2rem;
    }

    .bring-data .flex-box label {
        font-size: 1rem;
        color: #9B9B9B;
    }
    </style>
</head>

<body>
<div class="infoList bgcWhite">
    <div class="bring-data">
        <div class="required-dataList" style="border: none">
            <h4>必带材料:</h4>

            <p class="flex-box">
                <label class="flex1">已婚：</label>
                <span class="flex5">夫妻双方身份证原件；户口本原件；房产证原件；结婚证原件</span>
            </p>

            <p class="flex-box">
                <label class="flex1">未婚：</label>
                <span class="flex5">身份证原件；户口本原件；房产证原件</span>
            </p>

            <p class="flex-box">
                <label class="flex1">离异：</label>
                <span class="flex5">身份证原件；户口本原件；房产证原件；离婚证或法院判决书、离婚协议</span>
            </p>

            <p class="flex-box">
                <label class="flex1">再婚：</label>
                <span class="flex5">现夫妻双方身份证原件；户口本原件；房产证原件；结婚证原件；离婚证或法院判决书、离婚协议</span>
            </p>

            <p class="flex-box">
                <label class="flex1">丧偶：</label>
                <span class="flex5">身份证原件；户口本原件；房产证原件；房产遗产继承公证；配偶死亡证明；配偶死亡后婚姻状况证明</span>
            </p>

            <p>如借款人非抵押人本人，则需额外提供借款人夫妻双方身份证、户口本原件。</p>
        </div>
        <g:if test="${this.opportunity.lender?.level?.name == 'B'}">
            <div class="addition-dataList" style="border-top: 1px solid #e0e3e8;border-bottom: none">
                <h4>附加材料：</h4>

                <p>${this.opportunity.lender?.level?.description}</p>
            </div>
        </g:if>
    </div>

    <div class="bigBtn" style="margin: 4px 0">
        <g:link action="wxCreate" id="${this.opportunity?.id}" params="[activityDay:activityDay, activityPeriod:activityPeriod, openId: openId]" class="linkBtn2 weui_btn weui_btn_plain_default">返回上一级</g:link>
    </div>
</div>
</body>
</html>

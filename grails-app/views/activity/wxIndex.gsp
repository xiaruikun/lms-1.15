<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="wechat"/>
    <title>预约详情页</title>
</head>
<body>

<div class="wxIndex wxOrder">
    <div class="orderHistory">
        <g:each in="${opportunityList}" var="entry">
            <g:if test="${entry}">
                <div class="recordItem">
                    <div class="orderTitle">
                        <label>订单编号</label>
                        <strong>${entry?.serialNumber}</strong>
                        <g:link action="wxShow" id="${entry.id}" controller="opportunity">已初审</g:link>
                    </div>

                    <div class="orderDetail">
                        <p>
                            <label>提交时间</label>
                            <span><g:formatDate class="weui_input" date="${entry?.createdDate}" format="yyyy-MM-dd HH:mm:ss"
                                                name="createdDate" autocomplete="off" readonly="true"></g:formatDate></span>

                        </p>

                        <p>
                            <label>评房结果</label>
                            <span>${String.valueOf(Math.floor(entry?.loanAmount * 0.9)).substring(0, String.valueOf(Math.floor(entry?.loanAmount * 0.9)).lastIndexOf("."))}-${String.valueOf(entry?.loanAmount).substring(0, String.valueOf(entry?.loanAmount).lastIndexOf("."))}万元</span>
                        </p>
                        <p>
                            <label>房产地址</label>
                            <span>${entry.address}</span>
                        </p>
                        <div class="btnList">
                            <g:link action="wxShow" id="${entry.id}" controller="opportunity" class="linkBtn weui_btn weui_btn_plain_default ">查看详情</g:link>
                            <g:link action="wxCreate" class="linkBtn2 weui_btn weui_btn_plain_default" id="${entry.id}" params="[openId: openId]">立即预约</g:link>
                        </div>
                    </div>
                </div>
            </g:if>
        </g:each>
        <g:if test="${opportunityInstanceCount == 0}">
            <div class="empty-info">
                <g:img class="icon-expression" dir="images" file="icon-expression2.png"/>
            </div>
            <p class="warm-tip" style="margin-bottom: 4rem;">还没有“可预约”的订单，加油哦！</p>
        </g:if>
    </div>
</div>


<div class="wjwoo-hotline">
    <a href="tel:${contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${contact?.city?.telephone}</span>
    </a>
</div>
<script>
    $(function(){
        $(".linkBtn").click(function () {
            $(this).addClass("active");
        });
        $(".linkBtn2").click(function () {
            $(this).addClass("active");
        });
    });
</script>
</body>
</html>

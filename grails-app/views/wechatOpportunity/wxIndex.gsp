<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="wechat"/>
    <title>订单详情</title>
</head>

<body>
<div class="wxIndex">
<div class="orderHistory">
<g:each in="${opportunityList}" var="entry">
    <g:if test="${entry}">
        <g:if test="${entry.status != 'Failed'}">
            <g:if test="${entry.stage.code == '02' || entry.stage.code == '15'}">
                <div class="recordItem">
                    <div class="orderTitle">
                        <label>提交时间</label>
                        <span>
                            <g:formatDate class="weui_input" date="${entry.createdDate}" format="yyyy-MM-dd HH:mm:ss"
                                          name="createdDate" autocomplete="off" readonly="true"></g:formatDate>
                        </span>
                        <g:link action="wxShow" id="${entry.id}">已评房</g:link>
                    </div>

                    <div class="orderDetail">
                        <p>
                            <label>房产地址</label>
                            <span>${com.next.Collateral.findByOpportunity(entry)?.projectName}</span>
                        </p>

                        <p>
                            <label>订单编号</label>
                            <span>${entry.serialNumber}</span>
                        </p>

                        <p>
                            <g:if test="${com.next.Collateral.findByOpportunity(entry)?.status == 'Completed'}">
                                <label>评房结果</label>
                                <span class="colorRed">${Math.round(entry?.loanAmount)}万元
                                    <g:if test="${com.next.Collateral.findByOpportunity(entry)?.atticArea > 0}">
                                        <g:if test="${com.next.Collateral.findByOpportunity(entry)?.specialFactors == '跃层'}">
                                            <span style="color:#CD001A ">(含跃层总价</span>
                                            <span style="color:#CD001A ">${Math.round(com.next.Collateral.findByOpportunity(entry)?.unitPrice * com.next.Collateral.findByOpportunity(entry)?.atticArea / 20000)}万元)</span>
                                        </g:if>
                                        <g:if test="${com.next.Collateral.findByOpportunity(entry)?.specialFactors == '顶楼带阁楼'}">
                                            <span style="color:#CD001A ">(含阁楼总价</span>
                                            <span style="color:#CD001A ">${Math.round(com.next.Collateral.findByOpportunity(entry)?.unitPrice * com.next.Collateral.findByOpportunity(entry)?.atticArea / 20000)}万元)</span>
                                        </g:if>
                                    </g:if>
                                </span>
                            </g:if>
                            <g:if test="${com.next.Collateral.findByOpportunity(entry)?.status == 'Pending'}">
                                <label>参考评房结果</label>
                                <span style="color:blue">${Math.round(entry?.loanAmount)}万元
                                    <g:if test="${com.next.Collateral.findByOpportunity(entry)?.atticArea > 0}">
                                        <g:if test="${com.next.Collateral.findByOpportunity(entry)?.specialFactors == '跃层'}">
                                            <span style="color:blue ">(含跃层总价</span>
                                            <span style="color:blue ">${Math.round(com.next.Collateral.findByOpportunity(entry)?.unitPrice * com.next.Collateral.findByOpportunity(entry)?.atticArea / 20000)}万元)</span>
                                        </g:if>
                                        <g:if test="${com.next.Collateral.findByOpportunity(entry)?.specialFactors == '顶楼带阁楼'}">
                                            <span style="color:blue ">(含阁楼总价</span>
                                            <span style="color:blue ">${Math.round(com.next.Collateral.findByOpportunity(entry)?.unitPrice * com.next.Collateral.findByOpportunity(entry)?.atticArea / 20000)}万元)</span>
                                        </g:if>
                                    </g:if>
                                </span>
                            </g:if>
                            <g:if test="${com.next.Collateral.findByOpportunity(entry)?.status == 'Failed'}">
                                <label>评房结果</label>
                                <span class="colorRed">待确认</span>
                            </g:if>
                        </p>
                    </div>
                    <g:if test="${com.next.Collateral.findByOpportunity(entry)?.status == 'Completed'}">
                        <div class="bigBtn" style="margin: 12px 0 10px">
                            <g:link class="linkBtn weui_btn weui_btn_plain_default" controller="wechatOpportunity" params="[code: null, stage: null]" action="wxShow2" id="${entry.id}">立即报单，确保收益</g:link>
                        </div>
                        <h4 class="benefit-btn" id="benefit-btn">报单的好处？</h4>
                        <ul class="reportBill-benefit hide" id="reportBill-benefit">
                            <li class="flex-box">
                                <label class="flex1">享受保护：</label>
                                <span class="flex3">经纪人提交报单后，借款人的借款信息立即受保护；提交的信息越详细，保护期时间越长（最多15天）</span>
                            </li>
                            <li class="flex-box">
                                <label class="flex1">确保收益：</label>
                                <span class="flex3">保护期时间内，其他经纪人无法再次提交该借款人的借款信息，保障最先报单人的收益。</span>
                            </li>
                        </ul>
                    </g:if>
                </div>
            </g:if>
            <g:if test="${entry.stage.code == '03' || entry.stage.code == '04' || entry.stage.code == '16'}">
                <div class="recordItem">
                    <div class="orderTitle">
                        <label>提交时间</label>
                        <span>
                            <g:formatDate class="weui_input" date="${entry.createdDate}" format="yyyy-MM-dd HH:mm:ss"
                                          name="createdDate" autocomplete="off" readonly="true"></g:formatDate>
                        </span>
                        <g:link action="wxShow" id="${entry.id}">已报单</g:link>
                    </div>
                    <g:if test="${com.next.Collateral.findByOpportunity(entry) == null && entry?.loanAmount == 0}">
                        <div class="orderDetail">
                            <p>
                                <label>订单编号</label>
                                <span>${entry.serialNumber}</span>
                            </p>

                            <p>
                                <label>借款人</label>
                                <span>${entry.fullName}</span>
                            </p>

                            <p>
                                <label>婚姻状态</label>
                                <span>${entry.maritalStatus}</span>
                            </p>
                        </div>
                    </g:if>
                    <g:else>
                        <div class="orderDetail">
                            <p>
                                <label>房产地址</label>
                                <span>${com.next.Collateral.findByOpportunity(entry)?.projectName}</span>
                            </p>

                            <p>
                                <label>订单编号</label>
                                <span>${entry.serialNumber}</span>
                            </p>

                            <p>
                                <label>评房结果</label>
                                <span>${Math.round(entry?.loanAmount)}万元
                                    <g:if test="${com.next.Collateral.findByOpportunity(entry)?.atticArea > 0}">
                                        <g:if test="${com.next.Collateral.findByOpportunity(entry)?.specialFactors == '跃层'}">
                                            <span style="color:#CD001A ">(含跃层总价</span>
                                            <span style="color:#CD001A ">${Math.round(com.next.Collateral.findByOpportunity(entry)?.unitPrice * com.next.Collateral.findByOpportunity(entry)?.atticArea / 20000)}万元)</span>
                                        </g:if>
                                        <g:if test="${com.next.Collateral.findByOpportunity(entry)?.specialFactors == '顶楼带阁楼'}">
                                            <span style="color:#CD001A ">(含阁楼总价</span>
                                            <span style="color:#CD001A ">${Math.round(com.next.Collateral.findByOpportunity(entry)?.unitPrice * com.next.Collateral.findByOpportunity(entry)?.atticArea / 20000)}万元)</span>
                                        </g:if>
                                    </g:if>
                                </span>
                            </p>
                        </div>
                    </g:else>
                </div>
            </g:if>
            <g:if test="${entry.stage.code == '07' || entry.stage.code == '05' || entry.stage.code == '06'}">
                <div class="recordItem">
                    <div class="orderTitle">
                        <label>提交时间</label>
                        <span>
                            <g:formatDate class="weui_input" date="${entry.createdDate}" format="yyyy-MM-dd HH:mm:ss"
                                          name="createdDate" autocomplete="off" readonly="true"></g:formatDate>
                        </span>
                        <g:link action="wxShow" id="${entry.id}">已初审</g:link>
                    </div>

                    <div class="orderDetail">
                        <p>
                            <label>房产地址</label>
                            <span>${com.next.Collateral.findByOpportunity(entry)?.projectName}</span>
                        </p>

                        <p>
                            <label>订单编号</label>
                            <span>${entry.serialNumber}</span>
                        </p>

                        <p>
                            <label>初审结果</label>
                            <span class="result">最高可贷金额${entry.maximumAmountOfCredit}万元  ，所需材料：
                                <span style="color: #00a2d4" class="click-show">点击查看</span>
                            </span>
                        </p>
                    </div>

                    <div class="bring-data hide" style="padding:0 15px">
                        <div class="required-dataList">
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
                        <g:if test="${entry.lender?.level?.name == 'B'}">
                            <div class="addition-dataList">
                                <h4>附加材料：</h4>

                                <p>${entry.lender?.level?.description}</p>
                            </div>
                        </g:if>
                    </div>
                </div>
                </div>
            </g:if>
            <g:if test="${entry.stage.code == '08' || entry.stage.code == '09' || entry.stage.code == '10'}">
                <div class="recordItem">
                    <div class="orderTitle">
                        <label>提交时间</label>
                        <span>
                            <g:formatDate class="weui_input" date="${entry.createdDate}" format="yyyy-MM-dd HH:mm:ss"
                                          name="createdDate" autocomplete="off" readonly="true"></g:formatDate>
                        </span>
                        <g:link action="wxShow" id="${entry.id}">已审批</g:link>
                    </div>

                    <div class="orderDetail">
                        <p>
                            <label>房产地址</label>
                            <span>${com.next.Collateral.findByOpportunity(entry)?.projectName}</span>
                        </p>

                        <p>
                            <label>订单编号</label>
                            <span>${entry.serialNumber}</span>
                        </p>

                        <p>
                            <label>审批结果</label>
                            <span>放款金额${entry.actualAmountOfCredit}万元；贷款期限${entry.loanDuration}个月；月息${entry?.monthlyInterest}%；${entry?.interestPaymentMethod?.name}</span>
                        </p>
                    </div>
                </div>
            </g:if>
            <g:if test="${entry.stage.code == '11'}">
                <div class="recordItem">
                    <div class="orderTitle">
                        <label>提交时间</label>
                        <span>
                            <g:formatDate class="weui_input" date="${entry.createdDate}" format="yyyy-MM-dd HH:mm:ss"
                                          name="createdDate" autocomplete="off" readonly="true"></g:formatDate>
                        </span>
                        <g:link action="wxShow" id="${entry.id}">已返点</g:link>
                    </div>

                    <div class="orderDetail">
                        <p>
                            <label>房产地址</label>
                            <span>${com.next.Collateral.findByOpportunity(entry)?.projectName}</span>
                        </p>

                        <p>
                            <label>订单编号</label>
                            <span>${entry.serialNumber}</span>
                        </p>

                        <p>
                            <label>返点金额</label>
                            <span>${entry.commission}万元；${entry.commissionPaymentMethod?.name}</span>
                        </p>
                    </div>
                </div>
            </g:if>
        </g:if>
        <g:if test="${entry.status == 'Failed'}">
            <div class="recordItem">
                <div class="orderTitle">
                    <label>提交时间</label>
                    <span>
                        <g:formatDate class="weui_input" date="${entry.createdDate}" format="yyyy-MM-dd HH:mm:ss"
                                      name="createdDate" autocomplete="off" readonly="true"></g:formatDate>
                    </span>
                    <g:link class="recordFail" action="wxShow" id="${entry.id}" style="color:#CD001A">已失败</g:link>
                </div>

                <div class="orderDetail">
                    <p>
                        <label>房产地址</label>
                        <span>${com.next.Collateral.findByOpportunity(entry)?.projectName}</span>
                    </p>

                    <p>
                        <label>订单编号</label>
                        <span>${entry.serialNumber}</span>
                    </p>

                    <p>
                        <label>失败原因</label>
                        <span class="fail-reason">${entry?.causeOfFailure?.name}</span>
                    </p>
                </div>
            </div>
        </g:if>
    </g:if>
</g:each>
<g:if test="${opportunityInstanceCount == 0}">
    <div class="empty-info">
        <g:img class="icon-expression" dir="images" file="icon-expression2.png"/>
    </div>
    <g:if test="${stageStatus == "alreadyPF"}">
        <p class="warm-tip" style="margin-bottom: 4rem;">还没有“已评房”的订单，加油哦！</p>

        <div class="bigBtn">
            <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464de39cbfe33d14&redirect_uri=https%3A%2F%2Fs6f.zhongjiaxin.com%2FwechatOpportunity%2FwxCreate2Step1&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect" class="linkBtn weui_btn weui_btn_plain_default">免费评房</a>
            %{--<g:link action="wxCreate2Step1" controller="wechatOpportunity" class="linkBtn weui_btn weui_btn_plain_default">免费评房</g:link>--}%
        </div>
    </g:if>
    <g:if test="${stageStatus == "allItems"}">
        <p class="warm-tip">还没有订单信息，加油哦！</p>
    </g:if>
    <g:if test="${stageStatus == "waitSH"}">
        <p class="warm-tip">还没有“待审核”的订单，加油哦！</p>
    </g:if>
    <g:if test="${stageStatus == "alreadyCS"}">
        <p class="warm-tip">还没有“已初审”的订单，加油哦！</p>
    </g:if>
    <g:if test="${stageStatus == "alreadySP"}">
        <p class="warm-tip">还没有“已审批”的订单，加油哦！</p>
    </g:if>
    <g:if test="${stageStatus == "alreadyFD"}">
        <p class="warm-tip">还没有“已返点”的订单，加油哦！</p>
    </g:if>
    <g:if test="${stageStatus == "alreadySB"}">
        <p class="warm-tip">还没有“已失败”的订单</p>
    </g:if>
    </div>
</g:if>
</div>
<div class="hjwoo-hotline2">
    <a href="tel:${contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${contact?.city?.telephone}</span>
    </a>
</div>
</div>
<script>
    var benefit_btn = $(".benefit-btn");
    for (var i = 0; i < benefit_btn.length; i++) {
        benefit_btn.eq(i).hide();
        benefit_btn.eq(0).show();
    }
    //携带资料
    $(".click-show").click(function () {
        if ($(".click-show").text() == "点击查看") {
            $(this).parent().parent().parent().nextAll(".bring-data").show();
            $(".click-show").text("关闭");
        } else {
            $(this).parent().parent().parent().nextAll(".bring-data").hide();
            $(".click-show").text("点击查看");
        }
    });
    //报单的好处
    $("#benefit-btn").click(function (event) {
        $("#reportBill-benefit").toggleClass("hide")
        event.stopPropagation();
    });
    $(document).click(function () {
        if (!$("#reportBill-benefit").hasClass("hide")) {
            $("#reportBill-benefit").addClass("hide");
        }
    });
    //阻止冒泡
    $("#reportBill-benefit").click(function (event) {
        if (!$("#reportBill-benefit").hasClass("hide")) {
            event.stopPropagation();
        }
    });
</script>
</body>
</html>
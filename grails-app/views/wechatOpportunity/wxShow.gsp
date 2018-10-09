<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="wechat"/>
    <title>订单详情</title>
    <style>
    .workflow {
        position: relative;
        padding: 1rem 0;
    }

    .flowList {
        margin-left: 3rem;
    }

    .flowItem {
        height: 5rem;
        padding: 10px 10px;
        border-bottom: 1px solid #e0e3e8;
    }

    .flowItem h3 {
        font-size: 1.3rem;
        color: #AAA;
        margin-bottom: 0.4rem;
    }

    .flowItem h4 {
        font-size: 1rem;
        color: #AAA;
    }

    .siderSign {
        position: absolute;
        left: 1.2rem;
        top: 10px;
        z-index: 5;
    }

    .signList li {
        height: 5rem;
        line-height: 4.5rem;
    }

    .signList li:before {
        content: '';
        width: 0;
        border-left: 1px solid #B4AFAF;
        height: 5rem;
        display: inline-block;
        position: absolute;
        left: 8px;
        z-index: -1;
        margin-top: 2.5rem;
    }

    .signList li:last-child:before {
        height: 0;
    }

    .signList li span {
        display: inline-block;
        background: url('${resource(dir: "images", file: "icon-well.png")}') no-repeat;
        background-size: 100% 100%;
        width: 16px;
        height: 16px;
        border-radius: 50%;
    }

    .flowItem.active h3 {
        color: #000;
    }

    .flowItem.active h4 {
        color: #4A4A4A;
    }

    .flowItem.activeError h3, .flowItem.activeError h4 {
        color: #CD001A;
    }

    .signList li.active span {
        width: 24px;
        height: 24px;
        margin-left: -4px;
        background: url('${resource(dir: "images", file: "icon-yes.png")}') no-repeat;
        background-size: 100% 100%;
    }

    .signList li.activeError span {
        width: 24px;
        height: 24px;
        margin-left: -4px;
        background: url('${resource(dir: "images", file: "icon-no.png")}') no-repeat;
        background-size: 100% 100%;
    }

    .houseInfo {
        margin-top: 1.5rem;
    }

    .houseInfo h3 {
        font-size: 1.4rem;
    }

    .infoList {
        padding: .8rem 0;
        font-size: 1.2rem;
        color: #4A4A4A;
        background-color: #fff;
        position: relative;
    }

    .infoList li {
        padding: 0 1.2rem;
        font-size: 1.2rem;
        margin-bottom: 10px;
    }

    .infoList li:last-child {
        margin-bottom: 0;
        border-bottom: 1px solid #F0F0F1;
        padding-bottom: 4px;
    }

    .infoList li label {
        font-size: 1rem;
        color: #9B9B9B;
        margin-right: 1.2rem;
    }

    .infoList li span {
        font-size: 1.25rem;
        color: #4A4A4A;
    }

    .infoTitle {
        height: 3.5rem;
        line-height: 3.5rem;
        padding: 0 1.2rem;
        font-size: 1.4rem;
        color: #4A4A4A;
        background-color: #DEDEDE;
    }

    .infoList li.active {
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: flex-start;
        -webkit-align-items: flex-start;
        -ms-flex-align: flex-start;
        align-items: flex-start;
    }

    .infoList li.active label {
        -webkit-box-flex: 1;
        -webkit-flex: 1;
        -ms-flex: 1;
        flex: 1;
    }

    .infoList li.active span {
        -webkit-box-flex: 4;
        -webkit-flex: 4;
        -ms-flex: 4;
        flex: 4;
    }
    </style>
</head>

<body>
<div class="hide" id="stage">${this.opportunity.stage.code}</div>

<div class="hide" id="status">${this.opportunity.status}</div>
%{--<div class="hide" id="stage">07</div>--}%
%{--<div class="hide" id="status">Failed</div>--}%
<div class="container">
    <div class="bgcWhite workflow">
        <ul class="flowList">
            <li class="flowItem active">
                <h3 data-id="01">评房申请已提交</h3>
                <h4>极速评房，3秒出值</h4>
            </li>
            <li class="flowItem">
                <h3 data-id="02">评房已完成</h3>
                <h4>即刻报单，享受100%“保护期”</h4>
            </li>
            <li class="flowItem">
                <h3 data-id="03">报单申请已提交</h3>
                <h4>马不停蹄的审核中，请您稍候</h4>
            </li>
            <li class="flowItem">
                <h3 data-id="07">房产初审已完成</h3>
                <h4>若符合黄金屋审核标准，将约定面审时间</h4>
            </li>
            <li class="flowItem">
                <h3 data-id="08">审批已完成</h3>
                <h4>审批流程已完成，将约定下户时间</h4>
            </li>
            <li class="flowItem">
                <h3 data-id="09">抵押公证手续已完成</h3>
                <h4>公证、抵押登记已完成</h4>
            </li>
            <li class="flowItem">
                <h3 data-id="10">放款已完成</h3>
                <h4>见受理单后即时放款</h4>
            </li>
            <li class="flowItem">
                <h3 data-id="11">返点已完成</h3>
                <h4>订单返点已发放，请及时查收</h4>
            </li>
        </ul>

        <div class="siderSign">
            <ul class="signList" id="signList">
                <li data-id="01"><span></span></li>
                <li data-id="02"><span></span></li>
                <li data-id="03"><span></span></li>
                <li data-id="07"><span></span></li>
                <li data-id="08"><span></span></li>
                <li data-id="09"><span></span></li>
                <li data-id="10"><span></span></li>
                <li data-id="11"><span></span></li>
            </ul>
        </div>
    </div>
    <g:if test="${this.opportunity.stage.code == "08" || this.opportunity.stage.code == "09" || this.opportunity.stage.code == "10" || this.opportunity.stage.code == "11"}">
        <div class="houseInfo">
            <h3 class="infoTitle">审核结果</h3>
            <ul class="infoList">
                <g:if test="${this.opportunity.status != "Failed"}">
                    <li class="active">
                        <label>审核结果</label>
                        <span style="color: #CD001A">放款金额${this.opportunity.actualAmountOfCredit}万元；贷款期限${this.opportunity.loanDuration}个月。</span>
                    </li>
                    <li class="active">
                        <label>付息方式</label>
                        <span style="color: #CD001A">月息${this.opportunity?.monthlyInterest}%；${this.opportunity?.interestPaymentMethod?.name}</span>
                    </li>
                    <li class="active">
                        <label>返点点位</label>
                        <span style="color: #CD001A">${this.opportunity?.commissionRate}%；${this.opportunity?.commissionPaymentMethod?.name}</span>
                    </li>
                    <li class="active">
                        <label>返点金额</label>
                        <span style="color: #CD001A">${this.opportunity.commission}万元；${this.opportunity?.commissionPaymentMethod?.name}</span>
                    </li>
                </g:if>
                <g:if test="${this.opportunity.status == "Failed"}">
                    <g:if test="${this.opportunity.stage.code == "08"}">
                        <li class="active">
                            <label>失败原因</label>
                            <span class="fail-reason">${this.opportunity?.causeOfFailure?.name}</span>
                        </li>
                    </g:if>
                    <g:if test="${this.opportunity.stage.code != "08"}">
                        <li class="active">
                            <label>审核结果</label>
                            <span style="color: #CD001A">放款金额${this.opportunity.actualAmountOfCredit}万元；贷款期限${this.opportunity.loanDuration}个月。</span>
                        </li>
                        <li>
                            <label>可提返点</label>
                            <span style="color: #CD001A">${this.opportunity.commission}万元；${this.opportunity?.commissionPaymentMethod?.name}</span>
                        </li>
                        <li class="active">
                            <label>失败原因</label>
                            <span class="fail-reason">${this.opportunity?.causeOfFailure?.name}</span>
                        </li>
                    </g:if>
                </g:if>
            </ul>
        </div>
    </g:if>

    <g:if test="${this.opportunity.stage.code != "02" && this.opportunity.stage.code != "15"}">
        <div class="houseInfo">
            <h3 class="infoTitle">初审信息</h3>
            <ul class="infoList">
                <li>
                    <label>贷款金额</label>
                    <span>${this.opportunity.requestedAmount}</span>万元
                </li>
                <li>
                    <label>贷款期限</label>
                    <span>${this.opportunity.loanDuration}个月</span>
                </li>
                <li>
                    <label>借款人姓名</label>
                    <span>${this.opportunity.fullName}</span>
                </li>
                <li>
                    <label>借款人婚姻状态</label>
                    <span>${this.opportunity.maritalStatus}</span>
                </li>

                <g:if test="${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人')) != null}">
                    <li>
                        <label>抵押人姓名</label>
                        <span>${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人')).contact?.fullName}</span>
                    </li>
                    <li>
                        <label>抵押人婚姻状态</label>
                        <span>${com.next.OpportunityContact.findByOpportunityAndType(this.opportunity, com.next.OpportunityContactType.findByName('抵押人')).contact?.maritalStatus}</span>
                    </li>
                </g:if>
                <li>
                    <label>抵押类型</label>
                    <span>${this.opportunity?.mortgageType?.name}</span>
                </li>
                <g:if test="${this.opportunity.stage.code == "03" || this.opportunity.stage.code == "04" || this.opportunity.stage.code == "16" || this.opportunity.stage.code == "05" || this.opportunity.stage.code == "06" || this.opportunity.stage.code == "07"}">
                    <g:if test="${this.opportunity.status == "Failed"}">
                        <li class="active">
                            <label>失败原因</label>
                            <span class="fail-reason">${this.opportunity?.causeOfFailure?.name}</span>
                        </li>
                    </g:if>
                </g:if>
                <g:if test="${this.opportunity.stage.code == "07" && this.opportunity.status != "Failed"}">
                    <li class="active">
                        <label>初审结果</label>
                        <span class="result" style="color: #CD001A">最高可贷金额${this.opportunity.maximumAmountOfCredit}万元
                        ，所需材料： <span style="color: #00a2d4" class="click-show">点击查看</span>
                        </span>
                    </li>
                    <li class="bring-data hide">
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
                        <g:if test="${this.opportunity.lender?.level?.name == 'B'}">
                            <div class="addition-dataList">
                                <h4>附加材料：</h4>

                                <p>${this.opportunity.lender?.level?.description}</p>
                            </div>
                        </g:if>
                    </li>
                    <li class="active">
                        <label>免责声明</label>
                        <span>如拟抵押房产存在银行贷款，请自动扣除贷款余额。</span>
                    </li>
                </g:if>

                <g:if test="${this.opportunity.stage.code != "03" && this.opportunity.stage.code != "04" && this.opportunity.stage.code != "16" && this.opportunity.stage.code != "07"}">
                    <li class="active">
                        <label>初审结果</label>
                        <span class="result"
                              style="color: #CD001A">最高可贷金额${this.opportunity.maximumAmountOfCredit}万元，所需材料：
                            <span style="color: #00a2d4" class="click-show">点击查看</span>
                        </span>
                    </li>
                    <li class="bring-data hide">
                        <div class="required-dataList">
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
                            <div class="addition-dataList">
                                <h4>附加材料：</h4>

                                <p>${this.opportunity.lender?.level?.description}</p>
                            </div>
                        </g:if>
                    </li>
                    <li class="active">
                        <label>免责声明</label>
                        <span>如拟抵押房产存在银行贷款，请自动扣除贷款余额。</span>
                    </li>
                </g:if>
            </ul>
        </div>
    </g:if>

    <div class="houseInfo">
        <h3 class="infoTitle">评房信息</h3>
        <ul class="infoList">
            <li>
                <label>申请时间</label>
                <span>
                    <g:formatDate class="weui_input" date="${this.opportunity.createdDate}" format="yyyy-MM-dd HH:mm:ss"
                                  name="createdDate" autocomplete="off" readonly="true"></g:formatDate>
                </span>
            </li>
            <li>
                <label>所在城市</label>
                <span>${this.collateral?.city?.name}</span>
            </li>
            <li>
                <label>房屋信息</label>
                <span>${this.collateral?.projectName}；</span>
                <span>${this.collateral?.building}号楼；</span>
                <span>${this.collateral?.unit}单元</span>
            </li>
            <li>
                <label>居&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;室</label>
                <span>${this.collateral?.roomNumber}</span>居室
            </li>
            <li>
                <label>朝&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向</label>
                <span>${this.collateral?.orientation}</span>
            </li>
            <li>
                <label>建筑面积</label>
                <span>${this.collateral?.area}</span>
                m<sup>2</sup>
            </li>
            <li>
                <label>物业种类</label>
                <span>${this.collateral?.houseType}</span>
            </li>
            <li>
                <label>特殊因素</label>
                <span>${this.collateral?.specialFactors}</span>
            </li>
            <li>
                <label>订单编号</label>
                <span>${this.opportunity.serialNumber}</span>
            </li>
            <g:if test="${this.opportunity.stage.code in ['02', '15'] && this.opportunity.status == "Failed"}">
                <li class="active">
                    <label>失败原因</label>
                    <span class="fail-reason">${this.opportunity?.causeOfFailure?.name}</span>
                </li>
            </g:if>
            <g:if test="${this.opportunity.stage.code in ['02', '15'] && this.opportunity.status != "Failed"}">
                <li>
                    <g:if test="${com.next.Collateral.findByOpportunity(opportunity)?.status == 'Completed'}">
                        <label>评房结果</label>
                        <span style="color:#CD001A ">${Math.round(this.opportunity?.loanAmount)}万元</span>
                        <g:if test="${this.collateral?.atticArea > 0}">
                            <g:if test="${this.collateral?.specialFactors == '跃层'}">
                                <span style="color:#CD001A ">(含跃层总价</span>
                                <span style="color:#CD001A ">${Math.round(this.collateral?.unitPrice * this.collateral?.atticArea / 20000)}万元)</span>
                            </g:if>
                            <g:if test="${this.collateral?.specialFactors == '顶楼带阁楼'}">
                                <span style="color:#CD001A ">(含阁楼总价</span>
                                <span style="color:#CD001A ">${Math.round(this.collateral?.unitPrice * this.collateral?.atticArea / 20000)}万元)</span>
                            </g:if>
                        </g:if>
                    </g:if>
                    <g:if test="${com.next.Collateral.findByOpportunity(opportunity)?.status == 'Pending'}">
                        <label>参考评房结果</label>
                        <span style="color:blue">${Math.round(this.opportunity?.loanAmount)}万元</span>
                        <g:if test="${this.collateral?.atticArea > 0}">
                            <g:if test="${this.collateral?.specialFactors == '跃层'}">
                                <span style="color:blue">(含跃层总价</span>
                                <span style="color:blue">${Math.round(this.collateral?.unitPrice * this.collateral?.atticArea / 20000)}万元)</span>
                            </g:if>
                            <g:if test="${this.collateral?.specialFactors == '顶楼带阁楼'}">
                                <span style="color:blue">(含阁楼总价</span>
                                <span style="color:blue">${Math.round(this.collateral?.unitPrice * this.collateral?.atticArea / 20000)}万元)</span>
                            </g:if>
                        </g:if>
                    </g:if>
                    <g:if test="${com.next.Collateral.findByOpportunity(opportunity)?.status == 'Failed'}">
                        <label>评房结果</label>
                        <span style="color:#CD001A ">待确认</span>
                    </g:if>
                </li>
                <g:if test="${com.next.Collateral.findByOpportunity(opportunity)?.status == 'Completed' && com.next.Collateral.findByOpportunity(opportunity)?.reasonOfPriceAdjustment == null}">
                    <li>
                       <g:link class="colorRed" action="wxSuggest" id="${this.collateral?.id}">对评房结果不满意？请点此反馈。</g:link>
                   </li>
                </g:if>
            </g:if>
            <g:if test="${this.opportunity.stage.code != "15" && this.opportunity.stage.code != "02"}">
                <li>
                    <label>评房结果</label>
                    <span style="color:#CD001A ">${Math.round(this.opportunity?.loanAmount)}万元</span>
                </li>
            </g:if>
        </ul>
    </div>
</div>
<div class="hjwoo-hotline2">
    <a href="tel:${this.opportunity?.contact?.city?.telephone}" class="hotline">
        <g:img class="hotlineIcon" dir="images" file="hotline-icon.png"/>
        <span class="city-hotline">${this.opportunity?.contact?.city?.telephone}</span>
    </a>
</div>
<script>
    $(function () {
        var pageStage = $("#stage").text();
        var status = $("#status").text();
        if (pageStage == "15") {
            var pageStage = "01";
        }
        if (pageStage == "04" || pageStage == "16") {
            var pageStage = "03";
        }
        if (pageStage == "05" || pageStage == "06") {
            var pageStage = "07";
        }
        var stage = $(".flowItem h3");
        var li = $("#signList li");
        for (var i = 0; i < stage.length; i++) {
            var dataId1 = stage.eq(i).attr("data-id");
            if (status == "Failed" && dataId1 == pageStage) {
                stage.eq(i).parent().addClass("activeError").prevAll().addClass("active");

                for (var i = 0; i < li.length; i++) {
                    var dataId2 = li.eq(i).attr("data-id");
                    if (dataId1 == dataId2) {
                        li.eq(i).addClass("activeError").prevAll().addClass("active");
                    }

                }
            }

            if (dataId1 == pageStage) {
                stage.eq(i).parent().addClass("active").prevAll().addClass("active");
                for (var i = 0; i < li.length; i++) {
                    var dataId2 = li.eq(i).attr("data-id");
                    if (dataId1 == dataId2) {
                        li.eq(i).addClass("active").prevAll().addClass("active");
                    }

                }
            }
        }
    });

    $(".click-show").click(function () {
        if ($(".click-show").text() == "点击查看") {
            $(this).parent().parent().nextAll(".bring-data").show();
            $(".click-show").text("关闭");
        } else {
            $(this).parent().parent().nextAll(".bring-data").hide();
            $(".click-show").text("点击查看");
        }
    });

</script>
</body>
</html>
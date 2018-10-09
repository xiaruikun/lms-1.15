<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>订单历史记录</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right m-t-lg">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="opportunityTeam" action="index">订单管理</g:link></li>
                    <li><g:link controller="opportunityTeam" action="show"
                                id="${com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber).id}">信息查看</g:link></li>
                    <li class="active">
                        <span>历史信息查看</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                订单号: ${this.opportunity.serialNumber}
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                订单基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12"><h3>${this.opportunity.serialNumber}</h3></div>

                    <div class="col-md-12"><p></p></div>

                    <div class="col-md-1"><strong><span class="glyphicon glyphicon-user"
                                                        aria-hidden="true"></span> ${this.opportunity?.fullName}
                    </strong></div>
                    <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                        <div class="col-md-1"><span
                                class="fa fa-chain"></span>${this.opportunity?.protectionEndTime - this.opportunity?.protectionStartTime}天
                        </div>
                    </g:if>
                    <div class="col-md-2"><strong>${this.opportunity?.stage?.name}</strong></div>
                    <g:if test="${this.opportunity?.status == 'Failed'}"><span
                            class="label label-danger pull-right">失败：${this.opportunity?.memo}</span></g:if>
                    <g:elseif test="${this.opportunity?.status == 'Completed'}"><span
                            class="label label-success pull-right">订单结果：成功</span></g:elseif>
                    <g:else><span class="label label-info pull-right">订单结果：进行中</span></g:else>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-2 border-right" style=""><div
                            class="contact-stat"><span>贷款金额:</span> <strong><span class="fa fa-rmb"
                                                                                  style="display: inline-block; padding-right: 3px;"></span>${this.opportunity?.requestedAmount}万元
                        </strong></div></div>

                    <div class="col-md-2 border-right" style=""><div
                            class="contact-stat"><span>贷款期限:</span> <strong>${this.opportunity?.loanDuration}月</strong>
                    </div></div>

                    <div class="col-md-2 border-right" style=""><div
                            class="contact-stat"><span>抵押类型:</span> <strong>${this.opportunity?.mortgageType?.name}</strong>
                    </div></div>

                    <div class="col-md-2 border-right" style=""><div
                            class="contact-stat"><span>一抵金额:</span> <strong><span class="fa fa-rmb"
                                                                                  style="display: inline-block; padding-right: 3px;"></span>${this.opportunity?.firstMortgageAmount}万元
                        </strong></div></div>

                    <div class="col-md-2 border-right" style=""><div
                            class="contact-stat"><span>二抵金额:</span><strong><span class="fa fa-rmb"
                                                                                 style="display: inline-block; padding-right: 3px;"></span>${this.opportunity?.secondMortgageAmount}万元
                        </strong></div></div>

                    <div class="col-md-2" style=""><div
                            class="contact-stat"><span>可提返点:</span> <strong>${this.opportunity.commission}万元</strong>
                    </div></div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="panel-footer contact-footer">
            <div class="row">
                <div class="hpanel hgreen">
                    <div class="panel-heading">
                        报单人信息
                    </div>

                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-3 border-right" style="">
                                <div class="contact-stat"><span>姓名:</span></div>
                            </div>

                            <div class="col-md-3 border-right" style="">
                                <div class="contact-stat"><span>${this.opportunity?.contact?.fullName}</span></div>
                            </div>

                            <div class="col-md-3 border-left border-right" style="">
                                <div class="contact-stat"><span>手机号:</span>
                                </div>
                            </div>

                            <div class="col-md-3" style="">
                                <div class="contact-stat"><span class="cellphoneFormat">${this.opportunity?.contact?.cellphone}</span>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3 border-right" style="">
                                <div class="contact-stat"><span>所属公司:</span></div>
                            </div>

                            <div class="col-md-3 border-right" style="">
                                <div class="contact-stat"><span>${this.opportunity?.contact?.account?.name}</span></div>
                            </div>

                            <div class="col-md-3 border-left border-right" style="">
                                <div class="contact-stat"><span>外部唯一ID:</span>
                                </div>
                            </div>

                            <div class="col-md-3" style="">
                                <div class="contact-stat"><span>${this.opportunity?.contact?.externalId}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                房产信息
            </div>

            <div class="panel-body">
                <div class="row">
                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>所属城市:</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>${this.opportunity?.city?.name}</span></div>
                    </div>

                    <div class="col-md-3 border-left border-right" style="">
                        <div class="contact-stat"><span>小区地址:</span>
                        </div>
                    </div>

                    <div class="col-md-3" style="">
                        <div class="contact-stat"><span>${this.opportunity?.address}</span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>楼栋信息:</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>${this.opportunity?.building}</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>楼层:</span></div>
                    </div>

                    <div class="col-md-3" style="">
                        <div class="contact-stat"><span>${this.opportunity?.floor}层</span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>总楼层:</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>${this.opportunity?.totalFloor}层</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>户号:</span></div>
                    </div>

                    <div class="col-md-3" style="">
                        <div class="contact-stat"><span>${this.opportunity?.roomNumber}</span></div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>居室:</span></div>
                    </div>

                    <div class="col-md-3" style="">
                        <div class="contact-stat"><span>${this.opportunity?.numberOfLivingRoom}</span></div>
                    </div>

                    <div class="col-md-3 border-left border-right" style="">
                        <div class="contact-stat"><span>朝向:</span>
                        </div>
                    </div>

                    <div class="col-md-3 " style="">
                        <div class="contact-stat"><span>${this.opportunity?.orientation}</span></div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>建筑面积:</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>${this.opportunity?.area}平</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>物业类型:</span></div>
                    </div>

                    <div class="col-md-3" style="">
                        <div class="contact-stat"><span>${this.opportunity?.houseType?.name}</span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>特殊因素:</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>${this.opportunity?.specialFactors?.name}</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>申请时间:</span></div>
                    </div>

                    <div class="col-md-3" style="">
                        <div class="contact-stat"><span><g:formatDate
                                format="yyyy-MM-dd HH:mm:ss" date="${this.opportunity?.createdDate}"/></span></div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>抵押类型:</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>${this.opportunity?.mortgageType?.name}</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>一抵金额:</span></div>
                    </div>

                    <div class="col-md-3" style="">
                        <div class="contact-stat"><span>${this.opportunity?.firstMortgageAmount}</span></div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>二抵金额:</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>${this.opportunity?.secondMortgageAmount}</span></div>
                    </div>

                    <div class="col-md-3 border-right" style="">
                        <div class="contact-stat"><span>一抵性质:</span></div>
                    </div>

                    <div class="col-md-3" style="">
                        <div class="contact-stat"><span>${this.opportunity?.typeOfFirstMortgage?.name}</span></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="" style="padding:0px">
            <div class="panel-heading" style="font-weight: 600">报单信息</div>

            <div class="col-lg-3">
                <div class="hpanel hblue">
                    <div class="panel-body">
                        <span class="label label-info pull-right">借款人</span>

                        <div class="text-muted font-bold m-b-xs"><h4>${this.opportunity?.fullName}</h4></div>

                        <p style="padding-top:5px;padding-bottom:5px" class="cellphoneFormat"><span
                                class="fa fa-phone"></span> ${this.opportunity?.cellphone}</p>

                        <p style="padding-top:5px"><span class="fa fa-credit-card"></span> ${this.opportunity?.idNumber}</p>

                        <p style="padding-top:5px;padding-bottom:5px"><span
                                class="fa fa-heart"></span> ${this.opportunity?.maritalStatus}</p>
                    </div>
                </div>
            </div>

            <div class="col-lg-3">
                <div class="hpanel hblue">
                    <div class="panel-body">
                        <span class="label label-info pull-right">借款人配偶</span>

                        <div class="text-muted font-bold m-b-xs"><h4>${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName("借款人配偶"))?.contact?.fullName}</h4>
                        </div>

                        <p style="padding-top:5px;padding-bottom:5px"><span
                                class="fa fa-phone"></span> ${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName("借款人配偶"))?.contact?.cellphone}
                        </p>

                        <p style="padding-top:5px"><span
                                class="fa fa-credit-card"></span> ${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName("借款人配偶"))?.contact?.idNumber}
                        </p>
                    </div>

                </div>
            </div>

            <div class="col-lg-3">
                <div class="hpanel hblue">
                    <div class="panel-body">
                        <span class="label label-info pull-right">抵押人</span>

                        <div class="text-muted font-bold m-b-xs"><h4>${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName("抵押人"))?.contact?.fullName}</h4>
                        </div>

                        <p style="padding-top:5px;padding-bottom:5px"><span
                                class="fa fa-phone"></span> ${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName("抵押人"))?.contact?.cellphone}
                        </p>

                        <p style="padding-top:5px"><span
                                class="fa fa-credit-card"></span> ${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName("抵押人"))?.contact?.idNumber}
                        </p>

                        <p style="padding-top:5px;padding-bottom:5px"><span
                                class="fa fa-heart"></span> ${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName('抵押人'))?.contact?.maritalStatus}
                        </p>
                    </div>

                </div>
            </div>

            <div class="col-lg-3">
                <div class="hpanel hblue">
                    <div class="panel-body">
                        <span class="label label-info pull-right">抵押人配偶</span>

                        <div class="text-muted font-bold m-b-xs"><h4>${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName("抵押人配偶"))?.contact?.fullName}</h4>
                        </div>

                        <p style="padding-top:5px;padding-bottom:5px"><span
                                class="fa fa-phone"></span> ${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName("抵押人配偶"))?.contact?.cellphone}
                        </p>

                        <p style="padding-top:5px"><span
                                class="fa fa-credit-card"></span> ${com.next.OpportunityContact.findByOpportunityAndType(com.next.Opportunity.findBySerialNumber(this.opportunity?.serialNumber), com.next.OpportunityContactType.findByName("抵押人配偶"))?.contact?.idNumber}
                        </p>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
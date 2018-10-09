<!DOCTYPE html>
<html lang="en">

<head>
    <meta name="layout" content="main2"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>（北京-上海）主单推进3.0-24</title>
    <style>
    .field label {
        font-weight: normal;
    }

    .input-group[class*="col-"] {
        float: left;
        padding-right: 15px;
        padding-left: 15px;
    }

    .borderActive {
        border-width: 1px 0 0 0 !important;
        border-color: #e4e5e7 !important;
        padding: 10px 0 !important;
    }

    .boreder-none {
        border: none;
    }

    .second-title {
        font-weight: 600;
        margin-left: 30px;
    }

    .checkboxGroup {
        display: inline-block;
        padding: 7px 10px;
    }

    .text-warning {
        color: #ffb606;
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
                        class="text-warning">失败</span>】</span></g:if>
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

        <g:form action="ajaxUpdate1" name="myForm" class="form_1" id="${this.opportunity?.id}">
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
                            <g:textField class="form-control" name="actualAmountOfCredit"
                                         value="${this.opportunity?.maximumAmountOfCredit}"/>
                        </g:else>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="actualLoanDuration">实际贷款期限（月）</label>

                        <g:textField class="form-control" name="actualLoanDuration"
                                     value="${this.opportunity?.actualLoanDuration}"/>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="product">产品类型</label>
                        <g:select class="form-control" optionKey="id" optionValue="name"
                                  name="product"
                                  value="${this.opportunity?.product?.id}"
                                  from="${com.next.Product.findAllByActive(true)}"/>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="interestPaymentMethod">付息方式</label>

                        <g:select class="form-control interestPaymentMethod" optionKey="id" optionValue="name"
                                  name="interestPaymentMethod"
                                  value="${this.opportunity?.interestPaymentMethod?.id}"
                                  from="${com.next.InterestPaymentMethod.findAllByNameInList(['下扣息','等额收息',this.opportunity?.interestPaymentMethod?.name])}"/>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="level">客户级别</label>

                        <div class="form-control" style="border:none;padding-left: 0">
                            <g:select class="form-control" name="lender.level.id" id="level"
                                      value="${this.opportunity?.lender?.level?.id}" noSelection="${['null': '-请选择-']}"
                                      from="${com.next.ContactLevel.findAllByActive(true)}" optionKey="id"
                                      optionValue="description"></g:select>

                        </div>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="mortgageCertificateType">抵押类凭证</label>

                        <g:select class="form-control" name="mortgageCertificateType"
                                  value="${this.opportunity?.mortgageCertificateType?.id}"
                                  noSelection="${['null': '无']}"
                                  from="${com.next.MortgageCertificateType.list()}" optionKey="id"
                                  optionValue="name"></g:select>
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
                        <div class="panel-heading font-bold">
                            <div class="panel-tools">
                                <button class="btn btn-xs btn-info" type="submit">
                                    <i class="fa fa-check"></i> 保存
                                </button>
                            </div>
                            第<span class="order2"></span>套房产
                        </div>

                        <div class="panel-body borderActive">
                            <div class="form-group col-md-4">
                                <label>在我司抵押类型</label>

                                <div class="form-control boreder-none"
                                     id="mortgageType">${it?.mortgageType?.name}</div>
                            </div>

                            <div class="form-group col-md-4">
                                <label for="typeOfFirstMortgage">一抵性质</label>
                                <g:select id="typeOfFirstMortgage" name='typeOfFirstMortgage.id'
                                             value="${it?.typeOfFirstMortgage?.id}" noSelection="['': '无']"
                                             from="${com.next.TypeOfFirstMortgage.list()}" optionKey="id"
                                             optionValue="name" class="form-control"></g:select>


                            </div>

                            <div class="form-group col-md-4">
                                <label for="loanToValue">抵押率（%）</label>
                                    <input class="form-control" name="loanToValue" type="text"
                                   value="<g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                            minFractionDigits="2"/>">
                            </div>

                            <div class="form-group col-md-4">
                                <label for="firstMortgageAmount">一抵金额（万元）</label>
                                <input class="form-control" name="firstMortgageAmount" type="text"
                                       id="firstMortgageAmount"
                                       value="${it?.firstMortgageAmount}">
                            </div>

                            <div class="form-group col-md-4 hide">
                                <label for="secondMortgageAmount">二抵金额（万元）</label>
                                <input class="form-control" name="secondMortgageAmount" type="text"
                                       id="secondMortgageAmount"
                                       value="${it?.secondMortgageAmount}">
                            </div>

                            <div class="form-group col-md-4">
                                <label for="region.id">所在区域</label>
                                <g:select id="region" name='region.id' noSelection="${['': '未选择']}"
                                          value="${it?.region?.id}" from='${com.next.CollateralRegion.list()}'
                                          optionKey="id" optionValue="name" class="form-control"></g:select>

                            </div>

                            <div class="form-group col-md-4">
                                <label for="buildTime">建成时间</label>

                                <div>
                                    <g:datePicker name="buildTime" value="${it?.buildTime}" precision="year"/>
                                </div>
                            </div>

                        </div>
                    </g:form>
                </g:each>
            </div>
        </div>
    </div>

    <div class="row">
        <h5 class="font-bold">第三步：完善风险调查报告</h5>

        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                抵押物情况
            </div>

            <div class="panel-body p-xs" style="border-top:2px solid #ffb606">
                <g:each in="${this.collaterals}">
                    <g:form controller="collateral" action="ajaxUpdate" id="${it?.id}" class="form_3">
                        <div class="panel-heading second-title">
                            <div class="panel-tools">
                                <button class="form3Btn btn btn-xs btn-info" type="button">
                                    <i class="fa fa-check"></i> 保存
                                </button>
                            </div>
                            第 <span class="order3"></span> 套抵押物描述
                        </div>

                        <div class="panel-body form-horizontal borderActive">
                            <div class="form-group">
                                <label class="col-md-2 control-label">地址：</label>

                                <div class="col-md-3">
                                    <p class="form-control-static">${it?.address}</p>
                                </div>
                                <label class="col-md-2 control-label">小区名称：</label>

                                <div class="col-md-3">
                                    <p class="form-control-static">${it?.projectName}</p>
                                </div>

                            </div>

                            <div class="form-group">

                                <label class="col-md-2 control-label">建筑面积（m<sup>2</sup>）：</label>

                                <div class="col-md-3">
                                    <p class="form-control-static">${it?.area}</p>
                                </div>
                                <label class="col-md-2 control-label">房龄（年）：</label>

                                <div class="col-md-3">
                                    <span class="cont pl12">
                                        <g:if test="${it?.buildTime}">
                                            ${new Date().format("yyyy").toInteger().minus(it?.buildTime.format("yyyy").toInteger())}
                                        </g:if>
                                        <g:else>
                                            ${it?.buildTime}
                                        </g:else>
                                    </span>
                                </div>
                            </div>

                            <div class="form-group">

                                <label class="col-md-2 control-label">房屋性质：</label>

                                <div class="col-md-3">
                                    <p class="form-control-static">${it?.houseType}</p>
                                </div>
                                <label class="col-md-2 control-label">规划用途：</label>

                                <div class="col-md-3">
                                    <p class="form-control-static">${it?.projectType?.name}</p>
                                </div>

                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">房屋使用状态：</label>

                                <div class="col-md-3">
                                    <g:select class="form-control" name="houseUsageStats" noSelection="${['': '未选择']}"
                                              from="${["自住", "空置", "出借", "出租"]}"
                                              value="${it?.houseUsageStats}"/>
                                </div>
                                <label class="col-md-2 control-label">审批时抵押状态：</label>

                                <div class="col-md-3">
                                    <g:select class="form-control" name="mortgageStatus" noSelection="${['': '未选择']}"
                                              from="${["有", "无"]}"
                                              value="${it?.mortgageStatus}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label text-warning">其他补充：</label>

                                <div class="col-md-8">
                                    <textarea rows="3" class="form-control" name="additionalComment"
                                              placeholder="非必填">${it?.additionalComment}</textarea>
                                </div>

                            </div>
                        </div>
                    </g:form>
                </g:each>
                <g:each in="${this.collaterals}">
                    <g:form controller="collateral" action="ajaxUpdate" id="${it?.id}" class="form_4">
                        <div class="panel-heading second-title">
                            <div class="panel-tools">
                                <button class="form4Btn btn btn-xs btn-info" type="button">
                                    <i class="fa fa-check"></i> 保存
                                </button>
                            </div>
                            第 <span class="order4"></span> 套抵押物权属
                        </div>

                        <div class="panel-body form-horizontal borderActive">
                            <div class="form-group">
                                <label class="col-md-2 control-label text-warning">抵押物权属：</label>

                                <div class="col-md-8">
                                    <g:textArea rows="3" class="form-control" name="mortgageProperty"
                                                required="required"
                                                value="${it?.mortgageProperty}"/>
                                </div>

                            </div>

                        </div>
                    </g:form>
                </g:each>
            </div>
        </div>

        <div class="hpanel hred">

            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                借款人资质小结
            </div>

            <div class="panel-body p-xs">
                <g:each in="${opportunityContacts}">
                    <g:form controller="opportunityContact" action="ajaxUpdate" id="${it?.id}" class="form_5">
                        <div class="panel-heading second-title">
                            <div class="panel-tools">
                                <button class="form5Btn btn btn-xs btn-info" type="button">
                                    <i class="fa fa-check"></i> 保存
                                </button>
                            </div>
                            ${it?.type?.name}
                        </div>

                        <div class="panel-body form-horizontal borderActive">
                            <div class="form-group">
                                <label class="col-md-2 control-label">联系人：</label>

                                <div class="col-md-3">
                                    <p class="form-control-static">${it?.contact?.fullName}</p>
                                </div>
                                <label class="col-md-2 control-label">年龄（岁）：</label>

                                <div class="col-md-3">
                                    <p class="form-control-static">
                                        <g:if test="${it?.contact?.idNumber}">
                                            ${new Date().format("yyyy").toInteger().minus(it?.contact?.idNumber[6..9].toInteger())}
                                        </g:if>
                                        <g:else>
                                            ${it?.contact?.idNumber}
                                        </g:else>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">职业：</label>

                                <div class="col-md-3">
                                    <g:select class="form-control" name="contact.profession"
                                              value="${it?.contact?.profession?.id}"
                                              from="${com.next.ContactProfession.list()}" optionKey="id"
                                              optionValue="name"></g:select>

                                </div>
                                <label class="col-md-2 control-label">年收入：</label>

                                <div class="col-md-3">
                                    <div class="input-group">
                                        <input class="form-control" name="contact.income" value="${it?.contact?.income}"
                                               type="number">
                                        <span class="input-group-addon">万元</span>
                                    </div>

                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">联系人婚姻状况：</label>

                                <div class="col-md-3">
                                    <g:select class="form-control" name="contact.maritalStatus"
                                              value="${it?.contact?.maritalStatus}"
                                              from="${["未婚", "已婚", "再婚", "离异", "丧偶"]}"/>
                                </div>
                                <label class="col-md-2 control-label">是否为抵押人：</label>

                                <div class="col-md-3">
                                    <g:select class="form-control" name="contact.propertyOwner"
                                              value="${it?.contact?.propertyOwner}" noSelection="${['': '未选择']}"
                                              from="${["是", "否"]}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">关联关系：</label>

                                <div class="col-md-3">
                                    <g:select class="form-control" name="connectedType"
                                              value="${it?.connectedType?.id}"
                                              noSelection="${['': '无']}"
                                              from="${com.next.OpportunityContactType.list()}" optionKey="id"
                                              optionValue="name"></g:select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label text-warning">其他说明：</label>

                                <div class="col-md-8">
                                    <textarea class="form-control" name="contact.personalProperty"
                                              rows="3" placeholder="非必填项">${it?.contact?.personalProperty}</textarea>
                                </div>

                            </div>
                        </div>

                    </g:form>
                </g:each>

            </div>
        </div>

        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                征信小结
            </div>

            <div class="panel-body p-xs" style="border-top:2px solid #ffb606">
                <g:each in="${opportunityContacts}">
                    <g:form controller="opportunityContact" action="ajaxUpdate" id="${it?.id}" class="form_6">
                        <div class="panel-heading second-title">
                            <div class="panel-tools">
                                <button class="form6Btn btn btn-xs btn-info" type="button">
                                    <i class="fa fa-check"></i> 保存
                                </button>
                            </div>
                            ${it?.type?.name}：${it?.contact?.fullName}

                        </div>

                        <div class="panel-body form-horizontal borderActive">
                            <div class="form-group">
                                <label class="col-md-3 control-label">近24个月内逾期次数：</label>

                                <div class="col-md-3">
                                    <g:textField class="form-control" type="text"
                                                 value="${it?.contact?.overdueRecent}"
                                                 name="contact.overdueRecent"/>
                                </div>
                                <label class="col-md-3 control-label">当前总贷款余额：</label>

                                <div class="col-md-3 input-group">
                                    <g:field type="number" class="form-control"
                                                 value="${it?.contact?.totalLoanBalance}"
                                                 name="contact.totalLoanBalance"/>
                                    <span class="input-group-addon">万元</span>
                                </div>

                            </div>

                            <div class="form-group">
                                  <label class="col-md-3 control-label">当前逾期金额：</label>

                                <div class="col-md-3 input-group">
                                    <g:field type="number" class="form-control"
                                                 value="${it?.contact?.currentOverdueAmount}"
                                                 name="contact.currentOverdueAmount"/>
                                    <span class="input-group-addon">万元</span>
                                </div>
                                <label class="col-md-3 control-label">对外担保情况：</label>

                                <div class="col-md-3">
                                    <g:select class="form-control contactGuaranteeStatus" name="contact.guaranteeStatus"
                                              noSelection="['': '-请选择-']"
                                              value="${it?.contact?.guaranteeStatus}"
                                              from="${["有", "无"]}"/>
                                </div>

                            </div>

                            <div class="contactGuaranteeStatusInfo">
                                <div class="form-group">

                                <label class="col-md-3 control-label">对外担保类型：</label>

                                <div class="col-md-3 input-group">
                                    <g:select class="form-control contactGuaranteeType" name="contact.guaranteeType"
                                              noSelection="['': '-请选择-']"
                                              value="${it?.contact?.guaranteeType}"
                                              from="${["信用卡担保", "贷款担保"]}"/>
                                </div>
                                <label class="col-md-3 control-label">对外担保余额：</label>

                                <div class="col-md-3 input-group">
                                    <g:field type="number" class="form-control contactGuaranteeBalance"
                                                 value="${it?.contact?.guaranteeBalance}"
                                                 name="contact.guaranteeBalance"/>
                                    <span class="input-group-addon">万元</span>
                                </div>

                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">被担保人与借款人关系：</label>

                                <div class="col-md-3 input-group">
                                    <g:if test="${it?.contact?.relationOfGuarantor}">
                                        <g:textField class="form-control contactRelationOfGuarantor" type="text"
                                                     value="${it?.contact?.relationOfGuarantor}"
                                                     name="contact.relationOfGuarantor"/>
                                    </g:if>
                                    <g:else>
                                        <g:textField class="form-control contactRelationOfGuarantor" type="text"
                                                     value="无"
                                                     name="contact.relationOfGuarantor"/>
                                    </g:else>
                                </div>

                                <label class="col-md-3 control-label">对外担保贷款评级：</label>

                                <div class="col-md-3 input-group">
                                    <g:select class="form-control contactGuaranteeState" name="contact.guaranteeState"
                                              noSelection="['': '-请选择-']"
                                              value="${it?.contact?.guaranteeState}"
                                              from="${["正常", "关注", "次级", "可疑", "损失"]}"/>
                                </div>
                            </div>
                            </div>
                        </div>

                    </g:form>
                </g:each>

            </div>
        </div>
        <g:form action="ajaxUpdate" name="myForm" id="${this.opportunity?.id}" class="form_7">
            <div class="hpanel">
                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">借款征信评级：</label>

                        <div class="col-md-6">
                            <g:select class="form-control" name="cBCState" noSelection="${['': '未选择']}"
                                      value="${this.opportunity?.cBCState}" from="${["优质", "次优", "疑难"]}"></g:select>
                        </div>
                    </div>
                </div>
            </div>


            <div class="hpanel hred">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    大数据小结

                </div>

                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">是否命中大数据拒单项：</label>

                        <div class="col-md-6">
                            <g:select class="form-control" name="rejected" noSelection="${['': '未选择']}"
                                      value="${this.opportunity?.rejected}" from="${["是", "否"]}"></g:select>
                        </div>
                    </div>

                    <div class="form-group hide" id="descriptionOfRejectionInfo">
                        <label class="col-md-3 control-label text-warning">命中大数据说明：</label>

                        <div class="col-md-6">
                            <textarea class="form-control" name="descriptionOfRejection" id="descriptionOfRejection"
                                      rows="3"
                                      placeholder="非必填项">${this.opportunity?.descriptionOfRejection}</textarea>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">是否涉诉，被执行：</label>

                        <div class="col-md-6">
                            <g:select class="form-control" name="execution" noSelection="${['': '未选择']}"
                                      value="${this.opportunity?.execution}" from="${["是", "否"]}"></g:select>
                        </div>

                    </div>

                    <div class="form-group hide" id="descriptionOfExecutionInfo">
                        <label class="col-md-3 control-label text-warning">涉诉，被执情况说明：</label>

                        <div class="col-md-6">
                            <textarea rows="3" class="form-control" name="descriptionOfExecution" id="descriptionOfExecution"
                                      placeholder="非必填项">${this.opportunity?.descriptionOfExecution}</textarea>
                        </div>

                    </div>
                </div>
            </div>

            <div class="hpanel hred">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    借款用途

                </div>

                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">借款用途：</label>

                        <div class="col-md-6">
                            <g:select class="form-control" name="loanUsage" noSelection="${['': '未选择']}"
                                      value="${this.opportunity?.loanUsage}"
                                      from="${["资金周转", "消费", "装修", "企业经营", "支付保证金", "过桥", "归还上家贷款", "进货", "扩大经营", "购买原材料", "支付房租", "其他"]}">

                            </g:select>

                        </div>
                    </div>

                    <div class="form-group" id="otherLoanUsageInfo">
                        <label class="col-md-3 control-label text-warning">借款用途说明：</label>

                        <div class="col-md-6">
                            <textarea class="form-control" name="otherLoanUsage" id="otherLoanUsage"
                                      rows="4"
                                      placeholder="非必填项">${this.opportunity?.otherLoanUsage}</textarea>
                        </div>

                    </div>
                </div>
            </div>

            <div class="hpanel hred">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    还款来源

                </div>

                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">还款来源：</label>

                        <div class="col-md-6">
                            <g:select class="form-control" name="repaymentSource"
                                      value="${this.opportunity?.repaymentSource}"
                                      from="${["企业经营收入", "保证金退换", "房屋租金", "银行贷款", "转单", "股票", "基金到期", "朋友还款", "其他"]}"></g:select>
                        </div>
                    </div>

                    <div class="form-group" id="otherRepaymentSourceInfo">
                        <label class="col-md-3 control-label text-warning">还款来源说明：</label>

                        <div class="col-md-6">
                            <textarea class="form-control" name="otherRepaymentSource" rows="3"
                                      placeholder="非必填项">${this.opportunity?.otherRepaymentSource}</textarea>
                        </div>

                    </div>
                </div>
            </div>

            <div class="hpanel hyellow">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    风险结论
                </div>

                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">优势：</label>

                        <div class="col-md-6">
                            <g:select class="form-control" name="advantages" noSelection="${['': '未选择']}"
                                      value="${this.opportunity?.advantages}"
                                      from="${["抵押率低", "还款意愿强", "抵押物地理位置优越流通变现强", "抵押物为重点学区房", "借款人增信资料多", "其他"]}"></g:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">劣势：</label>

                        <div class="col-md-6">
                            <g:select class="form-control" name="disadvantages" noSelection="${['': '未选择']}"
                                      value="${this.opportunity?.disadvantages}"
                                      from="${["房龄老", "抵押人年龄偏大", "抵押率高", "未提供还款来源", "大头小尾", "所属行业高危", "面谈配合度差", "转单业务我司贷款高于上家", "其他"]}"></g:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label text-warning">补充说明：</label>

                        <div class="col-md-6">
                            <textarea class="form-control" name="additionalComment" rows="5"
                                      placeholder="非必填项">${this.opportunity?.additionalComment}</textarea>
                        </div>

                    </div>

                    <div class="row">
                        <h5 class="col-md-2 text-right">授信建议</h5>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">授信金额（万元）：</label>

                        <div class="col-md-6">
                            <p class="form-control-static">${this.opportunity?.actualAmountOfCredit}</p>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">抵押率：</label>

                        <div class="col-md-6">
                            <p class="form-control-static">
                                <g:each in="${this.collaterals}">
                                    <span class="m-r">
                                        <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                                        minFractionDigits="2"/>%
                                    </span>
                                </g:each>
                            </p>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">实际贷款期限（月）：</label>

                        <div class="col-md-6">
                            <p class="form-control-static">${this.opportunity?.actualLoanDuration}</p>
                        </span>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">签约主体：</label>

                        <div class="col-md-6">
                            <p class="form-control-static">
                                <g:each in="${this.opportunityContacts}">
                                    <span class="m-r">
                                        ${it?.contact?.fullName}
                                    </span>
                                </g:each>
                            </p>
                        </span>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">月利息</label>

                        <div class="col-md-6 input-group">
                            <g:field type="number" class="form-control" name="monthlyInterest"
                                         value="${this.opportunity?.monthlyInterest}"/>
                            <span class="input-group-addon">%</span>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">月服务费率</label>

                        <div class="col-md-6 input-group">
                            <g:field type="number" class="form-control" name="serviceCharge"
                                         value="${this.opportunity?.serviceCharge}"/>
                            <span class="input-group-addon">%</span>
                        </div>

                    </div>

                    <div class="form-group">

                        <label class="col-md-3 control-label">是否要求面审：</label>

                        <div class="col-md-6">
                            <g:select class="form-control" name="interviewRequired" noSelection="${['': '未选择']}"
                                      value="${this.opportunity?.interviewRequired}"
                                      from="${["是", "否"]}"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="hpanel hyellow">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    放款前要求
                </div>

                <div class="panel-body form-horizontal">

                    <div class="form-group">
                        <label class="col-sm-3 control-label text-warning">公证事项</label>

                        <div class="col-md-6">

                            <g:each in="${["强制执行公证", "全委公证"]}" var="fileName" status="i">
                                <g:if test="${this.opportunity?.notarizationMatters?.contains(fileName)}">
                                    <div class="checkboxGroup">
                                        <input class="i-checks" type="checkbox" name="notarizationMatters"
                                               value="${fileName}" checked/> ${fileName}
                                    </div>

                                </g:if>
                                <g:else>
                                    <div class="checkboxGroup">
                                        <input class="i-checks" type="checkbox" name="notarizationMatters"
                                               value="${fileName}"/> ${fileName}
                                    </div>

                                </g:else>
                            </g:each>

                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">抵押凭证</label>

                        <div class="col-md-9">
                            <g:radioGroup class="i-checks" name="mortgageCertification"
                                          value="${this.opportunity?.mortgageCertification}" values="[true, false]"
                                          labels="['抵押登记受理单', '他项权利证书']">

                                <div class="checkboxGroup">
                                    ${it.radio} ${it.label}
                                </div>
                            </g:radioGroup>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label text-warning">签署文件</label>

                        <div class="col-md-9">
                            <g:each in="${["《征信授权书》", "《如实告知书》", "《未出租声明》", "《租户告知书》"]}" var="fileName" status="i">
                                <g:if test="${this.opportunity?.signedDocuments?.contains(fileName)}">
                                    <div class="checkboxGroup">
                                        <input class="i-checks" type="checkbox" name="signedDocuments"
                                               value="${fileName}" checked/> ${fileName}
                                    </div>

                                </g:if>
                                <g:else>
                                    <div class="checkboxGroup">
                                        <input class="i-checks" type="checkbox" name="signedDocuments"
                                               value="${fileName}"/> ${fileName}
                                    </div>

                                </g:else>
                            </g:each>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label text-warning">收押材料</label>

                        <div class="col-md-9">

                            <g:each in="${["收款收据", "银行卡复印件", "一抵银行按揭合同", "放款前产调"]}" var="fileName" status="i">
                                <g:if test="${this.opportunity?.mortgageMaterials?.contains(fileName)}">
                                    <div class="checkboxGroup">
                                        <input class="i-checks" type="checkbox" name="mortgageMaterials"
                                               value="${fileName}" checked/> ${fileName}
                                    </div>

                                </g:if>
                                <g:else>
                                    <div class="checkboxGroup">
                                        <input class="i-checks" type="checkbox" name="mortgageMaterials"
                                               value="${fileName}"/> ${fileName}
                                    </div>
                                </g:else>
                            </g:each>

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label text-warning">其他</label>

                        <div class="col-md-6">
                            <textarea rows="4" class="form-control" name="otherIssues"
                                      placeholder="非必填">${this.opportunity?.otherIssues}</textarea>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label text-warning">补充说明</label>

                        <div class="col-md-6">
                            <textarea rows="4" class="form-control" name="otherIssues"
                                      placeholder="非必填">${this.opportunity?.prePaymentAdditionalComment}</textarea>
                        </div>

                    </div>
                </div>
            </div>

            <div class="row">
                <div class="from-group col-md-offset-5">
                    <button class="form7Btn btn btn-sm btn-info" type="button">
                        <i class="fa fa-check"></i> 保存
                    </button>
                </div>
            </div>
        </g:form>

    </div>
</div>
<asset:javascript src="homer/vendor/jquery-validation/jquery.validate.min.js"/>
<script>

    $(function () {
        // 对外担保情况为无，隐藏下面两排
        $.each($(".contactGuaranteeStatus"), function (i, obj) {
            var contactGuaranteeStatus = $(obj).val().trim();
            if(contactGuaranteeStatus == "无"){
                $(obj).closest(".form-group").next(".contactGuaranteeStatusInfo").addClass('hide');
            }else{
                $(obj).closest(".form-group").next(".contactGuaranteeStatusInfo").removeClass('hide');
            }

        });

         $(".contactGuaranteeStatus").change(function () {
            var contactGuaranteeStatus = $(this).val().trim();
            var contactGuaranteeStatusInfo = $(this).closest(".form-group").next(".contactGuaranteeStatusInfo");
            if(contactGuaranteeStatus == "无"){
                contactGuaranteeStatusInfo.addClass("hide");
                contactGuaranteeStatusInfo.find(".contactGuaranteeType").val("");
                contactGuaranteeStatusInfo.find(".contactGuaranteeBalance").val("");
                contactGuaranteeStatusInfo.find(".contactRelationOfGuarantor").val("");
                contactGuaranteeStatusInfo.find(".contactGuaranteeState").val("");

            }else{
                contactGuaranteeStatusInfo.removeClass("hide");
            }


        })
        //抵押类型为一抵时，自动带出一抵金额（可贷金额）

        if ($("#mortgageType").text() == "一抵") {
            $("#firstMortgageAmount").val($("#actualAmountOfCredit").val());
        }
        if ($("#mortgageType").text() == "二抵") {
//            $("#firstMortgageAmount").val($("#actualAmountOfCredit").val());
            $("#secondMortgageAmount").val($("#actualAmountOfCredit").val()).closest(".form-group").removeClass("hide");
        }

        $("#actualAmountOfCredit").blur(function () {
            if ($("#mortgageType").text() == "一抵" && $("#actualAmountOfCredit").val().trim()) {
                $("#firstMortgageAmount").val($("#actualAmountOfCredit").val().trim());
            }
            if ($("#mortgageType").text() == "二抵" && $("#actualAmountOfCredit").val().trim()) {
                $("#secondMortgageAmount").val($("#actualAmountOfCredit").val()).closest(".form-group").removeClass("hide");
            }

        });
        //是否命中大数据拒单项
        if ($("#rejected").val().trim() == "是") {
            $("#descriptionOfRejectionInfo").removeClass("hide");
        }
        ;
        $("#rejected").change(function () {
            if ($("#rejected").val().trim() == "否") {
                $("#descriptionOfRejection").val("");
                $("#descriptionOfRejectionInfo").addClass("hide");
            } else {
                $("#descriptionOfRejectionInfo").removeClass("hide");
            }
        });
        //是否涉诉，被执行
        if ($("#execution").val().trim() == "是") {
            $("#descriptionOfExecutionInfo").removeClass("hide");
        }
        ;
        $("#execution").change(function () {
            if ($("#execution").val().trim() == "否") {
                $("#descriptionOfExecution").val("");
                $("#descriptionOfExecutionInfo").addClass("hide");
            } else {
                $("#descriptionOfExecutionInfo").removeClass("hide");
            }
        });
//        借款用途
       /* if ($("#loanUsage").val().trim() == "其他") {
            $("#otherLoanUsageInfo").removeClass("hide");
        }
        ;

        $("#loanUsage").change(function () {
            if ($("#loanUsage").val().trim() == "其他") {
                $("#otherLoanUsageInfo").removeClass("hide");
            } else {
                $("#otherLoanUsage").val("");
                $("#otherLoanUsageInfo").addClass("hide");
            }
        });*/
//        还款来源
       /* if ($("#repaymentSource").val().trim() == "其他") {
            $("#otherRepaymentSourceInfo").removeClass("hide");
        }
        ;

        $("#repaymentSource").change(function () {
            if ($("#repaymentSource").val().trim() == "其他") {
                $("#otherRepaymentSourceInfo").removeClass("hide");
            } else {
                $("#otherRepaymentSource").val("");
                $("#otherRepaymentSourceInfo").addClass("hide");
            }
        });*/

        //上扣息，上扣息月份数（月）
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
                            $(".sweet-alert .confirm").click(function(){
                                window.history.go(0);
                            });
                        }
                        else {
                            swal(data.errorMessage.errors[0].message, "", "error");
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
            $(obj).find(".order2").text(i + 1)
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
                                $(".sweet-alert .confirm").click(function(){
                                    window.history.go(0);
                                });
                            }
                            else {
                                swal(data.errorMessage.errors[0].message, "", "error");
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


        $(".form3Btn").click(function () {
            var form = $(this).closest(".form_3");
            formAjaxSubmit(form);
        });

        $.each($(".form_3"), function (i, obj) {
            $(obj).find(".order3").text(i + 1)

        });

        $(".form4Btn").click(function () {
            var form = $(this).closest(".form_4");
            formAjaxSubmit(form);
        });
        $.each($(".form_4"), function (i, obj) {
            $(obj).find(".order4").text(i + 1)

        });

        $(".form5Btn").click(function () {
            var form = $(this).closest(".form_5");
            formAjaxSubmit(form);
        });

        $(".form6Btn").click(function () {
            var form = $(this).closest(".form_6");
            formAjaxSubmit(form);
        });
        $(".form7Btn").click(function () {
            var form = $(".form_7");
            formAjaxSubmit(form);
        });

        function formAjaxSubmit(form) {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: form.attr('action'),
                data: form.serialize(),
                success: function (data) {
                    if (data.status == "success") {
                        swal("修改成功", "", "success");
                    }
                    else {
                        swal(data.errorMessage.errors[0].message, "", "error");
                    }

                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            })
        }
    });
</script>
</body>
</html>

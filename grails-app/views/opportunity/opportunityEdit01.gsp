<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>${this.opportunity.serialNumber}展期申请</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="opportunity" action="show" id="${this.opportunity?.id}">订单详情</g:link></li>
                    <li class="active">
                        <span>展期申请</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                ${this.opportunity.serialNumber}展期申请
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <g:hasErrors bean="${this.opportunity}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunity}" var="error">
                    <li>
                        <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>
                        <g:message error="${error}"/>
                    </li>
                </g:eachError>
            </ul>
        </g:hasErrors>
    </div>

    <div class="row">
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">

                </div>
                待展期订单基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12">
                        <h4>${this.opportunity?.parent?.serialNumber}</h4></div>

                    <div class="col-md-2">
                        <strong>
                            <span class="glyphicon glyphicon-user"
                                  aria-hidden="true"></span> ${this.opportunity?.fullName}
                        </strong>
                    </div>
                    <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                        <div class="col-md-1">
                            <span class="fa fa-chain"></span>${this.opportunity?.parent?.protectionEndTime - this.opportunity?.parent?.protectionStartTime}天
                        </div>
                    </g:if>

                <!--<div class="col-md-2">
                        <strong>${this.opportunity?.stage?.name}</strong>
                    </div>-->
                <!-- <div class="col-md-1">是否公证：<g:if test="${this.mortgageAttachments}"><g:checkBox
                        name="mortgageAttachments" checked="true" disabled="disabled"/></g:if><g:else><g:checkBox
                        name="mortgageAttachments" disabled="disabled"/></g:else></div> -->
                <!-- <div class="col-md-1">是否抵押：<g:if test="${this.notaryAttachments}"><g:checkBox
                        name="notaryAttachments" checked="true" disabled="disabled"/></g:if><g:else><g:checkBox
                        name="notaryAttachments" disabled="disabled"/></g:else></div> -->
                    <g:if test="${this.opportunity?.parent?.status == 'Failed'}"><span
                            class="label label-danger pull-right">订单结果：失败</span></g:if>
                    <g:elseif test="${this.opportunity?.parent?.status == 'Completed'}"><span
                            class="label label-success pull-right">订单结果：成功</span></g:elseif>
                    <g:else><span class="label label-info pull-right">订单结果：进行中</span></g:else>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-1 border-right">
                        <div class="contact-stat"><span>贷款金额</span><strong>${this.opportunity?.parent?.actualAmountOfCredit}万元</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>贷款期限</span>
                            <strong>${this.opportunity?.parent?.actualLoanDuration}月</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>原评房单价</span>
                            <strong><g:formatNumber number="${this.opportunity?.unitPrice}" type="number" maxFractionDigits="2" minFractionDigits="2"/>元
                            </strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>原评房总价</span>
                            <strong><g:formatNumber number="${this.opportunity?.loanAmount}" type="number" maxFractionDigits="2" minFractionDigits="2"/>万元</strong>
                        </div>
                    </div>

                    <!--<div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>初审（可贷）</span>
                            <strong>%{--${this.opportunity?.maximumAmountOfCredit}万元；--}%${this.opportunity?.lender?.level?.name}；${this.opportunity?.dealRate}</strong>
                        </div>
                    </div> -->

                    <!--<div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>审核可贷金额</span>
                            <strong>${this.opportunity?.actualAmountOfCredit}万元；${this.opportunity?.interestPaymentMethod?.name}；月息${this.opportunity?.monthlyInterest}%；
                            渠道服务费<g:formatNumber number="${this.opportunity?.commissionRate}" 
                                        maxFractionDigits="9"/>%；${this.opportunity?.commissionPaymentMethod}
                            </strong>
                        </div>
                    </div> -->

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>产品类型</span>
                            <strong>${this.opportunity?.product?.name}</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>抵押类型</span>
                            <strong>${this.opportunity?.mortgageType?.name}</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>一抵金额</span>
                            <strong>${this.opportunity?.firstMortgageAmount}万元</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>二抵金额</span>
                            <strong>${this.opportunity?.secondMortgageAmount}万元</strong>
                        </div>
                    </div>

                    <div class="col-md-1">
                        <div class="contact-stat">
                            <span>一抵性质</span>
                            <strong>${this.opportunity?.typeOfFirstMortgage?.name}</strong>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hred">
            <div class="panel-heading">
                展期申请
            </div>

            <div class="panel-body">
              <g:if test="${this.user?.department?.name == '支持组'}">
          
              <g:form resource="${this.opportunity}" method="PUT" name="myForm" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunity}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunity}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <div class="form-group">
                        <label class="col-md-3 control-label">拟展期金额</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="requestedAmount"
                                         value="${this.opportunity?.requestedAmount}"/>
                            <span class="input-group-addon">万元</span>
                        </div>

                        <label class="col-md-3 control-label">拟展期期限</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="loanDuration"
                                         value="${this.opportunity?.loanDuration}"/>
                            <span class="input-group-addon">月</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
              </g:if>
              <g:elseif test="${this.user?.department?.name == '风控组'}">

              <g:form resource="${this.opportunity}" method="PUT" name="myForm" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunity}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunity}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                    <label class="col-md-3 control-label">审核可展期金额</label>

                    <div class="col-md-4 input-group">
                        <g:textField class="form-control" name="actualAmountOfCredit"
                                     value="${this.opportunity?.actualAmountOfCredit}"/>
                        <span class="input-group-addon">万元</span>
                    </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                    <label class="col-md-3 control-label">审核可展期期限</label>

                    <div class="col-md-4 input-group">
                        <g:textField class="form-control" name="actualLoanDuration"
                                     value="${this.opportunity?.actualLoanDuration}"/>
                        <span class="input-group-addon">月</span>
                    </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                    <label class="col-md-3 control-label">展期需先还本金</label>

                    <div class="col-md-4 input-group">
                        <g:textField class="form-control" name="advancePayment"
                                    value="${this.opportunity?.advancePayment}"/>
                         <span class="input-group-addon">%</span>
                    </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                    <label class="col-md-3 control-label">展期费率</label>

                    <div class="col-md-4 input-group">
                        <g:textField class="form-control" name="monthlyInterest"
                                    value="${this.opportunity?.monthlyInterest}"/>
                         <span class="input-group-addon">%</span>
                    </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                    <label class="col-md-3 control-label">借款服务费</label>

                    <div class="col-md-4 input-group">
                        <g:textField class="form-control" name="serviceCharge"
                                    value="${this.opportunity?.serviceCharge}"/>
                         <span class="input-group-addon">%</span>
                    </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                    <label class="col-md-3 control-label">付息方式</label>

                    <div class="col-md-4">
                        <g:select class="form-control" optionKey="id" optionValue="name"
                            name="interestPaymentMethod"
                            value="${this.opportunity?.interestPaymentMethod?.id}"
                            from="${com.next.InterestPaymentMethod.list()}"/>
                    </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                    <label class="col-md-3 control-label">渠道服务费</label>

                    <div class="col-md-4 input-group">
                        <g:textField class="form-control" name="commissionRate" required="required" id = "commissionRate"
                            value="${this.opportunity?.commissionRate}"/>
                        <span class="input-group-addon">%</span>
                    </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        
                    <label class="col-md-3 control-label">返佣方式</label>

                    <div class="col-md-4 input-group">
                        <g:select class="form-control" optionKey="id" optionValue="name"
                            name="commissionPaymentMethod" required="required" id = "commissionPaymentMethod"
                            value="${this.opportunity?.commissionPaymentMethod?.id}"
                            from="${com.next.CommissionPaymentMethod.list()}"/>
                    </div>
                        
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">渠道返费</label>

                        <div class="col-md-4 input-group">
                            <g:textField class="form-control" name="commission" required="required" id="commission"
                            value="${this.opportunity?.commission}"/>
                            <span class="input-group-addon">万元</span>
                        </div>                       
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">客户级别</label>

                        <div class="col-md-4 checkbox-inline">
                            <g:radioGroup class="checkbox-inline" name="level"
                                        value="${this.opportunity?.lender?.level?.name}"
                                        labels="['优质', '次优', '疑难', '不良','待评级']"
                                        values="['A', 'B', 'C', 'D','待评级']">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>                        
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                    <label class="col-md-3 control-label">抵押率</label>

                    <div class="col-md-4 input-group">
                        <g:textField class="form-control" name="loanToValue" required="required" id = "loanToValue"
                            value="${this.opportunity?.collaterals[0]?.loanToValue}"/>
                        <span class="input-group-addon">%</span>
                    </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">抵押凭证类型</label>

                        <div class="col-md-4 checkbox-inline">
                            <g:select class="form-control" name="mortgageCertificateType"
                                      value="${this.opportunity?.mortgageCertificateType?.id}"
                                      noSelection="${['null': '无']}"
                                      from="${com.next.MortgageCertificateType.list()}"
                                      optionKey="id"
                                      optionValue="name"></g:select>
                        </div>                        
                    </div>

                    <div class="hr-line-dashed"></div>
        
                    <div class="form-group">
                        <div class="col-md-4 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
              </g:elseif>
              <g:elseif test="${this.user?.department?.name == '权证组'}">

              <g:form resource="${this.opportunity}" method="PUT" name="myForm" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunity}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunity}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <div class="form-group">
                        <label class="col-md-3 control-label">展期合同单号</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" name="externalId"
                                         value="${this.opportunity?.externalId}"/>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
              </g:elseif>


            </div>
        </div>
    </div>
</div>

</body>

</html>

<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityBankAccount.label', default: 'OpportunityBankAccount')}"/>
    <title>账号信息</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show"
                                id="${this.opportunityBankAccount?.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>新增</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增账号
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增账号
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                <g:if test="${flash.message}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${flash.message}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                <g:message error="${error}"/>
                            </li>
                        </g:eachError>
                    </ul>
                </g:if>
                    <g:hasErrors bean="${this.opportunityBankAccount}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityBankAccount}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <input class="form-control" name="opportunity.id" type="hidden"
                           value="${this.opportunityBankAccount?.opportunity?.id}">

                           <div class="form-group">
                               <label class="col-md-3 control-label">账户类型</label>

                               <div class="col-md-3">
                                   <g:select name="type.id" id="type"
                                             from="${com.next.OpportunityBankAccountType.list()}" optionKey="id"
                                             optionValue="name" class="form-control"/>
                               </div>
                           </div>

                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">银行</label>

                               <div class="col-md-3">

                                   <g:select name="bankAccount.bank.id" value="${this.opportunityBankAccount?.bankAccount?.bank?.id}"
                                             from="${com.next.Bank.findAllByActive(true)}" optionKey="id" optionValue="name" class="form-control"/>
                               </div>
                           </div>
                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">支付通道</label>

                               <div class="col-md-3">

                                   <g:select name="bankAccount.paymentChannel.id" value="${this.opportunityBankAccount?.bankAccount?.paymentChannel?.id}" from="${com.next.PaymentChannel.findAllByActive(true)}" optionKey="id" optionValue="name" class="form-control"/>
                               </div>
                           </div>

                           <div class="hr-line-dashed"></div>

                           <div class="form-group">
                               <label class="col-md-3 control-label">卡号</label>

                               <div class="col-md-3">
                                   <g:textField name="bankAccount.numberOfAccount" value="${this.opportunityBankAccount?.bankAccount?.numberOfAccount}" class="form-control"/>
                               </div>
                           </div>
                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">账号名</label>

                               <div class="col-md-3">
                                   <g:textField name="bankAccount.name" value="${this.opportunityBankAccount?.bankAccount?.name}" class="form-control"/>
                               </div>
                           </div>
                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">银行预留手机号</label>

                               <div class="col-md-3">
                                   <g:textField name="bankAccount.cellphone" value="${this.opportunityBankAccount?.bankAccount?.cellphone}" class="form-control"/>
                               </div>
                           </div>
                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">证件类型</label>

                               <div class="col-md-3">
                                 <g:select name="bankAccount.certificateType.id"
                                           from="${com.next.ContactIdentityType.list()}" optionKey="id"
                                           optionValue="name" class="form-control"/>
                                </div>
                           </div>
                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">证件号</label>

                               <div class="col-md-3">
                                 <g:textField name="bankAccount.numberOfCertificate" value="${this.opportunityBankAccount?.bankAccount?.numberOfCertificate}" class="form-control"/>
                                </div>
                           </div>
                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">是否验卡成功</label>

                               <div class="col-md-3 checkbox-inline">
                                   <g:radioGroup class="checkbox-inline" name="bankAccount.active" value="${this.opportunityBankAccount?.bankAccount?.active}" labels="['true','false']" values="[true,false]">
                                       ${it.radio} ${it.label}
                                   </g:radioGroup>
                               </div>
                           </div>
                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">支行</label>

                               <div class="col-md-3">
                                 <g:textField name="bankAccount.bankBranch" value="${this.opportunityBankAccount?.bankAccount?.bankBranch}" class="form-control"/>
                                </div>
                           </div>
                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">支行地址</label>

                               <div class="col-md-3">
                                 <g:textField name="bankAccount.bankAddress" value="${this.opportunityBankAccount?.bankAccount?.bankAddress}" class="form-control"/>
                                </div>
                           </div>
                           <div class="hr-line-dashed"></div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="create" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

</body>

</html>

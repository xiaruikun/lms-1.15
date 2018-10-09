<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'transactionRecord.label', default: 'TransactionRecord')}"/>
    <title>新增交易记录</title>
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
                                id="${this.oid}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>新增</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增交易记录
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增交易记录
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">${flash.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">×</span></button>
                        </div>
                    </g:if>
                    <g:hasErrors bean="${this.transactionRecord}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.transactionRecord}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <input class="form-control" name="opportunity.id" type="hidden"
                           value="${this.transactionRecord?.opportunity?.id}">

                   %{-- <div class="form-group">
                        <label class="col-md-4 control-label">交易类型</label>

                        <div class="col-md-4">
                            <g:select name="type.id" id="type"
                                      from="${com.next.TransactionType.list()}" optionKey="id"
                                      optionValue="name" class="form-control"/>
                        </div>
                    </div>--}%

                    %{--<div class="hr-line-dashed"></div>--}%

                    <div class="form-group">
                        <label class="col-md-4 control-label">金额</label>

                        <div class="col-md-4">
                            <div class="input-group">
                                <g:textField name="amount" value="${this.transactionRecord?.amount}"
                                             class="form-control"/>
                                <span class="input-group-addon">万元</span>
                            </div>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">借方账户名</label>

                        <div class="col-md-4">

                            <g:select name="debit" value="${this.transactionRecord?.debit}"
                                      from="${com.next.BankAccount.findAllByNameInList([com.next.OpportunityFlexField.findByCategoryAndName(com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(this.transactionRecord?.opportunity, com.next.FlexFieldCategory.findByName("资金渠道")), "放款账号").value, '富友'])}"
                                      optionKey="id" optionValue="name" class="form-control"/>
                        </div>
                    </div>

                    <!--<div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-4 control-label">贷方账户名</label>
                        <div class="col-md-3">
                    <g:select name="credit" value="${this.transactionRecord?.credit}"
                              from="${com.next.OpportunityBankAccount.findAllByOpportunity(this.transactionRecord?.opportunity).bankAccount}"
                              optionKey="id" optionValue="name" class="form-control"/>
                    </div>
                </div> -->
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">贷方帐号</label>

                        <div class="col-md-4">
                            <g:select name="credit" value="${this.transactionRecord?.credit}"
                                      from="${com.next.OpportunityBankAccount.findAllByOpportunity(this.transactionRecord?.opportunity).bankAccount}"
                                      optionKey="id" optionValue="numberOfAccount" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">预计开始时间</label>

                        <div class="col-md-5 datePicker">
                            <g:datePicker name="plannedStartTime" value="${new Date()}" precision="day"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">预计完成时间</label>

                        <div class="col-md-5 datePicker">
                            <g:datePicker name="plannedEndTime" value="${new Date()}" precision="day"/>
                        </div>
                    </div>
                    <!-- <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-4 control-label">开始时间</label>
                         <div class="col-md-5 datePicker">
                    <g:datePicker name="startTime" value="${new Date()}" precision="day"/>
                    </div>
               </div> -->
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">实际完成时间</label>

                        <div class="col-md-5 datePicker">
                            <g:datePicker name="endTime" precision="day" default="none" noSelection="['': '-请选择-']"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">清分账号</label>

                        <div class="col-md-4">
                            <div class="input-group">
                                <g:textField name="debitAccount" class="form-control"/>
                            </div>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">扣款方式</label>

                        <div class="col-md-4">
                            <g:select name="repaymentMethod.id" id="repaymentMethod"
                                      from="${com.next.RepaymentMethod.list()}" optionKey="id"
                                      optionValue="name" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">交易状态</label>

                        <div class="col-md-4">
                            <g:select name="status.id" id="status"
                                      from="${com.next.TransactionRecordStatus.list()}" optionKey="id"
                                      optionValue="name" class="form-control"/>
                        </div>
                    </div>
                    <input type="hidden" name="oid" value="${this.oid}">

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-5">
                            <button class="btn btn-info">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

</body>

</html>

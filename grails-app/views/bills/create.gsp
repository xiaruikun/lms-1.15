<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bills.label', default: 'Bills')}"/>
    <title>新增还款计划</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="user" action="index">还款计划</g:link>
                    </li>
                    <li class="active">
                        <span>新增还款计划</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                还款计划管理
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 新增还款计划</small>

        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增还款计划
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.bills}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.bills}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-2 control-label">还款状态</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="status" value="${this.bills?.status}"
                                      from="['Estimating', 'Repaying', 'Cleared', 'Overdue', 'Extended', 'Prepaid']"
                                      noSelection="['': '-请选择-']"/>
                        </div>
                        <label class="col-md-2 control-label">银行账号</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="bankAccount" id="bankAccount"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">开户行</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="bankName" id="bankName"/>
                        </div>
                        <label class="col-md-2 control-label">已收意向金</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="prepaidAmount" id="prepaidAmount"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">账户名称</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="accountName" id="accountName"/>
                        </div>
                        <label class="col-md-2 control-label">订单号</label>

                        <div class="col-md-3">
                            <!-- <g:select class="form-control" name="opportunity.id"
                                           value="${this.bills?.opportunity?.id}" id="opportunity" optionKey="id"
                                           optionValue="serialNumber" from="${com.next.Opportunity.list()}"/> -->
                            <g:textField class="form-control" type="text" name="serialNumber" id="serialNumber"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">借款金额（本金）</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="principal"/>
                        </div>
                        <label class="col-md-2 control-label">服务费</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="serviceCharge"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">月息年本每年还本金额</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="bankAccount"/>
                        </div>
                        <label class="col-md-2 control-label">预付金</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="advances"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">上扣息月份数</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="firstPayMonthes"/>
                        </div>
                        <label class="col-md-2 control-label">渠道费</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="channelCharge"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

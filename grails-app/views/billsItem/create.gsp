<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'billsItem.label', default: 'billsItem')}" />
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
                        <g:link controller="opportunity" action="show"
                                id="${this.billsItem?.bills?.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        新增还款计划
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs" style="text-transform:none;">
                新增还款计划
            </h2>
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
                <div id="edit-billsItem" class="content scaffold-edit" role="main">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.billsItem}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.billsItem}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <g:form action="save" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">期数</label>

                            <div class="col-md-3">
                                <g:textField type="text" name="period" id="period" value="${this.billsItem?.period}" class="form-control"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">开始时间</label>

                            <div class="col-md-6">
                                <g:datePicker precision="day" name="startTime" value="${this.billsItem?.startTime}"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">结束时间</label>

                            <div class="col-md-4">
                                <g:datePicker precision="day" name="endTime" value="${this.billsItem?.endTime}"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">实际还款时间</label>

                            <div class="col-md-6">
                                <g:datePicker precision="day" default="none" noSelection="['': '请选择']"
                                              name="actualEndTime"
                                              value="${this.billsItem?.actualEndTime}"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">余额</label>

                            <div class="col-md-3 input-group">
                                <g:textField type="text" name="balance" id="balance" value="${this.billsItem?.balance}" class="form-control"/>
                                <span class="input-group-addon">万元</span>

                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">应付金额</label>

                            <div class="col-md-3 input-group">
                                <g:textField type="text" id="receivable" name="receivable"  value="${this.billsItem?.receivable}" class="form-control"/>
                                <span class="input-group-addon">万元</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">实收金额</label>

                            <div class="col-md-3 input-group">
                                <g:textField type="text" id="receipts" name="receipts" value="${this.billsItem?.receipts}" class="form-control"/>
                                <span class="input-group-addon">万元</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">类型</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="type.id" required="required" id="type"
                                          value="${this.billsItem?.type?.id}" from="${com.next.BillsItemType.list()}"
                                          optionKey="id" optionValue="name"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">状态</label>
                            <div class="col-md-3">
                                <g:select class="form-control daily-b " name="status" id="status"
                                          from="${['待收', '已收', '扣款失败']}"
                                          valueMessagePrefix="stage" value="${this.billsItem?.status}"/>
                            </div>
                        </div>
                        <input type="hidden" name="bills.id" value="${this.bills?.id}">
                        <input type="hidden" name="credit.id" value="${this.credit?.id}">
                        <input type="hidden" name="debit.id" value="${this.debit?.id}">
                        <input type="hidden" name="oid" value="${this.oid}">
                        <input type="hidden" name="billsId" value="${this.billsId}">
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-lg-offset-3">
                                <g:submitButton name="create" class="btn btn-info" value="保存"/>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

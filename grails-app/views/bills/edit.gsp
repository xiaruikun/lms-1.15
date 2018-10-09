<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bills.label', default: 'Bills')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <!-- <a href="#edit-bills" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-bills" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.bills}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.bills}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.bills}" method="PUT">
                <g:hiddenField name="version" value="${this.bills?.version}" />
                <fieldset class="form">
                    <f:all bean="bills"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
         -->
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <h2 class="font-light m-b-xs">
                用戶: ${this.bills.opportunity.fullName}
            </h2>
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="bills" action="index">还款计划记录</g:link>
                    </li>
                    <li class="active">
                        <span>用戶还款信息编辑</span>
                    </li>
                </ol>
            </div>

        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                用戶还款信息编辑
            </div>
            <div class="panel-body">
                <g:form resource="${this.bills}" method="PUT" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.bills}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.bills}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}" />
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <div class="form-group">
                        <label class="col-md-3 control-label">还款状态</label>
                        <div class="col-md-3">
                            <g:select class="form-control" name="status" value="${this.bills?.status}" from="['Estimating', 'Repaying', 'Cleared', 'Overdue', 'Extended', 'Prepaid']" noSelection="['':'-请选择-']"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">银行账号</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="bankAccount" id="bankAccount" value="${this.bills?.bankAccount}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">开户行</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="bankName" id="depositBank" value="${this.bills?.bankName}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">已收意向金</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="prepaidAmount" id="prepaidAmount" value="${this.bills?.prepaidAmount}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">账户名称</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="accountName" id="accountName" value="${this.bills?.accountName}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">订单号</label>
                        <div class="col-md-3">
                            <g:select class="form-control" name="opportunity.id" value="${this.bills?.opportunity?.id}" id="opportunity" optionKey="id" optionValue="serialNumber" from="${com.next.Opportunity.list()}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">借款金额（本金）</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="principal" id="principal" value="${this.bills?.principal}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">服务费</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="serviceCharge" id="serviceCharge" value="${this.bills?.serviceCharge}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">月息年本每年还本金额</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="bankAccount" id="bankAccount" value="${this.bills?.bankAccount}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">预付金</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="advances" id="advances" value="${this.bills?.advances}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">上扣息月份数</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="firstPayMonthes" id="firstPayMonthes" value="${this.bills?.firstPayMonthes}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">渠道费</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="channelCharge" id="channelCharge" value="${this.bills?.channelCharge}"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存" />
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
    </body>
</html>

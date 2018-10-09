<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'contactJudgementRecord.label', default: 'ContactJudgementRecord')}"/>
    <title>编辑人法网被执记录</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunityContact" action="show"
                                id="${this.opportunityContact?.id}">联系人信息</g:link>
                    </li>
                    <li class="active">
                        <span>编辑人法网被执记录</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                编辑人法网被执记录
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                编辑人法网被执记录
            </div>

            <div class="panel-body">
                <g:form resource="${this.contactJudgementRecord}" method="PUT" class="form-horizontal receiverForm">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">${flash.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">×</span></button>
                        </div>
                    </g:if>
                    <g:hasErrors bean="${this.contactJudgementRecord}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.contactJudgementRecord}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <input type="hidden" name="opportunityContact" value="${this.opportunityContact?.id}">

                        <input type="hidden" name="contact.id" value="${this.contactJudgementRecord?.contact?.id}">

                        <label class="col-md-4 control-label">执行标的</label>

                        <div class="col-md-4 input-group">
                            <g:textField name="executionObject" class="form-control"
                                         value="${this.contactJudgementRecord?.executionObject}"/>
                            <span class="input-group-addon">元</span>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">执行状态</label>

                        <div class="col-md-4">
                            <g:select class="form-control" name="executionStatus"
                                      value="${this.contactJudgementRecord?.executionStatus}" noSelection="['': '请选择']"
                                      from="${['已结案', '未结案']}"></g:select>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">立案时间</label>

                        <div class="col-md-4">
                            <g:datePicker precision="day" noSelection="['': '请选择']"
                                          value="${this.contactJudgementRecord?.filingTime}"
                                          name="filingTime"/>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-5">
                            <button class="submitBtn btn btn-info" type="submit">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

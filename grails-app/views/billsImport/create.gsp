<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'billsImport.label', default: 'BillsImport')}" />
        <title>新增银行流水列表</title>
    </head>
    <body>
    <div class="small-header transition animated fadeIn">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li><g:link controller="billsImport" action="index">新增银行流水列表</g:link></li>
                        <li class="active">银行流水列表</li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                   新增银行流水列表
                </h2>
            </div>
        </div>
    </div>

    <div class="content animate-panel">
        <div class="row">
           %{-- <g:form action="save">
                <fieldset class="form">
                    <f:all bean="billsImport"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>--}%
            <div class="hpanel hyellow">
                <div class="panel-heading">
                    <div class="panel-tools">
                    </div>
                   新增银行流水列表
                </div>

                <div class="panel-body">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">${flash.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">×</span></button>
                        </div>
                    </g:if>
                    <g:hasErrors bean="${this.billsImport}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.billsImport}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <g:form action="save" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-4 control-label">银行账号名</label>

                            <div class="col-md-4">
                                <g:textField class="form-control" name="name"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">银行卡号</label>

                            <div class="col-md-4">
                                <g:textField class="form-control" name="numberOfAccount"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">清算日期</label>

                            <div class="col-md-4">
                                <g:datePicker precision="day" name="commitTime"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">订单合同编号</label>

                            <div class="col-md-4">
                                <g:textField class="form-control" name="serialNumber"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">清分账号</label>

                            <div class="col-md-4">
                                <g:textField class="form-control" name="debitAccount"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">结果反馈码</label>

                            <div class="col-md-4">
                                <g:textField class="form-control" name="resultCode"></g:textField>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">状态</label>

                            <div class="col-md-4">
                                <g:select class="form-control" name="status"
                                          from="${['成功', '失败', '待处理']}"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">交易金额</label>
                            <div class="col-md-4 input-group">
                                <g:textField class="form-control" name="actualAmountOfCredit"></g:textField>
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">证件号</label>
                            <div class="col-md-4">
                                <g:textField class="form-control" name="numberOfCertificate"></g:textField>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">结果原因</label>

                            <div class="col-md-4">
                                <g:textArea class="form-control" name="resultReason" rows="3"></g:textArea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">失败原因</label>

                            <div class="col-md-4">
                                <g:textArea class="form-control" name="failReason" rows="3"></g:textArea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-3 col-lg-offset-5">
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

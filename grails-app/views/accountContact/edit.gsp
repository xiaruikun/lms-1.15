<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'AccountContact.label', default: 'AccountContact')}"/>
    <title>离职人员编辑</title>
</head>
<body class="fixed-navbar fixed-sidebar">
%{--<div class="small-header">--}%
    %{--<div class="hpanel">--}%
        %{--<div class="panel-body">--}%
            %{--<div id="hbreadcrumb" class="pull-right">--}%
                %{--<ol class="hbreadcrumb breadcrumb">--}%
                    %{--<li>城市-评估机构</li>--}%
                    %{--<li class="active">--}%
                        %{--<span>城市评估机构编辑</span>--}%
                    %{--</li>--}%
                %{--</ol>--}%
            %{--</div>--}%

        %{--</div>--}%
    %{--</div>--}%
%{--</div>--}%
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                离职人员编辑
            </div>
            <div class="panel-body">
                <div class="">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.accountContact}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.accountContact}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="widget-body ">
                        <g:form resource="${this.accountContact}" method="PUT" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-3 control-label">公司名称</label>

                                <div class="col-md-3">

                                    %{--<g:select class="form-control" disabled="disabled" name="city" id="city"--}%
                                              %{--optionKey="id" optionValue="name"--}%
                                              %{--from="${com.next.City.list()}" value="${this.city?.id}"/>--}%

                                    <g:select class="form-control m-b" name="account"  id="account"
                                              from="${com.next.Account.list()}" optionKey="id"
                                              optionValue="name"  value="${this.accountContact?.account?.id}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">员工姓名</label>

                                <div class="col-md-3">
                                    <g:select class="form-control m-b" name="contact" id="contact"
                                              from="${com.next.Contact.list()}" optionKey="id"
                                              optionValue="fullName" value="${this.accountContact?.contact?.id}" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">公司编号</label>

                                <div class="col-md-3">
                                    <input class="form-control" name="accountExternalId"
                                           type="text" step="any" value="${this.accountContact?.accountExternalId}">
                                </div>
                            </div>
                            <div class="form-group">
                            <label class="col-md-3 control-label">员工编号</label>

                            <div class="col-md-3">
                                <input class="form-control" name="contactExternalId"
                                       type="text" step="any" value="${this.accountContact?.contactExternalId}">
                            </div>
                            </div>
                            <div class="form-group">
                            <label class="col-md-3 control-label">雇用时间</label>
                                <div class="col-md-4 datePicker">
                                <g:datePicker class="form-control" name="hiredate" value="${new Date()}" precision="day" years="${1990..2050}"/>

                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">离职时间</label>

                                <div class="col-md-4 datePicker">
                                    <g:datePicker class="form-control" name="leavedate" value="${new Date()}" precision="day" years="${1990..2050}"/>

                                </div>
                            </div>
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
    </div>
</div>
</body>
</html>

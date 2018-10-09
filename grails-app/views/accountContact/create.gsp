<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'AccountContact.label', default: 'AccountContact')}"/>
    <title>新增离职员工</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">

    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right m-t-lg">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>权限管理</li>
                    <li class="active">
                        <span>新增员工</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                用户:${com.next.User.findById(params['id'])?.fullName}
            </h2>
        </div>
    </div>

</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                新增员工
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.AccountContact}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.AccountContact}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form action="save" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">公司名称</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="account.id" required="" id="account"
                                      from="${com.next.Account.list()}" optionKey="id"
                                      optionValue="name"></g:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">员工姓名</label>

                        <div class="col-md-3">
                            <g:select class="form-control m-b" name="contact.id" required="" id="contact"
                                      from="${com.next.Contact.list()}" optionKey="id"
                                      optionValue="fullName"></g:select>
                        </div>
                        <g:link action="create" controller="contact" class="btn btn-info  m-b"><i class="fa fa-plus"></i>新建经纪人</g:link>
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
                </div>  <div class="form-group">
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

</body>
</html>

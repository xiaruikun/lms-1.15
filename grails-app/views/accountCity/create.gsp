<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'accountCity.label', default: 'AccountCity')}" />
    <title>新增开放城市</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header transition animated fadeIn">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="account" action="index">机构管理</g:link>
                        </li>
                        <li class="active">
                            <span>新增开放城市</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    新增开放城市
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增城市
                </div>
                <div class="panel-body">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.accountCity}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.accountCity}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}" />
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <g:form action="save" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">公司名称:</label>
                            <div class="col-md-3">
                                <g:textField name="account.id" id="account" value="${this.accountCity?.account?.id}" class="form-control" class="hidden" />
                                <g:textField name="accountName" id="accountName" value="${this.accountCity?.account?.name}" class="form-control" readonly="readonly" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">城市名称</label>
                            <div class="col-md-3">
                                <g:select  class="form-control" name="city.id" required="required" id="city" value="${this.accountCity?.city}" from="${com.next.City.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton class="btn btn-info" name="create" value="保存" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

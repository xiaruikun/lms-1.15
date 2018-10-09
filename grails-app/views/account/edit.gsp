<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}" />
    <title>机构：${account.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="account" action="index">机构</g:link>
                        </li>
                        <li class="active">
                            <span>信息编辑</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    机构: ${this.account.name}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    机构信息编辑
                </div>
                <div class="panel-body">
                    <g:form resource="${this.account}" method="PUT" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.account}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.account}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">机构编号:</label>
                            <div class="col-md-3">
                                <g:textField name="code" value="${this.account.code}" disabled="" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">机构名称</label>
                            <div class="col-md-3">
                                <g:textField name="name" value="${this.account.name}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">门店编号</label>
                            <div class="col-md-3">
                                <g:textField name="externalId" value="${this.account.externalId}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">合作方式</label>
                            <div class="col-md-3">
                                <g:select name="type.id" value="${this.account?.type?.id}" from="${com.next.AccountType.list()}" optionKey="id" optionValue="name" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">上级机构</label>
                            <div class="col-md-3">
                                <g:select name="parent.id" value="${this.account?.parent?.id}" from="${com.next.Account.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton class="btn btn-info" name="update" value="修改" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

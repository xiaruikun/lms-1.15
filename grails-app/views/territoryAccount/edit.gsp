<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'territoryAccount.label', default: 'TerritoryAccount')}" />
        <title>编辑区域-公司</title>
    </head>
<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territoryAccount" action="index">区域-公司</g:link>
                        </li>
                        <li class="active">
                            <span>编辑</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    区域-公司
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    区域-公司信息编辑
                </div>
                <div class="panel-body">
                    <g:form resource="${this.territoryAccount}" method="PUT" class="form-horizontal">
                        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.territoryAccount}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.territoryAccount}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">区域名称:</label>
                            <div class="col-md-3">
                                <g:select name="territory.id" required id="territory" value="${this.territoryAccount?.territory}" from="${com.next.Territory.list()}" optionKey="id" optionValue="name"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">公司名称</label>
                            <div class="col-md-3">
                                <g:select name="account.id" required id="account" value="${this.territoryAccount?.account}" from="${com.next.Account.list()}" optionKey="id" optionValue="name"></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton class="btn btn-default" name="update" value="修改" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

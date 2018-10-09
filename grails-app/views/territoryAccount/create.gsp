<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryAccount.label', default: 'TerritoryAccount')}" />
    <title>新增区域-机构</title>

</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territoryAccount" action="index">区域-机构</g:link>
                        </li>
                        <li class="active">
                            <span>新增区域-机构</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    新增区域-机构
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增区域-机构
                </div>
                <div class="panel-body">
                    <g:form action="save" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.territoryAccount}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.territoryAccount}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">区域名称:</label>
                            <div class="col-md-3">
                                <g:if test="${this.territoryAccount?.territory != null && this.territoryAccount?.territory != ''}">
                                    <g:textField name="territory.id" required="required" id="territory" value="${this.territoryAccount?.territory?.id}" class="hide" />
                                    <g:textField name="territoryName" value="${this.territoryAccount?.territory?.name}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select class="form-control" name="territory.id" required="required" id="territory" value="${this.territoryAccount?.territory}" from="${com.next.Territory.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">机构名称</label>
                            <div class="col-md-3">
                                <g:if test="${this.territoryAccount?.account != null && this.territoryAccount?.account != ''}">
                                    <g:textField name="account.id" required="required" id="account" value="${this.territoryAccount?.account?.id}" class="hide" />
                                    <g:textField name="accountName" value="${this.territoryAccount?.account?.name}" readonly="readonly" class="form-control" />
                                </g:if>
                                <g:else>
                                    <g:select class="form-control" name="account.id" required="required" id="account" value="${this.territoryAccount?.account}" from="${com.next.Account.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                </g:else>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">开始时间</label>
                            <div class="col-md-3 datePicker">
                                <g:datePicker name="startTime" value="${new Date()}" precision="day" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">结束时间</label>
                            <div class="col-md-3 datePicker">
                                <g:datePicker name="endTime" value="${new Date()}" precision="day" />
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

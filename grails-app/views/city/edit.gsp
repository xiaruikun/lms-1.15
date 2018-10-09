<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'city.label', default: 'City')}" />
    <title>城市：${city.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="city" action="index">城市管理</g:link>
                        </li>
                        <li class="active">
                            <span>${this.city?.name}</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    城市: ${this.city?.name}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    城市信息编辑
                </div>
                <div class="panel-body">
                    <g:form resource="${this.city}" method="PUT" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.city}">
                            <ul class="errors" role="alert">
                                <g:eachError bean="${this.city}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">城市名称</label>
                            <div class="col-md-3">
                                <g:textField name="name" value="${this.city?.name}" readonly="readonly" disabled="" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">城市热线</label>
                            <div class="col-md-3">
                                <g:textField name="telephone" value="${this.city?.telephone}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">是否必填邀请码</label>
                            <div class="col-md-3 checkbox-inline">
                                <g:radioGroup class="checkbox-inline" name="invitationCode" labels="['是', '否']"
                                              value="${this.city?.invitationCode}" values="[true, false]">
                                    ${it.radio} ${it.label}
                                </g:radioGroup>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">默认邀请码</label>
                            <div class="col-md-3">
                                <g:textField name="defaultInvitationCode" value="${this.city?.defaultInvitationCode}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">报单类型</label>
                            <div class="col-md-3">
                                <g:select class="form-control" name="loanApplicationProcessType.id"
                                          value="${this.city?.loanApplicationProcessType?.id}"
                                          from="${com.next.LoanApplicationProcessType.list()}" optionKey="id"
                                          optionValue="name">
                                </g:select>
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

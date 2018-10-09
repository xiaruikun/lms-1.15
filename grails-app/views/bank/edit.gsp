<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bank.label', default: 'Bank')}"/>
    <title>银行编辑</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="bank" action="index">银行</g:link>
                    </li>
                    <li class="active">
                        <span>修改</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                修改银行
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                修改银行
            </div>

            <div class="panel-body">
                <g:form resource="${this.bank}" method="PUT" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.bank}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.bank}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <div class="form-group">
                        <label class="col-md-3 control-label">银行代码</label>

                        <div class="col-md-3">
                            <g:textField name="code" value="${this.bank?.code}" readOnly="readOnly"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">银行名称</label>

                        <div class="col-md-3">
                            <g:textField name="name" value="${this.bank?.name}" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">描述</label>

                        <div class="col-md-3">
                            <g:textField name="description" value="${this.bank?.description}" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">支付类型</label>

                        <div class="col-md-3">
                            <g:select name="type" value="${this.bank?.type}" from="${['Funds Pay', 'Quick Pay']}"
                                      class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">有效性</label>

                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline" name="active" value="${this.bank?.active}"
                                          labels="['true', 'false']" values="[true, false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">单笔限额</label>

                        <div class="col-md-3">
                            <g:textField name="singleLimit" value="${this.bank?.singleLimit}" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">日累计限额</label>

                        <div class="col-md-3">
                            <g:textField name="dailyLimit" value="${this.bank?.dailyLimit}" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">月累计限额</label>

                        <div class="col-md-3">
                            <g:textField name="monthlyLimit" value="${this.bank?.monthlyLimit}" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="修改"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

%{-- 
        <a href="#edit-bank" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-bank" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.bank}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.bank}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.bank}" method="PUT">
                <g:hiddenField name="version" value="${this.bank?.version}" />
                <fieldset class="form">
                    <f:all bean="bank"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div> --}%
</body>
</html>

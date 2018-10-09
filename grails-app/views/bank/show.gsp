<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'bank.label', default: 'Bank')}"/>
    <title>银行</title>
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
                        <span>${this.bank?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                ${this.bank?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.bank}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                </div>
                ${this.bank?.name}
            </div>

            <div class="panel-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">银行代码：</label>

                        <div class="col-md-3">
                            <g:textField name="code" value="${this.bank?.code}" readOnly="readOnly"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">银行名称：</label>

                        <div class="col-md-3">
                            <g:textField name="name" value="${this.bank?.name}" readOnly="readOnly"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">描述：</label>

                        <div class="col-md-3">
                            <g:textField name="description" value="${this.bank?.description}" readOnly="readOnly"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">支付类型：</label>

                        <div class="col-md-3">
                            <g:textField name="type" value="${this.bank?.type}" readOnly="readOnly"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">有效性：</label>

                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline" readOnly="readOnly" name="active"
                                          value="${this.bank?.active}" labels="['true', 'false']"
                                          values="[true, false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">单笔限额：</label>

                        <div class="col-md-3">
                            <g:textField name="singleLimit" value="${this.bank?.singleLimit}" readOnly="readOnly"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">日累计限额：</label>

                        <div class="col-md-3">
                            <g:textField name="dailyLimit" value="${this.bank?.dailyLimit}" readOnly="readOnly"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">月累计限额：</label>

                        <div class="col-md-3">
                            <g:textField name="monthlyLimit" value="${this.bank?.monthlyLimit}" readOnly="readOnly"
                                         class="form-control"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
%{-- 
        <a href="#show-bank" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-bank" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="bank" />
            <g:form resource="${this.bank}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.bank}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div> --}%
</body>
</html>

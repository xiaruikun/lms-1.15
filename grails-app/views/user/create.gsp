<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'user')}"/>
    <title>新建用户</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="user" action="index">用户管理</g:link>
                    </li>
                    <li class="active">
                        <span>新增用户</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                用户管理
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增用户
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.user}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.user}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">员工姓名</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="fullName" id="fullName"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">所属部门</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="department" optionKey="id" optionValue="name" from="${com.next.Department.list()}" noSelection="['':'-请选择-']"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">岗位</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="position" optionKey="id" optionValue="name" from="${com.next.Position.list()}" noSelection="['':'-请选择-']"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">员工手机</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="cellphone" id="cellphone" MaxLength="11"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">所在城市</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="city" optionKey="id" optionValue="name" from="${com.next.City.list()}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">用户名</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="username" id="username"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">密码设置</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="password" name="password" id="password"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">所属公司</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="account" value="${this.user?.account?.id}" id="account" optionKey="id" optionValue="name" from="${com.next.Account.list()}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">外部唯一ID</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="externalId" id="externalId"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">启用</label>
                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline" name="enabled" value="${this.user?.enabled}" labels="['true','false']" values="[true,false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">账户过期</label>
                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline" name="accountExpired" value="${this.user?.accountExpired}" labels="['true','false']" values="[true,false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">账户锁定</label>
                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline" name="accountLocked" value="${this.user?.accountLocked}" labels="['true','false']" values="[true,false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">密码过期</label>
                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline" name="passwordExpired" value="${this.user?.passwordExpired}" labels="['true','false']" values="[true,false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="create" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

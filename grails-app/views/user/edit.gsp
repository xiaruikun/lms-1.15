<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title>用户：${user.fullName}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="user" action="index">用戶管理</g:link>
                    </li>
                    <li class="active">
                        <span>${this.user?.fullName}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                用戶: ${this.user?.fullName}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${this.user}">
            <ul class="text-danger" role="alert">
                <g:eachError bean="${this.user}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                        <g:message error="${error}"/>
                    </li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <div class="hpanel hblue">
            <div class="panel-heading">
                用戶信息编辑
            </div>

            <div class="panel-body">
                <g:form resource="${this.user}" method="PUT" class="form-horizontal">

                    <div class="form-group">
                        <label class="col-md-2 control-label">员工姓名</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="fullName" id="fullName"
                                         value="${this.user.fullName}"/>
                        </div>
                        <label class="col-md-2 control-label">手机号码</label>

                        <div class="col-md-3">
                            <g:textField class="cellphoneFormat2 form-control" name="userCellphone"
                                         value="${this.user?.cellphone}"></g:textField>
                            <input class="hiddenCellphone" type="hidden" name="cellphone"
                                   value="${this.user?.cellphone}">
                        </div>
                    </div>


                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">所属部门</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="department" value="${this.user?.department?.id}"
                                      optionKey="id" optionValue="name" from="${com.next.Department.list()}"
                                      noSelection="['': '-请选择-']"/>
                        </div>
                        <label class="col-md-2 control-label">岗位</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="position" value="${this.user?.position?.id}"
                                      optionKey="id" optionValue="name" from="${com.next.Position.list()}"
                                      noSelection="['': '-请选择-']"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">所在城市</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="city" value="${this.user?.city?.id}" optionKey="id"
                                      optionValue="name" from="${com.next.City.list()}"/>
                        </div>
                        <label class="col-md-2 control-label">所属公司</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="account" value="${this.user?.account?.id}" id="account"
                                      optionKey="id" optionValue="name" from="${com.next.Account.list()}"/>
                        </div>
                    </div>


                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">外部唯一ID</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="externalId" id="externalId"
                                         value="${this.user?.externalId}"/>
                        </div>
                        <label class="col-md-2 control-label">截取权限</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="securityProfile.id" id="${securityProfile}"
                                      value="${this.user?.securityProfile?.id}" optionKey="id" optionValue="name"
                                      from="${com.next.SecurityProfile.list()}" noSelection="['': '-请选择-']"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">启用</label>

                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline i-checks" name="enabled" value="${this.user?.enabled}"
                                          labels="['true', 'false']" values="[true, false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                        <label class="col-md-2 control-label">账户过期</label>

                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline i-checks" name="accountExpired"
                                          value="${this.user?.accountExpired}" labels="['true', 'false']"
                                          values="[true, false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">账户锁定</label>

                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline i-checks" name="accountLocked"
                                          value="${this.user?.accountLocked}" labels="['true', 'false']"
                                          values="[true, false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                        <label class="col-md-2 control-label">密码过期</label>

                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline i-checks" name="passwordExpired"
                                          value="${this.user?.passwordExpired}" labels="['true', 'false']"
                                          values="[true, false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">短信登录</label>

                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline i-checks" name="loginBySms"
                                          value="${this.user?.loginBySms}" labels="['true', 'false']"
                                          values="[true, false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                        <label class="col-md-2 control-label">固定ip</label>

                        <div class="col-md-3 checkbox-inline">
                            <g:radioGroup class="checkbox-inline i-checks" name="fixedIp" value="${this.user?.fixedIp}"
                                          labels="['true', 'false']" values="[true, false]">
                                ${it.radio} ${it.label}
                            </g:radioGroup>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">IP地址</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" type="text" name="ip" id="ip" value="${this.user?.ip}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-4">
                            <button class="btn btn-info" type="submit">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
<script>
    $(".cellphoneFormat2").focus(function () {
        $(this).val("");
        $(".hiddenCellphone").val("")
    })
    $(".cellphoneFormat2").blur(function () {
        $(".hiddenCellphone").val($(".cellphoneFormat2").val());
    });

</script>
</body>
</html>

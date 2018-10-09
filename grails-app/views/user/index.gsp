<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'user')}"/>
    <title>用户管理</title>
    <style>
    .table>tbody>tr>td{
        vertical-align: middle;
    }
    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>用户管理</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                用户管理
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有用户</small>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <g:form method="POST" action="searchUser">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>

                <div class="panel-body">
                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="员工姓名" name="fullName" id="fullName"
                               value="${user?.fullName}">
                    </div>

                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="邀请码" name="code" id="code"
                               value="${user?.code}">
                    </div>

                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="所在城市" name="city" id="city"
                               value="${user?.city?.name}">
                    </div>

                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="所属部门" name="department" id="department"
                               value="${user?.department?.name}">
                    </div>
                </div>
            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                全部用户
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'user.fullName.label', default: '员工姓名')}"/>
                            %{--<g:sortableColumn property="cellphone"
                                              title="${message(code: 'user.cellphone.label', default: '员工手机')}"/>--}%
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'user.fullName.label', default: '所在城市')}"/>
                            <g:sortableColumn property="department"
                                              title="${message(code: 'user.department.label', default: '所在部门')}"/>
                            <g:sortableColumn property="position"
                                              title="${message(code: 'user.position.label', default: '岗位')}"/>
                            <g:sortableColumn property="code"
                                              title="${message(code: 'user.code.label', default: '邀请码')}"/>
                            <g:sortableColumn property="externalId"
                                              title="${message(code: 'user.externalId.label', default: '外部唯一ID')}"/>
                            <g:sortableColumn property="account"
                                              title="${message(code: 'user.account.label', default: '所属公司')}"/>

                            <g:sortableColumn property="enabled"
                                              title="${message(code: 'user.enabled.label', default: '启用')}"/>
                            <g:sortableColumn property="accountExpired"
                                              title="${message(code: 'user.accountExpired.label', default: '账户过期')}"/>
                            <g:sortableColumn property="accountLocked"
                                              title="${message(code: 'user.accountLocked.label', default: '账户锁定')}"/>
                            <g:sortableColumn property="passwordExpired"
                                              title="${message(code: 'user.passwordExpired.label', default: '密码过期')}"/>
                            <g:sortableColumn property="loginBySms"
                                              title="${message(code: 'user.loginBySms.label', default: '短信登录')}"/>
                            <g:sortableColumn property="fiexedIp"
                                              title="${message(code: 'user.fixedIp.label', default: '固定IP')}"/>
                            <g:sortableColumn property="account"
                                              title="${message(code: 'user.account.label', default: '重置密码')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${userList}">
                            <tr>
                                <td><g:link action="show" id="${it?.id}"
                                            class="firstTd">${it?.fullName}</g:link></td>
                                %{--<td>${it?.cellphone}</td>--}%
                                <td>${it?.city?.name}</td>
                                <td>${it?.department?.name}</td>
                                <td>${it?.position?.name}</td>
                                <td>${it?.code}</td>
                                <td>${it?.externalId}</td>
                                <td>${it?.account?.name}</td>
                                <td>${it?.enabled}</td>
                                <td>${it?.accountExpired}</td>
                                <td>${it?.accountLocked}</td>
                                <td>${it?.passwordExpired}</td>
                                <td>${it?.loginBySms}</td>
                                <td>${it?.fixedIp}</td>

                                <td>
                                    <g:form controller="user" action="resetPassword">
                                        <div class="input-group">
                                            <input type="hidden" name="destination" value="user">
                                            <input type="hidden" name="user" value="${it?.id}">
                                            <input class="form-control" type="password" name="password" autocomplete="off">
                                            <span class="input-group-addon" style="padding: 0 12px">
                                                <button class="btn btn-success btn-xs" type="submit">提交</button>
                                            </span>
                                        </div>


                                    </g:form>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${userCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    $(function () {
        $("#resetBtn").click(function () {
            $("#fullName").val("");
            $("#department").val("");
            $("#city").val("");
            $("#code").val("");
        })
    });

</g:javascript>
</body>
</html>

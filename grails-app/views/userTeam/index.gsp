<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'userTeam.label', default: 'UserTeam')}"/>
    <title>人员管理</title>
    <style>
    .table>tbody>tr>td{
        vertical-align: middle;
    }
    </style>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>人员管理</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                人员管理
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有人员</small>
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
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                全部人员
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'user.fullName.label', default: '员工姓名')}"/>
                            <g:sortableColumn property="cellphone"
                                              title="${message(code: 'user.cellphone.label', default: '员工手机')}"/>
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
                            <g:sortableColumn property="account"
                                              title="${message(code: 'user.account.label', default: '重置密码')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${userTeamList}">
                            <tr>
                                <td><g:link action="show" id="${it?.id}"
                                            class="firstTd">${it?.member?.fullName}</g:link></td>
                                <td class="cellphoneFormat">${it?.member?.cellphone}</td>
                                <td>${it?.member?.city?.name}</td>
                                <td>${it?.member?.department?.name}</td>
                                <td>${it?.member?.position?.name}</td>
                                <td>${it?.member?.code}</td>
                                <td>${it?.member?.externalId}</td>
                                <td>${it?.member?.account?.name}</td>
                                <td>
                                    <g:form controller="user" action="resetPassword">
                                        <div class="input-group">
                                            <input type="hidden" name="user" value="${it?.member?.id}">
                                            <input class="form-control" type="password" name="password"
                                                   autocomplete="off">
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
                    <g:paginate total="${userTeamCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

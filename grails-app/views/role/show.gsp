<!DOCTYPE html>
<html>

    <head>
        <meta name="layout" content="main"/>
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}"/>
        <title>角色：${role.authority}</title>
    </head>

    <body class="fixed-navbar fixed-sidebar">
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li>
                                <g:link controller="role" action="index">角色管理</g:link>
                            </li>
                            <li class="active">
                                <span>${this.role?.authority}</span>
                            </li>
                        </ol>
                    </div>
                    <h2 class="font-light m-b-xs">
                        角色: ${this.role?.authority}
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" action="edit" resource="${this.role}">
                                <i class="fa fa-edit"></i>编辑</g:link>
                        </div>
                        角色详情
                    </div>
                    <div class="panel-body">
                        <div class="text-muted font-bold m-b-xs ol-md-6">
                            <div class="col-md-12">
                                <h4>
                                    <a href=>${this.role?.authority}</a>
                                </h4>
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer contact-footer">
                        <div class="row">
                            <div class="col-md-4 border-right">
                                <div class="contact-stat">
                                  <span>描述</span>
                                  <strong>${this.role?.description}</strong>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="contact-stat border-right">
                                    <span>用户数量</span>
                                    <strong>${this.userList?.size()}</strong>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="contact-stat">
                                    <span>菜单</span>
                                    <strong>${this.role?.menu?.name}</strong>
                                </div>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
            <div class="row">
                <div class="hpanel hviolet">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <a class="showhide">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                        用户
                    </div>

                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <g:sortableColumn property="fullName" title="${message(code: 'user.fullName.label', default: '姓名')}"/>
                                        <g:sortableColumn property="cellphone" title="${message(code: 'user.cellphone.label', default: '手机号')}"/>
                                        <g:sortableColumn property="city" title="${message(code: 'user.city.label', default: '所在城市')}"/>
                                        <g:sortableColumn property="department" title="${message(code: 'user.department.label', default: '部门')}"/>
                                        <g:sortableColumn property="position" title="${message(code: 'user.position.label', default: '岗位')}"/>
                                        <g:sortableColumn property="account" title="${message(code: 'user.account.label', default: '所属公司')}"/>

                                    </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${userList}">
                                            <tr>
                                                <td>
                                                    <g:link action="show" resource="${it}" class="firstTd">${it.fullName}</g:link>
                                                </td>
                                                <td class="cellphoneFormat">${it.cellphone}</td>
                                                <td>${it.city?.name}</td>
                                                <td>
                                                    ${it?.department?.name}
                                                </td>
                                                <td>${it.position?.name}</td>
                                                <td>${it.account?.name}</td>

                                            </tr>
                                    </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </body>

</html>

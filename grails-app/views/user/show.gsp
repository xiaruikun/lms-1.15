<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>用户: ${this.user.fullName}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="user" action="index">用户管理</g:link></li>
                    <li class="active">
                        <span>${this.user.fullName}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                用户: ${this.user.fullName}
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>${this.user.fullName} 的资料</small>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit"
                                resource="${this.user}"><i class="fa fa-edit"></i>编辑</g:link>
                    </div>
                </sec:ifAnyGranted>
                用户基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12"><h4><a href=>${this.user.fullName}</a></h4></div>

                    <div class="col-md-1"><strong>${this.user.city?.name}</strong></div>

                    <div class="col-md-1"><strong>${this.user.department?.name}</strong></div>

                    <div class="col-md-1"><strong>${this.user.position?.name}</strong></div>

                    <div class="col-md-1"><strong class="cellphoneFormat">${this.user.cellphone}</strong></div>

                    <div class="col-md-1"><strong>${this.user?.externalId}</strong></div>
                    <div class="col-md-1"><strong>启用：${this.user?.enabled}</strong></div>

                    <div class="col-md-1"><strong>账户过期：${this.user?.accountExpired}</strong></div>

                    <div class="col-md-1"><strong>账户锁定：${this.user?.accountLocked}</strong></div>

                    <div class="col-md-1"><strong>密码过期：${this.user?.passwordExpired}</strong></div>

                    <div class="col-md-1"><strong>短信登录：${this.user?.loginBySms}</strong></div>

                    <div class="col-md-1"><strong>固定ip：${this.user?.fixedIp}</strong></div>

                    <span class="label label-info pull-right" style="padding:5px 6px">邀请码: ${this.user.code}</span>

                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-4 border-right">
                        <div class="contact-stat"><span>经理人数量:</span> <strong>${com.next.Contact.countByUser(this.user)}</strong>
                        </div>
                    </div>

                    <div class="col-md-4 border-right">
                        <div class="contact-stat"><span>订单数量:</span> <strong>${com.next.Opportunity.countByUser(this.user)}</strong>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="contact-stat"><span>成交金额:</span> <strong>0</strong></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hviolet">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                下属经纪人
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'contact.serialNumber.label', default: '经纪人姓名')}"/>
                            <g:sortableColumn property="cellphone"
                                              title="${message(code: 'contact.cellphone.label', default: '经纪人手机')}"/>
                            <g:sortableColumn property="city"
                                              title="${message(code: 'contact.city.label', default: '所在城市')}"/>
                            <g:sortableColumn property="opportunity.stage"
                                              title="${message(code: 'opportunity.stage.label', default: '累计申请订单数')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunity.stage.label', default: '累计成功订单数')}"/>
                            <g:sortableColumn property="createdDate"
                                              title="${message(code: 'contact.createdDate.label', default: '最近申请时间')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${contactList}">
                            <div class="col-md-4">
                                <tr>
                                    <td><g:link action="show" resource="${it}"
                                                class="firstTd">${it.fullName}</g:link></td>
                                    <td class="cellphoneFormat">${it.cellphone}</td>
                                    <td>${it.city}</td>
                                    <td>${com.next.Opportunity.countByContact(it)}</td>
                                    <td>${com.next.Opportunity.countByContactAndStatus(it, "Completed")}</td>
                                    <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/></td>
                                </tr>
                            </div>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${contactListCount ?: 0}" params="${params}"/>
                    %{--<g:paginate total="${totalFoos}" max="10" offset="${session.fooPagination?.offset}" params="${[paginate:'Foo']}"/></td>--}%

                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                相关订单
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="serialNumber"
                                              title="${message(code: 'opportunity.serialNumber.label', default: '订单编号')}"/>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'opportunity.fullName.label', default: '借款人姓名')}"/>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'opportunity.contact.fullName.label', default: '经纪人姓名')}"/>
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunity.stage.label', default: '订单状态')}"/>
                            <g:sortableColumn property="createdDate"
                                              title="${message(code: 'opportunity.createdDate.label', default: '申请时间')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${opportunityList}">
                            <tr>
                                <td><g:link controller="opportunity" action="show" id="${it.id}"
                                            class="firstTd">${it.serialNumber}</g:link></td>
                                <td>${it.fullName}</td>
                                <td>${it.contact?.fullName}</td>
                                <td>${it.stage?.name}</td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${opportunityListCount ?: 0}" params="${params}"/>
                %{--<g:paginate total="${totalBars}" max="10" offset="${session.barPagination?.offset}" params="${[paginate:'Bar']}"/></td>--}%

                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel  hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                        <g:link controller="userRole" action="create" id="${this.user.id}"
                                class="btn btn-info btn-xs">增加授权</g:link>
                    </sec:ifAllGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                用户角色
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <tbody>
                        <g:each in="${userRoleList}" var="r">
                            <tr>
                                <td>${r?.role.description}</td>
                                <td width="6%">
                                    <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                        <g:link action="deleteRole" params="[userId: r.user.id, roleId: r.role.id]"
                                                class="btn btn-danger btn-block btn-xs btn-block">取消授权</g:link>
                                    </sec:ifAllGranted>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>

    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                  <div class="panel-tools">
                      <g:link class="btn btn-xs btn-info" controller="reporting" action="create"
                              id="${this.user.id}"><i class="fa fa-plus"></i>新增</g:link>
                      <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                  </div>
                </sec:ifAllGranted>
                下属员工
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="fullName"
                                              title="${message(code: 'user.fullName.label', default: '姓名')}"/>
                            <g:sortableColumn property="cellphone"
                                              title="${message(code: 'user.cellphone.label', default: '电话')}"/>
                                                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                            <g:sortableColumn property="operation"
                                              title="${message(code: '操作')}"/>
                                            </sec:ifAllGranted>
                        </thead>
                        <tbody>
                        <g:each in="${reportingList}">
                            <div class="col-md-4">
                                <tr>

                                    <td>${it.user?.fullName}</td>
                                    <td class="cellphoneFormat">${it.user?.cellphone}</td>
                                      <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                    <td>
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                  </sec:ifAllGranted>
                                </tr>
                            </div>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                下户信息
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>

                            <g:sortableColumn property="type"
                                              title="${message(code: 'activity.type.label', default: '活动类型')}"/>
                            <g:sortableColumn property="subtype"
                                              title="${message(code: 'activity.subtype.label', default: '子类型')}"/>
                            <g:sortableColumn property="startTime"
                                              title="${message(code: 'activity.startTime.label', default: '开始时间')}"/>
                            <g:sortableColumn property="endTime"
                                              title="${message(code: 'activity.endTime.label', default: '结束时间')}"/>
                            <g:sortableColumn property="actualStartTime"
                                              title="${message(code: 'activity.actualStartTime.label', default: '实际开始时间')}"/>
                            <g:sortableColumn property="actualEndTime"
                                              title="${message(code: 'activity.actualEndTime.label', default: '实际结束时间')}"/>
                            <g:sortableColumn property="contact"
                                              title="${message(code: 'activity.contact.label', default: '经纪人姓名')}"/>
                            <g:sortableColumn property="user"
                                              title="${message(code: 'activity.user.label', default: '所有者')}"/>
                            <g:sortableColumn property="assignedTo"
                                              title="${message(code: 'activity.assignedTo.label', default: '下户人')}"/>
                            <g:sortableColumn property="status"
                                              title="${message(code: 'activity.status.label', default: '状态')}"/>
                            <g:sortableColumn property="city"
                                              title="${message(code: 'activity.city.label', default: '城市')}"/>
                            <g:sortableColumn property="address"
                                              title="${message(code: 'activity.address.label', default: '地址')}"/>
                            <g:sortableColumn property="longitude"
                                              title="${message(code: 'activity.longitude.label', default: '经度')}"/>
                            <g:sortableColumn property="latitude"
                                              title="${message(code: 'activity.latitude.label', default: '维度')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.activityList}">
                            <tr>
                                <td><g:link controller="activity" action="show" id="${it.id}"
                                            class="firstTd">${it?.type?.name}</g:link></td>
                                <td>${it?.subtype?.name}</td>
                                <td><g:formatDate class="weui_input" date="${it?.startTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="startTime" autocomplete="off"
                                                  readonly="true"></g:formatDate></td>
                                <td><g:formatDate class="weui_input" date="${it?.endTime}" format="yyyy-MM-dd HH:mm:ss"
                                                  name="endTime" autocomplete="off" readonly="true"></g:formatDate></td>
                                <td><g:formatDate class="weui_input" date="${it?.actualStartTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="actualStartTime" autocomplete="off"
                                                  readonly="true"></g:formatDate></td>
                                <td><g:formatDate class="weui_input" date="${it?.actualEndTime}"
                                                  format="yyyy-MM-dd HH:mm:ss" name="actualEndTime" autocomplete="off"
                                                  readonly="true"></g:formatDate></td>
                                <td>${it?.contact?.fullName}</td>
                                <td>${it?.user?.fullName}</td>
                                <td>${it?.assignedTo?.fullName}</td>
                                <td>${it?.status}</td>
                                <td>${it?.city}</td>
                                <td>${it?.address}</td>
                                <td>${it?.longitude}</td>
                                <td>${it?.latitude}</td>
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

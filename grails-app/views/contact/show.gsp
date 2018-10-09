<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>经纪人: ${this.contact.fullName}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="contact" action="index">经纪人管理</g:link></li>
                    <li class="active">
                        <span>${this.contact.fullName}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                经纪人: ${this.contact.fullName}
            </h2>
            <small><span class="glyphicon glyphicon-user" aria-hidden="true"></span>${this.contact.fullName} 的资料
            </small>
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
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" action="edit"
                                resource="${this.contact}"><i class="fa fa-edit"></i>编辑</g:link>
                    </sec:ifNotGranted>
                </div>
                经纪人基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">

                    <div class="col-md-12"><h4>${this.contact?.fullName}</h4></div>

                    <div class="col-md-2"><strong >经纪人手机号：<g:if test="${sec.loggedInUserInfo(field: 'username')  == this.contact?.user?.username}">${this.contact?.cellphone}</g:if><g:else><span class="cellphoneFormat">${this.contact?.cellphone}</span></g:else></strong></div>

                    <div class="col-md-2"><strong>所在城市：${this.contact?.city?.name}</strong></div>

                    <div class="col-md-2"><strong>支持经理：${this.contact?.user?.fullName}</strong></div>

                    <div class="col-md-2"><strong>支持经理手机号：<g:if test="${sec.loggedInUserInfo(field: 'username')  == this.contact?.user?.username}">${this.contact?.user?.cellphone}</g:if><g:else><span class="cellphoneFormat">${this.contact?.user?.cellphone}</span></g:else></strong></div>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-3 border-right"><div
                            class="contact-stat"><span>订单数量</span> <strong>${com.next.Opportunity.countByContact(this.contact)}</strong>
                    </div></div>

                    <div class="col-md-3 border-right"><div
                            class="contact-stat"><span>已成功订单:</span> <strong>${com.next.Opportunity.countByContactAndStatus(this.contact, 'Completed')}</strong>
                    </div></div>

                    <div class="col-md-3 border-right "><div
                            class="contact-stat"><span>成交金额</span> <strong>0</strong></div></div>

                    <div class="col-md-3 border-right"><div
                            class="contact-stat"><span>已返点：</span> <strong>0</strong></div></div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-3 control-label">经纪人银行类别</label>

                    <div class="col-md-2">
                        <span class="cont">${this.contact.bankName}</span>
                    </div>
                    <label class="col-md-3 control-label">经纪人银行卡号</label>

                    <div class="col-md-2">
                        <span class="cont">${this.contact.bankAccount}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">经纪人身份证号</label>

                    <div class="col-md-2">
                        <span class="cont">${this.contact.idNumber}</span>
                    </div>
                    <label class="col-md-3 control-label">注册时间</label>

                    <div class="col-md-2">
                        <span class="cont"><g:formatDate format="yyyy-MM-dd HH:mm:ss"
                                                         date="${this.contact.createdDate}"/></span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">最近登陆时间</label>

                    <div class="col-md-2">
                        <span class="cont"><g:formatDate format="yyyy-MM-dd HH:mm:ss"
                                                         date="${this.contact.lastLoginTimestamp}"/></span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                业务信息
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
                            <g:sortableColumn property="stage"
                                              title="${message(code: 'opportunity.stage.label', default: '订单状态')}"/>
                            <g:sortableColumn property="status"
                                              title="${message(code: 'opportunity.status.label', default: '状态描述')}"/>
                            <g:sortableColumn property="createdDate"
                                              title="${message(code: 'opportunity.createdDate.label', default: '申请时间')}"/>
                        </thead>
                        <tbody>
                        <g:each in="${opportunityList}">
                            <div class="col-md-4">
                                <tr>
                                    <td><g:link controller="opportunity" action="show"
                                                id="${it.id}">${it.serialNumber}</g:link></td>
                                    <td>${it.fullName}</td>
                                    <td>${it.stage?.name}</td>
                                    <td>${it.status}</td>
                                    <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/></td>
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
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" controller="contactTeam" action="create"
                                params="[contact: this.contact?.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                团队经纪人
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="user"
                                              title="${message(code: 'contactTeam.user.label', default: '用户')}"/>
                            <g:sortableColumn property="teamRole"
                                              title="${message(code: 'contactTeam.teamRole.label', default: '权限')}"/>
                            <g:sortableColumn class="text-center" width="8%" property="comments"
                                              title="操作"/>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.contactTeamList}">
                            <tr>
                                <td>
                                    ${it?.user}
                                </td>
                                <td>${it?.teamRole?.name}</td>
                                <td>
                                    <g:form resource="${it}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                </td>
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

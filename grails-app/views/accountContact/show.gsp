<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'AccountContact.label', default: 'AccountContact')}"/>
    <title>离职人员详情</title>
</head>
<body class="fixed-navbar fixed-sidebar">
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>离职人员详情</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                离职人员详情
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.accountContact}"><i class="fa fa-edit"></i> 编辑</g:link>
                    <g:form resource="${this.accountContact}" method="DELETE" style="display: inline-block">
                        <button class="deleteBtn btn btn-danger btn-xs" type="button">
                            <i class="fa fa-trash-o"></i>
                            删除
                        </button>
                    </g:form>
                </div>
                离职人员基本信息
            </div>
            <div class="panel-body form-horizontal">
                <div class="">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            ${flash.message}
                        </div>
                    </g:if>
                    <div class="widget-body ">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">公司名称</label>

                                <div class="col-md-4">
                                    <input class="form-control" disabled="disabled"
                                           type="text" value="${this.accountContact?.account}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">员工姓名</label>

                                <div class="col-md-4">
                                    <input class="form-control" disabled="disabled" type="text"
                                           value="${this.accountContact?.contact?.fullName}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">公司编号</label>

                                <div class="col-md-4">
                                    <input class="form-control" disabled="disabled" type="text"
                                           value="${this.accountContact?.accountExternalId}">
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-md-2 control-label">员工编号</label>

                                <div class="col-md-4">
                                    <input class="form-control" disabled="disabled" type="text"
                                           value="${this.accountContact?.contactExternalId}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">入职时间</label>

                                <div class="col-md-4">

                                    <input class="form-control" disabled="disabled"
                                           type="text" value="<g:formatDate date="${this.accountContact?.hiredate}"
                                                                            format="yyyy-MM-dd"></g:formatDate>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">离职时间</label>
                                <div class="col-md-4">
                                    <input class="form-control" disabled="disabled"
                                           type="text" value="<g:formatDate date="${this.accountContact?.leavedate}"
                                                                            format="yyyy-MM-dd"></g:formatDate>">
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

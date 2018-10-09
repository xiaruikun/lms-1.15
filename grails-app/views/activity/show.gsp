<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
<div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="activity" action="index">预约</g:link>
                        </li>
                        <li class="active">
                            <span>预约详情</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    预约
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                <div class="panel-tools">
                <g:link class="btn btn-info btn-xs" controller="activity" id="${this.activity?.id}"
                            action="edit"><i class="fa fa-edit"></i>编辑</g:link>
                </div>
                    预约详情
                </div>
                <div class="panel-body form-horizontal">

                        <div class="form-group">
                           <label class="col-md-2 control-label">订单编号：</label>
                            <div class="col-md-3">
                                <g:textField name="opportunityName" value="${this.activity?.opportunity?.serialNumber}" disabled="" class="form-control" />
                            </div>
                           <label class="col-md-2 control-label">经纪人：</label>
                            <div class="col-md-3">
                                <g:textField name="contactName" value="${this.activity?.opportunity?.contact?.fullName}" disabled="" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                           <label class="col-md-2 control-label">所有者：</label>
                            <div class="col-md-3">
                                <g:textField name="userName" value="${this.activity?.user?.fullName}" disabled="" class="form-control" />
                            </div>
                           <label class="col-md-2 control-label">活动类型：</label>
                            <div class="col-md-3">
                                <g:textField id="type" name='type' value="${this.activity?.type?.name}" disabled="" class="form-control"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                           <label class="col-md-2 control-label">开始时间：</label>
                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled" type="text" value="<g:formatDate date="${this.activity?.startTime}" format="yyyy-MM-dd HH:mm:ss"></g:formatDate>">
                            </div>
                           <label class="col-md-2 control-label">结束时间：</label>
                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled" type="text" value="<g:formatDate date="${this.activity?.endTime}" format="yyyy-MM-dd HH:mm:ss"></g:formatDate>">

                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                           <label class="col-md-2 control-label">实际开始时间：</label>
                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled" type="text" value="<g:formatDate date="${this.activity?.actualStartTime}" format="yyyy-MM-dd HH:mm:ss"></g:formatDate>">
                            </div>
                           <label class="col-md-2 control-label">实际结束时间：</label>
                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled" type="text" value="<g:formatDate date="${this.activity?.actualEndTime}" format="yyyy-MM-dd HH:mm:ss"></g:formatDate>">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                           <label class="col-md-2 control-label">下户人：</label>
                            <div class="col-md-3">
                                <g:textField name="assignedTo" value="${this.activity?.assignedTo?.fullName}" disabled class="form-control" />
                            </div>
                           <label class="col-md-2 control-label">状态：</label>
                            <div class="col-md-3">
                                <g:textField name="status" value="${this.activity?.status}" disabled class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                           <label class="col-md-2 control-label">城市：</label>
                            <div class="col-md-3">
                                <g:textField name="city" value="${this.activity?.city}" disabled class="form-control" />
                            </div>
                           <label class="col-md-2 control-label">地址：</label>
                            <div class="col-md-3">
                                <g:textField name="address" value="${this.activity?.address}" disabled class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                           <label class="col-md-2 control-label">经度：</label>
                            <div class="col-md-3">
                                <g:textField name="longitude" value="${this.activity?.longitude}" disabled class="form-control" />
                            </div>
                            <label class="col-md-2 control-label">纬度：</label>
                            <div class="col-md-3">
                                <g:textField name="latitude" value="${this.activity?.latitude}" disabled class="form-control" />
                            </div>
                        </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">子类型：</label>
                        <div class="col-md-3">
                            <g:textField id="subtype" name='subtype' value="${this.activity?.subtype?.name}" disabled="" class="form-control"></g:textField>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

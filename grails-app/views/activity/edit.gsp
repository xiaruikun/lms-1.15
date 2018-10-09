<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
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
                            <span>预约编辑</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    预约编辑
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                <div class="panel-tools">
                </div>
                    预约编辑
                </div>
                <div class="panel-body">
                    <g:form resource="${this.activity}" method="PUT" class="form-horizontal">
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">订单编号</label>
                            <div class="col-md-3">
                                <g:textField name="opportunity.id" id="opportunity" value="${this.activity?.opportunity?.id}" class="hide" />
                                <g:textField name="opportunityName" value="${this.activity?.opportunity?.serialNumber}" disabled class="form-control" />
                            </div>
                            <label class="col-md-2 control-label">经纪人</label>
                            <div class="col-md-3">
                                <g:textField name="contactName" value="${this.activity?.opportunity?.contact?.fullName}" disabled class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">所有者</label>
                            <div class="col-md-3">
                                    <g:textField name="userName" value="${this.activity?.user?.fullName}" disabled class="form-control" />
                            </div>
                            <label class="col-md-2 control-label">活动类型</label>
                            <div class="col-md-3">
                                <g:select id="type" name='type.id' value="${this.activity?.type?.id}" from='${com.next.ActivityType.list()}' optionKey="id" optionValue="name" class="form-control" ></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">子类型</label>
                            <div class="col-md-3">
                                <g:select id="subtype" name='subtype.id' value="${this.activity?.subtype?.id}" from='${com.next.ActivitySubtype.list()}' optionKey="id" optionValue="name" class="form-control" ></g:select>
                            </div>
                            <label class="col-md-2 control-label">下户人</label>
                            <div class="col-md-3">
                                <g:select id="assignedTo" name='assignedTo.id' value="${this.activity?.assignedTo?.id}" from='${com.next.User.list()}' optionKey="id" optionValue="fullName" noSelection="${['null':'请选择']}" class="form-control" ></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">开始时间</label>
                            <div class="col-md-4 datePicker">
                                <g:datePicker name="startTime" value="${this.activity?.startTime}" precision="minute" />
                            </div>
                            <label class="col-md-1 control-label">结束时间</label>
                            <div class="col-md-4 datePicker">
                                <g:datePicker name="endTime" value="${this.activity?.endTime}" precision="minute" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">实际开始时间</label>
                            <div class="col-md-4 datePicker">
                                <g:datePicker name="actualStartTime" value="${this.activity?.actualStartTime}" precision="minute" />
                            </div>
                            <label class="col-md-1 control-label">实际结束时间</label>
                            <div class="col-md-4 datePicker">
                                <g:datePicker name="actualEndTime" value="${this.activity?.actualEndTime}" precision="minute" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">状态</label>
                            <div class="col-md-3">
                                <g:select id="status" name='status' value="${this.activity?.status}" from="${['Pending', 'Delayed', 'Completed', 'Canceled']}" class="form-control" ></g:select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
                                <g:submitButton class="btn btn-info" name="update" value="保存" />
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

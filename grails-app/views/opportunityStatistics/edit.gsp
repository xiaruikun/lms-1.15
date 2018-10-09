<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <title>订单失败原因分析：</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="causeOfFailure" action="index">订单分析</g:link>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    订单失败原因详情分析
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    订单失败详情填写
                </div>
                <div class="panel-body">
                    <g:form action="update" method="PUT" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <div class="form-group">
                            <label class="col-md-3 control-label">订单编号</label>
                            <input name="id" value="${this.opportunity?.id}" type="hidden" readonly class="form-control" />
                            <div class="col-md-3">
                                <input name="serialNumber" value="${this.opportunity?.serialNumber}" readonly class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">客户名称</label>
                            <div class="col-md-3">
                                <input name="fullName" value="${this.opportunity?.fullName}" readonly class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">失败原因</label>
                            <div class="col-md-3">
                                <input name="causeOfFailure" value="${this.opportunity?.causeOfFailure}" readonly class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">失败原因分析</label>
                            <div class="col-md-3">
                                <g:textArea name="memo" value="${this.opportunity?.memo}" rows="5" class="form-control"/>
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

<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'territoryAccount.label', default: 'TerritoryAccount')}" />
    <title>区域-公司</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="territoryAccount" action="index">区域-公司</g:link>
                        </li>
                        <li class="active">
                            <span>区域-公司</span>
                        </li>
                    </ol>
                </div>

                <h2 class="font-light m-b-xs">
                    区域-公司
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-default btn-xs" action="edit" resource="${this.territoryAccount}">编辑</g:link>
                    </div>
                    区域-公司详情
                </div>
                <div class="panel-body">
                    <div class="row" style="padding: 20px;">
                        <label class="col-md-1 col-md-offset-3 control-label">区域名称：</label>
                        <div class="col-md-4">
                            <g:textField class="form-control" disabled="" name="territory.id" value="${this.territoryAccount?.territory?.name}"></g:textField>
                        </div>
                    </div>
                    <div class="row" style="padding: 20px;">
                        <label class="col-md-1 col-md-offset-3 control-label">公司名称：</label>
                        <div class="col-md-4">
                            <g:textField class="form-control" disabled="" name="account.id" value="${this.territoryAccount?.account?.name}"></g:textField>
                        </div>
                    </div>
                    <div class="row" style="padding: 20px;">
                        <label class="col-md-1 col-md-offset-3 control-label">开始时间：</label>
                        <div class="col-md-4">
                            <g:textField class="form-control" disabled="" name="startTime" value="${this.territoryAccount?.startTime}"></g:textField>
                        </div>
                    </div>
                    <div class="row" style="padding: 20px;">
                        <label class="col-md-1 col-md-offset-3 control-label">结束时间：</label>
                        <div class="col-md-4">
                            <g:textField class="form-control" disabled="" name="endTime" value="${this.territoryAccount?.endTime}"></g:textField>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

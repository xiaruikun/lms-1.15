<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'appConfigurationKey.label', default: 'AppConfigurationKey')}" />
        <title>APP邀请码详情</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
    <div class="small-header transition animated fadeIn">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="appConfigurationKey" action="index">App邀请码</g:link>
                        </li>
                        <li class="active">
                            <span>邀请码详情</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    邀请码详情
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.appConfigurationKey}"><i class="fa fa-edit"></i>编辑</g:link>
                    </div>
                    邀请码详情
                </div>
                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-1 col-md-offset-3 control-label">名称：</label>
                        <div class="col-md-4">
                            <g:textField class="form-control" disabled="" name="name" value="${this.appConfigurationKey?.name}"></g:textField>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

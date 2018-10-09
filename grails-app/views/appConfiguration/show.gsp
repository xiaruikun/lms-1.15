<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'appConfiguration.label', default: 'AppConfiguration')}" />
        <title>邀请码配置详情</title>
    </head>
    <body>
    <div class="small-header transition animated fadeIn">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="appConfiguration" action="index">App邀请码配置</g:link>
                        </li>
                        <li class="active">
                            <span>邀请码配置详情</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    邀请码配置详情
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <input type="hidden" name="appVersion" value="${params['id']}">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.appConfiguration}"><i class="fa fa-edit"></i>编辑</g:link>
                    </div>
                    ${com.next.AppVersion.findById(params['id'])?.appName} ${com.next.AppVersion.findById(params['id'])?.appVersion} 邀请码配置
                </div>
                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-1 col-md-offset-3 control-label">key邀请码名称：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" readonly="true" name="key" value="${this.appConfiguration?.key?.name}"></g:textField>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-1 col-md-offset-3 control-label">邀请码内容：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" readonly="true" name="value" value="${this.appConfiguration?.value}"></g:textField>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'appVersion.label', default: 'AppVersion')}"/>
    <title>版本详情</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="appVersion" action="index">App版本控制</g:link>
                    </li>
                    <li class="active">
                        <span>版本详情</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                版本详情
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.appVersion}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                </div>
                版本详情
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-1 col-md-offset-3 control-label">名称：</label>

                    <div class="col-md-4">
                        <g:textField class="form-control" disabled="" name="appName"
                                     value="${this.appVersion?.appName}"></g:textField>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-1 col-md-offset-3 control-label">版本：</label>

                    <div class="col-md-4">
                        <g:textField class="form-control" disabled="" name="appVersion"
                                     value="${this.appVersion?.appVersion}"></g:textField>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-1 col-md-offset-3 control-label">强制更新：</label>

                    <div class="col-md-4">
                        <g:textField class="form-control" disabled="" name="mustUpdate"
                                     value="${this.appVersion?.mustUpdate}"></g:textField>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-1 col-md-offset-3 control-label">描述：</label>

                    <div class="col-md-4">
                        <g:textField class="form-control" disabled="" name="description"
                                     value="${this.appVersion?.description}"></g:textField>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="appConfiguration" action="create"
                            id="${this?.appVersion?.id}"><i class="fa fa-plus"></i>新增</g:link>
                </div>
                邀请码
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name" title="名称"/>
                            <g:sortableColumn property="value" title="内容"/>
                            <g:sortableColumn property="操作" title="操作"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.appVersion?.appConfiguration}">
                            <tr>
                                <td><g:link controller="appConfiguration" action="show" id="${it?.id}"
                                            class="firstTd">${it?.key?.name}</g:link></td>
                                <td>${it?.value}</td>
                                <td width="6%">
                                    <g:form resource="${it}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
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

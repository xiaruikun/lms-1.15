<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'creditReportProvider.label', default: 'CreditReportProvider')}"/>
    <title>征信配置：${creditReportProvider.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="creditReportProvider" action="index">征信配置</g:link>
                    </li>
                    <li class="active">
                        <span>${this.creditReportProvider?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                征信配置: ${this.creditReportProvider?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.creditReportProvider}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                </div>
                征信配置详情
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-3 control-label">名称：</label>

                    <div class="col-md-3">
                        <g:textField class="form-control" disabled="" name="name"
                                     value="${this.creditReportProvider?.name}"></g:textField>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">代码：</label>

                    <div class="col-md-3">
                        <g:textField class="form-control" disabled="" name="code"
                                     value="${this.creditReportProvider?.code}"></g:textField>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">连接：</label>

                    <div class="col-md-3">
                        <g:textField class="form-control" disabled="" name="apiUrl"
                                     value="${this.creditReportProvider?.apiUrl}"></g:textField>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

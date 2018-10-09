<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'flexFieldValue.label', default: 'FlexFieldValue')}" />
        <title>弹性域：${this.flexFieldValue?.field?.name}</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li><g:link controller="flexFieldCategory" action="index">弹性域值模块</g:link></li>
                            <li class="active">
                                <span>${this.flexFieldValue?.field?.name}</span>
                            </li>
                        </ol>
                    </div>
                    <h2 class="font-light m-b-xs" style="text-transform:none;">
                        弹性域值：${this.flexFieldValue?.field?.name}
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hgreen">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" action="edit" resource="${this.flexFieldValue}" >
                                <i class="fa fa-edit"></i>编辑
                            </g:link>
                        </div>
                        ${this.flexFieldValue?.field?.name}-弹性域值
                    </div>
                    <div class="panel-body">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="value" title="值"/>
                                <g:sortableColumn property="displayOrder" title="次序"/>
                                <g:sortableColumn property="active" title="有效性"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${flexFieldValue}">
                                <div class="col-md-4">
                                    <tr>
                                        <td>${it?.value}</td>
                                        <td>${it.displayOrder}</td>
                                        <td>${it.active}</td>
                                    </tr>
                                </div>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

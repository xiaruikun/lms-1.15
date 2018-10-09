<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}" />
        <title>查看公司信息</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right m-t-lg">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li><g:link controller="contact" action="show" id="${this.company?.contact?.id}">贷款人管理</g:link></li>
                        <li class="active">
                            <span>查看公司信息</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">查看公司信息</h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hyellow">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.company}" id="${it?.contact?.id}"><i class="fa fa-edit"></i> 编辑</g:link>
                    </div>
                    公司信息
                </div>
                <div class="panel-body">
                    <div class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message alert alert-danger" role="status">${flash.message}
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                            </div>
                        </g:if>
                        <div class="form-group">
                            <label class="col-md-3 control-label">公司：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="company" readOnly="true" value="${this.company?.company}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">行业：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="industry" readOnly="true" value="${this.company?.industry?.name}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">工商&机构代码：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="companyCode" readOnly="true" value="${this.company?.companyCode}"></g:textField>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

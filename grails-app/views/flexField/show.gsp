<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'flexField.label', default: 'FlexField')}" />
        <title>弹性域模块：${this.flexField?.category?.name}</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li><g:link controller="flexFieldCategory" action="index">弹性域模块</g:link></li>
                            <li class="active">
                                <span>${this.flexField?.category?.name}</span>
                            </li>
                        </ol>
                    </div>
                    <h2 class="font-light m-b-xs" style="text-transform:none;">
                        弹性域模块：${this.flexField?.category?.name}
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="hpanel hblue row">
                <div class="panel-heading">
                    <div class="panel-tools">
                      <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.flexField}"><i class="fa fa-edit"></i>编辑</g:link>
                      </sec:ifNotGranted>
                    </div>
                    弹性域详情
                </div>
                <div class="panel-body" style="padding: 10px">
                    <div class="text-muted font-bold m-b-xs ol-md-6">
                        <div class="col-md-12"><h4><a href=>${this.flexField?.name}</a></h4></div>
                    </div>
                </div>
                <div class="panel-footer contact-footer">
                    <div class="row">
                        <div class="col-md-2 border-right">
                            <div class="contact-stat"><span>名称:</span><strong>${this.flexField?.name}</strong></div>
                        </div>
                        <div class="col-md-2 border-right">
                            <div class="contact-stat"><span>描述:</span><strong>${this.flexField?.description}</strong></div>
                        </div>
                        <div class="col-md-1 border-right">
                            <div class="contact-stat"><span>次序:</span> <strong>${this.flexField?.displayOrder}</strong></div>
                        </div>
                        <div class="col-md-2 border-right">
                            <div class="contact-stat"><span>数据类型:</span><strong>${this.flexField?.dataType}</strong></div>
                        </div>
                        <div class="col-md-2 border-right">
                            <div class="contact-stat"><span>默认值:</span> <strong>${this.flexField?.defaultValue}</strong></div>
                        </div>
                        <div class="col-md-1 border-right">
                            <div class="contact-stat"><span>约束:</span> <strong>${this.flexField?.valueConstraints}</strong></div>
                        </div>
                        <div class="col-md-2">
                            <div class="contact-stat"><span>有效性:</span><strong>${this.flexField?.active}</strong></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">
                          <sec:ifNotGranted roles="ROLE_COO">
                            <g:link class="btn btn-xs btn-info" controller="flexFieldValue" action="create" id="${this.flexField?.id}"><i class="fa fa-plus"></i>新增</g:link>
                          </sec:ifNotGranted>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        弹性域值
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <g:sortableColumn property="value" title="值"/>
                                    <g:sortableColumn property="displayOrder" title="次序"/>
                                    <g:sortableColumn property="active" title="有效性"/>
                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${this.flexField?.values}">
                                    <div class="col-md-4">
                                        <tr>
                                            <td><g:link action="show" controller="flexFieldValue" id="${it?.id}"
                                                        class="firstTd">${it?.value}</g:link></td>
                                            <td>${it?.displayOrder}</td>
                                            <td>${it?.active}</td>
                                        </tr>
                                    </div>
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

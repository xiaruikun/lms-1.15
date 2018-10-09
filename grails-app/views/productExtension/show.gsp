<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'productExtension.label', default: 'ProductExtension')}" />
        <title>${this.productExtension?.product?.product?.name}-产品展期</title>
    </head>
    <body>
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li><g:link controller="product" action="index">产品管理</g:link></li>
                            <li class="active">
                                产品展期
                            </li>
                        </ol>
                    </div>

                    <h2 class="font-light m-b-xs" style="text-transform:none;">
                        产品: ${this.productExtension?.product?.product?.name}
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hgreen">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" action="edit" resource="${this.productExtension}" id="${it?.product?.id}">
                                <i class="fa fa-edit"></i>
                                编辑
                            </g:link>
                        </div>
                        ${this.productExtension?.product?.product?.name}-产品展期
                    </div>
                    <div class="panel-body">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="numberOfMonths" title="月份数"/>
                                <g:sortableColumn property="maximumServiceChargePerMonth" title="每月最大服务费率"/>
                                <g:sortableColumn property="totalServiceCharge" title="总服务费率"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${productExtension}">
                                <div class="col-md-4">
                                    <tr>
                                        <td><g:link action="show" id="${it?.id}" aria-labelledby="assetType-label"
                                                    class="firstTd">${it?.numberOfMonths}</g:link></td>
                                        <td>${it.maximumServiceChargePerMonth}</td>
                                        <td>${it.totalServiceCharge}</td>
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

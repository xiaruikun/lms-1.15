<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
        <title>产品：${this.product.name}</title>
    </head>
    <body class="fixed-navbar fixed-sidebar">
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li><g:link controller="product" action="index">产品管理</g:link></li>
                            <li class="active">
                                <span>${this.product.name}</span>
                            </li>
                        </ol>
                    </div>

                    <h2 class="font-light m-b-xs" style="text-transform:none;">
                        产品: ${this.product.name}
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="hpanel hblue row">
                <div class="panel-heading">
                    <div class="panel-tools">
                      <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.product}" id="${this?.product?.id}"><i class="fa fa-edit"></i>编辑</g:link>
                      </sec:ifNotGranted>
                    </div>
                    产品详情
                </div>
                <div class="panel-body">
                    <div class="text-muted font-bold m-b-xs ol-md-6">
                        <div class="col-md-12"><h4><a href=>${this.product.name}</a></h4></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">
                          <sec:ifNotGranted roles="ROLE_COO">
                            <g:link class="btn btn-xs btn-info" controller="productAccount" action="create" id="${this.product.id}"><i class="fa fa-plus"></i>新增</g:link>
                          </sec:ifNotGranted>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        产品-机构
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <g:sortableColumn property="name" title="名称"/>
                                    <g:sortableColumn property="account" title="机构"/>
                                    <g:sortableColumn property="minimumAmount" title="最小值"/>
                                    <g:sortableColumn property="maximumAmount" title="最大值"/>
                                    <g:sortableColumn property="active" title="有效性"/>
                                    <g:sortableColumn property="created_date" title="创建时间"/>
                                    <g:sortableColumn property="modified_date" title="修改时间"/>
                                    <g:sortableColumn property="principal_payment_method" title="本金支付方式"/>
                                    <g:sortableColumn property="description" title="描述"/>
                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${productAccountList}">
                                    <div class="col-md-4">
                                        <tr>
                                            <td><g:link action="show" controller="productAccount" id="${it?.id}"
                                                        class="firstTd">${it?.name}</g:link></td>
                                            <td>${it?.account?.name}</td>
                                            <td>${it?.minimumAmount}</td>
                                            <td>${it?.maximumAmount}</td>
                                            <td>${it?.active}</td>
                                            <td>${it?.createdDate}</td>
                                            <td>${it?.modifiedDate}</td>
                                            <td>${it?.principalPaymentMethod?.name}</td>
                                            <td>${it?.description}</td>
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

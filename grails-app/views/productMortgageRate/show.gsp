<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'productMortgageRate.label', default: 'ProductMortgageRate')}" />
        <title>${this.productMortgageRate.product.product.name}-产品抵押率</title>
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
                                产品抵押率
                            </li>
                        </ol>
                    </div>

                    <h2 class="font-light m-b-xs" style="text-transform:none;">
                        产品: ${this.productMortgageRate?.product?.product?.name}
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hgreen">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" action="edit" resource="${this.productMortgageRate}"id="${it?.product?.id}">
                                <i class="fa fa-edit"></i>
                                编辑
                            </g:link>
                        </div>
                        ${this.productMortgageRate.product.product.name}-产品抵押率
                    </div>
                    <div class="panel-body">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="mortgageRate" title="抵押率"/>
                                <g:sortableColumn property="assetType.name" title="房产类型"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${productMortgageRate}">
                                <div class="col-md-4">
                                    <tr>
                                        <td><g:link action="show" controller="productMortgageRate" id="${it?.id}" aria-labelledby="assetType-label"
                                                    class="firstTd">${it?.mortgageRate}</g:link></td>
                                        <td>${it.assetType?.name}</td>
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

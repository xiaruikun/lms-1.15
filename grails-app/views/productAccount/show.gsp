<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'productAccount.label', default: 'ProductAccount')}"/>
    <title>产品：${this.productAccount?.product?.name}</title>
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
                        <span>${this.productAccount?.product?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs" style="text-transform:none;">
                产品: ${this.productAccount?.product?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue ">
            <g:if test="${flash.message}">
                <div class="message alert alert-info" role="status">${flash.message}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                </div>
            </g:if>
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.productAccount}"
                                id="${this?.productAccount?.id}"><i class="fa fa-edit"></i>编辑</g:link>
                    </sec:ifNotGranted>
                </div>
                产品-机构详情
            </div>

            <div class="panel-body" style="padding: 10px">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12"><h4><a href=>${this.productAccount?.name}</a></h4></div>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-1 border-right">
                        <div class="contact-stat"><span>名称</span><strong>${this.productAccount?.name}</strong></div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat"><span>描述</span><strong>${this.productAccount?.description}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>机构</span><strong>${this.productAccount?.account?.name}</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>创建时间</span><strong>
                            <g:formatDate format="yyyy-MM-dd HH:mm:ss"
                                          date="${this.productAccount.createdDate}"/></strong></div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat"><span>修改时间</span>
                            <strong>
                                <g:formatDate format="yyyy-MM-dd HH:mm:ss"
                                              date="${this.productAccount.modifiedDate}"/></strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat"><span>有效性</span> <strong>${this.productAccount.active}</strong></div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat"><span>本金支付方式</span> <strong>${this.productAccount.principalPaymentMethod?.name}</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat"><span>最小值(万)</span><strong>${this.productAccount.minimumAmount}</strong>
                        </div>
                    </div>

                    <div class="col-md-1">
                        <div class="contact-stat"><span>最大值(万)</span> <strong>${this.productAccount.maximumAmount}</strong>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-xs btn-info" controller="productMortgageRate" action="create"
                                id="${this.productAccount?.id}"><i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                产品抵押率
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="mortgageRate" title="抵押率"/>
                            <g:sortableColumn property="assetType.name" title="房产类型"/>
                            <g:sortableColumn property="操作" title="操作"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.productAccount?.mortgageRates}">
                            <div class="col-md-4">
                                <tr>
                                    <td><g:link action="show" controller="productMortgageRate" id="${it?.id}"
                                                class="firstTd">${it?.mortgageRate}</g:link></td>
                                    <td>${it.assetType?.name}</td>
                                    <td>
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </tr>
                            </div>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-xs btn-info" controller="productExtension" action="create"
                                id="${this.productAccount.id}"><i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                产品展期
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="numberOfMonths" title="月份数"/>
                            <g:sortableColumn property="maximumServiceChargePerMonth" title="每月最大服务费率"/>
                            <g:sortableColumn property="totalServiceCharge" title="总服务费率"/>
                            <sec:ifNotGranted roles="ROLE_COO">
                                <g:sortableColumn property="操作" title="操作"/>
                            </sec:ifNotGranted>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.productAccount?.extensions}">
                            <div class="col-md-4">
                                <tr>
                                    <td><g:link action="show" controller="productExtension" id="${it?.id}"
                                                class="firstTd">${it.numberOfMonths}</g:link></td>
                                    <td>${it.maximumServiceChargePerMonth}</td>
                                    <td>${it.totalServiceCharge}</td>
                                    <sec:ifNotGranted roles="ROLE_COO">
                                        <td width="6%">
                                            <g:form resource="${it}" method="DELETE">
                                                <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                        class="fa fa-trash-o"></i> 删除</button>
                                            </g:form>
                                        </td>
                                    </sec:ifNotGranted>
                                </tr>
                            </div>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <sec:ifNotGranted roles="ROLE_COO">
                        <g:link class="btn btn-xs btn-info" controller="productInterest" action="create"
                                id="${this.productAccount.id}"><i class="fa fa-plus"></i>新增</g:link>
                    </sec:ifNotGranted>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                产品费率
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="minimumRate" title="最小费率"/>
                            <g:sortableColumn property="maximumRate" title="最大费率"/>
                            <g:sortableColumn property="monthesOfStart" title="起始月份"/>
                            <g:sortableColumn property="monthesOfEnd" title="结束月份"/>
                            <g:sortableColumn property="contactLevelname" title="客户级别"/>
                            <g:sortableColumn property="InterestTypename" title="费率类型"/>
                            <g:sortableColumn property="fixedRate" title="固定费率"/>
                            <g:sortableColumn property="installments" title="分期付款"/>
                            <g:sortableColumn property="firstPayMonthes" title="上扣息月份数"/>
                            <g:sortableColumn property="操作" title="操作"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.productAccount?.interests}">
                            <div class="col-md-4">
                                <tr>
                                    <td><g:link action="show" controller="ProductInterest" id="${it?.id}"
                                                class="firstTd"><g:formatNumber number="${it.minimumRate}"
                                                                                minFractionDigits="2"
                                                                                maxFractionDigits="6"/></g:link></td>
                                    <td><g:formatNumber number="${it.maximumRate}" minFractionDigits="2"
                                                        maxFractionDigits="6"/></td>
                                    <td>${it.monthesOfStart}</td>
                                    <td>${it.monthesOfEnd}</td>
                                    <td>${it.contactLevel?.name}</td>
                                    <td>${it.productInterestType?.name}</td>
                                    <td>${it.fixedRate}</td>
                                    <td>${it.installments}</td>
                                    <td>${it.firstPayMonthes}</td>
                                    <td>
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
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

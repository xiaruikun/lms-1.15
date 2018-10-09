<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'collateral.label', default: 'Collateral')}"/>
    <title>订单明细</title>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show"
                                id="${this.collateral?.opportunity?.id}">房产信息详情</g:link>
                    </li>
                    <li class="active">
                        <span>订单信息明细</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                订单号: ${this.collateral?.opportunity?.serialNumber}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <g:if test="${this?.CollateralAuditTrails?.size() > 0}">
        <g:each in="${this?.CollateralAuditTrails}">
            <div class="row">

                <div class="hpanel horange">
                    <div class="panel-heading">
                      <div class="panel-tools">
                        <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                        <g:form resource="${it}" method="DELETE">
                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">删除</button>
                        </g:form>
                      </sec:ifAllGranted>
                      </div>
                        <g:formatDate format="yyyy-MM-dd HH:mm"
                                      date="${it?.createdDate}"/>
                        评房信息
                    </div>

                    <div class="panel-body">
                        <div class="col-md-4 border-right">
                            <div class="contact-stat">
                                <span>评房单价（元）</span>
                                <strong>${it?.unitPrice}</strong>
                            </div>
                        </div>

                        <div class="col-md-4 border-right">
                            <div class="contact-stat">
                                <span>评房总价（万元）</span>
                                <strong><g:formatNumber number="${it?.totalPrice}" minFractionDigits="2" maxFractionDigits="2"/></strong>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="contact-stat">
                                <span>评房时间</span>
                                <strong><g:formatDate format="yyyy-MM-dd HH:mm:ss"
                                                      date="${it?.createdDate}"/></strong>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </g:each>
    </g:if>
    <g:else>
        <div class="row">

            <div class="hpanel horange">

                <div class="panel-body">
                    无评房信息
                </div>
            </div>

        </div>
    </g:else>
</div>
</body>
</html>

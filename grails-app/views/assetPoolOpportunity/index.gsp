<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'assetPool.label', default: 'assetPool')}"/>
    <title>资产池订单列表</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>资产池订单列表</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                资产池订单列表
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">

                资产池订单
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="opportunitySerialNumber" title="名称"/>
                            <g:sortableColumn property="assetPoolName" title="资产池名称"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${assetPoolOpportunityList}">
                            <tr>
                                <td>${it.opportunitySerialNumber}</td>
                                <td>${it?.assetPoolName}</td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${assetPoolOpportunityCount ?: 0}"/>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'assetPool.label', default: 'assetPool')}"/>
    <title>资产池订单详情</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="assetPool" action="index">资产池订单详情管理</g:link>
                    </li>
                    <li class="active">
                        <span>资产池订单详情</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                资产池订单详情
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                资产池订单详情
            </div>

            <div class="panel-body form-horizontal">

                <div class="form-group">
                    <label class="col-md-3 control-label">资产池名称</label>

                    <div class="col-md-4">
                        <span class="cont">${this.assetPoolOpportunity?.assetPool?.name}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <input type="hidden" name="assetPool.id" value="${this.assetPoolOpportunity?.assetPool?.id}">

                    <label class="col-md-3 control-label">资产池订单详情号</label>

                    <div class="col-md-4">
                        <span class="cont">${this.assetPoolOpportunity?.opportunitySerialNumber}</span>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>

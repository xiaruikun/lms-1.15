<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityProduct.label', default: 'opportunityProduct')}"/>
    <title>息费批量修改</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show" id="${this.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>息费批量修改</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                息费
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                息费修改
            </div>

            <div class="panel-body">
                <g:form controller="opportunityProduct"  action="batchUpdate" class="form-horizontal">
                    <input type="hidden" name="product" id = "product" value="${this.opportunity?.id}">
                    <g:each in="${opportunityProduct}">
                        <div class="form-group">
                            <label class="col-md-3 control-label">${it.productInterestType?.name}</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" type="text" name="${it.productInterestType?.name}${it.id}" id="${it.productInterestType?.name}" value="${it.rate}"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                    </g:each>
                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>


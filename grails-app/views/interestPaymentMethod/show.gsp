<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'interestPaymentMethod.label', default: 'InterestPaymentMethod')}" />
    <title>还款方式：${interestPaymentMethod}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="interestPaymentMethod" action="index">还款方式</g:link>
                        </li>
                        <li class="active">
                            <span>${this.interestPaymentMethod?.name}</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    还款方式: ${this.interestPaymentMethod?.name}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.interestPaymentMethod}"><i class="fa fa-edit"></i>编辑</g:link>
                    </div>
                    还款方式详情
                </div>

                <div class="panel-body">
                    <div class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">还款方式：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" disabled="" name="name" value="${this.interestPaymentMethod?.name}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">描述：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" disabled="" name="desription" value="${this.interestPaymentMethod?.desription}"></g:textField>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

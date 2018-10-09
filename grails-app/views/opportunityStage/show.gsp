<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'opportunityStage.label', default: 'OpportunityStage')}" />
    <title>订单阶段：${opportunityStage.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="opportunityStage" action="index">订单阶段</g:link>
                        </li>
                        <li class="active">
                            <span>${this.opportunityStage?.name}</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    订单阶段: ${this.opportunityStage?.name}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.opportunityStage}"><i class="fa fa-edit"></i>编辑</g:link>
                    </div>
                    订单阶段详情
                </div>
                <div class="panel-body">
                    <div class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">阶段编号：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control" name="code" disabled="" value="${this.opportunityStage?.code}" readonly="readonly"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">阶段名称：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control"  disabled="" readonly="readonly" name="name" value="${this.opportunityStage?.name}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">阶段描述：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control"  disabled="" readonly="readonly" name="description" value="${this.opportunityStage?.description}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">阶段类型：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control"  disabled="" readonly="readonly" name="type" value="${this.opportunityStage?.type}"></g:textField>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">是否启用：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control"  disabled="" readonly="readonly" name="active" value="${this.opportunityStage?.active}"></g:textField>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">阶段分类：</label>
                            <div class="col-md-3">
                                <g:textField class="form-control"  disabled="" readonly="readonly" name="category" value="${this.opportunityStage?.category?.name}"></g:textField>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>

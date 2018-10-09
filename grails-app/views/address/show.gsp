<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'address.label', default: 'Address')}" />
    <title>地址：${address.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="address" action="index">单位地址</g:link>
                        </li>
                        <li class="active">
                            <span>${this.address?.name}</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    单位名称: ${this.address.name}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.address}"><i class="fa fa-edit"></i>编辑</g:link>
                        <g:form resource="${this.address}" method="DELETE" style="display: inline-block">
                            <button class="deleteBtn btn btn-danger btn-xs" type="button">
                                <i class="fa fa-trash-o"></i>删除
                            </button>
                        </g:form>
                    </div>
                    地址详情
                </div>
                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-md-2  control-label">单位名称：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="name" value="${this.address?.name}"></g:textField>
                        </div>
                        <label class="col-md-2  control-label">地址：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="address" value="${this.address?.address}"></g:textField>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group" >
                        <label class="col-md-2  control-label">火车站：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="trainStations" value="${this.address?.trainStations}"></g:textField>
                        </div>
                        <label class="col-md-2  control-label">公交站：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="busStations" value="${this.address?.busStations}"></g:textField>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group" >
                        <label class="col-md-2  control-label">热线电话：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="tellphone" value="${this.address?.tellphone}"></g:textField>
                        </div>
                        <label class="col-md-2  control-label">工作时间：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="openingHours" value="${this.address?.openingHours}"></g:textField>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group" >
                        <label class="col-md-2  control-label">单位类型：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="type" value="${this.address?.type?.name}"></g:textField>
                        </div>
                        <label class="col-md-2  control-label">所属城区：</label>
                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="district" value="${this.address?.district?.name}"></g:textField>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>

</html>

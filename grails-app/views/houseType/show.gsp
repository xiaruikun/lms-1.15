<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'houseType.label', default: 'HouseType')}"/>
    <title>物业类型：${houseType.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="houseType" action="index">物业类型</g:link>
                    </li>
                    <li class="active">
                        <span>${this.houseType?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                物业类型: ${this.houseType?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.houseType}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                </div>
                物业类型详情
            </div>

            <div class="panel-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">名称：</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="name"
                                         value="${this.houseType?.name}"></g:textField>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

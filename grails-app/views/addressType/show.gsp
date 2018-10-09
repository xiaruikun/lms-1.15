<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'addressType.label', default: 'AddressType')}"/>
    <title>单位类别：${addressType.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="addressType" action="index">单位类别</g:link>
                    </li>
                    <li class="active">
                        <span>${this.addressType?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                单位类别: ${this.addressType?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.addressType}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                    <g:form resource="${this.addressType}" method="DELETE" style="display: inline-block">
                        <button class="deleteBtn btn btn-danger btn-xs" type="button">
                            <i class="fa fa-trash-o"></i>删除
                        </button>
                    </g:form>
                </div>
                单位类别详情
            </div>

            <div class="panel-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">名称：</label>

                        <div class="col-md-3">
                            <g:textField class="form-control" disabled="" name="name"
                                         value="${this.addressType?.name}"></g:textField>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

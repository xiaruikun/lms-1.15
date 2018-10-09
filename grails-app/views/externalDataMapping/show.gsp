<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'externalDataMapping.label', default: 'ExternalDataMapping')}"/>
    <title>外部数据映射</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="externalDataMapping" action="index">外部数据映射列表</g:link>
                    </li>
                    <li class="active">
                        <span>外部数据映射</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                外部数据映射
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:form resource="${this.externalDataMapping}" method="DELETE">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.externalDataMapping}"><i
                                class="fa fa-edit"></i>编辑</g:link>
                        <button type="button" class="deleteBtn btn btn-danger btn-xs">
                            <i class="fa fa-trash-o"></i>
                            删除
                        </button>
                    </g:form>
                </div>

                外部数据映射
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-3 control-label">systemName：</label>

                    <div class="col-md-3">
                        <input type="text" disabled="" value="${this.externalDataMapping?.systemName}"
                               class="form-control">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">categoryName：</label>

                    <div class="col-md-3">
                        <input type="text" disabled="" value="${this.externalDataMapping?.categoryName}"
                               class="form-control">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">value1：</label>

                    <div class="col-md-3">
                        <input type="text" disabled="" value="${this.externalDataMapping?.value1}" class="form-control">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">value2：</label>

                    <div class="col-md-3">
                        <input type="text" disabled="" value="${this.externalDataMapping?.value2}" class="form-control">
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</body>

</html>


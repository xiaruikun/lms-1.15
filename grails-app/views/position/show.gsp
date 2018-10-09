<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'position.label', default: 'Position')}"/>
    <title>岗位详情</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="position" action="index">岗位列表</g:link>
                    </li>
                    <li class="active">
                        <span>岗位详情</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                岗位详情
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">

                <div class="panel-tools">
                    <g:form resource="${this.position}" method="DELETE">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.position}"><i
                                class="fa fa-edit"></i>编辑</g:link>
                        <button type="button" class="deleteBtn btn btn-danger btn-xs">
                            <i class="fa fa-trash-o"></i>
                            删除
                        </button>
                    </g:form>
                </div>

                岗位详情
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-3 control-label">岗位名称：</label>

                    <div class="col-md-3">
                        <input disabled="disabled" value="${this.position?.name}" class="form-control"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>



<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'contactLevel.label', default: 'contactLevel')}"/>
    <title>客户级别详情</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="contactLevel" action="index">客户级别</g:link>
                    </li>
                    <li class="active">
                        <span>${this.contactLevel?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                客户级别: ${this.contactLevel?.id}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.contactLevel}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                    <g:form resource="${this.contactLevel}" method="DELETE" style="display: inline-block">
                        <button class="deleteBtn btn btn-danger btn-xs" type="button">
                            <i class="fa fa-trash-o"></i>删除
                        </button>
                    </g:form>
                </div>
                客户级别详情
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-4 control-label">名称：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.contactLevel?.name}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">描述：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.contactLevel?.description}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">启用</label>

                    <div class="col-md-4">
                        <span class="cont">${this.contactLevel?.active}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

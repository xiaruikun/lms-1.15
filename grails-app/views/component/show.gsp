<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'component.label', default: 'Component')}"/>
    <title>组件详情</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="component" action="index">组件</g:link>
                    </li>
                    <li class="active">
                        <span>${this.component?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                组件详情: ${this.component?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.component}">
                        <i class="fa fa-edit"></i>编辑</g:link>
                    <g:form resource="${this.component}" method="DELETE" style="display: inline-block">
                        <button class="deleteBtn btn btn-danger btn-xs" type="button">
                            <i class="fa fa-trash-o"></i>删除
                        </button>
                    </g:form>
                </div>
                组件详情
            </div>

            <div class="panel-body form-horizontal">
                <g:if test="${flash.message}">
                    <div class="message alert alert-info" role="status">${flash.message}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">×</span></button>
                    </div>
                </g:if>
                <div class="form-group">
                    <label class="col-md-1 control-label">名称：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.component?.name}</span>
                    </div>
                    <label class="col-md-2 control-label">类型：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.component?.type?.name}</span>
                    </div>
                </div>


                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-1 control-label">消息：</label>

                    <div class="col-md-4">
                        <span class="cont"> ${this.component?.message}</span>
                    </div>
                    <label class="col-md-2 control-label">是否启用：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.component?.active}</span>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-1 control-label">脚本：</label>

                    <div class="col-md-10">
                        <pre>
                            <code>${this.component?.script}</code>
                        </pre>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>


            </div>
        </div>
    </div>
</div>
</body>
</html>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'document.label', default: 'Document')}"/>
    <title>帮助文档详情</title>
    <style>
    .note-editor{
        border: none;
    }
    </style>
</head>

<body>
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="document" action="index">document</g:link></li>
                    <li class="active">
                        <span>帮助文档详情</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                帮助文档详情
            </h2>
        </div>
    </div>
</div>
%{--<g:form resource="${this.document}" method="DELETE">
    <fieldset class="buttons">
        <g:link class="edit" action="edit" resource="${this.document}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
</g:form>--}%
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:form resource="${this.document}" method="DELETE">
                        <g:link action="edit" class="btn btn-info btn-xs" resource="${this.document}">
                            <i class="fa fa-edit"></i>编辑</g:link>
                        <button type="button" class="deleteBtn btn btn-danger btn-xs">
                            <i class="fa fa-trash-o"></i>
                            删除
                        </button>
                    </g:form>
                </div>
                帮助文档详情
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-3 control-label">标题：</label>

                    <div class="col-md-3">
                        <input type="text" value="${this.document?.title}" disabled="disabled" class="form-control">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-md-3 control-label">创建时间：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled" type="text"
                               value="<g:formatDate date="${this.document?.createdDate}"
                                                    format="yyyy-MM-dd HH:mm:ss"></g:formatDate>">

                    </div>

                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-md-3 control-label">修改时间：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled" type="text"
                               value="<g:formatDate date="${this.document?.modifiedDate}"
                                                    format="yyyy-MM-dd HH:mm:ss"></g:formatDate>">
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-md-3 control-label">是否使用：</label>

                    <div class="col-md-6">
                        <input class="form-control" disabled="disabled" type="text"
                               value="${this.document?.active}"/>

                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <g:field name="document" id="document"
                             value="${this.document?.document}"
                             type="hidden"/>
                    <label class="col-md-3 control-label">帮助文档：</label>
                    <div class="col-md-6" >
                        <div id="summernote">${this.document?.document}</div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        var text = $("#document").val();
        $('#summernote').summernote({
            airMode: true,
        }).summernote('code', text);

    })
</script>
</body>
</html>

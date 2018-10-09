<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'document.label', default: 'Document')}" />
        <title> 编辑帮助文档</title>
    </head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="document" action="index">doucment</g:link>
                    </li>
                    <li class="active">
                        <span> 编辑帮助文档</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                编辑帮助文档
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                编辑帮助文档
            </div>

            <div class="panel-body">
                <g:form resource="${this.document}" method="PUT" class="documentForm form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.document}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.document}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">标题</label>

                        <div class="col-md-3">
                            <g:textField name="title" value="${this.document?.title}" required="" maxlength="128"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                  %{--  <div class="form-group">
                        <label class="col-md-3 control-label">版本</label>

                        <div class="col-md-3">
                            <g:textField name="version" value="${this.document?.version}" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
--}%
                    <div class="form-group">
                        <label class="col-md-3 control-label">创建时间</label>

                        <div class="col-md-6">
                            <g:datePicker name="createdDate" value="${new Date()}" precision="day"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">修改时间</label>

                        <div class="col-md-6">
                            <g:datePicker name="modifiedDate" value="${new Date()}" precision="day"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">是否使用</label>

                        <div class="col-md-6 checkbox-inline">
                            <g:checkBox class="i-checks" name="active" style="margin-left: 20px" value="${this.document?.active}"
                                        checked="true"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <g:field name="document" id="document"
                                 value="${this.document?.document}"
                                 type="hidden"/>
                        <label class="col-md-3 control-label">帮助文档</label>

                        <div class="col-md-5">
                            <div id="summernote">${this.document?.document}</div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <button type="button" class="btn btn-info" id="documentBtn">保存</button>

                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        var text = $("#document").val();
        $('#summernote').summernote({
            minHeight: 200,
            maxHeight: 500,
            toolbar: [
                ['edit', ['undo', 'redo']],
                ['para', ['style', 'ul', 'ol', 'paragraph', 'height']],
                ['font', ['fontsize', 'color', 'bold', 'italic', 'underline', 'clear']],
            ]
        }).summernote('code', text);

        $("#documentBtn").click(function () {
            var markupStr = $('#summernote').summernote('code');
            $("#document").val(markupStr);
            $(".documentForm").submit();
        });
    })
</script>
</body>
</html>

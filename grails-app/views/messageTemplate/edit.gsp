<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'messageTemplate.label', default: 'MessageTemplate')}"/>
    <title>编辑消息推送模板</title>
    <asset:stylesheet src="homer/vendor/codemirror/codemirror.css"/>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="messageTemplate" action="index">推送模板</g:link>
                    </li>
                    <li class="active">
                        <span>编辑消息推送模板</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                编辑消息推送模板
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增推送模板
            </div>

            <div class="panel-body">
                <g:form resource="${this.messageTemplate}" method="PUT" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.messageTemplate}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.messageTemplate}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-2 control-label">名称</label>

                        <div class="col-md-8">
                            <g:textField name="name" class="form-control" value="${this.messageTemplate?.name}"/>
                        </div>

                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">内容</label>

                        <div class="col-md-8">
                            <g:textArea name="text" value="${this.messageTemplate?.text}" class="form-control" rows="4"
                                        cols="40"/>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">模板</label>

                        <div class="col-md-8">

                            <g:textArea name="template" id="code1" value="${this.messageTemplate?.template}" class="form-control"
                                        rows="25" cols="15"/>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-4 col-md-offset-4">
                            <button class="submitBtn btn btn-info" type="submit">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
<asset:javascript src="homer/vendor/codemirror/codemirror.js"/>
<asset:javascript src="homer/vendor/codemirror/javascript.js"/>

<script>
    $(document).ready(function () {
        var textarea1 = document.getElementById("code1");
        var editor1 = CodeMirror.fromTextArea(textarea1, {
            lineNumbers: false,
            matchBrackets: true,
            styleActiveLine: true
        });

        $(".submitBtn").click(function () {
            $("#code1").val(editor1.getValue());
        });
    });
</script>

</body>
</html>

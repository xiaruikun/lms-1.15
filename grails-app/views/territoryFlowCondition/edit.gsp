<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'territoryFlowCondition.label', default: 'TerritoryFlowCondition')}"/>
    <title>编辑工作流-条件</title>
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
                        <g:link controller="territoryFlowCondition" action="index">工作流-条件</g:link>
                    </li>
                    <li class="active">
                        <span>编辑工作流-条件</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                编辑工作流条件
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                编辑工作流-条件
            </div>

            <div class="panel-body">
                <g:form resource="${this.territoryFlowCondition}" method="PUT" class="form-horizontal flowForm">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.territoryFlowCondition}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.territoryFlowCondition}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-2 control-label">条件描述</label>

                        <div class="col-md-8">

                            <g:textArea id="code2" name="message" value="${this.territoryFlowCondition?.message}"
                                        class="form-control textarea" rows="3" cols="15"/>
                        </div>


                    </div>
                    <div class="form-group">
                        <div class="hr-line-dashed"></div>
                        <g:textField name="flow.id" id="flow" value="${this.territoryFlowCondition?.flow?.id}"
                                     class="hide"/>
                        <label class="col-md-2 control-label">条件内容</label>

                        <div class="col-md-8">
                            <g:textArea id="code1" name="condition" value="${this.territoryFlowCondition?.condition}"
                                        class="form-control" rows="10" cols="15"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>


                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-5">
                            <button class="submitBtn btn btn-info" type="button">保存</button>
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
            $(".flowForm").submit();
        });
    });
</script>
</body>
</html>

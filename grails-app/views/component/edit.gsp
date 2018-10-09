<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'component.label', default: 'Component')}"/>
    <title>编辑组件${this.component?.name}</title>
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
                        <g:link controller="component" action="index">组件</g:link>
                    </li>
                    <li class="active">
                        <span>编辑${this.component?.name}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增组件
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增
            </div>

            <div class="panel-body">
                <g:form resource="${this.component}" method="PUT" class="form-horizontal receiverForm">
                    <g:if test="${flash.message}">
                        <div class="message alert alert-info" role="status">${flash.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">×</span></button>
                        </div>
                    </g:if>
                    <g:hasErrors bean="${this.component}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.component}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-1 control-label">名称</label>

                        <div class="col-md-4">
                            <g:textField name="name" value="${this.component?.name}" class="form-control"/>
                        </div>
                        <label class="col-md-2 control-label">类别</label>

                        <div class="col-md-4">
                            <g:select class="form-control" name="type.id" required="required" id="type"
                                      value="${this.component?.type?.id}" from="${com.next.ComponentType.list()}"
                                      optionKey="id" optionValue="name"></g:select>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-1 control-label">消息</label>

                        <div class="col-md-4">
                            <g:textArea name="message" value="${this.component?.message}"
                                        class="form-control textarea" rows="3" cols="15"/>

                        </div>
                        <label class="col-md-2 control-label">是否启用</label>

                        <div class="col-md-4">
                            <input type="hidden" value="${this.component?.active}" id="active">

                            <div class="radio radio-info radio-inline ">
                                <input type="radio" name="active" value="true" id="radio1">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline ">
                                <input type="radio" name="active" value="false" id="radio2">
                                <label for="radio2">false</label>
                            </div>
                            %{--  <g:radioGroup class="checkbox-inline radio-info" name="active" value="${this.component?.active}"
                                            labels="['true', 'false']" values="[true, false]">
                                  ${it.radio} ${it.label}
                              </g:radioGroup>--}%

                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-1 control-label">脚本</label>

                        <div class="col-md-10">

                            <g:textArea name="script" id="code1" value="${this.component?.script}" class="form-control textarea"
                                        rows="25" cols="15"/>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>


                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-5">
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

        if ($("#active").val() == "true") {
            $("#radio1")[0].checked = true;
        } else {
            $("#radio2")[0].checked = true;
        }

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

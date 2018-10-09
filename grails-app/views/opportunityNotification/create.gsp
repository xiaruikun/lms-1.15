<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityNotification.label', default: 'OpportunityNotification')}"/>
    <title>订单消息推送</title>
    <asset:stylesheet src="homer/vendor/codemirror/codemirror.css"/>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunityNotification" action="index">订单消息推送</g:link>
                    </li>
                    <li class="active">
                        <span>新增订单消息推送</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增订单消息推送
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增订单消息推送
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityNotification}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityNotification}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-2 control-label">订单编号</label>

                        <div class="col-md-3">
                            <g:if test="${this.opportunityNotification?.opportunity != null && this.opportunityNotification?.opportunity != ''}">
                                <g:textField name="opportunity.id" required="required" id="opportunity"
                                             value="${this.opportunityNotification?.opportunity?.id}" class="hide"/>
                                <g:textField name="opportunitySerialNumber"
                                             value="${this.opportunityNotification?.opportunity?.serialNumber}"
                                             readonly="readonly" class="form-control"/>
                            </g:if>
                            <g:else>
                                <g:select name="opportunity.id" required="required" id="opportunity"
                                          value="${this.opportunityNotification?.opportunity}"
                                          from="${com.next.Opportunity.list()}" optionKey="id"
                                          optionValue="serialNumber" noSelection="${['null': '请选择']}"></g:select>
                            </g:else>
                        </div>
                        <label class="col-md-2 control-label">用户名</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="user.id" id="user"
                                      value="${this.opportunityNotification?.user}" from="${this.userList}"
                                      optionKey="id" noSelection="${['null': '-请选择-']}"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">订单阶段</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="stage.id" required="required" id="stage"
                                      value="${this.opportunityNotification?.stage}"
                                      from="${this.opportunityStages}" optionKey="id" optionValue="name"
                                      noSelection="${['null': '请选择']}"></g:select>
                        </div>
                        <label class="col-md-2 control-label">消息模板</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="messageTemplate.id" required="required"
                                      id="messageTemplate" value="${this.opportunityNotification?.messageTemplate}"
                                      from="${com.next.MessageTemplate.list()}" optionKey="id"
                                      noSelection="${['null': '请选择']}"></g:select>
                        </div>
                    </div>


                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">推送主管</label>

                        <div class="col-md-3">
                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="toManager" value="true">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="toManager" value="false" checked="">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">手机号</label>

                        <div class="col-md-8">

                            <g:textArea name="cellphone" id="code1" value="${this.opportunityNotification?.cellphone}"
                                        class="form-control textarea"
                                        rows="25" cols="15"/>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
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

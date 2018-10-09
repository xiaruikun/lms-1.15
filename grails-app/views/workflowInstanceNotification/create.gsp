<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'workflowInstanceNotification.label', default: 'WorkflowInstanceNotification')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <asset:stylesheet src="homer/vendor/codemirror/codemirror.css"/>
    </head>
    <body>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增工作流消息
                  </div>

                  <div class="panel-body">

                      <g:form action="save" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.workflowInstanceNotification}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.workflowInstanceNotification}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>
                          <div class="form-group">
                              <label class="col-md-4 control-label">用户</label>

                              <div class="col-md-4">
                                <input type="hidden" name="stage.id" value="${this.workflowInstanceNotification?.stage?.id}">
                                <g:select class="form-control" name="user.id" id="user"
                                          value="${this.workflowInstanceNotification?.user?.id}"
                                          from="${this.userList}" optionKey="id"  noSelection="['':'-请选择-']"></g:select>

                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-4 control-label">消息模板</label>

                              <div class="col-md-4">
                                  <g:select class="form-control" name="messageTemplate.id" required="required"
                                            id="messageTemplate"
                                            value="${this.workflowInstanceNotification?.messageTemplate}"
                                            from="${com.next.MessageTemplate.list()}" optionKey="id"></g:select>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-4 control-label">推送主管</label>

                              <div class="col-md-4">
                                  <div class="radio radio-info radio-inline">
                                      <input type="radio" name="toManager"  value="true">
                                      <label for="radio1">true</label>
                                  </div>

                                  <div class="radio radio-info radio-inline">
                                      <input type="radio" name="toManager"  value="false"  checked="">
                                      <label for="radio2">false</label>
                                  </div>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-4 control-label">手机号</label>

                              <div class="col-md-6">

                                  <g:textArea name="cellphone" id="code1" value="${this.workflowInstanceNotification?.cellphone}" class="form-control textarea"
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

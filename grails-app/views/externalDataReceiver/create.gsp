<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'externalDataReceiver.label', default: 'ExternalDataReceiver')}" />
        <title>新增</title>
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
                              <g:link controller="externalDataReceiver" action="index">外部数据接收</g:link>
                          </li>
                          <li class="active">
                              <span>新增</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      外部数据接收
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
                    <g:form action="save" class="form-horizontal receiverForm">
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.externalDataReceiver}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.externalDataReceiver}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">名称</label>
                            <div class="col-md-3">
                                <g:textField name="name" value="${this.externalDataReceiver?.name}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">是否启用</label>

                            <div class="col-md-3">
                                <div class="radio radio-info radio-inline">
                                    <input type="radio" name="active"  value="true" checked="">
                                    <label for="radio1">true</label>
                                </div>

                                <div class="radio radio-info radio-inline">
                                    <input type="radio" name="active"  value="false">
                                    <label for="radio2">false</label>
                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">脚本</label>

                            <div class="col-md-6">
                                <g:textArea name="script" id="code1" value="${this.externalDataReceiver?.script}"  class="form-control" rows="10" cols="15"/>

                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-3">
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
          $(document).ready(function(){
              var textarea1 = document.getElementById("code1");
                  var editor1 = CodeMirror.fromTextArea(textarea1, {
                      lineNumbers: true,
                      matchBrackets: true,
                      styleActiveLine: true
                  });

              $(".submitBtn").click(function(){
                 $("#code1").val(editor1.getValue());
                  $(".receiverForm").submit();
              });
          });
      </script>

        <!-- <a href="#create-externalDataReceiver" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-externalDataReceiver" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.externalDataReceiver}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.externalDataReceiver}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                    <f:all bean="externalDataReceiver"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contractTemplateOptions.label', default: 'ContractTemplateOptions')}" />
        <title>模板选项</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              <g:link controller="contractTemplate" action="show" id="${this.contractTemplateOptions?.template?.id}">合同模板</g:link>
                          </li>
                          <li class="active">
                              <span>新增模板选项</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      模板选项
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
                    <g:hasErrors bean="${this.contractTemplateOptions}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.contractTemplateOptions}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-3 control-label">值</label>
                            <div class="col-md-3">
                              <g:textField name="template.id" value="${this.contractTemplateOptions?.template?.id}" class="hidden" />
                              <g:textField name="value" value="${this.contractTemplateOptions?.value}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">次序</label>
                            <div class="col-md-3">
                              <g:textField name="displayOrder" value="${this.contractTemplateOptions?.displayOrder}" class="form-control" />
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-4">
                                <button class="submitBtn btn btn-info" type="submit">保存</button>
                            </div>
                        </div>
                    </g:form>
                  </div>
              </div>
          </div>
      </div>

    </body>
</html>

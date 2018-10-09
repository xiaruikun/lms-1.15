<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contractTemplate.label', default: 'ContractTemplate')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              <g:link controller="contractType" action="show" id="${this.contractTemplate?.type?.id}">合同类型</g:link>
                          </li>
                          <li class="active">
                              <span>修改模板</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      合同模板
                  </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      修改
                  </div>
                  <div class="panel-body">
                    <g:form resource="${this.contractTemplate}" method="PUT" class="form-horizontal receiverForm">
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.contractTemplate}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.contractTemplate}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
                        <div class="form-group">
                            <label class="col-md-4 control-label">名称</label>
                            <div class="col-md-3">
                              <g:textField name="type.id" value="${this.contractTemplate?.type?.id}" class="hidden" />
                              <g:textField name="name" value="${this.contractTemplate?.name}" class="form-control" />
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
    </body>
</html>

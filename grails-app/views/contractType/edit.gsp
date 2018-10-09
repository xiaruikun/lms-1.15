<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contractType.label', default: 'ContractType')}" />
        <title>合同类型编辑</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              <g:link controller="contracType" action="index">合同类型</g:link>
                          </li>
                          <li class="active">
                              <span>${this.contractType?.name}</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      名称: ${this.contractType?.name}
                  </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      合同类型编辑
                  </div>
                  <div class="panel-body">
                      <g:form resource="${this.contractType}" method="PUT" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.contractType}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.contractType}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>
                          <div class="form-group">
                              <label class="col-md-3 control-label">名称</label>
                              <div class="col-md-3">
                                  <g:textField name="name" value="${this.contractType?.name}" class="form-control" />
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <div class="col-md-3 col-md-offset-3">
                                  <g:submitButton class="btn btn-info" name="update" value="保存" />
                              </div>
                          </div>
                      </g:form>
                  </div>
              </div>
          </div>
      </div>
    </body>
</html>

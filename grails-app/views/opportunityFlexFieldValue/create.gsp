<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityFlexFieldValue.label', default: 'OpportunityFlexFieldValue')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">

                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>

                          <li class="active">
                              <span>新增弹性域值</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      弹性域模块
                  </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增弹性域值
                  </div>
                  <div class="panel-body">
                      <div id="create-flexFieldValue" class="content scaffold-create" role="main">
                        <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.opportunityFlexFieldValue}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityFlexFieldValue}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                        </g:hasErrors>
                          <g:form action="save" class="form-horizontal">
                              <div class="form-group">
                                  <label class="col-md-3 control-label">值</label>
                                  <div class="col-md-3">
                                    <g:textField name="field.id" id="field" value="${this.opportunityFlexFieldValue?.field?.id}" class="hidden" />
                                      <g:textField name="value" value="${this.opportunityFlexField?.value}" class="form-control" />
                                  </div>
                              </div>
                              <div class="hr-line-dashed"></div>
                              <div class="form-group">
                                  <label class="col-md-3 control-label">次序</label>
                                  <div class="col-md-3">
                                      <g:textField name="displayOrder" value="${this.opportunityFlexField?.displayOrder}" class="form-control" />
                                  </div>
                              </div>

                              <div class="hr-line-dashed"></div>
                              <div class="form-group">
                                  <div class="col-md-3 col-md-offset-3">
                                      <g:submitButton class="btn btn-info" name="create" value="保存" />
                                  </div>
                              </div>
                          </g:form>
                      </div>
                  </div>
              </div>
          </div>
      </div>

    </body>
</html>

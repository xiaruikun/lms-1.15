<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityFlowNextStage.label', default: 'OpportunityFlowNextStage')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              工作流-分支
                          </li>
                          <li class="active">新增工作流-分支</li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      新增分支
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增工作流-分支
                  </div>

                  <div class="panel-body">
                      <g:form action="save" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.opportunityFlowNextStage}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.opportunityFlowNextStage}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>
                          <div class="form-group">
                              <label class="col-md-3 control-label">当前工作流</label>

                              <div class="col-md-3">
                                  <g:textField name="flow.id" required="" id="flow"
                                               value="${this.opportunityFlowNextStage?.flow?.id}" class="hide"/>
                                  <g:textField name="stageName" value="${this.opportunityFlowNextStage?.flow?.stage?.name}"
                                               class="form-control" readOnly="readOnly"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-3 control-label">分支</label>

                              <div class="col-md-3">
                                  <select name="nextStage.id" required="" id="nextStage" class="form-control">
                                      <g:each in="${this.nextStages}">
                                          <option value="${it?.id}">${it?.stage?.name}</option>
                                      </g:each>
                                  </select>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <div class="col-md-3 col-md-offset-3">
                                  <g:submitButton class="btn btn-info" name="update" value="保存"/>
                              </div>
                          </div>
                      </g:form>
                  </div>
              </div>
          </div>
      </div>
        
    </body>
</html>

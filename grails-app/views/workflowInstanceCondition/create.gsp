<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'workflowInstanceCondition.label', default: 'WorkflowInstanceCondition')}" />
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
                              工作流-条件
                          </li>
                          <li class="active">
                              <span>新增工作流-条件</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      新增工作流条件
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增工作流-条件
                  </div>

                  <div class="panel-body">
                      <g:form action="save" class="form-horizontal flowForm">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.workflowInstanceCondition}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.workflowInstanceCondition}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>

                          <div class="form-group">
                              <label class="col-md-4 control-label">执行次序</label>

                              <div class="col-md-4">
                                  <input type="hidden" name="stage.id" value="${this.workflowInstanceCondition?.stage?.id}">
                                  <g:textField name="executeSequence"
                                               value="${this.workflowInstanceCondition?.executeSequence}"
                                               class="form-control"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-4 control-label">组件</label>

                              <div class="col-md-4">
                                  <g:select class="form-control" name="component.id" id="component"
                                            value="${this.workflowInstanceCondition?.component?.id}"
                                            from="${this.componentList}" optionKey="id" optionValue="name"
                                            noSelection="['': '--请选择--']"></g:select>

                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-4 control-label">分支</label>

                              <div class="col-md-4">
                                  <g:select class="form-control" name="nextStage.id" id="nextStage"
                                            value="${this.workflowInstanceCondition?.nextStage?.id}"
                                            from="${this.workflowInstanceStages}" optionKey="id" optionValue="name"
                                            noSelection="['': '--请选择--']"></g:select>

                              </div>
                          </div>


                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-4 control-label">条件描述</label>

                              <div class="col-md-4">
                                  <g:textArea name="message" id="code2"
                                              value="${this.workflowInstanceCondition?.message}" class="form-control textarea"
                                              rows="3" cols="15"/>

                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <div class="col-md-4 col-md-offset-4">
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

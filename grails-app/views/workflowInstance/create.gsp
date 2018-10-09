<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'workflowInstance.label', default: 'WorkflowInstance')}" />
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
                              <g:link class="list" action="opportunity" action="show" id="${this.workflowInstance?.opportunity?.id}">订单详情</g:link>
                          </li>
                          <li class="active">
                              <span>新增工作流2.0</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      新增工作流2.0
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增工作流2.0
                  </div>

                  <div class="panel-body">
                      <g:form action="save" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.workflowInstance}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.workflowInstance}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>
                          <div class="form-group">
                              <label class="col-md-4 control-label">名称</label>

                              <div class="col-md-3">
                                <g:textField class="hidden" name="opportunity.id"
                                             value="${this.workflowInstance?.opportunity?.id}"/>
                                <g:textField class="form-control" name="name" required=""
                                               value="${this.workflowInstance?.name}"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-4 control-label">工作流2.0</label>

                              <div class="col-md-3">
                                  <g:select class="form-control" name="workflow.id" required="required" id="workflow"
                                            value="${this.workflowInstance?.workflow?.id}" from="${this.workflowList}"
                                            optionKey="id" optionValue="name" noSelection="${['null': '请选择']}"></g:select>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <div class="col-md-3 col-md-offset-4">
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

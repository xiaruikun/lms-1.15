<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityWorkflowStage.label', default: 'OpportunityWorkflowStage')}" />
        <title>工作流信息编辑</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              <g:link controller="opportunityWorkflow" class="list" action="index">工作流</g:link>
                          </li>
                          <li class="active">
                              <span>工作流信息编辑</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      工作流信息编辑
                  </h2>

              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      工作流信息编辑
                  </div>
                  <div class="panel-body">
                      <g:form resource="${this.opportunityWorkflowStage}" method="PUT" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.opportunityWorkflowStage}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.opportunityWorkflowStage}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>

                  <div class="hr-line-dashed"></div>
                  <div class="form-group">
                      <label class="col-md-3 control-label">阶段</label>
                      <div class="col-md-3">
                          <g:textField name="stage.id" value="${this.opportunityWorkflowStage?.stage?.id}" id="stage" class="hide"/>
                          <g:textField name="stageName" value="${this.opportunityWorkflowStage?.stage?.name}"  readonly="readonly" class="form-control" />
                      </div>
                  </div>

                  <div class="hr-line-dashed"></div>
                  <div class="form-group">
                      <label class="col-md-3 control-label">执行次序</label>
                      <div class="col-md-3 checkbox-inline">
                          <g:textField name="executionSequence" value="${this.opportunityWorkflowStage?.executionSequence}" class="form-control" readonly="readonly" />

                      </div>
                  </div>
                  <div class="hr-line-dashed"></div>
                  <div class="form-group ">
                      <label class="col-md-3 control-label">是否回退</label>
                      <div class="col-md-3 checkbox-inline">
                          <g:radioGroup class="checkbox-inline" name="canReject" value="${this.opportunityWorkflowStage?.canReject}" labels="['true','false']" values="[true,false]">
                              ${it.radio} ${it.label}
                          </g:radioGroup>
                      </div>
                  </div>
                  <div class="hr-line-dashed"></div>
                  <div class="form-group">
                      <label class="col-md-3 control-label">布局</label>
                      <div class="col-md-3 checkbox-inline">
                        <g:select class="form-control" name="opportunityLayout.id" value="${this.opportunityWorkflowStage?.opportunityLayout?.id}" required="required" id="opportunityLayout" from="${com.next.OpportunityLayout.findAllByActive(true)}" optionKey="id" optionValue="description" noSelection="${['null':'请选择']}"></g:select>

                      </div>
                  </div>
                  <div class="hr-line-dashed"></div>

                  <div class="form-group">
                      <label class="col-md-3 control-label">操作说明</label>

                      <div class="col-md-3">
                        <g:select class="form-control" name="document.id"
                                  value="${this.opportunityWorkflowStage?.document?.id}"
                                  id="document"
                                  from="${this.documentList}" optionKey="id"
                                  optionValue="title" noSelection="${['null': '请选择']}"></g:select>
                          <!-- <div id="summernote"></div> -->
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
        <!-- <a href="#edit-opportunityWorkflowStage" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-opportunityWorkflowStage" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.opportunityWorkflowStage}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunityWorkflowStage}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.opportunityWorkflowStage}" method="PUT">
                <g:hiddenField name="version" value="${this.opportunityWorkflowStage?.version}" />
                <fieldset class="form">
                    <f:all bean="opportunityWorkflowStage"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

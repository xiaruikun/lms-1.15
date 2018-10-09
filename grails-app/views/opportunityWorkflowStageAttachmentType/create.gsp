<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityWorkflowStageAttachmentType.label', default: 'OpportunityWorkflowStageAttachmentType')}" />
        <title>附件类型</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              工作流-附件
                          </li>
                          <li class="active">新增工作流-附件</li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      新增附件
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增工作流-附件
                  </div>

                  <div class="panel-body">
                      <g:form action="save" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.opportunityWorkflowStageAttachmentType}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.opportunityWorkflowStageAttachmentType}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>
                          <div class="form-group">
                              <label class="col-md-3 control-label">附件类型</label>

                              <div class="col-md-3">
                                  <g:textField name="stage.id" required="" id="stage"
                                               value="${this.opportunityWorkflowStageAttachmentType?.stage?.id}" class="hide"/>
                                  <g:select class="form-control" name="attachmentType.id" required="required" id="attachmentType"
                                                         value="${this.opportunityWorkflowStageAttachmentType?.attachmentType}"
                                                         from="${com.next.AttachmentType.list()}"
                                                         optionKey="id" optionValue="name"></g:select>
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
        <!-- <a href="#create-opportunityWorkflowStageAttachmentType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-opportunityWorkflowStageAttachmentType" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.opportunityWorkflowStageAttachmentType}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunityWorkflowStageAttachmentType}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                    <f:all bean="opportunityWorkflowStageAttachmentType"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

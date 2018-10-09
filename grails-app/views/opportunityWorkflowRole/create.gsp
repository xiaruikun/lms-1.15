<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityWorkflowRole.label', default: 'OpportunityWorkflowRole')}" />
        <title>权限</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              区域权限
                          </li>
                          <li class="active">
                              <span>新增区域权限</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                          区域权限-新增区域权限
                      </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增区域权限
                  </div>
                  <div class="panel-body">
                      <g:form action="save" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.opportunityWorkflowRole}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.opportunityWorkflowRole}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>
                          <div class="form-group">

                              <div class="col-md-3">

                                      <g:textField name="workflow.id" required id="workflow" value="${this.opportunityWorkflowRole?.workflow?.id}" class="hide" />

                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">用户名</label>
                              <div class="col-md-3">
                                  <g:select class="form-control" name="user.id" required id="user" value="${this.opportunityWorkflowRole?.user}" from="${this.userList}" optionKey="id"></g:select>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">订单阶段</label>
                              <div class="col-md-3">
                                  <g:select class="form-control" name="stage.id" required id="stage" value="${this.opportunityWorkflowRole?.stage}" from="${this.opportunityStages}" optionKey="id"></g:select>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">权限</label>
                              <div class="col-md-3">
                                  <g:select class="form-control" name="teamRole.id" required id="teamRole" value="${this.opportunityWorkflowRole?.teamRole}" from="${com.next.TeamRole.list()}" optionKey="id" optionValue="name"></g:select>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                          <label class="col-md-3 control-label">布局</label>

                          <div class="col-md-3">
                              <g:select class="form-control" name="opportunityLayout.id"
                                        value="${this.opportunityWorkflowRole?.opportunityLayout?.id}"
                                        id="opportunityLayout"
                                        from="${com.next.OpportunityLayout.findAllByActive(true)}" optionKey="id"
                                        optionValue="description" noSelection="${['null': '请选择']}"></g:select>

                          </div>
                        </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <div class="col-md-3 col-md-offset-3">
                                  <g:submitButton class="btn btn-info" name="update" value="添加" />
                              </div>
                          </div>
                      </g:form>
                  </div>
              </div>
          </div>
      </div>

      <!--
        <a href="#create-opportunityWorkflowRole" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-opportunityWorkflowRole" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.opportunityWorkflowRole}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunityWorkflowRole}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                    <f:all bean="opportunityWorkflowRole"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

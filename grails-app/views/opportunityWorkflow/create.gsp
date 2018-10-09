<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityWorkflow.label', default: 'OpportunityWorkflow')}" />
        <title>工作流</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                            <g:link class="list" action="index">工作流</g:link>
                          </li>
                          <li class="active">
                              <span>新增工作流</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      工作流
                  </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增工作流
                  </div>
                  <div class="panel-body">
                      <g:form action="save" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.opportunityWorkflow}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.opportunityWorkflow}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>
                      <div class="form-group">
                          <label class="col-md-3 control-label">名称</label>
                          <div class="col-md-3">
                              <g:textField  class="form-control" name="name" required value="${this.opportunityWorkflow?.name}" />
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">类型</label>
                              <div class="col-md-3">
                                  <g:select class="form-control" name="opportunityType.id" required id="opportunityType" value="${this.opportunityWorkflow?.opportunityType}" from="${com.next.OpportunityType.list()}" optionKey="id" noSelection="${['null':'请选择']}"></g:select>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">是否启用</label>

                              <div class="col-md-3">
                                  <div class="radio radio-info radio-inline">
                                      <input type="radio" name="active"  value="true" checked="">
                                      <label for="radio1">true</label>
                                  </div>

                                  <div class="radio radio-info radio-inline">
                                      <input type="radio" name="active"  value="false">
                                      <label for="radio2">false</label>
                                  </div>
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


      <!--
        <a href="#create-opportunityWorkflow" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-opportunityWorkflow" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.opportunityWorkflow}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunityWorkflow}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                    <f:all bean="opportunityWorkflow"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

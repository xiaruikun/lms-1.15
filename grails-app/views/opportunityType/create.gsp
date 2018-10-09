<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityType.label', default: 'OpportunityType')}" />
        <title>新增订单类型</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li><g:link controller="opportunityType" action="index">类型</g:link></li>
                          <li class="active">
                              <span>新增类型</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      新增类型
                  </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      新增类型
                  </div>
                  <div class="panel-body">
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityType}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.opportunityType}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
                      <g:form action="save" class="form-horizontal">
                          <div class="form-group">
                              <label class="col-md-3 control-label">编号</label>
                              <div class="col-md-3"><g:textField name="code" value="${this.opportunityType?.code}" class="form-control" /></div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">名称</label>
                              <div class="col-md-3"><g:textField name="name" value="${this.opportunityType?.name}" class="form-control" /></div>
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

        <!-- <a href="#create-opportunityType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-opportunityType" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.opportunityType}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunityType}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                    <f:all bean="opportunityType"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

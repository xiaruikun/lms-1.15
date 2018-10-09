<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contactTeam.label', default: 'ContactTeam')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li><g:link controller="contact" action="show" id="${this.contactTeam?.contact?.id}">${this.contactTeam?.contact?.fullName}</g:link></li>
                          <li class="active">
                              <span>新增团队经纪人</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      新增团队经纪人
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      新增团队经纪人
                  </div>

                  <div class="panel-body">
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.contactTeam}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.contactTeam}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
                      <g:form action="save" class="form-horizontal">
                          <div class="form-group">
                              <label class="col-md-4 control-label">名称</label>

                              <div class="col-md-3">
                                <g:textField name="contact.id" value="${this.contactTeam?.contact?.id}" class="hidden"/>
                                <g:select class="form-control" name="user.id" required="required" optionKey="id"
                                        from="${this.userList}" value="${this.contactTeam?.user?.id}"
                                        noSelection="${['null': '--请选择--']}"></g:select>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-4 control-label">权限</label>

                              <div class="col-md-3">
                                <g:select class="form-control" name="teamRole.id" required="required" optionKey="id" optionValue="name"
                                        from="${com.next.TeamRole.list()}" value="${this.contactTeam?.teamRole?.id}"></g:select>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <div class="col-md-3 col-md-offset-4">
                                  <g:submitButton class="btn btn-info" name="create" value="保存"/>
                              </div>
                          </div>
                      </g:form>
                  </div>
              </div>
          </div>
      </div>
      <!--
        <a href="#create-contactTeam" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-contactTeam" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.contactTeam}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.contactTeam}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                    <f:all bean="contactTeam"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

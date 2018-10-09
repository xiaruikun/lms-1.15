<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userTeam.label', default: 'UserTeam')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              <g:link controller="userTeam" action="index">人员管理</g:link>
                          </li>
                          <li class="active">
                              <span>修改人员</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      修改人员
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      修改人员
                  </div>

                  <div class="panel-body">
                      <g:form resource="${this.userTeam}" method="PUT" class="form-horizontal">
                      <g:if test="${flash.message}">
                          <ul class="errors" role="alert">
                              <g:eachError bean="${flash.message}" var="error">
                                  <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                      <g:message error="${error}"/>
                                  </li>
                              </g:eachError>
                          </ul>
                      </g:if>
                      <g:hasErrors bean="${this.userTeam}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.userTeam}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>

                          <div class="form-group">
                              <label class="col-md-3 control-label">员工姓名</label>

                              <div class="col-md-3">
                                  <g:textField class="form-control" type="text" name="member.fullName" value="${this.userTeam?.member?.fullName}"/>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">所属部门</label>

                              <div class="col-md-3">
                                  <g:select class="form-control" name="member.department" value="${this.userTeam?.member?.department?.id}" optionKey="id" optionValue="name" from="${com.next.Department.list()}" noSelection="['':'-请选择-']"/>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">岗位</label>

                              <div class="col-md-3">
                                  <g:select class="form-control" name="member.position" value="${this.userTeam?.member?.position?.id}" optionKey="id" optionValue="name" from="${com.next.Position.list()}" noSelection="['':'-请选择-']"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-3 control-label">所在城市</label>

                              <div class="col-md-3">
                                  <g:select class="form-control" name="member.city" value="${this.userTeam?.member?.city?.id}" optionKey="id" optionValue="name" from="${com.next.City.list()}"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">所属公司</label>

                              <div class="col-md-3">
                                  <g:select class="form-control" name="member.account" value="${this.userTeam?.member?.account?.id}" optionKey="id" optionValue="name" from="${com.next.Account.list()}"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">外部唯一ID</label>

                              <div class="col-md-3">
                                  <g:textField class="form-control" type="text" name="member.externalId" value="${this.userTeam?.member?.externalId}"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <div class="col-md-3 col-md-offset-3">
                                  <g:submitButton class="btn btn-info" name="create" value="修改"/>
                              </div>
                          </div>
                      </g:form>
                  </div>
              </div>
          </div>
      </div>

        <!-- <a href="#edit-userTeam" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-userTeam" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.userTeam}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.userTeam}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.userTeam}" method="PUT">
                <g:hiddenField name="version" value="${this.userTeam?.version}" />
                <fieldset class="form">
                    <f:all bean="userTeam"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

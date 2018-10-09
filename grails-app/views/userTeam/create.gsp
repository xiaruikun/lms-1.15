<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userTeam.label', default: 'UserTeam')}" />
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
                              <g:link controller="userTeam" action="index">人员管理</g:link>
                          </li>
                          <li class="active">
                              <span>新增人员</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      人员管理
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      新增人员
                  </div>

                  <div class="panel-body">
                      <g:form action="save" class="form-horizontal">
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
                                  <input class="form-control" type="hidden" name="user.id" id="user" value="${this.userTeam?.user?.id}">
                                  <g:textField class="form-control" type="text" name="member.fullName" value="${this.userTeam?.member?.fullName}"/>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">所属部门</label>

                              <div class="col-md-3">
                                  <g:select class="form-control" name="member.department" value="${this.userTeam?.member?.department}" optionKey="id" optionValue="name" from="${com.next.Department.list()}" noSelection="['':'-请选择-']"/>
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">岗位</label>

                              <div class="col-md-3">
                                  <g:select class="form-control" name="member.position" value="${this.userTeam?.member?.position}" optionKey="id" optionValue="name" from="${com.next.Position.list()}" noSelection="['':'-请选择-']"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-3 control-label">员工手机</label>

                              <div class="col-md-3">
                                  <g:textField class="form-control" type="text" name="member.cellphone" value="${this.userTeam?.member?.cellphone}" MaxLength="11"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-3 control-label">所在城市</label>

                              <div class="col-md-3">
                                  <g:select class="form-control" name="member.city" value="${this.userTeam?.member?.city}" optionKey="id" optionValue="name" from="${com.next.City.list()}"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-3 control-label">用户名</label>

                              <div class="col-md-3">
                                  <g:textField class="form-control" type="text" name="member.username" value="${this.userTeam?.member?.username}"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-3 control-label">密码设置</label>

                              <div class="col-md-3">
                                  <g:textField class="form-control" type="password" name="member.password" value="${this.userTeam?.member?.password}"/>
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>

                          <div class="form-group">
                              <label class="col-md-3 control-label">所属公司</label>

                              <div class="col-md-3">
                                  <g:select class="form-control" name="member.account" value="${this.user?.account}" optionKey="id" optionValue="name" from="${com.next.Account.list()}"/>
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
                                  <g:submitButton class="btn btn-info" name="create" value="保存"/>
                              </div>
                          </div>
                      </g:form>
                  </div>
              </div>
          </div>
      </div>

        <!-- <a href="#create-userTeam" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-userTeam" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
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
            <g:form action="save">
                <fieldset class="form">
                    <f:all bean="userTeam"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

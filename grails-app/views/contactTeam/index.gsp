<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contactTeam.label', default: 'ContactTeam')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li class="active">
                              <span>团队经纪人</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      团队经纪人
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hgreen">
                  <div class="panel-heading">
                      <div class="panel-tools">

                      </div>
                      团队经纪人
                  </div>

                  <div class="panel-body">
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>
                                  <g:sortableColumn property="contact" title="经纪人"/>
                                  <g:sortableColumn property="user" title="用户"/>
                                  <g:sortableColumn property="teamRole" title="权限"/>

                              </tr>
                              </thead>
                              <tbody>
                              <g:each in="${contactTeamList}">
                                  <tr>
                                      <td><g:link controller="contact" action="show"
                                                  id="${it[0]}">${it[1]}</g:link></td>
                                      <td>${it[2]}</td>
                                      <td>${it[3]}</td>

                                  </tr>
                              </g:each>
                              </tbody>
                          </table>
                      </div>
                  </div>

                  <div class="panel-footer">
                      <div class="pagination">
                          <g:paginate total="${contactTeamCount ?: 0}" params="${params}"/>
                      </div>
                  </div>
              </div>
          </div>
      </div>

      <!--
        <a href="#list-contactTeam" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-contactTeam" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${contactTeamList}" />

            <div class="pagination">
                <g:paginate total="${contactTeamCount ?: 0}" />
            </div>
        </div> -->
    </body>
</html>

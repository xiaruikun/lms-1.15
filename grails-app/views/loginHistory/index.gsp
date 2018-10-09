<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'loginHistory.label', default: 'LoginHistory')}" />
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
                              <span>登录明细</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      登录明细
                  </h2>
                  <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有消息</small>
              </div>
          </div>
      </div>

      <div class="content animate-panel">

          <g:if test="${flash.message}">
              <div class="alert alert-success alert-dismissible" message="alert">
                  ${flash.message}
              </div>
          </g:if>
          <div class="row">
              <div class="hpanel hgreen">
                  <div class="panel-heading">
                      <div class="panel-tools">
                      </div>
                      全部登录明细
                  </div>

                  <div class="panel-body">
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>
                                  <g:sortableColumn property="userName" title="${message(code: 'loginHistory.userName.label', default: '登录名')}"/>
                                  <g:sortableColumn property="ip" title="${message(code: 'loginHistory.ip.label', default: 'IP')}"/>
                                  <g:sortableColumn property="status" title="${message(code: 'loginHistory.status.label', default: '状态')}"/>
                                  <g:sortableColumn property="createdDate" title="${message(code: 'loginHistory.createdDate.label', default: '登录时间')}"/>

                              </tr>
                              </thead>
                              <tbody>
                              <g:each in="${loginHistoryList}">
                                  <tr>
                                  <td>${it?.userName}</td>
                                  <td>${it?.ip}</td>
                                  <td>${it?.status}</td>
                                  <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.createdDate}"/></td>
                                  </tr>
                              </g:each>
                              </tbody>
                          </table>
                      </div>
                  </div>

                  <div class="panel-footer">
                      <div class="pagination">
                          <g:paginate total="${loginHistoryCount ?: 0}" params="${params}"/>
                      </div>
                  </div>
              </div>
          </div>

      </div>


        <!-- <a href="#list-loginHistory" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-loginHistory" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${loginHistoryList}" />

            <div class="pagination">
                <g:paginate total="${loginHistoryCount ?: 0}" />
            </div>
        </div> -->
    </body>
</html>

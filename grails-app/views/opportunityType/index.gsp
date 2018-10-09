<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityType.label', default: 'OpportunityType')}" />
        <title>订单类型</title>
    </head>
    <body>

      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li class="active">
                              <span>订单类型</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      订单类型
                  </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hgreen">
                  <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="OpportunityType" action="create"><i class="fa fa-plus"></i>新增</g:link>
                    </div>
                      订单类型
                  </div>
                  <div class="panel-body">
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead>
                                  <tr>
                                      <g:sortableColumn property="code" title="${message(code: 'opportunityType.code.label', default: '编号')}" />
                                      <g:sortableColumn property="name" title="${message(code: 'opportunityType.name.label', default: '名称')}" />

                                  </tr>
                              </thead>
                              <tbody>
                                  <g:each in="${opportunityTypeList}">
                                      <tr>
                                          <td><g:link controller="opportunityType" action="show" id="${it?.id}">${it?.code}</g:link></td>
                                          <td>${it?.name}</td>

                                      </tr>
                                  </g:each>
                              </tbody>
                          </table>
                      </div>
                  </div>
                  <div class="panel-footer">
                      <div class="pagination">
                          <g:paginate total="${opportunityTypeCount ?: 0}" params="${params}" />
                      </div>
                  </div>
              </div>
          </div>
      </div>
        <!-- <a href="#list-opportunityType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-opportunityType" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${opportunityTypeList}" />

            <div class="pagination">
                <g:paginate total="${opportunityTypeCount ?: 0}" />
            </div>
        </div> -->
    </body>
</html>

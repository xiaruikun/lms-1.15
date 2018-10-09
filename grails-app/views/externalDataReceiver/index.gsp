<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'externalDataReceiver.label', default: 'ExternalDataReceiver')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>

                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      外部数据接收
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hgreen">
                  <div class="panel-heading">
                      <div class="panel-tools">
                        <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                      </div>
                      外部数据接收
                  </div>

                  <div class="panel-body">
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>

                                  <g:sortableColumn property="name"
                                                    title="${message(code: 'externalDataReceiver.name.label', default: '名称')}"/>
                                  <g:sortableColumn property="active"
                                                    title="${message(code: 'externalDataReceiver.active.label', default: '是否启用')}"/>
                                  <g:sortableColumn property="createdBy"
                                                    title="${message(code: 'externalDataReceiver.createdBy.label', default: '创建人')}"/>
                                  <g:sortableColumn property="modifiedBy"
                                                    title="${message(code: 'externalDataReceiver.modifiedBy.label', default: '修改人')}"/>
                                  <g:sortableColumn property="createdDate"
                                                    title="${message(code: 'externalDataReceiver.createdDate.label', default: '创建时间')}"/>
                                  <g:sortableColumn property="modifiedDate"
                                                    title="${message(code: 'externalDataReceiver.modifiedDate.label', default: '修改时间')}"/>

                              </tr>
                              </thead>
                              <tbody>
                              <g:each in="${externalDataReceiverList}">
                                  <tr>
                                      <td><g:link controller="externalDataReceiver" action="show"
                                                  id="${it?.id}">${it?.name}</g:link></td>
                                      <td>${it?.active}</td>
                                      <td>
                                        ${it?.createdBy}
                                      </td>
                                      <td>
                                        ${it?.modifiedBy}
                                      </td>
                                      <td>
                                        ${it?.createdDate}
                                      </td>
                                      <td>
                                        ${it?.modifiedDate}
                                      </td>

                                  </tr>
                              </g:each>
                              </tbody>
                          </table>
                      </div>
                  </div>

                  <div class="panel-footer">
                      <div class="pagination">
                          <g:paginate total="${externalDataReceiverCount ?: 0}" params="${params}"/>
                      </div>
                  </div>
              </div>
          </div>
      </div>

      <!--
        <a href="#list-externalDataReceiver" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-externalDataReceiver" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${externalDataReceiverList}" />

            <div class="pagination">
                <g:paginate total="${externalDataReceiverCount ?: 0}" />
            </div>
        </div> -->
    </body>
</html>

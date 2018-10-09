<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'workflowInstanceUser.label', default: 'WorkflowInstanceUser')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
  </head>
  <body class="fixed-navbar fixed-sidebar">
    <div class="small-header transition animated fadeIn">
      <div class="hpanel">
        <div class="panel-body">
          <div id="hbreadcrumb" class="pull-right">
            <ol class="hbreadcrumb breadcrumb">
              <li>中佳信LMS</li>
              <li class="active">
                <span>订单-权限</span>
              </li>
            </ol>
          </div>
          <h2 class="font-light m-b-xs">
            订单-权限
          </h2>
        </div>
      </div>
    </div>

    <g:if test="${flash.message}">
      <div class="row">
        <div class="hpanel">
          <div class="panel-body">
            <div class="alert alert-info" role="alert">${flash.message}</div>
          </div>
        </div>
      </div>
    </g:if>

    <div class="content animate-panel">
      <div class="row">
        <div class="hpanel hgreen">
          <div class="panel-heading">
            所有订单-权限
          </div>
          <div class="panel-body">
            <div class="table-responsive">
              <table class="table table-striped table-bordered table-hover">
                <thead>
                  <tr>
                    <g:sortableColumn property="serialNumber" title="${message(code: 'workflowInstanceUser.stage.instance.opportunity.serialNumber.label', default: '订单编号')}"/>
                    <g:sortableColumn property="name" title="${message(code: 'workflowInstanceUser.stage.instance.name.label', default: '工作流')}"/>
                    <g:sortableColumn property="stage" title="${message(code: 'workflowInstanceUser.stage.instance.stage.label', default: '当前阶段')}"/>
                    <g:sortableColumn property="status" title="${message(code: 'workflowInstanceUser.stage.instance.status.label', default: '当前状态')}"/>
                    <g:sortableColumn property="createdDate" title="${message(code: 'workflowInstanceUser.stage.instance.createdDate.label', default: '申请时间')}"></g:sortableColumn>
                    <g:sortableColumn property="modifiedDate" title="${message(code: 'workflowInstanceUser.stage.instance.modifiedDate.label', default: '修改时间')}"></g:sortableColumn>
                  </tr>
                </thead>
                <tbody>
                  <g:each in="${workflowInstanceUserList}">
                    <tr>
                      <td>
                        <g:link controller="opportunity" action="show" id="${it[0]}">${it[1]}</g:link>
                      </td>
                      <td>${it[2]}</td>
                      <td>${it[3]}</td>
                      <td>${it[4]}</td>
                      <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it[5]}"/></td>
                      <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it[6]}"/></td>


                    </tr>
                  </g:each>
                </tbody>
              </table>
            </div>
          </div>
          <div class="panel-footer">
            <div class="pagination">
              <g:paginate total="${workflowInstanceUserCount ?: 0}" params="${params}"/>
            </div>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>

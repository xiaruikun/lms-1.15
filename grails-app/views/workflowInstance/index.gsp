<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'workflowInstance.label', default: 'WorkflowInstance')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li><g:link controller="opportunity" action="show" id="${this.opportunity?.id}">订单详情</g:link></li>
                          <li class="active">
                              <span>工作流2.0</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      工作流2.0
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
                      工作流2.0
                  </div>

                  <div class="panel-body">
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>
                                  <g:sortableColumn property="name" title="名称"/>
                                  <g:sortableColumn property="workflow" title="工作流"/>
                                  <g:sortableColumn property="stage" title="当前阶段"/>
                                  <g:sortableColumn property="status" title="当前状态"/>
                                  <g:sortableColumn property="createdDate" title="创建时间"/>
                                  <g:sortableColumn property="modifiedDate" title="修改时间"/>
                                  <sec:ifNotGranted roles="ROLE_COO, ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                                  <g:sortableColumn property="operation" class="text-center" title="操作"/>
                                </sec:ifNotGranted>
                              </tr>
                              </thead>
                              <tbody>
                              <g:each in="${workflowInstanceList}">
                                  <tr>
                                      <td>
                                        <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                        <g:link controller="workflowInstance" action="show"
                                                  id="${it?.id}">${it?.name}</g:link>
                                        </sec:ifAllGranted>
                                        <sec:ifNotGranted roles="ROLE_ADMINISTRATOR">
                                        ${it?.name}
                                        </sec:ifNotGranted>
                                      </td>
                                      <td>${it?.workflow?.name}</td>
                                      <td>${it?.stage?.name}</td>
                                      <td>${it?.status?.name}</td>
                                      <td>${it?.createdDate}</td>
                                      <td>${it?.modifiedDate}</td>
                                      <sec:ifNotGranted roles="ROLE_COO, ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                                      <td class="text-center">
                                        <g:link class="btn btn-info btn-xs" controller="workflowInstance" id="${it?.id}"
                                                action="reject"><i class="fa fa-arrow-up"></i> 上一步</g:link>
                                        <g:link class="btn btn-info btn-xs" controller="workflowInstance" id="${it?.id}"
                                                action="approve"><i class="fa fa-arrow-down"></i> 下一步</g:link>
                                        <g:link class="btn btn-danger btn-xs" controller="workflowInstance" id="${it?.id}"
                                                        action="cancel"><i class="fa fa-exclamation-circle"></i> 失败</g:link>
                                        <g:link class="btn btn-success btn-xs" controller="workflowInstance" id="${it?.id}"
                                                action="complete"><i class="fa fa-check"></i> 完成</g:link>
                                      </td>
                                    </sec:ifNotGranted>
                                  </tr>
                              </g:each>
                              </tbody>
                          </table>
                      </div>
                  </div>

                  <div class="panel-footer">
                      <div class="pagination">
                          <g:paginate total="${workflowInstanceCount ?: 0}" params="${params}"/>
                      </div>
                  </div>
              </div>
          </div>
      </div>

    </body>
</html>

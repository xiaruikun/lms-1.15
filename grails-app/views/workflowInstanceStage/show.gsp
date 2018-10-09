<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'workflowInstanceInstanceStage.label', default: 'workflowInstanceInstanceStage')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header transition animated fadeIn">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              <g:link controller="workflowInstance" action="show" id="${this.workflowInstanceStage?.instance?.id}">工作流2.0</g:link>
                          </li>
                          <li class="active">
                              <span>${this.workflowInstanceStage?.name}</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      ${this.workflowInstanceStage?.name}
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel horange contact-panel">
                  <div class="panel-heading">
                      <div class="panel-tools">

                      </div>
                      ${this.workflowInstanceStage?.name}基本信息
                  </div>

                  <div class="panel-body">
                      <div class="text-muted font-bold m-b-xs ol-md-6">
                          <div class="col-md-12">
                              <h4>
                                  ${this.workflowInstanceStage?.name}
                              </h4>
                          </div>
                      </div>
                  </div>

                  <div class="panel-footer contact-footer">
                      <div class="row">

                          <div class="col-md-4 border-right">

                              <div class="contact-stat">
                                  <span>名称</span>
                                  <strong>${this.workflowInstanceStage?.name}</strong>
                              </div>

                          </div>

                          <div class="col-md-4 border-right">

                              <div class="contact-stat">
                                  <span>执行次序</span>
                                  <strong>${this.workflowInstanceStage?.executionSequence}</strong>
                              </div>

                          </div>

                          <div class="col-md-4 border-right">

                              <div class="contact-stat">
                                  <span>是否回退</span>
                                  <strong>${this.workflowInstanceStage?.canReject}</strong>
                              </div>

                          </div>
                      </div>
                  </div>
              </div>
          </div>
          <g:if test="${flash.message}">
          <div class="message" role="status">${flash.message}</div>
          </g:if>

          <div class="row">
              <div class="hpanel horange">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION">
                              <g:link class="btn btn-info btn-xs" controller="workflowInstanceCondition"
                                      action="create"
                                      params="[stage: this.workflowInstanceStage.id]">
                                  <i class="fa fa-plus"></i>新增
                              </g:link>
                          </sec:ifNotGranted>
                          <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                      </div>
                      约束条件
                  </div>

                  <div class="panel-body no-padding">
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead>
                              <th>执行次序</th>
                              <th>组件</th>
                              <th>分支</th>
                              <th>描述</th>
                              <th>日志</th>
                              <th>操作</th>
                              </thead>
                              <tbody>
                              <g:each in="${this.workflowInstanceConditions}">
                                  <tr>

                                      <td>
                                          ${it?.executeSequence}
                                      </td>
                                      <td>
                                          ${it?.component?.name}
                                      </td>
                                      <td>
                                          ${it?.nextStage?.name}
                                      </td>
                                      <td>
                                          ${it?.message}
                                      </td>
                                      <td>${it?.log}</td>
                                      <td width="10%" class="text-center">
                                          <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION">

                                              <g:form controller="workflowInstanceCondition" action="delete" style="display: inline-block"
                                                      style="display: inline-block"
                                                      id="${it.id}"
                                                      method="DELETE">
                                                  <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                                      <i class="fa fa-trash-o"></i>
                                                      删除
                                                  </button>
                                              </g:form>
                                          </sec:ifNotGranted>

                                      </td>
                                  </tr>

                              </g:each>
                              </tbody>
                          </table>
                      </div>
                  </div>
              </div>
          </div>


          <div class="row">
              <div class="hpanel hviolet">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <sec:ifNotGranted roles="ROLE_CONDITION_RULEENGINE">
                              <g:link class="btn btn-info btn-xs" controller="workflowInstanceEvent" action="create"
                                      params="[stage: this.workflowInstanceStage.id]">
                                  <i class="fa fa-plus"></i>新增
                              </g:link>
                          </sec:ifNotGranted>
                          <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                      </div>
                      事件
                  </div>

                  <div class="panel-body no-padding">
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>
                                  <g:sortableColumn property="executeSequence"
                                                    title="${message(code: 'workflowInstanceEvent.executeSequence.label', default: '执行次序')}"/>
                                  <g:sortableColumn property="isSynchronous"
                                                    title="${message(code: 'workflowInstanceEvent.isSynchronous.label', default: '同步执行')}"/>
                                  <g:sortableColumn property="component"
                                                    title="${message(code: 'workflowInstanceEvent.component.label', default: '组件')}"/>
                                  <g:sortableColumn property="log"
                                                    title="${message(code: 'workflowInstanceEvent.log.label', default: '日志')}"/>
                                  <g:sortableColumn property="startTime"
                                                    title="${message(code: 'workflowInstanceEvent.startTime.label', default: '开始时间')}"/>
                                  <g:sortableColumn property="endTime"
                                                    title="${message(code: 'workflowInstanceEvent.endTime.label', default: '结束时间')}"/>
                                  <g:sortableColumn width="10%" property="operation" class="text-center" title="操作"/>
                              </tr>
                              </thead>
                              <tbody>
                              <g:each in="${this.workflowInstanceEvents}">
                                  <tr>
                                      <td>
                                          ${it?.executeSequence}
                                      </td>
                                      <td>
                                          ${it?.isSynchronous}
                                      </td>
                                      <td>
                                          ${it?.component?.name}
                                      </td>
                                      <td>${it?.log}</td>
                                      <td>${it?.startTime}</td>
                                      <td>${it?.endTime}</td>

                                      <td width="10%" class="text-center">
                                          <sec:ifNotGranted roles="ROLE_CONDITION_RULEENGINE">

                                              <g:form controller="workflowInstanceEvent" action="delete" id="${it.id}" style="display: inline-block"
                                                      method="DELETE">
                                                  <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                                      <i class="fa fa-trash-o"></i>
                                                      删除
                                                  </button>
                                              </g:form>
                                          </sec:ifNotGranted>
                                      </td>

                                  </tr>

                              </g:each>
                              </tbody>
                          </table>
                      </div>

                  </div>
              </div>
          </div>
          <div class="row">
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                              <g:link class="btn btn-info btn-xs" controller="workflowInstanceUser" action="create"
                                      params="[stage: this.workflowInstanceStage.id]">
                                  <i class="fa fa-plus"></i>新增</g:link>
                          </sec:ifNotGranted>

                          <a class="showhide"><i class="fa fa-chevron-up"></i></a>

                      </div>
                      权限
                  </div>

                  <div class="panel-body no-padding">
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>
                                  <g:sortableColumn property="user"
                                                    title="用户"/>
                                  <g:sortableColumn property="authority"
                                                    title="用户"/>
                                  <g:sortableColumn width="10%" class="text-center" property="operation"
                                                    title="操作"/>
                              </tr>
                              </thead>
                              <tbody>
                              <g:each in="${this.workflowInstanceUsers}">
                                  <tr>
                                      <td>${it.user}</td>
                                      <td>
                                        ${it?.authority?.name}
                                      </td>
                                      <td class="text-center">
                                          <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                                                    <g:form controller="workflowInstanceUser" action="delete" id="${it.id}" style="display:inline-block"
                                                      method="DELETE">
                                                  <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                                      <i class="fa fa-trash-o"></i> 删除</button>
                                              </g:form>
                                          </sec:ifNotGranted>
                                      </td>
                                  </tr>
                              </g:each>
                              </tbody>
                          </table>
                      </div>
                  </div>
              </div>
          </div>

          <div class="row">
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                              <g:link class="btn btn-info btn-xs" controller="workflowInstanceNotification" action="create"
                                      params="[stage: this.workflowInstanceStage.id]">
                                  <i class="fa fa-plus"></i>新增</g:link>
                          </sec:ifNotGranted>
                          <a class="showhide">
                              <i class="fa fa-chevron-up"></i>
                          </a>
                      </div>
                      消息
                  </div>

                  <div class="panel-body no-padding">
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>
                                  <g:sortableColumn property="user"
                                                    title="用户"/>
                                  <g:sortableColumn property="messageTemplate"
                                                    title="${message(code: 'workflowInstanceNotification.messageTemplate.label', default: '消息模板')}"/>
                                  <g:sortableColumn property="toManager"
                                                    title="${message(code: 'workflowInstanceNotification.toManager.label', default: '推送主管')}"/>
                                  <g:sortableColumn property="cellphone"
                                                    title="${message(code: 'workflowInstanceNotification.cellphone.label', default: '手机号')}"/>
                                  <g:sortableColumn width="10%" class="text-center" property="operation"
                                                                      title="操作"/>
                              </tr>
                              </thead>
                              <tbody>
                              <g:each in="${this.workflowInstanceNotifications}">
                                  <tr>
                                      <td>${it?.user}</td>
                                      <td>${it?.messageTemplate}</td>
                                      <td>${it?.toManager}</td>
                                      <td>
                                        <pre>
                                            <code>${it?.cellphone}</code>
                                        </pre>
                                      </td>
                                      <td class="text-center">
                                          <sec:ifNotGranted roles="ROLE_EVENT_CONFIGURATION,ROLE_CONDITION_RULEENGINE">
                                              <g:form controller="workflowInstanceNotification" action="delete" id="${it?.id}" style="display:inline-block"
                                                      method="DELETE">
                                                  <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                                      <i class="fa fa-trash-o"></i> 删除</button>
                                              </g:form>
                                          </sec:ifNotGranted>
                                      </td>
                                  </tr>
                              </g:each>
                              </tbody>
                          </table>
                      </div>
                  </div>
              </div>
          </div>
      </div>
    </body>
</html>

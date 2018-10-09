<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'externalDataReceiver.label', default: 'ExternalDataReceiver')}" />
        <title>外部数据接收</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              外部数据接收
                          </li>
                          <li class="active">
                              <span>${this.externalDataReceiver?.name}</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      ${this.externalDataReceiver?.name}
                  </h2>
                  <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>${this.externalDataReceiver?.name}的信息</small>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hred contact-panel">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <g:link class="btn btn-info btn-xs" action="edit" resource="${this.externalDataReceiver}"><i class="fa fa-edit"></i>编辑</g:link>
                      </div>
                      基本信息
                  </div>
                  <div class="panel-body">
                      <div class="text-muted font-bold m-b-xs ol-md-6">
                          <div class="col-md-12">
                            <g:if test="${this.externalDataReceiver?.active == false}"><span
                                    class="label label-danger pull-right">未启用</span></g:if>
                            <g:else test="${this.externalDataReceiver?.active == true}"><span
                                    class="label label-success pull-right">已启用</span></g:else>
                              <h4>${this.externalDataReceiver?.name}</h4></div>

                      </div>
                  </div>
                  <div class="panel-footer contact-footer">
                      <div class="row border-bottom">

                          <div class="col-md-3 border-right" >

                                  <div class="contact-stat"><span>创建时间</span> <strong>${this.externalDataReceiver?.createdDate}</strong></div>

                          </div>
                          <div class="col-md-3 border-right" >

                                  <div class="contact-stat"><span>修改时间</span> <strong>${this.externalDataReceiver?.modifiedDate}</strong></div>
                          </div>
                          <div class="col-md-3 border-right" >

                                  <div class="contact-stat"><span>创建人</span> <strong>${this.externalDataReceiver?.createdBy}</strong></div>
                          </div>
                          <div class="col-md-3 border-right" >

                                  <div class="contact-stat"><span>修改人</span> <strong>${this.externalDataReceiver?.modifiedBy}</strong></div>

                          </div>


                      </div>
                      <div class="row">

                          <div class="col-md-12" >
                                  <pre>
                                    <code>${this.externalDataReceiver?.script}</code>
                                  </pre>
                          </div>

                      </div>
                  </div>
              </div>
          </div>
          <div class="row">
              <div class="hpanel hred collapsed">
                  <div class="panel-heading hbuilt">
                      <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="externalDataReceiverMessage" action="create"
                                params="[receiver: this.externalDataReceiver?.id]">
                            <i class="fa fa-plus"></i>新增</g:link>
                        <a class="showhide">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                      </div>
                      Message
                  </div>
                  <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="code"
                                                  title="${message(code: 'externalDataReceiverMessage.code.label', default: '编码')}"/>
                                <g:sortableColumn property="message"
                                                  title="${message(code: 'externalDataReceiverMessage.message.label', default: '消息')}"/>
                                <g:sortableColumn property="message" width="8%" class="text-center"
                                                  title="${message(code: 'externalDataReceiverMessage.message.label', default: '操作')}"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.externalDataReceiverMessages}">
                                <tr>
                                    <td>${it?.code}</td>
                                    <td>${it?.message}</td>
                                    <td width="8%" class="text-center">
                                      <g:form resource="${it}" method="DELETE">
                                          <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                  class="fa fa-trash-o"></i> 删除</button>
                                      </g:form>
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
              <div class="hpanel hred collapsed">
                  <div class="panel-heading hbuilt">
                      <div class="panel-tools">
                        <a class="showhide">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                      </div>
                      AutitTrail
                  </div>
                  <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="log"
                                                  title="${message(code: 'ExternalDataReceiverAutitTrail.log.label', default: '日志')}"/>
                                <g:sortableColumn property="startTime"
                                                  title="${message(code: 'ExternalDataReceiverAutitTrail.startTime.label', default: '开始时间')}"/>
                                                  <g:sortableColumn property="endTime"
                                                                    title="${message(code: 'ExternalDataReceiverAutitTrail.endTime.label', default: '结束时间')}"/>
                                                                    <g:sortableColumn property="createdBy"
                                                                                      title="${message(code: 'ExternalDataReceiverAutitTrail.createdBy.label', default: '创建人')}"/>
                                                                                      <g:sortableColumn property="createdDate"
                                                                                                        title="${message(code: 'ExternalDataReceiverAutitTrail.createdDate.label', default: '创建时间')}"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.externalDataReceiverAutitTrails}">
                                <tr>
                                    <td>${it?.log}</td>
                                    <td>${it?.startTime}</td>
                                    <td>
                                      ${it?.endTime}
                                    </td>
                                    <td>
                                      ${it?.createdBy}
                                    </td>
                                    <td>
                                      ${it?.createdDate}
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






        <!-- <a href="#show-externalDataReceiver" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-externalDataReceiver" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="externalDataReceiver" />
            <g:form resource="${this.externalDataReceiver}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.externalDataReceiver}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

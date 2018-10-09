<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'timingTask.label', default: 'TimingTask')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
  </head>
  <body>
    <div class="small-header">
      <div class="hpanel">
        <div class="panel-body">
          <div id="hbreadcrumb" class="pull-right">
            <ol class="hbreadcrumb breadcrumb">
              <li>中佳信LMS</li>
              <li><g:link controller="timingTask" action="index">定时任务</g:link></li>
              <li class="active">
                <span>详情</span>
              </li>
            </ol>
          </div>
          <h2 class="font-light m-b-xs">
            定时任务详情
          </h2>
        </div>
      </div>
    </div>
    <div class="content animate-panel">
      <div class="row">
        <div class="hpanel hblue">
          <div class="panel-heading">
            <div class="panel-tools">
              <g:link class="btn btn-info btn-xs" action="edit" resource="${this.timingTask}">
                <i class="fa fa-edit"></i>编辑</g:link>
            </div>
            定时任务详情
          </div>
          <div class="panel-body form-horizontal">
            <g:if test="${flash.message}">
              <div class="message" role="status">${flash.message}</div>
            </g:if>
            <div class="form-group">
              <label class="col-md-3  control-label">组件</label>
              <div class="col-md-4">
                <g:textField class="form-control" disabled="" name="component" value="${this.timingTask?.component?.name}"></g:textField>
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-md-3 control-label">周期</label>

              <div class="col-md-4">
                <g:textField class="form-control" disabled="" name="duration" value="${this.timingTask?.duration}"></g:textField>
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-md-3 control-label">执行次数</label>
              <div class="col-md-4">
                <g:textField class="form-control" disabled="" name="start" value="${this.timingTask?.start}"></g:textField>
              </div>
            </div>
            
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-md-3  control-label">日志</label>
              <div class="col-md-4">
                <g:textArea name="log" value="${this.timingTask?.log}" disabled="" class="form-control textarea"
                            rows="25" cols="15"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>

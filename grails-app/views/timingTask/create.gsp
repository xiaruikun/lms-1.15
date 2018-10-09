<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'timingTask.label', default: 'TimingTask')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
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
                <span>新增定时任务</span>
              </li>
            </ol>
          </div>

          <h2 class="font-light m-b-xs">
            新增定时任务
          </h2>
        </div>
      </div>
    </div>

    <div class="content animate-panel">
      <div class="row">
        <div class="hpanel hblue">
          <div class="panel-heading">
            新增定时任务
          </div>

          <div class="panel-body">
            <g:form action="save" class="form-horizontal flowForm">
              <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
              </g:if>
              <g:hasErrors bean="${this.timingTask}">
                <ul class="errors" role="alert">
                  <g:eachError bean="${this.timingTask}" var="error"><li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
              </g:eachError>
            </ul>
          </g:hasErrors>
          <div class="form-group">
            <label class="col-md-4 control-label">组件</label>

            <div class="col-md-4">
              <g:select class="form-control" name="component.id" id="component" value="${this.timingTask?.component?.id}" from="${com.next.Component.findAllByActive(true)}" optionKey="id" optionValue="name" noSelection="['': '--请选择--']"></g:select>

            </div>
          </div>
          <div class="hr-line-dashed"></div>
          <div class="form-group">
            <label class="col-md-4 control-label">周期</label>

            <div class="col-md-4">
              <g:select class="form-control" name="duration" id="duration" value="${this.timingTask?.duration}" from="['Everyday', 'Weekly', 'Monthly']"></g:select>

            </div>
          </div>
          <div class="hr-line-dashed"></div>

          <div class="form-group">
            <div class="col-md-3 col-md-offset-5">
              <button class="submitBtn btn btn-info" type="submit">保存</button>
            </div>
          </div>
        </g:form>
      </div>
    </div>
  </div>
</div>
</body>
</html>

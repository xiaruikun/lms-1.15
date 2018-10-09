<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityWorkflowRole.label', default: 'OpportunityWorkflowRole')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>

                          <li class="active">
                              <span>权限详情</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      权限详情
                  </h2>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <g:link class="btn btn-info btn-xs" action="edit" resource="${this.opportunityWorkflowRole}"><i
                                  class="fa fa-edit"></i>编辑</g:link>
                      </div>
                      权限详情
                  </div>
                  <div class="panel-body form-horizontal">
                      <div class="form-group" >
                          <label class="col-md-3 control-label">用户：</label>
                          <div class="col-md-4">
                              <g:textField class="form-control" disabled="" name="user.id" value="${this.opportunityWorkflowRole?.user}"></g:textField>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>
                      <div class="form-group" >
                          <label class="col-md-3 control-label">阶段</label>
                          <div class="col-md-4">
                              <g:textField class="form-control" disabled="" name="stage.id" value="${this.opportunityWorkflowRole?.stage?.name}"></g:textField>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>

                      <div class="form-group" >
                          <label class="col-md-3 control-label">权限：</label>
                          <div class="col-md-4">
                            <g:textField class="form-control" disabled="" name="teamRole.id" value="${this.opportunityWorkflowRole?.teamRole?.name}"></g:textField>
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-3 control-label">布局：</label>
                          <div class="col-md-3">
                              <input class="form-control" disabled="disabled" type="text" value="${this.opportunityWorkflowRole?.opportunityLayout?.description}">
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>
                  </div>
              </div>
          </div>
      </div>


        <!-- <a href="#show-opportunityWorkflowRole" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-opportunityWorkflowRole" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="opportunityWorkflowRole" />
            <g:form resource="${this.opportunityWorkflowRole}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.opportunityWorkflowRole}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

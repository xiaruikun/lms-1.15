<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityCsvFile.label', default: 'OpportunityCsvFile')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              <g:link controller="OpportunityCsvFile" action="index">存量订单导入</g:link>
                          </li>
                          <li class="active">
                              <span>${this.opportunityCsvFile?.filename}</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      存量订单导入
                  </h2>
              </div>
          </div>
        </div>
        <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <g:link class="btn btn-default btn-xs" action="edit" resource="${this.opportunityCsvFile}">编辑</g:link>
                          <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                      </div>
                      文件详情
                  </div>
                  <div class="panel-body form-horizontal">
                      <div class="form-group" >
                          <label class="col-md-1 col-md-offset-3 control-label">文件名称</label>
                          <div class="col-md-8">
                              <g:textField class="form-control" disabled="" name="filename" value="${this.opportunityCsvFile?.filename}"></g:textField>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>
                      <div class="form-group" >
                          <label class="col-md-1 col-md-offset-3 control-label">文件类型</label>
                          <div class="col-md-8">
                              <g:textField class="form-control" disabled="" name="filetype" value="${this.opportunityCsvFile?.filetype}"></g:textField>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>
                      <div class="form-group" >
                          <label class="col-md-1 col-md-offset-3 control-label">文件路径</label>
                          <div class="col-md-8">
                              <g:textField class="form-control" disabled="" name="filepath" value="${this.opportunityCsvFile?.filepath}"></g:textField>
                          </div>
                      </div>
                      <div class="hr-line-dashed"></div>

                  </div>
              </div>
          </div>
        </div>

        <!--<a href="#show-opportunityCsvFile" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-opportunityCsvFile" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="opportunityCsvFile" />
            <g:form resource="${this.opportunityCsvFile}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.opportunityCsvFile}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

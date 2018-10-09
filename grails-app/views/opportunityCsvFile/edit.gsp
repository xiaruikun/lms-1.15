<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityCsvFile.label', default: 'OpportunityCsvFile')}" />
        <title>导入文件名编辑</title>
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
                              <span>导入文件名编辑</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      存量订单文件
                  </h2>
              </div>
          </div>
        </div>
        <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      文件名编辑
                  </div>
                  <div class="panel-body">
                      <g:form resource="${this.opportunityCsvFile}" method="PUT" class="form-horizontal">
                      <g:if test="${flash.message}">
                      <div class="message" role="status">${flash.message}</div>
                      </g:if>
                      <g:hasErrors bean="${this.opportunityCsvFile}">
                      <ul class="errors" role="alert">
                          <g:eachError bean="${this.opportunityCsvFile}" var="error">
                          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                          </g:eachError>
                      </ul>
                      </g:hasErrors>
                          <div class="form-group">
                              <label class="col-md-3 control-label">文件名称:</label>
                              <div class="col-md-4">
                                  <g:textField name="filename" value="${this.opportunityCsvFile?.filename}" class="form-control" />
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">文件类型:</label>
                              <div class="col-md-4">
                                  <g:textField name="filename" value="${this.opportunityCsvFile?.filetype}" class="form-control" />
                              </div>
                          </div>
                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <label class="col-md-3 control-label">文件路径</label>
                              <div class="col-md-8">
                                  <g:textField name="filepath" value="${this.opportunityCsvFile?.filepath}" disabled=""class="form-control" />
                              </div>
                          </div>

                          <div class="hr-line-dashed"></div>
                          <div class="form-group">
                              <div class="col-md-3 col-md-offset-3">
                                  <g:submitButton class="btn btn-info" name="update" value="修改" />
                              </div>
                          </div>
                      </g:form>
                  </div>
              </div>
          </div>
        </div>
        <!--<a href="#edit-opportunityCsvFile" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-opportunityCsvFile" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.opportunityCsvFile}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunityCsvFile}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.opportunityCsvFile}" method="PUT">
                <g:hiddenField name="version" value="${this.opportunityCsvFile?.version}" />
                <fieldset class="form">
                    <f:all bean="opportunityCsvFile"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

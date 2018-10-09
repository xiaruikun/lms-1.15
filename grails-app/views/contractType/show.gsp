<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contractType.label', default: 'ContractType')}" />
        <title>合同类型</title>
    </head>
    <body>
      <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li>
                              合同类型
                          </li>
                          <li class="active">
                              <span>${this.contractType?.name}</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      ${this.contractType?.name}
                  </h2>
                  <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>${this.contractType?.name}的信息</small>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
          <div class="row">
            <g:if test="${flash.message}">
                <div class="message alert alert-info" role="status">${flash.message}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                </div>
            </g:if>
            <g:hasErrors bean="${this.contractType}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${this.contractType}" var="error">
                        <li>
                            <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>
                            <g:message error="${error}"/>
                        </li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
              <div class="hpanel hred contact-panel">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <g:link class="btn btn-info btn-xs" action="edit" resource="${this.contractType}"><i class="fa fa-edit"></i>编辑</g:link>
                      </div>
                      基本信息
                  </div>
                  <div class="panel-body">
                      <div class="text-muted font-bold m-b-xs ol-md-6">
                          <div class="col-md-12">

                              <h4>${this.contractType?.name}</h4></div>

                      </div>
                  </div>

              </div>
          </div>
          <div class="row">
              <div class="hpanel hred collapsed">
                  <div class="panel-heading hbuilt">
                      <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="contractTemplate" action="create"
                                params="[type: this.contractType?.id]">
                            <i class="fa fa-plus"></i>新增</g:link>
                        <a class="showhide">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                      </div>
                      模板
                  </div>
                  <div class="panel-body no-padding">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="name"
                                                  title="${message(code: 'contractTemplate.name.label', default: '名称')}"/>

                                <g:sortableColumn property="message" width="8%" class="text-center"
                                                  title="${message(code: 'contractTemplate.name.label', default: '操作')}"/>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${this.contractTemplates}">
                                <tr>
                                    <td><g:link controller="contractTemplate" action="show" id="${it?.id}">${it?.name}</g:link></td>
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

        </div>


        <!-- <a href="#show-contractType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-contractType" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="contractType" />
            <g:form resource="${this.contractType}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.contractType}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

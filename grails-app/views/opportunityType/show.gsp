<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityType.label', default: 'OpportunityType')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="small-header transition animated fadeIn">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li><g:link controller="opportunityType" action="index">订单类型</g:link></li>
                          <li class="active">
                              <span>${this.opportunityType?.name}</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      订单类型: ${this.opportunityType?.name}
                  </h2>
                  <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>${this.opportunityType?.name} 的信息</small>
              </div>
          </div>
      </div>
      <div class="content animate-panel">
              <div class="row">
                  <div class="hpanel hred contact-panel">
                      <div class="panel-heading">
                          <div class="panel-tools">
                              <g:link class="btn btn-info btn-xs" action="edit"
                                          resource="${this.opportunityType}"><i class="fa fa-edit"></i>编辑</g:link>
                          </div>
                          订单类型基本信息
                      </div>
                      <div class="panel-body">
                          <div class="text-muted font-bold m-b-xs ol-md-6">
                              <div class="col-md-12"><h4><a href=>${this.opportunityType?.name}</a></h4></div>
                          </div>
                      </div>
                      <div class="panel-footer contact-footer">
                          <div class="row">
                              <div class="col-md-6 border-right" style="">
                                  <div class="contact-stat"><span>类型编号:</span> <strong>${this.opportunityType?.code}</strong></div>
                              </div>
                              <div class="col-md-6 border-right" style="">
                                  <div class="contact-stat"><span>阶段数量:</span> <strong>${this.opportunityType?.stages?.size()}</strong></div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>

              <div class="row">
                  <div class="hpanel hyellow collapsed">
                      <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                          下属阶段
                      </div>
                      <div class="panel-body no-padding">
                          <ul class="list-group">
                              <g:each in="${this.opportunityType?.stages}">
                                  <li class="list-group-item">
                                      <div class="pull-right">
                                      </div>
                                      <g:link  controller="opportunityStage" action="show" id="${it?.id}">${it?.name}</g:link>
                                  </li>
                              </g:each>
                          </ul>
                      </div>
                  </div>
              </div>

      </div>
        <!-- <a href="#show-opportunityType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-opportunityType" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="opportunityType" />
            <g:form resource="${this.opportunityType}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.opportunityType}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

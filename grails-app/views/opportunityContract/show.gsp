<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityContract.label', default: 'OpportunityContract')}" />
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
                              <g:link controller="opportunity" action="show" id="${this.opportunityContract?.opportunity?.id}">订单详情</g:link>
                          </li>
                          <li class="active">
                              <span>合同详情</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      合同详情
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">

                      <div class="panel-tools">
                          <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityContract?.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityContract?.opportunity,this.opportunityContract?.opportunity.stage)?.executionSequence}">
                          </g:if>
                          <g:else>
                              <g:link class="btn btn-info btn-xs" action="edit" resource="${this.opportunityContract}"><i
                                      class="fa fa-edit"></i>编辑</g:link>
                          </g:else>


                      </div>

                      合同详情
                  </div>
                  <g:if test="${flash.message}">
                  <div class="message" role="status">${flash.message}</div>
                  </g:if>

                  <div class="panel-body form-horizontal">
                      <div class="form-group">
                          <label class="col-md-3 control-label">合同编号</label>
                          <div class="col-md-3">
                              <input disabled="disabled" value="${this.opportunityContract?.contract?.serialNumber}" class="form-control"/>
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="col-md-3 control-label">合同类型</label>
                          <div class="col-md-3">
                              <input disabled="disabled" value="${this.opportunityContract?.contract?.type?.name}" class="form-control"/>
                          </div>
                      </div>
                      <g:each in="${this.contractItems}">
                        <div class="form-group">
                            <label class="col-md-3 control-label">${it?.name}</label>
                            <g:if test="${it?.options?.size() > 0}">
                              <div class="col-md-3">
                                <g:select class="form-control" disabled="disabled" name="${it?.name}" value="${it?.value}" from="${it?.options}" noSelection="['': '-请选择-']" optionKey="value" optionValue="value"></g:select>

                              </div>
                            </g:if>
                            <g:else>
                              <div class="col-md-3">
                                  <input disabled="disabled" value="${it?.value}" class="form-control"/>
                              </div>
                            </g:else>

                        </div>
                      </g:each>
                  </div>
              </div>
          </div>
      </div>

        <!-- <a href="#show-opportunityContract" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-opportunityContract" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="opportunityContract" />
            <g:form resource="${this.opportunityContract}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.opportunityContract}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

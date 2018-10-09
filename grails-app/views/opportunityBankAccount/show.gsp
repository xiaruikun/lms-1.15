<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityBankAccount.label', default: 'OpportunityBankAccount')}" />
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
                              <g:link controller="opportunity" action="show"
                                      id="${this.opportunityBankAccount?.opportunity?.id}">订单详情</g:link>
                          </li>
                          <li class="active">
                              <span>详情</span>
                          </li>
                      </ol>
                  </div>

                  <h2 class="font-light m-b-xs">
                      账号详情
                  </h2>
              </div>
          </div>
      </div>

      <div class="content animate-panel">
          <div class="row">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityBankAccount?.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityBankAccount?.opportunity,this.opportunityBankAccount?.opportunity.stage)?.executionSequence}">
                          </g:if>
                          <g:else>
                              <g:link class="btn btn-info btn-xs" action="edit" resource="${this.opportunityBankAccount}"><i class="fa fa-edit"></i>编辑</g:link>
                          </g:else>

                      </div>
                      账号详情
                  </div>
                  <div class="panel-body">
                    <div class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-2 control-label">账户类别</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.type?.name}">
                            </div>
                            <label class="col-md-2 control-label">银行</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.bank?.name}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">支付通道</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.paymentChannel?.name}">
                            </div>
                            <label class="col-md-2 control-label">卡号</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.numberOfAccount}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">账户名</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.name}">
                            </div>
                            <label class="col-md-2 control-label">银行预留手机号</label>

                            <div class="col-md-3">
                            <span class="cont cellphoneFormat"> ${this.opportunityBankAccount?.bankAccount?.cellphone}</span>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">证件类型</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.certificateType?.name}">
                            </div>
                            <label class="col-md-2 control-label">证件号</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.numberOfCertificate}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">是否验卡成功</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.active}">
                            </div>
                            <label class="col-md-2 control-label">支行</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.bankBranch}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">支行地址</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.bankAddress}">
                            </div>
                            <label class="col-md-2 control-label">创建人</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.createdBy}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">修改人</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.modifiedBy}">
                            </div>
                            <label class="col-md-2 control-label">创建时间</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.createdDate}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">修改时间</label>

                            <div class="col-md-3">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.opportunityBankAccount?.bankAccount?.modifiedDate}">
                            </div>

                        </div>

                        <div class="hr-line-dashed"></div>

                    </div>
                  </div>
              </div>
          </div>
      </div>


        <!-- <a href="#show-opportunityBankAccount" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-opportunityBankAccount" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="opportunityBankAccount" />
            <g:form resource="${this.opportunityBankAccount}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.opportunityBankAccount}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div> -->
    </body>
</html>

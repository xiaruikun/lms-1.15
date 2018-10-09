<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityContact.label', default: 'OpportunityContact')}"/>
    <title>联系人信息：${this.opportunityContact?.contact?.fullName}</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li><g:link controller="opportunity" action="show"
                                id="${this.opportunityContact?.opportunity?.id}">订单详情</g:link></li>
                    <li class="active">联系人详情</li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                查看联系人信息
            </h2>
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
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                  <g:if test="${this.canEdit}">
                      <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityContact?.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityContact?.opportunity,this.opportunityContact?.opportunity.stage)?.executionSequence}">
                      </g:if>
                      <g:else>
                          <g:link class="btn btn-info btn-xs" action="edit"
                                  resource="${this.opportunityContact}"><i class="fa fa-edit"></i>修改联系人信息</g:link>
                      </g:else>

                  </g:if>
                </div>
                联系人信息
            </div>

            <div class="panel-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-2 control-label">订单编号：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.opportunity?.serialNumber}</span>
                        </div>
                        <label class="col-md-2 control-label">联系人类型：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.type?.name}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">联系人姓名：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.contact?.fullName}</span>
                        </div>
                        <label class="col-md-2 control-label">联系人手机号：</label>

                        <div class="col-md-3">
                            <span class="cont cellphoneFormat">${this.opportunityContact?.contact?.cellphone}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">联系人身份证号：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.contact?.idNumber}</span>
                        </div>
                        <label class="col-md-2 control-label">联系人婚姻状况：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.contact?.maritalStatus}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">关联人：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.connectedContact?.fullName}</span>
                        </div>
                        <label class="col-md-2 control-label">关联关系：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.connectedType?.name}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">职业：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.contact?.profession?.name}</span>
                        </div>
                        <label class="col-md-2 control-label">国籍：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.contact?.country?.name}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">身份证件类型：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.contact?.identityType?.name}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                  <g:if test="${this.canEdit}">
                      <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityContact?.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityContact?.opportunity,this.opportunityContact?.opportunity.stage)?.executionSequence}">
                      </g:if>
                      <g:else>
                          <g:link class="btn btn-info btn-xs" action="create2"
                                  resource="${this.opportunityContact}"><i class="fa fa-edit"></i>修改央行征信</g:link>
                      </g:else>

                    </g:if>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                央行征信
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-3 control-label">12个月内央行征信查询次数(贷款审批)：</label>

                    <div class="col-md-3">
                        <span class="cont">
                            ${this.opportunityContact?.contact?.queryTimes}
                        </span>
                    </div>
                    <label class="col-md-3 control-label">12个月内央行征信查询次数(其它查询)：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.opportunityContact?.contact?.queryTimesOther}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">近2年内贷款连续逾期期数：</label>

                    <div class="col-md-3">
                            <span class="cont">${this.opportunityContact?.contact?.continuousOverdue}</span>
                    </div>
                    <label class="col-md-3 control-label">近2年内贷款累计逾期期数：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.opportunityContact?.contact?.accumulativeOverdue}</span>
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">是否有贷款当前逾期：</label>

                    <div class="col-md-3">
                        <span class="cont">
                          <g:if test="${this.opportunityContact?.contact?.currentOverdue==true}">是</g:if>
                          <g:if test="${this.opportunityContact?.contact?.currentOverdue==false}">否</g:if>
                        </span>
                    </div>
                    <label class="col-md-3 control-label">贷款五级分类：</label>

                    <div class="col-md-3">
                        <span class="cont">
                            ${this.opportunityContact?.contact?.loanState}
                        </span>
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">非正常分类的贷款类型：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.opportunityContact?.contact?.loanType}</span>
                    </div>
                    <label class="col-md-3 control-label">非正常分类信贷授信额度：</label>

                    <div class="col-md-3 input-group">
                        <span class="cont">${this.opportunityContact?.contact?.loanAmount}<g:if test="${this.opportunityContact?.contact?.loanType}">
                            万元</g:if></span>

                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">非正常贷记卡账户状态：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.opportunityContact?.contact?.creditCardStatus}</span>
                    </div>
                    <label class="col-md-3 control-label">非正常贷记卡授信额度：</label>

                    <div class="col-md-3">
                       <span class="cont">${this.opportunityContact?.contact?.creditCardLimit}<g:if test="${this.opportunityContact?.contact?.creditCardLimit}">万元</g:if></span>
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-3 control-label">担保金额：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.opportunityContact?.contact?.guaranteedAmount}万元</span>

                    </div>
                    <label class="col-md-3 control-label">当前逾期金额</label>

                    <div class="col-md-3 input-group">
                        <span class="cont">${this.opportunityContact?.contact?.currentOverdueAmount}万元
                        </span>

                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-md-3 control-label">对外担保五级分类</label>

                    <div class="col-md-3 input-group">
                        <span class="cont">${this.opportunityContact?.contact?.guaranteeState}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                  <g:if test="${this.canEdit}">
                      <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityContact?.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunityContact?.opportunity,this.opportunityContact?.opportunity.stage)?.executionSequence}">
                      </g:if>
                      <g:else>
                          <g:link class="btn btn-info btn-xs" controller="contactJudgementRecord" action="create" params="[contact: this.opportunityContact?.contact.id, opportunityContact: this.opportunityContact?.id]"><i
                                  class="fa fa-plus"></i>新增</g:link>
                      </g:else>

                  </g:if>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                人法网被执记录
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>

                            <g:sortableColumn property="executionObject"
                                              title="执行标的（元）"/>
                            <g:sortableColumn property="executionStatus"
                                              title="执行状态"/>
                            <g:sortableColumn property="filingTime"
                                              title="立案时间"/>
                              <g:if test="${this.canEdit}">
                                <g:sortableColumn width="15%" class="text-center" colspan="2" property="filingTime"
                                title="操作"/>
                              </g:if>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityContact?.contact?.contactJudgementRecord}">
                            <tr>
                                <td>${it?.executionObject}</td>
                                <td>${it?.executionStatus}</td>
                                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.filingTime}"/></td>
                                <g:if test="${this.canEdit}">
                                <td class="text-center">
                                    <g:link class="btn btn-primary btn-xs btn-outline"
                                            controller="contactJudgementRecord" action="edit"
                                            id="${it?.id}" params="[opportunityContact: this.opportunityContact?.id]">
                                        <i class="fa fa-edit"></i>
                                        编辑
                                    </g:link>

                                </td>
                                  <td class="text-center">
                                    <g:form controller="contactJudgementRecord" action="delete"
                                      style="display: inline-block"
                                      id="${it.id}"
                                      method="DELETE">
                                      <input type="hidden" name="opportunityContact" value="${this.opportunityContact?.id}">
                                      <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                        <i class="fa fa-trash-o"></i>
                                        删除
                                      </button>
                                    </g:form>
                                </td>
                                </g:if>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

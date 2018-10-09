<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityContact.label', default: 'OpportunityContact')}"/>
    <title>编辑联系人央行征信信息</title>

</head>

<body>
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li><g:link controller="opportunity" action="show"
                                id="${this.opportunityContact?.opportunity?.id}">订单详情</g:link></li>
                    <li class="active">编辑联系人央行征信</li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                编辑联系人央行征信
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                </div>
                编辑联系人央行征信
            </div>

            <div class="panel-body">
                <g:if test="${flash.message}">
                    <div class="message alert alert-info" role="status">${flash.message}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">×</span></button>
                    </div>
                </g:if>
                <g:hasErrors bean="${this.opportunityContact}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.opportunityContact}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                    error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form resource="${this.opportunityContact}" method="PUT" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">12个月内央行征信查询次数(贷款审批)</label>

                        <div class="col-md-3">
                            <g:textField class="form-control figure" name="contact.queryTimes" value="${this.opportunityContact?.contact?.queryTimes}"></g:textField>
                        </div>
                        <label class="col-md-3 control-label">12个月内央行征信查询次数(其它查询)</label>

                        <div class="col-md-3">
                            <g:textField class="form-control figure" name="contact.queryTimesOther" value="${this.opportunityContact?.contact?.queryTimesOther}"></g:textField>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">近2年内贷款连续逾期期数</label>

                        <div class="col-md-3">

                            <g:textField class="form-control figure" name="contact.continuousOverdue" value="${this.opportunityContact?.contact?.continuousOverdue}"></g:textField>
                        </div>
                        <label class="col-md-3 control-label">近2年内贷款累计逾期期数</label>

                        <div class="col-md-3">
                            <g:textField class="form-control figure" name="contact.accumulativeOverdue" value="${this.opportunityContact?.contact?.accumulativeOverdue}"></g:textField>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">是否有贷款当前逾期</label>

                        <div class="col-md-3">
                            <span class="cont">
                                <g:checkBox class="i-checks" name="contact.currentOverdue" value="${this.opportunityContact?.contact?.currentOverdue}"/>
                            </span>
                        </div>
                        <label class="col-md-3 control-label">贷款五级分类</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="contact.loanState"  noSelection="['': '-请选择-']" value="${this.opportunityContact?.contact?.loanState}"
                                      from="${["正常", "关注", "次级", "可疑", "损失"]}"/>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">非正常分类的贷款类型</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="contact.loanType" noSelection="['': '-请选择-']" value="${this.opportunityContact?.contact?.loanType}"
                                      from="${["房产抵押", "信用", "其它"]}"/>
                        </div>
                        <label class="col-md-3 control-label">非正常分类信贷授信额度</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" type="text" value="${this.opportunityContact?.contact?.loanAmount}"
                                         name="contact.loanAmount"/>
                            <span class="input-group-addon">万元</span>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">非正常贷记卡账户状态</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="contact.creditCardStatus" noSelection="['': '-请选择-']"
                                      value="${this.opportunityContact?.contact?.creditCardStatus}"
                                      from="${["正常", "止付", "冻结", "呆账", "黑户"]}"/>
                        </div>
                        <label class="col-md-3 control-label">非正常贷记卡授信额度</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" type="text" value="${this.opportunityContact?.contact?.creditCardLimit}"
                                         name="contact.creditCardLimit"/>
                            <span class="input-group-addon">万元</span>
                        </div>

                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">担保金额</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" type="text" value="${this.opportunityContact?.contact?.guaranteedAmount}"
                                         name="contact.guaranteedAmount"/>
                            <span class="input-group-addon">万元</span>
                        </div>
                        <label class="col-md-3 control-label">当前逾期金额</label>

                        <div class="col-md-3 input-group">
                            <g:textField class="form-control" type="text" value="${this.opportunityContact?.contact?.currentOverdueAmount}"
                                         name="contact.currentOverdueAmount"/>
                            <span class="input-group-addon">万元</span>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">对外担保五级分类</label>

                        <div class="col-md-3 input-group">
                            <g:select class="form-control" name="contact.guaranteeState" noSelection="['': '-请选择-']"
                                      value="${this.opportunityContact?.contact?.guaranteeState}"
                                      from="${["正常", "关注", "次级", "可疑", "损失"]}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-lg-offset-6">
                            <button class="btn btn-info">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
<script>
    $(function(){
        $("input.figure").TouchSpin({
            verticalbuttons: true,
            min: 0,
        });
    })
</script>
</body>
</html>

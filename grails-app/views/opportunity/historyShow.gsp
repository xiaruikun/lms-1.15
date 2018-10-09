<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunityHistoryHistory.label', default: 'opportunityHistoryHistory')}"/>
    <title>操作记录详情</title>
    <style>
    .select2-container .select2-choice {
        height: 30px;
    !important;
        line-height: 30px;
    }

    .select2-chosen, .select2-choice > span:first-child, .select2-container .select2-choices .select2-search-field input {
        padding: 0 12px !important;
    }
    </style>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body clearfix">
            <h2 class="font-light pull-left">
                合同编号：${this.opportunityHistory?.externalId}
                <span>/</span>
                订单号: ${this.opportunityHistory?.serialNumber}
            </h2>

        </div>
    </div>
</div>

<div class="content active animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>

    </div>

    <div class="row" id="first">
        <div class="hpanel hred contact-panel">
            <div class="panel-heading">
                <div class="panel-tools">

                </div>
                订单基本信息
            </div>

            <div class="panel-body">
                <div class="text-muted font-bold m-b-xs ol-md-6">
                    <div class="col-md-12" style="padding-right: 0">
                        <h4>
                            ${this.opportunityHistory?.serialNumber}
                            (<g:if test="${this.opportunityHistory?.type}">${this.opportunityHistory?.type?.name}</g:if>
                            <g:else>${com.next.OpportunityType.findByCode('10')?.name}</g:else>)
                            <g:if test="${this.opportunityHistory?.isTest}">
                                <span class="label label-danger pull-right"><i class="fa fa-star"></i> 测试 <i class="fa fa-star"></i></span>
                            </g:if>
                        </h4>
                    </div>

                    <div class="col-md-2">
                        <strong>
                            <span class="glyphicon glyphicon-user"
                                  aria-hidden="true"></span> ${this.opportunityHistory?.fullName}
                        </strong>
                    </div>
                    <g:if test="${this.opportunityHistory?.protectionEndTime > new Date()}">
                        <div class="col-md-1">
                            <span class="fa fa-chain"></span>${this.opportunityHistory?.protectionEndTime - this.opportunityHistory?.protectionStartTime}天
                        </div>
                    </g:if>

                    <div class="col-md-2">
                        <strong>${this.opportunityHistory?.stage?.description}</strong>
                    </div>

                    <div class="col-md-2">
                        <strong>抵押凭证类型：${this.opportunityHistory?.mortgageCertificateType?.name}</strong>
                    </div>
                    <g:if test="${this.opportunityHistory?.status == 'Failed'}"><span
                            class="label label-danger pull-right">订单结果：失败</span></g:if>
                    <g:elseif test="${this.opportunityHistory?.status == 'Completed'}"><span
                            class="label label-success pull-right">订单结果：成功</span></g:elseif>
                    <g:else><span class="label label-info pull-right">订单结果：进行中</span></g:else>
                </div>
            </div>

            <div class="panel-footer contact-footer">
                <div class="row">
                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>拟贷款期限</span>
                            <strong>${this.opportunityHistory?.loanDuration}月</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>实际贷款期限</span>
                            <strong>${this.opportunityHistory?.actualLoanDuration}月</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>评房（可贷）</span>
                            <strong><g:formatNumber number="${this.opportunityHistory?.loanAmount}" minFractionDigits="2"
                                                    maxFractionDigits="2"/>万元</strong>
                        </div>
                    </div>

                    <div class="col-md-2 border-right">
                        <div class="contact-stat">
                            <span>初审</span>
                            <strong>%{--${this.opportunityHistory?.maximumAmountOfCredit}万元；--}%${this.opportunityHistory?.lender?.level?.description}；${this.opportunityHistory?.dealRate}</strong>
                        </div>
                    </div>

                    <div class="col-md-4 border-right">
                        <div class="contact-stat">
                            <span>审核可贷金额</span>
                            <strong>${this.opportunityHistory?.actualAmountOfCredit}万元；${this.opportunityHistory?.interestPaymentMethod?.name}；
                            <g:if test="${this.opportunityHistory?.interestPaymentMethod?.name == '上扣息'}">
                                上扣息月份数:${this.opportunityHistory?.monthOfAdvancePaymentOfInterest}个月；
                            </g:if>
                            综合息费<g:formatNumber
                                    number="${this.opportunityHistory?.monthlyInterest + this.opportunityHistory?.serviceCharge}"
                                    
                                    maxFractionDigits="9"/>%
                            （月息
                            <g:formatNumber
                                    number="${this.opportunityHistory?.monthlyInterest}"
                                    
                                    maxFractionDigits="9"/>%；
                            借款服务费${this.opportunityHistory?.serviceCharge}%）；
                                渠道服务费${this.opportunityHistory?.commissionRate}%；${this.opportunityHistory?.commissionPaymentMethod}
                            </strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>产品类型:</span>
                            <strong>${this.opportunityHistory?.product?.name}</strong>
                        </div>
                    </div>


                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>一抵金额</span>
                            <strong>${this.opportunityHistory?.firstMortgageAmount}万元</strong>
                        </div>
                    </div>

                    <div class="col-md-1 border-right">
                        <div class="contact-stat">
                            <span>二抵金额</span>
                            <strong>${this.opportunityHistory?.secondMortgageAmount}万元</strong>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hblue">
          <div class="panel-heading">
              详情
          </div>
          <div class="panel-body no-padding">
            <div class="form form-horizontal">
              <div class="form-group">
                  <label class="col-md-3 control-label">报单人</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.contact?.fullName}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">支持经理</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.user}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">借款人身份证号</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.idNumber}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">借款人手机号</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.cellphone}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">借款人婚姻状况</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.maritalStatus}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">申请金额</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.requestedAmount}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">佣金</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.commission}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">房产单价</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.unitPrice}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">未成交归类</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.causeOfFailure}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">失败原因</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.descriptionOfFailure}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">备注</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.memo}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">创建时间</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.createdDate}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">修改时间</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.modifiedDate}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">审批类型</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.subtype?.name}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">区域</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.territory?.name}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">是否公证</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.notarizingStatus}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">是否抵押</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.mortgagingStatus}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">意向金</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.advancePayment}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">报单方式</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.loanApplicationProcessType?.name}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">实际放款日期</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.actualLendingDate}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">预计放款日期</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.estimatedLendingDate}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">修改人</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.modifiedBy}
                      </span>
                  </div>

              </div>
              <div class="form-group">
                  <label class="col-md-3 control-label">机构</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.account?.name}
                      </span>
                  </div>

                  <label class="col-md-3 control-label">上一步操作</label>

                  <div class="col-md-2">
                      <span class="cont">
                      ${this.opportunityHistory?.lastAction?.name}
                      </span>
                  </div>

              </div>
            </div>
          </div>
        </div>
    </div>

</div>

<script>
    $(function () {
        $("body").addClass("fixed-small-header");

        $(".text-comment").each(function () {
            var text = $(this).text();
            if (text) {
                $(this).parent().parent().removeClass("collapsed");
                $(this).parent().prev(".panel-heading").removeClass("hbuilt");
            } else {
                $(this).parent().parent().addClass("collapsed");
                $(this).parent().prev(".panel-heading").addClass("hbuilt");
            }
        })
        $(".table-responsive").parent(".panel-body").addClass("active");

    });
</script>
</body>

</html>

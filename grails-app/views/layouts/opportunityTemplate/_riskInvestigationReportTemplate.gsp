<div class="row">
    <div class="hpanel collapsed">
        <div class="panel-heading hbuilt">
            <div class="panel-tools">
                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            风险调查报告
        </div>

        <div class="panel-body p-xs" style="border-top: 2px solid #ffb606">
            <div class="hpanel">

                <div class="panel-heading">
                    抵押物情况
                </div>

                <div class="panel-body p-xs">
                    <g:each in="${this.collaterals}">
                        <div class="panel-heading">
                            第 <span class="order1"></span> 套抵押物描述
                        </div>

                        <div class="panel-body form-horizontal borderActive">
                            地址：${it?.address}，小区名称：${it?.projectName}，建筑面积：${it?.area}（m<sup>2</sup>），
                        房龄：
                        <g:if test="${it?.buildTime}">
                            ${new Date().format("yyyy").toInteger().minus(it?.buildTime.format("yyyy").toInteger())}
                        </g:if>
                        <g:else>
                            ${it?.buildTime}
                        </g:else>,房屋性质：${it?.houseType}，规划用途：${it?.projectType?.name}，房屋使用状态：${it?.houseUsageStats}，
                        审批时抵押状态：${it?.mortgageStatus}，<br>
                            其他补充：${it?.additionalComment}

                        </div>
                    </g:each>
                    <g:each in="${this.collaterals}">
                        <div class="panel-heading">
                            第 <span class="order2"></span> 套抵押物权属
                        </div>

                        <div class="panel-body form-horizontal borderActive">
                            抵押物权属：${it?.mortgageProperty}
                        </div>
                    </g:each>
                </div>
            </div>

            <div class="hpanel">

                <div class="panel-heading">
                    借款人资质小结
                </div>

                <div class="panel-body p-xs">
                    <g:each in="${opportunityContacts}">
                        <div class="panel-heading">
                            ${it?.type?.name}
                        </div>

                        <div class="panel-body borderActive">
                            联系人：${it?.contact?.fullName}，
                            年龄：<g:if test="${it?.contact?.idNumber}">
                                        ${new Date().format("yyyy").toInteger().minus(it?.contact?.idNumber[6..9].toInteger())}
                                    </g:if>
                                    <g:else>
                                        ${it?.contact?.idNumber}
                                    </g:else>
                                    岁，
                            职业：${it?.contact?.profession?.name}，
                            年收入：${it?.contact?.income}万元，联系人婚姻状况：${it?.contact?.maritalStatus}，是否为抵押人：${it?.contact?.propertyOwner}，
                            关联关系：${it?.connectedType?.name}，其他说明：${it?.contact?.personalProperty}
                        </div>
                    </g:each>

                </div>
            </div>

            <div class="hpanel">
                <div class="panel-heading">
                    征信小结
                </div>

                <div class="panel-body p-xs">
                    <g:each in="${opportunityContacts}">
                        <div class="panel-heading">
                            ${it?.type?.name} ：${it?.contact?.fullName}
                        </div>

                        <div class="panel-body borderActive">
                            近24个月内逾期次数：${it?.contact?.overdueRecent}，当前总贷款余额：${it?.contact?.totalLoanBalance}万元，当前逾期金额：${it?.contact?.currentOverdueAmount}万元，
                            对外担保情况：${it?.contact?.guaranteeStatus}，
                            <g:if test="${it?.contact?.guaranteeStatus =="有"}">
                                对外担保类型：${it?.contact?.guaranteeType}，
                                对外担保余额${it?.contact?.guaranteeBalance}万元，被担保人与借款人关系：
                                <g:if test="${it?.contact?.relationOfGuarantor}">
                                    ${it?.contact?.relationOfGuarantor},
                                </g:if>
                                <g:else>
                                    无,
                                </g:else>
                                对外担保贷款评级：${it?.contact?.guaranteeState}，
                            </g:if>    
                           
                        </div>

                    </g:each>

                </div>
            </div>

            <div class="hpanel">
                <div class="panel-body">
                    借款征信评级：${this.opportunity?.cBCState}
                </div>
            </div>

            <div class="hpanel">
                <div class="panel-heading">
                    大数据小结
                </div>

                <div class="panel-body">
                    <p>
                        是否命中大数据拒单项：${this.opportunity?.rejected}，
                        <g:if test="${this.opportunity?.descriptionOfRejection}">
                            命中大数据说明：${this.opportunity?.descriptionOfRejection}
                        </g:if>
                    </p>

                    <p>
                        是否涉诉，被执行：${this.opportunity?.execution}，
                        <g:if test="${this.opportunity?.descriptionOfExecution}">
                            涉诉，被执情况说明：${this.opportunity?.descriptionOfExecution}
                        </g:if>
                    </p>

                </div>
            </div>

            <div class="hpanel">

                <div class="panel-heading">
                    借款用途
                </div>

                <div class="panel-body">
                    借款用途：${this.opportunity?.loanUsage},
                    <g:if test="${this.opportunity?.otherLoanUsage}">借款用途说明：${this.opportunity?.otherLoanUsage}</g:if>
                </div>
            </div>

            <div class="hpanel">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    还款来源

                </div>

                <div class="panel-body">
                    还款来源：${this.opportunity?.repaymentSource}，
                    <g:if test="${this.opportunity?.otherRepaymentSource}">还款来源说明：${this.opportunity?.otherRepaymentSource}</g:if>

                </div>
            </div>

            <div class="hpanel">

                <div class="panel-heading">
                    风险结论
                </div>

                <div class="panel-body comment-area">
                    优势：${this.opportunity?.advantages}<br>

                    劣势：${this.opportunity?.disadvantages}<br>
                    <g:if test="${this.opportunity?.additionalComment}">
                        补充说明：${this.opportunity?.additionalComment}<br>
                    </g:if>
                    <h5>授信建议</h5>

                    授信金额：${this.opportunity?.actualAmountOfCredit}万元<br>

                    抵押率：
                    <g:each in="${this.collaterals}">
                        <span class="m-r font-bold">
                            <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                            minFractionDigits="2"/>%
                        </span>
                    </g:each> <br>
                    实际贷款期限：${this.opportunity?.actualLoanDuration}月<br>
                    签约主体：
                    <g:each in="${this.opportunityContacts}">
                        <span class="m-r">
                            ${it?.contact?.fullName}
                        </span>
                    </g:each><br>
                    月利息：${this.opportunity?.monthlyInterest}%，月服务费率：${this.opportunity?.serviceCharge}%，是否要求面审：${this.opportunity?.interviewRequired}
                </div>
            </div>

            <div class="hpanel">

                <div class="panel-heading">
                    放款前要求
                </div>

                <div class="panel-body comment-area">
                    <g:if test="${this.opportunity?.notarizationMatters}">
                        公证事项：${this.opportunity?.notarizationMatters}<br>
                    </g:if>
                    抵押凭证：
                    <g:if test="${this.opportunity?.mortgageCertification == 'true'}">
                        抵押登记受理单 <br>
                    </g:if>
                    <g:if test="${this.opportunity?.mortgageCertification == 'false'}">
                        他项权利证书 <br>
                    </g:if>
                    <g:if test="${this.opportunity?.signedDocuments}">
                        签署文件：${this.opportunity?.signedDocuments} <br>
                    </g:if>
                    <g:if test="${this.opportunity?.mortgageMaterials}">
                        收押材料：${this.opportunity?.mortgageMaterials} <br>
                    </g:if>
                    <g:if test="${this.opportunity?.otherIssues}">
                        其他：${this.opportunity?.otherIssues} <br>
                    </g:if>
                    <g:if test="${this.opportunity?.prePaymentAdditionalComment}">
                        补充说明：${this.opportunity?.prePaymentAdditionalComment}
                    </g:if>
                </div>
            </div>
        </div>
    </div>

</div>
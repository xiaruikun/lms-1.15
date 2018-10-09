<div class="row">
    <div class="hpanel hgreen">
        <div class="panel-heading">
            <div class="panel-tools">
                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence >= com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                    <g:link class="btn btn-info btn-xs" controller="opportunityBankAccount" action="create"
                            params="[opportunity: this.opportunity.id]"><i class="fa fa-plus"></i>新增</g:link>
                </g:if>

                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            账户信息
        </div>

        <div class="panel-body no-padding">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover text-center">
                    <thead>
                    <tr>
                        <g:sortableColumn property="type" title="账户类别"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.bank" title="银行"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.paymentChannel" title="支付通道"></g:sortableColumn>

                        <g:sortableColumn property="bankAccount.numberOfAccount" title="卡号"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.name" title="账户名"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.cellphone" title="银行预留手机号"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.certificateType" title="证件类型"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.numberOfCertificate" title="证件号"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.active" title="是否验卡成功"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.bankBranch" title="支行"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.bankAddress" title="支行地址"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.createdBy" title="创建人"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.modifiedBy" title="修改人"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.createdDate" title="创建时间"></g:sortableColumn>
                        <g:sortableColumn property="bankAccount.modifiedDate" title="修改时间"></g:sortableColumn>
                        <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                        </g:if>
                        <g:else>
                            <g:sortableColumn property="bankBranch" title="操作"></g:sortableColumn>
                        </g:else>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${this.bankAccounts}">
                        <tr>
                            <td>
                                <g:link controller="opportunityBankAccount" action="show"
                                        id="${it?.id}">${it?.type?.name}</g:link>
                            </td>
                            <td>
                                ${it?.bankAccount?.bank?.name}
                            </td>
                            <td>
                                ${it?.bankAccount?.paymentChannel?.name}
                            </td>

                            <td>
                                ${it?.bankAccount?.numberOfAccount}
                            </td>
                            <td>
                                ${it?.bankAccount?.name}
                            </td>
                            <td class="cellphoneFormat">
                                ${it?.bankAccount?.cellphone}
                            </td>
                            <td>
                                ${it?.bankAccount?.certificateType?.name}
                            </td>
                            <td>
                                ${it?.bankAccount?.numberOfCertificate}
                            </td>
                            <td>
                                <g:if test="${it?.bankAccount?.active == true}">是</g:if>
                                <g:if test="${it?.bankAccount?.active == false}">否</g:if>
                            </td>
                            <td>
                                ${it?.bankAccount?.bankBranch}
                            </td>
                            <td>
                                ${it?.bankAccount?.bankAddress}
                            </td>
                            <td>
                                ${it?.bankAccount?.createdBy}
                            </td>
                            <td>
                                ${it?.bankAccount?.modifiedBy}
                            </td>
                            <td>
                                <g:formatDate date="${it?.bankAccount?.createdDate}"
                                              format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
                            </td>
                            <td>
                                <g:formatDate date="${it?.bankAccount?.modifiedDate}"
                                              format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
                            </td>
                            <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,it.opportunity.stage)?.executionSequence}">
                            </g:if>
                            <g:else>
                                <td>
                                <g:form resource="${it}" method="DELETE">
                                    <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                            class="fa fa-trash-o"></i> 删除</button>
                                </g:form>
                            </td>
                            </g:else>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
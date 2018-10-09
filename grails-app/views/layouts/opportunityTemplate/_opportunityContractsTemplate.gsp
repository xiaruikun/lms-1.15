<div class="row">
    <div class="hpanel hgreen">
        <div class="panel-heading">
            <div class="panel-tools">

                <sec:ifNotGranted roles="ROLE_COMPLIANCE_MANAGER">
                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence >= com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                    <g:link class="btn btn-info btn-xs" controller="opportunityContract" action="create"
                            params="[opportunity: this.opportunity.id]"><i class="fa fa-plus"></i>新增</g:link>
                </g:if>
                </sec:ifNotGranted>
                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            合同信息
        </div>

        <div class="panel-body no-padding">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover text-center">
                    <thead>
                    <tr>
                        <g:sortableColumn property="contract.serialNumber" title="合同编号"></g:sortableColumn>
                        <g:sortableColumn property="contract.type" title="合同类别"></g:sortableColumn>
                        <g:sortableColumn property="contract.createdDate" title="创建时间"></g:sortableColumn>
                        <g:sortableColumn property="contract.modifiedDate" title="修改时间"></g:sortableColumn>
<sec:ifNotGranted roles="ROLE_COMPLIANCE_MANAGER">
                        <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                        </g:if>
                        <g:else>
                            <g:sortableColumn property="operation" title="操作"></g:sortableColumn>
                        </g:else>
</sec:ifNotGranted>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${this.opportunity?.contracts}">
                        <tr>
                            <td>
                                %{--<g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.endTime != null}">
                                    ${it?.contract?.serialNumber}
                                </g:if>
                                <g:else>
                                    <g:link controller="opportunityContract" action="show"
                                             id="${it?.id}">${it?.contract?.serialNumber}</g:link>
                                </g:else>--}%
                                <g:link controller="opportunityContract" action="show"
                                        id="${it?.id}">${it?.contract?.serialNumber}</g:link>
                            </td>
                            <td>
                                ${it?.contract?.type?.name}
                            </td>
                            <td>
                                <g:formatDate date="${it?.createdDate}"
                                              format="yyyy-MM-dd HH:mm:ss"></g:formatDate>

                            </td>
                            <td>
                                <g:formatDate date="${it?.modifiedDate}"
                                              format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
                            </td>
                        <sec:ifNotGranted roles="ROLE_COMPLIANCE_MANAGER">
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
                        </sec:ifNotGranted>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
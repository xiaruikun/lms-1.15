<div class="pull-right text-right">
    <button class="btn btn-sm btn-info" data-toggle="modal" data-target="#rejectReason2">
        <i class="fa fa-arrow-up"></i> 上一步</button>
    <g:if test="${this.currentFlow?.nextStages?.size() > 0}">
        <g:each in="${this.currentFlow?.nextStages}">
            <div class="footBtn">
                <select name="nextOperators" class="selectRole">
                    <g:each in="${this.opportunityFlows}" var="flow">
                        <g:if test="${flow == it?.nextStage}">
                            <g:each var="role" in="${this.opportunityRoles}">
                                <g:if test="${role?.stage == flow?.stage && role.teamRole?.name == 'Approval'}">
                                    <option value="${role?.id}">${role?.user}</option>
                                </g:if>
                            </g:each>
                        </g:if>
                    </g:each>
                </select>
                <button class="ladda-button btn btn-sm btn-info nextStepBtn2" data-style="zoom-out"
                        value="${it?.nextStage?.id}">
                    <span class="ladda-label">
                        <i class="fa fa-arrow-down"></i>${it?.nextStage?.stage?.description}
                    </span>
                    <span class="ladda-spinner"></span>
                </button>
            </div>

        </g:each>
    </g:if>
    <g:else>
        <div class="footBtn">
            <select name="nextOperators" class="selectRole pull-left">
                <g:each in="${this.opportunityFlows}" var="flow">
                    <g:if test="${flow == com.next.OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and executionSequence > ? order by executionSequence asc", [this.opportunity?.id, this.currentFlow?.executionSequence])}">
                        <g:each var="role" in="${this.opportunityRoles}">
                            <g:if test="${role?.stage == flow?.stage && role.teamRole?.name == 'Approval'}">
                                <option value="${role?.id}">${role?.user}</option>
                            </g:if>
                        </g:each>
                    </g:if>
                </g:each>
            </select>
            <button class="ladda-button btn btn-sm btn-info nextStepBtn2" data-style="zoom-out">
                <span class="ladda-label"><i class="fa fa-arrow-down"></i>下一步</span><span
                    class="ladda-spinner"></span>
            </button>
        </div>

    </g:else>
    <!-- <g:link class="btn btn-sm btn-success" controller="opportunity" id="${this.opportunity?.id}"
            action="complete"><i class="fa fa-check"></i> 完成</g:link> -->
    <g:if test="${this.canSpecialApproval}">
        <g:link class="btn btn-sm btn-success" controller="opportunity" id="${this.opportunity?.id}"
                action="specialApprove"><i class="fa fa-check"></i> 特批</g:link>
    </g:if>

    <button class="btn  btn-sm btn-danger" data-toggle="modal" data-target="#myModal">
        <i class="fa fa-exclamation-circle"></i> 失败</button>
</div>
package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Transactional
class OpportunityWorkflowRoleService
{

    def initRole(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        def territoryAccount
        def territory
        OpportunityRole opportunityRole
        def opportunityWorkflow
        def opportunityWorkflowRoles
        def territoryOpportunityWorkflows

        //区域
        if (workflow)
        {
            opportunityWorkflow = workflow
        }
        else
        {
            territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
            territory = territoryAccount?.territory
            territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAll("from TerritoryOpportunityWorkflow where territory.id = ${territory?.id}")
            territoryOpportunityWorkflows?.each {
                if (opportunity?.type == it?.workflow?.opportunityType && it?.workflow?.active)
                {
                    opportunityWorkflow = it?.workflow
                }
            }
        }

        opportunityWorkflowRoles = OpportunityWorkflowRole.findAll("from OpportunityWorkflowRole where workflow.id = ${opportunityWorkflow?.id}")

        for (
            role in
                opportunityWorkflowRoles)
        {
            opportunityRole = OpportunityRole.find("from OpportunityRole where opportunity.id = ? and user.id" + " = ? and stage.id = ?", [opportunity?.id, role?.user?.id,
                role?.stage?.id])
            if (!opportunityRole)
            {
                opportunityRole = new OpportunityRole()
                opportunityRole.opportunity = opportunity
                opportunityRole.user = role?.user
                opportunityRole.teamRole = role?.teamRole
                opportunityRole.stage = role?.stage
                opportunityRole.opportunityLayout = role?.opportunityLayout
                if (opportunityRole.validate())
                {
                    opportunityRole.save flush: true
                }
                else
                {
                    log.info opportunityRole.errors
                }
            }
        }
    }
}

package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Transactional
class OpportunityRoleService
{

    def initRole(Opportunity opportunity)
    {
        Boolean inheritRole = true
        OpportunityRole opportunityRole
        def territoryRoles
        def territoryAccount
        def territory

        def stage = OpportunityStage.find("from OpportunityStage where code = ?", ["03"])
        def teamRole = TeamRole.find("from TeamRole where name = ?", ["Approval"])
        opportunityRole = OpportunityRole.find("from OpportunityRole where opportunity.id = ? and user.id = ? and " + "stage.id = ?", [opportunity?.id, opportunity?.user?.id, stage?.id])
        if (!opportunityRole)
        {
            opportunityRole = new OpportunityRole()
            opportunityRole.opportunity = opportunity
            opportunityRole.user = opportunity?.user
            opportunityRole.stage = stage
            opportunityRole.teamRole = teamRole
            if (opportunityRole.validate())
            {
                opportunityRole.save flush: true
            }
            else
            {
                println opportunityRole.errors
            }
        }

        territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
        territory = territoryAccount?.territory
        while (territory)
        {
            if (inheritRole)
            {
                territoryRoles = TerritoryRole.findAll("from TerritoryRole where territory.id = ?", [territory?.id])
                for (
                    role in
                        territoryRoles)
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
                        if (opportunityRole.validate())
                        {
                            opportunityRole.save flush: true
                        }
                        else
                        {
                            log.inifo opportunityRole.errors
                        }
                    }
                }

                inheritRole = territory?.inheritRole
                territory = territory?.parent
            }
            else
            {
                break
            }
        }
    }
}

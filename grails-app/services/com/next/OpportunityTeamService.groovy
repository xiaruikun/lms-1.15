package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Transactional
@CompileStatic
@TypeChecked
class OpportunityTeamService
{
    static scope = "singleton"

    Boolean initTeam(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        Boolean inheritTeam = true
        OpportunityTeam opportunityTeam
        def territoryTeams
        def territoryAccount
        def territory

        //区域
        if (workflow)
        {
            territory = TerritoryOpportunityWorkflow.find("from TerritoryOpportunityWorkflow where workflow.id = ?", [workflow?.id])?.territory
        }
        else
        {
            territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
            territory = territoryAccount?.territory
        }
        //添加owner到team
        opportunityTeam = OpportunityTeam.find("from OpportunityTeam where user.id = ? and opportunity.id = ?",
                                               [opportunity?.user?.id, opportunity?.id])
        if (!opportunityTeam)
        {
            opportunityTeam = new OpportunityTeam()
            opportunityTeam.user = opportunity?.user
            opportunityTeam.opportunity = opportunity
            if (opportunityTeam.validate())
            {
                opportunityTeam.save flush: true
            }
            else
            {
                opportunityTeam.errors.each {
                    log.info "${it}"
                }
            }
        }

        //复制territoryTeam到team
        while (territory)
        {
            //依次继承所有父级区域team
            if (inheritTeam)
            {
                territoryTeams = TerritoryTeam.findAll("from TerritoryTeam where territory.id = ?", [territory?.id])
                for (
                    team in
                        territoryTeams)
                {
                    opportunityTeam = OpportunityTeam.find("from OpportunityTeam where opportunity.id = ? and user" + ".id=?", [opportunity?.id, team?.user?.id])
                    if (!opportunityTeam)
                    {
                        opportunityTeam = new OpportunityTeam()
                        opportunityTeam.user = team?.user
                        opportunityTeam.opportunityLayout = team?.opportunityLayout
                        opportunityTeam.opportunity = opportunity
                        if (opportunityTeam.validate())
                        {
                            opportunityTeam.save flush: true
                        }
                        else
                        {
                            opportunityTeam.errors.each {
                                log.info "${it}"
                            }
                        }
                    }
                }

                inheritTeam = territory?.inheritTeam
                territory = territory?.parent
            }
            else
            {
                break
            }
        }
    }
}

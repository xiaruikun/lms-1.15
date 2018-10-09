package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Transactional
class OpportunityFlowService
{

    def initFlow(Opportunity opportunity)
    {
        def territoryAccount
        def territory
        def territoryFlows
        OpportunityFlow opportunityFlow
        def opportunityFlowCondition
        def opportunityFlowNextStage
        Boolean inheritFlow = true

        territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
        territory = territoryAccount?.territory

        while (territory)
        {
            inheritFlow = territory?.inheritFlow
            if (!inheritFlow)
            {
                territoryFlows = TerritoryFlow.findAll("from TerritoryFlow where territory.id = ?", [territory?.id])
                for (
                    flow in
                        territoryFlows)
                {
                    opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage" + ".id = ?", [opportunity?.id, flow?.stage?.id])
                    if (!opportunityFlow)
                    {
                        opportunityFlow = new OpportunityFlow()
                        opportunityFlow.opportunity = opportunity
                        opportunityFlow.stage = flow?.stage
                        opportunityFlow.canReject = flow?.canReject
                        opportunityFlow.executionSequence = flow?.executionSequence
                        opportunityFlow.opportunityLayout = flow?.opportunityLayout
                        if (opportunityFlow.validate())
                        {
                            opportunityFlow.save flush: true
                            flow?.conditions?.each {
                                opportunityFlowCondition = OpportunityFlowCondition.find("from " + "OpportunityFlowCondition " + "where flow.id = ? and " + "condition = ?", [opportunityFlow?.id, it?.condition])
                                if (!opportunityFlowCondition)
                                {
                                    opportunityFlowCondition = new OpportunityFlowCondition()
                                    opportunityFlowCondition.flow = opportunityFlow
                                    opportunityFlowCondition.condition = it?.condition
                                    opportunityFlowCondition.message = it?.message
                                    if (opportunityFlowCondition.validate())
                                    {
                                        opportunityFlowCondition.save flush: true
                                    }
                                    else
                                    {
                                        log.info opportunityFlowCondition.errors
                                    }
                                }
                            }

                        }
                        else
                        {
                            log.info opportunityFlow.errors
                        }
                    }
                }
                break
            }
            else
            {
                territory = territory?.parent
            }
        }

        def currentStage
        def nextStage
        territoryFlows?.each {
            if (it?.nextStages?.size() > 0)
            {
                it?.nextStages?.each {
                    currentStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.flow?.stage?.id])
                    nextStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.nextStage?.stage?.id])
                    opportunityFlowNextStage = OpportunityFlowNextStage.find("from " + "OpportunityFlowNextStage " + "where flow.id = ? and " + "nextStage.id = ?", [currentStage?.id, nextStage?.id])
                    if (!opportunityFlowNextStage)
                    {
                        opportunityFlowNextStage = new OpportunityFlowNextStage()
                        opportunityFlowNextStage.flow = currentStage
                        opportunityFlowNextStage.nextStage = nextStage
                        if (opportunityFlowNextStage.validate())
                        {
                            opportunityFlowNextStage.save flush: true
                        }
                        else
                        {
                            log.info opportunityFlowNextStage.errors
                        }
                    }
                }
            }
        }

    }
}

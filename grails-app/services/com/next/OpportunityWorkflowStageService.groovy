package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Transactional
class OpportunityWorkflowStageService
{

    def initFlow(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        def territoryAccount
        def territory
        def territoryOpportunityWorkflows
        def opportunityWorkflow
        def opportunityWorkflowStages
        OpportunityFlow opportunityFlow
        def opportunityFlowCondition
        def opportunityFlowNextStage
        // def currentStage
        def nextStage
        def opportunityEvent
        def opportunityWorkflowStageAttachmentTypes
        def opportunityFlowAttachmentType


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
        opportunityWorkflowStages = OpportunityWorkflowStage.findAll("from OpportunityWorkflowStage where workflow.id = ${opportunityWorkflow?.id}")

        opportunityWorkflowStages.each {
            opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage" + ".id = ?", [opportunity?.id, it?.stage?.id])
            if (!opportunityFlow)
            {
                opportunityFlow = new OpportunityFlow()
                opportunityFlow.opportunity = opportunity
                opportunityFlow.stage = it?.stage
                opportunityFlow.canReject = it?.canReject
                opportunityFlow.executionSequence = it?.executionSequence
                opportunityFlow.opportunityLayout = it?.opportunityLayout
                opportunityFlow.document = it?.document
                if (opportunityFlow.validate())
                {
                    opportunityFlow.save flush: true
                }
                else
                {
                    log.info opportunityFlow.errors
                }
            }
        }

        opportunityWorkflowStages?.each {
            opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage" + ".id = ?", [opportunity?.id, it?.stage?.id])
            //conditions
            if (it?.conditions?.size() > 0)
            {
                it?.conditions?.each {
                    nextStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.nextStage?.stage?.id])
                    opportunityFlowCondition = OpportunityFlowCondition.find("from " + "OpportunityFlowCondition " + "where flow.id = ? and " + "condition = ? and component.id = ? and nextStage.id = ?", [opportunityFlow?.id, it?.condition, it?.component?.id, nextStage?.id])
                    if (!opportunityFlowCondition)
                    {
                        opportunityFlowCondition = new OpportunityFlowCondition()
                        opportunityFlowCondition.flow = opportunityFlow
                        opportunityFlowCondition.condition = it?.condition
                        opportunityFlowCondition.message = it?.message
                        opportunityFlowCondition.component = it?.component
                        opportunityFlowCondition.executeSequence = it?.executeSequence
                        opportunityFlowCondition.nextStage = nextStage

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

            //nextStages
            if (it?.nextStages?.size() > 0)
            {
                it?.nextStages?.each {
                    // currentStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.stage?.stage?.id])
                    nextStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.nextStage?.stage?.id])
                    opportunityFlowNextStage = OpportunityFlowNextStage.find("from " + "OpportunityFlowNextStage " + "where flow.id = ? and " + "nextStage.id = ?", [opportunityFlow?.id, nextStage?.id])
                    if (!opportunityFlowNextStage)
                    {
                        opportunityFlowNextStage = new OpportunityFlowNextStage()
                        opportunityFlowNextStage.flow = opportunityFlow
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

            //events
            if (it?.events?.size() > 0)
            {
                it?.events?.each {
                    opportunityEvent = OpportunityEvent.find("from " + "OpportunityEvent " + "where stage.id = ? and " + "name = ?", [opportunityFlow?.id, it?.name])
                    if (!opportunityEvent)
                    {
                        opportunityEvent = new OpportunityEvent()
                        opportunityEvent.stage = opportunityFlow
                        opportunityEvent.name = it?.name
                        opportunityEvent.isSynchronous = it?.isSynchronous
                        opportunityEvent.executeSequence = it?.executeSequence
                        opportunityEvent.script = it?.script
                        opportunityEvent.log = it?.log
                        opportunityEvent.startTime = it?.startTime
                        opportunityEvent.endTime = it?.endTime
                        opportunityEvent.component = it?.component

                        if (opportunityEvent.validate())
                        {
                            opportunityEvent.save flush: true
                        }
                        else
                        {
                            log.info opportunityEvent.errors
                        }
                    }
                }
            }

            opportunityWorkflowStageAttachmentTypes = OpportunityWorkflowStageAttachmentType.findAll("from OpportunityWorkflowStageAttachmentType where stage.id = ?", [it?.id])
            if (opportunityWorkflowStageAttachmentTypes?.size() > 0)
            {
                opportunityWorkflowStageAttachmentTypes?.each {
                    opportunityFlowAttachmentType = OpportunityFlowAttachmentType.find("from OpportunityFlowAttachmentType where stage.id = ? and attachmentType.id = ?", [opportunityFlow?.id, it?.attachmentType?.id])
                    if (!opportunityFlowAttachmentType)
                    {
                        opportunityFlowAttachmentType = new OpportunityFlowAttachmentType()
                        opportunityFlowAttachmentType.stage = opportunityFlow
                        opportunityFlowAttachmentType.attachmentType = it?.attachmentType
                        if (opportunityFlowAttachmentType.validate())
                        {
                            opportunityFlowAttachmentType.save flush: true
                        }
                        else
                        {
                            log.info opportunityFlowAttachmentType.errors
                        }
                    }
                }
            }

        }
    }
}

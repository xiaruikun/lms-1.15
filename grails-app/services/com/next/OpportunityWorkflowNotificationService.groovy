package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-5-18
 */
@Transactional
class OpportunityWorkflowNotificationService
{

    def initNotification(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        def territoryAccount
        def territory
        OpportunityNotification opportunityNotification
        def opportunityWorkflow
        def opportunityWorkflowNotifications
        def territoryOpportunityWorkflows

        def stage
        def message
        def opportunityType = OpportunityType.find("from OpportunityType where code = '10'")


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

        opportunityWorkflowNotifications = OpportunityWorkflowNotification.findAll("from OpportunityWorkflowNotification where workflow.id = ${opportunityWorkflow?.id}")

        for (
            notification in
                opportunityWorkflowNotifications)
        {
            opportunityNotification = OpportunityNotification.find("from OpportunityNotification where " + "opportunity.id = ? and user.id = ? and " + "stage.id = ? and cellphone = ?", [opportunity?.id,
                notification?.user?.id,
                notification?.stage?.id, notification?.cellphone])
            if (!opportunityNotification)
            {
                opportunityNotification = new OpportunityNotification()
                opportunityNotification.opportunity = opportunity
                opportunityNotification.user = notification?.user
                opportunityNotification.stage = notification?.stage
                opportunityNotification.messageTemplate = notification?.messageTemplate
                opportunityNotification.toManager = notification?.toManager
                opportunityNotification.cellphone = notification?.cellphone
                if (opportunityNotification.validate())
                {
                    opportunityNotification.save flush: true
                }
                else
                {
                    log.info opportunityNotification.errors
                }
            }
        }
    }
}

package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-21
 * @CreatedDate 2017-4-26
 */
@Transactional
class WorkflowInstanceNotificationService
{

    static scope = "singleton"

    MessageService messageService

    def initWorkflowInstanceNotification(WorkflowInstanceStage workflowInstanceStage, Opportunity opportunity, WorkflowStage workflowStage)
    {
        def workflowInstanceNotification

        def workflowNotifications = WorkflowNotification.findAll("from WorkflowNotification where stage.id = ${workflowStage?.id}")
        def opportunityTeams = OpportunityTeam.findAll("from OpportunityTeam where opportunity.id = ${opportunity?.id}")
        workflowNotifications?.each { workflowNotification ->
            if (workflowNotification?.position)
            {
                opportunityTeams?.each { opportunityTeam ->
                    if (opportunityTeam?.user?.position == workflowNotification?.position)
                    {
                        workflowInstanceNotification = WorkflowInstanceNotification.find("from WorkflowInstanceNotification where user.id = ${opportunityTeam?.user?.id} and stage.id = ${workflowInstanceStage?.id} and messageTemplate.id = ${workflowNotification?.messageTemplate?.id} and cellphone = '${workflowNotification?.cellphone}'")
                        if (!workflowInstanceNotification)
                        {
                            workflowInstanceNotification = new WorkflowInstanceNotification()
                            workflowInstanceNotification.stage = workflowInstanceStage
                            workflowInstanceNotification.user = opportunityTeam?.user
                            workflowInstanceNotification.messageTemplate = workflowNotification?.messageTemplate
                            workflowInstanceNotification.toManager = workflowNotification?.toManager
                            workflowInstanceNotification.cellphone = workflowNotification?.cellphone
                            workflowInstanceNotification.save flush: true
                        }
                    }
                }
            }
            else
            {
                workflowInstanceNotification = WorkflowInstanceNotification.find("from WorkflowInstanceNotification where user.id is null and stage.id = ${workflowInstanceStage?.id} and messageTemplate.id = ${workflowNotification?.messageTemplate?.id} and cellphone = '${workflowNotification?.cellphone}'")
                if (!workflowInstanceNotification)
                {
                    workflowInstanceNotification = new WorkflowInstanceNotification()
                    workflowInstanceNotification.stage = workflowInstanceStage
                    workflowInstanceNotification.messageTemplate = workflowNotification?.messageTemplate
                    workflowInstanceNotification.toManager = workflowNotification?.toManager
                    workflowInstanceNotification.cellphone = workflowNotification?.cellphone
                    workflowInstanceNotification.save flush: true
                }
            }
        }
    }

    def sendNotification(WorkflowInstance workflowInstance)
    {
        def message
        def cellphone
        def reportings = []

        def shell = new GroovyShell()
        def closure

        if (workflowInstance?.status?.name == "Pending")
        {
            def workflowInstanceNotifications = WorkflowInstanceNotification.findAll("from WorkflowInstanceNotification where stage.id = ${workflowInstance?.stage?.id}")
            workflowInstanceNotifications.each {
                try
                {
                    if (it?.user)
                    {
                        cellphone = it?.user?.cellphone
                    }
                    else if (it?.cellphone)
                    {
                        closure = shell.evaluate(it?.cellphone)
                        cellphone = closure(workflowInstance)
                    }
                    else
                    {
                        log.error "workflowInstance cellphone configuration error!"
                    }

                    closure = shell.evaluate(it?.messageTemplate?.template)
                    message = closure(workflowInstance)

                    messageService.sendMessage2(cellphone, message)

                    if (it?.toManager && it?.user)
                    {
                        reportings = Reporting.findAll("from Reporting where user.id = ?", [it?.user])
                        reportings?.each {
                            cellphone = it?.manager?.cellphone
                            messageService.sendMessage2(cellphone, message)
                        }
                    }
                }
                catch (Exception e)
                {
                    log.error "workflowInstance script execute error: " + e
                }
            }
        }
    }
}

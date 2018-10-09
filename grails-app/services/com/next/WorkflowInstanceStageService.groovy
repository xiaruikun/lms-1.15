package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-21
 */
@Transactional
@CompileStatic
@TypeChecked
class WorkflowInstanceStageService
{

    static scope = "singleton"

    WorkflowInstanceUserService workflowInstanceUserService
    WorkflowInstanceNotificationService workflowInstanceNotificationService
    WorkflowInstanceConditionService workflowInstanceConditionService
    WorkflowInstanceEventService workflowInstanceEventService

    def initWorkflowInstanceStage(WorkflowInstance workflowInstance)
    {

        def workflow = workflowInstance?.workflow
        def opportunity = workflowInstance?.opportunity
        def workflowInstanceStage

        def workflowStages = WorkflowStage.findAll("from WorkflowStage where workflow.id = ${workflow?.id} order by executionSequence asc")
        workflowStages?.each {
            workflowInstanceStage = WorkflowInstanceStage.find("from WorkflowInstanceStage where instance.id = ${workflowInstance?.id} and name = '${it?.name}'")
            if (!workflowInstanceStage)
            {
                workflowInstanceStage = new WorkflowInstanceStage()
                workflowInstanceStage.instance = workflowInstance
                workflowInstanceStage.executionSequence = it?.executionSequence
                workflowInstanceStage.name = it?.name
                workflowInstanceStage.canReject = it?.canReject
                workflowInstanceStage.save flush: true

                workflowInstanceUserService.initWorkflowInstanceUser workflowInstanceStage, opportunity, it
                workflowInstanceNotificationService.initWorkflowInstanceNotification workflowInstanceStage, opportunity, it
                workflowInstanceEventService.initWorkflowInstanceEvent workflowInstanceStage, it
            }
        }
        workflowInstanceConditionService.initWorkflowInstanceCondition workflowInstance
    }
}

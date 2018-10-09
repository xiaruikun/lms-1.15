package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-21
 * @ModifiedDate 2017-4-26
 */
@Transactional
@CompileStatic
@TypeChecked
class WorkflowInstanceService
{
    static scope = "singleton"

    WorkflowInstanceStageService workflowInstanceStageService
    WorkflowInstanceEventService workflowInstanceEventService
    WorkflowInstanceConditionService workflowInstanceConditionService
    WorkflowInstanceNotificationService workflowInstanceNotificationService

    def initWorkflowInstance(WorkflowInstance workflowInstance)
    {

        workflowInstanceStageService.initWorkflowInstanceStage workflowInstance

        def workflowInstanceStage = WorkflowInstanceStage.find("from WorkflowInstanceStage where instance.id = ${workflowInstance?.id} order by executionSequence asc")
        def workflowInstanceStatus = WorkflowInstanceStatus.find("from WorkflowInstanceStatus order by id asc")
        workflowInstance.stage = workflowInstanceStage
        workflowInstance.status = workflowInstanceStatus
        workflowInstance.save flush: true
    }

    def approve(WorkflowInstance workflowInstance)
    {
        WorkflowInstanceStage workflowInstanceStage
        def executionSequence
        def map = [:]
        def verifyCondition

        if (!workflowInstance?.status?.name == "Pending")
        {
            map['flag'] = false
            map['message'] = '工作流已结束！'
            return map
        }
        workflowInstanceStage = workflowInstance?.stage
        if (!workflowInstanceStage)
        {
            map['flag'] = false
            map['message'] = "找不到工作流"
            return map
        }

        workflowInstanceEventService.evaluate(workflowInstanceStage, "negative")

        verifyCondition = workflowInstanceConditionService.evaluate(workflowInstanceStage)
        if (!verifyCondition['flag'])
        {
            return verifyCondition
        }

        workflowInstanceEventService.evaluate(workflowInstanceStage, "positive")

        if (verifyCondition['nextStage'])
        {
            workflowInstanceStage = (WorkflowInstanceStage) verifyCondition['nextStage']

            workflowInstance.stage = workflowInstanceStage
            workflowInstance.save flush: true
        }
        else
        {
            executionSequence = workflowInstanceStage?.executionSequence
            workflowInstanceStage = WorkflowInstanceStage.find("from WorkflowInstanceStage where instance.id = ? and executionSequence > ? order by executionSequence ASC",
                                                               [workflowInstance?.id, executionSequence])
            if (!workflowInstanceStage)
            {
                map['flag'] = false
                map['message'] = "找不到工作流"
                return map
            }

            workflowInstance.stage = workflowInstanceStage
            workflowInstance.save flush: true
        }
        workflowInstanceNotificationService.sendNotification(workflowInstance)
        map['flag'] = true
        map['message'] = "成功"
        return map
    }

    Boolean reject(WorkflowInstance workflowInstance)
    {
        if (workflowInstance?.status?.name == "Pending")
        {
            def workflowInstanceStage = workflowInstance?.stage
            if (workflowInstanceStage)
            {
                def executionSequence = workflowInstanceStage?.executionSequence
                if (workflowInstanceStage?.canReject)
                {
                    workflowInstanceStage = WorkflowInstanceStage.find("from WorkflowInstanceStage where instance.id = ? and executionSequence < ? order by executionSequence DESC",
                                                                       [workflowInstance?.id, executionSequence])
                    if (workflowInstanceStage)
                    {
                        workflowInstance.stage = workflowInstanceStage
                        workflowInstance.save flush: true

                        workflowInstanceNotificationService.sendNotification(workflowInstance)
                        return true
                    }
                }
            }
        }
        return false
    }

    Boolean cancel(WorkflowInstance workflowInstance)
    {
        if (workflowInstance?.status?.name == "Pending")
        {
            def status = WorkflowInstanceStatus.find("from WorkflowInstanceStatus where name = 'Failed'")
            workflowInstance.status = status
            workflowInstance.save flush: true

            workflowInstanceNotificationService.sendNotification(workflowInstance)
            return true
        }
        return false
    }

    Boolean complete(WorkflowInstance workflowInstance)
    {
        if (workflowInstance?.status?.name == "Pending")
        {
            def workflowInstanceStage = workflowInstance?.stage
            if (workflowInstanceStage)
            {
                def executionSequence = workflowInstanceStage?.executionSequence
                workflowInstanceStage = WorkflowInstanceStage.find("from WorkflowInstanceStage where instance.id = ? and executionSequence > ? order by executionSequence ASC",
                                                                   [workflowInstance?.id, executionSequence])
                if (!workflowInstanceStage)
                {
                    def status = WorkflowInstanceStatus.find("from WorkflowInstanceStatus where name = 'Completed'")
                    workflowInstance.status = status
                    workflowInstance.save flush: true

                    workflowInstanceNotificationService.sendNotification(workflowInstance)
                    return true
                }
            }
        }
        return false
    }
}

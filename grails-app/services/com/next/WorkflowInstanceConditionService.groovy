package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-26
 */
@Transactional
class WorkflowInstanceConditionService
{

    static scope = "singleton"

    ComponentService componentService

    def initWorkflowInstanceCondition(WorkflowInstance workflowInstance)
    {
        def workflow = workflowInstance?.workflow
        def workflowStages = WorkflowStage.findAll("from WorkflowStage where workflow.id = ${workflow?.id} order by executionSequence asc")
        def workflowInstanceStage
        def workflowConditions
        def workflowInstanceNextStage
        def workflowInstanceCondition

        workflowStages?.each { workflowStage ->
            workflowInstanceStage = WorkflowInstanceStage.find("from WorkflowInstanceStage where instance.id = ${workflowInstance?.id} and name = '${workflowStage?.name}'")
            workflowConditions = WorkflowCondition.findAll("from WorkflowCondition where stage.id = ${workflowStage?.id} order by executeSequence asc")
            workflowConditions?.each {
                workflowInstanceNextStage = WorkflowInstanceStage.find("from WorkflowInstanceStage where instance.id = ${workflowInstance?.id} and name = '${it?.nextStage?.name}'")
                workflowInstanceCondition = WorkflowInstanceCondition.find("from WorkflowInstanceCondition where stage.id = ${workflowInstanceStage?.id} and component.id =  ${it?.component?.id} and nextStage.id = ${workflowInstanceNextStage?.id}")
                if (!workflowInstanceCondition)
                {
                    workflowInstanceCondition = new WorkflowInstanceCondition()
                    workflowInstanceCondition.stage = workflowInstanceStage
                    workflowInstanceCondition.executeSequence = it?.executeSequence
                    workflowInstanceCondition.message = it?.message
                    workflowInstanceCondition.log = it?.log
                    workflowInstanceCondition.component = it?.component
                    workflowInstanceCondition.nextStage = workflowInstanceNextStage
                    workflowInstanceCondition.save flush: true
                }
            }
        }
    }

    def evaluate(WorkflowInstanceStage workflowInstanceStage)
    {
        def result
        def map = [:]
        map['flag'] = true

        def workflowInstanceCondition = WorkflowInstanceCondition.findAll("from WorkflowInstanceCondition where stage.id = ${workflowInstanceStage?.id} order by executeSequence asc")
        for (
            it in
                workflowInstanceCondition)
        {
            if (!it?.component)
            {
                map['flag'] = false
                map['message'] = "校验组件配置异常，请联系管理员"

                it.log = map['message']
                it.save flush: true

                break
            }

            result = componentService.evaluate it?.component, workflowInstanceStage?.instance?.opportunity

            it.log = result
            it.save flush: true

            if (result instanceof Exception)
            {
                map['flag'] = false
                map['message'] = "校验脚本运行异常，请联系管理员"
                break
            }
            else if (result instanceof Boolean)
            {
                if (result)
                {
                    if (it?.nextStage)
                    {
                        map['nextStage'] = it?.nextStage
                        break
                    }
                }
                else
                {
                    if (!it?.nextStage)
                    {
                        map['flag'] = false
                        map['message'] = it?.component?.message
                        break
                    }
                }
            }
            else
            {
                map['flag'] = false
                map['message'] = "校验脚本配置异常，请联系管理员"

                it.log = map['message']
                it.save flush: true
                break
            }
        }
        return map
    }
}

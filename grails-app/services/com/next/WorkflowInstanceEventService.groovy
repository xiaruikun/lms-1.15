package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-25
 */
@Transactional
class WorkflowInstanceEventService
{

    static scope = "singleton"
    ComponentService componentService

    def initWorkflowInstanceEvent(WorkflowInstanceStage workflowInstanceStage, WorkflowStage workflowStage)
    {
        def workflowInstanceEvent

        def workflowEvents = WorkflowEvent.findAll("from WorkflowEvent where stage.id = ${workflowStage?.id} order by executeSequence asc")
        workflowEvents?.each {
            workflowInstanceEvent = WorkflowInstanceEvent.find("from WorkflowInstanceEvent where stage.id = ${workflowInstanceStage?.id} and component.id = ${it?.component?.id}")
            if (!workflowInstanceEvent)
            {
                workflowInstanceEvent = new WorkflowInstanceEvent()
                workflowInstanceEvent.stage = workflowInstanceStage
                workflowInstanceEvent.isSynchronous = it?.isSynchronous
                workflowInstanceEvent.executeSequence = it?.executeSequence
                workflowInstanceEvent.log = it?.log
                workflowInstanceEvent.component = it?.component
                workflowInstanceEvent.save flush: true
            }
        }
    }

    def evaluate(WorkflowInstanceStage workflowInstanceStage, String executeSequence)
    {
        def events = []
        if (executeSequence == 'positive')
        {
            events = WorkflowInstanceEvent.findAll("from WorkflowInstanceEvent where stage.id = ${workflowInstanceStage?.id} and executeSequence > 0 order by executeSequence ASC")
        }
        else if (executeSequence == 'negative')
        {
            events = WorkflowInstanceEvent.findAll("from WorkflowInstanceEvent where stage.id = ${workflowInstanceStage?.id} and executeSequence < 0 order by executeSequence ASC")
        }
        if (events?.size() > 0)
        {
            events?.each {
                def event = it
                if (event?.isSynchronous)
                {
                    evaluate2(event)
                }
                else
                {
                    Thread.start {
                        evaluate2(event)
                    }
                }
            }
        }
    }

    def evaluate2(WorkflowInstanceEvent event)
    {
        def result
        event.startTime = new Date()
        result = componentService.evaluate event?.component, event?.stage?.instance?.opportunity

        event.log = result
        event.endTime = new Date()
        event.save flush: true

    }
}

package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Transactional
class OpportunityEventService
{
    ComponentService componentService

    def evaluate(OpportunityFlow opportunityFlow, String executeSequence)
    {
        def events = []
        if (executeSequence == 'positive')
        {
            events = OpportunityEvent.findAll("from OpportunityEvent where stage.id = ${opportunityFlow?.id} and executeSequence > 0 order by executeSequence ASC")
        }
        else if (executeSequence == 'negative')
        {
            events = OpportunityEvent.findAll("from OpportunityEvent where stage.id = ${opportunityFlow?.id} and executeSequence < 0 order by executeSequence ASC")
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

    def evaluate2(OpportunityEvent eventObject)
    {
        def shell = new GroovyShell()
        def closure
        def result
        def event = OpportunityEvent.find("from OpportunityEvent where id = ${eventObject?.id}")

        try
        {
            event.startTime = new Date()
            if (event?.component)
            {
                result = componentService.evaluate event?.component, event?.stage?.opportunity
            }
            else
            {
                closure = shell.evaluate(event?.script)
                result = closure(event?.stage?.opportunity)
            }

            event.log = result
            event.endTime = new Date()
            event.save flush: true
        }
        catch (Exception e)
        {
            event.log = e
            event.endTime = new Date()
            event.save flush: true
        }
    }

}

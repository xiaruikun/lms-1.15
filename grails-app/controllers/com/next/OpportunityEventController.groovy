package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class OpportunityEventController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def componentService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityEvent.list(params), model: [opportunityEventCount: OpportunityEvent.count()]
    }

    def show(OpportunityEvent opportunityEvent)
    {
        respond opportunityEvent
    }

    def create()
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Event'), true)

        respond new OpportunityEvent(params), model: [componentList: componentList]
    }

    @Transactional
    def save(OpportunityEvent opportunityEvent)
    {
        if (opportunityEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityEvent.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityEvent.errors, view: 'create'
            return
        }

        opportunityEvent.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityEvent.label', default: 'OpportunityEvent'), opportunityEvent.id])
                // redirect opportunityEvent
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityEvent?.stage?.id

            }
            '*' { respond opportunityEvent, [status: CREATED] }
        }
    }

    def edit(OpportunityEvent opportunityEvent)
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Event'), true)
        respond opportunityEvent, model: [componentList: componentList]
    }

    @Transactional
    def update(OpportunityEvent opportunityEvent)
    {
        if (opportunityEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityEvent.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityEvent.errors, view: 'edit'
            return
        }

        opportunityEvent.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityEvent.label', default: 'OpportunityEvent'), opportunityEvent.id])
                // redirect opportunityEvent
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityEvent?.stage?.id

            }
            '*' { respond opportunityEvent, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityEvent opportunityEvent)
    {

        if (opportunityEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityEvent.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityEvent.label', default: 'OpportunityEvent'), opportunityEvent.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityEvent?.stage?.id

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityEvent.label', default: 'OpportunityEvent'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def eventEvaluate(OpportunityEvent opportunityEvent)
    {
        def shell = new GroovyShell()
        def closure
        def result

        try
        {
            opportunityEvent.startTime = new Date()
            if (opportunityEvent?.component)
            {
                result = componentService.evaluate opportunityEvent?.component, opportunityEvent?.stage?.opportunity
            }
            else
            {
                closure = shell.evaluate(opportunityEvent?.script)
                result = closure(opportunityEvent?.stage?.opportunity)
            }
            opportunityEvent.log = result
            opportunityEvent.endTime = new Date()
            opportunityEvent.save flush: true
        }
        catch (Exception e)
        {
            opportunityEvent.log = e
            opportunityEvent.endTime = new Date()
            opportunityEvent.save flush: true
        }

        redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityEvent?.stage?.id
    }
}

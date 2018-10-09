package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION'])
@Transactional(readOnly = true)
class OpportunityWorkflowEventController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityWorkflowEvent.list(params), model: [opportunityWorkflowEventCount: OpportunityWorkflowEvent.count()]
    }

    def show(OpportunityWorkflowEvent opportunityWorkflowEvent)
    {
        respond opportunityWorkflowEvent
    }

    def create()
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Event'), true)
        respond new OpportunityWorkflowEvent(params), model: [componentList: componentList]
    }

    @Transactional
    def save(OpportunityWorkflowEvent opportunityWorkflowEvent)
    {
        if (opportunityWorkflowEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowEvent.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowEvent.errors, view: 'create'
            return
        }

        opportunityWorkflowEvent.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityWorkflowEvent.label', default: 'OpportunityWorkflowEvent'), opportunityWorkflowEvent.id])
                // redirect opportunityWorkflowEvent
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowEvent?.stage?.id
            }
            '*' { respond opportunityWorkflowEvent, [status: CREATED] }
        }
    }

    def edit(OpportunityWorkflowEvent opportunityWorkflowEvent)
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Event'), true)
        respond opportunityWorkflowEvent, model: [componentList: componentList]
    }

    @Transactional
    def update(OpportunityWorkflowEvent opportunityWorkflowEvent)
    {
        if (opportunityWorkflowEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowEvent.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowEvent.errors, view: 'edit'
            return
        }

        opportunityWorkflowEvent.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityWorkflowEvent.label', default: 'OpportunityWorkflowEvent'), opportunityWorkflowEvent.id])
                // redirect opportunityWorkflowEvent
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowEvent?.stage?.id
            }
            '*' { respond opportunityWorkflowEvent, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityWorkflowEvent opportunityWorkflowEvent)
    {

        if (opportunityWorkflowEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityWorkflowEvent.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityWorkflowEvent.label', default: 'OpportunityWorkflowEvent'), opportunityWorkflowEvent.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowEvent?.stage?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityWorkflowEvent.label', default: 'OpportunityWorkflowEvent'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

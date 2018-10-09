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
class WorkflowEventController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond WorkflowEvent.list(params), model: [workflowEventCount: WorkflowEvent.count()]
    }

    def show(WorkflowEvent workflowEvent)
    {
        respond workflowEvent
    }

    def create()
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Event'), true)
        respond new WorkflowEvent(params), model: [componentList: componentList]
    }

    @Transactional
    def save(WorkflowEvent workflowEvent)
    {
        if (workflowEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowEvent.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowEvent.errors, view: 'create'
            return
        }

        workflowEvent.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowEvent.label', default: 'WorkflowEvent'), workflowEvent.id])
                // redirect workflowEvent
                redirect controller: "workflowStage", action: "show", id: workflowEvent?.stage?.id
            }
            '*' { respond workflowEvent, [status: CREATED] }
        }
    }

    def edit(WorkflowEvent workflowEvent)
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Event'), true)
        respond workflowEvent, model: [componentList: componentList]
    }

    @Transactional
    def update(WorkflowEvent workflowEvent)
    {
        if (workflowEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowEvent.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowEvent.errors, view: 'edit'
            return
        }

        workflowEvent.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowEvent.label', default: 'WorkflowEvent'), workflowEvent.id])
                // redirect workflowEvent
                redirect controller: "workflowStage", action: "show", id: workflowEvent?.stage?.id
            }
            '*' { respond workflowEvent, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowEvent workflowEvent)
    {

        if (workflowEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowEvent.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowEvent.label', default: 'WorkflowEvent'), workflowEvent.id])
                // redirect action:"index", method:"GET"
                redirect controller: "workflowStage", action: "show", id: workflowEvent?.stage?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowEvent.label', default: 'WorkflowEvent'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-25
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class WorkflowInstanceEventController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond WorkflowInstanceEvent.list(params), model: [workflowInstanceEventCount: WorkflowInstanceEvent.count()]
    }

    def show(WorkflowInstanceEvent workflowInstanceEvent)
    {
        respond workflowInstanceEvent
    }

    def create()
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Event'), true)
        respond new WorkflowInstanceEvent(params), model: [componentList: componentList]
    }

    @Transactional
    def save(WorkflowInstanceEvent workflowInstanceEvent)
    {
        if (workflowInstanceEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceEvent.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceEvent.errors, view: 'create'
            return
        }

        workflowInstanceEvent.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowInstanceEvent.label', default: 'WorkflowInstanceEvent'), workflowInstanceEvent.id])
                //redirect workflowInstanceEvent
                redirect controller: "workflowInstanceStage", action: "show", method: "GET", id: workflowInstanceEvent.stage.id
            }
            '*' { respond workflowInstanceEvent, [status: CREATED] }
        }
    }

    def edit(WorkflowInstanceEvent workflowInstanceEvent)
    {
        respond workflowInstanceEvent
    }

    @Transactional
    def update(WorkflowInstanceEvent workflowInstanceEvent)
    {
        if (workflowInstanceEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceEvent.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceEvent.errors, view: 'edit'
            return
        }

        workflowInstanceEvent.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowInstanceEvent.label', default: 'WorkflowInstanceEvent'), workflowInstanceEvent.id])
                redirect workflowInstanceEvent
            }
            '*' { respond workflowInstanceEvent, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowInstanceEvent workflowInstanceEvent)
    {

        if (workflowInstanceEvent == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowInstanceEvent.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowInstanceEvent.label', default: 'WorkflowInstanceEvent'), workflowInstanceEvent.id])
                //redirect action:"index", method:"GET"
                redirect controller: "workflowInstanceStage", action: "show", method: "GET", id: workflowInstanceEvent.stage.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowInstanceEvent.label', default: 'WorkflowInstanceEvent'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

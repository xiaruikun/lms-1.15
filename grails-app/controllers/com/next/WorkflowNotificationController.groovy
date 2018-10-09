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
class WorkflowNotificationController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond WorkflowNotification.list(params), model: [workflowNotificationCount: WorkflowNotification.count()]
    }

    def show(WorkflowNotification workflowNotification)
    {
        respond workflowNotification
    }

    def create()
    {
        respond new WorkflowNotification(params)
    }

    @Transactional
    def save(WorkflowNotification workflowNotification)
    {
        if (workflowNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowNotification.errors, view: 'create'
            return
        }

        workflowNotification.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowNotification.label', default: 'WorkflowNotification'), workflowNotification.id])
                // redirect workflowNotification
                redirect controller: "workflowStage", action: "show", id: workflowNotification?.stage?.id
            }
            '*' { respond workflowNotification, [status: CREATED] }
        }
    }

    def edit(WorkflowNotification workflowNotification)
    {
        respond workflowNotification
    }

    @Transactional
    def update(WorkflowNotification workflowNotification)
    {
        if (workflowNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowNotification.errors, view: 'edit'
            return
        }

        workflowNotification.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowNotification.label', default: 'WorkflowNotification'), workflowNotification.id])
                // redirect workflowNotification
                redirect controller: "workflowStage", action: "show", id: workflowNotification?.stage?.id
            }
            '*' { respond workflowNotification, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowNotification workflowNotification)
    {

        if (workflowNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowNotification.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowNotification.label', default: 'WorkflowNotification'), workflowNotification.id])
                // redirect action:"index", method:"GET"
                redirect controller: "workflowStage", action: "show", id: workflowNotification?.stage?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowNotification.label', default: 'WorkflowNotification'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

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
class WorkflowInstanceStageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond WorkflowInstanceStage.list(params), model: [workflowInstanceStageCount: WorkflowInstanceStage.count()]
    }

    def show(WorkflowInstanceStage workflowInstanceStage)
    {
        def workflowInstanceConditions = WorkflowInstanceCondition.findAll("from WorkflowInstanceCondition where stage.id = ${workflowInstanceStage?.id} order by executeSequence asc")
        def workflowInstanceEvents = WorkflowInstanceEvent.findAll("from WorkflowInstanceEvent where stage.id = ${workflowInstanceStage?.id} order by executeSequence asc")
        def workflowInstanceUsers = WorkflowInstanceUser.findAllByStage(workflowInstanceStage)
        def workflowInstanceNotifications = WorkflowInstanceNotification.findAllByStage(workflowInstanceStage)
        respond workflowInstanceStage, model: [workflowInstanceConditions: workflowInstanceConditions, workflowInstanceEvents: workflowInstanceEvents, workflowInstanceUsers: workflowInstanceUsers, workflowInstanceNotifications: workflowInstanceNotifications]
    }

    def create()
    {
        respond new WorkflowInstanceStage(params)
    }

    @Transactional
    def save(WorkflowInstanceStage workflowInstanceStage)
    {
        if (workflowInstanceStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceStage.errors, view: 'create'
            return
        }

        workflowInstanceStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowInstanceStage.label', default: 'WorkflowInstanceStage'), workflowInstanceStage.id])
                //redirect workflowInstanceStage
                redirect controller: "workflowInstance", action: "show", method: "GET", id: workflowInstanceStage.instance.id
            }
            '*' { respond workflowInstanceStage, [status: CREATED] }
        }
    }

    def edit(WorkflowInstanceStage workflowInstanceStage)
    {
        respond workflowInstanceStage
    }

    @Transactional
    def update(WorkflowInstanceStage workflowInstanceStage)
    {
        if (workflowInstanceStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceStage.errors, view: 'edit'
            return
        }

        workflowInstanceStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowInstanceStage.label', default: 'WorkflowInstanceStage'), workflowInstanceStage.id])
                redirect workflowInstanceStage
            }
            '*' { respond workflowInstanceStage, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowInstanceStage workflowInstanceStage)
    {

        if (workflowInstanceStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowInstanceStage.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowInstanceStage.label', default: 'WorkflowInstanceStage'), workflowInstanceStage.id])
                //redirect action:"index", method:"GET"
                redirect controller: "workflowInstance", action: "show", method: "GET", id: workflowInstanceStage.instance.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowInstanceStage.label', default: 'WorkflowInstanceStage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

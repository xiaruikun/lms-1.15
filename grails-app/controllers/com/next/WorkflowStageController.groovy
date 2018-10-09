package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-28
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class WorkflowStageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE'])
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond WorkflowStage.list(params), model: [workflowStageCount: WorkflowStage.count()]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE'])
    def show(WorkflowStage workflowStage)
    {
        def workflowConditions = WorkflowCondition.findAll("from WorkflowCondition where stage.id = ${workflowStage?.id} order by executeSequence asc")
        def workflowEvents = WorkflowEvent.findAll("from WorkflowEvent where stage.id = ${workflowStage?.id} order by executeSequence asc")
        def workflowUsers = WorkflowUser.findAllByStage(workflowStage)
        def workflowNotifications = WorkflowNotification.findAllByStage(workflowStage)
        respond workflowStage, model: [workflowConditions: workflowConditions, workflowEvents: workflowEvents, workflowUsers: workflowUsers, workflowNotifications: workflowNotifications]
    }

    def create()
    {
        respond new WorkflowStage(params)
    }

    @Transactional
    def save(WorkflowStage workflowStage)
    {
        if (workflowStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowStage.errors, view: 'create'
            return
        }

        workflowStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowStage.label', default: 'WorkflowStage'), workflowStage.id])
                // redirect workflowStage
                redirect controller: "workflow", action: "show", id: workflowStage?.workflow?.id
            }
            '*' { respond workflowStage, [status: CREATED] }
        }
    }

    def edit(WorkflowStage workflowStage)
    {
        respond workflowStage
    }

    @Transactional
    def update(WorkflowStage workflowStage)
    {
        if (workflowStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowStage.errors, view: 'edit'
            return
        }

        workflowStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowStage.label', default: 'WorkflowStage'), workflowStage.id])
                // redirect workflowStage
                redirect controller: "workflow", action: "show", id: workflowStage?.workflow?.id
            }
            '*' { respond workflowStage, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowStage workflowStage)
    {

        if (workflowStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowStage.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowStage.label', default: 'WorkflowStage'), workflowStage.id])
                // redirect action:"index", method:"GET"
                redirect controller: "workflow", action: "show", id: workflowStage?.workflow?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowStage.label', default: 'WorkflowStage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

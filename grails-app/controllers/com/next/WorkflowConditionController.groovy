package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CONDITION_RULEENGINE'])
@Transactional(readOnly = true)
class WorkflowConditionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond WorkflowCondition.list(params), model: [workflowConditionCount: WorkflowCondition.count()]
    }

    def show(WorkflowCondition workflowCondition)
    {
        respond workflowCondition
    }

    def create()
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Condition'), true)
        def stage = WorkflowStage.findById(params['stage'])
        def workflowStages = WorkflowStage.findAllByWorkflow(stage?.workflow)
        respond new WorkflowCondition(params), model: [componentList: componentList, workflowStages: workflowStages]
    }

    @Transactional
    def save(WorkflowCondition workflowCondition)
    {
        if (workflowCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowCondition.errors, view: 'create'
            return
        }

        workflowCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowCondition.label', default: 'WorkflowCondition'), workflowCondition.id])
                // redirect workflowCondition
                redirect controller: "workflowStage", action: "show", id: workflowCondition?.stage?.id
            }
            '*' { respond workflowCondition, [status: CREATED] }
        }
    }

    def edit(WorkflowCondition workflowCondition)
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Condition'), true)
        def workflowStages = WorkflowStage.findAllByWorkflow(workflowCondition?.stage?.workflow)
        respond workflowCondition, model: [componentList: componentList, workflowStages: workflowStages]
    }

    @Transactional
    def update(WorkflowCondition workflowCondition)
    {
        if (workflowCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowCondition.errors, view: 'edit'
            return
        }

        workflowCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowCondition.label', default: 'WorkflowCondition'), workflowCondition.id])
                // redirect workflowCondition
                redirect controller: "workflowStage", action: "show", id: workflowCondition?.stage?.id
            }
            '*' { respond workflowCondition, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowCondition workflowCondition)
    {

        if (workflowCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowCondition.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowCondition.label', default: 'WorkflowCondition'), workflowCondition.id])
                // redirect action:"index", method:"GET"
                redirect controller: "workflowStage", action: "show", id: workflowCondition?.stage?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowCondition.label', default: 'WorkflowCondition'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

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
class WorkflowInstanceConditionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond WorkflowInstanceCondition.list(params), model: [workflowInstanceConditionCount: WorkflowInstanceCondition.count()]
    }

    def show(WorkflowInstanceCondition workflowInstanceCondition)
    {
        respond workflowInstanceCondition
    }

    def create()
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Condition'), true)
        def stage = WorkflowInstanceStage.findById(params['stage'])
        def workflowInstanceStages = WorkflowInstanceStage.findAllByInstance(stage?.instance)
        respond new WorkflowInstanceCondition(params), model: [componentList: componentList, workflowInstanceStages: workflowInstanceStages]
    }

    @Transactional
    def save(WorkflowInstanceCondition workflowInstanceCondition)
    {
        if (workflowInstanceCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceCondition.errors, view: 'create'
            return
        }

        workflowInstanceCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowInstanceCondition.label', default: 'WorkflowInstanceCondition'), workflowInstanceCondition.id])
                //  redirect workflowInstanceCondition
                redirect controller: "workflowInstanceStage", action: "show", method: "GET", id: workflowInstanceCondition.stage.id
            }
            '*' { respond workflowInstanceCondition, [status: CREATED] }
        }
    }

    def edit(WorkflowInstanceCondition workflowInstanceCondition)
    {
        respond workflowInstanceCondition
    }

    @Transactional
    def update(WorkflowInstanceCondition workflowInstanceCondition)
    {
        if (workflowInstanceCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceCondition.errors, view: 'edit'
            return
        }

        workflowInstanceCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowInstanceCondition.label', default: 'WorkflowInstanceCondition'), workflowInstanceCondition.id])
                redirect workflowInstanceCondition
            }
            '*' { respond workflowInstanceCondition, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowInstanceCondition workflowInstanceCondition)
    {

        if (workflowInstanceCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowInstanceCondition.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowInstanceCondition.label', default: 'WorkflowInstanceCondition'), workflowInstanceCondition.id])
                //redirect action:"index", method:"GET"
                redirect controller: "workflowInstanceStage", action: "show", method: "GET", id: workflowInstanceCondition.stage.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowInstanceCondition.label', default: 'WorkflowInstanceCondition'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

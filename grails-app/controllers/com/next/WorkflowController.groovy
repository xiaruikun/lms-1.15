package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-25
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class WorkflowController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE'])
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Workflow.list(params), model: [workflowCount: Workflow.count()]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE'])
    def show(Workflow workflow)
    {
        def workflowStages = WorkflowStage.findAll("from WorkflowStage where workflow.id = ${workflow?.id} order by executionSequence asc")
        respond workflow, model: [workflowStages: workflowStages]
    }

    def create()
    {
        respond new Workflow(params)
    }

    @Transactional
    def save(Workflow workflow)
    {
        if (workflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflow.errors, view: 'create'
            return
        }

        workflow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflow.label', default: 'Workflow'), workflow.id])
                redirect workflow
            }
            '*' { respond workflow, [status: CREATED] }
        }
    }

    def edit(Workflow workflow)
    {
        respond workflow
    }

    @Transactional
    def update(Workflow workflow)
    {
        if (workflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflow.errors, view: 'edit'
            return
        }

        workflow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflow.label', default: 'Workflow'), workflow.id])
                redirect workflow
            }
            '*' { respond workflow, [status: OK] }
        }
    }

    @Transactional
    def delete(Workflow workflow)
    {

        if (workflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflow.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflow.label', default: 'Workflow'), workflow.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflow.label', default: 'Workflow'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

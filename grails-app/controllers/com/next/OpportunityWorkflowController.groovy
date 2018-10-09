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
class OpportunityWorkflowController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE'])
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityWorkflow.list(params), model: [opportunityWorkflowCount: OpportunityWorkflow.count()]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE'])
    def show(OpportunityWorkflow opportunityWorkflow)
    {
        def opportunityWorkflowRoles = OpportunityWorkflowRole.findAllByWorkflow(opportunityWorkflow)
        def opportunityWorkflowNotifications = OpportunityWorkflowNotification.findAllByWorkflow(opportunityWorkflow)
        def opportunityWorkflowStages = OpportunityWorkflowStage.findAllByWorkflow(opportunityWorkflow)
        respond opportunityWorkflow, model: [opportunityWorkflowRoles: opportunityWorkflowRoles, opportunityWorkflowNotifications: opportunityWorkflowNotifications, opportunityWorkflowStages: opportunityWorkflowStages]
    }

    def create()
    {
        //def territories = Territory.findAllByInheritFlow(false)
        //respond new OpportunityWorkflow(params), model:[territories: territories]
        respond new OpportunityWorkflow(params)
    }

    @Transactional
    def save(OpportunityWorkflow opportunityWorkflow)
    {
        if (opportunityWorkflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflow.errors, view: 'create'
            return
        }

        opportunityWorkflow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityWorkflow.label', default: 'OpportunityWorkflow'), opportunityWorkflow.id])
                redirect opportunityWorkflow
            }
            '*' { respond opportunityWorkflow, [status: CREATED] }
        }
    }

    def edit(OpportunityWorkflow opportunityWorkflow)
    {
        respond opportunityWorkflow
    }

    @Transactional
    def update(OpportunityWorkflow opportunityWorkflow)
    {
        if (opportunityWorkflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflow.errors, view: 'edit'
            return
        }

        opportunityWorkflow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityWorkflow.label', default: 'OpportunityWorkflow'), opportunityWorkflow.id])
                redirect opportunityWorkflow
            }
            '*' { respond opportunityWorkflow, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityWorkflow opportunityWorkflow)
    {

        if (opportunityWorkflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityWorkflow.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityWorkflow.label', default: 'OpportunityWorkflow'), opportunityWorkflow.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityWorkflow.label', default: 'OpportunityWorkflow'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

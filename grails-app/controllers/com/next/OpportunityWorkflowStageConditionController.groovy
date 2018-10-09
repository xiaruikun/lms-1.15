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
class OpportunityWorkflowStageConditionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityWorkflowStageCondition.list(params), model: [opportunityWorkflowStageConditionCount: OpportunityWorkflowStageCondition.count()]
    }

    def show(OpportunityWorkflowStageCondition opportunityWorkflowStageCondition)
    {
        respond opportunityWorkflowStageCondition
    }

    def create()
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Condition'), true)

        def opportunityWorkflowStage = OpportunityWorkflowStage.findById(params['stage'])
        def nextStages = OpportunityWorkflowStage.findAllByWorkflow(opportunityWorkflowStage?.workflow)

        respond new OpportunityWorkflowStageCondition(params), model: [componentList: componentList, nextStages: nextStages]
    }

    @Transactional
    def save(OpportunityWorkflowStageCondition opportunityWorkflowStageCondition)
    {
        if (opportunityWorkflowStageCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowStageCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowStageCondition.errors, view: 'create'
            return
        }

        opportunityWorkflowStageCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityWorkflowStageCondition.label', default: 'OpportunityWorkflowStageCondition'), opportunityWorkflowStageCondition.id])
                // redirect opportunityWorkflowStageCondition
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowStageCondition?.stage?.id
            }
            '*' { respond opportunityWorkflowStageCondition, [status: CREATED] }
        }
    }

    def edit(OpportunityWorkflowStageCondition opportunityWorkflowStageCondition)
    {
        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Condition'), true)

        def nextStages = OpportunityWorkflowStage.findAllByWorkflow(opportunityWorkflowStageCondition?.stage?.workflow)

        respond opportunityWorkflowStageCondition, model: [componentList: componentList, nextStages: nextStages]
    }

    @Transactional
    def update(OpportunityWorkflowStageCondition opportunityWorkflowStageCondition)
    {
        if (opportunityWorkflowStageCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowStageCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowStageCondition.errors, view: 'edit'
            return
        }

        opportunityWorkflowStageCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityWorkflowStageCondition.label', default: 'OpportunityWorkflowStageCondition'), opportunityWorkflowStageCondition.id])
                // redirect opportunityWorkflowStageCondition
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowStageCondition?.stage?.id
            }
            '*' { respond opportunityWorkflowStageCondition, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityWorkflowStageCondition opportunityWorkflowStageCondition)
    {

        if (opportunityWorkflowStageCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityWorkflowStageCondition.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityWorkflowStageCondition.label', default: 'OpportunityWorkflowStageCondition'), opportunityWorkflowStageCondition.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowStageCondition?.stage?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityWorkflowStageCondition.label', default: 'OpportunityWorkflowStageCondition'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

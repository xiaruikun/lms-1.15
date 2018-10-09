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
class OpportunityWorkflowStageNextStageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityWorkflowStageNextStage.list(params), model: [opportunityWorkflowStageNextStageCount: OpportunityWorkflowStageNextStage.count()]
    }

    def show(OpportunityWorkflowStageNextStage opportunityWorkflowStageNextStage)
    {
        respond opportunityWorkflowStageNextStage
    }

    def create()
    {
        def stageId = params['stage']
        def flow = OpportunityWorkflowStage.findById(stageId)
        // def nextStages = TerritoryFlow.findAllByTerritoryAndExecutionSequenceGreaterThan(flow?.territory, flow?.executionSequence)
        def nextStages = OpportunityWorkflowStage.findAllByWorkflow(flow?.workflow)
        respond new OpportunityWorkflowStageNextStage(params), model: [nextStages: nextStages]
    }

    @Transactional
    def save(OpportunityWorkflowStageNextStage opportunityWorkflowStageNextStage)
    {
        if (opportunityWorkflowStageNextStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowStageNextStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowStageNextStage.errors, view: 'create'
            return
        }

        opportunityWorkflowStageNextStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityWorkflowStageNextStage.label', default: 'OpportunityWorkflowStageNextStage'), opportunityWorkflowStageNextStage.id])
                // redirect opportunityWorkflowStageNextStage
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowStageNextStage?.stage?.id
            }
            '*' { respond opportunityWorkflowStageNextStage, [status: CREATED] }
        }
    }

    def edit(OpportunityWorkflowStageNextStage opportunityWorkflowStageNextStage)
    {
        respond opportunityWorkflowStageNextStage
    }

    @Transactional
    def update(OpportunityWorkflowStageNextStage opportunityWorkflowStageNextStage)
    {
        if (opportunityWorkflowStageNextStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowStageNextStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowStageNextStage.errors, view: 'edit'
            return
        }

        opportunityWorkflowStageNextStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityWorkflowStageNextStage.label', default: 'OpportunityWorkflowStageNextStage'), opportunityWorkflowStageNextStage.id])
                redirect opportunityWorkflowStageNextStage
            }
            '*' { respond opportunityWorkflowStageNextStage, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityWorkflowStageNextStage opportunityWorkflowStageNextStage)
    {

        if (opportunityWorkflowStageNextStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityWorkflowStageNextStage.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityWorkflowStageNextStage.label', default: 'OpportunityWorkflowStageNextStage'), opportunityWorkflowStageNextStage.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowStageNextStage?.stage?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityWorkflowStageNextStage.label', default: 'OpportunityWorkflowStageNextStage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

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
class OpportunityFlowNextStageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityFlowNextStage.list(params), model: [opportunityFlowNextStageCount: OpportunityFlowNextStage.count()]
    }

    def show(OpportunityFlowNextStage opportunityFlowNextStage)
    {
        respond opportunityFlowNextStage
    }

    def create()
    {
        def flowId = params['flow']
        def flow = OpportunityFlow.findById(flowId)
        def nextStages = OpportunityFlow.findAllByOpportunity(flow?.opportunity)
        respond new OpportunityFlowNextStage(params), model: [nextStages: nextStages]
    }

    @Transactional
    def save(OpportunityFlowNextStage opportunityFlowNextStage)
    {
        if (opportunityFlowNextStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlowNextStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlowNextStage.errors, view: 'create'
            return
        }

        opportunityFlowNextStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityFlowNextStage.label', default: 'OpportunityFlowNextStage'), opportunityFlowNextStage.id])
                // redirect opportunityFlowNextStage
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityFlowNextStage?.flow?.id

            }
            '*' { respond opportunityFlowNextStage, [status: CREATED] }
        }
    }

    def edit(OpportunityFlowNextStage opportunityFlowNextStage)
    {
        respond opportunityFlowNextStage
    }

    @Transactional
    def update(OpportunityFlowNextStage opportunityFlowNextStage)
    {
        if (opportunityFlowNextStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlowNextStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlowNextStage.errors, view: 'edit'
            return
        }

        opportunityFlowNextStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityFlowNextStage.label', default: 'OpportunityFlowNextStage'), opportunityFlowNextStage.id])
                redirect opportunityFlowNextStage
            }
            '*' { respond opportunityFlowNextStage, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityFlowNextStage opportunityFlowNextStage)
    {

        if (opportunityFlowNextStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityFlowNextStage.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityFlowNextStage.label', default: 'OpportunityFlowNextStage'), opportunityFlowNextStage.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityFlowNextStage?.flow?.id

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityFlowNextStage.label', default: 'OpportunityFlowNextStage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

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
class OpportunityFlowConditionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityFlowCondition.list(params), model: [opportunityFlowConditionCount: OpportunityFlowCondition.count()]
    }

    def show(OpportunityFlowCondition opportunityFlowCondition)
    {
        respond opportunityFlowCondition
    }

    def create()
    {
        def opportunityFlow = OpportunityFlow.findById(params['flow'])
        def nextStages = OpportunityFlow.findAllByOpportunity(opportunityFlow?.opportunity)

        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Condition'), true)
        respond new OpportunityFlowCondition(params), model: [componentList: componentList, nextStages: nextStages]
    }

    @Transactional
    def save(OpportunityFlowCondition opportunityFlowCondition)
    {
        if (opportunityFlowCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlowCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlowCondition.errors, view: 'create'
            return
        }

        opportunityFlowCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityFlowCondition.label', default: 'OpportunityFlowCondition'), opportunityFlowCondition.id])
                // redirect opportunityFlowCondition
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityFlowCondition?.flow?.id

            }
            '*' { respond opportunityFlowCondition, [status: CREATED] }
        }
    }

    def edit(OpportunityFlowCondition opportunityFlowCondition)
    {
        def nextStages = OpportunityFlow.findAllByOpportunity(opportunityFlowCondition?.flow?.opportunity)

        def componentList = Component.findAllByTypeAndActive(ComponentType.findByName('Condition'), true)
        respond opportunityFlowCondition, model: [nextStages: nextStages, componentList: componentList]
    }

    @Transactional
    def update(OpportunityFlowCondition opportunityFlowCondition)
    {
        if (opportunityFlowCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlowCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlowCondition.errors, view: 'edit'
            return
        }

        opportunityFlowCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityFlowCondition.label', default: 'OpportunityFlowCondition'), opportunityFlowCondition.id])
                // redirect opportunityFlowCondition
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityFlowCondition?.flow?.id

            }
            '*' { respond opportunityFlowCondition, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityFlowCondition opportunityFlowCondition)
    {

        if (opportunityFlowCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityFlowCondition.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityFlowCondition.label', default: 'OpportunityFlowCondition'), opportunityFlowCondition.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityFlowCondition?.flow?.id

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityFlowCondition.label', default: 'OpportunityFlowCondition'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

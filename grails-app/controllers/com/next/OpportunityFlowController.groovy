package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
@Transactional(readOnly = true)
class OpportunityFlowController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityFlow.list(params), model: [opportunityFlowCount: OpportunityFlow.count()]
    }

    def show(OpportunityFlow opportunityFlow)
    {
        def opportunityFlowAttachmentTypes = OpportunityFlowAttachmentType.findAllByStage(opportunityFlow)
        def opportunityFlowConditions = OpportunityFlowCondition.findAll("from OpportunityFlowCondition where flow.id = ${opportunityFlow?.id} order by executeSequence asc")
        def opportunityEvents = OpportunityEvent.findAll("from OpportunityEvent where stage.id = ${opportunityFlow?.id} order by executeSequence asc")
        respond opportunityFlow, model: [opportunityFlowAttachmentTypes: opportunityFlowAttachmentTypes, opportunityFlowConditions: opportunityFlowConditions, opportunityEvents: opportunityEvents]
    }

    def create()
    {
        def opportunity = Opportunity.findById(params["opportunity"])
        def userList = []
        def teamList
        if (opportunity)
        {
            teamList = OpportunityTeam.findAllByOpportunity(opportunity)
        }
        else
        {
            teamList = OpportunityTeam.findAll()
        }

        teamList.each {
            userList.add(it?.user)
        }
        def opportunityFlow = new OpportunityFlow(params)
        def opportunityStages
        if (opportunityFlow?.opportunity?.type)
        {
            opportunityStages = opportunityFlow?.opportunity?.type?.stages
        }
        else
        {
            opportunityStages = OpportunityType.findByCode('10')?.stages
        }

        opportunityStages?.each {
            if (!it?.active)
            {
                opportunityStages = opportunityStages?.minus(it)
            }
        }
        respond opportunityFlow, model: [userList: userList, opportunityStages: opportunityStages]
    }

    @Transactional
    def save(OpportunityFlow opportunityFlow)
    {
        if (opportunityFlow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlow.errors, view: 'create'
            return
        }

        opportunityFlow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityFlow' + '.label', default: 'OpportunityFlow'),
                    opportunityFlow.id])
                // redirect opportunityFlow
                redirect controller: "opportunity", action: "show", method: "GET", id: opportunityFlow
                    .opportunity.id
            }
            '*' { respond opportunityFlow, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR'])
    def edit(OpportunityFlow opportunityFlow)
    {
        respond opportunityFlow
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR'])
    @Transactional
    def update(OpportunityFlow opportunityFlow)
    {
        if (opportunityFlow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlow.errors, view: 'edit'
            return
        }

        if (!opportunityFlow?.startTime){
            opportunityFlow?.startTime = new Date()
        }

        opportunityFlow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityFlow' + '.label', default: 'OpportunityFlow'),
                    opportunityFlow.id])
                // redirect opportunityFlow
                redirect controller: "opportunity", action: "show", method: "GET", id: opportunityFlow
                    .opportunity.id
            }
            '*' { respond opportunityFlow, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityFlow opportunityFlow)
    {

        if (opportunityFlow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlow?.stage == opportunityFlow?.opportunity?.stage)
        {
            flash.message = "操作失败"
            redirect controller: "opportunity", action: "show", method: "GET", id: opportunityFlow
                .opportunity.id
            return
        }
        else
        {
            opportunityFlow.delete flush: true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityFlow' + '.label', default: 'OpportunityFlow'),
                        opportunityFlow.id])
                    // redirect action:"index", method:"GET"
                    redirect controller: "opportunity", action: "show", method: "GET", id: opportunityFlow
                        .opportunity.id
                }
                '*' { render status: NO_CONTENT }
            }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityFlow' + '.label', default: 'OpportunityFlow'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

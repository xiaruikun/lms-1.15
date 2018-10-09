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
class OpportunityWorkflowStageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE'])
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityWorkflowStage.list(params), model: [opportunityWorkflowStageCount: OpportunityWorkflowStage.count()]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE'])
    def show(OpportunityWorkflowStage opportunityWorkflowStage)
    {
        def opportunityWorkflowStageAttachmentTypes = OpportunityWorkflowStageAttachmentType.findAllByStage(opportunityWorkflowStage)
        def opportunityWorkflowStageConditions = OpportunityWorkflowStageCondition.findAll("from OpportunityWorkflowStageCondition where stage.id = ${opportunityWorkflowStage?.id} order by executeSequence asc")
        def opportunityWorkflowStageEvents = OpportunityWorkflowEvent.findAll("from OpportunityWorkflowEvent where stage.id = ${opportunityWorkflowStage?.id} order by executeSequence asc")
        respond opportunityWorkflowStage, model: [opportunityWorkflowStageAttachmentTypes: opportunityWorkflowStageAttachmentTypes, opportunityWorkflowStageConditions: opportunityWorkflowStageConditions, opportunityWorkflowStageEvents: opportunityWorkflowStageEvents]
    }

    def create()
    {
        def documentList = Document.findAllByActive(true)
        def opportunityWorkflowStage = new OpportunityWorkflowStage(params)
        def opportunityStages = opportunityWorkflowStage?.workflow?.opportunityType?.stages
        opportunityStages?.each {
            if (!it?.active)
            {
                opportunityStages = opportunityStages?.minus(it)
            }
        }
        respond opportunityWorkflowStage, model: [documentList: documentList, opportunityStages: opportunityStages]
    }

    @Transactional
    def save(OpportunityWorkflowStage opportunityWorkflowStage)
    {
        if (opportunityWorkflowStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowStage.errors, view: 'create'
            return
        }

        opportunityWorkflowStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityWorkflowStage.label', default: 'OpportunityWorkflowStage'), opportunityWorkflowStage.id])
                // redirect opportunityWorkflowStage
                redirect controller: "opportunityWorkflow", action: "show", id: opportunityWorkflowStage?.workflow?.id
            }
            '*' { respond opportunityWorkflowStage, [status: CREATED] }
        }
    }

    def edit(OpportunityWorkflowStage opportunityWorkflowStage)
    {
        def documentList = Document.findAllByActive(true)
        respond opportunityWorkflowStage, model: [documentList: documentList]
    }

    @Transactional
    def update(OpportunityWorkflowStage opportunityWorkflowStage)
    {
        if (opportunityWorkflowStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowStage.errors, view: 'edit'
            return
        }

        opportunityWorkflowStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityWorkflowStage.label', default: 'OpportunityWorkflowStage'), opportunityWorkflowStage.id])
                redirect opportunityWorkflowStage
            }
            '*' { respond opportunityWorkflowStage, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityWorkflowStage opportunityWorkflowStage)
    {

        if (opportunityWorkflowStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityWorkflowStage.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityWorkflowStage.label', default: 'OpportunityWorkflowStage'), opportunityWorkflowStage.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityWorkflow", action: "show", id: opportunityWorkflowStage?.workflow?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityWorkflowStage.label', default: 'OpportunityWorkflowStage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

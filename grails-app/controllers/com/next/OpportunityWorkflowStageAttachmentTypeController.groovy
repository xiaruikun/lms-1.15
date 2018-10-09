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
class OpportunityWorkflowStageAttachmentTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityWorkflowStageAttachmentType.list(params), model: [opportunityWorkflowStageAttachmentTypeCount: OpportunityWorkflowStageAttachmentType.count()]
    }

    def show(OpportunityWorkflowStageAttachmentType opportunityWorkflowStageAttachmentType)
    {
        respond opportunityWorkflowStageAttachmentType
    }

    def create()
    {
        respond new OpportunityWorkflowStageAttachmentType(params)
    }

    @Transactional
    def save(OpportunityWorkflowStageAttachmentType opportunityWorkflowStageAttachmentType)
    {
        if (opportunityWorkflowStageAttachmentType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowStageAttachmentType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowStageAttachmentType.errors, view: 'create'
            return
        }

        opportunityWorkflowStageAttachmentType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityWorkflowStageAttachmentType.label', default: 'OpportunityWorkflowStageAttachmentType'), opportunityWorkflowStageAttachmentType.id])
                // redirect opportunityWorkflowStageAttachmentType
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowStageAttachmentType?.stage?.id
            }
            '*' { respond opportunityWorkflowStageAttachmentType, [status: CREATED] }
        }
    }

    def edit(OpportunityWorkflowStageAttachmentType opportunityWorkflowStageAttachmentType)
    {
        respond opportunityWorkflowStageAttachmentType
    }

    @Transactional
    def update(OpportunityWorkflowStageAttachmentType opportunityWorkflowStageAttachmentType)
    {
        if (opportunityWorkflowStageAttachmentType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowStageAttachmentType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowStageAttachmentType.errors, view: 'edit'
            return
        }

        opportunityWorkflowStageAttachmentType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityWorkflowStageAttachmentType.label', default: 'OpportunityWorkflowStageAttachmentType'), opportunityWorkflowStageAttachmentType.id])
                redirect opportunityWorkflowStageAttachmentType
            }
            '*' { respond opportunityWorkflowStageAttachmentType, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityWorkflowStageAttachmentType opportunityWorkflowStageAttachmentType)
    {

        if (opportunityWorkflowStageAttachmentType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityWorkflowStageAttachmentType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityWorkflowStageAttachmentType.label', default: 'OpportunityWorkflowStageAttachmentType'), opportunityWorkflowStageAttachmentType.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityWorkflowStage", action: "show", id: opportunityWorkflowStageAttachmentType?.stage?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityWorkflowStageAttachmentType.label', default: 'OpportunityWorkflowStageAttachmentType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

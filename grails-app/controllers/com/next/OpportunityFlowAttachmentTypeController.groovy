package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class OpportunityFlowAttachmentTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityFlowAttachmentType.list(params), model: [opportunityFlowAttachmentTypeCount: OpportunityFlowAttachmentType.count()]
    }

    def show(OpportunityFlowAttachmentType opportunityFlowAttachmentType)
    {
        respond opportunityFlowAttachmentType
    }

    def create()
    {
        respond new OpportunityFlowAttachmentType(params)
    }

    @Transactional
    def save(OpportunityFlowAttachmentType opportunityFlowAttachmentType)
    {
        if (opportunityFlowAttachmentType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlowAttachmentType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlowAttachmentType.errors, view: 'create'
            return
        }

        opportunityFlowAttachmentType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityFlowAttachmentType.label', default: 'OpportunityFlowAttachmentType'), opportunityFlowAttachmentType.id])
                // redirect opportunityFlowAttachmentType
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityFlowAttachmentType?.stage?.id

            }
            '*' { respond opportunityFlowAttachmentType, [status: CREATED] }
        }
    }

    def edit(OpportunityFlowAttachmentType opportunityFlowAttachmentType)
    {
        respond opportunityFlowAttachmentType
    }

    @Transactional
    def update(OpportunityFlowAttachmentType opportunityFlowAttachmentType)
    {
        if (opportunityFlowAttachmentType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlowAttachmentType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlowAttachmentType.errors, view: 'edit'
            return
        }

        opportunityFlowAttachmentType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityFlowAttachmentType.label', default: 'OpportunityFlowAttachmentType'), opportunityFlowAttachmentType.id])
                redirect opportunityFlowAttachmentType
            }
            '*' { respond opportunityFlowAttachmentType, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityFlowAttachmentType opportunityFlowAttachmentType)
    {

        if (opportunityFlowAttachmentType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityFlowAttachmentType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityFlowAttachmentType.label', default: 'OpportunityFlowAttachmentType'), opportunityFlowAttachmentType.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityFlow", action: "show", method: "GET", id: opportunityFlowAttachmentType?.stage?.id

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityFlowAttachmentType.label', default: 'OpportunityFlowAttachmentType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

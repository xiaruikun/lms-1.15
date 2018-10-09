package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class OpportunityFlexFieldValueController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityFlexFieldValue.list(params), model: [opportunityFlexFieldValueCount: OpportunityFlexFieldValue.count()]
    }

    def show(OpportunityFlexFieldValue opportunityFlexFieldValue)
    {
        respond opportunityFlexFieldValue
    }

    def create()
    {
        respond new OpportunityFlexFieldValue(params)
    }

    @Transactional
    def save(OpportunityFlexFieldValue opportunityFlexFieldValue)
    {
        if (opportunityFlexFieldValue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlexFieldValue.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlexFieldValue.errors, view: 'create'
            return
        }

        opportunityFlexFieldValue.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityFlexFieldValue.label', default: 'OpportunityFlexFieldValue'), opportunityFlexFieldValue.id])
                // redirect opportunityFlexFieldValue
                redirect(controller: "opportunityFlexField", action: "show", params: [id: opportunityFlexFieldValue?.field?.id])

            }
            '*' { respond opportunityFlexFieldValue, [status: CREATED] }
        }
    }

    def edit(OpportunityFlexFieldValue opportunityFlexFieldValue)
    {
        respond opportunityFlexFieldValue
    }

    @Transactional
    def update(OpportunityFlexFieldValue opportunityFlexFieldValue)
    {
        if (opportunityFlexFieldValue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlexFieldValue.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlexFieldValue.errors, view: 'edit'
            return
        }

        opportunityFlexFieldValue.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityFlexFieldValue.label', default: 'OpportunityFlexFieldValue'), opportunityFlexFieldValue.id])
                redirect opportunityFlexFieldValue
            }
            '*' { respond opportunityFlexFieldValue, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityFlexFieldValue opportunityFlexFieldValue)
    {

        if (opportunityFlexFieldValue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityFlexFieldValue.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityFlexFieldValue.label', default: 'OpportunityFlexFieldValue'), opportunityFlexFieldValue.id])
                // redirect action:"index", method:"GET"?
                redirect(controller: "opportunityFlexField", action: "show", params: [id: opportunityFlexFieldValue?.field?.id])

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityFlexFieldValue.label', default: 'OpportunityFlexFieldValue'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class OpportunityTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityType.list(params), model: [opportunityTypeCount: OpportunityType.count()]
    }

    def show(OpportunityType opportunityType)
    {
        respond opportunityType
    }

    def create()
    {
        respond new OpportunityType(params)
    }

    @Transactional
    def save(OpportunityType opportunityType)
    {
        if (opportunityType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityType.errors, view: 'create'
            return
        }

        opportunityType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityType.label', default: 'OpportunityType'), opportunityType.id])
                redirect opportunityType
            }
            '*' { respond opportunityType, [status: CREATED] }
        }
    }

    def edit(OpportunityType opportunityType)
    {
        respond opportunityType
    }

    @Transactional
    def update(OpportunityType opportunityType)
    {
        if (opportunityType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityType.errors, view: 'edit'
            return
        }

        opportunityType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityType.label', default: 'OpportunityType'), opportunityType.id])
                redirect opportunityType
            }
            '*' { respond opportunityType, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityType opportunityType)
    {

        if (opportunityType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityType.label', default: 'OpportunityType'), opportunityType.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityType.label', default: 'OpportunityType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

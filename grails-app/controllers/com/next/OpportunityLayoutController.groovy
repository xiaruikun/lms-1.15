package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class OpportunityLayoutController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityLayout.list(params), model: [opportunityLayoutCount: OpportunityLayout.count()]
    }

    def show(OpportunityLayout opportunityLayout)
    {
        respond opportunityLayout
    }

    def create()
    {
        respond new OpportunityLayout(params)
    }

    @Transactional
    def save(OpportunityLayout opportunityLayout)
    {
        if (opportunityLayout == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityLayout.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityLayout.errors, view: 'create'
            return
        }

        opportunityLayout.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityLayout.label', default: 'OpportunityLayout'), opportunityLayout.id])
                redirect opportunityLayout
            }
            '*' { respond opportunityLayout, [status: CREATED] }
        }
    }

    def edit(OpportunityLayout opportunityLayout)
    {
        respond opportunityLayout
    }

    @Transactional
    def update(OpportunityLayout opportunityLayout)
    {
        if (opportunityLayout == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityLayout.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityLayout.errors, view: 'edit'
            return
        }

        opportunityLayout.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityLayout.label', default: 'OpportunityLayout'), opportunityLayout.id])
                redirect opportunityLayout
            }
            '*' { respond opportunityLayout, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityLayout opportunityLayout)
    {

        if (opportunityLayout == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityLayout.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityLayout.label', default: 'OpportunityLayout'), opportunityLayout.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityLayout.label', default: 'OpportunityLayout'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class OpportunityStageCategoryController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityStageCategory.list(params), model: [opportunityStageCategoryCount: OpportunityStageCategory.count()]
    }

    def show(OpportunityStageCategory opportunityStageCategory)
    {
        respond opportunityStageCategory
    }

    def create()
    {
        respond new OpportunityStageCategory(params)
    }

    @Transactional
    def save(OpportunityStageCategory opportunityStageCategory)
    {
        if (opportunityStageCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityStageCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityStageCategory.errors, view: 'create'
            return
        }

        opportunityStageCategory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityStageCategory.label', default: 'OpportunityStageCategory'), opportunityStageCategory.id])
                redirect opportunityStageCategory
            }
            '*' { respond opportunityStageCategory, [status: CREATED] }
        }
    }

    def edit(OpportunityStageCategory opportunityStageCategory)
    {
        respond opportunityStageCategory
    }

    @Transactional
    def update(OpportunityStageCategory opportunityStageCategory)
    {
        if (opportunityStageCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityStageCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityStageCategory.errors, view: 'edit'
            return
        }

        opportunityStageCategory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityStageCategory.label', default: 'OpportunityStageCategory'), opportunityStageCategory.id])
                redirect opportunityStageCategory
            }
            '*' { respond opportunityStageCategory, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityStageCategory opportunityStageCategory)
    {

        if (opportunityStageCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityStageCategory.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityStageCategory.label', default: 'OpportunityStageCategory'), opportunityStageCategory.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityStageCategory.label', default: 'OpportunityStageCategory'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

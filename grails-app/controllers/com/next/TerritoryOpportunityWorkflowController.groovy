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
class TerritoryOpportunityWorkflowController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryOpportunityWorkflow.list(params), model: [territoryOpportunityWorkflowCount: TerritoryOpportunityWorkflow.count()]
    }

    def show(TerritoryOpportunityWorkflow territoryOpportunityWorkflow)
    {
        respond territoryOpportunityWorkflow
    }

    def create()
    {
        respond new TerritoryOpportunityWorkflow(params)
    }

    @Transactional
    def save(TerritoryOpportunityWorkflow territoryOpportunityWorkflow)
    {
        if (territoryOpportunityWorkflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryOpportunityWorkflow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryOpportunityWorkflow.errors, view: 'create'
            return
        }

        if (territoryOpportunityWorkflow.validate())
        {
            territoryOpportunityWorkflow.save flush: true
        }
        else
        {
            log.info territoryOpportunityWorkflow.errors
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryOpportunityWorkflow.label', default: 'TerritoryOpportunityWorkflow'), territoryOpportunityWorkflow.id])
                redirect territoryOpportunityWorkflow
            }
            '*' { respond territoryOpportunityWorkflow, [status: CREATED] }
        }
    }

    def edit(TerritoryOpportunityWorkflow territoryOpportunityWorkflow)
    {
        respond territoryOpportunityWorkflow
    }

    @Transactional
    def update(TerritoryOpportunityWorkflow territoryOpportunityWorkflow)
    {
        if (territoryOpportunityWorkflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryOpportunityWorkflow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryOpportunityWorkflow.errors, view: 'edit'
            return
        }

        territoryOpportunityWorkflow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryOpportunityWorkflow.label', default: 'TerritoryOpportunityWorkflow'), territoryOpportunityWorkflow.id])
                redirect territoryOpportunityWorkflow
            }
            '*' { respond territoryOpportunityWorkflow, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryOpportunityWorkflow territoryOpportunityWorkflow)
    {

        if (territoryOpportunityWorkflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryOpportunityWorkflow.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryOpportunityWorkflow.label', default: 'TerritoryOpportunityWorkflow'), territoryOpportunityWorkflow.id])
                redirect controller: "territory", action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryOpportunityWorkflow.label', default: 'TerritoryOpportunityWorkflow'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-28
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class TerritoryWorkflowController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryWorkflow.list(params), model: [territoryWorkflowCount: TerritoryWorkflow.count()]
    }

    def show(TerritoryWorkflow territoryWorkflow)
    {
        respond territoryWorkflow
    }

    def create()
    {
        def workflowList = Workflow.findAllByActive(true)
        respond new TerritoryWorkflow(params), model: [workflowList: workflowList]
    }

    @Transactional
    def save(TerritoryWorkflow territoryWorkflow)
    {
        if (territoryWorkflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryWorkflow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryWorkflow.errors, view: 'create'
            return
        }

        territoryWorkflow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryWorkflow.label', default: 'TerritoryWorkflow'), territoryWorkflow.id])
                // redirect territoryWorkflow
                redirect controller: "territory", action: "show", method: "GET", id: territoryWorkflow?.territory.id
            }
            '*' { respond territoryWorkflow, [status: CREATED] }
        }
    }

    def edit(TerritoryWorkflow territoryWorkflow)
    {
        respond territoryWorkflow
    }

    @Transactional
    def update(TerritoryWorkflow territoryWorkflow)
    {
        if (territoryWorkflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryWorkflow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryWorkflow.errors, view: 'edit'
            return
        }

        territoryWorkflow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryWorkflow.label', default: 'TerritoryWorkflow'), territoryWorkflow.id])
                redirect territoryWorkflow
            }
            '*' { respond territoryWorkflow, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryWorkflow territoryWorkflow)
    {

        if (territoryWorkflow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryWorkflow.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryWorkflow.label', default: 'TerritoryWorkflow'), territoryWorkflow.id])
                // redirect action:"index", method:"GET"
                redirect controller: "territory", action: "show", method: "GET", id: territoryWorkflow?.territory.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryWorkflow.label', default: 'TerritoryWorkflow'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

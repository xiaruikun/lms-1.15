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
class TerritoryFlowController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryFlow.list(params), model: [territoryFlowCount: TerritoryFlow.count()]
    }

    def show(TerritoryFlow territoryFlow)
    {
        respond territoryFlow
    }

    def create()
    {
        respond new TerritoryFlow(params)
    }

    @Transactional
    def save(TerritoryFlow territoryFlow)
    {
        if (territoryFlow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryFlow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryFlow.errors, view: 'create'
            return
        }

        def flow = TerritoryFlow.find("from TerritoryFlow where territory.id = ? and stage.id = ?", [territoryFlow?.territory?.id, territoryFlow?.stage?.id])
        if (flow)
        {
            flash.message = message(code: 'territoryFlow.stage.unique')
            respond territoryFlow.errors, view: 'create'
            return
        }
        else
        {
            territoryFlow.save flush: true
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryFlow.label',
                                                                                        default: 'TerritoryFlow'),
                    territoryFlow.id])
                // redirect territoryFlow
                redirect controller: "territory", action: "show", method: "GET", id: territoryFlow.territory.id
            }
            '*' { respond territoryFlow, [status: CREATED] }
        }
    }

    def edit(TerritoryFlow territoryFlow)
    {
        respond territoryFlow
    }

    @Transactional
    def update(TerritoryFlow territoryFlow)
    {
        if (territoryFlow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryFlow.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryFlow.errors, view: 'edit'
            return
        }

        territoryFlow.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryFlow.label',
                                                                                        default: 'TerritoryFlow'),
                    territoryFlow.id])
                redirect territoryFlow
            }
            '*' { respond territoryFlow, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryFlow territoryFlow)
    {

        if (territoryFlow == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryFlow.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryFlow.label',
                                                                                        default: 'TerritoryFlow'),
                    territoryFlow.id])
                // redirect action:"index", method:"GET"
                redirect controller: "territory", action: "show", method: "GET", id: territoryFlow.territory.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryFlow' + '.label', default: 'TerritoryFlow'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

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
class TerritoryFlowConditionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryFlowCondition.list(params), model: [territoryFlowConditionCount: TerritoryFlowCondition
            .count()]
    }

    def show(TerritoryFlowCondition territoryFlowCondition)
    {
        respond territoryFlowCondition
    }

    def create()
    {
        respond new TerritoryFlowCondition(params)
    }

    @Transactional
    def save(TerritoryFlowCondition territoryFlowCondition)
    {
        if (territoryFlowCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryFlowCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryFlowCondition.errors, view: 'create'
            return
        }

        territoryFlowCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryFlowCondition' + '.label', default: 'TerritoryFlowCondition')
                    , territoryFlowCondition.id])
                // redirect territoryFlowCondition
                redirect controller: 'territoryFlow', action: 'show', id: territoryFlowCondition.flow.id
            }
            '*' { respond territoryFlowCondition, [status: CREATED] }
        }
    }

    def edit(TerritoryFlowCondition territoryFlowCondition)
    {
        respond territoryFlowCondition
    }

    @Transactional
    def update(TerritoryFlowCondition territoryFlowCondition)
    {
        if (territoryFlowCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryFlowCondition.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryFlowCondition.errors, view: 'edit'
            return
        }

        territoryFlowCondition.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryFlowCondition' + '.label', default: 'TerritoryFlowCondition')
                    , territoryFlowCondition.id])
                // redirect territoryFlowCondition
                redirect controller: 'territoryFlow', action: 'show', id: territoryFlowCondition.flow.id
            }
            '*' { respond territoryFlowCondition, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryFlowCondition territoryFlowCondition)
    {

        if (territoryFlowCondition == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryFlowCondition.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryFlowCondition' + '.label', default: 'TerritoryFlowCondition')
                    , territoryFlowCondition.id])
                // redirect action: "index", method: "GET"
                redirect controller: 'territoryFlow', action: 'show', id: territoryFlowCondition.flow.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryFlowCondition' + '.label', default: 'TerritoryFlowCondition'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

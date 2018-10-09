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
class TerritoryFlowNextStageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryFlowNextStage.list(params), model: [territoryFlowNextStageCount: TerritoryFlowNextStage.count()]
    }

    def show(TerritoryFlowNextStage territoryFlowNextStage)
    {
        respond territoryFlowNextStage
    }

    def create()
    {
        def flowId = params['flow']
        def flow = TerritoryFlow.findById(flowId)
        // def nextStages = TerritoryFlow.findAllByTerritoryAndExecutionSequenceGreaterThan(flow?.territory, flow?.executionSequence)
        def nextStages = TerritoryFlow.findAllByTerritory(flow?.territory)
        respond new TerritoryFlowNextStage(params), model: [nextStages: nextStages]
    }

    @Transactional
    def save(TerritoryFlowNextStage territoryFlowNextStage)
    {
        if (territoryFlowNextStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        if (territoryFlowNextStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryFlowNextStage.errors, view: 'create'
            return
        }
        territoryFlowNextStage.save flush: true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryFlowNextStage.label', default: 'TerritoryFlowNextStage'), territoryFlowNextStage.id])
                // redirect territoryFlowNextStage
                redirect controller: 'territoryFlow', action: 'show', id: territoryFlowNextStage?.flow?.id
            }
            '*' { respond territoryFlowNextStage, [status: CREATED] }
        }
    }

    def edit(TerritoryFlowNextStage territoryFlowNextStage)
    {
        respond territoryFlowNextStage
    }

    @Transactional
    def update(TerritoryFlowNextStage territoryFlowNextStage)
    {
        if (territoryFlowNextStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryFlowNextStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryFlowNextStage.errors, view: 'edit'
            return
        }

        territoryFlowNextStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryFlowNextStage.label', default: 'TerritoryFlowNextStage'), territoryFlowNextStage.id])
                redirect territoryFlowNextStage
            }
            '*' { respond territoryFlowNextStage, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryFlowNextStage territoryFlowNextStage)
    {

        if (territoryFlowNextStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryFlowNextStage.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryFlowNextStage.label', default: 'TerritoryFlowNextStage'), territoryFlowNextStage.id])
                // redirect action:"index", method:"GET"
                redirect controller: 'territoryFlow', action: 'show', id: territoryFlowNextStage?.flow?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryFlowNextStage.label', default: 'TerritoryFlowNextStage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

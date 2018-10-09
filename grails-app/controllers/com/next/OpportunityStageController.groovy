package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class OpportunityStageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityStage.list(params), model: [opportunityStageCount: OpportunityStage.count()]
    }

    def show(OpportunityStage opportunityStage)
    {
        respond opportunityStage
    }

    def create()
    {
        respond new OpportunityStage(params)
    }

    @Transactional
    def save(OpportunityStage opportunityStage)
    {
        if (opportunityStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityStage.errors, view: 'create'
            return
        }

        opportunityStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityStage' + '.label', default: 'OpportunityStage'),
                    opportunityStage.id])
                redirect opportunityStage
            }
            '*' { respond opportunityStage, [status: CREATED] }
        }
    }

    def edit(OpportunityStage opportunityStage)
    {
        respond opportunityStage
    }

    @Transactional
    def update(OpportunityStage opportunityStage)
    {
        if (opportunityStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityStage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityStage.errors, view: 'edit'
            return
        }

        opportunityStage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityStage' + '.label', default: 'OpportunityStage'),
                    opportunityStage.id])
                redirect opportunityStage
            }
            '*' { respond opportunityStage, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityStage opportunityStage)
    {

        if (opportunityStage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityStage.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityStage' + '.label', default: 'OpportunityStage'),
                    opportunityStage.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityStage' + '.label', default: 'OpportunityStage'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def searchOpportunityStage()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def max = params.max
        def offset = params.offset

        def code = params["code"]
        def name = params['name']
        def description = params['description']

        String sql = "from OpportunityStage where 1 = 1"
        if (code)
        {
            sql += " and code like '%${code}%'"
        }
        if (name)
        {
            sql += " and name like '%${name}%'"
        }
        if (description)
        {
            sql += " and description like '%${description}%'"
        }

        def list = OpportunityStage.findAll(sql, [max: max, offset: offset])
        def opportunityStageList = OpportunityStage.findAll(sql)
        def count = opportunityStageList?.size()

        respond list, model: [opportunityStageCount: count, params: params], view: 'index'
    }
}

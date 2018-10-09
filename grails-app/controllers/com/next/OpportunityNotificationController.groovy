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
class OpportunityNotificationController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityNotification.list(params), model: [opportunityNotificationCount: OpportunityNotification
            .count()]
    }

    def show(OpportunityNotification opportunityNotification)
    {
        respond opportunityNotification
    }

    def create()
    {
        def opportunity = Opportunity.findById(params["opportunity"])
        def userList = []
        def teamList
        if (opportunity)
        {
            teamList = OpportunityTeam.findAllByOpportunity(opportunity)
        }
        else
        {
            teamList = OpportunityTeam.findAll()
        }

        teamList.each {
            userList.add(it?.user)
        }
        def opportunityNotification = new OpportunityNotification(params)
        def opportunityStages
        if (opportunityNotification?.opportunity?.type)
        {
            opportunityStages = opportunityNotification?.opportunity?.type?.stages
        }
        else
        {
            opportunityStages = OpportunityType.findByCode('10')?.stages
        }

        opportunityStages?.each {
            if (!it?.active)
            {
                opportunityStages = opportunityStages?.minus(it)
            }
        }

        respond opportunityNotification, model: [userList: userList, opportunityStages: opportunityStages]
    }

    @Transactional
    def save(OpportunityNotification opportunityNotification)
    {
        if (opportunityNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityNotification.errors, view: 'create'
            return
        }

        opportunityNotification.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityNotification' + '.label', default: 'OpportunityNotification'), opportunityNotification.id])
                // redirect opportunityNotification
                redirect controller: "opportunity", action: "show", method: "GET", id: opportunityNotification
                    .opportunity.id
            }
            '*' { respond opportunityNotification, [status: CREATED] }
        }
    }

    def edit(OpportunityNotification opportunityNotification)
    {
        respond opportunityNotification
    }

    @Transactional
    def update(OpportunityNotification opportunityNotification)
    {
        if (opportunityNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityNotification.errors, view: 'edit'
            return
        }

        opportunityNotification.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityNotification' + '.label', default: 'OpportunityNotification'), opportunityNotification.id])
                redirect opportunityNotification
            }
            '*' { respond opportunityNotification, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityNotification opportunityNotification)
    {

        if (opportunityNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityNotification.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityNotification' + '.label', default: 'OpportunityNotification'), opportunityNotification.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunity", action: "show", method: "GET", id: opportunityNotification
                    .opportunity.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityNotification.label', default: 'OpportunityNotification'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

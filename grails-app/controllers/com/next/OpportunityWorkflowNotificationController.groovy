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
class OpportunityWorkflowNotificationController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityWorkflowNotification.list(params), model: [opportunityWorkflowNotificationCount: OpportunityWorkflowNotification.count()]
    }

    def show(OpportunityWorkflowNotification opportunityWorkflowNotification)
    {
        respond opportunityWorkflowNotification
    }

    def create()
    {
        def workflow = OpportunityWorkflow.findById(params["workflow"])
        def territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAllByWorkflow(workflow)
        def territory
        def userList = []
        def teamList

        territoryOpportunityWorkflows?.each {
            territory = it?.territory
            teamList = TerritoryTeam.findAllByTerritory(territory)
            teamList.each {
                if (!userList.contains(it?.user))
                {
                    userList.add(it?.user)
                }
            }
            while (territory)
            {
                if (territory?.inheritTeam && territory?.parent)
                {
                    teamList = TerritoryTeam.findAllByTerritory(territory.parent)
                    teamList.each {
                        if (!userList.contains(it?.user))
                        {
                            userList.add(it?.user)
                        }
                    }
                    territory = territory?.parent
                }
                else
                {
                    break
                }
            }
        }
        def opportunityWorkflowNotification = new OpportunityWorkflowNotification(params)
        def opportunityStages = opportunityWorkflowNotification?.workflow?.opportunityType?.stages
        opportunityStages?.each {
            if (!it?.active)
            {
                opportunityStages = opportunityStages?.minus(it)
            }
        }

        respond opportunityWorkflowNotification, model: [userList: userList, opportunityStages: opportunityStages]

    }

    @Transactional
    def save(OpportunityWorkflowNotification opportunityWorkflowNotification)
    {
        if (opportunityWorkflowNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowNotification.errors, view: 'create'
            return
        }

        opportunityWorkflowNotification.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityWorkflowNotification.label', default: 'OpportunityWorkflowNotification'), opportunityWorkflowNotification.id])
                // redirect opportunityWorkflowNotification
                redirect controller: "opportunityWorkflow", action: "show", id: opportunityWorkflowNotification?.workflow?.id
            }
            '*' { respond opportunityWorkflowNotification, [status: CREATED] }
        }
    }

    def edit(OpportunityWorkflowNotification opportunityWorkflowNotification)
    {
        respond opportunityWorkflowNotification
    }

    @Transactional
    def update(OpportunityWorkflowNotification opportunityWorkflowNotification)
    {
        if (opportunityWorkflowNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowNotification.errors, view: 'edit'
            return
        }

        opportunityWorkflowNotification.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityWorkflowNotification.label', default: 'OpportunityWorkflowNotification'), opportunityWorkflowNotification.id])
                redirect opportunityWorkflowNotification
            }
            '*' { respond opportunityWorkflowNotification, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityWorkflowNotification opportunityWorkflowNotification)
    {

        if (opportunityWorkflowNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityWorkflowNotification.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityWorkflowNotification.label', default: 'OpportunityWorkflowNotification'), opportunityWorkflowNotification.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityWorkflow", action: "show", id: opportunityWorkflowNotification?.workflow?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityWorkflowNotification.label', default: 'OpportunityWorkflowNotification'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

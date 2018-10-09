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
class OpportunityWorkflowRoleController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityWorkflowRole.list(params), model: [opportunityWorkflowRoleCount: OpportunityWorkflowRole.count()]
    }

    def show(OpportunityWorkflowRole opportunityWorkflowRole)
    {
        respond opportunityWorkflowRole
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

        def opportunityWorkflowRole = new OpportunityWorkflowRole(params)
        def opportunityStages = opportunityWorkflowRole?.workflow?.opportunityType?.stages
        opportunityStages?.each {
            if (!it?.active)
            {
                opportunityStages = opportunityStages?.minus(it)
            }
        }

        respond opportunityWorkflowRole, model: [userList: userList, opportunityStages: opportunityStages]
    }

    @Transactional
    def save(OpportunityWorkflowRole opportunityWorkflowRole)
    {
        if (opportunityWorkflowRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowRole.hasErrors())
        {
            transactionStatus.setRollbackOnly()

            log.info opportunityWorkflowRole.errors

            respond opportunityWorkflowRole.errors, view: 'create'
            return
        }

        opportunityWorkflowRole.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityWorkflowRole.label', default: 'OpportunityWorkflowRole'), opportunityWorkflowRole.id])
                // redirect opportunityWorkflowRole
                redirect controller: "opportunityWorkflow", action: "show", id: opportunityWorkflowRole?.workflow?.id
            }
            '*' { respond opportunityWorkflowRole, [status: CREATED] }
        }
    }

    def edit(OpportunityWorkflowRole opportunityWorkflowRole)
    {
        respond opportunityWorkflowRole
    }

    @Transactional
    def update(OpportunityWorkflowRole opportunityWorkflowRole)
    {
        if (opportunityWorkflowRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityWorkflowRole.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityWorkflowRole.errors, view: 'edit'
            return
        }

        opportunityWorkflowRole.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityWorkflowRole.label', default: 'OpportunityWorkflowRole'), opportunityWorkflowRole.id])
                // redirect opportunityWorkflowRole
                redirect controller: "opportunityWorkflow", action: "show", id: opportunityWorkflowRole?.workflow?.id

            }
            '*' { respond opportunityWorkflowRole, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityWorkflowRole opportunityWorkflowRole)
    {

        if (opportunityWorkflowRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityWorkflowRole.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityWorkflowRole.label', default: 'OpportunityWorkflowRole'), opportunityWorkflowRole.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunityWorkflow", action: "show", id: opportunityWorkflowRole?.workflow?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityWorkflowRole.label', default: 'OpportunityWorkflowRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

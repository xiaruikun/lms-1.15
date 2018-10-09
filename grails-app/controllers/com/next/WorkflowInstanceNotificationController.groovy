package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-25
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class WorkflowInstanceNotificationController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond WorkflowInstanceNotification.list(params), model: [workflowInstanceNotificationCount: WorkflowInstanceNotification.count()]
    }

    def show(WorkflowInstanceNotification workflowInstanceNotification)
    {
        respond workflowInstanceNotification
    }

    def create()
    {
        def userList = []
        def stage = WorkflowInstanceStage.findById(params['stage'])
        def opportunity = stage?.instance?.opportunity
        def opportunityTeamList = OpportunityTeam.findAllByOpportunity(opportunity)
        opportunityTeamList?.each {
            userList += it?.user
        }
        respond new WorkflowInstanceNotification(params), model: [userList: userList]
    }

    @Transactional
    def save(WorkflowInstanceNotification workflowInstanceNotification)
    {
        if (workflowInstanceNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceNotification.errors, view: 'create'
            return
        }

        workflowInstanceNotification.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowInstanceNotification.label', default: 'WorkflowInstanceNotification'), workflowInstanceNotification.id])
                //redirect workflowInstanceNotification
                redirect controller: "workflowInstanceStage", action: "show", method: "GET", id: workflowInstanceNotification.stage.id
            }
            '*' { respond workflowInstanceNotification, [status: CREATED] }
        }
    }

    def edit(WorkflowInstanceNotification workflowInstanceNotification)
    {
        respond workflowInstanceNotification
    }

    @Transactional
    def update(WorkflowInstanceNotification workflowInstanceNotification)
    {
        if (workflowInstanceNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceNotification.errors, view: 'edit'
            return
        }

        workflowInstanceNotification.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowInstanceNotification.label', default: 'WorkflowInstanceNotification'), workflowInstanceNotification.id])
                redirect workflowInstanceNotification
            }
            '*' { respond workflowInstanceNotification, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowInstanceNotification workflowInstanceNotification)
    {

        if (workflowInstanceNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowInstanceNotification.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowInstanceNotification.label', default: 'WorkflowInstanceNotification'), workflowInstanceNotification.id])
                //redirect action:"index", method:"GET"
                redirect controller: "workflowInstanceStage", action: "show", method: "GET", id: workflowInstanceNotification.stage.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowInstanceNotification.label', default: 'WorkflowInstanceNotification'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-28
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class WorkflowUserController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond WorkflowUser.list(params), model: [workflowUserCount: WorkflowUser.count()]
    }

    def show(WorkflowUser workflowUser)
    {
        respond workflowUser
    }

    def create()
    {
        respond new WorkflowUser(params)
    }

    @Transactional
    def save(WorkflowUser workflowUser)
    {
        if (workflowUser == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowUser.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowUser.errors, view: 'create'
            return
        }

        workflowUser.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowUser.label', default: 'WorkflowUser'), workflowUser.id])
                // redirect workflowUser
                redirect controller: "workflowStage", action: "show", id: workflowUser?.stage?.id
            }
            '*' { respond workflowUser, [status: CREATED] }
        }
    }

    def edit(WorkflowUser workflowUser)
    {
        respond workflowUser
    }

    @Transactional
    def update(WorkflowUser workflowUser)
    {
        if (workflowUser == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowUser.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowUser.errors, view: 'edit'
            return
        }

        workflowUser.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowUser.label', default: 'WorkflowUser'), workflowUser.id])
                // redirect workflowUser
                redirect controller: "workflowStage", action: "show", id: workflowUser?.stage?.id
            }
            '*' { respond workflowUser, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowUser workflowUser)
    {

        if (workflowUser == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowUser.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowUser.label', default: 'WorkflowUser'), workflowUser.id])
                // redirect action:"index", method:"GET"
                redirect controller: "workflowStage", action: "show", id: workflowUser?.stage?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowUser.label', default: 'WorkflowUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

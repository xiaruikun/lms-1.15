package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-21
 * @modifiedDate 2017-4-27
 */
@Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class WorkflowInstanceUserController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        max = 10
        def offset = params.offset
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def status = params['status']
        def sql
        def workflowInstanceUserList
        def list
        def count
        if (!status)
        {
            sql = "select stage.instance.opportunity.id, stage.instance.opportunity.serialNumber, stage.instance.name, stage.instance.stage.name, stage.instance.status.name, stage.instance.createdDate, stage.instance.modifiedDate from WorkflowInstanceUser where user.id = ${user?.id} and stage.id = stage.instance.stage.id and stage.instance.status.name = 'Pending'"
        }
        else if (status == 'Already')
        {
            sql = "select stage.instance.opportunity.id, stage.instance.opportunity.serialNumber, stage.instance.name, stage.instance.stage.name, stage.instance.status.name, stage.instance.createdDate, stage.instance.modifiedDate from WorkflowInstanceUser where user.id = ${user?.id} and stage.executionSequence < stage.instance.stage.executionSequence group by stage.instance.name"
        }
        workflowInstanceUserList = WorkflowInstanceUser.executeQuery(sql, [max: max, offset: offset])
        list = WorkflowInstanceUser.executeQuery(sql)
        count = list?.size()

        respond workflowInstanceUserList, model: [workflowInstanceUserList: workflowInstanceUserList, workflowInstanceUserCount: count]
    }

    def show(WorkflowInstanceUser workflowInstanceUser)
    {
        respond workflowInstanceUser
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
        respond new WorkflowInstanceUser(params), model: [userList: userList]
    }

    @Transactional
    def save(WorkflowInstanceUser workflowInstanceUser)
    {
        if (workflowInstanceUser == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceUser.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceUser.errors, view: 'create'
            return
        }

        workflowInstanceUser.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowInstanceUser.label', default: 'WorkflowInstanceUser'), workflowInstanceUser.id])
                //redirect workflowInstanceUser
                redirect controller: "workflowInstanceStage", action: "show", method: "GET", id: workflowInstanceUser.stage.id

            }
            '*' { respond workflowInstanceUser, [status: CREATED] }
        }
    }

    def edit(WorkflowInstanceUser workflowInstanceUser)
    {
        respond workflowInstanceUser
    }

    @Transactional
    def update(WorkflowInstanceUser workflowInstanceUser)
    {
        if (workflowInstanceUser == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstanceUser.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstanceUser.errors, view: 'edit'
            return
        }

        workflowInstanceUser.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowInstanceUser.label', default: 'WorkflowInstanceUser'), workflowInstanceUser.id])
                redirect workflowInstanceUser
            }
            '*' { respond workflowInstanceUser, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowInstanceUser workflowInstanceUser)
    {

        if (workflowInstanceUser == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowInstanceUser.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowInstanceUser.label', default: 'WorkflowInstanceUser'), workflowInstanceUser.id])
                //redirect action:"index", method:"GET"
                redirect controller: "workflowInstanceStage", action: "show", method: "GET", id: workflowInstanceUser.stage.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowInstanceUser.label', default: 'WorkflowInstanceUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

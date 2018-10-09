package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-26
 */
@Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class WorkflowInstanceController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def workflowInstanceService
    def springSecurityService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        max = 10
        def offset = params.offset
        def opportunity = Opportunity.findById(params['opportunity'])
        def workflowInstanceList
        if (opportunity)
        {
            workflowInstanceList = WorkflowInstance.findAll("from WorkflowInstance where opportunity.id = ${opportunity?.id}", [max: max, offset: offset])
        }
        else
        {
            workflowInstanceList = WorkflowInstance.findAll("from WorkflowInstance", [max: max, offset: offset])
        }
        def count = workflowInstanceList?.size()
        respond workflowInstanceList, model: [opportunity: opportunity, workflowInstanceCount: count]
    }

    def show(WorkflowInstance workflowInstance)
    {
        def workflowInstanceStages = WorkflowInstanceStage.findAll("from WorkflowInstanceStage where instance.id = ${workflowInstance?.id} order by executionSequence asc")
        respond workflowInstance, model: [workflowInstanceStages: workflowInstanceStages]
    }

    def create()
    {
        def opportunity = Opportunity.findById(params['opportunity'])
        def territory
        if (opportunity?.territory)
        {
            territory = opportunity?.territory
        }
        else
        {
            territory = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])?.territory
        }
        def territoryWorkflowList = TerritoryWorkflow.findAllByTerritory(territory)
        def workflowList = []
        territoryWorkflowList?.each {
            if (it?.workflow?.active)
            {
                workflowList += it?.workflow
            }
        }
        respond new WorkflowInstance(params), model: [workflowList: workflowList]
    }

    @Transactional
    def save(WorkflowInstance workflowInstance)
    {
        if (workflowInstance == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstance.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstance.errors, view: 'create'
            return
        }

        workflowInstance.save flush: true

        workflowInstanceService.initWorkflowInstance workflowInstance

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workflowInstance.label', default: 'WorkflowInstance'), workflowInstance.id])
                redirect workflowInstance
            }
            '*' { respond workflowInstance, [status: CREATED] }
        }
    }

    def edit(WorkflowInstance workflowInstance)
    {
        respond workflowInstance
    }

    @Transactional
    def update(WorkflowInstance workflowInstance)
    {
        if (workflowInstance == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (workflowInstance.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond workflowInstance.errors, view: 'edit'
            return
        }

        workflowInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workflowInstance.label', default: 'WorkflowInstance'), workflowInstance.id])
                redirect workflowInstance
            }
            '*' { respond workflowInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkflowInstance workflowInstance)
    {

        if (workflowInstance == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        workflowInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workflowInstance.label', default: 'WorkflowInstance'), workflowInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workflowInstance.label', default: 'WorkflowInstance'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def approve(WorkflowInstance workflowInstance)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        def stage = workflowInstance?.stage
        def map = [:]

        def authority = WorkflowAuthority.findByName("Approval")
        def workflowInstanceUser = WorkflowInstanceUser.findByStageAndUserAndAuthority(stage, user, authority)
        if (workflowInstanceUser)
        {
            map = workflowInstanceService.approve(workflowInstance)
            if (map['flag'])
            {
                stage = workflowInstance?.stage
                def operator = ""
                def workflowInstanceUsers = WorkflowInstanceUser.findAllByStage(stage)
                workflowInstanceUsers?.each {
                    operator += it?.user?.toString() + ","
                }
                if (workflowInstanceUsers?.size() > 0)
                {
                    operator = operator[0..operator.length() - 2]
                }
                flash.message = "已提交至${workflowInstance?.stage?.name}(${operator})"
                redirect(controller: "opportunity", action: "show", params: [id: workflowInstance?.opportunity?.id])
                return
            }
            else
            {
                def message = map['message']
                if (message)
                {
                    flash.message = map['message']
                }
                else
                {
                    flash.message = "校验失败"
                }
                redirect(controller: "opportunity", action: "show", params: [id: workflowInstance?.opportunity?.id])
                return
            }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: workflowInstance?.opportunity?.id])
            return
        }
    }

    @Transactional
    def reject(WorkflowInstance workflowInstance)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def stage = workflowInstance?.stage
        def authority = WorkflowAuthority.findByName("Approval")
        def workflowInstanceUser = WorkflowInstanceUser.findByStageAndUserAndAuthority(stage, user, authority)
        def flag

        if (workflowInstanceUser)
        {
            flag = workflowInstanceService.reject(workflowInstance)
            if (flag)
            {
                flash.message = "订单回退操作成功！"
            }
            else
            {
                flash.message = message(code: 'approval.operation.denied')
            }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
        }
        redirect(controller: "opportunity", action: "show", params: [id: workflowInstance?.opportunity?.id])
        return
    }

    @Transactional
    def cancel(WorkflowInstance workflowInstance)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def stage = workflowInstance?.stage
        def authority = WorkflowAuthority.findByName("Approval")
        def workflowInstanceUser = WorkflowInstanceUser.findByStageAndUserAndAuthority(stage, user, authority)
        def flag
        if (workflowInstanceUser)
        {
            flag = workflowInstanceService.cancel(workflowInstance)
            if (flag)
            {
                flash.message = "订单失败操作成功！"
            }
            else
            {
                flash.message = message(code: 'approval.operation.denied')
            }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
        }
        redirect(controller: "opportunity", action: "show", params: [id: workflowInstance?.opportunity?.id])
        return
    }

    @Transactional
    def complete(WorkflowInstance workflowInstance)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def stage = workflowInstance?.stage
        def authority = WorkflowAuthority.findByName("Approval")
        def workflowInstanceUser = WorkflowInstanceUser.findByStageAndUserAndAuthority(stage, user, authority)
        def flag
        if (workflowInstanceUser)
        {
            flag = workflowInstanceService.complete(workflowInstance)
            if (flag)
            {
                flash.message = "工作流结束操作成功！"
            }
            else
            {
                flash.message = message(code: 'approval.operation.denied')
            }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
        }
        redirect(controller: "opportunity", action: "show", params: [id: workflowInstance?.opportunity?.id])
        return
    }
}

package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class OpportunityContactController
{
    def contactService
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityContact.list(params), model: [opportunityContactCount: OpportunityContact.count()]
    }

    def show(OpportunityContact opportunityContact)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunityContact?.opportunity, opportunityContact?.opportunity?.stage)
        def loanApproveStage = OpportunityStage.findByCode("08")
        def loanApproveFlow = OpportunityFlow.findByOpportunityAndStage(opportunityContact?.opportunity, loanApproveStage)
        def canEdit = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            canEdit = true
        }
        else if (currentFlow?.executionSequence < loanApproveFlow?.executionSequence)
        {
            canEdit = true
        }
        respond opportunityContact, model: [canEdit: canEdit]
    }

    def create()
    {
        def opportunity = Opportunity.findById(params['opportunity'])
        def connectedContactList = []
        def opportunityContactList = OpportunityContact.findAllByOpportunity(opportunity)
        opportunityContactList?.each {
            connectedContactList.add(it?.contact)
        }
        respond new OpportunityContact(params), model: [connectedContactList: connectedContactList]
    }

    @Transactional
    def save(OpportunityContact opportunityContact)
    {
        if (opportunityContact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityContact.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityContact.errors, view: 'create'
            return
        }

        def opportunity = opportunityContact?.opportunity
        def type = OpportunityContactType.findByName("借款人")
        if (opportunityContact.type == type)
        {
            if (opportunityContact?.contact?.idNumber)
            {
                Boolean flag = contactService.verifyIdNumber(opportunityContact?.contact?.idNumber)
                if (flag)
                {
                    opportunityContact.contact.type = 'Client'
                    opportunityContact.contact.userCode = opportunity?.user?.code
                    opportunityContact.save flush: true
                    opportunity.addToContacts(opportunityContact)
                    opportunity.save flush: true

                    opportunity.fullName = opportunityContact?.contact?.fullName
                    opportunity.cellphone = opportunityContact?.contact?.cellphone
                    opportunity.idNumber = opportunityContact?.contact?.idNumber
                    opportunity.maritalStatus = opportunityContact?.contact?.maritalStatus
                    opportunity.lender = opportunityContact?.contact
                    opportunity.save flush: true

                    request.withFormat {
                        form multipartForm {
                            flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityContact.label', default: 'OpportunityContact'), opportunityContact.id])
                            // redirect opportunityContact
                            redirect(controller: "opportunity", action: "show", params: [id: opportunityContact.opportunity.id])
                        }
                        '*' { respond opportunityContact, [status: CREATED] }
                    }
                }
                else
                {
                    flash.message = "身份证号校验不通过"
                    respond opportunityContact, view: 'create'
                    return
                }
            }
            else
            {
                flash.message = "借款人身份证号不能为空"
                respond opportunityContact, view: 'create'
                return
            }
        }
        else
        {
            opportunityContact.save flush: true
            opportunity.addToContacts(opportunityContact)
            opportunity.save flush: true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityContact.label', default: 'OpportunityContact'), opportunityContact.id])
                    // redirect opportunityContact
                    redirect(controller: "opportunity", action: "show", params: [id: opportunityContact.opportunity.id])
                }
                '*' { respond opportunityContact, [status: CREATED] }
            }
        }

    }

    def edit(OpportunityContact opportunityContact)
    {
        def opportunity = opportunityContact?.opportunity
        def connectedContactList = []
        def opportunityContactList = OpportunityContact.findAllByOpportunity(opportunity)
        opportunityContactList?.each {
            connectedContactList.add(it?.contact)
        }
        respond opportunityContact, model: [connectedContactList: connectedContactList]
    }

    @Transactional
    def update(OpportunityContact opportunityContact)
    {
        if (opportunityContact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityContact.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityContact.errors, view: 'edit'
            return
        }
        def opportunity = opportunityContact?.opportunity
        def type = OpportunityContactType.findByName("借款人")
        if (opportunityContact.type == type)
        {
            if (opportunityContact?.contact?.idNumber)
            {
                Boolean flag = contactService.verifyIdNumber(opportunityContact?.contact?.idNumber)
                if (flag)
                {
                    if (opportunityContact?.validate())
                    {
                        println "save ok"
                        opportunityContact.save flush: true
                    }
                    else
                    {
                        println opportunityContact.errors
                    }

                    opportunity.addToContacts(opportunityContact)
                    opportunity.save flush: true

                    opportunity.fullName = opportunityContact?.contact?.fullName
                    opportunity.cellphone = opportunityContact?.contact?.cellphone
                    opportunity.idNumber = opportunityContact?.contact?.idNumber
                    opportunity.maritalStatus = opportunityContact?.contact?.maritalStatus
                    opportunity.save flush: true

                    request.withFormat {
                        form multipartForm {
                            flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityContact.label', default: 'OpportunityContact'), opportunityContact.id])
                            // redirect opportunityContact
                            redirect(controller: "opportunity", action: "show", params: [id: opportunityContact.opportunity.id])
                        }
                        '*' { respond opportunityContact, [status: OK] }
                    }
                }
                else
                {
                    flash.message = "身份证号校验不通过"
                    respond opportunityContact, view: 'edit'
                    return
                }
            }
            else
            {
                flash.message = "借款人身份证号不能为空"
                respond opportunityContact, view: 'edit'
                return
            }
        }
        else
        {
            opportunityContact.save flush: true
            opportunity.addToContacts(opportunityContact)
            opportunity.save flush: true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityContact.label', default: 'OpportunityContact'), opportunityContact.id])
                    // redirect opportunityContact
                    redirect(controller: "opportunity", action: "show", params: [id: opportunityContact.opportunity.id])
                }
                '*' { respond opportunityContact, [status: OK] }
            }
        }
    }

    @Transactional
    def ajaxUpdate(OpportunityContact opportunityContact)
    {
        if (opportunityContact.validate())
        {
            opportunityContact.save flush: true
            render([status: "success"] as JSON)
            return
        }
        else
        {
            render([status: "fail", errorMessage: opportunityContact.errors] as JSON)
            return
        }
    }

    @Transactional
    def delete(OpportunityContact opportunityContact)
    {

        if (opportunityContact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityContact.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityContact.label', default: 'OpportunityContact'), opportunityContact.id])
                // redirect action: "index", method: "GET"
                redirect(controller: "opportunity", action: "show", params: [id: opportunityContact.opportunity.id])
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityContact.label', default: 'OpportunityContact'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def create2(OpportunityContact opportunityContact)
    {
        respond opportunityContact
    }
}

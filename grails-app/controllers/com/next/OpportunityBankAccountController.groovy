package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class OpportunityBankAccountController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityBankAccount.list(params), model: [opportunityBankAccountCount: OpportunityBankAccount.count()]
    }

    def show(OpportunityBankAccount opportunityBankAccount)
    {
        respond opportunityBankAccount
    }

    def create()
    {
        respond new OpportunityBankAccount(params)
    }

    @Transactional
    def save(OpportunityBankAccount opportunityBankAccount)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        opportunityBankAccount.bankAccount.createdBy = user

        if (opportunityBankAccount == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityBankAccount.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityBankAccount.errors, view: 'create'
            return
        }

        if (opportunityBankAccount?.bankAccount?.validate())
        {

            if (opportunityBankAccount.validate())
            {
                opportunityBankAccount.save flush: true
            }
            else
            {
                log.info opportunityBankAccount.errors
            }

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityBankAccount.label', default: 'OpportunityBankAccount'), opportunityBankAccount.id])
                    // redirect opportunityBankAccount
                    redirect(controller: "opportunity", action: "show", params: [id: opportunityBankAccount.opportunity.id])
                }
                '*' { respond opportunityBankAccount, [status: CREATED] }
            }
        }
        else
        {
            transactionStatus.setRollbackOnly()
            flash.message = opportunityBankAccount?.bankAccount.errors
            respond opportunityBankAccount, view: 'create'
            return
        }
    }

    def edit(OpportunityBankAccount opportunityBankAccount)
    {
        respond opportunityBankAccount
    }

    @Transactional
    def update(OpportunityBankAccount opportunityBankAccount)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        opportunityBankAccount.bankAccount.modifiedBy = user

        if (opportunityBankAccount == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityBankAccount.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityBankAccount.errors, view: 'edit'
            return
        }

        if (opportunityBankAccount?.bankAccount?.validate())
        {

            opportunityBankAccount.save flush: true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityBankAccount.label', default: 'OpportunityBankAccount'), opportunityBankAccount.id])
                    // redirect opportunityBankAccount
                    redirect(controller: "opportunity", action: "show", params: [id: opportunityBankAccount.opportunity.id])
                }
                '*' { respond opportunityBankAccount, [status: OK] }
            }
        }
        else
        {
            transactionStatus.setRollbackOnly()
            flash.message = opportunityBankAccount?.bankAccount.errors
            respond opportunityBankAccount, view: 'edit'
            return
        }
    }

    @Transactional
    def delete(OpportunityBankAccount opportunityBankAccount)
    {

        if (opportunityBankAccount == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityBankAccount.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityBankAccount.label', default: 'OpportunityBankAccount'), opportunityBankAccount.id])
                // redirect action:"index", method:"GET"
                redirect(controller: "opportunity", action: "show", params: [id: opportunityBankAccount.opportunity.id])
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityBankAccount.label', default: 'OpportunityBankAccount'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

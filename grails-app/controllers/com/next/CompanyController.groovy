package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])


@Transactional(readOnly = true)
class CompanyController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Company.list(params), model: [companyCount: Company.count()]
    }

    def show(Company company)
    {
        respond company
    }

    def create()
    {
        respond new Company(params)
    }

    @Transactional
    def save(Company company)
    {
        if (company == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (company.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond company.errors, view: 'create'
            return
        }

        company.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'company.label', default: 'Company'), company.id])
                redirect controller: "contact", action: "show", method: "GET", id: company?.contact?.id
            }
            '*' { respond company, [status: CREATED] }
        }
    }

    def edit(Company company)
    {
        respond company
    }

    @Transactional
    def update(Company company)
    {
        if (company == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (company.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond company.errors, view: 'edit'
            return
        }

        company.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'company.label', default: 'Company'), company.id])
                redirect controller: "contact", action: "show", method: "GET", id: company?.contact?.id
            }
            '*' { respond company, [status: OK] }
        }
    }

    @Transactional
    def delete(Company company)
    {

        if (company == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        company.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'company.label', default: 'Company'), company.id])
                redirect controller: "contact", action: "show", method: "GET", id: company?.contact?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

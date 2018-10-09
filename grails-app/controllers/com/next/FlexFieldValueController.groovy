package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
@Transactional(readOnly = true)
class FlexFieldValueController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond FlexFieldValue.list(params), model: [flexFieldValueCount: FlexFieldValue.count()]
    }

    def show(FlexFieldValue flexFieldValue)
    {
        respond flexFieldValue
    }

    def create()
    {
        respond new FlexFieldValue(params)
    }

    @Transactional
    def save(FlexFieldValue flexFieldValue)
    {
        if (flexFieldValue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (flexFieldValue.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond flexFieldValue.errors, view: 'create'
            return
        }

        flexFieldValue.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'flexFieldValue.label', default: 'FlexFieldValue'), flexFieldValue.id])
                redirect(controller: "flexField", action: "show", id: flexFieldValue?.field?.id)
            }
            '*' { respond flexFieldValue, [status: CREATED] }
        }
    }

    def edit(FlexFieldValue flexFieldValue)
    {
        respond flexFieldValue
    }

    @Transactional
    def update(FlexFieldValue flexFieldValue)
    {
        if (flexFieldValue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (flexFieldValue.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond flexFieldValue.errors, view: 'edit'
            return
        }

        flexFieldValue.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'flexFieldValue.label', default: 'FlexFieldValue'), flexFieldValue.id])
                redirect flexFieldValue
            }
            '*' { respond flexFieldValue, [status: OK] }
        }
    }

    @Transactional
    def delete(FlexFieldValue flexFieldValue)
    {

        if (flexFieldValue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        flexFieldValue.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'flexFieldValue.label', default: 'FlexFieldValue'), flexFieldValue.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'flexFieldValue.label', default: 'FlexFieldValue'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

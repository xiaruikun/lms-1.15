package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
@Transactional(readOnly = true)
class FlexFieldController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond FlexField.list(params), model: [flexFieldCount: FlexField.count()]
    }

    def show(FlexField flexField)
    {
        respond flexField
    }

    def create()
    {
        respond new FlexField(params)
    }

    @Transactional
    def save(FlexField flexField)
    {
        if (flexField == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (flexField.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond flexField.errors, view: 'create'
            return
        }

        flexField.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'flexField.label', default: 'FlexField'), flexField.id])
                // redirect flexField
                redirect(controller: "flexFieldCategory", action: "show", params: [id: flexField?.category?.id])
            }
            '*' { respond flexField, [status: CREATED] }
        }
    }

    def edit(FlexField flexField)
    {
        respond flexField
    }

    @Transactional
    def update(FlexField flexField)
    {
        if (flexField == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (flexField.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond flexField.errors, view: 'edit'
            return
        }

        flexField.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'flexField.label', default: 'FlexField'), flexField.id])
                redirect flexField
            }
            '*' { respond flexField, [status: OK] }
        }
    }

    @Transactional
    def delete(FlexField flexField)
    {

        if (flexField == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        flexField.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'flexField.label', default: 'FlexField'), flexField.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'flexField.label', default: 'FlexField'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

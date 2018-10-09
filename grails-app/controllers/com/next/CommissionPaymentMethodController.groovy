package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class CommissionPaymentMethodController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond CommissionPaymentMethod.list(params), model: [commissionPaymentMethodCount: CommissionPaymentMethod
            .count()]
    }

    def show(CommissionPaymentMethod commissionPaymentMethod)
    {
        respond commissionPaymentMethod
    }

    def create()
    {
        respond new CommissionPaymentMethod(params)
    }

    @Transactional
    def save(CommissionPaymentMethod commissionPaymentMethod)
    {
        if (commissionPaymentMethod == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (commissionPaymentMethod.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond commissionPaymentMethod.errors, view: 'create'
            return
        }

        commissionPaymentMethod.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'commissionPaymentMethod' + '.label', default: 'CommissionPaymentMethod'), commissionPaymentMethod.id])
                redirect commissionPaymentMethod
            }
            '*' { respond commissionPaymentMethod, [status: CREATED] }
        }
    }

    def edit(CommissionPaymentMethod commissionPaymentMethod)
    {
        respond commissionPaymentMethod
    }

    @Transactional
    def update(CommissionPaymentMethod commissionPaymentMethod)
    {
        if (commissionPaymentMethod == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (commissionPaymentMethod.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond commissionPaymentMethod.errors, view: 'edit'
            return
        }

        commissionPaymentMethod.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'commissionPaymentMethod' + '.label', default: 'CommissionPaymentMethod'), commissionPaymentMethod.id])
                redirect commissionPaymentMethod
            }
            '*' { respond commissionPaymentMethod, [status: OK] }
        }
    }

    @Transactional
    def delete(CommissionPaymentMethod commissionPaymentMethod)
    {

        if (commissionPaymentMethod == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        commissionPaymentMethod.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'commissionPaymentMethod' + '.label', default: 'CommissionPaymentMethod'), commissionPaymentMethod.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'commissionPaymentMethod.label', default: 'CommissionPaymentMethod'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

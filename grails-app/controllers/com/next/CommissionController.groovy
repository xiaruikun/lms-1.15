package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class CommissionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Commission.list(params), model: [commissionCount: Commission.count()]
    }

    def show(Commission commission)
    {
        respond commission
    }

    def create()
    {
        respond new Commission(params)
    }

    @Transactional
    def save(Commission commission)
    {
        if (commission == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (commission.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond commission.errors, view: 'create'
            return
        }

        commission.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'commission.label',
                                                                                        default: 'Commission'),
                    commission.id])
                redirect commission
            }
            '*' { respond commission, [status: CREATED] }
        }
    }

    def edit(Commission commission)
    {
        respond commission
    }

    @Transactional
    def update(Commission commission)
    {
        if (commission == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (commission.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond commission.errors, view: 'edit'
            return
        }

        commission.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'commission.label',
                                                                                        default: 'Commission'),
                    commission.id])
                redirect commission
            }
            '*' { respond commission, [status: OK] }
        }
    }

    @Transactional
    def delete(Commission commission)
    {

        if (commission == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        commission.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'commission.label',
                                                                                        default: 'Commission'),
                    commission.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'commission.label',
                                                                                          default: 'Commission'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

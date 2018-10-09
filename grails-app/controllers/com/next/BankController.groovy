package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class BankController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Bank.list(params), model: [bankCount: Bank.count()]
    }

    def show(Bank bank)
    {
        respond bank
    }

    def create()
    {
        respond new Bank(params)
    }

    @Transactional
    def save(Bank bank)
    {
        if (bank == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bank.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond bank.errors, view: 'create'
            return
        }

        bank.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bank.label', default: 'Bank'), bank.id])
                redirect bank
            }
            '*' { respond bank, [status: CREATED] }
        }
    }

    def edit(Bank bank)
    {
        respond bank
    }

    @Transactional
    def update(Bank bank)
    {
        if (bank == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bank.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond bank.errors, view: 'edit'
            return
        }

        bank.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bank.label', default: 'Bank'), bank.id])
                redirect bank
            }
            '*' { respond bank, [status: OK] }
        }
    }

    @Transactional
    def delete(Bank bank)
    {

        if (bank == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        bank.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bank.label', default: 'Bank'), bank.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bank.label', default: 'Bank'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

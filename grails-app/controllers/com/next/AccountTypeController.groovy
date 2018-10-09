package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class AccountTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AccountType.list(params), model: [accountTypeCount: AccountType.count()]
    }

    def show(AccountType accountType)
    {
        respond accountType
    }

    def create()
    {
        respond new AccountType(params)
    }

    @Transactional
    def save(AccountType accountType)
    {
        if (accountType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond accountType.errors, view: 'create'
            return
        }

        accountType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'accountType.label',
                                                                                        default: 'AccountType'),
                    accountType.id])
                redirect accountType
            }
            '*' { respond accountType, [status: CREATED] }
        }
    }

    def edit(AccountType accountType)
    {
        respond accountType
    }

    @Transactional
    def update(AccountType accountType)
    {
        if (accountType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond accountType.errors, view: 'edit'
            return
        }

        accountType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'accountType.label',
                                                                                        default: 'AccountType'),
                    accountType.id])
                redirect accountType
            }
            '*' { respond accountType, [status: OK] }
        }
    }

    @Transactional
    def delete(AccountType accountType)
    {

        if (accountType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        accountType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'accountType.label',
                                                                                        default: 'AccountType'),
                    accountType.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountType.label',
                                                                                          default: 'AccountType'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

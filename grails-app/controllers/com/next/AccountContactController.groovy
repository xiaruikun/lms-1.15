package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class AccountContactController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AccountContact.list(params), model: [accountContactCount: AccountContact.count()]
    }

    def show(AccountContact accountContact)
    {
        respond accountContact
    }

    def create()
    {
        respond new AccountContact(params)
    }

    @Transactional
    def save(AccountContact accountContact)
    {
        if (accountContact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountContact.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond accountContact.errors, view: 'create'
            return
        }

        accountContact.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'accountContact.label',
                                                                                        default: 'AccountContact'),
                    accountContact.id])
                redirect accountContact
            }
            '*' { respond accountContact, [status: CREATED] }
        }
    }

    def edit(AccountContact accountContact)
    {
        respond accountContact
    }

    @Transactional
    def update(AccountContact accountContact)
    {
        if (accountContact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountContact.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond accountContact.errors, view: 'edit'
            return
        }

        accountContact.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'accountContact.label',
                                                                                        default: 'AccountContact'),
                    accountContact.id])
                redirect accountContact
            }
            '*' { respond accountContact, [status: OK] }
        }
    }

    @Transactional
    def delete(AccountContact accountContact)
    {

        if (accountContact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        accountContact.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'accountContact.label',
                                                                                        default: 'AccountContact'),
                    accountContact.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountContact' + '.label', default: 'AccountContact'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ContactLoginHistoryController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ContactLoginHistory.list(params), model: [contactLoginHistoryCount: ContactLoginHistory.count()]
    }

    def show(ContactLoginHistory contactLoginHistory)
    {
        respond contactLoginHistory
    }

    def create()
    {
        respond new ContactLoginHistory(params)
    }

    @Transactional
    def save(ContactLoginHistory contactLoginHistory)
    {
        if (contactLoginHistory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactLoginHistory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactLoginHistory.errors, view: 'create'
            return
        }

        contactLoginHistory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contactLoginHistory' + '.label', default: 'ContactLoginHistory'),
                    contactLoginHistory.id])
                redirect contactLoginHistory
            }
            '*' { respond contactLoginHistory, [status: CREATED] }
        }
    }

    def edit(ContactLoginHistory contactLoginHistory)
    {
        respond contactLoginHistory
    }

    @Transactional
    def update(ContactLoginHistory contactLoginHistory)
    {
        if (contactLoginHistory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactLoginHistory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactLoginHistory.errors, view: 'edit'
            return
        }

        contactLoginHistory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contactLoginHistory' + '.label', default: 'ContactLoginHistory'),
                    contactLoginHistory.id])
                redirect contactLoginHistory
            }
            '*' { respond contactLoginHistory, [status: OK] }
        }
    }

    @Transactional
    def delete(ContactLoginHistory contactLoginHistory)
    {

        if (contactLoginHistory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contactLoginHistory.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contactLoginHistory' + '.label', default: 'ContactLoginHistory'),
                    contactLoginHistory.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactLoginHistory' + '.label', default: 'ContactLoginHistory'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

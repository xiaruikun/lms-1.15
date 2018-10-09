package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class TerritoryContactController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryContact.list(params), model: [territoryContactCount: TerritoryContact.count()]
    }

    def show(TerritoryContact territoryContact)
    {
        respond territoryContact
    }

    def create()
    {
        respond new TerritoryContact(params)
    }

    @Transactional
    def save(TerritoryContact territoryContact)
    {
        if (territoryContact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryContact.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryContact.errors, view: 'create'
            return
        }

        territoryContact.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryContact' + '.label', default: 'TerritoryContact'),
                    territoryContact.id])
                redirect territoryContact
            }
            '*' { respond territoryContact, [status: CREATED] }
        }
    }

    def edit(TerritoryContact territoryContact)
    {
        respond territoryContact
    }

    @Transactional
    def update(TerritoryContact territoryContact)
    {
        if (territoryContact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryContact.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryContact.errors, view: 'edit'
            return
        }

        territoryContact.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryContact' + '.label', default: 'TerritoryContact'),
                    territoryContact.id])
                redirect territoryContact
            }
            '*' { respond territoryContact, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryContact territoryContact)
    {

        if (territoryContact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryContact.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryContact' + '.label', default: 'TerritoryContact'),
                    territoryContact.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryContact' + '.label', default: 'TerritoryContact'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

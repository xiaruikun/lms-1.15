package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class TerritoryAccountController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('permitAll')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryAccount.list(params), model: [territoryAccountCount: TerritoryAccount.count()]
    }

    @Secured('permitAll')
    def show(TerritoryAccount territoryAccount)
    {
        respond territoryAccount
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new TerritoryAccount(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(TerritoryAccount territoryAccount)
    {
        if (territoryAccount == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryAccount.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryAccount.errors, view: 'create'
            return
        }

        territoryAccount.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryAccount' + '.label', default: 'TerritoryAccount'),
                    territoryAccount.id])
                // redirect territoryAccount
                redirect controller: "territory", action: "show", method: "GET", id: territoryAccount.territory.id
            }
            '*' { respond territoryAccount, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(TerritoryAccount territoryAccount)
    {
        respond territoryAccount
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(TerritoryAccount territoryAccount)
    {
        if (territoryAccount == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryAccount.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryAccount.errors, view: 'edit'
            return
        }

        territoryAccount.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryAccount' + '.label', default: 'TerritoryAccount'),
                    territoryAccount.id])
                redirect territoryAccount
            }
            '*' { respond territoryAccount, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(TerritoryAccount territoryAccount)
    {

        if (territoryAccount == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryAccount.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryAccount' + '.label', default: 'TerritoryAccount'),
                    territoryAccount.id])
                // redirect action: "index", method: "GET"
                redirect controller: "territory", action: "show", method: "GET", id: territoryAccount.territory.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryAccount' + '.label', default: 'TerritoryAccount'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class AccountCityController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AccountCity.list(params), model: [accountCityCount: AccountCity.count()]
    }

    def show(AccountCity accountCity)
    {
        respond accountCity
    }

    def create()
    {
        respond new AccountCity(params)
    }

    @Transactional
    def save(AccountCity accountCity)
    {
        if (accountCity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountCity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond accountCity.errors, view: 'create'
            return
        }

        accountCity.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'accountCity.label',
                                                                                        default: 'AccountCity'),
                    accountCity.id])
                redirect(controller: "account", action: "show", id: accountCity.account.id)
            }
            '*' { respond accountCity, [status: CREATED] }
        }
    }

    def edit(AccountCity accountCity)
    {
        respond accountCity
    }

    @Transactional
    def update(AccountCity accountCity)
    {
        if (accountCity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountCity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond accountCity.errors, view: 'edit'
            return
        }

        accountCity.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'accountCity.label',
                                                                                        default: 'AccountCity'),
                    accountCity.id])
                redirect accountCity
            }
            '*' { respond accountCity, [status: OK] }
        }
    }

    @Transactional
    def delete(AccountCity accountCity)
    {

        if (accountCity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        accountCity.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'accountCity.label',
                                                                                        default: 'AccountCity'),
                    accountCity.id])
                redirect(controller: "account", action: "show", id: accountCity.account.id)
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountCity.label',
                                                                                          default: 'AccountCity'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

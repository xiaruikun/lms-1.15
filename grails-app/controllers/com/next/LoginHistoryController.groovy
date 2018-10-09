package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class LoginHistoryController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        max = 10
        def offset = params.offset
        def loginHistory = LoginHistory.findAll("from LoginHistory order by createdDate desc", [max: max, offset: offset])
        respond loginHistory, model: [loginHistoryCount: LoginHistory.count()]
        // respond LoginHistory.list(params), model:[loginHistoryCount: LoginHistory.count()]
    }

    def show(LoginHistory loginHistory)
    {
        respond loginHistory
    }

    def create()
    {
        respond new LoginHistory(params)
    }

    @Transactional
    def save(LoginHistory loginHistory)
    {
        if (loginHistory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (loginHistory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond loginHistory.errors, view: 'create'
            return
        }

        loginHistory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'loginHistory.label', default: 'LoginHistory'), loginHistory.id])
                redirect loginHistory
            }
            '*' { respond loginHistory, [status: CREATED] }
        }
    }

    def edit(LoginHistory loginHistory)
    {
        respond loginHistory
    }

    @Transactional
    def update(LoginHistory loginHistory)
    {
        if (loginHistory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (loginHistory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond loginHistory.errors, view: 'edit'
            return
        }

        loginHistory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'loginHistory.label', default: 'LoginHistory'), loginHistory.id])
                redirect loginHistory
            }
            '*' { respond loginHistory, [status: OK] }
        }
    }

    @Transactional
    def delete(LoginHistory loginHistory)
    {

        if (loginHistory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        loginHistory.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'loginHistory.label', default: 'LoginHistory'), loginHistory.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'loginHistory.label', default: 'LoginHistory'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

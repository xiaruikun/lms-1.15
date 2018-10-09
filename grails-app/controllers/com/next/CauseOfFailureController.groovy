package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class CauseOfFailureController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond CauseOfFailure.list(params), model: [causeOfFailureCount: CauseOfFailure.count()]
    }

    def show(CauseOfFailure causeOfFailure)
    {
        respond causeOfFailure
    }

    def create()
    {
        respond new CauseOfFailure(params)
    }

    @Transactional
    def save(CauseOfFailure causeOfFailure)
    {
        if (causeOfFailure == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (causeOfFailure.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond causeOfFailure.errors, view: 'create'
            return
        }

        causeOfFailure.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'causeOfFailure.label',
                                                                                        default: 'CauseOfFailure'),
                    causeOfFailure.id])
                redirect causeOfFailure
            }
            '*' { respond causeOfFailure, [status: CREATED] }
        }
    }

    def edit(CauseOfFailure causeOfFailure)
    {
        respond causeOfFailure
    }

    @Transactional
    def update(CauseOfFailure causeOfFailure)
    {
        if (causeOfFailure == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (causeOfFailure.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond causeOfFailure.errors, view: 'edit'
            return
        }

        causeOfFailure.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'causeOfFailure.label',
                                                                                        default: 'CauseOfFailure'),
                    causeOfFailure.id])
                redirect causeOfFailure
            }
            '*' { respond causeOfFailure, [status: OK] }
        }
    }

    @Transactional
    def delete(CauseOfFailure causeOfFailure)
    {

        if (causeOfFailure == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        causeOfFailure.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'causeOfFailure.label',
                                                                                        default: 'CauseOfFailure'),
                    causeOfFailure.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'causeOfFailure' + '.label', default: 'CauseOfFailure'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

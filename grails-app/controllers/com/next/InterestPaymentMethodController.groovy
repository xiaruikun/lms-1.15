package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class InterestPaymentMethodController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond InterestPaymentMethod.list(params), model: [interestPaymentMethodCount: InterestPaymentMethod.count()]
    }

    def show(InterestPaymentMethod interestPaymentMethod)
    {
        respond interestPaymentMethod
    }

    def create()
    {
        respond new InterestPaymentMethod(params)
    }

    @Transactional
    def save(InterestPaymentMethod interestPaymentMethod)
    {
        if (interestPaymentMethod == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (interestPaymentMethod.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond interestPaymentMethod.errors, view: 'create'
            return
        }

        interestPaymentMethod.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'interestPaymentMethod' + '.label', default: 'InterestPaymentMethod'),
                    interestPaymentMethod.id])
                redirect interestPaymentMethod
            }
            '*' { respond interestPaymentMethod, [status: CREATED] }
        }
    }

    def edit(InterestPaymentMethod interestPaymentMethod)
    {
        respond interestPaymentMethod
    }

    @Transactional
    def update(InterestPaymentMethod interestPaymentMethod)
    {
        if (interestPaymentMethod == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (interestPaymentMethod.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond interestPaymentMethod.errors, view: 'edit'
            return
        }

        interestPaymentMethod.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'interestPaymentMethod' + '.label', default: 'InterestPaymentMethod'),
                    interestPaymentMethod.id])
                redirect interestPaymentMethod
            }
            '*' { respond interestPaymentMethod, [status: OK] }
        }
    }

    @Transactional
    def delete(InterestPaymentMethod interestPaymentMethod)
    {

        if (interestPaymentMethod == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        interestPaymentMethod.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'interestPaymentMethod' + '.label', default: 'InterestPaymentMethod'),
                    interestPaymentMethod.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'interestPaymentMethod' + '.label', default: 'InterestPaymentMethod'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

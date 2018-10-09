package com.next

import org.springframework.security.access.annotation.Secured

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*

class ChargeBackController
{

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	@Secured('permitAll')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Chargeback.list(params), model: [ChargebackCount: Chargeback.count()]
    }

    def show(Chargeback chargeback)
    {
        respond chargeback
    }

    def create()
    {
        def chargeback = new Chargeback()
        respond chargeback
    }

    def save(Chargeback chargeback)
    {
        if (chargeback == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (chargeback.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond chargeback.errors, view: 'create'
            return
        }

        chargeback.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'chargeback.label', default: 'Chargeback'), chargeback.id])
                redirect chargeback
            }
            '*' { respond chargeback, [status: CREATED] }
        }
    }

    def edit(Chargeback chargeback)
    {
        respond chargeback
    }

    def update(Chargeback chargeback)
    {
        if (chargeback == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (chargeback.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond chargeback.errors, view: 'edit'
            return
        }

        chargeback.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'chargeback.label', default: 'Chargeback'), chargeback.id])
                redirect chargeback
            }
            '*' { respond chargeback, [status: OK] }
        }
    }

    def delete(Chargeback chargeback)
    {

        if (chargeback == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        chargeback.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'chargeback.label', default: 'Chargeback'), chargeback.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'chargeback.label', default: 'Chargeback'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
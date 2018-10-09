package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ExternalDataReceiverAutitTrailController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ExternalDataReceiverAutitTrail.list(params), model: [externalDataReceiverAutitTrailCount: ExternalDataReceiverAutitTrail.count()]
    }

    def show(ExternalDataReceiverAutitTrail externalDataReceiverAutitTrail)
    {
        respond externalDataReceiverAutitTrail
    }

    def create()
    {
        respond new ExternalDataReceiverAutitTrail(params)
    }

    @Transactional
    def save(ExternalDataReceiverAutitTrail externalDataReceiverAutitTrail)
    {
        if (externalDataReceiverAutitTrail == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (externalDataReceiverAutitTrail.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond externalDataReceiverAutitTrail.errors, view: 'create'
            return
        }

        externalDataReceiverAutitTrail.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'externalDataReceiverAutitTrail.label', default: 'ExternalDataReceiverAutitTrail'), externalDataReceiverAutitTrail.id])
                redirect externalDataReceiverAutitTrail
            }
            '*' { respond externalDataReceiverAutitTrail, [status: CREATED] }
        }
    }

    def edit(ExternalDataReceiverAutitTrail externalDataReceiverAutitTrail)
    {
        respond externalDataReceiverAutitTrail
    }

    @Transactional
    def update(ExternalDataReceiverAutitTrail externalDataReceiverAutitTrail)
    {
        if (externalDataReceiverAutitTrail == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (externalDataReceiverAutitTrail.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond externalDataReceiverAutitTrail.errors, view: 'edit'
            return
        }

        externalDataReceiverAutitTrail.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'externalDataReceiverAutitTrail.label', default: 'ExternalDataReceiverAutitTrail'), externalDataReceiverAutitTrail.id])
                redirect externalDataReceiverAutitTrail
            }
            '*' { respond externalDataReceiverAutitTrail, [status: OK] }
        }
    }

    @Transactional
    def delete(ExternalDataReceiverAutitTrail externalDataReceiverAutitTrail)
    {

        if (externalDataReceiverAutitTrail == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        externalDataReceiverAutitTrail.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'externalDataReceiverAutitTrail.label', default: 'ExternalDataReceiverAutitTrail'), externalDataReceiverAutitTrail.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalDataReceiverAutitTrail.label', default: 'ExternalDataReceiverAutitTrail'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

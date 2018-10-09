package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])

@Transactional(readOnly = true)
class ExternalDataReceiverMessageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ExternalDataReceiverMessage.list(params), model: [externalDataReceiverMessageCount: ExternalDataReceiverMessage.count()]
    }

    def show(ExternalDataReceiverMessage externalDataReceiverMessage)
    {
        respond externalDataReceiverMessage
    }

    def create()
    {
        respond new ExternalDataReceiverMessage(params)
    }

    @Transactional
    def save(ExternalDataReceiverMessage externalDataReceiverMessage)
    {
        if (externalDataReceiverMessage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (externalDataReceiverMessage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond externalDataReceiverMessage.errors, view: 'create'
            return
        }

        externalDataReceiverMessage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'externalDataReceiverMessage.label', default: 'ExternalDataReceiverMessage'), externalDataReceiverMessage.id])
                // redirect externalDataReceiverMessage
                redirect(controller: "externalDataReceiver", action: "show", params: [id: externalDataReceiverMessage?.receiver?.id])
            }
            '*' { respond externalDataReceiverMessage, [status: CREATED] }
        }
    }

    def edit(ExternalDataReceiverMessage externalDataReceiverMessage)
    {
        respond externalDataReceiverMessage
    }

    @Transactional
    def update(ExternalDataReceiverMessage externalDataReceiverMessage)
    {
        if (externalDataReceiverMessage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (externalDataReceiverMessage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond externalDataReceiverMessage.errors, view: 'edit'
            return
        }

        externalDataReceiverMessage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'externalDataReceiverMessage.label', default: 'ExternalDataReceiverMessage'), externalDataReceiverMessage.id])
                redirect externalDataReceiverMessage
            }
            '*' { respond externalDataReceiverMessage, [status: OK] }
        }
    }

    @Transactional
    def delete(ExternalDataReceiverMessage externalDataReceiverMessage)
    {

        if (externalDataReceiverMessage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        externalDataReceiverMessage.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'externalDataReceiverMessage.label', default: 'ExternalDataReceiverMessage'), externalDataReceiverMessage.id])
                // redirect action: "index", method: "GET"
                redirect(controller: "externalDataReceiver", action: "show", params: [id: externalDataReceiverMessage?.receiver?.id])

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalDataReceiverMessage.label', default: 'ExternalDataReceiverMessage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

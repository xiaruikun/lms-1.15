package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class MessageTemplateController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond MessageTemplate.list(params), model: [messageTemplateCount: MessageTemplate.count()]
    }

    def show(MessageTemplate messageTemplate)
    {
        respond messageTemplate
    }

    def create()
    {
        respond new MessageTemplate(params)
    }

    @Transactional
    def save(MessageTemplate messageTemplate)
    {
        if (messageTemplate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (messageTemplate.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond messageTemplate.errors, view: 'create'
            return
        }

        messageTemplate.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'messageTemplate' + '.label', default: 'MessageTemplate'),
                    messageTemplate.id])
                // redirect messageTemplate
                redirect action: 'index'
            }
            '*' { respond messageTemplate, [status: CREATED] }
        }
    }

    def edit(MessageTemplate messageTemplate)
    {
        respond messageTemplate
    }

    @Transactional
    def update(MessageTemplate messageTemplate)
    {
        if (messageTemplate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (messageTemplate.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond messageTemplate.errors, view: 'edit'
            return
        }

        messageTemplate.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'messageTemplate' + '.label', default: 'MessageTemplate'),
                    messageTemplate.id])
                redirect messageTemplate
            }
            '*' { respond messageTemplate, [status: OK] }
        }
    }

    @Transactional
    def delete(MessageTemplate messageTemplate)
    {

        if (messageTemplate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        messageTemplate.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'messageTemplate' + '.label', default: 'MessageTemplate'),
                    messageTemplate.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'messageTemplate' + '.label', default: 'MessageTemplate'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

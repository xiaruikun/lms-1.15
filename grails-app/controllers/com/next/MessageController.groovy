package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class MessageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Message.list(params), model: [messageCount: Message.count()]
    }

    def show(Message message)
    {
        respond message
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new Message(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(Message message)
    {
        if (message == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (message.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond message.errors, view: 'create'
            return
        }

        message.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'message.label',
                                                                                        default: 'Message'), message
                    .id])
                redirect message
            }
            '*' { respond message, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(Message message)
    {
        respond message
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(Message message)
    {
        if (message == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (message.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond message.errors, view: 'edit'
            return
        }

        message.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'message.label',
                                                                                        default: 'Message'), message
                    .id])
                redirect message
            }
            '*' { respond message, [status: OK] }
        }
    }

    @Transactional
    def delete(Message message)
    {

        if (message == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        message.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'message.label',
                                                                                        default: 'Message'), message
                    .id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label',
                                                                                          default: 'Message'), params
                    .id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

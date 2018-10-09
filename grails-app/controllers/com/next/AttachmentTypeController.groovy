package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class AttachmentTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AttachmentType.list(params), model: [attachmentTypeCount: AttachmentType.count()]
    }

    def show(AttachmentType attachmentType)
    {
        respond attachmentType
    }

    def create()
    {
        respond new AttachmentType(params)
    }

    @Transactional
    def save(AttachmentType attachmentType)
    {
        if (attachmentType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (attachmentType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond attachmentType.errors, view: 'create'
            return
        }

        attachmentType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'attachmentType.label',
                                                                                        default: 'AttachmentType'),
                    attachmentType.id])
                redirect attachmentType
            }
            '*' { respond attachmentType, [status: CREATED] }
        }
    }

    def edit(AttachmentType attachmentType)
    {
        respond attachmentType
    }

    @Transactional
    def update(AttachmentType attachmentType)
    {
        if (attachmentType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (attachmentType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond attachmentType.errors, view: 'edit'
            return
        }

        attachmentType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'attachmentType.label',
                                                                                        default: 'AttachmentType'),
                    attachmentType.id])
                redirect attachmentType
            }
            '*' { respond attachmentType, [status: OK] }
        }
    }

    @Transactional
    def delete(AttachmentType attachmentType)
    {

        if (attachmentType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        attachmentType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'attachmentType.label',
                                                                                        default: 'AttachmentType'),
                    attachmentType.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'attachmentType' + '.label', default: 'AttachmentType'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class DocumentController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Document.list(params), model: [documentCount: Document.count()]
    }

    def show(Document document)
    {
        respond document
    }

    def create()
    {
        respond new Document(params)
    }

    @Transactional
    def save(Document document)
    {
        if (document == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (document.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond document.errors, view: 'create'
            return
        }

        document.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'document.label', default: 'Document'), document.id])
                redirect document
            }
            '*' { respond document, [status: CREATED] }
        }
    }

    def edit(Document document)
    {
        respond document
    }

    @Transactional
    def update(Document document)
    {
        if (document == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (document.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond document.errors, view: 'edit'
            return
        }

        document.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'document.label', default: 'Document'), document.id])
                redirect document
            }
            '*' { respond document, [status: OK] }
        }
    }

    @Transactional
    def delete(Document document)
    {

        if (document == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        document.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'document.label', default: 'Document'), document.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'document.label', default: 'Document'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

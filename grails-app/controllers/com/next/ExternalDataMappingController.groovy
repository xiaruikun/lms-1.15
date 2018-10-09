package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class ExternalDataMappingController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ExternalDataMapping.list(params), model: [externalDataMappingCount: ExternalDataMapping.count()]
    }

    def show(ExternalDataMapping externalDataMapping)
    {
        respond externalDataMapping
    }

    def create()
    {
        respond new ExternalDataMapping(params)
    }

    @Transactional
    def save(ExternalDataMapping externalDataMapping)
    {
        if (externalDataMapping == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (externalDataMapping.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond externalDataMapping.errors, view: 'create'
            return
        }

        externalDataMapping.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'externalDataMapping.label', default: 'ExternalDataMapping'), externalDataMapping.id])
                redirect externalDataMapping
            }
            '*' { respond externalDataMapping, [status: CREATED] }
        }
    }

    def edit(ExternalDataMapping externalDataMapping)
    {
        respond externalDataMapping
    }

    @Transactional
    def update(ExternalDataMapping externalDataMapping)
    {
        if (externalDataMapping == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (externalDataMapping.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond externalDataMapping.errors, view: 'edit'
            return
        }

        externalDataMapping.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'externalDataMapping.label', default: 'ExternalDataMapping'), externalDataMapping.id])
                redirect externalDataMapping
            }
            '*' { respond externalDataMapping, [status: OK] }
        }
    }

    @Transactional
    def delete(ExternalDataMapping externalDataMapping)
    {

        if (externalDataMapping == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        externalDataMapping.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'externalDataMapping.label', default: 'ExternalDataMapping'), externalDataMapping.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalDataMapping.label', default: 'ExternalDataMapping'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

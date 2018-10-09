package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ActivitySubtypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ActivitySubtype.list(params), model: [activitySubtypeCount: ActivitySubtype.count()]
    }

    def show(ActivitySubtype activitySubtype)
    {
        respond activitySubtype
    }

    def create()
    {
        respond new ActivitySubtype(params)
    }

    @Transactional
    def save(ActivitySubtype activitySubtype)
    {
        if (activitySubtype == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (activitySubtype.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond activitySubtype.errors, view: 'create'
            return
        }

        activitySubtype.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'activitySubtype' + '.label', default: 'ActivitySubtype'),
                    activitySubtype.id])
                redirect activitySubtype
            }
            '*' { respond activitySubtype, [status: CREATED] }
        }
    }

    def edit(ActivitySubtype activitySubtype)
    {
        respond activitySubtype
    }

    @Transactional
    def update(ActivitySubtype activitySubtype)
    {
        if (activitySubtype == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (activitySubtype.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond activitySubtype.errors, view: 'edit'
            return
        }

        activitySubtype.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'activitySubtype' + '.label', default: 'ActivitySubtype'),
                    activitySubtype.id])
                redirect activitySubtype
            }
            '*' { respond activitySubtype, [status: OK] }
        }
    }

    @Transactional
    def delete(ActivitySubtype activitySubtype)
    {

        if (activitySubtype == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        activitySubtype.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'activitySubtype' + '.label', default: 'ActivitySubtype'),
                    activitySubtype.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'activitySubtype' + '.label', default: 'ActivitySubtype'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

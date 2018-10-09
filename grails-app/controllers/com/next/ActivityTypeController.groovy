package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ActivityTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ActivityType.list(params), model: [activityTypeCount: ActivityType.count()]
    }

    def show(ActivityType activityType)
    {
        respond activityType
    }

    def create()
    {
        respond new ActivityType(params)
    }

    @Transactional
    def save(ActivityType activityType)
    {
        if (activityType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (activityType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond activityType.errors, view: 'create'
            return
        }

        activityType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'activityType.label',
                                                                                        default: 'ActivityType'),
                    activityType.id])
                redirect activityType
            }
            '*' { respond activityType, [status: CREATED] }
        }
    }

    def edit(ActivityType activityType)
    {
        respond activityType
    }

    @Transactional
    def update(ActivityType activityType)
    {
        if (activityType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (activityType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond activityType.errors, view: 'edit'
            return
        }

        activityType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'activityType.label',
                                                                                        default: 'ActivityType'),
                    activityType.id])
                redirect activityType
            }
            '*' { respond activityType, [status: OK] }
        }
    }

    @Transactional
    def delete(ActivityType activityType)
    {

        if (activityType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        activityType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'activityType.label',
                                                                                        default: 'ActivityType'),
                    activityType.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'activityType.label',
                                                                                          default: 'ActivityType'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

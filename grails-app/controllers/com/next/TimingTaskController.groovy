package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @CreatedDate 2017-5-4
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class TimingTaskController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    ComponentService componentService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TimingTask.list(params), model: [timingTaskCount: TimingTask.count()]
    }

    def show(TimingTask timingTask)
    {
        respond timingTask
    }

    def create()
    {
        respond new TimingTask(params)
    }

    @Transactional
    def save(TimingTask timingTask)
    {
        if (timingTask == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (timingTask.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond timingTask.errors, view: 'create'
            return
        }

        timingTask.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'timingTask.label', default: 'TimingTask'), timingTask.id])
                redirect timingTask
            }
            '*' { respond timingTask, [status: CREATED] }
        }
    }

    def edit(TimingTask timingTask)
    {
        respond timingTask
    }

    @Transactional
    def update(TimingTask timingTask)
    {
        if (timingTask == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (timingTask.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond timingTask.errors, view: 'edit'
            return
        }

        timingTask.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'timingTask.label', default: 'TimingTask'), timingTask.id])
                redirect timingTask
            }
            '*' { respond timingTask, [status: OK] }
        }
    }

    @Transactional
    def delete(TimingTask timingTask)
    {

        if (timingTask == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        timingTask.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'timingTask.label', default: 'TimingTask'), timingTask.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'timingTask.label', default: 'TimingTask'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def execute(TimingTask timingTask)
    {
        def result
        result = componentService.evaluate timingTask?.component, null
        timingTask.log = result
        if (!timingTask?.start)
        {
            timingTask.start = 1
        }
        else
        {
            timingTask.start += 1
        }
        timingTask.save flush: true
        redirect action: "index", method: "GET"
    }
}

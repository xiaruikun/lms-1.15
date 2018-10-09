package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class PositionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Position.list(params), model: [positionCount: Position.count()]
    }

    def show(Position position)
    {
        respond position
    }

    def create()
    {
        respond new Position(params)
    }

    @Transactional
    def save(Position position)
    {
        if (position == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (position.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond position.errors, view: 'create'
            return
        }

        position.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'position.label', default: 'Position'), position.id])
                redirect position
            }
            '*' { respond position, [status: CREATED] }
        }
    }

    def edit(Position position)
    {
        respond position
    }

    @Transactional
    def update(Position position)
    {
        if (position == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (position.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond position.errors, view: 'edit'
            return
        }

        position.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'position.label', default: 'Position'), position.id])
                redirect position
            }
            '*' { respond position, [status: OK] }
        }
    }

    @Transactional
    def delete(Position position)
    {

        if (position == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        position.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'position.label', default: 'Position'), position.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'position.label', default: 'Position'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

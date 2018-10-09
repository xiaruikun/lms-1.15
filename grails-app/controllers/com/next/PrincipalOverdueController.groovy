package com.next

import org.springframework.security.access.annotation.Secured

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*

class PrincipalOverdueController
{

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	@Secured('permitAll')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond PrincipalOverdue.list(params), model: [PrincipalOverdueCount: PrincipalOverdue.count()]
    }

    def show(PrincipalOverdue principalOverdue)
    {
        respond principalOverdue
    }

    def create()
    {
        def principalOverdue = new PrincipalOverdue()
        respond principalOverdue
    }

    def save(PrincipalOverdue principalOverdue)
    {
        if (principalOverdue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (principalOverdue.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond principalOverdue.errors, view: 'create'
            return
        }

        principalOverdue.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'principalOverdue.label', default: 'PrincipalOverdue'), principalOverdue.id])
                redirect principalOverdue
            }
            '*' { respond principalOverdue, [status: CREATED] }
        }
    }

    def edit(PrincipalOverdue principalOverdue)
    {
        respond principalOverdue
    }

    def update(PrincipalOverdue principalOverdue)
    {
        if (principalOverdue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (principalOverdue.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond principalOverdue.errors, view: 'edit'
            return
        }

        principalOverdue.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'principalOverdue.label', default: 'PrincipalOverdue'), principalOverdue.id])
                redirect principalOverdue
            }
            '*' { respond principalOverdue, [status: OK] }
        }
    }

    def delete(PrincipalOverdue principalOverdue)
    {

        if (principalOverdue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        principalOverdue.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'principalOverdue.label', default: 'PrincipalOverdue'), principalOverdue.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'principalOverdue.label', default: 'PrincipalOverdue'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
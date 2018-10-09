package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class TypeOfFirstMortgageController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TypeOfFirstMortgage.list(params), model: [typeOfFirstMortgageCount: TypeOfFirstMortgage.count()]
    }

    def show(TypeOfFirstMortgage typeOfFirstMortgage)
    {
        respond typeOfFirstMortgage
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new TypeOfFirstMortgage(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(TypeOfFirstMortgage typeOfFirstMortgage)
    {
        if (typeOfFirstMortgage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (typeOfFirstMortgage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond typeOfFirstMortgage.errors, view: 'create'
            return
        }

        typeOfFirstMortgage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'typeOfFirstMortgage' + '.label', default: 'TypeOfFirstMortgage'),
                    typeOfFirstMortgage.id])
                redirect typeOfFirstMortgage
            }
            '*' { respond typeOfFirstMortgage, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(TypeOfFirstMortgage typeOfFirstMortgage)
    {
        respond typeOfFirstMortgage
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(TypeOfFirstMortgage typeOfFirstMortgage)
    {
        if (typeOfFirstMortgage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (typeOfFirstMortgage.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond typeOfFirstMortgage.errors, view: 'edit'
            return
        }

        typeOfFirstMortgage.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'typeOfFirstMortgage' + '.label', default: 'TypeOfFirstMortgage'),
                    typeOfFirstMortgage.id])
                redirect typeOfFirstMortgage
            }
            '*' { respond typeOfFirstMortgage, [status: OK] }
        }
    }

    @Transactional
    def delete(TypeOfFirstMortgage typeOfFirstMortgage)
    {

        if (typeOfFirstMortgage == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        typeOfFirstMortgage.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'typeOfFirstMortgage' + '.label', default: 'TypeOfFirstMortgage'),
                    typeOfFirstMortgage.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeOfFirstMortgage' + '.label', default: 'TypeOfFirstMortgage'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

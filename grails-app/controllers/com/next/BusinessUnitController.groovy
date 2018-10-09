package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class BusinessUnitController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond BusinessUnit.list(params), model: [businessUnitCount: BusinessUnit.count()]
    }

    def show(BusinessUnit businessUnit)
    {
        respond businessUnit
    }

    def create()
    {
        respond new BusinessUnit(params)
    }

    @Transactional
    def save(BusinessUnit businessUnit)
    {
        if (businessUnit == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (businessUnit.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond businessUnit.errors, view: 'create'
            return
        }

        businessUnit.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'businessUnit.label',
                                                                                        default: 'BusinessUnit'),
                    businessUnit.id])
                redirect businessUnit
            }
            '*' { respond businessUnit, [status: CREATED] }
        }
    }

    def edit(BusinessUnit businessUnit)
    {
        respond businessUnit
    }

    @Transactional
    def update(BusinessUnit businessUnit)
    {
        if (businessUnit == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (businessUnit.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond businessUnit.errors, view: 'edit'
            return
        }

        businessUnit.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'businessUnit.label',
                                                                                        default: 'BusinessUnit'),
                    businessUnit.id])
                redirect businessUnit
            }
            '*' { respond businessUnit, [status: OK] }
        }
    }

    @Transactional
    def delete(BusinessUnit businessUnit)
    {

        if (businessUnit == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        businessUnit.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'businessUnit.label',
                                                                                        default: 'BusinessUnit'),
                    businessUnit.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'businessUnit.label',
                                                                                          default: 'BusinessUnit'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

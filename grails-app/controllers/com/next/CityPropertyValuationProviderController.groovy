package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class CityPropertyValuationProviderController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond CityPropertyValuationProvider.list(params), model: [cityPropertyValuationProviderCount: CityPropertyValuationProvider.count()]
    }

    def show(CityPropertyValuationProvider cityPropertyValuationProvider)
    {
        respond cityPropertyValuationProvider
    }

    def create()
    {
        respond new CityPropertyValuationProvider(params)
    }

    @Transactional
    def save(CityPropertyValuationProvider cityPropertyValuationProvider)
    {
        if (cityPropertyValuationProvider == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (cityPropertyValuationProvider.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond cityPropertyValuationProvider.errors, view: 'create'
            return
        }

        cityPropertyValuationProvider.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cityPropertyValuationProvider.label', default: 'CityPropertyValuationProvider'), cityPropertyValuationProvider.id])
                redirect cityPropertyValuationProvider
            }
            '*' { respond cityPropertyValuationProvider, [status: CREATED] }
        }
    }

    def edit(CityPropertyValuationProvider cityPropertyValuationProvider)
    {
        respond cityPropertyValuationProvider
    }

    @Transactional
    def update(CityPropertyValuationProvider cityPropertyValuationProvider)
    {
        if (cityPropertyValuationProvider == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (cityPropertyValuationProvider.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond cityPropertyValuationProvider.errors, view: 'edit'
            return
        }

        cityPropertyValuationProvider.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'cityPropertyValuationProvider.label', default: 'CityPropertyValuationProvider'), cityPropertyValuationProvider.id])
                redirect cityPropertyValuationProvider
            }
            '*' { respond cityPropertyValuationProvider, [status: OK] }
        }
    }

    @Transactional
    def delete(CityPropertyValuationProvider cityPropertyValuationProvider)
    {

        if (cityPropertyValuationProvider == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        cityPropertyValuationProvider.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cityPropertyValuationProvider.label', default: 'CityPropertyValuationProvider'), cityPropertyValuationProvider.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cityPropertyValuationProvider.label', default: 'CityPropertyValuationProvider'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

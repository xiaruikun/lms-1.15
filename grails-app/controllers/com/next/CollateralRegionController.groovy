package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class CollateralRegionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond CollateralRegion.list(params), model: [collateralRegionCount: CollateralRegion.count()]
    }

    def show(CollateralRegion collateralRegion)
    {
        respond collateralRegion
    }

    def create()
    {
        respond new CollateralRegion(params)
    }

    @Transactional
    def save(CollateralRegion collateralRegion)
    {
        if (collateralRegion == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (collateralRegion.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond collateralRegion.errors, view: 'create'
            return
        }

        collateralRegion.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'collateralRegion.label', default: 'CollateralRegion'), collateralRegion.id])
                redirect collateralRegion
            }
            '*' { respond collateralRegion, [status: CREATED] }
        }
    }

    def edit(CollateralRegion collateralRegion)
    {
        respond collateralRegion
    }

    @Transactional
    def update(CollateralRegion collateralRegion)
    {
        if (collateralRegion == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (collateralRegion.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond collateralRegion.errors, view: 'edit'
            return
        }

        collateralRegion.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'collateralRegion.label', default: 'CollateralRegion'), collateralRegion.id])
                redirect collateralRegion
            }
            '*' { respond collateralRegion, [status: OK] }
        }
    }

    @Transactional
    def delete(CollateralRegion collateralRegion)
    {

        if (collateralRegion == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        collateralRegion.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'collateralRegion.label', default: 'CollateralRegion'), collateralRegion.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'collateralRegion.label', default: 'CollateralRegion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

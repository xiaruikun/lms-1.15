package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class TerritoryProductController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryProduct.list(params), model: [territoryProductCount: TerritoryProduct.count()]
    }

    def show(TerritoryProduct territoryProduct)
    {
        respond territoryProduct
    }

    def create()
    {
        respond new TerritoryProduct(params)
    }

    @Transactional
    def save(TerritoryProduct territoryProduct)
    {
        if (territoryProduct == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryProduct.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryProduct.errors, view: 'create'
            return
        }

        territoryProduct.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryProduct' + '.label', default: 'TerritoryProduct'),
                    territoryProduct.id])
                redirect territoryProduct
            }
            '*' { respond territoryProduct, [status: CREATED] }
        }
    }

    def edit(TerritoryProduct territoryProduct)
    {
        respond territoryProduct
    }

    @Transactional
    def update(TerritoryProduct territoryProduct)
    {
        if (territoryProduct == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryProduct.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryProduct.errors, view: 'edit'
            return
        }

        territoryProduct.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryProduct' + '.label', default: 'TerritoryProduct'),
                    territoryProduct.id])
                redirect territoryProduct
            }
            '*' { respond territoryProduct, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryProduct territoryProduct)
    {

        if (territoryProduct == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryProduct.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryProduct' + '.label', default: 'TerritoryProduct'),
                    territoryProduct.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryProduct' + '.label', default: 'TerritoryProduct'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

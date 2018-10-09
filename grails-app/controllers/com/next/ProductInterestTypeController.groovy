package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ProductInterestTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ProductInterestType.list(params), model: [productInterestTypeCount: ProductInterestType.count()]
    }

    def show(ProductInterestType productInterestType)
    {
        respond productInterestType
    }

    def create()
    {
        respond new ProductInterestType(params)
    }

    @Transactional
    def save(ProductInterestType productInterestType)
    {
        if (productInterestType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productInterestType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productInterestType.errors, view: 'create'
            return
        }

        productInterestType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'productInterestType.label', default: 'ProductInterestType'), productInterestType.id])
                redirect productInterestType
            }
            '*' { respond productInterestType, [status: CREATED] }
        }
    }

    def edit(ProductInterestType productInterestType)
    {
        respond productInterestType
    }

    @Transactional
    def update(ProductInterestType productInterestType)
    {
        if (productInterestType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productInterestType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productInterestType.errors, view: 'edit'
            return
        }

        productInterestType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'productInterestType.label', default: 'ProductInterestType'), productInterestType.id])
                redirect productInterestType
            }
            '*' { respond productInterestType, [status: OK] }
        }
    }

    @Transactional
    def delete(ProductInterestType productInterestType)
    {

        if (productInterestType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        productInterestType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'productInterestType.label', default: 'ProductInterestType'), productInterestType.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'productInterestType.label', default: 'ProductInterestType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR'])
@Transactional(readOnly = true)
class ProductInterestController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ProductInterest.list(params), model: [productInterestCount: ProductInterest.count()]
    }

    def show(ProductInterest productInterest)
    {
        respond productInterest
    }

    def create()
    {
        respond new ProductInterest(params)
    }

    @Transactional
    def save(ProductInterest productInterest)
    {
        if (productInterest == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productInterest.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productInterest.errors, view: 'create'
            return
        }

        productInterest.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'productInterest.label', default: 'ProductInterest'), productInterest.id])
                redirect(controller: "productAccount", action: "show", id: productInterest.product.id)
            }
            '*' { respond productInterest, [status: CREATED] }
        }
    }

    def edit(ProductInterest productInterest)
    {
        respond productInterest
    }

    @Transactional
    def update(ProductInterest productInterest)
    {
        if (productInterest == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productInterest.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productInterest.errors, view: 'edit'
            return
        }

        productInterest.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'productInterest.label', default: 'ProductInterest'), productInterest.id])
                redirect productInterest
            }
            '*' { respond productInterest, [status: OK] }
        }
    }

    @Transactional
    def delete(ProductInterest productInterest)
    {

        if (productInterest == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        productInterest.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'productInterest.label', default: 'ProductInterest'), productInterest.id])
                redirect(controller: "productAccount", action: "show", id: productInterest.product.id)
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'productInterest.label', default: 'ProductInterest'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

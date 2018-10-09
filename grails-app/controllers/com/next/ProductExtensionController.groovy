package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR'])
@Transactional(readOnly = true)
class ProductExtensionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ProductExtension.list(params), model: [productExtensionCount: ProductExtension.count()]
    }

    def show(ProductExtension productExtension)
    {
        respond productExtension
    }

    def create()
    {
        respond new ProductExtension(params)
    }

    @Transactional
    def save(ProductExtension productExtension)
    {
        if (productExtension == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productExtension.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productExtension.errors, view: 'create'
            return
        }

        productExtension.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'productExtension' + '.label', default: 'ProductExtension'), productExtension.id])
                redirect(controller: "productAccount", action: "show", id: productExtension.product.id)
            }
            '*' { respond productExtension, [status: CREATED] }
        }
    }

    def edit(ProductExtension productExtension)
    {
        respond productExtension
    }

    @Transactional
    def update(ProductExtension productExtension)
    {
        if (productExtension == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productExtension.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productExtension.errors, view: 'edit'
            return
        }

        productExtension.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'productExtension' + '.label', default: 'ProductExtension'), productExtension.id])
                redirect productExtension
            }
            '*' { respond productExtension, [status: OK] }
        }
    }

    @Transactional
    def delete(ProductExtension productExtension)
    {

        if (productExtension == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        productExtension.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'productExtension' + '.label', default: 'ProductExtension'), productExtension.id])
                redirect(controller: "productAccount", action: "show", id: productExtension.product.id)
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'productExtension' + '.label', default: 'ProductExtension'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

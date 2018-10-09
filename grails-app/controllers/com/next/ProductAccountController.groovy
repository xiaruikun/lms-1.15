package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_COO'])
@Transactional(readOnly = true)
class ProductAccountController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ProductAccount.list(params), model: [productAccountCount: ProductAccount.count()]
    }

    def show(ProductAccount productAccount)
    {
        respond productAccount
    }

    def create()
    {
        respond new ProductAccount(params)
    }

    @Transactional
    def save(ProductAccount productAccount)
    {
        if (productAccount == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productAccount.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productAccount.errors, view: 'create'
            return
        }

        productAccount.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'productAccount.label', default: 'ProductAccount'), productAccount.id])
                redirect productAccount
            }
            '*' { respond productAccount, [status: CREATED] }
        }
    }

    def edit(ProductAccount productAccount)
    {
        respond productAccount
    }

    @Transactional
    def update(ProductAccount productAccount)
    {
        if (productAccount == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productAccount.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productAccount.errors, view: 'edit'
            return
        }

        productAccount.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'productAccount.label', default: 'ProductAccount'), productAccount.id])
                redirect productAccount
            }
            '*' { respond productAccount, [status: OK] }
        }
    }

    @Transactional
    def delete(ProductAccount productAccount)
    {

        if (productAccount == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        productAccount.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'productAccount.label', default: 'ProductAccount'), productAccount.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'productAccount.label', default: 'ProductAccount'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ProductCategoryController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ProductCategory.list(params), model: [productCategoryCount: ProductCategory.count()]
    }

    def show(ProductCategory productCategory)
    {
        respond productCategory
    }

    def create()
    {
        respond new ProductCategory(params)
    }

    @Transactional
    def save(ProductCategory productCategory)
    {
        if (productCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productCategory.errors, view: 'create'
            return
        }

        productCategory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'productCategory' + '.label', default: 'ProductCategory'), productCategory.id])
                redirect productCategory
            }
            '*' { respond productCategory, [status: CREATED] }
        }
    }

    def edit(ProductCategory productCategory)
    {
        respond productCategory
    }

    @Transactional
    def update(ProductCategory productCategory)
    {
        if (productCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productCategory.errors, view: 'edit'
            return
        }

        productCategory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'productCategory' + '.label', default: 'ProductCategory'), productCategory.id])
                redirect productCategory
            }
            '*' { respond productCategory, [status: OK] }
        }
    }

    @Transactional
    def delete(ProductCategory productCategory)
    {

        if (productCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        productCategory.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'productCategory' + '.label', default: 'ProductCategory'), productCategory.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'productCategory' + '.label', default: 'ProductCategory'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

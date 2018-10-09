package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_COO'])
@Transactional(readOnly = true)
class ProductController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Product.list(params), model: [productCount: Product.count()]
    }

    def show(Product product)
    {
        def productAccountList = ProductAccount.findAllByProduct(product)
        //        println productAccountList
        respond product, model: [productAccountList: productAccountList]
    }

    def create()
    {
        respond new Product(params)
    }

    @Transactional
    def save(Product product)
    {
        if (product == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (product.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond product.errors, view: 'create'
            return
        }

        product.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'product.label', default: 'Product'), product.id])
                redirect product
            }
            '*' { respond product, [status: CREATED] }
        }
    }

    def edit(Product product)
    {
        respond product
    }

    @Transactional
    def update(Product product)
    {
        if (product == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (product.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond product.errors, view: 'edit'
            return
        }

        product.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'product.label', default: 'Product'), product.id])
                redirect product
            }
            '*' { respond product, [status: OK] }
        }
    }

    @Transactional
    def delete(Product product)
    {

        if (product == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        product.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'product.label', default: 'Product'), product.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def searchProduct()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def name = params["name"]
        String sql = "from Product as u where 1=1"
        if (name)
        {
            sql += " and u.name like '%${name}%'"
        }

        sql += ' order by createdDate desc'

        println "sql:" + sql

        def max = params.max
        def offset = params.offset

        def list = Product.findAll(sql, [max: max, offset: offset])
        def list1 = Product.findAll(sql)
        def count = list1.size()

        respond list, model: [productCount: count, params: params], view: 'index'
    }
}

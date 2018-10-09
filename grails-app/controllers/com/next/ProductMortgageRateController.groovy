package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR'])
@Transactional(readOnly = true)
class ProductMortgageRateController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ProductMortgageRate.list(params), model: [productMortgageRateCount: ProductMortgageRate.count()]
    }

    def show(ProductMortgageRate productMortgageRate)
    {
        respond productMortgageRate
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR'])
    def create()
    {
        def productMortgageRateList = ProductMortgageRate.findAllByProduct(ProductAccount.findById(params["id"]))
        def assetTypeList = AssetType.findAll()
        if (productMortgageRateList)
        {
            def sqlStr = "("
            productMortgageRateList.each {
                sqlStr += it.assetType.id + ","
            }
            sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf(","))
            sqlStr += ")"
            assetTypeList = AssetType.findAll("from AssetType as a where a.id not in ${sqlStr}")
        }
        respond new ProductMortgageRate(params), model: [assetTypeList: assetTypeList]
    }

    @Transactional
    def save(ProductMortgageRate productMortgageRate)
    {
        if (productMortgageRate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productMortgageRate.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productMortgageRate.errors, view: 'create'
            return
        }

        productMortgageRate.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'productMortgageRate' + '.label', default: 'ProductMortgageRate'), productMortgageRate.id])
                redirect(controller: "productAccount", action: "show", id: productMortgageRate.product.id)
            }
            '*' { respond productMortgageRate, [status: CREATED] }
        }
    }

    def edit(ProductMortgageRate productMortgageRate)
    {
        respond productMortgageRate
    }

    @Transactional
    def update(ProductMortgageRate productMortgageRate)
    {
        if (productMortgageRate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (productMortgageRate.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond productMortgageRate.errors, view: 'edit'
            return
        }

        productMortgageRate.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'productMortgageRate' + '.label', default: 'ProductMortgageRate'), productMortgageRate.id])
                redirect productMortgageRate
            }
            '*' { respond productMortgageRate, [status: OK] }
        }
    }

    @Transactional
    def delete(ProductMortgageRate productMortgageRate)
    {

        if (productMortgageRate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        productMortgageRate.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'productMortgageRate' + '.label', default: 'ProductMortgageRate'), productMortgageRate.id])
                redirect(controller: "productAccount", action: "show", id: productMortgageRate.product.id)
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'productMortgageRate' + '.label', default: 'ProductMortgageRate'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

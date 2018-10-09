package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
@Transactional(readOnly = true)
class FlexFieldCategoryController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond FlexFieldCategory.list(params), model: [flexFieldCategoryCount: FlexFieldCategory.count()]
    }

    def show(FlexFieldCategory flexFieldCategory)
    {
        respond flexFieldCategory
    }

    def create()
    {
        respond new FlexFieldCategory(params)
    }

    @Transactional
    def save(FlexFieldCategory flexFieldCategory)
    {
        if (flexFieldCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (flexFieldCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond flexFieldCategory.errors, view: 'create'
            return
        }

        flexFieldCategory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'flexFieldCategory.label', default: 'FlexFieldCategory'), flexFieldCategory.id])
                redirect flexFieldCategory
            }
            '*' { respond flexFieldCategory, [status: CREATED] }
        }
    }

    def edit(FlexFieldCategory flexFieldCategory)
    {
        respond flexFieldCategory
    }

    @Transactional
    def update(FlexFieldCategory flexFieldCategory)
    {
        if (flexFieldCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (flexFieldCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond flexFieldCategory.errors, view: 'edit'
            return
        }

        flexFieldCategory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'flexFieldCategory.label', default: 'FlexFieldCategory'), flexFieldCategory.id])
                redirect flexFieldCategory
            }
            '*' { respond flexFieldCategory, [status: OK] }
        }
    }

    @Transactional
    def delete(FlexFieldCategory flexFieldCategory)
    {

        if (flexFieldCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        flexFieldCategory.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'flexFieldCategory.label', default: 'FlexFieldCategory'), flexFieldCategory.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'flexFieldCategory.label', default: 'FlexFieldCategory'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    /*
    *查询
    *author xiaruikun
    * */

    @Transactional
    def searchFieldCategory()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def name = params["name"]
        String sql = "from FlexFieldCategory as f where 1=1"
        if (name)
        {
            sql += " and f.name like '%${name}%'"
        }

        sql += ' order by id desc'

        println "sql:" + sql

        def max = params.max
        def offset = params.offset

        def list = FlexFieldCategory.findAll(sql, [max: max, offset: offset])
        def list1 = FlexFieldCategory.findAll(sql)
        def count = list1.size()

        respond list, model: [flexFieldCategoryCount: count, params: params], view: 'index'
    }
}

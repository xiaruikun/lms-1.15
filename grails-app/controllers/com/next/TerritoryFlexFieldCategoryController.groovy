package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class TerritoryFlexFieldCategoryController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryFlexFieldCategory.list(params), model: [territoryFlexFieldCategoryCount: TerritoryFlexFieldCategory.count()]
    }

    def show(TerritoryFlexFieldCategory territoryFlexFieldCategory)
    {
        respond territoryFlexFieldCategory
    }

    def create()
    {
        def flexFieldCategories = TerritoryFlexFieldCategory.findAllByTerritoryAndOpportunityType(Territory.findById(params["territory"]), OpportunityType.findById(params["opportunityType"]))
        def flexFieldCategoryList = FlexFieldCategory.findAll()
        if (flexFieldCategories)
        {
            def sqlStr = "("
            flexFieldCategories.each {
                sqlStr += it.flexFieldCategory.id + ","
            }
            sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf(","))
            sqlStr += ")"
            flexFieldCategoryList = FlexFieldCategory.findAll("from FlexFieldCategory as f where f.id not in ${sqlStr}")
        }
        respond new TerritoryFlexFieldCategory(params), model: [flexFieldCategoryList: flexFieldCategoryList]
    }

    def selectFlexFieldCategory()
    {
        def flexFieldCategories = TerritoryFlexFieldCategory.findAllByTerritoryAndOpportunityType(Territory.findById(params["territory"]), OpportunityType.findById(params["opportunityType"]))
        def flexFieldCategoryList = FlexFieldCategory.findAll()
        if (flexFieldCategories)
        {
            def sqlStr = "("
            flexFieldCategories.each {
                sqlStr += it.flexFieldCategory.id + ","
            }
            sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf(","))
            sqlStr += ")"
            flexFieldCategoryList = FlexFieldCategory.findAll("from FlexFieldCategory as f where f.id not in ${sqlStr}")
        }
        render([status: "success", flexFieldCategoryList: flexFieldCategoryList] as JSON)
    }

    @Transactional
    def save(TerritoryFlexFieldCategory territoryFlexFieldCategory)
    {
        if (territoryFlexFieldCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryFlexFieldCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryFlexFieldCategory.errors, view: 'create'
            return
        }

        territoryFlexFieldCategory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryFlexFieldCategory.label', default: 'TerritoryFlexFieldCategory'), territoryFlexFieldCategory.id])
                redirect controller: "territory", action: "show", method: "GET", id: territoryFlexFieldCategory.territory.id
            }
            '*' { respond territoryFlexFieldCategory, [status: CREATED] }
        }
    }

    def edit(TerritoryFlexFieldCategory territoryFlexFieldCategory)
    {
        respond territoryFlexFieldCategory
    }

    @Transactional
    def update(TerritoryFlexFieldCategory territoryFlexFieldCategory)
    {
        if (territoryFlexFieldCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryFlexFieldCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryFlexFieldCategory.errors, view: 'edit'
            return
        }

        territoryFlexFieldCategory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryFlexFieldCategory.label', default: 'TerritoryFlexFieldCategory'), territoryFlexFieldCategory.id])
                redirect territoryFlexFieldCategory
            }
            '*' { respond territoryFlexFieldCategory, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryFlexFieldCategory territoryFlexFieldCategory)
    {

        if (territoryFlexFieldCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryFlexFieldCategory.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryFlexFieldCategory.label', default: 'TerritoryFlexFieldCategory'), territoryFlexFieldCategory.id])
                redirect controller: "territory", action: "show", method: "GET", id: territoryFlexFieldCategory.territory.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryFlexFieldCategory.label', default: 'TerritoryFlexFieldCategory'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

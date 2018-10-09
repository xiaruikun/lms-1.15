package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class TerritoryCityController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('permitAll')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryCity.list(params), model: [territoryCityCount: TerritoryCity.count()]
    }

    @Secured('permitAll')
    def show(TerritoryCity territoryCity)
    {
        respond territoryCity
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new TerritoryCity(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(TerritoryCity territoryCity)
    {
        if (territoryCity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryCity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryCity.errors, view: 'create'
            return
        }

        territoryCity.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryCity.label',
                                                                                        default: 'TerritoryCity'),
                    territoryCity.id])
                // redirect territoryCity
                redirect controller: "territory", action: "show", method: "GET", id: territoryCity.territory.id
            }
            '*' { respond territoryCity, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(TerritoryCity territoryCity)
    {
        respond territoryCity
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(TerritoryCity territoryCity)
    {
        if (territoryCity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryCity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryCity.errors, view: 'edit'
            return
        }

        territoryCity.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryCity.label',
                                                                                        default: 'TerritoryCity'),
                    territoryCity.id])
                redirect territoryCity
            }
            '*' { respond territoryCity, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(TerritoryCity territoryCity)
    {

        if (territoryCity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryCity.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryCity.label',
                                                                                        default: 'TerritoryCity'),
                    territoryCity.id])
                // redirect action: "index", method: "GET"
                redirect controller: "territory", action: "show", method: "GET", id: territoryCity.territory.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryCity' + '.label', default: 'TerritoryCity'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

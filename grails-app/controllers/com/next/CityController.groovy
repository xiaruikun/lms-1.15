package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class CityController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('permitAll')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond City.list(params), model: [cityCount: City.count()]
    }

    @Secured('permitAll')
    def show(City city)
    {
        respond city
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new City(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(City city)
    {
        if (city == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (city.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond city.errors, view: 'create'
            return
        }

        city.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'city.label', default: 'City'), city.id])
                redirect city
            }
            '*' { respond city, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(City city)
    {
        respond city
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(City city)
    {
        if (city == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (city.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond city.errors, view: 'edit'
            return
        }

        city.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'city.label', default: 'City'), city.id])
                redirect city
            }
            '*' { respond city, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(City city)
    {

        if (city == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        city.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'city.label', default: 'City'), city.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'city.label',
                                                                                          default: 'City'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def searchCity()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def name = params["name"]
        def telephone = params["telephone"]

        String sql = "from City as u where 1=1"
        if (name)
        {
            sql += " and u.name like '%${name}%'"
        }
        if (telephone)
        {
            sql += " and u.telephone = '${telephone}'"
        }

        sql += ' order by createdDate desc'

        println "sql:" + sql

        def max = params.max
        def offset = params.offset

        def list = City.findAll(sql, [max: max, offset: offset])
        def list1 = City.findAll(sql)
        def count = list1.size()

        respond list, model: [cityCount: count, params: params], view: 'index'
    }
}

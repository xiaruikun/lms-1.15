package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class DistrictController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('permitAll')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond District.list(params), model: [districtCount: District.count()]
    }

    @Secured('permitAll')
    def show(District district)
    {
        respond district
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new District(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(District district)
    {
        if (district == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (district.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond district.errors, view: 'create'
            return
        }

        district.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'district.label',
                                                                                        default: 'District'),
                    district.id])
                redirect district
            }
            '*' { respond district, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(District district)
    {
        respond district
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(District district)
    {
        if (district == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (district.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond district.errors, view: 'edit'
            return
        }

        district.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'district.label',
                                                                                        default: 'District'),
                    district.id])
                redirect district
            }
            '*' { respond district, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(District district)
    {

        if (district == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        district.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'district.label',
                                                                                        default: 'District'),
                    district.id])
                // redirect action: "index", method: "GET"
                redirect controller: "city", action: "show", method: "GET", id: district?.city?.id

            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'district.label',
                                                                                          default: 'District'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def searchDistrict()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def name = params["name"]
        def city = params["city"]

        String sql = "from District as u where 1=1"
        if (name)
        {
            sql += " and u.name like '%${name}%'"
        }
        if (city)
        {
            sql += " and u.city.name = '${city}'"
        }

        sql += ' order by createdDate desc'

        println "sql:" + sql

        def max = params.max
        def offset = params.offset

        def list = District.findAll(sql, [max: max, offset: offset])
        def list1 = District.findAll(sql)
        def count = list1.size()

        respond list, model: [districtCount: count, params: params], view: 'index'
    }
}

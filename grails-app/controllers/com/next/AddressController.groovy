package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class AddressController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('permitAll')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Address.list(params), model: [addressCount: Address.count()]
    }

    @Secured('permitAll')
    def show(Address address)
    {
        respond address
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new Address(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(Address address)
    {
        if (address == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (address.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond address.errors, view: 'create'
            return
        }

        address.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'address.label',
                                                                                        default: 'Address'), address
                    .id])
                redirect address
            }
            '*' { respond address, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(Address address)
    {
        respond address
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(Address address)
    {
        if (address == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (address.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond address.errors, view: 'edit'
            return
        }

        address.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'address.label',
                                                                                        default: 'Address'), address
                    .id])
                redirect address
            }
            '*' { respond address, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(Address address)
    {

        if (address == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        address.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'address.label',
                                                                                        default: 'Address'), address
                    .id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'address.label',
                                                                                          default: 'Address'), params
                    .id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def searchAddress()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def name = params["name"]
        def address = params["address"]
        def type = params["type"]
        def district = params["district"]

        String sql = "from Address as u where 1=1"
        if (name)
        {
            sql += " and u.name like '%${name}%'"
        }
        if (address)
        {
            sql += " and u.address like '%${address}%'"
        }
        if (type)
        {
            sql += " and u.type.name = '${type}'"
        }
        if (district)
        {
            sql += " and u.district.name = '${district}'"
        }

        sql += ' order by createdDate desc'

        println "sql:" + sql

        def max = params.max
        def offset = params.offset

        def list = Address.findAll(sql, [max: max, offset: offset])
        def list1 = Address.findAll(sql)
        def count = list1.size()

        respond list, model: [addressCount: count, params: params], view: 'index'
    }
}

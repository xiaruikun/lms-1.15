package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class HouseTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond HouseType.list(params), model: [houseTypeCount: HouseType.count()]
    }

    def show(HouseType houseType)
    {
        respond houseType
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new HouseType(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(HouseType houseType)
    {
        if (houseType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (houseType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond houseType.errors, view: 'create'
            return
        }

        houseType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'houseType.label',
                                                                                        default: 'HouseType'),
                    houseType.id])
                redirect houseType
            }
            '*' { respond houseType, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(HouseType houseType)
    {
        respond houseType
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(HouseType houseType)
    {
        if (houseType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (houseType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond houseType.errors, view: 'edit'
            return
        }

        houseType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'houseType.label',
                                                                                        default: 'HouseType'),
                    houseType.id])
                redirect houseType
            }
            '*' { respond houseType, [status: OK] }
        }
    }

    @Transactional
    def delete(HouseType houseType)
    {

        if (houseType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        houseType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'houseType.label',
                                                                                        default: 'HouseType'),
                    houseType.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'houseType.label',
                                                                                          default: 'HouseType'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

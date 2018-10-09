package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class AddressTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('permitAll')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AddressType.list(params), model: [addressTypeCount: AddressType.count()]
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def show(AddressType addressType)
    {
        respond addressType
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new AddressType(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(AddressType addressType)
    {
        if (addressType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (addressType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond addressType.errors, view: 'create'
            return
        }

        addressType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'addressType.label',
                                                                                        default: 'AddressType'),
                    addressType.id])
                redirect addressType
            }
            '*' { respond addressType, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(AddressType addressType)
    {
        respond addressType
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(AddressType addressType)
    {
        if (addressType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (addressType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond addressType.errors, view: 'edit'
            return
        }

        addressType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'addressType.label',
                                                                                        default: 'AddressType'),
                    addressType.id])
                redirect addressType
            }
            '*' { respond addressType, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(AddressType addressType)
    {

        if (addressType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        addressType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'addressType.label',
                                                                                        default: 'AddressType'),
                    addressType.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'addressType.label',
                                                                                          default: 'AddressType'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

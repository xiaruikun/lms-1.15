package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class ContactLevelController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ContactLevel.list(params), model: [contactLevelCount: ContactLevel.count()]
    }

    def show(ContactLevel contactLevel)
    {
        respond contactLevel
    }

    def create()
    {
        respond new ContactLevel(params)
    }

    @Transactional
    def save(ContactLevel contactLevel)
    {
        if (contactLevel == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactLevel.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactLevel.errors, view: 'create'
            return
        }

        contactLevel.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contactLevel.label',
                                                                                        default: 'ContactLevel'),
                    contactLevel.id])
                redirect contactLevel
            }
            '*' { respond contactLevel, [status: CREATED] }
        }
    }

    def edit(ContactLevel contactLevel)
    {
        respond contactLevel
    }

    @Transactional
    def update(ContactLevel contactLevel)
    {
        if (contactLevel == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactLevel.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactLevel.errors, view: 'edit'
            return
        }

        contactLevel.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contactLevel.label',
                                                                                        default: 'ContactLevel'),
                    contactLevel.id])
                redirect contactLevel
            }
            '*' { respond contactLevel, [status: OK] }
        }
    }

    @Transactional
    def delete(ContactLevel contactLevel)
    {

        if (contactLevel == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contactLevel.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contactLevel.label',
                                                                                        default: 'ContactLevel'),
                    contactLevel.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactLevel.label',
                                                                                          default: 'ContactLevel'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

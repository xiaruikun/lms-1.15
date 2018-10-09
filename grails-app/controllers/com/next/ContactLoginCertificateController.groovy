package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ContactLoginCertificateController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMINISTRATOR'])
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ContactLoginCertificate.list(params), model: [contactLoginCertificateCount: ContactLoginCertificate
            .count()]
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def show(ContactLoginCertificate contactLoginCertificate)
    {
        respond contactLoginCertificate
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new ContactLoginCertificate(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(ContactLoginCertificate contactLoginCertificate)
    {
        if (contactLoginCertificate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactLoginCertificate.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactLoginCertificate.errors, view: 'create'
            return
        }

        contactLoginCertificate.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contactLoginCertificate' + '.label', default: 'ContactLoginCertificate'), contactLoginCertificate.id])
                redirect contactLoginCertificate
            }
            '*' { respond contactLoginCertificate, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(ContactLoginCertificate contactLoginCertificate)
    {
        respond contactLoginCertificate
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(ContactLoginCertificate contactLoginCertificate)
    {
        if (contactLoginCertificate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactLoginCertificate.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactLoginCertificate.errors, view: 'edit'
            return
        }

        contactLoginCertificate.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contactLoginCertificate' + '.label', default: 'ContactLoginCertificate'), contactLoginCertificate.id])
                redirect contactLoginCertificate
            }
            '*' { respond contactLoginCertificate, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(ContactLoginCertificate contactLoginCertificate)
    {

        if (contactLoginCertificate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contactLoginCertificate.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contactLoginCertificate' + '.label', default: 'ContactLoginCertificate'), contactLoginCertificate.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Secured('permitAll')
    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactLoginCertificate.label', default: 'ContactLoginCertificate'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

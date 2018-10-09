package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class SecurityProfileController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond SecurityProfile.list(params), model: [securityProfileCount: SecurityProfile.count()]
    }

    def show(SecurityProfile securityProfile)
    {
        respond securityProfile
    }

    def create()
    {
        respond new SecurityProfile(params)
    }

    @Transactional
    def save(SecurityProfile securityProfile)
    {
        if (securityProfile == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (securityProfile.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond securityProfile.errors, view: 'create'
            return
        }

        securityProfile.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'securityProfile.label', default: 'SecurityProfile'), securityProfile.id])
                redirect securityProfile
            }
            '*' { respond securityProfile, [status: CREATED] }
        }
    }

    def edit(SecurityProfile securityProfile)
    {
        respond securityProfile
    }

    @Transactional
    def update(SecurityProfile securityProfile)
    {
        if (securityProfile == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (securityProfile.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond securityProfile.errors, view: 'edit'
            return
        }

        securityProfile.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'securityProfile.label', default: 'SecurityProfile'), securityProfile.id])
                redirect securityProfile
            }
            '*' { respond securityProfile, [status: OK] }
        }
    }

    @Transactional
    def delete(SecurityProfile securityProfile)
    {

        if (securityProfile == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        securityProfile.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'securityProfile.label', default: 'SecurityProfile'), securityProfile.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityProfile.label', default: 'SecurityProfile'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

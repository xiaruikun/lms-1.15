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
class SecurityProfileItemController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond SecurityProfileItem.list(params), model: [securityProfileItemCount: SecurityProfileItem.count()]
    }

    def show(SecurityProfileItem securityProfileItem)
    {
        respond securityProfileItem
    }

    def create()
    {
        respond new SecurityProfileItem(params)
    }

    @Transactional
    def save(SecurityProfileItem securityProfileItem)
    {
        if (securityProfileItem == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (securityProfileItem.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond securityProfileItem.errors, view: 'create'
            return
        }

        securityProfileItem.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'securityProfileItem.label', default: 'SecurityProfileItem'), securityProfileItem.id])
                // redirect securityProfileItem
                redirect controller: "securityProfile", action: "show", method: "GET", id: securityProfileItem?.profile?.id
            }
            '*' { respond securityProfileItem, [status: CREATED] }
        }
    }

    def edit(SecurityProfileItem securityProfileItem)
    {
        respond securityProfileItem
    }

    @Transactional
    def update(SecurityProfileItem securityProfileItem)
    {
        if (securityProfileItem == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (securityProfileItem.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond securityProfileItem.errors, view: 'edit'
            return
        }

        securityProfileItem.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'securityProfileItem.label', default: 'SecurityProfileItem'), securityProfileItem.id])
                redirect securityProfileItem
            }
            '*' { respond securityProfileItem, [status: OK] }
        }
    }

    @Transactional
    def delete(SecurityProfileItem securityProfileItem)
    {

        if (securityProfileItem == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        securityProfileItem.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'securityProfileItem.label', default: 'SecurityProfileItem'), securityProfileItem.id])
                // redirect action: "index", method: "GET"
                redirect controller: "securityProfile", action: "show", method: "GET", id: securityProfileItem?.profile?.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityProfileItem.label', default: 'SecurityProfileItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

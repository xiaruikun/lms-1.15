package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class CollateralAuditTrailController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond CollateralAuditTrail.list(params), model: [CollateralAuditTrailCount: CollateralAuditTrail.count()]
    }

    def show(CollateralAuditTrail CollateralAuditTrail)
    {
        respond CollateralAuditTrail
    }

    def create()
    {
        respond new CollateralAuditTrail(params)
    }

    @Transactional
    def save(CollateralAuditTrail CollateralAuditTrail)
    {
        if (CollateralAuditTrail == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (CollateralAuditTrail.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond CollateralAuditTrail.errors, view: 'create'
            return
        }

        CollateralAuditTrail.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'CollateralAuditTrail.label', default: 'CollateralAuditTrail'), CollateralAuditTrail.id])
                redirect CollateralAuditTrail
            }
            '*' { respond CollateralAuditTrail, [status: CREATED] }
        }
    }

    def edit(CollateralAuditTrail CollateralAuditTrail)
    {
        respond CollateralAuditTrail
    }

    @Transactional
    def update(CollateralAuditTrail CollateralAuditTrail)
    {
        if (CollateralAuditTrail == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (CollateralAuditTrail.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond CollateralAuditTrail.errors, view: 'edit'
            return
        }

        CollateralAuditTrail.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'CollateralAuditTrail.label', default: 'CollateralAuditTrail'), CollateralAuditTrail.id])
                redirect CollateralAuditTrail
            }
            '*' { respond CollateralAuditTrail, [status: OK] }
        }
    }

    @Transactional
    def delete(CollateralAuditTrail CollateralAuditTrail)
    {

        if (CollateralAuditTrail == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        CollateralAuditTrail.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CollateralAuditTrail.label', default: 'CollateralAuditTrail'), CollateralAuditTrail.id])
                // redirect action:"index", method:"GET"
                redirect controller: "opportunity", action: "historyShow2", method: "GET", id: CollateralAuditTrail?.parent?.id

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'CollateralAuditTrail.label', default: 'CollateralAuditTrail'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

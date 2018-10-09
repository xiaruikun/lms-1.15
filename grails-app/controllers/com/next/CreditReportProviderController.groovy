package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class CreditReportProviderController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond CreditReportProvider.list(params), model: [creditReportProviderCount: CreditReportProvider.count()]
    }

    def show(CreditReportProvider creditReportProvider)
    {
        respond creditReportProvider
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new CreditReportProvider(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(CreditReportProvider creditReportProvider)
    {
        if (creditReportProvider == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (creditReportProvider.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond creditReportProvider.errors, view: 'create'
            return
        }

        creditReportProvider.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'creditReportProvider' + '.label', default: 'CreditReportProvider'),
                    creditReportProvider.id])
                redirect creditReportProvider
            }
            '*' { respond creditReportProvider, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(CreditReportProvider creditReportProvider)
    {
        respond creditReportProvider
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(CreditReportProvider creditReportProvider)
    {
        if (creditReportProvider == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (creditReportProvider.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond creditReportProvider.errors, view: 'edit'
            return
        }

        creditReportProvider.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'creditReportProvider' + '.label', default: 'CreditReportProvider'),
                    creditReportProvider.id])
                redirect creditReportProvider
            }
            '*' { respond creditReportProvider, [status: OK] }
        }
    }

    @Transactional
    def delete(CreditReportProvider creditReportProvider)
    {

        if (creditReportProvider == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        creditReportProvider.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'creditReportProvider' + '.label', default: 'CreditReportProvider'),
                    creditReportProvider.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'creditReportProvider' + '.label', default: 'CreditReportProvider')
                    , params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

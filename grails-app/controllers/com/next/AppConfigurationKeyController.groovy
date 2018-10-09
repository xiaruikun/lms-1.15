package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class AppConfigurationKeyController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AppConfigurationKey.list(params), model: [appConfigurationKeyCount: AppConfigurationKey.count()]
    }

    def show(AppConfigurationKey appConfigurationKey)
    {
        respond appConfigurationKey
    }

    def create()
    {
        respond new AppConfigurationKey(params)
    }

    @Transactional
    def save(AppConfigurationKey appConfigurationKey)
    {
        if (appConfigurationKey == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (appConfigurationKey.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond appConfigurationKey.errors, view: 'create'
            return
        }

        appConfigurationKey.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'appConfigurationKey.label', default: 'AppConfigurationKey'), appConfigurationKey.id])
                redirect appConfigurationKey
            }
            '*' { respond appConfigurationKey, [status: CREATED] }
        }
    }

    def edit(AppConfigurationKey appConfigurationKey)
    {
        respond appConfigurationKey
    }

    @Transactional
    def update(AppConfigurationKey appConfigurationKey)
    {
        if (appConfigurationKey == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (appConfigurationKey.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond appConfigurationKey.errors, view: 'edit'
            return
        }

        appConfigurationKey.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'appConfigurationKey.label', default: 'AppConfigurationKey'), appConfigurationKey.id])
                redirect appConfigurationKey
            }
            '*' { respond appConfigurationKey, [status: OK] }
        }
    }

    @Transactional
    def delete(AppConfigurationKey appConfigurationKey)
    {

        if (appConfigurationKey == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        appConfigurationKey.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'appConfigurationKey.label', default: 'AppConfigurationKey'), appConfigurationKey.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'appConfigurationKey.label', default: 'AppConfigurationKey'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

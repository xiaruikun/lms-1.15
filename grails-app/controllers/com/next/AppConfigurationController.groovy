package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class AppConfigurationController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AppConfiguration.list(params), model: [appConfigurationCount: AppConfiguration.count()]
    }

    def show(AppConfiguration appConfiguration)
    {
        respond appConfiguration
    }

    def create()
    {
        respond new AppConfiguration(params)
    }

    @Transactional
    def save(AppConfiguration appConfiguration)
    {
        if (appConfiguration == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (appConfiguration.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond appConfiguration.errors, view: 'create'
            return
        }

        appConfiguration.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'appConfiguration.label', default: 'AppConfiguration'), appConfiguration.id])
                redirect appConfiguration
            }
            '*' { respond appConfiguration, [status: CREATED] }
        }
    }

    def edit(AppConfiguration appConfiguration)
    {
        respond appConfiguration
    }

    @Transactional
    def update(AppConfiguration appConfiguration)
    {
        if (appConfiguration == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (appConfiguration.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond appConfiguration.errors, view: 'edit'
            return
        }

        appConfiguration.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'appConfiguration.label', default: 'AppConfiguration'), appConfiguration.id])
                redirect appConfiguration
            }
            '*' { respond appConfiguration, [status: OK] }
        }
    }

    @Transactional
    def delete(AppConfiguration appConfiguration)
    {

        if (appConfiguration == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        appConfiguration.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'appConfiguration.label', default: 'AppConfiguration'), appConfiguration.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'appConfiguration.label', default: 'AppConfiguration'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

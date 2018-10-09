package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class AppVersionController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AppVersion.list(params), model: [appVersionCount: AppVersion.count()]
    }

    def show(AppVersion appVersion)
    {
        respond appVersion
    }

    @Secured('permitAll')
    def touchDownload()
    {
        respond new AppVersion(params)
    }

    def create()
    {
        respond new AppVersion(params)
    }

    @Transactional
    def save(AppVersion appVersion)
    {
        if (appVersion == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (appVersion.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond appVersion.errors, view: 'create'
            return
        }

        appVersion.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'appVersion.label',
                                                                                        default: 'AppVersion'),
                    appVersion.id])
                redirect appVersion
            }
            '*' { respond appVersion, [status: CREATED] }
        }
    }

    def edit(AppVersion appVersion)
    {
        respond appVersion
    }

    @Transactional
    def update(AppVersion appVersion)
    {
        if (appVersion == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (appVersion.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond appVersion.errors, view: 'edit'
            return
        }

        appVersion.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'appVersion.label',
                                                                                        default: 'AppVersion'),
                    appVersion.id])
                redirect appVersion
            }
            '*' { respond appVersion, [status: OK] }
        }
    }

    @Transactional
    def delete(AppVersion appVersion)
    {

        if (appVersion == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        appVersion.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'appVersion.label',
                                                                                        default: 'AppVersion'),
                    appVersion.id])
                redirect action: "show", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'appVersion.label',
                                                                                          default: 'AppVersion'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

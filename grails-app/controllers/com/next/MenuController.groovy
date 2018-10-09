package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class MenuController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Menu.list(params), model: [menuCount: Menu.count()]
    }

    def show(Menu menu)
    {
        respond menu
    }

    def create()
    {
        respond new Menu(params)
    }

    @Transactional
    def save(Menu menu)
    {
        if (menu == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (menu.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond menu.errors, view: 'create'
            return
        }

        menu.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'menu.label', default: 'Menu'), menu.id])
                redirect menu
            }
            '*' { respond menu, [status: CREATED] }
        }
    }

    def edit(Menu menu)
    {
        respond menu
    }

    @Transactional
    def update(Menu menu)
    {
        if (menu == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (menu.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond menu.errors, view: 'edit'
            return
        }

        menu.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'menu.label', default: 'Menu'), menu.id])
                redirect menu
            }
            '*' { respond menu, [status: OK] }
        }
    }

    @Transactional
    def delete(Menu menu)
    {

        if (menu == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        menu.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'menu.label', default: 'Menu'), menu.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    /*@Transactional
    def batchDelete()
    {
        def menuIds = params['menuIds'].split(",")
        def menu
        menuIds.each {
            menu = Menu.findById(it)
            menu.delete()
        }
        def data = [status: "success"]
        render data as JSON
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }*/
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE'])
@Transactional(readOnly = true)
class ComponentController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Component.list(params), model: [componentCount: Component.count()]
    }

    def show(Component component)
    {
        respond component
    }

    def create()
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        def component = new Component(params)
        component.createBy = user
        component.modifyBy = user
        respond component
    }

    @Transactional
    def save(Component component)
    {
        if (component == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (component.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond component.errors, view: 'create'
            return
        }

        component.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'component.label', default: 'Component'), component.id])
                redirect component
            }
            '*' { respond component, [status: CREATED] }
        }
    }

    def edit(Component component)
    {
        respond component
    }

    @Transactional
    def update(Component component)
    {
        if (component == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (component.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond component.errors, view: 'edit'
            return
        }

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        component.modifyBy = user

        component.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'component.label', default: 'Component'), component.id])
                redirect component
            }
            '*' { respond component, [status: OK] }
        }
    }

    @Transactional
    def delete(Component component)
    {

        if (component == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        component.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'component.label', default: 'Component'), component.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def searchComponent()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def name = params["name"]
        def type = params["type"]

        String sql = "from Component as c where 1=1"
        if (name)
        {
            sql += " and c.name like '%${name}%'"
        }
        if (type && type != 'null')
        {
            sql += " and c.type.id = '${type}'"
        }

        def max = params.max
        def offset = params.offset

        def componentList = Component.findAll(sql, [max: max, offset: offset])
        def list1 = Component.findAll(sql)
        def count = list1.size()

        respond componentList, model: [componentCount: count, params: params], view: 'index'
    }
}

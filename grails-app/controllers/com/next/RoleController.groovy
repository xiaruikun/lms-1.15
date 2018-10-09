package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class RoleController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMINISTRATOR'])
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Role.list(params), model: [roleCount: Role.count()]
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def show(Role role)
    {
        def userRoleList = UserRole.findAllByRole(role)
        def userList = []
        userRoleList?.each {
            userList.add(it?.user)
        }
        userList.unique()
        respond role, model: [userList: userList]
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new Role(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(Role role)
    {
        if (role == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (role.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond role.errors, view: 'create'
            return
        }

        role.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), role.id])
                redirect role
            }
            '*' { respond role, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(Role role)
    {
        respond role
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(Role role)
    {
        if (role == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (role.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond role.errors, view: 'edit'
            return
        }

        role.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), role.id])
                redirect role
            }
            '*' { respond role, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(Role role)
    {

        if (role == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        role.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), role.id])
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
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label',
                                                                                          default: 'Role'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

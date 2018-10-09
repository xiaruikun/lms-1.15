package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class DepartmentController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Department.list(params), model: [departmentCount: Department.count()]
    }

    def show(Department department)
    {
        respond department
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new Department(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(Department department)
    {
        if (department == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (department.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond department.errors, view: 'create'
            return
        }

        department.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'department.label',
                                                                                        default: 'Department'),
                    department.id])
                redirect department
            }
            '*' { respond department, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(Department department)
    {
        respond department
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(Department department)
    {
        if (department == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (department.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond department.errors, view: 'edit'
            return
        }

        department.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'department.label',
                                                                                        default: 'Department'),
                    department.id])
                redirect department
            }
            '*' { respond department, [status: OK] }
        }
    }

    @Transactional
    def delete(Department department)
    {

        if (department == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        department.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'department.label',
                                                                                        default: 'Department'),
                    department.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'department.label',
                                                                                          default: 'Department'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

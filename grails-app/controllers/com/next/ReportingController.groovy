package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ReportingController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['permitAll'])
    @Transactional
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Reporting.list(params), model: [reportingCount: Reporting.count()]
    }

    @Secured(['permitAll'])
    @Transactional
    def show(Reporting reporting)
    {
        respond reporting
    }

    @Secured(['permitAll'])
    @Transactional
    def create()
    {
        def reportingList = Reporting.findAllByManager(User.findById(params["id"]))
        def userList = User.findAll("from User as u where u.id <> ${params["id"]}")
        if (reportingList)
        {
            def sqlStr = "("
            reportingList.each {
                sqlStr += it.user.id + ","
            }
            sqlStr += params["id"] + ")"
            userList = User.findAll("from User as u where u.id not in ${sqlStr}")
        }
        respond new Reporting(params), model: [userList: userList]
    }

    @Secured(['permitAll'])
    @Transactional
    def save(Reporting reporting)
    {
        if (reporting == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (reporting.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond reporting.errors, view: 'create'
            return
        }

        reporting.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reporting.label',
                                                                                        default: 'Reporting'),
                    reporting.id])
                redirect(controller: "user", action: "show", id: reporting.manager.id)
            }
            '*' { respond reporting, [status: CREATED] }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def edit(Reporting reporting)
    {
        respond reporting
    }

    @Secured(['permitAll'])
    @Transactional
    def update(Reporting reporting)
    {
        if (reporting == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (reporting.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond reporting.errors, view: 'edit'
            return
        }

        reporting.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'reporting.label',
                                                                                        default: 'Reporting'),
                    reporting.id])
                redirect reporting
            }
            '*' { respond reporting, [status: OK] }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def delete(Reporting reporting)
    {

        if (reporting == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        reporting.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporting.label',
                                                                                        default: 'Reporting'),
                    reporting.id])
                redirect(controller: "user", action: "show", id: reporting.manager.id)
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporting.label',
                                                                                          default: 'Reporting'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

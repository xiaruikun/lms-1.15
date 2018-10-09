package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class TeamRoleController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['permitAll'])
    @Transactional
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TeamRole.list(params), model: [teamRoleCount: TeamRole.count()]
    }

    @Secured(['permitAll'])
    @Transactional
    def show(TeamRole teamRole)
    {
        respond teamRole
    }

    @Secured(['permitAll'])
    @Transactional
    def create()
    {
        respond new TeamRole(params)
    }

    @Secured(['permitAll'])
    @Transactional
    def save(TeamRole teamRole)
    {
        if (teamRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (teamRole.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond teamRole.errors, view: 'create'
            return
        }

        teamRole.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'teamRole.label',
                                                                                        default: 'TeamRole'),
                    teamRole.id])
                redirect teamRole
            }
            '*' { respond teamRole, [status: CREATED] }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def edit(TeamRole teamRole)
    {
        respond teamRole
    }

    @Secured(['permitAll'])
    @Transactional
    def update(TeamRole teamRole)
    {
        if (teamRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (teamRole.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond teamRole.errors, view: 'edit'
            return
        }

        teamRole.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'teamRole.label',
                                                                                        default: 'TeamRole'),
                    teamRole.id])
                redirect teamRole
            }
            '*' { respond teamRole, [status: OK] }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def delete(TeamRole teamRole)
    {

        if (teamRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        teamRole.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'teamRole.label',
                                                                                        default: 'TeamRole'),
                    teamRole.id])
                redirect action: "index", method: "GET"
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
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'teamRole.label',
                                                                                          default: 'TeamRole'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

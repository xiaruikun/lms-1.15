package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_HR_MANAGER'])
@Transactional(readOnly = true)
class UserTeamController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def userTeamList = UserTeam.findAllByUser(user)
        respond userTeamList, model: [userTeamCount: userTeamList?.size()]
    }

    def show(UserTeam userTeam)
    {
        respond userTeam
    }

    def create()
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def userTeam = new UserTeam(user: user)
        respond userTeam
    }

    @Transactional
    def save(UserTeam userTeam)
    {
        if (userTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond userTeam.errors, view: 'create'
            return
        }
        if (userTeam?.member?.validate())
        {
            userTeam.save flush: true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'userTeam.label', default: 'UserTeam'), userTeam.id])
                    redirect userTeam
                }
                '*' { respond userTeam, [status: CREATED] }
            }
        }
        else
        {
            transactionStatus.setRollbackOnly()
            flash.message = userTeam?.member.errors
            respond userTeam, view: 'create'
            return
        }
    }

    def edit(UserTeam userTeam)
    {
        respond userTeam
    }

    @Transactional
    def update(UserTeam userTeam)
    {
        if (userTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond userTeam.errors, view: 'edit'
            return
        }
        if (userTeam?.member.validate())
        {
            userTeam.save flush: true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'userTeam.label', default: 'UserTeam'), userTeam.id])
                    redirect userTeam
                }
                '*' { respond userTeam, [status: OK] }
            }
        }
        else
        {
            transactionStatus.setRollbackOnly()
            flash.message = userTeam?.member.errors
            respond userTeam, view: 'edit'
            return
        }

    }

    @Transactional
    def delete(UserTeam userTeam)
    {

        if (userTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        userTeam.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userTeam.label', default: 'UserTeam'), userTeam.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userTeam.label', default: 'UserTeam'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

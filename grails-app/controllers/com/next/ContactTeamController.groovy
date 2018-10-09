package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Transactional(readOnly = true)
@Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
class ContactTeamController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        max = 10
        def offset = params.offset
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        // def contactTeamList = ContactTeam.findAll("from ContactTeam where user.id = ${user?.id}", [max: max, offset: offset])
        // def count = ContactTeam.findAll("from ContactTeam where user.id = ${user?.id}")?.size()
        // if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        // {
        //     contactTeamList = ContactTeam.list(params)
        //     count = ContactTeam.count()
        // }
        def contactTeamList
        def list
        def count
        def sql
        // sql = "select team.contact.id, team.contact.fullName, team.contact.user, team.teamRole.name from ContactTeam team join Reporting reporting on team.user.id = reporting.user.id where team.user.id = ${user?.id} or reporting.manager.id = ${user?.id}"
        sql = "select distinct team.contact.id, team.contact.fullName, team.contact.user, team.teamRole.name from ContactTeam team, Reporting report where team.user.id = ${user?.id} or (team.user.id = report.user.id and report.manager.id = ${user?.id})"
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            sql = "select team.contact.id, team.contact.fullName, team.contact.user, team.teamRole.name from ContactTeam team"
        }
        contactTeamList = ContactTeam.executeQuery(sql, [max: max, offset: offset])
        list = ContactTeam.executeQuery(sql)
        count = list?.size()

        respond contactTeamList, model: [contactTeamList: contactTeamList, contactTeamCount: count]
    }

    def show(ContactTeam contactTeam)
    {
        respond contactTeam
    }

    def create()
    {
        def contact = Contact.findById(params['contact'])
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        if (contact?.user == user || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            def userList = User.list()
            def contactTeams = ContactTeam.findAllByContact(contact)
            contactTeams?.each {
                userList -= it?.user
            }
            respond new ContactTeam(params), model: [userList: userList]
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "contact", action: "show", params: [id: contact.id])
            return
        }
    }

    @Transactional
    def save(ContactTeam contactTeam)
    {
        if (contactTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactTeam.errors, view: 'create'
            return
        }

        contactTeam.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contactTeam.label', default: 'ContactTeam'), contactTeam.id])
                // redirect contactTeam
                redirect(controller: "contact", action: "show", params: [id: contactTeam?.contact?.id])
            }
            '*' { respond contactTeam, [status: CREATED] }
        }
    }

    def edit(ContactTeam contactTeam)
    {
        respond contactTeam
    }

    @Transactional
    def update(ContactTeam contactTeam)
    {
        if (contactTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactTeam.errors, view: 'edit'
            return
        }

        contactTeam.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contactTeam.label', default: 'ContactTeam'), contactTeam.id])
                // redirect contactTeam
                redirect(controller: "contact", action: "show", params: [id: contactTeam?.contact?.id])
            }
            '*' { respond contactTeam, [status: OK] }
        }
    }

    @Transactional
    def delete(ContactTeam contactTeam)
    {

        if (contactTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        if (contactTeam?.contact?.user == user || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            contactTeam.delete flush: true
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'contactTeam.label', default: 'ContactTeam'), contactTeam.id])
                    // redirect action:"index", method:"GET"
                    redirect(controller: "contact", action: "show", params: [id: contactTeam?.contact?.id])
                }
                '*' { render status: NO_CONTENT }
            }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "contact", action: "show", params: [id: contactTeam?.contact.id])
            return
        }

    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactTeam.label', default: 'ContactTeam'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

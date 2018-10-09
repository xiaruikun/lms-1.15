package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class TerritoryRoleController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryRole.list(params), model: [territoryRoleCount: TerritoryRole.count()]
    }

    def show(TerritoryRole territoryRole)
    {
        respond territoryRole
    }

    def create()
    {
        def territory = Territory.findById(params["territory"])
        def userList = []
        def teamList

        teamList = TerritoryTeam.findAllByTerritory(territory)
        teamList.each {
            if (!userList.contains(it?.user))
            {
                userList.add(it?.user)
            }
        }

        while (territory)
        {
            if (territory?.inheritTeam && territory?.parent)
            {
                teamList = TerritoryTeam.findAllByTerritory(territory.parent)
                teamList.each {
                    if (!userList.contains(it?.user))
                    {
                        userList.add(it?.user)
                    }
                }
                territory = territory?.parent
            }
            else
            {
                break
            }
        }
        respond new TerritoryRole(params), model: [userList: userList]
    }

    @Transactional
    def save(TerritoryRole territoryRole)
    {
        if (territoryRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryRole.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryRole.errors, view: 'create'
            return
        }

        def userList = []
        def territory = territoryRole?.territory
        def stage = territoryRole?.stage
        def territoryRoles = TerritoryRole.findAllByTerritoryAndStage(territory, stage)
        territoryRoles.each {
            userList.add(it.user)
        }

        while (territory)
        {
            if (territory?.inheritRole && territory?.parent)
            {
                territoryRoles = TerritoryRole.findAllByTerritoryAndStage(territory.parent, stage)
                territoryRoles.each {
                    userList.add(it.user)
                }
                territory = territory?.parent
            }
            else
            {
                break
            }
        }
        if (userList.contains(territoryRole?.user))
        {
            flash.message = message(code: 'territoryRole.user.unique')
            redirect(action: "create", params: [territory: territoryRole.territory.id])
            return
        }

        territoryRole.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryRole.label',
                                                                                        default: 'TerritoryRole'),
                    territoryRole.id])
                // redirect territoryRole
                redirect controller: "territory", action: "show", method: "GET", id: territoryRole.territory.id
            }
            '*' { respond territoryRole, [status: CREATED] }
        }
    }

    def edit(TerritoryRole territoryRole)
    {
        respond territoryRole
    }

    @Transactional
    def update(TerritoryRole territoryRole)
    {
        if (territoryRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryRole.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryRole.errors, view: 'edit'
            return
        }

        territoryRole.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryRole.label',
                                                                                        default: 'TerritoryRole'),
                    territoryRole.id])
                redirect territoryRole
            }
            '*' { respond territoryRole, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryRole territoryRole)
    {

        if (territoryRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryRole.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryRole.label',
                                                                                        default: 'TerritoryRole'),
                    territoryRole.id])
                // redirect action:"index", method:"GET"
                redirect controller: "territory", action: "show", method: "GET", id: territoryRole.territory.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryRole' + '.label', default: 'TerritoryRole'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

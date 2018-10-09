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
class TerritoryTeamController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryTeam.list(params), model: [territoryTeamCount: TerritoryTeam.count()]
    }

    def show(TerritoryTeam territoryTeam)
    {
        respond territoryTeam
    }

    def create()
    {
        //已有userlist
        def exitList = []
        def territory = Territory.findById(params['territory'])
        def teams = TerritoryTeam.findAllByTerritory(territory)
        teams.each {
            exitList.add(it?.user?.id)
        }
        while (territory)
        {
            if (territory?.inheritTeam && territory?.parent)
            {
                teams = TerritoryTeam.findAllByTerritory(territory.parent)
                teams.each {
                    exitList.add(it?.user?.id)
                }
                territory = territory?.parent
            }
            else
            {
                break
            }
        }
        //去重
        def user = User.createCriteria()
        def userList
        if (exitList.size() > 0)
        {
            userList = user.list {
                not {
                    'in'("id", exitList)
                }
            }
        }
        else
        {
            userList = user.list {}
        }

        respond new TerritoryTeam(params), model: [userList: userList]
    }

    @Transactional
    def save(TerritoryTeam territoryTeam)
    {
        if (territoryTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryTeam.errors, view: 'create'
            return
        }

        def userList = []
        def territory = territoryTeam?.territory
        def territoryTeams = TerritoryTeam.findAllByTerritory(territory)
        territoryTeams.each {
            userList.add(it.user)
        }

        while (territory)
        {
            if (territory?.inheritTeam && territory?.parent)
            {
                territoryTeams = TerritoryTeam.findAllByTerritory(territory.parent)
                territoryTeams.each {
                    userList.add(it.user)
                }
                territory = territory?.parent
            }
            else
            {
                break
            }
        }
        if (userList.contains(territoryTeam?.user))
        {
            flash.message = message(code: 'territoryTeam.user.unique')
            respond territoryTeam.errors, view: 'create'
            return
        }

        territoryTeam.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryTeam.label',
                                                                                        default: 'TerritoryTeam'),
                    territoryTeam.id])
                // redirect territoryTeam
                redirect controller: "territory", action: "show", method: "GET", id: territoryTeam.territory.id
            }
            '*' { respond territoryTeam, [status: CREATED] }
        }
    }

    def edit(TerritoryTeam territoryTeam)
    {
        respond territoryTeam
    }

    @Transactional
    def update(TerritoryTeam territoryTeam)
    {
        if (territoryTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryTeam.errors, view: 'edit'
            return
        }

        territoryTeam.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryTeam.label',
                                                                                        default: 'TerritoryTeam'),
                    territoryTeam.id])
                // redirect territoryTeam
                redirect controller: "territory", action: "show", method: "GET", id: territoryTeam.territory.id
            }
            '*' { respond territoryTeam, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryTeam territoryTeam)
    {

        if (territoryTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryTeam.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryTeam.label',
                                                                                        default: 'TerritoryTeam'),
                    territoryTeam.id])
                // redirect action:"index", method:"GET"
                redirect controller: "territory", action: "show", method: "GET", id: territoryTeam.territory.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryTeam' + '.label', default: 'TerritoryTeam'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

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
class TerritoryNotificationController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TerritoryNotification.list(params), model: [territoryNotificationCount: TerritoryNotification.count()]
    }

    def show(TerritoryNotification territoryNotification)
    {
        respond territoryNotification
    }

    def create()
    {
        def territory = Territory.findById(params["territory"])
        def userList = []
        def teamList
        teamList = TerritoryTeam.findAllByTerritory(territory)
        teamList.each {
            userList.add(it?.user)
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
        respond new TerritoryNotification(params), model: [userList: userList]
    }

    @Transactional
    def save(TerritoryNotification territoryNotification)
    {
        if (territoryNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryNotification.errors, view: 'create'
            return
        }

        def notification = TerritoryNotification.find("from TerritoryNotification where user.id = ? and territory.id " + "= ? and stage.id = ?", [territoryNotification?.user?.id,
            territoryNotification?.territory?.id,
            territoryNotification?.stage?.id])
        if (notification)
        {
            flash.message = message(code: 'territoryNotification.user.unique')
            redirect(action: "create", params: [territory: territoryNotification.territory.id])
            return
        }
        else
        {
            territoryNotification.save flush: true
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territoryNotification' + '.label', default: 'TerritoryNotification'),
                    territoryNotification.id])
                // redirect territoryNotification
                redirect controller: "territory", action: "show", method: "GET", id: territoryNotification.territory.id
            }
            '*' { respond territoryNotification, [status: CREATED] }
        }
    }

    def edit(TerritoryNotification territoryNotification)
    {
        respond territoryNotification
    }

    @Transactional
    def update(TerritoryNotification territoryNotification)
    {
        if (territoryNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territoryNotification.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territoryNotification.errors, view: 'edit'
            return
        }

        territoryNotification.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territoryNotification' + '.label', default: 'TerritoryNotification'),
                    territoryNotification.id])
                redirect territoryNotification
            }
            '*' { respond territoryNotification, [status: OK] }
        }
    }

    @Transactional
    def delete(TerritoryNotification territoryNotification)
    {

        if (territoryNotification == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territoryNotification.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territoryNotification' + '.label', default: 'TerritoryNotification'),
                    territoryNotification.id])
                // redirect action:"index", method:"GET"
                redirect controller: "territory", action: "show", method: "GET", id: territoryNotification.territory.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territoryNotification' + '.label', default: 'TerritoryNotification'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_INVESTOR'])

class AssetPoolTeamController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AssetPoolTeam.list(params), model: [assetPoolTeamCount: AssetPoolTeam.count()]
    }

    def show(AssetPoolTeam assetPoolTeam)
    {
        respond assetPoolTeam
    }

    def create()
    {
        respond new AssetPoolTeam(params)
    }

    @Transactional
    def save(AssetPoolTeam assetPoolTeam)
    {

        if (assetPoolTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assetPoolTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond assetPoolTeam.errors, view: 'create'
            return
        }

        assetPoolTeam.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'assetPoolTeam.label', default: 'AssetPoolTeam'), assetPoolTeam.id])
                // redirect assetPoolTeam
                redirect controller: 'assetPool', action: 'show', id: assetPoolTeam.assetPool.id
            }
            '*' { respond assetPoolTeam, [status: CREATED] }
        }
    }

    def edit(AssetPoolTeam assetPoolTeam)
    {
        respond assetPoolTeam
    }

    @Transactional
    def update(AssetPoolTeam assetPoolTeam)
    {
        if (assetPoolTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assetPoolTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond assetPoolTeam.errors, view: 'edit'
            return
        }

        assetPoolTeam.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'assetPoolTeam.label', default: 'AssetPoolTeam'), assetPoolTeam.id])
                // redirect assetPoolTeam
                redirect controller: 'assetPool', action: 'show', id: assetPoolTeam.assetPool.id
            }
            '*' { respond assetPoolTeam, [status: OK] }
        }
    }

    @Transactional
    def delete(AssetPoolTeam assetPoolTeam)
    {

        if (assetPoolTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        assetPoolTeam.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'assetPoolTeam.label', default: 'AssetPoolTeam'), assetPoolTeam.id])
                // redirect action:"index", method:"GET"
                redirect controller: 'assetPool', action: 'show', id: assetPoolTeam.assetPool.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'assetPoolTeam.label', default: 'AssetPoolTeam'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

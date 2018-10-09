package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_INVESTOR'])


class AssetPoolController
{

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        String sql = "from AssetPool as c where id in (select assetPool.id from AssetPoolTeam where user.id = '${user?.id}')"
        def list = AssetPool.findAll(sql)
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            respond AssetPool.list(params), model: [assetPoolCount: AssetPool.count()]
        }
        else
        {
            respond list, model: [assetPoolCount: list?.size()]
            // respond list, model: [assetPoolCount: AssetPool.count()]
        }
    }

    def show(AssetPool assetPool)
    {
        respond assetPool
    }

    def create()
    {
        respond new AssetPool(params)
    }

    @Transactional
    def save(AssetPool assetPool)
    {
        if (assetPool == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assetPool.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond assetPool.errors, view: 'create'
            return
        }

        assetPool.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'assetPool.label', default: 'AssetPool'), assetPool.id])
                redirect assetPool
            }
            '*' { respond assetPool, [status: CREATED] }
        }
    }

    def edit(AssetPool assetPool)
    {
        respond assetPool
    }

    @Transactional
    def update(AssetPool assetPool)
    {
        if (assetPool == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assetPool.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond assetPool.errors, view: 'edit'
            return
        }

        assetPool.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'assetPool.label', default: 'AssetPool'), assetPool.id])
                redirect assetPool
            }
            '*' { respond assetPool, [status: OK] }
        }
    }

    @Transactional
    def delete(AssetPool assetPool)
    {

        if (assetPool == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        assetPool.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'assetPool.label', default: 'AssetPool'), assetPool.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'assetPool.label', default: 'AssetPool'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def searchAssetPool()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def name = params["name"]?.trim()

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        String sql = "from AssetPool as c where 1=1"
        if (name)
        {
            sql += " and c.name like '%${name}%'"
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_INVESTOR")))
        {
            sql += " and id in (select assetPool.id from AssetPoolTeam where user.id = '${user?.id}')"
        }

        def max = params.max
        def offset = params.offset

        def assetPoolList = AssetPool.findAll(sql, [max: max, offset: offset])
        def list1 = AssetPool.findAll(sql)
        def count = list1.size()

        respond assetPoolList, model: [componentCount: count, params: params], view: 'index'
    }
}

package com.next

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class AssetTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AssetType.list(params), model: [assetTypeCount: AssetType.count()]
    }

    def show(AssetType assetType)
    {
        respond assetType
    }

    def create()
    {
        respond new AssetType(params)
    }

    @Transactional
    def save(AssetType assetType)
    {
        if (assetType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assetType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond assetType.errors, view: 'create'
            return
        }

        assetType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'assetType.label',
                                                                                        default: 'AssetType'),
                    assetType.id])
                redirect assetType
            }
            '*' { respond assetType, [status: CREATED] }
        }
    }

    def edit(AssetType assetType)
    {
        respond assetType
    }

    @Transactional
    def update(AssetType assetType)
    {
        if (assetType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assetType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond assetType.errors, view: 'edit'
            return
        }

        assetType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'assetType.label',
                                                                                        default: 'AssetType'),
                    assetType.id])
                redirect assetType
            }
            '*' { respond assetType, [status: OK] }
        }
    }

    @Transactional
    def delete(AssetType assetType)
    {

        if (assetType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        assetType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'assetType.label',
                                                                                        default: 'AssetType'),
                    assetType.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'assetType.label',
                                                                                          default: 'AssetType'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

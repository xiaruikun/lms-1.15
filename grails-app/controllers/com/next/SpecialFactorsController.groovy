package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class SpecialFactorsController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond SpecialFactors.list(params), model: [specialFactorsCount: SpecialFactors.count()]
    }

    def show(SpecialFactors specialFactors)
    {
        respond specialFactors
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def create()
    {
        respond new SpecialFactors(params)
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def save(SpecialFactors specialFactors)
    {
        if (specialFactors == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (specialFactors.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond specialFactors.errors, view: 'create'
            return
        }

        specialFactors.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'specialFactors.label',
                                                                                        default: 'SpecialFactors'),
                    specialFactors.id])
                redirect specialFactors
            }
            '*' { respond specialFactors, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def edit(SpecialFactors specialFactors)
    {
        respond specialFactors
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def update(SpecialFactors specialFactors)
    {
        if (specialFactors == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (specialFactors.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond specialFactors.errors, view: 'edit'
            return
        }

        specialFactors.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'specialFactors.label',
                                                                                        default: 'SpecialFactors'),
                    specialFactors.id])
                redirect specialFactors
            }
            '*' { respond specialFactors, [status: OK] }
        }
    }

    @Transactional
    def delete(SpecialFactors specialFactors)
    {

        if (specialFactors == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        specialFactors.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'specialFactors.label',
                                                                                        default: 'SpecialFactors'),
                    specialFactors.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'specialFactors' + '.label', default: 'SpecialFactors'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

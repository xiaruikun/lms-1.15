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
class ContractTemplateOptionsController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ContractTemplateOptions.list(params), model: [contractTemplateOptionsCount: ContractTemplateOptions.count()]
    }

    def show(ContractTemplateOptions contractTemplateOptions)
    {
        respond contractTemplateOptions
    }

    def create()
    {
        respond new ContractTemplateOptions(params)
    }

    @Transactional
    def save(ContractTemplateOptions contractTemplateOptions)
    {
        if (contractTemplateOptions == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contractTemplateOptions.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contractTemplateOptions.errors, view: 'create'
            return
        }

        contractTemplateOptions.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contractTemplateOptions.label', default: 'ContractTemplateOptions'), contractTemplateOptions.id])
                // redirect contractTemplateOptions
                redirect(controller: "contractTemplate", action: "show", params: [id: contractTemplateOptions?.template?.id])
            }
            '*' { respond contractTemplateOptions, [status: CREATED] }
        }
    }

    def edit(ContractTemplateOptions contractTemplateOptions)
    {
        respond contractTemplateOptions
    }

    @Transactional
    def update(ContractTemplateOptions contractTemplateOptions)
    {
        if (contractTemplateOptions == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contractTemplateOptions.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contractTemplateOptions.errors, view: 'edit'
            return
        }

        contractTemplateOptions.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contractTemplateOptions.label', default: 'ContractTemplateOptions'), contractTemplateOptions.id])
                redirect contractTemplateOptions
            }
            '*' { respond contractTemplateOptions, [status: OK] }
        }
    }

    @Transactional
    def delete(ContractTemplateOptions contractTemplateOptions)
    {

        if (contractTemplateOptions == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contractTemplateOptions.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contractTemplateOptions.label', default: 'ContractTemplateOptions'), contractTemplateOptions.id])
                // redirect action:"index", method:"GET"
                redirect(controller: "contractTemplate", action: "show", params: [id: contractTemplateOptions?.template?.id])

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contractTemplateOptions.label', default: 'ContractTemplateOptions'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

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
class ContractTemplateController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ContractTemplate.list(params), model: [contractTemplateCount: ContractTemplate.count()]
    }

    def show(ContractTemplate contractTemplate)
    {
        def contractTemplateOptions = ContractTemplateOptions.findAllByTemplate(contractTemplate)
        respond contractTemplate, model: [contractTemplateOptions: contractTemplateOptions]
    }

    def create()
    {
        respond new ContractTemplate(params)
    }

    @Transactional
    def save(ContractTemplate contractTemplate)
    {
        if (contractTemplate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contractTemplate.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contractTemplate.errors, view: 'create'
            return
        }

        contractTemplate.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contractTemplate.label', default: 'ContractTemplate'), contractTemplate.id])
                // redirect contractTemplate
                redirect(controller: "contractType", action: "show", params: [id: contractTemplate?.type?.id])
            }
            '*' { respond contractTemplate, [status: CREATED] }
        }
    }

    def edit(ContractTemplate contractTemplate)
    {
        respond contractTemplate
    }

    @Transactional
    def update(ContractTemplate contractTemplate)
    {
        if (contractTemplate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contractTemplate.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contractTemplate.errors, view: 'edit'
            return
        }

        contractTemplate.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contractTemplate.label', default: 'ContractTemplate'), contractTemplate.id])
                // redirect contractTemplate
                redirect(controller: "contractType", action: "show", params: [id: contractTemplate?.type?.id])

            }
            '*' { respond contractTemplate, [status: OK] }
        }
    }

    @Transactional
    def delete(ContractTemplate contractTemplate)
    {

        if (contractTemplate == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contractTemplate.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contractTemplate.label', default: 'ContractTemplate'), contractTemplate.id])
                // redirect action:"index", method:"GET"
                redirect(controller: "contractType", action: "show", params: [id: contractTemplate?.type?.id])

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contractTemplate.label', default: 'ContractTemplate'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

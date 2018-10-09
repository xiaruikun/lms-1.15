package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class ContractTypeController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ContractType.list(params), model: [contractTypeCount: ContractType.count()]
    }

    def show(ContractType contractType)
    {
        def contractTemplates = ContractTemplate.findAllByType(contractType)
        respond contractType, model: [contractTemplates: contractTemplates]
    }

    def create()
    {
        respond new ContractType(params)
    }

    @Transactional
    def save(ContractType contractType)
    {
        if (contractType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contractType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contractType.errors, view: 'create'
            return
        }

        contractType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contractType.label', default: 'ContractType'), contractType.id])
                redirect contractType
            }
            '*' { respond contractType, [status: CREATED] }
        }
    }

    def edit(ContractType contractType)
    {
        respond contractType
    }

    @Transactional
    def update(ContractType contractType)
    {
        if (contractType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contractType.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contractType.errors, view: 'edit'
            return
        }

        contractType.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contractType.label', default: 'ContractType'), contractType.id])
                redirect contractType
            }
            '*' { respond contractType, [status: OK] }
        }
    }

    @Transactional
    def delete(ContractType contractType)
    {

        if (contractType == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contractType.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contractType.label', default: 'ContractType'), contractType.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contractType.label', default: 'ContractType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

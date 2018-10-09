package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['permitAll'])

@Transactional(readOnly = true)
class ContactJudgementRecordController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ContactJudgementRecord.list(params), model: [contactJudgementRecordCount: ContactJudgementRecord.count()]
    }

    def show(ContactJudgementRecord contactJudgementRecord)
    {
        respond contactJudgementRecord
    }

    def create()
    {
        def opportunityContact = OpportunityContact.findById(params['opportunityContact'])
        respond new ContactJudgementRecord(params), model: [opportunityContact: opportunityContact]
    }

    @Transactional
    def save(ContactJudgementRecord contactJudgementRecord)
    {
        if (contactJudgementRecord == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactJudgementRecord.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactJudgementRecord.errors, view: 'create'
            return
        }

        contactJudgementRecord.save flush: true

        def opportunityContact = OpportunityContact.findById(params['opportunityContact'])

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contactJudgementRecord.label', default: 'ContactJudgementRecord'), contactJudgementRecord.id])
                //                redirect contactJudgementRecord
                redirect(controller: "opportunityContact", action: "show", params: [id: opportunityContact?.id])
            }
            '*' { respond contactJudgementRecord, [status: CREATED] }
        }
    }

    def edit(ContactJudgementRecord contactJudgementRecord)
    {
        def opportunityContact = OpportunityContact.findById(params['opportunityContact'])
        respond contactJudgementRecord, model: [opportunityContact: opportunityContact]
    }

    @Transactional
    def update(ContactJudgementRecord contactJudgementRecord)
    {
        if (contactJudgementRecord == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contactJudgementRecord.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contactJudgementRecord.errors, view: 'edit'
            return
        }

        contactJudgementRecord.save flush: true

        def opportunityContact = OpportunityContact.findById(params['opportunityContact'])

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contactJudgementRecord.label', default: 'ContactJudgementRecord'), contactJudgementRecord.id])
                //                redirect contactJudgementRecord
                redirect(controller: "opportunityContact", action: "show", params: [id: opportunityContact?.id])
            }
            '*' { respond contactJudgementRecord, [status: OK] }
        }
    }

    @Transactional
    def delete(ContactJudgementRecord contactJudgementRecord)
    {

        if (contactJudgementRecord == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contactJudgementRecord.delete flush: true

        def opportunityContact = OpportunityContact.findById(params['opportunityContact'])

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contactJudgementRecord.label', default: 'ContactJudgementRecord'), contactJudgementRecord.id])
                //                redirect action: "index", method: "GET"
                redirect(controller: "opportunityContact", action: "show", params: [id: opportunityContact?.id])

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactJudgementRecord.label', default: 'ContactJudgementRecord'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

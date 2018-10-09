package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class AccountController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Account.list(params), model: [accountCount: Account.count()]
    }

    def show(Account account)
    {
        def territoryAccounts = TerritoryAccount.findAllByAccount(account)
        def accountCities = AccountCity.findAllByAccount(account)
        respond account, model: [territoryAccounts: territoryAccounts, accountCities: accountCities]
    }

    def create()
    {
        respond new Account(params)
    }

    @Transactional
    def save(Account account)
    {
        if (account == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (account.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond account.errors, view: 'create'
            return
        }

        account.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'account.label',
                                                                                        default: 'Account'), account
                    .id])
                redirect account
            }
            '*' { respond account, [status: CREATED] }
        }
    }

    def edit(Account account)
    {
        respond account
    }

    @Transactional
    def update(Account account)
    {
        if (account == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (account.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond account.errors, view: 'edit'
            return
        }

        account.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'account.label',
                                                                                        default: 'Account'), account
                    .id])
                redirect account
            }
            '*' { respond account, [status: OK] }
        }
    }

    @Transactional
    def delete(Account account)
    {

        if (account == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        account.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'account.label',
                                                                                        default: 'Account'), account
                    .id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label',
                                                                                          default: 'Account'), params
                    .id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def searchAccount()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def max = params.max
        def offset = params.offset

        def name = params["name"]
        def type = params["type"]
        println type
        def typeId = ""
        if (type)
        {
            typeId = AccountType.findByName(type).id
        }

        String sql = "from Account as c where 1=1"
        if (name)
        {
            sql += " and c.name like '%${name}%'"
        }
        if (typeId)
        {
            sql += " and c.type.id = ${typeId}"
        }
        sql += " order by modifiedDate desc"

        def list = Account.findAll(sql, [max: max, offset: offset])
        def list1 = Account.findAll(sql)
        def count = list1.size()

        Account account = new Account()
        account.type = AccountType.findByName(type)
        account.name = name

        respond list, model: [accountCount: count, account: account, params: params], view: 'index'
    }
}

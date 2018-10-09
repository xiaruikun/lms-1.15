package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
@Transactional(readOnly = true)
class ContractController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Contract.list(params), model: [contractCount: Contract.count()]
    }

    def show(Contract contract)
    {
        respond contract
    }

    def create()
    {
        respond new Contract(params)
    }

    @Transactional
    def save(Contract contract)
    {
        if (contract == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contract.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contract.errors, view: 'create'
            return
        }

        contract.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contract.label', default: 'Contract'), contract.id])
                redirect contract
            }
            '*' { respond contract, [status: CREATED] }
        }
    }

    def edit(Contract contract)
    {
        respond contract
    }

    @Transactional
    def update(Contract contract)
    {
        if (contract == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contract.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contract.errors, view: 'edit'
            return
        }

        contract.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contract.label', default: 'Contract'), contract.id])
                redirect contract
            }
            '*' { respond contract, [status: OK] }
        }
    }

    @Transactional
    def delete(Contract contract)
    {

        if (contract == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contract.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contract.label', default: 'Contract'), contract.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contract.label', default: 'Contract'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def getContractItems()
    {
        def contractType = ContractType.findById(params['contractType'])
        def contractItemList = ContractTemplate.findAll("from ContractTemplate where type.id = ${contractType?.id} order by id asc")
        render([status: "success", contractItemList: contractItemList] as JSON)

    }

    /**
     * @Description
     * @Author bigyuan
     * @Return ajax 验证合同号是否重复
     * @DateTime 2017/9/27 20:17
     */
    def verifyContractNumber()
    {
        def contractNumber = params.contractNumber
        def contract = Contract.findBySerialNumber(contractNumber)
        if(contract)
        {
            render ([status:"errors"] as JSON)
        } else {
            render ([status:"success"] as JSON)
        }

    }
}

package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-5-8
 */
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class OpportunityContractController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def componentService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityContract.list(params), model: [opportunityContractCount: OpportunityContract.count()]
    }

    def show(OpportunityContract opportunityContract)
    {
        def contractItems = ContractItem.findAll("from ContractItem where contract.id = ${opportunityContract?.contract?.id} order by id asc")
        respond opportunityContract, model: [contractItems: contractItems]
    }

    def create()
    {
        respond new OpportunityContract(params)
    }
    /**
     * @Description
     * @Author bigyuan
     * @Return ajax 生成合同号
     * @DateTime 2017/9/27 15:11
     */
    def createContractNumber()
    {
        def opportunity = Opportunity.findById(params.opportunity)
        def loanDoor = OpportunityFlexField.findByNameAndCategory("放款通道",OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity,FlexFieldCategory.findByName("资金渠道")))?.value
        def productName = opportunity?.product?.name
        def cityName = opportunity.collaterals[0]?.city?.name
        def contractType = params.contractType
        contractType = ContractType.findById(contractType)?.name
        def contractNumber= ""
        if(loanDoor&&productName&&cityName&&contractType&&contractType in ['借款合同','抵押合同','委托借款代理服务合同']&&productName in ['速e贷','新e贷','融e贷','等额本息'] ) {
            if(loanDoor=="外贸信托"){
                contractNumber += "ZJX"
                switch (contractType) {
                    case  "借款合同" : contractNumber+="DKHT"
                        break
                    case  "抵押合同" : contractNumber+="DYHT"
                        break
                    case "委托借款代理服务合同" : contractNumber+="WTJKFWHT"
                        break
                }
                switch (cityName) {
                    case  "北京" : contractNumber+="_BJ"
                        break
                    case "上海" : contractNumber+="_SH"
                        break
                    case  "成都" : contractNumber+="_CD"
                        break
                    case  "青岛" : contractNumber+="_QD"
                        break
                    case  "济南" : contractNumber+="_JN"
                        break
                    case  "郑州" : contractNumber+="_ZZ"
                        break
                    case  "南京" : contractNumber+="_NJ"
                        break
                    case  "西安" : contractNumber+="_XA"
                        break
                    case  "合肥" : contractNumber+="_HF"
                        break
                    case  "武汉" : contractNumber+="_WH"
                        break
                    case  "苏州" : contractNumber+="_SZ"
                        break
                    case  "石家庄" : contractNumber+="_SJZ"
                        break
                    case  "厦门" : contractNumber+="_XM"
                        break
                }
                switch (productName) {
                    case  "速e贷" : contractNumber+="S_"
                        break
                    case  "新e贷" : contractNumber+="X_"
                        break
                    case  "融e贷" : contractNumber+="RY_"
                        break
                    case  "等额本息" : contractNumber+="RD_"
                        break
                }

            }
            else if(loanDoor=="中航信托"){
                contractNumber += "ZHXTJKHT-"
                if(cityName)
                {
                    if(cityName == "北京"){
                        contractNumber += "BJ-"
                    } else if(cityName == "上海"){
                        contractNumber += "SH-"
                    } else if(cityName == "成都"){
                        contractNumber += "CD-"
                    }
                    else if(cityName == "青岛"){
                        contractNumber += "QD-"
                    }
                    else if(cityName == "济南"){
                        contractNumber += "JN-"
                    } else if(cityName == "郑州"){
                        contractNumber += "ZJ-"
                    } else if(cityName == "南京"){
                        contractNumber += "NJ-"
                    } else if(cityName == "西安"){
                        contractNumber += "XA-"
                    } else if(cityName == "合肥"){
                        contractNumber += "HF-"
                    } else if(cityName == "武汉"){
                        contractNumber += "WH-"
                    } else if(cityName == "苏州"){
                        contractNumber += "SZ-"
                    } else if(cityName == "石家庄"){
                        contractNumber += "SJZ-"
                    } else if(cityName == "厦门"){
                        contractNumber += "XM-"
                    }
                }
            }
        }

        render ([contractNumber: contractNumber] as JSON)
    }
    @Transactional
    def save(OpportunityContract opportunityContract)
    {
        if (opportunityContract == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityContract.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityContract.errors, view: 'create'
            return
        }

        if (opportunityContract?.contract?.validate())
        {

            if (opportunityContract.validate())
            {
                opportunityContract.save flush: true

                def contractTemplates = ContractTemplate.findAllByType(opportunityContract?.contract?.type)
                def contractTemplateOptions
                def contractItem
                def contractItemOptions
                for (
                        contractTemplate in
                                contractTemplates)
                {
                    contractTemplateOptions = ContractTemplateOptions.findAllByTemplate(contractTemplate)
                    contractItem = new ContractItem()
                    contractItem.contract = opportunityContract?.contract
                    contractItem.name = contractTemplate?.name
                    if (params[contractTemplate?.name])
                    {
                        contractItem.value = params[contractTemplate?.name]
                    }
                    if (contractItem.validate())
                    {
                        contractItem.save flush: true
                        for (
                                contractTemplateOption in
                                        contractTemplateOptions)
                        {
                            contractItemOptions = new ContractItemOptions()
                            contractItemOptions.item = contractItem
                            contractItemOptions.value = contractTemplateOption.value
                            contractItemOptions.displayOrder = contractTemplateOption.displayOrder
                            if (contractItemOptions.validate())
                            {
                                contractItemOptions.save flush: true
                            }
                            else
                            {
                                flash.message = contractItemOptions.errors
                                break
                            }
                        }
                    }
                    else
                    {
                        flash.message = contractItem.errors
                        break
                    }
                }
                if (flash.message)
                {
                    transactionStatus.setRollbackOnly()
                    respond opportunityContract, view: 'create'
                    return
                }
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityContract.label', default: 'OpportunityContract'), opportunityContract.id])
                        // redirect opportunityContract
                        redirect(controller: "opportunity", action: "show", params: [id: opportunityContract?.opportunity?.id])
                    }
                    '*' { respond opportunityContract, [status: CREATED] }
                }
            }
            else
            {
                transactionStatus.setRollbackOnly()
                flash.message = opportunityContract.errors
                respond opportunityContract, view: 'create'
                return
            }
        }
        else
        {
            transactionStatus.setRollbackOnly()
            flash.message = opportunityContract?.contract.errors
            respond opportunityContract, view: 'create'
            return
        }

    }

    def edit(OpportunityContract opportunityContract)
    {
        def contractItems = ContractItem.findAll("from ContractItem where contract.id = ${opportunityContract?.contract?.id} order by id asc")
        respond opportunityContract, model: [contractItems: contractItems]
    }

    @Transactional
    def update(OpportunityContract opportunityContract)
    {
        if (opportunityContract == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityContract.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityContract.errors, view: 'edit'
            return
        }

        if (opportunityContract?.contract?.validate())
        {

            if (opportunityContract.validate())
            {
                opportunityContract.save flush: true

                def contractItems = ContractItem.findAllByContract(opportunityContract?.contract)
                for (
                        contractItem in
                                contractItems)
                {
                    contractItem.value = params[contractItem?.name]

                    if (contractItem.validate())
                    {
                        contractItem.save flush: true
                    }
                    else
                    {
                        flash.message = contractItem.errors
                        break
                    }
                }
                if (flash.message)
                {
                    transactionStatus.setRollbackOnly()
                    respond opportunityContract, view: 'edit'
                    return
                }
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityContract.label', default: 'OpportunityContract'), opportunityContract.id])
                        // redirect opportunityContract
                        redirect(controller: "opportunity", action: "show", params: [id: opportunityContract?.opportunity?.id])

                    }
                    '*' { respond opportunityContract, [status: OK] }
                }
            }
            else
            {
                transactionStatus.setRollbackOnly()
                flash.message = opportunityContract.errors
                respond opportunityContract, view: 'edit'
                return
            }
        }
        else
        {
            transactionStatus.setRollbackOnly()
            flash.message = opportunityContract?.contract.errors
            respond opportunityContract, view: 'edit'
            return
        }
    }

    @Transactional
    def delete(OpportunityContract opportunityContract)
    {

        if (opportunityContract == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityContract.delete flush: true
        def options = ContractItemOptions.findAllByItemInList(ContractItem.findAllByContract(opportunityContract?.contract))
        options?.each {
            it.delete flash:true
        }
        def items = ContractItem.findAllByContract(opportunityContract?.contract)
        items?.each {
            it.delete flash:true
        }
        def contract = opportunityContract?.contract
        contract?.delete()

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityContract.label', default: 'OpportunityContract'), opportunityContract.id])
                // redirect action:"index", method:"GET"
                redirect(controller: "opportunity", action: "show", params: [id: opportunityContract.opportunity.id])
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityContract.label', default: 'OpportunityContract'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

package com.next

import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
@Transactional(readOnly = true)
class CreditReportConstraintController
{
    def creditReportService
    def springSecurityService
    def contactService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond CreditReportConstraint.list(params), model: [creditReportConstraintCount: CreditReportConstraint
            .count()]
    }

    //test
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def creditReportShow1(Opportunity opportunity)
    {

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def userRole = UserRole.findByUser(user)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        //opportunityRole && opportunityRole?.teamRole?.name == "Approval"
        def opportunityTeam = OpportunityTeam.findByOpportunityAndUser(opportunity, user)
        if (opportunityTeam || userRole && userRole?.role?.authority == "ROLE_ADMINISTRATOR")
        {
            def cList = []
            def dataMap = [:]
            def providerList = ExternalDataProvider.findAll()
            opportunity.contacts.each {
                def contactList = [:]
                def details = []
                def opportunityContact = it
                providerList.each {
                    def contactExternalDataset = ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '${it?.name}' order by dataset.createdDate desc")
                    def project = [:]
                    def itemList = ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
                    def items = []
                    itemList.each {
                        def item = [:]
                        item["name"] = it?.name
                        item["value"] = it?.value

                        def detailItem = ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                        def msglist = []
                        if (detailItem)
                        {
                            detailItem.each {
                                def detail = [:]
                                detail["name"] = it?.name
                                detail["value"] = it?.value
                                msglist.add(detail)
                            }
                        }
                        if (msglist.size() > 0)
                        {
                            item["items"] = msglist
                        }
                        items.add(item)
                    }
                    if (items.size() > 0)
                    {
                        project["items"] = items
                    }
                    if (project)
                    {
                        dataMap = [:]
                        dataMap["name"] = it?.name
                        dataMap["items"] = project
                        details.add(dataMap)
                    }
                    if (opportunityContact?.contact?.companies)
                    {
                        def provider = it
                        opportunityContact?.contact?.companies.each {
                            def companyExternalDataset
                            if (it?.company)
                            {
                                companyExternalDataset = CompanyExternalDataset.find("from CompanyExternalDataset where company.id = ${it?.id} and dataset.provider.name = '${provider?.name}' order by dataset.createdDate desc")
                            }
                            if (companyExternalDataset)
                            {
                                def project1 = [:]
                                def itemList1 = ExternalDatasetItem.findAllByDatasetAndLevel(companyExternalDataset?.dataset, 0)
                                def items1 = []
                                itemList1.each {
                                    def item = [:]
                                    item["name"] = it?.name
                                    item["value"] = it?.value

                                    def detailItem = ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, companyExternalDataset?.dataset?.id, 1])
                                    def msglist = []
                                    if (detailItem)
                                    {
                                        detailItem.each {
                                            def detail = [:]
                                            detail["name"] = it?.name
                                            detail["value"] = it?.value
                                            msglist.add(detail)
                                        }
                                    }
                                    if (msglist.size() > 0)
                                    {
                                        item["items"] = msglist
                                    }
                                    items1.add(item)
                                }
                                if (items1.size() > 0)
                                {
                                    project1["items"] = items1
                                }
                                if (project1)
                                {
                                    dataMap = [:]
                                    dataMap["name"] = provider?.name + ":" + it?.company
                                    dataMap["items"] = project1
                                    details.add(dataMap)
                                }
                            }
                        }
                    }
                }
                contactList["fullName"] = it?.contact?.fullName
                contactList["idNumber"] = it?.contact?.idNumber
                contactList["cellphone"] = it?.contact?.cellphone
                contactList["details"] = details
                cList.add(contactList)
            }
            render(view: "show", model: [cList: cList, opportunity: opportunity])
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
    }

    /*
    *used大数据调用方法
    *author xiaruikun
    * modifiedDate 2017-5-19
    * */

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COMPLIANCE_MANAGER'])
    @Transactional
    def creditReportShow2(Opportunity opportunity)
    {

    def username = springSecurityService.getPrincipal().username
    def user = User.findByUsername(username)
    def opportunityTeam = OpportunityTeam.findByOpportunityAndUser(opportunity, user)
    def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)

    def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
    def canCreditReportShow = true

    // if (opportunityTeam || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
    if (canCreditReportShow)
    {
        def cList = []
        def dataMap = [:]
        def providerList = CreditReportProvider.findAllByCodeNotEqual("BLACKLIST")

        def contactName = []
        def contactIdNumber = []
        def contactNameAndNumber = []
        def contactId = []
        def contactPhone = []
        def messageJudge = []
        opportunityContacts.each {
            def contactList = [:]
            def details = []
            if (it?.contact?.fullName)
            {
                if (it?.contact?.idNumber)
                {
                    Boolean flag = contactService.verifyIdNumber(it?.contact?.idNumber)
                    if (flag && it?.contact?.fullName.matches(/^([\u2190-\u9fff]{1,20}|[a-zA-Z\.\s]{1,20})/))
                    {
                        def opportunityContact = it
                        providerList.each {
                            //                                    println "=====" + it?.code
                            def json
                            if (it?.code == "PENGYUAN" || it?.code == "COMPANY")
                            {
                                if (opportunityContact?.contact?.companies)
                                {
                                    def provider = it
                                    opportunityContact?.contact?.companies.each {
                                        if (it?.company)
                                        {
                                            json = creditReportService.generatePengyuan(provider, it)
                                        }
                                        if (provider?.code == "PENGYUAN" && json && json["items"])
                                        {
                                            dataMap = [:]
                                            dataMap["name"] = "B:" + it?.company
                                            dataMap["items"] = json
                                            details.add(dataMap)
                                        }
                                        if (provider?.code == "COMPANY" && json && json["items"])
                                        {
                                            dataMap = [:]
                                            dataMap["name"] = "C:" + it?.company
                                            dataMap["items"] = json
                                            details.add(dataMap)
                                        }
                                    }
                                }
                            }
                            else
                            {
                                if (it?.code == 'HUIFA' || it?.code == 'BAIRONG')
                                {
                                    json = creditReportService.generateReportAll(it, opportunityContact)
                                }
                                /*else
                                {
                                    if (opportunityContact?.contact?.cellphone && opportunityContact?.contact?.cellphone?.size() == 11 && opportunityContact?.contact?.cellphone.matches(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/))
                                    {
                                        json = creditReportService.generateReportAll(it, opportunityContact)
                                    }
                                }*/
                            }
                            if (it?.code == "TONGDUN" && json && json["items"])
                            {
                                dataMap = [:]
                                dataMap["name"] = "D"
                                dataMap["items"] = json
                                details.add(dataMap)
                            }
                            if (it?.code == "BAIRONG" && json && json["items"])
                            {
                                dataMap = [:]
                                dataMap["name"] = "A"
                                dataMap["items"] = json
                                if (json["company"])
                                {
                                    for (
                                            def name in
                                                    json["company"])
                                    {
                                        if (!Company.findByCompanyAndContact(name, opportunityContact?.contact))
                                        {
                                            Company company = new Company()
                                            company.company = name
                                            company.contact = opportunityContact?.contact
                                            if (company.validate())
                                            {
                                                company.save flush: true
                                            }
                                            else
                                            {
                                                println company.errors
                                            }
                                        }
                                    }
                                }
                                details.add(dataMap)
                            }
                            if (it?.code == "HUIFA" && json && json["items"])
                            {
                                dataMap = [:]
                                dataMap["name"] = "C"
                                dataMap["items"] = json
                                if (json["company"])
                                {
                                    for (
                                            def name in
                                                    json["company"])
                                    {
                                        if (!Company.findByCompanyAndContact(name, opportunityContact?.contact))
                                        {
                                            Company company = new Company()
                                            company.company = name
                                            company.contact = opportunityContact?.contact
                                            if (company.validate())
                                            {
                                                company.save flush: true
                                            }
                                            else
                                            {
                                                println company.errors
                                            }
                                        }
                                    }
                                }
                                details.add(dataMap)
                            }
                            if (it?.code == "PERSON" && json && json["items"])
                            {
                                dataMap = [:]
                                dataMap["name"] = "B"
                                dataMap["items"] = json
                                details.add(dataMap)
                            }

                            if (it?.code == "FOTIC" && json)
                            {
                                if (json["items"])
                                {
                                    dataMap = [:]
                                    dataMap["name"] = it?.name
                                    dataMap["items"] = json
                                    details.add(dataMap)
                                }
                                else
                                {
                                    dataMap = [:]
                                    dataMap["name"] = it?.name + ":" + json["errorDes"]
                                    details.add(dataMap)
                                }
                            }
                        }
                        contactList["fullName"] = it?.contact?.fullName
                        contactList["idNumber"] = it?.contact?.idNumber
                        contactList["cellphone"] = it?.contact?.cellphone
                        contactList["details"] = details
                        cList.add(contactList)
                    }
                    else
                    {
                        messageJudge.add "姓名：" + it?.contact?.fullName + "或身份证号：" + it?.contact?.idNumber + "的校验不通过"
                    }
                }
                else
                {
                    contactName.add it?.type?.name + "的身份证号为空"
                }
            }
            else
            {
                if (it?.contact?.idNumber)
                {
                    contactIdNumber.add it?.type?.name + "的姓名为空 "
                }
                else
                {
                    if (it?.contact?.cellphone)
                    {
                        contactNameAndNumber.add it?.type?.name + "的身份证号和姓名都为空 "
                    }
                    else
                    {
                        contactId.add it?.type?.name + "的手机号，身份证号，姓名均为空 "
                    }
                }
            }
        }
        flash.message = ""
        if (contactName)
        {
            flash.message += contactName
        }
        if (contactIdNumber)
        {
            flash.message += contactIdNumber
        }
        if (contactNameAndNumber)
        {
            flash.message += contactNameAndNumber
        }
        if (contactId)
        {
            flash.message += contactId
        }
        if (messageJudge)
        {
            flash.message += messageJudge
        }
        if (contactPhone)
        {
            flash.message += contactPhone
        }
        //            println "=====" + cList
        render(view: "show", model: [cList: cList, opportunity: opportunity])
    }
    else
    {
        flash.message = message(code: 'opportunity.edit.permission.denied')
        redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
        return
    }
}


    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COMPLIANCE_MANAGER'])
    @Transactional
    def creditReportShow(Opportunity opportunity)
    {

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityTeam = OpportunityTeam.findByOpportunityAndUser(opportunity, user)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def canCreditReportShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
        {
            canCreditReportShow = true
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_HEAD_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CUSTOMER_SERVICE_REPRESENTATIVE")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
        {
            if (currentRole?.teamRole?.name == 'Approval')
            {
                canCreditReportShow = true
            }
        }
        // if (opportunityTeam || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
        if (canCreditReportShow)
        {
            def cList = []
            def dataMap = [:]
            def providerList = CreditReportProvider.findAllByCodeNotEqual("BLACKLIST")

            def contactName = []
            def contactIdNumber = []
            def contactNameAndNumber = []
            def contactId = []
            def contactPhone = []
            def messageJudge = []
            opportunityContacts.each {
                def contactList = [:]
                def details = []
                if (it?.contact?.fullName)
                {
                    if (it?.contact?.idNumber)
                    {
                        Boolean flag = contactService.verifyIdNumber(it?.contact?.idNumber)
                        if (flag && it?.contact?.fullName.matches(/^([\u2190-\u9fff]{1,20}|[a-zA-Z\.\s]{1,20})/))
                        {
                            def opportunityContact = it
                            providerList.each {
                                //                                    println "=====" + it?.code
                                def json
                                if (it?.code == "PENGYUAN" || it?.code == "COMPANY")
                                {
                                    if (opportunityContact?.contact?.companies)
                                    {
                                        def provider = it
                                        opportunityContact?.contact?.companies.each {
                                            if (it?.company)
                                            {
                                                json = creditReportService.generatePengyuan(provider, it)
                                            }
                                            if (provider?.code == "PENGYUAN" && json && json["items"])
                                            {
                                                dataMap = [:]
                                                dataMap["name"] = "B:" + it?.company
                                                dataMap["items"] = json
                                                details.add(dataMap)
                                            }
                                            if (provider?.code == "COMPANY" && json && json["items"])
                                            {
                                                dataMap = [:]
                                                dataMap["name"] = "C:" + it?.company
                                                dataMap["items"] = json
                                                details.add(dataMap)
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    if (it?.code == 'HUIFA' || it?.code == 'BAIRONG')
                                    {
                                        json = creditReportService.generateReportAll(it, opportunityContact)
                                    }
                                    /*else
                                    {
                                        if (opportunityContact?.contact?.cellphone && opportunityContact?.contact?.cellphone?.size() == 11 && opportunityContact?.contact?.cellphone.matches(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/))
                                        {
                                            json = creditReportService.generateReportAll(it, opportunityContact)
                                        }
                                    }*/
                                }
                                if (it?.code == "TONGDUN" && json && json["items"])
                                {
                                    dataMap = [:]
                                    dataMap["name"] = "D"
                                    dataMap["items"] = json
                                    details.add(dataMap)
                                }
                                if (it?.code == "BAIRONG" && json && json["items"])
                                {
                                    dataMap = [:]
                                    dataMap["name"] = "A"
                                    dataMap["items"] = json
                                    if (json["company"])
                                    {
                                        for (
                                                def name in
                                                        json["company"])
                                        {
                                            if (!Company.findByCompanyAndContact(name, opportunityContact?.contact))
                                            {
                                                Company company = new Company()
                                                company.company = name
                                                company.contact = opportunityContact?.contact
                                                if (company.validate())
                                                {
                                                    company.save flush: true
                                                }
                                                else
                                                {
                                                    println company.errors
                                                }
                                            }
                                        }
                                    }
                                    details.add(dataMap)
                                }
                                if (it?.code == "HUIFA" && json && json["items"])
                                {
                                    dataMap = [:]
                                    dataMap["name"] = "C"
                                    dataMap["items"] = json
                                    if (json["company"])
                                    {
                                        for (
                                                def name in
                                                        json["company"])
                                        {
                                            if (!Company.findByCompanyAndContact(name, opportunityContact?.contact))
                                            {
                                                Company company = new Company()
                                                company.company = name
                                                company.contact = opportunityContact?.contact
                                                if (company.validate())
                                                {
                                                    company.save flush: true
                                                }
                                                else
                                                {
                                                    println company.errors
                                                }
                                            }
                                        }
                                    }
                                    details.add(dataMap)
                                }
                                if (it?.code == "PERSON" && json && json["items"])
                                {
                                    dataMap = [:]
                                    dataMap["name"] = "B"
                                    dataMap["items"] = json
                                    details.add(dataMap)
                                }

                                if (it?.code == "FOTIC" && json)
                                {
                                    if (json["items"])
                                    {
                                        dataMap = [:]
                                        dataMap["name"] = it?.name
                                        dataMap["items"] = json
                                        details.add(dataMap)
                                    }
                                    else
                                    {
                                        dataMap = [:]
                                        dataMap["name"] = it?.name + ":" + json["errorDes"]
                                        details.add(dataMap)
                                    }
                                }
                            }
                            contactList["fullName"] = it?.contact?.fullName
                            contactList["idNumber"] = it?.contact?.idNumber
                            contactList["cellphone"] = it?.contact?.cellphone
                            contactList["details"] = details
                            cList.add(contactList)
                        }
                        else
                        {
                            messageJudge.add "姓名：" + it?.contact?.fullName + "或身份证号：" + it?.contact?.idNumber + "的校验不通过"
                        }
                    }
                    else
                    {
                        contactName.add it?.type?.name + "的身份证号为空"
                    }
                }
                else
                {
                    if (it?.contact?.idNumber)
                    {
                        contactIdNumber.add it?.type?.name + "的姓名为空 "
                    }
                    else
                    {
                        if (it?.contact?.cellphone)
                        {
                            contactNameAndNumber.add it?.type?.name + "的身份证号和姓名都为空 "
                        }
                        else
                        {
                            contactId.add it?.type?.name + "的手机号，身份证号，姓名均为空 "
                        }
                    }
                }
            }
            flash.message = ""
            if (contactName)
            {
                flash.message += contactName
            }
            if (contactIdNumber)
            {
                flash.message += contactIdNumber
            }
            if (contactNameAndNumber)
            {
                flash.message += contactNameAndNumber
            }
            if (contactId)
            {
                flash.message += contactId
            }
            if (messageJudge)
            {
                flash.message += messageJudge
            }
            if (contactPhone)
            {
                flash.message += contactPhone
            }
            //            println "=====" + cList
            render(view: "show", model: [cList: cList, opportunity: opportunity])
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
    }
    def create()
    {
        respond new CreditReportConstraint(params)
    }

    /*
    *黑名单方法
    *author xiaruikun
    * */

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def blackListShow(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def userRole = UserRole.findByUser(user)

        //        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        def opportunityTeam = OpportunityTeam.findByOpportunityAndUser(opportunity, user)
        //        println "team:" + opportunityTeam?.user?.fullName
        if (opportunityTeam || userRole && userRole?.role?.authority == "ROLE_ADMINISTRATOR")
        {
            def provider = CreditReportProvider.findByCode("BLACKLIST")

            def contactName = []
            def contactIdNumber = []
            def contactNameAndNumber = []
            def contactId = []
            def contactPhone = []
            def messageJudge = []
            def oppoContactList = []
            opportunity.contacts.each {
                if (it?.contact?.cellphone && it?.contact?.cellphone?.size() == 11 && it?.contact?.cellphone.matches(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/))
                {
                    if (it?.contact?.fullName)
                    {
                        if (it?.contact?.idNumber)
                        {
                            Boolean flag = contactService.verifyIdNumber(it?.contact?.idNumber)
                            if (flag && it?.contact?.fullName.matches(/^([\u2190-\u9fff]{1,20}|[a-zA-Z\.\s]{1,20})/))
                            {
                                def contactList = [:]
                                contactList["idNumber"] = it?.contact?.idNumber
                                contactList["fullName"] = it?.contact?.fullName
                                contactList["cellphone"] = it?.contact?.cellphone
                                if (it?.type?.name == '借款人')
                                {
                                    contactList["type"] = "loan"
                                }
                                if (it?.type?.name == '借款人配偶')
                                {
                                    contactList["type"] = "loanSpouse"
                                }
                                if (it?.type?.name == '抵押人')
                                {
                                    contactList["type"] = "mortgage"
                                }
                                if (it?.type?.name == '抵押人配偶')
                                {
                                    contactList["type"] = "mortgageSpouse"
                                }
                                if (it?.type?.name == '其它借款人')
                                {
                                    contactList["type"] = "other"
                                }
                                if (it?.type?.name == '借款人父母')
                                {
                                    contactList["type"] = "loanParents"
                                }
                                if (it?.type?.name == '借款人子女')
                                {
                                    contactList["type"] = "loanChildren"
                                }
                                oppoContactList.add(contactList)
                            }
                            else
                            {
                                messageJudge.add "姓名：" + it?.contact?.fullName + "或身份证号：" + it?.contact?.idNumber + "的校验不通过"
                            }
                        }
                        else
                        {
                            contactName.add it?.type?.name + "的身份证号为空"
                        }
                    }
                    else
                    {
                        if (it?.contact?.idNumber)
                        {
                            contactIdNumber.add it?.type?.name + "的姓名为空 "
                        }
                        else
                        {
                            if (it?.contact?.cellphone)
                            {
                                contactNameAndNumber.add it?.type?.name + "的身份证号和姓名都为空 "
                            }
                            else
                            {
                                contactId.add it?.type?.name + "的手机号，身份证号，姓名均为空 "
                            }
                        }
                    }
                }
                else
                {
                    contactPhone.add it?.type?.name + "手机号为空或手机号校验未通过"
                }
            }
            println "oppoContactList" + JsonOutput.toJson(oppoContactList) + oppoContactList.size()
            def json
            if (oppoContactList.size() > 0)
            {
                json = creditReportService.queryBlackList(provider, JsonOutput.toJson(oppoContactList))
            }
            flash.message = ""
            if (contactName)
            {
                flash.message += contactName
            }
            if (contactIdNumber)
            {
                flash.message += contactIdNumber
            }
            if (contactNameAndNumber)
            {
                flash.message += contactNameAndNumber
            }
            if (contactId)
            {
                flash.message += contactId
            }
            if (messageJudge)
            {
                flash.message += messageJudge
            }
            if (contactPhone)
            {
                flash.message += contactPhone
            }
            render(view: "queryBlackList", model: [json: json, opportunity: opportunity])
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
    }

    @Transactional
    def save(CreditReportConstraint creditReportConstraint)
    {
        if (creditReportConstraint == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (creditReportConstraint.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond creditReportConstraint.errors, view: 'create'
            return
        }

        creditReportConstraint.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'creditReportConstraint' + '.label', default: 'CreditReportConstraint')
                    , creditReportConstraint.id])
                redirect creditReportConstraint
            }
            '*' { respond creditReportConstraint, [status: CREATED] }
        }
    }

    def edit(CreditReportConstraint creditReportConstraint)
    {
        respond creditReportConstraint
    }

    @Transactional
    def update(CreditReportConstraint creditReportConstraint)
    {
        if (creditReportConstraint == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (creditReportConstraint.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond creditReportConstraint.errors, view: 'edit'
            return
        }

        creditReportConstraint.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'creditReportConstraint' + '.label', default: 'CreditReportConstraint')
                    , creditReportConstraint.id])
                redirect creditReportConstraint
            }
            '*' { respond creditReportConstraint, [status: OK] }
        }
    }

    @Transactional
    def delete(CreditReportConstraint creditReportConstraint)
    {

        if (creditReportConstraint == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        creditReportConstraint.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'creditReportConstraint' + '.label', default: 'CreditReportConstraint')
                    , creditReportConstraint.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'creditReportConstraint' + '.label', default: 'CreditReportConstraint'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

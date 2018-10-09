package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class OpportunityRoleController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService
    def opportunityStatisticsService

    @Secured(['ROLE_COMPLIANCE_MANAGER', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def index(Integer max)
    {
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRoleList = []
        def list
        def count
        def status = params['status']
        def prepareCity = params['prepareCity']
        if (status)
        {
            //已办理
            def opportunityList = []
            def roleList = OpportunityRole.findAll("from OpportunityRole where user.id = ${user?.id} order by opportunity.modifiedDate DESC")
            roleList?.each {
                def currentFlow = OpportunityFlow.findByStageAndOpportunity(it?.opportunity?.stage, it?.opportunity)
                def managedFlow = OpportunityFlow.findByStageAndOpportunity(it?.stage, it?.opportunity)
                if (currentFlow?.executionSequence > managedFlow?.executionSequence)
                {
                    opportunityList.add(it)
                }
            }
            //解决分页问题
            for (
                def i = offset;
                    i < offset + max;
                    i++)
            {
                if (opportunityList[i])
                {
                    opportunityRoleList.add(opportunityList[i])
                }
                else
                {
                    break
                }
            }
            count = opportunityList?.size()
        }
        else
        {
            //待办
            opportunityRoleList = OpportunityRole.findAll("from OpportunityRole where user.id = ${user?.id} and stage.id = opportunity.stage.id and opportunity.status != 'Failed' order by opportunity.modifiedDate DESC", [max: max, offset: offset])
            def list1 = OpportunityRole.executeQuery("select count(otr.id) from OpportunityRole otr where otr.user.id = ${user?.id} and otr.stage.id = otr.opportunity.stage.id and otr.opportunity.status != 'Failed'")
            count = list1[0]

            //上海区待办
            if (prepareCity == 'shanghai')
            {
                opportunityRoleList = OpportunityRole.findAll("from OpportunityRole where user.id = ${user?.id} and stage.id = opportunity.stage.id and opportunity.status != 'Failed' and opportunity.user.city.name = '上海' order by opportunity.modifiedDate DESC", [max: max, offset: offset])
                def list2 = OpportunityRole.executeQuery("select count(otr.id) from OpportunityRole otr where otr.user.id = ${user?.id} and otr.stage.id = otr.opportunity.stage.id and otr.opportunity.status != 'Failed' and otr.opportunity.user.city.name = '上海'")
                count = list2[0]
            }
        }
        respond opportunityRoleList, model: [opportunityRoleCount: count, status: status, params: params]
    }

    def show(OpportunityRole opportunityRole)
    {
        respond opportunityRole
    }

    def create()
    {
        def opportunity = Opportunity.findById(params["opportunity"])
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def userList = []
        def teamList
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (!(UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR"))))
        {
            if (!opportunityRole)
            {
                flash.message = message(code: 'opportunity.edit.permission.denied')
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
                return
            }
            else if (opportunityRole?.teamRole?.name != "Approval" && opportunityRole?.teamRole?.name != "Administrator")
            {
                flash.message = message(code: 'opportunity.edit.permission.denied')
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
                return
            }
        }
        if (opportunity)
        {
            teamList = OpportunityTeam.findAllByOpportunity(opportunity)
            teamList.each {
                userList.add(it?.user)
            }
        }
        else
        {
            userList = User.findAll()
        }

        opportunityRole = new OpportunityRole(params)
        def opportunityStages
        if (opportunityRole?.opportunity?.type)
        {
            opportunityStages = opportunityRole?.opportunity?.type?.stages
        }
        else
        {
            opportunityStages = OpportunityType.findByCode('10')?.stages
        }

        opportunityStages?.each {
            if (!it?.active)
            {
                opportunityStages = opportunityStages?.minus(it)
            }
        }

        respond opportunityRole, model: [userList: userList, opportunityStages: opportunityStages]
    }

    @Transactional
    def save(OpportunityRole opportunityRole)
    {
        if (opportunityRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityRole.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityRole.errors, view: 'create'
            return
        }

        opportunityRole.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityRole' + '.label', default: 'OpportunityRole'),
                    opportunityRole.id])
                redirect controller: "opportunity", action: "show", method: "GET", id: opportunityRole
                    .opportunity.id
            }
            '*' { respond opportunityRole, [status: CREATED] }
        }
    }

    def edit(OpportunityRole opportunityRole)
    {
        respond opportunityRole
    }

    @Transactional
    def update(OpportunityRole opportunityRole)
    {
        if (opportunityRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityRole.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityRole.errors, view: 'edit'
            return
        }

        opportunityRole.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityRole' + '.label', default: 'OpportunityRole'),
                    opportunityRole.id])
                redirect opportunityRole
            }
            '*' { respond opportunityRole, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityRole opportunityRole)
    {

        if (opportunityRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        if (!(opportunityRole.opportunity.user == user) && !(UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR"))))
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect controller: "opportunity", action: "show", method: "GET", id: opportunityRole.opportunity.id
            return
        }
        if (opportunityRole.user == opportunityRole.opportunity.user)
        {
            flash.message = message(code: 'opportunityTeam.delete.permission.denied')
            redirect controller: "opportunity", action: "show", method: "GET", id: opportunityRole.opportunity.id
            return
        }

        opportunityRole.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityRole' + '.label', default: 'OpportunityRole'), opportunityRole.id])
                redirect controller: "opportunity", action: "show", method: "GET", id: opportunityRole.opportunity.id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityRole' + '.label', default: 'OpportunityRole'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Secured(['ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def searchOpportunity()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def max = params.max
        def offset = params.offset

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def status = params["status"]
        def type = params["type"]
        def serialNumber = params["serialNumber"]
        def fullName = params["fullName"]
        def contact = params["contact"]
        def mangerName = params["user"]
        def stageName = params['stage']
        def startTime = params["startTime"]
        def endTime = params["endTime"]
        def city = params["city"]
        // def flexField = params["flexField"]
        // def flexFieldBankAccount = params["flexFieldBankAccount"]
        def mortgageCertificateType = params["mortgageCertificateType"]
        // def estimatedTime = params["estimatedTime"]
        def contactLevel = params["contactLevel"]
        def actualAmountOfCreditMin = params["actualAmountOfCreditMin"]
        def actualAmountOfCreditMax = params["actualAmountOfCreditMax"]
        // def approvalStartTime = params["approvalStartTime"]
        // def approvalEndTime = params["approvalEndTime"]
        def prepareCity = params["prepareCity"]

        def opportunityRoleList = []
        def list
        def count
        String sql = "from OpportunityRole as otr where otr.user.id = ${user?.id} and otr.stage.id = otr.opportunity.stage.id"
        String roleSql = "from OpportunityRole as otr where otr.user.id = ${user?.id}"
        if (serialNumber)
        {
            sql += " and otr.opportunity.serialNumber like '%${serialNumber}%'"
            roleSql += " and otr.opportunity.serialNumber like '%${serialNumber}%'"
        }
        if (fullName)
        {
            sql += " and otr.opportunity.fullName like '%${fullName}%'"
            roleSql += " and otr.opportunity.fullName like '%${fullName}%'"
        }
        if (contact)
        {
            sql += " and otr.opportunity.contact.fullName like '%${contact}%'"
            roleSql += " and otr.opportunity.contact.fullName like '%${contact}%'"
        }
        if (mangerName)
        {
            sql += " and otr.opportunity.user.fullName like '%${mangerName}%'"
            roleSql += " and otr.opportunity.user.fullName like '%${mangerName}%'"
        }
        if (stageName)
        {
            sql += " and otr.opportunity.stage.name = '${stageName}'"
            roleSql += " and otr.opportunity.stage.name = '${stageName}'"
        }
        if (!status)
        {
            sql += " and otr.opportunity.status != 'Failed'"
        }

        if (type)
        {
            sql += " and otr.opportunity.type.code = '${type}'"
        }
        if (startTime && endTime)
        {
            sql += " and otr.opportunity.createdDate between '${startTime}' and '${endTime}'"
            roleSql += " and otr.opportunity.createdDate between '${startTime}' and '${endTime}'"
        }

        if (city && city != "null")
        {
            sql += " and otr.opportunity.contact.city.name = '${city}'"
            roleSql += " and otr.opportunity.contact.city.name = '${city}'"
        }
        if (mortgageCertificateType && mortgageCertificateType != "null")
        {
            sql += " and otr.opportunity.mortgageCertificateType.name = '${mortgageCertificateType}'"
            roleSql += " and otr.opportunity.mortgageCertificateType.name = '${mortgageCertificateType}'"
        }
        // if (flexField && flexField != "null")
        // {
        //     sql += " and otr.opportunity.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款通道' and f.value= '${flexField}'))"
        //     roleSql += " and otr.opportunity.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款通道' and f.value= '${flexField}'))"
        // }
        // if (flexFieldBankAccount && flexFieldBankAccount != "null")
        // {
        //     println flexFieldBankAccount
        //     sql += " and otr.opportunity.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款账号' and f.value= '${flexFieldBankAccount}'))"
        //     roleSql += " and otr.opportunity.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款账号' and f.value= '${flexFieldBankAccount}'))"
        // }

        // if (estimatedTime)
        // {
        //     estimatedTime = estimatedTime.substring(0, 10)
        //     sql += " and otr.opportunity.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name in ('预计抵押登记时间', '预计出他项时间', '预计公证时间') and f.value like '%${estimatedTime}%'))"
        //     roleSql += " and otr.opportunity.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name in ('预计抵押登记时间', '预计出他项时间', '预计公证时间') and f.value like '%${estimatedTime}%'))"
        // }

        if (contactLevel && contactLevel != "null")
        {
            sql += " and otr.opportunity.lender.level.name = '${contactLevel}'"
            roleSql += " and otr.opportunity.lender.level.name = '${contactLevel}'"
        }

        if (actualAmountOfCreditMin)
        {
            sql += " and otr.opportunity.actualAmountOfCredit >= '${actualAmountOfCreditMin}'"
            roleSql += " and otr.opportunity.actualAmountOfCredit >= '${actualAmountOfCreditMin}'"
        }

        if (actualAmountOfCreditMax)
        {
            sql += " and otr.opportunity.actualAmountOfCredit <= '${actualAmountOfCreditMax}'"
            roleSql += " and otr.opportunity.actualAmountOfCredit <= '${actualAmountOfCreditMax}'"
        }

        // if (approvalStartTime)
        // {
        //     sql += " and opportunity.id in (select opportunity.id from OpportunityFlow where stage.code = '08' and startTime >= '${approvalStartTime}')"
        //     roleSql += " and opportunity.id in (select opportunity.id from OpportunityFlow where stage.code = '08' and startTime >= '${approvalStartTime}')"
        // }

        // if (approvalEndTime)
        // {
        //     sql += " and opportunity.id in (select opportunity.id from OpportunityFlow where stage.code = '08' and startTime <= '${approvalEndTime}')"
        //     roleSql += " and opportunity.id in (select opportunity.id from OpportunityFlow where stage.code = '08' and startTime <= '${approvalEndTime}')"
        // }
        //上海区待办
        if (prepareCity == 'shanghai')
        {
            sql += " and otr.opportunity.user.city.name = '上海'"
        }

        def countSql = "select count(otr.opportunity.id) " + sql

        sql += " order by otr.opportunity.modifiedDate desc"
        roleSql += " order by otr.opportunity.modifiedDate desc"

        if (status)
        {
            def opportunityList = []
            //已办理
            def roleList = OpportunityRole.findAll(roleSql)
            roleList?.each {
                def currentFlow = OpportunityFlow.findByStageAndOpportunity(it?.opportunity?.stage, it?.opportunity)
                def managedFlow = OpportunityFlow.findByStageAndOpportunity(it?.stage, it?.opportunity)
                if (currentFlow?.executionSequence > managedFlow?.executionSequence)
                {
                    opportunityList.add(it)
                }
            }
            //解决分页问题
            for (
                def i = offset;
                    i < offset + max;
                    i++)
            {
                if (opportunityList[i])
                {
                    opportunityRoleList.add(opportunityList[i])
                }
                else
                {
                    break
                }
            }
            count = opportunityList?.size()
        }
        else
        {
            println "countSql:" + countSql
            opportunityRoleList = OpportunityRole.findAll(sql, [max: max, offset: offset])
            def count1 = OpportunityRole.executeQuery(countSql)
            count = count1[0]
        }

        respond opportunityRoleList, model: [opportunityRoleCount: count, params: params, status: status], view: 'index'
    }

}

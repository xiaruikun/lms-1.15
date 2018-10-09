package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND

@Transactional(readOnly = true)
class OpportunityTeamController
{

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE', 'ROLE_COMPLIANCE_MANAGER', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def index(Integer max)
    {
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        def stageCode = params["stage"]
        def stage = OpportunityStage.findByCode(stageCode)
        def status = params["status"]

        def sql = "from OpportunityTeam as o where 1=1"
        def opSql = "from Opportunity as a where 1=1"
        def list = []
        def count
        if (!stage)
        {
            if (status)
            {
                sql += " and o.opportunity.status = '${status}'"
                opSql += " and a.status = '${status}'"
            }
        }
        else
        {
            sql += " and o.opportunity.stage.id = ${stage.id} and o.opportunity.status = '${status}'"
            opSql += " and a.stage.id = ${stage.id} and a.status = '${status}'"
        }

        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")))
        {
            opSql += " order by modifiedDate desc"
            list = Opportunity.findAll(opSql, [max: max, offset: offset])
            count = Opportunity.findAll(opSql).size()
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")))
        {
            opSql += " and o.user.city.id = ${user?.city?.id} order by modifiedDate desc"
            list = Opportunity.findAll(opSql, [max: max, offset: offset])
            count = Opportunity.findAll(opSql).size()
        }
        else
        {
            sql += " and o.user.id = ${user.id} order by modifiedDate desc"
            def buffer = OpportunityTeam.findAll(sql, [max: max, offset: offset])
            buffer.each {
                list.add(it.opportunity)
            }

            count = OpportunityTeam.findAll(sql).size()
        }


        respond list, model: [opportunityTeamCount: count, stage: stage, status: status, params: params]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
    def show(Opportunity opportunity)
    {
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = " + "${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentProgress = OpportunityFlow.countByOpportunityAndStageLessThanEquals(opportunity, opportunity?.stage) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def progressPercent
        if (totalProgress > 0)
        {
            progressPercent = currentProgress / totalProgress
        }
        else
        {
            progressPercent = 0
        }
        respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
            creditReport: creditReport, opportunityContacts: opportunityContacts,
            activities: activities, progressPercent: progressPercent]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def historyShow()
    {
        def opportunity = OpportunityHistory.findById(params["id"])
        respond opportunity, model: [opportunity: opportunity]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def create()
    {
        def opportunity = Opportunity.findById(params["opportunity"])
        if (opportunity?.status == "Pending")
        {
            def username = springSecurityService.getPrincipal().username
            def user = User.findByUsername(username)
            def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
            if ((opportunityRole && opportunityRole?.teamRole?.name == "Approval") || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
            {
                def opportunityTeam = OpportunityTeam.findAllByOpportunity(opportunity)
                def userList
                def sqlStr = "("
                opportunityTeam.each {
                    sqlStr += it.user.id + ","
                }
                sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf(',')) + ")"
                userList = User.findAll("from User as u where u.id not in ${sqlStr}")
                respond new OpportunityTeam(params), model: [userList: userList, opportunity: opportunity]
            }
            else
            {
                flash.message = message(code: 'opportunity.edit.permission.denied')
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
                return
            }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def save(OpportunityTeam opportunityTeam)
    {
        if (opportunityTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityTeam.errors, view: 'create', model: [opportunity: opportunityTeam?.opportunity]
            return
        }

        Reporting[] reportingList = Reporting.findAllByUser(opportunityTeam.user)
        reportingList.each {
            User manager = it.manager
            OpportunityTeam ot = OpportunityTeam.findByOpportunityAndUser(opportunityTeam.opportunity, manager)
            if (!ot)
            {
                ot = new OpportunityTeam()
                ot.user = manager
                ot.opportunity = opportunityTeam.opportunity
                ot.save()
            }
        }

        opportunityTeam.save flush: true
        //添加edit权限
        // OpportunityRole opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunityTeam
        //     .opportunity,
        //     opportunityTeam.user,
        //     opportunityTeam
        //         .opportunity.stage)
        // if (!opportunityRole)
        // {
        //     opportunityRole = new OpportunityRole()
        //     opportunityRole.opportunity = opportunityTeam?.opportunity
        //     opportunityRole.user = opportunityTeam?.user
        //     opportunityRole.stage = opportunityTeam?.opportunity?.stage
        //     opportunityRole.teamRole = TeamRole.findByName("Edit")
        //     opportunityRole.save flush: true
        // }

        // if (opportunityTeam.opportunity.stage.code == "04" && opportunityTeam.user.department.name in ["权证组",
        // "支持组", "风控组"])
        // {
        //     messageService.sendMessage2(opportunityTeam.user.cellphone, "【中佳信】您好，有已报单订单（" + opportunityTeam
        // .opportunity.serialNumber + "）分配至您处，请及时处理！谢谢！")
        // }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: '', args: [message(code: 'opportunityTeam.label', default: 'OpportunityTeam'), opportunityTeam.id])
                // redirect(action: "show", id: opportunityTeam.opportunity.id)
                redirect(controller: "opportunity", action: "show", params: [id: opportunityTeam.opportunity.id])
            }
            '*' { respond opportunityTeam, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def edit(OpportunityTeam opportunityTeam)
    {
        respond opportunityTeam
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def update(OpportunityTeam opportunityTeam)
    {
        if (opportunityTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityTeam.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityTeam.errors, view: 'edit'
            return
        }
        opportunityTeam.save flush: true

        redirect opportunityTeam
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def delete(OpportunityTeam opportunityTeam)
    {
        if (opportunityTeam == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunityTeam?.opportunity, user,
                                                                               opportunityTeam?.opportunity?.stage)

        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            if (opportunityTeam.user == opportunityTeam.opportunity.user)
            {
                flash.message = message(code: 'opportunityTeam.delete.permission.denied')
                // redirect(action: "show", id: opportunityTeam.opportunity.id)
                redirect(controller: "opportunity", action: "show", params: [id: opportunityTeam.opportunity.id])
                return
            }
            else
            {
                //删除team同时删除权限
                def roles = OpportunityRole.findAllByOpportunityAndUser(opportunityTeam?.opportunity,
                                                                        opportunityTeam?.user)
                roles.each {
                    it.delete flush: true
                }
                opportunityTeam.delete flush: true

                // redirect(action: "show", id: opportunityTeam.opportunity.id)
                redirect(controller: "opportunity", action: "show", params: [id: opportunityTeam.opportunity.id])
            }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            // redirect(action: "show", id: opportunityTeam.opportunity.id)
            redirect(controller: "opportunity", action: "show", params: [id: opportunityTeam.opportunity.id])
            return
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityTeam' + '.label', default: 'OpportunityTeam'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER', 'ROLE_COMPLIANCE_MANAGER'])
    def searchOpportunity()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def max = params.max
        def offset = params.offset

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        def search = params['search']
        if (search == 'false')
        {
            def list = []
            respond list, view: 'index'
        }
        else
        {
            def stageCode = params["stage"]
            def stage = OpportunityStage.findByCode(stageCode)
            def status = params["status"]
            def type = params["type"]
            def currentStage = OpportunityStage.findById(params['currentStage'])
            def rongShuStatus = params.rongShuStatus
            def rongShuApprovalDate = params.rongShuApprovalDate
            def numberOfAccount = params.numberOfAccount

            def newSql = "select o.id from OpportunityTeam ot left join ot.opportunity o where 1 = 1 and ot.user.id = ${user?.id}"
            String sql = "from Opportunity as o where 1 = 1"
            def serialNumber = params["serialNumber"]
            if(rongShuApprovalDate)
            {
                sql += " and CONVERT(varchar(100), o.rongShuApprovalDate, 120) like '${rongShuApprovalDate}%'"
                newSql += " and CONVERT(varchar(100), o.rongShuApprovalDate, 120) like '${rongShuApprovalDate}%'"
            }
            if(rongShuStatus && rongShuStatus!='请选择资方审核状态')
            {
                sql += " and o.rongShuStatus = '${rongShuStatus}'"
                newSql += " and o.rongShuStatus = '${rongShuStatus}'"
            }
            if (serialNumber)
            {
                sql += " and o.serialNumber like '%${serialNumber}%'"
                newSql += " and o.serialNumber like '%${serialNumber}%'"
            }

            def fullName = params["fullName"]
            if (fullName)
            {
                sql += " and o.fullName like '%${fullName}%'"
                newSql += " and o.fullName like '%${fullName}%'"
            }
            if (stage)
            {
                sql += " and o.stage.id = ${stage.id}"
                newSql += " and o.stage.id = ${stage.id}"
            }
            else if (currentStage)
            {
                sql += " and o.stage.id = ${currentStage?.id}"
                newSql += " and o.stage.id = ${currentStage?.id}"
            }

            def contact = params["contact"]
            if (contact)
            {
                sql += " and o.contact.fullName like '%${contact}%'"
                newSql += " and o.contact.fullName like '%${contact}%'"
            }

            def mangerName = params["user"]
            if (mangerName)
            {
                sql += " and o.user.fullName like '%${mangerName}%'"
                newSql += " and o.user.fullName like '%${mangerName}%'"
            }

            def startTime = params["startTime"]
            def endTime = params["endTime"]
            if (startTime && endTime)
            {
                sql += " and o.createdDate between '${startTime}' and '${endTime}'"
                newSql += " and o.createdDate between '${startTime}' and '${endTime}'"
            }

            def city = params["city"]
            if (city && city != "null")
            {
                sql += " and o.contact.city.name = '${city}'"
                newSql += " and o.contact.city.name = '${city}'"
            }

            if (status)
            {
                sql += " and o.status = '${status}'"
                newSql += " and o.status = '${status}'"
            }
            if (type)
            {
                sql += " and o.type.code = '${type}'"
                newSql += " and o.type.code = '${type}'"
            }

            def mortgageCertificateType = params["mortgageCertificateType"]
            if (mortgageCertificateType && mortgageCertificateType != "null")
            {
                sql += " and o.mortgageCertificateType.name = '" + mortgageCertificateType + "'"
                newSql += " and o.mortgageCertificateType.name = '" + mortgageCertificateType + "'"
            }

            if (numberOfAccount && numberOfAccount != "null")
            {
                sql += " and o.id in (select opportunity.id from OpportunityFlow ofo where ofo.stage.id in (186, 23) and ofo.endTime is not null) "
                sql += "  and o.status <> 'Failed' and o.isTest = false and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款账号' and f.value= '${numberOfAccount}'))"
                newSql += " and o.id in (select opportunity.id from OpportunityFlow ofo where ofo.stage.id in (186, 23) and ofo.endTime is not null) "
                newSql += " and o.status <> 'Failed' and o.isTest = false and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款账号' and f.value= '${numberOfAccount}'))"
            }

            // def flexFieldBankAccount = params["flexFieldBankAccount"]
            // if (flexFieldBankAccount && flexFieldBankAccount != "null")
            // {
            //     sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款账号' and f.value= '${flexFieldBankAccount}'))"
            //     newSql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款账号' and f.value= '${flexFieldBankAccount}'))"
            // }

            // def estimatedTime = params["estimatedTime"]
            // if (estimatedTime)
            // {
            //     estimatedTime = estimatedTime.substring(0, 10)
            //     sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name in ('预计抵押登记时间', '预计出他项时间', '预计公证时间') and f.value like '%${estimatedTime}%'))"
            //     newSql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name in ('预计抵押登记时间', '预计出他项时间', '预计公证时间') and f.value like '%${estimatedTime}%'))"
            // }

            // def contactLevel = params["contactLevel"]
            // if (contactLevel && contactLevel != "null")
            // {
            //     sql += " and o.lender.level.name = '" + contactLevel + "'"
            //     newSql += " and o.lender.level.name = '" + contactLevel + "'"
            // }

            def actualAmountOfCreditMin = params["actualAmountOfCreditMin"]
            def actualAmountOfCreditMax = params["actualAmountOfCreditMax"]
            if (actualAmountOfCreditMin)
            {
                sql += " and o.actualAmountOfCredit >= '" + actualAmountOfCreditMin + "'"
                newSql += " and o.actualAmountOfCredit >= '" + actualAmountOfCreditMin + "'"
            }
            if (actualAmountOfCreditMax)
            {
                sql += " and o.actualAmountOfCredit <= '" + actualAmountOfCreditMax + "'"
                newSql += " and o.actualAmountOfCredit <= '" + actualAmountOfCreditMax + "'"
            }

            // def approvalStartTime = params["approvalStartTime"]
            // def approvalEndTime = params["approvalEndTime"]
            // if (approvalStartTime)
            // {
            //     sql += " and o.id in (select opportunity.id from OpportunityFlow where stage.code = '08' and startTime >= '${approvalStartTime}')"
            //     newSql += " and o.id in (select opportunity.id from OpportunityFlow where stage.code = '08' and startTime >= '${approvalStartTime}')"
            // }

            // def complianceChecking = params['complianceChecking']
            // if (params['complianceChecking'])
            // {
            //     sql += " and o.complianceChecking = ${complianceChecking}"
            //     newSql += " and o.complianceChecking = ${complianceChecking}"
            // }

            // if (approvalEndTime)
            // {
            //     sql += " and o.id in (select opportunity.id from OpportunityFlow where stage.code = '08' and startTime <= '${approvalEndTime}')"
            //     newSql += " and o.id in (select opportunity.id from OpportunityFlow where stage.code = '08' and startTime <= '${approvalEndTime}')"
            // }
            def list = []
            def flag = false
            def count
            if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")))
            {
                sql += " order by modifiedDate desc"
                list = Opportunity.findAll(sql, [max: max, offset: offset])
                def list1 = Opportunity.findAll(sql)
                count = list1.size()
            }
            else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")))
            {
                sql += "and o.user.city.id = ${user?.city?.id} order by modifiedDate desc"
                list = Opportunity.findAll(sql, [max: max, offset: offset])
                def list1 = Opportunity.findAll(sql)
                count = list1.size()
            }
            else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPANY_ADMINISTRATOR")))
            {
                newSql += " order by o.modifiedDate desc"

                println "sql1:" + newSql
                def returnList = OpportunityTeam.executeQuery(newSql, [max: max, offset: offset])
                returnList?.each {
                    def listItem = []
                    def opportunityDomain = Opportunity.find("from Opportunity as o where o.id = ${it}")
                    listItem.add(opportunityDomain?.id)
                    listItem.add(opportunityDomain?.serialNumber)
                    listItem.add(opportunityDomain?.type?.name)
                    listItem.add(opportunityDomain?.stage?.name)
                    listItem.add(opportunityDomain?.status)
                    listItem.add(opportunityDomain?.fullName)
                    //listItem.add(opportunityDomain?.lender?.level?.description)
                    listItem.add(opportunityDomain?.contact?.fullName)
                    listItem.add(opportunityDomain?.user?.fullName)
                    listItem.add(opportunityDomain?.account?.name)
                    listItem.add(opportunityDomain?.product?.name)
                    listItem.add(opportunityDomain?.loanAmount)
                    listItem.add(opportunityDomain?.requestedAmount)
                    listItem.add(opportunityDomain?.actualAmountOfCredit)
                    listItem.add(opportunityDomain?.loanDuration)
                    listItem.add(opportunityDomain?.actualLoanDuration)
                    def oof1 = OpportunityFlow.executeQuery("select ofo.startTime from OpportunityFlow as ofo where ofo.opportunity.id = ${it} and ofo.stage.code = '08'")
                    listItem.add(oof1[0]?.format("yyyy-MM-dd HH:mm:ss"))
                    listItem.add(opportunityDomain?.mortgageCertificateType?.name)
                    listItem.add(opportunityDomain?.causeOfFailure?.name)
                    listItem.add(opportunityDomain?.lastAction?.name)
                    listItem.add(opportunityDomain?.createdDate?.format("yyyy-MM-dd HH:mm:ss"))
                    def accountDoor = OpportunityFlexField.executeQuery("select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it} and c.flexFieldCategory.name = '资金渠道') and o.name = '放款账号'")[0]
                    listItem.add(accountDoor)
                    listItem.add(opportunityDomain?.rongShuStatus)
                    listItem.add(opportunityDomain?.rongShuApprovalDate == null ? "" :opportunityDomain?.rongShuApprovalDate?.format("yyyy-MM-dd HH:mm:ss"))
                    listItem.add(opportunityDomain?.memo)

                    // 放款时间
                    // // 放款通道
                    // def oof2 = OpportunityFlexField.executeQuery("select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it} and c.flexFieldCategory.id = 13) and o.name = '放款通道'")

                    // // 放款账号
                    // def oof3 = OpportunityFlexField.executeQuery("select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it} and c.flexFieldCategory.id = 13) and o.name = '放款账号'")


                    // listItem.add(oof2[0])
                    // listItem.add(oof3[0])

                    list.add(listItem)
                }

                def list1 = OpportunityTeam.executeQuery(newSql)
                println "list1:" + list1?.size()
                count = list1.size()

                flag = true
            }

            def opportunity = new Opportunity(params)
            def opportunityStage = OpportunityStage.findByName(stage)
            opportunity.stage = opportunityStage

            if (flag)
            {
                respond list, model: [opportunityTeamCount: count, opportunity: opportunity, stage: stage, status: status, params: params, type: type, list: list,numberOfAccount:numberOfAccount,rongShuApprovalDate:rongShuApprovalDate,rongShuStatus:rongShuStatus], view: 'index1'
            }
            else
            {
                respond list, model: [opportunityTeamCount: count, opportunity: opportunity, stage: stage, status: status, params: params, type: type,numberOfAccount:numberOfAccount,rongShuStatus:rongShuStatus,rongShuApprovalDate:rongShuApprovalDate], view: 'index'
            }
        }
    }
}

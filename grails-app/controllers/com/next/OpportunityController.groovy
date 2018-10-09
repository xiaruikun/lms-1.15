package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils
import org.springframework.security.access.annotation.Secured

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER', 'ROLE_COMPLIANCE_MANAGER', 'ROLE_INVESTOR'])
@Transactional(readOnly = true)
class OpportunityController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def billsService
    def opportunityService
    def contactservice
    def springSecurityService
    def assetValueService
    //    def liquidityRiskReportService
    def opportunityNotificationService
    def creditReportService
    def contactService
    def opportunityStatisticsService

    def foticAheadService
    def avicAheadSubmitService

    @Secured(['ROLE_INVESTOR'])
    def indexByInvestor(Integer max)
    {
        params.max = 10
        max = 10

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def offset = params.offset
        def count
        def list = []
        def list1 = []
        def opportunityList = []
        //  if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_INVESTOR")))
        //  {
        //      opportunityList = Opportunity.findAll("from Opportunity where status in ('Completed', 'Pending')")
        //      opportunityList?.each {
        //        def currentFlow = OpportunityFlow.findByStageAndOpportunity(it?.stage, it)
        //        def approveStage = OpportunityStage.findByCode("23")
        //        def approveFlow = OpportunityFlow.findByStageAndOpportunity(approveStage, it)
        //        if (currentFlow?.executionSequence >= approveFlow?.executionSequence)
        //        {
        //          if (approveFlow.startTime >= '2017-01-01 00:00:00' && approveFlow.startTime <= '2017-06-30 00:00:00')
        //          {
        //            list1.add(it)
        //          }
        //        }
        //      }
        //
        //      //解决分页问题
        //      for (
        //          def i = offset;
        //              i < offset + max;
        //              i++)
        //      {
        //          if (list1[i])
        //          {
        //              list.add(list1[i])
        //          }
        //          else
        //          {
        //              break
        //          }
        //      }
        //      count = list1?.size()
        //  }
        println "count:" + count
        def opportunityStageList = OpportunityStage.findAll("from OpportunityStage where code in ('10','20','23','43','44','81','82','100')")
        respond list, model: [opportunityCount: count, opportunityStageList: opportunityStageList]
    }

    @Secured(['ROLE_INVESTOR'])
    def searchByInvestor()
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

        def mortgageCertificateType = params["mortgageCertificateType"]
        def contactLevel = params["contactLevel"]
        def actualAmountOfCreditMin = params["actualAmountOfCreditMin"]
        def actualAmountOfCreditMax = params["actualAmountOfCreditMax"]

        def prepareCity = params["prepareCity"]
        def currentStageId = params["currentStage"]
        def currentStage
        if (currentStageId)
        {
            currentStage = OpportunityStage.findById(currentStageId)
        }
        def opportunityRoleList = []
        def count
        String sql = "from OpportunityRole as otr where otr.user.id = ${user?.id} and otr.stage.id = otr.opportunity.stage.id"
        String roleSql = "select otr.id from Opportunity as otr where 1=1"
        if (serialNumber)
        {
            sql += " and otr.opportunity.serialNumber like '%${serialNumber}%'"
            roleSql += " and otr.serialNumber like '%${serialNumber}%'"
        }
        if (fullName)
        {
            sql += " and otr.opportunity.fullName like '%${fullName}%'"
            roleSql += " and otr.fullName like '%${fullName}%'"
        }
        if (contact)
        {
            sql += " and otr.opportunity.contact.fullName like '%${contact}%'"
            roleSql += " and otr.contact.fullName like '%${contact}%'"
        }
        if (mangerName)
        {
            sql += " and otr.opportunity.user.fullName like '%${mangerName}%'"
            roleSql += " and otr.user.fullName like '%${mangerName}%'"
        }
        if (stageName)
        {
            sql += " and otr.opportunity.stage.name = '${stageName}'"
            roleSql += " and otr.stage.name = '${stageName}'"
        }

        if (currentStage)
        {
            sql += " and o.stage.id = ${currentStage?.id}"
            roleSql += " and otr.stage.id = ${currentStage?.id}"
        }

        if (startTime && endTime)
        {
            sql += " and otr.opportunity.createdDate between '${startTime}' and '${endTime}'"
            roleSql += " and otr.createdDate between '${startTime}' and '${endTime}'"
        }

        if (city && city != "null")
        {
            sql += " and otr.opportunity.contact.city.name = '${city}'"
            roleSql += " and otr.contact.city.name = '${city}'"
        }
        if (mortgageCertificateType && mortgageCertificateType != "null")
        {
            sql += " and otr.opportunity.mortgageCertificateType.name = '${mortgageCertificateType}'"
            roleSql += " and otr.mortgageCertificateType.name = '${mortgageCertificateType}'"
        }

        if (contactLevel && contactLevel != "null")
        {
            sql += " and otr.opportunity.lender.level.name = '${contactLevel}'"
            roleSql += " and otr.lender.level.name = '${contactLevel}'"
        }

        if (actualAmountOfCreditMin)
        {
            sql += " and otr.opportunity.actualAmountOfCredit >= '${actualAmountOfCreditMin}'"
            roleSql += " and otr.actualAmountOfCredit >= '${actualAmountOfCreditMin}'"
        }

        if (actualAmountOfCreditMax)
        {
            sql += " and otr.opportunity.actualAmountOfCredit <= '${actualAmountOfCreditMax}'"
            roleSql += " and otr.actualAmountOfCredit <= '${actualAmountOfCreditMax}'"
        }


        sql += " order by otr.opportunity.modifiedDate desc"
        //  roleSql += " order by otr.modifiedDate desc"

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        def opportunityList = []
        def list1 = []
        def list = []
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_INVESTOR")))
        {
            roleSql += " and otr.status in ('Completed', 'Pending')"
            roleSql += " and otr.stage.code in ('10','20','23','43','44','81','82','100')"
            roleSql += " and otr.isTest = false"
            roleSql += " order by otr.modifiedDate desc"
            opportunityList = Opportunity.executeQuery(roleSql, [max: max, offset: offset])
            opportunityList?.each {
                def listItem = []
                def opportunityDomain = Opportunity.find("from Opportunity as o where o.id = ${it}")
                listItem.add(opportunityDomain?.id)
                listItem.add(opportunityDomain?.serialNumber)
                listItem.add(opportunityDomain?.type?.name)
                listItem.add(opportunityDomain?.stage?.name)
                listItem.add(opportunityDomain?.status)
                listItem.add(opportunityDomain?.fullName)
                listItem.add(opportunityDomain?.lender?.level?.description)
                listItem.add(opportunityDomain?.contact?.fullName)
                listItem.add(opportunityDomain?.user?.fullName)
                listItem.add(opportunityDomain?.account?.name)
                listItem.add(opportunityDomain?.product?.name)
                listItem.add(opportunityDomain?.loanAmount)
                listItem.add(opportunityDomain?.requestedAmount)
                listItem.add(opportunityDomain?.actualAmountOfCredit)
                listItem.add(opportunityDomain?.loanDuration)
                listItem.add(opportunityDomain?.actualLoanDuration)
                listItem.add(opportunityDomain?.mortgageCertificateType?.name)
                listItem.add(opportunityDomain?.causeOfFailure?.name)
                listItem.add(opportunityDomain?.lastAction?.name)
                listItem.add(opportunityDomain?.createdDate)
                listItem.add(opportunityDomain?.memo)
                def oof1 = OpportunityFlow.executeQuery("select ofo.startTime from OpportunityFlow as ofo where ofo.opportunity.id = ${it} and ofo.stage.code = '08'")
                listItem.add(oof1[0])

                list.add(listItem)
            }
            list1 = Opportunity.executeQuery(roleSql)
            count = list1.size()
            //  opportunityList = Opportunity.findAll(roleSql)
            //  opportunityList?.each {
            //    def currentFlow = OpportunityFlow.findByStageAndOpportunity(it?.stage, it)
            //    def approveStage = OpportunityStage.findByCode("23")
            //    def approveFlow = OpportunityFlow.findByStageAndOpportunity(approveStage, it)
            //    if (currentFlow?.executionSequence >= approveFlow?.executionSequence)
            //    {
            //     //  list1.add(it)
            //      String time1 = "2016-10-01"
            //      String time2 = "2017-06-30"
            //
            //         if ((approveFlow?.startTime >= sdf.parse(time1)) && (approveFlow?.startTime <= sdf.parse(time2)) && !it?.isTest)
            //        {
            //          list1.add(it)
            //        }
            //
            //    }
            //  }

            //解决分页问题
            //  for (
            //      def i = offset;
            //          i < offset + max;
            //          i++)
            //  {
            //      if (list1[i])
            //      {
            //          list.add(list1[i])
            //      }
            //      else
            //      {
            //          break
            //      }
            //  }
            //  count = list1?.size()
        }
        def opportunityStageList = OpportunityStage.findAll("from OpportunityStage where code in ('10','20','23','43','44','81','82','100')")
        respond list, model: [list: list, opportunityCount: count, params: params, opportunityStageList: opportunityStageList], view: 'indexByInvestor'
    }

    @Secured(['ROLE_INVESTOR'])
    @Transactional
    def showByInvestor(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def opportunityStage = OpportunityStage.findByCode("38")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        //        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")
        def transactionRecords
        if (!opportunity?.type || opportunity?.type?.code == "10")
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.id} order by createdDate asc")
        }
        else
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.parent?.id} order by createdDate asc")
        }


        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }

        if (totalProgress > 0)
        {
            progressPercent = currentProgress / totalProgress
        }
        else
        {
            progressPercent = 0
        }


        // def owner = opportunity?.user
        // def cityName = owner?.city?.name
        def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")
        // if (collaterals?.size() == 1)
        // {
        //     def collateral = Collateral.findByOpportunity(opportunity)
        //     if (!collateral?.propertySerialNumber && opportunity?.propertySerialNumber)
        //     {
        //         collateral.propertySerialNumber = opportunity?.propertySerialNumber
        //     }
        //     if (!collateral?.firstMortgageAmount && opportunity?.firstMortgageAmount)
        //     {
        //         collateral.firstMortgageAmount = opportunity?.firstMortgageAmount
        //     }
        //     if (!collateral?.secondMortgageAmount && opportunity?.secondMortgageAmount)
        //     {
        //         collateral.secondMortgageAmount = opportunity?.secondMortgageAmount
        //     }
        //     if (!collateral?.mortgageType && opportunity?.mortgageType)
        //     {
        //         collateral.mortgageType = opportunity?.mortgageType
        //     }
        //     if (!collateral?.typeOfFirstMortgage && opportunity?.typeOfFirstMortgage)
        //     {
        //         collateral.typeOfFirstMortgage = opportunity?.typeOfFirstMortgage
        //     }
        //     collateral.save flush: true
        // }
        def canSpecialApproval = false
        def specialApprovalAttachments = Attachments.findByOpportunityAndType(opportunity, AttachmentType.findByName('特批签呈'))
        if (specialApprovalAttachments && opportunity?.status == 'Failed' && currentRole?.teamRole?.name == 'Approval')
        {
            if (!(opportunity?.subtype))
            {
                canSpecialApproval = true
            }
            else if (opportunity?.subtype?.name == '正常审批')
            {
                canSpecialApproval = true
            }
        }
        def canQueryPrice = true

        def regionalRiskManageStage = OpportunityStage.findByCode("08")
        def regionalRiskManageFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, regionalRiskManageStage)
        if (currentFlow?.executionSequence >= regionalRiskManageFlow?.executionSequence)
        {
            canQueryPrice = false
        }
        def canCreditReportShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
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
        def canAttachmentsShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")))
        {
            canAttachmentsShow = true
        }
        else if (currentRole?.teamRole?.name == 'Approval')
        {
            canAttachmentsShow = true
        }
        def canPhotosShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CUSTOMER_SERVICE_REPRESENTATIVE")))
        {
            canPhotosShow = true
        }
        def canLoanReceiptShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            canLoanReceiptShow = true
        }

        def canInterestEdit = true
        def loanCompletionStage = OpportunityStage.findByCode("10")
        def loanCompletionFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, loanCompletionStage)
        if ((opportunity?.parent && opportunity?.type?.code == '30') || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")))
        {
            canInterestEdit = true
        }
        else if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
        {
            canInterestEdit = false
        }

        def canbillsShow = false
        if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
        {
            canbillsShow = true
        }

        def billsItems
        def bills = Bills.findByOpportunity(opportunity)
        if (opportunity?.type?.name == "抵押贷款" && bills)
        {
            billsItems = BillsItem.findAllByBills(bills)
        }
        else if (opportunity?.type?.name != "抵押贷款")
        {
            bills = Bills.findByOpportunity(opportunity?.parent)
            if (bills)
            {
                billsItems = BillsItem.findAllByBills(bills)
            }
        }
        def opportunityComments = OpportunityComments.findAllByOpportunityAndAction(opportunity, null)
        def opportunityWorkflows = []
        def territory = opportunity?.territory
        if (!territory)
        {
            territory = TerritoryAccount.findByAccount(opportunity?.account)?.territory
        }
        def territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAllByTerritory(territory)
        territoryOpportunityWorkflows?.each {
            opportunityWorkflows.add(it?.workflow)
        }
        //layout
        def opportunityLayout
        if (!opportunityLayout)
        {
            opportunityFlows?.each {
                if (it?.stage == opportunity?.stage)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }

            opportunityRoles?.each {
                if (it?.stage == opportunity?.stage && it?.user == user && it?.opportunityLayout)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }

            opportunityTeams?.each {
                if (it?.user == user && it?.opportunityLayout)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_INVESTOR")))
        {
            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                creditReport: creditReport,
                opportunityContacts: opportunityContacts,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, transactionRecords: transactionRecords, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals, opportunityWorkflows: opportunityWorkflows, billsItems: billsItems, canQueryPrice: canQueryPrice, opportunityComments: opportunityComments, canCreditReportShow: canCreditReportShow, canAttachmentsShow: canAttachmentsShow, canPhotosShow: canPhotosShow, canLoanReceiptShow: canLoanReceiptShow, canInterestEdit: canInterestEdit, canbillsShow: canbillsShow],
                    view: 'opportunityLayout30'
        }
    }

    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def opportunityLayout24(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                def history
                if (opportunity && opportunity?.serialNumber)
                {
                    history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
                }
                def code = opportunity?.stage?.code
                def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
                def opportunityStage = OpportunityStage.findByCode("38")
                def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
                //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
                def opportunityContacts = OpportunityContact.findAll("from OpportunityContact where opportunity.id = ${opportunity.id} order by type.id")
                //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
                def activities = Activity.findAllByOpportunity(opportunity)
                def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
                def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
                def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                def progressPercent
                def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

                def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

                def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
                def subUsers = []
                def reportings
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    reportings = Reporting.findAllByManager(user)
                    reportings?.each {
                        subUsers.add(it?.user)
                    }
                }

                respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                    //                    creditReport: creditReport,
                    opportunityContacts: opportunityContacts,
                    activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
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

    /**
     * @Author 班旭娟
     * @CreatedDate 2017-4-25
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def loanApplicationForm(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def opportunityStage = OpportunityStage.findByCode("38")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
        def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }
        def attachments = Attachments.findAllByOpportunity(opportunity)
        respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
            //                    creditReport: creditReport,
            opportunityContacts: opportunityContacts,
            activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow, attachments: attachments]

    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def opportunityLayout09Edit(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                def history
                if (opportunity && opportunity?.serialNumber)
                {
                    history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
                }
                def code = opportunity?.stage?.code
                def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
                def opportunityStage = OpportunityStage.findByCode("38")
                def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
                //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
                //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
                def activities = Activity.findAllByOpportunity(opportunity)
                def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
                def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
                def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                def progressPercent
                def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

                def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

                def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
                def subUsers = []
                def reportings
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    reportings = Reporting.findAllByManager(user)
                    reportings?.each {
                        subUsers.add(it?.user)
                    }
                }
                def attachments = Attachments.findAllByOpportunity(opportunity)
                respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                    //                    creditReport: creditReport,
                    opportunityContacts: opportunityContacts,
                    activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow, attachments: attachments]
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def opportunityLayout17Edit(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                def history
                if (opportunity && opportunity?.serialNumber)
                {
                    history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
                }
                def code = opportunity?.stage?.code
                def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
                def opportunityStage = OpportunityStage.findByCode("38")
                def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
                //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
                //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
                def activities = Activity.findAllByOpportunity(opportunity)
                def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
                def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
                def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                def progressPercent
                def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

                def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

                def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
                def subUsers = []
                def reportings
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    reportings = Reporting.findAllByManager(user)
                    reportings?.each {
                        subUsers.add(it?.user)
                    }
                }
                respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                    //                    creditReport: creditReport,
                    opportunityContacts: opportunityContacts,
                    activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def opportunityLayout11(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def opportunityStage = OpportunityStage.findByCode("38")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        //        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
        def collaterals = Collateral.findAllByOpportunity(opportunity)

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }
        respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
            //            creditReport: creditReport,
            opportunityContacts: opportunityContacts,
            activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
    }

    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def opportunityZhuShen(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def opportunityStage = OpportunityStage.findByCode("38")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        //        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
        def collaterals = Collateral.findAllByOpportunity(opportunity)

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }
        respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
            //            creditReport: creditReport,
            opportunityContacts: opportunityContacts,
            activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    def applicationForm(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        //        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")
        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }
        if (totalProgress > 0)
        {
            progressPercent = currentProgress / totalProgress
        }
        else
        {
            progressPercent = 0
        }
        if (opportunity?.type?.code == '20')
        {
            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                creditReport: creditReport,
                opportunityContacts: opportunityContacts,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, collaterals: collaterals], view: 'opportunityLayout08'
        }
        else
        {
            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                creditReport: creditReport,
                opportunityContacts: opportunityContacts,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, collaterals: collaterals], view: 'opportunityLayout03'
        }
    }

    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
    def index(Integer max)
    {
        params.max = 10
        max = 10

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def offset = params.offset

        def list = []
        def count
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            list = Opportunity.findAll("from Opportunity as o where o.user.id = ${user.id} order by modifiedDate " + "desc", [max: max, offset: offset])
            count = list.size()
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPANY_ADMINISTRATOR")))
        {
            list = Opportunity.findAll("from Opportunity as o where o.user.id = ${user.id} order by modifiedDate " + "desc", [max: max, offset: offset])
            count = list.size()
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            list = Opportunity.findAll("from Opportunity as c order by c.modifiedDate desc", [max: max, offset: offset])
            count = list.size()
        }

        respond list, model: [opportunityCount: count]
    }

    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
    def searchOpportunity()
    {
        params.max = 10
        def max = 10

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def offset = params.offset

        def stage = params["stage"]
        if (!OpportunityStage.findByName(stage))
        {
            stage = ""
        }
        def causeOfFailure = params["causeOfFailure"]
        if (!CauseOfFailure.findByName(causeOfFailure))
        {
            causeOfFailure = ""
        }

        String sql = "from Opportunity as o where 1 = 1"
        def serialNumber = params["serialNumber"]
        if (serialNumber)
        {
            sql += " and o.serialNumber = '" + serialNumber + "'"
        }

        def fullName = params["fullName"]
        if (fullName)
        {
            sql += " and o.fullName = '" + fullName + "'"
        }
        if (stage)
        {
            sql += " and o.stage.name = '" + stage + "'"
        }
        if (causeOfFailure)
        {
            sql += " and o.causeOfFailure.name = '" + causeOfFailure + "'"
        }

        def mortgageCertificateType = params["mortgageCertificateType"]
        if (mortgageCertificateType)
        {
            sql += " and o.mortgageCertificateType.name = '" + mortgageCertificateType + "'"
        }


        def list = []
        def count
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            sql += " and o.user.id = ${user.id} order by modifiedDate desc"
            list = Opportunity.findAll(sql, [max: max, offset: offset])
            count = list.size()
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPANY_ADMINISTRATOR")))
        {
            sql += " and o.user.id = ${user.id} order by modifiedDate desc"
            list = Opportunity.findAll(sql, [max: max, offset: offset])
            count = list.size()
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            sql += " order by modifiedDate desc"
            list = Opportunity.findAll(sql, [max: max, offset: offset])
            count = list.size()
        }

        def opportunity = new Opportunity(params)
        def opportunityStage = OpportunityStage.findByName(stage)
        def causeOfFail = CauseOfFailure.findByName(causeOfFailure)
        opportunity.stage = opportunityStage
        opportunity.causeOfFailure = causeOfFail

        respond list, model: [opportunityCount: count, opportunity: opportunity], view: 'index'
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_COMPLIANCE_MANAGER', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def show(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def opportunityStage = OpportunityStage.findByCode("38")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        //        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        //删除重复借款人（只删除opportunityContact）
        def lenders = OpportunityContact.findAllByOpportunityAndType(opportunity,OpportunityContactType.findByName("借款人"))
        def lenderContact = OpportunityContact.findByOpportunityAndTypeAndContact(opportunity,OpportunityContactType.findByName("借款人"),opportunity?.lender)
        if (lenders.size()>1&&lenderContact){
            lenders?.each {
                if (it.contact != opportunity.lender && it.contact.idNumber == opportunity.lender.idNumber) {
                    it.delete flush: true
                }
            }
        }

        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")
        //权利人入库单
        def notarizationsList = []
        if(opportunity.notarizations){
             notarizationsList = opportunity.notarizations.split(",")
        }
        def transactionRecords
        if (!opportunity?.type || opportunity?.type?.code == "10")
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.id} order by plannedStartTime asc")
        }
        else
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.parent?.id} order by plannedStartTime asc")
        }

        //查询相同房产证的订单（在途非测试）
        //def sameHouseOrders = Opportunity.executeQuery("select * from Collateral c left join c.opportunity o where c.propertySerialNumber = '${opportunity?.collaterals[0].propertySerialNumber}' and o.id != ${opportunity?.id} and ")

        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }

        if (totalProgress > 0)
        {
            progressPercent = currentProgress / totalProgress
        }
        else
        {
            progressPercent = 0
        }


        // def owner = opportunity?.user
        // def cityName = owner?.city?.name
        def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")
        // if (collaterals?.size() == 1)
        // {
        //     def collateral = Collateral.findByOpportunity(opportunity)
        //     if (!collateral?.propertySerialNumber && opportunity?.propertySerialNumber)
        //     {
        //         collateral.propertySerialNumber = opportunity?.propertySerialNumber
        //     }
        //     if (!collateral?.firstMortgageAmount && opportunity?.firstMortgageAmount)
        //     {
        //         collateral.firstMortgageAmount = opportunity?.firstMortgageAmount
        //     }
        //     if (!collateral?.secondMortgageAmount && opportunity?.secondMortgageAmount)
        //     {
        //         collateral.secondMortgageAmount = opportunity?.secondMortgageAmount
        //     }
        //     if (!collateral?.mortgageType && opportunity?.mortgageType)
        //     {
        //         collateral.mortgageType = opportunity?.mortgageType
        //     }
        //     if (!collateral?.typeOfFirstMortgage && opportunity?.typeOfFirstMortgage)
        //     {
        //         collateral.typeOfFirstMortgage = opportunity?.typeOfFirstMortgage
        //     }
        //     collateral.save flush: true
        // }
        def canSpecialApproval = false
        def specialApprovalAttachments = Attachments.findByOpportunityAndType(opportunity, AttachmentType.findByName('特批签呈'))
        if (specialApprovalAttachments && opportunity?.status == 'Failed' && currentRole?.teamRole?.name == 'Approval')
        {
            if (!(opportunity?.subtype))
            {
                canSpecialApproval = true
            }
            else if (opportunity?.subtype?.name == '正常审批')
            {
                canSpecialApproval = true
            }
        }
        def canQueryPrice = true

        def regionalRiskManageStage = OpportunityStage.findByCode("08")
        def regionalRiskManageFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, regionalRiskManageStage)
        if (currentFlow?.executionSequence >= regionalRiskManageFlow?.executionSequence)
        {
            canQueryPrice = false
        }
        def canCreditReportShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
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

        def canAttachmentsShow = false
      //  def role1 = OpportunityRole.findByTeamRoleAndUserAndStageAndOpportunity(TeamRole.findByName("Approval"),user,OpportunityStage.findByName("抵押已完成"))
        def role1 = OpportunityRole.executeQuery("select id from OpportunityRole where teamRole.name = 'Approval' and user.id = ${user.id} and stage.name = '抵押已完成' and opportunity.id = ${opportunity.id}")
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            canAttachmentsShow = true
        }
        else if (currentRole?.teamRole?.name == 'Approval')
        {
            canAttachmentsShow = true
        }
        if(role1 && opportunity.stage?.code == "44")
        {
            canAttachmentsShow = true
        }
        def canPhotosShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CUSTOMER_SERVICE_REPRESENTATIVE")))
        {
            canPhotosShow = true
        }
        def canLoanReceiptShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            canLoanReceiptShow = true
        }

        def canInterestEdit = true
        def loanCompletionStage = OpportunityStage.findByCode("08")
        def loanCompletionFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, loanCompletionStage)
        if ((opportunity?.parent && opportunity?.type?.code == '30') || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")))
        {
            canInterestEdit = true
        }
        else if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
        {
            canInterestEdit = false
        }

        def canbillsShow = false
        if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
        {
            canbillsShow = true
        }
        def users = OpportunityContact.findAllByOpportunity(opportunity?.parent)
        def borrowers = ""
        users.each {
            borrowers += it.contact?.fullName + ","
        }
        if (users)
        {
            borrowers = StringUtils.substringBeforeLast(borrowers, ",")
        }
        def billsItems
        def bills = Bills.findByOpportunity(opportunity)
        if (opportunity?.type?.name == "抵押贷款" && bills)
        {
            billsItems = BillsItem.findAllByBills(bills,[sort:"period"])
        }
        else if (opportunity?.type?.name != "抵押贷款")
        {
            bills = Bills.findByOpportunity(opportunity?.parent)
            if (bills)
            {
                billsItems = BillsItem.findAllByBills(bills,[sort:"period"])
            }
        }
        def opportunityComments = OpportunityComments.findAllByOpportunityAndAction(opportunity, null)
        def opportunityWorkflows = []
        def territory = opportunity?.territory
        if (!territory)
        {
            territory = TerritoryAccount.findByAccount(opportunity?.account)?.territory
        }
        def territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAllByTerritory(territory)
        territoryOpportunityWorkflows?.each {
            opportunityWorkflows.add(it?.workflow)
        }
        //layout
        def opportunityLayout
        if (!opportunityLayout)
        {
            opportunityFlows?.each {
                if (it?.stage == opportunity?.stage)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }

            opportunityRoles?.each {
                if (it?.stage == opportunity?.stage && it?.user == user && it?.opportunityLayout)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }

            opportunityTeams?.each {
                if (it?.user == user && it?.opportunityLayout)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                creditReport: creditReport,
                opportunityContacts: opportunityContacts, borrowers: borrowers,notarizationsList:notarizationsList,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, transactionRecords: transactionRecords, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals, opportunityWorkflows: opportunityWorkflows, billsItems: billsItems, canQueryPrice: canQueryPrice, opportunityComments: opportunityComments, canCreditReportShow: canCreditReportShow, canAttachmentsShow: canAttachmentsShow, canPhotosShow: canPhotosShow, canLoanReceiptShow: canLoanReceiptShow, canInterestEdit: canInterestEdit, canbillsShow: canbillsShow], view: 'administrator'
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")))
        {
            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                creditReport: creditReport,
                opportunityContacts: opportunityContacts, transactionRecords: transactionRecords, borrowers: borrowers,notarizationsList:notarizationsList,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals, opportunityWorkflows: opportunityWorkflows, billsItems: billsItems,
                canQueryPrice: canQueryPrice, opportunityComments: opportunityComments, canCreditReportShow: canCreditReportShow, canAttachmentsShow: canAttachmentsShow, canPhotosShow: canPhotosShow, canLoanReceiptShow: canLoanReceiptShow, canInterestEdit: canInterestEdit, canbillsShow: canbillsShow], view: 'postLoanManager01'
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
        {
            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                creditReport: creditReport,
                opportunityContacts: opportunityContacts, transactionRecords: transactionRecords, borrowers: borrowers,notarizationsList:notarizationsList,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals, opportunityWorkflows: opportunityWorkflows, billsItems: billsItems,
                canQueryPrice: canQueryPrice, opportunityComments: opportunityComments, canCreditReportShow: canCreditReportShow, canAttachmentsShow: canAttachmentsShow, canPhotosShow: canPhotosShow, canLoanReceiptShow: canLoanReceiptShow, canInterestEdit: canInterestEdit, canbillsShow: canbillsShow], view: 'opportunityLayout15'
        }
        else if (opportunityLayout)
        {
            if (code == '51' && currentRole && currentRole?.teamRole?.name == "Approval")
            {
                flash.message = "请重新进行房产询值，重查大数据，重做产调"
            }

            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                creditReport: creditReport,
                opportunityContacts: opportunityContacts, transactionRecords: transactionRecords, borrowers: borrowers,notarizationsList:notarizationsList,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals, billsItems: billsItems, canQueryPrice: canQueryPrice, opportunityComments: opportunityComments, canCreditReportShow: canCreditReportShow, canAttachmentsShow: canAttachmentsShow, canPhotosShow: canPhotosShow, canLoanReceiptShow: canLoanReceiptShow, canInterestEdit: canInterestEdit, canbillsShow: canbillsShow], view: opportunityLayout
        }
        else
        {
            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                creditReport: creditReport,
                opportunityContacts: opportunityContacts, transactionRecords: transactionRecords, borrowers: borrowers,notarizationsList:notarizationsList,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals, billsItems: billsItems, canQueryPrice: canQueryPrice, opportunityComments: opportunityComments, canCreditReportShow: canCreditReportShow, canAttachmentsShow: canAttachmentsShow, canPhotosShow: canPhotosShow, canLoanReceiptShow: canLoanReceiptShow, canInterestEdit: canInterestEdit, canbillsShow: canbillsShow]
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def replaceFlow()
    {
        def opportunity = Opportunity.findById(params['opportunity'])
        def workflow = OpportunityWorkflow.findById(params['workflow'])
        def operation = params['operation']
        def territory = opportunity?.territory
        if (!territory)
        {
            territory = TerritoryAccount.findByAccount(opportunity?.account)?.territory
        }
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentWorkflow = OpportunityWorkflowStage.findByWorkflowAndStage(workflow, opportunity?.stage)
        if ((currentFlow && currentWorkflow && currentWorkflow?.executionSequence >= currentFlow?.executionSequence && operation == "replace") || operation == "reset")
        {
            def opportunityFlows
            def opportunityRoles
            def opportunityNotifications
            def opportunityTeams
            def opportunityFlowAttachmentTypes
            def opportunityFlowForeStages
            def opportunityFlowConditions
            def opportunityFlowNextStageS
            def opportunityEvents
            def opportunityFlexFieldCategorys
            def opportunityFlexFields
            def opportunityFlexFieldValues
            if (operation == "replace")
            {
                opportunityFlows = OpportunityFlow.findAllByOpportunityAndExecutionSequenceGreaterThan(opportunity, currentFlow?.executionSequence)
            }
            else if (operation == "reset")
            {
                opportunityFlows = OpportunityFlow.findAllByOpportunity(opportunity)
                opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                opportunityTeams?.each {
                    it.delete flush: true
                }
                opportunityRoles?.each {
                    it.delete flush: true
                }
                opportunityNotifications?.each {
                    it.delete flush: true
                }
                opportunityFlexFieldCategorys?.each { opportunityFlexFieldCategory ->
                    opportunityFlexFields = OpportunityFlexField.findAllByCategory(opportunityFlexFieldCategory)
                    opportunityFlexFields?.each { opportunityFlexField ->
                        opportunityFlexFieldValues = OpportunityFlexFieldValue.findAllByField(opportunityFlexField)
                        opportunityFlexFieldValues?.each {
                            it.delete flush: true
                        }
                        opportunityFlexField.delete flush: true
                    }
                    opportunityFlexFieldCategory.delete flush: true
                }
            }

            opportunityFlows?.each { opportunityFlow ->
                if (operation == "replace")
                {
                    opportunityRoles = OpportunityRole.findAllByOpportunityAndStage(opportunity, opportunityFlow?.stage)
                    opportunityNotifications = OpportunityNotification.findAllByOpportunityAndStage(opportunity, opportunityFlow?.stage)
                    opportunityRoles?.each {
                        it.delete flush: true
                    }
                    opportunityNotifications?.each {
                        it.delete flush: true
                    }
                }
                opportunityFlowConditions = OpportunityFlowCondition.findAllByFlow(opportunityFlow)
                opportunityFlowConditions?.each {
                    it.delete flush: true
                }
                opportunityFlowNextStageS = OpportunityFlowNextStage.findAllByFlow(opportunityFlow)
                opportunityFlowNextStageS?.each {
                    it.delete flush: true
                }
                opportunityEvents = OpportunityEvent.findAllByStage(opportunityFlow)
                opportunityEvents?.each {
                    it.delete flush: true
                }
                opportunityFlowAttachmentTypes = OpportunityFlowAttachmentType.findAllByStage(opportunityFlow)
                opportunityFlowAttachmentTypes?.each {
                    it.delete flush: true
                }
                opportunityFlowForeStages = OpportunityFlowNextStage.findAllByNextStage(opportunityFlow)
                opportunityFlowForeStages?.each {
                    it.delete flush: true
                }
                opportunityFlow.delete flush: true
            }
            def opportunityWorkflowStages
            def opportunityTeam
            def opportunityRole
            def opportunityNotification
            def opportunityFlow
            def opportunityFlowAttachmentType
            def opportunityFlowCondition
            def nextStage
            def opportunityFlowNextStage
            def opportunityEvent
            def territoryFlexFieldCategorys
            def opportunityFlexFieldCategory
            def opportunityFlexField
            def opportunityFlexFieldValue
            def parentTerritory
            if (operation == "replace")
            {
                opportunityWorkflowStages = OpportunityWorkflowStage.findAllByWorkflowAndExecutionSequenceGreaterThan(workflow, currentWorkflow?.executionSequence)
            }
            else if (operation == "reset")
            {
                opportunityWorkflowStages = OpportunityWorkflowStage.findAllByWorkflow(workflow)
                opportunityRoles = OpportunityWorkflowRole.findAllByWorkflow(workflow)
                opportunityNotifications = OpportunityWorkflowNotification.findAllByWorkflow(workflow)
                Boolean inheritTeam = true
                parentTerritory = territory
                while (parentTerritory)
                {
                    if (inheritTeam)
                    {
                        opportunityTeams = TerritoryTeam.findAllByTerritory(parentTerritory)
                        opportunityTeams?.each {
                            opportunityTeam = OpportunityTeam.findByOpportunityAndUser(opportunity, it?.user)
                            if (!opportunityTeam)
                            {
                                opportunityTeam = new OpportunityTeam()
                                opportunityTeam.opportunity = opportunity
                                opportunityTeam.user = it?.user
                                opportunityTeam.save flush: true
                            }
                        }

                        inheritTeam = parentTerritory?.inheritTeam
                        parentTerritory = parentTerritory?.parent
                    }
                    else
                    {
                        break
                    }
                }

                opportunityRoles?.each {
                    opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, it?.user, it?.stage)
                    if (!opportunityRole)
                    {
                        opportunityRole = new OpportunityRole()
                        opportunityRole.opportunity = opportunity
                        opportunityRole.user = it?.user
                        opportunityRole.teamRole = it?.teamRole
                        opportunityRole.stage = it?.stage
                        opportunityRole.opportunityLayout = it?.opportunityLayout
                        opportunityRole.save flush: true
                    }
                }
                opportunityNotifications?.each {
                    opportunityNotification = OpportunityNotification.findByOpportunityAndUserAndStageAndCellphone(opportunity, it?.user, it?.stage, it?.cellphone)
                    if (!opportunityNotification)
                    {
                        opportunityNotification = new OpportunityNotification()
                        opportunityNotification.opportunity = opportunity
                        opportunityNotification.user = it?.user
                        opportunityNotification.stage = it?.stage
                        opportunityNotification.messageTemplate = it?.messageTemplate
                        opportunityNotification.cellphone = it?.cellphone
                        opportunityNotification.save flush: true
                    }
                }
                opportunity.stage = OpportunityStage.findByCode("02")
                opportunity.save flush: true
            }
            parentTerritory = territory
            while (parentTerritory)
            {
                territoryFlexFieldCategorys = TerritoryFlexFieldCategory.findAllByTerritory(parentTerritory)
                territoryFlexFieldCategorys.each {
                    opportunityFlexFieldCategory = OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity, it?.flexFieldCategory)
                    if (!opportunityFlexFieldCategory)
                    {
                        opportunityFlexFieldCategory = new OpportunityFlexFieldCategory()
                        opportunityFlexFieldCategory.opportunity = opportunity
                        opportunityFlexFieldCategory.flexFieldCategory = it?.flexFieldCategory
                        opportunityFlexFieldCategory.save flush: true
                    }
                    it?.flexFieldCategory?.fields?.each {
                        opportunityFlexField = OpportunityFlexField.findByCategoryAndName(opportunityFlexFieldCategory, it?.name)
                        if (!opportunityFlexField)
                        {
                            opportunityFlexField = new OpportunityFlexField()
                            opportunityFlexField.category = opportunityFlexFieldCategory
                            opportunityFlexField.name = it?.name
                            opportunityFlexField.description = it?.description
                            opportunityFlexField.dataType = it?.dataType
                            opportunityFlexField.defaultValue = it?.defaultValue
                            opportunityFlexField.valueConstraints = it?.valueConstraints
                            opportunityFlexField.displayOrder = it?.displayOrder
                            opportunityFlexField.save flush: true
                        }
                        it?.values?.each {
                            opportunityFlexFieldValue = OpportunityFlexFieldValue.findByFieldAndValue(opportunityFlexField, it?.value)
                            if (!opportunityFlexFieldValue)
                            {
                                opportunityFlexFieldValue = new OpportunityFlexFieldValue()
                                opportunityFlexFieldValue.field = opportunityFlexField
                                opportunityFlexFieldValue.value = it?.value
                                opportunityFlexFieldValue.displayOrder = it?.displayOrder
                                opportunityFlexFieldValue.save flush: true
                            }
                        }
                    }
                }
                parentTerritory = parentTerritory?.parent
            }
            opportunityWorkflowStages?.each { opportunityWorkflowStage ->
                if (operation == "replace")
                {
                    opportunityRoles = OpportunityWorkflowRole.findAllByWorkflowAndStage(workflow, opportunityWorkflowStage?.stage)
                    opportunityNotifications = OpportunityWorkflowNotification.findAllByWorkflowAndStage(workflow, opportunityWorkflowStage?.stage)

                    opportunityRoles?.each {
                        opportunityTeam = OpportunityTeam.findByOpportunityAndUser(opportunity, it?.user)
                        if (!opportunityTeam)
                        {
                            opportunityTeam = new OpportunityTeam()
                            opportunityTeam.opportunity = opportunity
                            opportunityTeam.user = it?.user
                            opportunityTeam.save flush: true
                        }
                        opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, it?.user, it?.stage)
                        if (!opportunityRole)
                        {
                            opportunityRole = new OpportunityRole()
                            opportunityRole.opportunity = opportunity
                            opportunityRole.user = it?.user
                            opportunityRole.teamRole = it?.teamRole
                            opportunityRole.stage = it?.stage
                            opportunityRole.opportunityLayout = it?.opportunityLayout
                            opportunityRole.save flush: true
                        }
                    }
                    opportunityNotifications?.each {
                        opportunityTeam = OpportunityTeam.findByOpportunityAndUser(opportunity, it?.user)
                        if (!opportunityTeam)
                        {
                            opportunityTeam = new OpportunityTeam()
                            opportunityTeam.opportunity = opportunity
                            opportunityTeam.user = it?.user
                            opportunityTeam.save flush: true
                        }
                        opportunityNotification = OpportunityNotification.findByOpportunityAndUserAndStageAndCellphone(opportunity, it?.user, it?.stage, it?.cellphone)
                        if (!opportunityNotification)
                        {
                            opportunityNotification = new OpportunityNotification()
                            opportunityNotification.opportunity = opportunity
                            opportunityNotification.user = it?.user
                            opportunityNotification.stage = it?.stage
                            opportunityNotification.messageTemplate = it?.messageTemplate
                            opportunityNotification.cellphone = it?.cellphone
                            opportunityNotification.save flush: true
                        }
                    }
                }
                opportunityFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityWorkflowStage?.stage)
                if (!opportunityFlow)
                {
                    opportunityFlow = new OpportunityFlow()
                    opportunityFlow.opportunity = opportunity
                    opportunityFlow.stage = opportunityWorkflowStage?.stage
                    opportunityFlow.canReject = opportunityWorkflowStage?.canReject
                    opportunityFlow.executionSequence = opportunityWorkflowStage?.executionSequence
                    opportunityFlow.opportunityLayout = opportunityWorkflowStage?.opportunityLayout
                    opportunityFlow.document = opportunityWorkflowStage?.document
                    opportunityFlow.save flush: true
                }
                opportunityFlowAttachmentTypes = OpportunityWorkflowStageAttachmentType.findAllByStage(opportunityWorkflowStage)
                opportunityFlowAttachmentTypes.each {
                    opportunityFlowAttachmentType = OpportunityFlowAttachmentType.findByStageAndAttachmentType(opportunityFlow, it?.attachmentType)
                    if (!opportunityFlowAttachmentType)
                    {
                        opportunityFlowAttachmentType = new OpportunityFlowAttachmentType()
                        opportunityFlowAttachmentType.attachmentType = it?.attachmentType
                        opportunityFlowAttachmentType.stage = opportunityFlow
                        opportunityFlowAttachmentType.save flush: true
                    }
                }
            }
            opportunityWorkflowStages?.each {
                opportunityFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, it?.stage)
                it?.conditions?.each {
                    nextStage = OpportunityFlow.findByOpportunityAndStage(opportunity, it?.nextStage?.stage)
                    opportunityFlowCondition = OpportunityFlowCondition.findByFlowAndConditionAndComponentAndNextStage(opportunityFlow, it?.condition, it?.component, nextStage)
                    if (!opportunityFlowCondition)
                    {
                        opportunityFlowCondition = new OpportunityFlowCondition()
                        opportunityFlowCondition.flow = opportunityFlow
                        opportunityFlowCondition.condition = it?.condition
                        opportunityFlowCondition.message = it?.message
                        opportunityFlowCondition.component = it?.component
                        opportunityFlowCondition.executeSequence = it?.executeSequence
                        opportunityFlowCondition.nextStage = nextStage
                        opportunityFlowCondition.save flush: true
                    }
                }
                it?.nextStages?.each {
                    nextStage = OpportunityFlow.findByOpportunityAndStage(opportunity, it?.nextStage?.stage)
                    opportunityFlowNextStage = OpportunityFlowNextStage.findByFlowAndNextStage(opportunityFlow, nextStage)
                    if (!opportunityFlowNextStage)
                    {
                        opportunityFlowNextStage = new OpportunityFlowNextStage()
                        opportunityFlowNextStage.flow = opportunityFlow
                        opportunityFlowNextStage.nextStage = nextStage
                        opportunityFlowNextStage.save flush: true
                    }
                }
                it?.events?.each {
                    opportunityEvent = OpportunityEvent.findByStageAndName(opportunityFlow, it?.name)
                    if (!opportunityEvent)
                    {
                        opportunityEvent = new OpportunityEvent()
                        opportunityEvent.stage = opportunityFlow
                        opportunityEvent.name = it?.name
                        opportunityEvent.isSynchronous = it?.isSynchronous
                        opportunityEvent.executeSequence = it?.executeSequence
                        opportunityEvent.component = it?.component
                        opportunityEvent.script = it?.script
                        opportunityEvent.log = it?.log
                        opportunityEvent.startTime = it?.startTime
                        opportunityEvent.endTime = it?.endTime
                        opportunityEvent.save flush: true
                    }
                }
            }
            redirect controller: "opportunity", action: "show", method: "GET", id: opportunity?.id
            return
        }
        else
        {
            flash.message = "工作流替换失败"
            redirect controller: "opportunity", action: "show", method: "GET", id: opportunity?.id
            return
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def historyShow()
    {
        def opportunityHistory = OpportunityHistory.findById(params["id"])
        respond opportunityHistory
    }

    // @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    //
    // def historyShow2(Opportunity opportunity)
    // {
    //     def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
    //     respond opportunity, model: [CollateralAuditTrails: CollateralAuditTrails]
    // }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def historyShow2(Collateral collateral)
    {
        def CollateralAuditTrails = CollateralAuditTrail.findAllByParent(collateral)
        respond collateral, model: [CollateralAuditTrails: CollateralAuditTrails]
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR'])
    def create()
    {
        // def username = springSecurityService.getPrincipal().username
        // def user = User.findByUsername(username)
        // respond new Opportunity(params), model: [user: user]
        def contacts = Contact.findAllByType("Agent")
        respond new Opportunity(params), model: [contacts: contacts]
    }

    def getUserAndAccount()
    {
        def contact = Contact.findById(params['contact'])
        def user = contact?.user?.id
        def account = contact?.user?.account?.id
        render([status: "success", user: user, account: account] as JSON)
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def save(Opportunity opportunity)
    {
        println opportunity.notarizations
        if (opportunity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunity.errors, view: 'create'
            return
        }



        opportunity.save flush: true
        //订单初始化
        opportunityService.initOpportunity(opportunity)
        // 流通性评估
        //        liquidityRiskReportService.initReport(opportunity)
        opportunityService.assignToClient(opportunity)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunity.label',
                                                                                        default: 'Opportunity'),
                    opportunity.id])
                redirect opportunity
            }
            '*' { respond opportunity, [status: CREATED] }
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def edit(Opportunity opportunity)
    {
        def flag = params['flag']
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            respond opportunity, view: 'administratorEdit'
        }
        else if (opportunity?.status == "Pending")
        {

            def stage = opportunity?.stage
            def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)

            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                respond opportunity, model: [flag: flag]
            }
            else
            {
                flash.message = message(code: 'opportunity.edit.permission.denied')
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id, flag: flag])
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def edit01(Opportunity opportunity)
    {
        def flag = params['flag']
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            respond opportunity, view: 'opportunityLayoutEdit15'
        }
        else if (opportunity?.status == "Pending")
        {

            def stage = opportunity?.stage
            def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)

            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                respond opportunity, model: [flag: flag], view: 'opportunityLayoutEdit16'
            }
            else
            {
                flash.message = message(code: 'opportunity.edit.permission.denied')
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id, flag: flag])
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def opportunityLayout18(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                def history
                if (opportunity && opportunity?.serialNumber)
                {
                    history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
                }
                def code = opportunity?.stage?.code
                def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
                def opportunityStage = OpportunityStage.findByCode("38")
                def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
                //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
                //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
                def activities = Activity.findAllByOpportunity(opportunity)
                def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
                def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
                def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                def progressPercent
                def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

                def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

                def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
                def subUsers = []
                def reportings
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    reportings = Reporting.findAllByManager(user)
                    reportings?.each {
                        subUsers.add(it?.user)
                    }
                }
                respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                    //                    creditReport: creditReport,
                    opportunityContacts: opportunityContacts,
                    activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def opportunityLayout21(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            // if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            // {
            def history
            if (opportunity && opportunity?.serialNumber)
            {
                history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
            }
            def code = opportunity?.stage?.code
            def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
            def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
            def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
            def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
            def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
            def opportunityStage = OpportunityStage.findByCode("38")
            def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
            //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
            def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
            //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
            def activities = Activity.findAllByOpportunity(opportunity)
            def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
            def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
            def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
            def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
            def progressPercent
            def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

            def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
            def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

            def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
            def subUsers = []
            def reportings
            if (currentRole?.teamRole?.name == 'Approval')
            {
                reportings = Reporting.findAllByManager(user)
                reportings?.each {
                    subUsers.add(it?.user)
                }
            }

            def canQueryPrice = true

            def regionalRiskManageStage = OpportunityStage.findByCode("08")
            def regionalRiskManageFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, regionalRiskManageStage)
            if (currentFlow?.executionSequence >= regionalRiskManageFlow?.executionSequence)
            {
                canQueryPrice = false
            }

            def canAttachmentsShow = false
            if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
            {
                canAttachmentsShow = true
            }
            else if (currentRole?.teamRole?.name == 'Approval')
            {
                canAttachmentsShow = true
            }
            def canCreditReportShow = false
            if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR"))|| UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
            {
                canCreditReportShow = true
            }
            else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
            {
                if (opportunity?.stage?.code in ["08", "21", "50", "19", "41", "49", "09"])
                {
                    canCreditReportShow = true
                }
            }
            else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_HEAD_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CUSTOMER_SERVICE_REPRESENTATIVE")))
            {
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    canCreditReportShow = true
                }
            }
            def canInterestEdit = true
            def loanCompletionStage = OpportunityStage.findByCode("10")
            def loanCompletionFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, loanCompletionStage)
            if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
            {
                canInterestEdit = false
            }
            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                    creditReport: creditReport,
                opportunityContacts: opportunityContacts,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow, canQueryPrice: canQueryPrice, canAttachmentsShow: canAttachmentsShow, canCreditReportShow: canCreditReportShow, canInterestEdit: canInterestEdit]
            // }
            // else
            // {
            //     flash.message = message(code: 'opportunity.edit.permission.denied')
            //     redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            //     return
            // }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }

    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-7-10
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def opportunityLayout27(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            // if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            // {
            def history
            if (opportunity && opportunity?.serialNumber)
            {
                history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
            }
            def code = opportunity?.stage?.code
            def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
            def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
            def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
            def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
            def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
            def opportunityStage = OpportunityStage.findByCode("38")
            def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
            //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
            def opportunityContacts = OpportunityContact.findAll("from OpportunityContact where opportunity.id = ${opportunity.id} order by type.id")
            //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
            def activities = Activity.findAllByOpportunity(opportunity)
            def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
            def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
            def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
            def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
            def progressPercent
            def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

            def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
            def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

            def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
            def subUsers = []
            def reportings
            if (currentRole?.teamRole?.name == 'Approval')
            {
                reportings = Reporting.findAllByManager(user)
                reportings?.each {
                    subUsers.add(it?.user)
                }
            }

            def canQueryPrice = true

            def regionalRiskManageStage = OpportunityStage.findByCode("08")
            def regionalRiskManageFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, regionalRiskManageStage)
            if (currentFlow?.executionSequence >= regionalRiskManageFlow?.executionSequence)
            {
                canQueryPrice = false
            }

            def canAttachmentsShow = false
            if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
            {
                canAttachmentsShow = true
            }
            else if (currentRole?.teamRole?.name == 'Approval')
            {
                canAttachmentsShow = true
            }
            def canCreditReportShow = false
            if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
            {
                canCreditReportShow = true
            }
            else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
            {
                if (opportunity?.stage?.code in ["08", "21", "50", "19", "41", "49", "09"])
                {
                    canCreditReportShow = true
                }
            }
            else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_HEAD_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CUSTOMER_SERVICE_REPRESENTATIVE")))
            {
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    canCreditReportShow = true
                }
            }
            def canInterestEdit = true
            def loanCompletionStage = OpportunityStage.findByCode("10")
            def loanCompletionFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, loanCompletionStage)
            if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
            {
                canInterestEdit = false
            }
            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                //                    creditReport: creditReport,
                opportunityContacts: opportunityContacts,
                activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow, canQueryPrice: canQueryPrice, canAttachmentsShow: canAttachmentsShow, canCreditReportShow: canCreditReportShow, canInterestEdit: canInterestEdit]
            // }
            // else
            // {
            //     flash.message = message(code: 'opportunity.edit.permission.denied')
            //     redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            //     return
            // }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }

    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def postLoanManager05(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                def history
                if (opportunity && opportunity?.serialNumber)
                {
                    history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
                }
                def code = opportunity?.stage?.code
                def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
                def opportunityStage = OpportunityStage.findByCode("38")
                def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
                //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
                //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
                def activities = Activity.findAllByOpportunity(opportunity)
                def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
                def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
                def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                def progressPercent
                def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

                def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

                def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
                def subUsers = []
                def reportings
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    reportings = Reporting.findAllByManager(user)
                    reportings?.each {
                        subUsers.add(it?.user)
                    }
                }
                respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                    //                    creditReport: creditReport,
                    opportunityContacts: opportunityContacts,
                    activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def postLoanManager07(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def opportunityStage = OpportunityStage.findByCode("38")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        //        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }

        if (totalProgress > 0)
        {
            progressPercent = currentProgress / totalProgress
        }
        else
        {
            progressPercent = 0
        }


        // def owner = opportunity?.user
        // def cityName = owner?.city?.name
        def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")
        if (collaterals?.size() == 1)
        {
            def collateral = Collateral.findByOpportunity(opportunity)
            if (!collateral?.propertySerialNumber && opportunity?.propertySerialNumber)
            {
                collateral.propertySerialNumber = opportunity?.propertySerialNumber
            }
            if (!collateral?.firstMortgageAmount && opportunity?.firstMortgageAmount)
            {
                collateral.firstMortgageAmount = opportunity?.firstMortgageAmount
            }
            if (!collateral?.secondMortgageAmount && opportunity?.secondMortgageAmount)
            {
                collateral.secondMortgageAmount = opportunity?.secondMortgageAmount
            }
            if (!collateral?.mortgageType && opportunity?.mortgageType)
            {
                collateral.mortgageType = opportunity?.mortgageType
            }
            if (!collateral?.typeOfFirstMortgage && opportunity?.typeOfFirstMortgage)
            {
                collateral.typeOfFirstMortgage = opportunity?.typeOfFirstMortgage
            }
            collateral.save flush: true
        }
        def canSpecialApproval = false
        def specialApprovalAttachments = Attachments.findByOpportunityAndType(opportunity, AttachmentType.findByName('特批签呈'))
        if (specialApprovalAttachments && opportunity?.status == 'Failed' && currentRole?.teamRole?.name == 'Approval')
        {
            if (!(opportunity?.subtype))
            {
                canSpecialApproval = true
            }
            else if (opportunity?.subtype?.name == '正常审批')
            {
                canSpecialApproval = true
            }
        }
        def opportunityWorkflows = []
        def territory = opportunity?.territory
        if (!territory)
        {
            territory = TerritoryAccount.findByAccount(opportunity?.account)?.territory
        }
        def territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAllByTerritory(territory)
        territoryOpportunityWorkflows?.each {
            opportunityWorkflows.add(it?.workflow)
        }


        respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
            //            creditReport: creditReport,
            opportunityContacts: opportunityContacts,
            activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals]

    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def postLoanManager08(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def opportunityStage = OpportunityStage.findByCode("38")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        //        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }

        if (totalProgress > 0)
        {
            progressPercent = currentProgress / totalProgress
        }
        else
        {
            progressPercent = 0
        }


        // def owner = opportunity?.user
        // def cityName = owner?.city?.name
        def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")
        if (collaterals?.size() == 1)
        {
            def collateral = Collateral.findByOpportunity(opportunity)
            if (!collateral?.propertySerialNumber && opportunity?.propertySerialNumber)
            {
                collateral.propertySerialNumber = opportunity?.propertySerialNumber
            }
            if (!collateral?.firstMortgageAmount && opportunity?.firstMortgageAmount)
            {
                collateral.firstMortgageAmount = opportunity?.firstMortgageAmount
            }
            if (!collateral?.secondMortgageAmount && opportunity?.secondMortgageAmount)
            {
                collateral.secondMortgageAmount = opportunity?.secondMortgageAmount
            }
            if (!collateral?.mortgageType && opportunity?.mortgageType)
            {
                collateral.mortgageType = opportunity?.mortgageType
            }
            if (!collateral?.typeOfFirstMortgage && opportunity?.typeOfFirstMortgage)
            {
                collateral.typeOfFirstMortgage = opportunity?.typeOfFirstMortgage
            }
            collateral.save flush: true
        }
        def canSpecialApproval = false
        def specialApprovalAttachments = Attachments.findByOpportunityAndType(opportunity, AttachmentType.findByName('特批签呈'))
        if (specialApprovalAttachments && opportunity?.status == 'Failed' && currentRole?.teamRole?.name == 'Approval')
        {
            if (!(opportunity?.subtype))
            {
                canSpecialApproval = true
            }
            else if (opportunity?.subtype?.name == '正常审批')
            {
                canSpecialApproval = true
            }
        }
        def opportunityWorkflows = []
        def territory = opportunity?.territory
        if (!territory)
        {
            territory = TerritoryAccount.findByAccount(opportunity?.account)?.territory
        }
        def territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAllByTerritory(territory)
        territoryOpportunityWorkflows?.each {
            opportunityWorkflows.add(it?.workflow)
        }

        respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
            //            creditReport: creditReport,
            opportunityContacts: opportunityContacts,
            activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals]

    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def postLoanManagerEdit03(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                def history
                if (opportunity && opportunity?.serialNumber)
                {
                    history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
                }
                def code = opportunity?.stage?.code
                def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
                def opportunityStage = OpportunityStage.findByCode("38")
                def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
                //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
                //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
                def activities = Activity.findAllByOpportunity(opportunity)
                def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
                def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
                def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                def progressPercent
                def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

                def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

                def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
                def subUsers = []
                def reportings
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    reportings = Reporting.findAllByManager(user)
                    reportings?.each {
                        subUsers.add(it?.user)
                    }
                }
                respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                    //                    creditReport: creditReport,
                    opportunityContacts: opportunityContacts,
                    activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def postLoanManagerEdit09(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                def history
                if (opportunity && opportunity?.serialNumber)
                {
                    history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
                }
                def code = opportunity?.stage?.code
                def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
                def opportunityStage = OpportunityStage.findByCode("38")
                def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
                //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
                //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
                def activities = Activity.findAllByOpportunity(opportunity)
                def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
                def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
                def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                def progressPercent
                def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

                def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

                def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
                def subUsers = []
                def reportings
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    reportings = Reporting.findAllByManager(user)
                    reportings?.each {
                        subUsers.add(it?.user)
                    }
                }
                respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                    //                    creditReport: creditReport,
                    opportunityContacts: opportunityContacts,
                    activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def postLoanManagerEdit10(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                def history
                if (opportunity && opportunity?.serialNumber)
                {
                    history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
                }
                def code = opportunity?.stage?.code
                def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
                def opportunityStage = OpportunityStage.findByCode("38")
                def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
                //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
                //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
                def activities = Activity.findAllByOpportunity(opportunity)
                def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
                def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
                def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                def progressPercent
                def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

                def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

                def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
                def subUsers = []
                def reportings
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    reportings = Reporting.findAllByManager(user)
                    reportings?.each {
                        subUsers.add(it?.user)
                    }
                }
                respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                    //                    creditReport: creditReport,
                    opportunityContacts: opportunityContacts,
                    activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def postLoanManagerEdit11(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            {
                def history
                if (opportunity && opportunity?.serialNumber)
                {
                    history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
                }
                def code = opportunity?.stage?.code
                def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
                def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
                def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
                def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
                def opportunityStage = OpportunityStage.findByCode("38")
                def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
                //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
                //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
                def activities = Activity.findAllByOpportunity(opportunity)
                def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
                def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
                def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
                def progressPercent
                def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

                def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

                def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
                def subUsers = []
                def reportings
                if (currentRole?.teamRole?.name == 'Approval')
                {
                    reportings = Reporting.findAllByManager(user)
                    reportings?.each {
                        subUsers.add(it?.user)
                    }
                }
                respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                    //                    creditReport: creditReport,
                    opportunityContacts: opportunityContacts,
                    activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails, collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow]
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def prepareApprove()
    {
        def opportunity = Opportunity.findById(params['opportunityId'])
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def nextStage
        def nextOperator = ""
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def currentRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)

        if (currentFlow)
        {
            if (params['nextStageId'])
            {
                nextStage = OpportunityFlow.findById(params['nextStageId'])
            }
            else
            {
                nextStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ${opportunity?.id} and executionSequence > ${currentFlow?.executionSequence} order by executionSequence asc")
            }
            if (params['nextOperatorId'] && currentRole && currentRole?.teamRole?.name == "Approval")
            {
                nextOperator = OpportunityRole.findById(params['nextOperatorId'])?.user?.toString()
            }
            else
            {
                def opportunityRoles = OpportunityRole.findAllByOpportunityAndStageAndTeamRole(opportunity, nextStage?.stage, TeamRole.findByName("Approval"))
                opportunityRoles?.each {
                    nextOperator += it?.user?.toString() + ","
                }
                if (opportunityRoles?.size() > 0)
                {
                    nextOperator = nextOperator[0..nextOperator.length() - 2]
                }
            }

            render([status: "success", nextStep: nextStage?.stage?.description, nextOperator: nextOperator] as JSON)
            return
        }
        else
        {
            render([status: "fail", errMsg: "找不到工作流"] as JSON)
            return
        }

    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Transactional
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def approve(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def stage = opportunity?.stage
        def nextStage = OpportunityFlow.findById(params['nextStage'])
        def nextOperator = OpportunityRole.findById(params['nextOperator'])
        def map = [:]

        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {

            map = opportunityService.approve(opportunity, nextStage)
            if (map['flag'])
            {
                def currentStage = opportunity?.stage
                def currentRoles = OpportunityRole.findAllByOpportunityAndStage(opportunity, currentStage)
                def operator = ""
                if (nextOperator)
                {
                    if (currentRoles?.size() == 1)
                    {
                        currentRoles?.each {
                            operator = it?.user?.toString()
                        }
                    }
                    else
                    {
                        operator = nextOperator?.user?.toString()
                        currentRoles?.each {
                            if (!(it == nextOperator))
                            {
                                it.delete flush: true
                            }
                        }
                    }
                }
                else
                {
                    currentRoles?.each {
                        operator += it?.user?.toString() + ","
                    }
                    if (currentRoles?.size() > 0)
                    {
                        operator = operator[0..operator.length() - 2]
                    }
                }
                flash.message = "已提交至${currentStage?.description}(${operator})"
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
                return
            }
            else
            {
                def message = map['message']
                if (message)
                {
                    flash.message = map['message']
                }
                else
                {
                    flash.message = "校验失败"
                    // flash.message = message(code: 'opportunity.edit.permission.denied')
                }
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

    /**
     * @Description 阶段跳转获取下一阶段操作人
     * @Author Nagelan
     * @Params username,stage
     * @Return nextOperator
     * @DateTime 2017/8/31 0031 16:48
     */
    @Transactional
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def nextOperator(){
        def opportunity = Opportunity.findById(params['opportunityId'])
        def stage = opportunity?.stage
        def nextStages = OpportunityFlow.findByOpportunityAndStage(opportunity,stage)?.nextStages
        def operator = ""
        def roleUser
        if (nextStages){
            nextStages.each {
                roleUser = OpportunityRole.findAllByOpportunityAndStage(opportunity, it?.nextStage?.stage)
                if (roleUser){
                    roleUser.each { role ->
                        operator += role?.user?.toString()
                    }
                }
            }
        }else {
            def flow = OpportunityFlow.findByOpportunityAndExecutionSequenceGreaterThan(opportunity,OpportunityFlow.findByOpportunityAndStage(opportunity,stage)?.executionSequence,[sort: "executionSequence"])
            roleUser = OpportunityRole.findByOpportunityAndStage(opportunity,flow?.stage)
            operator += roleUser?.user?.toString()
        }
        if (operator == "" || !operator){
            operator = "没有下阶段操作人"
        }
        render([status: "success", nextOperator: operator] as JSON)
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Transactional
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def ajaxApprove()
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunity = Opportunity.findById(params['opportunityId'])
        def stage = opportunity?.stage
        def nextStage = OpportunityFlow.findById(params['nextStage'])
        def nextOperator = OpportunityRole.findById(params['nextOperator'])
        def map = [:]
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            map = opportunityService.approve(opportunity, nextStage)
            if (map['flag'])
            {
                def currentStage = opportunity?.stage
                def currentRoles = OpportunityRole.findAllByOpportunityAndStage(opportunity, currentStage)
                def operator = ""
                if (nextOperator)
                {
                    if (currentRoles?.size() == 1)
                    {
                        currentRoles?.each {
                            operator = it?.user?.toString()
                        }
                    }
                    else
                    {
                        operator = nextOperator?.user?.toString()
                        currentRoles?.each {
                            if (!(it == nextOperator))
                            {
                                it.delete flush: true
                            }
                        }
                    }
                }
                else
                {
                    currentRoles?.each {
                        operator += it?.user?.toString() + ","
                    }
                    if (currentRoles?.size() > 0)
                    {
                        operator = operator[0..operator.length() - 2]
                    }
                }
                render([status: "success", nextStep: currentStage?.description, nextOperator: operator] as JSON)
                return
            }
            else
            {
                def errors = [:]
                errors['status'] = "fail"
                if (map['message'])
                {
                    errors['errMsg'] = map['message']
                }
                else
                {
                    errors['errMsg'] = "校验失败"
                }
                render errors as JSON
                return

            }
        }
        else
        {
            render([status: "fail", errMsg: "没有操作权限"] as JSON)
            return
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Transactional
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def specialApprove(Opportunity opportunity)
    {
        opportunity.subtype = OpportunitySubtype.findByName("特殊审批")
        opportunity.lastAction = OpportunityAction.findByName("特批")
        opportunity.status = 'Pending'
        opportunity.save flush: true

        flash.message = "特批成功"
        redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
        return
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Transactional
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def reject()
    {
        def opportunity = Opportunity.findById(params['opportunity'])
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def stage = opportunity?.stage
        def flag

        def comments = params["comments"]
        def opportunityFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            opportunityFlow?.comments = comments
            opportunityFlow.save flush: true

            flag = opportunityService.reject(opportunity)

            if (flag)
            {
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
                return
            }
            else
            {
                flash.message = message(code: 'approval.operation.denied')
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Transactional
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def ajaxReject()
    {
        def opportunity = Opportunity.findById(params['opportunityId'])
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def stage = opportunity?.stage
        def flag

        def comments = params["comments"]
        def opportunityFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            opportunityFlow?.comments = comments
            opportunityFlow.save flush: true

            flag = opportunityService.reject(opportunity)

            if (flag)
            {
                def foreRoles = OpportunityRole.findAllByOpportunityAndStage(opportunity, opportunity?.stage)
                def foreOperator = ""
                foreRoles?.each {
                    foreOperator += it?.user?.toString() + ","
                }
                if (foreRoles?.size() > 0)
                {
                    foreOperator = foreOperator[0..foreOperator.length() - 2]
                }
                render([status: "success", foreStep: opportunity?.stage?.description, foreOperator: foreOperator] as JSON)
                return
            }
            else
            {
                render([status: "fail", errMsg: "回退失败"] as JSON)
                return
            }
        }
        else
        {
            render([status: "fail", errMsg: "没有操作权限"] as JSON)
            return
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Transactional
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def cancel(Opportunity opportunity)
    {

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def stage = opportunity?.stage
        def flag

        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            opportunity.save flush: true

            flag = opportunityService.cancel(opportunity)
            if (flag)
            {
                flash.message = "订单失败操作成功！"
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
                return
            }
            else
            {
                flash.message = message(code: 'approval.operation.denied')
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

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Transactional
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def complete(Opportunity opportunity)
    {
        def flag = opportunityService.complete(opportunity)
        if (flag)
        {
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
        else
        {
            flash.message = message(code: 'approval.operation.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
    }

    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def initOpportunityProduct(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def map = [:]
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            def opportunityProductList = OpportunityProduct.findAllByOpportunity(opportunity)
            if (opportunityProductList && opportunityProductList.size() > 0)
            {
                for (
                    def opportunityProduct :
                        opportunityProductList)
                {
                    opportunityProduct.delete flush: true
                }
            }

            map = opportunityService.initProductInterest(opportunity)
            println map['flag']
            if (map['flag'])
            {
                redirect(controller: "opportunity", action: "refreshOpportunityProduct", params: [id: opportunity.id])
                return
            }
            else
            {
                def message = map['message']
                if (message)
                {
                    flash.message = map['message']
                }
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

    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def refreshOpportunityProduct(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            def opportunityProductList = OpportunityProduct.findAllByProductAndOpportunity(opportunity?.productAccount, opportunity)
            opportunity.monthlyInterest = 0
            if (opportunityProductList && opportunityProductList.size() > 0)
            {
                println opportunityProductList
                for (
                    def opportunityProduct :
                        opportunityProductList)
                {
                    if (opportunityProduct?.productInterestType?.name == "基本费率" || opportunityProduct?.productInterestType?.name == "信用调整" || opportunityProduct?.productInterestType?.name == "二抵加收费率" || opportunityProduct?.productInterestType?.name == "郊县" || opportunityProduct?.productInterestType?.name == "大头小尾" || opportunityProduct?.productInterestType?.name == "老人房（65周岁以上）")
                    {
                        opportunity.monthlyInterest += Math.round(opportunityProduct?.rate * 100 * 100) / 100
                        //月息=基本费率+二押费率（二押）+信用调整+郊县+大头小尾+老人房（65周岁以上）
                    }
                    if (opportunityProduct?.productInterestType?.name == "渠道返费费率")
                    {
                        opportunity.commissionRate = Math.round(opportunityProduct?.rate * 100 * 100 / opportunity.loanDuration) / 100
                        //渠道服务费
                    }
                    if (opportunityProduct?.productInterestType?.name == "服务费费率")
                    {
                        //借款服务费
                        opportunity.serviceCharge = Math.round(opportunityProduct?.rate * 100 * 100) / 100
                    }
                }
                /*if (opportunity?.product?.name == "速e贷")
                {
                    double rate = opportunity?.monthlyInterest + opportunity?.commissionRate + opportunity?.serviceCharge
                    //产品最高月息不超过2.5%
                    if (rate > 2.5 && opportunity?.commissionRate)
                    {
                        opportunity.commissionRate = Math.round((2.5 - opportunity?.monthlyInterest - opportunity?.serviceCharge) * 100) / 100
                    }
                }*/
                opportunity.save flush: true
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
                return
            }
            else
            {
                flash.message = "请先进行计算息费"
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
    def tongdun(Opportunity opportunity)
    {
        println "同盾评估"
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            def provider = CreditReportProvider.findByCode("TONGDUN")
            def contactName = []
            def contactIdNumber = []
            def contactNameAndNumber = []
            def contactId = []
            def messageJudge = []
            opportunity.contacts.each {
                if (it?.contact?.fullName)
                {
                    if (it?.contact?.idNumber)
                    {
                        Boolean flag = contactService.verifyIdNumber(it?.contact?.idNumber)
                        if (flag && it?.contact?.fullName.matches(/^([\u2190-\u9fff]{1,20}|[a-zA-Z\.\s]{1,20})/))
                        {
                            creditReportService.generateReport(provider, opportunity, it?.contact)
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
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
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
    def bairong(Opportunity opportunity)
    {
        println "百融评估"
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            def provider = CreditReportProvider.findByCode("BAIRONG")
            def contactName = []
            def contactIdNumber = []
            def contactNameAndNumber = []
            def contactPhone = []
            def messageJudge = []
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
                                creditReportService.generateReport(provider, opportunity, it?.contact)
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
                            contactNameAndNumber.add it?.type?.name + "的身份证号和姓名都为空 "
                        }
                    }
                }
                else
                {
                    contactPhone.add it?.type?.name + "手机号为空或手机号校验未通过"
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
            if (messageJudge)
            {
                flash.message += messageJudge
            }
            if (contactPhone)
            {
                flash.message += contactPhone
            }
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
    }

    //    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    //    @Transactional
    //    def initReport(Opportunity opportunity)
    //    {
    //        liquidityRiskReportService.initReport(opportunity)
    //        redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
    //        return
    //    }

    //    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    //    @Transactional
    //    def scoring(Opportunity opportunity)
    //    {
    //        def liquidityRiskReport = LiquidityRiskReport.findByOpportunity(opportunity)
    //        if (liquidityRiskReport)
    //        {
    //            liquidityRiskReportService.scoring(liquidityRiskReport)
    //        }
    //        else
    //        {
    //            flash.message = message(code: '请先初始化流通性数据')
    //        }
    //        redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
    //        return
    //    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def update(Opportunity opportunity)
    {
        if (opportunity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunity.errors, view: 'edit'
            return
        }

        // if (params["loanToValue"])
        // {
        //     def collateral = Collateral.findByOpportunity(opportunity)
        //     collateral.loanToValue = params["loanToValue"]?.toDouble()
        //     collateral.save flush: true
        // }
        //
        // if (params["level"])
        // {
        //     //def contact = Contact.findByIdNumberAndType(opportunity.idNumber, "Client");
        //     def contact = Contact.findById(opportunity.lender?.id);
        //     if (contact)
        //     {
        //         def level = ContactLevel.findByName(params["level"])
        //         contact.level = level
        //         contact.save flush: true
        //     }
        // }
        /*if(params["reportTo"] && params["reportTo"] != "null")
       {
           def user = User.findById(opportunity?.user?.id);
           if (user)
           {
               def reportTo = User.findById(params["reportTo"])
               user.reportTo = reportTo
               if(user.validate())
               {
                   user.save flush: true
               }
               else
               {
                   println user.errors
               }
           }
       }*/
        if(params["actuaDate"]){
            opportunity.actuaRepaymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(params["actuaDate"])
        }
        if(opportunity.notarizations){
            def last = opportunity.notarizations.substring(opportunity.notarizations.length()-1,opportunity.notarizations.length())
            if (last == ","){
                opportunity.notarizations = opportunity.notarizations.substring(0,opportunity.notarizations.lastIndexOf(","))
            }
        }
        def username = springSecurityService.getPrincipal().username
        opportunity.modifiedBy = User.findByUsername(username)
        opportunity.modifiedDate = new Date()
        opportunityService.backup(opportunity)

        opportunity.save flush: true

        // def collateral = Collateral.findByOpportunity(opportunity)
        // collateral.unitPrice = opportunity?.unitPrice
        // collateral.totalPrice = opportunity?.loanAmount
        // collateral.save flush: true

        //标记借款人
        opportunityService.assignToClient(opportunity)

        redirect(controller: "opportunity", action: "show", id: opportunity.id)
    }

    /**
     * @Description
     * @Author bigyuan
     * @Return
     * @DateTime 2017/9/18 10:57
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def ajaxUpd()
    {
        def actuaRepaymentDate = params['actuaRepaymentDate']
        if(actuaRepaymentDate){
            actuaRepaymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(actuaRepaymentDate)
        }

            if (new Date()<actuaRepaymentDate)
            {
                render([status: "success"] as JSON)

                return

            }
            else
            {
                render([status: "fail", errMsg:"约定还款日必须大于当天日期！"] as JSON)
                return
            }

    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def ajaxUpdate(Opportunity opportunity)
    {
        // println "params:" + params
        if (params["level"])
        {
            //def contact = Contact.findByIdNumberAndType(opportunity.idNumber, "Client");
            def contact = Contact.findById(opportunity?.lender?.id);
            if (contact)
            {
                def level = ContactLevel.findByName(params["level"])
                contact.level = level
                contact.save flush: true
            }
        }
        if (!params['notarizationMatters'])
        {
            opportunity.notarizationMatters = null
        }
        if (!params['signedDocuments'])
        {
            opportunity.signedDocuments = null
        }
        if (!params['mortgageMaterials'])
        {
            opportunity.mortgageMaterials = null
        }

        def username = springSecurityService.getPrincipal().username
        opportunity.modifiedBy = User.findByUsername(username)
        opportunity.modifiedDate = new Date()
        opportunityService.backup(opportunity)

        if (opportunity.validate())
        {
            opportunity.save flush: true
            //标记借款人
            opportunityService.assignToClient(opportunity)

            render([status: "success"] as JSON)
            return
        }
        else
        {
            render([status: "fail", errorMessage: opportunity.errors] as JSON)
            return
        }
    }

    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def ajaxUpdate1(Opportunity opportunity)
    {
        // println "params:" + params
        if (params["level"])
        {
            //def contact = Contact.findByIdNumberAndType(opportunity.idNumber, "Client");
            def contact = Contact.findById(opportunity?.lender?.id);
            if (contact)
            {
                def level = ContactLevel.findByName(params["level"])
                contact.level = level
                contact.save flush: true
            }
        }

        def username = springSecurityService.getPrincipal().username
        opportunity.modifiedBy = User.findByUsername(username)
        opportunity.modifiedDate = new Date()
        opportunityService.backup(opportunity)

        if (opportunity.validate())
        {
            opportunity.save flush: true
            //标记借款人
            opportunityService.assignToClient(opportunity)

            render([status: "success"] as JSON)
            return
        }
        else
        {
            render([status: "fail", errorMessage: opportunity.errors] as JSON)
            return
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(Opportunity opportunity)
    {

        if (opportunity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunity.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunity.label',
                                                                                        default: 'Opportunity'),
                    opportunity.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Secured('permitAll')
    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunity.label',
                                                                                          default: 'Opportunity'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def transferRole()
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunity = Opportunity.findById(params['opportunity'])
        def type = params['type']
        def transferedUser = User.findById(params['user'])

        def opportunityTeam = OpportunityTeam.findByOpportunityAndUser(opportunity, transferedUser)
        if (!opportunityTeam)
        {
            opportunityTeam = new OpportunityTeam()
            opportunityTeam.opportunity = opportunity
            opportunityTeam.user = transferedUser
        }
        opportunityTeam.opportunityLayout = (OpportunityTeam.findByOpportunityAndUser(opportunity, user))?.opportunityLayout
        opportunityTeam.save flush: true


        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def opportunityRole
        def opportunityNotification
        if (type == 'temp')
        {
            opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
            if (opportunityRole)
            {
                opportunityRole.user = transferedUser
                opportunityRole.save flush: true
            }

            opportunityNotification = OpportunityNotification.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
            if (opportunityNotification)
            {
                opportunityNotification.user = transferedUser
                opportunityNotification.save flush: true
            }
        }
        else if (type == 'total')
        {
            def nextStages = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ? and executionSequence >= ?", [opportunity?.id, currentFlow?.executionSequence])
            nextStages?.each {
                opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, it?.stage)
                if (opportunityRole)
                {
                    opportunityRole.user = transferedUser
                    opportunityRole.save flush: true
                }

                opportunityNotification = OpportunityNotification.findByOpportunityAndUserAndStage(opportunity, user, it?.stage)
                if (opportunityNotification)
                {
                    opportunityNotification.user = transferedUser
                    opportunityNotification.save flush: true
                }
            }
        }

        opportunityNotificationService.sendNotification(opportunity)
        redirect controller: "opportunity", action: "show", method: "GET", id: opportunity?.id
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def showHelp(Document document)
    {
        respond document
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def prepareRenewal(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        //def user = User.findByUsername(username)
        //def opportunityRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        //if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        // if (opportunity.user?.username == username)
        // {
        def newOpportunity = new Opportunity()
        newOpportunity.parent = opportunity
        newOpportunity.type = OpportunityType.findByCode("20")

        newOpportunity.user = opportunity?.user
        newOpportunity.account = opportunity?.account
        newOpportunity.fullName = opportunity?.fullName
        newOpportunity.unitPrice = opportunity?.unitPrice
        newOpportunity.loanAmount = opportunity?.loanAmount
        newOpportunity.product = opportunity?.product
        newOpportunity.mortgageType = opportunity?.mortgageType
        newOpportunity.firstMortgageAmount = opportunity?.firstMortgageAmount
        newOpportunity.secondMortgageAmount = opportunity?.secondMortgageAmount
        newOpportunity.typeOfFirstMortgage = opportunity?.typeOfFirstMortgage

        newOpportunity.save flush: true
        respond newOpportunity, view: 'renewal'
        return
        // }
        // else
        // {
        //     flash.message = message(code: 'opportunity.edit.permission.denied')
        //     redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
        //     return
        // }

    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def renewal(Opportunity opportunity)
    {
        if (opportunity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunity.errors, view: 'renewal'
            return
        }

        def opportunityStage = OpportunityStage.findByCode("51")
        opportunity.stage = opportunityStage
        opportunity.save flush: true
        opportunityService.initOpportunity(opportunity)

        //copy
        def parent = opportunity?.parent
        opportunity.contact = parent?.contact
        opportunity.lender = parent?.lender
        opportunity.productAccount = parent?.productAccount

        //def collaterals = Collateral.findAllByOpportunity(parent)
        def collaterals = parent?.collaterals
        collaterals?.each {
            def collateral = new Collateral()
            collateral.opportunity = opportunity
            collateral.externalId = it?.externalId
            collateral.city = it?.city
            collateral.district = it?.district
            collateral.projectName = it?.projectName
            collateral.address = it?.address
            collateral.orientation = it?.orientation
            collateral.area = it?.area
            collateral.building = it?.building
            collateral.unit = it?.unit
            collateral.roomNumber = it?.roomNumber
            collateral.floor = it?.floor
            collateral.totalFloor = it?.totalFloor
            collateral.assetType = it?.assetType
            collateral.houseType = it?.houseType
            collateral.specialFactors = it?.specialFactors
            collateral.status = 'Pending'
            collateral.unitPrice = 0
            collateral.totalPrice = 0
            collateral.requestStartTime = null
            collateral.requestEndTime = null
            collateral.save flush: true
        }

        def parentAutitTrails = CollateralAuditTrail.findAllByOpportunity(parent)
        parentAutitTrails?.each {
            def CollateralAuditTrail = new CollateralAuditTrail()
            CollateralAuditTrail.opportunity = opportunity
            CollateralAuditTrail.unitPrice = it?.unitPrice
            CollateralAuditTrail.totalPrice = it?.totalPrice
            CollateralAuditTrail.createdDate = it?.createdDate
            CollateralAuditTrail.save flush: true
        }

        def parentContacts = OpportunityContact.findAllByOpportunity(parent)
        parentContacts?.each {
            def opportunityContact = new OpportunityContact()
            opportunityContact.opportunity = opportunity
            opportunityContact.contact = it?.contact
            opportunityContact.type = it?.type
            opportunityContact.save flush: true
        }

        def parentBankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")
        parentBankAccounts?.each {
            def bankAccount = new OpportunityBankAccount()
            bankAccount.opportunity = opportunity
            bankAccount.name = it?.name
            bankAccount.type = it?.type
            bankAccount.bank = it?.bank
            bankAccount.bankBranch = it?.bankBranch
            bankAccount.bankAddress = it?.bankAddress
            bankAccount.bankAccount = it?.bankAccount
            bankAccount.idNumber = it?.idNumber
            bankAccount.save flush: true
        }

        def parentAttachments = Attachments.findAllByOpportunity(parent)
        parentAttachments?.each {
            def attachment = new Attachments()
            attachment.opportunity = opportunity
            attachment.activity = it?.activity
            attachment.contact = it?.contact
            attachment.type = it?.type
            attachment.fileName = it?.fileName
            attachment.description = it?.description
            attachment.createdDate = it?.createdDate
            attachment.modifiedDate = it?.modifiedDate
            attachment.save flush: true
        }

        // def parentProducts = OpportunityProduct.findAllByOpportunityAndProduct(parent, parent?.productAccount)
        // parentProducts?.each {
        //     def product1 = new OpportunityProduct()
        //     product1.opportunity = opportunity
        //     product1.product = it?.product
        //     product1.productInterestType = it?.productInterestType
        //     product1.fixedRate = it?.fixedRate
        //     product1.installments = it?.installments
        //     product1.rate = it?.rate
        //     product1.monthes = it?.monthes
        //     product1.firstPayMonthes = it?.firstPayMonthes
        //     product1.save flush: true
        // }

        def parentFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(parent)
        parentFlexFieldCategorys?.each {
            def flexFieldCategory1 = new OpportunityFlexFieldCategory()
            flexFieldCategory1.opportunity = opportunity
            flexFieldCategory1.flexFieldCategory = it?.flexFieldCategory
            flexFieldCategory1.save flush: true

            it?.fields?.each {
                def flexField = new OpportunityFlexField()
                flexField.category = flexFieldCategory1
                flexField.name = it?.name
                flexField.description = it?.description
                flexField.dataType = it?.dataType
                flexField.defaultValue = it?.defaultValue
                flexField.value = it?.value
                flexField.displayOrder = it?.displayOrder
                flexField.valueConstraints = it?.valueConstraints
                flexField.save flush: true

                it?.values?.each {
                    def flexValue = new OpportunityFlexFieldValue()
                    flexValue.field = flexField
                    flexValue.value = it?.value
                    flexValue.displayOrder = it?.displayOrder
                    flexValue.save flush: true
                }
            }

        }

        def parentTeams = OpportunityTeam.findAllByOpportunity(parent)
        parentTeams?.each {
            def opportunityTeam = new OpportunityTeam()
            opportunityTeam.opportunity = opportunity
            opportunityTeam.user = it?.user
            opportunityTeam.createdDate = it?.createdDate
            opportunityTeam.modifiedDate = it?.modifiedDate
            opportunityTeam.save flush: true
        }

        /*def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code */

        opportunity.save flush: true

        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def activities = Activity.findAllByOpportunity(opportunity)

        //def creditReport = CreditReport.findAllByOpportunity(opportunity)
        //def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def progressPercent
        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }

        if (totalProgress > 0)
        {
            progressPercent = currentProgress / totalProgress
        }
        else
        {
            progressPercent = 0
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunity.label',
                                                                                        default: 'Opportunity'),
                    opportunity.id])
                respond opportunity, model: [opportunityContacts: opportunityContacts, bankAccounts: bankAccounts, opportunityProduct: opportunityProduct, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys,
                    opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, CollateralAuditTrails: CollateralAuditTrails, opportunityFlows: opportunityFlows,
                    activities: activities, progressPercent: progressPercent, currentFlow: currentFlow], view: 'opportunityLayout04'
            }
            '*' { respond opportunity, [status: CREATED] }
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def opportunityEdit01(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            respond opportunity, model: [user: user]
            return
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }

    }

    def calculateCommission()
    {
        def actualAmountOfCredit = params["actualAmountOfCredit"]
        def commissionRate = params["commissionRate"]
        def actualLoanDuration = params["actualLoanDuration"]
        def commissionPaymentMethod = params["commissionPaymentMethod"]
        def method = CommissionPaymentMethod.findById(commissionPaymentMethod)?.name
        def commission = actualAmountOfCredit.toDouble() * commissionRate.toDouble() / 100
        if (method == '月月返')
        {
            commission = commission * actualLoanDuration.toDouble()
        }
        commission = Math.round(commission * 100 * 100 * 100) / 100 / 100 / 100
        render([status: "success", commission: commission] as JSON)
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPLIANCE_MANAGER'])
    @Transactional
    def changeOpportunityStage()
    {
        def opportunity = Opportunity.findById(params['opportunity'])
        def opportunityStage = OpportunityStage.findById(params['opportunityStage'])
        def memo = params['memo']
        if (memo)
        {
            opportunity.memo = memo
        }
        opportunity.stage = opportunityStage
        opportunity.save()
        opportunityNotificationService.sendNotification(opportunity)
        redirect controller: "opportunity", action: "show", method: "GET", id: opportunity?.id
        return
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPLIANCE_MANAGER'])
    @Transactional
    def rollbackOpportunityStage()
    {
        def opportunity = Opportunity.findById(params['opportunity'])
        def opportunityStage = OpportunityStage.findByName("结清申请已提交")
        /*def memo = params['memo']
        if (memo)
        {
            opportunity.memo = memo
        }*/
        if (opportunity && opportunityStage)
        {
            opportunity.stage = opportunityStage
            opportunity.save()
            opportunityNotificationService.sendNotification(opportunity)
        }
        flash.message = "回退成功！"
        redirect controller: "opportunity", action: "show", method: "GET", id: opportunity?.id
        return
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER'])
    @Transactional
    def changeOpportunityStatus()
    {
        def opportunity = Opportunity.findById(params['opportunity'])
        def opportunityStatus = params['opportunityStatus']
        opportunity.status = opportunityStatus
        opportunity.save()
        opportunityNotificationService.sendNotification(opportunity)
        redirect controller: "opportunity", action: "show", method: "GET", id: opportunity?.id
        return
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def preparePostLoan()
    {
        def parentOpportunity = Opportunity.findById(params['parentOpportunity'])
        def type = OpportunityType.findByCode(params['type'])
        def opportunity = new Opportunity()
        opportunity.parent = parentOpportunity
        opportunity.type = type
        opportunity.user = parentOpportunity?.user
        opportunity.account = parentOpportunity?.account
        // opportunity.save flush: true

        respond opportunity, view: 'postLoanManager02'
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def preparePrepayment()
    {
        def parentOpportunity = Opportunity.findById(params['parentOpportunity'])
        def type = OpportunityType.findByCode(params['type'])
        def opportunity = new Opportunity()
        opportunity.parent = parentOpportunity
        opportunity.type = type
        opportunity.user = parentOpportunity?.user
        opportunity.account = parentOpportunity?.account
        // opportunity.save flush: true

        respond opportunity, view: 'postLoanManager12'
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def postLoan(Opportunity opportunity)
    {
        if (opportunity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunity.hasErrors())
        {
            println opportunity.errors
            transactionStatus.setRollbackOnly()
            respond opportunity.errors, view: 'postLoanManager01'
            return
        }
        if(params.actuaDate){
            opportunity.actuaRepaymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.actuaDate)
        }
        opportunity.contact = opportunity?.parent?.contact
        opportunity.lender = opportunity?.parent?.lender
        opportunity.fullName = opportunity?.parent?.fullName
        opportunity.idNumber = opportunity?.parent?.idNumber
        opportunity.maritalStatus = opportunity?.parent?.maritalStatus
        opportunity.product = opportunity?.parent?.product
        opportunity.interestPaymentMethod = opportunity?.parent?.interestPaymentMethod
        opportunity.monthOfAdvancePaymentOfInterest = opportunity?.parent?.monthOfAdvancePaymentOfInterest
        opportunity.actualAmountOfCredit = opportunity?.parent?.actualAmountOfCredit
        opportunity.actualLoanDuration = opportunity?.parent?.actualLoanDuration
        opportunity.save flush: true
        opportunityService.initOpportunity(opportunity)

        def opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? order by executionSequence ASC", [opportunity?.id])
        opportunity.stage = opportunityFlow?.stage
        opportunity.save flush: true
        //房产(去价格)、联系人、附件、下户、账户、合同、息费
        def parent = opportunity?.parent
        def collaterals = Collateral.findAllByOpportunity(parent)
        collaterals?.each {
            def collateral = new Collateral()
            collateral.opportunity = opportunity
            collateral.assetType = it?.assetType
            collateral.projectName = it?.projectName
            collateral.city = it?.city
            collateral.district = it?.district
            collateral.address = it?.address
            collateral.floor = it?.floor
            collateral.orientation = it?.orientation
            collateral.area = it?.area
            collateral.building = it?.building
            collateral.unit = it?.unit
            collateral.totalFloor = it?.totalFloor
            collateral.roomNumber = it?.roomNumber
            collateral.houseType = it?.houseType
            collateral.specialFactors = it?.specialFactors
            collateral.reasonOfPriceAdjustment = it?.reasonOfPriceAdjustment
            collateral.appliedTotalPrice = it?.appliedTotalPrice
            collateral.requestStartTime = it?.requestStartTime
            collateral.requestEndTime = it?.requestEndTime
            collateral.projectType = it?.projectType
            collateral.buildTime = it?.buildTime
            collateral.loanToValue = it?.loanToValue
            collateral.propertySerialNumber = it?.propertySerialNumber
            collateral.typeOfFirstMortgage = it?.typeOfFirstMortgage
            collateral.firstMortgageAmount = it?.firstMortgageAmount
            collateral.secondMortgageAmount = it?.secondMortgageAmount
            collateral.mortgageType = it?.mortgageType
            collateral.region = it?.region
            collateral.atticArea = it?.atticArea
            collateral.status = 'Pending'
            if (collateral.validate())
            {
                collateral.save flush: true
            }
            else
            {
                log.info collateral.errors
            }
        }
        def opportunityContacts = OpportunityContact.findAllByOpportunity(parent)
        opportunityContacts?.each {
            def opportunityContact = new OpportunityContact()
            opportunityContact.opportunity = opportunity
            opportunityContact.type = it?.type
            opportunityContact.contact = it?.contact
            opportunityContact.connectedContact = it?.connectedContact
            opportunityContact.connectedType = it?.connectedType
            if (opportunityContact.validate())
            {
                opportunityContact.save flush: true
            }
            else
            {
                log.info opportunityContact.errors
            }
        }
        def attachments = Attachments.findAllByOpportunity(parent)
        attachments?.each {
            def attachment = new Attachments()
            attachment.opportunity = opportunity
            attachment.type = it?.type
            attachment.fileName = it?.fileName
            attachment.description = it?.description
            attachment.displayOrder = it?.displayOrder
            attachment.activity = it?.activity
            attachment.contact = it?.contact
            if (attachment.validate())
            {
                attachment.save flush: true
            }
            else
            {
                log.info attachment.errors
            }
        }
        def activities = Activity.findAllByOpportunity(parent)
        activities?.each {
            def activity = new Activity()
            activity.opportunity = opportunity
            activity.startTime = it?.startTime
            activity.endTime = it?.endTime
            activity.actualStartTime = it?.actualStartTime
            activity.actualEndTime = it?.actualEndTime
            activity.description = it?.description
            activity.type = it?.type
            activity.subtype = it?.subtype
            activity.status = it?.status
            activity.longitude = it?.longitude
            activity.latitude = it?.latitude
            activity.city = it?.city
            activity.address = it?.address
            activity.name = it?.name
            activity.user = it?.user
            activity.contact = it?.contact
            activity.assignedTo = it?.assignedTo
            activity.parent = it?.parent
            if (activity.validate())
            {
                activity.save flush: true
            }
            else
            {
                log.info activity.errors
            }
        }
        def opportunityBankAccounts = OpportunityBankAccount.findAllByOpportunity(parent)
        opportunityBankAccounts?.each {
            def opportunityBankAccount = new OpportunityBankAccount()
            opportunityBankAccount.opportunity = opportunity
            opportunityBankAccount.type = it?.type
            opportunityBankAccount.bankAccount = it?.bankAccount
            if (opportunityBankAccount.validate())
            {
                opportunityBankAccount.save flush: true
            }
            else
            {
                log.info opportunityBankAccount.errors
            }
        }
        def opportunityContracts = OpportunityContract.findAllByOpportunity(parent)
        opportunityContracts?.each {
            def opportunityContract = new OpportunityContract()
            opportunityContract.opportunity = opportunity
            opportunityContract.contract = it?.contract
            if (opportunityContract.validate())
            {
                opportunityContract.save flush: true
            }
            else
            {
                log.info opportunityContract.errors
            }
        }
        def opportunityProducts = OpportunityProduct.findAllByOpportunity(parent)
        opportunityProducts?.each {
            def opportunityProduct = new OpportunityProduct()
            opportunityProduct.opportunity = opportunity
            opportunityProduct.productInterestType = it?.productInterestType
            opportunityProduct.fixedRate = it?.fixedRate
            opportunityProduct.installments = it?.installments
            opportunityProduct.rate = it?.rate
            opportunityProduct.monthes = it?.monthes
            opportunityProduct.firstPayMonthes = it?.firstPayMonthes
            opportunityProduct.product = it?.product
            opportunityProduct.createBy = it?.createBy
            opportunityProduct.modifyBy = it?.modifyBy
            if (opportunityProduct.validate())
            {
                opportunityProduct.save flush: true
            }
            else
            {
                log.info opportunityProduct.errors
            }
        }

        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(parent)
        opportunityFlexFieldCategorys?.each {
            def opportunityFlexFieldCategory = new OpportunityFlexFieldCategory()
            opportunityFlexFieldCategory.opportunity = opportunity
            opportunityFlexFieldCategory.flexFieldCategory = it?.flexFieldCategory
            opportunityFlexFieldCategory.save flush: true

            it?.fields?.each {
                def opportunityflexField = new OpportunityFlexField()
                opportunityflexField.category = opportunityFlexFieldCategory
                opportunityflexField.name = it?.name
                opportunityflexField.description = it?.description
                opportunityflexField.dataType = it?.dataType
                opportunityflexField.defaultValue = it?.defaultValue
                opportunityflexField.value = it?.value
                opportunityflexField.displayOrder = it?.displayOrder
                opportunityflexField.valueConstraints = it?.valueConstraints
                opportunityflexField.save flush: true

                it?.values?.each {
                    def opportunityFlexFieldValue = new OpportunityFlexFieldValue()
                    opportunityFlexFieldValue.field = opportunityflexField
                    opportunityFlexFieldValue.value = it?.value
                    opportunityFlexFieldValue.displayOrder = it?.displayOrder
                    opportunityFlexFieldValue.save flush: true
                }
            }
        }

        redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
        return
    }


    /**
     * @Description  ajax
     * @Author bigyuan
     * @Return
     * @DateTime 2017/9/15 13:54
     */
    @Secured(['ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def afterPostLoan()
    {
        def opportunity = Opportunity.findById(params['opportunityId'])
        def actuaRepaymentDate = params['actuaRepaymentDate']
        if(actuaRepaymentDate){
            actuaRepaymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(actuaRepaymentDate)
        }
        def map = [:]
        def sonOpportunity = Opportunity.findAllByParent(opportunity)
        if(sonOpportunity.size()>0){
            render([status: "fail", errMsg:"结清只能发起一次"] as JSON)
            return
        } else
        {
            if (opportunity.bills[0].endTime<actuaRepaymentDate)
            {
                render([status: "fail", errMsg:"约定还款日不能大于到期日！"] as JSON)
                return
            }
            else
            {
                render([status: "success"] as JSON)

                return
            }
        }

    }
    @Secured(['permitAll'])
    @Transactional
    def welcome()
    {
        println "************************* update remote ip ****************************************"

        String ip = request.getHeader("x-forwarded-for")
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr()
        }

        println "client ip: " + ip

        if (ip)
        {
            def userName = springSecurityService.getPrincipal().username
            def loginHistory = LoginHistory.find("from LoginHistory where userName = '${userName}' order by createdDate desc")
            loginHistory?.ip = ip
            if (loginHistory?.validate())
            {
                loginHistory?.save flush: true
            }
            else
            {
                println loginHistory?.errors
            }
        }
        return
    }

    /**
     * 外贸预审*/
    @Secured(['permitAll'])
    @Transactional
    def foticAheadSubmit(String id)
    {
        println("foticAheadSubmitfoticAheadSubmitfoticAheadSubmitfoticAheadSubmit");
        println("id" + id);

        foticAheadService.foticAheadSubmit(id);

    }

    /**
     *  中航审批接口调用
     *  */
    @Secured(['permitAll'])
    @Transactional
    def avicAheadSubmit()
    {
        println "aaaaaaaaaaaa"
        def opportunity = Opportunity.findById(404)
        println opportunity
        println("hehhe")
        avicAheadSubmitService.avicAheadSubmit(opportunity)
    }

    /**
     * 传递5010报文接口*/
    @Secured(['permitAll'])
    @Transactional
    def sendData()
    {
        def xml = """
<REQ>
   <HEAD>
      <BchCde>900000000</BchCde>
      <MsgId>0000093956</MsgId>
      <MsgNo>5010</MsgNo>
      <MsgRef>0000093956</MsgRef>
      <ReqCode>CMIS</ReqCode>
      <Teller>lixiaole</Teller>
      <WorkData>20160812</WorkData>
      <WorkTime>163504</WorkTime>
   </HEAD>
   <MSG>
      <ApplSeq>752716</ApplSeq>
      <CustName>佰仟测试004</CustName>
      <IdTyp>22</IdTyp>
      <IdNo>佰仟测试004</IdNo>
      <AppConclusion>20</AppConclusion>
      <AppAdvice>20</AppAdvice>
      <CrtUsr>lixiaole</CrtUsr>
      <CrtDt>2016-03-27 00:00:00</CrtDt>
   </MSG>
</REQ>
"""
        println xml
        def xml1 = new XmlParser().parseText(xml)
        println xml1.MSG.ApplSeq.text()
        URL url = new URL("http://localhost:8080/Opportunity/serviceMethod5010")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        connection.setRequestProperty("Content-Type", "application/xml")
        connection.outputStream.withWriter { Writer writer -> writer.write xml }
        try
        {
            if (connection.getResponseCode() == 200)
            {
                render 200
            }
        }

        catch (Exception e)
        {
            render 400
        }

    }

    /**
     * 5010贷款申请审批反馈信息*/
    @Secured(['permitAll'])
    @Transactional
    def serviceMethod5010()
    {
        println "Good"
        def respxml = request.XML
        //println respxml.MSG.ApplSeq.text()
        def sw = new java.io.StringWriter()
        def xml = new groovy.xml.MarkupBuilder(sw)
        xml.RESP {
            HEAD {
                // 申请渠道码
                ReqCode('WBJF')
                // 报文编号，交易码
                MsgNo('5010')
                //报文标识号
                MsgId("WBJFZJXF" + new Date().format("yyyyMMdd") + "000001")
                //报文参考号,同MsgId
                MsgRef("WBJFZJXF" + new Date().format("yyyyMMdd") + "000001")
                //工作日期
                WorkData(new Date().format("yyyyMMdd"))
                //工作时间
                WorkTime(new Date().format("HHmmss"))
                def channelRecord = com.next.ChannelRecord.findByApplySeq(respxml.MSG.ApplSeq.text())
                println(respxml.MSG.AppConclusion.text())
                println(channelRecord.applySeq)
                // 根据ApplSeq的值设定状态
                if (channelRecord.applySeq.toDouble() > 0)
                {
                    channelRecord.status = respxml.MSG.AppConclusion.text()
                    if (channelRecord.validate())
                    {
                        channelRecord.save flush: true
                        println "成功插入"
                    }
                    else
                    {
                        println channelRecord.errors
                        println("cuowu")
                    }
                    println(channelRecord)
                    if (respxml.MSG.AppConclusion.text() == '10' || respxml.MSG.AppConclusion.text() == '20' || respxml.MSG.AppConclusion.text() == '30')
                    {
                        ErrorCode('0000')
                        ErrorMsg('交易成功')
                    }
                    else
                    {
                        ErrorCode('9001')
                        ErrorMsg('数据验证错误')
                    }
                }
                // 错误信息
                if (respxml.MSG.ApplSeq.text() == null)
                {
                    ErrorCode('9998')
                    ErrorMsg('无此交易码')
                }
            }
        }

        println sw
    }

    /**
     * //3.29 申请审批结果推送(回调)
     * @return
     */
    @Secured(['permitAll'])
    @Transactional
    def evaluate()
    {
        println("enter in")
        //放回Json报文
        def json = request.JSON
        println(ChannelRecord.findByInterfaceCode("04001001")?.loanApplyNo)
        println(ChannelRecord.find("from ChannelRecord as a where a.interfaceCode='04002017' and a.loanApplyNo='${json["loanApplyNo"]}'"))
        println("hen  hao")
        try
        {
            if (ChannelRecord.find("from ChannelRecord as a where a.interfaceCode='04002017' and a.loanApplyNo='${json["loanApplyNo"]}'") != null)
            {
                println("you shu") //更新
                def channelRecord = ChannelRecord.find("from ChannelRecord as a where a.interfaceCode='04002017' and a.loanApplyNo='${json["loanApplyNo"]}'")
                channelRecord.startTime = new Date()
                channelRecord.interfaceCode = '04002017'
                channelRecord.loanApplyNo = json["loanApplyNo"]
                channelRecord.status = json["appConclusion"]
                channelRecord.crtDt = json["crtDt"]
                channelRecord.opportunity = ChannelRecord.findByInterfaceCode("04001001").opportunity
                channelRecord.createdBy = com.next.User.findByUsername("zz")
                channelRecord.endTime = new Date()
                if (channelRecord.validate())
                {
                    channelRecord.save flush: true
                    println("insert succeed")
                }
                else
                {
                    println channelRecord.errors
                    println("cuowu")
                }
            }
            else
            {
                println("kong")
                def channelRecord = new com.next.ChannelRecord()
                channelRecord.startTime = new Date()
                channelRecord.interfaceCode = '04002017'
                channelRecord.loanApplyNo = json["loanApplyNo"]
                channelRecord.status = json["appConclusion"]
                channelRecord.crtDt = json["crtDt"]
                println(json["crtDt"])
                channelRecord.opportunity = ChannelRecord.findByInterfaceCode("04001001").opportunity
                channelRecord.createdBy = com.next.User.findByUsername("zz")
                channelRecord.endTime = new Date()
                if (channelRecord.validate())
                {
                    channelRecord.save flush: true
                    println("insert succeed")
                }
                else
                {
                    println channelRecord.errors
                    println("cuowu")
                }
            }
            /*            println(json)
                        println(json.getClass())
                        println(json["name"])
                        println(json["serialNumber"])
                        println(json["status"])
                        println(json["responseMessage"])
                        println "hehe"*/

        }
        catch (Exception e)
        {
            render ""
        }
        def huidiao = [:]
        huidiao["loanApplyNo"] = json["loanApplyNo"]
        def huidiaowen = new JSON(huidiao)
        render huidiaowen

    }

    /**
     * 结清证明打印*/
    @Secured(['permitAll'])
    @Transactional
    def closingProof(Opportunity opportunity)
    {
        def map = [:]
        def cityName = opportunity?.parent?.user?.city?.name
        if (cityName)
        {
            if (cityName == "北京")
            {
                cityName = "BJ"
            }
            else if (cityName == "上海")
            {
                cityName = "SH"
            }
            else if (cityName == "成都")
            {
                cityName = "CD"
            }
            else if (cityName == "青岛")
            {
                cityName = "QD"
            }
            else if (cityName == "济南")
            {
                cityName = "JN"
            }
            else if (cityName == "郑州")
            {
                cityName = "ZJ"
            }
            else if (cityName == "南京")
            {
                cityName = "NJ"
            }
            else if (cityName == "西安")
            {
                cityName = "XA"
            }
            else if (cityName == "合肥")
            {
                cityName = "HF"
            }
            else if (cityName == "武汉")
            {
                cityName = "WH"
            }
            else if (cityName == "苏州")
            {
                cityName = "SZ"
            }
            else if (cityName == "石家庄")
            {
                cityName = "SJZ"
            }
            else if (cityName == "厦门")
            {
                cityName = "XM"
            }
        }
        map["city"] = cityName
        if (opportunity?.parent?.actualLendingDate)
        {
            map["actualLendingDate"] = opportunity?.parent?.actualLendingDate.format("yyyy年MM月dd日")
        }
        else
        {
            map["actualLendingDate"] = opportunity?.parent?.actualLendingDate
        }
        def users = OpportunityContact.findAllByOpportunity(opportunity?.parent)
        def fullName = ""
        users.each {
            fullName += it.contact?.fullName + ","
        }
        if (users)
        {
            map["fullName"] = StringUtils.substringBeforeLast(fullName, ",")
        }
        else
        {
            map["fullName"] = ""
        }
        def bills = Bills.findByOpportunity(opportunity?.parent)
        if (bills?.capital)
        {
            map["principal"] = billsService.transforNumToRMB(bills?.capital * 10000)
            //map["principal"] = bills?.capital
        }
        else
        {
            map["principal"] = billsService.transforNumToRMB(opportunity?.parent?.actualLoanAmount * 10000 == 0 ? opportunity?.parent?.actualAmountOfCredit * 10000 : opportunity?.parent?.actualLoanAmount * 10000)
            //map["principal"] = opportunity?.parent?.actualLoanAmount==0?opportunity?.parent?.actualAmountOfCredit:opportunity?.parent?.actualLoanAmount
        }

        def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join c.type t left join oc.opportunity o where o.id = ${opportunity?.parent?.id} and t.name = '借款合同'")
        if (contract)
        {
            map["serialNumber"] = contract[0]
        }
        else
        {
            map["serialNumber"] = ""
        }
        respond opportunity, model: [map: map], view: "closingProof"
    }
    @Secured(['ROLE_POST_LOAN_MANAGER','ROLE_ADMINISTRATOR'])
    @Transactional
    def editJqStatus(Opportunity opportunity){
        def map = [:]
        def jqStatus = params.jqStatus
        opportunity = Opportunity.findById(params.opportunityId)
        if (jqStatus!="请选择"&&opportunity){
            def jieqingFile= Attachments.findAllByOpportunityAndType(opportunity,AttachmentType.findByName("结清证明"))
            if(jieqingFile && jieqingFile.size()>0){
                def opportunityParent
                def bills = Bills.findByOpportunity(opportunity)
                if (opportunity?.type?.name != '抵押贷款'){
                    opportunityParent = opportunity?.parent
                    if (opportunityParent){
                        bills = Bills.findByOpportunity(opportunityParent)
                    }
                }
                def lastTime = BillsItem.findByBillsAndActualEndTimeIsNotNull(bills,[sort: "actualEndTime",order:"desc"])?.actualEndTime
                //lastTime = new Date()
                if (lastTime){
                    opportunity.jqStatus = jqStatus
                    opportunity.jqModifiedDate = new Date()
                    opportunity.jqDate = null
                    if (jqStatus == "手动结清"){
                        opportunity.jqDate = lastTime
                    }
                    opportunity.save flush:true
                    if (opportunityParent){
                        opportunityParent.jqStatus = jqStatus
                        opportunityParent.jqModifiedDate = new Date()
                        opportunityParent.jqDate = null
                        if (jqStatus == "手动结清"){
                            opportunityParent.jqDate = lastTime
                        }
                        opportunityParent.save()
                    }
                    map["status"] = "success"
                    map["message"] ="结清状态更新成功"
                }else {
                    map["status"] = "failure"
                    map["message"] ="无实际还款时间"
                }
            }else{
                map["status"] = "failure"
                map["message"] ="请上传结清证明"
            }

        }else{
            map["status"] = "failure"
            map["message"] ="请选择正确状态"
        }
        render  map  as JSON
        return
    }
}

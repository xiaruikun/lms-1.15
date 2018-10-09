package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured
import sun.misc.BASE64Decoder

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class AttachmentsController
{

    def fileServerService
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_INVESTOR', 'ROLE_ACCOUNT_MANAGER'])
    def showByInvestor(Opportunity opportunity)
    {
        def type = AttachmentType.findById(params['type'])
        def photoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, type?.id])

        def opportunityLayout = OpportunityLayout.findByNameAndActive("showCopy", true)
        respond photoes, model: [photoes: photoes, type: type], view: 'showByInvestor'
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-7-7
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def ajaxUpdate(Attachments attachments)
    {
        def description = params['description']
        attachments.description = description
        attachments.save()
        render([status: "success"] as JSON)
        return
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def createCreditReport()
    {
        def attachments = new Attachments(params)

        def opportunity = Opportunity.findById(params["id"])
        attachments.opportunity = opportunity

        respond attachments, model: [opportunity: opportunity]
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def createFlexFieldCategort()
    {
        def attachments = new Attachments(params)

        def opportunity = Opportunity.findById(params["id"])
        attachments.opportunity = opportunity
        def flexFieldCategory = FlexFieldCategory.findByName('产调结果')
        def opportunityFlexFieldCategory = OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity, flexFieldCategory)
        respond attachments, model: [opportunity: opportunity, opportunityFlexFieldCategory: opportunityFlexFieldCategory]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Attachments.list(params), model: [attachmentsCount: Attachments.count()]
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def show(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def opportunityStage = OpportunityStage.findByCode("46")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        if (user?.account?.id == 351 || user?.account?.id == 357)
        {
            opportunityStage = OpportunityStage.findByCode("08") //审批已完成
            opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        }
        def attachmentTypeName = params["attachmentTypeName"]
        if (attachmentTypeName == "身份证")
        {
            def maritalPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("借款人身份证")?.id])
            def maritalSpousePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("借款人配偶身份证")?.id])
            def mortgagorPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押人身份证")?.id])

            def mortgagorSpousePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押人配偶身份证")?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("attachmentsCopy07", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [maritalPhotoes: maritalPhotoes,
                    maritalSpousePhotoes: maritalSpousePhotoes,
                    mortgagorPhotoes: mortgagorPhotoes,
                    mortgagorSpousePhotoes: mortgagorSpousePhotoes, attachmentTypeName: attachmentTypeName,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy07'
            }
            else
            {
                respond opportunity, model: [maritalPhotoes: maritalPhotoes,
                    maritalSpousePhotoes: maritalSpousePhotoes,
                    mortgagorPhotoes: mortgagorPhotoes,
                    mortgagorSpousePhotoes: mortgagorSpousePhotoes, attachmentTypeName: attachmentTypeName,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachments07'
            }
        }
        else if (attachmentTypeName == '基础材料')
        {
            def maritalPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("借款人身份证")?.id])

            def maritalSpousePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("借款人配偶身份证")?.id])

            def mortgagorPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押人身份证")?.id])

            def mortgagorSpousePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押人配偶身份证")?.id])

            def housePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("房产证")?.id])

            def bookPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("户口本")?.id])

            def maritalIncPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("婚姻证明")?.id])

            def acceptancePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押登记受理单")?.id])

            def otherPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("他项证明")?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("attachmentsCopy02", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [housePhotoes: housePhotoes,
                    bookPhotoes: bookPhotoes,
                    maritalIncPhotoes: maritalIncPhotoes,
                    maritalPhotoes: maritalPhotoes,
                    maritalSpousePhotoes: maritalSpousePhotoes,
                    mortgagorPhotoes: mortgagorPhotoes,
                    mortgagorSpousePhotoes: mortgagorSpousePhotoes, attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    otherPhotoes: otherPhotoes,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy02'
            }
            else
            {
                respond opportunity, model: [housePhotoes: housePhotoes,
                    bookPhotoes: bookPhotoes,
                    maritalIncPhotoes: maritalIncPhotoes,
                    maritalPhotoes: maritalPhotoes,
                    maritalSpousePhotoes: maritalSpousePhotoes,
                    mortgagorPhotoes: mortgagorPhotoes,
                    mortgagorSpousePhotoes: mortgagorSpousePhotoes, attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    otherPhotoes: otherPhotoes,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachments02'
            }
        }
        else if (attachmentTypeName == '征信')
        {
            def notarizingPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("征信报告")?.id])

            def donePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("被执情况")?.id])

            def decimalPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("大数据")?.id])

            def centralBankCreditPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("央行征信（中佳信）")?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("showCopy", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [notarizingPhotoes: notarizingPhotoes,
                    donePhotoes: donePhotoes,
                    decimalPhotoes: decimalPhotoes, centralBankCreditPhotoes: centralBankCreditPhotoes, attachmentTypeName: attachmentTypeName,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'showCopy'
            }
            else
            {
                respond opportunity, model: [notarizingPhotoes: notarizingPhotoes,
                    donePhotoes: donePhotoes,
                    decimalPhotoes: decimalPhotoes, centralBankCreditPhotoes: centralBankCreditPhotoes, attachmentTypeName: attachmentTypeName,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'show'
            }
        }
        else if (attachmentTypeName == '要求材料')
        {

            def acceptancePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押登记受理单")?.id])

            def otherPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("他项证明")?.id])

            def transferPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("产调结果")?.id])

            //            def extraPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by id asc", [opportunity?.id, AttachmentType.findByName("其他材料")?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("showCopy", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    otherPhotoes: otherPhotoes,
                    transferPhotoes: transferPhotoes,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'showCopy'
            }
            else
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    otherPhotoes: otherPhotoes,
                    transferPhotoes: transferPhotoes,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'show'
            }
        }
        else if (attachmentTypeName == '担保资料')
        {
            def acceptancePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押登记受理单")?.id])

            def enforcePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("强执公证")?.id])

            def sellPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("售房公证")?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("attachmentsCopy01", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    enforcePhotoes: enforcePhotoes,
                    sellPhotoes: sellPhotoes,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy01'
            }
            else
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    enforcePhotoes: enforcePhotoes,
                    sellPhotoes: sellPhotoes,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachments01'
            }

        }
        else if (attachmentTypeName == '签呈')
        {
            def acceptancePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押登记受理单")?.id])

            def normalApprovalPhotos = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("普通签呈")?.id])

            def specialApprovalPhotos = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("特批签呈")?.id])
            def opportunityLayout = OpportunityLayout.findByNameAndActive("attachmentsCopy03", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    normalApprovalPhotos: normalApprovalPhotos,
                    specialApprovalPhotos: specialApprovalPhotos,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy03'
            }
            else
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    normalApprovalPhotos: normalApprovalPhotos,
                    specialApprovalPhotos: specialApprovalPhotos,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachments03'
            }
        }
        else if (attachmentTypeName == '抵押合同全委')
        {
            def acceptancePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押登记受理单")?.id])
            def otherPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("他项证明")?.id])
            def loanPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("借款合同")?.id])
            def mortgagePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押合同")?.id])
            def loanProxyPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("委托借款代理服务合同")?.id])
            def sellPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("售房公证")?.id])
            def notarizingPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("公证受理单")?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("attachmentsCopy04", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName, acceptancePhotoes: acceptancePhotoes, otherPhotoes: otherPhotoes, loanPhotoes: loanPhotoes, mortgagePhotoes: mortgagePhotoes, loanProxyPhotoes: loanProxyPhotoes, sellPhotoes: sellPhotoes, notarizingPhotoes: notarizingPhotoes, currentFlow: currentFlow, opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy04'
            }
            else
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName, acceptancePhotoes: acceptancePhotoes, otherPhotoes: otherPhotoes, loanPhotoes: loanPhotoes, mortgagePhotoes: mortgagePhotoes, loanProxyPhotoes: loanProxyPhotoes, sellPhotoes: sellPhotoes, notarizingPhotoes: notarizingPhotoes, currentFlow: currentFlow, opportunityLoanFlow: opportunityLoanFlow], view: 'attachments04'
            }
        }
        else
        {
            def photoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName(attachmentTypeName)?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("showCopy", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [photoes: photoes, attachmentTypeName: attachmentTypeName, currentFlow: currentFlow, opportunityLoanFlow: opportunityLoanFlow], view: 'showCopy'
            }
            else
            {
                respond opportunity, model: [photoes: photoes, attachmentTypeName: attachmentTypeName, currentFlow: currentFlow, opportunityLoanFlow: opportunityLoanFlow], view: 'show'
            }

        }
    }

    /**
     * @Author 夏瑞坤
     * @ModifiedDate 2017-6-12
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def riskManageShow(Opportunity opportunity)
    {
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def opportunityStage = OpportunityStage.findByCode("46")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        def attachmentTypeName = params["attachmentTypeName"]
        if (attachmentTypeName == '基础材料')
        {
            def maritalPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("借款人身份证")?.id])

            def maritalSpousePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("借款人配偶身份证")?.id])

            def mortgagorPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押人身份证")?.id])

            def mortgagorSpousePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押人配偶身份证")?.id])

            def housePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("房产证")?.id])

            def bookPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("户口本")?.id])

            def maritalIncPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("婚姻证明")?.id])

            def acceptancePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押登记受理单")?.id])

            def otherPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("他项证明")?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("attachmentsCopy02", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [housePhotoes: housePhotoes,
                    bookPhotoes: bookPhotoes,
                    maritalIncPhotoes: maritalIncPhotoes,
                    maritalPhotoes: maritalPhotoes,
                    maritalSpousePhotoes: maritalSpousePhotoes,
                    mortgagorPhotoes: mortgagorPhotoes,
                    mortgagorSpousePhotoes: mortgagorSpousePhotoes, attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    otherPhotoes: otherPhotoes,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy06'
            }
            else
            {
                respond opportunity, model: [housePhotoes: housePhotoes,
                    bookPhotoes: bookPhotoes,
                    maritalIncPhotoes: maritalIncPhotoes,
                    maritalPhotoes: maritalPhotoes,
                    maritalSpousePhotoes: maritalSpousePhotoes,
                    mortgagorPhotoes: mortgagorPhotoes,
                    mortgagorSpousePhotoes: mortgagorSpousePhotoes, attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    otherPhotoes: otherPhotoes,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachments02'
            }
        }
        else if (attachmentTypeName == '征信')
        {
            def notarizingPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("征信报告")?.id])

            def donePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("被执情况")?.id])

            def decimalPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("大数据")?.id])

            def centralBankCreditPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("央行征信（中佳信）")?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("showCopy", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [notarizingPhotoes: notarizingPhotoes,
                    donePhotoes: donePhotoes,
                    decimalPhotoes: decimalPhotoes, centralBankCreditPhotoes: centralBankCreditPhotoes, attachmentTypeName: attachmentTypeName,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy08'
            }
            else
            {
                respond opportunity, model: [notarizingPhotoes: notarizingPhotoes,
                    donePhotoes: donePhotoes,
                    decimalPhotoes: decimalPhotoes, centralBankCreditPhotoes: centralBankCreditPhotoes, attachmentTypeName: attachmentTypeName,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'show'
            }
        }
        else if (attachmentTypeName == '签呈')
        {
            def acceptancePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押登记受理单")?.id])

            def normalApprovalPhotos = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("普通签呈")?.id])

            def specialApprovalPhotos = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("特批签呈")?.id])
            def opportunityLayout = OpportunityLayout.findByNameAndActive("attachmentsCopy03", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    normalApprovalPhotos: normalApprovalPhotos,
                    specialApprovalPhotos: specialApprovalPhotos,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy05'
            }
            else
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName,
                    acceptancePhotoes: acceptancePhotoes,
                    normalApprovalPhotos: normalApprovalPhotos,
                    specialApprovalPhotos: specialApprovalPhotos,
                    currentFlow: currentFlow,
                    opportunityLoanFlow: opportunityLoanFlow], view: 'attachments03'
            }
        }
        else if (attachmentTypeName == '抵押合同全委')
        {
            def acceptancePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押登记受理单")?.id])
            def otherPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("他项证明")?.id])
            def loanPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("借款合同")?.id])
            def mortgagePhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("抵押合同")?.id])
            def loanProxyPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("委托借款代理服务合同")?.id])
            def sellPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("售房公证")?.id])
            def notarizingPhotoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName("公证受理单")?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("attachmentsCopy04", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName, acceptancePhotoes: acceptancePhotoes, otherPhotoes: otherPhotoes, loanPhotoes: loanPhotoes, mortgagePhotoes: mortgagePhotoes, loanProxyPhotoes: loanProxyPhotoes, sellPhotoes: sellPhotoes, notarizingPhotoes: notarizingPhotoes, currentFlow: currentFlow, opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy05'
            }
            else
            {
                respond opportunity, model: [attachmentTypeName: attachmentTypeName, acceptancePhotoes: acceptancePhotoes, otherPhotoes: otherPhotoes, loanPhotoes: loanPhotoes, mortgagePhotoes: mortgagePhotoes, loanProxyPhotoes: loanProxyPhotoes, sellPhotoes: sellPhotoes, notarizingPhotoes: notarizingPhotoes, currentFlow: currentFlow, opportunityLoanFlow: opportunityLoanFlow], view: 'attachments04'
            }
        }
        else
        {
            def photoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, AttachmentType.findByName(attachmentTypeName)?.id])

            def opportunityLayout = OpportunityLayout.findByNameAndActive("showCopy", true)
            if (opportunityLayout)
            {
                respond opportunity, model: [photoes: photoes, attachmentTypeName: attachmentTypeName, currentFlow: currentFlow, opportunityLoanFlow: opportunityLoanFlow], view: 'attachmentsCopy05'
            }
            else
            {
                respond opportunity, model: [photoes: photoes, attachmentTypeName: attachmentTypeName, currentFlow: currentFlow, opportunityLoanFlow: opportunityLoanFlow], view: 'show'
            }

        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def ajaxShow()
    {
        def opportunity = Opportunity.findById(params["opportunityId"])
        def attachmentType = AttachmentType.findByName(params["attachmentTypeName"])

        def photoes = Attachments.findAll("from Attachments where opportunity.id = ? and type.id = ? order by displayOrder, createdDate asc", [opportunity?.id, attachmentType?.id])
        render([status: "success", photoes: photoes] as JSON)
        return
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def create()
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def attachments = new Attachments(params)
        def opportunityFlowAttachmentTypes
        def attachmentTypeList = []

        def opportunity = Opportunity.findById(params["id"])
        attachments.opportunity = opportunity
        if(UserRole.findByUserAndRole(user,Role.findByAuthority("ROLE_ADMINISTRATOR"))){
            attachmentTypeList = AttachmentType.list()
        }else {
            def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
            if (currentFlow)
            {
                opportunityFlowAttachmentTypes = OpportunityFlowAttachmentType.findAllByStage(currentFlow)
                opportunityFlowAttachmentTypes?.each {
                    attachmentTypeList.add(it?.attachmentType)
                }
                if (attachmentTypeList?.size() == 0)
                {
                    attachmentTypeList = AttachmentType.list()
                }
            }
        }


        respond attachments, model: [attachmentTypeList: attachmentTypeList]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    @Transactional
    def save(Attachments attachments)
    {
        if (attachments == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (attachments.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond attachments.errors, view: 'create'
            return
        }

        attachments.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'attachments.label',
                                                                                        default: 'Attachments'),
                    attachments.id])
                redirect attachments
            }
            '*' { respond attachments, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def edit(Attachments attachments)
    {
        def attachmentTypeName = params['attachmentTypeName']
        respond attachments, model: [attachmentTypeName: attachmentTypeName]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def update(Attachments attachments)
    {
        def attachmentTypeName = params['attachmentTypeName']
        if (attachments == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (attachments.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond attachments.errors, view: 'edit'
            return
        }

        attachments.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'attachments.label',
                                                                                        default: 'Attachments'),
                    attachments.id])
                // redirect attachments
                redirect(controller: "attachments", action: "show", params: [id: attachments?.opportunity?.id, attachmentTypeName: attachmentTypeName])
            }
            '*' { respond attachments, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def delete(Attachments attachments)
    {
        println "******************* lms delete files ********************************"
        def attachmentTypeName = params['attachmentTypeName']
        if (attachments == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        def username = springSecurityService.getPrincipal().username
        println "username:" + username
        println "attachmentId:" + attachments?.id
        println "fileName:" + attachments?.fileName
        println "fileUrl:" + attachments?.fileUrl
        println "attachmentTypeName:" + attachmentTypeName
        println "serialNumber:" + attachments?.opportunity?.serialNumber

        // String file = attachments.fileName.split('/')[-1]
        // fileServerService.remove(file)

        attachments.delete flush: true

        // redirect(controller: "opportunity", action: "show", id: attachments?.opportunity?.id)
        redirect(controller: "attachments", action: "show", params: [id: attachments?.opportunity?.id, attachmentTypeName: attachmentTypeName])
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def riskDelete(Attachments attachments)
    {
        println "******************* lms delete files ********************************"
        def attachmentTypeName = params['attachmentTypeName']
        if (attachments == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        def username = springSecurityService.getPrincipal().username
        println "username:" + username
        println "attachmentId:" + attachments?.id
        println "fileName:" + attachments?.fileName
        println "fileUrl:" + attachments?.fileUrl
        println "attachmentTypeName:" + attachmentTypeName
        println "serialNumber:" + attachments?.opportunity?.serialNumber

        // String file = attachments.fileName.split('/')[-1]
        // fileServerService.remove(file)

        attachments.delete flush: true

        // redirect(controller: "opportunity", action: "show", id: attachments?.opportunity?.id)
        redirect(controller: "attachments", action: "riskManageShow", params: [id: attachments?.opportunity?.id, attachmentTypeName: attachmentTypeName])
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'attachments.label',
                                                                                          default: 'Attachments'),
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
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def prepareSetDisplayOrder()
    {
        def opportunity = Opportunity.findById(params['opportunity'])
        def attachmentType = AttachmentType.findById(params['attachmentType'])
        def attachments = Attachments.findAll("from Attachments where opportunity.id = ${opportunity?.id} and type.id = ${attachmentType?.id} order by displayOrder,createdDate asc")
        respond opportunity, model: [attachments: attachments, attachmentType: attachmentType], view: 'displayOrder'
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def setDisplayOrder()
    {
        def list = params['imgIdList[]']
        if (list)
        {
            def attachment
            for (
                int i = 0;
                    i < list.size();
                    i++)
            {
                attachment = Attachments.findById(list[i]?.toInteger())
                attachment.displayOrder = i
                attachment.save flush: true
            }
        }
        render([status: "success"] as JSON)
    }

    @Secured(['permitAll'])
    @Transactional
    def uploadAvatar()
    {
        // 图片参数校验
        def opportunityId = params["opportunityId"]
        def attachmentType = params["type"]
        def file = request.getFile("file")

        if (file.empty)
        {
            flash.message = message(code: '请选择要上传的文件')
            redirect(controller: "attachments", action: "create", params: [id: opportunityId, type: attachmentType])
            return
        }
        def imageExtensionsList = ['jpg', 'png', 'jpeg', 'JPG', 'PNG', 'JPEG']
        def fileExtensionsList = ['pdf', 'doc', 'docx', 'xlsx', 'xls']
        def videoExtensionsList = ['mov','mp4','avi','rm','3gp','mkv','wmv','ogg','rmvb']
        def fileSize = request.getContentLength()
        println fileSize
        if (AttachmentType.findById(attachmentType)?.id==AttachmentType.findByName("签约录像")?.id&&fileSize > 31457280){
            flash.message = message(code: '视频大小不能超过30M')
            redirect(controller: "attachments", action: "create", params: [id: opportunityId, type: attachmentType])
            return
        }
        if (AttachmentType.findById(attachmentType)?.id!=AttachmentType.findByName("签约录像")?.id&&fileSize > 15728640)
        {
            flash.message = message(code: '文件大小不能超过15M')
            redirect(controller: "attachments", action: "create", params: [id: opportunityId, type: attachmentType])
            return
        }
        def fileOrgName = file?.getOriginalFilename()
        def fileType = fileOrgName?.split('\\.')[-1]
        fileType = fileType?.toLowerCase()
        if (!(fileType in imageExtensionsList) && !(fileType in fileExtensionsList)&&!(fileType in videoExtensionsList))
        {
            flash.message = message(code: '文件格式不支持')
            redirect(controller: "attachments", action: "create", params: [id: opportunityId, type: attachmentType])
            return
        }

        // 将图片临时存储到 images目录中
        def webrootDir = servletContext.getRealPath("/")
        def code = UUID.randomUUID().toString()
        File fileImage = new File(webrootDir, "images/${code}.${fileType}")
        file.transferTo(fileImage)

        // 将临时文件存储转成 Base64
        // FileInputStream inputStream = new FileInputStream(fileImage)
        // ByteArrayOutputStream output = new ByteArrayOutputStream()
        // byte[] fileBytes = new byte[inputStream.available()]
        // int len = 0
        // while ((len = inputStream.read(fileBytes)) != -1)
        // {
        //     output.write(fileBytes, 0, len);
        // }
        // def encoded = fileBytes.encodeBase64().toString()

        // 存储图片信息
        def o = [:]
        def param = [:]
        param.put("fileType", fileType)
        param.put("appSessionId", "b6759ecb-0bb8-4c04-93ab-762cb16d91bc")
        def fileName = fileServerService.upload1(fileImage, param)
        def fileUrl = fileServerService.upload2(fileImage, param)
        def thumbnailUrl = ""
        if(!(fileType in videoExtensionsList)) {
            thumbnailUrl = fileServerService.compress1(fileImage, param)
        }
        // 删除临时文件
        if (fileImage.isFile() && fileImage.exists())
        {
            def flag = fileImage.delete()
        }

        // 存储到旧版文件服务器
        if (fileName || fileUrl)
        {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy")
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM")
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd")
            Date date = new Date()

            def year = sdf1.format(date)
            def month = sdf2.format(date)
            def day = sdf3.format(date)

            def attachment = new Attachments()
            attachment.fileName = "https://s7a.zhongjiaxin.com/${year}/${month}/${day}/${fileName}.${fileType}"
            attachment.fileUrl = "https://s74.zhongjiaxin.com/${year}/${month}/${day}/${fileUrl}.${fileType}"
            if(!(fileType in videoExtensionsList)){
                attachment.thumbnailUrl = "https://s74.zhongjiaxin.com/${year}/${month}/${day}/${thumbnailUrl}.${fileType}"
            }


            def opportunity = Opportunity.findById(opportunityId)
            attachment.type = AttachmentType.findById(attachmentType)
            attachment.opportunity = opportunity
            attachment.contact = opportunity?.contact
            attachment.description = fileOrgName?.split('\\.')[0]

            def files = []
            def sfile = [:]
            if (fileType in imageExtensionsList)
            {
                sfile['thumbnailUrl'] = attachment.fileName
            }
            else
            {
                sfile['thumbnailUrl'] = ''
            }
            sfile['name'] = fileOrgName
            files.add(sfile)
            o['files'] = files
            if (attachment.validate())
            {
                println "fileName:" + attachment.fileName
                println "fileUrl:" + attachment.fileUrl
                println "thumbnailUrl:" + attachment.thumbnailUrl

                attachment.save flush: true
                render o as JSON
            }
            else
            {
                flash.message = message(code: '文件上传失败，请稍后重试')
                redirect(controller: "attachments", action: "create", params: [id: opportunityId, type: attachmentType])
            }
        }
        else
        {
            flash.message = message(code: '调用上传文件服务失败，请稍后重试')
            redirect(controller: "attachments", action: "create", params: [id: opportunityId, type: attachmentType])
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def searchCallInfo()
    {
        params.max = 10
        def max = 10

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def offset = params.offset
        def opportunityId = params["id"]
        def sql = "from Activity as a where a.assignedTo.id = ${user.id} and a.opportunity.id = ${opportunityId} and " + "a.type.name = 'Call' and a.subtype.name = 'Sign In' order by startTime desc"
        def activityList = Activity.findAll(sql, [max: max, offset: offset])
        def allList = Activity.findAll(sql)

        respond activityList, model: [activityCount: allList.size(), params: params, attachmentTypeName: "打卡记录"],
                view: 'show'
    }

    /*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/

    // @Secured('permitAll')
    // @Transactional
    // def upload()
    // {
    //     println "******************* upload ********************"
    //     def json = request.JSON
    //     String attachmentType = json["attachmentType"]
    //     String file = json["file"]
    //     String fileType = json["fileType"]
    //     String opportunitySerialNumber = json["opportunitySerialNumber"]

    //     if (!attachmentType)
    //     {
    //         def errors = [errorCode: 5200, errorMessage: "上传图片的类型不能为空"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     if (!AttachmentType.findByName(attachmentType))
    //     {
    //         def errors = [errorCode: 5206, errorMessage: "上传图片的类型不正确"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     if (!file)
    //     {
    //         def errors = [errorCode: 5201, errorMessage: "请选择要上传的图片"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     if (!fileType)
    //     {
    //         def errors = [errorCode: 5202, errorMessage: "上传图片的格式不能为空"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     if (!opportunitySerialNumber)
    //     {
    //         def errors = [errorCode: 5203, errorMessage: "订单编号不能为空"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     def opportunity = Opportunity.findBySerialNumber(opportunitySerialNumber)
    //     if (!opportunity)
    //     {
    //         def errors = [errorCode: 5204, errorMessage: "订单编号不存在"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }

    //     def fileName = fileServerService.upload(file, fileType)
    //     if (fileName)
    //     {
    //         def attachment = new Attachments()
    //         attachment.fileName = "http://s27.zhongjiaxin.com/fs/static/images/${fileName}.${fileType}"
    //         println attachment.fileName
    //         attachment.type = AttachmentType.findByName(attachmentType)
    //         attachment.opportunity = opportunity
    //         if (attachment.validate())
    //         {
    //             attachment.save flush: true
    //             def fileList = [fileName: fileName, fileUrl: attachment.fileName]
    //             render JsonOutput.toJson(fileList), status: 200
    //             return
    //         }
    //         else
    //         {
    //             println attachment.errors
    //         }
    //     }
    //     else
    //     {
    //         def errors = [errorCode: 5205, errorMessage: "调用上传图片服务失败，请稍后重试"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    // }

    // @Secured('permitAll')
    // @Transactional
    // def appUploadActivityAttachments()
    // {
    //     //助手端上传照片
    //     println "******************* appUploadActivityAttachments ********************"
    //     def json = request.JSON
    //     //        println json
    //     def attachmentType = json["attachmentType"]?.toString()
    //     String file = json["file"]
    //     String fileType = json["fileType"]
    //     def opportunityId = json['opportunityId']?.toString()
    //     //下户
    //     def activityId = json['activityId']?.toString()
    //     //下户
    //     def contactId = json['contactId']?.toString()

    //     def displayOrder = json['displayOrder']?.toString()

    //     if (!attachmentType)
    //     {
    //         def errors = [errorCode: 5200, errorMessage: "上传图片的类型不能为空"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     def attype = AttachmentType.findByName(attachmentType)
    //     if (!attype)
    //     {
    //         def errors = [errorCode: 5206, errorMessage: "上传图片的类型不正确"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     if (!file)
    //     {
    //         def errors = [errorCode: 5201, errorMessage: "请选择要上传的图片"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     if (!fileType)
    //     {
    //         def errors = [errorCode: 5202, errorMessage: "上传图片的格式不能为空"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     if (!opportunityId || opportunityId.length() == 0)
    //     {
    //         def errors = [errorCode: 5207, errorMessage: "上传图片的订单不能为空"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }

    //     println "#######opportunityId########3"
    //     println opportunityId
    //     Opportunity opportunity = Opportunity.get(opportunityId)
    //     if (!opportunity)
    //     {
    //         def errors = [errorCode: 5207, errorMessage: "此订单不存在"]
    //         render JsonOutput.toJson(errors), status: 400
    //         return
    //     }
    //     switch (attype.name && opportunity.stage.name in ["预约已指派"])
    //     {
    //     case "购房合同":
    //     case "出租：抵押物租赁合同、租客告知书":
    //     case "二抵：抵押物银行按揭贷款合同":
    //     case "特殊房产需提供资料":
    //     case "小区外围配套物业":
    //     case "出入口道路":
    //     case "小区名称及物业地址":
    //     case "小区楼栋号、单元号、门牌号":
    //     case "小区规模":
    //     case "抵押物各房间情况":
    //     case "家访人员出镜照片":
    //     case "客户出镜的照片":
    //     case "户口本（集体户口需提供额外资料）":
    //     case "特殊资料":
    //     case "附加资料":
    //         if (!activityId || activityId.length() == 0)
    //         {
    //             def errors = [errorCode: 5208, errorMessage: "上传图片的活动不能为空"]
    //             render JsonOutput.toJson(errors), status: 400
    //             return
    //         }
    //         break
    //     case "借款人身份证":
    //     case "借款人配偶身份证":
    //     case "抵押人身份证":
    //     case "抵押人配偶身份证":
    //         println "########contactId########"
    //         println contactId
    //         if (!contactId || contactId.length() == 0)
    //         {
    //             def errors = [errorCode: 5209, errorMessage: "上传图片的关联人不能为空"]
    //             render JsonOutput.toJson(errors), status: 400
    //             return
    //         }
    //         break
    //     }
    //     def fileName = fileServerService.upload(file, fileType)
    //     if (fileName)
    //     {
    //         def attachment = new Attachments()
    //         attachment.fileName = "http://s27.zhongjiaxin.com/fs/static/images/${fileName}.${fileType}"

    //         println attachment.fileName
    //         attachment.type = attype

    //         if (opportunityId)
    //         {
    //             opportunityId = Integer.parseInt opportunityId
    //             attachment.opportunity = Opportunity.get(opportunityId)
    //         }
    //         if (activityId && activityId.length() > 0)
    //         {
    //             activityId = Integer.parseInt activityId
    //             attachment.activity = Activity.get(activityId)
    //         }
    //         if (contactId && contactId.length() > 0)
    //         {
    //             contactId = Integer.parseInt contactId
    //             attachment.contact = Contact.get(contactId)
    //         }
    //         if (displayOrder && displayOrder.length() > 0)
    //         {
    //             displayOrder = Integer.parseInt displayOrder
    //             attachment.displayOrder = displayOrder
    //         }

    //         if (attachment.validate())
    //         {
    //             attachment.save flush: true
    //             def fileList = [fileName: fileName, fileUrl: attachment.fileName]
    //             render JsonOutput.toJson(fileList), status: 200
    //         }
    //         else
    //         {
    //             println attachment.errors
    //         }
    //     }
    //     else
    //     {
    //         def errors = [errorCode: 5205, errorMessage: "调用上传图片服务失败，请稍后重试"]
    //         render JsonOutput.toJson(errors), status: 400
    //     }
    // }

    /**
     * @ Author 张成远
     * @ function 新版黄金屋上传图片功能，将图片上传到 https://s74.zhongjiaxin.com，https://s7a.zhongjiaxin.com 两台文件服务器上
     * @ ModifiedDate 2017-5-24*/
    @Secured('permitAll')
    /*@Transactional*/
    def upload()
    {
        println "******************* upload ********************"
        def json = request.JSON
        String attachmentType = json["attachmentType"]
        String file = json["file"]
        String fileType = json["fileType"]
        String opportunitySerialNumber = json["opportunitySerialNumber"]
        def appSessionId = json['appSessionId']

        if (!attachmentType)
        {
            def errors = [errorCode: 5200, errorMessage: "上传图片的类型不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!AttachmentType.findByName(attachmentType))
        {
            def errors = [errorCode: 5206, errorMessage: "上传图片的类型不正确"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!file)
        {
            def errors = [errorCode: 5201, errorMessage: "请选择要上传的图片"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!fileType)
        {
            def errors = [errorCode: 5202, errorMessage: "上传图片的格式不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunitySerialNumber)
        {
            def errors = [errorCode: 5203, errorMessage: "订单编号不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunity = Opportunity.findBySerialNumber(opportunitySerialNumber)
        if (!opportunity)
        {
            def errors = [errorCode: 5204, errorMessage: "订单编号不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def webrootDir = servletContext.getRealPath("/")
        def code = UUID.randomUUID().toString()

        BASE64Decoder decoder = new BASE64Decoder()
        byte[] bImage = decoder.decodeBuffer(file)
        File fileImage = new File(webrootDir, "images/${code}.${fileType}")
        fileImage.append(bImage)

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy")
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM")
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd")
        Date date = new Date()

        def year = sdf1.format(date)
        def month = sdf2.format(date)
        def day = sdf3.format(date)

        def fileName = ""
        def fileUrl = ""
        def param = [:]
        if (appSessionId)
        {
            // 新版
            Contact contact = Contact.findByAppSessionId(appSessionId)
            if (contact)
            {
                param.put("fileType", fileType)
                param.put("appSessionId", "b6759ecb-0bb8-4c04-93ab-762cb16d91bc")
            }
            else
            {
                def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        else
        {
            // 旧版
            param.put("fileType", fileType)
            param.put("appSessionId", "b6759ecb-0bb8-4c04-93ab-762cb16d91bc")
        }

        // 1.上传到备份文件服务器 s7a
        def fileNameOld = fileServerService.upload1(fileImage, param)
        if (fileNameOld)
        {
            fileName = "https://s7a.zhongjiaxin.com/${year}/${month}/${day}/${fileNameOld}.${fileType}"
        }

        // 2.上传到新版文件服务器 s74
        def fileNameNew = fileServerService.upload2(fileImage, param)
        if (fileNameNew)
        {
            fileUrl = "https://s74.zhongjiaxin.com/${year}/${month}/${day}/${fileNameNew}.${fileType}"
        }

        // 删除临时文件
        if (fileImage.isFile() && fileImage.exists())
        {
            fileImage.delete()
        }

        if (fileName || fileUrl)
        {
            def attachment = new Attachments()
            attachment.fileName = fileName
            attachment.fileUrl = fileUrl
            attachment.type = AttachmentType.findByName(attachmentType)
            attachment.opportunity = opportunity

            if (attachment.validate())
            {
                println "黄金屋订单号：" + opportunitySerialNumber
                println "黄金屋上传图片旧版地址：" + attachment.fileName
                println "黄金屋上传图片新版地址：" + attachment.fileUrl

                attachment.save flush: true
                def fileList = [fileName: fileNameNew, fileUrl: attachment.fileUrl]
                render JsonOutput.toJson(fileList), status: 200
                return
            }
            else
            {
                println attachment.errors
            }
        }
        else
        {
            def errors = [errorCode: 5205, errorMessage: "调用上传图片服务失败，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
    }

    /**
     * @ Author 张成远
     * @ function 新版小助手上传图片功能，将图片上传到 https://s74.zhongjiaxin.com，https://s7a.zhongjiaxin.com 两台文件服务器上
     * @ ModifiedDate 2017-5-24*/
    @Secured('permitAll')
    /*@Transactional*/
    def appUploadActivityAttachments()
    {
        //助手端上传照片
        println "******************* appUploadActivityAttachments ********************"
        def json = request.JSON

        def attachmentType = json["attachmentType"]?.toString()
        String file = json["file"]
        String fileType = json["fileType"]
        def opportunityId = json['opportunityId']?.toString()
        def activityId = json['activityId']?.toString()
        def contactId = json['contactId']?.toString()
        def appSessionId = json['appSessionId']?.toString()
        def displayOrder = json['displayOrder']?.toString()

        if (!attachmentType)
        {
            def errors = [errorCode: 5200, errorMessage: "上传图片的类型不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def attype = AttachmentType.findByName(attachmentType)
        if (!attype)
        {
            def errors = [errorCode: 5206, errorMessage: "上传图片的类型不正确"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!file)
        {
            def errors = [errorCode: 5201, errorMessage: "请选择要上传的图片"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!fileType)
        {
            def errors = [errorCode: 5202, errorMessage: "上传图片的格式不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunityId || opportunityId.length() == 0)
        {
            def errors = [errorCode: 5207, errorMessage: "上传图片的订单不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        Opportunity opportunity = Opportunity.get(opportunityId)
        if (!opportunity)
        {
            def errors = [errorCode: 5207, errorMessage: "此订单不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        switch (attype.name && opportunity.stage.name in ["预约已指派"])
        {
        case "购房合同":
        case "出租：抵押物租赁合同、租客告知书":
        case "二抵：抵押物银行按揭贷款合同":
        case "特殊房产需提供资料":
        case "小区外围配套物业":
        case "出入口道路":
        case "小区名称及物业地址":
        case "小区楼栋号、单元号、门牌号":
        case "小区规模":
        case "抵押物各房间情况":
        case "家访人员出镜照片":
        case "客户出镜的照片":
        case "户口本（集体户口需提供额外资料）":
        case "特殊资料":
        case "附加资料":
            if (!activityId || activityId.length() == 0)
            {
                def errors = [errorCode: 5208, errorMessage: "上传图片的活动不能为空"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            break
        case "借款人身份证":
        case "借款人配偶身份证":
        case "抵押人身份证":
        case "抵押人配偶身份证":
            println "########contactId########"
            println contactId
            if (!contactId || contactId.length() == 0)
            {
                def errors = [errorCode: 5209, errorMessage: "上传图片的关联人不能为空"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            break
        }

        def webrootDir = servletContext.getRealPath("/")
        def code = UUID.randomUUID().toString()

        BASE64Decoder decoder = new BASE64Decoder()
        byte[] bImage = decoder.decodeBuffer(file)
        File fileImage = new File(webrootDir, "images/${code}.${fileType}")
        fileImage.append(bImage)

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy")
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM")
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd")
        Date date = new Date()

        def year = sdf1.format(date)
        def month = sdf2.format(date)
        def day = sdf3.format(date)

        def fileName = ""
        def fileUrl = ""
        def param = [:]
        if (appSessionId)
        {
            // 新版
            User user = User.findByAppSessionId(appSessionId)
            if (user)
            {
                param.put("fileType", fileType)
                param.put("appSessionId", "b6759ecb-0bb8-4c04-93ab-762cb16d91bc")
            }
            else
            {
                def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        else
        {
            // 旧版
            param.put("fileType", fileType)
            param.put("appSessionId", "b6759ecb-0bb8-4c04-93ab-762cb16d91bc")
        }

        // 1.上传到备份文件服务器 s7a
        def fileNameOld = fileServerService.upload1(fileImage, param)
        if (fileNameOld)
        {
            fileName = "https://s7a.zhongjiaxin.com/${year}/${month}/${day}/${fileNameOld}.${fileType}"
        }

        // 2.上传到新版文件服务器 s74
        def fileNameNew = fileServerService.upload2(fileImage, param)
        if (fileNameNew)
        {
            fileUrl = "https://s74.zhongjiaxin.com/${year}/${month}/${day}/${fileNameNew}.${fileType}"
        }

        // 删除临时文件
        if (fileImage.isFile() && fileImage.exists())
        {
            fileImage.delete()
        }

        if (fileName || fileUrl)
        {
            def attachment = new Attachments()
            attachment.fileName = fileName
            attachment.fileUrl = fileUrl
            attachment.type = attype

            if (opportunityId)
            {
                opportunityId = Integer.parseInt opportunityId
                attachment.opportunity = Opportunity.get(opportunityId)
            }
            if (activityId && activityId.length() > 0)
            {
                activityId = Integer.parseInt activityId
                attachment.activity = Activity.get(activityId)
            }
            if (contactId && contactId.length() > 0)
            {
                contactId = Integer.parseInt contactId
                attachment.contact = Contact.get(contactId)
            }
            if (displayOrder && displayOrder.length() > 0)
            {
                displayOrder = Integer.parseInt displayOrder
                attachment.displayOrder = displayOrder
            }

            if (attachment.validate())
            {
                println "助手订单号：" + opportunity.serialNumber
                println "助手上传图片旧版地址：" + attachment.fileName
                println "助手上传图片新版地址：" + attachment.fileUrl

                attachment.save flush: true
                def fileList = [fileName: fileNameNew, fileUrl: attachment.fileUrl]
                render JsonOutput.toJson(fileList), status: 200
            }
            else
            {
                println attachment.errors
            }
        }
        else
        {
            def errors = [errorCode: 5205, errorMessage: "调用上传图片服务失败，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
        }
    }

    @Secured('permitAll')
    def query()
    {
        println "******************* query ********************"
        def json = request.JSON
        String attachmentType = json["attachmentType"]
        String opportunitySerialNumber = json["opportunitySerialNumber"]

        if (!attachmentType)
        {
            def errors = [errorCode: 5200, errorMessage: "上传图片的类型不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def attType = AttachmentType.findByName(attachmentType)
        if (!attType)
        {
            def errors = [errorCode: 5206, errorMessage: "上传图片的类型不正确"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunitySerialNumber)
        {
            def errors = [errorCode: 5203, errorMessage: "订单编号不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunity = Opportunity.findBySerialNumber(opportunitySerialNumber)
        if (!opportunity)
        {
            def errors = [errorCode: 5204, errorMessage: "订单编号不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def fileList = []
        def attList = Attachments.findAllByOpportunityAndType(opportunity, attType)
        if (attList)
        {
            attList.each { fileList.add(fileUrl: it.fileUrl) }
        }
        render JsonOutput.toJson(fileList), status: 200
    }

    /*
    *app查询附件类型
    *author xiaruikun
    * */

    @Secured('permitAll')
    @Transactional
    def queryAll()
    {
        def json = request.JSON

        def sessionId = json['sessionId']
        if (!sessionId)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        User user = User.findByAppSessionId(sessionId)
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def attachmentTypeList
        if (user.department?.name == '客服部')
        {
            attachmentTypeList = AttachmentType.findAll("from AttachmentType where name in ('房产证','借款人身份证','借款人配偶身份证','抵押人身份证','抵押人配偶身份证','户口本','婚姻证明','被执情况','产调结果','征信报告','借款合同','其他资料','大数据','公证书','抵押合同','抵押登记受理单','公证受理单','意向申请单','银行卡复印件','委托借款代理服务合同','其他放款要求资料')")
        }
        else if (user.department?.name == '权证组')
        {
            attachmentTypeList = AttachmentType.findAll("from AttachmentType where name in ('公证书','他项证明','抵押登记受理单','产调结果','借款合同','其他资料','抵押合同','公证受理单','意向申请单','银行卡复印件','委托借款代理服务合同','其他放款要求资料')")
        }
        else if (user.department?.name == '风控组')
        {
            attachmentTypeList = AttachmentType.findAll("from AttachmentType where name in ('其他资料','大数据','被执情况','公证书','借款合同','抵押合同','抵押登记受理单','公证受理单','意向申请单','银行卡复印件','委托借款代理服务合同','其他放款要求资料')")
        }
        //        else if (user.department?.name == '支持组')
        //        {
        //            attachmentTypeList = AttachmentType.findAll("from AttachmentType where name in ('其他资料','大数据')")
        //        }
        else
        {
            attachmentTypeList = ""
        }
        if (attachmentTypeList)
        {
            def typeList = []
            attachmentTypeList.each {
                typeList.add(it.name)
            }
            render JsonOutput.toJson(typeList)
        }
        else
        {
            render "value is null"
        }
    }

    @Secured('permitAll')
    @Transactional
    def remove()
    {
        //助手端删除照片
        println "******************* remove ********************"
        def json = request.JSON
        println json
        String fileUrl = json["fileUrl"]
        String opportunitySerialNumber = json["opportunitySerialNumber"]

        if (!fileUrl)
        {
            def errors = [errorCode: 5207, errorMessage: "图片名不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunitySerialNumber)
        {
            def errors = [errorCode: 5203, errorMessage: "订单编号不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunity = Opportunity.findBySerialNumber(opportunitySerialNumber)
        if (!opportunity)
        {
            def errors = [errorCode: 5204, errorMessage: "订单编号不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        // def attachments = Attachments.findByOpportunityAndFileName(opportunity, fileUrl)
        def attachments = Attachments.findByOpportunityAndFileUrl(opportunity, fileUrl)
        if (attachments)
        {
            // String file = attachments.fileName.split('/')[-1]
            // fileServerService.remove(file)

            println "attachmentId:" + attachments?.id
            println "fileUrl:" + fileUrl
            println "opportunitySerialNumber:" + opportunitySerialNumber

            attachments.delete flush: true
        }
        render status: 200
    }

    @Secured('permitAll')
    def appQueryByContact()
    {
        //助手端查询下户关联人照片
        def json = request.JSON
        println "---------------------appQueryByContact------------------------------"
        println json

        String attachmentType = json["attachmentType"]
        def opportunityId = json['opportunityId']
        //def contactId = json['contactId']
        //def activityId = json['activityId']

        if (!attachmentType)
        {
            def errors = [errorCode: 5200, errorMessage: "上传图片的类型不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def attType = AttachmentType.findByName(attachmentType)
        if (!attType)
        {
            def errors = [errorCode: 5206, errorMessage: "上传图片的类型不正确"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        /*if (contactId == null || contactId == '')
        {
            def errors = [errorCode: 5209, errorMessage: "上传图片的关联人不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }*/
        if (opportunityId == null || opportunityId == '')
        {
            def errors = [errorCode: 5209, errorMessage: "上传图片的关联人不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        /*if (activityId == null || activityId == '')
        {
            def errors = [errorCode: 5209, errorMessage: "上传图片的关联人不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }*/

        def result = []
        def list = Attachments.findAll("from Attachments as a where a.opportunity.id=${opportunityId} and a.type" + ".id=${attType.id} order by displayOrder,createdDate asc")
        println list.size()
        if (list)
        {
            list.each {
                def atturl = [:]
                atturl['fileUrl'] = it.fileUrl
                result.add(atturl)
            }
        }
        println result.size()
        render JsonOutput.toJson(result), status: 200
    }

    @Secured('permitAll')
    def appQueryByActivity()
    {
        //助手端查询下户非关联人照片
        def json = request.JSON
        println "---------------------appQueryByActivity------------------------------"
        println json

        String attachmentType = json["attachmentType"]
        def opportunityId = json['opportunityId']?.toInteger()
        //def activityId = json['activityId']?.toInteger()

        if (!attachmentType)
        {
            def errors = [errorCode: 5200, errorMessage: "上传图片的类型不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def attType = AttachmentType.findByName(attachmentType)
        if (!attType)
        {
            def errors = [errorCode: 5206, errorMessage: "上传图片的类型不正确"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (opportunityId == null || opportunityId == '')
        {
            def errors = [errorCode: 5209, errorMessage: "上传图片的关联人不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        /*if (activityId == null || activityId == '')
        {
            def errors = [errorCode: 5209, errorMessage: "上传图片的关联人不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }*/

        def result = []
        def list = Attachments.findAll("from Attachments as a where a.opportunity.id=${opportunityId} and a.type" + ".id=${attType.id} order by displayOrder,createdDate asc")
        if (list)
        {
            list.each {
                def atturl = [:]
                atturl['fileUrl'] = it.fileUrl
                result.add(atturl)
            }
        }
        render JsonOutput.toJson(result), status: 200
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def getFileUrl()
    {
        def attachment = Attachments.findById(params["dataId"])
        def opportunityLayout = OpportunityLayout.findByNameAndActive("attachmentsCopy02", true)
        def fileUrl
        if (opportunityLayout)
        {
            fileUrl = attachment?.fileUrl
        }
        else
        {
            fileUrl = attachment?.fileName
        }
        render([status: "success", fileUrl: fileUrl] as JSON)
        return
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
    def create01(Opportunity opportunity)
    {
        def attachments = new Attachments(params)
        def attachmentTypeList = []

        //def opportunity = Opportunity.findById(params["id"])
        attachments.opportunity = opportunity

        attachmentTypeList = AttachmentType.findAllByName("融数审批通知书")

        respond attachments, model: [attachmentTypeList: attachmentTypeList]
    }
}

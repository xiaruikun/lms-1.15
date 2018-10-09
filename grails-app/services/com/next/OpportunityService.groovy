package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

import java.security.MessageDigest
import java.text.SimpleDateFormat

@Transactional
@CompileStatic
@TypeChecked
class OpportunityService
{
    static scope = "singleton"

    OpportunityTeamService opportunityTeamService
    OpportunityRoleService opportunityRoleService
    OpportunityNotificationService opportunityNotificationService
    OpportunityFlowService opportunityFlowService

    OpportunityWorkflowRoleService opportunityWorkflowRoleService
    OpportunityWorkflowNotificationService opportunityWorkflowNotificationService
    OpportunityWorkflowStageService opportunityWorkflowStageService

    OpportunityFlowConditionService opportunityFlowConditionService
    OpportunityEventService opportunityEventService
    FileServerService fileServerService
    ActivityService activityService
    OpportunityFlexFieldCategoryService opportunityFlexFieldCategoryService

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    def initOpportunity(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        def territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
        def territory = territoryAccount?.territory
        def territoryOpportunityWorkflows
        def flag = true
        def opportunityFlow

        opportunity.territory = territory
        opportunity.save flush: true

        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id}")
        if (opportunityFlows?.size() == 0)
        {
            opportunityTeamService.initTeam(opportunity, workflow)

            def type = OpportunityType.find("from OpportunityType where code = '10'")

            territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAll("from TerritoryOpportunityWorkflow where territory.id = ${territory?.id}")
            if (territoryOpportunityWorkflows?.size() > 0)
            {
                for (
                    territoryWorkflow in
                        territoryOpportunityWorkflows)
                {
                    if (opportunity?.type?.id == territoryWorkflow?.workflow?.opportunityType?.id && territoryWorkflow?.workflow?.active)
                    {
                        flag = true
                        break
                    }
                    else
                    {

                        if (opportunity?.type == type)
                        {
                            flag = false
                        }

                    }
                }
            }
            else
            {
                flag = false
            }

            if (flag)
            {
                opportunityWorkflowRoleService.initRole(opportunity, workflow)
                opportunityWorkflowNotificationService.initNotification(opportunity, workflow)
                opportunityWorkflowStageService.initFlow(opportunity, workflow)
            }
            else
            {
                opportunityRoleService.initRole(opportunity)
                opportunityNotificationService.initNotification(opportunity)
                opportunityFlowService.initFlow(opportunity)
            }

            opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? order by executionSequence asc", [opportunity?.id])
            if (opportunityFlow)
            {
                opportunityFlow.startTime = new Date()
                opportunityFlow.save flush: true
            }

            opportunityFlexFieldCategoryService.initFlexFieldCategory(opportunity, workflow)
        }
        opportunityNotificationService.sendNotification(opportunity)
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    def approve(Opportunity opportunity, OpportunityFlow nextStage)
    {
        OpportunityFlow opportunityFlow
        def executionSequence
        def map = [:]
        def verifyCondition
        def response
        def opportunityAction = OpportunityAction.find("from OpportunityAction where name = '同意'")

        if (!opportunity.status == "Pending")
        {
            map['flag'] = false
            map['message'] = '订单已结束！'
            return map
        }

        opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?",
                                               [opportunity?.id, opportunity?.stage?.id])
        if (!opportunityFlow)
        {
            map['flag'] = false
            map['message'] = "找不到工作流"
            return map
        }
        //没通过暂时不保存结束时间
        //opportunityFlow.endTime = new Date()
        opportunityFlow.processed = true
        opportunityFlow.save flush: true

        opportunityEventService.evaluate(opportunityFlow, "negative")

        verifyCondition = opportunityFlowConditionService.evaluate(opportunity)
        // verifyCondition = opportunityFlowConditionService.evaluate(opportunityFlow)
        if (!verifyCondition['flag'])
        {
            return verifyCondition
        }

        opportunityEventService.evaluate(opportunityFlow, "positive")
        def opportunityFlowNext
        if (nextStage)
        {

            nextStage.startTime = new Date()
            nextStage.save flush: true

            opportunityFlow.endTime = new Date()
            opportunityFlow.save flush:true

            opportunity.stage = nextStage?.stage
            opportunity.lastAction = opportunityAction
            opportunity.save flush: true
        }
        else if (verifyCondition['nextStage'])
        {
            def opportunityStage = OpportunityStage.find("from OpportunityStage where code = '${verifyCondition['nextStage']}'")
            opportunityFlowNext = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, opportunityStage?.id])
            if (!opportunityFlowNext)
            {
                map['flag'] = false
                map['message'] = "找不到工作流"
                return map
            }

            opportunityFlowNext.startTime = new Date()
            opportunityFlowNext.save flush: true

            opportunityFlow.endTime = new Date()
            opportunityFlow.save flush:true

            opportunity.stage = opportunityFlowNext?.stage
            opportunity.lastAction = opportunityAction
            opportunity.save flush: true
        }
        // else if (verifyCondition['nextStage'])
        // {
        //     if (verifyCondition['nextStage'] instanceof OpportunityFlow)
        //     {
        //       nextStage = (OpportunityFlow) verifyCondition['nextStage']
        //     }
        //     else if (verifyCondition['nextStage'] instanceof String)
        //     {
        //       def opportunityStage = OpportunityStage.find("from OpportunityStage where code = '${verifyCondition['nextStage']}'")
        //       nextStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ${opportunity?.id} and stage.id = ${opportunityStage?.id}")
        //       if (!nextStage)
        //       {
        //         map['flag'] = false
        //         map['message'] = "找不到工作流"
        //         return map
        //       }
        //     }
        //     else
        //     {
        //       map['flag'] = false
        //       map['message'] = "校验跳转配置异常，请联系管理员"
        //       return map
        //     }
        //     nextStage.startTime = new Date()
        //     nextStage.save flush: true
        //
        //     opportunity.stage = nextStage?.stage
        //     opportunity.lastAction = opportunityAction
        //     opportunity.save flush: true
        // }
        else
        {
            executionSequence = opportunityFlow?.executionSequence
            opportunityFlowNext = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and " + "executionSequence > ? order by executionSequence ASC",
                                                   [opportunity?.id, executionSequence])
            if (!opportunityFlowNext)
            {
                map['flag'] = false
                map['message'] = "找不到工作流"
                return map
            }

            opportunityFlowNext.startTime = new Date()
            opportunityFlowNext.save flush: true


            opportunityFlow.endTime = new Date()
            opportunityFlow.save flush:true

            opportunity.stage = opportunityFlowNext?.stage
            opportunity.lastAction = opportunityAction
            opportunity.save flush: true
        }
        opportunityNotificationService.sendNotification(opportunity)
        map['flag'] = true
        map['message'] = "成功"
        return map
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    Boolean reject(Opportunity opportunity)
    {
        def opportunityAction = OpportunityAction.find("from OpportunityAction where name = '不同意'")
        if (opportunity?.status == "Pending")
        {
            OpportunityFlow opportunityFlow
            def executionSequence

            opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?",
                                                   [opportunity?.id, opportunity?.stage?.id])
            def canReject = opportunityFlow?.canReject
            if (canReject)
            {
                executionSequence = opportunityFlow?.executionSequence
                opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and " + "executionSequence < ? and processed = true order by executionSequence DESC",
                                                       [opportunity?.id, executionSequence])
                if (opportunityFlow)
                {
                    opportunity.stage = opportunityFlow.stage
                    opportunity.lastAction = opportunityAction
                    if (opportunity.validate())
                    {
                        opportunity.save flush: true

                        //发送消息
                        opportunityNotificationService.sendNotification(opportunity)
                        return true
                    }
                    else
                    {
                        opportunity.errors.each {
                            log.info "${it}"
                        }

                        return false
                    }
                }
                else
                {
                    return false
                }
            }
            else
            {
                return false
            }
        }
        else
        {
            return false
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    Boolean cancel(Opportunity opportunity)
    {
        def opportunityAction = OpportunityAction.find("from OpportunityAction where name = '拒单'")
        if (opportunity?.status == "Pending")
        {
            opportunity.status = "Failed"
            opportunity.lastAction = opportunityAction
            if (opportunity.validate())
            {
                opportunity.save flush: true
                //发送消息
                opportunityNotificationService.sendNotification(opportunity)
                return true
            }
            else
            {
                opportunity.errors.each {
                    log.info "${it}"
                }

                return false
            }
        }
        else
        {
            return false
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    Boolean complete(Opportunity opportunity)
    {
        if (opportunity?.status == "Pending")
        {
            OpportunityFlow opportunityFlow
            def executionSequence
            opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?",
                                                   [opportunity?.id, opportunity?.stage?.id])
            executionSequence = opportunityFlow?.executionSequence
            opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and " + "executionSequence > ? order by executionSequence ASC",
                                                   [opportunity?.id, executionSequence])
            if (!opportunityFlow)
            {
                opportunity.status = "Completed"
                if (opportunity.validate())
                {
                    opportunity.save flush: true
                    return true
                }
                else
                {
                    opportunity.errors.each {
                        log.info "${it}"
                    }

                    return false
                }
            }
            else
            {
                return false
            }
        }
        else
        {
            return false
        }
    }

    def initProductInterest(Opportunity opportunity)
    {
        def map = [:]
        //房产初审已完成
        //        if (opportunity?.stage?.code == '07')
        //        {
        def productInterestList
        def opportunityProduct
        opportunityProduct = OpportunityProduct.find("from OpportunityProduct where product.id = ? and opportunity.id = ?", [opportunity?.productAccount?.id, opportunity?.id])
        if (opportunity.collaterals)
        {
            if (!opportunityProduct && opportunity?.lender?.level?.name != "D")
            {
                if (opportunity?.lender?.level?.name == "A")
                {
                    productInterestList = ProductInterest.findAll("from ProductInterest where product.id = ? and contactLevel.id = ?", [opportunity?.productAccount?.id, opportunity?.lender?.level?.id])
                }
                else if (opportunity?.lender?.level?.name == "B")
                {
                    def contactLevel = ContactLevel.find("from ContactLevel where name = ?", ["C"])
                    productInterestList = ProductInterest.findAll("from ProductInterest where product.id = ? and contactLevel.id != ?", [opportunity?.productAccount?.id, contactLevel?.id])
                }
                else if (opportunity?.lender?.level?.name == "C")
                {
                    def contactLevel = ContactLevel.find("from ContactLevel where name = ?", ["B"])
                    productInterestList = ProductInterest.findAll("from ProductInterest where product.id = ? and contactLevel.id != ?", [opportunity?.productAccount?.id, contactLevel?.id])
                }
                if (productInterestList && productInterestList.size() > 0)
                {
                    productInterestList.each {
                        if (it?.monthesOfStart <= opportunity.loanDuration && opportunity.loanDuration <= it?.monthesOfEnd)
                        {
                            if (opportunity?.mortgageType?.name == "二抵" || opportunity?.mortgageType?.name == "二抵转单")
                            {
                                opportunityProduct = new OpportunityProduct()
                                opportunityProduct.product = opportunity?.productAccount
                                opportunityProduct.opportunity = opportunity
                                opportunityProduct.productInterestType = it?.productInterestType
                                opportunityProduct.rate = it?.maximumRate
                                opportunityProduct.monthes = opportunity.loanDuration
                                opportunityProduct.fixedRate = it?.fixedRate
                                opportunityProduct.installments = it?.installments
                                opportunityProduct.firstPayMonthes = it?.firstPayMonthes
                                if (opportunityProduct.validate())
                                {
                                    opportunityProduct.save flush: true
                                }
                                else
                                {
                                    opportunityProduct.errors.each {
                                        log.info "${it}"
                                    }
                                }
                            }
                            else
                            {
                                if (it?.productInterestType?.name != "二抵加收费率")
                                {
                                    opportunityProduct = new OpportunityProduct()
                                    opportunityProduct.product = opportunity?.productAccount
                                    opportunityProduct.opportunity = opportunity
                                    opportunityProduct.productInterestType = it?.productInterestType
                                    opportunityProduct.rate = it?.maximumRate
                                    opportunityProduct.monthes = opportunity.loanDuration
                                    opportunityProduct.fixedRate = it?.fixedRate
                                    opportunityProduct.installments = it?.installments
                                    opportunityProduct.firstPayMonthes = it?.firstPayMonthes
                                    if (opportunityProduct.validate())
                                    {
                                        opportunityProduct.save flush: true
                                    }
                                    else
                                    {
                                        opportunityProduct.errors.each {
                                            log.info "${it}"
                                        }
                                    }
                                }
                            }
                        }
                    }
                    map['flag'] = true
                    map['message'] = ''
                    return map
                }
                else
                {
                    map['flag'] = false
                    map['message'] = '没有找到对应产品或未确定客户级别'
                    return map
                }
            }
            else
            {
                map['flag'] = false
                map['message'] = '请进行计算息费操作或提升客户级别！'
                return map
            }
        }
        else
        {
            map['flag'] = false
            map['message'] = '请添加房产信息！'
            return map
        }

        //        }
        //        else
        //        {
        //            map['flag'] = false
        //            map['message'] = '该阶段不是房产初审已完成！'
        //            return map
        //        }
    }

    def assignToClient(Opportunity opportunity)
    {
        if (opportunity.idNumber)
        {
            //Contact client = Contact.findByIdNumberAndType(opportunity.idNumber, "Client")
            Contact client = Contact.find("from Contact as c where c.idNumber = ? and c.type = 'Client'",
                                          [opportunity.idNumber])
            if (!client)
            {
                client = new Contact()
                client.type = "Client"
                client.idNumber = opportunity.idNumber
                client.cellphone = opportunity.cellphone
                client.fullName = opportunity.fullName
                client.user = opportunity.user
                client.maritalStatus = opportunity.maritalStatus
                client.account = opportunity.account
                client.level = ContactLevel.find("from ContactLevel where name = 'B'")

                if (client.validate())
                {
                    client.save()
                }
                else
                {
                    client.errors.each {
                        log.info "${it}"
                    }
                }
            }
            opportunity.lender = client
            if (opportunity.validate())
            {
                opportunity.save flush: true
            }
            else
            {
                opportunity.errors.each {
                    log.info "${it}"
                }
            }
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    def backup(Opportunity opportunity)
    {

        def ob = new OpportunityHistory()
        ob.serialNumber = opportunity?.serialNumber
        ob.idNumber = opportunity?.idNumber
        ob.fullName = opportunity?.fullName
        ob.cellphone = opportunity?.cellphone
        ob.maritalStatus = opportunity?.maritalStatus
        ob.maximumAmountOfCredit = opportunity?.maximumAmountOfCredit
        ob.actualAmountOfCredit = opportunity?.actualAmountOfCredit
        ob.requestedAmount = opportunity?.requestedAmount
        ob.loanAmount = opportunity?.loanAmount
        ob.actualLoanAmount = opportunity?.actualLoanAmount
        ob.commission = opportunity?.commission
        ob.loanDuration = opportunity?.loanDuration
        ob.actualLoanDuration = opportunity?.actualLoanDuration
        ob.protectionStartTime = opportunity?.protectionStartTime
        ob.protectionEndTime = opportunity?.protectionEndTime
        ob.unitPrice = opportunity?.unitPrice
        ob.mortgageCertificateType = opportunity?.mortgageCertificateType
        // if (opportunity?.mortgageType)
        // {
        //     ob.mortgageType = opportunity?.mortgageType
        // }
        // if (opportunity?.typeOfFirstMortgage)
        // {
        //     ob.typeOfFirstMortgage = opportunity?.typeOfFirstMortgage
        // }
        // if (opportunity?.firstMortgageAmount)
        // {
        //     ob.firstMortgageAmount = opportunity?.firstMortgageAmount
        // }
        // if (opportunity?.secondMortgageAmount)
        // {
        //     ob.secondMortgageAmount = opportunity?.secondMortgageAmount
        // }
        // if (opportunity.propertySerialNumber)
        // {
        //     ob.propertySerialNumber = opportunity.propertySerialNumber
        // }
        ob.status = opportunity?.status
        ob.causeOfFailure = opportunity?.causeOfFailure
        ob.descriptionOfFailure = opportunity?.descriptionOfFailure
        ob.memo = opportunity?.memo
        ob.createdDate = opportunity?.createdDate
        ob.modifiedDate = opportunity?.modifiedDate
        ob.stage = opportunity?.stage
        ob.type = opportunity?.type
        ob.subtype = opportunity?.subtype
        ob.interestPaymentMethod = opportunity?.interestPaymentMethod
        ob.principalPaymentMethod = opportunity?.principalPaymentMethod
        ob.commissionPaymentMethod = opportunity?.commissionPaymentMethod
        ob.monthlyInterest = opportunity?.monthlyInterest
        ob.ompositeMonthlyInterest = opportunity?.ompositeMonthlyInterest
        ob.commissionRate = opportunity?.commissionRate
        ob.dealRate = opportunity?.dealRate
        ob.lender = opportunity?.lender
        ob.propertyAdditionalInformation = opportunity?.propertyAdditionalInformation
        ob.territory = opportunity?.territory
        ob.product = opportunity?.product
        ob.period = opportunity?.period
        ob.startTime = opportunity?.startTime
        ob.endTime = opportunity?.endTime
        ob.interestNumberOfMonths = opportunity?.interestNumberOfMonths
        ob.productAccount = opportunity?.productAccount
        ob.serviceCharge = opportunity?.serviceCharge
        ob.externalId = opportunity?.externalId
        ob.notarizingStatus = opportunity?.notarizingStatus
        ob.mortgagingStatus = opportunity?.mortgagingStatus
        ob.advancePayment = opportunity?.advancePayment
        ob.loanApplicationProcessType = opportunity?.loanApplicationProcessType
        ob.monthOfAdvancePaymentOfInterest = opportunity?.monthOfAdvancePaymentOfInterest
        ob.actualLendingDate = opportunity?.actualLendingDate
        ob.estimatedLendingDate = opportunity?.estimatedLendingDate
        ob.isTest = opportunity?.isTest
        ob.actuaRepaymentDate = opportunity?.actuaRepaymentDate
        ob.contact = opportunity?.contact
        ob.user = opportunity?.user
        ob.modifiedBy = opportunity?.modifiedBy
        ob.account = opportunity?.account
        ob.parent = opportunity?.parent
        ob.lastAction = opportunity?.lastAction
        ob.mortgageReleasingType = opportunity?.mortgageReleasingType
        ob.importDate = opportunity?.importDate
        ob.externalModifiedDate = opportunity?.externalModifiedDate
        ob.dateOfMortgage = opportunity?.dateOfMortgage
        ob.dateOfNotarization = opportunity?.dateOfNotarization
        ob.complianceChecking = opportunity?.complianceChecking

        if (ob.validate())
        {
            ob.save flush: true
        }
        else
        {
            ob.errors.each {
                log.info "${it}"
            }
        }
    }

    def verifyProtectionTime(String idNumber)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        def opportunity = Opportunity.find("from Opportunity as c where c.idNumber = '" + idNumber + "' and c.stage " + "in ('03', '04', '05', '06', '07', '08', '09', '10', '11', '12') and c" + ".status = 'Pending' and c.protectionEndTime > '" + format.format(new Date()) + "'")
        if (opportunity)
        {
            true
        }
        else
        {
            false
        }
    }

    def getProtectDays(String cellphone, String spouseCellphone, String mortgagorCellphone, String
        mortgagorSpouseCellphone)
    {
        Integer protectDays = 10
        if (cellphone)
        {
            protectDays += 5
        }
        if (spouseCellphone)
        {
            protectDays += 5
        }
        if (mortgagorCellphone)
        {
            protectDays += 5
        }
        if (mortgagorSpouseCellphone)
        {
            protectDays += 5
        }
        return protectDays
    }

    def addContacts(String fullName, String idNumber, String cellphone, String maritalStatus, String type,
        Opportunity opportunity)
    {
        OpportunityContact opportunityContact = OpportunityContact.find("from OpportunityContact as o where o" + ".opportunity.id = ${opportunity.id} and o" + ".type.name = '${type}'")
        def contact
        if (opportunityContact)
        {
            contact = opportunityContact.contact
        }
        else
        {
            opportunityContact = new OpportunityContact()
            //contact避免重复添加
            if (type == "借款人")
            {
                contact = Contact.find("from Contact as c where c.idNumber = '${idNumber}' and c.type = 'Client'")

            }
            else
            {
                contact = Contact.find("from Contact as c where c.idNumber = '${idNumber}' and c.type is null")
            }
            if (!contact)
            {
                contact = new Contact()
                if (type == "借款人")
                {
                    contact.level = ContactLevel.find("from ContactLevel where name = 'B'")
                    contact.type = "Client"
                }
            }
        }
        contact.fullName = fullName
        contact.idNumber = idNumber
        contact.maritalStatus = maritalStatus
        contact.cellphone = cellphone
        contact.user = opportunity.user
        contact.account = Account.find("from Account as c where c.id = ${opportunity.account.id}")
        if (contact.validate())
        {
            contact.save flush: true
        }
        else
        {
            contact.errors.each {
                log.info "${it}"
            }
        }

        opportunityContact.contact = contact
        opportunityContact.opportunity = opportunity
        opportunityContact.type = OpportunityContactType.find("from OpportunityContactType where name = '${type}'")
        if (opportunityContact.validate())
        {
            opportunityContact.save flush: true
        }
        else
        {
            opportunityContact.errors.each {
                log.info "${it}"
            }
        }
    }

    def submit(Opportunity opportunity)
    {
        OpportunityFlow opportunityFlow
        opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ${opportunity?.id} and " + "stage.id = ${opportunity?.stage?.id}")
        if (opportunityFlow)
        {
            opportunityFlow.endTime = new Date()
            opportunityFlow.save flush: true
        }
        def executionSequence = opportunityFlow?.executionSequence
        opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ${opportunity?.id} and " + "executionSequence > ${executionSequence} order by executionSequence " + "ASC")
        if (opportunityFlow)
        {
            opportunityFlow.startTime = new Date()
            opportunity.stage = opportunityFlow?.stage
            if (opportunity.validate())
            {
                opportunity.save flush: true
                //activityService.makeActivity(opportunity)
            }
            else
            {
                opportunity.errors.each {
                    log.info "${it}"
                }
            }
        }
    }

    def getAccessToken()
    {
        //北京贷款中心
        // def APPID = "wxe49dcb507643c1cd"
        // def SECRET = "217c05ff855a665ef4e0f3db8f4c9371"

        // 中佳信中佳信
        def APPID = "wx464de39cbfe33d14"
        def SECRET = "1ea81262014312117b5d2fc44d95053c"

        def accessToken = ""
        try
        {
            URL url = new URL("https://api.weixin.qq" + ".com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET")
            def params = "APPID=" + URLEncoder.encode(APPID, 'UTF-8') + "&SECRET=" + URLEncoder.encode(SECRET, 'UTF-8')

            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.outputStream.withWriter { Writer writer -> writer.write params }
            def response = connection.inputStream.withReader { Reader reader -> reader.text }
            accessToken = JSON.parse(response).getAt("access_token")
            return accessToken
        }
        catch (Exception e)
        {
            log.info "${e}"
            return accessToken
        }
    }

    def getTicket(String accessToken)
    {
        def ticket = ""
        try
        {
            URL url = new URL("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi")
            def params = "ACCESS_TOKEN=" + URLEncoder.encode(accessToken, 'UTF-8')

            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.outputStream.withWriter { Writer writer -> writer.write params }
            def response = connection.inputStream.withReader { Reader reader -> reader.text }
            ticket = JSON.parse(response).getAt("ticket")
            return ticket
        }
        catch (Exception e)
        {
            log.info "${e}"
            return ticket
        }
    }

    def getSignature(String jsapi_ticket, String nonce_str, String timestamp, String url)
    {
        def string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url
        MessageDigest crypt = MessageDigest.getInstance("SHA-1")
        crypt.reset()
        crypt.update(string1.getBytes("UTF-8"))
        byteToHex(crypt.digest())
    }

    def byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter()
        for (
            byte b :
                hash)
        {
            formatter.format("%02x", b)
        }
        formatter.toString()
    }

    def uploadImage(String webrootDir, String accessToken, String media_id)
    {
        String requestUrl = "http://file.api.weixin.qq" + ".com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID"
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", media_id)
        URL url = new URL(requestUrl)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()
        conn.setDoInput(true)
        conn.setRequestMethod("GET")
        def fileType = conn.getHeaderField("Content-Type").split("/")[-1]

        if (conn.getResponseCode() == 200)
        {
            // 将图片临时存储到 images目录中
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream())
            File tempFile = new File("${webrootDir}images/wxTempFile.${fileType}")
            FileOutputStream fos = new FileOutputStream(tempFile)
            byte[] buf = new byte[1024 * 1024]
            int size = 0
            while ((size = bis.read(buf)) != -1)
            {
                fos.write(buf, 0, size)
            }

            // 将临时文件存储转成 Base64
            FileInputStream inputStream = new FileInputStream(tempFile)
            ByteArrayOutputStream output = new ByteArrayOutputStream()
            byte[] fileBytes = new byte[inputStream.available()]
            int len = 0
            while ((len = inputStream.read(fileBytes)) != -1)
            {
                output.write(fileBytes, 0, len);
            }
            def encoded = fileBytes.encodeBase64().toString()

            // 存储图片信息
            def fileName = fileServerService.upload(encoded, fileType)
            return "${fileName}.${fileType}"
        }
        else
        {
            return 0
        }
    }
}

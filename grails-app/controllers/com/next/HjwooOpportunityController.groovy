package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import groovy.sql.Sql
import org.springframework.security.access.annotation.Secured

/*@Transactional(readOnly = true)*/

class HjwooOpportunityController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def opportunityService
    def opportunityAppService
    //    def liquidityRiskReportService
    def opportunityNotificationService
    def contactService
    def propertyValuationProviderService
    def dataSource

    @Secured(['permitAll'])
    @Transactional
    def appQuery() {
        def offset = params['offset']?.toInteger()
        def sessionId = params["sessionId"]
    }

    @Secured(['permitAll'])
    @Transactional
    def appProjectTime() {
        def json = request.JSON
        println "************************* appProjectTime ***************************"
        println json

        def sessionId = json["sessionId"]
        String requestedAmount = json["requestedAmount"].toString()
        String loanDuration = json["loanDuration"]
        String fullName = json["fullName"]
        //旧版
        String idNumber = json["idNumber"]

        Contact contact = Contact.findByAppSessionId(sessionId)
        if (!sessionId) {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else if (!contact) {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (idNumber) {
            //旧版
            Boolean inProtectTime = opportunityService.verifyProtectionTime(idNumber)
            if (inProtectTime) {
                def errors = [errorCode: 4555, errorMessage: "借款人处于保护期，不允许报单"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            render status: 200
        } else {
            //新版
            if (!fullName || !(fullName.matches(/^[\u2190-\u9fff]{1,10}$|^[\dA-Za-z]{1,20}$/))) {
                def errors = [errorCode: 4209, errorMessage: "请输入正确的借款人姓名"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (!requestedAmount || !(requestedAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(requestedAmount) <= 0) {
                def errors = [errorCode: 4201, errorMessage: "请输入正确的贷款金额"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (!loanDuration || !(loanDuration.matches(/^[0-9]\d*$/)) || Integer.parseInt(loanDuration) <= 0) {
                def errors = [errorCode: 4203, errorMessage: "请输入正确的贷款期限"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (Opportunity.findByFullNameAndRequestedAmountAndLoanDurationAndStatusAndProtectionEndTimeGreaterThan(fullName, Double.parseDouble(requestedAmount), Integer.parseInt(loanDuration), 'Pending', new Date())) {
                def errors = [errorCode: 4555, errorMessage: "借款人处于保护期，不允许报单"]
                render JsonOutput.toJson(errors), status: 400
                return
            }

            render status: 200
        }

    }

    @Secured(['permitAll'])
    @Transactional
    def appCreate() {
        def json = request.JSON

        println "************************* appCreate ***************************"
        println json

        def sessionId = json["sessionId"]
        Contact contact = Contact.findByAppSessionId(sessionId)

        if (!sessionId) {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else if (!contact) {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def opportunity = new Opportunity()
        def collateral = new Collateral()
        def city = City.findByName(json["opportunity"]["city"]?.toString())
        collateral.area = Double.parseDouble(json["opportunity"]["area"]?.toString())
        collateral.specialFactors = json["opportunity"]["specialFactors"]?.toString()
        def unitPrice = Double.parseDouble(json["opportunity"]["unitPrice"]?.toString())
        if (unitPrice < 0) {
            opportunity.unitPrice = unitPrice * (-1)
            collateral.unitPrice = unitPrice * (-1)
        } else {
            opportunity.unitPrice = unitPrice
            collateral.unitPrice = unitPrice
        }
        collateral.orientation = json["opportunity"]["orientation"]
        collateral.roomNumber = json["opportunity"]["roomNumber"]
        collateral.city = city
        collateral.houseType = json["opportunity"]["houseType"]?.toString()
        collateral.building = json["opportunity"]["building"]
        def loanAmount = Double.parseDouble(json["opportunity"]["loanAmount"]?.toString())
        if (loanAmount < 0) {
            opportunity.loanAmount = loanAmount * (-1)
        } else {
            opportunity.loanAmount = loanAmount
        }
        /*if (json["opportunity"]["numberOfLivingRoom"])
        {
            opportunity.numberOfLivingRoom = Integer.parseInt(json["opportunity"]["numberOfLivingRoom"]?.toString())
        }*/
        collateral.district = json["opportunity"]["district"]
        collateral.address = json["opportunity"]["address"]
        collateral.totalFloor = json["opportunity"]["totalFloor"]
        collateral.floor = json["opportunity"]["floor"]

        def user = User.findByCellphone(contact?.userCode)
        if (!contact?.userCode || !user) {
            def errors = [errorCode: 4500, errorMessage: "邀请码异常，请联系管理员"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        opportunity.contact = contact
        /*if (!opportunity?.address)
        {
            opportunity.address = json["opportunity"]["district"]
        }
        if (!opportunity?.district)
        {
            opportunity.district = json["opportunity"]["address"]
        }*/
        if (city.name in ['南京', '苏州', '武汉', '上海']) {
            if (city.name == "南京") {
                if (opportunity.collateral.area >= 200 || opportunity.unitPrice >= 35000 || opportunity.loanAmount >= 500) {
                    opportunity.stage = OpportunityStage.findByCode("15")
                } else {
                    opportunity.stage = OpportunityStage.findByCode("02")
                }
            }
            if (city.name == "苏州") {
                if (opportunity.collateral.area >= 200 || opportunity.unitPrice >= 25000 || opportunity.loanAmount >= 400) {
                    opportunity.stage = OpportunityStage.findByCode("15")
                } else {
                    opportunity.stage = OpportunityStage.findByCode("02")
                }
            }
            if (city.name == "武汉") {
                if (opportunity.collateral.area >= 200 || opportunity.unitPrice >= 20000 || opportunity.loanAmount >= 300) {
                    opportunity.stage = OpportunityStage.findByCode("15")
                } else {
                    opportunity.stage = OpportunityStage.findByCode("02")
                }
            }
            if (city.name == "上海") {
                if (opportunity.collateral.area >= 200 || opportunity.unitPrice >= 72000 || opportunity.loanAmount >= 1000) {
                    opportunity.stage = OpportunityStage.findByCode("15")
                } else {
                    opportunity.stage = OpportunityStage.findByCode("02")
                }
            }
        } else {
            opportunity.stage = OpportunityStage.findByCode("02")
        }



        opportunity.user = user
        opportunity.account = user?.account
        if (opportunity.validate()) {
            opportunity.save flush: true
            collateral.opportunity = opportunity
            collateral.status = json["opportunity"]["status"]
            collateral.externalId = json["opportunity"]["externalId"]
            collateral.projectName = json["opportunity"]["projectName"]
            //collateral.projectName=json["opportunity"]["assetType"]待定
            collateral.assetType = "住宅"
            if (collateral.validate()) {
                collateral.save flush: true

            } else {
                println collateral.errors
            }

            //订单初始化
            opportunityService.initOpportunity(opportunity)
            println "######新建的opportunity.id###########"
            println opportunity.id
            render opportunity as JSON
            return
        } else {
            def errors = [errorCode: 9000, errorMessage: opportunity.errors]
            render JsonOutput.toJson(errors), status: 400
        }
    }

    @Secured(['permitAll'])
    /*@Transactional*/
    def appCreateOpportunity() {
        def json = request.JSON
        println "************************* appCreateOpportunity ***************************"
        println json

        def sessionId = json["sessionId"]
        Contact contact = Contact.findByAppSessionId(sessionId)
        if (!sessionId) {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else if (!contact) {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        Opportunity opportunity = new Opportunity()
        opportunity.status = "Pending"
        opportunity.stage = OpportunityStage.findByCode("02")
        opportunity.account = contact?.user?.account
        opportunity.user = contact?.user
        opportunity.loanApplicationProcessType = LoanApplicationProcessType.findByName("先评房再报单")

        if (opportunity.validate()) {
            opportunity.save flush: true
        } else {
            pritnln opportunity.errors
        }

        render opportunity as JSON
        return
    }

    @Secured(['permitAll'])
    /*@Transactional*/
    def appSubmit() {
        def json = request.JSON
        println "************************* appSubmit ***************************"
        println json

        String sessionId = json["sessionId"]
        Contact contact = Contact.findByAppSessionId(sessionId)
        if (!sessionId) {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else if (!contact) {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        String serialNumber = json["opportunity"]["serialNumber"]
        String requestedAmount = json["opportunity"]["requestedAmount"].toString()
        String loanDuration = json["opportunity"]["loanDuration"]
        String mortgageType = json["opportunity"]["mortgageType"]
        String firstMortgageAmount = json["opportunity"]["firstMortgageAmount"]?.toString()
        String secondMortgageAmount = json["opportunity"]["secondMortgageAmount"]?.toString()
        String typeOfFirstMortgage = json["opportunity"]["typeOfFirstMortgage"]
        String fullName = json["opportunity"]["fullName"]
        String maritalStatus = json["opportunity"]["maritalStatus"]
        String mortgageStatus = json["opportunity"]["mortgageStatus"]
        String cellphone = json["opportunity"]["cellphone"]
        //兼容
        def contacts = json["opportunity"]["contacts"]

        Integer protectDays = 10
        if (cellphone) {
            protectDays += 5
            if (!(cellphone.matches(/^1\d{10}$/))) {
                def errors = [errorCode: 4216, errorMessage: "借款人手机号格式不正确，请重新输入"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        Opportunity opportunity = Opportunity.findBySerialNumber(serialNumber)
        if (!opportunity || !serialNumber) {
            def errors = [errorCode: 4200, errorMessage: "请选择房源"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!requestedAmount || !(requestedAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(requestedAmount) <= 0) {
            println "333"
            def errors = [errorCode: 4201, errorMessage: "请输入正确的贷款金额"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!loanDuration || !(loanDuration.matches(/^[0-9]\d*$/)) || Integer.parseInt(loanDuration) <= 0) {
            def errors = [errorCode: 4203, errorMessage: "请输入正确的贷款期限"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!mortgageType || !(mortgageType in ["一抵", "二抵", "一抵转单", "二抵转单"])) {
            def errors = [errorCode: 4205, errorMessage: "请选择抵押类型"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (mortgageType == "一抵") {
            firstMortgageAmount = "0"
            secondMortgageAmount = "0"
        }
        if ((mortgageType in ["二抵", "一抵转单"])) {
            if (!firstMortgageAmount || !(firstMortgageAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(firstMortgageAmount) <= 0) {
                def errors = [errorCode: 4206, errorMessage: "请输入正确的一抵金额"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (!typeOfFirstMortgage || !(typeOfFirstMortgage in ["银行类", "非银行类"])) {
                def errors = [errorCode: 4207, errorMessage: "请选择一抵类型"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            secondMortgageAmount = "0"
        }
        if (mortgageType == "二抵转单") {
            if (!firstMortgageAmount || !(firstMortgageAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(firstMortgageAmount) <= 0) {
                def errors = [errorCode: 4206, errorMessage: "请输入正确的一抵金额"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (!typeOfFirstMortgage || !(typeOfFirstMortgage in ["银行类", "非银行类"])) {
                def errors = [errorCode: 4207, errorMessage: "请选择一抵类型"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (!secondMortgageAmount || !(secondMortgageAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(secondMortgageAmount) <= 0) {
                def errors = [errorCode: 4208, errorMessage: "请输入正确的二抵金额"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        if (!fullName || !(fullName.matches(/^[\u2190-\u9fff]{1,10}$|^[\dA-Za-z]{1,20}$/))) {
            def errors = [errorCode: 4209, errorMessage: "请输入正确的借款人姓名"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!maritalStatus || !(maritalStatus in ["未婚", "已婚", "再婚", "离异", "丧偶"])) {
            println "ddd"
            def errors = [errorCode: 4212, errorMessage: "请选择借款人婚姻状况"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        boolean contacts_flag = true
        println "debug1:" + contacts_flag
        if (contacts) {
            println "debug2"
            for (
                    it in
                            contacts) {
                if (it['idNumber']) {
                    if (it["type"] && (it["type"] in ["借款人配偶", "抵押人", "抵押人配偶"])) {
                        if (!it["idNumber"] || !contactService.verifyIdNumber(it["idNumber"])) {
                            def errors = [errorCode: 4215, errorMessage: "请输入正确的${it["type"]}身份证号"]
                            render JsonOutput.toJson(errors), status: 400
                            contacts_flag = false
                            return
                            break
                        } else if (!it["fullName"] || !(it["fullName"].matches(/^[\u2190-\u9fff]{1,10}$|^[\dA-Za-z]{1,20}$/))) {
                            def errors = [errorCode: 4219, errorMessage: "请输入正确的${it["type"]}姓名"]
                            render JsonOutput.toJson(errors), status: 400
                            contacts_flag = false
                            return
                            break
                        } else if (it["cellphone"]) {
                            if (!(it["cellphone"].matches(/^1\d{10}$/))) {
                                def errors = [errorCode: 4216, errorMessage: "${it["type"]}手机号格式不正确，请重新输入"]
                                render JsonOutput.toJson(errors), status: 400
                                contacts_flag = false
                                return
                                break
                            } else {
                                protectDays += 5
                            }
                        }
                    } else {
                        def errors = [errorCode: 4218, errorMessage: "请输入正确的联系人类型"]
                        render JsonOutput.toJson(errors), status: 400
                        contacts_flag = false
                        return
                        break
                    }
                }

            }
        }
        println "bug:" + contacts_flag
        if (contacts_flag) {
            println "debug3"
            //兼容
            if (contacts) {
                for (
                        it in
                                contacts) {
                    if (it['idNumber']) {
                        def contactFullName = it["fullName"]
                        def contactIdNumber = it["idNumber"]
                        def contactCellphone = it["cellphone"]
                        def contactMaritalStatus = "已婚"
                        if (it["mortgagorMaritalStatus"]) {
                            contactMaritalStatus = it["mortgagorMaritalStatus"]
                        }
                        def type = it["type"]
                        if (contactIdNumber) {
                            opportunityService.addContacts(contactFullName, contactIdNumber, contactCellphone, contactMaritalStatus, type, opportunity)
                        }
                        continue;
                    }

                }
            }

            if (Opportunity.findByFullNameAndRequestedAmountAndLoanDurationAndStatusAndProtectionEndTimeGreaterThan(fullName, Double.parseDouble(requestedAmount), Integer.parseInt(loanDuration), 'Pending', new Date())) {
                println "debug5"
                def errors = [errorCode: 4220, errorMessage: "借款人处于保护期，不可以报单"]
                render JsonOutput.toJson(errors), status: 400
            } else {
                println "debug6"
                Contact loanContact = new Contact()
                loanContact.fullName = fullName
                loanContact.maritalStatus = maritalStatus
                loanContact.cellphone = cellphone
                loanContact.user = contact?.user
                loanContact.account = contact?.account
                loanContact.level = ContactLevel.findByName("待评级")
                loanContact.type = "Client"
                //旧版
                if (json["opportunity"]["idNumber"]) {
                    loanContact.idNumber = json["opportunity"]["idNumber"]
                }
                loanContact.save flush: true

                Contact mortgageContact = null
                if (mortgageStatus) {
                    mortgageContact = new Contact()
                    mortgageContact.fullName = "抵押人姓名"
                    mortgageContact.maritalStatus = mortgageStatus
                    mortgageContact.user = contact?.user
                    mortgageContact.account = contact?.account
                    mortgageContact.save flush: true
                }

                opportunity.requestedAmount = Double.parseDouble(requestedAmount)
                opportunity.loanDuration = Integer.parseInt(loanDuration)
                //实际贷款期限取默认值为拟贷款期限
                opportunity.actualLoanDuration = Integer.parseInt(loanDuration)

                opportunity.mortgageType = MortgageType.findByName(mortgageType)
                opportunity.firstMortgageAmount = Double.parseDouble(firstMortgageAmount)
                opportunity.typeOfFirstMortgage = TypeOfFirstMortgage.findByName(typeOfFirstMortgage)
                opportunity.secondMortgageAmount = Double.parseDouble(secondMortgageAmount)

                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")
                println "collaterals" + collaterals.size()
                if (collaterals?.size() == 1) {
                    def collateral = Collateral.findByOpportunity(opportunity)
                    collateral.firstMortgageAmount = Double.parseDouble(firstMortgageAmount)
                    collateral.secondMortgageAmount = Double.parseDouble(secondMortgageAmount)
                    collateral.mortgageType = MortgageType.findByName(mortgageType)
                    collateral.typeOfFirstMortgage = TypeOfFirstMortgage.findByName(typeOfFirstMortgage)
                    collateral.save flush: true
                }

                opportunity.fullName = fullName
                //旧版
                if (json["opportunity"]["idNumber"]) {
                    opportunity.idNumber = json["opportunity"]["idNumber"]
                }
                opportunity.contact = contact
                opportunity.lender = loanContact
                opportunity.maritalStatus = maritalStatus
                opportunity.stage = OpportunityStage.findByCode("04")
                Date protectionStartTime = new java.util.Date()
                opportunity.protectionStartTime = protectionStartTime
                opportunity.protectionEndTime = protectionStartTime + protectDays
                //                opportunity.createdDate = new Date()
                //                opportunity.modifiedDate = new Date()
                if (opportunity.validate()) {
                    opportunity.save flush: true
                } else {
                    opportunity.errors.each {
                        println it
                    }
                }

                OpportunityContact loanOpportunityContact = new OpportunityContact()
                loanOpportunityContact.opportunity = opportunity
                loanOpportunityContact.contact = loanContact
                loanOpportunityContact.type = OpportunityContactType.findByName("借款人")
                loanOpportunityContact.save flush: true

                if (mortgageContact) {
                    OpportunityContact mortgageOpportunityContact = new OpportunityContact()
                    mortgageOpportunityContact.opportunity = opportunity
                    mortgageOpportunityContact.contact = mortgageContact
                    mortgageOpportunityContact.type = OpportunityContactType.findByName("抵押人")
                    mortgageOpportunityContact.save flush: true
                }

                //订单初始化
                //                opportunityService.initOpportunity(opportunity)
                opportunityAppService.initOpportunity(opportunity)


                if (collaterals?.size() == 0) {
                    println "********** 极速询值 appQuery3 *************"
                    def city = "北京"

                    println "######新建房产信息###########"
                    // def collateral = Collateral.findByOpportunity(opportunity)
                    def collateral = new Collateral()
                    collateral.opportunity = opportunity
                    collateral.requestStartTime = new Date()
                    collateral.city = City.findByName(city)
                    collateral.district = "未知"
                    collateral.projectName = "未知"
                    collateral.building = 0
                    collateral.orientation = "未知"
                    collateral.unit = "未知"
                    collateral.atticArea = 0
                    collateral.floor = 0
                    collateral.roomNumber = "未知"
                    collateral.totalFloor = 0
                    collateral.area = 0
                    collateral.address = "未知"
                    collateral.houseType = "未知"
                    collateral.assetType = "未知"
                    collateral.specialFactors = "未知"
                    collateral.appliedTotalPrice = 0
                    collateral.opportunity = opportunity

                    def result = propertyValuationProviderService.createCollateral(collateral)
                    println "result:" + result
                    if (result) {
                        def externalId = result['externalId']?.toString()
                        // collateral.opportunity = opportunity
                        collateral.unitPrice = 0
                        collateral.totalPrice = 0
                        collateral.externalId = externalId
                        collateral.status = "Pending"

                        if (collateral.validate()) {
                            collateral.save flush: true
                        } else {
                            println collateral.errors
                        }

                        def result1 = [:]
                        result1['reasonOfPriceAdjustment'] = "unknown"
                        result1['appliedTotalPrice'] = 0

                        def ats = []
                        def attachmentsList = Attachments.findAllByOpportunityAndType(opportunity, AttachmentType.findByName('房产证'))
                        attachmentsList?.each {
                            def simple = [:]
                            simple['fileName'] = it?.fileUrl
                            simple['attachmentType'] = "房产证"
                            ats.add(simple)
                        }
                        result1['attachments'] = ats
                        result1['externalId'] = collateral.externalId

                        def resultJson = ""
                        println "result1:" + result1
                        String param = result1 as JSON
                        println param
                        resultJson = propertyValuationProviderService.suggestSubmmit1(param)

                        if (!resultJson) {
                            def errors = [errorCode: 4796, errorMessage: "调用评房反馈服务上传图片失败，请稍后重试"]
                            render JsonOutput.toJson(errors), status: 400
                        }
                    } else {
                        def errors = [errorCode: 4604, errorMessage: "网络不稳定，请稍后重试"]
                        render JsonOutput.toJson(errors), status: 400
                    }
                    render opportunity as JSON
                    return
                } else {
                    //A类重评
                    def collateral = Collateral.findByOpportunity(opportunity)
                    // def pvJson = propertyValuationProviderService.queryByExternalId(collateral?.externalId)
                    // def valuationReliability = pvJson['valuationReliability']
                    // if (valuationReliability)
                    // {
                    //     if (valuationReliability == 'A')
                    //     {
                    //         def result = propertyValuationProviderService.createCollateral1(collateral)
                    //         if (result)
                    //         {
                    //             collateral.externalId = result['externalId']?.toString()
                    //             collateral.status = result['status']?.toString()
                    //             collateral.save()
                    //             def ats = []
                    //             def result1 = [:]
                    //             def attachmentsList = Attachments.findAllByOpportunityAndType(opportunity, AttachmentType.findByName('房产证'))
                    //             attachmentsList?.each {
                    //                 def simple = [:]
                    //                 simple['fileName'] = it?.fileUrl
                    //                 simple['attachmentType'] = "房产证"
                    //                 ats.add(simple)
                    //             }
                    //             result1['attachments'] = ats
                    //             result1['externalId'] = collateral.externalId
                    //
                    //             def resultJson = ""
                    //             println "result1:" + result1
                    //             String param = result1 as JSON
                    //             println param
                    //             resultJson = propertyValuationProviderService.suggestSubmmit1(param)
                    //
                    //             if (!resultJson)
                    //             {
                    //                 def errors = [errorCode: 4796, errorMessage: "调用评房反馈服务上传图片失败，请稍后重试"]
                    //                 render JsonOutput.toJson(errors), status: 400
                    //             }
                    //         }
                    //         else
                    //         {
                    //             def errors = [errorCode: 4604, errorMessage: "网络不稳定，请稍后重试"]
                    //             render JsonOutput.toJson(errors), status: 400
                    //         }
                    //     }
                    // }
                    // else
                    // {
                    //   def errors = [errorCode: 4604, errorMessage: "网络不稳定，请稍后重试"]
                    //   render JsonOutput.toJson(errors), status: 400
                    // }
                    def result1 = [:]
                    // result1['reasonOfPriceAdjustment'] = "unknown"
                    // result1['appliedTotalPrice'] = 0

                    def ats = []
                    def attachmentsList = Attachments.findAllByOpportunityAndType(opportunity, AttachmentType.findByName('房产证'))
                    attachmentsList?.each {
                        def simple = [:]
                        simple['fileName'] = it?.fileUrl
                        simple['attachmentType'] = "产证照片"
                        ats.add(simple)
                    }
                    result1['attachments'] = ats
                    result1['externalId'] = collateral.externalId

                    def resultJson = ""
                    println "result1:" + result1
                    String param = result1 as JSON
                    println param
                    resultJson = propertyValuationProviderService.suggestSubmmit2(param)

                    if (!resultJson) {
                        def errors = [errorCode: 4796, errorMessage: "调用评房反馈服务上传图片失败，请稍后重试"]
                        render JsonOutput.toJson(errors), status: 400
                    }

                    render opportunity as JSON
                    return
                }
            }

        } else {
            println "debug4"
            def errors = [errorCode: 4218, errorMessage: "请输入正确的信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
    }


    @Secured(['permitAll'])
    /*@Transactional*/
    def appQueryByStageAndStatus()
    {
        def json = request.JSON
        println "************************* appQueryByStageAndStatus ***************************"
        println json

        def max = 10
        def stages = json["stages"]
        def status = json["status"]
        def offset = json['offset']?.toInteger()
        String sessionId = json["sessionId"]
        Contact contact = Contact.findByAppSessionId(sessionId)
        if (!sessionId)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else if (!contact)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!status || status.size() <= 0)
        {
            def errors = [errorCode: 4262, errorMessage: "请输入订单结果"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //        if (offset == null)
        //        {
        //            def errors = [errorCode: 5261, errorMessage: "分页数缺失"]
        //            render JsonOutput.toJson(errors), status: 400
        //            return
        //        }
        def stageIdString = ""
        if (!stages || stages.size() <= 0)
        {
            def errors = [errorCode: 4260, errorMessage: "请输入要查询的订单状态"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            stages.each {
                if (!(it == '抵押公证手续已完成'))
                {
                    def stage = OpportunityStage.findByName(it)
                    if (!stage)
                    {
                        def errors = [errorCode: 4261, errorMessage: "无效的订单状态：'${it}'"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                    }
                }
            }
        }

        def list = []
        def statusList = []
        def opportunity = Opportunity.createCriteria()
        def opportunityList
        stages.each {
            if (it == '抵押公证手续已完成')
            {
                list.add(OpportunityStage.findByName('抵押已完成'))
                list.add(OpportunityStage.findByName('公证已完成'))
                stageIdString += OpportunityStage.findByName('抵押已完成')?.id + ","
                stageIdString += OpportunityStage.findByName('公证已完成')?.id + ","
            }
            else
            {
                list.add(OpportunityStage.findByName(it))
                stageIdString += OpportunityStage.findByName(it)?.id + ","
            }
        }
        def statusString = ""
        status.each {
            if (it == "成功")
            {
                statusList.add("Pending")
                statusList.add("Completed")
                statusString += "'Pending','Completed'" + ","
            }
            else if (it == "失败")
            {
                statusList.add("Failed")
                statusString += "'Failed'" + ","
            }
        }
        if (offset == null)
        {
            if (statusList.size() == 1)
            {
                opportunityList = opportunity.list {
                    eq("contact", contact)
                    and {
                        'in'("status", statusList)
                    }
                    order("modifiedDate", "desc")
                }
            }
            else
            {
                opportunityList = opportunity.list {
                    eq("contact", contact)
                    and {
                        'in'("stage", list)
                        'in'("status", statusList)
                    }
                    order("modifiedDate", "desc")
                }
            }
        }
        else
        {
            offset *= 10
            String sql = " from Opportunity as o where 1=1 "
            sql += " and o.contact.id = ${contact.id} "
            statusString = statusString.substring(0, statusString.length() - 1)
            sql += " and o.status in (${statusString}) "
            if (!(statusString == "'Failed'"))
            {
                stageIdString = stageIdString.substring(0, stageIdString.length() - 1)
                sql += " and o.stage.id in (${stageIdString}) "
            }
            sql += " order by modifiedDate desc "
            println "sql:" + sql
            opportunityList = Opportunity.findAll(sql, [max: max, offset: offset])
        }
        println opportunityList.size()
        render opportunityList as JSON
        return
    }

    @Secured(['permitAll'])
    def appQueryByStageCategoryAndStatus()
    {
        def json = request.JSON
        println "************************* appQueryByStageCategoryAndStatus ***************************"
        println json

        def max = 10
        def stageCategory = json["stageCategory"]
        def status = json["status"]
        def offset = json['offset']?.toInteger()
        String sessionId = json["sessionId"]
        Contact contact = Contact.findByAppSessionId(sessionId)
        if (!sessionId)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else if (!contact)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!status || status.size() <= 0)
        {
            def errors = [errorCode: 4262, errorMessage: "请输入订单结果"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!stageCategory)
        {
            def errors = [errorCode: 4388, errorMessage: "请输入订单类别"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def stageList
        def opportunityStageCategory
        if (stageCategory == '全部')
        {
            stageList = OpportunityStage.list()
        }
        else
        {
            opportunityStageCategory = OpportunityStageCategory.findByNameAndActive(stageCategory, true)
            if (!opportunityStageCategory)
            {
                def errors = [errorCode: 4389, errorMessage: "请输入正确的订单类别"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            stageList = OpportunityStage.findAllByCategory(opportunityStageCategory)
        }

        def statusList = []
        def stageIdString = ""
        def statusString = ""
        def opportunity = Opportunity.createCriteria()
        def opportunityList

        stageList?.each {
            stageIdString += it?.id + ","
        }

        status.each {
            if (it == '全部')
            {
                statusList.add("Pending")
                statusList.add("Completed")
                statusList.add("Failed")
                statusString += "'Pending','Completed','Failed'" + ","
            }
            else if (it == "成功")
            {
                statusList.add("Pending")
                statusList.add("Completed")
                statusString += "'Pending','Completed'" + ","
            }
            else if (it == "失败")
            {
                statusList.add("Failed")
                statusString += "'Failed'" + ","
            }
        }

        if (stageList?.size() > 0)
        {
            if (offset == null)
            {
                if (statusList.size() == 1)
                {
                    opportunityList = opportunity.list {
                        eq("contact", contact)
                        and {
                            'in'("status", statusList)
                        }
                        order("modifiedDate", "desc")
                    }
                }
                else
                {
                    opportunityList = opportunity.list {
                        eq("contact", contact)
                        and {
                            'in'("stage", stageList)
                            'in'("status", statusList)
                        }
                        order("modifiedDate", "desc")
                    }
                }
            }
            else
            {
                offset *= 10
                String sql = " from Opportunity as o where 1=1 "
                sql += " and o.contact.id = ${contact.id} "
                statusString = statusString.substring(0, statusString.length() - 1)
                sql += " and o.status in (${statusString}) "
                if (!(statusString == "'Failed'"))
                {
                    stageIdString = stageIdString.substring(0, stageIdString.length() - 1)
                    sql += " and o.stage.id in (${stageIdString}) "
                }
                sql += " order by modifiedDate desc "
                println "sql:" + sql
                opportunityList = Opportunity.findAll(sql, [max: max, offset: offset])
            }
        }

        println opportunityList?.size()
        render opportunityList as JSON
        return
    }
    @Secured(['permitAll'])
    @Transactional
    def appQueryByStageAndStatusNew() {
        def json = request.JSON
        println "************************* appQueryByStageAndStatus ***************************"
        println json

        def max = 10
        def stages = json["stages"]
        def status = json["status"]
        def offset = json['offset']?.toInteger()
        String sessionId = json["sessionId"]
        String contact = Contact.executeQuery("select id from Contact where appSessionId = '${sessionId}' ")[0]
        if (!sessionId) {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else if (!contact) {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!status || status.size() <= 0) {
            def errors = [errorCode: 4262, errorMessage: "请输入订单结果"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def stageIdString = ""
        if (!stages || stages.size() <= 0) {
            def errors = [errorCode: 4260, errorMessage: "请输入要查询的订单状态"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else {
            stages.each {
                if (!(it == '抵押公证手续已完成')) {
                    def stage = OpportunityStage.findByName(it)
                    if (!stage) {
                        def errors = [errorCode: 4261, errorMessage: "无效的订单状态：'${it}'"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                    }
                }
            }
        }
        def opportunityIdList
        stages.each {
            if (it == '抵押公证手续已完成') {
                stageIdString += OpportunityStage.executeQuery("select id from OpportunityStage where name = '抵押已完成' ")[0] + ","
                stageIdString += OpportunityStage.executeQuery("select id from OpportunityStage where name = '公证已完成' ")[0] + ","
            } else {
                stageIdString += OpportunityStage.executeQuery("select id from OpportunityStage where name = '${it}' ")[0] + ","
            }
        }

        def statusString = ""
        status.each {
            if (it == "成功") {
                statusString += "'Pending','Completed'" + ","
            } else if (it == "失败") {
                statusString += "'Failed'" + ","
            }
        }
        statusString = statusString.substring(0, statusString.length() - 1)
        stageIdString = stageIdString.substring(0, stageIdString.length() - 1)
        def sql = "select id from Opportunity as o where  o.contact.id = ${contact}  and o.status in (${statusString}) "

        if (statusString != 'Failed') {
            sql += " and o.stage.id in (${stageIdString}) "
        }
        sql += " order by createdDate desc "
        println "sql:" + sql
        if (offset != null) {
            opportunityIdList = Opportunity.executeQuery(sql, [max: max, offset: offset *= 10])
        } else {
            opportunityIdList = Opportunity.executeQuery(sql)
        }
//        def opportunityIdList = [103364]
        def opportunityList = []
        def db = new Sql(dataSource)
        opportunityIdList.each {
            def oppMap = [:]
            def opportunityBase = db.rows("SELECT  stage_id,lender_id, loan_amount, interest_payment_method_id, actual_amount_of_credit, cause_of_failure_id, status, serial_number, created_date,requested_amount,loan_duration,  mortgage_type_id,   full_name,  id_number,  marital_status, protection_start_time,  protection_end_time,  id ,valuation_type from opportunity where id = ?", [it])[0]
            oppMap.put("loanAmount",opportunityBase[2])
            oppMap.put("interestPaymentMethod",["name":InterestPaymentMethod.findById(opportunityBase[3])?.name])
            oppMap.put("actualAmountOfCredit",opportunityBase[4])
            oppMap.put("causeOfFailure",["name":CauseOfFailure.findById(opportunityBase[5])]?.name)
            oppMap.put("status",opportunityBase[6])
            oppMap.put("serialNumber",opportunityBase[7])
            oppMap.put("createdDate",opportunityBase[8])
            oppMap.put("requestedAmount",opportunityBase[9])
            oppMap.put("loanDuration",opportunityBase[10])
            oppMap.put("mortgageType",["name":MortgageType.findById(opportunityBase[11])?.name])
            oppMap.put("fullName",opportunityBase[12])
            oppMap.put("idNumber",opportunityBase[13])
            oppMap.put("maritalStatus",opportunityBase[14])
            oppMap.put("protectionStartTime",opportunityBase[15])
            oppMap.put("protectionEndTime",opportunityBase[16])
            oppMap.put("id",opportunityBase[17])
            oppMap.put("valuationType",opportunityBase[18])

            def collaterals = db.rows("SELECT city_id,reason_of_price_adjustment,id,address,project_name,building,room_number,orientation,area,house_type,special_factors,status ,total_price  from collateral WHERE opportunity_id = ? ", [it])[0]
            def map1=[:]
            def collateralsList = []
            def map = [:]
            if(collaterals)
            {
                def cityName = City.findById(collaterals[0])?.name
                map.put("name", cityName)
                map1.put("city",map)
                map1.put("reasonOfPriceAdjustment",collaterals[1])
                map1.put("id",collaterals[2])
                map1.put("address",collaterals[3])
                map1.put("projectName",collaterals[4])
                map1.put("building",collaterals[5])
                map1.put("roomNumber",collaterals[6])
                map1.put("orientation",collaterals[7])
                map1.put("area",collaterals[8])
                map1.put("houseType",collaterals[9])
                map1.put("specialFactors",collaterals[10])
                map1.put("status",collaterals[11])
                map1.put("totalPrice",collaterals[12])
            }

            collateralsList.add(map1)
            oppMap.put("collaterals",collateralsList)

            def opportunityContacts = db.rows("SELECT type_id,contact_id from opportunity_contact where opportunity_id = ? ", [it])
            def opportunityContactsList = []
            opportunityContacts.each {
                def contactType = db.rows("SELECT name from opportunity_contact_type where id = ? ", [it[0]])[0]
                def contacts = db.rows("SELECT full_name,id_number,marital_status FROM  contact WHERE id = ?", [it[1]])[0]
                map = [:]
                map1= [:]
                def map2 = [:]
                map1.put("fullName",contacts[0])
                map1.put("idNumber",contacts[1])
                map1.put("maritalStatus",contacts[2])
                map.put("name",contactType[0])
                map2.put("contact",map1)
                map2.put("type",map)
                opportunityContactsList.add(map2)
            }
            oppMap.put("contacts",opportunityContactsList)

            def stage = db.rows("SELECT name,code,category_id from opportunity_stage where id = ?", [opportunityBase[0]])[0]
            def categeName = db.rows("SELECT name from opportunity_stage_category WHERE id = ?", [stage[2]])[0]
            map = [:]
            map1= [:]
            map.put("name",categeName[0])
            map1.put("category",map)
            map1.put("name",stage[0])
            map1.put("code",stage[1])
            oppMap.put("stage",map1)

            def lenderId = db.rows("SELECT level_id from contact WHERE id = ? ", [opportunityBase[1]])[0]
            map = [:]
            map1 = [:]
            if(lenderId)
            {
                def level = db.rows("SELECT name,description FROM contact_level WHERE id = ? ", [lenderId[0]])[0]
                if(level)
                {
                    map.put("name",level[0])
                    map.put("description",level[1])
                }

            }

            map1.put("level",map)
            oppMap.put("lender",map1)
            //身份证
            def attachments = db.rows("SELECT file_name,file_url FROM attachments WHERE opportunity_id = ? and type_id = 1", [it])
            def attachmentsList = []
            attachments.each {
                map = [:]
                map.put("fileName",it[0])
                map.put("fileUrl",it[1])
                map.put("type",["name":"房产证"])
                attachmentsList.add(map)
            }
            oppMap.put("attachments",attachmentsList)
            opportunityList.add(oppMap)
        }
        render opportunityList as JSON
        return
    }

    @Secured(['permitAll'])
    def appQueryByStageCategoryAndStatusNew() {
        def json = request.JSON
        println "************************* appQueryByStageCategoryAndStatus ***************************"
        println json

        def max = 10
        def stageCategory = json["stageCategory"]
        def status = json["status"]
        def offset = json['offset']?.toInteger()
        String sessionId = json["sessionId"]
        String contact = Contact.executeQuery("select id from Contact where appSessionId = '${sessionId}' ")[0]
        if (!sessionId) {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else if (!contact) {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!status || status.size() <= 0) {
            def errors = [errorCode: 4262, errorMessage: "请输入订单结果"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!stageCategory) {
            def errors = [errorCode: 4388, errorMessage: "请输入订单类别"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def stageList
        def opportunityStageCategory
        if (stageCategory == '全部') {
            stageList = OpportunityStage.executeQuery("select id from OpportunityStage")
        } else {
            opportunityStageCategory = OpportunityStageCategory.findByNameAndActive(stageCategory, true)
            if (!opportunityStageCategory) {
                def errors = [errorCode: 4389, errorMessage: "请输入正确的订单类别"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            stageList = OpportunityStage.executeQuery("select id from OpportunityStage where category.id = ${opportunityStageCategory.id}")
        }

        def stageIdString = ""
        def statusString = ""
        def opportunityIdList

        stageList?.each {
            stageIdString += it + ","
        }

        status.each {
            if (it == '全部') {
                statusString += "'Pending','Completed','Failed'" + ","
            } else if (it == "成功") {
                statusString += "'Pending','Completed'" + ","
            } else if (it == "失败") {
                statusString += "'Failed'" + ","
            }
        }

        statusString = statusString.substring(0, statusString.length() - 1)
        stageIdString = stageIdString.substring(0, stageIdString.length() - 1)
        def sql = "select id from Opportunity as o where  o.contact.id = ${contact}  and o.status in (${statusString}) "

        if (statusString != 'Failed') {
            sql += " and o.stage.id in (${stageIdString}) "
        }
        sql += " order by createdDate desc "
        println "sql:" + sql
        if (offset != null) {
            opportunityIdList = Opportunity.executeQuery(sql, [max: max, offset: offset *= 10])
        } else {
            opportunityIdList = Opportunity.executeQuery(sql)
        }


        //拼数据
        def opportunityList = []
        def db = new Sql(dataSource)
        opportunityIdList.each {
            def oppMap = [:]
            def opportunityBase = db.rows("SELECT  stage_id,lender_id, loan_amount, interest_payment_method_id, actual_amount_of_credit, cause_of_failure_id, status, serial_number, created_date,requested_amount,loan_duration,  mortgage_type_id,   full_name,  id_number,  marital_status, protection_start_time,  protection_end_time,  id  , valuation_type from opportunity where id = ?", [it])[0]
            oppMap.put("loanAmount",opportunityBase[2])
            oppMap.put("interestPaymentMethod",["name":InterestPaymentMethod.findById(opportunityBase[3])?.name])
            oppMap.put("actualAmountOfCredit",opportunityBase[4])
            oppMap.put("causeOfFailure",["name":CauseOfFailure.findById(opportunityBase[5])]?.name)
            oppMap.put("status",opportunityBase[6])
            oppMap.put("serialNumber",opportunityBase[7])
            oppMap.put("createdDate",opportunityBase[8])
            oppMap.put("requestedAmount",opportunityBase[9])
            oppMap.put("loanDuration",opportunityBase[10])
            oppMap.put("mortgageType",["name":MortgageType.findById(opportunityBase[11])?.name])
            oppMap.put("fullName",opportunityBase[12])
            oppMap.put("idNumber",opportunityBase[13])
            oppMap.put("maritalStatus",opportunityBase[14])
            oppMap.put("protectionStartTime",opportunityBase[15])
            oppMap.put("protectionEndTime",opportunityBase[16])
            oppMap.put("id",opportunityBase[17])
            oppMap.put("valuationType",opportunityBase[18])

            def collaterals = db.rows("SELECT city_id,reason_of_price_adjustment,id,address,project_name,building,room_number,orientation,area,house_type,special_factors,status ,total_price  from collateral WHERE opportunity_id = ? ", [it])[0]
            def map1=[:]
            def collateralsList = []
            def map = [:]
            if(collaterals)
            {
                def cityName = City.findById(collaterals[0])?.name
                map.put("name", cityName)
                map1.put("city",map)
                map1.put("reasonOfPriceAdjustment",collaterals[1])
                map1.put("id",collaterals[2])
                map1.put("address",collaterals[3])
                map1.put("projectName",collaterals[4])
                map1.put("building",collaterals[5])
                map1.put("roomNumber",collaterals[6])
                map1.put("orientation",collaterals[7])
                map1.put("area",collaterals[8])
                map1.put("houseType",collaterals[9])
                map1.put("specialFactors",collaterals[10])
                map1.put("status",collaterals[11])
                map1.put("totalPrice",collaterals[12])
            }

            collateralsList.add(map1)
            oppMap.put("collaterals",collateralsList)

            def opportunityContacts = db.rows("SELECT type_id,contact_id from opportunity_contact where opportunity_id = ? ", [it])
            def opportunityContactsList = []
            opportunityContacts.each {
                def contactType = db.rows("SELECT name from opportunity_contact_type where id = ? ", [it[0]])[0]
                def contacts = db.rows("SELECT full_name,id_number,marital_status FROM  contact WHERE id = ?", [it[1]])[0]
                map = [:]
                map1= [:]
                def map2 = [:]
                map1.put("fullName",contacts[0])
                map1.put("idNumber",contacts[1])
                map1.put("maritalStatus",contacts[2])
                map.put("name",contactType[0])
                map2.put("contact",map1)
                map2.put("type",map)
                opportunityContactsList.add(map2)
            }
            oppMap.put("contacts",opportunityContactsList)

            def stage = db.rows("SELECT name,code,category_id from opportunity_stage where id = ?", [opportunityBase[0]])[0]
            def categeName = db.rows("SELECT name from opportunity_stage_category WHERE id = ?", [stage[2]])[0]
            map = [:]
            map1= [:]
            map.put("name",categeName == null ? "" :categeName[0])
            map1.put("category",map)
            map1.put("name",stage[0])
            map1.put("code",stage[1])
            oppMap.put("stage",map1)

            def lenderId = db.rows("SELECT level_id from contact WHERE id = ? ", [opportunityBase[1]])[0]
            map = [:]
            map1 = [:]
            if(lenderId)
            {
                def level = db.rows("SELECT name,description FROM contact_level WHERE id = ? ", [lenderId[0]])[0]
                if(level)
                {
                    map.put("name",level[0])
                    map.put("description",level[1])
                }

            }

            map1.put("level",map)
            oppMap.put("lender",map1)
            //身份证
            def attachments = db.rows("SELECT file_name,file_url FROM attachments WHERE opportunity_id = ? and type_id = 1", [it])
            def attachmentsList = []
            attachments.each {
                map = [:]
                map.put("fileName",it[0])
                map.put("fileUrl",it[1])
                map.put("type",["name":"房产证"])
                attachmentsList.add(map)
            }
            oppMap.put("attachments",attachmentsList)
            opportunityList.add(oppMap)
        }

        println opportunityList?.size()
        render opportunityList as JSON
        return
    }

    @Secured(['permitAll'])
    def appQueryOpportunityFlowsBySerialNumber() {
        def json = request.JSON
        println "************************* appQueryOpportunityFlowsBySerialNumber ***************************"
        println json

        def serialNumber = json["serialNumber"]
        String sessionId = json["sessionId"]
        Contact contact = Contact.findByAppSessionId(sessionId)
        if (!sessionId) {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else if (!contact) {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (!serialNumber || serialNumber.size() <= 0) {
            def errors = [errorCode: 4262, errorMessage: "订单编号未传"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def list = [:]
        def flows = []
        def opportunityFlow = [:]
        def opportunity = Opportunity.findBySerialNumber(serialNumber)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        opportunityFlows.each {
            if (it?.executionSequence < currentFlow?.executionSequence) {
                opportunityFlow = [:]
                opportunityFlow.stage = it?.stage?.name
                opportunityFlow.endTime = it?.endTime
                opportunityFlow.flag = 1
                flows.add(opportunityFlow)
            } else if (it?.executionSequence == currentFlow?.executionSequence) {
                opportunityFlow = [:]
                opportunityFlow.stage = it?.stage?.name
                opportunityFlow.endTime = it?.endTime
                if (opportunity?.status == "Failed") {
                    opportunityFlow.flag = 3
                } else {
                    opportunityFlow.flag = 1
                }
                flows.add(opportunityFlow)
            } else {
                opportunityFlow = [:]
                opportunityFlow.stage = it?.stage?.name
                opportunityFlow.endTime = it?.endTime
                opportunityFlow.flag = 0
                flows.add(opportunityFlow)
            }
        }
        list['opportunityFlow'] = flows
        list['serialNumber'] = serialNumber


        println list
        render list as JSON
        return
    }

    @Secured(['permitAll'])
    def appSuggestSubmit() {
        //中佳信3期评房反馈建议提交
        def json = request.JSON
        println "************************* appQueryByStageAndStatus ***************************"
        println json

        String sessionId = json["sessionId"]
        Contact contact = Contact.findByAppSessionId(sessionId)
        if (!sessionId) {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else if (!contact) {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def unitPrice = json["unitPrice"]
        def specialX = json["specialX"]
        def memo = json["memo"]

        if (!unitPrice) {
            def errors = [errorCode: 4270, errorMessage: "请输入期望单价"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!specialX) {
            def errors = [errorCode: 4271, errorMessage: "请输入特殊因素"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!memo) {
            def errors = [errorCode: 4272, errorMessage: "请输入期望单价"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def result = [code: 1000, message: "成功"]
        render JsonOutput.toJson(result), status: 400

    }

    @Secured(['permitAll'])
    def getCityList() {
        //
        def cityList = City.findAll()
        render cityList as JSON
    }

    @Secured(['permitAll'])
    def appQueryByParamValue() {
        def json = request.JSON
        println "************************* appQueryByParamValue ***************************"
        println json

        String sessionId = json["sessionId"]
        String paramValue = json["paramValue"]
        String stageType = json["stageType"]

        Contact contact = Contact.findByAppSessionId(sessionId)
        if (!sessionId) {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        } else if (!contact) {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!paramValue) {
            def errors = [errorCode: 4274, errorMessage: "请输入查询参数"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def opportunityList = []
        if (stageType == "ALL") {
            opportunityList = Opportunity.executeQuery("from Opportunity as o, Collateral as c where o.id = c.opportunity.id and o.contact.id = ${contact.id} and (o.fullName like '%${paramValue}%' or o.serialNumber like '%${paramValue}%' or c.address like '%${paramValue}%' or c.projectName like '%${paramValue}%')")
        } else {
            opportunityList = Opportunity.executeQuery("from Opportunity as o, Collateral as c where o.id = c.opportunity.id and o.contact.id = ${contact.id} and o.stage.id in (12, 2) and (o.fullName like '%${paramValue}%' or o.serialNumber like '%${paramValue}%' or c.address like '%${paramValue}%' or c.projectName like '%${paramValue}%')")
        }

        println opportunityList
        println opportunityList.size()

        def resultList = []
        if (opportunityList.size() > 0) {
            opportunityList?.each {
                println it[0]
                resultList.add(it[0])
            }
        }
        println resultList

        render resultList as JSON
    }

}

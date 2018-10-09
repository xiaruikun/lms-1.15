package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import java.text.SimpleDateFormat

/**
 *
 * 创建日期 2017-04-21
 * 创建人员 张成远
 * 方法功能 “黄金屋”微信公众号的评房、报单、图片上传下载
 **/
@Transactional(readOnly = true)
class WechatOpportunityController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def opportunityService
    def contactService
    def opportunityNotificationService
    def propertyValuationProviderService
    def messageService

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 微信报单功能
     **/
    @Secured('permitAll')
    @Transactional
    def wxUpdate2(Opportunity opportunity)
    {
        if (!opportunity?.contact)
        {
            if (!params["openId"])
            {
                flash.message = message(code: "报单失败，请稍后重试")
                respond opportunity, view: 'wxShow2'
                return
            }
            else
            {
                def contact = ContactLoginCertificate.findByExternalId(params["openId"])?.contact
                opportunity.contact = contact
                opportunity.status = "Pending"
                opportunity.account = contact?.user?.account
                opportunity.user = contact?.user
                opportunity.loanApplicationProcessType = LoanApplicationProcessType.findByName("直接报单")
            }
        }

        // 借款人信息
        Contact loanContact = new Contact()
        loanContact.fullName = opportunity.fullName
        loanContact.maritalStatus = opportunity.maritalStatus
        loanContact.cellphone = opportunity.cellphone
        loanContact.user = opportunity?.contact?.user
        loanContact.account = opportunity?.contact?.account
        loanContact.level = ContactLevel.findByName("B")
        loanContact.type = "Client"
        loanContact.save flush: true

        // 抵押人信息
        Contact mortgageContact = null
        if (params['myGroup'] == '0')
        {
            mortgageContact = new Contact()
            mortgageContact.fullName = "抵押人姓名"
            mortgageContact.maritalStatus = params["mortgagorMaritalStatus"]
            mortgageContact.user = opportunity?.contact?.user
            mortgageContact.account = opportunity?.contact?.account
            mortgageContact.save flush: true
        }

        Integer protectDays = 10
        if (opportunity.cellphone)
        {
            protectDays += 5
        }
        Date protectionStartTime = new java.util.Date()
        opportunity.protectionStartTime = protectionStartTime
        opportunity.protectionEndTime = protectionStartTime + protectDays
        opportunity.stage = OpportunityStage.findByCode("04")
        opportunity.createdDate = new Date()
        opportunity.modifiedDate = new Date()
        opportunity.lender = loanContact
        if (opportunity.validate())
        {
            opportunity.save flush: true
        }
        else
        {
            println opportunity.errors
        }

        def fileNames = params["fileNames"]
        def imagList = fileNames?.split(",")
        if (imagList.size() > 0)
        {
            imagList.each {
                def attachmentType = it.split(":")[0]
                def fileName = it.split(":")[1]
                def attachment = new Attachments()
                attachment.fileName = "http://s27.zhongjiaxin.com/fs/static/images/${fileName}"
                attachment.type = AttachmentType.find("from AttachmentType where name = '${attachmentType}'")
                attachment.opportunity = opportunity
                attachment.contact = opportunity?.contact
                if (attachment.validate())
                {
                    attachment.save flush: true
                }
                else
                {
                    attachment.errors.each {
                        println it
                    }
                }
            }
        }
        else
        {
            flash.message = message(code: "图片上传失败，请稍后重试")
            respond opportunity, view: 'wxShow2'
            return
        }

        OpportunityContact loanOpportunityContact = new OpportunityContact()
        loanOpportunityContact.opportunity = opportunity
        loanOpportunityContact.contact = loanContact
        loanOpportunityContact.type = OpportunityContactType.findByName("借款人")
        loanOpportunityContact.save flush: true

        if (mortgageContact)
        {
            OpportunityContact mortgageOpportunityContact = new OpportunityContact()
            mortgageOpportunityContact.opportunity = opportunity
            mortgageOpportunityContact.contact = mortgageContact
            mortgageOpportunityContact.type = OpportunityContactType.findByName("抵押人")
            mortgageOpportunityContact.save flush: true
        }

        //订单初始化
        opportunityService.initOpportunity(opportunity)

        redirect(action: "wxOpportunitySuccessful", id: opportunity.id)
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 根据微信公众平台返回图片的serverId拉取图片，并存储到图片服务器
     **/
    @Secured('permitAll')
    @Transactional
    def wxGetImgServerId()
    {
        def marialFile = "借款人身份证," + params["marialFile"]
        def maritalReverseFile = "借款人身份证," + params["maritalReverseFile"]
        def spouseFile = "借款人配偶身份证," + params["spouseFile"]
        def spouseReverseFile = "借款人配偶身份证," + params["spouseReverseFile"]
        def mortgagorFile = "抵押人身份证," + params["mortgagorFile"]
        def mortgagorReverseFile = "抵押人身份证," + params["mortgagorReverseFile"]
        def mortgagorSpouseFile = "抵押人配偶身份证," + params["mortgagorSpouseFile"]
        def mortgagorSpouseReverseFile = "抵押人配偶身份证," + params["mortgagorSpouseReverseFile"]
        def properties = params["properties"]

        def list = [marialFile, maritalReverseFile]
        if (params["spouseFile"])
        {
            list.add(spouseFile)
            list.add(spouseReverseFile)
        }
        if (params["mortgagorFile"])
        {
            list.add(mortgagorFile)
            list.add(mortgagorReverseFile)
        }
        if (params["mortgagorSpouseFile"])
        {
            list.add(mortgagorSpouseFile)
            list.add(mortgagorSpouseReverseFile)
        }
        if (!properties)
        {
            flash.message = message(code: "请上传房产证")
            respond opportunity, view: 'wxUpload'
            return
        }
        def propertiesList = properties.split(",")
        if (propertiesList.size() > 0)
        {
            propertiesList.each {
                list.add("房产证," + it)
            }
        }

        def fileNameList = []
        def webrootDir = servletContext.getRealPath("/")
        def accessToken = params["accessToken"]
        list.each {
            def serverId = it.split(",")[-1]
            def fileName = opportunityService.uploadImage(webrootDir, accessToken, serverId)
            if (fileName != 0)
            {
                def attachmentType = it.split(",")[0]
                fileNameList.add("${attachmentType}:${fileName}")
            }
            else
            {
                flash.message = message(code: "图片上传失败，请稍后重试")
                respond opportunity, view: 'wxShow2'
                return
            }
        }

        if (fileNameList.size() > 0)
        {
            println "Ajax请求返回图片相关信息（新版）："
            render([status: "success", fileNameList: fileNameList] as JSON)
        }
        else
        {
            render([status: "error", errorMsg: "图片上传失败，请稍后重试"] as JSON)
        }
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 报单成功页跳转
     **/
    @Secured('permitAll')
    def wxOpportunitySuccessful(Opportunity opportunity)
    {
        respond opportunity
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 订单详情页跳转
     **/
    @Secured('permitAll')
    def wxShow(Opportunity opportunity)
    {
        def collateral = Collateral.findByOpportunity(opportunity)
        respond opportunity, model: [collateral: collateral], view: "wxShow"
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 报单页跳转
     **/
    @Secured('permitAll')
    @Transactional
    def wxShow2(String code, String state)
    {
        if (code)
        {
            // 直接报单
            def openId
            if (session.openId)
            {
                openId = session.openId
            }
            else
            {
                openId = contactService.setRequest(code, state)
                session.openId = openId
            }

            def certificate = ContactLoginCertificate.findByExternalId(openId)
            if (certificate)
            {
                Opportunity opportunity = new Opportunity()
                def loanApplicationProcessTypeId = certificate?.contact?.city?.loanApplicationProcessType?.id
                if (loanApplicationProcessTypeId == 2)
                {
                    respond opportunity, model: [openId: openId], view: "wxShow3"
                    return
                }

                respond opportunity, model: [openId: openId], view: "wxShow2"
            }
            else
            {
                redirect(controller: "contact", action: "wxWelcome", params: [code: code, state: state])
            }
        }
        else
        {
            // 评房报单
            def opportunity = Opportunity.findById(params["id"])
            opportunity.loanApplicationProcessType = LoanApplicationProcessType.findByName("先评房再报单")
            if (opportunity?.contact?.city?.id == 1)
            {
                respond opportunity
            }
            else
            {
                redirect(action: "wxShow4", id: params["id"])
            }
        }
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 订单列表页跳转
     **/
    @Secured('permitAll')
    @Transactional
    def wxIndex(String code, String state)
    {
        def openId
        if (session.openId)
        {
            openId = session.openId
        }
        else
        {
            openId = contactService.setRequest(code, state)
            session.openId = openId
        }

        def contact
        def certificate = ContactLoginCertificate.findByExternalId(openId)
        if (certificate)
        {
            contact = certificate.contact
        }
        else
        {
            redirect(controller: "contact", action: "wxWelcome", params: [code: null, state: null])
            return
        }

        def stageStatus = params["stage"]
        def opportunity = []
        if ("allItems" == stageStatus)
        {
            opportunity = Opportunity.findAll("from Opportunity as c where c.contact = " + contact.id + " and c.stage.code <> '02' and c.stage.code <> '15' order by createdDate desc")
        }
        if ("alreadyPF" == stageStatus)
        {
            opportunity = Opportunity.findAll("from Opportunity as c where c.contact = " + contact.id + " and c.stage.code in ('02', '15') and c.status <> 'Failed' order by createdDate desc")
        }
        if ("waitSH" == stageStatus)
        {
            opportunity = Opportunity.findAll("from Opportunity as c where c.contact = " + contact.id + " and c.stage.code in ('03','04', '16') and c.status <> 'Failed' order by " + "createdDate desc")
        }
        if ("alreadyCS" == stageStatus)
        {
            opportunity = Opportunity.findAll("from Opportunity as c where c.contact = " + contact.id + " and c.stage.code in ('05','06', '07') and c.status <> 'Failed' order by " + "createdDate desc")
        }
        if ("alreadySP" == stageStatus)
        {
            opportunity = Opportunity.findAll("from Opportunity as c where c.contact = " + contact.id + " and c.stage.code in ('08','09','10') and c.status <> 'Failed' order by " + "createdDate desc")
        }
        if ("alreadyFD" == stageStatus)
        {
            opportunity = Opportunity.findAll("from Opportunity as c where c.contact = " + contact.id + " and c.stage.code = '11' and c.status <> 'Failed' order by createdDate desc")
        }
        if ("alreadySB" == stageStatus)
        {
            opportunity = Opportunity.findAll("from Opportunity as c where c.contact = " + contact.id + " and c.status = 'Failed' order by createdDate desc")
        }

        respond opportunity, model: [opportunityInstanceCount: opportunity.size(), stageStatus: stageStatus, contact: contact], view: "wxIndex"
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 验证订单是否处于保护期
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxVerifyProtectDay()
    {
        def requestedAmount = params["requestedAmount"]
        def loanDuration = params["loanDuration"]
        def fullName = params["fullName"]
        if (Opportunity.findByFullNameAndRequestedAmountAndLoanDuration(fullName, Integer.parseInt(requestedAmount), Double.parseDouble(loanDuration)))
        {
            render([status: "success", flag: true] as JSON)
        }
        else
        {
            render([status: "success", flag: false] as JSON)
        }
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 评房功能，页面一跳转
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step1(String code, String state)
    {
        def openId
        if (session.openId)
        {
            openId = session.openId
        }
        else
        {
            openId = contactService.setRequest(code, state)
            session.openId = openId
        }

        def certificate = ContactLoginCertificate.findByExternalId(openId)
        if (certificate)
        {
            def cityList = []
            def accountCityList = AccountCity.findAll("from AccountCity as a where a.account.id = 1")
            if (accountCityList && accountCityList.size() == 0)
            {
                flash.message = message(code: "暂无开放城市")
            }
            else
            {
                accountCityList.each {
                    cityList.add(it.city)
                }
            }

            def collateral = new Collateral()
            respond collateral, model: [cityList: cityList, openId: openId]
        }
        else
        {
            // 跳转到leads的评房页面
            redirect(controller: "leads", action: "wxCreate2Step1", params: [code: code, state: state])
            return
        }
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 评房功能，页面二跳转，查询区县功能
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step2(Collateral collateral)
    {
        def projectNameList = []
        def result = propertyValuationProviderService.searchProjectName(collateral?.city?.name, collateral?.projectName)
        if (result != 0)
        {
            projectNameList = result.getAt("body")
        }
        def newList = []
        if (projectNameList.size() > 8)
        {
            for (
                i in
                    0..7)
            {
                newList.add(projectNameList.get(i));
            }
        }
        else
        {
            newList = projectNameList
        }

        respond collateral, model: [projectNameList: newList, openId: params["openId"]], view: "wxCreate2Step2"
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 评房功能，页面三跳转
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step3(Collateral collateral)
    {
        def houseTypeList1 = HouseType.list()
        def houseTypeList = []
        houseTypeList1.each {
            if (!(it?.name in ["独栋", "联排", "双拼", "叠拼"]))
            {
                houseTypeList.add(it?.name)
            }
        }
        def assetTypeList1 = AssetType.list()
        def assetTypeList = []
        assetTypeList1.each {
            if (!(it?.name in ["住宅", "央产房", "优惠价房改房", "标准价房改房", "军产房", "校产房", "其他", "成本价房改房"]))
            {
                assetTypeList.add(it?.name)
            }
        }
        respond collateral, model: [openId: params["openId"], houseTypeList: houseTypeList, assetTypeList: assetTypeList], view: "wxCreate2Step3"
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 评房功能，询值
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step4Save(Collateral collateral)
    {
        def opportunity = new Opportunity()
        def certificate = ContactLoginCertificate.findByExternalId(params["openId"])
        if (certificate)
        {
            opportunity.contact = certificate.contact
        }
        else
        {
            redirect(controller: "contact", action: "wxWelcome", params: [code: null, state: null])
            return
        }

        collateral.status = params["status"]
        collateral.externalId = params["externalId"]

        def unitPrice = params["unitPrice"]
        if (!unitPrice)
        {
            unitPrice = '0'
        }
        collateral.unitPrice = Double.parseDouble(unitPrice)
        collateral.totalPrice = Double.parseDouble(unitPrice) * collateral.area / 10000
        // 楼阁、跃层
        if (collateral.atticArea > 0)
        {
            collateral.totalPrice += Double.parseDouble(unitPrice) * collateral.atticArea / 20000
        }

        opportunity.loanAmount = collateral.totalPrice
        opportunity.unitPrice = collateral.unitPrice
        if (collateral?.status == "Completed")
        {
            opportunity.stage = OpportunityStage.findByCode("02")
        }
        else
        {
            opportunity.stage = OpportunityStage.findByCode("15")
        }

        // 订单默认分配给支持经理人
        def user = User.findByCellphone(opportunity?.contact?.userCode)
        opportunity.user = user
        opportunity.account = user?.account

        if (opportunity.validate())
        {
            opportunity.save flush: true
            if (opportunity?.stage?.code == '02')
            {
                messageService.sendMessage2(opportunity?.contact?.userCode, "【中佳信】您的经纪人 " + opportunity?.contact?.fullName + "（" + opportunity?.contact?.cellphone + "） 有评房成功订单（" + opportunity?.serialNumber + "），请及时关注跟进！")
            }
        }
        else
        {
            opportunity.errors.each {
                println it
            }
        }
        collateral.opportunity = opportunity
        collateral.requestStartTime = new Date()
        if (collateral.validate())
        {
            collateral.save()
        }
        else
        {
            println collateral.errors
        }

        //订单初始化
        opportunityService.initOpportunity(opportunity)

        redirect(action: "wxCreate2Step4", id: collateral.id)
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 评房功能，询值成功
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step4(Collateral collateral)
    {
        respond collateral
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 将图片上传到微信公众平台，该方法用于获取微信上传接口的参数，accessToken、nonceStr、signature、timestamp
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxUpload(Opportunity opportunity)
    {
        def accessToken = opportunityService.getAccessToken()
        if (!accessToken)
        {
            flash.message = message(code: "获取access_token失败，请稍后重试")
            respond opportunity, view: 'wxUpload'
            return
        }

        def ticket = opportunityService.getTicket(accessToken)
        if (!ticket)
        {
            flash.message = message(code: "获取jsapi_ticket失败，请稍后重试")
            respond new Opportunity(), view: 'wxUpload'
            return
        }

        def nonceStr = UUID.randomUUID().toString()
        def time = (System.currentTimeMillis() / 1000).toString()
        def timestamp = time.substring(0, time.lastIndexOf("."))
        def url = request.getRequestURL()
        def signature = opportunityService.getSignature(ticket, nonceStr, timestamp, url.toString())

        respond opportunity, model: [nonceStr: nonceStr, timestamp: timestamp, signature: signature, accessToken: accessToken], view: "wxUpload"
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 通过城市获取小区
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxGetDistrictByCity()
    {
        def city = params["city"]
        def list = []
        if (city)
        {
            list = District.findAllByCity(City.findById(city))
        }

        def districtList = []
        if (list)
        {
            list.each {
                districtList.add(it?.name)
            }
        }
        render([status: "success", districtList: districtList] as JSON)
        return
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 微信复评功能上传图片，该方法用于获取微信上传接口的参数，accessToken、nonceStr、signature、timestamp
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxSuggest(Collateral collateral)
    {
        def accessToken = opportunityService.getAccessToken()
        if (!accessToken)
        {
            flash.message = message(code: "获取access_token失败，请稍后重试")
            respond collateral, view: 'wxUpload'
            return
        }

        def ticket = opportunityService.getTicket(accessToken)
        if (ticket)
        {
            def nonceStr = UUID.randomUUID().toString()
            def time = (System.currentTimeMillis() / 1000).toString()
            def timestamp = time.substring(0, time.lastIndexOf("."))
            def url = request.getRequestURL()
            def signature = opportunityService.getSignature(ticket, nonceStr, timestamp, url.toString())

            respond collateral, model: [nonceStr: nonceStr, timestamp: timestamp, signature: signature, accessToken: accessToken], view: "wxSuggest"
        }
        else
        {
            flash.message = message(code: "获取jsapi_ticket失败，请稍后重试")
            respond new Collateral(), view: 'wxSuggest'
            return
        }
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 微信复评功能
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxSuggestSave(Collateral collateral)
    {
        if (collateral == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (collateral.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond collateral.errors, view: 'wxSuggest'
            return
        }

        collateral.status = "Pending"
        if (collateral.validate())
        {
            collateral.save flush: true
        }
        else
        {
            println collateral.errors
        }

        def opportunity = collateral.opportunity
        def attachmentsList = []
        def fileNames = params["fileNames"]
        if (fileNames)
        {
            def fileNamesList = fileNames.split(",")
            if (fileNamesList.size() > 0)
            {
                fileNamesList.each {
                    def attachmentType = it.split(":")[0]
                    def fileName = it.split(":")[1]
                    def attachment = new Attachments()
                    attachment.fileName = "http://s27.zhongjiaxin.com/fs/static/images/${fileName}"
                    attachment.type = AttachmentType.findByName(attachmentType)
                    attachment.opportunity = collateral?.opportunity
                    attachment.contact = collateral?.opportunity?.contact
                    if (attachment.validate())
                    {
                        attachment.save flush: true
                    }
                    else
                    {
                        println attachment.errors
                    }
                    def suggestAttachment = [:]
                    suggestAttachment['attachmentType'] = attachmentType
                    suggestAttachment['fileName'] = "http://s27.zhongjiaxin.com/fs/static/images/${fileName}"
                    attachmentsList.add(suggestAttachment)
                }
            }
        }

        // 微信复评功能
        def result = [:]
        result['reasonOfPriceAdjustment'] = collateral?.reasonOfPriceAdjustment
        result['appliedTotalPrice'] = collateral?.appliedTotalPrice
        result['attachments'] = attachmentsList
        result['externalId'] = collateral.externalId

        println "微信复评入参:"
        println result

        def resultJson = propertyValuationProviderService.suggestSubmmit(result)
        println "微信复评反馈结果:" + resultJson

        if (resultJson != "" && resultJson["true"])
        {
            if (resultJson["status"] == "Success")
            {
                def unitPrice = resultJson['unitPrice']
                if (unitPrice)
                {
                    if (collateral)
                    {
                        collateral.requestEndTime = new Date()
                        collateral.unitPrice = unitPrice.toDouble()
                        collateral.totalPrice = collateral.unitPrice * collateral.area / 10000
                        // 楼阁、跃层
                        if (collateral.atticArea > 0)
                        {
                            collateral.totalPrice += collateral.unitPrice * collateral.atticArea / 20000
                        }
                        collateral.status = "Completed"
                        if (collateral.validate())
                        {
                            collateral.save flush: true
                        }
                        else
                        {
                            println collateral.errors
                        }
                        if (opportunity)
                        {
                            opportunity.unitPrice = unitPrice.toDouble()
                            opportunity.loanAmount = collateral.unitPrice * collateral.area / 10000
                            // 楼阁、跃层
                            if (collateral.atticArea > 0)
                            {
                                opportunity.loanAmount += collateral.unitPrice * collateral.atticArea / 20000
                            }
                            // opportunity.stage = OpportunityStage.findByCode("02")
                            if (opportunity.validate())
                            {
                                opportunity.save flush: true
                            }
                            else
                            {
                                println opportunity.errors
                            }

                            //发送估值结果短信
                            messageService.sendMessage2(collateral?.opportunity?.contact?.cellphone, "【中佳信】您的订单（${collateral?.opportunity?.serialNumber}）复评结果出来了，复评结果：" + opportunity.loanAmount + "万元。详情请登录中佳信黄金屋评估记录中查看")
                        }
                    }
                }

                redirect(action: "wxSuggestSuccess", id: collateral.id, params: [suggestMessage: "复评价格为" + opportunity.loanAmount + "万元"])
            }
            else
            {
                //发送复评结果短信
                messageService.sendMessage2(collateral?.opportunity?.contact?.cellphone, "【中佳信】您的订单（${collateral?.opportunity?.serialNumber}）复评失败，请联系复评人员。详情请登录中佳信黄金屋评估记录中查看")
                redirect(action: "wxSuggestSuccess", id: collateral.id, params: [suggestMessage: "请联系复评人员"])
            }
        }
        else
        {
            flash.message = message(code: "调用评房反馈服务失败，请稍后重试")
            respond collateral, view: 'wxSuggest'
            return
        }
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 微信复评功能 根据微信公众平台返回图片的serverId拉取图片，并存储到图片服务器
     **/
    @Secured('permitAll')
    @Transactional
    def wxGetEvaluateImgServerId()
    {
        def attachmentsList = []
        def evaluateFiles = params["evaluateFiles"]
        def accessToken = params["accessToken"]
        def webrootDir = servletContext.getRealPath("/")
        def evaluateFilesList = evaluateFiles.split(",")
        if (evaluateFilesList.size() > 0)
        {
            evaluateFilesList.each {
                def fileName = opportunityService.uploadImage(webrootDir, accessToken, it)
                if (fileName != 0)
                {
                    attachmentsList.add("估值附回材料:${fileName}")
                }
                else
                {
                    render([status: "error", errorMsg: "图片获取失败，请稍后重试"] as JSON)
                }
            }
        }

        render([status: "success", fileNameList: attachmentsList] as JSON)
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 微信复评成功跳转
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxSuggestSuccess(Collateral collateral)
    {
        respond collateral, model: [suggestMessage: params.suggestMessage]
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 根据区县信息查询房屋价格，询值功能
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxQueryPrice()
    {
        def collateral = new Collateral()
        collateral.city = City.findById(params['city'])
        collateral.district = params['district']
        collateral.projectName = params['projectName']
        collateral.building = Integer.parseInt(params['building'])
        collateral.unit = Integer.parseInt(params['unit'])
        collateral.floor = params['floor']
        collateral.roomNumber = params['roomNumber']
        collateral.totalFloor = params['totalFloor']
        collateral.area = Double.parseDouble(params['area'])
        collateral.address = params['address']
        collateral.orientation = params['orientation']
        collateral.houseType = params['houseType']
        collateral.specialFactors = params['specialFactors']
        collateral.assetType = params['assetType']
        collateral.appliedTotalPrice = Double.parseDouble(params['appliedTotalPrice'])
        def atticArea = params['atticArea']
        if (atticArea)
        {
            collateral.atticArea = Double.parseDouble(atticArea)
        }
        else
        {
            collateral.atticArea = 0
        }

        def price = propertyValuationProviderService.queryPrice(collateral)
        if (price != 0)
        {
            println "询值结果："
            println price
            render([status: "success", price: price] as JSON)
        }
        else
        {
            render([status: "error", errorMsg: "询价失败，请稍后重试"] as JSON)
        }
    }

    // ****************************供老版保单页面使用*****************************

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 报单页跳转 旧版（需要录入借款人、借款人配偶，抵押人、抵押人配偶信息）
     **/
    @Secured('permitAll')
    @Transactional
    def wxShow4(Opportunity opportunity)
    {
        respond opportunity
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 订单保护期时长计算 旧版（根据借款人、借款人配偶、抵押人、抵押人配偶手机号）
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxGetProtectTime()
    {
        Integer protectDays = 10
        if (params["cellphone"])
        {
            protectDays += 5
        }
        if (params["spouseCellphone"])
        {
            protectDays += 5
        }
        if (params["mortgagorCellphone"])
        {
            protectDays += 5
        }
        if (params["mortgagorSpouseCellphone"])
        {
            protectDays += 5
        }

        render([status: "success", protectDays: protectDays] as JSON)
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 验证订单是否处于保护期 旧版（根据借款人身份证验证）
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxVerifyProtectDay2()
    {
        def idNumber = params["idNumber"]
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        def opportunity = Opportunity.find("from Opportunity as c where c.idNumber = '${idNumber}' and c.stage not in ('01', '02', '11') and c.status = 'Pending' and c.protectionEndTime > '" + format.format(new Date()) + "'")
        if (opportunity)
        {
            render([status: "success", flag: true] as JSON)
        }
        else
        {
            render([status: "success", flag: false] as JSON)
        }
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 将图片上传到微信公众平台，该方法用于获取微信上传接口的参数，accessToken、nonceStr、signature、timestamp 旧版
     **/
    @Secured(['permitAll'])
    @Transactional
    def wxUpload2(Opportunity opportunity)
    {
        def accessToken = opportunityService.getAccessToken()
        if (!accessToken)
        {
            flash.message = message(code: "获取access_token失败，请稍后重试")
            respond opportunity, view: 'wxUpload2'
            return
        }

        def ticket = opportunityService.getTicket(accessToken)
        if (!ticket)
        {
            flash.message = message(code: "获取jsapi_ticket失败，请稍后重试")
            respond new Opportunity(), view: 'wxUpload2'
            return
        }

        def nonceStr = UUID.randomUUID().toString()
        def time = (System.currentTimeMillis() / 1000).toString()
        def timestamp = time.substring(0, time.lastIndexOf("."))
        def url = request.getRequestURL()
        def signature = opportunityService.getSignature(ticket, nonceStr, timestamp, url.toString())

        respond opportunity, model: [nonceStr: nonceStr, timestamp: timestamp, signature: signature, accessToken: accessToken, params: params], view: "wxUpload2"
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 根据微信公众平台返回图片的serverId拉取图片，并存储到图片服务器 旧版
     **/
    @Secured('permitAll')
    @Transactional
    def wxGetImgServerId2()
    {
        def marialFile = "借款人身份证," + params["marialFile"]
        def maritalReverseFile = "借款人身份证," + params["maritalReverseFile"]
        def spouseFile = "借款人配偶身份证," + params["spouseFile"]
        def spouseReverseFile = "借款人配偶身份证," + params["spouseReverseFile"]
        def mortgagorFile = "抵押人身份证," + params["mortgagorFile"]
        def mortgagorReverseFile = "抵押人身份证," + params["mortgagorReverseFile"]
        def mortgagorSpouseFile = "抵押人配偶身份证," + params["mortgagorSpouseFile"]
        def mortgagorSpouseReverseFile = "抵押人配偶身份证," + params["mortgagorSpouseReverseFile"]
        def properties = params["properties"]

        def list = [marialFile, maritalReverseFile]
        if (params["spouseFile"])
        {
            list.add(spouseFile)
            list.add(spouseReverseFile)
        }
        if (params["mortgagorFile"])
        {
            list.add(mortgagorFile)
            list.add(mortgagorReverseFile)
        }
        if (params["mortgagorSpouseFile"])
        {
            list.add(mortgagorSpouseFile)
            list.add(mortgagorSpouseReverseFile)
        }
        if (!properties)
        {
            flash.message = message(code: "请上传房产证")
            respond opportunity, view: 'wxUpload2'
            return
        }
        def propertiesList = properties.split(",")
        if (propertiesList.size() > 0)
        {
            propertiesList.each {
                list.add("房产证," + it)
            }
        }

        def fileNameList = []
        def webrootDir = servletContext.getRealPath("/")
        def accessToken = params["accessToken"]
        list.each {
            def serverId = it.split(",")[-1]
            def fileName = opportunityService.uploadImage(webrootDir, accessToken, serverId)
            if (fileName != 0)
            {
                def attachmentType = it.split(",")[0]
                fileNameList.add("${attachmentType}:${fileName}")
            }
            else
            {
                flash.message = message(code: "图片上传失败，请稍后重试")
                respond opportunity, view: 'wxShow4'
                return
            }
        }

        if (fileNameList.size() > 0)
        {
            println "Ajax请求返回图片相关信息（老版）："
            println fileNameList
            render([status: "success", fileNameList: fileNameList] as JSON)
        }
        else
        {
            render([status: "error", errorMsg: "图片上传失败，请稍后重试"] as JSON)
        }
    }

    /**
     *
     * 创建日期 2017-04-21
     * 创建人员 张成远
     * 方法功能 报单功能 旧版
     **/
    @Secured('permitAll')
    @Transactional
    def wxUpdate3(Opportunity opportunity)
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
            respond opportunity.errors, view: 'wxShow4'
            return
        }

        def idNumber = params["idNumberTemp"]
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        def opportunity1 = Opportunity.find("from Opportunity as c where c.idNumber = '${idNumber}' and c.stage not in ('01', '02', '11') and c.status = 'Pending' and c.protectionEndTime > '" + format.format(new Date()) + "'")
        if (opportunity1)
        {
            // 借款人处于保护期，不可以报单
            flash.message = message(code: 'opportunity.inProjectTime.message')
            redirect(action: "wxShow4", id: opportunity.id)
        }
        else
        {
            Integer protectDays = 10
            if (params["cellphone"])
            {
                protectDays += 5
            }
            if (params["spouseCellphone"])
            {
                protectDays += 5
            }
            if (params["mortgagorCellphone"])
            {
                protectDays += 5
            }
            if (params["mortgagorSpouseCellphone"])
            {
                protectDays += 5
            }

            // 保护期
            Date protectionStartTime = new java.util.Date()
            opportunity.protectionStartTime = protectionStartTime
            opportunity.protectionEndTime = protectionStartTime + protectDays
            opportunity.stage = OpportunityStage.findByCode("04")
            opportunity.modifiedDate = new Date()
            opportunity.idNumber = params["idNumberTemp"]

            if (opportunity.validate())
            {
                opportunity.save flush: true
            }
            else
            {
                println opportunity.errors
            }

            // 保存图片信息
            def fileNames = params["fileNames"]
            def imagList = fileNames?.split(",")
            if (imagList.size() > 0)
            {
                imagList.each {
                    def attachmentType = it.split(":")[0]
                    def fileName = it.split(":")[1]
                    def attachment = new Attachments()
                    attachment.fileName = "http://s27.zhongjiaxin.com/fs/static/images/${fileName}"
                    attachment.type = AttachmentType.find("from AttachmentType where name = '${attachmentType}'")
                    attachment.opportunity = opportunity
                    attachment.contact = opportunity?.contact
                    if (attachment.validate())
                    {
                        attachment.save flush: true
                    }
                    else
                    {
                        attachment.errors.each {
                            println it
                        }
                    }
                }
            }
            else
            {
                flash.message = message(code: "图片上传失败，请稍后重试")
                respond opportunity, view: 'wxShow4'
                return
            }

            // 借款人信息
            if (opportunity?.idNumber)
            {
                opportunityService.addContacts(opportunity?.fullName, opportunity?.idNumber, opportunity?.cellphone, opportunity?.maritalStatus, "借款人", opportunity)
            }
            // 借款人配偶信息
            String spouseIdNumber = params["spouseIdNumber"]
            if (spouseIdNumber)
            {
                opportunityService.addContacts(params["spouseFullName"], params["spouseIdNumber"], params["spouseCellphone"], "已婚", "借款人配偶", opportunity)
            }
            // 抵押人信息
            String mortgagorIdNumber = params["mortgagorIdNumber"]
            if (mortgagorIdNumber)
            {
                opportunityService.addContacts(params["mortgagorFullName"], params["mortgagorIdNumber"], params["mortgagorCellphone"], params["mortgagorMaritalStatus"], "抵押人", opportunity)
            }
            // 抵押人配偶信息
            String mortgagorSpouseIdNumber = params["mortgagorSpouseIdNumber"]
            if (mortgagorSpouseIdNumber)
            {
                opportunityService.addContacts(params["mortgagorSpouseFullName"], params["mortgagorSpouseIdNumber"], params["mortgagorSpouseCellphone"], "已婚", "抵押人配偶", opportunity)
            }

            opportunityService.assignToClient(opportunity)

            //订单初始化
            opportunityService.initOpportunity(opportunity)

            redirect(action: "wxShow", id: opportunity.id)
        }
    }
}
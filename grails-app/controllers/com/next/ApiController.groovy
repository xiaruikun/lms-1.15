package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

@Transactional(readOnly = true)
class ApiController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def opportunityService
    def contactService
    def propertyValuationProviderService
    def applySubmitService

    /*
     4001   订单externalId不能为空
     4002   请输入正确的贷款金额
     4003   请输入正确的贷款期限
     4004   请输入正确的销售名称
     4005   请输入正确的机构名称
     4006   请输入正确的身份证号
     4007   请输入正确的姓名
     4008   手机号格式不正确，请重新输入
     4009   请选择婚姻状况
     4010   联系人externalId不能为空
     4011   附件externalId不能为空
     4012   房产externalId不能为空
     4013   请输入正确的一抵金额
     4014   请选择一抵类型
     4015   请输入正确的二抵金额
     4016   请输入房产城市
     4017   息与费externalId不能为空
     4018   账户externalId不能为空
     4019   合同externalId不能为空
     4020   请输入正确的评房总价
     4021   请输入正确的实际贷款期限
     4022   附件类型名称有误
     4023   请输入房产区域
     4024   请输入正确的产品名称
     4025   请输入正确的城市名称
     4026   请输入正确的区域名称
     4027   联系人城市不正确
     4028   客户级别不正确
     4029   请添加联系人信息
     4030   请添加附件信息
     4031   请添加房产信息
     4032   请添加费用信息
     4033   请输入的抵押物权属信息
     4034   请添加合同信息
     4035   请输入审批时抵押状态
     4036   附件创建者有误
     4037   请输入正确的楼栋信息
     4038   请输入正确的房产单价
     4039   请输入正确的房产总价
     4040   请输入正确的房产建成时间
     4041   请输入正确的费率
     4042   请输入正确的费率期限
     4043   请输入正确的预收取月份数
     4044   请输入正确的费用创建人
     4045   请输入正确的费用修改人
     4046   请输入正确的费用所属合同类型
     4047   请输入正确的合同类型
     4048   请输入正确的房产特殊因素
     4049   请输入房产信息其他补充
     4050   接收数据为空
     4051   账户银行不能为空
     4052   请输入请输入房产区域
     4053   请输入房产小区
     4054   请输入房产楼栋
     4055   请输入房产所在楼层
     4056   请输入房产总楼层
     4057   请输入房产房间号
     4058   请输入房产面积
     4059   请输入房产地址
     4060   请输入房产朝向
     4112   请选择住宅类型
     4061   请输入当前总贷款余额
     4062   请输入房产性质
     4064   请输入房产立项类型
     4065   合同编号不能为空
     4066   合同编号不能为重复
     4067   未找到对应订单阶段
     4068   未找到对应订单
     4069   请输入对外担保情况
     4070   externalId，stage参数不全
     4071   请输入对外担保类型
     4072   请输入对外担保余额
     4073   请输入正确的借款用途
     4074   请添加联系人信息
     4075   关联人externalId不能为空
     4076   经纪人手机号格式不正确，请重新输入
     4077   经纪人externalId不能为空
     4078   经纪人城市不正确
     4079   请添加经纪人信息
     4080   请输入月息
     4081   请输入正确的综合月息
     4082   请输入创建人
     4083   请输入创建时间
     4084   请输入放款前要求
     4085   请输入大数据小结
     4086   请输入还款来源
     4087   请输入借款征信评级
     4088   请输入是否要求面审
     4089   请输入正确的对外担保分类
     4090   银行卡号过长
     */
    /**
     * 外部贷审接口
     * @Author 夏瑞坤
     * @ModifiedDate 2017-5-26
     */

    @Secured(['permitAll'])
    @Transactional
    def testInsertOrUpdateOpportunity()
    {
        String apiUrl = "http://10.33.50.9:8080/api/insertOrUpdateOpportunity"
        def all = [:]
        def test = []
        def opportunity = [:]
        def collateral = [:]
        collateral["city"] = "上海"
        collateral["externalId"] = "sdfsafas"
        test.add(collateral)
        collateral = [:]
        collateral["city"] = "上海"
        collateral["externalId"] = "sdfsafas"
        test.add(collateral)
        opportunity["collateral"] = test
        opportunity["externalId"] = "sdfsafas"
        all["opportunity"] = opportunity

        def data = JsonOutput.toJson(all)

        BufferedReader br = null
        println "apiUrl===" + apiUrl
        def result = ""
        URL url = new URL(apiUrl)
        try
        {
            def connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.getOutputStream().write(data.toString().getBytes("UTF-8"))
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = br.readLine()) != null)
            {
                result += line
            }
            def json = JSON.parse(result.toString())
            render json
        }
        catch (Exception e)
        {
            render ""
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def insertOrUpdateOpportunity()
    {

        //def tem = ''
        def json = request.JSON
        //def json = JSON.parse(tem)
        println "************************* insertOrUpdateOpportunity ***************************"

        def opportunity = json.opportunity

        if (!opportunity)
        {
            def errors = [errorCode: 4050, errorMessage: "接收数据为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        println opportunity
        opportunity = JSON.parse(opportunity)
        def workflowName = opportunity?.workflowName
        def workflow = OpportunityWorkflow.findByNameAndActive(workflowName, true)
        //        if (!workflow)
        //        {
        //            def errors = [errorCode: 4001, errorMessage: "请传入正确的workflowName"]
        //            render JsonOutput.toJson(errors), status: 400
        //            return
        //        }

        def externalOpportunity
        if (opportunity?.externalId)
        {
            externalOpportunity = Opportunity.findByExternalId(opportunity?.externalId)
        }
        else
        {
            def errors = [errorCode: 4001, errorMessage: "订单externalId不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!externalOpportunity)
        {
            externalOpportunity = new Opportunity()
        }

        if (!opportunity.requestedAmount || !(opportunity.requestedAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(opportunity.requestedAmount) <= 0)
        {
            def errors = [errorCode: 4002, errorMessage: "请输入正确的贷款金额"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunity.loanAmount || !(opportunity.loanAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(opportunity.loanAmount) <= 0)
        {
            def errors = [errorCode: 4020, errorMessage: "请输入正确的评房总价"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (!opportunity.loanDuration || !(opportunity.loanDuration.matches(/^[0-9]\d*$/)) || Integer.parseInt(opportunity.loanDuration) <= 0)
        {
            def errors = [errorCode: 4003, errorMessage: "请输入正确的贷款期限"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        /*if (!opportunity.actualLoanDuration || !(opportunity.actualLoanDuration.matches(/^[0-9]\d*$/)) || Integer.parseInt(opportunity.actualLoanDuration) <= 0)
        {
            def errors = [errorCode: 4021, errorMessage: "请输入正确的实际贷款期限"]
            render JsonOutput.toJson(errors), status: 400
            return
        }*/

        //        externalOpportunity.serialNumber = opportunity.serialNumber
        if (opportunity.actualAmountOfCredit && opportunity.actualAmountOfCredit.matches(/^\d+(\.\d+)*$/))
        {
            externalOpportunity.actualAmountOfCredit = opportunity.actualAmountOfCredit?.toDouble()
        }
        externalOpportunity.requestedAmount = opportunity.requestedAmount?.toDouble()

        externalOpportunity.loanAmount = opportunity.loanAmount?.toDouble()
        if (opportunity.actualLoanAmount && opportunity.actualLoanAmount.matches(/^\d+(\.\d+)*$/))
        {
            externalOpportunity.actualLoanAmount = opportunity.actualLoanAmount?.toDouble()
        }
        externalOpportunity.loanDuration = opportunity.loanDuration?.toInteger()
        if (opportunity.actualLoanDuration && opportunity.actualLoanDuration.matches(/^[0-9]\d*$/))
        {
            externalOpportunity.actualLoanDuration = opportunity.actualLoanDuration?.toInteger()
        }
        if (opportunity.monthOfAdvancePaymentOfInterest && opportunity.monthOfAdvancePaymentOfInterest.matches(/^\d+(\.\d+)*$/))
        {
            externalOpportunity.monthOfAdvancePaymentOfInterest = opportunity.monthOfAdvancePaymentOfInterest?.toDouble()
        }

        //失败原因及失败原因说明
        def causeOfFailure = CauseOfFailure.findByName(opportunity?.causeOfFailure)
        if (causeOfFailure)
        {
            externalOpportunity.causeOfFailure = causeOfFailure
        }
        if (opportunity.descriptionOfFailure)
        {
            externalOpportunity.descriptionOfFailure = opportunity.descriptionOfFailure
        }

        externalOpportunity.memo = opportunity.memo

        def interestPaymentMethod = InterestPaymentMethod.findByName(opportunity?.interestPaymentMethod)
        if (interestPaymentMethod)
        {
            externalOpportunity.interestPaymentMethod = interestPaymentMethod
        }
        def principalPaymentMethod = PrincipalPaymentMethod.findByName(opportunity?.principalPaymentMethod)
        if (principalPaymentMethod)
        {
            externalOpportunity.principalPaymentMethod = principalPaymentMethod
        }

        //区域名称
        def territory = Territory.findByName(opportunity?.territoryName)
        if (territory)
        {
            externalOpportunity.territory = territory
        }
        else
        {
            def errors = [errorCode: 4026, errorMessage: "请输入正确的区域名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }


        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd")

        if (opportunity.period && opportunity.period.matches(/^[0-9]\d*$/))
        {
            externalOpportunity.period = opportunity.period?.toInteger()
        }
        else
        {
            externalOpportunity.period = 0
        }
        if (opportunity?.startTime)
        {
            externalOpportunity.startTime = sdf1.parse(opportunity?.startTime)
        }
        if (opportunity?.endTime)
        {
            externalOpportunity.endTime = sdf1.parse(opportunity?.endTime)
        }

        def city = City.findByName(opportunity?.cityName)
        if (city)
        {
            externalOpportunity.city = city
        }
        else
        {
            def errors = [errorCode: 4025, errorMessage: "请输入正确的城市名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def product = Product.findByName(opportunity?.productName)
        if (product)
        {
            externalOpportunity.product = product
        }
        else
        {
            def errors = [errorCode: 4024, errorMessage: "请输入正确的产品名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        externalOpportunity.externalId = opportunity.externalId

        if (opportunity?.actualLendingDate)
        {
            externalOpportunity.actualLendingDate = sdf1.parse(opportunity?.actualLendingDate)
        }
        if (opportunity?.estimatedLendingDate)
        {
            externalOpportunity.estimatedLendingDate = sdf1.parse(opportunity?.estimatedLendingDate)
        }
        if (opportunity.isTest)
        {
            externalOpportunity.isTest = opportunity.isTest == "true" ? true : false
        }

        if (opportunity?.dateOfMortgage)
        {
            externalOpportunity.dateOfMortgage = sdf1.parse(opportunity?.dateOfMortgage)
        }
        if (opportunity?.dateOfNotarization)
        {
            externalOpportunity.dateOfNotarization = sdf1.parse(opportunity?.dateOfNotarization)
        }

        if (opportunity.complianceChecking)
        {
            externalOpportunity.complianceChecking = opportunity.complianceChecking == "true" ? true : false
        }

        if (opportunity?.externalModifiedDate)
        {
            externalOpportunity.externalModifiedDate = sdf1.parse(opportunity?.externalModifiedDate)
        }

        //销售
        def user = User.findByFullName(opportunity?.userName)
        if (user)
        {
            externalOpportunity.user = user
        }
        else
        {
            def errors = [errorCode: 4004, errorMessage: "请输入正确的销售名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        //机构名称
        def account = Account.findByName(opportunity?.accountName)
        if (account)
        {
            externalOpportunity.account = account
        }
        else
        {
            def errors = [errorCode: 4005, errorMessage: "请输入正确的机构名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def productAccount
        if (product)
        {
            def temp = account
            while (temp)
            {
                productAccount = ProductAccount.findByAccountAndProduct(temp, product)
                if (productAccount)
                {
                    break
                }
                else
                {
                    temp = temp?.parent
                }
            }
        }
        externalOpportunity.productAccount = productAccount

        if (externalOpportunity.validate())
        {
            externalOpportunity.save()
        }
        else
        {
            println externalOpportunity.errors
        }
        //经纪人
        if (opportunity?.contact)
        {
            //            for (
            //                    def externalContact in
            //                            opportunity?.contact)
            //            {
            def externalContact = opportunity?.contact
            if (externalContact?.cellphone)
            {
                if (!(externalContact?.cellphone.matches(/^1\d{10}$/)))
                {
                    def errors = [errorCode: 4076, errorMessage: "经纪人手机号格式不正确，请重新输入"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }
            def contact
            if (externalContact?.externalId)
            {
                contact = Contact.findByCellphoneAndType(externalContact?.cellphone, 'Agent')
            }
            else
            {
                def errors = [errorCode: 4077, errorMessage: "经纪人externalId不能为空"]
                render JsonOutput.toJson(errors), status: 400
                return
            }

            if (!contact)
            {
                contact = new Contact()
            }
            contact.fullName = externalContact?.fullName
            contact.cellphone = externalContact?.cellphone
            contact.idNumber = externalContact?.idNumber
            def contactCity = City.findByName(externalContact?.cityName)
            if (contactCity)
            {
                contact.city = contactCity
            }
            else
            {
                def errors = [errorCode: 4078, errorMessage: "经纪人城市不正确"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            contact.externalId = externalContact?.externalId
            contact.type = 'Agent'
            contact.user = user
            if (contact.validate())
            {
                contact.save()
            }
            else
            {
                println contact.errors
            }
            //经纪人
            externalOpportunity.contact = contact
            externalOpportunity.save()
            if (externalContact?.account)
            {
                if (!Account.find("from Account where name = '${externalContact?.account?.name}'"))
                {
                    def accountNew = new Account(name: "${externalContact?.account?.name}", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"), externalId: "${externalContact?.account?.externalId}")
                    accountNew.save()
                    contact.account = accountNew
                    contact.save()
                }
                else
                {
                    def account1 = Account.findByName(externalContact?.account?.name)
                    account1?.externalId = externalContact?.account?.externalId
                    account1?.save()
                }
            }
            //            }
        }
        //        else
        //        {
        //            def errors = [errorCode: 4079, errorMessage: "请添加经纪人信息"]
        //            render JsonOutput.toJson(errors), status: 400
        //            return
        //        }
        //联系人
        if (opportunity?.contacts)
        {
            for (
                def externalContact in
                    opportunity?.contacts)
            {
                String idNumber = externalContact?.idNumber
                if (idNumber)
                {
                    idNumber = idNumber.replace('x', 'X')
                }
                if (!idNumber || !contactService.verifyIdNumber(idNumber))
                {
                    def errors = [errorCode: 4006, errorMessage: "请输入${externalContact?.type}正确的身份证号"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else if (!externalContact?.fullName || !(externalContact?.fullName.matches(/^[\u2190-\u9fff]{1,10}$|^[\dA-Za-z]{1,20}$/)))
                {
                    def errors = [errorCode: 4007, errorMessage: "请输入${externalContact?.type}正确的姓名"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else if (externalContact?.cellphone)
                {
                    if (!(externalContact?.cellphone.matches(/^1\d{10}$/)))
                    {
                        def errors = [errorCode: 4008, errorMessage: "${externalContact?.type}手机号格式不正确，请重新输入"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                }
                if (!externalContact?.maritalStatus || !(externalContact?.maritalStatus in ["未婚", "已婚", "再婚", "离异", "丧偶"]))
                {
                    def errors = [errorCode: 4009, errorMessage: "请选择婚姻状况"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                def contact
                if (externalContact?.externalId)
                {
                    if (externalContact?.type =='借款人'&&externalOpportunity?.lender){
                        contact = externalOpportunity?.lender
                    }else {
                        contact = Contact.findByExternalId(externalContact?.externalId)
                    }
                }
                else
                {
                    def errors = [errorCode: 4010, errorMessage: "${externalContact?.type}联系人externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                //                if(!externalContact?.connectedContact)
                //                {
                //                    def errors = [errorCode: 4075, errorMessage: "${externalContact?.type}关联人externalId不能为空"]
                //                    render JsonOutput.toJson(errors), status: 400
                //                    return
                //                    break
                //                }

                if (!contact)
                {
                    contact = new Contact()
                }
                contact.fullName = externalContact?.fullName
                contact.cellphone = externalContact?.cellphone
                contact.idNumber = idNumber
                contact.maritalStatus = externalContact?.maritalStatus
                def contactCity = City.findByName(externalContact?.cityName)
                if (contactCity)
                {
                    contact.city = contactCity
                }
                else
                {
                    def errors = [errorCode: 4027, errorMessage: "${externalContact?.type}联系人城市不正确"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                /*def level = ContactLevel.findByNameAndActive(externalContact?.level, true)
                if (level)
                {
                    contact.level = level
                }
                else
                {
                    def errors = [errorCode: 4028, errorMessage: "${externalContact?.type}客户级别不正确"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }*/
                //测试回调信息修改评级问题
                println("-----Nagelan:原始评级"+contact?.level?.name+"--新的评级"+externalContact?.lever)
                contact.identityType = ContactIdentityType.findByName(externalContact?.identityType)
                contact.country = Country.findByName(externalContact?.country)
                contact.profession = ContactProfession.findByName(externalContact?.profession)
                contact.externalId = externalContact?.externalId
                if (externalContact?.type =='借款人'){
                    contact.type = 'Client'
                }

                if (contact.validate())
                {
                    contact.save()
                }
                else
                {
                    println contact.errors
                }
                def type = OpportunityContactType.findByName(externalContact?.type)
                def opportunityContact = OpportunityContact.findByOpportunityAndContact(externalOpportunity, contact)
                if (!opportunityContact)
                {
                    opportunityContact = new OpportunityContact()
                    if (type)
                    {
                        opportunityContact.type = type
                    }
                }
                if (type && externalContact?.connectedType)
                {
                    opportunityContact.type = type
                }
                opportunityContact.contact = contact
                opportunityContact.opportunity = externalOpportunity
                if (opportunityContact.validate())
                {
                    opportunityContact.save()
                }
                else
                {
                    def errors = [errorCode: 4074, errorMessage: "请添加联系人信息"+opportunityContact.errors]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    println opportunityContact.errors
                }

                if (externalContact?.type == '借款人')
                {
                    externalOpportunity.fullName = opportunityContact.contact.fullName
                    externalOpportunity.cellphone = opportunityContact.contact.cellphone
                    externalOpportunity.idNumber = opportunityContact.contact.idNumber
                    externalOpportunity.maritalStatus = opportunityContact.contact.maritalStatus
                    externalOpportunity.lender = opportunityContact.contact
                    externalOpportunity.save()
                }
            }
            for (
                def externalContact in
                    opportunity?.contacts)
            {
                if (externalContact?.connectedContact)
                {
                    def contact = Contact.findByExternalId(externalContact?.externalId)
                    def opportunityContact = OpportunityContact.findByOpportunityAndContact(externalOpportunity, contact)
                    if (opportunityContact){
                        def connectedContact = Contact.findByExternalId(externalContact?.connectedContact)
                        opportunityContact.connectedContact = connectedContact
                        if (opportunityContact?.type?.name == '借款人')
                        {
                            opportunityContact.connectedType = OpportunityContactType.findByName("本人")
                        }
                        else
                        {
                            opportunityContact.connectedType = opportunityContact?.type
                        }
                        if (externalContact?.connectedType)
                        {
                            def connectedType = OpportunityContactType.findByName(externalContact?.connectedType)
                            opportunityContact.connectedType = connectedType
                        }
                        opportunityContact.save()
                    }
                }
            }
        }
        else
        {
            def errors = [errorCode: 4029, errorMessage: "请添加联系人信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //附件
        if (opportunity?.attachments)
        {
            for (
                def externalAttachments in
                    opportunity?.attachments)
            {
                def attachments
                if (externalAttachments?.externalId)
                {
                    attachments = Attachments.findByExternalId(externalAttachments?.externalId)
                }
                else
                {
                    def errors = [errorCode: 4011, errorMessage: "${externalAttachments?.type}附件externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!attachments)
                {
                    attachments = new Attachments()
                }

                def attachmentType = AttachmentType.findByName(externalAttachments?.type)
                if (attachmentType)
                {
                    attachments.type = attachmentType
                }
                else
                {
                    def errors = [errorCode: 4022, errorMessage: "${externalAttachments?.type}类型名称有误"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                // attachments.fileName = externalAttachments?.fileName
                attachments.fileName = externalAttachments?.fileUrl
                attachments.fileUrl = externalAttachments?.fileUrl
                attachments.description = externalAttachments?.description
                if (externalAttachments?.displayOrder && externalAttachments.displayOrder.matches(/^[0-9]\d*$/))
                {
                    attachments.displayOrder = externalAttachments?.displayOrder?.toInteger()
                }

                def createBy = User.findByFullName(externalAttachments?.createByUserName)
                if (createBy)
                {
                    attachments.createBy = createBy
                    attachments.modifyBy = createBy
                }
                else
                {
                    def errors = [errorCode: 4036, errorMessage: "附件${externalAttachments?.type}创建者有误"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                attachments.externalId = externalAttachments?.externalId
                attachments.opportunity = externalOpportunity
                if (attachments.validate())
                {
                    attachments.save()
                }
                else
                {
                    println attachments.errors
                }
            }
        }
        else
        {
            def errors = [errorCode: 4030, errorMessage: "请添加附件信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //房产
        if (opportunity?.collaterals)
        {
            for (
                def externalCollateral in
                    opportunity?.collaterals)
            {
                def collateral
                if (externalCollateral?.externalId)
                {
                    collateral = Collateral.findByExternalId(externalCollateral?.externalId)
                }
                else
                {
                    def errors = [errorCode: 4012, errorMessage: "房产externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!collateral)
                {
                    collateral = Collateral.findByPropertySerialNumberAndOpportunity(externalCollateral?.propertySerialNumber, externalOpportunity)
                }
                if (!collateral)
                {
                    collateral = new Collateral()
                }
                //新需求修改by：nagelan
                //if ((externalCollateral?.mortgageType in ["二抵", "一抵转单"]))
                if ((externalCollateral?.mortgageType in ["二抵"]))
                {
                    if (!externalCollateral?.firstMortgageAmount || !(externalCollateral?.firstMortgageAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.firstMortgageAmount) <= 0)
                    {
                        def errors = [errorCode: 4013, errorMessage: "请输入正确的一抵金额"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                    if (!externalCollateral?.typeOfFirstMortgage || !(externalCollateral?.typeOfFirstMortgage in ["银行类", "非银行类"]))
                    {
                        def errors = [errorCode: 4014, errorMessage: "请选择一抵类型"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                }
                if (externalCollateral?.mortgageType == "二抵转单")
                {
                    if (!externalCollateral?.firstMortgageAmount || !(externalCollateral?.firstMortgageAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.firstMortgageAmount) <= 0)
                    {
                        def errors = [errorCode: 4013, errorMessage: "请输入正确的一抵金额"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                    if (!externalCollateral?.typeOfFirstMortgage || !(externalCollateral?.typeOfFirstMortgage in ["银行类", "非银行类"]))
                    {
                        def errors = [errorCode: 4014, errorMessage: "请选择一抵类型"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                    //新需求修改by：nagelan
                    /*if (!externalCollateral?.secondMortgageAmount || !(externalCollateral?.secondMortgageAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.secondMortgageAmount) <= 0)
                    {
                        def errors = [errorCode: 4015, errorMessage: "请输入正确的二抵金额"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }*/
                }

                if (!(externalCollateral?.city))
                {
                    def errors = [errorCode: 4016, errorMessage: "请输入房产城市"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.district))
                {
                    def errors = [errorCode: 4052, errorMessage: "请输入房产区域"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.projectName))
                {
                    def errors = [errorCode: 4053, errorMessage: "请输入房产小区"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.building))
                {
                    def errors = [errorCode: 4054, errorMessage: "请输入房产楼栋"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.floor))
                {
                    def errors = [errorCode: 4055, errorMessage: "请输入房产所在楼层"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.totalFloor))
                {
                    def errors = [errorCode: 4056, errorMessage: "请输入房产总楼层"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.roomNumber))
                {
                    def errors = [errorCode: 4057, errorMessage: "请输入房产房间号"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.area))
                {
                    def errors = [errorCode: 4058, errorMessage: "请输入房产面积"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.address))
                {
                    def errors = [errorCode: 4059, errorMessage: "请输入房产地址"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.orientation))
                {
                    def errors = [errorCode: 4060, errorMessage: "请输入房产朝向"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!externalCollateral?.houseType || !(HouseType.findByName(externalCollateral?.houseType) in HouseType.findAll()))
                {
                    def errors = [errorCode: 4112, errorMessage: "请选择住宅类型"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                if (!(externalCollateral?.assetType))
                {
                    def errors = [errorCode: 4062, errorMessage: "请输入房产性质"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.projectType))
                {
                    def errors = [errorCode: 4064, errorMessage: "请输入房产立项类型"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                if (!(externalCollateral?.specialFactors in ['无', '复式', 'LOFT', '跃层', '一层赠送', '临湖', '楼王', '临街', '顶楼带阁楼', '看海', '一层赠送半地下']))
                {
                    def errors = [errorCode: 4048, errorMessage: "请输入正确的房产特殊因素"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                collateral.assetType = externalCollateral?.assetType
                collateral.projectName = externalCollateral?.projectName
                /*---------------------------------------------------------------------*/
                def collateralCity = City.findByName(externalCollateral?.city)
                if (collateralCity)
                {
                    collateral.city = collateralCity
                }
                collateral.district = externalCollateral?.district
                collateral.address = externalCollateral?.address
                collateral.floor = externalCollateral?.floor
                collateral.orientation = externalCollateral?.orientation
                collateral.area = externalCollateral?.area?.toDouble()
                if (!externalCollateral?.building)
                {
                    def errors = [errorCode: 4037, errorMessage: "请输入正确的楼栋信息"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    collateral.building = externalCollateral?.building
                }
                collateral.unit = externalCollateral?.unit
                collateral.totalFloor = externalCollateral?.totalFloor
                collateral.roomNumber = externalCollateral?.roomNumber
                collateral.houseType = externalCollateral?.houseType
                collateral.specialFactors = externalCollateral?.specialFactors
                if (!externalCollateral?.unitPrice || !(externalCollateral?.unitPrice.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.unitPrice) <= 0)
                {
                    def errors = [errorCode: 4038, errorMessage: "请输入正确的房产单价"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    if (!collateral.unitPrice)
                    {
                        collateral.unitPrice = externalCollateral?.unitPrice?.toDouble() * 10000
                    }
                }

                if (!externalCollateral?.totalPrice || !(externalCollateral?.totalPrice.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.totalPrice) <= 0)
                {
                    def errors = [errorCode: 4039, errorMessage: "请输入正确的房产总价"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    if (!collateral.totalPrice)
                    {
                        collateral.totalPrice = externalCollateral?.totalPrice?.toDouble()
                    }
                }


                if (externalCollateral?.fastUnitPrice && externalCollateral?.fastUnitPrice.matches(/^\d+(\.\d+)*$/) && Double.parseDouble(externalCollateral?.fastUnitPrice) >= 0)
                {
                    if (!collateral.fastUnitPrice || collateral.fastUnitPrice ==0)
                    {
                        collateral.fastUnitPrice = externalCollateral?.fastUnitPrice?.toDouble()

                        //PV推房产证
                        def ats = []
                        def result1 = [:]
                        def attachmentsList = Attachments.findAllByOpportunityAndType(externalOpportunity, AttachmentType.findByName('房产证'))
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

                        //                        if (!resultJson)
                        //                        {
                        //                            def errors = [errorCode: 4796, errorMessage: "调用评房反馈服务上传图片失败，请稍后重试"]
                        //                            render JsonOutput.toJson(errors), status: 400
                        //                            return
                        //                            break
                        //                        }

                        // PV推送外访值
                        propertyValuationProviderService.updateFastUnitPrice(collateral?.externalId, externalCollateral?.fastUnitPrice)
                    }
                }

                if (!externalCollateral?.appliedTotalPrice || !(externalCollateral?.appliedTotalPrice.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.appliedTotalPrice) <= 0)
                {
                    def errors = [errorCode: 4039, errorMessage: "请输入正确的房产期望价格"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    collateral.appliedTotalPrice = externalCollateral?.appliedTotalPrice?.toDouble()
                }


                collateral.externalId = externalCollateral?.externalId

                def projectType = CollateralProjectType.findByName(externalCollateral?.projectType)
                collateral.projectType = projectType
                if (externalCollateral?.buildTime)
                {
                    collateral.buildTime = sdf1.parse(externalCollateral?.buildTime)
                }
                else
                {
                    def errors = [errorCode: 4040, errorMessage: "请输入正确的房产建成时间"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                //抵押率
                /*if (externalCollateral?.loanToValue && externalCollateral?.loanToValue.matches(/^\d+(\.\d+)*$/))
                {
                    collateral.loanToValue = externalCollateral?.loanToValue?.toDouble()
                }*/
                collateral.propertySerialNumber = externalCollateral?.propertySerialNumber
                def typeOfFirstMortgage = TypeOfFirstMortgage.findByName(externalCollateral?.typeOfFirstMortgage)
                collateral.typeOfFirstMortgage = typeOfFirstMortgage

                if ((externalCollateral?.mortgageType == "二抵")){
                    collateral.firstMortgageAmount = externalCollateral?.firstMortgageAmount?.toDouble()
                    collateral.secondMortgageAmount = externalOpportunity?.actualAmountOfCredit?.toDouble()
                }else if ((externalCollateral?.mortgageType == "一抵")){
                    collateral.firstMortgageAmount = externalOpportunity?.actualAmountOfCredit?.toDouble()
                    //collateral.secondMortgageAmount = externalCollateral?.secondMortgageAmount?.toDouble()
                }
                /*collateral.firstMortgageAmount = externalCollateral?.firstMortgageAmount?.toDouble()
                collateral.secondMortgageAmount = externalCollateral?.secondMortgageAmount?.toDouble()*/

                def mortgageType = MortgageType.findByName(externalCollateral?.mortgageType)
                collateral.mortgageType = mortgageType

                def region = CollateralRegion.findByName(externalCollateral?.region)
                collateral.region = region

                collateral.atticArea = externalCollateral?.atticArea?.toDouble()
                collateral.postcode = externalCollateral?.postcode

                //产调
                collateral.propertyOwnershipInvestigationStatus = externalCollateral?.propertyOwnershipInvestigationStatus
                collateral.propertyOwnership = externalCollateral?.propertyOwnership
                collateral.propertySealedup = externalCollateral?.propertySealedup == "true" ? true : false
                collateral.propertySealedupReason = externalCollateral?.propertySealedupReason == "true" ? true : false
                if (externalCollateral?.propertySealedupDate)
                {
                    collateral.propertySealedupDate = sdf1.parse(externalCollateral?.propertySealedupDate)
                }
                collateral.newHouse = externalCollateral?.newHouse == "true" ? true : false
                if (externalCollateral?.propertyCertificateHoldDate)
                {
                    collateral.propertyCertificateHoldDate = sdf1.parse(externalCollateral?.propertyCertificateHoldDate)
                }

                //外访预警
                collateral.seventyYearsElder = externalCollateral?.seventyYearsElder == "true" ? true : false
                collateral.specialPopulation = externalCollateral?.specialPopulation == "true" ? true : false
                collateral.propertyStructure = externalCollateral?.propertyStructure == "true" ? true : false
                if (externalCollateral?.partitionNumber && externalCollateral?.partitionNumber.matches(/^[0-9]\d*$/))
                {
                    collateral.partitionNumber = externalCollateral?.partitionNumber?.toInteger()
                }
                collateral.propertyLivingCondition = externalCollateral?.propertyLivingCondition == "true" ? true : false
                collateral.marksOfFire = externalCollateral?.marksOfFire == "true" ? true : false
                collateral.topFloorWithWaterLeakage = externalCollateral?.topFloorWithWaterLeakage == "true" ? true : false
                collateral.noiseEnvironment = externalCollateral?.noiseEnvironment == "true" ? true : false
                collateral.tubeShapedApartment = externalCollateral?.tubeShapedApartment == "true" ? true : false
                collateral.housingDemolition = externalCollateral?.housingDemolition == "true" ? true : false
                collateral.actualUsageIsOffice = externalCollateral?.actualUsageIsOffice == "true" ? true : false
                collateral.mostOfFloorsAreOffices = externalCollateral?.mostOfFloorsAreOffices == "true" ? true : false
                collateral.undisclosedTenant = externalCollateral?.undisclosedTenant == "true" ? true : false
                collateral.basementOrHighVoltage = externalCollateral?.basementOrHighVoltage == "true" ? true : false
                collateral.dangerousAnimal = externalCollateral?.dangerousAnimal == "true" ? true : false
                collateral.doubtfulMarriageStatus = externalCollateral?.doubtfulMarriageStatus == "true" ? true : false

                //抵押物其它情况
                collateral.businessSophistication = externalCollateral?.businessSophistication
                collateral.traffic = externalCollateral?.traffic
                collateral.hospital = externalCollateral?.hospital
                collateral.schoolDistrictType = externalCollateral?.schoolDistrictType
                collateral.kindergarten = externalCollateral?.kindergarten
                collateral.primarySchool = externalCollateral?.primarySchool
                collateral.middleSchool = externalCollateral?.middleSchool
                collateral.highSchool = externalCollateral?.highSchool
                if (externalCollateral?.numberOfBanks && externalCollateral?.numberOfBanks.matches(/^[0-9]\d*$/))
                {
                    collateral.numberOfBanks = externalCollateral?.numberOfBanks?.toInteger()
                }
                collateral.supermarket = externalCollateral?.supermarket == "true" ? true : false
                collateral.farmersMarket = externalCollateral?.farmersMarket == "true" ? true : false
                collateral.landAgency = externalCollateral?.landAgency
                collateral.residentialGreen = externalCollateral?.residentialGreen
                collateral.parkingSpace = externalCollateral?.parkingSpace
                collateral.buildingAppreance = externalCollateral?.buildingAppreance
                collateral.numberOfElevators = externalCollateral?.numberOfElevators
                collateral.gymEquipment = externalCollateral?.gymEquipment == "true" ? true : false
                collateral.communitySize = externalCollateral?.communitySize
                if (externalCollateral?.houseUsageStats in ["自住", "空置", "出借", "出租"])
                {
                    collateral.houseUsageStats = externalCollateral?.houseUsageStats
                }
                collateral.decoration = externalCollateral?.decoration

                //其它  土地使用年限
                /*if (externalCollateral?.landUsageTerm && externalCollateral?.landUsageTerm.matches(/^[0-9]\d*$/))
                {
                    collateral.landUsageTerm = externalCollateral?.landUsageTerm?.toInteger()
                }*/
                if (externalCollateral?.houseOrigin in ["购置", "继承", "分割", "赠与", "产权无争议", "全额付款", "更换产证"]){
                    collateral.houseOrigin = externalCollateral?.houseOrigin
                }
                collateral.tenantType = externalCollateral?.tenantType
                collateral.status = "Completed"
                collateral.opportunity = externalOpportunity

                if (collateral.validate())
                {
                    collateral.save()
                }
                else
                {
                    def errors = [errorCode: 4031, errorMessage: "房产信息校验不通过"+collateral.errors]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    println collateral.errors
                }

            }
        }
        else
        {
            def errors = [errorCode: 4031, errorMessage: "请添加房产信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        /*if(opportunity?.interest)
        {
            for(def externalInterest in opportunity?.interest)
            {
                def interest
                if(externalInterest?.externalId)
                {
                    interest = OpportunityProduct.findByExternalId(externalInterest?.externalId)
                }
                else
                {
                    def errors = [errorCode: 4017, errorMessage: "息与费externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if(!interest)
                {
                    interest = new OpportunityProduct()
                }
                def productInterestType = ProductInterestType.findByName(externalInterest?.productInterestType)
                interest.productInterestType = productInterestType
                interest.installments = externalInterest?.installments == "true" ? true : false

                if (!externalInterest?.rate || !(externalInterest?.rate.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalInterest?.rate) <= 0)
                {
                    def errors = [errorCode: 4041, errorMessage: "请输入正确的费率"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    interest.rate = externalInterest?.rate?.toDouble()
                }
                if (!externalInterest?.monthes || !(externalInterest?.monthes.matches(/^[0-9]\d*$/)) || Integer.parseInt(externalInterest?.monthes) <= 0)
                {
                    def errors = [errorCode: 4042, errorMessage: "请输入正确的费率期限"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    interest.monthes = externalInterest?.monthes?.toInteger()
                }

                if (!externalInterest?.firstPayMonthes || !(externalInterest?.firstPayMonthes.matches(/^[0-9]\d*$/)) || Integer.parseInt(externalInterest?.firstPayMonthes) < 0)
                {
                    def errors = [errorCode: 4043, errorMessage: "请输入正确的预收取月份数"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    interest.firstPayMonthes = externalInterest?.firstPayMonthes?.toInteger()
                }

                def createByUser = User.findByFullName(externalInterest?.createByUserName)
                if(createByUser)
                {
                    interest.createBy = createByUser
                }
                else
                {
                    def errors = [errorCode: 4044, errorMessage: "请输入正确的费用创建人"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                def modifyByUser = User.findByFullName(externalInterest?.modifyByUserName)
                if(modifyByUser)
                {
                    interest.modifyBy = modifyByUser
                }
                else
                {
                    def errors = [errorCode: 4045, errorMessage: "请输入正确的费用修改人"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                def contractType = ContractType.findByName(externalInterest?.contractType)
                if(contractType)
                {
                    interest.contractType = contractType
                }
                else
                {
                    def errors = [errorCode: 4046, errorMessage: "请输入正确的费用所属合同类型"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                interest.externalId = externalInterest.externalId
                interest.opportunity = externalOpportunity
                interest.product = externalOpportunity.productAccount
                if(interest.validate())
                {
                    interest.save()
                }
                else
                {
                    println interest.errors
                }
            }
        }
        else
        {
            def errors = [errorCode: 4032, errorMessage: "请添加费用信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }*/
        //账号
        if (opportunity?.bankAccounts)
        {
            for (
                def externalBankAccount in
                    opportunity?.bankAccounts)
            {
                def bankAccount
                if (externalBankAccount?.externalId)
                {
                    bankAccount = BankAccount.findByExternalId(externalBankAccount?.externalId)
                }
                else
                {
                    def errors = [errorCode: 4018, errorMessage: "账户externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!externalBankAccount?.bank || !(Bank.findByName(externalBankAccount?.bank)))
                {
                    def errors = [errorCode: 4051, errorMessage: "账户银行不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!bankAccount)
                {
                    bankAccount = new BankAccount()
                }
                bankAccount.numberOfAccount = externalBankAccount?.numberOfAccount
                if (bankAccount.numberOfAccount.length()>21){
                    def errors = [errorCode: 4090, errorMessage: "银行卡号过长"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                bankAccount.name = externalBankAccount?.name
                bankAccount.cellphone = externalBankAccount?.cellphone
                def certificateType = ContactIdentityType.findByName(externalBankAccount?.certificateType)
                bankAccount.certificateType = certificateType
                bankAccount.numberOfCertificate = externalBankAccount?.numberOfCertificate
                bankAccount.active = externalBankAccount?.active == "true" ? true : false
                bankAccount.bankBranch = externalBankAccount?.bankBranch
                bankAccount.bankAddress = externalBankAccount?.bankAddress
                bankAccount.externalId = externalBankAccount?.externalId
                bankAccount.bank = Bank.findByName(externalBankAccount?.bank)
                bankAccount.paymentChannel = PaymentChannel.findByName("广银联")
                if (bankAccount.validate())
                {
                    bankAccount.save()
                }
                else
                {
                    println bankAccount.errors
                }
                def bankAccountType = OpportunityBankAccountType.findByName(externalBankAccount?.type)
                def opportunityBankAccount = OpportunityBankAccount.findByOpportunityAndTypeAndBankAccount(externalOpportunity, bankAccountType, bankAccount)
                if (!opportunityBankAccount)
                {
                    opportunityBankAccount = new OpportunityBankAccount()
                }
                opportunityBankAccount.type = bankAccountType
                opportunityBankAccount.bankAccount = bankAccount
                opportunityBankAccount.opportunity = externalOpportunity
                if (opportunityBankAccount.validate())
                {
                    opportunityBankAccount.save()
                }
                else
                {
                    println opportunityBankAccount.errors
                }
            }
        }
        //合同
        if (opportunity?.contracts)
        {
            for (
                def externalContract in
                    opportunity?.contracts)
            {
                def contract
                if (externalContract?.externalId)
                {
                    contract = Contract.findByExternalId(externalContract?.externalId)
                }
                else
                {
                    def errors = [errorCode: 4019, errorMessage: "合同externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                if (!externalContract?.serialNumber)
                {
                    def errors = [errorCode: 4065, errorMessage: "合同编号不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!contract && Contract.findBySerialNumber(externalContract?.serialNumber))
                {
                    def errors = [errorCode: 4066, errorMessage: "合同编号不能为重复"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                if (!contract)
                {
                    contract = new Contract()
                }
                contract.serialNumber = externalContract?.serialNumber
                def contractType = ContractType.findByName(externalContract?.type)
                if (contractType)
                {
                    contract.type = contractType
                }
                else
                {
                    def errors = [errorCode: 4047, errorMessage: "请输入正确的合同类型"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                contract.externalId = externalContract?.externalId
                if (contract.validate())
                {
                    contract.save()
                }
                else
                {
                    println contract.errors
                }
                def opportunityContract = OpportunityContract.findByOpportunityAndContract(externalOpportunity, contract)
                if (!opportunityContract)
                {
                    opportunityContract = new OpportunityContract()
                }
                opportunityContract.contract = contract
                opportunityContract.opportunity = externalOpportunity
                if (opportunityContract.validate())
                {
                    opportunityContract.save()
                }
                else
                {
                    println opportunityContract.errors
                }
            }
        }
        /*else
        {
            def errors = [errorCode: 4034, errorMessage: "请添加合同信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }*/
        //订单初始化
        opportunityService.initOpportunity(externalOpportunity, workflow)

        if (externalOpportunity?.stage?.name in ['评房申请已提交', '价格待确认', '评房已完成'])
        {
            def opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? order by executionSequence asc", [externalOpportunity?.id])
            if (opportunityFlow)
            {
                externalOpportunity.stage = opportunityFlow?.stage
                externalOpportunity.save flush: true
            }
        }

        render opportunity as JSON
        return
    }

    @Secured(['permitAll'])
    @Transactional
    def submitOpportunity()
    {
        def json = request.JSON

        println "************************* submitOpportunity ***************************"
        println json

        def externalId = json.externalId
        def stage = json.stage
        if (externalId && stage)
        {
            def externalOpportunity = Opportunity.findByExternalId(externalId)
            if (externalOpportunity)
            {
                def opportunityStage = OpportunityStage.findByName(stage)
                if (opportunityStage)
                {
                    def opportunityFlow = OpportunityFlow.findByOpportunityAndStage(externalOpportunity, externalOpportunity?.stage)
                    if (opportunityFlow)
                    {
                        opportunityFlow.endTime = new Date()
                        opportunityFlow.processed = true
                        opportunityFlow.save()
                    }

                    externalOpportunity.stage = opportunityStage
                    externalOpportunity.save()

                    opportunityFlow = OpportunityFlow.findByOpportunityAndStage(externalOpportunity, externalOpportunity?.stage)
                    if (opportunityFlow)
                    {
                        opportunityFlow.startTime = new Date()
                        opportunityFlow.save()
                    }
                }
                else
                {
                    def errors = [errorCode: 4067, errorMessage: "未找到对应订单阶段"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }
            else
            {
                def errors = [errorCode: 4068, errorMessage: "未找到对应订单"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        else
        {
            def errors = [errorCode: 4070, errorMessage: "externalId，stage参数不全"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def setOpportunityStatus()
    {
        def json = request.JSON

        println "************************* setOpportunityStatus ***************************"
        println json

        def externalId = json.externalId
        def status = json.status
        if (externalId && status)
        {
            def externalOpportunity = Opportunity.findByExternalId(externalId)
            if (externalOpportunity)
            {
                if (status in ['Pending', 'Failed', 'Completed'])
                {
                    externalOpportunity.status = status
                    externalOpportunity.save()
                }
                else
                {
                    def errors = [errorCode: 4069, errorMessage: "订单状态不合法"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }
            else
            {
                def errors = [errorCode: 4068, errorMessage: "未找到对应订单"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def valuation()
    {
        def json = request.JSON
        def jsonOpportunityData = json.opportunity
        jsonOpportunityData = JSON.parse(jsonOpportunityData)

        println "********** valuation *************"

        def sessionId = jsonOpportunityData.sessionId
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
        def externalOpportunity = jsonOpportunityData.opportunity
        def opportunityExternalId = externalOpportunity?.externalId
        def requestedAmount = externalOpportunity?.requestedAmount
        def workflowName = externalOpportunity?.workflowName

        def workflow = OpportunityWorkflow.findByNameAndActive(workflowName, true)
        //        if (!workflow)
        //        {
        //            def errors = [errorCode: 4001, errorMessage: "请传入正确的workflowName"]
        //            render JsonOutput.toJson(errors), status: 400
        //            return
        //        }
        if (!opportunityExternalId)
        {
            def errors = [errorCode: 4001, errorMessage: "订单externalId不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (requestedAmount)
        {
            if (!(requestedAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(requestedAmount) <= 0)
            {
                def errors = [errorCode: 4002, errorMessage: "请输入正确的贷款金额"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }

        String city = externalOpportunity?.collateral?.city
        //城市
        String district = externalOpportunity?.collateral?.district
        //所属区县
        String projectName = externalOpportunity?.collateral?.projectName
        //小区名称
        String building = externalOpportunity?.collateral?.building
        //楼栋信息
        String floor = externalOpportunity?.collateral?.floor
        //楼层
        String roomNumber = externalOpportunity?.collateral?.roomNumber
        //户号
        String totalFloor = externalOpportunity?.collateral?.totalFloor
        //总楼层
        String area = externalOpportunity?.collateral?.area
        //住宅面积
        String address = externalOpportunity?.collateral?.address
        //住房地址
        String houseType = externalOpportunity?.collateral?.houseType
        //物业类型
        String specialFactors = externalOpportunity?.collateral?.specialFactors
        //特殊因素
        String assetType = externalOpportunity?.collateral?.assetType
        String atticArea = externalOpportunity?.collateral?.atticArea
        //阁楼、跃层面积
        String propertySerialNumber = externalOpportunity?.collateral?.propertySerialNumber
        //房产证编号

        if (!city)
        {
            def errors = [errorCode: 4101, errorMessage: "请选择所在城市"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!district)
        {
            def errors = [errorCode: 4102, errorMessage: "请选择所属区县"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!projectName)
        {
            def errors = [errorCode: 4103, errorMessage: "请填写小区名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (!building)
        {
            def errors = [errorCode: 4104, errorMessage: "请输入楼栋信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!floor || !(floor.matches(/^-?[1-9]\d*$/)))
        {
            def errors = [errorCode: 4105, errorMessage: "请输入所在楼层"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!roomNumber)
        {
            def errors = [errorCode: 4106, errorMessage: "请输入户号"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!totalFloor || !(totalFloor.matches(/^[1-9]\d*$/)))
        {
            def errors = [errorCode: 4107, errorMessage: "请输入总楼层"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (Integer.parseInt(floor) > Integer.parseInt(totalFloor))
        {
            def errors = [errorCode: 4108, errorMessage: "所在楼层必须小于等于总楼层"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!area || !(area.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(area) <= 0 || Double.parseDouble(area) >= 10000)
        {
            def errors = [errorCode: 4109, errorMessage: "请输入正确的建筑面积"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!address)
        {
            def errors = [errorCode: 4110, errorMessage: "请输入地址"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!houseType || !(HouseType.findByName(houseType) in HouseType.findAll()))
        {
            def errors = [errorCode: 4112, errorMessage: "请选择住宅类型"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (!assetType)
        {
            def errors = [errorCode: 4113, errorMessage: "请输入房产性质"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (specialFactors == "跃层" && atticArea)
        {
            if (!(atticArea.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(atticArea) <= 0 || Double.parseDouble(atticArea) >= 10000)
            {
                def errors = [errorCode: 4133, errorMessage: "请输入正确的跃层面积"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        if (specialFactors == "顶楼带阁楼" && atticArea)
        {
            if (!(atticArea.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(atticArea) <= 0 || Double.parseDouble(atticArea) >= 10000)
            {
                def errors = [errorCode: 4134, errorMessage: "请输入正确的阁楼面积"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        if (!propertySerialNumber)
        {
            def errors = [errorCode: 4012, errorMessage: "房产证编号不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        //新建collateral
        Collateral collateral = new Collateral()
        collateral.requestStartTime = new Date()
        collateral.city = City.findByName(city)
        collateral.district = district
        collateral.projectName = projectName
        collateral.building = building
        collateral.unit = "0"
        if (atticArea)
        {
            collateral.atticArea = Double.parseDouble(atticArea)
        }
        else
        {
            collateral.atticArea = 0
        }
        collateral.floor = floor
        collateral.roomNumber = roomNumber
        collateral.totalFloor = totalFloor
        collateral.area = Double.parseDouble(area)
        collateral.address = address
        collateral.houseType = houseType
        collateral.assetType = assetType
        collateral.propertySerialNumber = propertySerialNumber
        if (specialFactors && SpecialFactors.findByName(specialFactors) in SpecialFactors.findAll())
        {
            collateral.specialFactors = specialFactors
        }
        def result = propertyValuationProviderService.queryPrice2(collateral)
        println result
        if (result != 0)
        {
            def unitPrice = result['unitPrice']?.toString()
            def externalId = result['externalId']?.toString()
            def status = result['status']?.toString()
            //新建opportunity
            def opportunity = Opportunity.findByExternalId(opportunityExternalId)
            if (!opportunity)
            {
                opportunity = new Opportunity()
            }
            opportunity.externalId = opportunityExternalId
            opportunity.requestedAmount = Double.parseDouble(requestedAmount)

            def loanAmount = Double.parseDouble(unitPrice) * Double.parseDouble(area) / 10000
            if (loanAmount < 0)
            {
                opportunity.loanAmount = loanAmount * (-1)
            }
            else
            {
                opportunity.loanAmount = loanAmount
            }
            if (specialFactors in ["跃层", "顶楼带阁楼"] && collateral.atticArea > 0)
            {
                opportunity.loanAmount += Double.parseDouble(unitPrice) * collateral.atticArea / 20000
            }
            def user = contact?.user
            opportunity.contact = contact

            opportunity.user = user
            opportunity.account = user?.account
            opportunity.unitPrice = Double.parseDouble(unitPrice)
            opportunity.loanApplicationProcessType = LoanApplicationProcessType.findByName("先评房再报单")
            if (opportunity.validate())
            {
                opportunity.save flush: true
            }
            else
            {
                println opportunity.errors
            }
            if (opportunity?.stage?.code == '01')
            {
                if (status == "Completed")
                {
                    opportunity.stage = OpportunityStage.findByCode("02")
                    collateral.requestEndTime = new Date()
                }
                else
                {
                    opportunity.stage = OpportunityStage.findByCode("15")
                }
                if (opportunity.validate())
                {
                    opportunity.save flush: true
                }
                else
                {
                    println opportunity.errors
                }
            }

            println "######新建的opportunity.id###########"

            collateral.opportunity = opportunity
            collateral.unitPrice = Double.parseDouble(unitPrice)
            collateral.totalPrice = Double.parseDouble(unitPrice) * Double.parseDouble(area) / 10000
            if (specialFactors in ["跃层", "顶楼带阁楼"] && collateral.atticArea > 0)
            {
                collateral.totalPrice += Double.parseDouble(unitPrice) * collateral.atticArea / 20000
            }
            collateral.externalId = externalId

            collateral.status = status

            if (collateral.validate())
            {
                println "collateral save"
                collateral.save flush: true
            }
            else
            {
                def errors = [errorCode: 4031, errorMessage: "房产信息校验不通过"+collateral.errors]
                render JsonOutput.toJson(errors), status: 400
                return
                println collateral.errors
            }

            //订单初始化
            println "######订单初始化###########"
            println workflow?.name
            opportunityService.initOpportunity(opportunity, workflow)
            def opportunityData = [:]
            opportunityData.serialNumber = opportunity?.serialNumber
            opportunityData.externalId = opportunity?.externalId
            def collateralData = [:]
            collateralData.unitPrice = collateral?.unitPrice
            collateralData.totalPrice = collateral?.totalPrice
            collateralData.externalId = collateral?.externalId
            opportunityData.collateral = collateralData
            render JsonOutput.toJson(opportunityData), status: 200
        }
        else
        {
            def errors = [errorCode: 4604, errorMessage: "网络不稳定，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
        }

    }

    /**
     * 中行放款反馈接口
     * @Author 张彦超
     * @ModifiedDate 2017-6-28
     */
    @Secured('permitAll')
    @Transactional
    //放款反馈
    def loanFeedBack()
    {
        println "ttttttttt"
        def xml2 = request.XML
        def sw = new java.io.StringWriter()
        def xml = new groovy.xml.MarkupBuilder(sw)
        def isSuccess = xml2.MSG.IsSuccess.text()
        def applSeq = xml2.MSG.ApplSeq.text()
        def channelRecord = ChannelRecord.findByApplySeq(applSeq)
        println "sssssssssssssss" + channelRecord?.opportunity
        def channelRecord1 = ChannelRecord.findByOpportunityAndInterfaceCode(channelRecord.opportunity, "1001")
        def channelRecord2 = new ChannelRecord()
        channelRecord2.loanNo = xml2.MSG.LoanNo.text()
        println "ssss" + xml2
        xml.RESP {
            HEAD {
                if (isSuccess != null || ("Y").equals(isSuccess) || ("N").equals(isSuccess) || ("R").equals(isSuccess))
                {
                    channelRecord2.status = isSuccess
                    ErrorCode("0000")
                    ErrorMsg("交易成功")
                }
                else
                {
                    ErrorCode("9001")
                    ErrorMsg("数据验证错误")
                }
                MsgNo(xml2.HEAD.MsgNo)
                MsgId(xml2.HEAD.MsgId)
                MsgRef("CA" + new Date().format("yyyyMMdd") + "000001")
                ReqCode("WBJF")
                WorkData(new Date().format("yyyyMMdd"))
                //工作时间
                WorkTime(new Date().format("HHmmss"))
            }
            MSG {
                ApplSeq(xml2.MSG.ApplSeq)
                LoanNo(xml2.MSG.LoanNo)
                IsSuccess(xml2.MSG.IsSuccess)
                SuccDesc(xml2.MSG.SuccDesc)
            }
        }
        channelRecord2.startTime = new Date()
        channelRecord2.interfaceCode = '5012'
        channelRecord2.applyCode = channelRecord1.applyCode
        channelRecord2.applySeq = channelRecord1.applySeq
        channelRecord2.endTime = new Date()
        channelRecord2.createdBy = com.next.User.findByUsername("zz")
        channelRecord2.modifiedBy = com.next.User.findByUsername("zz")
        channelRecord2.opportunity = channelRecord?.opportunity
        if (channelRecord2.validate())
        {
            channelRecord2.save flush: true
        }
        else
        {
            println channelRecord2.errors
            println("error")
        }
        println "LoanFeedBack========================" + sw
        render sw
    }

    /**
     * 中行新放款反馈接口
     * @Author 张彦超
     * @ModifiedDate 2017-6-28
     */
    @Secured('permitAll')
    @Transactional
    def loanResults()
    {
        println "****************************loanResults************************************"
        def json = request.JSON
        println "json" + json
        //贷款申请编号
        def loanApplyNo = json["loanApplyNo"]
        //放款申请编号
        def loanSeq = json["loanSeq"]
        //放款日期
        def loanActvDt = json["loanActvDt"]
        //借据号
        def loanNo = json["loanNo"]
        //到期日期
        def dueDate = json["dueDate"]
        //是否放款成功
        def isSuccess = json["isSuccess"]
        //是否放款成功描述
        def succDesc = json["succDesc"]

        def cc = ChannelRecord.findByOpportunityAndInterfaceCode(ChannelRecord.findByLoanApplyNo(loanApplyNo)?.opportunity, "04001001")
        def bb = ChannelRecord.findByOpportunityAndInterfaceCode(cc?.opportunity, "04002008")
        if (bb)
        {
            bb.startTime = new Date()
            bb.endTime = new Date()
            bb.loanApplyNo = loanApplyNo
            bb.loanNo = loanNo
            bb.interfaceCode = "04002008"
            bb.status = succDesc
            bb.opportunity = cc?.opportunity
            if (bb.validate())
            {
                bb.save flush: true
            }
            else
            {
                println bb.errors
            }

        }
        else
        {
            def channelRecord = new ChannelRecord()
            channelRecord.startTime = new Date()
            channelRecord.endTime = new Date()
            channelRecord.loanApplyNo = loanApplyNo
            channelRecord.loanNo = loanNo
            channelRecord.interfaceCode = "04002008"
            channelRecord.status = succDesc
            channelRecord.opportunity = cc?.opportunity
            if (channelRecord.validate())
            {
                channelRecord.save flush: true
            }
            else
            {
                println channelRecord.errors
            }
        }

        render json
    }

    /**
     * 查询还款计划
     * @Author 夏瑞坤
     * @ModifiedDate 2017-7-20
     */
    @Secured(['permitAll'])
    @Transactional
    def searchRepayMentPlan()
    {
        def id = "88697"
        def opportunity = Opportunity.get(id)
        println "************************* search ***************************"
        def mentPlan = JSON.parse(applySubmitService.searchRepayMentPlan(opportunity))
        def channelRecord = new ChannelRecord()
        channelRecord.opportunity = opportunity
        channelRecord.interfaceCode = "04001012"
        if (mentPlan)
        {
            channelRecord.status = '成功'
        }
        else
        {
            channelRecord.status = '失败'
        }
        if (channelRecord.validate())
        {
            channelRecord.save flush: true
        }
        else
        {
            println channelRecord.errors
            println("error")
        }
        respond mentPlan, model: [mentPlan: mentPlan]
    }

    /**
     * 计算应还金额
     * @Author 夏瑞坤
     * @ModifiedDate 2017-7-20
     */
    @Secured(['permitAll'])
    @Transactional
    def calculateTheAmount()
    {
        def id = "110475"
        def opportunity = Opportunity.get(id)
        println "************************* calculateTheAmount ***************************"
        def theAmount = JSON.parse(applySubmitService.calculateTheAmount(opportunity))
        render theAmount, model: [theAmount: theAmount]
    }

    /**
     * 还款结果推送
     * @Author 夏瑞坤
     * @ModifiedDate 2017-7-18
     */
    @Secured(['permitAll'])
    @Transactional
    def repaymentResults()
    {
        def json = request.JSON
        println "************************* repaymentResults ***************************"
        println json
        def isSuccess = json["isSuccess"]
        def succDesc = json["succDesc"]
        def loanActvDt = json["loanActvDt"]
        def loanNo = json["loanNo"]
        def seqNo = json["seqNo"]
        def opportunity = ChannelRecord.findByLoanNo(loanNo)?.opportunity
        def channelRecord = new ChannelRecord()
        channelRecord.opportunity = opportunity
        channelRecord.interfaceCode = "04002017"
        channelRecord.applySeq = seqNo
        if (isSuccess == "N")
        {
            channelRecord.status = "失败"
        }
        if (isSuccess == "Y")
        {
            channelRecord.status = "成功"
        }
        if (isSuccess == "R")
        {
            channelRecord.status = "拒绝"
        }
        if (channelRecord.validate())
        {
            channelRecord.save flush: true
        }
        else
        {
            println channelRecord.errors
            println("error")
        }
        render "success"
    }

    /**
     * //3.29 申请审批结果推送(回调)
     * @Author 孙建岗
     * @ModifiedDate 2017-8-3
     * @return
     */
    @Secured(['permitAll'])
    @Transactional
    def evaluate()
    {
        println("enter in")
        //放回Json报文
        def json = request.JSON
        println(ChannelRecord.findByInterfaceCodeAndLoanApplyNo("04001001", "${json["loanApplyNo"]}").opportunity)
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
                channelRecord.opportunity = ChannelRecord.findByInterfaceCodeAndLoanApplyNo("04001001", "${json["loanApplyNo"]}").opportunity
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
                channelRecord.opportunity = ChannelRecord.findByInterfaceCodeAndLoanApplyNo("04001001", "${json["loanApplyNo"]}").opportunity
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
     * @Description 业务系统推LMS（放款审批）
     * @Author Nagelan
     * @DateTime 2017/8/22 17:04
     */
    @Secured(['permitAll'])
    @Transactional
    def updateOpportunity(){

        def json = request.JSON
        println "************************* updateOpportunity ***************************"

        def opportunity = json.opportunity

        if (!opportunity)
        {
            def errors = [errorCode: 4050, errorMessage: "接收数据为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        println opportunity
        opportunity = JSON.parse(opportunity)
        def workflowName = opportunity?.workflowName
        def workflow = OpportunityWorkflow.findByNameAndActive(workflowName, true)
        //        if (!workflow)
        //        {
        //            def errors = [errorCode: 4001, errorMessage: "请传入正确的workflowName"]
        //            render JsonOutput.toJson(errors), status: 400
        //            return
        //        }

        def externalOpportunity
        if (opportunity?.externalId)
        {
            externalOpportunity = Opportunity.findByExternalId(opportunity?.externalId)
        }
        else
        {
            def errors = [errorCode: 4001, errorMessage: "订单externalId不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!externalOpportunity)
        {
            externalOpportunity = new Opportunity()
        }

        if (!opportunity.requestedAmount || !(opportunity.requestedAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(opportunity.requestedAmount) <= 0)
        {
            def errors = [errorCode: 4002, errorMessage: "请输入正确的贷款金额"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunity.loanAmount || !(opportunity.loanAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(opportunity.loanAmount) <= 0)
        {
            def errors = [errorCode: 4020, errorMessage: "请输入正确的评房总价"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (!opportunity.loanDuration || !(opportunity.loanDuration.matches(/^[0-9]\d*$/)) || Integer.parseInt(opportunity.loanDuration) <= 0)
        {
            def errors = [errorCode: 4003, errorMessage: "请输入正确的贷款期限"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        /*if (!opportunity.actualLoanDuration || !(opportunity.actualLoanDuration.matches(/^[0-9]\d*$/)) || Integer.parseInt(opportunity.actualLoanDuration) <= 0)
        {
            def errors = [errorCode: 4021, errorMessage: "请输入正确的实际贷款期限"]
            render JsonOutput.toJson(errors), status: 400
            return
        }*/

        //        externalOpportunity.serialNumber = opportunity.serialNumber
        if (opportunity.actualAmountOfCredit && opportunity.actualAmountOfCredit.matches(/^\d+(\.\d+)*$/))
        {
            externalOpportunity.actualAmountOfCredit = opportunity.actualAmountOfCredit?.toDouble()
        }
        externalOpportunity.requestedAmount = opportunity.requestedAmount?.toDouble()

        externalOpportunity.loanAmount = opportunity.loanAmount?.toDouble()
        if (opportunity.actualLoanAmount && opportunity.actualLoanAmount.matches(/^\d+(\.\d+)*$/))
        {
            externalOpportunity.actualLoanAmount = opportunity.actualLoanAmount?.toDouble()
        }
        externalOpportunity.loanDuration = opportunity.loanDuration?.toInteger()
        if (opportunity.actualLoanDuration && opportunity.actualLoanDuration.matches(/^[0-9]\d*$/))
        {
            externalOpportunity.actualLoanDuration = opportunity.actualLoanDuration?.toInteger()
        }
        if (opportunity.monthOfAdvancePaymentOfInterest && opportunity.monthOfAdvancePaymentOfInterest.matches(/^\d+(\.\d+)*$/))
        {
            externalOpportunity.monthOfAdvancePaymentOfInterest = opportunity.monthOfAdvancePaymentOfInterest?.toDouble()
        }

        //失败原因及失败原因说明
        def causeOfFailure = CauseOfFailure.findByName(opportunity?.causeOfFailure)
        if (causeOfFailure)
        {
            externalOpportunity.causeOfFailure = causeOfFailure
        }
        if (opportunity.descriptionOfFailure)
        {
            externalOpportunity.descriptionOfFailure = opportunity.descriptionOfFailure
        }

        externalOpportunity.memo = opportunity.memo

        def interestPaymentMethod = InterestPaymentMethod.findByName(opportunity?.interestPaymentMethod)
        if (interestPaymentMethod)
        {
            externalOpportunity.interestPaymentMethod = interestPaymentMethod
        }
        def principalPaymentMethod = PrincipalPaymentMethod.findByName(opportunity?.principalPaymentMethod)
        if (principalPaymentMethod)
        {
            externalOpportunity.principalPaymentMethod = principalPaymentMethod
        }

        //区域名称
        def territory = Territory.findByName(opportunity?.territoryName)
        if (territory)
        {
            externalOpportunity.territory = territory
        }
        else
        {
            def errors = [errorCode: 4026, errorMessage: "请输入正确的区域名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }


        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd")

        if (opportunity.period && opportunity.period.matches(/^[0-9]\d*$/))
        {
            externalOpportunity.period = opportunity.period?.toInteger()
        }
        else
        {
            externalOpportunity.period = 0
        }
        if (opportunity?.startTime)
        {
            externalOpportunity.startTime = sdf1.parse(opportunity?.startTime)
        }
        if (opportunity?.endTime)
        {
            externalOpportunity.endTime = sdf1.parse(opportunity?.endTime)
        }

        def city = City.findByName(opportunity?.cityName)
        if (city)
        {
            externalOpportunity.city = city
        }
        else
        {
            def errors = [errorCode: 4025, errorMessage: "请输入正确的城市名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def product = Product.findByName(opportunity?.productName)
        if (product)
        {
            externalOpportunity.product = product
        }
        else
        {
            def errors = [errorCode: 4024, errorMessage: "请输入正确的产品名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }


         //新增
         //借款用途
         if (opportunity?.loanUsage in ["资金周转", "消费", "装修", "企业经营", "支付保证金", "过桥", "归还上家贷款", "进货", "扩大经营", "购买原材料", "支付房租", "其他"]){
             externalOpportunity.loanUsage = opportunity?.loanUsage
         }
         else
         {
             def errors = [errorCode: 4073, errorMessage: "请输入正确的借款用途"]
             render JsonOutput.toJson(errors), status: 400
             return
         }
         //其他借款用途
         if (opportunity?.otherLoanUsage){
             externalOpportunity.otherLoanUsage = opportunity?.otherLoanUsage
         }
         //月息费率
         if (opportunity?.monthlyInterest){
             externalOpportunity.monthlyInterest = opportunity?.monthlyInterest?.toDouble()
         }
         else
         {
             def errors = [errorCode: 4080, errorMessage: "请输入月息"]
             render JsonOutput.toJson(errors), status: 400
             return
         }
         //综合息费费率
         if (opportunity?.ompositeMonthlyInterest){
             externalOpportunity.ompositeMonthlyInterest = opportunity?.ompositeMonthlyInterest?.toDouble()
         }
         else
         {
             def errors = [errorCode: 4081, errorMessage: "请输入正确的综合月息"]
             render JsonOutput.toJson(errors), status: 400
             return
         }
         //服务费费率
         if (opportunity?.serviceCharge){
             externalOpportunity.serviceCharge = opportunity?.serviceCharge?.toDouble()
         }
         //渠道返费支付方式
         externalOpportunity.commissionPaymentMethod = CommissionPaymentMethod.findByName("一次性返")
         if (opportunity?.creater){
             externalOpportunity.user = User.findByUsername(opportunity?.creater)
         }
         else
         {
             def errors = [errorCode: 4082, errorMessage: "请输入创建人"]
             render JsonOutput.toJson(errors), status: 400
             return
         }
         if (opportunity?.modifiedBy){
             externalOpportunity.modifiedBy = User.findByUsername(opportunity?.modifiedBy)
         }
         if (opportunity?.modifiedDate){
             externalOpportunity.modifiedDate = sdf1.parse(opportunity?.modifiedDate)
         }

         //放款前要求
         if (opportunity?.notarizationMatters){
             externalOpportunity.notarizationMatters = opportunity?.notarizationMatters
         }
         else
         {
             def errors = [errorCode: 4084, errorMessage: "请输入放款前要求mortgageMaterials"]
             render JsonOutput.toJson(errors), status: 400
             return
         }
        if (opportunity?.mortgageCertification){
            externalOpportunity.mortgageCertification = opportunity?.mortgageCertification
            if (MortgageCertificateType.findByName(opportunity?.mortgageCertification)){
                externalOpportunity.mortgageCertificateType = MortgageCertificateType.findByName(opportunity?.mortgageCertification)
                if (opportunity?.mortgageCertification == '抵押受理单'){
                    externalOpportunity.mortgageCertification = 'true'
                }else if (opportunity?.mortgageCertification == '他项证明'){
                    externalOpportunity.mortgageCertification = 'false'
                }
            }
        }
        else
        {
            def errors = [errorCode: 4084, errorMessage: "请输入放款前要求mortgageCertification"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (opportunity?.signedDocuments){
            externalOpportunity.signedDocuments = opportunity?.signedDocuments
        }
        else
        {
            def errors = [errorCode: 4084, errorMessage: "请输入放款前要求signedDocuments"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (opportunity?.mortgageMaterials){
            externalOpportunity.mortgageMaterials = opportunity?.mortgageMaterials
        }
        else
        {
            def errors = [errorCode: 4084, errorMessage: "请输入放款前要求mortgageMaterials"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (opportunity?.otherIssues){
            externalOpportunity.otherIssues = opportunity?.otherIssues
        }
        if (opportunity?.prePaymentAdditionalComment){
            externalOpportunity.prePaymentAdditionalComment = opportunity?.prePaymentAdditionalComment
        }

         //大数据小结
         if (opportunity?.rejected in["是", "否"]){
             externalOpportunity.rejected = opportunity?.rejected
             externalOpportunity.execution = opportunity?.execution
             if (opportunity?.rejected == '是' && opportunity?.descriptionOfRejection){
                 externalOpportunity.descriptionOfRejection = opportunity?.descriptionOfRejection
             }
             if (opportunity?.execution == '是' && opportunity?.descriptionOfExecution){
                 externalOpportunity.descriptionOfExecution = opportunity?.descriptionOfExecution
             }
         }
         else
         {
             def errors = [errorCode: 4085, errorMessage: "请输入大数据小结"]
             render JsonOutput.toJson(errors), status: 400
             return
         }

         //还款来源
         if (opportunity?.repaymentSource in ["企业经营收入", "保证金退换", "房屋租金", "银行贷款", "转单", "股票", "基金到期", "朋友还款", "其他"]){
             externalOpportunity.repaymentSource = opportunity?.repaymentSource
         }
         else
         {
             def errors = [errorCode: 4086, errorMessage: "请输入还款来源"]
             render JsonOutput.toJson(errors), status: 400
             return
         }
         if (opportunity?.otherRepaymentSource){
             externalOpportunity.otherRepaymentSource = opportunity?.otherRepaymentSource
         }

         //借款征信评级
         if (opportunity?.cBCState in["正常", "不良", "恶劣","优质", "次优", "疑难"]){
             externalOpportunity.cBCState = opportunity?.cBCState
         }
         /*else
         {
             def errors = [errorCode: 4087, errorMessage: "请输入借款征信评级"]
             render JsonOutput.toJson(errors), status: 400
             return
         }*/

         //授信建议
         if (opportunity?.interviewRequired in ["是", "否"]){
             externalOpportunity.interviewRequired = opportunity?.interviewRequired
         }
         else
         {
             def errors = [errorCode: 4088, errorMessage: "请输入是否要求面审"]
             render JsonOutput.toJson(errors), status: 400
             return
         }



        externalOpportunity.externalId = opportunity.externalId

        if (opportunity?.actualLendingDate)
        {
            externalOpportunity.actualLendingDate = sdf1.parse(opportunity?.actualLendingDate)
        }
        if (opportunity?.estimatedLendingDate)
        {
            externalOpportunity.estimatedLendingDate = sdf1.parse(opportunity?.estimatedLendingDate)
        }
        if (opportunity.isTest)
        {
            externalOpportunity.isTest = opportunity.isTest == "true" ? true : false
        }

        if (opportunity?.dateOfMortgage)
        {
            externalOpportunity.dateOfMortgage = sdf1.parse(opportunity?.dateOfMortgage)
        }
        if (opportunity?.dateOfNotarization)
        {
            externalOpportunity.dateOfNotarization = sdf1.parse(opportunity?.dateOfNotarization)
        }

        if (opportunity.complianceChecking)
        {
            externalOpportunity.complianceChecking = opportunity.complianceChecking == "true" ? true : false
        }

        if (opportunity?.externalModifiedDate)
        {
            externalOpportunity.externalModifiedDate = sdf1.parse(opportunity?.externalModifiedDate)
        }

        //销售
        def user = User.findByFullName(opportunity?.userName)
        if (user)
        {
            externalOpportunity.user = user
        }
        else
        {
            def errors = [errorCode: 4004, errorMessage: "请输入正确的销售名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        //机构名称
        def account = Account.findByName(opportunity?.accountName)
        if (account)
        {
            externalOpportunity.account = account
        }
        else
        {
            def errors = [errorCode: 4005, errorMessage: "请输入正确的机构名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def productAccount
        if (product)
        {
            def temp = account
            while (temp)
            {
                productAccount = ProductAccount.findByAccountAndProduct(temp, product)
                if (productAccount)
                {
                    break
                }
                else
                {
                    temp = temp?.parent
                }
            }
        }
        externalOpportunity.productAccount = productAccount

        if (externalOpportunity.validate())
        {
            externalOpportunity.save()
        }
        else
        {
            println externalOpportunity.errors
        }
        //经纪人
        if (opportunity?.contact)
        {
            //            for (
            //                    def externalContact in
            //                            opportunity?.contact)
            //            {
            def externalContact = opportunity?.contact
            if (externalContact?.cellphone)
            {
                if (!(externalContact?.cellphone.matches(/^1\d{10}$/)))
                {
                    def errors = [errorCode: 4076, errorMessage: "经纪人手机号格式不正确，请重新输入"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }
            def contact
            if (externalContact?.externalId)
            {
                contact = Contact.findByCellphoneAndType(externalContact?.cellphone, 'Agent')
            }
            else
            {
                def errors = [errorCode: 4077, errorMessage: "经纪人externalId不能为空"]
                render JsonOutput.toJson(errors), status: 400
                return
            }

            if (!contact)
            {
                contact = new Contact()
            }
            contact.fullName = externalContact?.fullName
            contact.cellphone = externalContact?.cellphone
            contact.idNumber = externalContact?.idNumber
            def contactCity = City.findByName(externalContact?.cityName)
            if (contactCity)
            {
                contact.city = contactCity
            }
            else
            {
                def errors = [errorCode: 4078, errorMessage: "经纪人城市不正确"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            contact.externalId = externalContact?.externalId
            contact.type = 'Agent'
            contact.user = user
            if (contact.validate())
            {
                contact.save()
            }
            else
            {
                println contact.errors
            }
            //经纪人
            externalOpportunity.contact = contact
            externalOpportunity.save()
            if (externalContact?.account)
            {
                if (!Account.find("from Account where name = '${externalContact?.account?.name}'"))
                {
                    def accountNew = new Account(name: "${externalContact?.account?.name}", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"), externalId: "${externalContact?.account?.externalId}")
                    accountNew.save()
                    contact.account = accountNew
                    contact.save()
                }
                else
                {
                    def account1 = Account.findByName(externalContact?.account?.name)
                    account1?.externalId = externalContact?.account?.externalId
                    account1?.save()
                }
            }
            //            }
        }
        //        else
        //        {
        //            def errors = [errorCode: 4079, errorMessage: "请添加经纪人信息"]
        //            render JsonOutput.toJson(errors), status: 400
        //            return
        //        }
        //联系人
        if (opportunity?.contacts)
        {
            for (
                    def externalContact in
                            opportunity?.contacts)
            {
                String idNumber = externalContact?.idNumber
                if (idNumber)
                {
                    idNumber = idNumber.replace('x', 'X')
                }
                if (!idNumber || !contactService.verifyIdNumber(idNumber))
                {
                    def errors = [errorCode: 4006, errorMessage: "请输入${externalContact?.type}正确的身份证号"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else if (!externalContact?.fullName)
                {
                    def errors = [errorCode: 4007, errorMessage: "请输入${externalContact?.type}正确的姓名"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else if (externalContact?.cellphone)
                {
                    if (!(externalContact?.cellphone.matches(/^1\d{10}$/)))
                    {
                        def errors = [errorCode: 4008, errorMessage: "${externalContact?.type}手机号格式不正确，请重新输入"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                }
                if (!externalContact?.maritalStatus || !(externalContact?.maritalStatus in ["未婚", "已婚", "再婚", "离异", "丧偶"]))
                {
                    def errors = [errorCode: 4009, errorMessage: "请选择婚姻状况"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                def contact
                if (externalContact?.externalId)
                {
                    if (externalContact?.type =='借款人'&&externalOpportunity?.lender){
                        contact = externalOpportunity?.lender
                    }else {
                        contact = Contact.findByExternalId(externalContact?.externalId)
                    }
                }
                else
                {
                    def errors = [errorCode: 4010, errorMessage: "${externalContact?.type}联系人externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                //                if(!externalContact?.connectedContact)
                //                {
                //                    def errors = [errorCode: 4075, errorMessage: "${externalContact?.type}关联人externalId不能为空"]
                //                    render JsonOutput.toJson(errors), status: 400
                //                    return
                //                    break
                //                }

                if (!contact)
                {
                    contact = new Contact()
                    println "新建了联系人"
                }
                contact.fullName = externalContact?.fullName
                contact.cellphone = externalContact?.cellphone
                contact.idNumber = idNumber
                contact.maritalStatus = externalContact?.maritalStatus
                def contactCity = City.findByName(externalContact?.cityName)
                if (contactCity)
                {
                    contact.city = contactCity
                }
                else
                {
                    def errors = [errorCode: 4027, errorMessage: "${externalContact?.type}联系人城市不正确"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                def level = ContactLevel.findByNameAndActive(externalContact?.level, true)
                if (level)
                {
                    contact.level = level
                }
                else
                {
                    def errors = [errorCode: 4028, errorMessage: "${externalContact?.type}客户级别不正确"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                //测试回调信息修改评级问题
                println("-----Nagelan:原始评级"+contact?.level?.name+"--新的评级"+externalContact?.lever)
                contact.identityType = ContactIdentityType.findByName(externalContact?.identityType)
                contact.country = Country.findByName(externalContact?.country)
                contact.profession = ContactProfession.findByName(externalContact?.profession)
                contact.externalId = externalContact?.externalId

                //新增条件
                if(externalContact?.overdueRecentTwoYears){
                    contact.overdueRecent = externalContact?.overdueRecentTwoYears+""
                }
                //两年内逾期次数
                if (externalContact?.totalLoanBalance){
                    contact.totalLoanBalance = externalContact?.totalLoanBalance?.toDouble()
                }
                else
                {
                    def errors = [errorCode: 4061, errorMessage: "请输入当前总贷款余额"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                if (externalContact?.guaranteeStatus in ["有", "无"]){
                    contact.guaranteeStatus = externalContact?.guaranteeStatus
                }
                /*else
                {
                    def errors = [errorCode: 4069, errorMessage: "请输入对外担保情况"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }*/
                if (externalContact?.guaranteeType in ["信用卡担保", "贷款担保"]){
                    contact.guaranteeType = externalContact?.guaranteeType
                }
                /*else
                {
                    def errors = [errorCode: 4071, errorMessage: "请输入对外担保类型"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }*/

                if (externalContact?.guaranteeBalance){
                    contact.guaranteeBalance = externalContact?.guaranteeBalance?.toDouble()
                }
                else
                {
                    def errors = [errorCode: 4072, errorMessage: "请输入对外担保余额"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                if (externalContact?.relationOfGuarantor){
                    contact.relationOfGuarantor = externalContact?.relationOfGuarantor
                }
                if (externalContact?.currentOverdueAmount){
                    contact.currentOverdueAmount = externalContact?.currentOverdueAmount?.toDouble()
                }
                if (externalContact?.guaranteeState in["正常", "关注", "次级", "可疑", "损失"]){
                    contact.guaranteeState = externalContact?.guaranteeState
                }
                /*else
                {
                    def errors = [errorCode: 4089, errorMessage: "请输入正确的对外担保分类"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }*/
                //是否为抵押人
                if (externalContact?.propertyOwner in ["是", "否"]){
                    contact.propertyOwner = externalContact?.propertyOwner
                }else {
                    println '未传是否为抵押人'
                }
                //年收入
                if (externalContact?.income){
                    contact.income = externalContact?.income?.toDouble()
                }
                //其他说明
                if (externalContact?.personalProperty){
                    contact.personalProperty = externalContact?.personalProperty
                }


                if (externalContact?.type =='借款人'){
                    contact.type = 'Client'
            }


            if (contact.validate())
                {
                    contact.save()
                }
                else
                {
                    println contact.errors
                }
                def type = OpportunityContactType.findByName(externalContact?.type)
                def opportunityContact = OpportunityContact.findByOpportunityAndContact(externalOpportunity, contact)
                if (!opportunityContact)
                {
                    opportunityContact = new OpportunityContact()
                    if (type)
                    {
                        opportunityContact.type = type
                    }
                }
                if (type && externalContact?.connectedType)
                {
                    opportunityContact.type = type
                }
                opportunityContact.contact = contact
                opportunityContact.opportunity = externalOpportunity
                if (opportunityContact.validate())
                {
                    opportunityContact.save()
                }
                else
                {
                    def errors = [errorCode: 4074, errorMessage: "请添加联系人信息"+opportunityContact.errors]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    println opportunityContact.errors
                }

                if (externalContact?.type == '借款人')
                {
                    externalOpportunity.fullName = opportunityContact.contact.fullName
                    externalOpportunity.cellphone = opportunityContact.contact.cellphone
                    externalOpportunity.idNumber = opportunityContact.contact.idNumber
                    externalOpportunity.maritalStatus = opportunityContact.contact.maritalStatus
                    externalOpportunity.lender = opportunityContact.contact
                    externalOpportunity.save()
                }
            }
            for (
                    def externalContact in
                            opportunity?.contacts)
            {
                if (externalContact?.connectedContact)
                {
                    def contact = Contact.findByExternalId(externalContact?.externalId)
                    def opportunityContact = OpportunityContact.findByOpportunityAndContact(externalOpportunity, contact)
                    if (opportunityContact){
                        def connectedContact = Contact.findByExternalId(externalContact?.connectedContact)
                        opportunityContact.connectedContact = connectedContact
                        if (opportunityContact?.type?.name == '借款人')
                        {
                            opportunityContact.connectedType = OpportunityContactType.findByName("本人")
                        }
                        else
                        {
                            opportunityContact.connectedType = opportunityContact?.type
                        }
                        if (externalContact?.connectedType)
                        {
                            def connectedType = OpportunityContactType.findByName(externalContact?.connectedType)
                            opportunityContact.connectedType = connectedType
                        }
                        opportunityContact.save()
                    }
                }
            }
        }
        else
        {
            def errors = [errorCode: 4029, errorMessage: "请添加联系人信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //附件
        if (opportunity?.attachments)
        {
            for (
                    def externalAttachments in
                            opportunity?.attachments)
            {
                def attachments
                if (externalAttachments?.externalId)
                {
                    attachments = Attachments.findByExternalId(externalAttachments?.externalId)
                }
                else
                {
                    def errors = [errorCode: 4011, errorMessage: "${externalAttachments?.type}附件externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!attachments)
                {
                    attachments = new Attachments()
                }

                def attachmentType = AttachmentType.findByName(externalAttachments?.type)
                if (attachmentType)
                {
                    attachments.type = attachmentType
                }
                else
                {
                    def errors = [errorCode: 4022, errorMessage: "${externalAttachments?.type}类型名称有误"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                // attachments.fileName = externalAttachments?.fileName
                attachments.fileName = externalAttachments?.fileUrl
                attachments.fileUrl = externalAttachments?.fileUrl
                attachments.description = externalAttachments?.description
                if (externalAttachments?.displayOrder && externalAttachments.displayOrder.matches(/^[0-9]\d*$/))
                {
                    attachments.displayOrder = externalAttachments?.displayOrder?.toInteger()
                }

                def createBy = User.findByFullName(externalAttachments?.createByUserName)
                if (createBy)
                {
                    attachments.createBy = createBy
                    attachments.modifyBy = createBy
                }
                else
                {
                    def errors = [errorCode: 4036, errorMessage: "附件${externalAttachments?.type}创建者有误"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                attachments.externalId = externalAttachments?.externalId
                attachments.opportunity = externalOpportunity
                if (attachments.validate())
                {
                    attachments.save()
                }
                else
                {
                    println attachments.errors
                }
            }
        }
        else
        {
            def errors = [errorCode: 4030, errorMessage: "请添加附件信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //房产
        if (opportunity?.collaterals)
        {
            for (
                    def externalCollateral in
                            opportunity?.collaterals)
            {
                def collateral
                if (externalCollateral?.externalId)
                {
                    collateral = Collateral.findByExternalId(externalCollateral?.externalId)
                }
                else
                {
                    def errors = [errorCode: 4012, errorMessage: "房产externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!collateral)
                {
                    collateral = Collateral.findByPropertySerialNumberAndOpportunity(externalCollateral?.propertySerialNumber, externalOpportunity)
                }
                if (!collateral)
                {
                    collateral = new Collateral()
                }
                //新需求修改by：nagelan
                //if ((externalCollateral?.mortgageType in ["二抵", "一抵转单"]))
                if ((externalCollateral?.mortgageType in ["二抵"]))
                {
                    if (!externalCollateral?.firstMortgageAmount || !(externalCollateral?.firstMortgageAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.firstMortgageAmount) <= 0)
                    {
                        def errors = [errorCode: 4013, errorMessage: "请输入正确的一抵金额"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                    if (!externalCollateral?.typeOfFirstMortgage || !(externalCollateral?.typeOfFirstMortgage in ["银行类", "非银行类"]))
                    {
                        def errors = [errorCode: 4014, errorMessage: "请选择一抵类型"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                }
                if (externalCollateral?.mortgageType == "二抵转单")
                {
                    if (!externalCollateral?.firstMortgageAmount || !(externalCollateral?.firstMortgageAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.firstMortgageAmount) <= 0)
                    {
                        def errors = [errorCode: 4013, errorMessage: "请输入正确的一抵金额"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                    if (!externalCollateral?.typeOfFirstMortgage || !(externalCollateral?.typeOfFirstMortgage in ["银行类", "非银行类"]))
                    {
                        def errors = [errorCode: 4014, errorMessage: "请选择一抵类型"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }
                    //新需求修改by：nagelan
                    /*if (!externalCollateral?.secondMortgageAmount || !(externalCollateral?.secondMortgageAmount.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.secondMortgageAmount) <= 0)
                    {
                        def errors = [errorCode: 4015, errorMessage: "请输入正确的二抵金额"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                        break
                    }*/
                }

                if (!(externalCollateral?.city))
                {
                    def errors = [errorCode: 4016, errorMessage: "请输入房产城市"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.district))
                {
                    def errors = [errorCode: 4052, errorMessage: "请输入房产区域"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.projectName))
                {
                    def errors = [errorCode: 4053, errorMessage: "请输入房产小区"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.building))
                {
                    def errors = [errorCode: 4054, errorMessage: "请输入房产楼栋"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.floor))
                {
                    def errors = [errorCode: 4055, errorMessage: "请输入房产所在楼层"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.totalFloor))
                {
                    def errors = [errorCode: 4056, errorMessage: "请输入房产总楼层"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.roomNumber))
                {
                    def errors = [errorCode: 4057, errorMessage: "请输入房产房间号"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.area))
                {
                    def errors = [errorCode: 4058, errorMessage: "请输入房产面积"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.address))
                {
                    def errors = [errorCode: 4059, errorMessage: "请输入房产地址"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.orientation))
                {
                    def errors = [errorCode: 4060, errorMessage: "请输入房产朝向"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!externalCollateral?.houseType || !(HouseType.findByName(externalCollateral?.houseType) in HouseType.findAll()))
                {
                    def errors = [errorCode: 4112, errorMessage: "请选择住宅类型"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                if (!(externalCollateral?.assetType))
                {
                    def errors = [errorCode: 4062, errorMessage: "请输入房产性质"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!(externalCollateral?.projectType))
                {
                    def errors = [errorCode: 4064, errorMessage: "请输入房产立项类型"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                if (!(externalCollateral?.specialFactors in ['无', '复式', 'LOFT', '跃层', '一层赠送', '临湖', '楼王', '临街', '顶楼带阁楼', '看海', '一层赠送半地下']))
                {
                    def errors = [errorCode: 4048, errorMessage: "请输入正确的房产特殊因素"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                //新增 抵押物权属
                if (externalCollateral?.mortgageProperty){
                    collateral.mortgageProperty = externalCollateral?.mortgageProperty
                }
                else
                {
                    def errors = [errorCode: 4033, errorMessage: "请输入抵押物权属信息"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                //审批时抵押状态
                if (externalCollateral?.mortgageStatus){
                    collateral.mortgageStatus = externalCollateral?.mortgageStatus
                }else {
                    println '未传审批时抵押状态'
                    def errors = [errorCode: 4035, errorMessage: "请输入审批时抵押状态"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                //其他补充
                if (externalCollateral?.additionalComment){
                    collateral.additionalComment = externalCollateral?.additionalComment
                }else {
                    println '未传房产信息其他补充'
                    def errors = [errorCode: 4049, errorMessage: "请输入房产信息其他补充"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }


                collateral.assetType = externalCollateral?.assetType
                collateral.projectName = externalCollateral?.projectName
                /*---------------------------------------------------------------------*/
                def collateralCity = City.findByName(externalCollateral?.city)
                if (collateralCity)
                {
                    collateral.city = collateralCity
                }
                collateral.district = externalCollateral?.district
                collateral.address = externalCollateral?.address
                collateral.floor = externalCollateral?.floor
                collateral.orientation = externalCollateral?.orientation
                collateral.area = externalCollateral?.area?.toDouble()
                if (!externalCollateral?.building)
                {
                    def errors = [errorCode: 4037, errorMessage: "请输入正确的楼栋信息"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    collateral.building = externalCollateral?.building
                }
                collateral.unit = externalCollateral?.unit
                collateral.totalFloor = externalCollateral?.totalFloor
                collateral.roomNumber = externalCollateral?.roomNumber
                collateral.houseType = externalCollateral?.houseType
                collateral.specialFactors = externalCollateral?.specialFactors
                if (!externalCollateral?.unitPrice || !(externalCollateral?.unitPrice.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.unitPrice) <= 0)
                {
                    def errors = [errorCode: 4038, errorMessage: "请输入正确的房产单价"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    if (!collateral.unitPrice)
                    {
                        collateral.unitPrice = externalCollateral?.unitPrice?.toDouble() * 10000
                    }
                }

                if (!externalCollateral?.totalPrice || !(externalCollateral?.totalPrice.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.totalPrice) <= 0)
                {
                    def errors = [errorCode: 4039, errorMessage: "请输入正确的房产总价"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    if (!collateral.totalPrice)
                    {
                        collateral.totalPrice = externalCollateral?.totalPrice?.toDouble()
                    }
                }


                if (externalCollateral?.fastUnitPrice && externalCollateral?.fastUnitPrice.matches(/^\d+(\.\d+)*$/) && Double.parseDouble(externalCollateral?.fastUnitPrice) >= 0)
                {
                    if (!collateral.fastUnitPrice || collateral.fastUnitPrice ==0)
                    {
                        collateral.fastUnitPrice = externalCollateral?.fastUnitPrice?.toDouble()

                        //PV推房产证
                        def ats = []
                        def result1 = [:]
                        def attachmentsList = Attachments.findAllByOpportunityAndType(externalOpportunity, AttachmentType.findByName('房产证'))
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

                        //                        if (!resultJson)
                        //                        {
                        //                            def errors = [errorCode: 4796, errorMessage: "调用评房反馈服务上传图片失败，请稍后重试"]
                        //                            render JsonOutput.toJson(errors), status: 400
                        //                            return
                        //                            break
                        //                        }

                        // PV推送外访值
                        propertyValuationProviderService.updateFastUnitPrice(collateral?.externalId, externalCollateral?.fastUnitPrice)
                    }
                }

                if (!externalCollateral?.appliedTotalPrice || !(externalCollateral?.appliedTotalPrice.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalCollateral?.appliedTotalPrice) <= 0)
                {
                    def errors = [errorCode: 4039, errorMessage: "请输入正确的房产期望价格"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    collateral.appliedTotalPrice = externalCollateral?.appliedTotalPrice?.toDouble()
                }


                collateral.externalId = externalCollateral?.externalId

                def projectType = CollateralProjectType.findByName(externalCollateral?.projectType)
                collateral.projectType = projectType
                if (externalCollateral?.buildTime)
                {
                    collateral.buildTime = sdf1.parse(externalCollateral?.buildTime)
                }
                else
                {
                    def errors = [errorCode: 4040, errorMessage: "请输入正确的房产建成时间"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                //抵押率
                if (externalCollateral?.loanToValue && externalCollateral?.loanToValue.matches(/^\d+(\.\d+)*$/))
                {
                    collateral.loanToValue = externalCollateral?.loanToValue?.toDouble()
                }
                collateral.propertySerialNumber = externalCollateral?.propertySerialNumber
                def typeOfFirstMortgage = TypeOfFirstMortgage.findByName(externalCollateral?.typeOfFirstMortgage)
                collateral.typeOfFirstMortgage = typeOfFirstMortgage

                if ((externalCollateral?.mortgageType == "二抵")){
                    collateral.firstMortgageAmount = externalCollateral?.firstMortgageAmount?.toDouble()
                    collateral.secondMortgageAmount = externalOpportunity?.actualAmountOfCredit?.toDouble()
                }else if ((externalCollateral?.mortgageType == "一抵")){
                    collateral.firstMortgageAmount = externalOpportunity?.actualAmountOfCredit?.toDouble()
                    //collateral.secondMortgageAmount = externalCollateral?.secondMortgageAmount?.toDouble()
                }
                /*collateral.firstMortgageAmount = externalCollateral?.firstMortgageAmount?.toDouble()
                collateral.secondMortgageAmount = externalCollateral?.secondMortgageAmount?.toDouble()*/

                def mortgageType = MortgageType.findByName(externalCollateral?.mortgageType)
                collateral.mortgageType = mortgageType

                def region = CollateralRegion.findByName(externalCollateral?.region)
                collateral.region = region

                collateral.atticArea = externalCollateral?.atticArea?.toDouble()
                collateral.postcode = externalCollateral?.postcode

                //产调
                collateral.propertyOwnershipInvestigationStatus = externalCollateral?.propertyOwnershipInvestigationStatus
                collateral.propertyOwnership = externalCollateral?.propertyOwnership
                collateral.propertySealedup = externalCollateral?.propertySealedup == "true" ? true : false
                collateral.propertySealedupReason = externalCollateral?.propertySealedupReason == "true" ? true : false
                if (externalCollateral?.propertySealedupDate)
                {
                    collateral.propertySealedupDate = sdf1.parse(externalCollateral?.propertySealedupDate)
                }
                collateral.newHouse = externalCollateral?.newHouse == "true" ? true : false
                if (externalCollateral?.propertyCertificateHoldDate)
                {
                    collateral.propertyCertificateHoldDate = sdf1.parse(externalCollateral?.propertyCertificateHoldDate)
                }

                //外访预警
                collateral.seventyYearsElder = externalCollateral?.seventyYearsElder == "true" ? true : false
                collateral.specialPopulation = externalCollateral?.specialPopulation == "true" ? true : false
                collateral.propertyStructure = externalCollateral?.propertyStructure == "true" ? true : false
                if (externalCollateral?.partitionNumber && externalCollateral?.partitionNumber.matches(/^[0-9]\d*$/))
                {
                    collateral.partitionNumber = externalCollateral?.partitionNumber?.toInteger()
                }
                collateral.propertyLivingCondition = externalCollateral?.propertyLivingCondition == "true" ? true : false
                collateral.marksOfFire = externalCollateral?.marksOfFire == "true" ? true : false
                collateral.topFloorWithWaterLeakage = externalCollateral?.topFloorWithWaterLeakage == "true" ? true : false
                collateral.noiseEnvironment = externalCollateral?.noiseEnvironment == "true" ? true : false
                collateral.tubeShapedApartment = externalCollateral?.tubeShapedApartment == "true" ? true : false
                collateral.housingDemolition = externalCollateral?.housingDemolition == "true" ? true : false
                collateral.actualUsageIsOffice = externalCollateral?.actualUsageIsOffice == "true" ? true : false
                collateral.mostOfFloorsAreOffices = externalCollateral?.mostOfFloorsAreOffices == "true" ? true : false
                collateral.undisclosedTenant = externalCollateral?.undisclosedTenant == "true" ? true : false
                collateral.basementOrHighVoltage = externalCollateral?.basementOrHighVoltage == "true" ? true : false
                collateral.dangerousAnimal = externalCollateral?.dangerousAnimal == "true" ? true : false
                collateral.doubtfulMarriageStatus = externalCollateral?.doubtfulMarriageStatus == "true" ? true : false

                //抵押物其它情况
                collateral.businessSophistication = externalCollateral?.businessSophistication
                collateral.traffic = externalCollateral?.traffic
                collateral.hospital = externalCollateral?.hospital
                collateral.schoolDistrictType = externalCollateral?.schoolDistrictType
                collateral.kindergarten = externalCollateral?.kindergarten
                collateral.primarySchool = externalCollateral?.primarySchool
                collateral.middleSchool = externalCollateral?.middleSchool
                collateral.highSchool = externalCollateral?.highSchool
                if (externalCollateral?.numberOfBanks && externalCollateral?.numberOfBanks.matches(/^[0-9]\d*$/))
                {
                    collateral.numberOfBanks = externalCollateral?.numberOfBanks?.toInteger()
                }
                collateral.supermarket = externalCollateral?.supermarket == "true" ? true : false
                collateral.farmersMarket = externalCollateral?.farmersMarket == "true" ? true : false
                collateral.landAgency = externalCollateral?.landAgency
                collateral.residentialGreen = externalCollateral?.residentialGreen
                collateral.parkingSpace = externalCollateral?.parkingSpace
                collateral.buildingAppreance = externalCollateral?.buildingAppreance
                collateral.numberOfElevators = externalCollateral?.numberOfElevators
                collateral.gymEquipment = externalCollateral?.gymEquipment == "true" ? true : false
                collateral.communitySize = externalCollateral?.communitySize
                if (externalCollateral?.houseUsageStats in ["自住", "空置", "出借", "出租"])
                {
                    collateral.houseUsageStats = externalCollateral?.houseUsageStats
                }
                collateral.decoration = externalCollateral?.decoration

                //其它
                if (externalCollateral?.landUsageTerm && externalCollateral?.landUsageTerm.matches(/^[0-9]\d*$/))
                {
                    collateral.landUsageTerm = externalCollateral?.landUsageTerm?.toInteger()
                }
                if(externalCollateral?.houseOrigin in["购置", "继承", "分割", "赠与", "产权无争议", "全额付款", "更换产证"]){
                    collateral.houseOrigin = externalCollateral?.houseOrigin
                }

                collateral.tenantType = externalCollateral?.tenantType
                collateral.status = "Completed"
                collateral.opportunity = externalOpportunity

                if (collateral.validate())
                {
                    collateral.save()
                }
                else
                {
                    def errors = [errorCode: 4031, errorMessage: "房产信息校验不通过"+collateral.errors]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    println collateral.errors
                }

            }
        }
        else
        {
            def errors = [errorCode: 4031, errorMessage: "请添加房产信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        //费率
        if(opportunity?.interest)
        {
            for(def externalInterest in opportunity?.interest)
            {
                def interest
                if(externalInterest?.externalId)
                {
                    interest = OpportunityProduct.findByExternalId(externalInterest?.externalId)
                    //根据订单、订单产品类型，费率类型查找费率信息
                    //interest = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(externalOpportunity,externalOpportunity?.productAccount,ProductInterestType.findByName(externalInterest?.productInterestType))
                }
                else
                {
                    def errors = [errorCode: 4017, errorMessage: "息与费externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if(!interest)
                {
                    interest = new OpportunityProduct()
                }
                def productInterestType = ProductInterestType.findByName(externalInterest?.productInterestType)
                interest.productInterestType = productInterestType
                interest.installments = externalInterest?.installments == "true" ? true : false

                if (!externalInterest?.rate || !(externalInterest?.rate.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(externalInterest?.rate) < 0)
                {
                    def errors = [errorCode: 4041, errorMessage: "请输入正确的费率"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    interest.rate = externalInterest?.rate?.toDouble()
                }
                if (!externalInterest?.monthes || !(externalInterest?.monthes.matches(/^[0-9]\d*$/)) || Integer.parseInt(externalInterest?.monthes) <= 0)
                {
                    def errors = [errorCode: 4042, errorMessage: "请输入正确的费率期限"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    interest.monthes = externalInterest?.monthes?.toInteger()
                }

                if (!externalInterest?.firstPayMonthes || !(externalInterest?.firstPayMonthes.matches(/^[0-9]\d*$/)) || Integer.parseInt(externalInterest?.firstPayMonthes) < 0)
                {
                    def errors = [errorCode: 4043, errorMessage: "请输入正确的预收取月份数"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                else
                {
                    interest.firstPayMonthes = externalInterest?.firstPayMonthes?.toInteger()
                }

                def createByUser = User.findByFullName(externalInterest?.createByUserName)
                if(createByUser)
                {
                    interest.createBy = createByUser
                }
                else
                {
                    def errors = [errorCode: 4044, errorMessage: "请输入正确的费用创建人"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                def modifyByUser = User.findByFullName(externalInterest?.modifyByUserName)
                if(modifyByUser)
                {
                    interest.modifyBy = modifyByUser
                }
                else
                {
                    def errors = [errorCode: 4045, errorMessage: "请输入正确的费用修改人"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                def contractType = ContractType.findByName(externalInterest?.contractType)
                if(contractType)
                {
                    interest.contractType = contractType
                }
                else
                {
                    def errors = [errorCode: 4046, errorMessage: "请输入正确的费用所属合同类型"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                interest.externalId = externalInterest.externalId
                interest.opportunity = externalOpportunity
                interest.product = externalOpportunity.productAccount
                if(interest.validate())
                {
                    interest.save()
                }
                else
                {
                    println interest.errors
                }
            }
        }
        else
        {
            def errors = [errorCode: 4032, errorMessage: "请添加费用信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //账号
        if (opportunity?.bankAccounts)
        {
            for (
                    def externalBankAccount in
                            opportunity?.bankAccounts)
            {
                def bankAccount
                if (externalBankAccount?.externalId)
                {
                    bankAccount = BankAccount.findByExternalId(externalBankAccount?.externalId)
                }
                else
                {
                    def errors = [errorCode: 4018, errorMessage: "账户externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!externalBankAccount?.bank || !(Bank.findByName(externalBankAccount?.bank)))
                {
                    def errors = [errorCode: 4051, errorMessage: "账户银行不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!bankAccount)
                {
                    bankAccount = new BankAccount()
                }
                bankAccount.numberOfAccount = externalBankAccount?.numberOfAccount
                if (bankAccount.numberOfAccount.length()>21){
                    def errors = [errorCode: 4090, errorMessage: "银行卡号过长"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                bankAccount.name = externalBankAccount?.name
                bankAccount.cellphone = externalBankAccount?.cellphone
                def certificateType = ContactIdentityType.findByName(externalBankAccount?.certificateType)
                bankAccount.certificateType = certificateType
                bankAccount.numberOfCertificate = externalBankAccount?.numberOfCertificate
                bankAccount.active = externalBankAccount?.active == "true" ? true : false
                bankAccount.bankBranch = externalBankAccount?.bankBranch
                bankAccount.bankAddress = externalBankAccount?.bankAddress
                bankAccount.externalId = externalBankAccount?.externalId
                bankAccount.bank = Bank.findByName(externalBankAccount?.bank)
                bankAccount.paymentChannel = PaymentChannel.findByName("广银联")
                if (bankAccount.validate())
                {
                    bankAccount.save()
                }
                else
                {
                    println bankAccount.errors
                }
                def bankAccountType = OpportunityBankAccountType.findByName(externalBankAccount?.type)
                def opportunityBankAccount = OpportunityBankAccount.findByOpportunityAndTypeAndBankAccount(externalOpportunity, bankAccountType, bankAccount)
                if (!opportunityBankAccount)
                {
                    opportunityBankAccount = new OpportunityBankAccount()
                }
                opportunityBankAccount.type = bankAccountType
                opportunityBankAccount.bankAccount = bankAccount
                opportunityBankAccount.opportunity = externalOpportunity
                if (opportunityBankAccount.validate())
                {
                    opportunityBankAccount.save()
                }
                else
                {
                    println opportunityBankAccount.errors
                }
            }
        }
        //合同
        if (opportunity?.contracts)
        {
            for (
                    def externalContract in
                            opportunity?.contracts)
            {
                def contract
                if (externalContract?.externalId)
                {
                    contract = Contract.findByExternalId(externalContract?.externalId)
                }
                else
                {
                    def errors = [errorCode: 4019, errorMessage: "合同externalId不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                if (!externalContract?.serialNumber)
                {
                    def errors = [errorCode: 4065, errorMessage: "合同编号不能为空"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                if (!contract && Contract.findBySerialNumber(externalContract?.serialNumber))
                {
                    def errors = [errorCode: 4066, errorMessage: "合同编号不能为重复"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }

                if (!contract)
                {
                    contract = new Contract()
                }
                contract.serialNumber = externalContract?.serialNumber
                def contractType = ContractType.findByName(externalContract?.type)
                if (contractType)
                {
                    contract.type = contractType
                }
                else
                {
                    def errors = [errorCode: 4047, errorMessage: "请输入正确的合同类型"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                    break
                }
                contract.externalId = externalContract?.externalId
                if (contract.validate())
                {
                    contract.save()
                }
                else
                {
                    println contract.errors
                }
                def opportunityContract = OpportunityContract.findByOpportunityAndContract(externalOpportunity, contract)
                if (!opportunityContract)
                {
                    opportunityContract = new OpportunityContract()
                }
                opportunityContract.contract = contract
                opportunityContract.opportunity = externalOpportunity
                if (opportunityContract.validate())
                {
                    opportunityContract.save()
                }
                else
                {
                    println opportunityContract.errors
                }
            }
        }
        /*else
        {
            def errors = [errorCode: 4034, errorMessage: "请添加合同信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }*/

        //订单初始化
        if (!OpportunityFlow.findByOpportunity(externalOpportunity)){
            opportunityService.initOpportunity(externalOpportunity, workflow)
        }
        /*if (externalOpportunity?.stage?.name in ['评房申请已提交', '价格待确认', '评房已完成'])
        {
            def opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? order by executionSequence asc", [externalOpportunity?.id])
            if (opportunityFlow)
            {
                externalOpportunity.stage = opportunityFlow?.stage
                externalOpportunity.save flush: true
            }
        }*/

        render opportunity as JSON
        return
    }
}

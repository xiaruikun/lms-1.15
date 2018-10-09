package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

@Transactional(readOnly = true)
class TouchOpportunityController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def opportunityService
    def opportunityContactService
    def creditReportService

    @Secured(['permitAll'])
    @Transactional
    def updatePropertyAdditionalInformation()
    {
        //助手端修改下户详情
        def json = request.JSON
        println "******************* updatePropertyAdditionalInformation ********************"
        println json

        def sessionId = json['sessionId']
        def opportunityId = json['opportunityId']?.toString()
        String propertyAdditionalInformationId = json['propertyAdditionalInformationId']
        String decoration = json['decoration']
        String numberOfTenant = json['numberOfTenant']
        String numberOfElderlyPeople = json['numberOfElderlyPeople']
        String numberOfSegments = json['numberOfSegments']

        if (!sessionId)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!propertyAdditionalInformationId)
        {
            def errors = [errorCode: 4298, errorMessage: "详情为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunityId || opportunityId.length() == 0)
        {
            def errors = [errorCode: 4270, errorMessage: "订单缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunity = Opportunity.findById opportunityId
        if (!opportunity)
        {
            def errors = [errorCode: 4271, errorMessage: "订单不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!(decoration in ['豪华装修', '精装修', '一般装修', '简装', '毛坯']))
        {
            def errors = [errorCode: 4297, errorMessage: "装修格式有误"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (numberOfTenant == null || !(numberOfTenant.matches(/^[0-9]\d*$/)) || Integer.parseInt(numberOfTenant) < 0)
        {
            def errors = [errorCode: 4296, errorMessage: "出租情况格式有误"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (numberOfElderlyPeople == null || !(numberOfTenant.matches(/^[0-9]\d*$/)) || Integer.parseInt(numberOfTenant) < 0)
        {
            def errors = [errorCode: 4295, errorMessage: "有无老人格式有误"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (numberOfSegments == null || !(numberOfTenant.matches(/^[0-9]\d*$/)) || Integer.parseInt(numberOfTenant) < 0)
        {
            def errors = [errorCode: 4294, errorMessage: "隔断格式有误"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        User user = User.findByAppSessionId sessionId
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def stage = opportunity?.stage

        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage opportunity, user, stage
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            propertyAdditionalInformationId = propertyAdditionalInformationId.toInteger()
            PropertyAdditionalInformation pinfo = PropertyAdditionalInformation.get propertyAdditionalInformationId
            if (!pinfo)
            {
                def errors = [errorCode: 4293, errorMessage: "此下户详情不存在"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (pinfo.validate())
            {
                pinfo.decoration = decoration
                pinfo.numberOfTenant = Integer.parseInt(numberOfTenant)
                pinfo.numberOfElderlyPeople = Integer.parseInt(numberOfElderlyPeople)
                pinfo.numberOfSegments = Integer.parseInt(numberOfSegments)
                pinfo.save flush: true

                render pinfo as JSON
            }
            else
            {
                println pinfo.errors
                def errors = [errorCode: 5265, errorMessage: "对不起，信息修改失败，请稍后重试"]
                render JsonOutput.toJson(errors), status: 400
            }
        }
        else
        {
            def errors = [errorCode: 4270, errorMessage: "无权限"]
            render JsonOutput.toJson(errors), status: 400
        }

    }

    @Secured(['permitAll'])
    @Transactional
    def getTodo()
    {
        //助手端非下户组待办列表
        def json = request.JSON
        println "******************* getTodo ********************"
        println json

        def max = 10
        def sessionId = json['sessionId']
        def searchValue = json['searchValue']
        def offset = json['offset']?.toInteger()


        if (!sessionId)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        User user = User.findByAppSessionId sessionId
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def teamRole = TeamRole.findByName("Approval")
        def opportunityIdString = ""
        def opportunityRoleList = OpportunityRole.findAll "from OpportunityRole as o where o.teamRole.id=${teamRole.id} and o.user.id=${user.id}"
        opportunityRoleList.each {
            if (it.stage.id == it.opportunity.stage.id)
            {
                opportunityIdString += (it.opportunity.id + ",")
            }
        }
        if (offset == null)
        {
            def errors = [errorCode: 5261, errorMessage: "分页数缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        offset *= 10
        def opportunityList = []
        if (opportunityIdString.length() > 1)
        {
            opportunityIdString = opportunityIdString.substring(0, opportunityIdString.length() - 1)
            String sql = "from Opportunity as o where o.id in (${opportunityIdString})"
            if (searchValue != null && searchValue != '')
            {
                sql += " and (o.serialNumber like '%${searchValue}%' or o.fullName ='${searchValue}')"
            }
            sql += " order by modifiedDate desc"
            //            println sql
            println max
            println offset
            opportunityList = Opportunity.findAll(sql, [max: max, offset: offset])
        }
        println opportunityList.size()
        render opportunityList as JSON

    }

    @Secured(['permitAll'])
    def query()
    {
        //助手端非下户组订单列表
        def json = request.JSON
        println "******************* query ********************"
        println json

        def max = 10
        def sessionId = json['sessionId']
        def searchValue = json['searchValue']
        def stages = json['stages']
        def status = json['status']
        def offset = json['offset']?.toInteger()
        offset *= 10

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
        String sql = "from Opportunity as o where 1=1"
        def stageIdString = ""
        if (stages && stages.size() > 0)
        {
            //            stages = JSON.parse(stages)
            stages.each {
                //                println it
                def stage = OpportunityStage.findByName(it)
                if (!stage)
                {
                    def errors = [errorCode: 4261, errorMessage: "无效的订单状态：'${it}'"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                else
                {
                    stageIdString += stage.id + ","
                }
            }
        }

        if (status != null && status != "")
        {
            if (status == "Completed")
            {
                sql += " and o.status ='Completed'"
            }
            else if (status == "Failed")
            {
                sql += " and o.status ='Failed'"
            }
            else
            {
                sql += " and o.status ='Pending'"
            }
        }
        //        else
        //        {
        //            sql += " and o.status in ('Pending','Failed','Completed')"
        //        }
        def opportunityIdString = ""
        def opportunityList = []
        if (stages && stages.size() > 0 && stages != "")
        {
            def teamRole = TeamRole.findByName("Approval")
            //            def teamRoleE = TeamRole.findByName("Edit")
            def opportunityRoleList = OpportunityRole.findAll "from OpportunityRole as o where o.teamRole.id = '${teamRole?.id}' and o.user.id=${user?.id}"
            opportunityRoleList.each {
                if (it?.stage?.id == it?.opportunity?.stage?.id)
                {
                    opportunityIdString += (it?.opportunity.id + ",")
                }
            }
        }
        else
        {
            def teSql = " from OpportunityTeam as o where 1=1 and o.user.id = ${user.id}"
            if (searchValue != null && searchValue != '')
            {
                teSql += " and (o.opportunity.serialNumber like '%${searchValue}%' or o.opportunity.fullName like '%${searchValue}%')"
            }
            teSql += " order by modifiedDate desc"
            println teSql
            def buffer = OpportunityTeam.findAll(teSql, [max: max, offset: offset])
            buffer.each {
                opportunityList.add(it.opportunity)
            }
            //            def count = Opportunity.findAll(teSql).size()
            //            println "count:" + count
        }
        if (opportunityIdString.length() > 1)
        {
            opportunityIdString = opportunityIdString.substring(0, opportunityIdString.length() - 1)
            sql += " and o.id in (${opportunityIdString})"

            //            if (stages && stages.size() > 0 && stages != "")
            //            {
            //                stageIdString = stageIdString.substring(0, stageIdString.length() - 1)
            //                sql += " and o.stage.id in (${stageIdString})"
            //            }

            if (searchValue != null && searchValue != '')
            {
                sql += " and (o.serialNumber like '%${searchValue}%' or o.fullName ='${searchValue}')"
            }
            sql += " order by modifiedDate desc"
            //            println sql
            opportunityList = Opportunity.findAll(sql, [max: max, offset: offset])
        }
        println opportunityList.size()
        render opportunityList as JSON
    }

    @Transactional
    @Secured(['permitAll'])
    def approval()
    {
        //助手端非下户组工作流下一步
        def json = request.JSON
        println "******************* approval ********************"
        println json

        def sessionId = json['sessionId']
        def opportunityId = json['opportunityId']

        if (sessionId == null || sessionId == "")
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        User user = User.findByAppSessionId sessionId
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (opportunityId == null || opportunityId == "")
        {
            def errors = [errorCode: 4270, errorMessage: "订单缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunity = Opportunity.findById opportunityId
        def stage = opportunity?.stage
        def flag

        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {

            flag = opportunityService.approve opportunity
            if (flag)
            {
                render opportunity as JSON
            }
            else
            {
                def errors = [errorCode: 4280, errorMessage: "更新失败"]
                render JsonOutput.toJson(errors), status: 400
            }
        }
        else
        {
            def errors = [errorCode: 4270, errorMessage: "无权限"]
            render JsonOutput.toJson(errors), status: 400
        }
    }

    @Transactional
    @Secured(['permitAll'])
    def reject()
    {
        //助手端非下户组工作流上一步
        def json = request.JSON
        println "******************* reject ********************"
        println json


        def sessionId = json['sessionId']
        def opportunityId = json['opportunityId']

        if (sessionId == null || sessionId == "")
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
        if (opportunityId == null || opportunityId == "")
        {
            def errors = [errorCode: 4270, errorMessage: "订单缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunity = Opportunity.findById opportunityId
        def stage = opportunity?.stage
        def flag

        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            flag = opportunityService.reject opportunity
            if (flag)
            {
                render opportunity as JSON
            }
            else
            {
                def errors = [errorCode: 4280, errorMessage: "更新失败"]
                render JsonOutput.toJson(errors), status: 400
            }
        }
        else
        {
            def errors = [errorCode: 4270, errorMessage: "无权限"]
            render JsonOutput.toJson(errors), status: 400
        }
    }

    @Transactional
    @Secured(['permitAll'])
    def cancel()
    {
        //助手端非下户组失败操作
        def json = request.JSON
        println "******************* cancel ********************"
        println json


        def sessionId = json['sessionId']
        def opportunityId = json['opportunityId']

        if (sessionId == null || sessionId == "")
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        User user = User.findByAppSessionId sessionId
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (opportunityId == null || opportunityId == "")
        {
            def errors = [errorCode: 4270, errorMessage: "订单缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunity = Opportunity.findById opportunityId

        def stage = opportunity?.stage
        def flag

        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            flag = opportunityService.cancel opportunity
            if (flag)
            {
                render opportunity as JSON
            }
            else
            {
                def errors = [errorCode: 4280, errorMessage: "更新失败"]
                render JsonOutput.toJson(errors), status: 400
            }
        }
        else
        {
            def errors = [errorCode: 4270, errorMessage: "无权限"]
            render JsonOutput.toJson(errors), status: 400
        }
    }

    @Transactional
    @Secured(['permitAll'])
    def updateOpportunity()
    {
        //助手端非下户组订单信息编辑保存
        def json = request.JSON
        println "******************* updateOpportunity ********************"
        println json

        def sessionId = json['sessionId']?.toString()
        def opportunityId = json['opportunityId']?.toString()

        if (!sessionId || sessionId.length() == 0)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        User user = User.findByAppSessionId sessionId
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunityId || opportunityId.length() == 0)
        {
            def errors = [errorCode: 4270, errorMessage: "订单缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunity = Opportunity.findById opportunityId
        if (!opportunity)
        {
            def errors = [errorCode: 4271, errorMessage: "订单不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def stage = opportunity?.stage

        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage opportunity, user, stage
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            //评房信息
            def address = json['address']?.toString()
            if (address && address.length() > 1)
            {
                opportunity.collaterals[0].address = address
            }
            def building = json['building']?.toString()
            if (building && building.length() > 1)
            {
                opportunity.collaterals[0].building = Integer.parseInt(building)
            }
            def floor = json['floor']?.toString()
            if (floor && floor.length() > 1)
            {
                opportunity.collaterals[0].floor = floor
            }
            def roomNumber = json['roomNumber']?.toString()
            if (roomNumber && roomNumber.length() > 1)
            {
                opportunity.collaterals[0].roomNumber = roomNumber
            }
            def area = json['area']?.toString()
            if (area && area.length() > 1)
            {
                opportunity.collaterals[0].area = Double.parseDouble area
            }
            /*def numberOfLivingRoom = json['numberOfLivingRoom']?.toString()
            if (numberOfLivingRoom && numberOfLivingRoom.length() > 1)
            {
                opportunity.collateral.numberOfLivingRoom = Integer.parseInt numberOfLivingRoom
            }*/
            def orientation = json['orientation']?.toString()
            if (orientation && orientation.length() > 1 && orientation in ["东", "南", "西", "北", "东西", "南北", "东南",
                "东北", "西南", "西北"])
            {
                opportunity.collaterals[0].orientation = orientation
            }
            def houseType = json['houseType']?.toString()
            if (address && address.length() > 1)
            {
                def hType = HouseType.findByName houseType
                if (hType)
                {
                    opportunity.collaterals[0].houseType = hType.name
                }
                else
                {
                    def errors = [errorCode: 4277, errorMessage: "此房屋类型不存在"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }
            def unitPrice = json['unitPrice']?.toString()
            if (unitPrice && unitPrice.length() > 1)
            {
                opportunity.unitPrice = Double.parseDouble unitPrice
            }
            def loanAmount = json['loanAmount']?.toString()
            if (loanAmount && loanAmount.length() > 1)
            {
                opportunity.loanAmount = Double.parseDouble loanAmount
            }

            //报单信息
            def requestedAmount = json['requestedAmount']?.toString()
            if (requestedAmount && requestedAmount.length() > 1)
            {
                opportunity.requestedAmount = Double.parseDouble requestedAmount
            }
            def loanDuration = json['loanDuration']?.toString()
            if (loanDuration && loanDuration.length() > 1)
            {
                opportunity.loanDuration = Integer.parseInt loanDuration
            }
            def mortgageType = json['mortgageType']?.toString()
            if (mortgageType && mortgageType.length() > 1)
            {
                def mType = MortgageType.findByName mortgageType
                if (mType)
                {
                    opportunity.mortgageType = mType

                }
                else
                {
                    def errors = [errorCode: 4276, errorMessage: "此抵押类型不存在"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }
            def typeOfFirstMortgage = json['typeOfFirstMortgage']?.toString()
            if (typeOfFirstMortgage && typeOfFirstMortgage.length() > 1)
            {
                def tOfFirstMortgage = TypeOfFirstMortgage.findByName typeOfFirstMortgage
                if (tOfFirstMortgage)
                {
                    opportunity.typeOfFirstMortgage = tOfFirstMortgage

                }
                else
                {
                    def errors = [errorCode: 4276, errorMessage: "此一抵类型不存在"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }
            def firstMortgageAmount = json['firstMortgageAmount']?.toString()
            if (firstMortgageAmount && firstMortgageAmount.length() > 1)
            {
                opportunity.firstMortgageAmount = Double.parseDouble firstMortgageAmount
            }
            def secondMortgageAmount = json['secondMortgageAmount']?.toString()
            if (secondMortgageAmount && secondMortgageAmount.length() > 1)
            {
                opportunity.secondMortgageAmount = Double.parseDouble secondMortgageAmount
            }
            Contact contact
            def opportunityContact = json['contacts']
            opportunityContact.each {
                def fullName = it['fullName']?.toString()
                def idNumber = it['idNumber']?.toString()
                def cellphone = it['cellphone']?.toString()
                def maritalStatus = it['maritalStatus']?.toString()
                def typeName = it['type']?.toString()
                if ((fullName && fullName.length() > 1) || (idNumber && idNumber.length() > 1) || (cellphone && cellphone
                    .length() > 1) || (maritalStatus && maritalStatus.length() > 1 && maritalStatus in ["未婚", "已婚", "再婚",
                    "离异", "丧偶"]))
                {
                    contact = opportunityContactService.getContact(opportunity, typeName)
                    if (contact)
                    {
                        if (fullName && fullName.length() > 1)
                        {
                            if (typeName == "借款人")
                            {
                                opportunity.fullName = fullName
                            }
                            contact.fullName = fullName
                        }
                        if (idNumber && idNumber.length() > 1)
                        {
                            if (typeName == "借款人")
                            {
                                opportunity.idNumber = idNumber
                            }
                            contact.idNumber = idNumber
                        }
                        if (cellphone && cellphone.length() > 1)
                        {
                            if (typeName == "借款人")
                            {
                                opportunity.cellphone = cellphone
                            }
                            contact.cellphone = cellphone
                        }
                        if (maritalStatus && maritalStatus.length() > 1 && maritalStatus in ["未婚", "已婚", "再婚",
                            "离异", "丧偶"])
                        {
                            contact.maritalStatus = maritalStatus
                        }

                    }
                    else
                    {
                        opportunityService.addContacts(fullName, idNumber, cellphone, maritalStatus, typeName,
                                                       opportunity)
                    }
                }
            }

            //初审
            def maximumAmountOfCredit = json['maximumAmountOfCredit']?.toString()
            if (maximumAmountOfCredit && maximumAmountOfCredit.length() > 1)
            {
                opportunity.maximumAmountOfCredit = Double.parseDouble maximumAmountOfCredit
            }
            def level = json['level']?.toString()
            if (level && level.length() > 1)
            {
                def contactLevel = ContactLevel.findByName level
                if (contactLevel)
                {
                    opportunity.lender.level = contactLevel

                }
                else
                {
                    def errors = [errorCode: 4275, errorMessage: "此客户级别不存在"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }

            def memo = json['memo']?.toString()
            if (memo && memo.length() > 1)
            {
                opportunity.memo = memo
            }
            //面签

            // 付息方式
            def interestPaymentMethod = json['interestPaymentMethod']?.toString()
            if (interestPaymentMethod && interestPaymentMethod.length() > 1)
            {
                InterestPaymentMethod method = InterestPaymentMethod.findByName interestPaymentMethod
                if (method)
                {
                    opportunity.interestPaymentMethod = method
                }
                else
                {
                    def errors = [errorCode: 4274, errorMessage: "此付息方式不存在"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }

            opportunity.save flush: true


            render opportunity as JSON
        }
        else
        {
            def errors = [errorCode: 4270, errorMessage: "无权限"]
            render JsonOutput.toJson(errors), status: 400
        }
    }

    @Transactional
    @Secured(['permitAll'])
    def queryUsers()
    {
        //助手端非下户组联系方式
        def json = request.JSON
        println "******************* queryUsers ********************"
        println json


        def sessionId = json['sessionId']?.toString()
        def opportunityId = json['opportunityId']?.toString()

        if (!sessionId || sessionId.length() == 0)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        User user = User.findByAppSessionId sessionId
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunityId || opportunityId.length() == 0)
        {
            def errors = [errorCode: 4270, errorMessage: "订单id缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunity = Opportunity.findById opportunityId
        if (!opportunity)
        {
            def errors = [errorCode: 4500, errorMessage: "订单不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def teamRole = TeamRole.findByName("Approval")
        String sql = "from OpportunityRole as o where o.opportunity.id=${opportunity.id} and o.teamRole.id=${teamRole.id}"
        //获取此订单所有“Approval”权限的人
        def opportunityRoleList = OpportunityRole.findAll(sql)
        //获取当前配置的工作流(顺序)
        sql = "from OpportunityFlow as o where o.opportunity.id=${opportunity.id} order by execution_sequence ASC"
        def opportunityFlowList = OpportunityFlow.findAll(sql)
        def result = []
        opportunityFlowList.each {
            OpportunityFlow flow = it
            def flowResult = [:]
            flowResult["stageName"] = it.stage.name
            def users = []
            opportunityRoleList.each {
                OpportunityRole role = it
                if (flow.stage.id == role.stage.id)
                {
                    users.add(role.user)
                }
            }
            flowResult["users"] = users
            result.add(flowResult)
        }

        println result.size()
        render result as JSON

    }
}
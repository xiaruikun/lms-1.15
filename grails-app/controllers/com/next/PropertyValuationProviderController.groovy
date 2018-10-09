package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured
import sun.misc.BASE64Decoder

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class PropertyValuationProviderController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def propertyValuationProviderService
    def opportunityService
    def fileServerService
    def messageService
    def opportunityNotificationService
    def opportunityAppService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond PropertyValuationProvider.list(params), model: [propertyValuationProviderCount: PropertyValuationProvider.count()]
    }

    def show(PropertyValuationProvider propertyValuationProvider)
    {
        respond propertyValuationProvider
    }

    def create()
    {
        respond new PropertyValuationProvider(params)
    }

    @Transactional
    def save(PropertyValuationProvider propertyValuationProvider)
    {
        if (propertyValuationProvider == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (propertyValuationProvider.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond propertyValuationProvider.errors, view: 'create'
            return
        }

        propertyValuationProvider.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'propertyValuationProvider.label', default: 'PropertyValuationProvider'), propertyValuationProvider.id])
                redirect propertyValuationProvider
            }
            '*' { respond propertyValuationProvider, [status: CREATED] }
        }
    }

    def edit(PropertyValuationProvider propertyValuationProvider)
    {
        respond propertyValuationProvider
    }

    @Transactional
    def update(PropertyValuationProvider propertyValuationProvider)
    {
        if (propertyValuationProvider == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (propertyValuationProvider.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond propertyValuationProvider.errors, view: 'edit'
            return
        }

        propertyValuationProvider.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'propertyValuationProvider.label', default: 'PropertyValuationProvider'), propertyValuationProvider.id])
                redirect propertyValuationProvider
            }
            '*' { respond propertyValuationProvider, [status: OK] }
        }
    }

    @Transactional
    def delete(PropertyValuationProvider propertyValuationProvider)
    {

        if (propertyValuationProvider == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        propertyValuationProvider.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'propertyValuationProvider.label', default: 'PropertyValuationProvider'), propertyValuationProvider.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'propertyValuationProvider.label', default: 'PropertyValuationProvider'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Secured('permitAll')
    def appQueryPrice()
    {
        println "**********************  appQueryPrice ********************************"
        def json = request.JSON
        println json

        def sessionId = json["sessionId"]
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

        String city = json["city"]
        // String district = json["district"]?.trim()
        String building = json["building"]?.trim()
        String floor = json["floor"]
        String totalFloor = json["totalFloor"]
        String area = json["area"]
        String orientation = json["orientation"]
        String houseType = json["houseType"]
        String address = json["address"]
        String specialFactors = json["specialFactors"]
        String atticArea = json["atticArea"]

        if (!city)
        {
            def errors = [errorCode: 4100, errorMessage: "请选择所在城市"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        // if (!district)
        // {
        //     def errors = [errorCode: 4101, errorMessage: "请输入小区名称"]
        //     render JsonOutput.toJson(errors), status: 400
        //     return
        // }
        if (!building)
        {
            def errors = [errorCode: 4102, errorMessage: "请输入楼栋信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!floor || !(floor.matches(/^-?[1-9]\d*$/)))
        {
            def errors = [errorCode: 4103, errorMessage: "请输入所在楼层"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!totalFloor || !(totalFloor.matches(/^[1-9]\d*$/)))
        {
            def errors = [errorCode: 4104, errorMessage: "请输入总楼层"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (Integer.parseInt(floor) > Integer.parseInt(totalFloor))
        {
            def errors = [errorCode: 4132, errorMessage: "所在楼层必须小于等于总楼层"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!orientation || !(orientation in ["东", "南", "西", "北", "东西", "南北", "东南", "东北", "西南", "西北"]))
        {
            def errors = [errorCode: 4107, errorMessage: "请选择房屋朝向"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!area || !(area.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(area) <= 0 || Double.parseDouble(area) >= 10000)
        {
            def errors = [errorCode: 4108, errorMessage: "请输入正确的建筑面积"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!houseType || !(HouseType.findByName(houseType) in HouseType.findAll()))
        {
            def errors = [errorCode: 4109, errorMessage: "请选择住宅类型"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!address)
        {
            def errors = [errorCode: 4200, errorMessage: "请输入地址"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!specialFactors || !(SpecialFactors.findByName(specialFactors) in SpecialFactors.findAll()))
        {
            def errors = [errorCode: 4110, errorMessage: "请选择特殊因素"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (specialFactors == "跃层")
        {
            if (!atticArea || !(atticArea.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(atticArea) <= 0 || Double.parseDouble(atticArea) >= 10000)
            {
                def errors = [errorCode: 4133, errorMessage: "请输入正确的跃层面积"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        if (specialFactors == "顶楼带阁楼")
        {
            if (!atticArea || !(atticArea.matches(/^\d+(\.\d+)*$/)) || Double.parseDouble(atticArea) <= 0 || Double.parseDouble(atticArea) >= 10000)
            {
                def errors = [errorCode: 4134, errorMessage: "请输入正确的阁楼面积"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }

        def params = [:]
        params['city'] = city
        // params['projectName'] = district
        params['floor'] = floor
        params['totalFloor'] = totalFloor
        params['houseType'] = houseType
        params['area'] = area
        params['orientation'] = orientation
        params['address'] = address
        // params['atticArea'] = atticArea
        // TODO
        def result = propertyValuationProviderService.evaluate(params)


        if (result == 0 || !result['totalPrice'])
        {
            def errors = [errorCode: 4170, errorMessage: "估值失败，请输入正确的房产信息"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            Double unitPrice = result['unitPrice']?.toDouble()
            Double loanAmount = result['totalPrice'].toDouble() / 10000
            Double areaLimit = Double.parseDouble(area)
            def prices
            if (city in ['南京', '苏州', '武汉', '上海'])
            {
                if (city == "南京")
                {
                    if (areaLimit >= 200 || unitPrice >= 35000 || loanAmount >= 500)
                    {
                        prices = [unitPrice: unitPrice * (-1), loanAmount: loanAmount * (-1)]
                        render JsonOutput.toJson(prices), status: 200
                        return
                    }
                    else
                    {
                        prices = [unitPrice: unitPrice, loanAmount: loanAmount]
                        render JsonOutput.toJson(prices), status: 200
                        return
                    }
                }
                else if (city == "苏州")
                {
                    if (areaLimit >= 200 || unitPrice >= 25000 || loanAmount >= 400)
                    {
                        prices = [unitPrice: unitPrice * (-1), loanAmount: loanAmount * (-1)]
                        render JsonOutput.toJson(prices), status: 200
                        return
                    }
                    else
                    {
                        prices = [unitPrice: unitPrice, loanAmount: loanAmount]
                        render JsonOutput.toJson(prices), status: 200
                        return
                    }
                }
                else if (city == "武汉")
                {
                    if (areaLimit >= 200 || unitPrice >= 20000 || loanAmount >= 300)
                    {
                        prices = [unitPrice: unitPrice * (-1), loanAmount: loanAmount * (-1)]
                        println prices
                        render JsonOutput.toJson(prices), status: 200
                        return
                    }
                    else
                    {
                        prices = [unitPrice: unitPrice, loanAmount: loanAmount]
                        render JsonOutput.toJson(prices), status: 200
                        return
                    }
                }
                else if (city == "上海")
                {
                    if (areaLimit >= 200 || unitPrice >= 72000 || loanAmount >= 1000)
                    {
                        prices = [unitPrice: unitPrice * (-1), loanAmount: loanAmount * (-1)]
                        render JsonOutput.toJson(prices), status: 200
                        return
                    }
                    else
                    {
                        prices = [unitPrice: unitPrice, loanAmount: loanAmount]
                        render JsonOutput.toJson(prices), status: 200
                        return
                    }
                }
            }
            else
            {
                prices = [unitPrice: unitPrice, loanAmount: loanAmount]
                render JsonOutput.toJson(prices), status: 200
                return
            }
        }
    }

    @Secured('permitAll')
    def appQueryCity()
    {
        //中佳信3期评房阶段查询城市
        def json = request.JSON
        println "************************* appqueryCity ***************************"
        println json

        def sessionId = json["sessionId"]
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

        def cityList = []
        def accountCityList = []
        def account = Account.findByExternalId("zjx-1")
        accountCityList = AccountCity.findAllByAccount(account)

        accountCityList.each {
            if (it.city?.name !="武汉"&&it.city?.name !="上海"){
                cityList.add(it.city)
            }
        }
        if (cityList.size() <= 0)
        {
            def errors = [errorCode: 4111, errorMessage: "暂无城市开通评房功能"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            render cityList as JSON
            return
        }
    }

    @Secured('permitAll')
    def searchProjectName()
    {
        //中佳信3期搜索小区名称
        def json = request.JSON
        println "************************* searchProjectName ***************************"
        println json

        def sessionId = json["sessionId"]
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

        def city = json["city"]
        def district = json["district"]
        def projectName = json["projectName"]

        def cityList = []
        def accountCityList = []
        def account = Account.findByExternalId("zjx-1")
        accountCityList = AccountCity.findAllByAccount(account)
        accountCityList.each {
            cityList.add(it.city.name)
        }

        if (!city || !(city in cityList))
        {
            def errors = [errorCode: 4100, errorMessage: "请选择所在城市"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!district)
        {
            def errors = [errorCode: 4160, errorMessage: "请输入所属区县"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!projectName)
        {
            def errors = [errorCode: 4101, errorMessage: "请输入小区名称"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def result = propertyValuationProviderService.searchProjectName(city, projectName)

        if (result != 0)
        {
            //            println result
            render result, status: 200
        }
        else
        {
            def list = []
            //            println list
            render list as JSON
            /*def errors = [errorCode: 4607, errorMessage: "网络不稳定，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400*/
        }
    }

    @Transactional
    @Secured('permitAll')
    def appQuery()
    {
        //中佳信端3期极速询值
        println "********** 极速询值 appQuery*************"
        def json = request.JSON
        println json
        def sessionId = json["sessionId"]
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

        String city = json["city"]
        //城市
        String district = json["district"]
        //所属区县
        String projectName = json["projectName"]
        //小区名称
        String building = json["building"]
        //楼栋信息
        String unit = json["unit"]
        //单元信息
        String floor = json["floor"]
        //楼层
        String roomNumber = json["roomNumber"]
        //户号
        String totalFloor = json["totalFloor"]
        //总楼层
        String area = json["area"]
        //住宅面积
        String address = json["address"]
        //住房地址
        String orientation = json["orientation"]
        //朝向
        String houseType = json["houseType"]
        //物业类型
        String specialFactors = json["specialFactors"]
        //特殊因素
        String assetType = json["assetType"]
        //
        String requestStartTime = json["requestStartTime"]
        //评房开始时间
        def appliedTotalPrice = json['appliedTotalPrice']
        //申请金额
        String atticArea = json["atticArea"]
        //阁楼、跃层面积

        //        if (!appliedTotalPrice || !appliedTotalPrice.toString().matches("^[0-9]+([.][0-9]+){0,1}\$"))
        //        {
        //            def errors = [errorCode: 4903, errorMessage: "申请金额填写有误"]
        //            render JsonOutput.toJson(errors), status: 400
        //            return
        //        }
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
        if (!orientation || !(orientation in ["东", "南", "西", "北", "东西", "南北", "东南", "东北", "西南", "西北"]))
        {
            def errors = [errorCode: 4111, errorMessage: "请选择房屋朝向"]
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
            def errors = [errorCode: 4113, errorMessage: "请输入户号"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!requestStartTime)
        {
            def errors = [errorCode: 4114, errorMessage: "开始时间不能为空"]
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

        //新建collateral
        Collateral collateral = new Collateral()
        collateral.requestStartTime = new Date().parse("yyyy-MM-dd HH:mm:ss", requestStartTime)
        collateral.city = City.findByName(city)
        collateral.district = district
        collateral.projectName = projectName
        //collateral.building = Integer.parseInt(building)
        collateral.building = building
        if (unit)
        {
            collateral.unit = unit
        }
        else
        {
            collateral.unit = "0"
        }
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
        collateral.orientation = orientation
        collateral.houseType = houseType
        collateral.assetType = assetType
        if (specialFactors && SpecialFactors.findByName(specialFactors) in SpecialFactors.findAll())
        {
            collateral.specialFactors = specialFactors
        }
        if (appliedTotalPrice)
        {
            collateral.appliedTotalPrice = Double.parseDouble(appliedTotalPrice)
        }
        //        else
        //        {
        //            collateral.appliedTotalPrice = 0
        //        }
        def result = propertyValuationProviderService.queryPrice(collateral)
        println result
        if (result != 0)
        {
            def unitPrice = result['unitPrice']?.toString()
            def externalId = result['externalId']?.toString()
            def status = result['status']?.toString()
            //新建opportunity
            def opportunity = new Opportunity()

            def loanAmount = Double.parseDouble(unitPrice) * Double.parseDouble(area) / 10000
            if (loanAmount < 0)
            {
                opportunity.loanAmount = loanAmount * (-1)
            }
            else
            {
                opportunity.loanAmount = loanAmount
            }
            // if (specialFactors in ["跃层", "顶楼带阁楼"] && collateral.atticArea > 0)
            // {
            //     opportunity.loanAmount += Double.parseDouble(unitPrice) * collateral.atticArea / 20000
            // }
            def user = contact.user
            opportunity.contact = contact
            if (status == "Completed")
            {
                opportunity.stage = OpportunityStage.findByCode("02")
                collateral.requestEndTime = new Date()
            }
            else
            {
                opportunity.stage = OpportunityStage.findByCode("15")
            }
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

            println "######新建的opportunity.id###########"
            println opportunity.id

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
                collateral.save flush: true
            }
            else
            {
                println collateral.errors
            }

            //订单初始化
            println "######订单初始化###########"
            opportunityService.initOpportunity(opportunity)
            // render collateral as JSON
            def collateralData = [:]
            collateralData.status = collateral?.status
            collateralData.totalPrice = collateral?.totalPrice
            collateralData.specialFactors = collateral?.specialFactors
            collateralData.atticArea = collateral?.atticArea
            collateralData.unitPrice = collateral?.unitPrice
            render JsonOutput.toJson(collateralData), status: 200

        }
        else
        {
            def errors = [errorCode: 4604, errorMessage: "网络不稳定，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
        }

    }

    /*@Transactional*/

    @Secured('permitAll')
    def appQuery2()
    {
        //中佳信端3期极速询值
        println "********** 极速询值 appQuery2*************"
        def json = request.JSON
        def sessionId = json["sessionId"]
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

        String city = json["city"]
        //城市
        String district = json["district"]
        //所属区县
        String projectName = json["projectName"]
        //小区名称
        String building = json["building"]
        //楼栋信息
        //  String unit = json["unit"]

        //单元信息
        String floor = json["floor"]

        //楼层
        String roomNumber = json["roomNumber"]
        //户号
        String totalFloor = json["totalFloor"]
        //总楼层
        String area = json["area"]
        //住宅面积
        String address = json["address"]
        //住房地址
        // String orientation = json["orientation"]
        //朝向
        String houseType = json["houseType"]
        //物业类型
        String specialFactors = json["specialFactors"]
        //特殊因素
        String assetType = json["assetType"]
        //
        String requestStartTime = json["requestStartTime"]
        //评房开始时间
        def appliedTotalPrice = json['appliedTotalPrice']
        //申请金额
        String atticArea = json["atticArea"]
        //阁楼、跃层面积

        //        if (!appliedTotalPrice || !appliedTotalPrice.toString().matches("^[0-9]+([.][0-9]+){0,1}\$"))
        //        {
        //            def errors = [errorCode: 4903, errorMessage: "申请金额填写有误"]
        //            render JsonOutput.toJson(errors), status: 400
        //            return
        //        }
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
        /* if (!orientation || !(orientation in ["东", "南", "西", "北", "东西", "南北", "东南", "东北", "西南", "西北"]))
         {
             def errors = [errorCode: 4111, errorMessage: "请选择房屋朝向"]
             render JsonOutput.toJson(errors), status: 400
             return
         }*/
        if (!houseType || !(HouseType.findByName(houseType) in HouseType.findAll()))
        {
            def errors = [errorCode: 4112, errorMessage: "请选择住宅类型"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (!assetType)
        {
            def errors = [errorCode: 4113, errorMessage: "请输入户号"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!requestStartTime)
        {
            def errors = [errorCode: 4114, errorMessage: "开始时间不能为空"]
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

        //新建collateral
        Collateral collateral = new Collateral()
        collateral.requestStartTime = new Date().parse("yyyy-MM-dd HH:mm:ss", requestStartTime)
        collateral.city = City.findByName(city)
        collateral.district = district
        collateral.projectName = projectName
        //  collateral.building = Integer.parseInt(building)
        collateral.building = building
        collateral.unit = "0"
        /* if (unit)
         {
             collateral.unit = unit
         }
         else
         {
             collateral.unit = "0"
         }*/
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
        //collateral.orientation = orientation
        collateral.houseType = houseType
        collateral.assetType = assetType
        if (specialFactors && SpecialFactors.findByName(specialFactors) in SpecialFactors.findAll())
        {
            collateral.specialFactors = specialFactors
        }
        if (appliedTotalPrice)
        {
            collateral.appliedTotalPrice = Double.parseDouble(appliedTotalPrice)
        }
        //        else
        //        {
        //            collateral.appliedTotalPrice = 0
        //        }
        //new Opportunity start
        def opportunity = new Opportunity()
        def user = contact.user
        opportunity.contact = contact
        opportunity.user = user
        opportunity.account = user?.account
        opportunity.save()
        collateral.opportunity = opportunity
        //new Opportunity end
        def result = propertyValuationProviderService.queryPrice2(collateral)
        println result
        if (result != 0)
        {
            def unitPrice = result['unitPrice']?.toString()
            def externalId = result['externalId']?.toString()
            def status = result['status']?.toString()
            //新建opportunity
            // def opportunity = new Opportunity()

            def loanAmount = Double.parseDouble(unitPrice) * Double.parseDouble(area) / 10000
            if (loanAmount < 0)
            {
                opportunity.loanAmount = loanAmount * (-1)
            }
            else
            {
                opportunity.loanAmount = loanAmount
            }
            // if (specialFactors in ["跃层", "顶楼带阁楼"] && collateral.atticArea > 0)
            // {
            //     opportunity.loanAmount += Double.parseDouble(unitPrice) * collateral.atticArea / 20000
            // }
            // new opportunity start
            // def user = contact.user
            // opportunity.contact = contact
            // new opportunity end
            if (status == "Completed")
            {
                opportunity.stage = OpportunityStage.findByCode("02")
                collateral.requestEndTime = new Date()
            }
            else
            {
                opportunity.stage = OpportunityStage.findByCode("15")
            }
            // new opportunity start
            // opportunity.user = user
            // opportunity.account = user?.account
            // new opportunity end
            opportunity.unitPrice = Double.parseDouble(unitPrice)
            opportunity.loanApplicationProcessType = LoanApplicationProcessType.findByName("先评房再报单")
            opportunity.valuationType = "fast"
            if (opportunity.validate())
            {
                opportunity.save flush: true
            }
            else
            {
                println opportunity.errors
            }

            println "######新建的opportunity.id###########"
            println opportunity.id

            // collateral.opportunity = opportunity
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
                println collateral.errors
            }

            //订单初始化
            println "######订单初始化###########"
            //            opportunityService.initOpportunity(opportunity)
            opportunityAppService.initOpportunity(opportunity)
            // render collateral as JSON
            def collateralData = [:]
            collateralData.status = collateral?.status
            collateralData.totalPrice = collateral?.totalPrice
            collateralData.specialFactors = collateral?.specialFactors
            collateralData.atticArea = collateral?.atticArea
            collateralData.unitPrice = collateral?.unitPrice
            render JsonOutput.toJson(collateralData), status: 200

        }
        else
        {
            def errors = [errorCode: 4604, errorMessage: "网络不稳定，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
        }

    }

    /*@Transactional*/

    @Secured('permitAll')
    def appQuery3()
    {
        println "********** 极速询值 appQuery3 *************"
        def json = request.JSON
        def attachments = json['attachments']
        def city = json['city']
        def sessionId = json["sessionId"]

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
        if (!city)
        {
            def errors = [errorCode: 4101, errorMessage: "请选择所在城市"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        println "######新建房产信息###########"
        Collateral collateral = new Collateral()
        collateral.requestStartTime = new Date()
        collateral.city = City.findByName(city)
        collateral.district = "未知"
        collateral.projectName = "未知"
        collateral.building = "未知"
        collateral.orientation = "未知"
        collateral.unit = "未知"
        collateral.atticArea = 0
        collateral.floor = "未知"
        collateral.roomNumber = "未知"
        collateral.totalFloor = "未知"
        collateral.area = 0
        collateral.address = "未知"
        collateral.houseType = "未知"
        collateral.assetType = "未知"
        collateral.specialFactors = "未知"
        collateral.appliedTotalPrice = 0
        //new Opportunity start
        def opportunity = new Opportunity()
        opportunity.contact = contact
        opportunity.user = contact?.user
        opportunity.account = contact?.user?.account
        opportunity.save()
        collateral.opportunity = opportunity
        //new Opportunity end

        def result = propertyValuationProviderService.createCollateral(collateral)
        println "result:" + result
        if (result)
        {
            def externalId = result['externalId']?.toString()
            def status = result['status']?.toString()
            println "externalId:" + externalId
            println "status:" + status

            println "######新建订单信息###########"
            // def opportunity = new Opportunity()
            opportunity.loanAmount = 0
            // opportunity.contact = contact
            opportunity.stage = OpportunityStage.findByCode("15")
            // opportunity.user = contact?.user
            // opportunity.account = contact?.user?.account
            opportunity.unitPrice = 0
            opportunity.loanApplicationProcessType = LoanApplicationProcessType.findByName("直接报单")
            opportunity.valuationType = "accuracy"

            if (opportunity.validate())
            {
                opportunity.save flush: true
            }
            else
            {
                println opportunity.errors
            }

            println "######新建的opportunity.id###########"
            println opportunity.id

            // collateral.opportunity = opportunity
            collateral.unitPrice = 0
            collateral.totalPrice = 0
            collateral.externalId = externalId
            collateral.status = "Pending"

            if (collateral.validate())
            {
                collateral.save flush: true
            }
            else
            {
                println collateral.errors
            }

            println "######订单初始化###########"
            //            opportunityService.initOpportunity(opportunity)
            opportunityAppService.initOpportunity(opportunity)

            def result1 = [:]
            result1['reasonOfPriceAdjustment'] = "unknown"
            result1['appliedTotalPrice'] = 0

            def ats = []
            def returnFlag = true
            attachments.each {
                def attachmentTypeName = it['attachmentType']
                def fileType = it['fileType']
                def file = it['file']
                if (!attachmentTypeName)
                {
                    returnFlag = false
                    def errors = [errorCode: 4906, errorMessage: "附件类型缺失"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                def attachmentType = AttachmentType.findByName(attachmentTypeName)
                if (!attachmentType)
                {
                    returnFlag = false
                    def errors = [errorCode: 4908, errorMessage: "附件类型不存在"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                if (!file)
                {
                    returnFlag = false
                    def errors = [errorCode: 4909, errorMessage: "附件缺失"]
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
                param.put("fileType", fileType)
                param.put("appSessionId", "b6759ecb-0bb8-4c04-93ab-762cb16d91bc")

                def simple = [:]
                simple['attachmentType'] = attachmentType

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
                    simple['fileName'] = fileUrl
                }

                // 删除临时文件
                if (fileImage.isFile() && fileImage.exists())
                {
                    fileImage.delete()
                }

                if (fileUrl)
                {
                    def attachment = new Attachments()
                    attachment.fileName = fileName
                    attachment.fileUrl = fileUrl
                    attachment.type = attachmentType
                    attachment.opportunity = opportunity

                    if (attachment.validate())
                    {
                        println "fileName" + attachment.fileName
                        println "fileUrl" + attachment.fileUrl

                        attachment.save flush: true
                        ats.add(simple)
                    }
                    else
                    {
                        returnFlag = false
                        println attachment.errors
                        return
                    }
                }
                else
                {
                    returnFlag = false
                    def errors = [errorCode: 5205, errorMessage: "调用上传图片服务失败，请稍后重试"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }
            if (!returnFlag)
            {
                return
            }

            result1['attachments'] = ats
            result1['externalId'] = collateral.externalId

            def resultJson = ""
            println "result1:" + result1
            String param = result1 as JSON
            println param
            resultJson = propertyValuationProviderService.suggestSubmmit1(param)

            if (!resultJson)
            {
                def errors = [errorCode: 4796, errorMessage: "调用评房反馈服务上传图片失败，请稍后重试"]
                render JsonOutput.toJson(errors), status: 400
            }

            println "######返回信息###########"
            def collateralData = [:]
            collateralData.status = collateral?.status
            collateralData.totalPrice = collateral?.totalPrice
            collateralData.specialFactors = collateral?.specialFactors
            collateralData.atticArea = collateral?.atticArea
            collateralData.unitPrice = collateral?.unitPrice

            render JsonOutput.toJson(collateralData), status: 200
        }
        else
        {
            def errors = [errorCode: 4604, errorMessage: "网络不稳定，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
        }
    }

    @Transactional
    @Secured('permitAll')
    def appTest()
    {
        //极速询值接口链接测试
        println "#############评房appTest############"
        def params = [:]
        params['city'] = "北京"
        params['district'] = "朝阳区"
        params['projectName'] = "巴拉巴拉"
        params['building'] = "3"
        params['unit'] = "4"
        params['floor'] = "5"
        params['roomNumber'] = "1"
        params['totalFloor'] = "13"
        params['area'] = "70"
        params['address'] = "朝阳区巴拉巴拉"
        params['orientation'] = "南"
        params['houseType'] = "普通住宅"
        params['specialFactors'] = "临湖"
        params['assetType'] = "住宅" //待定
        params = JsonOutput.toJson(params)

        def result = propertyValuationProviderService.queryPrice(params)

        def a = result['state']
        render a as JSON
    }

    @Transactional
    @Secured('permitAll')
    def receiveNotification()
    {
        println "********** receiveNotification *************"

        def json = request.JSON
        println json
        def unitPrice = json['unitPrice']
        def externalId = json['externalId']
        OpportunityFlow nextStage

        if (unitPrice && externalId)
        {
            def collateral = Collateral.findByExternalId(externalId)
            if (collateral)
            {
                def opportunity = collateral?.opportunity
                if (opportunity)
                {
                    def opportunityApproveStage = OpportunityStage.findByCode("08")
                    def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                    def opportunityApproveFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityApproveStage)
                    if (currentFlow?.executionSequence < opportunityApproveFlow?.executionSequence)
                    {
                        collateral.requestEndTime = new Date()
                        collateral.unitPrice = unitPrice.toDouble()
                        collateral.totalPrice = collateral.unitPrice * collateral.area / 10000
                        collateral.status = "Completed"
                        // 楼阁、跃层
                        // if (collateral.atticArea > 0)
                        // {
                        //     collateral.totalPrice += collateral.unitPrice * collateral.atticArea / 20000
                        // }
                        if (collateral.validate())
                        {
                            collateral.save flush: true
                        }
                        else
                        {
                            println collateral.errors
                        }

                        if (opportunity.stage.code == "15")
                        {
                            println "##########估值返回更新工作流#############"
                            opportunityService.approve(opportunity, nextStage)
                        }

                        //发送估值结果短信
                        opportunityNotificationService.sendMessageToUser(collateral)

                        def errors = [errorCode: 4000, errorMessage: "更新成功"]
                        render JsonOutput.toJson(errors), status: 200
                    }
                    else
                    {
                        def errors = [errorCode: 4301, errorMessage: "关联订单已完成审批，房产价格不可更改！"]
                        render JsonOutput.toJson(errors), status: 200
                    }
                }
                else
                {
                    println "########房产关联订单不存在#####################"
                    def errors = [errorCode: 4002, errorMessage: "房产关联订单不存在"]
                    render JsonOutput.toJson(errors), status: 200
                }
            }
            else
            {
                println "########房产不存在#####################"
                def errors = [errorCode: 4001, errorMessage: "房产不存在"]
                render JsonOutput.toJson(errors), status: 200
            }

        }
        else
        {
            println "##########unitPrice||externalId###信息为空#############"
            def errors = [errorCode: 4300, errorMessage: "返回信息有误"]
            render JsonOutput.toJson(errors), status: 400
        }

    }

    @Transactional
    @Secured('permitAll')
    def receiveNotification1()
    {
        println "********** receiveNotification1 *************"

        def json = request.JSON
        println json
        def unitPrice = json['unitPrice']
        def externalId = json['externalId']

        def city = json['city']
        def district = json['district']
        def projectName = json['projectName']
        def building = json['building']
        def floor = json['floor']
        def orientation = json['orientation']
        def unit = json['unit']
        def atticArea = json['atticArea']
        def roomNumber = json['roomNumber']
        def totalFloor = json['totalFloor']
        def area = json['area']
        def address = json['address']
        def houseType = json['houseType']
        def assetType = json['assetType']
        def specialFactors = json['specialFactors']
        def fastUnitPrice = json['fastUnitPrice']

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
        if (!floor)
        {
            def errors = [errorCode: 4105, errorMessage: "请输入所在楼层"]

            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!orientation || !(orientation in ["东", "南", "西", "北", "东西", "南北", "东南", "东北", "西南", "西北", "未知"]))
        {
            def errors = [errorCode: 4111, errorMessage: "请选择房屋朝向"]

            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!roomNumber)
        {
            def errors = [errorCode: 4106, errorMessage: "请输入户号"]

            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!totalFloor)
        {
            def errors = [errorCode: 4107, errorMessage: "请输入总楼层"]

            render JsonOutput.toJson(errors), status: 400
            return
        }
        // if (Integer.parseInt(floor) > Integer.parseInt(totalFloor))
        // {
        //     def errors = [errorCode: 4108, errorMessage: "所在楼层必须小于等于总楼层"]

        //     render JsonOutput.toJson(errors), status: 400
        //     return
        // }
        if (!area)
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
            def errors = [errorCode: 4113, errorMessage: "请输入户号"]

            render JsonOutput.toJson(errors), status: 400
            return
        }

        OpportunityFlow nextStage
        if (unitPrice && externalId)
        {
            def collateral = Collateral.findByExternalId(externalId)
            if (collateral)
            {
                def opportunity = collateral?.opportunity
                if (opportunity)
                {
                    def opportunityApproveStage = OpportunityStage.findByCode("08")
                    def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
                    def opportunityApproveFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityApproveStage)
                    if (currentFlow?.executionSequence < opportunityApproveFlow?.executionSequence)
                    {
                        collateral.district = district
                        collateral.projectName = projectName
                        collateral.building = building
                        collateral.orientation = orientation
                        collateral.unit = unit
                        collateral.atticArea = atticArea
                        collateral.floor = floor
                        collateral.roomNumber = roomNumber
                        collateral.totalFloor = totalFloor
                        collateral.area = area
                        collateral.address = address
                        collateral.houseType = houseType
                        collateral.assetType = assetType
                        collateral.specialFactors = specialFactors
                        collateral.fastUnitPrice = fastUnitPrice

                        collateral.requestEndTime = new Date()
                        collateral.unitPrice = unitPrice.toDouble()
                        collateral.totalPrice = collateral.unitPrice * collateral.area / 10000
                        collateral.status = "Completed"
                        // 楼阁、跃层
                        if (collateral.atticArea > 0)
                        {
                            collateral.totalPrice += collateral.unitPrice * collateral.atticArea / 20000
                        }
                        if (collateral.validate())
                        {
                            collateral.save flush: true
                            /**
                             * @Description 更新订单表字段
                             * @Author Nagelan
                             * @Params collateral的相关属性值
                             * @Return null
                             * @DateTime 2017/8/31 0031 12:59
                             */
                            opportunity.unitPrice = collateral.unitPrice
                            opportunity.area = collateral.area
                            opportunity.loanAmount = collateral.totalPrice
                            if (opportunity.validate()){
                                opportunity.save flush:true
                                //fastUnitPrice UPDATE
                                def flexField = OpportunityFlexField.findByNameAndCategory("快速成交价（万元）", OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity, FlexFieldCategory.findByName("抵押物评估值")))
                                if (flexField)
                                {
                                    flexField.value = fastUnitPrice
                                    flexField.save()
                                }
                                if (opportunity.stage.code == "15")
                                {
                                    println "##########估值返回更新工作流#############"
                                    opportunityService.approve(opportunity, nextStage)
                                }

                                //发送估值结果短信
                                if (opportunity?.contact?.cellphone){
                                    opportunityNotificationService.sendMessageToUser(collateral)
                                }else {
                                    println "订单"+opportunity?.serialNumber+"没有经纪人电话"
                                }
                                def errors = [errorCode: 4000, errorMessage: "更新成功"]

                                render JsonOutput.toJson(errors), status: 200
                            }
                            else
                            {
                                println opportunity.errors
                                def errors = [errorCode: 4003, errorMessage: "订单保存不成功"]

                                render JsonOutput.toJson(errors), status: 200
                            }

                        }
                        else
                        {
                            println collateral.errors
                            def errors = [errorCode: 4004, errorMessage: "房屋信息保存不成功"]
                            render JsonOutput.toJson(errors), status: 200
                        }

                    }
                    else
                    {
                        def errors = [errorCode: 4301, errorMessage: "关联订单已完成审批，房产价格不可更改！"]

                        render JsonOutput.toJson(errors), status: 200
                    }
                }
                else
                {
                    println "########房产关联订单不存在#####################"
                    def errors = [errorCode: 4002, errorMessage: "房产关联订单不存在"]

                    render JsonOutput.toJson(errors), status: 200
                }
            }
            else
            {
                println "########房产不存在#####################"
                def errors = [errorCode: 4001, errorMessage: "房产不存在"]

                render JsonOutput.toJson(errors), status: 200
            }

        }
        else
        {
            println "##########unitPrice||externalId###信息为空#############"
            def errors = [errorCode: 4300, errorMessage: "返回信息有误"]

            render JsonOutput.toJson(errors), status: 400
        }

    }

    @Transactional
    @Secured('permitAll')
    def suggestSubmit()
    {
        //中佳信端3期建议反馈
        println "********** suggestSubmit *************"
        def json = request.JSON

        // def flag = true

        // if (flag)
        // {
        //     println "******************关闭复评功能*******************"
        //     def errors = [errorCode: 4796, errorMessage: "调用评房反馈服务失败，请稍后重试"]
        //     render JsonOutput.toJson(errors), status: 400
        //     return
        // }

        def sessionId = json["sessionId"]
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
        def collateralId = json['collateralId']
        def opportunityId = json['opportunityId']
        def reasonOfPriceAdjustment = json['reasonOfPriceAdjustment']
        def appliedTotalPrice = json['appliedTotalPrice']
        def attachments = json['attachments']
        if (!collateralId)
        {
            def errors = [errorCode: 4904, errorMessage: "房产评估id不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def collateral = Collateral.findById(collateralId)
        if (collateral.reasonOfPriceAdjustment && collateral.reasonOfPriceAdjustment.length() > 0)
        {
            def errors = [errorCode: 4907, errorMessage: "意见反馈只能提交一次"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!opportunityId)
        {
            def errors = [errorCode: 4900, errorMessage: "订单id不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!reasonOfPriceAdjustment || reasonOfPriceAdjustment.toString().length() == 0)
        {
            def errors = [errorCode: 4902, errorMessage: "反馈因素不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //        if (!appliedTotalPrice || !appliedTotalPrice.toString().matches("^[0-9]+([.][0-9]+){0,1}\$"))
        //        {
        //            def errors = [errorCode: 4903, errorMessage: "建议金额填写有误"]
        //            render JsonOutput.toJson(errors), status: 400
        //            return
        //        }
        def opportunity = Opportunity.findById(opportunityId)
        if (!opportunity)
        {
            def errors = [errorCode: 4901, errorMessage: "订单不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //        println "##########建议反馈，回退工作流到价格待确认#############"
        //        opportunity.stage = OpportunityStage.findByCode("15")
        //        opportunity.save flush: true
        def result = [:]
        result['reasonOfPriceAdjustment'] = reasonOfPriceAdjustment
        if (appliedTotalPrice)
        {
            result['appliedTotalPrice'] = Double.parseDouble(appliedTotalPrice)
        }
        else
        {
            result['appliedTotalPrice'] = 0
        }
        def ats = []
        def returnFlag = true
        attachments.each {
            def type = it['attachmentType']
            def file = it['file']
            if (!type)
            {
                returnFlag = false
                def errors = [errorCode: 4906, errorMessage: "附件类型缺失"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            def fileType = AttachmentType.findByName(type)
            if (!fileType)
            {
                returnFlag = false
                def errors = [errorCode: 4908, errorMessage: "附件类型不存在"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (!file)
            {
                returnFlag = false
                def errors = [errorCode: 4909, errorMessage: "附件缺失"]
                render JsonOutput.toJson(errors), status: 400
                return
            }

            def simple = [:]
            simple['attachmentType'] = type
            def fileName = fileServerService.upload(file, "jpg")
            if (fileName)
            {
                def attachment = new Attachments()
                attachment.fileName = "http://s27.zhongjiaxin.com/fs/static/images/${fileName}.jpg"
                simple['fileName'] = "http://s27.zhongjiaxin.com/fs/static/images/${fileName}.jpg"
                println attachment.fileName
                attachment.type = fileType
                attachment.opportunity = opportunity
                attachment.contact = contact

                if (attachment.validate())
                {
                    attachment.save flush: true
                    ats.add(simple)
                }
                else
                {
                    returnFlag = false
                    println attachment.errors
                    return
                }
            }
            else
            {
                returnFlag = false
                def errors = [errorCode: 5205, errorMessage: "调用上传图片服务失败，请稍后重试"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        if (!returnFlag)
        {
            return
        }
        result['attachments'] = ats
        if (appliedTotalPrice)
        {
            collateral.appliedTotalPrice = Double.parseDouble(appliedTotalPrice)
        }
        collateral.reasonOfPriceAdjustment = reasonOfPriceAdjustment
        collateral.status = "Pending"
        collateral.save flush: true
        result['externalId'] = collateral.externalId
        println "result:" + result
        def resultJson = propertyValuationProviderService.suggestSubmmit(result)
        //def flag = true

        if (resultJson != "" && resultJson["true"])
        {
            if (resultJson["status"] == "Success")
            {
                def unitPrice = resultJson['unitPrice']
                OpportunityFlow nextStage

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
                            if (opportunity.validate())
                            {
                                opportunity.save flush: true
                            }
                            else
                            {
                                println opportunity.errors
                            }

                            //                            if (opportunity.stage.code == "15")
                            //                            {
                            //                                println "##########估值返回更新工作流#############"
                            //                                opportunityService.approve(opportunity, nextStage)
                            //                            }

                            //发送估值结果短信
                            opportunityNotificationService.sendMessageToUser(collateral)
                        }
                    }
                }
                def message = [code: 5000, message: "反馈已经提交成功，复评价格为" + opportunity.loanAmount + "万元"]
                render JsonOutput.toJson(message), status: 200
            }
            else
            {
                //发送复评结果短信
                messageService.sendMessage2(collateral?.opportunity?.contact?.cellphone, "【中佳信】您的订单（${collateral?.opportunity?.serialNumber}）复评失败，请联系复评人员。详情请登录中佳信端app评估记录中查看")
                def message = [code: 5000, message: "反馈已经提交成功，请联系复评人员"]
                render JsonOutput.toJson(message), status: 200
            }
        }
        else
        {
            def errors = [errorCode: 4796, errorMessage: "调用评房反馈服务失败，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
        }
    }
}

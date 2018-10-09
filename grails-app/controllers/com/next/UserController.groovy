package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER'])
@Transactional(readOnly = true)
class UserController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", appLogin: "POST", appQueryUser: "POST"]

    def userService
    def passwordEncoder
    def liquidityRiskReportService
    def opportunityService
    def springSecurityService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model: [userCount: User.count()]
    }

    @Transactional
    def searchUser()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def fullName = params["fullName"]
        def code = params["code"]
        def city = params["city"]
        def department = params["department"]

        String sql = "from User as u where 1=1"
        if (fullName)
        {
            sql += " and u.fullName like '%${fullName}%'"
        }
        if (code)
        {
            sql += " and u.code = '${code}'"
        }
        if (city)
        {
            sql += " and u.city.name = '${city}'"
        }
        if (department)
        {
            sql += " and u.department.name = '${department}'"
        }

        def max = params.max
        def offset = params.offset

        def list = User.findAll(sql, [max: max, offset: offset])
        def list1 = User.findAll(sql)
        def count = list1.size()

        def user = new User(params)
        def cityObj = City.findByName(city)
        def departmentObj = Department.findByName(department)
        user.city = cityObj
        user.department = departmentObj

        respond list, model: [userCount: count, user: user, params: params], view: 'index'
    }

    def show(User user)
    {
        def contactList = Contact.findAllByUserAndType(user, "Agent")
        def opportunityList = Opportunity.findAllByUser(user)
        def userRoleList = UserRole.findAllByUser(user)
        def reportingList = Reporting.findAllByManager(user)
        def activityList = Activity.findAllByUserOrAssignedTo(user, user)
        respond user, model: [userRoleList: userRoleList, contactList: contactList, opportunityList: opportunityList,
            reportingList: reportingList, activityList: activityList]
    }

    def create()
    {
        def accounts = Account.findAll()
        respond new User(params), model: [accounts: accounts]
    }

    @Transactional
    def save(User user)
    {
        if (user == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond user.errors, view: 'create'
            return
        }

        // user.code = userService.generateCode()

        user.save flush: true

        UserRole.create(user, Role.findByAuthority('ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER'))

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    def edit(User user)
    {
        def userrole = UserRole.findByUser(user)
        respond user, model: [userrole: userrole]
    }

    @Transactional
    def update(User user)
    {
        if (user == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond user.errors, view: 'edit'
            return
        }

        user.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: OK] }
        }
    }

    @Transactional
    def deleteRole()
    {
        String userId = params["userId"]
        String roleId = params["roleId"]
        def userRole = UserRole.findByUserAndRole(User.findById(userId), Role.findById(roleId))

        if (userRole == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        userRole.delete flush: true

        respond User.list(params), model: [userCount: User.count()], view: "index"

        /*request.withFormat{
            form multipartForm{
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userRole.label',
                default: 'userRole')])
                redirect action: "index", method: "GET"

            }
            '*'{render status: NO_CONTENT}
        }*/
    }

    @Transactional
    def delete(User user)
    {

        if (user == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        user.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label',
                                                                                          default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['permitAll'])
    def changePassword()
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        respond user
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['permitAll'])
    @Transactional
    def updatePassword(User user)
    {
        def oldPassword = params['oldPassword']
        def newPassword = params['newPassword']

        if (passwordEncoder.isPasswordValid(user.password, oldPassword, null))
        {
            user.password = newPassword
            user.save flush: true
            render([status: "success"] as JSON)
        }
        else
        {
            flash.message = message(code: 'user.password.matches.invalid')
            // respond user, view: 'changePassword'
            render([status: "error", errorMessage: message(code: 'user.password.matches.invalid')] as JSON)
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['permitAll'])
    @Transactional
    def resetPassword()
    {
        def user = User.findById(params['user'])
        def password = params['password']
        def destination = params['destination']
        user.password = password
        if (user.validate())
        {
            user.save flush: true
            flash.message = "密码重置成功"
            if (destination)
            {
                redirect controller: destination, action: 'index'
                return
            }
            else
            {
                redirect controller: 'userTeam', action: 'index'
                return
            }
        }
        else
        {
            flash.message = user.errors
            if (destination)
            {
                redirect controller: destination, action: 'index'
                return
            }
            else
            {
                redirect controller: 'userTeam', action: 'index'
                return
            }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appLogin()
    {
        //助手端登录。
        def json = request.JSON
        println "####################助手端登录############################"
        println json
        def username = json['username']
        def password = json['password']
        if (!username)
        {
            def errors = [errorCode: 5262, errorMessage: "登录名不可空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!password)
        {
            def errors = [errorCode: 5261, errorMessage: "密码不可空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def user = User.findByUsername(username)
        if (!user)
        {
            def errors = [errorCode: 4500, errorMessage: "用户名不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (passwordEncoder.isPasswordValid(user.password, password, null))
        {
            user.appSessionId = UUID.randomUUID().toString()
            if (user.validate())
            {
                user.save flush: true
                render JsonOutput.toJson([sessionId: user.appSessionId])
            }
            else
            {
                println user.errors
            }
        }
        else
        {
            def errors = [errorCode: 4400, errorMessage: "用户名或口令错误"]
            render JsonOutput.toJson(errors), status: 400
        }

    }

    @Secured(['permitAll'])
    @Transactional
    def appQueryUser()
    {
        //助手端我的
        def json = request.JSON
        println "####################助手端我的############################"
        println json
        def sessionId = json['sessionId']
        if (sessionId == null || sessionId == '')
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
        }
        else
        {

            render user as JSON

            /*def result=[]
            result.add(user)
            if(user.department.name=="销售组")
            {
                //销售组统计本月评房，本月报单，已评房，已报单
                def info=[:]
                String sql="from Opportunity as o where "
                def opportunityList=Opportunity.findAll(sql)
                info["本月评房"]=opportunityList.size()
                info[""]=
                info[""]=
                info[""]=
                result.add(info)
            }
            render result as JSON*/
        }

    }

    @Secured(['permitAll'])
    @Transactional
    def appCreateContactTest()
    {
        //助手端,创建测试用经纪人和订单关联人，测试使用。
        def contact = new Contact()
        if (contact.validate())
        {
            contact.type = "Agent"
            contact.fullName = "王毅"
            contact.idNumber = "110001199902020901"
            contact.cellphone = "13113113100"
            contact.maritalStatus = "已婚"
            contact.account = Account.findById(3)
            contact.userCode = "18811414141"
            contact.city = City.findByName("北京")
            contact.save flush: true
        }
        else
        {
            println contact.errors
        }

        contact = new Contact()
        if (contact.validate())
        {
            contact.type = "Client"
            contact.fullName = "王二"
            contact.idNumber = "110001199901010901"
            contact.cellphone = "13113113111"
            contact.maritalStatus = "已婚"
            contact.account = Account.findById(3)
            contact.save flush: true
        }
        else
        {
            println contact.errors
        }

        contact = new Contact()
        if (contact.validate())
        {
            contact.type = "Client"
            contact.fullName = "王三"
            contact.idNumber = "110001199903030901"
            contact.cellphone = "13113113122"
            contact.maritalStatus = "已婚"
            contact.account = Account.findById(3)
            contact.save flush: true
        }
        else
        {
            println contact.errors
        }

        contact = new Contact()
        if (contact.validate())
        {
            contact.type = "Client"
            contact.fullName = "王四"
            contact.idNumber = "110001199904040901"
            contact.cellphone = "13113113133"
            contact.maritalStatus = "已婚"
            contact.account = Account.findById(3)
            contact.save flush: true
        }
        else
        {
            println contact.errors
        }

        contact = new Contact()
        if (contact.validate())
        {
            contact.type = "Client"
            contact.fullName = "王五"
            contact.idNumber = "110001199905050901"
            contact.cellphone = "13113113144"
            contact.maritalStatus = "已婚"
            contact.account = Account.findById(3)
            contact.save flush: true
        }
        else
        {
            println contact.errors
        }

        render 1
    }

    @Secured(['permitAll'])
    @Transactional
    def appCreateOpportunityTest()
    {
        //助手端,创建未经过中佳信端评房和报单的订单，为测试时期制造可预约的订单
        def opportunity = new Opportunity()
        //以下为不可空字段
        opportunity.user = User.findByUsername("lyky")
        opportunity.account = Account.findById(3)
        opportunity.requestedAmount = 200
        //工作流中"房产初审已完成"(07)的表单可以预约
        opportunity.stage = OpportunityStage.findByCode("21")
        //下户需要显示字段
        //评估单价
        opportunity.unitPrice = 4
        //评估总价
        opportunity.loanAmount = 320
        //授信方案
        opportunity.actualAmountOfCredit = 300
        //抵押类型(二抵)
        opportunity.mortgageType = MortgageType.findById(2)
        //抵押金额
        opportunity.firstMortgageAmount = 250
        opportunity.secondMortgageAmount = 150
        //关联人
        opportunity.contact = Contact.findByFullName("王毅")
        //借款人
        opportunity.lender = Contact.findByFullName("王二")
        opportunity.interestPaymentMethod = InterestPaymentMethod.findById(2)

        if (opportunity.validate())
        {
            opportunity.save flush: true
        }
        else
        {
            println opportunity.errors
        }
        println opportunity.id
        //订单初始化
        opportunityService.initOpportunity(opportunity)
        // 流通性评估
        liquidityRiskReportService.initReport(opportunity)
        def opportunityContact = new OpportunityContact()
        opportunityContact.contact = Contact.findByFullName("王二")
        opportunityContact.opportunity = opportunity
        opportunityContact.type = OpportunityContactType.findById(1)
        if (opportunityContact.validate())
        {
            opportunityContact.save flush: true
        }
        else
        {
            println opportunityContact.errors
        }
        //借款人配偶
        opportunityContact = new OpportunityContact()
        opportunityContact.contact = Contact.findByFullName("王三")
        opportunityContact.opportunity = opportunity
        opportunityContact.type = OpportunityContactType.findById(2)
        if (opportunityContact.validate())
        {
            opportunityContact.save flush: true
        }
        else
        {
            println opportunityContact.errors
        }
        //抵押人
        opportunityContact = new OpportunityContact()
        opportunityContact.contact = Contact.findByFullName("王四")
        opportunityContact.opportunity = opportunity
        opportunityContact.type = OpportunityContactType.findById(3)
        if (opportunityContact.validate())
        {
            opportunityContact.save flush: true
        }
        else
        {
            println opportunityContact.errors
        }
        //抵押人配偶
        opportunityContact = new OpportunityContact()
        opportunityContact.contact = Contact.findByFullName("王五")
        opportunityContact.opportunity = opportunity
        opportunityContact.type = OpportunityContactType.findById(4)
        if (opportunityContact.validate())
        {
            opportunityContact.save flush: true
        }
        else
        {
            println opportunityContact.errors
        }

        def collateral = new Collateral()
        collateral.opportunity = opportunity
        collateral.city = City.findById(1)
        collateral.address = "朝阳区xx路"
        collateral.totalFloor = "10"
        collateral.unit = 10
        collateral.building = 3
        collateral.area = Double.parseDouble("10")
        collateral.projectName = "龙岩小区"
        collateral.orientation = "南"
        collateral.houseType = "普通住宅"
        collateral.specialFactors = "一层赠送"
        collateral.assetType = "住宅"
        collateral.externalId = "111"
        collateral.status = "Completed"
        collateral.roomNumber = "12"
        collateral.district = "朝阳区"
        collateral.floor = "4"
        if (collateral.validate())
        {
            collateral.save()
        }
        else
        {
        }
        def atta = Attachments.findById(3)
        atta.opportunity = opportunity
        def atta1 = Attachments.findById(2)
        atta1.opportunity = opportunity

        println opportunity.serialNumber
        render opportunity.serialNumber

    }

    @Secured(['permitAll'])
    @Transactional
    def appCreateActivityTest()
    {
        //助手端,创建未经过中佳信端评房和报单的订单，为测试时期制造可预约的订单
        def opportunity = Opportunity.findBySerialNumber("30I-8F3-BC2")
        def contact = Contact.findByFullName("下户测试经纪人")
        def cityName = contact.city.name
        def activity = new Activity()
        activity.startTime = new Date().parse("yyyy-MM-dd HH:mm:ss", "2016-04-04 14:33:22")
        activity.endTime = new Date().parse("yyyy-MM-dd HH:mm:ss", "2016-04-04 14:33:22")
        activity.type = ActivityType.findByName("Appointment")
        activity.subtype = ActivitySubtype.findByName("下户")
        activity.description = ""
        activity.opportunity = opportunity
        activity.contact = contact
        activity.city = cityName

        if (activity.validate())
        {
            activity.save flush: true
            //更新工作流
            opportunityService.submit(opportunity)

        }
        else
        {
            println activity.errors
        }
        render activity.id
    }

    @Secured(['permitAll'])
    @Transactional
    def appTest()
    {
        println "********** 极速询值 *************"
        Contact contact = Contact.findById(1)


        String city = "北京"
        //城市
        String district = "朝阳"
        //所属区县
        String projectName = "岩石小区"
        //小区名称
        String building = "3"
        //楼栋信息
        String unit = "4"
        //单元信息
        String floor = "5"
        //楼层
        String roomNumber = "6"
        //户号
        String totalFloor = "7"
        //总楼层
        String area = "55"
        //住宅面积
        String address = "酒仙路xxx号"
        //住房地址
        String orientation = "南"
        //朝向
        String houseType = "独栋"
        //物业类型
        String specialFactors = "临湖"
        //特殊因素
        String assetType = "住宅"
        //

        //新建collateral
        Collateral collateral = new Collateral()
        collateral.city = City.findByName(city)
        collateral.district = district
        collateral.projectName = projectName
        collateral.building = Integer.parseInt(building)
        collateral.unit = Integer.parseInt(unit)
        collateral.floor = floor
        collateral.roomNumber = roomNumber
        collateral.totalFloor = totalFloor
        collateral.area = Double.parseDouble(area)
        collateral.address = address
        collateral.orientation = orientation
        collateral.houseType = houseType
        collateral.specialFactors = specialFactors
        collateral.assetType = assetType


        def result = 1
        if (result != 0)
        {
            println "###########返回成功###############"
            Double unitPrice = 2000
            def externalId = 12343
            def status = "Completed"
            //是否为工作日还无法判断
            //新建opportunity
            def opportunity = new Opportunity()

            def loanAmount = unitPrice * Double.parseDouble(area)
            if (loanAmount < 0)
            {
                opportunity.loanAmount = loanAmount * (-1)
            }
            else
            {
                opportunity.loanAmount = loanAmount
            }
            def user = User.findByCellphone(contact?.userCode)
            if (!contact?.userCode || !user)
            {
                def errors = [errorCode: 4500, errorMessage: "邀请码异常，请联系管理员"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            opportunity.contact = contact
            if (city in ['南京', '苏州', '武汉', '上海'])
            {
                if (city == "南京")
                {
                    if (Double.parseDouble(area) >= 200 || unitPrice >= 35000 || loanAmount >= 500)
                    {
                        opportunity.stage = OpportunityStage.findByCode("15")
                    }
                    else
                    {
                        opportunity.stage = OpportunityStage.findByCode("02")
                    }
                }
                if (city == "苏州")
                {
                    if (Double.parseDouble(area) >= 200 || unitPrice >= 25000 || loanAmount >= 400)
                    {
                        opportunity.stage = OpportunityStage.findByCode("15")
                    }
                    else
                    {
                        opportunity.stage = OpportunityStage.findByCode("02")
                    }
                }
                if (city == "武汉")
                {
                    if (Double.parseDouble(area) >= 200 || unitPrice >= 20000 || loanAmount >= 300)
                    {
                        opportunity.stage = OpportunityStage.findByCode("15")
                    }
                    else
                    {
                        opportunity.stage = OpportunityStage.findByCode("02")
                    }
                }
                if (city == "上海")
                {
                    if (Double.parseDouble(area) >= 200 || unitPrice >= 72000 || loanAmount >= 1000)
                    {
                        opportunity.stage = OpportunityStage.findByCode("15")
                    }
                    else
                    {
                        opportunity.stage = OpportunityStage.findByCode("02")
                    }
                }
            }
            else
            {
                opportunity.stage = OpportunityStage.findByCode("02")
            }
            opportunity.user = user
            opportunity.account = user?.account
            opportunity.unitPrice = unitPrice
            if (opportunity.validate())
            {
                opportunity.save flush: true
            }
            else
            {
                println opportunity.errors
            }
            //订单初始化
            opportunityService.initOpportunity(opportunity)
            println "######新建的opportunity.id###########"
            println opportunity.id

            collateral.opportunity = opportunity
            collateral.unitPrice = unitPrice
            collateral.externalId = externalId.toString()
            collateral.status = status

            if (collateral.validate())
            {
                collateral.save flush: true
            }
            else
            {
                println collateral.errors
            }
        }
        render collateral as JSON
    }

    @Secured(['permitAll'])
    @Transactional
    def atest()
    {
        def collateral = Collateral.findById(8)
        render collateral as JSON
    }

    @Secured('permitAll')
    @Transactional
    def sendVerifiedCode()
    {
        String username = params.username
        def user = User.findByUsername(username)
        if (user)
        {
            if (user?.loginBySms)
            {
                userService.sendVerificationCode(user)
                render([status: "success"] as JSON)
            }
            else
            {
                render([status: "error", errorMessage: message(code: '此账户不支持短信验证码，请使用口令登录!')] as JSON)
                return
            }
        }
        else
        {
            render([status: "error", errorMessage: message(code: '用户名不存在，请核对用户名!')] as JSON)
            return
        }
    }
}

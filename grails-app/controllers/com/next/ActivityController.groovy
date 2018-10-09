package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class ActivityController
{
    def springSecurityService
    def opportunityNotificationService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",
        appObtainAppointment: "POST",
        appQueryAppointmentByAssignedTo: "POST", appFinishAppointment: "POST",
        appMakeAppointmentByContact: "POST", appQueryAppointmentByUser: "POST"]

    def contactService
    def opportunityService

    def index(Integer max)
    {
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        String sql = "from Activity as a where a.user.id=${user.id} or a.assignedTo.id=${user.id} and a.status='Pending' order by startTime desc"
        def activityList = Activity.findAll(sql, [max: max, offset: offset])
        def count = Activity.findAll(sql)
        respond activityList, model: [count: count]

    }

    def show(Activity activity)
    {
        respond activity
    }

    def create()
    {
        String opportunityId = params['opportunityId']
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityList = []
        if (opportunityId && opportunityId.length() > 0)
        {
            Opportunity opportunity = Opportunity.findById(params['opportunityId'])
            if (opportunity?.fullName && (opportunity?.collaterals?.size() > 0))
            {
                opportunityList.add(opportunity)
            }
            else
            {
                flash.message = "请完善借款人信息或房产信息"
                redirect(controller: "opportunity", action: "show", params: [id: opportunity?.id])
                return
            }
        }
        else
        {
            String sql = "from OpportunityTeam as o where o.user.id = ${user.id} order by modifiedDate desc"
            def opportunityTeam = OpportunityTeam.findAll(sql)

            opportunityTeam.each {
                if (it.opportunity.contacts.size() > 0 && it.opportunity.status == "Pending" && !opportunityList.contains(it.opportunity))
                {
                    if (it?.opportunity?.fullName && (it?.opportunity?.collaterals?.size() > 0))
                    {
                        opportunityList.add(it.opportunity)
                    }
                }
            }
            println opportunityList.size()
        }
        def activity = new Activity(params)
        activity.opportunity = Opportunity.findById(opportunityId)

        respond activity, model: [opportunityList: opportunityList, user: user]
    }

    def create2()
    {
        String opportunityId = params['opportunityId']
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityList = []
        if (opportunityId && opportunityId.length() > 0)
        {
            Opportunity opportunity = Opportunity.findById(params['opportunityId'])
            if (opportunity?.fullName && (opportunity?.collaterals?.size() > 0))
            {
                opportunityList.add(opportunity)
            }
            else
            {
                flash.message = "请完善借款人信息或房产信息"
                redirect(controller: "opportunity", action: "show", params: [id: opportunity?.id])
                return
            }
        }
        else
        {
            String sql = "from OpportunityTeam as o where o.user.id = ${user.id} order by modifiedDate desc"
            def opportunityTeam = OpportunityTeam.findAll(sql)

            opportunityTeam.each {
                if (it.opportunity.contacts.size() > 0 && it.opportunity.status == "Pending" && !opportunityList.contains(it.opportunity))
                {
                    if (it?.opportunity?.fullName && (it?.opportunity?.collaterals?.size() > 0))
                    {
                        opportunityList.add(it.opportunity)
                    }
                }
            }
            println opportunityList.size()
        }
        def activity = new Activity(params)
        activity.opportunity = Opportunity.findById(opportunityId)

        respond activity, model: [opportunityList: opportunityList, user: user]
    }

    def getContacts()
    {
        def opportunityId = params['opportunityId']
        def opportunity = Opportunity.findById(opportunityId)
        def contactList = []
        opportunity?.contacts.each {
            def contact = [:]
            contact['type'] = it.type.name
            contact['id'] = it.contact.id
            contact['name'] = it.contact.fullName
            contactList.add(contact)
        }
        render([status: "success", contactList: contactList] as JSON)

    }

    @Transactional
    def save(Activity activity)
    {
        if (activity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (activity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond activity.errors, view: 'create'
            return
        }

        activity.save flush: true

        if (activity?.status == "Pending" && activity?.subtype?.name == "下户")
        {
            opportunityNotificationService.sendAssignNotification(activity)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'activity.label',
                                                                                        default: 'Activity'),
                    activity.id])
                // redirect activity
                if (activity?.opportunity)
                {
                    redirect(controller: "opportunity", action: "show", params: [id: activity.opportunity.id])
                }
                else
                {
                    redirect(controller: "activity", action: "index")
                }

            }
            '*' { respond activity, [status: CREATED] }
        }
    }

    @Transactional
    def ajaxSave(Activity activity)
    {
        if (activity.validate())
        {
            activity.save flush: true
            if (activity?.status == "Pending" && activity?.subtype?.name == "下户")
            {
                opportunityNotificationService.sendAssignNotification(activity)
            }
            render([status: "success"] as JSON)
            return
        }
        else
        {
            render([status: "fail", errMsg: activity.errors] as JSON)
            return
        }
    }

    def edit(Activity activity)
    {
        respond activity
    }

    @Transactional
    def update(Activity activity)
    {
        if (activity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (activity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond activity.errors, view: 'edit'
            return
        }

        activity.save flush: true

        if (activity?.status == "Pending" && activity?.subtype?.name == "下户")
        {
            opportunityNotificationService.sendAssignNotification(activity)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'activity.label',
                                                                                        default: 'Activity'),
                    activity.id])
                // redirect activity
                redirect(controller: "opportunity", action: "show", params: [id: activity.opportunity.id])
            }
            '*' { respond activity, [status: OK] }
        }
    }

    @Transactional
    def delete(Activity activity)
    {

        if (activity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        activity.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'activity.label',
                                                                                        default: 'Activity'),
                    activity.id])
                // redirect action: "index", method: "GET"
                redirect(controller: "opportunity", action: "show", params: [id: activity?.opportunity?.id])

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'activity.label',
                                                                                          default: 'Activity'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    /*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/

    @Secured(['permitAll'])
    @Transactional
    def wxCreate()
    {
        def openId = params["openId"]
        def opportunityId = params["id"]?.toInteger()
        def activityDay = params["activityDay"]
        def activityPeriod = params["activityPeriod"]

        def opportunity = Opportunity.findById(opportunityId)
        def activity = new Activity(params)
        activity.opportunity = opportunity
        respond activity, model: [contact: opportunity?.contact, activityDay: activityDay, activityPeriod: activityPeriod, openId: openId]
    }

    @Secured(['permitAll'])
    @Transactional
    def wxCreate2()
    {
        def opportunity = Opportunity.findById(params["id"]?.toInteger())
        def openId = params["openId"]
        def nowDate = new Date()
        respond new Activity(params), model: [opportunity: opportunity, nowDate: nowDate, openId: openId]
    }

    @Secured(['permitAll'])
    @Transactional
    def wxBringData()
    {
        def opportunity = Opportunity.findById(params["id"]?.toInteger())
        def openId = params["openId"]
        def activityDay = params["activityDay"]
        def activityPeriod = params["activityPeriod"]
        respond new Activity(params), model: [opportunity: opportunity, activityDay: activityDay, activityPeriod: activityPeriod, openId: openId], view: "wxBringData"
    }

    @Secured(['permitAll'])
    @Transactional
    def wxMakeAppointmentByContact()
    {
        String startTime = params["startTime"]
        String endTime = params["endTime"]
        String openId = params["openId"]
        def opportunityId = params["opportunityId"]?.toInteger()
        def opportunity = Opportunity.findById(opportunityId)

        def contact = Contact.findByOpenId(openId)
        if (!contact)
        {
            contact = new Contact(params)
            contact.openId = openId
            flash.message = message(code: 'contact.cellphone.register.invalid')
            respond contact, view: 'wxRegister'
        }

        def activity = new Activity()
        activity.contact = contact
        activity.opportunity = opportunity
        activity.startTime = new Date().parse("yyyy-MM-dd HH:mm:ss", startTime)
        activity.endTime = new Date().parse("yyyy-MM-dd HH:mm:ss", endTime)
        activity.type = ActivityType.findByName("Appointment")
        activity.subtype = ActivitySubtype.findByName("下户")

        if (activity.validate())
        {
            opportunity.stage = OpportunityStage.findByCode("12")
            if (opportunity.validate())
            {
                activity.save flush: true
                opportunity.save flush: true

                redirect(action: "wxMakeAppointmentSuccessful", id: activity.id)
                return
            }
        }

        flash.message = message(code: '对不起，预约失败，请稍后重试')
        respond activity, model: [opportunity: opportunity, startTime: startTime, endTime: endTime, openId: openId],
                view: "wxCreate"
    }

    @Secured(['permitAll'])
    @Transactional
    def wxIndex(String code, String state)
    {
        String openId
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
            def opportunityList = Opportunity.findAll("from Opportunity as c where c.contact = ${certificate?.contact?.id} and c.stage = 7 and c.status <> 'Failed' order by createdDate desc")
            respond new Activity(params), model: [opportunityList: opportunityList, opportunityInstanceCount: opportunityList.size(), openId: openId, contact: certificate?.contact], view: "wxIndex"
        }
        else
        {
            redirect(controller: "contact", action: "wxRegister", params: [code: code, state: state])
        }
    }

    // @Secured(['permitAll'])
    // @Transactional
    // def wxIndex()
    // {
    //     def contact = Contact.findById(4)
    //     def opportunityList = Opportunity.findAll("from Opportunity as c where c.contact = ${contact?.id} and c
    // .stage = 7 and c.status <> 'Failed' order by createdDate desc")
    //     respond new Activity(params), model: [opportunityList: opportunityList, opportunityInstanceCount:
    // opportunityList.size(), openId: "123456", contact: contact], view: "wxIndex"
    // }

    @Secured(['permitAll'])
    @Transactional
    def wxMakeAppointmentSuccessful(Activity activity)
    {
        respond activity
    }

    /*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/

    @Secured(['permitAll'])
    @Transactional
    def appMakeAppointmentByContact()
    {
        def json = request.JSON
        println "******************* appMakeAppointmentByContact ********************"
        println json

        String sessionId = json["sessionId"]
        if (!sessionId)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        Contact contact = Contact.findByAppSessionId(sessionId)
        if (!contact)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def opportunitySerialNumber = json["opportunitySerialNumber"]
        if (!opportunitySerialNumber)
        {
            def errors = [errorCode: 5250, errorMessage: "订单编号不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        Opportunity opportunity = Opportunity.findBySerialNumber(opportunitySerialNumber)
        if (!opportunity)
        {
            def errors = [errorCode: 5251, errorMessage: "订单编号不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def subtypeName = json["subtype"]
        def subtype
        if (subtypeName)
        {
            subtype = ActivitySubtype.findByName(subtypeName)
            if (!subtype)
            {
                def errors = [errorCode: 5252, errorMessage: "请输入正确的预约类型"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        else
        {
            def errors = [errorCode: 5253, errorMessage: "预约类型不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def startTime = json["startTime"]

        if (startTime == null)
        {
            def errors = [errorCode: 5254, errorMessage: "请输入预约开始时间"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def endTime = json["endTime"]
        if (endTime == null)
        {
            def errors = [errorCode: 5255, errorMessage: "请输入预约截至时间"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def cityName = contact?.city?.name
        if (!cityName)
        {
            def errors = [errorCode: 5257, errorMessage: "关联城市名称缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def activity = new Activity()
        activity.startTime = new Date().parse("yyyy-MM-dd HH:mm:ss", startTime)
        activity.endTime = new Date().parse("yyyy-MM-dd HH:mm:ss", endTime)
        activity.type = ActivityType.findByName("Appointment")
        activity.subtype = subtype
        activity.description = ""
        activity.opportunity = opportunity
        activity.contact = contact
        activity.city = cityName

        if (activity.validate())
        {
            activity.save flush: true
            //更新工作流
            opportunityService.submit(opportunity)

            render activity as JSON
        }
        else
        {
            println activity.errors

            def errors = [errorCode: 5256, errorMessage: "对不起，预约失败，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
        }
    }

    /*@Secured(['permitAll'])
    def appQueryAppointmentByUser()
    {
        def json = request.JSON
        println "******************* appQueryAppointmentByUser ********************"
        println json

        def status = json['status']
        def sessionId = json['sessionId']
        def offset = json['offset']?.toInteger()

        def assigned = json['assigned']?.toBoolean()
        def max = 10

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


        def typeName = json["type"]
        def type
        if (typeName)
        {
            type = ActivityType.findByName(typeName)
            if (!type)
            {
                def errors = [errorCode: 5258, errorMessage: "请输入正确的活动类型"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        else
        {
            def errors = [errorCode: 5259, errorMessage: "活动类型不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def subtypeName = json["subtype"]
        def subtype
        if (subtypeName)
        {
            subtype = ActivitySubtype.findByName(subtypeName)
            if (!subtype)
            {
                def errors = [errorCode: 5252, errorMessage: "请输入正确的预约类型"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        else
        {
            def errors = [errorCode: 5253, errorMessage: "预约类型不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!status)
        {
            def errors = [errorCode: 5260, errorMessage: "预约状态缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (offset == null)
        {
            def errors = [errorCode: 5261, errorMessage: "分页数缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def list = []
        if (assigned)
        {
            list = Activity.findAll("from Activity as a where a.type.id = ${type.id} and a.subtype.id = ${subtype.id}
             and a.user.id = ${user.id} and status = '${status}' order by startTime desc", [max: max, offset: offset])
        }
        else
        {
            list = Activity.findAll("from Activity as a where a.type.id = ${type.id} and a.subtype.id = ${subtype.id}
             and status = '${status}' and a.user is null order by startTime desc", [max: max, offset: offset])
        }

        // def buffer = []
        // list.each
        // {
        //     def appotinment = [:]
        //     appotinment['startTime'] = it.startTime
        //     appotinment['contact'] = it.contact
        //     appotinment['contacts'] = it.contacts
        //     appotinment['type'] = it.type
        //     appotinment['subtype'] it.subtype

        //     buffer.add(appotinment)
        // }
        render list as JSON
    }*/

    @Secured(['permitAll'])
    def appQueryAppointmentByAssignedTo()
    {
        //助手端查询下户与查询待办列表
        def json = request.JSON
        println "******************* appQueryAppointmentByAssignedTo ********************"
        println json

        def status = json['status']
        def sessionId = json['sessionId']
        def assigned = json['assigned']?.toBoolean()
        def year = json['year'].toString()
        def month = json['month'].toString()

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
        def cityName = user?.city?.name
        if (!cityName)
        {
            def errors = [errorCode: 5257, errorMessage: "关联城市名称缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def typeName = json["type"]
        def type
        if (typeName)
        {
            type = ActivityType.findByName(typeName)
            if (!type)
            {
                def errors = [errorCode: 5258, errorMessage: "请输入正确的活动类型"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        else
        {
            def errors = [errorCode: 5259, errorMessage: "活动类型不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def subtypeName = json["subtype"]
        def subtype
        if (subtypeName)
        {
            subtype = ActivitySubtype.findByName(subtypeName)
            if (!subtype)
            {
                def errors = [errorCode: 5252, errorMessage: "请输入正确的预约类型"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        else
        {
            def errors = [errorCode: 5253, errorMessage: "预约类型不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!status)
        {
            def errors = [errorCode: 5260, errorMessage: "预约状态缺失"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!year)
        {
            def errors = [errorCode: 5262, errorMessage: "年份不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!month || month.length() < 2)
        {
            def errors = [errorCode: 5263, errorMessage: "月份数据有误"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def list = []
        if (!assigned)
        {
            list = Activity.findAll("from Activity as a where Convert(varchar,a.startTime,120) like '%${year}-${month}%' and a.type.id = ${type.id} and a.subtype.id = ${subtype.id}" + " and a.assignedTo.id = ${user.id} and status = '${status}' order by startTime desc")
            // list = Activity.findAll("from Activity as a where date_format(a.startTime,'%Y-%m')='${year}-${month}' and a.type.id = ${type.id} and a.subtype.id = ${subtype.id}" + " and a.assignedTo.id = ${user.id} and status = '${status}' order by startTime desc")
        }
        else
        {
            list = Activity.findAll("from Activity as a where Convert(varchar,a.startTime,120) like '%${year}-${month}%' and a.type.id = ${type.id} and a.subtype.id = ${subtype.id}" + " and status = '${status}' and a.assignedTo is null order by startTime desc")
            // list = Activity.findAll("from Activity as a where date_format(a.startTime,'%Y-%m')='${year}-${month}' and a.type.id = ${type.id} and a.subtype.id = ${subtype.id}" + " and status = '${status}' and a.assignedTo is null order by startTime desc")
        }
        println list.size()
        render list as JSON
    }

    @Secured(['permitAll'])
    @Transactional
    def appObtainAppointment()//接单
    {
        //助手端接单与修改预约时间
        def json = request.JSON
        println "******************* appObtainAppointment ********************"
        println json

        def sessionId = json['sessionId']
        def appointmentId = json['appointmentId']

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

        def startTime = json["startTime"]
        if (startTime == null)
        {
            def errors = [errorCode: 5254, errorMessage: "请输入预约开始时间"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def endTime = json["endTime"]
        if (endTime == null)
        {
            def errors = [errorCode: 5255, errorMessage: "请输入预约截至时间"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!appointmentId)
        {
            def errors = [errorCode: 5250, errorMessage: "订单编号不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        Activity activity = Activity.findById(appointmentId)

        if (!activity)
        {
            def errors = [errorCode: 4400, errorMessage: "appointment not found."]
            render JsonOutput.toJson(errors), status: 400
        }
        else
        {
            //预约已指派
            Opportunity opportunity = Opportunity.findById(activity.opportunity.id)
            if (!opportunity)
            {
                def errors = [errorCode: 5251, errorMessage: "订单编号不存在"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (activity.validate())
            {

                activity.startTime = new Date().parse("yyyy-MM-dd HH:mm:ss", startTime)
                activity.endTime = new Date().parse("yyyy-MM-dd HH:mm:ss", endTime)
                activity.assignedTo = user

                activity.save flush: true

                def message = [successCode: 6001, successMessage: "接单完成"]
                render JsonOutput.toJson(message), status: 200
            }
            else
            {
                println opportunity.errors

                def errors = [errorCode: 5264, errorMessage: "对不起，预约下户失败，请稍后重试"]
                render JsonOutput.toJson(errors), status: 400
            }

        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appFinishAppointment()//完成
    {
        //助手端完成下户
        def json = request.JSON
        println "******************* appFinishAppointment ********************"
        println json

        def sessionId = json['sessionId']
        def appointmentId = json['appointmentId']

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
        if (!appointmentId)
        {
            def errors = [errorCode: 5250, errorMessage: "订单编号不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        Activity activity = Activity.findById(appointmentId)

        if (!activity)
        {
            def errors = [errorCode: 4400, errorMessage: "appointment not found."]
            render JsonOutput.toJson(errors), status: 400
        }
        else
        {
            //预约下户已经完成
            Opportunity opportunity = Opportunity.findById(activity.opportunity.id)
            if (!opportunity)
            {
                def errors = [errorCode: 5251, errorMessage: "订单编号不存在"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            /*def stageCode = opportunity.stage.code

            if (stageCode != "06")
            {
                def errors = [errorCode: 5266, errorMessage: "预约没有完成征信查询"]
                render JsonOutput.toJson(errors), status: 400
                return
            }*/
            if (activity.validate())
            {
                activity.status = 'Completed'
                activity.save flush: true

                def message = [successCode: 6000, successMessage: "预约完成"]
                render JsonOutput.toJson(message), status: 200
            }
            else
            {
                println opportunity.errors

                def errors = [errorCode: 5265, errorMessage: "对不起，下户完成失败，请稍后重试"]
                render JsonOutput.toJson(errors), status: 400
            }

        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appCancelAppointment()
    {
        def sessionId = params['sessionId']
        def appointmentId = params['appointmentId']?.toInteger()

        if (!sessionId)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        User user = User.findByAppSessionId(sessionId)
        if (!contact)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def activity = Activity.get(appointmentId)
        if (!activity)
        {
            def errors = [errorCode: 4400, errorMessage: "appointment not found."]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            activity.status = 'Canceled'
            activity.save()

            render activity as JSON
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appUpdateAppointment()
    {
        def sessionId = params['sessionId']
        def appointmentId = params['appointmentId']?.toInteger()

        if (!sessionId)
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        User user = User.findByAppSessionId(sessionId)
        if (!contact)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def activity = Activity.get(appointmentId)
        if (!activity)
        {
            def errors = [errorCode: 4400, errorMessage: "appointment not found."]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            if (params['description'])
            {
                activity.description = params['description']?.trim()
            }

            activity.save()

            render activity as JSON
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appCreateCall()
    {
        //助手端打卡
        def json = request.JSON
        println "******************* appCreateCall ********************"
        println json

        def sessionId = json['sessionId']
        def opportunityId = json['opportunityId']
        def activityId = json['activityId']
        def subtypeName = json['subtype']
        //Sign In
        def longitude = json['longitude']?.toDouble()
        def latitude = json['latitude']?.toDouble()
        def city = json['city']
        def address = json['address']
        def startTime = json['startTime']
        def endTime = json['endTime']

        if (sessionId == null || sessionId == '')
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (longitude == null || longitude == '')
        {
            def errors = [errorCode: 5289, errorMessage: "经度不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (city == null || city == '')
        {
            def errors = [errorCode: 5288, errorMessage: "城市定位不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (latitude == null || latitude == '')
        {
            def errors = [errorCode: 5287, errorMessage: "纬度不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (address == null || address == '')
        {
            def errors = [errorCode: 5286, errorMessage: "定位地址不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (startTime == null || startTime == '')
        {
            def errors = [errorCode: 5285, errorMessage: "开始时间不能空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        startTime = new Date().parse("yyyy-MM-dd HH:mm:ss", startTime)
        if (endTime == null || endTime == '')
        {
            def errors = [errorCode: 5284, errorMessage: "结束时间不能空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        endTime = new Date().parse("yyyy-MM-dd HH:mm:ss", endTime)
        User user = User.findByAppSessionId(sessionId)
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (opportunityId == null || opportunityId == '')
        {
            def errors = [errorCode: 6098, errorMessage: "订单id不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (activityId == null || activityId == '')
        {
            def errors = [errorCode: 6088, errorMessage: "活动id不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        opportunityId = opportunityId.toInteger()
        Opportunity opportunity = Opportunity.get(opportunityId)
        if (!opportunity)
        {
            def errors = [errorCode: 5298, errorMessage: "订单不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        Contact contact = opportunity.contact
        def type = ActivityType.findByName("Call")
        if (subtypeName == null || subtypeName == '')
        {
            def errors = [errorCode: 5297, errorMessage: "活动子类型不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def subtype = ActivitySubtype.findByName(subtypeName)
        def activity = Activity.findById(activityId)
        if (!activity)
        {
            def errors = [errorCode: 6087, errorMessage: "活动不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def call = new Activity(parent: activity, assignedTo: user, contact: contact, type: type, subtype: subtype,
                                status: "Completed", opportunity: opportunity, startTime: startTime, endTime: endTime, longitude: longitude, latitude: latitude, city: city, address: address)
        if (call.validate())
        {
            call.save flush: true

            def message = [successCode: 5290, successMessage: "打卡完成"]
            render JsonOutput.toJson(message), status: 200
        }
        else
        {
            println call.errors

            def errors = [errorCode: 5296, errorMessage: "对不起，打卡失败，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
        }

    }

    @Secured(['permitAll'])
    def appQueryCallByOpportunityId()
    {
        //助手端查看打卡记录
        def json = request.JSON
        println "#################appQueryCallByOpportunityId#############################"
        println json
        def sessionId = json['sessionId']
        def opportunityId = json['opportunityId']
        def activityId = json['activityId']
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
            return
        }
        if (opportunityId == null || opportunityId == '')
        {
            def errors = [errorCode: 5294, errorMessage: "订单不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (activityId == null || activityId == '')
        {
            def errors = [errorCode: 6088, errorMessage: "活动id不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def type = ActivityType.findByName("Call")
        def subType = ActivitySubtype.findByName("Sign In")
        def alist = []
        alist = Activity.findAll("from Activity as a where a.opportunity.id=${opportunityId} and a.type.id=${type.id} and a" + ".subtype.id=${subType.id} and status ='Completed' and a.parent.id=${activityId} order by " + "startTime asc")
        render alist as JSON

    }

}

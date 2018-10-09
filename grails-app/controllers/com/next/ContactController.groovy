package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ContactController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", appRegister: "POST", appLogin: "POST"]

    def messageService
    def contactService
    def springSecurityService
    def fileServerService

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE', 'ROLE_ADMINISTRATOR', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
    def indexByAgent(Integer max)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        Integer offset = 0
        if (params.offset)
        {
            offset = params.offset.toInteger()
        }
        def list = contactService.indexByAgent(user, offset)
        Integer count
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            count = Contact.countByTypeAndUser("Agent", user)
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            count = Contact.findAll("from Contact where type = 'Agent' and user.city.id = ${user?.city?.id}")?.size()
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            count = Contact.countByType("Agent")
        }

        respond list, model: [contactCount: count], view: 'index'
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_CONDITION_RULEENGINE', 'ROLE_ADMINISTRATOR', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
    def indexByClient(Integer max)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        Integer offset
        if (!(params.offset))
        {
            offset = 0
        }
        else
        {
            offset = params.offset.toInteger()
        }

        def list = contactService.indexByClient(user, offset)
        Integer count
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            count = Contact.findAll("from Contact as c where c.user.id=${user.id} and (c.type='Client' or c.type is null) order by " + "createdDate desc")?.size()
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            count = Contact.findAll("from Contact as c where (c.type = 'Client' or c.type is null) and c.user.city.id = ${user?.city?.id} order by createdDate desc")?.size()
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            count = Contact.findAll("from Contact as c where c.type = 'Client' or c.type is null order by createdDate desc")?.size()
        }

        respond list, model: [contactCount: count], view: 'indexByClient'
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
    def show(Contact contact)
    {
        String id = params["id"]
        contact = Contact.findById(id)
        def opportunityList = []
        def contactTeamList
        if ((!contact?.type) || contact?.type == 'Client')
        {
            // opportunityList = Opportunity.findAllByLender(contact)
            def opportunityContactList = OpportunityContact.findAllByContact(contact)
            opportunityContactList?.each {
                opportunityList += it?.opportunity
            }
            //            def creditReport = CreditReport.findAllByContact(contact)
            respond contact, model: [opportunityList: opportunityList], view: 'clientShow'
        }
        if (contact?.type == 'Agent')
        {
            opportunityList = Opportunity.findAllByContact(contact)
            contactTeamList = ContactTeam.findAllByContact(contact)
            respond contact, model: [opportunityList: opportunityList, contactTeamList: contactTeamList]
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def create()
    {
        respond new Contact(params)
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def save(Contact contact)
    {
        if (contact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contact.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contact.errors, view: 'create'
            return
        }

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        if (user)
        {
            contact.user = user
        }

        contact.save flush: true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contact.label',
                                                                                        default: 'Contact'), contact
                    .id])
                redirect contact
            }
            '*' { respond contact, [status: CREATED] }
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    def edit(Contact contact)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        if (contact?.user == user || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            String id = params["id"]
            contact = Contact.findById(id)
            if (!contact?.type || contact?.type == 'Client')
            {
                respond contact, view: 'clientEdit'
            }
            respond contact
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "contact", action: "show", params: [id: contact.id])
            return
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
    @Transactional
    def update(Contact contact)
    {
        if (contact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contact.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond contact.errors, view: 'edit'
            return
        }

        if (contact.user)
        {
            def list = Opportunity.findAll("from Opportunity as o where o.contact.id = ${contact.id} and o.user.id <>" + " ${contact.user.id}")
            list.each {
                it.user = contact.user
                it.save flush: true
            }
        }
        contact.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contact.label',
                                                                                        default: 'Contact'), contact
                    .id])
                if (contact.type == "Agent")
                {
                    respond contact, view: 'show'
                }
                else if (!contact?.type || contact?.type == "Client")
                {
                    respond contact, view: 'clientShow'
                }
                else
                {
                    redirect contact
                }
            }
            '*' { respond contact, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    @Transactional
    def delete(Contact contact)
    {
        if (contact == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        println("删除借款人："+contact?.fullName+"时间"+new Date().format("yyyy-MM-dd hh:mm:ss")+"借款人externalId为"+contact?.externalId)
        contact.delete flush: true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contact.label',
                                                                                        default: 'Contact'), contact
                    .id])
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
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label',
                                                                                          default: 'Contact'), params
                    .id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    /*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/

    @Secured('permitAll')
    @Transactional
    def wxRegister(String code, String state)
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

        def contact = new Contact(params)
        contact.openId = openId
        respond contact
    }

    @Secured('permitAll')
    @Transactional
    def wxRegisterVerifyCode()
    {
        // 通过手机号码获取用户信息
        Contact contact = Contact.findByCellphone(params["cellphone"])
        if (contact)
        {
            if (contact.appSessionId)
            {
                // 注册失败，请先获取验证码
                flash.message = message(code: '手机号已注册，请直接的登录！')
                respond new Contact(params), view: 'wxRegister'
                return
            }

            contact.fullName = params["fullName"]
            contact.cellphone = params["cellphone"]
            contact.city = City.findById(params["city"])
            contact.openId = params["openId"]
            contact.type = "Agent"

            if (contact.verifiedCode != params["verifiedCode"])
            {
                flash.message = message(code: 'contact.verifiedCode.invalid.message')
                respond contact, view: 'wxRegister'
                return
            }
            else
            {
                def userCode = params["userCode"]
                if (userCode)
                {
                    if (!User.findByCellphone(userCode))
                    {
                        flash.message = message(code: 'contact.userCode.nonExistence.message')
                        respond contact, view: 'wxRegister'
                        return
                    }

                    contact.userCode = userCode
                }
                else
                {
                    contact.userCode = City.findById(params["city"])?.defaultInvitationCode
                }

                contact.save flush: true

                ContactLoginCertificate contactLoginCertificate = new ContactLoginCertificate()
                contactLoginCertificate.contact = contact
                contactLoginCertificate.type = "wechat"
                contactLoginCertificate.externalId = params["openId"]
                if (contactLoginCertificate.validate())
                {
                    contactLoginCertificate.save flush: true
                }
                else
                {
                    println contactLoginCertificate.errors
                }

                session.openId = params["openId"]

                // 验证码验证成功
                redirect(action: "wxRegisterSuccesful", id: contact.id)
            }
        }
        else
        {
            // 注册失败，请先获取验证码
            flash.message = message(code: 'contact.verifiedCode.getFailed.message')
            respond new Contact(params), view: 'wxRegister'
        }
    }

    @Secured('permitAll')
    @Transactional
    def wxRegisterSuccesful(Contact contact)
    {
        respond contact
    }

    @Secured('permitAll')
    @Transactional
    def wxWelcome(String code, String state)
    {
        if (!code)
        {
            respond new Contact(params)
            return
        }

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
            redirect(action: "wxShow", params: [code: code, state: state])
            return
        }
        else
        {
            respond new Contact(params)
        }
    }

    @Secured('permitAll')
    @Transactional
    def sendVerifiedCode()
    {
        String cellphone = params.cellphone
        String operation = params.operation

        if (operation == "register")
        {
            def cellphoneForCentury21 = contactService.verifyCellphoneForCentury21(cellphone)
            if (!cellphoneForCentury21)
            {
                // 显示错误信息:请使用“酷客部落”报单，谢谢！
                render([status: "error", errorMessage: message(code: 'contact.cellphone.kuke.message')] as JSON)
            }
            else
            {
                def contact = Contact.findByCellphoneAndType(cellphone, "Agent")
                if (contact)
                {
                    def certificate = ContactLoginCertificate.findByContactAndType(contact, "wechat")
                    if (certificate)
                    {
                        if (session.openId)
                        {
                            if (certificate.externalId != session.openId)
                            {
                                certificate.externalId = session.openId
                                certificate.save flush: true
                            }
                        }
                        else
                        {
                            redirect(action: "wxWelcome", params: [code: null, state: null])
                            return
                        }
                        // 显示错误信息:此手机号已经使用过
                        render([status: "error", errorMessage: message(code: 'contact.cellphone.unique')] as JSON)
                    }
                    else
                    {
                        if (contact.appSessionId)
                        {
                            render([status: "error", errorMessage: message(code: '手机号已注册，请直接登录!')] as JSON)
                        }
                        else
                        {
                            // 注册失败，第二次发送验证码
                            contact.verifiedCode = contactService.generateVerifiedCode()
                            contact.save flush: true

                            // 发送短信
                            messageService.sendMessage2(cellphone, "【中佳信】您的“中佳信”验证码为:" + contact.verifiedCode + "，10分钟内有效")
                            render([status: "success"] as JSON)
                        }
                    }
                }
                else
                {
                    contact = new Contact()
                    contact.type = "Agent"
                    contact.account = Account.findByName("北京中佳信科技发展有限公司")
                    contact.cellphone = cellphone
                    contact.verifiedCode = contactService.generateVerifiedCode()
                    contact.save flush: true

                    // 发送短信
                    messageService.sendMessage2(cellphone, "【中佳信】您的“中佳信”验证码为:" + contact.verifiedCode + "，10分钟内有效")
                    render([status: "success"] as JSON)
                }
            }
        }
        else if (operation == "login")
        {
            def contact = Contact.findByCellphoneAndType(cellphone, "Agent")
            if (contact)
            {
                contact.verifiedCode = contactService.generateVerifiedCode()
                contact.save flush: true

                // 发送短信
                messageService.sendMessage2(cellphone, "【中佳信】您的“中佳信”验证码为:" + contact.verifiedCode + "，10分钟内有效")

                render([status: "success"] as JSON)
            }
            else
            {
                // 手机号未注册，请注册后登录
                render([status: "error", errorMessage: message(code: 'contact.cellphone.register.invalid')] as JSON)
            }
        }
    }

    @Secured('permitAll')
    @Transactional
    def wxLogin(String code, String state)
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

        def contact = new Contact(params)
        contact.openId = openId
        respond contact
    }

    @Secured('permitAll')
    @Transactional
    def wxLoginVerifyCode()
    {
        // 通过手机号码获取用户信息
        Contact contact = Contact.findByCellphone(params["cellphone"])
        if (contact)
        {
            if (contact.verifiedCode != params["verifiedCode"])
            {
                // 验证码验证失败
                contact.openId = params["openId"]
                flash.message = message(code: 'contact.verifiedCode.invalid.message')
                respond contact, view: 'wxLogin'
            }
            else
            {
                def certificate = ContactLoginCertificate.findByContactAndType(contact, "wechat")
                if (!certificate)
                {
                    contact.openId = params["openId"]
                    contact.save flush: true

                    certificate = new ContactLoginCertificate()
                    certificate.contact = contact
                    certificate.type = "wechat"
                    certificate.externalId = params["openId"]
                }
                else
                {
                    certificate.externalId = params["openId"]
                    certificate.lastLoginTime = new Date()
                }

                certificate.save flush: true
                session.openId = params["openId"]

                redirect(action: "wxShow", params: [code: null, state: null, id: contact.id])
                return
            }
        }
        else
        {
            // 手机号未注册，请注册后登录
            contact = new Contact(params)
            contact.openId = params["openId"]
            flash.message = message(code: 'contact.cellphone.register.invalid')
            respond contact, view: 'wxRegister'
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def wxShow(String code, String state)
    {
        def contact
        if (code)
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
                contact = certificate.contact
            }
            else
            {
                redirect(controller: "contact", action: "wxWelcome", params: [code: code, state: state])
                return
            }
        }
        else
        {
            contact = Contact.findById(params["id"])
        }

        def opportunityCounts = Opportunity.countByContactAndStageNotEqualAndStageNotEqual(contact, OpportunityStage.findByCode("02"), OpportunityStage.findByCode("15"))
        def waitSHCounts = Opportunity.countByContactAndStageInListAndStatusNotEqual(contact, OpportunityStage.findAllByCodeOrCodeOrCode("03", "04", "16"), "Failed")
        def alreadyCSCounts = Opportunity.countByContactAndStageInListAndStatusNotEqual(contact, OpportunityStage.findAllByCodeOrCodeOrCode("05", "06", "07"), "Failed")
        def alreadySPCounts = Opportunity.countByContactAndStageInListAndStatusNotEqual(contact, OpportunityStage.findAllByCodeOrCodeOrCode("08", "09", "10"), "Failed")
        def alreadyFDCounts = Opportunity.countByContactAndStageAndStatusNotEqual(contact, OpportunityStage.findByCode("11"), "Failed")
        def alreadySBCounts = Opportunity.countByContactAndStatus(contact, "Failed")

        respond contact, model: [opportunityCounts: opportunityCounts, waitSHCounts: waitSHCounts, alreadyCSCounts: alreadyCSCounts, alreadySPCounts: alreadySPCounts, alreadyFDCounts: alreadyFDCounts, alreadySBCounts: alreadySBCounts], view: 'wxShow'
    }

    @Secured('permitAll')
    @Transactional
    def wxEdit()
    {
        respond Contact.findByCellphone(params["cellphone"])
    }

    @Secured(['permitAll'])
    @Transactional
    def wxUpdate()
    {
        def contact = Contact.findByCellphone(params["cellphone"])
        contact.city = City.findById(params["city"])
        contact.idNumber = params["idNumber"]
        contact.bankName = params["bankName"]
        contact.bankAccount = params["bankAccount"]
        contact.save flush: true

        redirect(action: "wxShow", params: [code: null, state: null, id: contact.id])
    }

    @Secured(['permitAll'])
    @Transactional
    def search()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def max = params.max
        def offset = params.offset

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        def fullName = params["fullName"]
        def city = params["city"]
        def cellphone = params["cellphone"]
        def idNumber = params["idNumber"]
        def type = params["type"]

        String sql
        if (type == 'Agent')
        {
            sql = "from Contact as c where c.type = '${type}'"
        }
        else
        {
            sql = "from Contact as c where (c.type = 'Client' or c.type is null)"
        }
        if (fullName)
        {
            sql += " and c.fullName like '%${fullName}%'"
        }
        if (city)
        {
            sql += " and c.city.name = '${city}'"
        }
        if (cellphone)
        {
            sql += " and c.cellphone = '${cellphone}'"
        }
        if (idNumber)
        {
            sql += " and c.idNumber = '${idNumber}'"
        }

        def list = []
        def count
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            sql += " order by createdDate desc"
            list = Contact.findAll(sql, [max: max, offset: offset])
            def list1 = Contact.findAll(sql)
            count = list1.size()
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            sql += " and c.user.city.id = ${user?.city?.id} order by createdDate desc"
            list = Contact.findAll(sql, [max: max, offset: offset])
            def list1 = Contact.findAll(sql)
            count = list1.size()
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            sql += " and c.user.id = ${user.id} order by createdDate desc"
            list = Contact.findAll(sql, [max: max, offset: offset])
            def list1 = Contact.findAll(sql)
            count = list1.size()
        }

        def contact = new Contact(params)
        contact.city = City.findByName(city)

        if ("Agent" == type)
        {
            respond list, model: [contactCount: count, contact: contact, params: params], view: 'index'
        }
        if ("Client" == type)
        {
            respond list, model: [contactCount: count, contact: contact, params: params], view: 'indexByClient'
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def wxVerifiedUserCode()
    {
        def user = User.findByCellphone(params["userCode"])
        if (user)
        {
            render([status: "success", flag: true] as JSON)
        }
        else
        {
            render([status: "success", flag: false] as JSON)
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def wxGetInvitationCode()
    {
        def invitationCode = City.findById(params["city"])?.invitationCode
        render([status: "success", invitationCode: invitationCode] as JSON)
    }

    // // 测试 TODO
    // @Secured('permitAll')
    // @Transactional
    // def wxRegister()
    // {
    //     def contact = new Contact(params)
    //     respond contact
    // }

    /*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/

    @Secured(['permitAll'])
    @Transactional
    def appRegister()
    {
        def json = request.JSON
        println "******************* register ********************"
        println json

        def cellphone = json["cellphone"]
        def password = json["password"]
        def fullName = json["fullName"]
        def cityName = json["city"]
        def userCode = json["userCode"]
        def city = City.findByName(cityName)

        println userCode
        if (userCode && userCode.toString().length() > 0)
        {
            if (userCode.toString().length() != 11)
            {
                def errors = [errorCode: 2060, errorMessage: "请填写正确的邀请码"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            else if (!User.findByCellphone(userCode))
            {
                def errors = [errorCode: 1040, errorMessage: "对不起，您输入的邀请码不存在"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        if (!userCode)
        {
            if (city.invitationCode == true && userCode.toString().length() != 11)
            {
                def errors = [errorCode: 2060, errorMessage: "请填写正确的邀请码"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            else
            {
                userCode = city.defaultInvitationCode
            }
        }

        if (!cellphone || cellphone.length() != 11)
        {
            def errors = [errorCode: 2000, errorMessage: "请填写正确的手机号"]
            render JsonOutput.toJson(errors), status: 400
        }
        else if (password == null || password.length() < 6 || password.length() > 16)
        {
            def errors = [errorCode: 2010, errorMessage: "密码长度在6-16位之间，请重新输入"]
            render JsonOutput.toJson(errors), status: 400
        }
        else if (!fullName || !(fullName.matches(/^[\u2190-\u9fff]{1,10}$|^[\dA-Za-z]{1,20}$/)))
        {
            def errors = [errorCode: 2030, errorMessage: "请填写正确的姓名"]
            render JsonOutput.toJson(errors), status: 400
        }
        else if (!cityName || !city)
        {
            def errors = [errorCode: 2050, errorMessage: "请选择所在城市"]
            render JsonOutput.toJson(errors), status: 400
        }
        else
        {
            def contact = Contact.findByCellphoneAndType(cellphone, "Agent")

            if (contact)
            {
                if (contact.password)
                {
                    def errors = [errorCode: 1010, errorMessage: "该手机号已注册，请直接登录。"]
                    render JsonOutput.toJson(errors), status: 400
                }
                else
                {
                    contact.fullName = fullName
                    contact.city = city
                    contact.password = password.encodeAsMD5()
                    contact.appSessionId = UUID.randomUUID().toString()
                    contact.type = "Agent"
                    contact.userCode = userCode

                    if (contact.validate())
                    {
                        contact.save()
                        render contactService.toJson(contact), status: 200
                    }
                    else
                    {
                        def errors = [errorCode: 9000, errorMessage: contact.errors]
                        render JsonOutput.toJson(errors), status: 400
                    }
                }
            }
            else
            {
                def errors = [errorCode: 1020, errorMessage: "请获取验证码"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appCellphoneIsRegistered()
    {
        def json = request.JSON
        println "******************* appFindContactByCellphone ********************"
        println json

        def cellphone = json["cellphone"]?.trim()
        if (!cellphone || cellphone.length() != 11)
        {
            def errors = [errorCode: 2000, errorMessage: "请填写正确的手机号"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            /*def cellphoneForCentury21 = contactService.verifyCellphoneForCentury21(cellphone)
            if (!cellphoneForCentury21)
            {
                // 显示错误信息:请使用“酷客部落”报单，谢谢！
                def errors = [errorCode: 1060, errorMessage: "请使用“酷客部落”报单，谢谢！"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            else
            {*/
                def contact = Contact.findByCellphoneAndType(cellphone, "Agent")

                if (contact && contact.password)
                {
                    def errors = [errorCode: 1010, errorMessage: "该手机号已注册，请直接登录。"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                else
                {
                    render status: 200
                    return
                }
           /* }*/

        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appLogin()
    {
        def json = request.JSON
        println "******************* login ********************"
        println json

        def cellphone = json["cellphone"]?.trim()
        def password = json["password"]?.trim()

        if (!cellphone || cellphone.length() != 11)
        {
            def errors = [errorCode: 2000, errorMessage: "请填写正确的手机号"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        if (password == null || password.length() < 6 || password.length() > 16)
        {
            def errors = [errorCode: 2000, errorMessage: "密码长度在6-16位之间，请重新输入"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def c = Contact.findByCellphoneAndType(cellphone, "Agent")

        if (c == null || !c.password)
        {
            def errors = [errorCode: 3040, errorMessage: "您填写的手机号不存在，请先注册"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            password = password.encodeAsMD5()
            if (c.password == password)
            {
                c.appSessionId = UUID.randomUUID().toString()
                if (c.validate())
                {
                    c.save flush: true
                }
                else
                {
                    println c.errors
                }
                render contactService.toJson(c)
                return
            }
            else
            {
                def errors = [errorCode: 3060, errorMessage: "密码错误"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
    }

    @Secured('permitAll')
    @Transactional
    def appSendVerifiedCode()
    {
        def json = request.JSON
        println "******************* appSendVerifiedCode ********************"
        println json

        String cellphone = json["cellphone"]?.trim()
        String operation = json["operation"]

        if (!cellphone || cellphone.length() != 11)
        {
            def errors = [errorCode: 2000, errorMessage: "请填写正确的手机号"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            def contact = Contact.findByCellphoneAndType(cellphone, "Agent")
            switch (operation)
            {
            case "Register":
                if (contact)
                {
                    if (contact.password)
                    {
                        def errors = [errorCode: 1010, errorMessage: "该手机号已注册，请直接登录。"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                    }
                    else
                    {
                        contact.cellphoneHasVerified = false
                        contact.verifiedCode = contactService.generateVerifiedCode()

                        contact.save flush: true

                        messageService.sendMessage2(cellphone, "【中佳信】您的“中佳信”验证码为:" + contact.verifiedCode + "，10分钟内有效")
                        render status: 200
                        return
                    }
                }
                else
                {
                    contact = new Contact()
                    contact.type = "Agent"
                    contact.cellphone = cellphone
                    contact.verifiedCode = contactService.generateVerifiedCode()
                    contact.cellphoneHasVerified = false
                    contact.account = Account.find("from Account where name = ?", ['北京中佳信科技发展有限公司'])
                    if (contact.validate())
                    {
                        contact.save flush: true
                    }
                    else
                    {
                        println contact.errors
                    }

                    messageService.sendMessage2(cellphone, "【中佳信】您的“中佳信”验证码为:" + contact.verifiedCode + "，10分钟内有效")
                    render status: 200
                    return
                }
                break
            case "Login":
            case "UpdatePassword":
                if (!contact || !contact.password)
                {
                    def errors = [errorCode: 3040, errorMessage: "您填写的手机号不存在，请先注册"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                else
                {
                    contact.verifiedCode = contactService.generateVerifiedCode()

                    contact.save flush: true
                    messageService.sendMessage2(cellphone, "【中佳信】您的“中佳信”验证码为:" + contact.verifiedCode + "，10分钟内有效")
                    render status: 200
                    return
                }
                break
            }
        }
    }

    @Secured('permitAll')
    @Transactional
    def appVerifyCode()
    {
        // 通过手机号码获取用户信息
        def json = request.JSON
        println "******************* appVerifyCode ********************"
        println json

        def cellphone = json["cellphone"]?.trim()
        def verifiedCode = json["verifiedCode"]?.trim()

        if (!cellphone || cellphone.length() != 11)
        {
            def errors = [errorCode: 2000, errorMessage: "请填写正确的手机号"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else if (verifiedCode == null || verifiedCode.length() < 1 || verifiedCode.length() > 6)
        {
            def errors = [errorCode: 2040, errorMessage: "验证码位数错误，请重新输入"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            Contact contact = Contact.findByCellphoneAndType(cellphone, "Agent")
            if (contact)
            {
                if (contact.password)
                {
                    def errors = [errorCode: 1010, errorMessage: "该手机号已注册，请直接登录。"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                else if (contact.verifiedCode)
                {
                    if (contact.verifiedCode == verifiedCode)
                    {
                        contact.cellphoneHasVerified = true
                        contact.save flush: true
                        render status: 200
                        return
                    }
                    else
                    {
                        def errors = [errorCode: 1030, errorMessage: "验证码不正确"]
                        render JsonOutput.toJson(errors), status: 400
                        return
                    }
                }
                else
                {
                    def errors = [errorCode: 1020, errorMessage: "请获取验证码"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
            }
            else
            {
                def errors = [errorCode: 1020, errorMessage: "请获取验证码"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
    }

    @Secured('permitAll')
    @Transactional
    def appUpdatePassword()
    {
        // 找回密码
        def json = request.JSON
        println "******************* appUpdatePassword ********************"
        println json

        def cellphone = json["cellphone"]?.trim()
        def verifiedCode = json["verifiedCode"]?.trim()
        def password = json["password"]?.trim()
        def contact = Contact.findByCellphoneAndType(cellphone, "Agent")

        if (!cellphone || cellphone.length() != 11)
        {
            def errors = [errorCode: 2000, errorMessage: "请填写正确的手机号"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else if (verifiedCode == null || verifiedCode.length() < 1 || verifiedCode.length() > 6)
        {
            def errors = [errorCode: 2040, errorMessage: "验证码位数错误，请重新输入"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else if (password == null || password.length() < 6 || password.length() > 16)
        {
            def errors = [errorCode: 2010, errorMessage: "密码长度在6-16位之间，请重新输入"]
            render JsonOutput.toJson(errors), status: 400
        }
        else if (!contact || !contact.password)
        {
            def errors = [errorCode: 3040, errorMessage: "您填写的手机号不存在，请先注册"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            if (contact.verifiedCode == verifiedCode)
            {
                password = password.encodeAsMD5()
                if (contact.password == password)
                {
                    def errors = [errorCode: 1050, errorMessage: "新密码与原始密码一致，请重新设置"]
                    render JsonOutput.toJson(errors), status: 400
                    return
                }
                else
                {
                    contact.password = password
                    contact.appSessionId = UUID.randomUUID().toString()
                    contact.save flush: true
                    render contactService.toJson(contact)
                    return
                }
            }
            else
            {
                def errors = [errorCode: 1030, errorMessage: "验证码不正确"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appLoginWithCode()
    {
        // 验证码登录
        def json = request.JSON
        println "******************* appLoginWithCode ********************"
        println json

        def cellphone = json["cellphone"]?.trim()
        def verifiedCode = json["verifiedCode"]?.trim()

        if (!cellphone || cellphone.length() != 11)
        {
            def errors = [errorCode: 2000, errorMessage: "请填写正确的手机号"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (verifiedCode == null || verifiedCode.length() < 1 || verifiedCode.length() > 6)
        {
            def errors = [errorCode: 2040, errorMessage: "验证码位数错误，请重新输入"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def contact = Contact.findByCellphoneAndType(cellphone, "Agent")
        if (!contact || !contact.password)
        {
            def errors = [errorCode: 3040, errorMessage: "您填写的手机号不存在，请先注册"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            if (contact.verifiedCode == verifiedCode)
            {
                contact.appSessionId = UUID.randomUUID().toString()
                contact.save()
                render contactService.toJson(contact)
                return
            }
            else
            {
                def errors = [errorCode: 1030, errorMessage: "验证码不正确"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
    }

    @Secured(['permitAll'])
    def appQueryContactBySessionId()
    {
        // 个人中心
        def json = request.JSON
        println "******************* appQueryContactBySessionId ********************"
        println json

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
        else
        {
            render contactService.toJson(contact)
            return
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appUpdateContact()
    {
        // 修改个人信息
        def json = request.JSON
        println "******************* appUpdateContact ********************"
        println json

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

        def cityName = json["city"]
        def idNumber = json["idNumber"]?.trim()
        def bankName = json["bankName"]?.trim()
        def bankAccount = json["bankAccount"]?.trim()

        def city = City.findByName(cityName)

        if (idNumber)
        {
            if (!contactService.verifyIdNumber(idNumber))
            {
                def errors = [errorCode: 2070, errorMessage: "请输入正确的身份证号"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        if (bankName)
        {
            if (!(bankName.matches(/^[\u2190-\u9fff]+$/)))
            {
                def errors = [errorCode: 2080, errorMessage: "银行名称必须为汉字"]
                render JsonOutput.toJson(errors), status: 400
                return
            }

        }
        if (bankAccount)
        {
            if (!(bankAccount.length() > 14 && bankAccount.length() < 20))
            {
                def errors = [errorCode: 2090, errorMessage: "银行卡号长度为15~19位"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
        }
        if (!cityName || !city)
        {
            def errors = [errorCode: 2050, errorMessage: "请选择所在城市"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        else
        {
            contact.city = city
            contact.idNumber = idNumber
            contact.bankName = bankName
            contact.bankAccount = bankAccount
            contact.save flush: true

            render contactService.toJson(contact)
            return
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def appUpdateContactByUser()
    {
        // 助手端修改个人信息
        def json = request.JSON
        println "******************* appUpdateContactByUser ********************"
        println json

        def sessionId = json["sessionId"]
        def contactId = json["contactId"]
        //def sex = json["sex"]
        String cellphone = json["cellphone"]
        String maritalStatus = json["maritalStatus"]

        if (sessionId == null || sessionId == '')
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (contactId == null || contactId == '')
        {
            def errors = [errorCode: 2021, errorMessage: "需要修改的关联人不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        /*if (sex==null || sex=='')
        {
            def errors = [errorCode: 2022, errorMessage: "性别不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }*/
        if (cellphone == null || cellphone == '' || !(cellphone.matches(/^1\d{10}$/)))
        {
            def errors = [errorCode: 2023, errorMessage: "电话号不正确"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (maritalStatus == null || maritalStatus == '' || !(maritalStatus in ["未婚", "已婚", "再婚", "离异", "丧偶"]))
        {
            def errors = [errorCode: 2024, errorMessage: "婚姻状态不正确"]
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
        contactId = contactId.toInteger()
        Contact contact = Contact.findById(contactId)
        if (!contact)
        {
            def errors = [errorCode: 2022, errorMessage: "此关联人不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (contact.validate())
        {
            //contact.sex=sex
            contact.maritalStatus = maritalStatus
            contact.cellphone = cellphone

            contact.save flush: true
            render contact as JSON
        }
        else
        {
            println contact.errors

            def errors = [errorCode: 2098, errorMessage: "对不起，修改信息失败，请稍后重试"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

    }

    @Secured(['permitAll'])
    @Transactional
    def appUploadAvatar()
    {
        //修改头像
        def json = request.JSON
        println "******************* appUploadAvatar ********************"
        String sessionId = json["sessionId"]
        String avatar = json["avatar"]

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
        if (!avatar)
        {
            def errors = [errorCode: 2020, errorMessage: "请选择要上传的头像"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        String fileType = "jpg"
        def fileName = fileServerService.upload(avatar, fileType)
        if (fileName)
        {
            contact.avatar = "http://s27.zhongjiaxin.com/fs/static/images/${fileName}.${fileType}"
            if (contact.validate())
            {
                contact.save flush: true

                def data = [fullName: contact?.fullName, city: contact?.city?.name, avatar: contact?.avatar,
                    userCode: contact?.userCode, appSessionId: contact?.appSessionId,
                    level: contact?.level,
                    cellphone: contact?.cellphone, userName: contact?.user?.fullName,
                    idNumber: contact?.idNumber, bankName: contact?.bankName, bankAccount: contact?.bankAccount]
                render JsonOutput.toJson(data)
                return
            }
            else
            {
                contact.errors.each {
                    println it
                }
            }
        }

        def errors = [errorCode: 1070, errorMessage: "头像设置失败，请稍后重试"]
        render JsonOutput.toJson(errors), status: 400
        return
    }
}

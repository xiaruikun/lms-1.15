package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class OpportunityProductController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService
    def productService
    def componentService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityProduct.list(params), model: [opportunityProductCount: OpportunityProduct.count()]
    }

    def show(OpportunityProduct opportunityProduct)
    {
        respond opportunityProduct
    }

    def create()
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunity = Opportunity.findById(params["opportunity"])
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if ((opportunityRole && opportunityRole?.teamRole?.name == "Approval") || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")))
        {
            if (opportunity?.product && opportunity?.productAccount)
            {
                def opportunityProduct = new OpportunityProduct(params)
                opportunityProduct.createBy = user
                opportunityProduct.modifyBy = user
                respond opportunityProduct
            }
            else
            {
                flash.message = "对不起，请选择相应产品"
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
                return
            }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
    }

    @Transactional
    def save(OpportunityProduct opportunityProduct)
    {
        if (opportunityProduct == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityProduct.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityProduct.errors, view: 'create'
            return
        }

        //对于费率固定合同的修改
        def rateType = opportunityProduct?.productInterestType?.name
        if (rateType){
            if (rateType=="基本费率"||rateType=="二抵加收费率"||rateType=="信用调整"){
                opportunityProduct.contractType = ContractType.findByName("借款合同")
            }else if (rateType=="服务费费率"||rateType=="渠道返费费率"){
                opportunityProduct.contractType = ContractType.findByName("委托借款代理服务合同")
            }
        }

        def flag = productService.verifyInterest(opportunityProduct)
        if (flag)
        {
            def opportunityId = opportunityProduct.opportunity.id
            opportunityProduct.save flush: true
            println(opportunityProduct?.id)
            if (opportunityProduct?.opportunity?.parent)
            {
                def opportunityProduct1 = new OpportunityProduct()
                opportunityProduct1.rate = opportunityProduct.rate
                opportunityProduct1.monthes = opportunityProduct.monthes
                opportunityProduct1.firstPayMonthes = opportunityProduct.firstPayMonthes
                opportunityProduct1.lastPayMonthes = opportunityProduct.lastPayMonthes
                opportunityProduct1.fixedRate = opportunityProduct.fixedRate
                opportunityProduct1.installments = opportunityProduct.installments
                opportunityProduct1.product = opportunityProduct.product
                opportunityProduct1.productInterestType = opportunityProduct.productInterestType
                opportunityProduct1.createBy = opportunityProduct.createBy
                opportunityProduct1.modifyBy = opportunityProduct.modifyBy
                opportunityProduct1.createdDate = opportunityProduct.createdDate
                opportunityProduct1.modifiedDate = opportunityProduct.modifiedDate
                opportunityProduct1.contractType = opportunityProduct.contractType
                opportunityProduct1.externalId = opportunityProduct.externalId
                opportunityProduct1.opportunity = opportunityProduct?.opportunity?.parent
                opportunityProduct1.save flush: true
                println(opportunityProduct1?.id)
            }
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityProduct.label', default: 'OpportunityProduct'), opportunityId])
                    redirect(controller: "opportunityProduct", action: "eventEvaluate", params: [id: opportunityId])
                }
                '*' { respond opportunityProduct, [status: CREATED] }
            }
        }
        else
        {
            flash.message = "对不起，您保存的费用不符合产品标准"
            respond opportunityProduct, view: 'create'
            return
        }
    }

    def edit(OpportunityProduct opportunityProduct)
    {
        respond opportunityProduct
    }

    @Transactional
    def update(OpportunityProduct opportunityProduct)
    {
        if (opportunityProduct == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityProduct.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityProduct.errors, view: 'edit'
            return
        }
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def adjust = adjustOpportunityProduct(opportunityProduct)
        def flag = productService.verifyInterest(opportunityProduct)
        if (adjust)
        {
            if (flag)
            {
                opportunityProduct.modifyBy = user
                opportunityProduct.save flush: true
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityProduct.label', default: 'OpportunityProduct'), opportunityProduct.opportunity.id])
                        redirect(controller: "opportunityProduct", action: "eventEvaluate", params: [id: opportunityProduct.opportunity.id])
                    }
                    '*' { respond opportunityProduct, [status: OK] }
                }
            }
            else
            {
                transactionStatus.setRollbackOnly()
                flash.message = "对不起，您保存的费用不符合产品标准"
                respond opportunityProduct, view: 'edit'
                return
            }
        }
        else
        {
            transactionStatus.setRollbackOnly()
            flash.message = "对不起，您没有权限修改该费用"
            respond opportunityProduct, view: 'edit'
            return
        }
    }

    @Transactional
    def batchEdit(Opportunity opportunity)
    {
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
        {
            if (!opportunity.interest)
            {
                flash.message = "请先进行息费计算"
                redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
                return
            }
            else
            {
                render(view: "batchedit", model: [opportunity: opportunity, opportunityProduct: opportunityProduct])
                return
            }
        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }
    }

    @Transactional
    def batchUpdate()
    {
        def opportunityId = params['product']
        def opportunity = Opportunity.findById(opportunityId)
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        opportunityProduct.each {
            //                        println "ci chu mei keng" + params[it.productInterestType?.name+it.id]
            it.rate = params[it.productInterestType?.name + it.id].toDouble()
            //            println "zhende mei keng" + params[it.productInterestType?.name]
            it.save flush: true
            //            println "dou shuo le mei keng" + it.rate
        }

        redirect(controller: "opportunity", action: "refreshOpportunityProduct", params: [id: opportunity.id])
        return
    }

    @Transactional
    def delete(OpportunityProduct opportunityProduct)
    {

        if (opportunityProduct == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        def adjust = adjustOpportunityProduct(opportunityProduct)
        if (adjust)
        {
            opportunityProduct.delete flush: true
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityProduct.label', default: 'OpportunityProduct'), opportunityProduct.id])
                    redirect(controller: "opportunityProduct", action: "eventEvaluate", id: opportunityProduct?.opportunity?.id)
                }
                '*' { render status: NO_CONTENT }
            }
        }
        else
        {
            flash.message = "对不起，您没有权限删除该费用"
            redirect(controller: "opportunity", action: "show", params: [id: opportunityProduct.opportunity.id])
        }
    }

    /*
    *校验登录人是否为费用添加人上级或区域贷后角色
    *author xiaruikun
    *modifiedDate 20170705
    * */

    def adjustOpportunityProduct(OpportunityProduct opportunityProduct)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def reportingList = Reporting.findByUser(opportunityProduct?.createBy)
        boolean flag = false
        if (opportunityProduct?.createBy == user)
        {
            flag = true
        }
        if (reportingList)
        {
            for (
                def report in
                    reportingList)
            {
                if (report?.manager == user)
                {
                    flag = true
                }
                else
                {
                    def reportingList1 = Reporting.findByUser(report?.manager)
                    for (
                        def report1 in
                            reportingList1)
                    {
                        if (report1?.manager == user)
                        {
                            flag = true
                        }
                        else
                        {
                            def reportingList2 = Reporting.findByUser(report1?.manager)
                            for (
                                def report2 in
                                    reportingList2)
                            {
                                if (report2?.manager == user)
                                {
                                    flag = true
                                }
                            }
                        }
                    }
                }
            }
        }

        //区域贷后角色有删除修改权限
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")))
        {
            flag = true
        }
        return flag
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityProduct.label', default: 'OpportunityProduct'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    /*
    *执行相应component
    *author xiaruikun
    *modified banxujuan 2017-5-8
    * */

    @Transactional
    def eventEvaluate(Opportunity opportunity)
    {
        def result

        def component = Component.findByNameAndTypeAndActive("refreshOpportunityProduct", ComponentType.findByName("System"), true)
        if (component)
        {
            result = componentService.evaluate component, opportunity
        }

        if (result instanceof Exception)
        {
            log.error "SystemComponent error!"
        }

        redirect controller: "opportunity", action: "show", method: "GET", id: opportunity?.id
    }

    /*
    *计算息费
    *author xiaruikun
    * */
    def index = { opportunity ->
        def opportunityProductList = com.next.OpportunityProduct.findAllByProductAndOpportunity(opportunity?.productAccount, opportunity)
        opportunity.monthlyInterest = 0
        double service = 0.0
        double commission = 0.0
        java.math.BigDecimal b1
        java.math.BigDecimal monthlyInterest = new java.math.BigDecimal(Double.toString(opportunity?.monthlyInterest))
        java.math.BigDecimal serviceCharge = new java.math.BigDecimal(Double.toString(service))
        java.math.BigDecimal commissionRate = new java.math.BigDecimal(Double.toString(commission))
        java.math.BigDecimal actualLoanDuration = new java.math.BigDecimal(Integer.toString(opportunity?.actualLoanDuration))
        if (opportunityProductList && opportunityProductList.size() > 0)
        {
            println opportunityProductList
            for (
                def opportunityProduct :
                    opportunityProductList)
            {
                b1 = new BigDecimal(Double.toString(opportunityProduct.rate))
                if (opportunityProduct?.productInterestType?.name == "基本费率")
                {
                    if (opportunityProduct.installments)
                    {
                        monthlyInterest = monthlyInterest.add(b1)
                    }
                    else
                    {
                        monthlyInterest = monthlyInterest.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                    }
                }
                if (opportunityProduct?.productInterestType?.name == "郊县" || opportunityProduct?.productInterestType?.name == "大头小尾" || opportunityProduct?.productInterestType?.name == "信用调整" || opportunityProduct?.productInterestType?.name == "二抵加收费率" || opportunityProduct?.productInterestType?.name == "老人房（65周岁以上）" || opportunityProduct?.productInterestType?.name == "老龄房（房龄35年以上）" || opportunityProduct?.productInterestType?.name == "非7成区域")
                //所有加息项按照合同类型加到月息或服务费上
                {
                    if (opportunityProduct?.contractType)
                    {
                        if (opportunityProduct?.contractType?.name == '借款合同')
                        {
                            if (opportunityProduct.installments)
                            {
                                monthlyInterest = monthlyInterest.add(b1)
                            }
                            else
                            {
                                monthlyInterest = monthlyInterest.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                            }
                        }
                        else if (opportunityProduct?.contractType?.name == '委托借款代理服务合同')
                        {
                            if (opportunityProduct.installments)
                            {
                                serviceCharge = serviceCharge.add(b1)
                            }
                            else
                            {
                                serviceCharge = serviceCharge.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                            }
                        }
                    }
                    else
                    {
                        if (opportunityProduct.installments)
                        {
                            monthlyInterest = monthlyInterest.add(b1)
                        }
                        else
                        {
                            monthlyInterest = monthlyInterest.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                        }
                    }
                }
                if (opportunityProduct?.productInterestType?.name == "渠道返费费率")
                {
                    if (opportunityProduct.installments)
                    {
                        commissionRate = commissionRate.add(b1)
                        //渠道服务费
                    }
                    else
                    {
                        commissionRate = commissionRate.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                    }
                }
                if (opportunityProduct?.productInterestType?.name == "服务费费率")
                {
                    //借款服务费
                    if (opportunityProduct.installments)
                    {
                        serviceCharge = serviceCharge.add(b1)
                    }
                    else
                    {
                        serviceCharge = serviceCharge.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                    }
                }
            }
            opportunity.monthlyInterest = monthlyInterest.doubleValue()

            opportunity.ompositeMonthlyInterest = monthlyInterest.add(serviceCharge).doubleValue()
            opportunity.save flush: true
        }
    }
}

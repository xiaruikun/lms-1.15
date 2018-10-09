package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['permitAll'])
class BillsItemController
{
    def springSecurityService
    def billsService
    def componentService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond BillsItem.list(params), model: [billsItemCount: BillsItem.count()]
    }

    def show(BillsItem billsItem)
    {
        respond billsItem
    }

    def create()
    {
        def oid = params.opportunity
        def billsId = params.bills
        def bills = Bills.findById(billsId)
        def opportunity = bills.opportunity
        def credit = OpportunityBankAccount.findByOpportunityAndType(opportunity, OpportunityBankAccountType.findByName("还款账号"))?.bankAccount
        def debit = OpportunityBankAccount.findByOpportunityAndType(opportunity, OpportunityBankAccountType.findByName("收款账号"))?.bankAccount
        respond new BillsItem(params), model: [oid: oid, billsId: billsId, bills: bills, credit: credit, debit: debit]
    }

    @Transactional
    def save(BillsItem billsItem)
    {
        if (billsItem == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (billsItem.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond billsItem.errors, view: 'create'
            return
        }
        def oid = params.oid
        def billsId = params.billsId
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        if (oid && billsId)
        {
            def bills = Bills.findById(billsId)
            def opportunity = bills.opportunity
            def credit = OpportunityBankAccount.findByOpportunityAndType(opportunity, OpportunityBankAccountType.findByName("还款账号"))?.bankAccount
            def debit = OpportunityBankAccount.findByOpportunityAndType(opportunity, OpportunityBankAccountType.findByName("收款账号"))?.bankAccount
            billsItem.bills = bills
            billsItem.credit = credit
            billsItem.debit = debit
            billsItem.createdTime = new Date()
            billsItem.createdBy = user
            //            def startTime
            //            if (billsItem.period == 0) {
            //                startTime = billsItem.startTime + 1
            //            } else {
            //                startTime = billsItem.startTime - 2
            //            }
            //            println "1"
            //            billsItem.transactionRecord = TransactionRecord.findByOpportunityAndPlannedStartTimeAndRepaymentMethod(opportunity,startTime,RepaymentMethod.findByName("富友代扣"))
            //            if(billsItem.transactionRecord){
            //                println billsItem.transactionRecord
            //                billsItem.transactionRecord.amount += billsItem.receivable
            //            }
            billsItem.save flush: true
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'billsItem.label',
                                                                                        default: 'BillsItem'),
                    billsItem.id])
                redirect controller: "opportunity", action: "show", method: "GET", id: oid
            }
            '*' { respond billsItem, [status: CREATED] }
        }
    }

    def edit(BillsItem billsItem)
    {
        def oid = params.opportunity
        if (params?.isJq)
        {
            def isJq = params.isJq
            respond billsItem, model: [oid: oid, isJq: isJq]
        }
        else
        {
            respond billsItem, model: [oid: oid]
        }
    }

    @Transactional
    def update(BillsItem billsItem)
    {
        if (billsItem == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (billsItem.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond billsItem.errors, view: 'edit'
            return
        }
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        billsItem.type = BillsItemType.findById(billsItem?.type?.id)
        billsItem.modifiedBy = user
        //        println "1"
        //        if(billsItem.transactionRecord && billsItem.transactionRecord.status.name == "未执行" && billsItem.status == "已收"){
        //            println "2"
        //            billsItem.transactionRecord.amount = billsItem.transactionRecord.amount - billsItem.receivable
        //            if(billsItem.transactionRecord.amount == 0){
        //                billsItem.transactionRecord.status = TransactionRecordStatus.findByName("已成功")
        //            }
        //        }
        billsItem.save flush: true
        def id = params.oid
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'billsItem.label',
                                                                                        default: 'BillsItem'),
                    billsItem.id])
                redirect controller: "opportunity", action: "show", method: "GET", id: id
            }
            '*' { respond billsItem, [status: OK] }
        }
    }

    @Transactional
    def delete(BillsItem billsItem)
    {

        if (billsItem == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        billsItem.delete flush: true
        def id = params.oid
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'billsItem.label',
                                                                                        default: 'BillsItem'),
                    billsItem.id])
                redirect controller: "opportunity", action: "show", method: "GET", id: id
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Transactional
    def deleteAll()
    {
        def id = params.opportunity
        def opportunity = Opportunity.findById(id)
        if (opportunity?.type != OpportunityType.findByName("抵押贷款"))
        {
            opportunity = opportunity?.parent
        }
        def bills = opportunity?.bills[0]
        if (bills == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        def items = BillsItem.findAllByBills(bills)
        items.each {
            it.delete flush: true
        }
        redirect controller: "opportunity", action: "show", method: "GET", id: id
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'billsItem.label',
                                                                                          default: 'BillsItem'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    /**
     * 修改还款计划状态
     * @author Nagelan
     */
    def updateStatus()
    {
        def billsItem = BillsItem.findById(params["billItemId"])
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def key = params["name"]
        def value = params["dataName"]
        boolean val
        if (value == "true")
        {
            val = true
        }
        else
        {
            val = false
        }
        def data = [:]
        if (billsItem && key && value)
        {
            billsItem.modifiedBy = user
            if (key == "split")
            {
                billsItem.split = val
            }
            else if (key == "prepayment")
            {
                billsItem.prepayment = val
            }
            else
            {
                billsItem.overdue = val
            }
            if (billsItem.validate())
            {
                billsItem.save()
                data["status"] = "success"
                data["msg"] = "保存成功"
            }
            else
            {
                data["status"] = "failure"
                data["msg"] = "校验失败"
            }
        }
        else
        {
            data["status"] = "failure"
            data["msg"] = "没有取到值"
        }
        render data as JSON
    }

    /**
     * 重置还款计划退回到指定状态
     * @author Nagelan
     * */
    @Secured(['permitAll'])
    @Transactional
    def resetBillsItems()
    {
        def result
        def id = params.opportunity
        def opportunity = Opportunity.findById(id)
        def component = Component.findByName("重置还款计划")
        if (component && opportunity)
        {
            result = componentService.evaluate component, opportunity
        }

        if (result instanceof Exception)
        {
            log.error "SystemComponent error!"
        }

        redirect controller: "opportunity", action: "show", method: "GET", id: opportunity?.id
    }

    /**
     * 手动生成还款计划
     * 判断执行哪种还款计划的生成
     * @author Nagelan
     * @modified wangchao 2017-7-4
     * */
    @Secured(['permitAll'])
    @Transactional
    def eventEvaluate(Opportunity opportunity)
    {
        def result
        def component
        if (opportunity?.type?.name == "抵押贷款")
        {
            component = Component.findByName("生成还款计划")
            //component = OpportunityEvent.findByStageAndComponent(OpportunityFlow.findByOpportunityAndStage(opportunity, OpportunityStage.findByName("放款已完成")), Component.findByName("生成还款计划"))?.component
        }
        else if (opportunity?.type?.name == "抵押贷款展期")
        {
            component = Component.findByName("展期还款计划")
            //component = OpportunityEvent.findByStageAndComponent(OpportunityFlow.findByOpportunityAndStage(opportunity, OpportunityStage.findByName("展期审批已完成")), Component.findByName("展期还款计划"))?.component
        }
        else if (opportunity?.type?.name == "抵押贷款结清")
        {
            component = Component.findByName("结清计划")
            //component = OpportunityEvent.findByStageAndComponent(OpportunityFlow.findByOpportunityAndStage(opportunity, OpportunityStage.findByName("结清申请已提交")), Component.findByName("结清计划"))?.component
        }
        else if (opportunity?.type?.name == "抵押贷款早偿")
        {
            component = Component.findByName("早偿还款计划")
            //component = OpportunityEvent.findByStageAndComponent(OpportunityFlow.findByOpportunityAndStage(opportunity, OpportunityStage.findByName("早偿申请已提交")), Component.findByName("早偿还款计划"))?.component
        }
        else if (opportunity?.type?.name == "抵押贷款逾期")
        {
            component = Component.findByName("逾期还款计划")
            //component = OpportunityEvent.findByStageAndComponent(OpportunityFlow.findByOpportunityAndStage(opportunity, OpportunityStage.findByName("逾期申请已提交")), Component.findByName("逾期还款计划"))?.component
        }
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

    @Secured(['ROLE_ADMINISTRATOR'])
    def deleteErrorTransactionRecord()
    {
        def startTime = params.startTime
        def endTime = params.endTime
        def opportunities = Opportunity.findAll("from Opportunity where type.name = '抵押贷款' and actualLendingDate between '${startTime}' and '${endTime}' and actualLoanAmount != 0")
        opportunities.each {
            def transactionRecord = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${it.id} and status.name not in ('待确认','未执行')")
            def transactionRecords = TransactionRecord.findAllByOpportunity(it)
            if (transactionRecord.size() == 0)
            {
                println "删除还款计划opportunity.id = " + it.id
                def billsItem = BillsItem.findAllByBills(Bills.findByOpportunity(it))
                billsItem.each {
                    it.delete()
                }

            }
            if (transactionRecord.size() == 0)
            {
                println "删除交易记录opportunity.id = " + it.id
                transactionRecords.each {
                    it.delete()
                }
            }
        }
        render "success"
    }

    @Secured(['ROLE_ADMINISTRATOR'])
    def createOldBillsItems()
    {
        def component = Component.findByName("生成还款计划")
        def component1 = Component.findByName("生成交易记录")
        def startTime = params.startTime
        def endTime = params.endTime
        def opportunities = Opportunity.findAll("from Opportunity where type.name = '抵押贷款' and actualLendingDate between '${startTime}' and '${endTime}' and actualLoanAmount != 0")
        //def opportunities = Opportunity.findAll("from Opportunity where type.name = '抵押贷款' and actualLendingDate between '${startTime}' and '${endTime}' and actualLoanAmount != 0")
        println opportunities?.id
        println opportunities?.size()
        if (1 == 1)
        {
            println "删除已存在的还款计划，请等待"
            opportunities.each { opportunity ->
                def items
                //def transactionRecord = TransactionRecord.findByOpportunity(opportunity)
                //if (!transactionRecord)
                def transactionRecords = TransactionRecord.findAllByOpportunity(opportunity)
                println "订单" + opportunity?.id + "交易记录数" + transactionRecords.size()
                if (transactionRecords.size() == 0)
                {
                    def bills = Bills.findByOpportunity(opportunity)
                    if (bills)
                    {
                        items = BillsItem.findAllByBills(bills)
                        if (items)
                        {
                            items.each {
                                it.delete flush: true
                            }
                        }
                    }
                }
            }
            println "删除已存在的还款计划完成"
            println "生成新的还款计划"
            opportunities.each { opportunity ->
                //def transactionRecord = TransactionRecord.findByOpportunity(opportunity)
                //if (!transactionRecord)
                def transactionRecords = TransactionRecord.findAllByOpportunity(opportunity)
                println "订单" + opportunity?.id + "交易记录数" + transactionRecords.size()
                def result
                if (transactionRecords.size() == 0 && component)
                {
                    result = componentService.evaluate(component, opportunity)
                }
                if (result instanceof Exception)
                {
                    println "还款计划生成失败！！！"
                    log.error "SystemComponent error!"
                }
            }
            println "生成新的交易记录开始"
            opportunities.each { opportunity ->
                //def transactionRecord = TransactionRecord.findByOpportunity(opportunity)
                //println "订单" + opportunity?.id + "交易记录" + transactionRecord?.id
                //if (!transactionRecord)
                def transactionRecords = TransactionRecord.findAllByOpportunity(opportunity)
                println "订单" + opportunity?.id + "交易记录数" + transactionRecords.size()
                def result1
                if (transactionRecords.size() == 0 && component1)
                {
                    result1 = componentService.evaluate(component1, opportunity)
                }
                if (result1 instanceof Exception)
                {
                    println "交易记录生成失败！！！"
                    log.error "SystemComponent error!"
                }
            }
            println "生成新的交易记录完成"
        }
        render opportunities?.id
    }

    /**
     * 下载还款计划（告知书）
     * @param opportunityId
     * @return map
     * @author Nagelan
     */
    @Transactional
    def download(Opportunity opportunity)
    {
        def items = []
        def itemsService = []
        def itemsAll = []
        def itemsPlat = []
        def bills = [:]
        opportunity = Opportunity.findById(opportunity?.id)
        if (opportunity?.type != OpportunityType.findByName("抵押贷款"))
        {
            opportunity = opportunity?.parent
        }
        def opportunityBills = opportunity?.bills
        //还本类型
        def principalPaymentType = opportunity?.productAccount?.principalPaymentMethod?.name
        //产品类型
        def productType = opportunity?.product?.name
        def op = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        if (opportunityBills && op)
        {
            //还款计划的基础信息
            bills["fullName"] = opportunity?.fullName
            def principal = opportunity?.actualLoanAmount * 10000 ? opportunity?.actualAmountOfCredit * 10000 : opportunity?.actualLoanAmount * 10000
            bills["actualAmountOfCredit"] = principal / 10000
            bills["actualLoanDuration"] = opportunity?.actualLoanDuration
            def interestPaymentMethod = opportunity?.interestPaymentMethod?.name
            if (interestPaymentMethod == "上扣息")
            {
                bills["interestPaymentMethod"] = interestPaymentMethod + "(" + (int)opportunity?.monthOfAdvancePaymentOfInterest + "个月)"
            }
            else if (interestPaymentMethod == "下扣息")(
                    bills["interestPaymentMethod"] = interestPaymentMethod + "(" + opportunity?.lastPayMonthes + "个月)"
            )
            else
            {
                bills["interestPaymentMethod"] = interestPaymentMethod
            }
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join c.type t left join oc.opportunity o where o.id = ${opportunity?.id} and t.name = '借款合同'")
            if (contract)
            {
                bills["serialNumber"] = contract[0]
            }
            def advancePayment = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, ProductInterestType.findByName("意向金"))?.rate == 0 ? opportunity?.advancePayment : OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, ProductInterestType.findByName("意向金"))?.rate
            bills["advancePayment"] = advancePayment
            def interestRates = OpportunityProduct.findAllByOpportunityAndProductAndContractType(opportunity, opportunity?.productAccount, ContractType.findByName("借款合同"))
            def interestRate = 0
            interestRates.each {
                if (it?.installments)
                {
                    interestRate += it?.rate
                }
                else
                {
                    interestRate += it?.rate / opportunity?.actualLoanDuration
                }
            }
            bills["interestRate"] = interestRate
            bills["actualLendingDate"] = opportunity?.actualLendingDate.format("yyyy-MM-dd")
            //订单结束时间处理
            Calendar calendar = Calendar.getInstance()
            calendar.clear()
            calendar.setTime(opportunity?.actualLendingDate)
            def day = calendar.get(Calendar.DAY_OF_MONTH)
            calendar.add(Calendar.MONTH, opportunity?.actualLoanDuration)
            def monthMaxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            def endDate = calendar.get(Calendar.DAY_OF_MONTH)
            println monthMaxDays
            if (endDate< day) {
                println '此月天数小于初始月份天数'
            }else{
                println '此月天数不小于初始月份天数'
                calendar.add(Calendar.DAY_OF_MONTH,-1)
            }
            def opportunityEndTime = calendar.getTime().format("yyyy-MM-dd")
            bills["endTime"] = opportunityEndTime
            //def serviceTimes = OpportunityProduct.findAllByOpportunityAndProductInterestType(opportunity, ProductInterestType.findByName("服务费费率"))?.size()
            def serviceTimes = 1
            bills["serviceTimes"] = serviceTimes
            bills["platformCharge"] = OpportunityProduct.findByOpportunityAndProductAndInstallmentsAndProductInterestType(opportunity, opportunity?.productAccount, true, ProductInterestType.findByName("服务费费率"))?.rate
            if (serviceTimes == 2)
            {
                bills["serviceChargeOne"] = OpportunityProduct.findByOpportunityAndProductAndInstallmentsAndProductInterestType(opportunity, opportunity?.productAccount, false, ProductInterestType.findByName("服务费费率"))?.rate / opportunity?.actualLoanDuration
            }
            else
            {
                def serviceCharge = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, ProductInterestType.findByName("服务费费率"))
                if (serviceCharge)
                {
                    if (serviceCharge?.installments)
                    {
                        bills["serviceChargeOne"] = serviceCharge?.rate
                    }
                    else
                    {
                        bills["serviceChargeOne"] = serviceCharge?.rate / opportunity?.actualLoanDuration
                    }
                }
                else
                {
                    bills["serviceChargeOne"] = 0
                }
            }
            def serviceCharge = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, ProductInterestType.findByName("渠道返费费率"))
            if (serviceCharge)
            {
                if (serviceCharge?.installments)
                {
                    bills["serviceChargeTwo"] = serviceCharge?.rate
                }
                else
                {
                    bills["serviceChargeTwo"] = serviceCharge?.rate / opportunity?.actualLoanDuration
                }
            }
            else
            {
                bills["serviceChargeTwo"] = 0
            }
            //还款计划详情
            //上扣息月份数
            def dayFirst
            def dayLast
            def firstPayOfMonthes = opportunity?.monthOfAdvancePaymentOfInterest?.intValue() == 0 ? 1 : opportunity?.monthOfAdvancePaymentOfInterest?.intValue()
            //意向金
            if (day < 15)
            {
                if (interestPaymentMethod =="下扣息"){
                    firstPayOfMonthes = 1
                }
                dayFirst = 20 - day + (firstPayOfMonthes - 1) * 30
                dayLast = 10 + day
            }
            else if (day >= 15 && day < 20)
            {
                if (interestPaymentMethod == "扣息差" && opportunity.actualLoanDuration != 1)
                {
                    firstPayOfMonthes = 2
                }
                if (interestPaymentMethod == "上扣息" && firstPayOfMonthes == 1 && opportunity.actualLoanDuration != 1)
                {
                    firstPayOfMonthes = 2
                }
                if (interestPaymentMethod == "月息年本")
                {
                    firstPayOfMonthes = 2
                }
                if (interestPaymentMethod =="下扣息"){
                    firstPayOfMonthes = 1
                }
                dayFirst = 50 - day + (firstPayOfMonthes - 2) * 30
                dayLast = 10 + day
            }
            else
            {
                if (interestPaymentMethod =="下扣息"){
                    firstPayOfMonthes = 1
                }
                dayFirst = 50 - day + (firstPayOfMonthes - 1) * 30
                dayLast = day - 20
            }
            for (
                int i = 0;
                    i <= (opportunity.actualLoanDuration - firstPayOfMonthes + 1);
                    i++)
            {
                def item = [:]
                //总
                def item1 = [:]
                //服务费
                def item2 = [:]
                //利息费
                def item3 = [:]
                //平台费
                def billsItems
                if (i == opportunity?.actualLoanDuration + 1)
                {
                    billsItems = BillsItem.findAllByBillsAndPeriod(opportunity?.bills, i - 1)
                }
                else
                {
                    billsItems = BillsItem.findAllByBillsAndPeriod(opportunity?.bills, i)
                }

                if (billsItems)
                {
                    //def principal = opportunity?.actualLoanAmount* 10000?opportunity?.actualAmountOfCredit* 10000:opportunity?.actualLoanAmount * 10000
                    def totalMoney = 0
                    def serviceMoney = 0
                    def money = 0
                    def money1 = 0
                    def money2 = 0
                    //平台费
                    def serviceMoney1 = 0
                    //特殊产品的服务费（有两个服务费费率）
                    def installmentsService
                    if (serviceTimes == 2)
                    {
                        installmentsService = false
                    }
                    else
                    {
                        installmentsService = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, ProductInterestType.findByName("服务费费率"))?.installments
                    }
                    billsItems?.each {
                        def contractType = com.next.OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, com.next.ProductInterestType.findByName(it?.type?.name))?.contractType?.name
                        /*if (it.type?.name=="本金"&&it.receivable*10000==principal){
                            totalMoney += 0
                        }else {
                            totalMoney += it.receivable * 10000
                        }*/
                        totalMoney += it.receivable * 10000
                        if (contractType == "借款合同")
                        {
                            money += it.receivable * 10000
                        }
                        else if (contractType == "委托借款代理服务合同")
                        {
                            serviceMoney += it.receivable * 10000
                        }
                        /*if (it?.type?.name == "服务费费率" || it?.type?.name == "渠道费费率")
                        {
                            serviceMoney += it.receivable * 10000
                        }
                        if (it?.type?.name != "服务费费率"&& it?.type?.name != "本金"&& it?.type?.name != "渠道费费率")
                        {
                            money += it.receivable * 10000
                        }
                        if (it?.type?.name != "基本费率" && it?.type?.name != "本金" && it?.type?.name != "服务费费率")
                        {
                            //money1 += it.receivable * 10000
                            money1 == 0
                        }*/
                        if (it?.type?.name == "服务费费率" && serviceTimes == 2)
                        {
                            money2 += it.receivable * 10000
                        }
                    }
                    if (serviceTimes == 2)
                    {
                        def serviceRate = OpportunityProduct.findByOpportunityAndProductAndInstallmentsAndProductInterestType(opportunity, opportunity?.productAccount, false, ProductInterestType.findByName("服务费费率"))?.rate
                        if (serviceRate)
                        {
                            serviceMoney1 = principal * serviceRate * 10000
                            if (i == 0)
                            {
                                money2 -= serviceMoney1
                            }
                        }
                        else
                        {
                            serviceMoney1 = 0
                        }
                    }
                    //服务费列表
                    item1["installmentsService"] = installmentsService
                    item1["startTime"] = billsItems[0].startTime.format("yyyy-MM-dd")
                    item1["endTime"] = billsItems[0].endTime.format("yyyy-MM-dd")
                    item1["money"] = serviceMoney
                    item1["period"] = i
                    if (i == 0)
                    {
                        /*Calendar calendar1 = Calendar.getInstance()
                        calendar1.setTime(billsItems[0].startTime)
                        calendar1.add(Calendar.DAY_OF_MONTH, 1)*/
                        if (principalPaymentType == "等额本息" || productType == "等额本息"||interestPaymentMethod == "下扣息")
                        {
                            /*Calendar calendar2 = Calendar.getInstance()
                            calendar2.setTime(billsItems[0].endTime)
                            calendar2.add(Calendar.DAY_OF_MONTH, 1)
                            item1["repaymentDate"] = calendar2.getTime().format("yyyy-MM-dd")*/
                            item1["repaymentDate"] = (billsItems[0].startTime+1)?.format("yyyy-MM-dd")
                        }
                        else
                        {
                            item1["repaymentDate"] = (billsItems[0].startTime+1)?.format("yyyy-MM-dd")
                        }
                        item1["days"] = dayFirst
                        item1["monthes"] = ""
                        if (!installmentsService)
                        {
                            item1["startTime1"] = billsItems[0].startTime.format("yyyy-MM-dd")
                            item1["endTime1"] = opportunityEndTime
                            item1["monthes1"] = opportunity?.actualLoanDuration
                            if (serviceTimes == 2)
                            {
                                item1["serviceMoney"] = serviceMoney1
                            }
                            else
                            {
                                item1["serviceMoney"] = serviceMoney - money1
                            }
                            item1["money1"] = money1
                        }
                    }
                    else if (i == (opportunity.actualLoanDuration - firstPayOfMonthes + 1))
                    {
                        if (principalPaymentType == "等额本息" || productType == "等额本息"||interestPaymentMethod == "下扣息")
                        {
                            item1["repaymentDate"] = opportunityEndTime
                        }
                        else
                        {
                            item1["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                        }
                        item1["days"] = dayLast
                        item1["monthes"] = ""
                    }
                    else
                    {
                        if (principalPaymentType == "等额本息" || productType == "等额本息"||interestPaymentMethod == "下扣息")
                        {
                           /* Calendar calendar2 = Calendar.getInstance()
                            calendar2.setTime(billsItems[0].endTime)
                            calendar2.add(Calendar.DAY_OF_MONTH, 1)
                            item1["repaymentDate"] = calendar2.getTime().format("yyyy-MM-dd")*/
                            item1["repaymentDate"] = (billsItems[0].endTime+1).format("yyyy-MM-dd")
                        }
                        else
                        {
                            item1["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                        }
                        item1["days"] = ""
                        item1["monthes"] = 1
                    }
                    /*if (serviceTimes != 2)
                    {
                        if (i == opportunity?.actualLoanDuration)
                        {
                            item1["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                            item1["days"] = dayLast
                            item1["monthes"] = ""
                        }
                        else if (i != opportunity?.actualLoanDuration + 1 && i != 0)
                        {
                            item1["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                            item1["days"] = ""
                            item1["monthes"] = 1
                        }
                    }*/
                    if (serviceMoney != 0)
                    {
                        itemsService.add(item1)
                    }
                    //利息告知书
                    item2["installmentsService"] = installmentsService
                    item2["startTime"] = billsItems[0].startTime.format("yyyy-MM-dd")
                    item2["endTime"] = billsItems[0].endTime.format("yyyy-MM-dd")
                    item2["money"] = money
                    item2["period"] = i
                    if (i == 0)
                    {
                        /*Calendar calendar1 = Calendar.getInstance()
                        calendar1.setTime(billsItems[0].startTime)
                        calendar1.add(Calendar.DAY_OF_MONTH, 1)*/
                        if (principalPaymentType == "等额本息" || productType == "等额本息"||interestPaymentMethod == "下扣息")
                        {
                           /* Calendar calendar2 = Calendar.getInstance()
                            calendar2.setTime(billsItems[0].endTime)
                            calendar2.add(Calendar.DAY_OF_MONTH, 1)
                            item2["repaymentDate"] = calendar2.getTime().format("yyyy-MM-dd")*/
                            item2["repaymentDate"] = (billsItems[0].endTime+1)?.format("yyyy-MM-dd")
                        }
                        else
                        {
                            item2["repaymentDate"] = (billsItems[0].startTime+1)?.format("yyyy-MM-dd")
                        }
                        item2["days"] = dayFirst
                        item2["monthes"] = ""
                        if (!installmentsService)
                        {
                            item2["startTime1"] = billsItems[0].startTime.format("yyyy-MM-dd")
                            item2["endTime1"] = opportunityEndTime
                        }
                    }
                    else if (i == (opportunity.actualLoanDuration - firstPayOfMonthes + 1))
                    {
                        if (principalPaymentType == "等额本息" || productType == "等额本息"||interestPaymentMethod == "下扣息")
                        {
                            item2["repaymentDate"] = opportunityEndTime
                        }
                        else
                        {
                            item2["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                        }
                        item2["days"] = dayLast
                        item2["monthes"] = ""
                    }
                    /*else if (i == opportunity?.actualLoanDuration + 1)
                    {
                        item2["startTime"] = opportunity?.actualLendingDate.format("yyyy-MM-dd")
                        item2["endTime"] = opportunityEndTime
                        item2["repaymentDate"] = opportunityEndTime
                        item2["days"] = ""
                        item2["monthes"] = opportunity?.actualLoanDuration
                        item2["money"] = opportunity?.actualLoanAmount* 10000?opportunity?.actualAmountOfCredit* 10000:opportunity?.actualLoanAmount * 10000
                        if(day==20){
                            item2["period"] = i-1
                        }
                    }*/ else
                    {
                        if (principalPaymentType == "等额本息" || productType == "等额本息"||interestPaymentMethod == "下扣息")
                        {
                            /*Calendar calendar2 = Calendar.getInstance()
                            calendar2.setTime(billsItems[0].endTime)
                            calendar2.add(Calendar.DAY_OF_MONTH, 1)*/
                            item2["repaymentDate"] = (billsItems[0].endTime+1).format("yyyy-MM-dd")
                        }
                        else
                        {
                            item2["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                        }
                        item2["days"] = ""
                        item2["monthes"] = 1
                    }
                    items.add(item2)
                    //所有还款计划
                    item["installmentsService"] = installmentsService
                    item["startTime"] = billsItems[0].startTime.format("yyyy-MM-dd")
                    item["endTime"] = billsItems[0].endTime.format("yyyy-MM-dd")
                    item["money"] = totalMoney
                    item["period"] = i
                    if (i == 0)
                    {
                        /*Calendar calendar1 = Calendar.getInstance()
                        calendar1.setTime(billsItems[0].startTime)
                        calendar1.add(Calendar.DAY_OF_MONTH, 1)*/
                        if (principalPaymentType == "等额本息" || productType == "等额本息"||interestPaymentMethod == "下扣息")
                        {
                            /*Calendar calendar2 = Calendar.getInstance()
                            calendar2.setTime(billsItems[0].endTime)
                            calendar2.add(Calendar.DAY_OF_MONTH, 1)*/
                            item["repaymentDate"] = (billsItems[0].endTime+1)?.format("yyyy-MM-dd")
                        }
                        else
                        {
                            item["repaymentDate"] = (billsItems[0].startTime+1)?.format("yyyy-MM-dd")
                        }
                        if (opportunity?.interestPaymentMethod?.name == '扣息差' && opportunity?.actualLoanDuration == 1)
                        {
                            item["days"] = ""
                            item["monthes"] = 1
                            item["money"] = totalMoney - principal
                        }
                        else
                        {
                            item["days"] = dayFirst
                            item["monthes"] = ""
                        }
                        if (!installmentsService)
                        {
                            item["repaymentDate1"] = (billsItems[0].startTime+1)?.format("yyyy-MM-dd")
                            item["startTime1"] = billsItems[0].startTime.format("yyyy-MM-dd")
                            item["endTime1"] = opportunityEndTime
                            item["serviceMoney"] = serviceMoney
                            item["money1"] = totalMoney - serviceMoney
                            item["monthes1"] = opportunity?.actualLoanDuration
                        }
                    }
                    else if (i == (opportunity.actualLoanDuration - firstPayOfMonthes + 1))
                    {
                        if (principalPaymentType == "等额本息" || productType == "等额本息"||interestPaymentMethod == "下扣息")
                        {
                            item["repaymentDate"] = opportunityEndTime
                        }
                        else
                        {
                            item["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                        }
                        item["days"] = dayLast
                        item["monthes"] = ""
                        if (productType == "速e贷" || principalPaymentType == "到期还本")
                        {
                            item["money"] = totalMoney - principal
                        }
                    }
                    /*else if (i == opportunity?.actualLoanDuration + 1)
                    {
                        item["startTime"] = opportunity?.actualLendingDate.format("yyyy-MM-dd")
                        item["endTime"] = opportunityEndTime
                        item["repaymentDate"] = opportunityEndTime
                        item["days"] = ""
                        item["monthes"] = opportunity?.actualLoanDuration
                        item["money"] = opportunity?.actualLoanAmount* 10000?opportunity?.actualAmountOfCredit* 10000:opportunity?.actualLoanAmount * 10000
                        if(day==20){
                            item["period"] = i-1
                        }
                    }*/ else
                    {
                        if (principalPaymentType == "等额本息" || productType == "等额本息"||interestPaymentMethod == "下扣息")
                        {
                            /*Calendar calendar2 = Calendar.getInstance()
                            calendar2.setTime(billsItems[0].endTime)
                            calendar2.add(Calendar.DAY_OF_MONTH, 1)*/
                            item["repaymentDate"] = (billsItems[0].endTime+1)?.format("yyyy-MM-dd")
                        }
                        else
                        {
                            item["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                        }
                        item["days"] = ""
                        item["monthes"] = 1
                    }
                    itemsAll.add(item)

                    //平台费
                    item3["installmentsService"] = installmentsService
                    item3["startTime"] = billsItems[0].startTime.format("yyyy-MM-dd")
                    item3["endTime"] = billsItems[0].endTime.format("yyyy-MM-dd")
                    item3["money"] = money2
                    item3["period"] = i
                    if (i == 0)
                    {
                        /*Calendar calendar1 = Calendar.getInstance()
                        calendar1.setTime(billsItems[0].startTime)
                        calendar1.add(Calendar.DAY_OF_MONTH, 1)*/
                        item3["repaymentDate"] = (billsItems[0].startTime+1).format("yyyy-MM-dd")
                        item3["days"] = dayFirst
                        item3["monthes"] = ""
                        if (!installmentsService)
                        {
                            item3["startTime1"] = billsItems[0].startTime.format("yyyy-MM-dd")
                            item3["endTime1"] = opportunityEndTime
                        }
                    }
                    else if (i == (opportunity.actualLoanDuration - firstPayOfMonthes + 1))
                    {
                        item3["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                        item3["days"] = dayLast
                        item3["monthes"] = ""
                    }
                    else
                    {
                        item3["repaymentDate"] = billsItems[0].startTime.format("yyyy-MM-dd")
                        item3["days"] = ""
                        item3["monthes"] = 1
                    }
                    itemsPlat.add(item3)
                }
            }
            if (productType == "速e贷" || principalPaymentType == "到期还本")
            {
                def item = [:]
                item["startTime"] = opportunity?.actualLendingDate.format("yyyy-MM-dd")
                item["endTime"] = opportunityEndTime
                item["repaymentDate"] = opportunityEndTime
                item["days"] = ""
                item["monthes"] = opportunity?.actualLoanDuration
                item["money"] = principal
                if (day == 20)
                {
                    item["period"] = opportunity.actualLoanDuration - firstPayOfMonthes + 1
                }
                else
                {
                    item["period"] = opportunity.actualLoanDuration - firstPayOfMonthes + 2
                }

                itemsAll.add(item)
            }
        }
        respond bills, model: [bills: bills, items: items, itemsService: itemsService, itemsAll: itemsAll, itemsPlat: itemsPlat], view: 'billsItemsPrint'
    }

    /**
     * 结清记录（流水记录）
     * @param opportunity
     * @return map
     * @author Nagelan
     */
    @Transactional
    def closingProofItems(Opportunity opportunity)
    {
        def map = [:]
        def receivableList = []
        def receiptsList = []
        def cityName = opportunity?.parent?.user?.city?.name
        if (cityName)
        {
            if (cityName == "北京")
            {
                cityName = "BJ"
            }
            else if (cityName == "上海")
            {
                cityName = "SH"
            }
            else if (cityName == "成都")
            {
                cityName = "CD"
            }
            else if (cityName == "青岛")
            {
                cityName = "QD"
            }
            else if (cityName == "济南")
            {
                cityName = "JN"
            }
            else if (cityName == "郑州")
            {
                cityName = "ZJ"
            }
            else if (cityName == "南京")
            {
                cityName = "NJ"
            }
            else if (cityName == "西安")
            {
                cityName = "XA"
            }
            else if (cityName == "合肥")
            {
                cityName = "HF"
            }
            else if (cityName == "武汉")
            {
                cityName = "WH"
            }
            else if (cityName == "苏州")
            {
                cityName = "SZ"
            }
            else if (cityName == "石家庄")
            {
                cityName = "SJZ"
            }
            else if (cityName == "厦门")
            {
                cityName = "XM"
            }
        }
        map["city"] = cityName
        if (opportunity?.parent?.actualLendingDate)
        {
            map["actualLendingDate"] = opportunity?.parent?.actualLendingDate.format("yyyy年MM月dd日")
        }
        else
        {
            map["actualLendingDate"] = opportunity?.parent?.actualLendingDate
        }
        map["actualLoanDuration"] = opportunity?.parent?.actualLoanDuration
        def users = OpportunityContact.findAllByOpportunity(opportunity?.parent)
        def fullName = ""
        users.each {
            fullName += it.contact?.fullName + ","
        }
        if (users)
        {
            map["fullName"] = StringUtils.substringBeforeLast(fullName, ",")
        }
        else
        {
            map["fullName"] = ""
        }
        def bills = Bills.findByOpportunity(opportunity?.parent)
        if (bills?.capital)
        {
            map["principal"] = billsService.transforNumToRMB(bills?.capital * 10000)
        }
        else
        {
            map["principal"] = billsService.transforNumToRMB(opportunity?.parent?.actualLoanAmount * 10000 == 0 ? opportunity?.parent?.actualAmountOfCredit * 10000 : opportunity?.parent?.actualLoanAmount * 10000)
        }
        def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join c.type t left join oc.opportunity o where o.id = ${opportunity?.parent?.id} and t.name = '借款合同'")
        if (contract)
        {
            map["serialNumber"] = contract[0]
        }
        else
        {
            map["serialNumber"] = ""
        }
        //约定还款日
        if (opportunity?.actuaRepaymentDate)
        {
            map["actuaRepaymentDate"] = opportunity?.actuaRepaymentDate.format("yyyy年MM月dd日")
        }
        else
        {
            map["actuaRepaymentDate"] = opportunity?.actuaRepaymentDate
        }

        def items = [:]
        def op = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def type
        def rate = 0, rate1 = 0, rate2 = 0, rate3 = 0, rate4 = 0, rate5 = 0, rate6 = 0
        //基本费率,服务费费率，渠道返费费率，早偿违约金，本金违约金，利息违约金，意向金
        op.each {
            type = it.productInterestType.name
            if (type != "服务费费率" && type != "渠道返费费率" && type != "早偿违约金" && type != "本金违约金" && type != "利息违约金" && type != "逾期本金利息" && type != "本金违约金减免金额" && type != "利息违约金减免金额" && type != "意向金")
            {
                if (it.installments)
                {
                    rate += it.rate
                }
                else
                {
                    rate += it.rate / opportunity?.parent?.actualLoanDuration
                }
            }
            else if (type == "服务费费率")
            {
                if (it.installments)
                {
                    rate1 += it.rate
                }
                else
                {
                    rate1 += it.rate / opportunity?.parent?.actualLoanDuration
                }
            }
            else if (type == "渠道返费费率")
            {
                if (it.installments)
                {
                    rate2 += it.rate * opportunity?.parent?.actualLoanDuration
                }
                else
                {
                    rate2 += it.rate
                }
            }
            else if (type == "早偿违约金")
            {
                if (it.installments)
                {
                    rate3 += it.rate * opportunity?.parent?.actualLoanDuration
                }
                else
                {
                    rate3 += it.rate
                }
            }
            else if (type == "本金违约金")
            {
                if (it.installments)
                {
                    rate4 += it.rate
                }
                else
                {
                    rate4 += it.rate / opportunity?.parent?.actualLoanDuration
                }
            }
            else if (type == "利息违约金")
            {
                if (it.installments)
                {
                    rate5 += it.rate
                }
                else
                {
                    rate5 += it.rate / opportunity?.parent?.actualLoanDuration
                }
            }
            else if (type == "意向金")
            {
                rate6 += it.rate
            }
        }
        println rate + "," + rate1 + "," + rate2 + "," + rate3 + "," + rate4 + "," + rate5
        Calendar endDate = Calendar.getInstance()
        endDate.setTime(opportunity?.parent?.actualLendingDate)
        endDate.add(Calendar.MONTH, opportunity?.actualLoanDuration)
        endDate.add(Calendar.DATE, -1)
        def startItem, endItem
        int dLast, y1, m1, d1, y2, m2, d2
        def monthes
        //计算时间（月）
        def days
        //计算时间（日）
        def fee = 0
        //减免费用
        def receivable = 0
        //每种费用应收
        def receivables = 0
        //合计应收
        Calendar c1 = Calendar.getInstance()
        Calendar c2 = Calendar.getInstance()
        if (rate != 0)
        {
            startItem = BillsItem.findByBillsAndTypeAndStatusAndOverdueAndPrepaymentAndSplit(bills, BillsItemType.findByName("基本费率"), "已收", false, false, false, [sort: "startTime", order: "asc"])
            endItem = BillsItem.findByBillsAndTypeAndStatusAndOverdueAndPrepaymentAndSplit(bills, BillsItemType.findByName("基本费率"), "已收", false, false, false, [sort: "startTime", order: "desc"])
            if (startItem && endItem)
            {
                items = [:]
                items["program"] = "利息"
                items["radix"] = opportunity?.parent?.actualLoanAmount * 10000
                items["startTime"] = startItem?.startTime.format("yyyy-MM-dd")
                items["endTime"] = endItem?.endTime.format("yyyy-MM-dd")
                //计算还款月份数和天数
                c1.setTime(endItem.endTime)
                y1 = c1.get(Calendar.YEAR)
                m1 = c1.get(Calendar.MONTH)
                d1 = c1.get(Calendar.DAY_OF_MONTH)
                c1.setTime(startItem.startTime)
                y2 = c2.get(Calendar.YEAR)
                m2 = c2.get(Calendar.MONTH)
                d2 = c2.get(Calendar.DAY_OF_MONTH)
                monthes = (y1 - y2) * 12 + m1 - m2 - 1
                days = d1 - d2 + 31

                /*dLast = (endDate.getTimeInMillis()-endItem.endTime.getTime())/(3600*1000*24)
                if (dLast == 0){
                    monthes = opportunity?.actualLoanDuration
                    days = 0
                }else if (dLast>0){
                    monthes = opportunity?.actualLoanDuration - (Integer)(dLast/30)
                    days = 30 - dLast%30
                }else {
                    monthes = opportunity?.actualLoanDuration + (Integer)(dLast/30)
                    days = dLast%30
                }*/
                if (monthes)
                {
                    items["monthes"] = monthes
                }
                else
                {
                    items["monthes"] = ""
                }
                if (days)
                {
                    items["days"] = days
                }
                else
                {
                    items["days"] = ""
                }

                items["rate"] = rate
                items["unit"] = "/月"
                if (fee)
                {
                    items["fee"] = fee
                }
                else
                {
                    items["fee"] = ""
                }
                receivable = opportunity?.parent?.actualLoanAmount * rate * (monthes + ((Double) days / 30)) * 100
                items["receivable"] = receivable
                BigDecimal serviceCharge = new BigDecimal(Double.toString(items["receivable"]))
                items["receivable"] = serviceCharge.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
                receivableList.add(items)
                receivables += items["receivable"]
            }
        }
        if (rate1 != 0)
        {
            startItem = BillsItem.findByBillsAndTypeAndStatusAndOverdueAndPrepaymentAndSplit(bills, BillsItemType.findByName("服务费费率"), "已收", false, false, false, [sort: "startTime", order: "asc"])
            endItem = BillsItem.findByBillsAndTypeAndStatusAndOverdueAndPrepaymentAndSplit(bills, BillsItemType.findByName("服务费费率"), "已收", false, false, false, [sort: "startTime", order: "desc"])
            if (startItem && endItem)
            {
                items = [:]
                items["program"] = "服务费"
                items["radix"] = opportunity?.parent?.actualLoanAmount * 10000
                items["startTime"] = startItem?.startTime.format("yyyy-MM-dd")
                items["endTime"] = endItem?.endTime.format("yyyy-MM-dd")
                //计算还款月份数和天数
                c1.setTime(endItem.endTime)
                y1 = c1.get(Calendar.YEAR)
                m1 = c1.get(Calendar.MONTH)
                d1 = c1.get(Calendar.DAY_OF_MONTH)
                c1.setTime(startItem.startTime)
                y2 = c2.get(Calendar.YEAR)
                m2 = c2.get(Calendar.MONTH)
                d2 = c2.get(Calendar.DAY_OF_MONTH)
                monthes = (y1 - y2) * 12 + m1 - m2 - 1
                days = d1 - d2 + 31

                /*//dLast = (endDate.getTime().getTime()-endItem.endTime.getTime())/3600*1000*24
                dLast = 0
                if (dLast == 0){
                    monthes = opportunity?.actualLoanDuration
                    days = 0
                }else if (dLast>0){
                    monthes = opportunity?.actualLoanDuration - dLast/30
                    days = 30 - dLast%30
                }else {
                    monthes = opportunity?.actualLoanDuration + dLast/30
                    days = dLast%30
                }*/
                if (monthes)
                {
                    items["monthes"] = monthes
                }
                else
                {
                    items["monthes"] = ""
                }
                if (days)
                {
                    items["days"] = days
                }
                else
                {
                    items["days"] = ""
                }
                items["rate"] = rate1
                items["unit"] = "/月"
                if (fee)
                {
                    items["fee"] = fee
                }
                else
                {
                    items["fee"] = ""
                }
                receivable = opportunity?.parent?.actualLoanAmount * rate1 * (monthes + ((Double) days / 30)) * 100
                items["receivable"] = receivable
                BigDecimal serviceCharge = new BigDecimal(Double.toString(items["receivable"]))
                items["receivable"] = serviceCharge.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
                receivableList.add(items)
                receivables += items["receivable"]
            }
        }
        if (rate2 != 0)
        {
            startItem = BillsItem.findByBillsAndTypeAndStatusAndOverdueAndPrepaymentAndSplit(bills, BillsItemType.findByName("渠道返费费率"), "已收", false, false, false)
            items = [:]
            items["program"] = "渠道返费"
            items["radix"] = opportunity?.parent?.actualLoanAmount * 10000
            items["startTime"] = ""
            items["endTime"] = ""
            items["monthes"] = ""
            items["days"] = ""
            items["rate"] = rate2
            items["unit"] = "/笔"
            //fee = opportunity?.actualLoanAmount*rate2*100
            if (fee)
            {
                items["fee"] = fee
            }
            else
            {
                items["fee"] = ""
            }
            items["receivable"] = receivable = startItem?.receivable * 10000
            BigDecimal serviceCharge = new BigDecimal(Double.toString(items["receivable"]))
            items["receivable"] = serviceCharge.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
            receivableList.add(items)
            receivables += items["receivable"]
        }
        if (rate3 != 0)
        {
            startItem = BillsItem.findByBillsAndTypeAndStatusAndOverdueAndPrepaymentAndSplit(bills, BillsItemType.findByName("早偿违约金"), "已收", false, false, false)
            items = [:]
            items["program"] = "提前还款违约金"
            items["radix"] = opportunity?.parent?.actualLoanAmount * 10000
            items["startTime"] = ""
            items["endTime"] = ""
            items["monthes"] = ""
            items["days"] = ""
            items["rate"] = rate3
            items["unit"] = "/笔"
            //fee = opportunity?.actualLoanAmount * rate3 * 100
            if (fee)
            {
                items["fee"] = fee
            }
            else
            {
                items["fee"] = "-"
            }
            items["receivable"] = receivable = startItem?.receivable * 10000
            BigDecimal serviceCharge = new BigDecimal(Double.toString(items["receivable"]))
            items["receivable"] = serviceCharge.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
            receivableList.add(items)
            receivables += items["receivable"]
        }
        if (rate4 != 0)
        {
            endItem = BillsItem.findByBillsAndTypeAndStatusAndOverdueAndPrepaymentAndSplit(bills, BillsItemType.findByName("本金违约金"), "已收", false, false, false)
            items = [:]
            items["startTime"] = ""
            items["endTime"] = ""
            items["monthes"] = ""
            items["days"] = ""
            if (endItem)
            {
                items["startTime"] = endDate.getTime().format("yyyy-MM-dd")
                items["endTime"] = endItem.endTime.format("yyyy-MM-dd")
                dLast = (endItem.endTime.getTime() - endDate.getTime().getTime()) / (3600 * 1000 * 24)
                items["days"] = dLast
            }
            items["program"] = "本金逾期违约金"
            items["radix"] = opportunity?.parent?.actualLoanAmount * 10000
            items["rate"] = rate4
            items["unit"] = "/天"
            if (fee)
            {
                items["fee"] = fee
            }
            else
            {
                items["fee"] = ""
            }
            items["receivable"] = receivable = endItem?.receivable * 10000
            BigDecimal serviceCharge = new BigDecimal(Double.toString(items["receivable"]))
            items["receivable"] = serviceCharge.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
            receivableList.add(items)
            receivables += items["receivable"]
        }
        if (rate5 != 0)
        {
            def itemsAll = BillsItem.findAllByBillsAndTypeAndStatusAndOverdueAndPrepaymentAndSplitAndActualEndTimeGreaterThan(bills, BillsItemType.findByName("基本利率"), "已收", false, false, false, "startTime")
            itemsAll.each {
                def day = (it.actualEndTime.getTime() - it.startTime.getTime()) / (1000 * 3600 * 24)
                //处理收款日是星期天问题
                c1.setTime(it.actualEndTime)
                c2.setTime(it.startTime)
                def dayOfWeek1 = c2.get(java.util.Calendar.DAY_OF_WEEK)
                if (it.period == 0)
                {
                    if (dayOfWeek1 == 6)
                    {
                        if (day <= 3)
                        {
                            day = 0
                        }
                        else if (day > 3)
                        {
                            day -= 1
                        }
                    }
                    else if (dayOfWeek1 == 7)
                    {
                        if (day <= 2)
                        {
                            day = 0
                        }
                        else if (day > 2)
                        {
                            day -= 1
                        }
                    }
                    else
                    {
                        day -= 1
                    }
                }
                else
                {
                    if (day == 2)
                    {
                        if (dayOfWeek1 == 7)
                        {
                            day -= 2
                        }
                    }
                    else if (day == 1)
                    {
                        if (dayOfWeek1 == 1)
                        {
                            day -= 1
                        }
                    }
                }
                if (day > 0)
                {
                    items = [:]
                    items["program"] = "利息逾期违约金"
                    items["radix"] = opportunity?.parent?.actualLoanAmount * 10000
                    if (it.period == 0)
                    {
                        items["startTime"] = it.startTime + 1
                    }
                    else
                    {
                        items["startTime"] = it.startTime
                    }
                    items["endTime"] = it.actualEndTime
                    if (monthes)
                    {
                        items["monthes"] = monthes
                    }
                    else
                    {
                        items["monthes"] = ""
                    }
                    if (day)
                    {
                        items["days"] = day
                    }
                    else
                    {
                        items["days"] = ""
                    }
                    items["rate"] = rate5
                    items["unit"] = "/天"
                    if (fee)
                    {
                        items["fee"] = fee
                    }
                    else
                    {
                        items["fee"] = ""
                    }
                    items["receivable"] = receivable = it.receivable * 10000
                    BigDecimal serviceCharge = new BigDecimal(Double.toString(items["receivable"]))
                    items["receivable"] = serviceCharge.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
                    receivableList.add(items)
                    receivables += items["receivable"]
                }
            }
        }
        if (rate6 != 0)
        {
            startItem = BillsItem.findByBillsAndTypeAndStatusAndOverdueAndPrepaymentAndSplit(bills, BillsItemType.findByName("意向金"), "已收", false, false, false)
            items = [:]
            items["program"] = "意向金"
            items["radix"] = ""
            items["startTime"] = ""
            items["endTime"] = ""
            items["monthes"] = ""
            items["days"] = ""
            items["rate"] = ""
            items["unit"] = "/笔"
            //fee = opportunity?.actualLoanAmount * rate3 * 100
            if (fee)
            {
                items["fee"] = fee
            }
            else
            {
                items["fee"] = "-"
            }
            items["receivable"] = receivable = startItem.receivable * 10000
            BigDecimal serviceCharge = new BigDecimal(Double.toString(items["receivable"]))
            items["receivable"] = serviceCharge.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
            receivableList.add(items)
            receivables += items["receivable"]
        }
        //应收
        map["receivable"] = receivables
        def items1 = [:]
        def receiptsAll = 0
        def receiptDates = BillsItem.executeQuery("select DISTINCT actualEndTime from BillsItem where bills.id = ${bills?.id} and actualEndTime is not null order by actualEndTime")
        receiptDates.each {
            def billsItems = BillsItem.findAllByBillsAndActualEndTime(bills, it)
            items1 = [:]
            items1["fullName"] = opportunity?.fullName
            items1["repaymentDate"] = it.format("yyyy-MM-dd")
            items1["principal"] = 0
            items1["advance"] = 0 //意向金
            items1["interest"] = 0
            items1["serviceCharge"] = 0
            items1["channelCharge"] = 0
            items1["penalty"] = 0 //违约金
            items1["derate"] = 0 //减免金
            items1["receipts"] = 0
            billsItems.each { item ->
                type = item.type.name
                if (type != "服务费费率" && type != "渠道返费费率" && type != "早偿违约金" && type != "本金违约金" && type != "利息违约金" && type != "逾期本金利息" && type != "本金违约金减免金额" && type != "利息违约金减免金额" && type != "意向金")
                {
                    items1["interest"] += item.receipts * 10000
                }
                else if (type == "服务费费率")
                {
                    items1["serviceCharge"] += item.receipts * 10000
                }
                else if (type == "渠道返费费率")
                {
                    items1["channelCharge"] += item.receipts * 10000
                }
                else if (type == "早偿违约金" || type == "早偿违约金" || type == "早偿违约金")
                {
                    items1["penalty"] += item.receipts * 10000
                }
                else if (type == "本金")
                {
                    items1["principal"] += item.receipts * 10000
                }
                else if (type == "意向金")
                {
                    items1["advance"] += item.receipts * 10000
                }
                else
                {
                    items1["derate"] += item.receipts * 10000
                }
                items1["receipts"] += item.receipts * 10000
            }
            receiptsAll += items1["receipts"]
            receiptsList.add(items1)
        }
        //实收
        map["receipts"] = receiptsAll
        map["channel"] = OpportunityFlexField.findByCategoryAndName(OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity?.parent, FlexFieldCategory.findByName("资金渠道")), "放款通道")?.value
        respond opportunity, model: [map: map, receivableList: receivableList, receiptsList: receiptsList], view: "closingProofItems"
    }

    /**
     * @Description
     * @Author Nagelan
     * @Return
     * @DateTime 2017/9/19 0019 10:34
     */
    @Transactional
    def lmsToHuofh(){
        billsService.lmsToHuofh()
    }
}

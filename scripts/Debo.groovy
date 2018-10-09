package com.next

import org.springframework.security.access.annotation.Secured

@Secured(['permitAll'])
class TestController
{

    def sendEamilOfLoanlistService
    def timeoutHandlingService
    //condiction  交易记录已完成校验
    def a =
        { opportunity ->
            def flag = true
            def transactionRecords = com.next.TransactionRecord.findAllByOpportunity(opportunity.parent)
            println "transactionRecords+++" + transactionRecords
            transactionRecords.each {
                if (it.status.name != "已成功" && it.status.name != "已失效")
                {
                    flag = false
                }
            }
            return flag
        }
    //生成交易记录
    def aaa = { opportunity ->
        //判断是否已经生成交易记录
        if (com.next.TransactionRecord.findAllByOpportunity(opportunity)?.size() > 0)
        {
            return
        }
        //信托超额
        def isLimit = { lendingChannel1, aomunt, typename ->
            def b = true
            //超额
            def total = aomunt
            if (lendingChannel1 == "中航信托")
            {
                if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行")
                {
                    if (total > 500)
                    {
                        b = false
                    }
                }
                else if (typename == "民生银行" || typename == "工商银行" || typename == "农业银行")
                {
                    if (total > 50)
                    {
                        b = false
                    }
                }
                else if (typename == "光大银行")
                {
                    if (total > 25)
                    {
                        b = false
                    }
                }
                else
                {
                    b = false
                }
            }
            if (lendingChannel1 == "渤海一期")
            {
                if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行" || typename == "光大银行" || typename == "平安银行")
                {
                    if (total > 200)
                    {
                        b = false
                    }
                }
                else if (typename == "民生银行")
                {
                    if (total > 50)
                    {
                        b = false
                    }
                }
                else
                {
                    b = false
                }
            }
            if (lendingChannel1 == "渤海二期")
            {
                if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行" || typename == "光大银行" || typename == "平安银行")
                {
                    if (total > 100)
                    {
                        b = false
                    }
                }
                else if (typename == "民生银行")
                {
                    if (total > 50)
                    {
                        b = false
                    }
                }
                else
                {
                    b = false
                }
            }
            return b
        }

        //富有超额
        def isLimit2 = { aomunt, typename ->
            def b = true
            //超额
            def total = aomunt
            if (typename == "工商银行" || typename == "兴业银行" || typename == "招商银行" || typename == "农业银行" || typename == "交通银行" || typename == "建设银行")
            {
                if (total > 5)
                {
                    b = false
                }
                //5
            }
            else if (typename == "广发银行" || typename == "民生银行" || typename == "光大银行")
            {
                //50
                if (total > 50)
                {
                    b = false
                }
            }
            else if (typename == "上海浦东发展银行")
            {
                if (total > 4.9999)
                {
                    b = false
                }
            }


            return b
        }

        //得到还款计划
        def bills
        if (opportunity?.type.name == "抵押贷款")
        {
            bills = com.next.Bills.findByOpportunity(opportunity)
        }
        else
        {
            bills = com.next.Bills.findByOpportunity(opportunity?.parent)
            opportunity = opportunity?.parent
        }
        if (bills == null)
        {
            println "还款计划不存在！！！"
        }
        def bilsItems = com.next.BillsItem.findAllByBills(bills)
        println "还款计划数：" + bilsItems.size()
        def maxPeriod = bilsItems.period.max()
        //遍历还款计划  生成交易流水
        def transactionRecord
        bilsItems.each {
            transactionRecord = new com.next.TransactionRecord(opportunity: opportunity)
            //plannedStartTime
            if (opportunity.product.name == "融e贷" || opportunity.product.name == "等额本息")
            {
                if (it.period == 0)
                {
                    transactionRecord.plannedStartTime = it.startTime + 1
                }
                else
                {
                    transactionRecord.plannedStartTime = it.startTime - 2
                }
            }
            else
            {
                if (it.type.name == "本金" && it.period == maxPeriod)
                {
                    transactionRecord.plannedStartTime = it.endTime
                }
                else
                {
                    if (it.period == 0)
                    {
                        transactionRecord.plannedStartTime = it.startTime + 1
                    }
                    else
                    {
                        transactionRecord.plannedStartTime = it.startTime - 2
                    }
                }
            }

            //amount
            transactionRecord.amount = new java.math.BigDecimal(Double.toString(it.receivable)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
            //credit
            transactionRecord.credit = it.credit

            //status
            transactionRecord.status = com.next.TransactionRecordStatus.findByName("未执行")
            //type
            transactionRecord.type = com.next.TransactionType.findByName("还息")

            //关联 contact
            def type = com.next.OpportunityContactType.findByName("借款人")
            if (com.next.OpportunityContact.findByOpportunityAndType(opportunity, type))
            {
                transactionRecord.contact = com.next.OpportunityContact.findByOpportunityAndType(opportunity, type)?.contact
            }



            def feilvName = it.type.name
            //费率用于判断代扣的方式
            def productInterestType = com.next.OpportunityProduct.find("from OpportunityProduct where opportunity.id = ${opportunity.id}  and productInterestType.name = '${feilvName}'")

            //repaymentMethod
            //                    if (feilvName == "渠道返费费率" || feilvName == "服务费费率") {
            //                        transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('富友代扣')
            //                    } else if (feilvName == "本金" || feilvName == "基本费率") {
            //                        transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
            //                    } else if (feilvName == "早偿违约金" || feilvName == "本金违约金" || feilvName == "利息违约金" || feilvName == "本金违约金减免金额" || feilvName == "利息违约金减免金额") {
            //                        transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
            //                    } else {
            //                        if (productInterestType?.contractType?.name == "借款合同") {
            //                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
            //                        } else if (productInterestType?.contractType?.name == "委托借款代理服务合同") {
            //                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('富友代扣')
            //                        } else {
            //                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
            //                        }
            //                    }
            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
            def bankAccount
            def opportunityFlexFieldCategory1 = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
            if (opportunityFlexFieldCategory1)
            {
                def fields = com.next.OpportunityFlexField.findAllByCategory(opportunityFlexFieldCategory1)
                fields?.each {
                    if (it.name == '放款账号')
                    {
                        bankAccount = it.value
                    }
                }
            }
            //收款账号 debit
            if (transactionRecord.repaymentMethod.name == "富友代扣")
            {
                transactionRecord.debit = com.next.BankAccount.findByName("富友")
            }
            else
            {
                if (com.next.BankAccount.findByName(bankAccount))
                {
                    transactionRecord.debit = com.next.BankAccount.findByName(bankAccount)
                }

            }

            def item = com.next.BillsItem.findByBillsAndPeriodAndTransactionRecordIsNotNull(opportunity.bills, it.period)
            if (opportunity.product.name == "融e贷" || opportunity.product.name == "等额本息")
            {
                if (transactionRecord.amount != 0)
                {
                    if (item)
                    {
                        print "2"
                        item.transactionRecord.amount += transactionRecord.amount
                        item.transactionRecord.amount = new java.math.BigDecimal(Double.toString(item.transactionRecord.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        item.transactionRecord.save()
                        it.transactionRecord = item.transactionRecord
                        it.save()
                    }
                    else
                    {
                        print "1"
                        transactionRecord.save()
                        it.transactionRecord = transactionRecord
                        it.save()
                    }
                }
            }
            else
            {
                if (feilvName == "本金" && transactionRecord.amount != 0)
                {
                    print "1"
                    transactionRecord.save()
                    it.transactionRecord = transactionRecord
                    it.save()
                }
                else
                {
                    if (transactionRecord.amount != 0)
                    {
                        if (item)
                        {
                            print "2"
                            item.transactionRecord.amount += transactionRecord.amount
                            item.transactionRecord.amount = new java.math.BigDecimal(Double.toString(item.transactionRecord.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            item.transactionRecord.save()
                            it.transactionRecord = item.transactionRecord
                            it.save()
                        }
                        else
                        {
                            print "1"
                            transactionRecord.save()
                            it.transactionRecord = transactionRecord
                            it.save()
                        }
                    }
                }
            }

        }


        def recods = com.next.TransactionRecord.findAllByOpportunity(opportunity)
        recods.each {
            def record = it
            //每条记录
            def bankAccount
            //账号类型
            def opportunityFlexFieldCategory1 = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
            if (opportunityFlexFieldCategory1)
            {
                def fields = com.next.OpportunityFlexField.findAllByCategory(opportunityFlexFieldCategory1)
                fields?.each {
                    if (it.name == '放款账号')
                    {
                        bankAccount = it.value
                    }
                }
            }

            def bankName
            //银行类型名称
            def bankAccounts = com.next.OpportunityBankAccount.findAllByOpportunity(opportunity)
            bankAccounts.each {
                String BankAccountType = it.type.name
                if (BankAccountType.contains("还款"))
                {
                    bankName = it.bankAccount.bank.name
                }
            }
            def lendingChannel
            def opportunityFlexFieldCategory = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
            if (opportunityFlexFieldCategory)
            {
                def fields = com.next.OpportunityFlexField.findAllByCategory(opportunityFlexFieldCategory)
                fields?.each {
                    if (it.name == '放款通道')
                    {
                        if (it.value == '中航信托')
                        {
                            lendingChannel = "中航信托"
                            //中航信托
                        }
                    }
                    if (it.name == '放款账号')
                    {
                        if (it.value == '渤海264')
                        {
                            lendingChannel = "渤海一期"
                            //渤海一期
                        }
                        if (it.value == '渤海295')
                        {
                            lendingChannel = "渤海二期"
                            //渤海而期
                        }
                    }
                }
            }
            if (record.repaymentMethod.name == "广银联代扣")
            {
                if (lendingChannel)
                {
                    def b = isLimit(lendingChannel, record.amount, bankName)
                    if (!b)
                    {
                        println "超额"
                        record.repaymentMethod = com.next.RepaymentMethod.findByName('自主还款')
                    }
                }

            }
            it.status = com.next.TransactionRecordStatus.findByName("未执行")
            it.save()
        }

    }
    // 定时任务  每日生成利息违约金减免当期
    def c = {
        def currentTime = new java.util.Date()
        def billsIteams = com.next.BillsItem.findAllByCreatedTimeBetween(currentTime - 2, currentTime)
        billsIteams.each {
            if (it.type.name == '利息违约金' || it.type.name == '利息违约金减免金额')
            {
                def date
                if (it.period == 0)
                {
                    date = it.startTime + 1
                }
                else
                {
                    date = it.startTime - 2
                }
                String date1 = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date)

                def transactionRecord = com.next.TransactionRecord.find("from TransactionRecord where opportunity.id = ${it.bills.opportunity.id} and  Convert(varchar,plannedStartTime,120)  like '${date1}%' and repaymentMethod.name in ('广银联代扣','自主还款') and status.name in ('未执行','扣款失败')")

                println transactionRecord
                if (!it.transactionRecord && transactionRecord)
                {
                    transactionRecord.amount += it.receivable
                    transactionRecord.amount = new java.math.BigDecimal(Double.toString(transactionRecord.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                    transactionRecord.save()
                    it.transactionRecord = transactionRecord
                    it.save()
                }
            }
            //            it.bills.opportunity.createdDate > new Date(year: 2017, month: 7, date: 11, hours: 00, minutes: 00, seconds: 00)

            // date = date.getYear()+"-"+date.getMonth()+"-"+date.getDate()

        }
    }

    //拆分
    def split =
        { opportunity ->
            def isLimit = { lendingChannel1, aomunt, typename ->
                def b = true
                //超额
                def total = aomunt
                if (lendingChannel1 == "中航信托")
                {
                    if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行")
                    {
                        if (total > 500)
                        {
                            b = false
                        }
                    }
                    else if (typename == "民生银行" || typename == "工商银行" || typename == "农业银行")
                    {
                        if (total > 50)
                        {
                            b = false
                        }
                    }
                    else if (typename == "光大银行")
                    {
                        if (total > 25)
                        {
                            b = false
                        }
                    }
                    else
                    {
                        b = false
                    }
                }
                if (lendingChannel1 == "渤海一期")
                {
                    if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行" || typename == "光大银行" || typename == "平安银行")
                    {
                        if (total > 200)
                        {
                            b = false
                        }
                    }
                    else if (typename == "民生银行")
                    {
                        if (total > 50)
                        {
                            b = false
                        }
                    }
                    else
                    {
                        b = false
                    }
                }
                if (lendingChannel1 == "渤海二期")
                {
                    if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行" || typename == "光大银行" || typename == "平安银行")
                    {
                        if (total > 100)
                        {
                            b = false
                        }
                    }
                    else if (typename == "民生银行")
                    {
                        if (total > 50)
                        {
                            b = false
                        }
                    }
                    else
                    {
                        b = false
                    }
                }
                return b
            }
            def billsItems = com.next.BillsItem.findAllByBills(opportunity.bills)
            if (billsItems.size() == 0)
            {
                billsItems = com.next.BillsItem.findAllByBills(opportunity.parent.bills)
            }
            println billsItems
            def maxPeriod = billsItems.period.max()
            billsItems.each {
                if (it.split && it.transactionRecord)
                {
                    it.transactionRecord.status = com.next.TransactionRecordStatus.findByName("已失效")
                    it.transactionRecord.save()
                }
                else
                {
                    if (!it.transactionRecord && it.status == "待收" && it.receivable != 0)
                    {
                        def date
                        if (opportunity.product.name == "融e贷" || opportunity.product.name == "等额本息")
                        {
                            if (it.period == 0)
                            {
                                date = it.startTime + 1
                            }
                            else
                            {
                                date = it.startTime - 2
                            }
                        }
                        else
                        {
                            if (it.type.name == "本金" && it.period == maxPeriod)
                            {
                                date = it.endTime
                            }
                            else
                            {
                                if (it.period == 0)
                                {
                                    date = it.startTime + 1
                                }
                                else
                                {
                                    date = it.startTime - 2
                                }
                            }
                        }

                        def transactionRecord = new com.next.TransactionRecord(opportunity: it.bills.opportunity)
                        //plannedStartTime
                        transactionRecord.plannedStartTime = date
                        //amount
                        transactionRecord.amount = new java.math.BigDecimal(Double.toString(it.receivable)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        //credit
                        transactionRecord.credit = it.credit

                        //status
                        transactionRecord.status = com.next.TransactionRecordStatus.findByName("未执行")
                        //type
                        transactionRecord.type = com.next.TransactionType.findByName("还本")

                        //关联 contact
                        def type = com.next.OpportunityContactType.findByName("借款人")
                        transactionRecord.contact = com.next.OpportunityContact.findByOpportunityAndType(it.bills.opportunity, type).contact


                        def bankName
                        //银行类型名称
                        opportunity.bankAccounts.each {
                            String BankAccountType = it.type.name
                            if (BankAccountType.contains("还款"))
                            {
                                bankName = it.bankAccount.bank.name
                            }
                        }
                        def lendingChannel
                        def opportunityFlexFieldCategory = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.bills.opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
                        if (opportunityFlexFieldCategory)
                        {
                            opportunityFlexFieldCategory?.fields?.each {
                                if (it.name == '放款通道')
                                {
                                    if (it.value == '中航信托')
                                    {
                                        lendingChannel = "中航信托"
                                        //中航信托
                                    }
                                }
                                if (it.name == '放款账号')
                                {
                                    if (it.value == '渤海264')
                                    {
                                        lendingChannel = "渤海一期"
                                        //渤海一期
                                    }
                                    if (it.value == '渤海295')
                                    {
                                        lendingChannel = "渤海二期"
                                        //渤海而期
                                    }
                                }
                            }
                        }
                        def b = isLimit(lendingChannel, it.receivable, bankName)
                        if (!b)
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('自主还款')
                        }
                        else
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
                        }

                        //收款账号 debit
                        def bankAccount
                        //放款账户
                        def opportunityFlexFieldCategory1 = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.bills.opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
                        if (opportunityFlexFieldCategory1)
                        {
                            opportunityFlexFieldCategory1?.fields?.each {
                                if (it.name == '放款账号')
                                {
                                    bankAccount = it.value
                                }
                            }
                        }

                        if (com.next.BankAccount.findByName(bankAccount))
                        {
                            transactionRecord.debit = com.next.BankAccount.findByName(bankAccount)
                        }
                        transactionRecord.save()
                        it.transactionRecord = transactionRecord
                        it.save()
                    }
                }

            }
        }
    //早偿，逾期
    def prepayment = { opportunity ->
        def isLimit = { lendingChannel1, aomunt, typename ->
            def b = true
            //超额
            def total = aomunt
            if (lendingChannel1 == "中航信托")
            {
                if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行")
                {
                    if (total > 500)
                    {
                        b = false
                    }
                }
                else if (typename == "民生银行" || typename == "工商银行" || typename == "农业银行")
                {
                    if (total > 50)
                    {
                        b = false
                    }
                }
                else if (typename == "光大银行")
                {
                    if (total > 25)
                    {
                        b = false
                    }
                }
                else
                {
                    b = false
                }
            }
            if (lendingChannel1 == "渤海一期")
            {
                if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行" || typename == "光大银行" || typename == "平安银行")
                {
                    if (total > 200)
                    {
                        b = false
                    }
                }
                else if (typename == "民生银行")
                {
                    if (total > 50)
                    {
                        b = false
                    }
                }
                else
                {
                    b = false
                }
            }
            if (lendingChannel1 == "渤海二期")
            {
                if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行" || typename == "光大银行" || typename == "平安银行")
                {
                    if (total > 100)
                    {
                        b = false
                    }
                }
                else if (typename == "民生银行")
                {
                    if (total > 50)
                    {
                        b = false
                    }
                }
                else
                {
                    b = false
                }
            }
            return b
        }
        def billsItems = com.next.BillsItem.findAllByBills(opportunity?.parent?.bills)
        billsItems.each {
            if ((it.prepayment || it.overdue) && it.transactionRecord)
            {
                it.transactionRecord.status = com.next.TransactionRecordStatus.findByName("已失效")
                it.transactionRecord.save()
            }
            else
            {
                def bankName
                //银行类型名称
                opportunity.bankAccounts.each {
                    String BankAccountType = it.type.name
                    if (BankAccountType.contains("还款"))
                    {
                        bankName = it.bankAccount.bank.name
                    }
                }
                def lendingChannel
                def opportunityFlexFieldCategory = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.bills.opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
                if (opportunityFlexFieldCategory)
                {
                    opportunityFlexFieldCategory?.fields?.each {
                        if (it.name == '放款通道')
                        {
                            if (it.value == '中航信托')
                            {
                                lendingChannel = "中航信托"
                                //中航信托
                            }
                        }
                        if (it.name == '放款账号')
                        {
                            if (it.value == '渤海264')
                            {
                                lendingChannel = "渤海一期"
                                //渤海一期
                            }
                            if (it.value == '渤海295')
                            {
                                lendingChannel = "渤海二期"
                                //渤海而期
                            }
                        }
                    }
                }
                if (!it.transactionRecord && it.status == "待收" && it.receivable != 0)
                {
                    if (it.type.name == "本金" || it.type.name == "利息违约金")
                    {
                        def date = it.endTime
                    }
                    else
                    {
                        def date = it.startTime
                    }

                    def transactionRecord = com.next.TransactionRecord.findByOpportunityAndPlannedStartTimeAndStatus(it.bills.opportunity, date, com.next.TransactionRecordStatus.findByName("未执行"))
                    if (transactionRecord)
                    {
                        transactionRecord.amount += it.receivable
                        transactionRecord.amount = new java.math.BigDecimal(Double.toString(transactionRecord.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        def b = isLimit(lendingChannel, transactionRecord.amount, bankName)
                        if (!b)
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('自主还款')
                        }
                        else
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
                        }
                        transactionRecord.save()
                        it.transactionRecord = transactionRecord
                        it.save()
                    }
                    else
                    {
                        transactionRecord = new com.next.TransactionRecord(opportunity: it.bills.opportunity)

                        //plannedStartTime
                        transactionRecord.plannedStartTime = date
                        //amount
                        transactionRecord.amount = new java.math.BigDecimal(Double.toString(it.receivable)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        //credit
                        transactionRecord.credit = it.credit

                        //status
                        transactionRecord.status = com.next.TransactionRecordStatus.findByName("未执行")
                        //type
                        transactionRecord.type = com.next.TransactionType.findByName("还息")

                        //关联 contact
                        def type = com.next.OpportunityContactType.findByName("借款人")
                        transactionRecord.contact = com.next.OpportunityContact.findByOpportunityAndType(it.bills.opportunity, type).contact



                        def b = isLimit(lendingChannel, it.receivable, bankName)
                        if (!b)
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('自主还款')
                        }
                        else
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
                        }

                        //收款账号 debit
                        def bankAccount
                        //放款账户
                        def opportunityFlexFieldCategory1 = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.bills.opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
                        if (opportunityFlexFieldCategory1)
                        {
                            opportunityFlexFieldCategory1?.fields?.each {
                                if (it.name == '放款账号')
                                {
                                    bankAccount = it.value
                                }
                            }
                        }
                        if (com.next.BankAccount.findByName(bankAccount))
                        {
                            transactionRecord.debit = com.next.BankAccount.findByName(bankAccount)
                        }
                        transactionRecord.save()
                        it.transactionRecord = transactionRecord
                        it.save()
                    }

                }
            }

        }
    }

    //逾期   一般是说本金逾期
    def overdue = { opportunity ->
        def isLimit = { lendingChannel1, aomunt, typename ->
            def b = true
            //超额
            def total = aomunt
            if (lendingChannel1 == "中航信托")
            {
                if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行")
                {
                    if (total > 500)
                    {
                        b = false
                    }
                }
                else if (typename == "民生银行" || typename == "工商银行" || typename == "农业银行")
                {
                    if (total > 50)
                    {
                        b = false
                    }
                }
                else if (typename == "光大银行")
                {
                    if (total > 25)
                    {
                        b = false
                    }
                }
                else
                {
                    b = false
                }
            }
            if (lendingChannel1 == "渤海一期")
            {
                if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行" || typename == "光大银行" || typename == "平安银行")
                {
                    if (total > 200)
                    {
                        b = false
                    }
                }
                else if (typename == "民生银行")
                {
                    if (total > 50)
                    {
                        b = false
                    }
                }
                else
                {
                    b = false
                }
            }
            if (lendingChannel1 == "渤海二期")
            {
                if (typename == "招商银行" || typename == "广发银行" || typename == "兴业银行" || typename == "光大银行" || typename == "平安银行")
                {
                    if (total > 100)
                    {
                        b = false
                    }
                }
                else if (typename == "民生银行")
                {
                    if (total > 50)
                    {
                        b = false
                    }
                }
                else
                {
                    b = false
                }
            }
            return b
        }
        def billsItems = com.next.BillsItem.findAllByBills(com.next.Bills.findByOpportunity(opportunity.parent.bills))
        billsItems.each {
            if (it.overdue && it.transactionRecord)
            {
                it.transactionRecord.status = com.next.TransactionRecordStatus.findByName("已失效")
                it.transactionRecord.save()
            }
            else
            {
                def bankName
                //银行类型名称
                opportunity.bankAccounts.each {
                    String BankAccountType = it.type.name
                    if (BankAccountType.contains("还款"))
                    {
                        bankName = it.bankAccount.bank.name
                    }
                }
                def lendingChannel
                def opportunityFlexFieldCategory = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.bills.opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
                if (opportunityFlexFieldCategory)
                {
                    opportunityFlexFieldCategory?.fields?.each {
                        if (it.name == '放款通道')
                        {
                            if (it.value == '中航信托')
                            {
                                lendingChannel = "中航信托"
                                //中航信托
                            }
                        }
                        if (it.name == '放款账号')
                        {
                            if (it.value == '渤海264')
                            {
                                lendingChannel = "渤海一期"
                                //渤海一期
                            }
                            if (it.value == '渤海295')
                            {
                                lendingChannel = "渤海二期"
                                //渤海而期
                            }
                        }
                    }
                }
                if (!it.transactionRecord && it.status == "待收" && it.receivable != 0)
                {
                    def date = it.startTime
                    def transactionRecord = com.next.TransactionRecord.findByOpportunityAndPlannedStartTimeAndStatus(it.bills.opportunity, date, com.next.TransactionRecordStatus.findByName("未执行"))
                    if (transactionRecord)
                    {
                        transactionRecord.amount += it.receivable
                        transactionRecord.amount = new java.math.BigDecimal(Double.toString(transactionRecord.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        def b = isLimit(lendingChannel, transactionRecord.amount, bankName)
                        if (!b)
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('自主还款')
                        }
                        else
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
                        }
                        transactionRecord.save()
                        it.transactionRecord = transactionRecord
                        it.save()
                    }
                    else
                    {
                        transactionRecord = new com.next.TransactionRecord(opportunity: it.bills.opportunity)
                        //plannedStartTime
                        transactionRecord.plannedStartTime = date
                        //amount
                        transactionRecord.amount = new java.math.BigDecimal(Double.toString(it.receivable)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        //credit
                        transactionRecord.credit = it.credit

                        //status
                        transactionRecord.status = com.next.TransactionRecordStatus.findByName("未执行")
                        //type
                        transactionRecord.type = com.next.TransactionType.findByName("还息")

                        //关联 contact
                        def type = com.next.OpportunityContactType.findByName("借款人")
                        transactionRecord.contact = com.next.OpportunityContact.findByOpportunityAndType(it.bills.opportunity, type).contact



                        def b = isLimit(lendingChannel, it.receivable, bankName)
                        if (!b)
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('自主还款')
                        }
                        else
                        {
                            transactionRecord.repaymentMethod = com.next.RepaymentMethod.findByName('广银联代扣')
                        }

                        //收款账号 debit
                        def bankAccount
                        //放款账户
                        def opportunityFlexFieldCategory1 = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.bills.opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
                        if (opportunityFlexFieldCategory1)
                        {
                            opportunityFlexFieldCategory1?.fields?.each {
                                if (it.name == '放款账号')
                                {
                                    bankAccount = it.value
                                }
                            }
                        }

                        if (com.next.BankAccount.findByName(bankAccount))
                        {
                            transactionRecord.debit = com.next.BankAccount.findByName(bankAccount)
                        }
                        transactionRecord.save()
                        it.transactionRecord = transactionRecord
                        it.save()
                    }

                }
            }

        }
    }

    //本金违约金加到最后一期
    def d = { opportunity ->
        def billsItems = com.next.BillsItem.findAllByBills(opportunity?.parent?.bills)
        billsItems.each {
            if (it.type.name == "本金违约金" && !it.transactionRecord && it.status == "待收")
            {
                def billsItem = com.next.BillsItem.find("from BillsItem  where bills.id = ${it.bills.id} and type.name = '本金' and receivable > 0 ")
                println "找到本金" + billsItem
                billsItem.transactionRecord.amount += it.receivable
                billsItem.transactionRecord.amount = new java.math.BigDecimal(Double.toString(billsItem.transactionRecord.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                billsItem.save()
                it.transactionRecord = billsItem.transactionRecord
                it.save()
            }
        }
        //校验违约金
        def benjin = com.next.BillsItem.findByBillsAndTypeAndStatus(opportunity?.parent?.bills, com.next.BillsItemType.findByName("本金"), "待收")
        def weiyue = com.next.BillsItem.findAllByBillsAndTypeAndStatus(opportunity?.parent?.bills, com.next.BillsItemType.findByName("利息违约金"), "待收")?.receivable?.sum()
        if (benjin && weiyue)
        {
            benjin?.transactionRecord?.amount = weiyue + benjin?.receivable
            benjin.save()
        }
    }

    //本金减免加到最后一期
    def e = { opportunity ->

        def billsItems = com.next.BillsItem.findAllByBills(opportunity?.parent?.bills)

        billsItems.each {
            if ((it.type.name == "利息违约金减免金额" || it.type.name == "本金违约金减免金额") && !it.transactionRecord && it.status == "待收")
            {
                def billsItem = com.next.BillsItem.find("from BillsItem  where bills.id = ${it.bills.id} and type.name = '本金' and receivable > 0 and prepayment = 0 and overdue =  0 and split = 0  ")
                println "找到本金" + billsItem
                billsItem.transactionRecord.amount += it.receivable
                billsItem.transactionRecord.amount = new java.math.BigDecimal(Double.toString(billsItem.transactionRecord.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                billsItem.save()
                it.transactionRecord = billsItem.transactionRecord
                it.save()
            }
        }
    }

    //本金合并交易记录
    def f = { opportunity ->
        def billsItems = com.next.BillsItem.findByBillsAndTypeAndReceivableGreaterThan(opportunity?.parent?.bills, com.next.BillsItemType.findByName("本金"), 0)
        def billsItems1 = com.next.BillsItem.findAllByBillsAndPeriodAndTypeNotEqual(opportunity?.parent?.bills, billsItems.period, com.next.BillsItemType.findByName("本金"))
        billsItems.transactionRecord.amount += billsItems1[0].transactionRecord.amount
        billsItems.save()
        billsItems1[0].transactionRecord.status = com.next.TransactionRecordStatus.findByName("已失效")
        billsItems1.each {
            it.transactionRecord = billsItems.transactionRecord
            it.save()
        }

    }

    //验卡  生产环境
    def CheckCard = { opportunity ->
        def flag = true
        if (opportunity?.bankAccounts?.size() > 1)
        {
            opportunity?.bankAccounts?.each {
                /*java.util.regex.Pattern p = java.util.regex.Pattern.compile("/\\d{11}/");
                java.util.regex.Matcher m = p.matcher(it.bankAccount.cellphone)*/
                if (!it.bankAccount.cellphone)
                {
                    flag = false
                }
                if (!it.bankAccount.numberOfCertificate)
                {
                    flag = false
                }
            }
            if (flag)
            {
                def encode = { String origin, String charsetname ->

                    String resultString = null;
                    resultString = new java.lang.String(origin);
                    java.security.MessageDigest md;
                    try
                    {
                        md = java.security.MessageDigest.getInstance("MD5");
                    }
                    catch (java.security.NoSuchAlgorithmException e)
                    {
                        throw new java.lang.RuntimeException(e);
                    }
                    if (charsetname == null || "".equals(charsetname))
                    {
                        resultString = org.apache.commons.codec.binary.Hex.encodeHexString(md.digest(resultString.getBytes()));
                    }
                    else
                    {
                        try
                        {
                            resultString = org.apache.commons.codec.binary.Hex.encodeHexString(md.digest(resultString.getBytes(charsetname)));
                        }
                        catch (java.io.UnsupportedEncodingException e)
                        {
                            throw new java.lang.RuntimeException(e);
                        }
                    }
                    return resultString;
                }
                def requestPost = { url, params ->
                    URL urlString = new java.net.URL(url)
                    println url
                    java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) urlString.openConnection()
                    urlConnection.setRequestMethod('POST')
                    urlConnection.setDoOutput(true)
                    urlConnection.setReadTimeout(10000)
                    urlConnection.outputStream.withWriter("UTF-8") { Writer writer -> writer.write params }
                    def responeResult = urlConnection.inputStream.withReader("UTF-8") { Reader reader -> reader.text }
                    return responeResult
                }
                def url = "https://mpay.fuiou.com:16128/checkCard/checkCard01.pay"
                def merchantNo = "0001000F0397137"
                def key = "jvmda9d2sg2tgfjlagojwaty9vlfy32t"
                def version = "1.30"
                def cardType = "0"
                def opportunityBankAccount = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("还款账号"))
                def opportunityBankAccount1 = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("收款账号"))
                if (opportunityBankAccount?.bankAccount?.numberOfAccount == opportunityBankAccount1?.bankAccount?.numberOfAccount && opportunityBankAccount?.bankAccount?.active)
                {
                    def serialNumber = new Date().getTime()
                    def fullName = opportunityBankAccount?.bankAccount?.name
                    def cardNo = opportunityBankAccount?.bankAccount?.numberOfAccount
                    def cellphone = opportunityBankAccount?.bankAccount?.cellphone
                    def idNumber = opportunityBankAccount?.bankAccount?.numberOfCertificate
                    def macSource = merchantNo + "|" + version + "|" + serialNumber + "|" + cardNo + "|" + cardType + "|" + idNumber + "|" + key
                    println macSource
                    def signature = encode(macSource.toString(), "UTF-8")
                    def xml = "<FM>" + "<MchntCd>" + merchantNo + "</MchntCd>\n" + "<Ono>" + cardNo + "</Ono>\n" + "<Onm>" + fullName + "</Onm>\n" + "<OCerTp>" + cardType + "</OCerTp>\n" + "<OCerNo>" + idNumber + "</OCerNo>\n" + "<Mno>" + cellphone + "</Mno>\n" + "<Sign>" + signature + "</Sign>\n" + "<Ver>" + version + "</Ver>\n" + "<OSsn>" + serialNumber + "</OSsn>\n" + "</FM>"
                    println xml
                    def result = requestPost(url, "FM=" + xml)
                    def rootNode = new groovy.util.XmlParser().parseText(result)
                    def Rcd = rootNode.Rcd.text()
                    def RDesc = rootNode.RDesc.text()
                    def OSsn = rootNode.OSsn.text()
                    def CardNo = rootNode.CardNo.text()
                    def MchntCd = rootNode.MchntCd.text()
                    def Ver = rootNode.Ver.text()
                    def Sign = rootNode.Sign.text()
                    println Sign
                    def signSource = Rcd + "|" + OSsn + "|" + CardNo + "|" + MchntCd + "|" + Ver + "|" + key
                    signSource = encode(signSource.toString(), "UTF-8")
                    println signSource
                    def map = [:]
                    if (Sign == signSource)
                    {
                        if (Rcd == "0000")
                        {
                            opportunityBankAccount.bankAccount.active = false
                            opportunityBankAccount.save()
                            opportunityBankAccount1.bankAccount.active = false
                            opportunityBankAccount1.save()
                            map.put("flag", true)
                            map.put("memo", RDesc)
                        }
                        else
                        {
                            map.put("flag", false)
                            map.put("memo", RDesc)
                        }
                    }
                    else
                    {
                        map.put("flag", false)
                        map.put("memo", "验签失败")
                    }
                    return map
                }
                else
                {
                    def opportunityBankAccounts = com.next.OpportunityBankAccount.findAllByOpportunity(opportunity)
                    def map = [:]
                    map.put("flag", true)
                    map.put("memo", "该银行卡已通过验证")
                    for (
                        it in
                            opportunityBankAccounts)
                    {
                        if (it.bankAccount.active)
                        {
                            def serialNumber = new Date().getTime()
                            def fullName = it?.bankAccount?.name
                            def cardNo = it?.bankAccount?.numberOfAccount
                            def cellphone = it?.bankAccount?.cellphone
                            def idNumber = it?.bankAccount?.numberOfCertificate
                            def macSource = merchantNo + "|" + version + "|" + serialNumber + "|" + cardNo + "|" + cardType + "|" + idNumber + "|" + key
                            println macSource
                            def signature = encode(macSource.toString(), "UTF-8")
                            def xml = "<FM>" + "<MchntCd>" + merchantNo + "</MchntCd>\n" + "<Ono>" + cardNo + "</Ono>\n" + "<Onm>" + fullName + "</Onm>\n" + "<OCerTp>" + cardType + "</OCerTp>\n" + "<OCerNo>" + idNumber + "</OCerNo>\n" + "<Mno>" + cellphone + "</Mno>\n" + "<Sign>" + signature + "</Sign>\n" + "<Ver>" + version + "</Ver>\n" + "<OSsn>" + serialNumber + "</OSsn>\n" + "</FM>"
                            println xml
                            def result = requestPost(url, "FM=" + xml)
                            def rootNode = new groovy.util.XmlParser().parseText(result)
                            def Rcd = rootNode.Rcd.text()
                            def RDesc = rootNode.RDesc.text()
                            def OSsn = rootNode.OSsn.text()
                            def CardNo = rootNode.CardNo.text()
                            def MchntCd = rootNode.MchntCd.text()
                            def Ver = rootNode.Ver.text()
                            def Sign = rootNode.Sign.text()
                            println Sign
                            def signSource = Rcd + "|" + OSsn + "|" + CardNo + "|" + MchntCd + "|" + Ver + "|" + key
                            signSource = encode(signSource.toString(), "UTF-8")
                            println signSource
                            if (Sign == signSource)
                            {
                                if (Rcd == "0000")
                                {
                                    it.bankAccount.active = false
                                    it.save()
                                    map.put("flag", true)
                                    map.put("memo", RDesc)
                                }
                                else
                                {
                                    map.put("flag", false)
                                    map.put("memo", RDesc)
                                    map.put("account", it.type.name)
                                }
                            }
                            else
                            {
                                map.put("flag", false)
                                map.put("memo", "验签失败")
                                map.put("account", it.type.name)
                            }
                            if (map.get("flag") == false)
                            {
                                break
                            }
                        }

                    }
                    return map
                }
            }
            else
            {
                def map = [:]
                map.put("flag", false)
                map.put("memo", "银行卡预留手机号或者身份证号未填写")
                return map
            }
        }
        else
        {
            def map = [:]
            map.put("flag", false)
            map.put("memo", "收款账号还款账号未填写")
            return map
        }

    }

    //验卡  测试环境
    def CheckCardtest = { opportunity ->
        def flag = true
        if (opportunity?.bankAccounts?.size() > 1)
        {
            opportunity?.bankAccounts?.each {
                /*java.util.regex.Pattern p = java.util.regex.Pattern.compile("/\\d{11}/");
                java.util.regex.Matcher m = p.matcher(it.bankAccount.cellphone)*/
                if (!it.bankAccount.cellphone)
                {
                    flag = false
                }
                if (!it.bankAccount.numberOfCertificate)
                {
                    flag = false
                }
            }
            if (flag)
            {
                def encode = { String origin, String charsetname ->

                    String resultString = null;
                    resultString = new java.lang.String(origin);
                    java.security.MessageDigest md;
                    try
                    {
                        md = java.security.MessageDigest.getInstance("MD5");
                    }
                    catch (java.security.NoSuchAlgorithmException e)
                    {
                        throw new java.lang.RuntimeException(e);
                    }
                    if (charsetname == null || "".equals(charsetname))
                    {
                        resultString = org.apache.commons.codec.binary.Hex.encodeHexString(md.digest(resultString.getBytes()));
                    }
                    else
                    {
                        try
                        {
                            resultString = org.apache.commons.codec.binary.Hex.encodeHexString(md.digest(resultString.getBytes(charsetname)));
                        }
                        catch (java.io.UnsupportedEncodingException e)
                        {
                            throw new java.lang.RuntimeException(e);
                        }
                    }
                    return resultString;
                }
                def requestPost = { url, params ->
                    URL urlString = new java.net.URL(url)
                    println url
                    java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) urlString.openConnection()
                    urlConnection.setRequestMethod('POST')
                    urlConnection.setDoOutput(true)
                    urlConnection.setReadTimeout(10000)
                    urlConnection.outputStream.withWriter("UTF-8") { Writer writer -> writer.write params }
                    def responeResult = urlConnection.inputStream.withReader("UTF-8") { Reader reader -> reader.text }
                    return responeResult
                }
                def merchantNo = "0002900F0096235"
                def version = "1.30"
                def key = "5old71wihg2tqjug9kkpxnhx9hiujoqj"
                def cardType = "0"
                def url = "http://www-1.fuiou.com:18670/mobile_pay/checkCard/checkCard01.pay"
                def opportunityBankAccount = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("还款账号"))
                def opportunityBankAccount1 = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("收款账号"))
                if (opportunityBankAccount?.bankAccount?.numberOfAccount == opportunityBankAccount1?.bankAccount?.numberOfAccount)
                {
                    def serialNumber = new Date().getTime()
                    def fullName = opportunityBankAccount?.bankAccount?.name
                    def cardNo = opportunityBankAccount?.bankAccount?.numberOfAccount
                    def cellphone = opportunityBankAccount?.bankAccount?.cellphone
                    def idNumber = opportunityBankAccount?.bankAccount?.numberOfCertificate
                    def macSource = merchantNo + "|" + version + "|" + serialNumber + "|" + cardNo + "|" + cardType + "|" + idNumber + "|" + key
                    println macSource
                    def signature = encode(macSource.toString(), "UTF-8")
                    def xml = "<FM>" + "<MchntCd>" + merchantNo + "</MchntCd>\n" + "<Ono>" + cardNo + "</Ono>\n" + "<Onm>" + fullName + "</Onm>\n" + "<OCerTp>" + cardType + "</OCerTp>\n" + "<OCerNo>" + idNumber + "</OCerNo>\n" + "<Mno>" + cellphone + "</Mno>\n" + "<Sign>" + signature + "</Sign>\n" + "<Ver>" + version + "</Ver>\n" + "<OSsn>" + serialNumber + "</OSsn>\n" + "</FM>"
                    println xml
                    def result = requestPost(url, "FM=" + xml)
                    def rootNode = new groovy.util.XmlParser().parseText(result)
                    def Rcd = rootNode.Rcd.text()
                    def RDesc = rootNode.RDesc.text()
                    def OSsn = rootNode.OSsn.text()
                    def CardNo = rootNode.CardNo.text()
                    def MchntCd = rootNode.MchntCd.text()
                    def Ver = rootNode.Ver.text()
                    def Sign = rootNode.Sign.text()
                    println Sign
                    def signSource = Rcd + "|" + OSsn + "|" + CardNo + "|" + MchntCd + "|" + Ver + "|" + key
                    signSource = encode(signSource.toString(), "UTF-8")
                    println signSource
                    def map = [:]
                    if (Sign == signSource)
                    {
                        if (Rcd == "0000")
                        {
                            map.put("flag", true)
                            map.put("memo", RDesc)
                        }
                        else
                        {
                            map.put("flag", false)
                            map.put("memo", RDesc)
                        }
                    }
                    else
                    {
                        map.put("flag", false)
                        map.put("memo", "验签失败")
                    }
                    return map
                }
                else
                {
                    def opportunityBankAccounts = com.next.OpportunityBankAccount.findAllByOpportunity(opportunity)
                    def map = [:]
                    for (
                        it in
                            opportunityBankAccounts)
                    {
                        def serialNumber = new Date().getTime()
                        def fullName = it?.bankAccount?.name
                        def cardNo = it?.bankAccount?.numberOfAccount
                        def cellphone = it?.bankAccount?.cellphone
                        def idNumber = it?.bankAccount?.numberOfCertificate
                        def macSource = merchantNo + "|" + version + "|" + serialNumber + "|" + cardNo + "|" + cardType + "|" + idNumber + "|" + key
                        println macSource
                        def signature = encode(macSource.toString(), "UTF-8")
                        def xml = "<FM>" + "<MchntCd>" + merchantNo + "</MchntCd>\n" + "<Ono>" + cardNo + "</Ono>\n" + "<Onm>" + fullName + "</Onm>\n" + "<OCerTp>" + cardType + "</OCerTp>\n" + "<OCerNo>" + idNumber + "</OCerNo>\n" + "<Mno>" + cellphone + "</Mno>\n" + "<Sign>" + signature + "</Sign>\n" + "<Ver>" + version + "</Ver>\n" + "<OSsn>" + serialNumber + "</OSsn>\n" + "</FM>"
                        println xml
                        def result = requestPost(url, "FM=" + xml)
                        def rootNode = new groovy.util.XmlParser().parseText(result)
                        def Rcd = rootNode.Rcd.text()
                        def RDesc = rootNode.RDesc.text()
                        def OSsn = rootNode.OSsn.text()
                        def CardNo = rootNode.CardNo.text()
                        def MchntCd = rootNode.MchntCd.text()
                        def Ver = rootNode.Ver.text()
                        def Sign = rootNode.Sign.text()
                        println Sign
                        def signSource = Rcd + "|" + OSsn + "|" + CardNo + "|" + MchntCd + "|" + Ver + "|" + key
                        signSource = encode(signSource.toString(), "UTF-8")
                        println signSource
                        if (Sign == signSource)
                        {
                            if (Rcd == "0000")
                            {
                                map.put("flag", true)
                                map.put("memo", RDesc)
                            }
                            else
                            {
                                map.put("flag", false)
                                map.put("memo", RDesc)
                                map.put("account", it.type.name)
                            }
                        }
                        else
                        {
                            map.put("flag", false)
                            map.put("memo", "验签失败")
                            map.put("account", it.type.name)
                        }
                        println "flag" + map.get("flag")
                        if (map.get("flag") == false)
                        {
                            println "return"
                            break
                        }
                    }
                    return map
                }
            }
            else
            {
                def map = [:]
                map.put("flag", false)
                map.put("memo", "银行卡预留手机号或者身份证号未填写")
                return map
            }
        }
        else
        {
            def map = [:]
            map.put("flag", false)
            map.put("memo", "收款账号还款账号未填写")
            return map
        }

    }

    //火凤凰贷款
    def huod = { opportunity ->
        def OpportunityFlexField = com.next.OpportunityFlexField.findAll("from OpportunityFlexField where category.opportunity.id = ${opportunity?.id} and value is not null")
        def activitity = com.next.Activity.findAllByOpportunity(opportunity)
        def opportunityFlows = com.next.OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def teamRole = com.next.TeamRole.find("from TeamRole where name = 'Approval'")
        def opportunityRoles = com.next.OpportunityRole.findAll("from OpportunityRole where opportunity.id = ${opportunity?.id} and teamRole.id = ${teamRole?.id}")

        def regpersoncode = opportunity.contact?.cellphone
        def regpersonname = opportunity.contact?.fullName
        def regdt = new java.text.SimpleDateFormat("yyyy-MM-dd").format(opportunity.createdDate)
        def term = opportunity.actualLoanDuration
        def limitterm = opportunity.actualLoanDuration
        def comapplysum = opportunity.requestedAmount * 10000
        def isborrow//还款方式
        def lilv = 0 //利率
        def qudaolv = 0 //渠道费率
        def fuwufeilv = 0//服务费  月收
        def fuwufeilv2 = 0 // 服务费 一次性
        def curency = 0 //上口息月数
        def services = []
        def advancePayment = 0
        def isContractType = false
        def createBy = false
        def opportunityProduct = com.next.OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        opportunity.interest.each {
            if (it.contractType) {
                isContractType = true
            }
        }
        opportunity.interest.each {
            if (it.createBy) {
                createBy = true
            }
        }
        if (createBy) {
            def interestPaymentMethod = opportunity.interestPaymentMethod?.name//支付方式
            Boolean channel = false
            opportunityProduct.each {
                if (isContractType) {
                    if (it.productInterestType.name == "服务费费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            fuwufeilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            fuwufeilv += it.rate //费率金额 月
                        }
                        services.add(it.installments)// 用于判断服务费的收费方式
                    }
                    if (it.productInterestType.name == "渠道返费费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            qudaolv = serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            qudaolv = it.rate //费率金额 月
                        }
                        channel = it.installments
                    }
                    if (it?.productInterestType?.name == "基本费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            lilv += it.rate //费率金额 月
                            curency = it.firstPayMonthes
                        }
                    }
                    if (it?.productInterestType?.name == "大额（单套大于1200万）" || it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域") {
                        if (it.contractType?.name == "借款合同") {

                            if (!it.installments) {
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            } else {
                                lilv += it.rate //费率金额 月
                            }
                        }
                        if (it.contractType?.name == "委托借款代理服务合同") {
                            if (!it.installments) {
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            } else {
                                fuwufeilv += it.rate //费率金额 月
                            }
                        }
                        if (it.productInterestType.name == "意向金") {
                            advancePayment = it.rate
                        }

                    }
                } else {
                    lilv = opportunity.monthlyInterest
                    if (it.productInterestType.name == "服务费费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            fuwufeilv = serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            fuwufeilv = it.rate //费率金额 月
                        }
                        services.add(it.installments)// 用于判断服务费的收费方式
                    }
                    if (it.productInterestType.name == "渠道返费费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            qudaolv = serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            qudaolv = it.rate //费率金额 月
                        }
                        channel = it.installments
                    }
                    if (it.productInterestType.name == "意向金") {
                        advancePayment = it.rate
                    }

                }
            }
            Boolean service
            Boolean mixture = true
            if (services.size() == 1) {
                service = services.get(0)
            } else {
                mixture = false
            }

            if (interestPaymentMethod == "上扣息" && channel) {
                isborrow = "0"
            } else if (interestPaymentMethod == "扣息差" && !channel) {
                isborrow = "1"
            } else if (interestPaymentMethod == "一次性付息") {
                isborrow = "2"
            } else if (interestPaymentMethod == "上扣息" && !channel) {
                isborrow = "3"
            } else if (interestPaymentMethod == "扣息差" && channel) {
                isborrow = "4"
            } else if (interestPaymentMethod == "等额收息") {
                isborrow = "15"
            } else if (interestPaymentMethod == "月息年本" && service && channel && mixture) {
                isborrow = "9"
            } else if (interestPaymentMethod == "月息年本" && !service && !channel && mixture) {
                isborrow = "10"
            } else if (interestPaymentMethod == "月息年本" && service && !channel && mixture) {
                isborrow = "11"
            } else if (interestPaymentMethod == "月息年本" && !service && channel && mixture) {
                isborrow = "12"
            } else if (interestPaymentMethod == "月息年本" && channel && !mixture) {
                isborrow = "13"
            } else if (interestPaymentMethod == "月息年本" && !channel && !mixture) {
                isborrow = "14"
            } else if (interestPaymentMethod == "等本等息" && service && channel && mixture) {
                isborrow = "5"
            } else if (interestPaymentMethod == "等本等息" && !service && !channel && mixture) {
                isborrow = "6"
            } else if (interestPaymentMethod == "等本等息" && !service && channel && mixture) {
                isborrow = "7"
            } else if (interestPaymentMethod == "等本等息" && service && !channel && mixture) {
                isborrow = "8"
            }else if (interestPaymentMethod == "下扣息") {
                isborrow = "17"
            }

        } else {
            lilv = opportunity.monthlyInterest
            if (opportunity.advancePayment) {
                advancePayment = opportunity.advancePayment
            }
            def interestPaymentMethod = opportunity.interestPaymentMethod?.name//支付方式
            Boolean channel
            if (opportunity.commissionPaymentMethod?.name == "月月返") {
                channel = true
                qudaolv = opportunity.commissionRate
            } else {
                channel = false
                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(opportunity.commissionRate / term)
                qudaolv = serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
            }

            if (interestPaymentMethod == "上扣息" && channel) {
                isborrow = "0"
            } else if (interestPaymentMethod == "扣息差" && !channel) {
                isborrow = "1"
            } else if (interestPaymentMethod == "一次性付息") {
                isborrow = "2"
            } else if (interestPaymentMethod == "上扣息" && !channel) {
                isborrow = "3"
            } else if (interestPaymentMethod == "扣息差" && channel) {
                isborrow = "4"
            } else if (interestPaymentMethod == "等额收息") {
                isborrow = "15"
            }else if (interestPaymentMethod == "下扣息") {
                isborrow = "17"
            }

            if (opportunity.serviceCharge) {
                fuwufeilv = opportunity.serviceCharge
            }
        }
        if (services.size() > 1) {
            fuwufeilv = 0
            fuwufeilv2 = 0
            opportunityProduct.each {
                if (it.productInterestType.name == "服务费费率") {
                    if (it.installments == true) {
                        fuwufeilv += it.rate //费率金额 月
                    } else {
                        java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                        fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                    }
                }
                if (isContractType) {
                    if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域") {
                        if (it.contractType?.name == "委托借款代理服务合同") {
                            if (!it.installments) {
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            } else {
                                fuwufeilv += it.rate //费率金额 月
                            }
                        }

                    }
                }
            }
        }

        //计算总系费率息
        def total = lilv + qudaolv + fuwufeilv + fuwufeilv2

        def risker = ""
        opportunityFlows.each {
            if (it.opportunityLayout) {
                if (it.opportunityLayout.name == "opportunityLayout02") {
                    def stagecode = it.stage.code
                    opportunityRoles.each {
                        if (it.stage.code == stagecode) {
                            if (it.stage) {
                                risker += it.stage.name + ":" + it.user.fullName + ","
                            }

                        }
                    }
                }
            }
        }

        //APP 贷款申请基本信息
        def apply = [:]
        def value1 = ""//借款人资质小结
        def value2 = ""//征信小结
        def value3 = ""//大数据小结
        OpportunityFlexField.each {
            if (it.name == "借款人资质小结") {
                value1 = it.value
            } else if (it.name == "征信小结") {
                value2 = it.value
            } else if (it.name == "大数据小结") {
                value3 = it.value
            }
        }
        apply.put("borrower_quality", value1)//借款人资质小结
        apply.put("credit_summary", value2)//征信小结
        apply.put("data_summary", value3)//大数据小结
        apply.put("appno", opportunity.serialNumber)//APP进单号
        apply.put("regperson_code", regpersoncode)//登记人号
        apply.put("regperson_name", regpersonname)//登记人名
        apply.put("regdt", regdt)//登记日期isborrow
        apply.put("ratemode", "1")//固定利率
        apply.put("term", term)//申请期限
        apply.put("comapplysum", comapplysum)//申请金额d

        apply.put("isborrow", isborrow)//还款方式  数据字典

        apply.put("feeratio", lilv + "")//利率d
        apply.put("floatratio", qudaolv + "")//渠道费率d
        apply.put("marginratio", fuwufeilv + "")//服务费率d
        apply.put("marginratio2", fuwufeilv2 + "")
        apply.put("rate", total + "")//息费率d
        apply.put("limitamt", advancePayment)//意向金
        apply.put("curency", curency)//上扣息月数
        apply.put("regorg", "001001002")//登记机构
        apply.put("remark", "无")//意见   非必填
        apply.put("loanuse", "")//贷款用途类别
        apply.put("loanuse_explain", "其他")//贷款用途说明
        apply.put("limitterm",limitterm)//实际贷款期限
        apply.put("approveamt", opportunity.actualAmountOfCredit * 10000)//批贷金额
        def groupname = com.next.Reporting.findByUser(opportunity.user)?.manager?.fullName//组长名字
        def manager = com.next.Reporting.findByUser(opportunity.user)?.manager//组长
        if (groupname) {
            apply.put("groupname", groupname)//销售组长姓名
            manager = com.next.Reporting.findByUser(manager)?.manager?.fullName
            if (manager) {
                apply.put("sale_manager", manager)//销售组长姓名
            } else {
                apply.put("sale_manager", "")//销售组长姓名
            }
        } else {
            apply.put("groupname", "")//销售组长姓名
            apply.put("sale_manager", "")//销售组长姓名
        }
        apply.put("saler", opportunity?.user?.fullName)//销售姓名
        apply.put("risker", risker)//风控主单姓名

        //借款人
        def custname
        def certiftype
        def certifid
        def certifcountry
        String brithdt
        def phone
        def marrstatus = "10"
        def customerchannel
        String accountname

        //抵押人
        def certifcountry1
        def idtype1
        def custname1
        def idno1

        def idNumberFormat = { String idnumber ->
            def birthday
            if (idnumber.length() == 18) {
                String year = idnumber.substring(6, 10)
                String month = idnumber.substring(10, 12)
                String day = idnumber.substring(12, 14)
                birthday = year + "-" + month + "-" + day
            }
            if (idnumber.length() == 15) {
                String year = idnumber.substring(6, 8)
                String month = idnumber.substring(8, 10)
                String day = idnumber.substring(10, 12)
                birthday = "19" + year + "-" + month + "-" + day

            }
            return birthday
        }

        def family = []
        opportunity.contacts.each {
            def relation
            def flybrithdt
            def flycertifid
            def flyycustname
            def marrystatus1
            def cellphone
            if (it.type) {
                if (it.type.name == "借款人" || it.type.name == "本人") {
                    custname = it.contact?.fullName
                    certiftype = "0"
                    certifid = it.contact.idNumber
                    certifcountry = "CHN"
                    brithdt = idNumberFormat(it.contact.idNumber)
                    phone = it.contact.cellphone
                    marrstatus = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "婚姻状况", it.contact?.maritalStatus)?.value2
                    if (it.contact && it.contact.account) {
                        accountname = it.contact.account.name
                        if (accountname.contains("中佳信")) {
                            customerchannel = "PC1600000001"
                        } else {
                            customerchannel = "PC1600000002"
                        }
                    } else {
                        customerchannel = "PC1600000002"
                    }

                }
                if (it.type.name == "抵押人") {
                    certifcountry1 = "CHN"
                    idtype1 = "0"
                    custname1 = it.contact?.fullName
                    idno1 = it.contact.idNumber
                }
                ////////////////////////////对私客户家庭成员信息

                if (it.type.name == "借款人") {
                    relation = "1"
                } else if (it.type.name == "借款人配偶") {
                    relation = "2"
                } else if (it.type.name == "抵押人") {
                    relation = "3"
                } else if (it.type.name == "抵押人配偶") {
                    relation = "4"
                } else if (it.type.name == "其他借款人") {
                    relation = "5"
                } else if (it.type.name == "借款人父母") {
                    relation = "6"
                } else if (it.type.name == "借款人子女") {
                    relation = "7"
                }

                flybrithdt = idNumberFormat(it.contact.idNumber)
                flycertifid = it.contact.idNumber
                flyycustname = it.contact?.fullName
                marrystatus1 = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "婚姻状况", it.contact.maritalStatus)?.value2
                cellphone = it.contact.cellphone
                ////////////////////////////对私客户家庭成员信息
            }
            def familyMap = [:]
            familyMap.put("appellation", "01")//成员称谓 共借人
            familyMap.put("relation", relation)
            familyMap.put("flycertifcountry", "CHN")//中国
            familyMap.put("flycertiftype", "0")//身份证
            familyMap.put("flybrithdt", flybrithdt)//出生日期
            familyMap.put("flycertifid", flycertifid)//证件号码
            familyMap.put("flyycustname", flyycustname)//姓名
            familyMap.put("marrstatus", marrystatus1)//户口本上的婚姻状况
            familyMap.put("valid", "20")//证件有效期
            familyMap.put("houseregisaddr", "")//户籍所在地
            familyMap.put("usedname", "")//曾用名
            familyMap.put("bookletnumber", "")//户口本号码
            familyMap.put("phone_no", cellphone)//手机号码
            family.add(familyMap)
        }
        //////////////////////////////////// //对私客户信息
        def custom = [:]
        custom.put("custname", custname)//客户名称
        custom.put("certiftype", certiftype)//证件类型 身份证
        custom.put("certifid", certifid)//证件号码
        custom.put("certifcountry", certifcountry)//发证国家
        custom.put("brithdt", brithdt)//出生日期
        custom.put("phone", phone)//电话
        custom.put("marrstatus", marrstatus)//婚姻状态 如果是离婚则提供离婚证明
        custom.put("customerchannel", customerchannel)//客户渠道
        //////////////////////////////////////////////////
        custom.put("houseregisaddr", "110100")//户籍所在地
        custom.put("bookletnumber", "1111111111")//户口本号码
        custom.put("usedname", "无")//曾用名  可选
        custom.put("valid", "20")//证件有效期
        //新增
        custom.put("address", "")//通讯地址
        custom.put("education", "")//学历
        custom.put("monthly_income", "")//月收入
        custom.put("sex", "")//性别  暂无

        //////////////////////////////////  //抵押品所有权人信息
        def owner = []

        def evaluate = []

        /////////////////////////////抵押品信息
        def gageList = []
        opportunity.collaterals.each {
            def evalvalue = opportunity.loanAmount
            def evaluateMap = [:]
            evaluateMap.put("evafs", "1")//评估方式 没有
            evaluateMap.put("evaloutfit", "中佳信")//评估机构
            evaluateMap.put("evalvalue", evalvalue)//评估价值
            evaluateMap.put("mortgage_no", it.externalId)
            evaluate.add(evaluateMap)

            def ownerMap = [:]
            if (custname1) {
                ownerMap.put("certifcountry", certifcountry1)//国籍
                ownerMap.put("idtype", idtype1)//证件类型
                ownerMap.put("custname", custname1)//所有权人姓名
                ownerMap.put("idno", idno1)//证件号码
                ownerMap.put("mortgage_no", it.externalId)
                owner.add(ownerMap)
            } else {
                ownerMap.put("certifcountry", certifcountry)//国籍
                ownerMap.put("idtype", certiftype)//证件类型
                ownerMap.put("custname", custname)//所有权人姓名
                ownerMap.put("idno", certifid)//证件号码
                ownerMap.put("mortgage_no", it.externalId)
                owner.add(ownerMap)
            }



            def houseproperty = "0"
            def houseregisaddrtext = "110000"
            def mortgagetype = "0"
            def specificfactor = "0"
            def towards = "10"
            def buildingtype = "0"
            def buildingarea = it.area
            def buildingname = it.projectName
            def buildingno = it.propertySerialNumber
            def buildingonfloor = it.floor
            buildingtype = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "房产类型", it.assetType)?.value2
            def firstmortgagemoney = it.firstMortgageAmount
            def firstmortgagetype = "1"
            if (it.typeOfFirstMortgage?.name == "银行类") {
                firstmortgagetype = "0"
            }

            def floorridgepole = it.building
            houseproperty = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "房屋类型", it.houseType)?.value2
            if (it.city) {
                houseregisaddrtext = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "城市", it.city.name)?.value2

            }

            def housetype = "1"  //字段修改 找不到了
            if (it.mortgageType) {
                mortgagetype = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "抵押类型", it.mortgageType.name)?.value2
            }
            def roomnumber = it.roomNumber
            def secondmortgagemoney = it.secondMortgageAmount
            if (it.specialFactors) {
                specificfactor = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "特殊因素", it.specialFactors)?.value2
            }
            def totalfloor = it.totalFloor
            towards = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "房屋朝向", it.orientation)?.value2
            def unitprice = it.unitPrice
            def district = com.next.District.findByCityAndName(it.city, it.district)?.code
            def mortgageaddr = it.address
            def buildTime = "9999-01-01"
            if (it.buildTime) {
                buildTime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(it.buildTime)
            }

            def gage = [:]
            gage.put("buildingarea", buildingarea)//建筑面firstMortgageAmount
            gage.put("buildingname", buildingname)//；楼盘名称
            gage.put("buildingonfloor", buildingonfloor)//抵押房产所在楼层
            gage.put("buildingtype", buildingtype)//房产类型  数据字典
            gage.put("firstmortgagemoney", firstmortgagemoney)//一抵金额d
            gage.put("firstmortgagetype", firstmortgagetype)//首资抵押类型
            gage.put("floorridgepole", floorridgepole)//楼栋
            gage.put("houseproperty", houseproperty)//房屋类型
            gage.put("houseregisaddrtext", houseregisaddrtext)//城市编码中文名  数据字典
            gage.put("housetype", housetype)//户型  数据字典
            gage.put("mortgagetype", mortgagetype)//抵押类型  数据字典
            gage.put("roomnumber", roomnumber)//房号
            gage.put("secondmortgagemoney", secondmortgagemoney)//二抵金额d
            gage.put("specificfactor", specificfactor)//特殊因素 数据字典
            gage.put("totalfloor", totalfloor)//总楼层
            gage.put("towards", towards)//房屋朝向 数据字典
            gage.put("unitprice", unitprice)//单价d
            gage.put("buildingno", buildingno)//房产证号码
            gage.put("onemortgagetype", "2")//一抵类型
            gage.put("onemortgagedeadline", "4")//一抵押期限
            gage.put("secondmortgagedeadline", "7")//二抵押期限
            gage.put("buildingstates", "2")//房产状态
            gage.put("mortgageaddr", mortgageaddr)//抵押物地址
            gage.put("mortgagerate", it.loanToValue)//抵押率d
            gage.put("area", district)//区域
            gage.put("lease", "")//是否出租
            gage.put("build_date", buildTime)//建成时间
            if (it.buildTime) {
                gage.put("ageofhouse", new java.util.Date().year - new java.text.SimpleDateFormat("yyyy-MM-dd").parse("2016-12-08 00:00:00").year)
//房龄
            } else {
                gage.put("ageofhouse", "")//房龄
            }

            gage.put("mortgage_no", it.externalId)
            if (it.region) {
                if (it.region.name == "六环外") {
                    gage.put("location", "2")//位置信息
                } else {
                    gage.put("location", "1")//位置信息
                }
            } else {
                gage.put("location", "0")//位置信息
            }

            gageList.add(gage)
        }

        //抵押品评估信息

        //第三方征信信息
        def credit = []
        def creditMap = [:]
        creditMap.put("provider", "央行征信")//提供商
        creditMap.put("name", "征信信息见附件")//名称
        creditMap.put("value", "5")//数据值
        credit.add(creditMap)

        //评分信息
        def score = []
        def scoreMap = [:]
        /*scoreMap.put("name", "评分信息")//名称
        scoreMap.put("value", "见附件")//数据值*/
        scoreMap.put("name", "01")//名称


        def level
        if (opportunity.lender.level) {
            level = opportunity.lender.level.name
        }
        if (level == "A") {
            level = "0101"
        } else if (level == "B") {
            level = "0102"
        } else if (level == "C") {
            level = "0103"
        } else if (level == "D") {
            level = "0104"
        }
        scoreMap.put("value", level)//数据值
        score.add(scoreMap)

        //产调信息
        def survey = []
        def surveyMap = [:]
        surveyMap.put("name", "产调信息见附件")//名称
        surveyMap.put("value", "5")//数值
        survey.add(surveyMap)

        //附件信息
        def files = []
        opportunity.attachments.each {
            def filesMap = [:]
            filesMap.put("name", it.type.name)
            filesMap.put("value", it.fileUrl)
            files.add(filesMap)
        }

        //产品信息
        def productaccount = [:]
        if (opportunity.productAccount) {
            productaccount.put("name", opportunity.productAccount.name)//名称
            if (opportunity.productAccount.account) {
                productaccount.put("account", opportunity.productAccount.account.name)//机构
            } else {
                productaccount.put("account", "")//机构
            }

            def active = "有效"
            if (opportunity.productAccount.active == true) {
                active = "有效"
            } else {
                active = "无效"
            }
            productaccount.put("active", active)//有效性
            def createdDate
            if (opportunity.productAccount.createdDate) {
                createdDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(opportunity.productAccount.createdDate)
            }
            def modifiedDate
            if (opportunity.productAccount.modifiedDate) {
                modifiedDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(opportunity.productAccount.modifiedDate)
            }

            productaccount.put("createddate", createdDate)//创建时间
            productaccount.put("modifieddate", modifiedDate)//修改时间
            if (opportunity.productAccount.principalPaymentMethod) {
                productaccount.put("principalpaymentmethod", opportunity.productAccount.principalPaymentMethod.name)
//本金支付方式
            } else {
                productaccount.put("principalpaymentmethod", "")//本金支付方式
            }

            productaccount.put("description", opportunity.productAccount?.description)//描述
            productaccount.put("number", opportunity.productAccount?.product?.id)//产品编号
        } else {
            productaccount.put("name", "无")//名称
            productaccount.put("account", "无")//机构
            productaccount.put("active", true)//有效性
            productaccount.put("createddate", "2017-01-01")//创建时间
            productaccount.put("modifieddate", "2017-01-01")//修改时间
            productaccount.put("principalpaymentmethod", "无")//本金支付方式
            productaccount.put("description", "")//描述
            productaccount.put("number", "")//产品编号
        }




        def opportunityflows = []
        opportunityFlows.each {

            def opportunityflowsMAp = [:]
            opportunityflowsMAp.put("comments", it.comments)//建议
            if (it.stage) {
                opportunityflowsMAp.put("stage", it.stage.name)//阶段名称
            } else {
                opportunityflowsMAp.put("stage", "")//阶段名称
            }

            def name
            def stage = it.stage.code
            opportunityRoles.each {
                if (stage == it.stage.code) {
                    name = it.user?.fullName
                }
            }
            opportunityflowsMAp.put("name", name)
            opportunityflows.add(opportunityflowsMAp)

        }

        def activities = []
        activitity.each {
            def activitiesMap = [:]
            if (it.type) {
                activitiesMap.put("type", it.type.name)//类型
            } else {
                activitiesMap.put("type", "")//类型
            }
            if (it.subtype) {
                activitiesMap.put("subtype", it.subtype.name)//子类型
            } else {
                activitiesMap.put("subtype", "")//类型
            }


            def starttime
            if (it.startTime) {
                starttime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(it.startTime)
            }
            def endtime
            if (it.endTime) {
                endtime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(it.endTime)
            }
            activitiesMap.put("starttime", starttime)//开始时间
            activitiesMap.put("endtime", endtime)//结束时间
            def status
            if (it.status == "Pending") {
                status = "开始"
            } else if (it.status == "Delayed") {
                status = "延期"
            } else if (it.status == "Completed") {
                status = "完成"
            } else if (it.status == "Canceled") {
                status = "取消"
            }
            activitiesMap.put("status", status)//状态
            def user
            if (it.user) {
                user = it.user?.fullName
            } else {
                user = "无"
            }
            activitiesMap.put("user", user)//外访发起人
            activitiesMap.put("contact", it.contact?.fullName)//客户名称
            activitiesMap.put("assignedto", it.assignedTo?.fullName)//下户人
            activities.add(activitiesMap)
        }

        //数据都放入一个map中转换成JSON
        def loanApprovalMap = [:]
        loanApprovalMap.put("apply", apply)
        loanApprovalMap.put("custom", custom)
        loanApprovalMap.put("evaluate", evaluate)
        loanApprovalMap.put("family", family)
        loanApprovalMap.put("gage", gageList)
        loanApprovalMap.put("owner", owner)
        loanApprovalMap.put("score", score)
        loanApprovalMap.put("survey", survey)
        loanApprovalMap.put("credit", credit)
        loanApprovalMap.put("files", files)
        loanApprovalMap.put("product", productaccount)
        loanApprovalMap.put("opportunityflows", opportunityflows)
        loanApprovalMap.put("activities", activities)
        loanApprovalMap.put("username", "")  //用户名
        loanApprovalMap.put("password", "")  //密码

        String loanApproval = groovy.json.JsonOutput.toJson(loanApprovalMap).toString()
        def sendPost = { String urlString, String params1 ->
            URL url = new java.net.URL(urlString)
            def result
            try {
                def connection = (java.net.HttpURLConnection) url.openConnection()
                connection.setDoOutput(true)
                connection.setRequestMethod("POST")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write params1 }
                connection.setConnectTimeout(10000)
                result = grails.converters.JSON.parse(connection.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
                println("返回结果" + result)
            }
            catch (java.lang.Exception e) {
                e.printStackTrace()
                println e
            }
            return result
        }
        def res = sendPost(com.next.CreditReportProvider.findByCode("HUOFH")?.apiUrl+"/m/huofhservice/interface.do?reqCode=apiApply", loanApproval)
        return res
    }
    //火凤凰放款
    def huofang = { opportunity ->
        def OpportunityFlexField = com.next.OpportunityFlexField.findAll("from OpportunityFlexField where category.opportunity.id = ${opportunity?.id} and value is not null")
        def opportunityFlows = com.next.OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def teamRole = com.next.TeamRole.find("from TeamRole where name = 'Approval'")
        def opportunityRoles = com.next.OpportunityRole.findAll("from OpportunityRole where opportunity.id = ${opportunity?.id} and teamRole.id = ${teamRole?.id}")
        def activitity = com.next.Activity.findAllByOpportunity(opportunity)
        def dateFormat = { java.util.Date date ->
            java.text.SimpleDateFormat sFormat = new java.text.SimpleDateFormat("yyyy-MM-dd")
            String d = sFormat.format(date)
            return d
        }
        def bank = []
        opportunity.bankAccounts.each {
            def bankAccount = [:]
            String BankAccountType = it.type.name
            def typename
            if (BankAccountType.contains("收款")) {
                typename = "00"
            } else if (BankAccountType.contains("还款")) {
                typename = "01"
            }

            def bankname = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "银行", it.bankAccount?.bank?.name)?.value2

            bankAccount.put("bankaccounttype", typename)//交易类型
            bankAccount.put("loanname", it.bankAccount?.name)//放（还）款账号名
            bankAccount.put("paymentaccount", it.bankAccount?.numberOfAccount)//放(还）款账号
            bankAccount.put("loanbankcode", bankname)//放（还）款银行
            bankAccount.put("loandeposit", it.bankAccount?.bankBranch)//放（还）款账户开户行
            bankAccount.put("loandepprovince", it.bankAccount?.bankAddress)//放（还）款开户行所在地
            bankAccount.put("loan_idnumber", it.bankAccount?.numberOfCertificate)//放(还）款户名身份证号码
            bankAccount.put("loan_cellphone", it.bankAccount?.cellphone)//放(还）款银行预留手机号
            bankAccount.put("loanaccountnotype", "00")//放（还）款账号类型
            bankAccount.put("loanaccounttype", "0")//放（还）款账户类型
            bankAccount.put("payment_chnl", "")//放款渠道    0-广银联，1-中金
            bankAccount.put("repayment_chnl", "")//还款渠道  0-广银联，1-中金
            bank.add(bankAccount)
        }

        //合同信息
        def contract = [:]//2016年10月14日10:09:33 新增
        if (opportunity.contracts) {
            opportunity.contracts.each {
                if (it.contract?.type?.name == "借款合同") {
                    contract.put("contract_no", it.contract?.serialNumber)//合同号
                    contract.put("startdate", "2016-01-01")//合同起始日
                    contract.put("enddate", "2016-01-01")//合同截止日
                    contract.put("signdate", dateFormat(it.contract?.createdDate))//合同签订日
                }
            }

        } else {
            contract.put("contract_no", opportunity.externalId)//合同号
            contract.put("startdate", "2016-01-01")//合同起始日
            contract.put("enddate", "2016-01-01")//合同截止日
            contract.put("signdate", dateFormat(new Date()))//合同签订日
        }

        //抵押品入库信息
        def storage = [:]
        storage.put("beforecompletion", "1")//放款前条件完成情况
        storage.put("beforecompletionremark", "无")//放款前条件完成情况备注
        storage.put("gagefinishinfo", "2")//抵押完成情况
        storage.put("gagefinishinforemark", "无备注")//抵押完成情况备注
        storage.put("gageinquestfinishinfo", "2")//抵押品勘验完成情况
        storage.put("gageinquestfinishinforemark", "无")//抵押品勘验完成情况备注
        storage.put("notaryfinishinfo", "2")//公证完成情况
        storage.put("notaryfinishinforemark", "无")//公证完成情况备注
        storage.put("status", "2")//出入库状态
        storage.put("storageperson", "没有数据")//入库人
        storage.put("storageorg", "001001002")//入库机构
        storage.put("storagedate", dateFormat(new Date()))//入库日期
        storage.put("storageexplain", "无信息")//入库说明
        storage.put("deliverydate", dateFormat(new Date()))//出库日期
        storage.put("deliveryexplain", "无")//出库说明
        storage.put("deliveryorg", "001001002")//出库机构
        storage.put("deliveryperson", "无信息")//出库人

        //放款申请信息
        def loan = [:]
        def regdt2 = dateFormat(opportunity.createdDate)
        Double requestedAmount = opportunity.actualAmountOfCredit
        requestedAmount = requestedAmount * 10000
        loan.put("appno", opportunity.serialNumber)//APP进单编号
        loan.put("accountamount", requestedAmount)//出账金额
        loan.put("regperson_code", opportunity.contact?.cellphone)//登记人号
        loan.put("regperson_name", opportunity.contact?.fullName)//登记人名
        loan.put("limitterm", opportunity.actualLoanDuration)//实际贷款期限
        loan.put("remarks", "无")//备注
        loan.put("startingdate", dateFormat(new Date()))//出账起始日
        loan.put("accountorg", "001001002")//业务机构
        loan.put("regorg", "001001002")//登记机构
        loan.put("loancenter", "001001002")//放款中心
        loan.put("enddate", "2016-01-01")//出账到期日
        loan.put("loancondition", "6")//放款落实条件（他项 、回执）
        loan.put("regdt", regdt2)//登记日期

        //抵押品入库信息  抵押类型
        def sub = []
        def sunMap = [:]
        sunMap.put("mortgagetype", "1")
        sub.add(sunMap)

        //
        //附件信息
        def files = []
        opportunity.attachments.each {
            def filesMap = [:]
            filesMap.put("name", it.type.name)
            filesMap.put("value", it.fileUrl)
            files.add(filesMap)
        }

        //资金渠道
        def inner_account = ""
        OpportunityFlexField.each {
            if (it.name == "放款账号") {
                inner_account = it.value
            }
        }
        if (!inner_account) {
            inner_account = opportunity?.bills?.account?.name
        }
        def trust_program = [:]
        trust_program.put("inner_account", inner_account)//放款账号


        def regpersoncode = opportunity.contact?.cellphone
        def regpersonname = opportunity.contact?.fullName
        def regdt = new java.text.SimpleDateFormat("yyyy-MM-dd").format(opportunity.createdDate)
        def term = opportunity.actualLoanDuration
        def comapplysum = opportunity.requestedAmount * 10000
        def isborrow//还款方式
        def lilv = 0 //利率
        def qudaolv = 0 //渠道费率
        def fuwufeilv = 0//服务费  月收
        def fuwufeilv2 = 0 // 服务费 一次性
        def curency = 0 //上口息月数
        def services = []
        def advancePayment = 0
        def isContractType = false
        def createBy = false
        def opportunityProduct = com.next.OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        opportunity.interest.each {
            if (it.contractType) {
                isContractType = true
            }
        }
        opportunity.interest.each {
            if (it.createBy) {
                createBy = true
            }
        }
        if (createBy) {
            def interestPaymentMethod = opportunity.interestPaymentMethod?.name//支付方式
            Boolean channel = false
            opportunityProduct.each {
                if (isContractType) {
                    if (it.productInterestType.name == "服务费费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            fuwufeilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            fuwufeilv += it.rate //费率金额 月
                        }
                        services.add(it.installments)// 用于判断服务费的收费方式
                    }
                    if (it.productInterestType.name == "渠道返费费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            qudaolv = serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            qudaolv = it.rate //费率金额 月
                        }
                        channel = it.installments
                    }
                    if (it?.productInterestType?.name == "基本费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            lilv += it.rate //费率金额 月
                            curency = it.firstPayMonthes
                        }
                    }
                    if (it?.productInterestType?.name == "大额（单套大于1200万）" || it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域") {
                        if (it.contractType?.name == "借款合同") {

                            if (!it.installments) {
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            } else {
                                lilv += it.rate //费率金额 月
                            }
                        }
                        if (it.contractType?.name == "委托借款代理服务合同") {
                            if (!it.installments) {
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            } else {
                                fuwufeilv += it.rate //费率金额 月
                            }
                        }
                    }
                } else {
                    lilv = opportunity.monthlyInterest
                    if (it.productInterestType.name == "服务费费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            fuwufeilv = serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            fuwufeilv = it.rate //费率金额 月
                        }
                        services.add(it.installments)// 用于判断服务费的收费方式
                    }
                    if (it.productInterestType.name == "渠道返费费率") {
                        if (!it.installments) {
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            qudaolv = serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        } else {
                            qudaolv = it.rate //费率金额 月
                        }
                        channel = it.installments
                    }
                    if (it.productInterestType.name == "意向金") {
                        advancePayment = it.rate
                    }

                }
            }
            Boolean service
            Boolean mixture = true
            if (services.size() == 1) {
                service = services.get(0)
            } else {
                mixture = false
            }

            if (interestPaymentMethod == "上扣息" && channel) {
                isborrow = "0"
            } else if (interestPaymentMethod == "扣息差" && !channel) {
                isborrow = "1"
            } else if (interestPaymentMethod == "一次性付息") {
                isborrow = "2"
            } else if (interestPaymentMethod == "上扣息" && !channel) {
                isborrow = "3"
            } else if (interestPaymentMethod == "扣息差" && channel) {
                isborrow = "4"
            } else if (interestPaymentMethod == "等额收息") {
                isborrow = "15"
            } else if (interestPaymentMethod == "月息年本" && service && channel && mixture) {
                isborrow = "9"
            } else if (interestPaymentMethod == "月息年本" && !service && !channel && mixture) {
                isborrow = "10"
            } else if (interestPaymentMethod == "月息年本" && service && !channel && mixture) {
                isborrow = "11"
            } else if (interestPaymentMethod == "月息年本" && !service && channel && mixture) {
                isborrow = "12"
            } else if (interestPaymentMethod == "月息年本" && channel && !mixture) {
                isborrow = "13"
            } else if (interestPaymentMethod == "月息年本" && !channel && !mixture) {
                isborrow = "14"
            } else if (interestPaymentMethod == "等本等息" && service && channel && mixture) {
                isborrow = "5"
            } else if (interestPaymentMethod == "等本等息" && !service && !channel && mixture) {
                isborrow = "6"
            } else if (interestPaymentMethod == "等本等息" && !service && channel && mixture) {
                isborrow = "7"
            } else if (interestPaymentMethod == "等本等息" && service && !channel && mixture) {
                isborrow = "8"
            }else if (interestPaymentMethod == "下扣息") {
                isborrow = "17"
            }

        } else {
            lilv = opportunity.monthlyInterest
            if (opportunity.advancePayment) {
                advancePayment = opportunity.advancePayment
            }
            def interestPaymentMethod = opportunity.interestPaymentMethod?.name//支付方式
            Boolean channel
            if (opportunity.commissionPaymentMethod?.name == "月月返") {
                channel = true
                qudaolv = opportunity.commissionRate
            } else {
                channel = false
                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(opportunity.commissionRate / term)
                qudaolv = serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
            }

            if (interestPaymentMethod == "上扣息" && channel) {
                isborrow = "0"
            } else if (interestPaymentMethod == "扣息差" && !channel) {
                isborrow = "1"
            } else if (interestPaymentMethod == "一次性付息") {
                isborrow = "2"
            } else if (interestPaymentMethod == "上扣息" && !channel) {
                isborrow = "3"
            } else if (interestPaymentMethod == "扣息差" && channel) {
                isborrow = "4"
            } else if (interestPaymentMethod == "等额收息") {
                isborrow = "15"
            }else if (interestPaymentMethod == "下扣息") {
                isborrow = "17"
            }

            if (opportunity.serviceCharge) {
                fuwufeilv = opportunity.serviceCharge
            }
        }
        if (services.size() > 1) {
            fuwufeilv = 0
            fuwufeilv2 = 0
            opportunityProduct.each {
                if (it.productInterestType.name == "服务费费率") {
                    if (it.installments == true) {
                        fuwufeilv += it.rate //费率金额 月
                    } else {
                        java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                        fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                    }
                }
                if (isContractType) {
                    if (it?.productInterestType?.name == "大额（单套大于1200万）" || it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域") {
                        if (it.contractType?.name == "委托借款代理服务合同") {
                            if (!it.installments) {
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            } else {
                                fuwufeilv += it.rate //费率金额 月
                            }
                        }

                    }
                }
            }
        }

        //计算总系费率息
        def total = lilv + qudaolv + fuwufeilv + fuwufeilv2
        def risker = ""
        opportunityFlows.each {
            if (it.opportunityLayout) {
                if (it.opportunityLayout.name == "opportunityLayout02") {
                    def stagecode = it.stage.code
                    opportunityRoles.each {
                        if (it.stage.code == stagecode) {
                            if (it.stage) {
                                risker += it.stage.name + ":" + it.user?.fullName + ","
                            }
                        }
                    }
                }
            }
        }

        //APP 贷款申请基本信息
        def apply = [:]
        def value1 = ""//借款人资质小结
        def value2 = ""//征信小结
        def value3 = ""//大数据小结
        OpportunityFlexField.each {
            if (it.name == "借款人资质小结") {
                value1 = it.value
            } else if (it.name == "征信小结") {
                value2 = it.value
            } else if (it.name == "大数据小结") {
                value3 = it.value
            }
        }
        apply.put("borrower_quality", value1)//借款人资质小结
        apply.put("credit_summary", value2)//征信小结
        apply.put("data_summary", value3)//大数据小结
        apply.put("appno", opportunity.serialNumber)//APP进单号
        apply.put("regperson_code", regpersoncode)//登记人号
        apply.put("regperson_name", regpersonname)//登记人名
        apply.put("regdt", regdt)//登记日期isborrow
        apply.put("ratemode", "1")//固定利率
        apply.put("term", term)//申请期限
        apply.put("comapplysum", comapplysum)//申请金额d

        apply.put("isborrow", isborrow)//还款方式  数据字典

        apply.put("feeratio", lilv + "")//利率d
        apply.put("floatratio", qudaolv + "")//渠道费率d
        apply.put("marginratio", fuwufeilv + "")//服务费率d
        apply.put("marginratio2", fuwufeilv2 + "")
        apply.put("rate", total + "")//息费率d
        apply.put("limitamt", advancePayment)//意向金d
        apply.put("curency", curency)//上扣息月数
        apply.put("regorg", "001001002")//登记机构
        apply.put("remark", "无")//意见   非必填
        apply.put("loanuse", "")//贷款用途类别
        apply.put("loanuse_explain", "其他")//贷款用途说明
        apply.put("approveamt", opportunity.actualAmountOfCredit * 10000)//批贷金额
        def groupname = com.next.Reporting.findByUser(opportunity.user)?.manager?.fullName//组长名字
        def manager = com.next.Reporting.findByUser(opportunity.user)?.manager//组长
        if (groupname) {
            apply.put("groupname", groupname)//销售组长姓名
            manager = com.next.Reporting.findByUser(manager)?.manager?.fullName
            if (manager) {
                apply.put("sale_manager", manager)//销售组长姓名
            } else {
                apply.put("sale_manager", "")//销售组长姓名
            }
        } else {
            apply.put("groupname", "")//销售组长姓名
            apply.put("sale_manager", "")//销售组长姓名
        }
        apply.put("saler", opportunity?.user?.fullName)//销售姓名
        apply.put("risker", risker)//风控主单姓名

        //借款人
        def custname
        def certiftype
        def certifid
        def certifcountry
        String brithdt
        def phone
        def marrstatus = "10"
        def customerchannel
        String accountname

        //抵押人
        def certifcountry1
        def idtype1
        def custname1
        def idno1

        def idNumberFormat = { String idnumber ->
            def birthday
            if (idnumber.length() == 18) {
                String year = idnumber.substring(6, 10)
                String month = idnumber.substring(10, 12)
                String day = idnumber.substring(12, 14)
                birthday = year + "-" + month + "-" + day
            }
            if (idnumber.length() == 15) {
                String year = idnumber.substring(6, 8)
                String month = idnumber.substring(8, 10)
                String day = idnumber.substring(10, 12)
                birthday = "19" + year + "-" + month + "-" + day

            }
            return birthday
        }

        def family = []
        opportunity.contacts.each {
            def relation
            def flybrithdt
            def flycertifid
            def flyycustname
            def marrystatus1
            def cellphone
            if (it.type) {
                if (it.type.name == "借款人" || it.type.name == "本人") {
                    custname = it.contact?.fullName
                    certiftype = "0"
                    certifid = it.contact.idNumber
                    certifcountry = "CHN"
                    brithdt = idNumberFormat(it.contact.idNumber)
                    phone = it.contact.cellphone
                    marrstatus = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "婚姻状况", it.contact?.maritalStatus)?.value2
                    if (it.contact && it.contact.account) {
                        accountname = it.contact.account.name
                        if (accountname.contains("中佳信")) {
                            customerchannel = "PC1600000001"
                        } else {
                            customerchannel = "PC1600000002"
                        }
                    } else {
                        customerchannel = "PC1600000002"
                    }

                }
                if (it.type.name == "抵押人") {
                    certifcountry1 = "CHN"
                    idtype1 = "0"
                    custname1 = it.contact?.fullName
                    idno1 = it.contact.idNumber
                }
                ////////////////////////////对私客户家庭成员信息

                if (it.type.name == "借款人") {
                    relation = "1"
                } else if (it.type.name == "借款人配偶") {
                    relation = "2"
                } else if (it.type.name == "抵押人") {
                    relation = "3"
                } else if (it.type.name == "抵押人配偶") {
                    relation = "4"
                } else if (it.type.name == "其他借款人") {
                    relation = "5"
                } else if (it.type.name == "借款人父母") {
                    relation = "6"
                } else if (it.type.name == "借款人子女") {
                    relation = "7"
                }

                flybrithdt = idNumberFormat(it.contact.idNumber)
                flycertifid = it.contact.idNumber
                flyycustname = it.contact?.fullName
                marrystatus1 = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "婚姻状况", it.contact.maritalStatus)?.value2
                cellphone = it.contact.cellphone
                ////////////////////////////对私客户家庭成员信息
            }
            def familyMap = [:]
            familyMap.put("appellation", "01")//成员称谓 共借人
            familyMap.put("relation", relation)
            familyMap.put("flycertifcountry", "CHN")//中国
            familyMap.put("flycertiftype", "0")//身份证
            familyMap.put("flybrithdt", flybrithdt)//出生日期
            familyMap.put("flycertifid", flycertifid)//证件号码
            familyMap.put("flyycustname", flyycustname)//姓名
            familyMap.put("marrstatus", marrystatus1)//户口本上的婚姻状况
            familyMap.put("valid", "20")//证件有效期
            familyMap.put("houseregisaddr", "")//户籍所在地
            familyMap.put("usedname", "")//曾用名
            familyMap.put("bookletnumber", "")//户口本号码
            familyMap.put("phone_no", cellphone)//手机号码
            family.add(familyMap)
        }
        //////////////////////////////////// //对私客户信息
        def custom = [:]
        custom.put("custname", custname)//客户名称
        custom.put("certiftype", certiftype)//证件类型 身份证
        custom.put("certifid", certifid)//证件号码
        custom.put("certifcountry", certifcountry)//发证国家
        custom.put("brithdt", brithdt)//出生日期
        custom.put("phone", phone)//电话
        custom.put("marrstatus", marrstatus)//婚姻状态 如果是离婚则提供离婚证明
        custom.put("customerchannel", customerchannel)//客户渠道
        //////////////////////////////////////////////////
        custom.put("houseregisaddr", "110100")//户籍所在地
        custom.put("bookletnumber", "1111111111")//户口本号码
        custom.put("usedname", "无")//曾用名  可选
        custom.put("valid", "20")//证件有效期
        //新增
        custom.put("address", "")//通讯地址
        custom.put("education", "")//学历
        custom.put("monthly_income", "")//月收入
        custom.put("sex", "")//性别  暂无

        //////////////////////////////////  //抵押品所有权人信息
        def owner = []

        def evaluate = []

        /////////////////////////////抵押品信息
        def gageList = []
        opportunity.collaterals.each {
            def evalvalue = opportunity.loanAmount
            def evaluateMap = [:]
            evaluateMap.put("evafs", "1")//评估方式 没有
            evaluateMap.put("evaloutfit", "中佳信")//评估机构
            evaluateMap.put("evalvalue", evalvalue)//评估价值
            evaluateMap.put("mortgage_no", it.externalId)
            evaluate.add(evaluateMap)

            def ownerMap = [:]
            if (custname1) {
                ownerMap.put("certifcountry", certifcountry1)//国籍
                ownerMap.put("idtype", idtype1)//证件类型
                ownerMap.put("custname", custname1)//所有权人姓名
                ownerMap.put("idno", idno1)//证件号码
                ownerMap.put("mortgage_no", it.externalId)
                owner.add(ownerMap)
            } else {
                ownerMap.put("certifcountry", certifcountry)//国籍
                ownerMap.put("idtype", certiftype)//证件类型
                ownerMap.put("custname", custname)//所有权人姓名
                ownerMap.put("idno", certifid)//证件号码
                ownerMap.put("mortgage_no", it.externalId)
                owner.add(ownerMap)
            }



            def houseproperty = "0"
            def houseregisaddrtext = "110000"
            def mortgagetype = "0"
            def specificfactor = "0"
            def towards = "10"
            def buildingtype = "0"
            def buildingarea = it.area
            def buildingname = it.projectName
            def buildingno = it.propertySerialNumber
            def buildingonfloor = it.floor
            buildingtype = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "房产类型", it.assetType)?.value2
            def firstmortgagemoney = it.firstMortgageAmount
            def firstmortgagetype = "1"
            if (it.typeOfFirstMortgage?.name == "银行类") {
                firstmortgagetype = "0"
            }
            def floorridgepole = it.building
            houseproperty = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "房屋类型", it.houseType)?.value2
            if (it.city) {
                houseregisaddrtext = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "城市", it.city.name)?.value2

            }

            def housetype = "1"  //字段修改 找不到了
            if (it.mortgageType) {
                mortgagetype = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "抵押类型", it.mortgageType.name)?.value2
            }
            def roomnumber = it.roomNumber
            def secondmortgagemoney = it.secondMortgageAmount
            if (it.specialFactors) {
                specificfactor = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "特殊因素", it.specialFactors)?.value2
            }
            def totalfloor = it.totalFloor
            towards = com.next.ExternalDataMapping.findBySystemNameAndCategoryNameAndValue1("火凤凰", "房屋朝向", it.orientation)?.value2
            def unitprice = it.unitPrice
            def district = com.next.District.findByCityAndName(it.city, it.district)?.code
            def mortgageaddr = it.address
            def buildTime = "9999-01-01"
            if (it.buildTime) {
                buildTime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(it.buildTime)
            }
            def gage = [:]
            gage.put("buildingarea", buildingarea)//建筑面firstMortgageAmount
            gage.put("buildingname", buildingname)//；楼盘名称
            gage.put("buildingonfloor", buildingonfloor)//抵押房产所在楼层
            gage.put("buildingtype", buildingtype)//房产类型  数据字典
            gage.put("firstmortgagemoney", firstmortgagemoney)//一抵金额d
            gage.put("firstmortgagetype", firstmortgagetype)//首资抵押类型
            gage.put("floorridgepole", floorridgepole)//楼栋
            gage.put("houseproperty", houseproperty)//房屋类型
            gage.put("houseregisaddrtext", houseregisaddrtext)//城市编码中文名  数据字典
            gage.put("housetype", housetype)//户型  数据字典
            gage.put("mortgagetype", mortgagetype)//抵押类型  数据字典
            gage.put("roomnumber", roomnumber)//房号
            gage.put("secondmortgagemoney", secondmortgagemoney)//二抵金额d
            gage.put("specificfactor", specificfactor)//特殊因素 数据字典
            gage.put("totalfloor", totalfloor)//总楼层
            gage.put("towards", towards)//房屋朝向 数据字典
            gage.put("unitprice", unitprice)//单价d
            gage.put("buildingno", buildingno)//房产证号码
            gage.put("onemortgagetype", "2")//一抵类型
            gage.put("onemortgagedeadline", "4")//一抵押期限
            gage.put("secondmortgagedeadline", "7")//二抵押期限
            gage.put("buildingstates", "2")//房产状态
            gage.put("mortgageaddr", mortgageaddr)//抵押物地址
            gage.put("mortgagerate", it.loanToValue)//抵押率d
            gage.put("area", district)//区域
            gage.put("lease", "")//是否出租
            gage.put("build_date", buildTime)//建成时间
            if (it.buildTime) {
                gage.put("ageofhouse", new java.util.Date().year - new java.text.SimpleDateFormat("yyyy-MM-dd").parse("2016-12-08 00:00:00").year)
//房龄
            } else {
                gage.put("ageofhouse", "")//房龄
            }

            gage.put("mortgage_no", it.externalId)
            if (it.region) {
                if (it.region.name == "六环外") {
                    gage.put("location", "2")//位置信息
                } else {
                    gage.put("location", "1")//位置信息
                }
            } else {
                gage.put("location", "0")//位置信息
            }

            gageList.add(gage)
        }

        //抵押品评估信息

        //第三方征信信息
        def credit = []
        def creditMap = [:]
        creditMap.put("provider", "央行征信")//提供商
        creditMap.put("name", "征信信息见附件")//名称
        creditMap.put("value", "5")//数据值
        credit.add(creditMap)

        //评分信息
        def score = []
        def scoreMap = [:]
        /*scoreMap.put("name", "评分信息")//名称
        scoreMap.put("value", "见附件")//数据值*/
        scoreMap.put("name", "01")//名称


        def level
        if (opportunity.lender.level) {
            level = opportunity.lender.level.name
        }
        if (level == "A") {
            level = "0101"
        } else if (level == "B") {
            level = "0102"
        } else if (level == "C") {
            level = "0103"
        } else if (level == "D") {
            level = "0104"
        }
        scoreMap.put("value", level)//数据值
        score.add(scoreMap)

        //产调信息
        def survey = []
        def surveyMap = [:]
        surveyMap.put("name", "产调信息见附件")//名称
        surveyMap.put("value", "5")//数值
        survey.add(surveyMap)

        //产品信息
        def productaccount = [:]
        if (opportunity.productAccount) {
            productaccount.put("name", opportunity.productAccount.name)//名称
            if (opportunity.productAccount.account) {
                productaccount.put("account", opportunity.productAccount.account.name)//机构
            } else {
                productaccount.put("account", "")//机构
            }

            def active = "有效"
            if (opportunity.productAccount.active == true) {
                active = "有效"
            } else {
                active = "无效"
            }
            productaccount.put("active", active)//有效性
            def createdDate
            if (opportunity.productAccount.createdDate) {
                createdDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(opportunity.productAccount.createdDate)
            }
            def modifiedDate
            if (opportunity.productAccount.modifiedDate) {
                modifiedDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(opportunity.productAccount.modifiedDate)
            }

            productaccount.put("createddate", createdDate)//创建时间
            productaccount.put("modifieddate", modifiedDate)//修改时间
            if (opportunity.productAccount.principalPaymentMethod) {
                productaccount.put("principalpaymentmethod", opportunity.productAccount.principalPaymentMethod.name)
//本金支付方式
            } else {
                productaccount.put("principalpaymentmethod", "")//本金支付方式
            }

            productaccount.put("description", opportunity.productAccount?.description)//描述
            productaccount.put("number", opportunity.productAccount?.product?.id)//产品编号
        } else {
            productaccount.put("name", "无")//名称
            productaccount.put("account", "无")//机构
            productaccount.put("active", true)//有效性
            productaccount.put("createddate", "2017-01-01")//创建时间
            productaccount.put("modifieddate", "2017-01-01")//修改时间
            productaccount.put("principalpaymentmethod", "无")//本金支付方式
            productaccount.put("description", "")//描述
            productaccount.put("number", "")//产品编号
        }




        def opportunityflows = []
        opportunityFlows.each {
            def opportunityflowsMAp = [:]
            opportunityflowsMAp.put("comments", it.comments)//建议
            opportunityflowsMAp.put("stage", it.stage?.name)//1阶段名称
            def name
            def stage = it.stage.code
            opportunityRoles.each {
                if (stage == it.stage?.code) {
                    name = it.user?.fullName
                }
            }
            opportunityflowsMAp.put("name", name)//姓名
            opportunityflows.add(opportunityflowsMAp)

        }

        def activities = []
        activitity.each {
            def activitiesMap = [:]
            if (it.type) {
                activitiesMap.put("type", it.type.name)//类型
            } else {
                activitiesMap.put("type", "")//类型
            }
            if (it.subtype) {
                activitiesMap.put("subtype", it.subtype.name)//子类型
            } else {
                activitiesMap.put("subtype", "")//类型
            }


            def starttime
            if (it.startTime) {
                starttime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(it.startTime)
            }
            def endtime
            if (it.endTime) {
                endtime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(it.endTime)
            }
            activitiesMap.put("starttime", starttime)//开始时间
            activitiesMap.put("endtime", endtime)//结束时间
            def status
            if (it.status == "Pending") {
                status = "开始"
            } else if (it.status == "Delayed") {
                status = "延期"
            } else if (it.status == "Completed") {
                status = "完成"
            } else if (it.status == "Canceled") {
                status = "取消"
            }
            activitiesMap.put("status", status)//状态
            def user
            if (it.user) {
                user = it.user?.fullName
            } else {
                user = "无"
            }
            activitiesMap.put("user", user)//外访发起人
            activitiesMap.put("contact", it.contact?.fullName)//客户名称
            activitiesMap.put("assignedto", it.assignedTo?.fullName)//下户人
            activities.add(activitiesMap)
        }

        //数据都放入一个map中转换成JSON
        def paramsMap = [:]
        paramsMap.put("apply", apply)
        paramsMap.put("custom", custom)
        paramsMap.put("evaluate", evaluate)
        paramsMap.put("family", family)
        paramsMap.put("gage", gageList)
        paramsMap.put("owner", owner)
        paramsMap.put("score", score)
        paramsMap.put("survey", survey)
        paramsMap.put("credit", credit)
        paramsMap.put("product", productaccount)
        paramsMap.put("activities", activities)
        paramsMap.put("trust_program", trust_program)
        paramsMap.put("bank", bank)
        paramsMap.put("contract", contract)
        paramsMap.put("loan", loan)
        paramsMap.put("storage", storage)
        paramsMap.put("sub", sub)
        paramsMap.put("files", files)
        paramsMap.put("username", "")
        paramsMap.put("password", "")
        paramsMap.put("opportunityflows", opportunityflows)
        String json = groovy.json.JsonOutput.toJson(paramsMap).toString()
        def sendPost = { String urlString, String params1 ->
            URL url = new java.net.URL(urlString)
            def result
            try {
                def connection = (java.net.HttpURLConnection) url.openConnection()
                connection.setDoOutput(true)
                connection.setRequestMethod("POST")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write params1 }
                connection.setConnectTimeout(10000)
                result = grails.converters.JSON.parse(connection.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
                println("返回结果" + result)
            }
            catch (java.lang.Exception e) {
                e.printStackTrace()
                println e
            }
            return result
        }
        def result = sendPost(com.next.CreditReportProvider.findByCode("HUOFH")?.apiUrl+"/m/huofhservice/interface.do?reqCode=apiLoan", json)
        return result
    }
    //火凤凰他向证明发送
    def taxiang = { opportunity ->
        def apply = [:]
        def regpersoncode = opportunity?.contact?.cellphone
        def regpersonname = opportunity?.contact?.fullName
        def regdt = ""
        if (opportunity.createdDate)
        {
            regdt = new java.text.SimpleDateFormat("yyyy-MM-dd").format(opportunity.createdDate)
        }
        apply.put("appno", opportunity.serialNumber) //APP进单号
        apply.put("regperson_code", regpersoncode) //登记人号
        apply.put("regperson_name", regpersonname) //登记人名
        apply.put("regdt", regdt) //登记日期isborrow
        apply.put("regorg", "001001002") //登记机构

        def files = []
        opportunity.attachments.each {
            def file = [:]
            if (it.type.name == "他项证明")
            {
                file.put("name", it.type?.name)
                file.put("value", it.fileName)
                files.add(file)
            }
        }
        def loanApprovalMap = [:]
        loanApprovalMap.put("apply", apply)
        loanApprovalMap.put("files", files)
        String loanApproval = groovy.json.JsonOutput.toJson(loanApprovalMap).toString()

        def sendPost = { String urlString, String params1 ->
            URL url = new java.net.URL(urlString)
            def result
            try
            {
                def connection = (java.net.HttpURLConnection) url.openConnection()
                connection.setDoOutput(true)
                connection.setRequestMethod("POST")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write params1 }
                connection.setConnectTimeout(10000)
                result = grails.converters.JSON.parse(connection.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
                println("返回结果" + result)
            }
            catch (java.lang.Exception e)
            {
                e.printStackTrace()
                println e
            }
            return result
        }
        def res = sendPost("http://11.0.12.102/m/huofhservice/interface.do?reqCode=apiApply", loanApproval)
        return res
    }

    def mail =
        { opportunity ->
            InetAddress address = InetAddress.getLocalHost();
            String hostAddress = address.getHostAddress()
            return hostAddress
        }

    def chongxinshengc =
        { opportunity ->
            if (opportunity?.type.name != "抵押贷款")
            {
                opportunity = opportunity?.parent
            }
            //订单期数
            java.util.Calendar calendar = java.util.Calendar.getInstance()
            calendar.clear()
            calendar.setTime(opportunity?.actualLendingDate)
            int day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
            //上扣息月份数
            def firstPayOfMonthes = opportunity?.monthOfAdvancePaymentOfInterest?.intValue() == 0 ? 1 : opportunity?.monthOfAdvancePaymentOfInterest?.intValue()
            if (day >= 15 && day < 20)
            {
                if (opportunity?.interestPaymentMethod?.name == "扣息差")
                {
                    firstPayOfMonthes = 2
                }
            }
            if (day == 20)
            {
                firstPayOfMonthes += 1
            }
            def bills = com.next.Bills.findByOpportunity(opportunity)
            def billsItems = com.next.BillsItem.findAllByBillsAndOverdue(bills, true)
            billsItems.each {
                it.overdue = false
                it.status = "待收"
                it.transactionRecord.status = com.next.TransactionRecordStatus.findByName("未执行")
                it.save()
            }
            billsItems = com.next.BillsItem.findAllByBillsAndPrepayment(bills, true)
            billsItems.each {
                it.prepayment = false
                it.status = "待收"
                it.transactionRecord.status = com.next.TransactionRecordStatus.findByName("未执行")
                it.save()
            }
            billsItems = com.next.BillsItem.findAllByBillsAndSplit(bills, true)
            billsItems.each {
                it.split = false
                it.status = "待收"
                it.transactionRecord.status = com.next.TransactionRecordStatus.findByName("未执行")
                it.save()
            }
            billsItems = com.next.BillsItem.findAllByBillsAndPeriodAndType(bills, opportunity?.actualLoanDuration - firstPayOfMonthes + 1, com.next.BillsItemType.findByName("利息违约金"))
            billsItems.each {
                def transactionRecord = com.next.TransactionRecord.findById(it.transactionRecordId)
                transactionRecord.amount -= it.receivable
                transactionRecord.save()
                it.delete()
                if (transactionRecord.amount == 0)
                {
                    transactionRecord.delete()
                }
            }
            billsItems = com.next.BillsItem.findAllByBillsAndPeriod(bills, opportunity?.actualLoanDuration - firstPayOfMonthes + 2)
            billsItems.each {
                def transactionRecord = com.next.TransactionRecord.findById(it.transactionRecordId)
                it.delete()
                transactionRecord.delete()
            }
        }
    //还款计划加到第二期  boshi
    def abss = { opportunity ->
        def bills
        if (opportunity?.type.name == "抵押贷款")
        {
            bills = com.next.Bills.findByOpportunity(opportunity)
        }
        else
        {
            bills = com.next.Bills.findByOpportunity(opportunity?.parent)
        }
        if (bills == null)
        {
            println "还款计划不存在！！！"
        }
        def billsItems = com.next.BillsItem.findAllByBillsAndPeriod(bills, 0)
        def sumReceivable = billsItems.receivable.sum()
        if (sumReceivable < 0)
        {
            billsItems.each {
                it.status = "已收"
                it.actualEndTime = it.transactionRecord.plannedStartTime
                it.save()
            }
            def billsItems1 = com.next.BillsItem.findByBillsAndPeriod(bills, 0)
            billsItems1.balance = sumReceivable
            billsItems1.balance = new java.math.BigDecimal(Double.toString(billsItems1.balance)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
            billsItems1.transactionRecord.status = com.next.TransactionRecordStatus.findByName("已成功")
            billsItems1.transactionRecord.startTime = new Date()
            billsItems1.transactionRecord.endTime = new Date()
            def billsItems2 = com.next.BillsItem.findByBillsAndPeriod(bills, 1)
            billsItems2.receivable += sumReceivable
            billsItems2.receivable = new java.math.BigDecimal(Double.toString(billsItems2.receivable)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
            billsItems2.transactionRecord.amount += billsItems1.transactionRecord.amount
            billsItems2.transactionRecord.amount = new java.math.BigDecimal(Double.toString(billsItems2.transactionRecord.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
            billsItems1.save()
            billsItems2.save()
        }

    }

    def tt = {
        def opportunities = com.next.Opportunity.findAll("from Opportunity where type.name = '抵押贷款' and actualLendingDate between '20160706' and '20160707' and actualLoanAmount != 0")
        opportunities.each {
            def list = [com.next.TransactionRecordStatus.findByName("未执行"), com.next.TransactionRecordStatus.findByName("待确认")]
            def transactionRecord = com.next.TransactionRecord.findAllByOpportunityAndStatusNotInList(it, list)
            if (transactionRecord.size() > 0)
            {
                println "删除交易记录"
                def BillsItem = com.next.BillsItem.findAllByBills(Bills.findByOpportunity(it))
                BillsItem.each {
                    it.delete()
                }

            }
            if (transactionRecord.size() > 0)
            {
                transactionRecord.each {
                    it.delete()
                }
            }
        }
    }
}

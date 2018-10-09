package com.next

import grails.transaction.Transactional

@Transactional
class BillsImportService
{
    BillsService billsService
    ComponentService componentService

    def oldDeal(Opportunity opportunity, Double actualAmountOfCredit, Date actualEndTime)
    {
        //Bills bills = opportunity.bills
        def bills = Bills.find("from Bills where opportunity.id = ${opportunity?.id}")
        if (!bills)
        {
            println "没有找到还款计划！！！"
            return false
        }
        double advancePayment = 0.0
        // 意向金
        if (opportunity.advancePayment != null)
        {
            advancePayment = opportunity.advancePayment
        }
        else
        {
            def interests = OpportunityProduct.findAll("from OpportunityProduct where opportunity.id = ${opportunity?.id}")
            if (interests)
            {
                interests?.each {
                    if (it.productInterestType == ProductInterestType.findByName("意向金"))
                    {
                        advancePayment = it.rate
                    }
                }
            }
        }
        println "意向金为：" + advancePayment

        Double money = actualAmountOfCredit / 10000
        println "该笔流水金额为：" + money
        def includePrincipal = false
        def billsItemList = BillsItem.findAll("from BillsItem as a where a.status='待收' and a.bills.id=${bills.id}")
        if (billsItemList.size() > 0)
        {
            Integer period = billsItemList[0].period
            Double sum = 0
            //Integer size = 0
            //billsItemList.each {
            // 计算本期总的待还金额
            for (
                def i = 0;
                    i < billsItemList.size();
                    i++)
            {
                if (billsItemList[i].period == period)
                {
                    billsItemList[i].receivable = (double) Math.round(billsItemList[i].receivable * 10e5) / 10e5
                    // 金额保留到分
                    if (i > 0 && billsItemList[i].receivable > 0 && billsItemList[i].type.name == '本金')
                    {
                        includePrincipal = true
                    }
                    if (!includePrincipal || (includePrincipal && billsItemList[i].type.name == '利息违约金'))
                    {
                        sum += billsItemList[i].receivable
                        println "本笔应还金额为：" + billsItemList[i].receivable + ", 类型为：" + billsItemList[i].type.name
                    }
                }
                else
                {
                    continue
                }
            }

            if (period == 0 && advancePayment > 0)
            // 处理意向金
            {
                money += advancePayment / 10000
            }

            if (money + 1e-10 > sum)
            // 流水与本期还款计划匹配
            {
                if (money / sum > 20)
                {
                    println "待还金额为：" + sum + "，实收金额为：" + money + "，本次流水疑为早偿，需人工处理！！！"
                    return false
                }
                Integer index
                for (
                    def i = 0;
                        i < billsItemList.size();
                        i++)
                {
                    BillsItem billsItem = billsItemList[i]
                    if (billsItem.period != period || (i > 0 && billsItemList[i].receivable > 0 && billsItemList[i].type.name == '本金'))
                    {
                        continue
                    }
                    billsItem.receipts = billsItem.receivable
                    billsItem.actualEndTime = actualEndTime
                    //if (i == size - 1)
                    //{
                    //    billsItem.balance = money - sum
                    //}
                    billsItem.status = "已收"
                    billsItem.save flush: true
                    index = i
                }
                println "本次流水匹配成功，待还金额为：" + sum + "，实收金额为：" + money
                if (money - sum > 1e-6)
                {
                    BillsItemList[index].balance = money - sum
                }
                return true
            }
            else
            // 流水没有匹配上账单，本次流水待查
            {
                println "本次流水自动匹配失败，需人工处理！！！"
                return false
            }

            /* BillsItem billsItem = billsItemList[i]
            Double receivable = billsItem.receivable
            println billsItem.type.name + ": " + receivable + "  @  " + money
            if (money + 1e-10 >= receivable)
            {
                billsItem.receipts = receivable
                billsItem.actualEndTime = actualEndTime
                billsItem.status = "已收"
                billsItem.save flush: true
                money -= receivable
                period = billsItem.period
            }
            else
            {
                if (billsItem.period == period)
                // 流水没有匹配上账单，本次流水待查
                {
                    billsItem.receipts = money
                    billsItem.balance = (money - receivable)
                    billsItem.save flush: true
                    println "本次流水匹配失败！！！"
                    return false
                }
                else
                {
                    println "本次流水匹配的还款计划条数为：" + i
                    return true
                }
            } */
        }
        else
        {
            println "没有找到待收的还款计划！！！"
            return false
        }
    }

    def deal(Opportunity opportunity, Double actualAmountOfCredit, Date actualEndTime, String debitAccount)
    {
        Double money = actualAmountOfCredit / 10000
        println "该笔流水金额为：" + money
        Double principal = opportunity.actualLoanAmount
        // 存量流水归本时处理早偿和逾期
        if (money / principal > 0.95)
        {
            println "放款金额为：" + principal
            Boolean useEvent = billsService.alreadyDealPrePayOrOverdue(opportunity)
            if (!useEvent)
            {
                opportunity.actuaRepaymentDate = actualEndTime
                def result
                def component = Component.findByName("历史利息违约金生成1")
                if (component)
                {
                    result = componentService.evaluate component, opportunity
                }
                if (result instanceof Exception)
                {
                    println "历史利息违约金生成失败！！！"
                    log.error "SystemComponent error!"
                }

                component = Component.findByName("结清计划1")
                if (component)
                {
                    result = componentService.evaluate component, opportunity
                }
                if (result instanceof Exception)
                {
                    println "还款计划更新失败！！！"
                    log.error "SystemComponent error!"
                }

                component = Component.findByName("结清交易记录1")
                if (component)
                {
                    result = componentService.evaluate component, opportunity
                }
                if (result instanceof Exception)
                {
                    println "交易记录更新失败！！！"
                    log.error "SystemComponent error!"
                }
            }
        }

        def records = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.id} and status.name in ('未执行','扣款失败')")
        if (records.size() > 0)
        {
            for (
                def i = 0;
                    i < records.size();
                    i++)
            {
                TransactionRecord record = records[i]
                record.plannedStartTime.setHours(23)
                record.plannedStartTime.setMinutes(59)
                record.plannedStartTime.setSeconds(59)
                //println "record.amount: " + record.amount + "record.plannedStartTime: " + record.plannedStartTime

                if (records.size() > 2 && i < records.size() - 2)
                {
                    if (actualEndTime > records[i + 1].plannedStartTime - 1) // 在下一期开始时间之后，继续
                    {
                        continue
                    }
                }
                if (actualEndTime >= record.plannedStartTime - 1 || record.type?.name == "还本" || money / principal > 0.95)
                {
                    if (Math.abs(money - record.amount) < 1.1e-6)
                    {
                        record.endTime = actualEndTime
                        record.debitAccount = debitAccount
                        record.status = TransactionRecordStatus.findByName("已成功")
                        record.save flush: true
                        Boolean flag = billsService.updateBillsFromRecord(opportunity, record)
                        if (flag)
                        {
                            println "本次流水匹配成功，待还金额为：" + record.amount + "，实收金额为：" + money
                        }
                        return true
                    }
                    else if (money > record.amount && i < records.size() - 2 && money / principal < 0.2)
                    {
                        double balance = money - record.amount
                        if (balance >= records[i + 1].amount)
                        {
                            return false
                        }
                        record.amount = money
                        record.amount = new java.math.BigDecimal(Double.toString(record.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        record.endTime = actualEndTime
                        record.debitAccount = debitAccount
                        record.status = TransactionRecordStatus.findByName("已成功")
                        record.save flush: true
                        records[i + 1].amount -= balance
                        records[i + 1].amount = new java.math.BigDecimal(Double.toString(records[i + 1].amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        records[i + 1].save flush: true
                        Boolean flag = billsService.updateBillsFromRecord(opportunity, record)
                        if (flag)
                        {
                            println "本次流水匹配成功，待还金额为：" + record.amount + "，实收金额为：" + money
                        }
                        return true
                    }
                }
            }
            println "本次流水匹配失败，扣款金额为：" + money + ", 扣款日期为：" + actualEndTime
            return false
        }
        else
        {
            println "没有找到未执行的交易记录！！！"
            return false
        }

    }
}

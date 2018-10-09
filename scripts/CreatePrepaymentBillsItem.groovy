/**
 * Created by Nagelan on 2017/6/23 0023.*/
{ opportunity ->
    def saveBillsItem = {
        def billsItem, def status, def period, def startTime, def endTime, def balance, def credit, def debit ->
            billsItem.status = status
            billsItem.period = period
            billsItem.startTime = startTime
            billsItem.endTime = endTime
            billsItem.balance = balance
            billsItem.credit = credit
            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
            billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
            billsItem.debit = debit
            if (billsItem.validate())
            {
                billsItem.save()
                print("1")
            }
            else
            {
                println(billsItem.errors)
            }
    }

    //查找父订单还款计划
    def actuaRepaymentDate = opportunity?.actuaRepaymentDate
    def opportunityChild = opportunity
    if (opportunity?.type.name != "抵押贷款")
    {
        opportunity = opportunity?.parent
    }
    def bills = com.next.Bills.findByOpportunity(opportunity)
    def principal
    if (bills?.capital)
    {
        principal = bills?.capital
    }
    else if (opportunity?.actualLoanAmount)
    {
        principal = opportunity?.actualLoanAmount
    }
    else if (opportunity?.actualAmountOfCredit)
    {
        principal = opportunity?.actualAmountOfCredit
    }
    def principalItem = com.next.BillsItem.findByBillsAndType(bills, com.next.BillsItemType.findByName("本金"), [sort: "id", order: "desc"])
    if (actuaRepaymentDate && principalItem && principal)
    {
        def day = (principalItem.endTime.getTime() - actuaRepaymentDate.getTime()) / (1000 * 3600 * 24)
        def normalOpportunityProduct = com.next.OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, com.next.ProductInterestType.findByName("基本费率"))
        if (day < 0)
        {
            println("*******进入逾期还款计划生产*******")
            def rate = com.next.OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, com.next.ProductInterestType.findByName("本金违约金"))?.rate
            if (!rate && normalOpportunityProduct)
            {
                def nop = new com.next.OpportunityProduct(opportunity: opportunityChild, productInterestType: com.next.ProductInterestType.findByName("本金违约金"))
                nop.product = normalOpportunityProduct.product
                nop.rate = 0.1
                nop.externalId = null
                nop.monthes = normalOpportunityProduct.monthes
                nop.installments = false
                nop.contractType = normalOpportunityProduct.contractType
                nop.createBy = normalOpportunityProduct.createBy
                nop.fixedRate = normalOpportunityProduct.fixedRate
                nop.firstPayMonthes = 0
                nop.radixProductInterestType = normalOpportunityProduct.radixProductInterestType
                nop.save()
                nop = new com.next.OpportunityProduct(opportunity: opportunity, productInterestType: com.next.ProductInterestType.findByName("本金违约金"))
                nop.product = normalOpportunityProduct.product
                nop.rate = 0.1
                nop.externalId = null
                nop.monthes = normalOpportunityProduct.monthes
                nop.installments = false
                nop.contractType = normalOpportunityProduct.contractType
                nop.createBy = normalOpportunityProduct.createBy
                nop.fixedRate = normalOpportunityProduct.fixedRate
                nop.firstPayMonthes = 0
                nop.radixProductInterestType = normalOpportunityProduct.radixProductInterestType
                nop.save()
                rate = 0.1
            }
            rate = 0.1
            if (rate)
            {
                def oldItems
                //对本金的计算
                def actualLoanAmount = 0
                oldItems = com.next.BillsItem.findAllByBillsAndTypeAndStatusNotEqual(bills, com.next.BillsItemType.findByName("本金"), "已收")
                oldItems?.each {
                    actualLoanAmount = actualLoanAmount + it.receivable
                }
                //修改还款计划之后的状态
                oldItems = com.next.BillsItem.findAllByBillsAndStatusNotEqual(bills, "已收")
                oldItems?.each {
                    if (it.type != com.next.BillsItemType.findByName("利息违约金") && it.type != com.next.BillsItemType.findByName("服务费费率") && it.type != com.next.BillsItemType.findByName("渠道返费费率"))
                    {
                        it.overdue = true
                        it.status = '已收'
                        it.save()
                    }
                    else
                    {
                        it.startTime = actuaRepaymentDate
                        it.endTime = actuaRepaymentDate
                        it.period = principalItem?.period + 1
                        it.save()
                    }
                }
                def rate1 = 0
                def ops = com.next.OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                ops.each {
                    if (it.productInterestType.name != "利息违约金" && it.productInterestType.name != "早偿违约金" && it.productInterestType.name != "本金违约金")
                    {
                        if (it.installments)
                        {
                            rate1 += it.rate
                        }
                        else
                        {
                            if (opportunity?.actualLoanDuration)
                            {
                                rate1 += it.rate / opportunity?.actualLoanDuration
                            }
                        }
                    }
                }
                def credit = principalItem.credit
                def debit = principalItem.debit
                //计算违约金
                def billsItem
                def startTime = actuaRepaymentDate
                def endTime = actuaRepaymentDate
                def period = principalItem?.period + 1
                def status = '待收'
                def balance = 0
                def days
                oldItems = com.next.BillsItem.findAllByBillsAndStatusAndOverdue(bills, "已收", true)
                oldItems.each {
                    days = 0
                    if (it.type.name != "利息违约金" && it.type.name != "服务费费率" && it.type.name != "渠道返费费率")
                    {
                        if (it.type.name == "本金")
                        {
                            days = (actuaRepaymentDate?.getTime() - it.endTime.getTime()) / (1000 * 3600 * 24)
                            billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName('本金违约金'))
                            billsItem.receivable = actualLoanAmount * rate * days / 100
                        }
                        else
                        {
                            days = (actuaRepaymentDate?.getTime() - it.startTime.getTime()) / (1000 * 3600 * 24)

                            java.util.Calendar c1 = java.util.Calendar.getInstance()
                            c1.setTime(actuaRepaymentDate)
                            java.util.Calendar c2 = java.util.Calendar.getInstance()
                            c2.setTime(it.startTime)
                            def dayOfWeek1 = c2.get(java.util.Calendar.DAY_OF_WEEK)
                            if (it.period == 0)
                            {
                                if (dayOfWeek1 == 6)
                                {
                                    if (days <= 3)
                                    {
                                        days = 0
                                    }
                                    else if (days > 3)
                                    {
                                        days -= 1
                                    }
                                }
                                else if (dayOfWeek1 == 7)
                                {
                                    if (days <= 2)
                                    {
                                        days = 0
                                    }
                                    else if (days > 2)
                                    {
                                        days -= 1
                                    }
                                }
                                else
                                {
                                    days -= 1
                                }
                            }
                            else
                            {
                                if (days == 2)
                                {
                                    if (dayOfWeek1 == 7)
                                    {
                                        days -= 2
                                    }
                                }
                                else if (days == 1)
                                {
                                    if (dayOfWeek1 == 1)
                                    {
                                        days -= 1
                                    }
                                }
                            }

                            billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName('利息违约金'))
                            billsItem.receivable = it.receivable * rate * days / 100
                        }
                        billsItem.receipts = 0
                        saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                    }
                }
                /*//本金利息应还
                billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName('逾期本金利息'))
                days = (actuaRepaymentDate?.getTime()-principalItem?.endTime.getTime())/(1000 * 3600 * 24)
                billsItem.receivable = actualLoanAmount*days*rate1/100
                billsItem.receipts = 0
                saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)*/
                //本金应还
                billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName('本金'))
                billsItem.receivable = actualLoanAmount
                billsItem.receipts = 0
                saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
            }
            else
            {
                println("订单" + opportunity?.serialNumber + "没有本金违约金费率")
                return "订单" + opportunity?.serialNumber + "没有本金违约金费率"
            }
            println("*******逾期还款计划生成完成*******")
        }
        else
        {
            def oldItems = com.next.BillsItem.findByBillsAndStatusAndTypeNotEqualAndTypeNotEqual(bills, "待收", com.next.BillsItemType.findByName("本金"), com.next.BillsItemType.findByName("利息违约金"))
            if (oldItems)
            {
                println("*******进入早偿还款计划生产*******")
                def rate = com.next.OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, com.next.ProductInterestType.findByName("早偿违约金"))?.rate
                if (!rate && normalOpportunityProduct)
                {
                    def nop = new com.next.OpportunityProduct(opportunity: opportunityChild, productInterestType: com.next.ProductInterestType.findByName("早偿违约金"))
                    nop.product = normalOpportunityProduct.product
                    nop.rate = 0.25
                    nop.externalId = null
                    nop.monthes = normalOpportunityProduct.monthes
                    nop.installments = false
                    nop.contractType = normalOpportunityProduct.contractType
                    nop.createBy = normalOpportunityProduct.createBy
                    nop.fixedRate = normalOpportunityProduct.fixedRate
                    nop.firstPayMonthes = 0
                    nop.radixProductInterestType = normalOpportunityProduct.radixProductInterestType
                    nop.save()
                    nop = new com.next.OpportunityProduct(opportunity: opportunity, productInterestType: com.next.ProductInterestType.findByName("本金违约金"))
                    nop.product = normalOpportunityProduct.product
                    nop.rate = 0.25
                    nop.externalId = null
                    nop.monthes = normalOpportunityProduct.monthes
                    nop.installments = false
                    nop.contractType = normalOpportunityProduct.contractType
                    nop.createBy = normalOpportunityProduct.createBy
                    nop.fixedRate = normalOpportunityProduct.fixedRate
                    nop.firstPayMonthes = 0
                    nop.radixProductInterestType = normalOpportunityProduct.radixProductInterestType
                    nop.save()
                    rate = 0.25
                }
                if (rate)
                {
                    //对本金的计算
                    def actualLoanAmount = 0
                    oldItems = com.next.BillsItem.findAllByBillsAndTypeAndStatusNotEqual(bills, com.next.BillsItemType.findByName("本金"), "已收")
                    oldItems?.each {
                        actualLoanAmount = actualLoanAmount + it.receivable
                    }
                    //修改还款计划之后的状态
                    if (day > 30)
                    {
                        oldItems = com.next.BillsItem.findAllByBillsAndStatusNotEqual(bills, "已收")
                        oldItems?.each {
                            if (it.type != com.next.BillsItemType.findByName("利息违约金"))
                            {
                                it.prepayment = true
                                it.status = '已收'
                                it.save()
                            }
                            else
                            {
                                it.startTime = actuaRepaymentDate
                                it.endTime = actuaRepaymentDate
                                it.period = principalItem?.period + 1
                                it.save()
                            }
                        }
                    }
                    else
                    {
                        oldItems = com.next.BillsItem.findAllByBillsAndType(bills, com.next.BillsItemType.findByName("本金"))
                        oldItems?.each {
                            it.prepayment = true
                            it.status = '已收'
                            it.save()
                        }
                    }
                    def credit = principalItem.credit
                    def debit = principalItem.debit
                    //计算违约金
                    def billsItem
                    int days = 0
                    def payMore = 0
                    def payLess = 0
                    def startTime = actuaRepaymentDate
                    def endTime = actuaRepaymentDate
                    def period = principalItem?.period + 1
                    def status = '待收'
                    def balance = 0
                    java.util.Calendar calendar = java.util.Calendar.getInstance()
                    calendar.setTime(actuaRepaymentDate)
                    /* //计算多还的息费
                     def items = com.next.BillsItem.findAllByBillsAndStatusAndPrepaymentAndEndTimeGreaterThan(bills, "已收", false, actuaRepaymentDate)
                     println(items?.id)
                     int dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH)
                     items.each {
                         if (it.type?.name != "本金" && it.type?.name != "渠道返费费率" && it.type?.name != "服务费费率")
                         {
                             if (it.period != bills?.opportunity?.actualLoanDuration)
                             {
                                 if (dayOfMonth <= 19)
                                 {
                                     days = 19 - dayOfMonth
                                 }
                                 else
                                 {
                                     days = 19 + 30 - dayOfMonth
                                 }
                             }
                             else
                             {
                                 days = (it.endTime.getTime() - actuaRepaymentDate?.getTime()) / (1000 * 3600 * 24)
                             }
                             payMore += -it.receivable * (days / 30)
                             *//*billsItem = new com.next.BillsItem(bills: bills, type: it?.type)
                             billsItem.receivable = payMore
                             java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                             billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                             billsItem.receipts = 0
                             saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                             println("多收息费天数" + days + "多收" + it.type.name + "金额" + it.receivable * (days / 30))*//*
                         }
                     }
                     println("多收息费总金额" + payMore + "万元")*/
                    /*//计算少还的息费
                    def items1 = com.next.BillsItem.findAllByBillsAndStatusNotEqualAndStartTimeLessThan(bills, "已收", actuaRepaymentDate)
                    items1.each {
                        days = (opportunity?.actuaRepaymentDate?.getTime() - it.startTime.getTime()) / (1000 * 3600 * 24)
                        payLess += it.receivable *(days/30)
                    }*/

                    /*//利息违约金应还
                    billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName("利息违约金"))
                    billsItem.receivable = payLess-payMore
                    billsItem.receipts = 0
                    saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)*/

                    //本金应还
                    billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName('本金'))
                    billsItem.receivable = actualLoanAmount
                    billsItem.receipts = 0
                    saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                    if (day > 30)
                    {
                        //总共应还的金额
                        def totalPayment = 0
                        def totalPaymentItem = com.next.BillsItem.findAllByBillsAndStatusAndTypeAndPrepayment(bills, "已收", com.next.BillsItemType.findByName("本金"), true)
                        totalPaymentItem.each {
                            totalPayment += it.receivable
                        }
                        println("需还本金金额" + totalPayment + "万元")
                        /*def servicePayment = 0
                        def lastServicePayment = com.next.BillsItem.findAllByBillsAndStatusAndType(bills, "待收", com.next.BillsItemType.findByName("服务费费率"))
                        lastServicePayment.each {
                            servicePayment += it.receivable
                        }
                        if (servicePayment)
                            {
                                billsItem = new com.next.BillsItem(bills: bills, type: it?.type)
                                billsItem.receivable = servicePayment
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                                billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                                billsItem.receipts = 0
                                saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                            }
                        println("需还服务费金额" + servicePayment + "万元")*/
                        //早偿违约金应还
                        billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName('早偿违约金'))
                        billsItem.receivable = totalPayment * rate / 100
                        java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                        billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        billsItem.receipts = 0
                        println("早偿违约金应还金额" + billsItem.receivable + "万元")
                        saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                        println("**************早偿还款计划生成完成*************")
                    }
                }
                else
                {
                    println("订单" + opportunity?.serialNumber + "没有早偿违约金费率")
                    return "订单" + opportunity?.serialNumber + "没有早偿违约金费率"
                }
            }
            else
            {
                println("*******进入结清还款计划生产*******")
                java.util.Calendar calendar = java.util.Calendar.getInstance()
                calendar.clear()
                calendar.setTime(opportunity?.actualLendingDate)
                //根据产品的费用要求生成还款计划
                def op = com.next.OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
                def rate = com.next.OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity?.productAccount, com.next.ProductInterestType.findByName("利息违约金"))?.rate
                if (!rate && normalOpportunityProduct)
                {
                    def nop = new com.next.OpportunityProduct(opportunity: opportunityChild, productInterestType: com.next.ProductInterestType.findByName("利息违约金"))
                    nop.product = normalOpportunityProduct.product
                    nop.rate = 0.1
                    nop.externalId = null
                    nop.monthes = normalOpportunityProduct.monthes
                    nop.installments = false
                    nop.contractType = normalOpportunityProduct.contractType
                    nop.createBy = normalOpportunityProduct.createBy
                    nop.fixedRate = normalOpportunityProduct.fixedRate
                    nop.firstPayMonthes = 0
                    nop.radixProductInterestType = normalOpportunityProduct.radixProductInterestType
                    nop.save()
                    nop = new com.next.OpportunityProduct(opportunity: opportunity, productInterestType: com.next.ProductInterestType.findByName("本金违约金"))
                    nop.product = normalOpportunityProduct.product
                    nop.rate = 0.1
                    nop.externalId = null
                    nop.monthes = normalOpportunityProduct.monthes
                    nop.installments = false
                    nop.contractType = normalOpportunityProduct.contractType
                    nop.createBy = normalOpportunityProduct.createBy
                    nop.fixedRate = normalOpportunityProduct.fixedRate
                    nop.firstPayMonthes = 0
                    nop.radixProductInterestType = normalOpportunityProduct.radixProductInterestType
                    nop.save()
                    rate = 0.1
                }
                rate = 0.1
                //对本金的计算
                oldItems = com.next.BillsItem.findAllByBillsAndTypeAndStatus(bills, com.next.BillsItemType.findByName("利息违约金"), "待收")
                oldItems?.each {
                    it.startTime = principalItem.startTime
                    it.endTime = principalItem.endTime
                    it.save()
                }
                def monthes = opportunity?.actualLoanDuration == 0 ? 1 : opportunity?.actualLoanDuration
                if (op && monthes && monthes != 0 && principal)
                {
                    def billsItem
                    def status = '待收'
                    def balance = 0
                    def items = com.next.BillsItem.findAllByBillsAndStatusNotEqualAndTypeNotEqual(opportunity?.bills, "已收", com.next.BillsItemType.findByName("本金"))
                    items.each {
                        if (rate && actuaRepaymentDate)
                        {
                            if (actuaRepaymentDate > it.startTime)
                            {
                                if (it.type != com.next.BillsItemType.findByName("利息违约金") && it.type != com.next.BillsItemType.findByName("渠道返费费率") && it.type != com.next.BillsItemType.findByName("服务费费率"))
                                {
                                    billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName('利息违约金'))
                                    def days = (actuaRepaymentDate.getTime() - it.startTime.getTime()) / (1000 * 3600 * 24)
                                    //处理收款日是星期天问题
                                    java.util.Calendar c1 = java.util.Calendar.getInstance()
                                    c1.setTime(actuaRepaymentDate)
                                    java.util.Calendar c2 = java.util.Calendar.getInstance()
                                    c2.setTime(it.startTime)
                                    def dayOfWeek1 = c2.get(java.util.Calendar.DAY_OF_WEEK)
                                    if (it.period == 0)
                                    {
                                        if (dayOfWeek1 == 6)
                                        {
                                            if (days <= 3)
                                            {
                                                days = 0
                                            }
                                            else if (days > 3)
                                            {
                                                days -= 1
                                            }
                                        }
                                        else if (dayOfWeek1 == 7)
                                        {
                                            if (days <= 2)
                                            {
                                                days = 0
                                            }
                                            else if (days > 2)
                                            {
                                                days -= 1
                                            }
                                        }
                                        else
                                        {
                                            days -= 1
                                        }
                                    }
                                    else
                                    {
                                        if (days == 2)
                                        {
                                            if (dayOfWeek1 == 7)
                                            {
                                                days -= 2
                                            }
                                        }
                                        else if (days == 1)
                                        {
                                            if (dayOfWeek1 == 1)
                                            {
                                                days -= 1
                                            }
                                        }
                                    }
                                    billsItem.receivable = principal * days * rate / 100
                                    billsItem.receipts = 0
                                    saveBillsItem(billsItem, status, it.period, it.startTime, it.endTime, balance, it.credit, it.debit)
                                }
                            }
                        }
                    }
                }
                println("**************结清还款计划生成完成*************")
            }
        }
    }
    else
    {
        println("订单" + opportunity?.serialNumber + "没有约定还款时间或者本金")
        return "订单" + opportunity?.serialNumber + "没有约定还款时间或者本金"
    }

}

    /**
     * 已还账单的息费逾期处理*/ { opportunity ->
    def saveBillsItem = {
        def billsItem, def status, def period, def startTime, def endTime, def balance, def credit, def debit ->
            billsItem.status = status
            billsItem.period = period
            billsItem.startTime = startTime
            billsItem.endTime = endTime
            billsItem.balance = balance
            billsItem.credit = credit
            billsItem.debit = debit
            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
            billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
            if (billsItem.validate())
            {
                billsItem.save()
            }
            else
            {
                println(billsItem.errors)
            }
    }
    if (opportunity?.type.name != "抵押贷款")
    {
        opportunity = opportunity?.parent
    }
    java.util.Calendar calendar = java.util.Calendar.getInstance()
    calendar.clear()
    java.util.Calendar calendar1 = java.util.Calendar.getInstance()
    calendar1.clear()
    def bills = com.next.Bills.findByOpportunity(opportunity)
    def billsItem
    def dayOfWeek1
    def status = '待收'
    def balance = 0
    def days = 0, day = 0
    //def rate = com.next.OpportunityProduct.findByOpportunityAndProductInterestType(opportunity,com.next.ProductInterestType.findByName("利息违约金"))?.rate
    def itemLast = com.next.BillsItem.findByBillsAndType(bills, com.next.BillsItemType.findByName("本金"))
    def items = com.next.BillsItem.findAllByBillsAndStatusAndType(bills, "已收", com.next.BillsItemType.findByName("基本费率"))
    if (itemLast && items)
    {
        items.each {
            if (it.actualEndTime > it.startTime)
            {
                day = (it.actualEndTime.getTime() - it.startTime.getTime()) / (1000 * 3600 * 24)
                java.util.Calendar c1 = java.util.Calendar.getInstance()
                c1.setTime(it.actualEndTime)
                java.util.Calendar c2 = java.util.Calendar.getInstance()
                c2.setTime(it.startTime)
                dayOfWeek1 = c2.get(java.util.Calendar.DAY_OF_WEEK)
                println("期数" + it.period + "实际还款时间：" + it.actualEndTime + "--扣款时间：" + it.startTime + ";扣款时间是星期" + dayOfWeek1 + "--逾期天数：" + day)
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
                println("判断后的逾期天数" + day)
                days += day
            }
        }
        billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName('利息违约金'))
        billsItem.receivable = days * opportunity?.actualLoanAmount * 1 / 1000
        billsItem.receipts = 0
        if (billsItem.receivable != 0)
        {
            saveBillsItem(billsItem, status, itemLast.period, itemLast.startTime, itemLast.endTime, balance, itemLast.credit, itemLast.debit)
        }
    }
}
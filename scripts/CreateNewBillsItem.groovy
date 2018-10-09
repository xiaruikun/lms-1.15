/**
 * Created by Nagelan on 2017/6/23 0023.
 * */
def newBills = { opportunity ->
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
               if (billsItem.validate() && billsItem.receivable != 0)
               {
                   if (billsItem.type != com.next.BillsItemType.findByName("利息违约金"))
                   {
                       billsItem.save()
                   }
               }
               else
               {
                   println(billsItem.errors)
               }

       }
       if (opportunity?.actualLendingDate && opportunity?.actualLoanAmount && opportunity?.actualLoanAmount != 0)
       {
           java.util.Calendar calendar = java.util.Calendar.getInstance()
           calendar.clear()
           calendar.setTime(opportunity?.actualLendingDate)
           //还款结束时间
           java.util.Calendar calendar1 = java.util.Calendar.getInstance()
           calendar1.setTime(opportunity?.actualLendingDate)
           def startDate = calendar1.get(java.util.Calendar.DAY_OF_MONTH)
           calendar1.add(java.util.Calendar.MONTH, opportunity?.actualLoanDuration)
           def monthMaxDays = calendar1.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
           def endDate = calendar1.get(java.util.Calendar.DAY_OF_MONTH)
           println monthMaxDays
           if (endDate< startDate) {
               println '此月天数小于初始月份天数'
           }else{
               println '此月天数不小于初始月份天数'
               calendar1.add(java.util.Calendar.DAY_OF_MONTH,-1)
           }

           int day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
           //根据产品的费用要求生成还款计划
           def bills = com.next.Bills.findByOpportunity(opportunity)
           if (!bills)
           {
               def status = com.next.BillsStatus.findByName("测算")
               bills = new com.next.Bills(opportunity: opportunity, status: status)
               bills.startTime = opportunity.actualLendingDate
               bills.endTime = calendar1.getTime()
               bills.bankAccount = com.next.BankAccount.findByName("中航628")
               bills.capital = opportunity?.actualLoanAmount
               bills.save()
           }
           else
           {
               bills.startTime = opportunity.actualLendingDate
               bills.endTime = calendar1.getTime()
               bills.bankAccount = com.next.BankAccount.findByName("中航628")
               bills.capital = opportunity?.actualLoanAmount
               bills.save()
           }
           if (!bills?.items)
           {
               def op = com.next.OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
               def actualLoanAmount = opportunity?.actualLoanAmount
               if (!actualLoanAmount || actualLoanAmount == 0)
               {
                   println("actualAmountOfCredit not found or is zero")
               }
               def principalPayMethod = opportunity?.productAccount?.principalPaymentMethod?.name
               def monthes = opportunity?.actualLoanDuration == 0 ? 1 : opportunity?.actualLoanDuration
               if (op && monthes && monthes != 0)
               {
                   //处理还款天数，按每月15号划分，15号之前算当月到20号的天数，15号之后算到下月20号
                   int dayFirst = 0
                   int dayLast = 0
                   def num = 0
                   def monthTotal
                   def dayTotal
                   def total
                   def param = monthes * 30 * (monthes * 30 + 1) / 2
                   def i = 0
                   //意向金
                   double advancePayment = 0.0
                   if (opportunity.advancePayment != null)
                   {
                       advancePayment = opportunity.advancePayment
                   }
                   else
                   {
                       def interests = com.next.OpportunityProduct.findByOpportunityAndProductInterestType(opportunity, com.next.ProductInterestType.findByName("意向金"))
                       if (interests)
                       {
                           advancePayment = interests.rate
                       }
                   }
                   //扣息方式
                   def interestPaymentMethod = opportunity?.interestPaymentMethod?.name
                   //上扣息月份数
                   def firstPayOfMonthes = opportunity?.monthOfAdvancePaymentOfInterest?.intValue() == 0 ? 1 : opportunity?.monthOfAdvancePaymentOfInterest?.intValue()
                   //下扣息月份数
                   def lastPayMonthes = opportunity?.lastPayMonthes == null?1:opportunity?.lastPayMonthes
                   if (day < 15)
                   {
                       if (interestPaymentMethod == "下扣息"){
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
                       if (interestPaymentMethod == "下扣息"){
                           firstPayOfMonthes = 1
                       }
                       dayFirst = 50 - day + (firstPayOfMonthes - 2) * 30
                       dayLast = 10 + day
                   }
                   else
                   {
                       if (interestPaymentMethod == "下扣息"){
                           firstPayOfMonthes = 1
                       }
                       dayFirst = 50 - day + (firstPayOfMonthes - 1) * 30
                       dayLast = day - 20
                   }
                   def credit = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("还款账号"))?.bankAccount
                   def debit = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("收款账号"))?.bankAccount
                   def rate = 0
                   op.each {
                       if (it.rate < 100 && it.rate != 0 && it.installments)
                       {
                           rate += it.rate
                       }
                   }
                   def dayTotalMoney = actualLoanAmount * (1 + monthes * rate / 100) / (monthes * 30)
                   def paymentInterest = 0
                   def paymentPrincipal = 0
                   if (credit && debit)
                   {
                       for (
                               i;
                               i <= (opportunity.actualLoanDuration - firstPayOfMonthes + 1);
                               i++)
                       {
                           def billsItem
                           def startTime
                           def endTime
                           def period = i
                           def status = '待收'
                           def balance = 0
                           if (i == 0)
                           {
                               monthTotal = 0
                               if (day < 20)
                               {
                                   startTime = calendar.getTime()
                                   calendar.add(java.util.Calendar.MONTH, firstPayOfMonthes - 1)
                                   calendar.set(java.util.Calendar.DAY_OF_MONTH, 19)
                                   endTime = calendar.getTime()
                               }
                               else
                               {
                                   startTime = calendar.getTime()
                                   calendar.add(java.util.Calendar.MONTH, firstPayOfMonthes)
                                   calendar.set(java.util.Calendar.DAY_OF_MONTH, 19)
                                   endTime = calendar.getTime()
                               }
                               op.each {
                                   if (it.productInterestType.name != "利息违约金" && it.productInterestType.name != "本金违约金" && it.productInterestType.name != "早偿违约金" && it.productInterestType.name != "逾期违约金")
                                   {
                                       period = i
                                       if (it.rate < 100 && it.rate != 0)
                                       {
                                           billsItem = new com.next.BillsItem(bills: bills)
                                           if (!it.installments)
                                           {
                                               billsItem.type = com.next.BillsItemType.findByName(it.productInterestType.name)
                                               billsItem.receivable = actualLoanAmount * it.rate / 100
                                               billsItem.receipts = 0
                                               billsItem.payTime = startTime+1
                                               saveBillsItem(billsItem, status, period, startTime, calendar1.getTime(), balance, credit, debit)
                                           }
                                           else
                                           {
                                               billsItem.type = com.next.BillsItemType.findByName(it.productInterestType.name)
                                               if (principalPayMethod == "等额本息")
                                               {
                                                   def num1 = monthes * 30
                                                   total = actualLoanAmount * monthes * it.rate * ((num1 + num1 - dayFirst + 1) * dayFirst / (2 * param)) / 100
                                                   billsItem.receivable = total
                                                   java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                                                   billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                                                   monthTotal += billsItem.receivable
                                                   billsItem.payTime = endTime + 1
                                                   period = i + 1
                                               }
                                               else
                                               {
                                                   /*if (opportunity.actualLoanDuration == 1)
                                                   {
                                                       billsItem.receivable = actualLoanAmount * it.rate / 100
                                                       endTime = calendar1.getTime()
                                                   }
                                                   else
                                                   {
                                                       billsItem.receivable = actualLoanAmount * it.rate / 30 * dayFirst / 100
                                                   }*/
                                                   billsItem.receivable = actualLoanAmount * it.rate / 30 * dayFirst / 100
                                                   if (interestPaymentMethod == '下扣息'){
                                                       if (day==20){
                                                           billsItem.payTime = endTime
                                                       }else {
                                                           billsItem.payTime = endTime +1
                                                       }
                                                       period = i + 1
                                                   }else {
                                                       billsItem.payTime = startTime +1
                                                   }

                                               }
                                               billsItem.receipts = 0
                                               saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                                           }
                                       }
                                   }
                               }
                               if (advancePayment > 0)
                               {
                                   billsItem = new com.next.BillsItem(bills: bills)
                                   billsItem.type = com.next.BillsItemType.findByName("意向金")
                                   billsItem.receivable = -advancePayment / 10000
                                   billsItem.payTime = startTime +1
                                   billsItem.receipts = 0
                                   saveBillsItem(billsItem, status, period, startTime, calendar1.getTime(), balance, credit, debit)
                               }
                               //本金
                               billsItem = new com.next.BillsItem(bills: bills)
                               billsItem.type = com.next.BillsItemType.findByName("本金")
                               if (principalPayMethod == "到期还本")
                               {
                                   billsItem.receivable = 0
                               }
                               else if (principalPayMethod == "月息年本")
                               {
                                   billsItem.receivable = 0
                               }
                               else if (principalPayMethod == "等本等息")
                               {
                                   billsItem.receivable = actualLoanAmount / monthes * dayFirst / 30
                               }
                               else if (principalPayMethod == "气球贷（60期）")
                               {
                                   billsItem.receivable = 0
                               }
                               else if (principalPayMethod == "气球贷（120期）")
                               {
                                   billsItem.receivable = 0
                               }
                               else if (principalPayMethod == "等额本息")
                               {
                                   //total = actualLoanAmount * ((1+dayFirst)*dayFirst/(2*param))
                                   total = dayTotalMoney
                                   java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(total))
                                   total = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                                   dayTotal = monthes * 30 - dayFirst
                                   billsItem.receivable = total * dayFirst - monthTotal
                                   billsItem.payTime = endTime +1
                                   period = i + 1
                               }
                               billsItem.receipts = 0
                               if (billsItem.receivable != 0)
                               {
                                   saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                               }
                           }
                           else if (i == (monthes - firstPayOfMonthes + 1))
                           {
                               monthTotal = 0
                               if (day < 20)
                               {
                                   calendar.set(java.util.Calendar.DAY_OF_MONTH, 20)
                                   startTime = calendar.getTime()
                               }
                               else
                               {
                                   calendar.set(java.util.Calendar.DAY_OF_MONTH, 20)
                                   startTime = calendar.getTime()
                               }
                               if (day != 20 || (opportunity?.product?.name != "融e贷" && opportunity?.product?.name != "等额本息"))
                               {
                                   /*if (opportunity.actualLoanDuration != 1)
                                   {*/
                                       op.each {
                                           if (it.productInterestType.name != "利息违约金" && it.productInterestType.name != "本金违约金" && it.productInterestType.name != "早偿违约金" && it.productInterestType.name != "逾期违约金")
                                           {
                                               period = i
                                               if (it.rate < 100 && it.rate != 0)
                                               {
                                                   billsItem = new com.next.BillsItem(bills: bills)
                                                   if (it.installments)
                                                   {
                                                       billsItem.type = com.next.BillsItemType.findByName(it.productInterestType.name)
                                                       paymentInterest = 0
                                                       def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                                       items.each {
                                                           paymentInterest += it.receivable
                                                       }
                                                       billsItem.receivable = actualLoanAmount * it?.rate * monthes / 100 - paymentInterest
                                                       billsItem.receipts = 0
                                                       if (opportunity?.product?.name == "等额本息"||interestPaymentMethod == '下扣息'){
                                                           billsItem.payTime = calendar1.getTime()
                                                           period = i + 1
                                                       }else {
                                                           billsItem.payTime = startTime
                                                       }
                                                       if (billsItem.receivable != 0)
                                                       {
                                                           saveBillsItem(billsItem, status, period, startTime, calendar1.getTime(), balance, credit, debit)
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                   /*}*/
                                   //本金
                                   billsItem = new com.next.BillsItem(bills: bills)
                                   billsItem.type = com.next.BillsItemType.findByName("本金")
                                   if (principalPayMethod == "到期还本")
                                   {
                                       billsItem.receivable = actualLoanAmount

                                       startTime = opportunity?.actualLendingDate
                                   }
                                   else if (principalPayMethod == "月息年本")
                                   {
                                       billsItem.receivable = actualLoanAmount * 0.8
                                   }
                                   else if (principalPayMethod == "等本等息")
                                   {
                                       billsItem.receivable = actualLoanAmount / monthes * dayLast / 30
                                   }
                                   else if (principalPayMethod == "气球贷（60期）")
                                   {
                                       billsItem.receivable = 0
                                   }
                                   else if (principalPayMethod == "气球贷（120期）")
                                   {
                                       billsItem.receivable = 0
                                   }
                                   else if (principalPayMethod == "等额本息")
                                   {
                                       paymentPrincipal = 0
                                       def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                       items.each {
                                           paymentPrincipal += it.receivable
                                       }
                                       //num = monthes*30+monthes*30-dayLast+1
                                       //total = actualLoanAmount * (num*dayLast/(2*param))
                                       total = actualLoanAmount - paymentPrincipal
                                       //total = dayTotalMoney * dayLast - monthTotal
                                       billsItem.receivable = total

                                   }
                                   billsItem.receipts = 0
                                   billsItem.payTime = calendar1.getTime()
                                   if (opportunity?.product?.name == "等额本息"||interestPaymentMethod == '下扣息'){
                                       if (day == 20){
                                           period = i
                                       }else {
                                           period = i + 1
                                       }
                                   }
                                   if (billsItem.receivable != 0)
                                   {
                                       saveBillsItem(billsItem, status, period, startTime, calendar1.getTime(), balance, credit, debit)
                                   }
                               }
                           }
                           else
                           {
                               monthTotal = 0
                               calendar.set(java.util.Calendar.DAY_OF_MONTH, 20)
                               startTime = calendar.getTime()
                               calendar.add(java.util.Calendar.MONTH, 1)
                               calendar.set(java.util.Calendar.DAY_OF_MONTH, 19)
                               endTime = calendar.getTime()
                               op.each {
                                   if (it.productInterestType.name != "利息违约金" && it.productInterestType.name != "本金违约金" && it.productInterestType.name != "早偿违约金" && it.productInterestType.name != "逾期违约金")
                                   {
                                       period = i
                                       if (it.rate < 100 && it.rate != 0)
                                       {
                                           billsItem = new com.next.BillsItem(bills: bills)
                                           if (it.installments)
                                           {
                                               billsItem.type = com.next.BillsItemType.findByName(it.productInterestType.name)
                                               if (principalPayMethod == "等额本息")
                                               {
                                                   def num1 = dayTotal
                                                   total = actualLoanAmount * monthes * it.rate * ((num1 + num1 - 29) * 30 / (2 * param)) / 100
                                                   billsItem.receivable = total
                                                   java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                                                   billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                                                   monthTotal += billsItem.receivable
                                                   billsItem.payTime = endTime+1
                                                   period = i + 1
                                               }
                                               else
                                               {
                                                   billsItem.receivable = actualLoanAmount * it.rate / 100
                                                   if (interestPaymentMethod == '下扣息'){
                                                       billsItem.payTime = endTime+1
                                                       period = i + 1
                                                   }else {
                                                       billsItem.payTime = startTime
                                                   }
                                               }
                                               if (day == 20 && i == (monthes - firstPayOfMonthes))
                                               {
                                                   paymentInterest = 0
                                                   def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                                   items.each {
                                                       paymentInterest += it.receivable
                                                   }
                                                   if (opportunity?.product?.name == "等额本息"||interestPaymentMethod == '下扣息'){
                                                       billsItem.payTime = calendar1.getTime()
                                                       period = i + 1
                                                   }else {
                                                       billsItem.payTime = startTime
                                                   }
                                                   billsItem.receivable = actualLoanAmount * monthes * it.rate / 100 - paymentInterest
                                               }
                                               billsItem.receipts = 0
                                               saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                                           }
                                       }
                                   }
                               }
                               //本金
                               billsItem = new com.next.BillsItem(bills: bills)
                               billsItem.type = com.next.BillsItemType.findByName("本金")
                               if (principalPayMethod == "到期还本")
                               {
                                   billsItem.receivable = 0
                               }
                               else if (principalPayMethod == "月息年本")
                               {
                                   if (i % 12 == 0 && i % 24 != 0)
                                   {
                                       billsItem.receivable = actualLoanAmount * 0.1
                                       billsItem.payTime = calendar1.getTime()
                                   }
                                   else if (i % 24 == 0)
                                   {
                                       billsItem.receivable = actualLoanAmount * 0.1
                                       billsItem.payTime = calendar1.getTime()
                                   }
                                   else if (i == (monthes - firstPayOfMonthes))
                                   {
                                       if (day == 20)
                                       {
                                           billsItem.receivable = actualLoanAmount * 0.8
                                           billsItem.payTime = calendar1.getTime()
                                       }
                                       else
                                       {
                                           billsItem.receivable = 0
                                       }
                                   }
                                   else
                                   {
                                       billsItem.receivable = 0
                                   }
                               }
                               else if (principalPayMethod == "等本等息")
                               {
                                   billsItem.receivable = actualLoanAmount / monthes
                               }
                               else if (principalPayMethod == "气球贷（60期）")
                               {
                                   billsItem.receivable = 0
                               }
                               else if (principalPayMethod == "气球贷（120期）")
                               {
                                   billsItem.receivable = 0
                               }
                               else if (principalPayMethod == "等额本息")
                               {
                                   //num = monthes*30-dayTotal+1
                                   //total = actualLoanAmount * ((num+num+29)*30/(2*param))
                                   total = dayTotalMoney
                                   java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(total))
                                   total = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                                   dayTotal = dayTotal - 30
                                   billsItem.receivable = total * 30 - monthTotal
                                   billsItem.payTime = endTime+1
                                   if (day == 20 && i == (monthes - firstPayOfMonthes))
                                   {
                                       paymentPrincipal = 0
                                       def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                       items.each {
                                           paymentPrincipal += it.receivable
                                       }
                                       billsItem.receivable = actualLoanAmount - paymentPrincipal
                                       billsItem.payTime = calendar1.getTime()
                                   }
                               }
                               billsItem.receipts = 0
                               if (opportunity?.product?.name == "等额本息"||interestPaymentMethod == '下扣息'){
                                   period = i + 1
                               }
                               if (billsItem.receivable != 0)
                               {
                                   saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                               }
                           }
                       }
                   }
                   else
                   {
                       println("贷方或借方不存在")
                       return "贷方或借方不存在"
                   }
               }
               else
               {
                   println("订单产品信息不存在")
                   return "订单产品信息不存在"
               }
           }
           else
           {
               println("已经存在还款计划")
               return "已经存在还款计划"
           }
       }
       else
       {
           println("实际放款时间或者实际放款金额不存在或者金额为0")
           return "实际放款时间或者实际放款金额不存在或者金额为0"
       }
   }

/**
 * @describtion 生成还款计划（早期等额本息）
 * @author 王超
 * @date 2017/7/31
 * */
def index1 = { opportunity ->
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
            if (billsItem.validate() && billsItem.receivable != 0)
            {
                if (billsItem.type != com.next.BillsItemType.findByName("利息违约金"))
                {
                    billsItem.save()
                    print("1")
                }
            }
            else
            {
                println(billsItem.errors)
            }

    }
    if (opportunity?.actualLendingDate && opportunity?.actualLoanAmount && opportunity?.actualLoanAmount != 0)
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance()
        calendar.clear()
        calendar.setTime(opportunity?.actualLendingDate)
        java.util.Calendar calendarBills = java.util.Calendar.getInstance()
        calendarBills.clear()
        calendarBills.setTime(opportunity?.actualLendingDate)
        calendarBills.add(java.util.Calendar.MONTH, opportunity?.actualLoanDuration)
        calendarBills.add(java.util.Calendar.DAY_OF_MONTH, -1)
        //上扣息月份数
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
        //根据产品的费用要求生成还款计划
        def bills = com.next.Bills.findByOpportunity(opportunity)
        if (!bills)
        {
            def status = com.next.BillsStatus.findByName("测算")
            bills = new com.next.Bills(opportunity: opportunity, status: status)
            bills.startTime = opportunity.actualLendingDate
            bills.endTime = calendarBills.getTime()
            bills.bankAccount = com.next.BankAccount.findByName("中航628")
            bills.capital = opportunity?.actualLoanAmount
            bills.save()
        }
        else
        {
            bills.startTime = opportunity.actualLendingDate
            bills.endTime = calendarBills.getTime()
            bills.bankAccount = com.next.BankAccount.findByName("中航628")
            bills.capital = opportunity?.actualLoanAmount
            bills.save()
        }
        if (!bills?.items)
        {
            def op = com.next.OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
            def actualLoanAmount = opportunity?.actualLoanAmount
            if (!actualLoanAmount || actualLoanAmount == 0)
            {
                println("actualAmountOfCredit not found or is zero")
            }
            def principalPayMethod = opportunity?.productAccount?.principalPaymentMethod?.name
            def monthes = opportunity?.actualLoanDuration == 0 ? 1 : opportunity?.actualLoanDuration
            if (op && monthes && monthes != 0)
            {
                //处理还款天数，按每月15号划分，15号之前算当月到20号的天数，15号之后算到下月20号
                int dayFirst = 0
                def monthTotal
                def total
                def param = monthes * (monthes + 1) / 2
                def i = 0
                //意向金
                double advancePayment = 0.0
                if (opportunity.advancePayment != null)
                {
                    advancePayment = opportunity.advancePayment
                }
                else
                {
                    def interests = com.next.OpportunityProduct.findByOpportunityAndProductInterestType(opportunity, com.next.ProductInterestType.findByName("意向金"))
                    if (interests)
                    {
                        advancePayment = interests.rate
                    }
                }
                //上扣息月份数
                def firstPayOfMonthes = opportunity?.monthOfAdvancePaymentOfInterest?.intValue() == 0 ? 1 : opportunity?.monthOfAdvancePaymentOfInterest?.intValue()
                if (day < 15)
                {
                    dayFirst = 20 - day + (firstPayOfMonthes - 1) * 30
                }
                else if (day >= 15 && day < 20)
                {
                    if (opportunity?.interestPaymentMethod?.name == "扣息差")
                    {
                        firstPayOfMonthes = 2
                    }
                    if (opportunity?.interestPaymentMethod?.name == "上扣息" && firstPayOfMonthes == 1 && opportunity.actualLoanDuration != 1)
                    {
                        firstPayOfMonthes = 2
                    }
                    if (opportunity?.interestPaymentMethod?.name == "月息年本")
                    {
                        firstPayOfMonthes = 2
                    }
                    dayFirst = 50 - day + (firstPayOfMonthes - 2) * 30
                }
                else
                {
                    dayFirst = 50 - day + (firstPayOfMonthes - 1) * 30
                }
                //还款结束时间
                java.util.Calendar calendar1 = java.util.Calendar.getInstance()
                calendar1.setTime(opportunity?.actualLendingDate)
                calendar1.add(java.util.Calendar.MONTH, monthes)
                calendar1.add(java.util.Calendar.DAY_OF_MONTH, -1)
                def credit = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("还款账号"))?.bankAccount
                def debit = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("收款账号"))?.bankAccount
                def rate = 0
                op.each {
                    if (it.rate < 100 && it.rate != 0 && it.installments)
                    {
                        rate += it.rate
                    }
                }
                def dayTotalMoney = actualLoanAmount * (1 + monthes * rate / 100) / monthes
                def paymentInterest = 0
                def paymentPrincipal = 0
                if (credit && debit)
                {
                    for (
                            i;
                            i <= (monthes - firstPayOfMonthes + 1);
                            i++)
                    {
                        def billsItem
                        def startTime
                        def endTime
                        def period = i
                        def status = '待收'
                        def balance = 0
                        if (i == 0)
                        {
                            monthTotal = 0
                            startTime = calendar.getTime()
                            calendar.add(java.util.Calendar.MONTH, firstPayOfMonthes)
                            calendar.add(java.util.Calendar.DAY_OF_MONTH, -1)
                            endTime = calendar.getTime()
                            op.each {
                                if (it.productInterestType.name != "利息违约金" && it.productInterestType.name != "本金违约金" && it.productInterestType.name != "早偿违约金" && it.productInterestType.name != "逾期违约金")
                                {
                                    if (it.rate < 100 && it.rate != 0)
                                    {
                                        billsItem = new com.next.BillsItem(bills: bills)
                                        if (!it.installments)
                                        {
                                            billsItem.type = com.next.BillsItemType.findByName(it.productInterestType.name)
                                            billsItem.receivable = actualLoanAmount * it.rate / 100
                                            billsItem.receipts = 0
                                            saveBillsItem(billsItem, status, period, startTime, calendar1.getTime(), balance, credit, debit)
                                        }
                                        else
                                        {
                                            billsItem.type = com.next.BillsItemType.findByName(it.productInterestType.name)
                                            if (principalPayMethod == "等额本息")
                                            {
                                                total = actualLoanAmount * monthes * it.rate * (monthes - i) / param / 100
                                                billsItem.receivable = total
                                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                                                billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                                                monthTotal += billsItem.receivable
                                            }
                                            else
                                            {
                                                if (monthes == 1)
                                                {
                                                    billsItem.receivable = actualLoanAmount * it.rate / 100
                                                    endTime = calendarBills.getTime()
                                                }
                                                else
                                                {
                                                    billsItem.receivable = actualLoanAmount * it.rate / 30 * dayFirst / 100
                                                }
                                            }
                                            billsItem.receipts = 0
                                            saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                                        }
                                    }
                                }
                            }
                            if (advancePayment > 0)
                            {
                                billsItem = new com.next.BillsItem(bills: bills)
                                billsItem.type = com.next.BillsItemType.findByName("意向金")
                                billsItem.receivable = -advancePayment / 10000
                                billsItem.receipts = 0
                                saveBillsItem(billsItem, status, period, startTime, calendar1.getTime(), balance, credit, debit)
                            }
                            //本金
                            billsItem = new com.next.BillsItem(bills: bills)
                            billsItem.type = com.next.BillsItemType.findByName("本金")
                            if (principalPayMethod == "到期还本")
                            {
                                billsItem.receivable = 0
                            }
                            else if (principalPayMethod == "月息年本")
                            {
                                billsItem.receivable = 0
                            }
                            else if (principalPayMethod == "等本等息")
                            {
                                billsItem.receivable = actualLoanAmount / monthes * dayFirst / 30
                            }
                            else if (principalPayMethod == "气球贷（60期）")
                            {
                                billsItem.receivable = 0
                            }
                            else if (principalPayMethod == "气球贷（120期）")
                            {
                                billsItem.receivable = 0
                            }
                            else if (principalPayMethod == "等额本息")
                            {
                                billsItem.receivable = dayTotalMoney - monthTotal
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                                billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                            billsItem.receipts = 0
                            if (billsItem.receivable != 0)
                            {
                                saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                            }
                        }
                        else if (i == (monthes - firstPayOfMonthes + 1))
                        {
                            monthTotal = 0
                            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
                            startTime = calendar.getTime()
                            calendar.add(java.util.Calendar.MONTH, 1)
                            calendar.add(java.util.Calendar.DAY_OF_MONTH, -1)
                            endTime = calendar.getTime()
                            if (day != 20 || (opportunity?.product?.name != "融e贷" && opportunity?.product?.name != "等额本息"))
                            {
                                if (opportunity.actualLoanDuration != 1)
                                {
                                    op.each {
                                        if (it.productInterestType.name != "利息违约金" && it.productInterestType.name != "本金违约金" && it.productInterestType.name != "早偿违约金" && it.productInterestType.name != "逾期违约金")
                                        {
                                            if (it.rate < 100 && it.rate != 0)
                                            {
                                                billsItem = new com.next.BillsItem(bills: bills)
                                                if (it.installments)
                                                {
                                                    billsItem.type = com.next.BillsItemType.findByName(it.productInterestType.name)
                                                    paymentInterest = 0
                                                    def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                                    items.each {
                                                        paymentInterest += it.receivable
                                                    }
                                                    billsItem.receivable = actualLoanAmount * it?.rate * monthes / 100 - paymentInterest
                                                    billsItem.receipts = 0
                                                    if (billsItem.receivable != 0)
                                                    {
                                                        saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                //本金
                                billsItem = new com.next.BillsItem(bills: bills)
                                billsItem.type = com.next.BillsItemType.findByName("本金")
                                if (principalPayMethod == "到期还本")
                                {
                                    billsItem.receivable = actualLoanAmount
                                    startTime = opportunity?.actualLendingDate
                                }
                                else if (principalPayMethod == "月息年本")
                                {
                                    billsItem.receivable = actualLoanAmount * 0.8
                                }
                                else if (principalPayMethod == "等本等息")
                                {
                                    paymentPrincipal = 0
                                    def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                    items.each {
                                        paymentPrincipal += it.receivable
                                    }
                                    total = actualLoanAmount - paymentPrincipal
                                    billsItem.receivable = total
                                }
                                else if (principalPayMethod == "气球贷（60期）")
                                {
                                    billsItem.receivable = 0
                                }
                                else if (principalPayMethod == "气球贷（120期）")
                                {
                                    billsItem.receivable = 0
                                }
                                else if (principalPayMethod == "等额本息")
                                {
                                    paymentPrincipal = 0
                                    def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                    items.each {
                                        paymentPrincipal += it.receivable
                                    }
                                    total = actualLoanAmount - paymentPrincipal
                                    billsItem.receivable = total
                                }
                                billsItem.receipts = 0
                                if (billsItem.receivable != 0)
                                {
                                    saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                                }
                            }
                        }
                        else
                        {
                            monthTotal = 0
                            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
                            startTime = calendar.getTime()
                            calendar.add(java.util.Calendar.MONTH, 1)
                            calendar.add(java.util.Calendar.DAY_OF_MONTH, -1)
                            endTime = calendar.getTime()
                            op.each {
                                if (it.productInterestType.name != "利息违约金" && it.productInterestType.name != "本金违约金" && it.productInterestType.name != "早偿违约金" && it.productInterestType.name != "逾期违约金")
                                {
                                    if (it.rate < 100 && it.rate != 0)
                                    {
                                        billsItem = new com.next.BillsItem(bills: bills)
                                        if (it.installments)
                                        {
                                            billsItem.type = com.next.BillsItemType.findByName(it.productInterestType.name)
                                            if (principalPayMethod == "等额本息")
                                            {
                                                if (i == (monthes - firstPayOfMonthes))
                                                {
                                                    paymentInterest = 0
                                                    def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                                    items.each {
                                                        paymentInterest += it.receivable
                                                    }
                                                    billsItem.receivable = actualLoanAmount * monthes * it.rate / 100 - paymentInterest
                                                }
                                                else
                                                {
                                                    total = actualLoanAmount * monthes * it.rate * (monthes - i) / param / 100
                                                    billsItem.receivable = total
                                                    java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                                                    billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                                                    monthTotal += billsItem.receivable
                                                }
                                            }
                                            else
                                            {
                                                billsItem.receivable = actualLoanAmount * it.rate / 100
                                            }
                                            billsItem.receipts = 0
                                            saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                                        }
                                    }
                                }
                            }
                            //本金
                            billsItem = new com.next.BillsItem(bills: bills)
                            billsItem.type = com.next.BillsItemType.findByName("本金")
                            if (principalPayMethod == "到期还本")
                            {
                                billsItem.receivable = 0
                            }
                            else if (principalPayMethod == "月息年本")
                            {
                                if (i % 12 == 0 && i % 24 != 0)
                                {
                                    billsItem.receivable = actualLoanAmount * 0.1
                                }
                                else if (i % 24 == 0)
                                {
                                    billsItem.receivable = actualLoanAmount * 0.1
                                }
                                else if (i == (monthes - firstPayOfMonthes))
                                {
                                    if (day == 20)
                                    {
                                        billsItem.receivable = actualLoanAmount * 0.8
                                    }
                                    else
                                    {
                                        billsItem.receivable = 0
                                    }
                                }
                                else
                                {
                                    billsItem.receivable = 0
                                }
                            }
                            else if (principalPayMethod == "等本等息")
                            {
                                billsItem.receivable = actualLoanAmount / monthes
                            }
                            else if (principalPayMethod == "气球贷（60期）")
                            {
                                billsItem.receivable = 0
                            }
                            else if (principalPayMethod == "气球贷（120期）")
                            {
                                billsItem.receivable = 0
                            }
                            else if (principalPayMethod == "等额本息")
                            {
                                /*if (day == 20 && i == (monthes - firstPayOfMonthes))
                               {
                                   paymentPrincipal = 0
                                   def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                   items.each {
                                       paymentPrincipal += it.receivable
                                   }
                                   billsItem.receivable = actualLoanAmount - paymentPrincipal
                               }*/
                                if (i == (monthes - firstPayOfMonthes))
                                {
                                    paymentPrincipal = 0
                                    def items = com.next.BillsItem.findAllByBillsAndType(bills, billsItem.type)
                                    items.each {
                                        paymentPrincipal += it.receivable
                                    }
                                    billsItem.receivable = actualLoanAmount - paymentPrincipal
                                }
                                else
                                {
                                    billsItem.receivable = dayTotalMoney - monthTotal
                                    java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                                    billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                                }

                            }
                            billsItem.receipts = 0
                            if (billsItem.receivable != 0)
                            {
                                saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
                            }
                        }
                    }
                }
                else
                {
                    println("贷方或借方不存在")
                    return "贷方或借方不存在"
                }
            }
            else
            {
                println("订单产品信息不存在")
                return "订单产品信息不存在"
            }
        }
        else
        {
            println("已经存在还款计划")
            return "已经存在还款计划"
        }
    }
    else
    {
        println("实际放款时间或者实际放款金额不存在或者金额为0")
        return "实际放款时间或者实际放款金额不存在或者金额为0"
    }
}

/**
 * 结清计划&&早偿计划生成
 * */
def zaocang1 = { opportunity ->
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
 * 展期还款计划生成*/
def zhanqi = { opportunity ->
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
    opportunity = opportunity?.parent
    java.util.Calendar calendar = java.util.Calendar.getInstance()
    int day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
    //查找父订单还款计划
    def bills = com.next.Bills.findByOpportunity(opportunity)
    //修改还款计划之后的状态
    def oldItems = com.next.BillsItem.findAllByBillsAndStatusNotEqual(bills, "已收")
    oldItems?.each {
        it.status = "已展期"
        it.save()
    }
    def op = com.next.OpportunityProduct.findAllByOpportunity(opportunity)
    //对本金的计算
    def actualLoanAmount = 0
    if (opportunity?.actualLoanAmount == 0)
    {
        actualLoanAmount = opportunity?.actualAmountOfCredit
    }
    else
    {
        actualLoanAmount = opportunity.actualLoanAmount
    }
    if (!actualLoanAmount || actualLoanAmount == 0)
    {
        oldItems = com.next.BillsItem.findAllByBillsAndTypeAndStatusNotEqual(bills, com.next.BillsItemType.findByName("本金"), "已收")
        oldItems?.each {
            actualLoanAmount = actualLoanAmount + it.receivable
        }
    }
    def credit = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("还款账号"))?.bankAccount
    def debit = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("收款账号"))?.bankAccount
    def monthes = opportunity?.actualLoanDuration == 0 ? 1 : opportunity?.actualLoanDuration
    if (op && monthes && monthes != 0)
    {
        //计算违约金
        def billsItem
        if (opportunity?.actuaRepaymentDate)
        {
            calendar.setTime(opportunity?.actuaRepaymentDate)
            def startTime = calendar.getTime()
            def endTime = calendar.getTime()
            def period = 1
            def status = '待收'
            def balance = 0
            //本金应还
            billsItem = new com.next.BillsItem(bills: bills, type: com.next.BillsItemType.findByName('本金'))
            billsItem.receivable = actualLoanAmount
            billsItem.receipts = 0
            saveBillsItem(billsItem, status, period, startTime, endTime, balance, credit, debit)
        }
        else
        {
            println("订单" + opportunity?.serialNumber + "展期没有实际还款时间")
        }
    }
}

/**
 * 平时逾期计算*/
def yuqiNormal()
{
    java.util.Calendar calendar = java.util.Calendar.getInstance()
    calendar.set(Calendar.HOUR, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    def dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
    def month = calendar.get(java.util.Calendar.MONTH)
    if (1 == 1)
    {
        //根据产品的费用要求生成还款计划
        def returnMessage = ""
        def billsItem
        def status = '待收'
        def balance = 0
        def bills = com.next.Bills.findAllByStatusInList(com.next.BillsStatus.findAllByNameInList(["测算", "还款中", "逾期未处理", "已展期"]))
        bills.each { bill ->
            if (bill?.opportunity?.createdDate >= new java.util.Date("2017-03-10"))
            {
                def items = com.next.BillsItem.findAllByBillsAndStatusAndTypeInListAndStartTimeLessThan(bill, "待收", [com.next.BillsItemType.findByName("基本费率")], calendar.getTime())
                println("billsId=" + bill?.id + "billsItemId=" + items?.id)
                //def principal = bill?.opportunity?.actualLoanAmount
                def rate = com.next.OpportunityProduct.findByOpportunityAndProductAndProductInterestType(bill?.opportunity, bill?.opportunity?.productAccount, com.next.ProductInterestType.findByName("利息违约金"))?.rate
                def createItems = com.next.BillsItem.findByBillsAndCreatedTime(bill, calendar.getTime())
                if (!createItems && items && rate)
                {
                    returnMessage = "BillsItems"
                    items.each { item ->
                        billsItem = new com.next.BillsItem(bills: bill, type: com.next.BillsItemType.findByName('利息违约金'))
                        java.util.Calendar calendar1 = java.util.Calendar.getInstance()
                        calendar1.setTime(item.startTime)
                        def day = calendar1.get(java.util.Calendar.DAY_OF_MONTH)
                        def month1 = calendar1.get(java.util.Calendar.MONTH)
                        if (dayOfWeek == 3 && month == month1 && day == 20)
                        {
                            billsItem.receivable = item.receivable * rate * 3
                        }
                        else
                        {
                            billsItem.receivable = item.receivable * rate
                        }
                        billsItem.receipts = 0
                        billsItem.status = status
                        billsItem.period = item.period
                        billsItem.startTime = item.startTime
                        billsItem.createdTime = calendar.getTime()
                        billsItem.endTime = item.endTime
                        billsItem.balance = balance
                        billsItem.credit = item.credit
                        java.math.BigDecimal serviceCharge = new java.math.BigDecimal(java.lang.Double.toString(billsItem.receivable))
                        billsItem.receivable = serviceCharge.setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        billsItem.debit = item.debit
                        if (billsItem.validate() && billsItem.receivable)
                        {
                            if ((dayOfWeek == 2 || dayOfWeek == 1) && month1 == month && day == 20)
                            {
                            }
                            else
                            {
                                billsItem.save()
                                returnMessage += "bills:" + bill?.id + "-item:" + billsItem?.id + "/n"
                                print("1")
                            }
                        }
                        else
                        {
                            println(billsItem.errors)
                        }
                    }
                }
            }
        }
        return returnMessage
    }
}

/**
 * 对于已收账单的逾期处理
 * */
def huankuanzhihou = { opportunity ->
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
                //判断日期是否节假日URL
                //http://www.easybots.cn/api/holiday.php?d=20170728,20170729,20170730,20170731
                //返回集{"20170728":"0","20170729":"1","20170730":"1","20170731":"0"} 0代表工作日，1代表双休，2代表法定休息日
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
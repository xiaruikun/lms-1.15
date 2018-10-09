/**
 * Created by 王超 on 2017/07/7.*/
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

    }
    billsItems = com.next.BillsItem.findAllByBillsAndPeriod(bills, opportunity?.actualLoanDuration - firstPayOfMonthes + 2)
    billsItems.each {
        def transactionRecord = com.next.TransactionRecord.findById(it.transactionRecordId)
        it.delete()
        transactionRecord.delete()
    }
}
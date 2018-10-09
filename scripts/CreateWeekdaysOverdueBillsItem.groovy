/**
 * Created by Nagelan on 2017/6/23 0023.*/
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
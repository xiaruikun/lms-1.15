package com.next

import grails.converters.JSON
import groovy.json.JsonOutput

import javax.transaction.Transactional
import java.text.DecimalFormat

//@Transactional
class BillsService
{
    def componentService
    def dataSource
    /**
     * 重置计划
     * */
    def reset = { opportunity ->
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
            it.transactionRecord.save()
            it.save()
        }
        billsItems = com.next.BillsItem.findAllByBillsAndPrepayment(bills, true)
        billsItems.each {
            it.prepayment = false
            it.status = "待收"
            it.transactionRecord.status = com.next.TransactionRecordStatus.findByName("未执行")
            it.transactionRecord.save()
            it.save()
        }
        billsItems = com.next.BillsItem.findAllByBillsAndSplit(bills, true)
        billsItems.each {
            it.split = false
            it.status = "待收"
            it.transactionRecord.status = com.next.TransactionRecordStatus.findByName("未执行")
            it.transactionRecord.save()
            it.save()
        }
        billsItems = com.next.BillsItem.findAllByBillsAndPeriodAndType(bills, opportunity?.actualLoanDuration - firstPayOfMonthes + 1, com.next.BillsItemType.findByName("利息违约金"))
        billsItems.each {
            def transactionRecord = com.next.TransactionRecord.findById(it.transactionRecordId)
            transactionRecord.amount -= it.receivable
            transactionRecord.save()

        }
        billsItems.each {

            it.delete()

        }
        println "1111111111111111111111111111111111111111111111111111111"
        billsItems = com.next.BillsItem.findAllByBillsAndPeriod(bills, opportunity?.actualLoanDuration - firstPayOfMonthes + 2)
        billsItems.each {
            it.transactionRecord.status = com.next.TransactionRecordStatus.findByName("已失效")
            it.save()
        }
        billsItems.each {
            it.delete()

        }
    }

    /**
     * 是否已经处理过早偿或者逾期*/
    def alreadyDealPrePayOrOverdue(Opportunity opportunity)
    {
        def bills = com.next.Bills.findByOpportunity(opportunity)
        def billsItems = com.next.BillsItem.findAllByBillsAndStatus(bills, "待收")
        if (billsItems.size() > 0)
        {
            for (
                def i = 0;
                    i < billsItems.size();
                    i++)
            {
                if (billsItems[i].type?.name == "利息违约金" || billsItems[i].type?.name == "早偿违约金" || billsItems[i].type?.name == "本金违约金")
                {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 富友交易记录更新还款计划*/
    def updateBillsFromRecord(Opportunity opportunity, TransactionRecord record)
    {
        def bills = com.next.Bills.findByOpportunity(opportunity)
        if (!bills)
        {
            println "没有找到还款计划！！！"
            return false
        }

        /*Integer period = 0
        Integer i
        def billsItems = com.next.BillsItem.findAllByBillsAndStatusNotEqual(bills, "已收")
        if (billsItems.size() > 0)
        {
            // 找到本次交易记录对应的还款计划的期数
            for (
                i = 0;
                    i < billsItems.size();
                    i++)
            {
                if (billsItems[i].period == 0 && billsItems[i].startTime == record.plannedStartTime - 1)
                {
                    break
                }
                else if (billsItems[i].period > 0 && billsItems[i].startTime == record.plannedStartTime + 2)
                {
                    period = billsItems[i].period
                    break
                }
            }

            // 计算应扣总金额
            Double sum = 0
            for (
                i = 0;
                    i < billsItems.size();
                    i++)
            {
                def type = billsItems[i].type.name
                def productInterestType = com.next.OpportunityProduct.find("from OpportunityProduct where opportunity.id = ${opportunity.id} and productInterestType.name = '${type}'")
                if (billsItems[i].period == period && billsItems[i].receivable > 0)
                {
                    if (type == "渠道返费费率" || type == "服务费费率")
                    {
                        sum += billsItems[i].receivable
                    }
                    else if (productInterestType?.contractType?.name == "委托借款代理服务合同")
                    {
                        sum += billsItems[i].receivable
                    }
                }
            } */
        def billsItems = com.next.BillsItem.findAllByBillsAndTransactionRecord(bills, record)
        if (billsItems.size() > 0)
        {
            // 计算应扣总金额
            Double sum = 0
            billsItems.each {
                sum += it.receivable
            }

            // 扣减上期结余
            Integer period = billsItems[0].period
            if (period > 0)
            {
                def billsItems1 = com.next.BillsItem.findAllByBillsAndPeriod(bills, period - 1)
                billsItems1.each {
                    sum += it.balance
                }
            }
            println "本次流水匹配待还金额为：" + sum + "，实收金额为：" + record.amount

            // 匹配交易记录
            if (record.amount + 1e-10 > sum)
            // 流水与本期还款计划匹配
            {
                //Integer index = 0
                def i = 0
                for (
                    i = 0;
                        i < billsItems.size();
                        i++)
                {
                    BillsItem billsItem = billsItems[i]
                    def type = billsItem.type.name
                    //def productInterestType = com.next.OpportunityProduct.find("from OpportunityProduct where opportunity.id = ${opportunity.id} and productInterestType.name = '${type}'")
                    //if (billsItem.period == period)
                    //{
                    //if (type == "渠道返费费率" || type == "服务费费率" || productInterestType?.contractType?.name == "委托借款代理服务合同")
                    //{
                    billsItem.receipts = billsItem.receivable
                    billsItem.actualEndTime = record.endTime
                    billsItem.status = "已收"
                    billsItem.save flush: true
                    //index = i
                    //}
                    //}
                }
                println "本次流水匹配成功，待还金额为：" + sum + "，实收金额为：" + record.amount
                if (record.amount - sum > 1e-6)
                {
                    billsItems[0].balance = sum - record.amount
                }
                return true
            }
            else
            // 流水没有匹配上账单，本次流水待查
            {
                println "本次流水自动匹配失败，需人工处理！！！"
                return false
            }
        }
        else
        {
            println "本次交易记录无法自动匹配到还款计划，请人工处理！！！"
        }
    }

    /**
     * 数字转换为汉语中人民币的大写<br>
     *
     * @author nagelan
     * @create 2017-07-17
     */
    def transforNumToRMB(Double value)
    {
        DecimalFormat format = new DecimalFormat("#.00")
        def str = format.format(value)
        def s = str.split("\\.")
        def temp = ""
        int ling = 0
        int shu = 0
        int pos = 0
        def s1 = s[0].toCharArray()
        def s2 = s[1].toCharArray()
        for (
            int j = 0;
                j < s[0].length();
                ++j)
        {
            int num = s1[j] - 48
            if (num == 0)
            {
                ling++
                if (ling == s[0].length())
                {
                    temp = "零"
                }
                else if (s[0].length() - j - 1 == 4)
                {
                    if (shu == 1 && (s[0].length() - pos - 1) >= 5 && (s[0].length() - pos - 1) <= 7)
                    {
                        temp += "万"
                    }
                }
                else if (s[0].length() - j - 1 == 8)
                {
                    if (shu == 1 && (s[0].length() - pos - 1) >= 9 && (s[0].length() - pos - 1) <= 11)
                    {
                        temp += "亿"
                    }
                }
            }
            else
            {
                shu++
                int flag = 0
                if (shu == 1)
                {
                    ling = 0
                    pos = j
                }
                if (shu == 2)
                {
                    flag = 1
                    if (ling > 0)
                    {
                        temp += "零"
                    }
                    shu = 1
                    pos = j
                    ling = 0
                }
                if (s[0].length() - j - 1 == 11)
                {
                    temp += ToBig(num) + "仟"
                }
                else if (s[0].length() - j - 1 == 10)
                {
                    temp += ToBig(num) + "佰"
                }
                else if (s[0].length() - j - 1 == 9)
                {
                    if (num == 1 && flag != 1)
                    {
                        temp += "拾"
                    }
                    else
                    {
                        temp += ToBig(num) + "拾"
                    }
                }
                else if (s[0].length() - j - 1 == 8)
                {
                    temp += ToBig(num) + "亿"
                }
                else if (s[0].length() - j - 1 == 7)
                {
                    temp += ToBig(num) + "仟"
                }
                else if (s[0].length() - j - 1 == 6)
                {
                    temp += ToBig(num) + "佰"
                }
                else if (s[0].length() - j - 1 == 5)
                {
                    if (num == 1 && flag != 1)
                    {
                        temp += "拾"
                    }
                    else
                    {
                        temp += ToBig(num) + "拾"
                    }
                }
                else if (s[0].length() - j - 1 == 4)
                {
                    temp += ToBig(num) + "万"
                }
                else if (s[0].length() - j - 1 == 3)
                {
                    temp += ToBig(num) + "仟"
                }
                else if (s[0].length() - j - 1 == 2)
                {
                    temp += ToBig(num) + "佰"
                }
                else if (s[0].length() - j - 1 == 1)
                {
                    if (num == 1 && flag != 1)
                    {
                        temp += "拾"
                    }
                    else
                    {
                        temp += ToBig(num) + "拾"
                    }
                }
                else
                {
                    temp += ToBig(num)
                }
            }
        }
        temp += "元"
        for (
            int j = 0;
                j < s[1].length();
                ++j)
        {
            int num = s2[j] - 48
            if (j == 0)
            {
                if (num != 0)
                {
                    temp += ToBig(num) + "角"
                }
                else if (num == 0 && 1 < s[1].length() && s2[1] != '0')
                {
                    temp += "零"
                }
            }
            else if (j == 1)
            {
                if (num != 0)
                {
                    temp += ToBig(num) + "分"
                }
            }
        }
        println(temp)
        return temp
        //return value/10000+"万元"
    }

    /**
     * @Author Nagelan
     * @param num
     * @return 阿拉伯数字转中文数字正写
     * @date 2017/7/18 10:39
     */
    def ToBig(int num)
    {
        def str = ["壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"]
        return str[num - 1]
    }

    /**
     * 测试代码位置
     * */
    def test = { opportunity ->
        def sendPost = { String urlString, String params1 ->
            URL url = new java.net.URL(urlString)
            def json
            try
            {
                def connection = (java.net.HttpURLConnection) url.openConnection()
                connection.setDoOutput(true)
                connection.setRequestMethod("POST")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write params1 }
                connection.setConnectTimeout(10000)
                json = grails.converters.JSON.parse(connection.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
                println("返回结果" + json)
                def resultCode = json['resultCode']
                def map = [:]
                if (resultCode == "0000")
                {
                    map['flag'] = true
                    map['message'] = json['resultMsg']
                    return map
                }
                else
                {
                    map['flag'] = false
                    map['message'] = json['resultMsg']
                    return map
                }
            }
            catch (java.lang.Exception e)
            {
                e.printStackTrace()
                println e
            }
            return json
        }
        if (opportunity?.product?.name == '新e贷'){
            def res
            if (opportunity?.externalId){
                def map = [:]
                //贷款回调信息
                map["flag"] = true
                map["bussNo"] = opportunity?.externalId
                def url = CreditReportProvider.findByCode("YEWU")?.apiUrl+"/outerinfo/lmsPushBackInfo"
                String loanApproval = groovy.json.JsonOutput.toJson(map).toString()
                println "-------------------LMS推业务系统告知面谈已完成-----------------"
                println loanApproval
                println "--------------------------------------------------------"
                res = sendPost(url, loanApproval)
            }else{
                res = "LMS没有此订单业务系统订单号"
            }
            return res
        }

    }

    /**
     * @Description 结清LMS推火凤凰应结款项及相关信息
     * @Author Nagelan
     * @Params
        contract_no           合同编号
        borrower              借款人
        period                期数
        bill_date             应还款日期
        begin_date            计息区间(始)
        end_date              计息区间(终)
        days                  天数
        principal             应还本金
        intrest               应收利息
        fee                   应收服务费
        channel_fee           应收渠道费
        payment               应收综合息费
        due_days              逾期天数
        fine                  应收罚息
        settlement_date       结息时间
        itemIds               对应收本息的还款计划id
     * @Return
     * @DateTime 2017/9/19 0019 15:05
     */
    def lmsToHuofh(){
        def result
        /*def component = Component.findByName("LMS推送火凤凰结清款项")
        if (component)
        {
            result = componentService.evaluate component, new Date()
            println result
        }
        if (result instanceof Exception)
        {
            log.error "SystemComponent error!"
        }*/

        result = lmsToHuofhData1()
        println result
    }

    def lmsToHuofhData(){
        println "<--------------------开始LMS推送火凤凰结清款项-------------------->"
        println "<--------------------------组织数据开始时间"+new Date()+"-------------------------->"
        def mapAll = [:]
        def list = []
        def map
        def res
        def totalPayment = 0
        Calendar calendar1 = Calendar.getInstance()
        calendar1.clear()
        calendar1.setTime(new Date())
        calendar1.set(Calendar.HOUR_OF_DAY,0)
        calendar1.set(Calendar.MINUTE,0)
        calendar1.set(Calendar.SECOND,0)
        def today = calendar1.getTime()
        println today.format("yyyy-MM-dd")
        def dateOfMonth = calendar1.get(Calendar.DAY_OF_MONTH)
        def dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK)
        if (dayOfWeek!=7&&dayOfWeek!=1){
            //在途月结
            if (dateOfMonth>=18&&dateOfMonth<=22){
                def bills1
                if (dayOfWeek==2&&dateOfMonth==21){
                    bills1 = Bills.findAllByEndTimeGreaterThan(today-1)
                }else if (dayOfWeek==2&&dateOfMonth==22){
                    bills1 = Bills.findAllByEndTimeGreaterThan(today-2)
                }else if (dateOfMonth>=18&&dateOfMonth<=20){
                    bills1 = Bills.findAllByEndTimeGreaterThan(today+(20-dateOfMonth))
                }
                //bills1 = Bills.findAllByOpportunityInList(com.next.Opportunity.findAllBySerialNumberInList(['HD6-V4X-2GL']))
                bills1.each {
                    calendar1.set(Calendar.DAY_OF_MONTH,19)
                    def opportunity = it?.opportunity
                    if (opportunity?.isTest==false&&opportunity?.status == 'Pending'&&!com.next.Opportunity.findByParentAndType(opportunity,com.next.OpportunityType.findByName("抵押贷款结清")))
                    {
                        //扣息方式
                        def interestPaymentMethod = opportunity?.interestPaymentMethod?.name
                        def items
                        if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                            items = BillsItem.executeQuery("select id from BillsItem where bills.id = ${it?.id} and CONVERT(varchar(100), endTime, 120) like '${calendar1.getTime().format("yyyy-MM-dd")}%' and status != '已收' and period != 0")
                        }else{
                            items = BillsItem.executeQuery("select id from BillsItem where bills.id = ${it?.id} and CONVERT(varchar(100), startTime, 120) like '${(calendar1.getTime()+1).format("yyyy-MM-dd")}%' and status != '已收' and period != 0")
                        }
                        if (items){
                            map = [:]
                            map["principal"] = 0        //应还本金
                            map["intrest"] = 0          //应收利息
                            map["fee"] = 0              //应收服务费
                            map["channel_fee"] = 0      //应收渠道费
                            map["payment"] = 0          //应收综合息费
                            map["due_days"] = 0         //逾期天数
                            map["fine"] = 0             //应收罚息
                            map["settlement_date"] = today?.format("yyyy-MM-dd") //结息时间
                            map["item_ids"] = ""        //对应收本息的还款计划id
                            def contract_no = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.opportunity o left join oc.contract c where o.id = ${opportunity?.id} and c.type.name = '借款合同'")
                            if (contract_no){
                                map["contract_no"] = contract_no[0]
                            }else {
                                map["contract_no"] = contract_no
                            }
                            map["borrower"] = opportunity?.lender?.fullName
                            map["days"] = 0
                            def item
                            items.each {
                                item = BillsItem.findById(it)
                                if (item?.type?.name == "本金"){
                                    map["principal"] = item.receivable
                                }else if (item?.type?.name == "服务费费率"){
                                    map["fee"] = item.receivable
                                }else if (item?.type?.name == "渠道返费费率"){
                                    map["channel_fee"] = item.receivable
                                }else{
                                    map["intrest"] += item.receivable
                                }
                                map["payment"] += item.receivable
                                map["item_ids"] += item?.id+","
                            }
                            item = BillsItem.findById(items[0])
                            map["period"] = item.period
                            if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                                if (item.payTime){
                                    map["bill_date"] = item.payTime?.format("yyyy-MM-dd")
                                }else {
                                    map["bill_date"] = (item.endTime+1)?.format("yyyy-MM-dd")
                                }
                            }else{
                                if (item.payTime){
                                    map["bill_date"] = item.payTime?.format("yyyy-MM-dd")
                                }else {
                                    map["bill_date"] = item.startTime?.format("yyyy-MM-dd")
                                }
                            }
                            map["begin_date"] = item?.startTime?.format("yyyy-MM-dd")
                            map["end_date"] = item?.endTime?.format("yyyy-MM-dd")
                            if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息")
                            {
                                map["days"] = 0
                            }
                            else
                            {
                                map["days"] = 0
                            }
                            list.add(map)
                            totalPayment += map["payment"]
                            print "订单"+opportunity?.serialNumber+"的结清数据"
                        }
                    }
                }
            }
            def opps
            if (dayOfWeek==2){
                opps = Opportunity.executeQuery("select id from Opportunity where type.name = '抵押贷款结清' and CONVERT(varchar(100), actuaRepaymentDate, 120) like '${today.format("yyyy-MM-dd")}%' or CONVERT(varchar(100), actuaRepaymentDate, 120) like '${(today-1).format("yyyy-MM-dd")}%' or CONVERT(varchar(100), actuaRepaymentDate, 120) like '${(today-2).format("yyyy-MM-dd")}%'")
            }else{
                opps = Opportunity.executeQuery("select id from Opportunity where type.name = '抵押贷款结清' and CONVERT(varchar(100), actuaRepaymentDate, 120) like '${today.format("yyyy-MM-dd")}%'")
            }
            println '今天的opportunityId有：'+opps
            //约定还款日是当天
            opps.each {
                def jqOpportunity = Opportunity.findById(it)
                def opportunity = jqOpportunity?.parent
                def b = opportunity.bills[0]
                //扣息方式
                def interestPaymentMethod = opportunity?.interestPaymentMethod?.name
                def periodPrincipal = BillsItem.findByBills(b,[sort:"period",order:"desc"])?.period
                if (opportunity){
                    if (opportunity?.isTest==false&&opportunity?.status == 'Pending'){
                        def items = BillsItem.executeQuery("select bi.id from BillsItem bi left join bi.bills b where b.id = ${b?.id} and bi.status != '已收' and bi.period = ${periodPrincipal}")  // and bi.transactionRecord.id not in[]
                        if (items){
                            map = [:]
                            map["principal"] = 0        //应还本金
                            map["intrest"] = 0          //应收利息
                            map["fee"] = 0              //应收服务费
                            map["channel_fee"] = 0      //应收渠道费
                            map["payment"] = 0          //应收综合息费
                            map["due_days"] = 0         //逾期天数
                            map["fine"] = 0             //应收罚息
                            map["settlement_date"] = today?.format("yyyy-MM-dd") //结息时间
                            map["item_ids"] = ""        //对应收本息的还款计划id
                            //添加订单约定还款日
                            opportunity.actuaRepaymentDate = today
                            opportunity.save()
                            def contract_no = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.opportunity o left join oc.contract c where o.id = ${opportunity?.id} and c.type.name = '借款合同'")
                            map["contract_no"] = contract_no[0]
                            map["borrower"] = opportunity?.lender?.fullName
                            map["days"] = 0
                            def item
                            items.each {
                                item = BillsItem.findById(it)
                                if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                                    if (item?.type?.name == "本金"){
                                        map["principal"] = item.receivable
                                        map["period"] = item.period
                                    }else if (item?.type?.name == "服务费费率"){
                                        map["fee"] = item.receivable
                                    }else if (item?.type?.name == "渠道返费费率"){
                                        map["channel_fee"] = item.receivable
                                    }else{
                                        map["intrest"] += item.receivable
                                    }
                                    map["payment"] += item.receivable
                                }else{
                                    if (item?.type?.name == "本金"){
                                        map["principal"] = item.receivable
                                        map["period"] = item.period
                                        map["payment"] += item.receivable
                                    }
                                }
                                map["item_ids"] += item?.id+","
                            }
                            item = BillsItem.findById(items[items.size()-1])
                            if (!map["period"]){
                                map["period"] = item.period
                            }
                            map["bill_date"] = jqOpportunity?.actuaRepaymentDate?.format("yyyy-MM-dd")
                            map["begin_date"] = item?.startTime?.format("yyyy-MM-dd")
                            map["end_date"] = jqOpportunity?.actuaRepaymentDate?.format("yyyy-MM-dd")
                            list.add(map)
                            totalPayment += map["payment"]
                            print "订单"+opportunity?.serialNumber+"的结清数据"
                        }
                    }
                }
            }
            def url = CreditReportProvider.findByCode("HUOFH")?.apiUrl+"/m/huofhservice/interface.do?reqCode=apiSettle"
            mapAll["totalPayment"] = totalPayment
            mapAll["totalRecord"] = list.size()
            mapAll["data"] = list
            String loanApproval = JsonOutput.toJson(mapAll).toString()
            println loanApproval
            println "<--------------------------组织数据结束时间"+new Date()+"-------------------------->"
            for (int i = 0;i<3;i++){
                res = sendPost(url, loanApproval)
                if (res?.code=='100'){
                    break
                }
                Thread.sleep(300000)
            }
        }else {
            res = "周六日不执行"
        }
        println "<--------------------LMS推送火凤凰结清款项结束-------------------->"
        return res
    }

    def lmsToHuofhData1(){
        println "<--------------------开始LMS推送火凤凰结清款项-------------------->"
        println "<--------------------------组织数据开始时间"+new Date()+"-------------------------->"
        def mapAll = [:]
        def list = []
        def map
        def res
        def totalPayment = 0
        Calendar calendar1 = Calendar.getInstance()
        calendar1.clear()
        calendar1.setTime(new Date())
        calendar1.set(Calendar.HOUR_OF_DAY,0)
        calendar1.set(Calendar.MINUTE,0)
        calendar1.set(Calendar.SECOND,0)
        def today = calendar1.getTime()
        println today.format("yyyy-MM-dd")
        def dateOfMonth = calendar1.get(Calendar.DAY_OF_MONTH)
        def dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK)
        if (dayOfWeek!=7&&dayOfWeek!=1){
            def db = new groovy.sql.Sql (dataSource)
            def sql
            def params = []
            def datas
            //在途月结
            if (dateOfMonth>=18&&dateOfMonth<=22){
                if (dayOfWeek==2&&dateOfMonth==21){
                    datas = db.rows("select id,opportunity_id from bills where CONVERT(varchar(100), end_time, 120) > ?",[(today-1).format("yyyy-MM-dd")])
                }else if (dayOfWeek==2&&dateOfMonth==22){
                    datas = db.rows("select id,opportunity_id from bills where CONVERT(varchar(100), end_time, 120) > ?",[(today-2).format("yyyy-MM-dd")])
                }else if (dateOfMonth>=18&&dateOfMonth<=20){
                    datas = db.rows("select id,opportunity_id from bills where CONVERT(varchar(100), end_time, 120) > ?",[(today+(20-dateOfMonth)).format("yyyy-MM-dd")])
                }
                //datas = db.rows("select b.id,b.opportunity_id from bills b LEFT JOIN opportunity o ON b.opportunity_id = o.id where o.serial_number IN ['9MR-L8Y-FRS','RCR-NDT-LTC','U3M-8K1-JG9','05F-2KX-BNG','H5N-U1H-KQM','HS4-2RS-NZO','AM8-TNX-9GW','QTL-F6H-H3I','YF4-HOR-E6T','XP0-SUZ-3DL','G4H-TWA-VN5','30Q-2IV-H5T','3OP-F2R-XQF','ZPI-W11-I6I','KEL-EI6-KKJ','EHQ-I19-GQS','LTT-QIJ-PMC','5YM-S9P-06T']")
                datas.each {
                    calendar1.set(Calendar.DAY_OF_MONTH,19)
                    def opportunityId = it[1]
                    def opportunity = db.rows("select id,interest_payment_method_id,full_name FROM opportunity WHERE is_test = ? AND id = ? and status = ?",[0,opportunityId,"Pending"])
                    if (opportunity[0]&&opportunity[0][0]){
                        def jqOpportunity = db.rows("SELECT id FROM opportunity WHERE parent_id = ?" ,[opportunity[0][0]])
                        if (!jqOpportunity[0]){
                            //扣息方式
                            def interestPaymentMethod = db.rows("SELECT name FROM interest_payment_method WHERE id = ?",opportunity[0][1])[0][0]
                            def items
                            if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                                //items = BillsItem.executeQuery("select id from BillsItem where bills.id = ${it?.id} and CONVERT(varchar(100), endTime, 120) like '${calendar1.getTime().format("yyyy-MM-dd")}%' and status != '已收' and period != 0")
                                sql = "SELECT id FROM bills_item WHERE bills_id = ? and CONVERT(varchar(100), end_time, 120) like ? and status != '已收' and period != 0"
                                params.clear()
                                params.add(it[0])
                                params.add(calendar1.getTime().format("yyyy-MM-dd")+'%')
                            }else{
                                //items = BillsItem.executeQuery("select id from BillsItem where bills.id = ${it?.id} and CONVERT(varchar(100), startTime, 120) like '${(calendar1.getTime()+1).format("yyyy-MM-dd")}%' and status != '已收' and period != 0")
                                sql = "SELECT id FROM bills_item WHERE bills_id = ? and CONVERT(varchar(100), start_time, 120) like ? and status != '已收' and period != 0"
                                params.clear()
                                params.add(it[0])
                                params.add((calendar1.getTime()+1).format("yyyy-MM-dd")+'%')
                            }
                            items = db.rows(sql,params)
                            if (items){
                                map = [:]
                                map["principal"] = 0        //应还本金
                                map["intrest"] = 0          //应收利息
                                map["fee"] = 0              //应收服务费
                                map["channel_fee"] = 0      //应收渠道费
                                map["payment"] = 0          //应收综合息费
                                map["due_days"] = 0         //逾期天数
                                map["fine"] = 0             //应收罚息
                                map["settlement_date"] = today?.format("yyyy-MM-dd") //结息时间
                                map["item_ids"] = ""
                                //def contract_no = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.opportunity o left join oc.contract c where o.id = ${opportunity?.id} and c.type.name = '借款合同'")
                                def contract_no = db.rows("SELECT c.serial_number FROM opportunity_contract oc LEFT JOIN opportunity o ON oc.opportunity_id = o.id LEFT JOIN contract c ON oc.contract_id = c.id left join contract_type ct on c.type_id = ct.id WHERE o.id = ? and ct.name = '借款合同'",[opportunity[0][0]])
                                if (contract_no){
                                    map["contract_no"] = contract_no[0][0]
                                }else {
                                    map["contract_no"] = contract_no
                                }
                                map["borrower"] = opportunity[0][2]
                                map["days"] = 0
                                def item
                                db.eachRow(sql,params) {
                                    item = db.rows("SELECT bt.name,b.receivable,b.id FROM bills_item b LEFT JOIN bills_item_type bt ON b.type_id = bt.id WHERE b.id = ?",it[0])
                                    if (item[0][0] == "本金"){
                                        map["principal"] = item[0][1]
                                    }else if (item[0][0] == "服务费费率"){
                                        map["fee"] = item[0][1]
                                    }else if (item[0][0] == "渠道返费费率"){
                                        map["channel_fee"] = item[0][1]
                                    }else{
                                        map["intrest"] += item[0][1]
                                    }
                                    map["payment"] += item[0][1]
                                    map["item_ids"] += item[0][2]+","
                                }
                                item = db.rows("SELECT period,start_time,end_time,pay_time FROM bills_item  WHERE id = ? ",items[0][0])
                                map["period"] = item[0][0]
                                if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                                    if (item[0][3]){
                                        map["bill_date"] = item[0][3].format("yyyy-MM-dd")
                                    }else {
                                        map["bill_date"] = (item[0][2]+1)?.format("yyyy-MM-dd")
                                    }
                                }else{
                                    if (item[0][3]){
                                        map["bill_date"] = item[0][3]?.format("yyyy-MM-dd")
                                    }else {
                                        map["bill_date"] = item[0][1]?.format("yyyy-MM-dd")
                                    }
                                }
                                map["begin_date"] = item[0][1]?.format("yyyy-MM-dd")
                                map["end_date"] = item[0][2]?.format("yyyy-MM-dd")

                                list.add(map)
                                totalPayment += map["payment"]
                                print "订单id:"+opportunity[0][0]+"的结清数据"
                            }
                        }
                    }
                }
            }
            if (dayOfWeek==2){
                //sql = Opportunity.executeQuery("select id from Opportunity where type.name = '抵押贷款结清' and CONVERT(varchar(100), actuaRepaymentDate, 120) like '${today.format("yyyy-MM-dd")}%' or CONVERT(varchar(100), actuaRepaymentDate, 120) like '${(today-1).format("yyyy-MM-dd")}%' or CONVERT(varchar(100), actuaRepaymentDate, 120) like '${(today-2).format("yyyy-MM-dd")}%'")
                sql = "SELECT o.parent_id FROM opportunity o LEFT JOIN opportunity_type ot ON o.type_id = ot.id WHERE ot.name = ? and (CONVERT(varchar(100), o.actua_repayment_date, 120) like ? OR CONVERT(varchar(100), o.actua_repayment_date, 120) like ? OR CONVERT(varchar(100), o.actua_repayment_date, 120) like ?)"
                params.clear()
                params.add("抵押贷款结清")
                params.add(today.format( 'yyyy-MM-dd')+"%")
                params.add((today-1).format( 'yyyy-MM-dd')+"%")
                params.add((today-2).format( 'yyyy-MM-dd')+"%")
            }else{
                //sql = Opportunity.executeQuery("select id from Opportunity where type.name = '抵押贷款结清' and CONVERT(varchar(100), actuaRepaymentDate, 120) like '${today.format("yyyy-MM-dd")}%'")
                sql = "SELECT o.parent_id FROM opportunity o LEFT JOIN opportunity_type ot ON o.type_id = ot.id WHERE ot.name = ? and CONVERT(varchar(100), o.actua_repayment_date, 120) like ?"
                params.clear()
                params.add("抵押贷款结清")
                params.add(today.format( 'yyyy-MM-dd')+"%")
            }
            //约定还款日是当天
            println sql
            db.eachRow(sql,params) {
                    def opportunity = db.rows("select id,interest_payment_method_id,full_name FROM opportunity WHERE is_test = ? AND id = ? and status = ?",[0,it[0],"Pending"])[0]
                    def interestPaymentMethod = db.rows("SELECT name FROM interest_payment_method WHERE id = ?",[opportunity[1]])[0][0]
                    def b = db.rows("SELECT id FROM bills WHERE opportunity_id = ?",[opportunity[0]])[0][0]
                    if (opportunity[0]&&b){
                        //def items = BillsItem.executeQuery("select bi.id from BillsItem bi left join bi.bills b where b.id = ${b?.id} and bi.status != '已收'")
                        def periodPrincipal = db.rows("SELECT period FROM bills_item WHERE bills_id = ? ORDER BY id DESC",[b])[0][0]
                        def items = db.rows("SELECT id FROM bills_item WHERE bills_id = ? AND status != ? AND period = ? ORDER BY id DESC",[b,"已收",periodPrincipal])
                        if (items){
                            map = [:]
                            map["principal"] = 0        //应还本金
                            map["intrest"] = 0          //应收利息
                            map["fee"] = 0              //应收服务费
                            map["channel_fee"] = 0      //应收渠道费
                            map["payment"] = 0          //应收综合息费
                            map["due_days"] = 0         //逾期天数
                            map["fine"] = 0             //应收罚息
                            map["settlement_date"] = today?.format("yyyy-MM-dd") //结息时间
                            map["item_ids"] = ""
                            //添加订单约定还款日
                            def opp = Opportunity.findById(opportunity[0])
                            opp.actuaRepaymentDate = today
                            opp.save()
                            def contract_no = db.rows("SELECT c.serial_number FROM opportunity_contract oc LEFT JOIN opportunity o ON oc.opportunity_id = o.id LEFT JOIN contract c ON oc.contract_id = c.id left join contract_type ct on c.type_id = ct.id WHERE o.id = ? and ct.name = ?",[opportunity[0],"借款合同"])[0]
                            if (contract_no[0]){
                                map["contract_no"] = contract_no[0]
                            }else {
                                map["contract_no"] = contract_no
                            }
                            map["borrower"] = opportunity[2]
                            map["days"] = 0
                            def item
                            items.each {
                                item = db.rows("SELECT bt.name,b.receivable,b.id FROM bills_item b LEFT JOIN bills_item_type bt ON b.type_id = bt.id WHERE b.id = ?",[it[0]])[0]
                                if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                                    if (item[0] == "本金"){
                                        map["principal"] = item[1]
                                    }else if (item[0] == "服务费费率"){
                                        map["fee"] = item[1]
                                    }else if (item[0] == "渠道返费费率"){
                                        map["channel_fee"] = item[1]
                                    }else{
                                        map["intrest"] += item[1]
                                    }
                                    map["payment"] += item[1]
                                    map["item_ids"] += item[2]+","
                                }else {
                                    if (item[0] == "本金"){
                                        map["principal"] = item[1]
                                        map["payment"] += item[1]
                                        map["item_ids"] += item[2]+","
                                    }
                                }
                            }
                            item = db.rows("SELECT period,start_time,end_time,pay_time FROM bills_item WHERE id = ?",[items[0][0]])[0]
                            map["period"] = item[0]
                            /*if (item[3]){
                                map["bill_date"] = item[3]?.format("yyyy-MM-dd")
                            }else {
                                map["bill_date"] = item[2]?.format("yyyy-MM-dd")
                            }*/
                            map["bill_date"] = today.format("yyyy-MM-dd")
                            map["begin_date"] = item[1]?.format("yyyy-MM-dd")
                            map["end_date"] = today.format("yyyy-MM-dd")
                            list.add(map)
                            totalPayment += map["payment"]
                            print "订单id:"+opportunity[0]+"的结清数据"
                        }
                    }
                }
            def url = CreditReportProvider.findByCode("HUOFH")?.apiUrl+"/m/huofhservice/interface.do?reqCode=apiSettle"
            mapAll["totalPayment"] = totalPayment
            mapAll["totalRecord"] = list.size()
            mapAll["data"] = list
            String loanApproval = JsonOutput.toJson(mapAll).toString()
            println loanApproval
            println "<--------------------------组织数据结束时间"+new Date()+"-------------------------->"
            for (int i = 0;i<3;i++){
                res = sendPost(url, loanApproval)
                if (res?.code=='100'){
                    break
                }
                Thread.sleep(300000)
            }
        }else {
            res = "周六日不执行"
        }
        println "<--------------------LMS推送火凤凰结清款项结束-------------------->"
        return res
    }

    def sendPost(String urlString, String params1){
        URL url = new java.net.URL(urlString)
        def json
        try
        {
            def connection = (java.net.HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write params1 }
            json = grails.converters.JSON.parse(connection.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
            println("返回结果" + json)
        }
        catch (java.lang.Exception e)
        {
            e.printStackTrace()
            println e
        }
        return json
    }

    def jieqing = { object ->
        def sendPost = { String urlString, String params1 ->
            URL url = new java.net.URL(urlString)
            def json
            try
            {
                def connection = (java.net.HttpURLConnection) url.openConnection()
                connection.setDoOutput(true)
                connection.setRequestMethod("POST")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write params1 }
                json = grails.converters.JSON.parse(connection.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
                println("返回结果" + json)
            }
            catch (java.lang.Exception e)
            {
                e.printStackTrace()
                println e
            }
            return json
        }
        println "<--------------------开始LMS推送火凤凰结清款项-------------------->"
        println "<--------------------------组织数据开始时间"+new java.util.Date()+"-------------------------->"
        def mapAll = [:]
        def list = []
        def map
        def res
        def totalPayment = 0
        java.util.Calendar calendar1 = java.util.Calendar.getInstance()
        calendar1.clear()
        calendar1.setTime(new java.util.Date())
        calendar1.set(java.util.Calendar.HOUR_OF_DAY,0)
        calendar1.set(java.util.Calendar.MINUTE,0)
        calendar1.set(java.util.Calendar.SECOND,0)
        def today = calendar1.getTime()
        println today.format("yyyy-MM-dd")
        def dateOfMonth = calendar1.get(java.util.Calendar.DAY_OF_MONTH)
        def dayOfWeek = calendar1.get(java.util.Calendar.DAY_OF_WEEK)
        if (dayOfWeek!=7&&dayOfWeek!=1){
            def opps
            if (dayOfWeek==2){
                //bills = com.next.Bills.executeQuery("select id from Bills where CONVERT(varchar(100), endTime, 120) like '${today.format("yyyy-MM-dd")}%' or CONVERT(varchar(100), endTime, 120) like '${(today-1).format("yyyy-MM-dd")}%' or CONVERT(varchar(100), endTime, 120) like '${(today-2).format("yyyy-MM-dd")}%'")
                opps = com.next.Opportunity.executeQuery("select id from Opportunity where type.name = '抵押贷款结清' and CONVERT(varchar(100), actuaRepaymentDate, 120) like '${today.format("yyyy-MM-dd")}%' or CONVERT(varchar(100), actuaRepaymentDate, 120) like '${(today-1).format("yyyy-MM-dd")}%' or CONVERT(varchar(100), actuaRepaymentDate, 120) like '${(today-2).format("yyyy-MM-dd")}%'")
            }else{
                //bills = com.next.Bills.executeQuery("select id from Bills where CONVERT(varchar(100), endTime, 120) like '${today.format("yyyy-MM-dd")}%'")
                opps = com.next.Opportunity.executeQuery("select id from Opportunity where type.name = '抵押贷款结清' and CONVERT(varchar(100), actuaRepaymentDate, 120) like '${today.format("yyyy-MM-dd")}%'")
            }
            println '今天的opportunityId有：'+opps
            /* bills.each {
                 def opportunity = com.next.Bills.findById(it)?.opportunity
                 //def o1 = com.next.Opportunity.findAllByParentAndTypeAndCreatedDateGreaterThan(opportunity,com.next.OpportunityType.findByName("抵押贷款结清"),today+1)
                 if (opportunity?.isTest==false&&opportunity?.status == 'Pending'){
                     def items = com.next.BillsItem.executeQuery("select bi.id from BillsItem bi left join bi.bills b where b.id = ${it} and CONVERT(varchar(100), bi.endTime, 120) like '${today.format("yyyy-MM-dd")}%' and bi.status != '已收'")
                     if (items){
                         map = [:]
                         map["principal"] = 0        //应还本金
                         map["intrest"] = 0          //应收利息
                         map["fee"] = 0              //应收服务费
                         map["channel_fee"] = 0      //应收渠道费
                         map["payment"] = 0          //应收综合息费
                         map["due_days"] = 0         //逾期天数
                         map["fine"] = 0             //应收罚息
                         map["settlement_date"] = today?.format("yyyy-MM-dd") //结息时间
                         //添加订单约定还款日
                         opportunity.actuaRepaymentDate = today
                         opportunity.save()
                         def contract_no = com.next.Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.opportunity o left join oc.contract c where o.id = ${opportunity?.id} and c.type.name = '借款合同'")
                         map["contract_no"] = contract_no[0]
                         map["borrower"] = opportunity?.lender?.fullName
                         java.util.Calendar calendar = java.util.Calendar.getInstance()
                         calendar.clear()
                         calendar.setTime(opportunity?.actualLendingDate)
                         int day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
                         int dayLast
                         if (day < 15)
                         {
                             dayLast = 10 + day
                         }
                         else if (day >= 15 && day < 20)
                         {
                             dayLast = 10 + day
                         }
                         else
                         {
                             dayLast = day - 20
                         }
                         map["days"] = 0
                         def item
                         def type = opportunity?.interestPaymentMethod?.name
                         items.each {
                             item = com.next.BillsItem.findById(it)
                             if (item?.type?.name == "本金"){
                                 map["principal"] = item.receivable
                             }else if (item?.type?.name == "服务费费率"){
                                 map["fee"] = item.receivable
                             }else if (item?.type?.name == "渠道返费费率"){
                                 map["channel_fee"] = item.receivable
                             }else{
                                 map["intrest"] += item.receivable
                             }
                             map["payment"] += item.receivable
                         }
                         item = com.next.BillsItem.findById(items[0])
                         map["period"] = item.period
                         if (item.payTime){
                             map["bill_date"] = item.payTime?.format("yyyy-MM-dd")
                         }else {
                             map["bill_date"] = item.endTime?.format("yyyy-MM-dd")
                         }
                         map["begin_date"] = item?.startTime?.format("yyyy-MM-dd")
                         map["end_date"] = item?.endTime?.format("yyyy-MM-dd")
                         if (type =="下扣息"||type =="等额收息")
                         {
                             map["days"] = dayLast
                         }
                         else
                         {
                             map["days"] = 0
                         }

                         list.add(map)
                         totalPayment += map["payment"]
                     }
                 }
             }*/
            //约定还款日是当天
            opps.each {
                def opportunity = com.next.Opportunity.findById(it)?.parent
                def b = opportunity.bills[0]
                if (opportunity){
                    if (opportunity?.isTest==false&&opportunity?.status == 'Pending'){
                        def items = com.next.BillsItem.executeQuery("select bi.id from BillsItem bi left join bi.bills b where b.id = ${b?.id} and bi.status != '已收'")
                        if (items){
                            map = [:]
                            map["principal"] = 0        //应还本金
                            map["intrest"] = 0          //应收利息
                            map["fee"] = 0              //应收服务费
                            map["channel_fee"] = 0      //应收渠道费
                            map["payment"] = 0          //应收综合息费
                            map["due_days"] = 0         //逾期天数
                            map["fine"] = 0             //应收罚息
                            map["settlement_date"] = today?.format("yyyy-MM-dd") //结息时间

                            //添加订单约定还款日
                            opportunity.actuaRepaymentDate = today
                            opportunity.save()
                            def contract_no = com.next.Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.opportunity o left join oc.contract c where o.id = ${opportunity?.id} and c.type.name = '借款合同'")
                            map["contract_no"] = contract_no[0]
                            map["borrower"] = opportunity?.lender?.fullName

                            java.util.Calendar calendar = java.util.Calendar.getInstance()
                            calendar.clear()
                            calendar.setTime(opportunity?.actualLendingDate)
                            int day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
                            int dayLast
                            if (day < 15)
                            {
                                dayLast = 10 + day
                            }
                            else if (day >= 15 && day < 20)
                            {
                                dayLast = 10 + day
                            }
                            else
                            {
                                dayLast = day - 20
                            }
                            map["days"] = 0
                            def item
                            def type = opportunity?.interestPaymentMethod?.name
                            items.each {
                                item = com.next.BillsItem.findById(it)
                                if (item?.type?.name == "本金"){
                                    map["principal"] = item.receivable
                                    map["period"] = item.period
                                }else if (item?.type?.name == "服务费费率"){
                                    map["fee"] = item.receivable
                                }else if (item?.type?.name == "渠道返费费率"){
                                    map["channel_fee"] = item.receivable
                                }else{
                                    map["intrest"] += item.receivable
                                }
                                map["payment"] += item.receivable
                            }
                            item = com.next.BillsItem.findById(items[items.size()-1])
                            if (!map["period"]){
                                map["period"] = item.period
                            }
                            if (item.payTime){
                                map["bill_date"] = item.payTime?.format("yyyy-MM-dd")
                            }else {
                                map["bill_date"] = item.endTime?.format("yyyy-MM-dd")
                            }
                            map["begin_date"] = item?.startTime?.format("yyyy-MM-dd")
                            map["end_date"] = item?.endTime?.format("yyyy-MM-dd")
                            if (type =="下扣息"||type =="等额收息")
                            {
                                map["days"] = dayLast
                            }
                            else
                            {
                                map["days"] = 0
                            }
                            list.add(map)
                            totalPayment += map["payment"]
                        }
                    }
                }
            }
            //在途月结
            if (dateOfMonth>=18&&dateOfMonth<=22){
                def bills1
                if (dayOfWeek==2&&dateOfMonth==21){
                    bills1 = com.next.Bills.findAllByEndTimeGreaterThan(today-1)
                }else if (dayOfWeek==2&&dateOfMonth==22){
                    bills1 = com.next.Bills.findAllByEndTimeGreaterThan(today-2)
                }else if (dateOfMonth>=18&&dateOfMonth<=20){
                    bills1 = com.next.Bills.findAllByEndTimeGreaterThan(today+(20-dateOfMonth))
                }
                //bills1 = com.next.Bills.findAllByOpportunityInList(com.next.Opportunity.findAllBySerialNumberInList(['9MR-L8Y-FRS','RCR-NDT-LTC','U3M-8K1-JG9','05F-2KX-BNG','H5N-U1H-KQM','HS4-2RS-NZO','AM8-TNX-9GW','QTL-F6H-H3I','YF4-HOR-E6T','XP0-SUZ-3DL','G4H-TWA-VN5','30Q-2IV-H5T','3OP-F2R-XQF','ZPI-W11-I6I','KEL-EI6-KKJ','EHQ-I19-GQS','LTT-QIJ-PMC','5YM-S9P-06T']))
                //println "在途月结的订单有："
                def a = 0
                bills1.each {
                    a+=1
                    if (a<=10){
                        calendar1.set(java.util.Calendar.DAY_OF_MONTH,19)
                        def opportunity = it?.opportunity
                        if (opportunity?.isTest==false&&opportunity?.status == 'Pending'&&!com.next.Opportunity.findByParentAndType(opportunity,com.next.OpportunityType.findByName("抵押贷款结清")))
                        {
                            //扣息方式
                            def interestPaymentMethod = opportunity?.interestPaymentMethod?.name
                            def items
                            def time = new java.util.Date()
                            println "<--------------------------查询items开始时间"+time+"-------------------------->"
                            if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                                items = com.next.BillsItem.executeQuery("select id from BillsItem where bills.id = ${it?.id} and CONVERT(varchar(100), endTime, 120) like '${calendar1.getTime().format("yyyy-MM-dd")}%' and status != '已收' and period != 0")
                            }else{
                                items = com.next.BillsItem.executeQuery("select id from BillsItem where bills.id = ${it?.id} and CONVERT(varchar(100), startTime, 120) like '${(calendar1.getTime()+1).format("yyyy-MM-dd")}%' and status != '已收' and period != 0")
                            }
                            println "<--------------------------查询items结束时间"+new java.util.Date()+"耗时"+(new java.util.Date().getTime() - time.getTime())+"-------------------------->"
                            if (items){
                                //print "'"+opportunity?.serialNumber+"',"
                                map = [:]
                                map["principal"] = 0        //应还本金
                                map["intrest"] = 0          //应收利息
                                map["fee"] = 0              //应收服务费
                                map["channel_fee"] = 0      //应收渠道费
                                map["payment"] = 0          //应收综合息费
                                map["due_days"] = 0         //逾期天数
                                map["fine"] = 0             //应收罚息
                                map["settlement_date"] = today?.format("yyyy-MM-dd") //结息时间
                                def contract_no = com.next.Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.opportunity o left join oc.contract c where o.id = ${opportunity?.id} and c.type.name = '借款合同'")
                                if (contract_no){
                                    map["contract_no"] = contract_no[0]
                                }else {
                                    map["contract_no"] = contract_no
                                }
                                map["borrower"] = opportunity?.lender?.fullName
                                map["days"] = 0
                                def item
                                time = new java.util.Date()
                                println "<--------------------------查询billsItems开始时间"+time+"-------------------------->"
                                items.each {
                                    item = com.next.BillsItem.findById(it)
                                    if (item?.type?.name == "本金"){
                                        map["principal"] = item.receivable
                                    }else if (item?.type?.name == "服务费费率"){
                                        map["fee"] = item.receivable
                                    }else if (item?.type?.name == "渠道返费费率"){
                                        map["channel_fee"] = item.receivable
                                    }else{
                                        map["intrest"] += item.receivable
                                    }
                                    map["payment"] += item.receivable
                                }
                                println "<--------------------------查询billsItems结束时间"+new java.util.Date()+"耗时"+(new java.util.Date().getTime()-time.getTime())+"-------------------------->"
                                item = com.next.BillsItem.findById(items[0])
                                map["period"] = item.period
                                if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                                    if (item.payTime){
                                        map["bill_date"] = item.payTime?.format("yyyy-MM-dd")
                                    }else {
                                        map["bill_date"] = (item.endTime+1)?.format("yyyy-MM-dd")
                                    }
                                }else{
                                    if (item.payTime){
                                        map["bill_date"] = item.payTime?.format("yyyy-MM-dd")
                                    }else {
                                        map["bill_date"] = item.startTime?.format("yyyy-MM-dd")
                                    }
                                }
                                map["begin_date"] = item?.startTime?.format("yyyy-MM-dd")
                                map["end_date"] = item?.endTime?.format("yyyy-MM-dd")
                                if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息")
                                {
                                    map["days"] = 0
                                }
                                else
                                {
                                    map["days"] = 0
                                }
                                list.add(map)
                                totalPayment += map["payment"]
                            }
                        }
                    }
                }
            }
            def url = CreditReportProvider.findByCode("HUOFH")?.apiUrl+"/m/huofhservice/interface.do?reqCode=apiSettle"
            mapAll["totalPayment"] = totalPayment
            mapAll["totalRecord"] = list.size()
            mapAll["data"] = list
            String loanApproval = groovy.json.JsonOutput.toJson(mapAll).toString()
            println loanApproval
            println "<--------------------------组织数据结束时间"+new java.util.Date()+"-------------------------->"
            for (int i = 0;i<3;i++){
                res = sendPost(url, loanApproval)
                if (res.code=='100'){
                    break
                }
                java.lang.Thread.sleep(300000)
            }
        }else {
            res = "周六日不执行"
        }
        println "<--------------------LMS推送火凤凰结清款项结束-------------------->"
        return res
    }
}
/**
 * Created by 夏瑞坤 on 2017/07/12.*/
{ opportunity ->
    def opportunityStage = com.next.OpportunityStage.findByCode(48)
    def opportunityFlows = com.next.OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)

    /*------------------------------贷款回调--------------------------------------*/
    //外部id
    def externalId = opportunity?.externalId
    //订单号
    def serialNumber = opportunity?.serialNumber
    //实际授信金额
    def actualAmountOfCredit = opportunity?.actualAmountOfCredit
    //审批期限or放款期限
    def actualLoanDuration = opportunity?.actualLoanDuration
    //审批利率or放款利率
    def opportunityProductList = com.next.OpportunityProduct.findAllByProductAndOpportunity(opportunity?.productAccount, opportunity)
    java.math.BigDecimal b1
    java.math.BigDecimal monthlyInterest = new java.math.BigDecimal(Double.toString(0.00))
    java.math.BigDecimal serviceCharge = new java.math.BigDecimal(Double.toString(0.00))
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
                    monthlyInterest = monthlyInterest.add(b1.divide(actualLoanDuration, 9, BigDecimal.ROUND_HALF_UP))
                }
            }
            if (opportunityProduct?.productInterestType?.name == "郊县" || opportunityProduct?.productInterestType?.name == "大头小尾" || opportunityProduct?.productInterestType?.name == "信用调整" || opportunityProduct?.productInterestType?.name == "二抵加收费率" || opportunityProduct?.productInterestType?.name == "老人房（65周岁以上）" || opportunityProduct?.productInterestType?.name == "老龄房（房龄35年以上）" || opportunityProduct?.productInterestType?.name == "非7成区域" || opportunityProduct?.productInterestType?.name == "大额（单套大于1200万）")
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
                            monthlyInterest = monthlyInterest.add(b1.divide(actualLoanDuration, 9, BigDecimal.ROUND_HALF_UP))
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
                            serviceCharge = serviceCharge.add(b1.divide(actualLoanDuration, 9, BigDecimal.ROUND_HALF_UP))
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
                        monthlyInterest = monthlyInterest.add(b1.divide(actualLoanDuration, 9, BigDecimal.ROUND_HALF_UP))
                    }
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
                    serviceCharge = serviceCharge.add(b1.divide(actualLoanDuration, 9, BigDecimal.ROUND_HALF_UP))
                }
            }
        }
    }
    //失败原因
    def causeOfFailure = opportunity?.causeOfFailure?.name
    //失败原因说明
    def descriptionOfFailure = opportunity?.descriptionOfFailure
    //贷款审批结果
    def approvalResult = "1"
    //贷款审批已完成（区域总经理）审批意见
    def comments = opportunityFlows?.comments

    /*-----------------------放款回调-----------------------------------*/
    //实际放款时间
    def actualLendingDate
    if (opportunity?.actualLendingDate)
    {
        actualLendingDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(opportunity?.actualLendingDate)
    }
    else
    {
        actualLendingDate = ""
    }
    //实际放款金额
    def actualLoanAmount = opportunity?.actualLoanAmount
    //放款审批结果
    def loanResult = "1"
    //放款审批结论
    opportunityStage = com.next.OpportunityStage.findByCode(33)
    opportunityFlows = com.next.OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
    def loanComments = opportunityFlows?.comments
    //放款审批拒绝原因
    def loanRefuseConclusion = ""
    //还款计划
    /*def bills
    if(opportunity?.type?.code == '10')
    {
        bills = opportunity?.bills
    }
    else
    {
        bills = []
    }*/
    //放款通道
    def passagewayValue = ""
    //放款账号
    def loanAccount = ""
    def opportunityFlexFieldCategory = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
    if (opportunityFlexFieldCategory)
    {
        opportunityFlexFieldCategory?.fields?.each {
            if (it.name == '放款通道')
            {
                if (it?.value)
                {
                    passagewayValue = it?.value
                }
            }
            if (it.name == '放款账号')
            {
                if (it?.value)
                {
                    loanAccount = it?.value
                }
            }
        }
    }

    //风险结论及放款前要求
    def riskConclusion = "优势：${opportunity?.advantages}；"
    riskConclusion += "劣势：${opportunity?.disadvantages}；"
    riskConclusion += "补充说明：${opportunity?.additionalComment}；"
    riskConclusion += "授信金额：${opportunity?.actualAmountOfCredit}万元；"
    def collaterals = com.next.Collateral.findAllByOpportunity(opportunity)
    def loanToValue = ""
    collaterals.each {
        loanToValue += it?.loanToValue + "%、"
    }
    loanToValue = loanToValue.substring(0, loanToValue.lastIndexOf("、"))
    riskConclusion += "抵押率：${loanToValue}；"
    riskConclusion += "实际货款期限：${opportunity?.actualLoanDuration}月；"
    def opportunityContacts = com.next.OpportunityContact.findAll("from OpportunityContact where opportunity.id = ${opportunity.id} order by type.id")
    def fullNameList = ""
    opportunityContacts.each {
        fullNameList += it?.contact?.fullName + "、"
    }
    fullNameList = fullNameList.substring(0, fullNameList.lastIndexOf("、"))
    riskConclusion += "签约主体：${fullNameList}；"
    riskConclusion += "是否要求面审：${opportunity?.interviewRequired}；"
    if (opportunity?.notarizationMatters)
    {
        riskConclusion += "公证事项：${opportunity?.notarizationMatters}；"
    }
    def mortgageCertification = opportunity?.mortgageCertification == 'true' ? "抵押登记受理单" : "他项权利证书"
    riskConclusion += "抵押凭证：${mortgageCertification}；"
    if (opportunity?.signedDocuments)
    {
        riskConclusion += "签署文件：${opportunity?.signedDocuments}；"
    }
    if (opportunity?.mortgageMaterials)
    {
        riskConclusion += "收押材料：${opportunity?.mortgageMaterials}；"
    }
    if (opportunity?.otherIssues)
    {
        riskConclusion += "其他：${opportunity?.otherIssues}；"
    }

    //数据都放入一个map中转换成JSON
    def loanApprovalMap = [:]
    //贷款回调信息
    loanApprovalMap.put("bussNo", externalId)
    loanApprovalMap.put("serialNumber", serialNumber)
    loanApprovalMap.put("actualAmountOfCredit", actualAmountOfCredit)
    loanApprovalMap.put("approveInterest", monthlyInterest)
    loanApprovalMap.put("approveTerm", actualLoanDuration)
    loanApprovalMap.put("causeOfFailure", causeOfFailure)
    loanApprovalMap.put("descriptionOfFailure", descriptionOfFailure)
    loanApprovalMap.put("approveResult", approvalResult)
    loanApprovalMap.put("approveConclusion", comments)
    loanApprovalMap.put("lendName", passagewayValue)
    loanApprovalMap.put("approveServiceCharge", serviceCharge)
    loanApprovalMap.put("riskConclusion", riskConclusion)

    //放款回调信息
    loanApprovalMap.put("actualLendingDate", actualLendingDate)
    loanApprovalMap.put("actualLoanAmount", actualLoanAmount)
    loanApprovalMap.put("loanResult", loanResult)
    loanApprovalMap.put("loanConclusion", loanComments)
    loanApprovalMap.put("loanRefuseConclusion", loanRefuseConclusion)
    loanApprovalMap.put("loanInterest", monthlyInterest)
    loanApprovalMap.put("loanTerm", actualLoanDuration)
    loanApprovalMap.put("accountName", loanAccount)



    String loanApproval = groovy.json.JsonOutput.toJson(loanApprovalMap).toString()
    println "--------------------------------------------------------"
    println loanApproval
    println "--------------------------------------------------------"
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

    def res = sendPost("http://s87.zhongjiaxin.com:8083/outerinfo/readlmsinfo", loanApproval)
    return res
}
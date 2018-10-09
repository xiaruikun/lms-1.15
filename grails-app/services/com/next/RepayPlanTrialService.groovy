package com.next

import grails.transaction.Transactional
import groovy.json.JsonOutput

@Transactional
class RepayPlanTrialService
{

    //还款计划试算
    def repayPlanTrial(String json)
    {
        def res = sendPost("http://219.143.184.27:10000/ecp/repayPlanTrial", json)
        return res
    }

    //线下打款通知
    def compBuyBackPayNotity(Opportunity opportunity)
    {
        def res
        return res
    }

    //还款账户变更
    def changeRepayAccount(Opportunity opportunity)
    {

        def token = "token"
        //token
        def tradeCode = "04001040"
        //交易代码(接口代码)
        def cusId = "A060000009"
        //合作方编码
        def transDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new Date())
        //交易时间
        def reqNo = "20170718111111"
        //请求号

        def changeRepayMap = [:]

        //      def loanNo = com.next.ChannelRecord.findByOpportunity(opportunity)?.loanNo
        def loanNo = "JJ201711170000005003"
        //借据号
        def opportunityBankAccount = com.next.OpportunityBankAccount.findByOpportunityAndType(opportunity, com.next.OpportunityBankAccountType.findByName("还款账号"))
        def bankAccount = opportunityBankAccount?.bankAccount
        def repaymentAccNo = bankAccount?.numberOfAccount
        //还款银行账号
        def repaymentAccName = bankAccount?.name
        //还款银行卡账户名
        def repaymentMobNo = bankAccount?.cellphone
        //还款预留手机号
        def repaymentBankName = bankAccount?.bank?.name
        //还款账号开户行名

        def repaymentBankCard = ""
        //根据xls进行判断
        //还款账号开户行号
        def repaymentBankNo = ""
        //还款账号开户行行别
        if (repaymentBankName == "工商银行")
        {
            repaymentBankCard = "ICBC"
            repaymentBankNo = "120"
        }
        else if (repaymentBankName == "建设银行")
        {
            repaymentBankCard = "CCB"
            repaymentBankNo = "118"
        }
        else if (repaymentBankName == "招商银行")
        {
            repaymentBankCard = "CMB"
            repaymentBankNo = "119"
        }
        else if (repaymentBankName == "农业银行")
        {
            repaymentBankCard = "ABC"
            repaymentBankNo = "121"
        }
        else if (repaymentBankName == "光大银行")
        {
            repaymentBankCard = "CEB"
            repaymentBankNo = "113"
        }
        else if (repaymentBankName == "广发银行")
        {
            repaymentBankCard = "GDB"
            repaymentBankNo = "122"
        }
        else if (repaymentBankName == "兴业银行")
        {
            repaymentBankCard = "CIB"
            repaymentBankNo = "111"
        }
        else if (repaymentBankName == "上海浦东发展银行")
        {
            repaymentBankCard = "SPDB"
            repaymentBankNo = "114"
        }
        else if (repaymentBankName == "民生银行")
        {
            repaymentBankCard = "CMBC"
            repaymentBankNo = "125"
        }
        else if (repaymentBankName == "交通银行")
        {
            repaymentBankCard = "COMM"
            repaymentBankNo = "123"
        }

        def changeDate = new java.text.SimpleDateFormat("yyyyMMdd").format(bankAccount?.modifiedDate)
        //变更日期

        changeRepayMap["token"] = "token"
        changeRepayMap["tradeCode"] = tradeCode
        changeRepayMap["cusId"] = cusId
        changeRepayMap["reqNo"] = reqNo
        changeRepayMap["transDate"] = transDate
        changeRepayMap["loanNo"] = loanNo
        changeRepayMap["repaymentBankNo"] = repaymentBankNo
        changeRepayMap["repaymentBankName"] = repaymentBankName
        changeRepayMap["repaymentBankCard"] = repaymentBankCard
        changeRepayMap["repaymentAccNo"] = repaymentAccNo
        changeRepayMap["repaymentAccName"] = repaymentAccName
        changeRepayMap["repaymentMobNo"] = repaymentMobNo
        changeRepayMap["changeDate"] = changeDate

        def json = JsonOutput.toJson(changeRepayMap).toString()
        def res = sendPost("http://219.143.184.27:10000/ecp/changeAcc", json)
        return res
    }

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
            // println e
        }
        return result
    }
}

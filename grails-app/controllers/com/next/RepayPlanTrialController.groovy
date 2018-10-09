package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER'])
class RepayPlanTrialController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def repayPlanTrialService

    /**
     * 还款计划试算
     * @Author 王明卿
     * @Date 2017/7/25
     */
    def repayPlanTrial()
    {
        def repayPlanTrialMap = [:]
        def token = "token"
        //token
        def tradeCode = "04001013"
        //交易代码(接口代码)
        def cusId = "A060000009"
        //合作方编码
        def transDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new Date())
        //交易时间
        // def productNo = "ZJX001"
        def productNo = "ZJX001"
        //产品编号
        def origPrcp = params['origPrcp']
        //实际放款金额
        //申请金额
        def dueDay = params['dueDay']
        //扣款日
        def loanActvDt = params['loanActvDt'].toString()
        loanActvDt = loanActvDt.substring(0, 10).replace("-", "")
        //发放日期
        def lastDueDt = params['lastDueDt'].toString()
        lastDueDt = lastDueDt.substring(0, 10).replace("-", "")
        //合同到期日
        def loanTypStr = params['loanTyp']
        //贷款品种
        def loanTyp = ""
        if (loanTypStr == "速e贷")
        {
            loanTyp = "01"
        }
        def intStartDt = params['intStartDt'].toString()
        intStartDt = intStartDt.substring(0, 10).replace("-", "")
        //起息日,仅限发放日当日
        def loanIntRate = params['loanIntRate']
        //执行利率
        def paymFreqUnit = params['paymFreqUnit']
        //还款间隔单位 M: 月 W:周 Q: 季 Y:年
        if ("月".equals(paymFreqUnit))
        {
            paymFreqUnit = "M"
        }
        else if ("周".equals(paymFreqUnit))
        {
            paymFreqUnit = "W"
        }
        else if ("季".equals(paymFreqUnit))
        {
            paymFreqUnit = "Q"
        }
        else if ("年".equals(paymFreqUnit))
        {
            paymFreqUnit = "Y"
        }
        def paymFreqFreq = params['paymFreqFreq']
        //还款间隔,一般填1
        def loanPaymMtd = params['loanPaymMtd']
        //还款方式
        def loanPaymTyp = ""
        if ("等额本息" == loanPaymMtd)
        {
            loanPaymTyp = "01"
        }

        def reqNo = "20170718111111"
        //请求号
        repayPlanTrialMap.put("token", token)
        repayPlanTrialMap.put("tradeCode", tradeCode)
        repayPlanTrialMap.put("cusId", cusId)
        repayPlanTrialMap.put("reqNo", reqNo)
        repayPlanTrialMap.put("transDate", transDate)
        repayPlanTrialMap.put("productNo", productNo)
        repayPlanTrialMap.put("origPrcp", origPrcp) //签名
        repayPlanTrialMap.put("dueDay", dueDay)
        repayPlanTrialMap.put("loanActvDt", loanActvDt)
        repayPlanTrialMap.put("lastDueDt", lastDueDt)
        repayPlanTrialMap.put("loanTyp", loanTyp)
        repayPlanTrialMap.put("intStartDt", intStartDt)
        repayPlanTrialMap.put("loanIntRate", loanIntRate) //签名
        repayPlanTrialMap.put("paymFreqUnit", paymFreqUnit)
        repayPlanTrialMap.put("paymFreqFreq", paymFreqFreq)
        repayPlanTrialMap.put("loanPaymMtd", loanPaymMtd)
        repayPlanTrialMap.put("loanPaymTyp", loanPaymTyp)

        def json = groovy.json.JsonOutput.toJson(repayPlanTrialMap)
        println "***********repayPlanTrialResult*************"

        //调用还款试算外部接口
        def res = repayPlanTrialService.repayPlanTrial(json)
        render(view: "index", model: [res: res])
    }

    /**
     * 还款账户变更
     * @Author 王明卿
     * @Date 2017/7/25
     */
    def changeAcc()
    {
        println "***********changeAcc*************"
        Opportunity opportunity = com.next.Opportunity.find("from Opportunity o where o.id = '72941'")
        def res = repayPlanTrialService.changeRepayAccount(opportunity)
        println res
        render(view: "index", model: [res: res])
    }

    /**
     * 对账文件推送
     * @Author 王明卿
     * @Date 2017/7/25
     */
    def billFileResults()
    {
        def json = request.JSON
        println "************************* billFileResults ***************************"
        println json
        def applSeq = json["applSeq"]
        //序号
        def fileNo = json["fileNo"]
        //文件编号
        def file = json["file"]
        //文件?格式？？作为附件存储
        def date = json["date"]
        //对账日期
        def number = json["number"]
        //条数

        //如何存储
        //......

    }

    /**
     * 线下打款通知
     * @Author 王明卿
     * @Date 2017/7/25
     */
    def compBuyBackPayNotity()
    {
        println "***********compBuyBackPayNotity*************"
        Opportunity opportunity = com.next.Opportunity.find("from Opportunity o where o.id = '72941'")
        def res = repayPlanTrialService.compBuyBackPayNotity(opportunity)
        println res
    }

    def create()
    {
        render(view: "create")
    }

    def show()
    {
        render(view: "show")
    }
}

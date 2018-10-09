/**
 * Created by 夏瑞坤 on 2017/06/27.*/
def opportunityList = com.next.Opportunity.findAll("from Opportunity where status = 'Pending' and dateOfMortgage is not null")
def opportunities = []
//放款已完成阶段之后的订单
opportunityList?.each {
    def opportunityStage = com.next.OpportunityStage.findByCode("10")
    def opportunityLoanFlow = com.next.OpportunityFlow.findByOpportunityAndStage(it, opportunityStage)
    def currentFlow = com.next.OpportunityFlow.findByOpportunityAndStage(it, it?.stage)
    if (opportunityLoanFlow?.executionSequence <= currentFlow?.executionSequence)
    {
        opportunities.add(it)
    }
}
println "----------------opportunities--------------------" + opportunities.size()
//发送短信
def sendMessage = { String cellphone, String text ->
    def response

    def corpId = "kaijing"
    def pwd = "8FTeR5dA"
    def encode = "UTF-8"

    def errors = ["00": "多个手机号请求发送成功",
        "02": "IP限制",
        "03": "单个手机号请求发送成功",
        "04": "用户名错误",
        "05": "密码错误",
        "07": "发送时间有误",
        "08": "内容有误",
        "09": "手机号码有误",
        "10": "扩展号码有误",
        "11": "余额不足",
        "-1": "服务器内部异常"]

    def message = new com.next.Message()
    message.platform = "领选互联"
    message.text = text
    message.cellphone = cellphone
    message.save()

    java.net.URL url = new java.net.URL("http://101.200.29.88:8082/SendMT/SendMessage")
    java.net.HttpURLConnection connection = (HttpURLConnection) url.openConnection()
    connection.setDoOutput(true)
    connection.setRequestMethod("POST")

    def params = "CorpID=" + java.net.URLEncoder.encode(corpId, encode) + "&Pwd=" + java.net.URLEncoder.encode(pwd, encode) + "&Mobile=" + java.net.URLEncoder.encode(cellphone, encode) + "&Content=" + java.net.URLEncoder.encode(text, encode)


    connection.outputStream.withWriter { Writer writer -> writer.write params }
    response = connection.inputStream.withReader { Reader reader -> reader.text }

    def result = response.toString().split(",")
    def code = result[0]


    if (code in ["00", "03"])
    {
        message.status = "Succesful"
        message.returnCode = result[0]
        message.save()
    }
    else
    {
        message.status = "Failed"
        message.returnCode = result[0]
        message.returnMessage = errors[result[0]]
        message.save()

    }
}
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd")
opportunities?.each {
    def opp = it
    //是否有他项证明
    def attachmentJudge
    def attachmentType = com.next.AttachmentType.find("from AttachmentType where name = '他项证明'");
    def attachments = com.next.Attachments.findAll("from Attachments where opportunity.id = ${opp?.id} and type.id = ${attachmentType?.id}");
    if (attachments.size() > 0)
    {
        attachmentJudge = true
    }
    else
    {
        attachmentJudge = false
    }

    if (!attachmentJudge)
    {
        java.util.Calendar ca = java.util.Calendar.getInstance()
        java.util.Date date = new java.util.Date()
        ca.setTime(sdf.parse(sdf.format(opp?.dateOfMortgage)))

        //计算上传他项最晚时间
        def daysOfOtherRights = 0
        def collaterals = com.next.Collateral.findAllByOpportunity(opp)
        collaterals.each {
            def days = com.next.District.findByCityAndName(it?.city, it?.district)?.daysOfOtherRights
            if (days && daysOfOtherRights < days)
            {
                daysOfOtherRights = days
            }
        }
        daysOfOtherRights = daysOfOtherRights + 3
        ca.add(java.util.Calendar.DAY_OF_MONTH, daysOfOtherRights)
        if (daysOfOtherRights > 3 && sdf.format(date) > sdf.format(ca.getTime()))
        {
            def userCellphone
            //得出权证部操作人
            if (opp?.contact?.city?.name)
            {
                def stage1 = com.next.OpportunityStage.findByName("放款已完成")
                def teamRole = com.next.TeamRole.findByName("Approval")
                userCellphone = com.next.OpportunityRole.findByOpportunityAndStageAndTeamRole(opp, stage1, teamRole)?.user?.cellphone
            }

            if (userCellphone)
            {
                def text = "【中佳信】 订单编号：" + it?.serialNumber + "，借款人：" + it?.fullName + "，他项证明材料未上传，请及时补充"
                sendMessage(userCellphone, text)
            }
        }
    }
}
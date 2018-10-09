/**
 * Created by 夏瑞坤 on 2017/06/27.*/
{ opportunity ->
    //调用查询contact
    def evaluate = { String urlString, String externalId ->
        String response = ""
        String params
        if (externalId)
        {
            params = "externalId=" + URLEncoder.encode(externalId, "UTF-8")
        }

        String apiUrl = urlString + "?" + params
        URL url = new java.net.URL(apiUrl)
        println "url:" + url
        try
        {
            def connection = (java.net.HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("GET")
            //          def response = connection.inputStream.withReader { Reader reader -> reader.text }
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = br.readLine()) != null)
            {
                response += line
            }
            if (response)
            {
                println "pv调用查询成功"
                def json = grails.converters.JSON.parse(response)
                return json

            }
        }
        catch (java.lang.Exception e)
        {
            println "调用pv失败"
            println e.printStackTrace()
        }
    }

    println "ralf 111"
    def urlStr = "http://10.0.8.127/provider/queryByExternalId"
    def collaterals = com.next.Collateral.findAll("from Collateral where opportunity.id = ${opportunity.id}")
    println "ralf 222"
    for (
        def c in
            collaterals)
    {
        def pvCollateral = evaluate(urlStr, c?.externalId)
        def totalPrice
        if (opportunity?.product?.name == "融e贷" || opportunity?.product?.name == "等额本息")
        {
            totalPrice = pvCollateral.unitPrice * c.area * 0.8 / 10000
        }
        else
        {
            totalPrice = pvCollateral.unitPrice * c.area * 0.7 / 10000
        }
        println "ralf total price ${totalPrice}"
        println "ralf actual amount of credit ${opportunity.actualAmountOfCredit}"
        if (pvCollateral?.valuationReliability != "C")
        {
            println "ralf 444"
            return "评估状态有误，请联系评房组完成评估"
            // return false
        }
        if (c?.mortgageType?.name in ['一抵', '一抵转单'])
        {
            if (opportunity.actualAmountOfCredit > totalPrice)
            {
                println "ralf 555"
                // return "房产估值已更新，当前价格${pvCollateral.unitPrice}，请修改审批可贷金额"
                def price = pvCollateral.unitPrice * c.area / 10000
                def msg = "房产价值变动，请联系区域风控确认最终放款金额"
                // return "房产估值已更新，请修改审批可贷金额"
                return msg
                // return false
            }
            else if (opportunity.actualAmountOfCredit <= totalPrice)
            {
                println "ralf 666"
                return true
            }
        }
        else if (c?.mortgageType?.name in ['二抵', '二抵转单'])
        {
            if (opportunity.actualAmountOfCredit + c?.firstMortgageAmount > totalPrice)
            {
                println "ralf 555"
                def price = pvCollateral.unitPrice * c.area / 10000
                def msg = "房产价值变动，请联系区域风控确认最终放款金额"
                return msg
            }
            else if (opportunity.actualAmountOfCredit + c?.firstMortgageAmount <= totalPrice)
            {
                println "ralf 666"
                return true
            }
        }

    }
}
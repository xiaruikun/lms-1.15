package com.next

import grails.converters.JSON
import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class ColApplyService
{
    //押品信息提交
    def CollaterService(com.next.Opportunity opportunity)
    {
        def collateral = [:]
        //报文头信息
        //collateral["token"]="token"
        collateral["token"] = obtainToken()
        collateral["cusId"] = "A060000009"
        collateral["tradeCode"] = "04001064"
        collateral["transDate"] = new Date().format("yyyyMMddHHmmss")
        //押品编号
        collateral["mortgageNo"] = com.next.OpportunityContract.find("from OpportunityContract where opportunity.id" + " = ${opportunity.id} and contract.type.name = '借款合同'")?.contract?.serialNumber
        //担保类型
        collateral["mortgageType"] = "00"
        //押品类型
        collateral["mortgagesType"] = "0"
        //登记时间
        collateral["bookingTime"] = com.next.Collateral.findByOpportunity(opportunity).requestStartTime.format("yyyyMMdd")
        //权属人身份(xiugai)
        if (com.next.OpportunityContact.findByOpportunityAndType(opportunity, com.next.OpportunityContactType.findByName("借款人")))
        {
            collateral["mortgagerType"] = "01"
        }
        else
        {
            collateral["mortgagerType"] = "02"
        }
        //权属人姓名
        collateral["ownerName"] = com.next.OpportunityContact.findByOpportunityAndType(opportunity, com.next.OpportunityContactType.findByName("借款人"))?.contact?.fullName
        //权属人证件类型
        collateral["ownerIDType"] = "20"
        //所有权人证件号码
        collateral["ownerID"] = com.next.OpportunityContact.findByOpportunityAndType(opportunity, com.next.OpportunityContactType.findByName("借款人"))?.contact?.idNumber
        //房产类型
        if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "住宅"))
        {
            collateral["buildingType"] = "00"
        }
        else if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "央产房"))
        {
            collateral["buildingType"] = "02"
        }
        else if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "商品房"))
        {
            collateral["buildingType"] = "01"
        }
        else if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "按经济适用房管理"))
        {
            collateral["buildingType"] = "04"
        }
        else if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "成本价房改房"))
        {
            collateral["buildingType"] = "05"
        }
        else if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "标准价房改房"))
        {
            collateral["buildingType"] = "06"
        }
        else if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "回迁房"))
        {
            collateral["buildingType"] = "08"
        }
        else if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "军产房"))
        {
            collateral["buildingType"] = "09"
        }
        else if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "校产房"))
        {
            collateral["buildingType"] = "10"
        }
        else if (com.next.Collateral.findByOpportunityAndAssetType(opportunity, "其他"))
        {
            collateral["buildingType"] = "11"
        }
        else
        {
            collateral["buildingType"] = "21"
        }
        //房产获得方式
        if (com.next.Collateral.findByOpportunityAndHouseOrigin(opportunity, "购置"))
        {
            collateral["gainType"] = "01"
        }
        else if (com.next.Collateral.findByOpportunityAndHouseOrigin(opportunity, "继承"))
        {
            collateral["gainType"] = "03"
        }
        else if (com.next.Collateral.findByOpportunityAndHouseOrigin(opportunity, "赠与"))
        {
            collateral["gainType"] = "02"
        }
        else
        {
            collateral["gainType"] = "04"
        }
        //购房合同号(买卖是必输入)
        collateral["houseContNo"] = com.next.OpportunityContract.find("from OpportunityContract where opportunity.id" + " = ${opportunity.id} and contract.type.name = '借款合同'")?.contract?.serialNumber
        //房产状态
        collateral["buildingStates"] = "01"
        //房产证编号
        collateral["buildingNo"] = com.next.Collateral.findByOpportunity(opportunity)?.propertySerialNumber
        //房产地址（省）
        if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("石家庄")))
        {
            collateral["buildingProvince"] = "130000"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("苏州")))
        {
            collateral["buildingProvince"] = "320000"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("济南")))
        {
            collateral["buildingProvince"] = "370000"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("郑州")))
        {
            collateral["buildingProvince"] = "410000"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("武汉")))
        {
            collateral["buildingProvince"] = "420000"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("西安")))
        {
            collateral["buildingProvince"] = "610000"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("合肥")))
        {
            collateral["buildingProvince"] = "340000"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("成都")))
        {
            collateral["buildingProvince"] = "510000"
        }
        else
        {
            collateral["buildingProvince"] = "510000"
        }
        //房产地址（市）
        if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("石家庄")))
        {
            collateral["buildingCity"] = "130100"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("苏州")))
        {
            collateral["buildingCity"] = "320500"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("济南")))
        {
            collateral["buildingCity"] = "370100"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("郑州")))
        {
            collateral["buildingCity"] = "410100"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("武汉")))
        {
            collateral["buildingCity"] = "420100"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("西安")))
        {
            collateral["buildingCity"] = "610100"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("合肥")))
        {
            collateral["buildingCity"] = "340100"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("成都")))
        {
            collateral["buildingCity"] = "510100"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("北京")))
        {
            collateral["buildingCity"] = "110000"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("上海")))
        {
            collateral["buildingCity"] = "310000"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("青岛")))
        {
            collateral["buildingCity"] = "370200"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("南京")))
        {
            collateral["buildingCity"] = "320100"
        }
        else if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("厦门")))
        {
            collateral["buildingCity"] = "350200"
        }
        else
        {
            collateral["buildingCity"] = "350200"
        }
        //房产地址（区）
        collateral["buildingArea"] = "441802"
        //房产详细地址
        collateral["buildingAddr"] = com.next.Collateral.findByOpportunity(opportunity)?.address
        //楼盘名称
        collateral["buildingName"] = com.next.Collateral.findByOpportunity(opportunity)?.projectName
        //建筑面积
        collateral["buildSquare"] = com.next.Collateral.findByOpportunity(opportunity)?.area
        //面积单价
        collateral["squarePrice"] = com.next.Collateral.findByOpportunity(opportunity)?.unitPrice
        //房龄
        def s1 = com.next.Collateral.findByOpportunity(opportunity)?.buildTime?.format("yyyy-MM-dd")
        def s2 = new Date().format("yyyy-MM-dd")
        def sdf = new SimpleDateFormat("yyyy-mm-dd")
        def t1 = sdf.parse(s1)
        def t2 = sdf.parse(s2)
        def t = t2 - t1
        def y = (int) (t / 365)
        collateral["buildingAge"] = "$y"
        //产权年限
        def term = com.next.Collateral.findByOpportunity(opportunity)?.landUsageTerm
        if (term != null)
        {
            collateral["propertyRight"] = "$term"
        }
        else
        {
            collateral["propertyRight"] = "70"
        }
        //房产使用状态
        if (com.next.Collateral.findByOpportunityAndHouseUsageStats(opportunity, "自住") != null)
        {
            collateral["useType"] = "01"
        }
        else if (com.next.Collateral.findByOpportunityAndHouseUsageStats(opportunity, "出租") != null)
        {
            collateral["useType"] = "02"
        }
        else if (com.next.Collateral.findByOpportunityAndHouseUsageStats(opportunity, "空置") != null)
        {
            collateral["useType"] = "03"
        }
        else
        {
            collateral["useType"] = "04"
        }

        //签名认证
        println(collateral["mortgagerType"])
        String source = "mortgagerType=${collateral["mortgagerType"]}&ownerName=${collateral["ownerName"]}&ownerIDType=20&ownerID=${OpportunityContact.findByOpportunityAndType(opportunity, OpportunityContactType.findByName("借款人"))?.contact?.idNumber}&buildingType=${collateral["buildingType"]}&houseContNo=${collateral["houseContNo"]}&buildingStates=01&buildingNo=${collateral["buildingNo"]}";
        println(source)
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd1IIXnKRuY7Ngj/P7Bud9JJvAI1xLrzyEPWFyVccDUCeiRQawxfKMrQrmRToDidTTC+/XHUdRktr19g07TtC2NKCpZSb8qkEnOSpEvZKjb53oli1RbwkWjBVfwW4KlU04haASnhnubvFhTYYAb1hSwaiNGXOqbB/o82Wx8cxrxwIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3UghecpG5js2CP8/sG530km8AjXEuvPIQ9YXJVxwNQJ6JFBrDF8oytCuZFOgOJ1NML79cdR1GS2vX2DTtO0LY0oKllJvyqQSc5KkS9kqNvneiWLVFvCRaMFV/BbgqVTTiFoBKeGe5u8WFNhgBvWFLBqI0Zc6psH+jzZbHxzGvHAgMBAAECgYAbJLwG4Yqp7X2hAsDcEDDppc0fezVZUtbei0viBPyIBlX6o1JmPnTbWrtAJPG4QBEZBrmFzmRAlDgSCvH1nDVqi10L4PDeZYB5c0a3HqrbaNciB9f+dhEHEBz8VXo8sTJLOpxqEgDP4uYtbSln6wvd3uZY9BrDU9d2vkDCUJ8AYQJBANSS3R0SWTH+bH8av6dpWwrawSufCwiAw8VdOU5PnOp9OMAKdpcSEPFgNkMmO6HFhoihdpreNk5z4NlyuY47wzcCQQC+Eq0Bfm3Ed1tcI1EWY5Ag7bLaU59peWAJm8V0OAWzAyO0yOctAoHlF5mT5/R9iYKL7P5og1dkirK6oh8JwAPxAkEAvhbazb1zr6YxhXP5AI7RECLQbN6bMi5bYqlbrnC5BGOYFPsGU0+fgQmlXGTbHG2TQakJc7HUZFFxN2JFFjDQ/wJAUG0wnYg0xERI+TTMc+/PJc/OtUlbE9NTCt3J8EJgTv4OFspH36jG8/xHdOlab+BGyBSRFgI1cYqq1AQTpBmG0QJAb9nyONoND1v3bTUl79A+uT0w4LzWODy5lfbyZK4VEdGrX4vTjUQnmP/5p0+1ZVZHk5wnPqmUq+CFINAZpjc0eA==";
        //签名
        String sign = com.zjx.deal.RSADeal.sign(source, privateKey);
        //验证
        def status = com.zjx.deal.RSADeal.verify(source, sign, publicKey);
        collateral["sign"] = sign

        def json = new JSON(collateral)
        println(json)
        //println(json["token"])
        def colappUrl = "http://219.143.184.27:10000/ecp/collateralApply"
        URL url = new URL(colappUrl)
        BufferedReader bufferedReader = null
        def result = ""
        try
        {
            def connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.getOutputStream().write(json.toString().getBytes("UTF-8"))
            println("123")
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = bufferedReader.readLine()) != null)
            {
                result += line
            }

            // def parse =new JSONParser()
            def json1 = JSON.parse(result.toString())
            //[returnCode:USPS0000, cusId:A060000009, tradeCode:04001064, mortgageNoZH:11223332343434]
            // println("returnCode:"+json1.get("returnCode").toString)
            println(json1.getClass())
            println(json1)
            println(json1["mortgageNoZH"])
        }
        catch (Exception e)
        {
            println(e.getStackTrace())
            render ""
        }

    }

    //个人贷款申请提交
    def perloanApplyService(Opportunity opportunity)
    {

        def personAppl = [:]
        def personinfo = [:]
        personAppl["token"] = obtainToken()
        personinfo["cusId"] = "A060000009"
        personinfo["tradeCode"] = "04001001"
        personAppl["cusId"] = "A060000009"
        personAppl["tradeCode"] = "04001001"
        //personAppl["transDate"]=new Date().format("yyyyMMddHHmmss")
        //1 产品编号
        personAppl["productNo"] = "ZJX001"
        //2 贷款人类型
        personAppl["loanMenMode"] = "1"
        //3 计划编号
        personAppl["financeCode"] = "1111111"
        //4 批次号
        personAppl["batchNo"] = "1"
        //5 申请方合同编号
        def conCode = com.next.OpportunityContract.find("from OpportunityContract where opportunity.id" + " = ${opportunity.id} and contract.type.name = '借款合同'")?.contract?.serialNumber
        personAppl["loanContractCode"] = "$conCode"
        //6 担保方式
        personAppl["mortgageType"] = "3"
        //7 进件省份(Z)
        if (com.next.Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("石家庄")))
        {
            personAppl["loanProvince"] = "130000"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("苏州")))
        {
            personAppl["loanProvince"] = "320000"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("济南")))
        {
            personAppl["loanProvince"] = "370000"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("郑州")))
        {
            personAppl["loanProvince"] = "410000"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("武汉")))
        {
            personAppl["loanProvince"] = "420000"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("西安")))
        {
            personAppl["loanProvince"] = "610000"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("合肥")))
        {
            personAppl["loanProvince"] = "340000"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("成都")))
        {
            personAppl["loanProvince"] = "510000"
        }
        else
        {
            personAppl["loanProvince"] = "111111"
        }
        // 8 进件城市
        if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("石家庄")))
        {
            personAppl["loanCity"] = "130100"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("苏州")))
        {
            personAppl["loanCity"] = "320500"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("济南")))
        {
            personAppl["loanCity"] = "370100"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("郑州")))
        {
            personAppl["loanCity"] = "410100"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("武汉")))
        {
            personAppl["loanCity"] = "420100"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("西安")))
        {
            personAppl["loanCity"] = "610100"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("合肥")))
        {
            personAppl["loanCity"] = "340100"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("成都")))
        {
            personAppl["loanCity"] = "510100"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("北京")))
        {
            personAppl["loanCity"] = "110000"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("上海")))
        {
            personAppl["loanCity"] = "310000"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("青岛")))
        {
            personAppl["loanCity"] = "370200"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("南京")))
        {
            personAppl["loanCity"] = "320100"
        }
        else if (Collateral.findByOpportunityAndCity(opportunity, com.next.City.findByName("厦门")))
        {
            personAppl["loanCity"] = "350200"
        }
        else
        {
            personAppl["loanCity"] = "222222"
        }
        //9 客户评级
        if (OpportunityContact.find("from OpportunityContact where opportunity.id=${opportunity.id} and contact.level.name='A'") != null)
        //if (OpportunityContact.findByOpportunity(opportunity)?.contact?.level?.name=="A")
        {
            personAppl["certLevel"] = "1"
        }
        else if (OpportunityContact.findByOpportunity(opportunity)?.contact?.level?.name == "B")
        {
            personAppl["certLevel"] = "2"
        }
        else if (OpportunityContact.findByOpportunity(opportunity)?.contact?.level?.name == "C")
        {
            personAppl["certLevel"] = "3"
        }
        else
        {
            personAppl["certLevel"] = "0"
        }
        //10 借款用途
        personAppl["loanPurpose"] = "1"
        //11 还款方式
        if (OpportunityProduct.findByOpportunity(opportunity)?.product?.principalPaymentMethod?.name == "等额本息")
        {
            personAppl["paymentType"] = "1"
        }
        else if (OpportunityProduct.findByOpportunity(opportunity)?.product?.principalPaymentMethod?.name == "等本等息")
        {
            personAppl["paymentType"] = "6"
        }
        else
        {
            personAppl["paymentType"] = "5"
        }
        //12 贷款人姓名
        personAppl["lenderName"] = OpportunityContact.findByOpportunity(opportunity)?.contact?.fullName
        //13 贷款人手机号码
        personAppl["lenderMobile"] = OpportunityContact.findByOpportunity(opportunity)?.contact?.cellphone
        //放款信息
        if (OpportunityBankAccount.findByOpportunity(opportunity)?.type?.name.contains("收款"))
        {
            //14 放款账号
            personAppl["receAccNo"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfAccount
            //15 放款账户类型
            personAppl["receAccType"] = "01"
            //16 放款账号开户名
            personAppl["receAccName"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.name
            //17 放款账号开户证件类型
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.certificateType?.name == "身份证")
            {
                personAppl["receCertType"] = "20"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.certificateType?.name == "护照")
            {
                personAppl["receCertType"] = "22"
            }
            else
            {
                personAppl["receCertType"] = "2X"
            }
            //18 放款账号开户证件号码
            personAppl["receCertNo"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfCertificate
            //新增 receBankNo	放款账号开户行行别
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "工商银行")
            {
                personAppl["receBankNo"] = "120"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "建设银行")
            {
                personAppl["receBankNo"] = "118"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "招商银行")
            {
                personAppl["receBankNo"] = "119"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "农业银行")
            {
                personAppl["receBankNo"] = "121"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "光大银行")
            {
                personAppl["receBankNo"] = "113"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "广发银行")
            {
                personAppl["receBankNo"] = "122"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "兴业银行")
            {
                personAppl["receBankNo"] = "111"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "上海浦东发展银行")
            {
                personAppl["receBankNo"] = "114"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "民生银行")
            {
                personAppl["receBankNo"] = "125"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "交通银行")
            {
                personAppl["receBankNo"] = "123"
            }
            else
            {
                personAppl["receBankNo"] = "666"
            }
            //19 放款账号开户行名
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "工商银行")
            {
                personAppl["receBankName"] = "工商银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "建设银行")
            {
                personAppl["receBankName"] = "建设银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "招商银行")
            {
                personAppl["receBankName"] = "招商银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "农业银行")
            {
                personAppl["receBankName"] = "农业银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "光大银行")
            {
                personAppl["receBankName"] = "光大银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "广发银行")
            {
                personAppl["receBankName"] = "广发银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "兴业银行")
            {
                personAppl["receBankName"] = "兴业银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "上海浦东发展银行")
            {
                personAppl["receBankName"] = "上海浦东发展银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "民生银行")
            {
                personAppl["receBankName"] = "民生银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "交通银行")
            {
                personAppl["receBankName"] = "交通银行"
            }
            else
            {
                personAppl["receBankName"] = "其他"
            }
            //20 放款账号开户行号
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "工商银行")
            {
                personAppl["receBankCard"] = "ICBC"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "建设银行")
            {
                personAppl["receBankCard"] = "CCB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "招商银行")
            {
                personAppl["receBankCard"] = "CMB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "农业银行")
            {
                personAppl["receBankCard"] = "ABC"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "光大银行")
            {
                personAppl["receBankCard"] = "CEB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "广发银行")
            {
                personAppl["receBankCard"] = "GDB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "兴业银行")
            {
                personAppl["receBankCard"] = "CIB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "上海浦东发展银行")
            {
                personAppl["receBankCard"] = "SPDB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "民生银行")
            {
                personAppl["receBankCard"] = "CMBC"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "交通银行")
            {
                personAppl["receBankCard"] = "COMM"
            }
            else
            {
                personAppl["receBankCard"] = "wwww"
            }
        }
        else
        {
            //放款账号
            personAppl["receAccNo"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfAccount
            //放款账户类型
            personAppl["receAccType"] = "01"
            //放款账号开户名
            personAppl["receAccName"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.name
            //放款账号开户证件类型
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.certificateType?.name == "身份证")
            {
                personAppl["receCertType"] = "20"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.certificateType?.name == "护照")
            {
                personAppl["receCertType"] = "22"
            }
            else
            {
                personAppl["receCertType"] = "2X"
            }
            //放款账号开户证件号码
            personAppl["receCertNo"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfCertificate
            //新增 receBankNo	放款账号开户行行别
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "工商银行")
            {
                personAppl["receBankNo"] = "120"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "建设银行")
            {
                personAppl["receBankNo"] = "118"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "招商银行")
            {
                personAppl["receBankNo"] = "119"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "农业银行")
            {
                personAppl["receBankNo"] = "121"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "光大银行")
            {
                personAppl["receBankNo"] = "113"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "广发银行")
            {
                personAppl["receBankNo"] = "122"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "兴业银行")
            {
                personAppl["receBankNo"] = "111"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "上海浦东发展银行")
            {
                personAppl["receBankNo"] = "114"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "民生银行")
            {
                personAppl["receBankNo"] = "125"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "交通银行")
            {
                personAppl["receBankNo"] = "123"
            }
            else
            {
                personAppl["receBankNo"] = "666"
            }
            //放款账号开户行名
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "工商银行")
            {
                personAppl["receBankName"] = "工商银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "建设银行")
            {
                personAppl["receBankName"] = "建设银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "招商银行")
            {
                personAppl["receBankName"] = "招商银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "农业银行")
            {
                personAppl["receBankName"] = "农业银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "光大银行")
            {
                personAppl["receBankName"] = "光大银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "广发银行")
            {
                personAppl["receBankName"] = "广发银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "兴业银行")
            {
                personAppl["receBankName"] = "兴业银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "上海浦东发展银行")
            {
                personAppl["receBankName"] = "上海浦东发展银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "民生银行")
            {
                personAppl["receBankName"] = "民生银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "交通银行")
            {
                personAppl["receBankName"] = "交通银行"
            }
            else
            {
                personAppl["receBankName"] = "其他"
            }
            //放款账号开户行号
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "工商银行")
            {
                personAppl["receBankCard"] = "ICBC"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "建设银行")
            {
                personAppl["receBankCard"] = "CCB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "招商银行")
            {
                personAppl["receBankCard"] = "CMB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "农业银行")
            {
                personAppl["receBankCard"] = "ABC"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "光大银行")
            {
                personAppl["receBankCard"] = "CEB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "广发银行")
            {
                personAppl["receBankCard"] = "GDB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "兴业银行")
            {
                personAppl["receBankCard"] = "CIB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "上海浦东发展银行")
            {
                personAppl["receBankCard"] = "SPDB"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "民生银行")
            {
                personAppl["receBankCard"] = "CMBC"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "交通银行")
            {
                personAppl["receBankCard"] = "COMM"
            }
            else
            {
                personAppl["receBankCard"] = "wwww"
            }
        }
        if (OpportunityBankAccount.findByOpportunity(opportunity)?.type?.name.contains("还款"))
        {
            //21 还款账号开户行名
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "工商银行")
            {
                personAppl["repaymentBankName"] = "工商银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "建设银行")
            {
                personAppl["repaymentBankName"] = "建设银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "招商银行")
            {
                personAppl["repaymentBankName"] = "招商银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "农业银行")
            {
                personAppl["repaymentBankName"] = "农业银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "光大银行")
            {
                personAppl["repaymentBankName"] = "光大银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "广发银行")
            {
                personAppl["repaymentBankName"] = "广发银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "兴业银行")
            {
                personAppl["repaymentBankName"] = "兴业银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "上海浦东发展银行")
            {
                personAppl["repaymentBankName"] = "上海浦东发展银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "民生银行")
            {
                personAppl["repaymentBankName"] = "民生银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "交通银行")
            {
                personAppl["repaymentBankName"] = "交通银行"
            }
            else
            {
                personAppl["repaymentBankName"] = "其他"
            }
            //22 还款银行卡账号
            personAppl["repaymentAccNo"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfAccount
            //23 还款银行卡账户名
            personAppl["repaymentAccName"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.name
            //24 还款预留手机号
            personAppl["repaymentMobNo"] = OpportunityContact.findByOpportunity(opportunity)?.contact?.cellphone
        }
        else
        {
            //还款账号开户行名
            if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "工商银行")
            {
                personAppl["repaymentBankName"] = "工商银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "建设银行")
            {
                personAppl["repaymentBankName"] = "建设银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "招商银行")
            {
                personAppl["repaymentBankName"] = "招商银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "农业银行")
            {
                personAppl["repaymentBankName"] = "农业银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "光大银行")
            {
                personAppl["repaymentBankName"] = "光大银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "广发银行")
            {
                personAppl["repaymentBankName"] = "广发银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "兴业银行")
            {
                personAppl["repaymentBankName"] = "兴业银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "上海浦东发展银行")
            {
                personAppl["repaymentBankName"] = "上海浦东发展银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "民生银行")
            {
                personAppl["repaymentBankName"] = "民生银行"
            }
            else if (OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.bank?.name == "交通银行")
            {
                personAppl["repaymentBankName"] = "交通银行"
            }
            else
            {
                personAppl["repaymentBankName"] = "其他"
            }
            //还款银行卡账号
            personAppl["repaymentAccNo"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfAccount
            //还款银行卡账户名
            personAppl["repaymentAccName"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.name
            //还款预留手机号
            personAppl["repaymentMobNo"] = OpportunityContact.findByOpportunity(opportunity)?.contact?.cellphone

        }
        //25 贷款金额
        personAppl["loanAmt"] = opportunity?.actualLoanAmount.toBigDecimal()
        //26 正常执行利率
        personAppl["contractRate"] = opportunity?.dealRate.toBigDecimal()
        //27 罚息执行利率
        personAppl["punitiveRate"] = OpportunityProduct.findByOpportunity(opportunity)?.rate
        //28 贷款期限单位
        personAppl["loanTermUnit"] = "M"
        //29 贷款期限
        personAppl["loanTerm"] = opportunity?.loanDuration
        //30 是否允许提前还款
        personAppl["isForward"] = "1"
        //31 客户贷款申请日期
        personAppl["custApplyDate"] = opportunity?.createdDate?.format("yyyyMMdd")

        //申请人信息applyPerInfoList
        def test3 = []
        def applyInfo = [:]
        OpportunityContact.findAllByOpportunity(opportunity)?.each {
            //32 贷款人证件类型
            if (OpportunityBankAccount.findAllByOpportunity(opportunity)?.bankAccount?.certificateType?.name.contains("身份证"))
            {
                applyInfo["appCertType"] = "1"
            }
            else
            {
                applyInfo["appCertType"] = "0"
            }

            //33 贷款人证件号码
            applyInfo["appCertNo"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfCertificate
            //34 户籍地址
            applyInfo["houseRegistAddress"] = Collateral.findByOpportunity(opportunity)?.address
            //35 单位电话
            applyInfo["companyPhone"] = it?.contact?.cellphone
            //36 工作年限
            applyInfo["workTime"] = "4"
            //37 工资发放形式
            applyInfo["salaryPaytype"] = "1"
            //38 房产证号码
            applyInfo["proCertNo"] = Collateral.findByOpportunity(opportunity)?.propertySerialNumber
            test3.add(applyInfo)
        }
        personAppl["applyPerInfoList"] = test3
        /*//32 贷款人证件类型
        if (OpportunityBankAccount.findAllByOpportunity(opportunity)?.bankAccount?.certificateType?.name.contains("身份证")){
            personAppl["appCertType"]="1"
        }else {
            personAppl["appCertType"]="0"
        }

        //33 贷款人证件号码
        personAppl["appCertNo"]=OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfCertificate
        //34 户籍地址
        personAppl["houseRegistAddress"]=Collateral.findByOpportunity(opportunity)?.address
        //35 单位电话
        personAppl["companyPhone"]=OpportunityContact.findByOpportunity(opportunity)?.contact?.cellphone
        //36 工作年限
        personAppl["workTime"]="4"
        //37 工资发放形式
        personAppl["salaryPaytype"]="1"
        //38 房产证号码
        personAppl["proCertNo"]=Collateral.findByOpportunity(opportunity)?.propertySerialNumber*/
        def test = []
        //多联系人的集合
        def relatpeople = [:]
        OpportunityContact.findAllByOpportunity(opportunity).each {
            if (it?.type?.name.contains("借款人"))
            {
                //39 联系人姓名
                relatpeople["attnName"] = it?.contact?.fullName
                //40 联系人手机号
                relatpeople["attnMoblie"] = it?.contact?.cellphone
                //41 联系人与主申请人关系
                relatpeople["attnRelation"] = "99"
            }
            else if (it?.type?.name.contains("配偶"))
            {
                //联系人姓名
                relatpeople["attnName"] = it?.contact?.fullName
                //联系人手机号
                relatpeople["attnMoblie"] = it?.contact?.cellphone
                //联系人与主申请人关系
                relatpeople["attnRelation"] = "06"
            }
            else if (it?.type?.name.contains("父母子女"))
            {
                //联系人姓名
                relatpeople["attnName"] = it?.contact?.fullName
                //联系人手机号
                relatpeople["attnMoblie"] = it?.contact?.cellphone
                //联系人与主申请人关系
                relatpeople["attnRelation"] = "01"
            }
            else
            {
                //联系人姓名
                relatpeople["attnName"] = it?.contact?.fullName
                //联系人手机号
                relatpeople["attnMoblie"] = it?.contact?.cellphone
                //联系人与主申请人关系
                relatpeople["attnRelation"] = "99"
            }
            test.add(relatpeople)
        }
        personAppl["attnList"] = test
        //贷款关联人relaPerlist
        def test1 = []
        def relaperlist = [:]
        OpportunityContact.findAllByOpportunity(opportunity).each {
            // 42 关联人类型
            relaperlist["relaPerType"] = "02"
            if (it?.type?.name?.contains("父母子女"))
            {
                //关联人与主申请人关系
                relaperlist["relaPerRelation"] = "01"
            }
            else if (it?.type?.name?.contains("配偶"))
            {
                relaperlist["relaPerRelation"] = "06"
            }
            else
            {
                relaperlist["relaPerRelation"] = "99"
            }
            // 43 关联人姓名
            relaperlist["relaPerName"] = it?.contact?.fullName
            //44 联系人手机号
            relaperlist["relaPerMoblie"] = it?.contact?.cellphone
            //45 关联人身份证号码
            relaperlist["relaPerCertNo"] = it?.contact?.idNumber
            //46 关联人婚姻状态
            if (it?.contact?.maritalStatus == '未婚')
            {
                relaperlist["relaPerMarriageStatus"] = "10"
            }
            else if (it?.contact?.maritalStatus == '已婚')
            {
                relaperlist["relaPerMarriageStatus"] = "20"
            }
            else if (it?.contact?.maritalStatus == '离异')
            {
                relaperlist["relaPerMarriageStatus"] = "40"
            }
            else if (it?.contact?.maritalStatus == '丧偶')
            {
                relaperlist["relaPerMarriageStatus"] = "50"
            }
            else
            {
                relaperlist["relaPerMarriageStatus"] = "90"
            }
            //47 关联人学历
            relaperlist["relaPerEducationType"] = "00"
            //48 关联人居住地址省
            if (it?.contact?.city?.name?.contains("石家庄"))
            {
                relaperlist["relaPerProvince"] = "130000"
            }
            else if (it?.contact?.city?.name?.contains("苏州"))
            {
                relaperlist["relaPerProvince"] = "320000"
            }
            else if (it?.contact?.city?.name?.contains("济南"))
            {
                relaperlist["relaPerProvince"] = "370000"
            }
            else if (it?.contact?.city?.name?.contains("郑州"))
            {
                relaperlist["relaPerProvince"] = "410000"
            }
            else if (it?.contact?.city?.name?.contains("武汉"))
            {
                relaperlist["relaPerProvince"] = "420000"
            }
            else if (it?.contact?.city?.name?.contains("西安"))
            {
                relaperlist["relaPerProvince"] = "610000"
            }
            else if (it?.contact?.city?.name?.contains("合肥"))
            {
                relaperlist["relaPerProvince"] = "340000"
            }
            else if (it?.contact?.city?.name?.contains("成都"))
            {
                relaperlist["relaPerProvince"] = "510000"
            }
            else
            {
                relaperlist["relaPerProvince"] = "510000"
            }
            //49 关联人居住地址市
            if (it?.contact.city?.name == "石家庄")
            {
                relaperlist["relaPerCity"] = "130100"
            }
            else if (it?.contact.city?.name == "苏州")
            {
                relaperlist["relaPerCity"] = "320500"
            }
            else if (it?.contact.city?.name == "济南")
            {
                relaperlist["relaPerCity"] = "370100"
            }
            else if (it?.contact.city?.name == "郑州")
            {
                relaperlist["relaPerCity"] = "410100"
            }
            else if (it?.contact.city?.name == "武汉")
            {
                relaperlist["relaPerCity"] = "420100"
            }
            else if (it?.contact.city?.name == "西安")
            {
                relaperlist["relaPerCity"] = "610100"
            }
            else if (it?.contact.city?.name == "合肥")
            {
                relaperlist["relaPerCity"] = "340100"
            }
            else if (it?.contact.city?.name == "成都")
            {
                relaperlist["relaPerCity"] = "510100"
            }
            else if (it?.contact.city?.name == "北京")
            {
                relaperlist["relaPerCity"] = "110000"
            }
            else if (it?.contact.city?.name == "上海")
            {
                relaperlist["relaPerCity"] = "310000"
            }
            else if (it?.contact.city?.name == "青岛")
            {
                relaperlist["relaPerCity"] = "370200"
            }
            else if (it?.contact.city?.name == "南京")
            {
                relaperlist["relaPerCity"] = "320100"
            }
            else if (it?.contact.city?.name == "厦门")
            {
                relaperlist["relaPerCity"] = "350200"
            }
            else
            {
                relaperlist["relaPerCity"] = "222222"
            }
            //50 关联人居住地址区
            relaperlist["relaPerArea"] = "441802"
            //51 关联人居住详细地址
            relaperlist["relaPerAddr"] = Collateral.findByOpportunity(it?.opportunity)?.address
            //52 关联人职业类型
            relaperlist["relaPerOccupation"] = "Z"
            //53 关联人工资发放形式
            relaperlist["relaPerSalaryPaytype"] = "1"
            //54 关联人单位名称
            relaperlist["relaPerCompanyName"] = "个体"
            //55 关联人职位
            relaperlist["relaPerCompany"] = "00"
            //56 关联人工作年限
            relaperlist["relaPerWorkTime"] = "7"
            //57 关联人单位电话
            relaperlist["relaPerCompanyTel"] = it?.contact?.cellphone
            //58 关联人单位地址省
            relaperlist["relaPerCompanyProvince"] = "510000"
            //59 关联人单位地址市
            relaperlist["relaPerCompanyCity"] = "340100"
            //60 关联人单位地址区
            relaperlist["appCompanyArea"] = "441802"
            //61 关联人单位详细地址
            relaperlist["relaPerCompanyAddr"] = "无"
            test1.add(relaperlist)
        }
        personAppl["relaPerlist"] = test1
        //62 押品估值mortgageList
        def test2 = []
        //def i=1 //用于押品编号编序
        def mortgaglist = [:]
        Collateral.findAllByOpportunity(opportunity).each {
            //63 押品编号
            mortgaglist["mortgageNo"] = OpportunityContract.findByOpportunity(opportunity)?.contract?.serialNumber
            //64 评估报告编号
            mortgaglist["assessNo"] = "11111"
            //65 评估单位类型
            mortgaglist["assessType"] = "02"
            //66 评估单位
            mortgaglist["assessName"] = "某某某公司"
            //67 评估币种
            mortgaglist["assessCur"] = "01"
            //68 评估价值
            mortgaglist["assessValue"] = it?.totalPrice.toBigDecimal()
            //69 抵质押率（%）
            mortgaglist["mortgageRate"] = it?.loanToValue.toBigDecimal()
            //70 抵押类型
            if (it?.mortgageType?.name.contains("一抵"))
            {
                mortgaglist["mortgageType"] = "00"
            }
            else if (it?.mortgageType?.name.contains("二抵"))
            {
                mortgaglist["mortgageType"] = "01"
            }
            else
            {
                mortgaglist["mortgageType"] = "01"
            }
            test2.add(mortgaglist)
        }
        personAppl["mortgageList"] = test2

        //签名认证
        String source = "productNo=ZJX001&lenderName=${personAppl["lenderName"]}&lenderMobile=${personAppl["lenderMobile"]}&receAccNo=${personAppl["receAccNo"]}&receAccType=${personAppl["receAccType"]}&receAccName=${personAppl["receAccName"]}&receCertNo=${personAppl["receCertNo"]}&receBankName=${personAppl["receBankName"]}&receBankCard=${personAppl["receBankCard"]}&repaymentBankName=${personAppl["repaymentBankName"]}&repaymentAccNo=${personAppl["repaymentAccNo"]}&repaymentAccName=${personAppl["repaymentAccName"]}&repaymentMobNo=${personAppl["repaymentMobNo"]}&loanAmt=${personAppl["loanAmt"]}&contractRate=${personAppl["contractRate"]}";
        println(source)
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd1IIXnKRuY7Ngj/P7Bud9JJvAI1xLrzyEPWFyVccDUCeiRQawxfKMrQrmRToDidTTC+/XHUdRktr19g07TtC2NKCpZSb8qkEnOSpEvZKjb53oli1RbwkWjBVfwW4KlU04haASnhnubvFhTYYAb1hSwaiNGXOqbB/o82Wx8cxrxwIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3UghecpG5js2CP8/sG530km8AjXEuvPIQ9YXJVxwNQJ6JFBrDF8oytCuZFOgOJ1NML79cdR1GS2vX2DTtO0LY0oKllJvyqQSc5KkS9kqNvneiWLVFvCRaMFV/BbgqVTTiFoBKeGe5u8WFNhgBvWFLBqI0Zc6psH+jzZbHxzGvHAgMBAAECgYAbJLwG4Yqp7X2hAsDcEDDppc0fezVZUtbei0viBPyIBlX6o1JmPnTbWrtAJPG4QBEZBrmFzmRAlDgSCvH1nDVqi10L4PDeZYB5c0a3HqrbaNciB9f+dhEHEBz8VXo8sTJLOpxqEgDP4uYtbSln6wvd3uZY9BrDU9d2vkDCUJ8AYQJBANSS3R0SWTH+bH8av6dpWwrawSufCwiAw8VdOU5PnOp9OMAKdpcSEPFgNkMmO6HFhoihdpreNk5z4NlyuY47wzcCQQC+Eq0Bfm3Ed1tcI1EWY5Ag7bLaU59peWAJm8V0OAWzAyO0yOctAoHlF5mT5/R9iYKL7P5og1dkirK6oh8JwAPxAkEAvhbazb1zr6YxhXP5AI7RECLQbN6bMi5bYqlbrnC5BGOYFPsGU0+fgQmlXGTbHG2TQakJc7HUZFFxN2JFFjDQ/wJAUG0wnYg0xERI+TTMc+/PJc/OtUlbE9NTCt3J8EJgTv4OFspH36jG8/xHdOlab+BGyBSRFgI1cYqq1AQTpBmG0QJAb9nyONoND1v3bTUl79A+uT0w4LzWODy5lfbyZK4VEdGrX4vTjUQnmP/5p0+1ZVZHk5wnPqmUq+CFINAZpjc0eA==";
        def publicKeyZH = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGnQOnRQN0uK39npAywWekO0rezGijgjVPstDtyOM/w+kA7T6RUUA9FsY1xcqZNUK/+OuNLk5t0B5iR2KvOIAVZDVs4Y/TskTtLBDHbFFYPFB2F3HlmIaO8k9OLcVUSimkisi1+WjH48CSnU0viomfTOVDHgx27gH2c0H9q6ZLRQIDAQAB"
        //解密
        //com.zjx.deal.RSADeal.decrypt(miwen, privateKey);
        //签名
        String sign = com.zjx.deal.RSADeal.sign(source, privateKey);
        //验证
        def status = com.zjx.deal.RSADeal.verify(source, sign, publicKey);
        println(status)
        println(sign)
        personinfo["sign"] = sign
        def json2 = new JSON(personAppl)
        //加密
        String miwen = com.zjx.deal.RSADeal.encrypt(json2.toString(), publicKeyZH);
        personinfo["data"] = miwen
        def json3 = new JSON(personinfo)
        println(json3)
        def colappUrl = "http://219.143.184.27:10000/ecp/perloanApply"
        URL url = new URL(colappUrl)
        BufferedReader bufferedReader = null
        def result = ""
        try
        {
            def connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.getOutputStream().write(json3.toString().getBytes("UTF-8"))
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = bufferedReader.readLine()) != null)
            {
                result += line
            }
            //JSON解析字符串
            def json = JSON.parse(result.toString())
            println(json.getClass())
            println(json)
            println(json["loanApplyNo"])
            println(json["applySeq"])
            if (json["loanApplyNo"] != null)
            {
                println("ke diao hui")
                def channelRecord = new com.next.ChannelRecord()
                channelRecord.startTime = new Date()
                channelRecord.interfaceCode = '04001001'
                channelRecord.loanApplyNo = json["loanApplyNo"]
                channelRecord.applySeq = json["applySeq"]
                channelRecord.createdBy = com.next.User.findByUsername("zz")
                channelRecord.opportunity = opportunity
                channelRecord.endTime = new Date()
                if (channelRecord.validate())
                {
                    channelRecord.save flush: true
                    println("insert succeed")
                }
                else
                {
                    println channelRecord.errors
                    println("cuowu")
                }
            }
            else
            {
                println("wu shuju fanghui")
            }
        }
        catch (Exception e)
        {
            println("catch exception")
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            println(e.getStackTrace())
            println("dayin chu lai de xiang xi xinxi")

        }
        return true

    }

    //3.29 申请审批结果推送（测试）
    def appresultpush(Opportunity opportunity)
    {
        def params = [:]
        params["appConclusion"] = "10"
        params["loanApplyNo"] = "DKSQ00000000029975"
        params["crtDt"] = "2017-08-03 11:45:36"
        println(params.getClass())
        def json1 = new JSON(params)
        println(json1)
        println(json1.getClass())
        println(json1.class)
        println(json1.toString())
        println(json1.toString().class)
        println("end")

        //URL url = new URL("http://106.3.133.209/opportunity/evaluate")
        def colappUrl = "http://localhost:8080/api/evaluate"
        URL url = new URL(colappUrl)
        BufferedReader bufferedReader = null
        def result = ""
        try
        {
            def connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.getOutputStream().write(json1.toString().getBytes("UTF-8"))
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            println("789")
            def line
            while ((line = bufferedReader.readLine()) != null)
            {
                result += line
            }
            //JSON解析字符串
            println("100")
            println(result)
            def json = JSON.parse(result.toString())

            println(json.getClass())
            println(json)
            println(json["loanApplyNo"])
        }
        catch (Exception e)
        {
            println(e.getStackTrace())
            println("you exception")
        }
        render " "
    }

    //3.58 已补件通知 04001068
    def fixmatter(Opportunity opportunity)
    {
        def fixmatter = [:]
        //报文头信息
        fixmatter["token"] = "token"
        fixmatter["cusId"] = "A060000009"
        fixmatter["tradeCode"] = "04001068"
        fixmatter["transDate"] = new Date().format("yyyyMMddHHmmss")
        //贷款申请编号
        fixmatter["loanApplyNo"] = ChannelRecord.findByOpportunity(opportunity).loanApplyNo
        def json = new JSON(fixmatter)
        println(json)
        def colappUrl = "http://219.143.184.27:10000/ecp/dribblewareNotity"
        URL url = new URL(colappUrl)
        BufferedReader bufferedReader = null
        def result = ""
        try
        {
            def connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.getOutputStream().write(json.toString().getBytes("UTF-8"))
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = bufferedReader.readLine()) != null)
            {
                result += line
            }

            def json1 = JSON.parse(result.toString())
            println(json1)
        }
        catch (Exception e)
        {
            render ""
        }
    }

    //3.59 修改已打回贷款申请	04001069 /repAppNotity
    def fixapply(Opportunity opportunity)
    {
        def fixapplyinfo = [:]
        //报文头信息
        //报文头信息
        fixapplyinfo["token"] = "token"
        fixapplyinfo["cusId"] = "A060000009"
        fixapplyinfo["tradeCode"] = "04001069"
        fixapplyinfo["transDate"] = new Date().format("yyyyMMddHHmmss")
        //贷款申请编号
        fixapplyinfo["loanApplyNo"] = ChannelRecord.findByOpportunity(opportunity).loanApplyNo
        //贷款人手机号码
        fixapplyinfo["lenderMobile"] = OpportunityContact.findByOpportunity(opportunity)?.contact?.cellphone
        //还款银行卡账号
        fixapplyinfo["repaymentAccNo"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfAccount
        //还款银行卡账户名
        fixapplyinfo["repaymentAccName"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.name
        //贷款金额
        fixapplyinfo["loanAmt"] = opportunity?.actualLoanAmount.toBigDecimal()
        //正常执行利率
        fixapplyinfo["contractRate"] = opportunity?.dealRate.toBigDecimal()
        //贷款期限单位
        fixapplyinfo["loanTermUnit"] = "M"
        //贷款期限
        fixapplyinfo["loanTerm"] = opportunity?.loanDuration
        //申请人信息 applyPerInfoList
        def test3 = []
        def applyInfo = [:]
        OpportunityContact.findAllByOpportunity(opportunity)?.each {
            //32 贷款人证件类型
            if (OpportunityBankAccount.findAllByOpportunity(opportunity)?.bankAccount?.certificateType?.name.contains("身份证"))
            {
                applyInfo["appCertType"] = "1"
            }
            else
            {
                applyInfo["appCertType"] = "0"
            }
            //33 贷款人证件号码
            applyInfo["appCertNo"] = OpportunityBankAccount.findByOpportunity(opportunity)?.bankAccount?.numberOfCertificate
            test3.add(applyInfo)
        }
        fixapplyinfo["applyPerInfoList"] = test3
        //贷款关联人 relaPerlist
        def test1 = []
        def relaperlist = [:]
        test1.add(relaperlist)
        fixapplyinfo["relaPerlist"] = test1
        //押品估值 mortgageList
        def test2 = []
        //def i=1 //用于押品编号编序
        def mortgaglist = [:]
        Collateral.findAllByOpportunity(opportunity).each {
            //63 押品编号
            mortgaglist["mortgageNo"] = OpportunityContract.findByOpportunity(opportunity)?.contract?.serialNumber
            //64 评估报告编号
            mortgaglist["assessNo"] = "11111"
            //65 评估单位类型
            mortgaglist["assessType"] = "02"
            //66 评估单位
            mortgaglist["assessName"] = "某某某公司"
            //67 评估币种
            mortgaglist["assessCur"] = "01"
            //68 评估价值
            mortgaglist["assessValue"] = it?.totalPrice.toBigDecimal()
            //69 抵质押率（%）
            mortgaglist["mortgageRate"] = it?.loanToValue.toBigDecimal()
            test2.add(mortgaglist)
        }
        fixapplyinfo["mortgageList"] = test2

        def json = new JSON(fixapplyinfo)
        println(json)
        def colappUrl = "http://219.143.184.27:10000/ecp/repAppNotity"
        URL url = new URL(colappUrl)
        BufferedReader bufferedReader = null
        def result = ""
        try
        {
            def connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.getOutputStream().write(json.toString().getBytes("UTF-8"))
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = bufferedReader.readLine()) != null)
            {
                result += line
            }

            def json1 = JSON.parse(result.toString())
            println(json1)
        }
        catch (Exception e)
        {
            render ""
        }
    }

    //3.28 获取token的 值
    def obtainToken()
    {
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3UghecpG5js2CP8/sG530km8AjXEuvPIQ9YXJVxwNQJ6JFBrDF8oytCuZFOgOJ1NML79cdR1GS2vX2DTtO0LY0oKllJvyqQSc5KkS9kqNvneiWLVFvCRaMFV/BbgqVTTiFoBKeGe5u8WFNhgBvWFLBqI0Zc6psH+jzZbHxzGvHAgMBAAECgYAbJLwG4Yqp7X2hAsDcEDDppc0fezVZUtbei0viBPyIBlX6o1JmPnTbWrtAJPG4QBEZBrmFzmRAlDgSCvH1nDVqi10L4PDeZYB5c0a3HqrbaNciB9f+dhEHEBz8VXo8sTJLOpxqEgDP4uYtbSln6wvd3uZY9BrDU9d2vkDCUJ8AYQJBANSS3R0SWTH+bH8av6dpWwrawSufCwiAw8VdOU5PnOp9OMAKdpcSEPFgNkMmO6HFhoihdpreNk5z4NlyuY47wzcCQQC+Eq0Bfm3Ed1tcI1EWY5Ag7bLaU59peWAJm8V0OAWzAyO0yOctAoHlF5mT5/R9iYKL7P5og1dkirK6oh8JwAPxAkEAvhbazb1zr6YxhXP5AI7RECLQbN6bMi5bYqlbrnC5BGOYFPsGU0+fgQmlXGTbHG2TQakJc7HUZFFxN2JFFjDQ/wJAUG0wnYg0xERI+TTMc+/PJc/OtUlbE9NTCt3J8EJgTv4OFspH36jG8/xHdOlab+BGyBSRFgI1cYqq1AQTpBmG0QJAb9nyONoND1v3bTUl79A+uT0w4LzWODy5lfbyZK4VEdGrX4vTjUQnmP/5p0+1ZVZHk5wnPqmUq+CFINAZpjc0eA==";
        def date = new Date().format("yyyyMMddHHmmss")

        String ss = "cusId=A060000009&tradeCode=04001028&transDate=${date}"
        String signLoanAmt = com.zjx.deal.RSADeal.sign(ss, privateKey)
        def map = [:]
        map["cusId"] = "A060000009"
        map["tradeCode"] = "04001028"
        map["transDate"] = date
        map["sign"] = signLoanAmt
        //String urlString1 = " http://219.143.184.27:10000/ecp/getToken"
        def mapdata = new JSON(map)
        /*def sendPost =  {String urlString, String params1 ->
            URL url = new java.net.URL(urlString)
            def result
            try {
                def connection = (java.net.HttpURLConnection) url.openConnection()
                connection.setDoOutput(true)
                connection.setRequestMethod("POST")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write params1 }
                result = grails.converters.JSON.parse(connection.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
                println("返回结果"+result)
            }
            catch (java.lang.Exception e) {
                e.printStackTrace()
                println e
            }
            return result
        }*/

        def colappUrl = "http://219.143.184.27:10000/ecp/getToken"
        URL url = new URL(colappUrl)
        BufferedReader bufferedReader = null
        def result = ""
        try
        {
            def connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.getOutputStream().write(mapdata.toString().getBytes("UTF-8"))
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = bufferedReader.readLine()) != null)
            {
                result += line
            }

            def json1 = JSON.parse(result.toString())
            //[returnCode:USPS0000, cusId:A060000009, tradeCode:04001064, mortgageNoZH:11223332343434]
            // println("returnCode:"+json1.get("returnCode").toString)
            println(json1.getClass())
            println(json1)
            println(json1["token"])
            return json1["token"]
        }
        catch (Exception e)
        {
            println(e.getStackTrace())
            render ""
        }
        // def aa = sendPost(urlString1,mapdata)
        // println "aaaaaaaaaa"+aa["token"]
        //return aa["token"]
    }

    def aaa()
    {
        Opportunity opportunity = com.next.Opportunity.find("from Opportunity o where o.id = '122371'")
        println(opportunity)
        println(OpportunityContact.findByOpportunity(opportunity))
        def serialNumber = com.next.OpportunityContract.find("from OpportunityContract where opportunity.id = '122371' and contract.type.name = '借款合同'")?.contract?.serialNumber
        println(serialNumber)
        println("wu serialnumber")
    }

}

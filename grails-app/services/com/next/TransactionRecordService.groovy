package com.next

import grails.converters.XML
import grails.transaction.Transactional
import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

@Transactional
class TransactionRecordService
{
    static scope = 'singleton'
    static encoding = 'UTF-8'
    static version = 1.0
    static bussenessType = 101001
    //商户业务类型
    static urlPay = ''
    static urlQuery = ''
    static serverUrl = ''
    static MD5Key = '201408071000001543test_20140812'
    static NoOfseller = '20140807100001543'

    def generator = { String alphabet, int n ->
        new Random().with {
            (1..n).collect { alphabet[nextInt(alphabet.length())] }.join()
        }
    }

    def sendRequest(url, params)
    {
        println '0->sendRequest: '
        URL _url = new URL(url)

        HttpURLConnection urlConnection = (HttpURLConnection) _url.openConnection()
        println '2:'
        urlConnection.setRequestMethod('POST')
        println '3:'
        urlConnection.setDoOutput(true)
        println '4:'
        urlConnection.setReadTimeout(6000)
        println '5:'
        String requestParams = '';
        params.each {
            if (!requestParams)
            {
                requestParams += it.key + '=' + it.value
            }
            else
            {
                requestParams += '&' + it.key + '=' + it.value
            }
        }
        println '6:'
        urlConnection.outputStream.withWriter { Writer writer -> writer.write requestParams }
        println '7:'
        def responeResult = urlConnection.inputStream.withReader { Reader reader -> reader.text }
        println 'over->sendRequest: '
        return responeResult
    }

    //代收接口 - 这块还可以再精简一下
    //def payForCollection(nameOfAccount, bankCode, bankCardNo, certificateNo, amount, notifyCellphone, orderCode)
    def payForCollection(contactBank, transactionRecord)
    {

        def version = TransactionRecordConfig.version
        def merdt = new Date().format('yyyyMMdd')
        def name = contactBank.nameOfAccount
        def bank = contactBank.bank
        def bankCode = bank.code
        def cardNo = contactBank.numberOfAccount
        println " 传入的amount：" + transactionRecord.transactionAmount
        def amt = transactionRecord.transactionAmount * 100 as Integer
        // 以分为单位
        println " 传给富友的amount：" + amt
        def entseq = ''
        def memo = ''
        def cellphone = contactBank.cellphone
        def certtp = '0'
        def certno = contactBank.numberOfCertificate
        def orderCode = transactionRecord.orderSerialNumber

        //        def merchantNumber = TransactionRecordConfig.testmerchantId // 测试商户号
        def merchantNumber = TransactionRecordConfig.merchantId
        //生产上的商户号

        //        def merchantPassword = TransactionRecordConfig.testPrivateKey//'123456' //(测试)商户密码
        def merchantPassword = TransactionRecordConfig.collectionPrivateKey
        //(生产)商户密码

        def requestType = TransactionRecordConfig.payType
        //'sincomeforreq'  //请求类型
        //        def url =  TransactionRecordConfig.requestPayForServletofTesturl    //'http://www-1.fuiou
        // .com:8992/fuMer/req.do'
        def url = TransactionRecordConfig.requestPayForServleturl
        try
        {

            def xmlString = '<?xml version="1.0" encoding="utf-8" standalone="yes"?><incomeforreq><ver>' + version + '</ver><merdt>' + merdt + '</merdt><orderno>' + orderCode + '</orderno><bankno>' + bankCode + '</bankno><accntno>' + cardNo + '</accntno><accntnm>' + name + '</accntnm><amt>' + amt + '</amt><entseq>' + entseq + '</entseq><memo>' + memo + '</memo><mobile>' + cellphone + '</mobile><certtp>' + certtp + '</certtp><certno>' + certno + '</certno></incomeforreq>'

            def mac = merchantNumber + '|' + merchantPassword + '|' + requestType + '|' + xmlString
            //def mac =URLEncoder.encode((merchantNumber  + '|' + merchantPassword + '|' + requestType + '|' +
            // xmlString),"UTF-8");
            def requestParams = ['merid': merchantNumber, 'reqtype': requestType, 'xml': xmlString, 'mac': mac
                .encodeAsMD5()]
            println "requestParams:" + requestParams
            def resultStr = sendRequest(url, requestParams)
            //

            def xmlObject = new XmlParser().parseText(resultStr)
            //            def xmlObject= new XML().parse(resultStr)
            def responseCode = xmlObject.ret.text()
            def responseDescription = xmlObject.memo.text()

            println "responseCode:${responseCode},responseDescription:${responseDescription}"
            //def returnMsg = "responseCode: " + responseCode + "#" + "responseDescription: " + responseDescription
            return [orderSerialNumber: orderCode, responseCode: responseCode, responseDescription: responseDescription]
            //def rec = com.myfund.TransactionRecord.findByOrderSerialNumber(orderCode)

            /*if(responseCode == '000000')
            {
                //rec.payStatus = '成功'
                //rec.save()
                render ([status:200,message:responseDescription] as JSON)
            }

            else
            {
                //rec.payStatus = '失败'
                //rec.save()
                render ([status:400,message:responseDescription] as JSON)
            }
            */
        }
        catch (Exception e)
        {
            println "payForCollection:" + e.message
            //return [state:400,message:"程序内部错误！"] as JSON
            def returnMsg = "payForCollection:" + e.message
            return [orderSerialNumber: orderCode, responseCode: 400, responseDescription: returnMsg]
        }
    }

    // 查询支付记录 根据支付记录、起始日期和结束日期查询订单， 需要完善e.g: orderSerialNumber 为空等 - 这块回头得改一下
    def queryOrderByOrderSerialNumber(orderSerialNumber, startDate, endDate, status)
    {

        def version = TransactionRecordConfig.version
        //        def merchantNumber = TransactionRecordConfig.testmerchantId //测试商户号
        def merchantNumber = TransactionRecordConfig.merchantId
        //商户号
        //        def merchantPassword = TransactionRecordConfig.testPrivateKey //'123456' //商户密码
        def merchantPassword = TransactionRecordConfig.collectionPrivateKey
        //商户密码

        def requestType = TransactionRecordConfig.queryPayType
        //'qrytransreq'          //请求类型

        def transst = status
        //        def url = TransactionRecordConfig.requestPayForServletofTesturl    //'http://www-1.fuiou.com:8992/fuMer/req
        // .do'
        def url = TransactionRecordConfig.requestPayForServleturl
        //'http://www-1.fuiou.com:8992/fuMer/req.do'
        println '22---- merchantNumber: ' + merchantNumber
        println '23---- merchantPassword: ' + merchantPassword
        println '24---- requestType: ' + requestType
        println '25---- url: ' + url

        try
        {

            def xmlString = '<?xml version="1.0" encoding="utf-8" standalone="yes"?><qrytransreq><ver>' + '1.0' + '</ver><busicd>' + 'AC01' + '</busicd><orderno>' + '' + '</orderno><startdt>' + startDate
            +'</startdt><enddt>' + endDate + '</enddt><transst>' + transst + '</transst></qrytransreq>'
            println "xmlString:" + xmlString
            def mac = merchantNumber + '|' + merchantPassword + '|' + requestType + '|' + xmlString
            //def mac =URLEncoder.encode((merchantNumber  + '|' + merchantPassword + '|' + requestType + '|' +
            // xmlString),"UTF-8");
            def requestParams = ['merid': merchantNumber, 'reqtype': requestType, 'xml': URLEncoder.encode(xmlString
                                                                                                               .toString(), 'UTF-8'), 'mac': mac.encodeAsMD5()]
            println "requestParams:" + requestParams
            //
            def resultStr = sendRequest(url, requestParams)

            resultStr = URLDecoder.decode(resultStr, "UTF-8")

            //            def xmlObject = new XmlParser().parseText(resultStr)
            def xmlObject = new XmlSlurper().parseText(resultStr)
            def responseCode = xmlObject.ret.text()
            def responseDescription = xmlObject.memo.text()

            def trans = []
            xmlObject.trans?.each {
                trans.add([merchantDate: it.merdt.text(),
                    orderNo: it.orderno.text(),
                    accountNo: it.accntno.text(),
                    accountName: it.accntnm.text(),
                    amount: it.amt.text(),
                    state: it.state.text()])
            }



            println "----responseCode:${responseCode},responseDescription:${responseDescription},trans:" + trans
            //def returnMsg = "responseCode: " + responseCode + "#" + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription, trans: trans]
            /*if(responseCode == '000000')
            {
                //rec.payStatus = '成功'
                render ([status:200,message:responseDescription] as JSON)
            }

            else
            {

                render ([status:400,message:responseDescription] as JSON)
            }
            */

        }
        catch (Exception e)
        {
            //
            //def rec = com.myfund.TransactionRecord.findByOrderSerialNumber(orderCode)
            //rec.payStatus = '失败'
            //rec.save()
            println "queryOrderByOrderSerialNumber:" + e.message
            //return [state:400,message:"程序内部错误！"] as JSON
            def returnMsg = "queryOrderByOrderSerialNumber: " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }

    }

    // 5 快捷支付的五要素签约
    def sendFiveElementsQuickPay(name, bankCode, bankCardNo, certificateType, certificateNo, cellPhone)
    {

        def version = TransactionRecordConfig.version
        def srcChnl = 'DSF'
        // 代收付
        def busiCode = 'AC01'
        // 业务代码，默认为AC01
        //def bankCode = jsonObject.bankCode // '0105'
        def userName = name
        //jsonObject.name//'李翔'
        //def cellPhone = jsonObject.cellPhone//'18652909140'
        //def certificateType = jsonObject.certificateType//'0'
        //def certificateNo = jsonObject.certificateNo//'220602198805283312'
        def acntTp = '01'
        // 借记卡
        //def bankCardNo = jsonObject.bankCardNo//'6217001370006390003'

        //生产上的商户号
        def merchantCode = TransactionRecordConfig.merchantId
        // '0001000F0282080'//'0002900F0345178'//'290062110000031'
        //测试时的商户号
        //        def merchantCode = TransactionRecordConfig.testmerchantId //'0002900F0345178'//'290062110000031'
        println "merchantCode:" + merchantCode
        def isCallback = '0'
        //
        //        def reserved1 = '0' // 保留字段
        def signature = ''
        // 签名字段

        //userNm = URLEncoder.encode(userNm, "utf-8")
        def list = []
        list.add(srcChnl)
        list.add(busiCode)
        list.add(bankCode)
        list.add(userName)
        list.add(cellPhone)
        list.add(certificateType)
        list.add(certificateNo)
        list.add(acntTp)
        list.add(bankCardNo)
        list.add(merchantCode)
        list.add(isCallback)
        //        list.add(reserved1)

        signature = dataSortAndSign(list) // 数字签名
        println "signature:" + signature
        // 拼接xml
        try
        {

            String reqestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><custmrBusi><srcChnl>" + srcChnl + "</srcChnl><busiCd>" + busiCode + "</busiCd><bankCd>" + bankCode + "</bankCd><userNm>"
            +userName + "</userNm><mobileNo>" + cellPhone + "</mobileNo><credtTp>" + certificateType + "</credtTp><credtNo>" + certificateNo + "</credtNo><acntTp>" + acntTp + "</acntTp><acntNo>" + bankCardNo
            +"</acntNo><mchntCd>" + merchantCode + "</mchntCd><isCallback>" + isCallback + "</isCallback><reserved1>" + '' + "</reserved1><signature>" + signature + "</signature></custmrBusi>"
            //                        println "send xml encode: " + URLEncoder.encode(reqestXml.toString(),"UTF-8")
            println "reqestXml:" + reqestXml
            //def responseXml = transactionService.sendRequest(url, [req: URLEncoder.encode(reqestXml.toString(),
            // "GBK")])
            //def responseXml = transactionService.sendRequest(url, [req: URLEncoder.encode(URLEncoder.encode
            // (reqestXml.toString(),"utf-8"), "utf-8")])
            //println "send xml encode: " + URLEncoder.encode(reqestXml.toString(),"UTF-8")

            ////生产环境快捷支付五要素签约的地址
            def responseXml = sendRequest(TransactionRecordConfig.requestSignFiveElementsQuickServleturl, [xml: URLEncoder.encode(reqestXml.toString(), "UTF-8")])

            //测试环境快捷支付五要素签约的地址
            //            def responseXml = sendRequest(TransactionRecordConfig.requestSignFiveElementsQuickServletofTesturl,
            // [xml: URLEncoder.encode(reqestXml.toString(),"UTF-8")])
            println responseXml

            def resultStr = URLDecoder.decode(responseXml, "UTF-8")
            //            println resultStr
            def xmlObject = XML.parse(resultStr)
            def responseCode = xmlObject.respCd
            def responseDescription = xmlObject.respDesc

            /*
            if(responseCode == '0000')
            {
                render ([state: 200, message:"成功签约！"] as JSON)
            }
            else
            {
                render ([state: 400, message:"代收付的手机验证码验证:-->${responseDescription}"] as JSON)
            }
            */
            def returnMsg = "responseCode: " + responseCode + "#" + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription]
        }
        catch (Exception e)
        {
            println "sendFiveElementsQuickPay()" + e.message
            //return [state: 400,message: "程序内部错误!"] as JSON
            def returnMsg = "sendFiveElementsQuickPay(): " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }

    }

    // 代收付的手机验证码验证
    def sendVerifyCodeInQuickPay(verifyCode, bankCardNo)
    {

        def version = TransactionRecordConfig.version

        //        def merchantId = TransactionRecordConfig.testmerchantId     //测试商户号'0002900F0345178'
        def merchantId = TransactionRecordConfig.merchantId
        //生产商户号

        def signature = ''

        def list = []
        list.add(bankCardNo)
        list.add(merchantId)
        list.add(verifyCode)

        signature = dataSortAndSign(list) // 数字签名
        println "signature:" + signature
        try
        {
            String reqestXml = '<?xml version="1.0" encoding="UTF-8"?><custmrBusi><acntNo>' + bankCardNo + '</acntNo><mchntCd>' + merchantId + '</mchntCd><verifyCode>' + verifyCode + '</verifyCode><signature>' + signature + '</signature></custmrBusi>'
            println "requst xml: " + reqestXml
            def url = TransactionRecordConfig.requstVerifyCodeInQuickPayServleturl
            //生产环境地址
            //            def url = TransactionRecordConfig.requstVerifyCodeInQuickPayServletofTesturl //测试环境地址

            def responseXml = sendRequest(TransactionRecordConfig.requstVerifyCodeInQuickPayServleturl, [xml: URLEncoder.encode(reqestXml.toString(), "UTF-8")])

            def resultStr = URLDecoder.decode(responseXml, "UTF-8")
            println "resultStr: " + resultStr

            def xmlObject = XML.parse(resultStr)
            def responseCode = xmlObject.respCd
            def responseDescription = xmlObject.respDesc
            /*if(responseCode == '0000') {
                render ([state: 200, message:"成功签约！"] as JSON)
            }
            else
            {
                render ([state: 400, message:"代收付的手机验证码验证:-->${responseDescription}"] as JSON)
            }
            */
            def returnMsg = "responseCode: " + responseCode + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription]
        }
        catch (Exception e)
        {
            println "sendVerifyCodeInQuickPay()" + e.message
            //return [state: 400,message: "程序内部错误!"] as JSON
            def returnMsg = "sendVerifyCodeInQuickPay(): " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }

    }

    // 代收付的四要素签约: cellphone 是不是必填项
    def sendFourElementsForSignInQuickpay(name, bankCode, bankCardNo, certificateType, certificateNo, cellPhone)
    {
        def version = TransactionRecordConfig.version
        def srcChnl = 'DSF'
        // 代收付
        def busiCode = 'AC01'
        // 业务代码，默认为AC01
        //def bankCode = jsonObject.bankCode // '0105'
        def userName = name
        //jsonObject.name//'李翔'
        //def cellPhone = jsonObject.cellPhone//'18652909140'
        //def certificateType = jsonObject.certificateType//'0'
        //def certificateNo = jsonObject.certificateNo//'220602198805283312'
        def acntTp = '01'
        // 借记卡
        //def bankCardNo = jsonObject.bankCardNo//'6217001370006390003'

        //生产上的商户号
        def merchantCode = TransactionRecordConfig.merchantId
        // '0001000F0282080'//'0002900F0345178'//'290062110000031'
        //测试时的商户号
        //        def merchantCode = TransactionRecordConfig.testmerchantId //'0002900F0345178'//'290062110000031'
        println "merchantCode:" + merchantCode
        def isCallback = '0'
        //
        //        def reserved1 = '0' // 保留字段
        def signature = ''
        // 签名字段

        //userNm = URLEncoder.encode(userNm, "utf-8")
        def list = []
        list.add(srcChnl)
        list.add(busiCode)
        list.add(bankCode)
        list.add(userName)
        list.add(cellPhone)
        list.add(certificateType)
        list.add(certificateNo)
        list.add(acntTp)
        list.add(bankCardNo)
        list.add(merchantCode)
        list.add(isCallback)
        //        list.add(reserved1)

        signature = dataSortAndSign(list) // 数字签名
        println "signature:" + signature
        // 拼接xml
        try
        {

            String reqestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><custmrBusi><srcChnl>" + srcChnl + "</srcChnl><busiCd>" + busiCode + "</busiCd><bankCd>" + bankCode + "</bankCd><userNm>"
            +userName + "</userNm><mobileNo>" + cellPhone + "</mobileNo><credtTp>" + certificateType + "</credtTp><credtNo>" + certificateNo + "</credtNo><acntTp>" + acntTp + "</acntTp><acntNo>" + bankCardNo
            +"</acntNo><mchntCd>" + merchantCode + "</mchntCd><isCallback>" + isCallback + "</isCallback><reserved1>" + '' + "</reserved1><signature>" + signature + "</signature></custmrBusi>"
            //                        println "send xml encode: " + URLEncoder.encode(reqestXml.toString(),"UTF-8")
            println "reqestXml:" + reqestXml
            //def responseXml = transactionService.sendRequest(url, [req: URLEncoder.encode(reqestXml.toString(),
            // "GBK")])
            //def responseXml = transactionService.sendRequest(url, [req: URLEncoder.encode(URLEncoder.encode
            // (reqestXml.toString(),"utf-8"), "utf-8")])
            //println "send xml encode: " + URLEncoder.encode(reqestXml.toString(),"UTF-8")

            ////生产环境快捷支付五要素签约的地址
            def responseXml = sendRequest(TransactionRecordConfig.requestSignFourElementsQuickpayTestUrl, [xml: URLEncoder.encode(reqestXml.toString(), "UTF-8")])
            //def responseXml = sendRequest(TransactionRecordConfig.requestSignFourElementsQuickpayUrl, [xml:
            // URLEncoder.encode(reqestXml.toString(),"UTF-8")])

            println responseXml

            def resultStr = URLDecoder.decode(responseXml, "UTF-8")

            def xmlObject = XML.parse(resultStr)
            def responseCode = xmlObject.respCd
            def responseDescription = xmlObject.respDesc

            /*
            if(responseCode == '0000')
            {
                render ([state: 200, message:"成功签约！"] as JSON)
            }
            else
            {
                render ([state: 400, message:"代收付的手机验证码验证:-->${responseDescription}"] as JSON)
            }
            */
            def returnMsg = "responseCode: " + responseCode + "#" + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription]
        }
        catch (Exception e)
        {
            println "sendFourElementsForSignInQuickpay()" + e.message
            //return [state: 400,message: "程序内部错误!"] as JSON
            def returnMsg = "sendFourElementsForSignInQuickpay(): " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }
    }

    // 代收付中验卡和开户数字签名数据排序
    def dataSortAndSign(list)
    {
        def signValue = ''
        String[] strs = new String[list.size()];
        for (
            int i = 0;
                i < strs.length;
                i++)
        {
            strs[i] = list.get(i);
        }
        println "strs: " + strs
        Arrays.sort(strs);
        for (
            int i = 0;
                i < strs.length;
                i++)
        {
            signValue = signValue + strs[i] + '|'
        }
        signValue = signValue.substring(0, signValue.length() - 1)
        def sign = signValue.encodeAsSHA1()

        sign = sign + '|' + TransactionRecordConfig.collectionPrivateKey //生产环境的秘钥
        //        sign =  sign + '|' + TransactionRecordConfig.testPrivateKey  // TransactionRecordConfig.privateKey 测试环境秘钥
        def signature = sign.encodeAsSHA1()
        return signature
    }

    // 单笔交易结果查询
    def queryTradeResultBySerialNumber(orderSerialNumber, transactionDate)
    {
        def version = TransactionRecordConfig.version
        def txType = TransactionRecordConfig.tradeQueryPayType; // 查询支付类型
        def fsino = TransactionRecordConfig.merchantId; //基金销售机构代码
        //        def lastTxTraceNo = "20151229152348000000001"//"12345678901234567890123"; // 支付时对应的订单的交易流水
        def lastTxTraceNo = orderSerialNumber
        //"12345678901234567890123"; // 支付时对应的订单的交易流水
        //        def lastTxDate = "20151229" // 支付时对应的订单的交易日期
        def lastTxDate = transactionDate
        // 支付时对应的订单的交易日期
        def lastTxType = TransactionRecordConfig.transactionType
        // 订单的交易类型

        def txTime = new Date().format('yyyyMMddHHmmss')
        //new Date().format('yyyyMMdd') //
        //        def txTraceNo ="20151229152348000000001" ;//查询的时候生成的交易流水号
        def alphabet = '0'..'9'
        def txTraceNo = new Date().format('yyyyMMddHHmmss') + generator(alphabet.join(), 9)

        def signPacket = fsino + txTime + txTraceNo + lastTxTraceNo + lastTxDate + lastTxType
        println "signPacket---->" + signPacket

        String sign = "";

        // 数字签名
        sign = addSign(signPacket)
        println "sign: " + sign

        try
        {
            String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?><FPS><Request><Version>" + version + "</Version><TxType>" + txType + "</TxType><TxInfo><FSINo>" + fsino + "</FSINo><TxTime>" + txTime + "</TxTime><TxTraceNo>" + txTraceNo + "</TxTraceNo><LastTxTraceNo>" + lastTxTraceNo + "</LastTxTraceNo><LastTxDate>" + lastTxDate + "</LastTxDate><LastTxType>" + lastTxType + "</LastTxType></TxInfo><Sign>"
            +sign + "</Sign></Request></FPS>"
            println "请求报文====: " + xmlString

            def url = TransactionRecordConfig.fundQueryForServleturl

            def oo = URLEncoder.encode(xmlString.toString(), "GBK")
            println "oo--: " + oo
            println 'url: ' + url
            println 'req:' + URLEncoder.encode(URLEncoder.encode(xmlString.toString(), "GBK"), "GBK")
            //def responseXml = transactionService.sendRequest(url, [req: URLEncoder.encode(xmlString.toString(),
            // "GBK")])
            def responseXml = sendRequest(url, [req: URLEncoder.encode(URLEncoder.encode(xmlString.toString(), "GBK")
                                                                       , "GBK")])

            println "response: " + responseXml
            def resultStr = URLDecoder.decode(responseXml, "GBK")
            println "response: decode: " + resultStr
            def xmlObj = XML.parse(resultStr)
            println 'xmlObj: ' + xmlObj + '****'

            def rootNode = new XmlParser().parseText(resultStr)
            println 'rootNode: ' + rootNode + '****'

            def ret = verifySign(rootNode, 'Query')
            // 数字签名验证

            //            return [status:200, ret:ret]
            def responseCode = rootNode.Response.ResponseInfo.ResponseCode.text()
            def responseDescription = rootNode.Response.ResponseInfo.ResponseCode.text()
            println "responseCode: " + responseCode + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription]
        }
        catch (Exception e)
        {
            println "queryTradeResultBySerialNumber()" + e.message
            //return [state: 400,message: "程序内部错误!"] as JSON
            def returnMsg = "queryTradeResultBySerialNumber(): " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }

    }

    // 认申购20/22
    //    def subscribeAndApplication()
    def subscribeAndApplication(txTraceNo, fundNo, txAmount, realName, bankID, cardNo, certificateTypeID, certificateNo)
    {
        println '6->'
        def version = TransactionRecordConfig.version
        def txType = TransactionRecordConfig.transactionType
        def fsino = TransactionRecordConfig.fundMerchantId
        //merchantId//基金销售机构代码
        def txTime = new Date().format('yyyyMMddHHmmss')
        //new Date().format('yyyyMMdd') //
        //        def txTraceNo ="12345678901234567890123" ;//基金销售机构交易流水号
        //def busiType = "TS";//信任签约
        //        def fundNo = "000697" // 基金代码
        //        def txAmount = "100.00" // 交易金额
        //        def realName = "测试";
        //        def bankID = "0803080000"; //支付银行
        //        def cardNo = "6225882143016540"; //银行卡号
        //        def certificateTypeID = "0"; //0 身份证
        //        def certificateNo = "411424198810102110"; //证件号码
        def sgType = TransactionRecordConfig.sgType

        def signPacket = fsino + txTime + txTraceNo + fundNo + txAmount + realName + bankID + cardNo + certificateTypeID + certificateNo + sgType
        println "signPacket---->" + signPacket

        String sign = "";

        // 数字签名
        sign = addSign(signPacket)

        try
        {
            String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?><FPS><Request><Version>" + version + "</Version><TxType>" + txType + "</TxType><TxInfo><FSINo>" + fsino + "</FSINo><TxTime>" + txTime + "</TxTime><TxTraceNo>" + txTraceNo + "</TxTraceNo><FundNo>" + fundNo + "</FundNo><TxAmount>" + txAmount + "</TxAmount><RealName>" + realName + "</RealName><BankID>" + bankID + "</BankID><CardNo>" + cardNo + "</CardNo><CertificateTypeID>" + certificateTypeID + "</CertificateTypeID><CertificateNo>" + certificateNo + "</CertificateNo><SgType>" + sgType + "</SgType></TxInfo><Sign>" + sign + "</Sign></Request></FPS>"
            //<reserved1>"+ "" + "</reserved1><reserved2>"+ "" + "</reserved2><reserved3>" + "" + "</reserved3>
            println '请求报文： ' + xmlString
            //            http://www-1.fuiou.com:28056/wg1_run/GdFundSubscribeServlet.do
            def url = TransactionRecordConfig.gdFundSubscribeServleturl

            //def responseXml = transactionService.sendRequest(url, [req: URLEncoder.encode(xmlString.toString(),
            // "GBK")])
            def responseXml = sendRequest(url, [req: URLEncoder.encode(URLEncoder.encode(xmlString.toString(), "GBK")
                                                                       , "GBK")])
            def resultStr = URLDecoder.decode(responseXml, "GBK")
            def xmlObj = XML.parse(resultStr)
            def rootNode = new XmlParser().parseText(resultStr)
            def ret = verifySign(rootNode, '2022')
            // 数字签名验证
            println 'return node: ' + resultStr
            def responseCode = rootNode.ResponseInfo.ResponseCode.text()
            def responseDescription = rootNode.ResponseInfo.ResponseDesc.text()
            println 'rootNode: ' + rootNode
            println "responseCode: " + responseCode + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription]

        }
        catch (Exception e)
        {
            println "subscribeAndApplication()" + e.message
            def returnMsg = "subscribeAndApplication(): " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }

    }

    // 根据oracle数据库中的银行网点代码查找fuiou对应的支付代码
    def findFuiouPayCode(channelID)
    {
        // table: bank / type: Funds Pay
        def fuiouPayCode = ''
        switch (channelID)
        {
        case '': // 工商银行
            fuiouPayCode = '0801020000'
            break
        /*case '': // 农业银行
            fuiouPayCode = '0801030000'
            break
        case '': // 中国银行
            fuiouPayCode = '0801040000'
            break
        case '': // 建设银行
            fuiouPayCode = '0801050000'
            break
        case '': // 兴业银行
            fuiouPayCode = '0803090000'
            break
        case '': // 光大银行
            fuiouPayCode = '0803030000'
            break
        case '': // 中信银行
            fuiouPayCode = '0803020000'
            break
        case '': // 上海浦东发展银行
            fuiouPayCode = '0803100000'
            break
        case '': // 交通银行
            fuiouPayCode = '0803010000'
            break
        case '': // 中国邮政储蓄银行
            fuiouPayCode = '0801000000'
            break
        case '': // 平安银行
            fuiouPayCode = '0804105840'
            break
        case '': // 上海银行
            fuiouPayCode = '0804012900'
            break
        case '': // 北京银行
            fuiouPayCode = '0804031000'
            break
        case '': // 招商银行
            fuiouPayCode = '0803080000'
            break
        case '': // 广东发展银行
            fuiouPayCode = '0803060000'
            break
        case '': // 中国民生银行
            fuiouPayCode = '0803050000'
            break
        case '': // 华夏银行
            fuiouPayCode = '0863040000'
            break
        */
        }
    }

    // 1 基金支付 - 信任签约
    def trustSign(serialNumber, realName, bankID, cardNo, certificateTypeID, certificateNo, mobile)
    {
        def version = TransactionRecordConfig.version
        //"1.0";
        def txType = TransactionRecordConfig.operateType
        //"SG11";
        def fsino = TransactionRecordConfig.fundMerchantId
        //"290062110000031";//基金销售机构代码
        def txTime = new Date().format('yyyyMMddHHmmss')
        //DateUtil.getCurrentTime();
        def txTraceNo = serialNumber
        //"20151231092720000000002" ;//基金销售机构交易流水号
        def busiType = TransactionRecordConfig.sgType
        // "TS";//信任签约

        //def realName = "李翔";
        //def bankID = "0801050000";
        //def cardNo = "6217001370006393292";
        //def certificateTypeID = "0";//0 身份证
        //def certificateNo = "220602198805283312";

        //def mobile = "18652909140"; //
        def extendInfo = cardNo
        // "6217001370006393292";//填客户卡号，作为备案号
        def withholdingAgreementNo = "";
        def signWithholding = "1";
        def notifyURL = "";
        def merchantName = "";
        def authorizationAgreementNo = "";


        def signPacket = fsino + txTime + txTraceNo + realName + bankID + cardNo + certificateTypeID + certificateNo
        +mobile + withholdingAgreementNo + signWithholding + notifyURL + busiType + merchantName + authorizationAgreementNo + extendInfo;

        println "signPacket---->" + signPacket

        String sign = "";

        // 数字签名
        sign = addSign(signPacket)
        println "sign: " + sign

        try
        {
            String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?><FPS><Request><Version>" + version + "</Version><TxType>" + txType + "</TxType><TxInfo><FSINo>" + fsino + "</FSINo><TxTime>" + txTime + "</TxTime><TxTraceNo>" + txTraceNo + "</TxTraceNo><RealName>" + realName + "</RealName><BankID>" + bankID + "</BankID><CardNo>" + cardNo + "</CardNo><CertificateTypeID>" + certificateTypeID + "</CertificateTypeID><CertificateNo>" + certificateNo + "</CertificateNo><Mobile>" + mobile + "</Mobile><WithholdingAgreementNo>" + withholdingAgreementNo + "</WithholdingAgreementNo><SignWithholding>" + signWithholding + "</SignWithholding><NotifyURL>" + notifyURL + "</NotifyURL><BusiType>" + busiType + "</BusiType><MerchantName>" + merchantName + "</MerchantName><AuthorizationAgreementNo>" + authorizationAgreementNo + "</AuthorizationAgreementNo><ExtendInfo>" + extendInfo + "</ExtendInfo></TxInfo><Sign>" + sign + "</Sign></Request></FPS>";
            println "请求报文====" + xmlString
            def url = TransactionRecordConfig.trustSignUrl
            def oo = URLEncoder.encode(xmlString.toString(), "GBK")
            //def responseXml = transactionService.sendRequest(url, [req: URLEncoder.encode(reqestXml.toString(),
            // "GBK")])
            def responseXml = sendRequest(url, [req: URLEncoder.encode(URLEncoder.encode(xmlString.toString(), "GBK")
                                                                       , "GBK")])
            println "response: " + responseXml
            def resultStr = URLDecoder.decode(responseXml, "GBK")
            println "response: decode: " + resultStr
            def xmlObj = XML.parse(resultStr)
            def rootNode = new XmlParser().parseText(resultStr)
            def ret = verifySign(rootNode, 'Sign')
            // 数字签名验证
            def responseCode = rootNode.Response.ResponseInfo.ResponseCode.text()
            def responseDescription = rootNode.Response.ResponseInfo.ResponseDesc.text()
            println "responseCode: " + responseCode + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription]
        }
        catch (Exception e)
        {
            println "trustSign()" + e.message
            def returnMsg = "trustSign(): " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }
        /*
        if (ret)
        {
            println "返回报文验签Sign通过 ret:" + ret
            def errors = [errorCode:0000, errorMessage:'返回报文验签Sign通过 ret: ${ret}']
            render JsonOutput.toJson(errors), status: 200
            //render resultStr as JSON// ? rootNode
        }
        else
        {
            println "验签Sign--- failed ..."
            def errors = [errorCode:0006, errorMessage:"验签Sign--- failed ..."]
            render JsonOutput.toJson(errors), status: 400
        }
        */
    }

    // 基金支付 - 五要素直接签约 bankID: 10位的
    def fiveElementsDirectSign(serialNumber, realName, bankID, cardNo, certificateTypeID, certificateNo, mobile)
    {
        def version = TransactionRecordConfig.version
        //"1.0";
        def txType = TransactionRecordConfig.operateType
        //"SG11";
        def fsino = TransactionRecordConfig.fundMerchantId
        //"290062110000031";//基金销售机构代码
        def txTime = new Date().format('yyyyMMddHHmmss')
        //DateUtil.getCurrentTime();
        def txTraceNo = serialNumber
        //"20151231092720000000002" ;//基金销售机构交易流水号

        def extendInfo = ""
        //cardNo // "6217001370006393292";//填客户卡号，作为备案号
        def reserved1 = ""
        def reserved2 = ""
        def reserved3 = ""


        def signPacket = fsino + txTime + txTraceNo + realName + bankID + cardNo + certificateTypeID + certificateNo
        +mobile + extendInfo + reserved1 + reserved2 + reserved3

        println "signPacket---->" + signPacket

        String sign = "";

        // 数字签名
        sign = addSign(signPacket)
        println "sign: " + sign

        try
        {
            String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?><FPS><Request><Version>" + version + "</Version><TxType>" + txType + "</TxType><TxInfo><FSINo>" + fsino + "</FSINo><TxTime>" + txTime + "</TxTime><TxTraceNo>" + txTraceNo + "</TxTraceNo><RealName>" + realName + "</RealName><BankID>" + bankID + "</BankID><CardNo>" + cardNo + "</CardNo><CertificateTypeID>" + certificateTypeID + "</CertificateTypeID><CertificateNo>" + certificateNo + "</CertificateNo><Mobile>" + mobile + "</Mobile><ExtendInfo>" + extendInfo + "</ExtendInfo><Reserved1>" + reserved1
            +"</Reserved1><Reserved2>" + reserved2 + "</Reserved2><Reserved3>" + reserved3 + "</Reserved3></TxInfo><Sign>" + sign + "</Sign></Request></FPS>"
            println "请求报文====" + xmlString
            def url = TransactionRecordConfig.fundFiveElementsDirectSignUrl
            // 五要素直接签约
            def oo = URLEncoder.encode(xmlString.toString(), "GBK")
            //def responseXml = transactionService.sendRequest(url, [req: URLEncoder.encode(reqestXml.toString(),
            // "GBK")])
            def responseXml = sendRequest(url, [req: URLEncoder.encode(URLEncoder.encode(xmlString.toString(), "GBK")
                                                                       , "GBK")])
            println "response: " + responseXml
            def resultStr = URLDecoder.decode(responseXml, "GBK")
            println "response: decode: " + resultStr
            def xmlObj = XML.parse(resultStr)
            def rootNode = new XmlParser().parseText(resultStr)
            println 'rootNode: ' + rootNode
            def ret = verifySign(rootNode, 'DirectSign')
            // 数字签名验证
            def responseCode = rootNode.Response.ResponseInfo.ResponseCode.text()
            def responseDescription = rootNode.Response.ResponseInfo.ResponseDesc.text()
            println "responseCode: " + responseCode + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription]
        }
        catch (Exception e)
        {
            println "trustSign()" + e.message
            def returnMsg = "trustSign(): " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }

    }

    // 基金支付： 短信签约：短信验证码获取 (五要素签约 + 动态码)
    def getSMSDynamicCode(serialNumber, realName, bankID, cardNo, certificateTypeID, certificateNo, reservedCellphone)
    {
        def version = TransactionRecordConfig.version
        // "1.0";
        def txType = TransactionRecordConfig.operateType
        // "SG11";
        def fSINo = TransactionRecordConfig.fundMerchantId
        // "290062110000031";
        def txTime = new Date().format("yyyyMMddHHmmss")

        def busiType = TransactionRecordConfig.busiTypeGetNote
        //"CG";//短信验证码获取

        def txTraceNo = serialNumber
        //"20151207103030111111111" ;//基金销售机构交易流水号
        //def realName = realName                           //"测试NoteVer";
        //def bankID = "0803080000";
        //def cardNo = "6225882143016540";
        //def certificateTypeID = "0";    //0 身份证
        //def certificateNo = "411424198810102110";
        def mobile = reservedCellphone
        //"18410119857";
        def extendInfo = "";
        def withholdingAgreementNo = "";
        def signWithholding = "1";
        def notifyURL = "";

        //def strPriKey = 'MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJnuAYf6jWLg6fcfRszCBOCaPccVDx2pFqvM6
        // /9TMc65Y/LnqZpYS8KwQK2BgJi3rddu/hWJ+I3zmeIQLMkl+Kxy1PtutyDv9nTjrclsbDbiYorXv4GnzOdQnKE9eeyDHaB/Y+L
        // +fb7wZuBTGuxDVrmK0lo0Fg4AEtLsNvLAdC8lAgMBAAECgYBk2bUTHCsqD2X6zPkcCjob1bKIDtoaesApy1cLr7xATuKXMQTD5PmuEsAtbV2NsToqncPG5OB+UI4PuL0/BKvJeN1LhSkjrpKCFvHbh1rOvdzOCr9SVjC72KR6c+QeXz501iAHq74AsAnu1lgj95XL8ceuIAP0n4+K2HhwbkhmgQJBAOl9YNo3hfP4d3jmLX+j+kfA4jh0q0owm8ZvBMZWniZ15vi6dnlhqWj820q70WJuSkAGtdwR1PqGCN5bTPJp4xUCQQCoxQ2xe3BYYSW5XD20cD9qBPOp6JyMW9tNH942ipFeHe2z5Z5K9R0EYDBdDRa068zW/XxN5HP0LOqhgeCNCV/RAkBMQowImcvhdEypKxy/LLKJDwGUCN8NOzUVqZr4oL/Etgt7P4OEf5ZSWQvSq+dma7lKkiaBuz1Bmdd5sH5ASr9FAkANRcH37T8+lBVFaI2pvRIfolgPSq3VE/xBKnptS6R3BF+HQE2ck34+s9nZ14ernsezOKgn8Al2cHnbTb19GfmhAkBWvGAFNcnNA2GzfijdwDW67uPCNLg5ma0b7TzGChCvH2EojT5lAP9Nf7cop6JaQXmQTeQiMYqZJab2CVpX7Fx8'

        def signPacket = fSINo + txTime + txTraceNo + realName + bankID + cardNo + certificateTypeID + certificateNo
        +mobile + withholdingAgreementNo + signWithholding + notifyURL + busiType + extendInfo;

        def sign = "";
        sign = addSign(signPacket)
        println "sign: " + sign
        try
        {
            def xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?><FPS><Request><Version>" + version + "</Version><TxType>" + txType + "</TxType><TxInfo><FSINo>" + fSINo + "</FSINo><TxTime>" + txTime + "</TxTime><TxTraceNo>" + txTraceNo + "</TxTraceNo><RealName>" + realName + "</RealName><BankID>" + bankID + "</BankID><CardNo>" + cardNo + "</CardNo><CertificateTypeID>" + certificateTypeID + "</CertificateTypeID><CertificateNo>" + certificateNo + "</CertificateNo><Mobile>" + mobile + "</Mobile><WithholdingAgreementNo>" + withholdingAgreementNo + "</WithholdingAgreementNo><SignWithholding>" + signWithholding + "</SignWithholding><NotifyURL>" + notifyURL + "</NotifyURL><BusiType>" + busiType + "</BusiType><ExtendInfo>" + extendInfo + "</ExtendInfo></TxInfo><Sign>" + sign + "</Sign></Request></FPS>";

            def url = TransactionRecordConfig.requestNoteVerServleturl
            //"http://www-1.fuiou.com:28056/wg1_run/GdFundNoteVerificationServlet.do"

            def responseXml = sendRequest(url, [req: URLEncoder.encode(URLEncoder.encode(xmlString, "GBK"), "GBK")])

            def resultStr = URLDecoder.decode(responseXml, "GBK")
            println "result: " + resultStr
            def rootNode = new XmlParser().parseText(resultStr)
            println "rootNode: " + rootNode

            def ret = verifySign(rootNode, 'SMSSign')
            // 数字签名验证
            def responseCode = rootNode.Response.ResponseInfo.ResponseCode.text()
            def responseDescription = rootNode.Response.ResponseInfo.ResponseDesc.text()
            println "responseCode: " + responseCode + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription]

        }
        catch (Exception e)
        {
            println "getSMSDynamicCode(): " + e.message
            def returnMsg = "getSMSDynamicCode(): " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }
    }

    // 基金支付： 短信签约：五要素签约 (五要素签约 + 动态码)
    def signSMSFiveElementsInFundsPay(serialNumber, realName, bankID, cardNo, certificateTypeID, certificateNo,
        reservedCellphone, dynamicCode)
    {
        def version = TransactionRecordConfig.version
        //'1.0'
        def txType = TransactionRecordConfig.operateType
        //'SG11'
        def fsIno = TransactionRecordConfig.fundMerchantId
        //'290062110000031' //
        def tradeTime = new Date().format("yyyyMMddHHmmss")
        //'20151111161530' //params['createDate']?.trim()

        def busiType = TransactionRecordConfig.busiType
        //"CV";//短信验证码验证

        //def txTraceNo = serialNumber                        //"12345678901234567890123" ;//基金销售机构交易流水号
        //def nameOfBankAccount = realName                    //'测试DirectSign' //params['nameOfBankAccount']?.trim()
        //def bankCode = bankID                               //'0803080000' //params['bankCode']?.trim() //
        // 或者这块前端传总行名称，然后需要查找对应的code
        //def numberOfbankAccount = cardNo                    //'6225882143016540' //params['numberOfbankAccount']?
        // .trim()
        //def certificateType = certificateTypeID             //'0' //params['certificateType']?.trim()
        //def numberOfCertificate = certificateNo             //'411424198810102110' //params['numberOfCertificate']?
        // .trim()
        //def cellphone = reservedCellphone                   //'18410119857'//params['cellphone']?.trim()
        def withholdingAgreementNo = ''
        def signWithholding = '1'
        def notifyURL = ''
        def extendInfo = ''
        def verificationCode = dynamicCode
        //'111111' 客户收到的短信验证码，测试环境默认是'111111'

        String signValue = fsIno + tradeTime + serialNumber + realName + bankID + cardNo + certificateTypeID + certificateNo + reservedCellphone + withholdingAgreementNo + signWithholding + notifyURL + busiType + verificationCode + extendInfo

        println 'signValue: ' + signValue
        println 'version : ' + version
        println 'txType: ' + txType
        println 'fsIno: ' + fsIno
        println 'tradeTime: ' + tradeTime
        println 'realName: ' + realName
        println 'bankID: ' + bankID
        println 'cardNo: ' + cardNo
        println 'certificateType: ' + certificateTypeID
        println 'certificateNo: ' + certificateNo
        println 'reservedCellphone: ' + reservedCellphone

        // 数字签名
        String sign = ""
        sign = addSign(signValue)
        println "sign: " + sign

        try
        {
            // 拼接xml
            String reqestXml = "<?xml version=\"1.0\" encoding=\"GBK\"?><FPS><Request><Version>" + version + "</Version><TxType>" + txType + "</TxType><TxInfo><FSINo>" + fsIno + "</FSINo><TxTime>" + tradeTime + "</TxTime><TxTraceNo>" + serialNumber + "</TxTraceNo><RealName>" + realName + "</RealName><BankID>" + bankID + "</BankID><CardNo>" + cardNo + "</CardNo><CertificateTypeID>" + certificateTypeID + "</CertificateTypeID><CertificateNo>" + certificateNo + "</CertificateNo><Mobile>" + reservedCellphone + "</Mobile><WithholdingAgreementNo>" + withholdingAgreementNo + "</WithholdingAgreementNo><SignWithholding>" + signWithholding + "</SignWithholding><NotifyURL>" + notifyURL + "</NotifyURL><BusiType>" + busiType + "</BusiType><VerificationCode>" + verificationCode + "</VerificationCode><ExtendInfo>"
            +extendInfo + "</ExtendInfo></TxInfo><Sign>" + sign + "</Sign></Request></FPS>"
            println "requst xml: " + reqestXml
            def url = TransactionRecordConfig.requestNoteSignServletRUL
            //'http://www-1.fuiou.com:28056/wg1_run/GdFundNoteSignedServlet.do'
            def oo = URLEncoder.encode(reqestXml.toString(), "GBK")
            println "oo--: " + oo
            def responseXml = sendRequest(url, [req: URLEncoder.encode(URLEncoder.encode(reqestXml.toString(), "GBK")
                                                                       , "GBK")])
            println '-------------------------^-^------------------------------------'
            println "response: " + responseXml
            def resultStr = URLDecoder.decode(responseXml, "GBK")
            println "response: decode: " + resultStr
            //def xmlObj = XML.parse(resultStr)
            def rootNode = new XmlParser().parseText(resultStr)
            def ret = verifySign(rootNode, 'SMSSign')
            // 数字签名验证
            def responseCode = rootNode.Response.ResponseInfo.ResponseCode.text()
            def responseDescription = rootNode.Response.ResponseInfo.ResponseDesc.text()
            println "responseCode: " + responseCode + "responseDescription: " + responseDescription
            return [responseCode: responseCode, responseDescription: responseDescription]
        }
        catch (Exception e)
        {
            println "signSMSFiveElementsInFundsPay():" + e.message
            def returnMsg = "signSMSFiveElementsInFundsPay(): " + e.message
            return [responseCode: 400, responseDescription: returnMsg]
        }
    }

    // 数字签名
    def addSign(signValue)
    {

        def privateKey = TransactionRecordConfig.privateKey
        def sign = ''
        try
        {
            byte[] bytesKey = (new BASE64Decoder()).decodeBuffer(privateKey)
            //展恒私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(bytesKey)
            KeyFactory keyFactory = KeyFactory.getInstance("RSA")
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec)
            Signature signature = Signature.getInstance("MD5WithRSA")
            signature.initSign(priKey);
            signature.update(signValue.getBytes("GBK"));
            sign = (new BASE64Encoder()).encodeBuffer(signature.sign());
        }
        catch (Exception e)
        {
            e.printStackTrace()
        }

        return sign
    }

    // 对发送回来的数据验签
    def verifySign(rootNode, flag)
    {
        def retSignPackage
        def responseSign
        if (flag == 'Sign') // 基金支付 - 信任签约
        {
            def responsefsIno = rootNode.Response.TxInfo.FSINo.text()
            def responseTxTraceNo = rootNode.Response.TxInfo.TxTraceNo.text()
            def responseFPSTxTraceNo = rootNode.Response.TxInfo.FPSTxTraceNo.text()
            def responseFPSTxTime = rootNode.Response.TxInfo.FPSTxTime.text()
            def responseStatus = rootNode.Response.TxInfo.TxStatus.text()
            def responseWithholdingAgreementNo = rootNode.Response.TxInfo.WithholdingAgreementNo.text()
            def responseSex = rootNode.Response.TxInfo.Sex.text()
            def responseAddress = rootNode.Response.TxInfo.Address.text()
            def responseZipCode = rootNode.Response.TxInfo.ZipCode.text()
            def responsePhone = rootNode.Response.TxInfo.Phone.text()
            def responseMobile = rootNode.Response.TxInfo.Mobile.text()
            def responseEmail = rootNode.Response.TxInfo.EMail.text()
            def responseExtendInfo = rootNode.Response.TxInfo.ExtendInfo.text()
            responseSign = rootNode.Response.Sign.text()
            def responseCode = rootNode.Response.ResponseInfo.ResponseCode.text()
            def responseDesc = rootNode.Response.ResponseInfo.ResponseDesc.text()
            // 解fuiou发送过来的数字签名
            retSignPackage = responsefsIno + responseTxTraceNo + responseFPSTxTraceNo + responseFPSTxTime + responseStatus + responseWithholdingAgreementNo + responseSex + responseAddress + responseZipCode + responsePhone + responseMobile + responseEmail + responseExtendInfo
        }
        if (flag == 'DirectSign') // 基金支付 - 五要素签约
        {
            def responsefsIno = rootNode.TxInfo.FSINo.text()
            def responseTxTraceNo = rootNode.TxInfo.TxTraceNo.text()
            def responseFPSTxTraceNo = rootNode.TxInfo.FPSTxTraceNo.text()
            def responseFPSTxTime = rootNode.TxInfo.FPSTxTime.text()
            def responseStatus = rootNode.TxInfo.TxStatus.text()
            def responseMobile = rootNode.TxInfo.Mobile.text()
            def responseExtendInfo = rootNode.TxInfo.ExtendInfo.text()
            def responseReserved1 = rootNode.TxInfo.Reserved1.text()
            def responseReserved2 = rootNode.TxInfo.Reserved2.text()
            def responseReserved3 = rootNode.TxInfo.Reserved3.text()
            responseSign = rootNode.Sign.text()
            def responseCode = rootNode.ResponseInfo.ResponseCode.text()
            def responseDesc = rootNode.ResponseInfo.responseDesc.text()
            // 解fuiou发送过来的数字签名
            retSignPackage = responsefsIno + responseTxTraceNo + responseFPSTxTraceNo + responseFPSTxTime + responseStatus + responseMobile + responseExtendInfo + responseReserved1 + responseReserved2 + responseReserved3
        }
        else if (flag == 'SMSSign') // 短信签约(五要素签约+动态码中的五要素签约):
        {
            def responsefsIno = rootNode.TxInfo.FSINo.text()
            def responseTxTraceNo = rootNode.TxInfo.TxTraceNo.text()
            def responseFPSTxTraceNo = rootNode.TxInfo.FPSTxTraceNo.text()
            def responseFPSTxTime = rootNode.TxInfo.FPSTxTime.text()
            def responseStatus = rootNode.TxInfo.TxStatus.text()
            def responseWithholdingAgreementNo = rootNode.TxInfo.WithholdingAgreementNo.text()
            def responseSex = rootNode.TxInfo.Sex.text()
            def responseAddress = rootNode.TxInfo.Address.text()
            def responseZipCode = rootNode.TxInfo.ZipCode.text()
            def responsePhone = rootNode.TxInfo.Phone.text()
            def responseMobile = rootNode.TxInfo.Mobile.text()
            def responseEmail = rootNode.TxInfo.EMail.text()
            def responseExtendInfo = rootNode.TxInfo.ExtendInfo.text()
            responseSign = rootNode.Sign.text()
            def responseCode = rootNode.ResponseInfo.ResponseCode.text()
            def responseDesc = rootNode.ResponseInfo.ResponseDesc.text()

            // 解fuiou发送过来的数字签名
            retSignPackage = responsefsIno + responseTxTraceNo + responseFPSTxTraceNo + responseFPSTxTime + responseStatus + responseWithholdingAgreementNo + responseSex + responseAddress + responseZipCode + responsePhone + responseMobile + responseEmail + responseExtendInfo
        }
        else if (flag == 'SMSCode') // 短信签约（五要素签约+短信验证码获取）： 短信验证码
        {
            def responsefsIno = rootNode.TxInfo.FSINo.text()
            def responseTxTraceNo = rootNode.TxInfo.TxTraceNo.text()
            def responseFPSTxTraceNo = rootNode.TxInfo.FPSTxTraceNo.text()
            def responseFPSTxTime = rootNode.TxInfo.FPSTxTime.text()
            def responseStatus = rootNode.TxInfo.TxStatus.text()
            def responseWithholdingAgreementNo = rootNode.TxInfo.WithholdingAgreementNo.text()
            def responseSex = rootNode.TxInfo.Sex.text()
            def responseAddress = rootNode.TxInfo.Address.text()
            def responseZipCode = rootNode.TxInfo.ZipCode.text()
            def responsePhone = rootNode.TxInfo.Phone.text()
            def responseMobile = rootNode.TxInfo.Mobile.text()
            def responseEmail = rootNode.TxInfo.EMail.text()
            def responseExtendInfo = rootNode.TxInfo.ExtendInfo.text()
            responseSign = rootNode.Sign.text()
            def responseCode = rootNode.ResponseInfo.ResponseCode.text()
            def responseDesc = rootNode.ResponseInfo.ResponseDesc.text()

            // 解fuiou发送过来的数字签名
            retSignPackage = responsefsIno + responseTxTraceNo + responseFPSTxTraceNo + responseFPSTxTime + responseStatus + responseWithholdingAgreementNo + responseSex + responseAddress + responseZipCode + responsePhone + responseMobile + responseEmail + responseExtendInfo
        }
        else if (flag == '2022') // 认申购
        {
            def responsefsIno = rootNode.TxInfo.FSINo.text()
            def responseTxTraceNo = rootNode.TxInfo.TxTraceNo.text()
            def responseTxAmount = rootNode.TxInfo.TxAmount.text()
            def responseFPSTxTraceNo = rootNode.TxInfo.FPSTxTraceNo.text()
            def responseFPSTxTime = rootNode.TxInfo.FPSTxTime.text()
            def responseStatus = rootNode.TxInfo.TxStatus.text()
            responseSign = rootNode.Sign.text()
            def responseCode = rootNode.ResponseInfo.ResponseCode.text()
            def responseDesc = rootNode.ResponseInfo.ResponseDesc.text()
            // 解fuiou发送过来的数字签名
            retSignPackage = responsefsIno + responseTxTraceNo + responseTxAmount + responseFPSTxTraceNo + responseFPSTxTime + responseStatus
        }
        else if (flag == 'Query')
        {
            def responsefsIno = rootNode.Response.TxInfo.FSINo.text()
            def responseTxTraceNo = rootNode.Response.TxInfo.TxTraceNo.text()
            def responseLastTxTraceNo = rootNode.Response.TxInfo.LastTxTraceNo.text()
            def responseLastTxDate = rootNode.Response.TxInfo.LastTxDate.text()
            def responseLastTxType = rootNode.Response.TxInfo.LastTxType.text()
            def responseTxAmount = rootNode.Response.TxInfo.TxAmount.text()
            def responseFPSTxTraceNo = rootNode.Response.TxInfo.FPSTxTraceNo.text()
            def responseFPSTxTime = rootNode.Response.TxInfo.FPSTxTime.text()
            def responseStatus = rootNode.Response.TxInfo.TxStatus.text()
            def responseExtendInfo = rootNode.Response.TxInfo.ExtendInfo.text()
            responseSign = rootNode.Response.Sign.text()
            def responseCode = rootNode.Response.ResponseInfo.ResponseCode.text()
            def responseDesc = rootNode.Response.ResponseInfo.ResponseDesc.text()
            // 解fuiou发送过来的数字签名
            retSignPackage = responsefsIno + responseTxTraceNo + responseLastTxTraceNo + responseLastTxDate + responseLastTxType + responseTxAmount + responseFPSTxTraceNo + responseFPSTxTime + responseStatus + responseExtendInfo
        }

        println 'retSignPackage: ' + retSignPackage

        def publicKey = TransactionRecordConfig.publicKey
        boolean ret = 'false'
        // 解数字签名 解密由base64编码的公钥
        try
        {
            byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey)
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec1 = new X509EncodedKeySpec(keyBytes)
            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory1 = KeyFactory.getInstance("RSA")
            // 取公钥匙对象
            PublicKey pubKey1 = keyFactory1.generatePublic(keySpec1)
            Signature signature = Signature.getInstance("MD5withRSA")
            signature.initVerify(pubKey1)
            signature.update(retSignPackage.getBytes('GBK'))
            ret = signature.verify(new BASE64Decoder().decodeBuffer(responseSign))
            println 'ret: ' + ret
            return ret
        }
        catch (Exception e)
        {
            println '-1111: '
            e.printStackTrace()
            return ret
        }

    }
}

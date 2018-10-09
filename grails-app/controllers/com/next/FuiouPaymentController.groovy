package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.apache.commons.codec.binary.Hex
import org.springframework.security.access.annotation.Secured

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@Transactional(readOnly = true)
class FuiouPaymentController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    /**
     * 富有四要素验卡
     * @Author 袁超
     * @ModifiedDate 2017年7月11日 10:03:44
     */
    @Secured(['permitAll'])
    @Transactional
    def bankCardCheck()
    {
        def fullName = params.fullName
        def cardNo = params.numberOfAccount
        def cellphone = params.cellphone
        def idNumber = params.idNumber
        def map = [:]
        if (fullName && cardNo && cellphone && idNumber)
        {
            /* def url = "https://mpay.fuiou.com:16128/checkCard/checkCard01.pay"
             def merchantNo = "0001000F0397137"
             def key = "jvmda9d2sg2tgfjlagojwaty9vlfy32t"*/
            def url = "https://mpay.fuiou.com:16128/checkCard/checkCard01.pay"
            def merchantNo = "0001000F0397137"
            def key = "jvmda9d2sg2tgfjlagojwaty9vlfy32t"
            def version = "1.30"
            def cardType = "0"
            def serialNumber = new Date().getTime()

            def macSource = merchantNo + "|" + version + "|" + serialNumber + "|" + cardNo + "|" + cardType + "|" + idNumber + "|" + key
            println macSource
            def signature = encode(macSource.toString(), "UTF-8")
            def xml = "<FM>" + "<MchntCd>" + merchantNo + "</MchntCd>\n" + "<Ono>" + cardNo + "</Ono>\n" + "<Onm>" + fullName + "</Onm>\n" + "<OCerTp>" + cardType + "</OCerTp>\n" + "<OCerNo>" + idNumber + "</OCerNo>\n" + "<Mno>" + cellphone + "</Mno>\n" + "<Sign>" + signature + "</Sign>\n" + "<Ver>" + version + "</Ver>\n" + "<OSsn>" + serialNumber + "</OSsn>\n" + "</FM>"
            println xml
            def result = requestPost(url, "FM=" + xml)
            def rootNode = new groovy.util.XmlParser().parseText(result)
            def Rcd = rootNode.Rcd.text()
            def RDesc = rootNode.RDesc.text()
            def OSsn = rootNode.OSsn.text()
            def CardNo = rootNode.CardNo.text()
            def MchntCd = rootNode.MchntCd.text()
            def Ver = rootNode.Ver.text()
            def Sign = rootNode.Sign.text()
            println Sign
            def signSource = Rcd + "|" + OSsn + "|" + CardNo + "|" + MchntCd + "|" + Ver + "|" + key
            signSource = encode(signSource.toString(), "UTF-8")
            println signSource
            if (Sign == signSource)
            {
                if (Rcd == "0000")
                {
                    map.put("flag", "成功")
                    map.put("memo", RDesc)
                }
                else
                {
                    map.put("flag", "失败")
                    map.put("memo", RDesc)
                }
            }
            else
            {
                map.put("flag", "失败")
                map.put("memo", "验签失败")
            }
        }
        else
        {
            map.put("flag", "失败")
            map.put("memo", "四要素不全")
        }
        println map
        render JSON.parse(JsonOutput.toJson(map))
    }

    def requestPost(url, params)
    {
        URL urlString = new URL(url)
        println url
        HttpURLConnection urlConnection = (HttpURLConnection) urlString.openConnection()
        urlConnection.setRequestMethod('POST')
        urlConnection.setDoOutput(true)
        urlConnection.setReadTimeout(10000)
        urlConnection.outputStream.withWriter("UTF-8") { Writer writer -> writer.write params }
        def responeResult = urlConnection.inputStream.withReader("UTF-8") { Reader reader -> reader.text }
        return responeResult
    }

    def encode(String origin, String charsetname)
    {
        String resultString = null;
        resultString = new String(origin);
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        if (charsetname == null || "".equals(charsetname))
        {
            resultString = Hex.encodeHexString(md.digest(resultString.getBytes()));
        }
        else
        {
            try
            {
                resultString = Hex.encodeHexString(md.digest(resultString.getBytes(charsetname)));
            }
            catch (UnsupportedEncodingException e)
            {
                throw new RuntimeException(e);
            }
        }
        return resultString;
    }

}

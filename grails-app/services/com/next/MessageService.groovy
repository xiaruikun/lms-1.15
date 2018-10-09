package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

class ApiUrl {
    def apiUrl = CreditReportProvider.findByCode('PV').apiUrl
}

@Transactional
@CompileStatic
@TypeChecked
class MessageService
{
    ApiUrl apiUrl
    static scope = "singleton"
    //返回值为[statusCode:message],如果调成功statusCode为0,如果调用失败statusCode为错误代码,message里为错误原因
    def sendMessage(String cellphone, String templateId, String text)
    {
        def errors = ["0": "OK",
            "1": "请求参数缺失",
            "2": "请求参数格式错误",
            "3": "账户余额不足",
            "4": "关键词屏蔽",
            "5": "未找到对应id的模板",
            "6": "添加模板失败",
            "7": "模板不可用",
            "8": "同一手机号30秒内重复提交相同的内容",
            "9": "同一手机号5分钟内重复提交相同的内容超过3次",
            "10": "手机号黑名单过滤",
            "11": "接口不支持GET方式调用",
            "12": "接口不支持POST方式调用",
            "13": "营销短信暂停发送",
            "14": "解码失败",
            "15": "签名不匹配",
            "16": "签名格式不正确",
            "17": "24小时内同一手机号发送次数超过限制",
            "18": "签名校验失败",
            "19": "请求已失效",
            "20": "不支持的国家地区",
            "21": "解密失败",
            "22": "1小时内同一手机号发送次数超过限制",
            "23": "发往模板支持的国家列表之外的地区",
            "24": "添加告警设置失败",
            "25": "手机号和内容个数不匹配",
            "26": "流量包错误",
            "27": "未开通金额计费",
            "28": "运营商错误",
            "-1": "非法的apikey",
            "-2": "API没有权限",
            "-3": "IP没有权限",
            "-4": "访问次数超限",
            "-5": "访问频率超限",
            "-50": "未知异常",
            "-51": "系统繁忙",
            "-52": "充值失败",
            "-53": "提交短信失败",
            "-54": "记录已存在",
            "-55": "记录不存在",
            "-57": "用户开通过固定签名功能，但签名未设置"]

        def response

        def encode = "UTF-8"
        def apiKey = "74eaa83e3d2eba356a95e929a1e663f4"
        // def templateId = "1302623"

        Message message = new Message()

        message.platform = "云片"
        message.text = "${templateId}:${text}"
        message.cellphone = cellphone
        message.save()

        URL url = new URL("http://yunpian.com/v1/sms/tpl_send.json")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        //            connection.setRequestProperty("Accept-Charset",encode)

        def params = "apikey=" + URLEncoder.encode(apiKey, encode) + "&tpl_id=" + URLEncoder.encode(templateId,
                                                                                                    encode) + "&tpl_value=" + URLEncoder.encode("#code#=" + text, encode) + "&mobile=" + URLEncoder.encode(cellphone, encode)
        println params

        connection.outputStream.withWriter { Writer writer -> writer.write params }
        response = connection.inputStream.withReader { Reader reader -> reader.text }

        def code = JSON.parse(response).getAt("code")?.toString()

        if (code)
        {
            message.status = "Failed"
            message.returnCode = code
            message.returnMessage = errors[code]
            message.save()

            return [statusCode: code, message: message.returnMessage]
        }
        else
        {
            message.status = "Succesful"
            message.returnCode = 0
            message.save()
        }

        [statusCode: 0, message: "OK"]
    }

    //返回值为[statusCode:message],如果调成功statusCode为0,如果调用失败statusCode为错误代码,message里为错误原因
    def sendMessage2(String cellphone, String text)
    {
        /*InetAddress address = InetAddress.getByName("s85.zhongjiaxin.com");
        InetAddress address1 = InetAddress.getByName("s54.zhongjiaxin.com");
        if(address.getHostAddress()=="10.0.8.170" || address1.getHostAddress()=="10.0.8.121")
        {
            cellphone = "18001317761"
        }*/
        if (apiUrl != 'http://10.0.8.127'){
            cellphone = '18001317761'
        }

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

        def message = new Message()
        message.platform = "领选互联"
        message.text = text
        message.cellphone = cellphone
        message.save()

        URL url = new URL("http://101.200.29.88:8082/SendMT/SendMessage")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        //            connection.setRequestProperty("Accept-Charset",encode)

        def params = "CorpID=" + URLEncoder.encode(corpId, encode) + "&Pwd=" + URLEncoder.encode(pwd, encode) + "&Mobile=" + URLEncoder.encode(cellphone, encode) + "&Content=" + URLEncoder.encode(text, encode)

        println params

        connection.outputStream.withWriter { Writer writer -> writer.write params }
        response = connection.inputStream.withReader { Reader reader -> reader.text }
        println response.toString().split(",")[0]
        def result = response.toString().split(",")
        def code = result[0]

        println code

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

            return [statusCode: code.toInteger(), message: message.returnMessage]
        }

        // log.message = message
        // message.save flush: true
        // log.save flush: true
        [statusCode: 0, message: "OK"]
    }

    //返回值为[statusCode:message],如果调成功statusCode为0,如果调用失败statusCode为错误代码,message里为错误原因
    def sendMessage3(String cellphone, String text)
    {
        def response

        def corpId = "kaijingyz"
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

        def message = new Message()
        message.platform = "领选互联"
        message.text = text
        message.cellphone = cellphone
        message.save()

        URL url = new URL("http://101.200.29.88:8082/SendMT/SendMessage")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        //            connection.setRequestProperty("Accept-Charset",encode)

        def params = "CorpID=" + URLEncoder.encode(corpId, encode) + "&Pwd=" + URLEncoder.encode(pwd, encode) + "&Mobile=" + URLEncoder.encode(cellphone, encode) + "&Content=" + URLEncoder.encode(text, encode)

        println params

        connection.outputStream.withWriter { Writer writer -> writer.write params }
        response = connection.inputStream.withReader { Reader reader -> reader.text }

        def result = response.toString().split(",")
        def code = result[0]

        println code

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

            return [statusCode: code.toInteger(), message: message.returnMessage]
        }

        // log.message = message
        // message.save flush: true
        // log.save flush: true
        [statusCode: 0, message: "OK"]
    }
}

//http://101.200.29.88:8082/SendMT/SendMessage?CorpID=1001&Pwd=123&Mobile=XXX&Content=%D%E

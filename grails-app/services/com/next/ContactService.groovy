package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@Transactional
@CompileStatic
class ContactService
{
    static scope = "singleton"

    def setRequest(String code, String state)
    {
        def response
        def encode = "UTF-8"
        def CODE = code

        // 中佳信中佳信
        def APPID = "wx464de39cbfe33d14"
        def SECRET = "1ea81262014312117b5d2fc44d95053c"

        //北京贷款中心
        // def APPID = "wxe49dcb507643c1cd"
        // def SECRET = "217c05ff855a665ef4e0f3db8f4c9371"

        URL url = new URL("https://api.weixin.qq" + ".com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type" + "=authorization_code")
        def params = "APPID=" + URLEncoder.encode(APPID, encode) + "&SECRET=" + URLEncoder.encode(SECRET, encode) + "&CODE=" + URLEncoder.encode(CODE, encode)

        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        connection.outputStream.withWriter { Writer writer -> writer.write params }
        response = connection.inputStream.withReader { Reader reader -> reader.text }
        def openId = JSON.parse(response).getAt("openid")
        println "微信公众平台响应：" + response
        println "微信公众平台返回OpenID：" + openId
        return openId
    }

    def toJson(Contact contact)
    {
        def data = [fullName: contact.fullName, city: contact.city?.name, avatar: contact.avatar, userCode: contact
            .userCode, appSessionId: contact.appSessionId, level: contact.level, cellphone: contact.cellphone,
            userName: contact.user?.fullName, idNumber: contact.idNumber, bankName: contact.bankName,
            bankAccount: contact.bankAccount]
        return JsonOutput.toJson(data)
    }

    Contact findContactBySessionId(String sessionId)
    {
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    Boolean verifyIdNumber(String idNumber)
    {
        def city = [11: "北京",
            12: "天津",
            13: "河北",
            14: "山西",
            15: "内蒙古",
            21: "辽宁",
            22: "吉林",
            23: "黑龙江 ",
            31: "上海",
            32: "江苏",
            33: "浙江",
            34: "安徽",
            35: "福建",
            36: "江西",
            37: "山东",
            41: "河南",
            42: "湖北 ",
            43: "湖南",
            44: "广东",
            45: "广西",
            46: "海南",
            50: "重庆",
            51: "四川",
            52: "贵州",
            53: "云南",
            54: "西藏 ",
            61: "陕西",
            62: "甘肃",
            63: "青海",
            64: "宁夏",
            65: "新疆",
            71: "台湾",
            81: "香港",
            82: "澳门",
            91: "国外 "]
        if (!(idNumber.matches(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/)))
        {
            return false
        }
        else if (!city[Integer.parseInt(idNumber.substring(0, 2))])
        {
            return false
        }
        else
        {
            if (idNumber.length() == 18)
            {
                def factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                def parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
                def sum = 0;
                def ai = 0;
                def wi = 0;
                for (
                    int i = 0;
                        i < 17;
                        i++)
                {
                    ai = Integer.parseInt(String.valueOf(idNumber.charAt(i)));
                    wi = factor[i];
                    sum += ai * wi;
                }
                def index = sum % 11;

                if (index == 2 && idNumber[17] != 'X') //&& idNumber[17] != 'x'
                {
                    return false;
                }
                if (index != 2 && !parity[sum % 11].toString().equals(String.valueOf(idNumber.charAt(17))))
                {
                    return false;
                }
            }
        }
        return true
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    Boolean verifyCellphoneForCentury21(String cellphone)
    {
        println "####################121212####"
        URL url = new URL("http://sis-resource.century21cn.com:8001/WebPage/Other/CheckUserPhone.ashx")
        def encode = "UTF-8"
        def params = "cellphone=" + URLEncoder.encode(cellphone, encode)
        def now = new Date().getTime()
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        connection.outputStream.withWriter { Writer writer -> writer.write params }
        Contact conntact = Contact.find("from Contact where cellphone='${cellphone}' and type='Agent'")
        def response
        try
        {
            response = connection.inputStream.withReader("UTF-8") { Reader reader -> reader.text }
            println "************************" + response
            if (connection.getResponseCode() == 200)
            {
                return true
            }
            //            else
            //            {
            //                println "*************888888888*************"
            //                while (account){
            //                    println "**********account:"+account.id
            //                    if (account.parent?.id){
            //                        account.id = account.parent.id
            //                        account = Account.find("from Account where id = ${account.id}")
            //                    }else {
            //                        println "************account.code:"+account.code
            //                        if (account.code=="cdae403a-c468-49ee-b68d-b40b4807e55a"){
            //                            AccountContact accountContact = AccountContact.find("from AccountContact where contact.id=${conntact.id}")
            //                            def nowd = accountContact.leavedate.getTime()
            //                            println "******now:"+now
            //                            println "******nowd:"+nowd
            //                            if (nowd<now){
            //                                println "###############TRUE"
            //                                return true
            //                            }else
            //                            {
            //                                println "###############FALSE"
            //                                //21员工
            //                                return false
            //                            }
            //                        }else
            //                        {
            //                            return true
            //                            break
            //                        }
            //                    }
            //
            //                }
            //
            //            }
        }
        catch (Exception e)
        {
            AccountContact accountContact = AccountContact.find("from AccountContact where contact.id=${conntact.id} and account.id=${conntact.account.id}")
            if (accountContact)
            {
                def nowd = accountContact.leavedate.getTime()
                if (nowd < now)
                {
                    return true
                }
                else
                {
                    return false
                }
            }
            else
            {
                return false
            }
            //            while (account){
            //                if (account.parent?.id){
            ////                    account.id = account.parent.id
            //                    println "***************account.id:"+account.id
            //                    account = Account.find("from Account where id = ${account.id}")
            //                }
            //                else
            //                {
            //                    println "************account.code:"+account.code
            //                    if (account.code=="cdae403a-c468-49ee-b68d-b40b4807e55a"){
            //                        println "debug"
            //                        AccountContact accountContact = AccountContact.find("from AccountContact where contact.id=${conntact.id}")
            //                        println "debug2"+accountContact.id
            //                        def nowd = accountContact.leavedate.getTime()
            //                        println "*********now:"+now
            //                        println "*********nowd:"+nowd
            //                        if (nowd<now){
            //                            println "###############TRUE"
            //                            return true
            //                        }else
            //                        {
            //                            println "###############FALSE"
            //                            //21员工
            //                            return false
            //                        }
            //                    }
            //                    else
            //                    {
            //                        return true
            //                        break
            //                    }
            //                }
            //
            //            }
            //            println "21InterfaceException:" + e
            //            return false
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    def indexByAgent(User user, Integer offset)
    {
        //Integer offset
        Integer max = 10

        def list = []
        Role role = Role.find("from Role where authority = 'ROLE_ACCOUNT_MANAGER'")
        UserRole userRole = UserRole.find("from UserRole as ur where ur.user.id = ${user.id} and ur.role.id = ${role.id}")
        if (userRole)
        {
            list = Contact.findAll("from Contact as c where c.user.id = ${user.id} and c.type = 'Agent' order by " + "createdDate desc", [max: max, offset: offset])
        }
        // role = Role.find("from Role where authority = 'ROLE_COO'")
        // userRole = UserRole.find("from UserRole as ur where ur.user.id = ${user.id} and ur.role.id = ${role.id}")
        // if (userRole)
        // {
        //     list = Contact.findAll("from Contact as c where c.type = 'Agent' order by c.createdDate desc", [max: max,
        //         offset: offset])
        // }
        role = Role.find("from Role where authority = 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'")
        userRole = UserRole.find("from UserRole as ur where ur.user.id = ${user.id} and ur.role.id = ${role.id}")
        if (userRole)
        {
            list = Contact.findAll("from Contact as c where c.user.city.id = ${user?.city?.id} and c.type = 'Agent' order by c.createdDate desc", [max: max,
                offset: offset])
        }
        role = Role.find("from Role where authority = 'ROLE_BRANCH_GENERAL_MANAGER'")
        userRole = UserRole.find("from UserRole as ur where ur.user.id = ${user.id} and ur.role.id = ${role.id}")
        if (userRole)
        {
            list = Contact.findAll("from Contact as c where c.user.city.id = ${user?.city?.id} and c.type = 'Agent' order by c.createdDate desc", [max: max,
                offset: offset])
        }
        role = Role.find("from Role where authority = 'ROLE_ADMINISTRATOR'")
        userRole = UserRole.find("from UserRole as ur where ur.user.id = ${user.id} and ur.role.id = ${role.id}")
        if (userRole)
        {
            list = Contact.findAll("from Contact as c where c.type = 'Agent' order by c.createdDate desc", [max: max,
                offset: offset])
        }

        return list
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    def indexByClient(User user, Integer offset)
    {

        Integer max = 10

        def list = []
        Role role = Role.find("from Role where authority ='ROLE_ACCOUNT_MANAGER'")
        UserRole userRole = UserRole.find("from UserRole  as ur where ur.user.id=${user.id} and ur.role.id = ${role.id}")
        if (userRole)
        {
            list = Contact.findAll("from Contact as c where c.user.id=${user.id} and (c.type='Client' or c.type is null) order by " + "createdDate desc", [max: max, offset: offset])
        }
        // role = Role.find("from Role where authority ='ROLE_COO'")
        //
        // userRole = UserRole.find("from UserRole as ur where ur.user.id = ${user.id} and ur.role.id = ${role.id}")
        //
        // if (userRole)
        // {
        //     list = Contact.findAll("from Contact as c where  c.type='Client' or c.type is null order by createdDate desc", [max: max,
        //         offset: offset])
        // }

        role = Role.find("from Role where authority ='ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'")

        userRole = UserRole.find("from UserRole as ur where ur.user.id = ${user.id} and ur.role.id = ${role.id}")

        if (userRole)
        {
            list = Contact.findAll("from Contact as c where c.user.city.id = ${user?.city?.id} and c.type='Client' or c.type is null order by createdDate desc", [max: max,
                offset: offset])
        }
        role = Role.find("from Role where authority ='ROLE_BRANCH_GENERAL_MANAGER'")

        userRole = UserRole.find("from UserRole as ur where ur.user.id = ${user.id} and ur.role.id = ${role.id}")

        if (userRole)
        {
            list = Contact.findAll("from Contact as c where c.user.city.id = ${user?.city?.id} and c.type='Client' or c.type is null order by createdDate desc", [max: max,
                offset: offset])
        }

        role = Role.find("from Role where authority ='ROLE_ADMINISTRATOR'")

        userRole = UserRole.find("from UserRole as ur where ur.user.id = ${user.id} and ur.role.id = ${role.id}")

        if (userRole)
        {
            list = Contact.findAll("from Contact as c where  c.type='Client' or c.type is null order by createdDate desc", [max: max,
                offset: offset])
        }


        return list

    }

    def generateVerifiedCode()
    {
        def verifiedCode = ""
        for (
            int i = 0;
                i < 6;
                i++)
        {
            verifiedCode += (int) Math.floor(Math.random() * 10)
        }

        return verifiedCode
    }

}

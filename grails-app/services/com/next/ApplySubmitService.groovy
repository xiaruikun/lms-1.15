package com.next

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class ApplySubmitService
{

    /**
     * 1001申请流水号接口*/
    def avicAheadSubmit = { opportunity ->

        def sw = new java.io.StringWriter()
        def xml = new groovy.xml.MarkupBuilder(sw)
        xml.REQ {
            HEAD {
                //申请方渠道码
                ReqCode('WBJFZJXF')
                //交易代码
                MsgNo('1001')
                //报文标识号
                MsgId("WBJFZJXF" + new Date().format("yyyyMMdd") + "000001")
                //工作日期
                WorkData(new Date().format("yyyyMMdd"))
                //工作时间
                WorkTime(new Date().format("HHmmss"))
            }
        }
        def callAvicWs = { String xml2 ->
            String retMsg = null;
            try
            {
                javax.xml.namespace.QName SERVICE_NAME = new javax.xml.namespace.QName("CMISChannelService", "CMISChannelService");

                URL wsdlURL = com.zjx.service.webservice.avic.CMISChannelService.WSDL_LOCATION;

                com.zjx.service.webservice.avic.CMISChannelService ss = new com.zjx.service.webservice.avic.CMISChannelService(wsdlURL, SERVICE_NAME);
                com.zjx.service.webservice.avic.CMISChannelServicePortType port = ss.getCMISChannelServiceHttpPort();

                retMsg = port.input(xml2);
            }
            catch (java.lang.Exception e)
            {
                org.apache.commons.logging.Log.logger.error(e.getMessage(), e);
            }

            return retMsg;
        }
        def retXml = callAvicWs(sw.toString())
        println retXml
        def resps = new XmlParser().parseText(retXml)
        println resps.text()
        def channelRecord = new com.next.ChannelRecord()
        channelRecord.interfaceCode = '1001'
        channelRecord.avic_apply_cde = resps.MSG.ApplCde.text()
        channelRecord.avic_apply_seq = resps.MSG.ApplSeq.text()
        //println resps.HEAD.ErrorMsg.text()
        //channelRecord.status= resps.HEAD.ErrorMsg.text()
        channelRecord.opportunity = opportunity
        if (channelRecord.validate())
        {
            channelRecord.save flush: true
        }
        else
        {
            println channelRecord.errors
            println("cuowu")
        }
    }
    /**
     * 5000中航信申请审批*/
    def avicAheadSubmit5000 = { opportunity ->
        def apptTypeList = com.next.OpportunityContact.findAllByOpportunity(opportunity)
        def houseInfo = com.next.Collateral.findByOpportunity(opportunity)
        def acctList = com.next.OpportunityBankAccount.findAllByOpportunity(opportunity)
        println(ChannelRecord.findByOpportunity(opportunity).avic_apply_seq)
        def sw1 = new java.io.StringWriter()
        def xml1 = new groovy.xml.MarkupBuilder(sw1)

        xml1.REQ {
            HEAD {
                //申请方渠道码
                ReqCode('WBJFZJXF')
                //交易代码
                MsgNo('5000')
                //报文标识号
                MsgId("WBJFZJXF" + new Date().format("yyyyMMdd") + "000001")
                //工作日期
                WorkData(new Date().format("yyyyMMdd"))
                //工作时间
                WorkTime(new Date().format("HHmmss"))
            }
            MSG {
                ApplSeq(com.next.ChannelRecord.findByOpportunity(opportunity).avic_apply_seq)
                AppAdvice('同意')
                AppConclusion('10')
                //申请人
                ApptList {
                    apptTypeList?.each {
                        def opportunityContact = it
                        //将每一列it传递到Result的节点中

                        Result {
                            //申请人类型
                            if (opportunityContact?.type?.name == '借款人')
                            {
                                ApptTyp('01')
                            }
                            else
                            {
                                ApptTyp('02')
                            }
                            CustName(opportunityContact?.opportunity.fullName)
                            if (opportunity?.contact?.identityType?.name == '身份证')
                            {
                                IdTyp('20')
                            }
                            else
                            {
                                IdTyp('20')
                            }

                            IdNo(opportunityContact?.contact?.idNumber) //证件号码
                            //出生日期
                            def text = opportunityContact?.contact?.idNumber
                            ApptStartDate(text[6..9] + "-" + text[10..11] + "-" + text[12..13])
                            //申请人年龄
                            def now = "${new Date().format("yyyyMMdd")}"
                            def year = now[0..3]
                            //def m1=year.toInteger()
                            //def m2=text[6..9].toString().toInteger()
                            //ApptAge(m1)
                            ApptAge("${year.toInteger() - text[6..9].toInteger()}")
                            // 创建机构(表中没有)
                            CrtBch('WBJFZJXF')
                            //创建时间
                            CrtDt(new Date().format("yyyy-MM-dd"))
                            //申请人基本信息
                            IndivInfo {
                                //性别
                                IndivSex('10')
                                //婚姻
                                if (opportunityContact?.contact?.maritalStatus == '未婚')
                                {
                                    IndivMarital('10')
                                }
                                else if (opportunityContact?.contact?.maritalStatus == '已婚')
                                {
                                    IndivMarital('20')
                                }
                                else if (opportunityContact?.contact?.maritalStatus == '离异')
                                {
                                    IndivMarital('40')
                                }
                                else if (opportunityContact?.contact?.maritalStatus == '丧偶')
                                {
                                    IndivMarital('50')
                                }
                                else
                                {
                                    IndivMarital('20')
                                }
                                // 最高学历
                                IndivEdu('10')
                                //文化程度
                                IndivDegree('9')
                                // 现住房情况
                                LiveInfo('99')
                                // 现住房所在省
                                LiveProvince('120105')
                                // 现住房所在城市
                                if (com.next.Collateral.findByOpportunity(opportunityContact)?.city?.name != null)
                                {
                                    LiveCity(com.next.Collateral.findByOpportunity(opportunityContact)?.city?.name)
                                }
                                else
                                {
                                    LiveCity('131000')
                                }
                                // 现住房所在区
                                if (com.next.Collateral.findByOpportunity(opportunityContact)?.district != null)
                                {
                                    LiveArea(com.next.Collateral.findByOpportunity(opportunityContact)?.district)
                                }
                                else
                                {
                                    LiveArea('131002')
                                }
                                // 现住房所在地址,详细地址
                                if (com.next.Collateral.findByOpportunity(opportunityContact)?.address != null)
                                {
                                    LiveAddr(com.next.Collateral.findByOpportunity(opportunityContact)?.address)
                                }
                                else
                                {
                                    LiveAddr('其他')
                                }
                                // 现住房所在地址邮编
                                LiveZip('131004')
                                // 户口性质
                                LocalResid('10')
                                // 手机号
                                IndivMobile(opportunityContact?.contact?.cellphone)
                                // 职务
                                PositionOpt('50')
                                // 现单位名称
                                IndivEmpName('个体')
                                // 单位性质
                                IndivEmpTyp('A')
                                // 收入
                                IndivMthInc('12000.0')
                                // 邮寄地址选项
                                MailOpt('A')
                                // 邮寄地址省
                                MailProvince('120105')
                                // 邮寄地址市
                                MailCity('131000')
                                // 邮寄地址区
                                MailArea('131002')
                                // 邮寄地址详细地址
                                MailAddr('123102')
                                // 职称
                                IndivProfsn('00')
                                // 申请单位行业
                                IndivIndtryPaper('A')
                                // 职业
                                IndivPro('Z')
                                // 自有房产地址是否同居住地址
                                PptyLive('Y')
                            }
                            // 申请人扩展信息,可以全部不传
                            ExtInfo()
                            // 联系人,多联系人循环result
                            RelList {
                                Result()
                            }
                        }
                    }

                }
                //房屋信息
                HouInfo {
                    // 房屋做落地
                    Location(houseInfo?.address)
                    // 建筑面积
                    HouseArea('1')
                    // 建成日期
                    CompDate('1')
                    // 产权类型
                    if (houseInfo?.propertyOwnership == '个人')
                    {
                        PropRight('06')
                    }
                    else if (houseInfo?.propertyOwnership == '公司')
                    {
                        PropRight('05')
                    }
                    else if (houseInfo?.propertyOwnership == '未明')
                    {
                        PropRight('99')
                    }
                    else
                    {
                        PropRight('99')
                    }
                    //房屋用途
                    if (houseInfo?.projectType?.name == '住宅')
                    {
                        HouseKindList('01')
                    }
                    else if (houseInfo?.projectType?.name == '别墅')
                    {
                        HouseKindList('04')
                    }
                    else if (houseInfo?.projectType?.name == '商业')
                    {
                        HouseKindList('03')
                    }
                    else if (houseInfo?.projectType?.name == '商住两用')
                    {
                        HouseKindList('02')
                    }
                    else if (houseInfo?.projectType?.name == '办公')
                    {
                        HouseKindList('05')
                    }
                    else if (houseInfo?.projectType?.name == '其他')
                    {
                        HouseKindList('99')
                    }
                    else
                    {
                        HouseKindList('99')
                    }
                    // 房屋性质
                    if (houseInfo?.assetType == '商品房')
                    {
                        HouseClass('01')
                    }
                    else if (houseInfo?.assetType == '央产房')
                    {
                        HouseClass('11')
                    }
                    else if (houseInfo?.assetType == '经济适用房')
                    {
                        HouseClass('02')
                    }
                    else if (houseInfo?.opportunity?.assetType?.name == '回迁房')
                    {
                        HouseClass('12')
                    }
                    else if (houseInfo?.opportunity?.assetType?.name == '军产房')
                    {
                        HouseClass('05')
                    }
                    else if (houseInfo?.opportunity?.assetType?.name == '其他')
                    {
                        HouseClass('99')
                    }
                    else
                    {
                        HouseClass('99')
                    }
                    // 房屋类型
                    if (houseInfo?.houseType == '独栋别墅')
                    {
                        HouseType('03')
                    }
                    else if (houseInfo?.houseType == '普通住宅')
                    {
                        HouseType('01')
                    }
                    else if (houseInfo?.houseType == '联排别墅')
                    {
                        HouseType('04')
                    }
                    else if (houseInfo?.houseType == '叠拼别墅')
                    {
                        HouseType('05')
                    }
                    else
                    {
                        HouseType('05')
                    }
                    //房屋结构
                    HouseFrameSign('99')
                    // 新/二手
                    if (houseInfo?.newHouse == true)
                    {
                        UsedFlag('1')
                    }
                    else
                    {
                        UsedFlag('0')
                    }
                    //房屋权利证书类型
                    HouseCertKind('01')
                    //房屋权利证书号码
                    HouseCertNo('其他')

                }
                //贷款申请信息
                ApplInfo {
                    //申请书编号
                    ApplCde(com.next.ChannelRecord.findByOpportunity(opportunity).avic_apply_cde)
                    //申请书流水号
                    ApplSeq(com.next.ChannelRecord.findByOpportunity(opportunity).avic_apply_seq)
                    // 身份证类型
                    if (opportunity?.contact?.identityType?.name == "身份证")
                    {
                        IdTyp('20')
                    }
                    else
                    {
                        IdTyp('20')
                    }
                    // 身份证号码
                    IdNo(opportunity?.contact?.idNumber)
                    //客户名称
                    CustName(opportunity.fullName)
                    //贷款用途
                    Purpose('OTH')
                    // 进件渠道,由中航信托提供
                    DocChannel('SYS001')
                    //客户准备结果
                    ComplResult('01')
                    //贷款申请日期
                    ApplyDt(opportunity?.startTime?.format("yyyy-MM-dd"))
                    //首付比例
                    FstPct('0.3')
                    //首付金额
                    FstPay('5000')
                    //贷款金额
                    ApplyAmt(opportunity?.actualLoanAmount)
                    //申批金额
                    ApprvAmt(opportunity?.requestedAmount)
                    // 贷款申请期限
                    ApplyTnr(opportunity?.loanDuration)
                    //贷款申请期限类型
                    if (opportunity?.actualLoanDuration == 0)
                    {
                        ApplyTnrTyp('M')
                    }
                    else
                    {
                        ApplyTnrTyp('M')
                    }
                    // 贷款审批期限
                    ApprvTnr(opportunity?.loanDuration)
                    //贷款审批期限类型
                    if (opportunity?.actualLoanDuration == 0)
                    {
                        ApprvTnrTyp('M')
                    }
                    else
                    {
                        ApprvTnrTyp('M')
                    }
                    // 贷款品种,中航信托提供
                    LoanTyp('ZJXF00001')
                    //贷款还款方式
                    if (opportunity?.principalPaymentMethod?.name == "等额本息")
                    {
                        MtdCde('RP01')
                    }
                    else
                    {
                        MtdCde('RP01')
                    }
                    // 贷款还款间隔
                    if (opportunity?.commissionPaymentMethod?.name == "月月返")
                    {
                        LoanFreq('1M')
                    }
                    else if (opportunity?.commissionPaymentMethod?.name == "一次性返")
                    {
                        switch (opportunity?.period)
                        {
                        case 3: LoanFreq('1Q')
                        case 6: LoanFreq('6M')
                        case 12: LoanFreq('12m')
                        case 24: LoanFreq('2W')
                        }
                    }
                    else
                    {
                        LoanFreq('1M')
                    }
                    // 贷款利率模式
                    MtdMode('RV')
                    // 贷款利率
                    PriceIntRat(opportunity?.dealRate)
                    // 创建日期
                    CrtDt(opportunity?.createdDate)
                    // 贷款类型
                    TypGrp('04')
                    // 担保方式
                    if (opportunity?.mortgagingStatus == false)
                    {
                        GutrOpt('20')
                    }
                    else
                    {
                        GutrOpt('20')
                    }
                    // 创建机构
                    CrtBch('900000007')
                    // 创建机构是否为行内
                    CrtBchInd('N')
                    // 利率调整方式
                    RepcOpt('FIX')
                    // 还款日类型
                    DueDayOpt('2')
                    // 业务表单
                    Form('04')
                }
                // 还款策略
                MtdList {
                    apptTypeList?.each {
                        //def opportunitytemp= it
                        Result {
                            //还款方式
                            if (opportunity?.principalPaymentMethod?.name == "等额本息")
                            {
                                MtdCde('RP01')
                            }
                            else
                            {
                                MtdCde('LM004')
                            }
                            //还款类别
                            MtdTyp('04')
                            // 还款期数
                            LoanInstal(opportunity?.period)
                        }
                    }

                }
                // 账号循环信息
                AcctList {
                    acctList?.each {
                        def acct = it
                        Result {
                            //账号类别
                            ApplAcKind('01')
                            // 账号类型
                            ApplAcTyp('01')
                            // 账号开户银行
                            RpymAcBank(acct?.bankAccount?.bank?.name)
                            // 账户开户机构
                            RpymAcBck(acct?.bankAccount?.bankBranch)
                            // 账户开户名
                            RpymAcNam(acct?.bankAccount?.name)
                            // 账号
                            RpymAcNo(acct?.bankAccount?.numberOfAccount)
                            // 开户人证件类型
                            if (acct?.bankAccount?.certificateType?.name == "身份证")
                            {
                                RpymIdTyp('20')
                            }
                            else
                            {
                                RpymIdTyp('20')
                            }
                            // 开户人证件号码
                            RpymIdNo(acct?.bankAccount?.numberOfCertificate)
                        }
                    }
                }
            }
        }
        println sw1
        def callAvicWs = { String xml2 ->
            String retMsg = null;
            try
            {
                javax.xml.namespace.QName SERVICE_NAME = new javax.xml.namespace.QName("CMISChannelService", "CMISChannelService");

                URL wsdlURL = com.zjx.service.webservice.avic.CMISChannelService.WSDL_LOCATION;

                com.zjx.service.webservice.avic.CMISChannelService ss = new com.zjx.service.webservice.avic.CMISChannelService(wsdlURL, SERVICE_NAME);
                com.zjx.service.webservice.avic.CMISChannelServicePortType port = ss.getCMISChannelServiceHttpPort();

                retMsg = port.input(xml2);
            }
            catch (java.lang.Exception e)
            {
                org.apache.commons.logging.Log.logger.error(e.getMessage(), e);
            }

            return retMsg;
        }
        def reXml1 = callAvicWs(sw1.toString())
        println reXml1
        def resps1 = new XmlParser().parseText(reXml1)
        println resps1.HEAD.ErrorMsg.text()
    }
    /**
     * 5010贷款申请审批反馈信息*/
    def serviceMethod5010 = { reqXml1 ->
        def reqXml = """
<REQ>
   <HEAD>
      <BchCde>900000000</BchCde>
      <MsgId>0000093956</MsgId>
      <MsgNo>5010</MsgNo>
      <MsgRef>0000093956</MsgRef>
      <ReqCode>CMIS</ReqCode>
      <Teller>lixiaole</Teller>
      <WorkData>20160812</WorkData>
      <WorkTime>163504</WorkTime>
   </HEAD>
   <MSG>
      <ApplSeq>754206</ApplSeq>
      <CustName>佰仟测试004</CustName>
      <IdTyp>22</IdTyp>
      <IdNo>佰仟测试004</IdNo>
      <AppConclusion>20</AppConclusion>
      <AppAdvice>20</AppAdvice>
      <CrtUsr>lixiaole</CrtUsr>
      <CrtDt>2016-03-27 00:00:00</CrtDt>
   </MSG>
</REQ>
"""
        def reqxml1 = new XmlParser().parseText(reqXml)
        def sw = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(sw)
        xml.RESP {
            HEAD {
                // 申请渠道码
                ReqCode('WBJF')
                // 报文编号，交易码
                MsgNo('5010')
                //报文标识号
                MsgId("WBJFZJXF" + new Date().format("yyyyMMdd") + "000001")
                //报文参考号,同MsgId
                MsgRef("WBJFZJXF" + new Date().format("yyyyMMdd") + "000001")
                //工作日期
                WorkData(new Date().format("yyyyMMdd"))
                //工作时间
                WorkTime(new Date().format("HHmmss"))
                def channelRecord = com.next.ChannelRecord.findByAvic_apply_seq(reqxml1.MSG.ApplSeq.text())
                println(reqxml1.MSG.AppConclusion.text())
                println(channelRecord.avic_apply_seq)
                println("xiayibu")
                // 错误码ApplSeq的值待定
                if (channelRecord.avic_apply_seq.toDouble() > 0)
                {
                    channelRecord.status = reqxml1.MSG.AppConclusion.text()
                    if (channelRecord.validate())
                    {
                        channelRecord.save flush: true
                    }
                    else
                    {
                        println channelRecord.errors
                        println("cuowu")
                    }
                    println(channelRecord)
                    if (reqxml1.MSG.AppConclusion.text() == '10' || reqxml1.MSG.AppConclusion.text() == '20' || reqxml1.MSG.AppConclusion.text() == '30')
                    {
                        ErrorCode('0000')
                        ErrorMsg('交易成功')
                    }
                    else
                    {
                        ErrorCode('9001')
                        ErrorMsg('数据验证错误')
                    }
                }
                // 错误信息
                if (reqxml1.MSG.ApplSeq.text() == null)
                {
                    ErrorCode('9998')
                    ErrorMsg('无此交易码')
                }
            }
        }

        println sw
    }

    /**
     * 查询还款计划
     * @Author 夏瑞坤
     * @ModifiedDate 2017-7-18
     */
    def searchRepayMentPlan(Opportunity opportunity)
    {
        //3.12查询还款计划 04001012
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddhhmmss")
        Date date = new Date()
        def loanNo = ChannelRecord.findByOpportunityAndInterfaceCode(opportunity, "04002008")?.loanNo
        //私钥
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3UghecpG5js2CP8/sG530km8AjXEuvPIQ9YXJVxwNQJ6JFBrDF8oytCuZFOgOJ1NML79cdR1GS2vX2DTtO0LY0oKllJvyqQSc5KkS9kqNvneiWLVFvCRaMFV/BbgqVTTiFoBKeGe5u8WFNhgBvWFLBqI0Zc6psH+jzZbHxzGvHAgMBAAECgYAbJLwG4Yqp7X2hAsDcEDDppc0fezVZUtbei0viBPyIBlX6o1JmPnTbWrtAJPG4QBEZBrmFzmRAlDgSCvH1nDVqi10L4PDeZYB5c0a3HqrbaNciB9f+dhEHEBz8VXo8sTJLOpxqEgDP4uYtbSln6wvd3uZY9BrDU9d2vkDCUJ8AYQJBANSS3R0SWTH+bH8av6dpWwrawSufCwiAw8VdOU5PnOp9OMAKdpcSEPFgNkMmO6HFhoihdpreNk5z4NlyuY47wzcCQQC+Eq0Bfm3Ed1tcI1EWY5Ag7bLaU59peWAJm8V0OAWzAyO0yOctAoHlF5mT5/R9iYKL7P5og1dkirK6oh8JwAPxAkEAvhbazb1zr6YxhXP5AI7RECLQbN6bMi5bYqlbrnC5BGOYFPsGU0+fgQmlXGTbHG2TQakJc7HUZFFxN2JFFjDQ/wJAUG0wnYg0xERI+TTMc+/PJc/OtUlbE9NTCt3J8EJgTv4OFspH36jG8/xHdOlab+BGyBSRFgI1cYqq1AQTpBmG0QJAb9nyONoND1v3bTUl79A+uT0w4LzWODy5lfbyZK4VEdGrX4vTjUQnmP/5p0+1ZVZHk5wnPqmUq+CFINAZpjc0eA==";
        //签名
        String str = "loanNo=${loanNo}"
        String sign = com.zjx.deal.RSADeal.sign(str, privateKey)
        def searchRepayMentPlan = [:]
        searchRepayMentPlan.put("token", ObtainToken())
        searchRepayMentPlan.put("cusId", "A060000009")
        searchRepayMentPlan.put("tradeCode", "04001012")
        searchRepayMentPlan.put("transDate", sdf.format(date))
        searchRepayMentPlan.put("loanNo", "JJ201711170000005003")
        searchRepayMentPlan.put("sign", sign)

        String params = groovy.json.JsonOutput.toJson(searchRepayMentPlan).toString()

        println "--------------------------------------------------------"
        println params
        println "--------------------------------------------------------"

        try
        {
            def res = sendPost("http://219.143.184.27:10000/ecp/repayMentPlan", params)
            return groovy.json.JsonOutput.toJson(res)
        }
        catch (Exception e)
        {
            e.printStackTrace()
            return ""
        }

        //        println res
        //        if(res)
        //        {
        //            println res["loanNo"]
        //            res["list"].each
        //                    {
        //                        def phaseNum = it["phaseNum"]
        //                        def startDate = it["startDate"]
        //                        def startRateDate = it["startRateDate"]
        //                        def endDate = it["endDate"]
        //                        def phaseAmount = it["phaseAmount"]
        //                        def principal = it["principal"]
        //                        def normalInterest = it["normalInterest"]
        //                        def remainPrincipal = it["remainPrincipal"]
        //                        def payment = it["payment"]
        //                        def compoundInterest = it["compoundInterest"]
        //                        def outRateDate = it["outRateDate"]
        //                        def normalAccInterest = it["normalAccInterest"]
        //                        def trunNormInterest = it["trunNormInterest"]
        //                        def accPayment = it["accPayment"]
        //                        def planPayment = it["planPayment"]
        //                        def normCompAccInterest = it["normCompAccInterest"]
        //                        def normInterestCompSectMeter = it["normInterestCompSectMeter"]
        //                        def paymentAccCompInterest = it["paymentAccCompInterest"]
        //                        def cutMeterPaymentCompInterest = it["cutMeterPaymentCompInterest"]
        //                        def compInterestAccCompInterest = it["compInterestAccCompInterest"]
        //                        def compCompInterest = it["compCompInterest"]
        //                        def recePrincipal = it["recePrincipal"]
        //                        def normInterestRece = it["normInterestRece"]
        //                        def RecePayment = it["RecePayment"]
        //                        def receCompInterest = it["receCompInterest"]
        //                        def minusPrincipal = it["minusPrincipal"]
        //                        def breakNormInterest = it["breakNormInterest"]
        //                        def creditpayment = it["creditpayment"]
        //                        def reduCompInterest = it["reduCompInterest"]
        //                        def needDelayPayment = it["needDelayPayment"]
        //                        def alsoDelayPayment = it["alsoDelayPayment"]
        //                        def reduDelayPayment = it["reduDelayPayment"]
        //                        def loanExecuteRate = it["loanExecuteRate"]
        //                        def paymentExecuteRate = it["paymentExecuteRate"]
        //                        def commission = it["commission"]
        //                        def otherSum = it["otherSum"]
        //                        def status = it["status"]
        //                        def isPayOff = it["isPayOff"]
        //                    }
        //        }
    }

    /**
     * 计算应还金额
     * @Author 夏瑞坤
     * @ModifiedDate 2017-7-18
     */
    def calculateTheAmount(Opportunity opportunity)
    {
        //3.16计算应还款金额 04001016
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddhhmmss")
        java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyyMMdd")
        Date date = new Date()
        def loanNo = ChannelRecord.findByOpportunityAndInterfaceCode(opportunity, "04002008")?.loanNo
        //私钥
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3UghecpG5js2CP8/sG530km8AjXEuvPIQ9YXJVxwNQJ6JFBrDF8oytCuZFOgOJ1NML79cdR1GS2vX2DTtO0LY0oKllJvyqQSc5KkS9kqNvneiWLVFvCRaMFV/BbgqVTTiFoBKeGe5u8WFNhgBvWFLBqI0Zc6psH+jzZbHxzGvHAgMBAAECgYAbJLwG4Yqp7X2hAsDcEDDppc0fezVZUtbei0viBPyIBlX6o1JmPnTbWrtAJPG4QBEZBrmFzmRAlDgSCvH1nDVqi10L4PDeZYB5c0a3HqrbaNciB9f+dhEHEBz8VXo8sTJLOpxqEgDP4uYtbSln6wvd3uZY9BrDU9d2vkDCUJ8AYQJBANSS3R0SWTH+bH8av6dpWwrawSufCwiAw8VdOU5PnOp9OMAKdpcSEPFgNkMmO6HFhoihdpreNk5z4NlyuY47wzcCQQC+Eq0Bfm3Ed1tcI1EWY5Ag7bLaU59peWAJm8V0OAWzAyO0yOctAoHlF5mT5/R9iYKL7P5og1dkirK6oh8JwAPxAkEAvhbazb1zr6YxhXP5AI7RECLQbN6bMi5bYqlbrnC5BGOYFPsGU0+fgQmlXGTbHG2TQakJc7HUZFFxN2JFFjDQ/wJAUG0wnYg0xERI+TTMc+/PJc/OtUlbE9NTCt3J8EJgTv4OFspH36jG8/xHdOlab+BGyBSRFgI1cYqq1AQTpBmG0QJAb9nyONoND1v3bTUl79A+uT0w4LzWODy5lfbyZK4VEdGrX4vTjUQnmP/5p0+1ZVZHk5wnPqmUq+CFINAZpjc0eA==";
        //签名
        //        String sign = com.zjx.deal.RSADeal.sign(loanNo, privateKey)

        def calculateTheAmount = [:]
        calculateTheAmount.put("token", ObtainToken())
        calculateTheAmount.put("cusId", "A060000009")
        calculateTheAmount.put("tradeCode", "04001016")
        calculateTheAmount.put("transDate", sdf.format(date))

        calculateTheAmount.put("loanNo", "JJ201711170000005003")
        calculateTheAmount.put("startDate", sdf1.format(date))
        //还款方式：01：全部还清 02：应还欠款 03：提前还款 04：部分还款 05：归还当期
        calculateTheAmount.put("refundMethod", "01")
        //        calculateTheAmount.put("sign",sign)

        String params = groovy.json.JsonOutput.toJson(calculateTheAmount).toString()

        println "--------------------------------------------------------"
        println params
        println "--------------------------------------------------------"

        try
        {
            def res = sendPost("http://219.143.184.27:10000/ecp/AdvRepayTrial", params)
            println res
            return groovy.json.JsonOutput.toJson(res)
        }
        catch (Exception e)
        {
            e.printStackTrace()
            return ""
        }
    }

    /**
     * 主动还款
     * @Author 夏瑞坤
     * @ModifiedDate 2017-7-18
     */
    def activeRepayment(Opportunity opportunity)
    {
        //3.15主动还款 04001015
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddhhmmss")
        java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("YYYYMMDD")
        Date date = new Date()
        def loanNo = ChannelRecord.findByOpportunityAndInterfaceCode(opportunity, "04002008")?.loanNo
        //私钥
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3UghecpG5js2CP8/sG530km8AjXEuvPIQ9YXJVxwNQJ6JFBrDF8oytCuZFOgOJ1NML79cdR1GS2vX2DTtO0LY0oKllJvyqQSc5KkS9kqNvneiWLVFvCRaMFV/BbgqVTTiFoBKeGe5u8WFNhgBvWFLBqI0Zc6psH+jzZbHxzGvHAgMBAAECgYAbJLwG4Yqp7X2hAsDcEDDppc0fezVZUtbei0viBPyIBlX6o1JmPnTbWrtAJPG4QBEZBrmFzmRAlDgSCvH1nDVqi10L4PDeZYB5c0a3HqrbaNciB9f+dhEHEBz8VXo8sTJLOpxqEgDP4uYtbSln6wvd3uZY9BrDU9d2vkDCUJ8AYQJBANSS3R0SWTH+bH8av6dpWwrawSufCwiAw8VdOU5PnOp9OMAKdpcSEPFgNkMmO6HFhoihdpreNk5z4NlyuY47wzcCQQC+Eq0Bfm3Ed1tcI1EWY5Ag7bLaU59peWAJm8V0OAWzAyO0yOctAoHlF5mT5/R9iYKL7P5og1dkirK6oh8JwAPxAkEAvhbazb1zr6YxhXP5AI7RECLQbN6bMi5bYqlbrnC5BGOYFPsGU0+fgQmlXGTbHG2TQakJc7HUZFFxN2JFFjDQ/wJAUG0wnYg0xERI+TTMc+/PJc/OtUlbE9NTCt3J8EJgTv4OFspH36jG8/xHdOlab+BGyBSRFgI1cYqq1AQTpBmG0QJAb9nyONoND1v3bTUl79A+uT0w4LzWODy5lfbyZK4VEdGrX4vTjUQnmP/5p0+1ZVZHk5wnPqmUq+CFINAZpjc0eA=="
        //签名
        //        String sign = com.zjx.deal.RSADeal.sign(loanNo, privateKey)
        def repaymentAccount = ""
        def repaymentBankName = ""
        def repaymentBankNo = ""
        def bankAccounts = com.next.OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")
        if (bankAccounts)
        {
            bankAccounts.each {

                if (it?.type?.name == "还款账号")
                {
                    repaymentAccount = it?.bankAccount?.numberOfAccount
                    repaymentBankName = it?.bankAccount?.bank?.name
                }
            }
        }
        //银行编码
        if (repaymentBankName == "工商银行")
        {
            repaymentBankNo = "102100099996"
        }
        else if (repaymentBankName == "建设银行")
        {
            repaymentBankNo = "105100000017"
        }
        else if (repaymentBankName == "招商银行")
        {
            repaymentBankNo = "308584000013"
        }
        else if (repaymentBankName == "农业银行")
        {
            repaymentBankNo = "103100000026"
        }
        else if (repaymentBankName == "光大银行")
        {
            repaymentBankNo = "303100000006"
        }
        else if (repaymentBankName == "广发银行")
        {
            repaymentBankNo = "306581000003"
        }
        else if (repaymentBankName == "兴业银行")
        {
            repaymentBankNo = "309391000011"
        }
        else if (repaymentBankName == "上海浦东发展银行")
        {
            repaymentBankNo = "310290000013"
        }
        else if (repaymentBankName == "民生银行")
        {
            repaymentBankNo = "305100000013"
        }
        else if (repaymentBankName == "交通银行")
        {
            repaymentBankNo = "301290000007"
        }
        def activeRepayment = [:]
        activeRepayment.put("token", ObtainToken())
        activeRepayment.put("cusId", "A060000009")
        activeRepayment.put("tradeCode", "04001015")
        activeRepayment.put("transDate", sdf.format(date))

        //借据号
        activeRepayment.put("loanNo", "JJ201711170000005003")
        //流水号
        def seqNo = "ZJX" + sdf1.format(date) + getStringRandom(7)
        activeRepayment.put("seqNo", seqNo)
        //产品代码
        activeRepayment.put("prdNo", "ZJX001")
        //还款账号
        activeRepayment.put("repaymentAccount", repaymentAccount)
        //还款银行编号
        activeRepayment.put("repaymentBankNo", repaymentBankNo)
        //还款金额
        def repaymentAmount = 200.0
        activeRepayment.put("repaymentAmount", repaymentAmount)
        //还款日期
        activeRepayment.put("repaymentDate", sdf1.format(date))
        //还款方式：01：全部还清 02：应还欠款 03：提前还款 04：部分还款 05：归还当期
        activeRepayment.put("repaymentType", "01")
        String source = "loanNo=${loanNo}&seqNo=${seqNo}&prdNo=ZJX001&repaymentAccount=${repaymentAccount}&repaymentBankNo=${repaymentBankNo}&repaymentAmount=${repaymentAmount}"
        String sign = com.zjx.deal.RSADeal.sign(source, privateKey)
        //        activeRepayment.put("sign", sign)

        String params = groovy.json.JsonOutput.toJson(activeRepayment).toString()

        println "--------------------------------------------------------"
        println params
        println "--------------------------------------------------------"

        try
        {
            def res = sendPost("http://219.143.184.27:10000/ecp/activeRepay", params)
            println res
            return groovy.json.JsonOutput.toJson(res)
        }
        catch (Exception e)
        {
            e.printStackTrace()
            return ""
        }
    }

    /**
     * 获取token
     * @Author 夏瑞坤
     * @ModifiedDate 2017-7-18
     */
    def ObtainToken()
    {
        //3.15主动还款 04001015
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddhhmmss")
        Date date = new Date()
        def token = [:]
        token.put("cusId", "A060000009")
        token.put("tradeCode", "04001028")
        token.put("transDate", sdf.format(date))


        String params = groovy.json.JsonOutput.toJson(token).toString()

        println "--------------------------------------------------------"
        println params
        println "--------------------------------------------------------"

        try
        {
            def res = sendPost("http://219.143.184.27:10000/ecp/getToken", params)
            String str = groovy.json.JsonOutput.toJson(res)
            def result = JSON.parse(str)
            println result
            return result["token"]
        }
        catch (Exception e)
        {
            e.printStackTrace()
            return ""
        }
    }

    def sendPost(String urlString, String params1)
    {
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
        }
        catch (java.lang.Exception e)
        {
            e.printStackTrace()
            println e
        }
        return result
    }

    def getStringRandom(def length)
    {

        String val = ""
        Random random = new Random()

        //参数length，表示生成几位随机数
        for (
            int i = 0;
                i < length;
                i++)
        {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"
            if ("char".equalsIgnoreCase(charOrNum))
            {
                val += (char) (random.nextInt(26) + 97)
            }
            else if ("num".equalsIgnoreCase(charOrNum))
            {
                val += String.valueOf(random.nextInt(10))
            }
        }
        return val
    }
}

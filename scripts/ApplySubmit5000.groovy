/**
 * create by 孙建岗 2017-6-28*/
2017 / 7 / 3
修改 { opportunity ->
    println "很好"
    //println com.next.Collateral.findByOpportunity(opportunity)?.district
    //println com.next.Collateral.findByOpportunity(opportunity)?.address
    def apptTypeList = com.next.OpportunityContact.findAllByOpportunity(opportunity)
    def houseInfo = com.next.Collateral.findByOpportunity(opportunity)
    def acctList = com.next.OpportunityBankAccount.findAllByOpportunity(opportunity)
    //println(com.next.ChannelRecord.findByOpportunity(opportunity).applySeq)
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
            ApplSeq(com.next.ChannelRecord.findByOpportunity(opportunity).applySeq)
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
                        //姓名
                        if (opportunityContact?.opportunity.fullName == null)
                        {
                            println "姓名不能为空，请校验。。。"
                        }
                        CustName(opportunityContact?.opportunity.fullName)
                        if (opportunity?.contact?.identityType?.name == null)
                        {
                            println "证件类型不能为空，请校验。。。"
                        }
                        if (opportunity?.contact?.identityType?.name == '身份证')
                        {
                            IdTyp('20')
                        }
                        else
                        {
                            IdTyp('20')
                        }
                        //证件校验
                        if (opportunityContact?.contact?.idNumber == null)
                        {
                            println "证件号码不能为空，请校验。。。"
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
                            if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '北京')
                            {
                                LiveCity('110000')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '上海')
                            {
                                LiveCity('310000')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '成都')
                            {
                                LiveCity('510100')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '青岛')
                            {
                                LiveCity('370200')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '济南')
                            {
                                LiveCity('370100')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '郑州')
                            {
                                LiveCity('410100')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '南京')
                            {
                                LiveCity('320100')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '西安')
                            {
                                LiveCity('610100')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '合肥')
                            {
                                LiveCity('340100')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '武汉')
                            {
                                LiveCity('420100')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '苏州')
                            {
                                LiveCity('320500')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '石家庄')
                            {
                                LiveCity('130100')
                            }
                            else if (com.next.Collateral.findByOpportunity(opportunity)?.city?.name == '厦门')
                            {
                                LiveCity('350200')
                            }
                            else
                            {
                                LiveCity('131000')
                            }
                            //                                                               // 现住房所在区
                            //                                if (com.next.Collateral.findByOpportunity(opportunity)?.district!=null){
                            //                                    LiveArea(com.next.Collateral.findByOpportunity(opportunity)?.district)
                            //                                }
                            //                                else {
                            //                                    LiveArea('131002')
                            //                                }
                            LiveArea('131002')
                            // 现住房所在地址,详细地址
                            if (com.next.Collateral.findByOpportunity(opportunity)?.address != null)
                            {
                                LiveAddr(com.next.Collateral.findByOpportunity(opportunity)?.address)
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
                            if (opportunityContact?.contact?.cellphone == null)
                            {
                                println "手机号不能为空，请校验。。。"
                            }
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
                if (houseInfo?.propertyOwnership == null)
                {
                    println "产权类型不能为空，请校验。。。"
                }
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
                if (houseInfo?.projectType?.name == null)
                {
                    println "房屋用途不能为空，请校验。。。"
                }
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
                ApplCde(com.next.ChannelRecord.findByOpportunity(opportunity).applyCode)
                //申请书流水号
                ApplSeq(com.next.ChannelRecord.findByOpportunity(opportunity).applySeq)
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
                IdNo(com.next.OpportunityContact.findByOpportunity(opportunity)?.contact?.idNumber)
                //IdNo(opportunity?.contact?.idNumber)
                //客户名称
                CustName(opportunity.fullName)
                //贷款用途
                Purpose('OTH')
                // 进件渠道,由中航信托提供
                DocChannel('SYS001')
                //客户准备结果
                ComplResult('01')
                //贷款申请日期
                ApplyDt(opportunity?.createdDate?.format("yyyy-MM-dd"))
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
    def channelRecord = new com.next.ChannelRecord()
    channelRecord.startTime = new Date()
    println(com.next.ChannelRecord.findByOpportunity(opportunity).applyCode)
    println(com.next.ChannelRecord.findByOpportunity(opportunity).applySeq)
    channelRecord.applyCode = com.next.ChannelRecord.findByOpportunity(opportunity).applyCode
    channelRecord.applySeq = com.next.ChannelRecord.findByOpportunity(opportunity).applySeq
    channelRecord.interfaceCode = resps1.HEAD.MsgNo.text()
    channelRecord.status = resps1.HEAD.ErrorMsg.text()
    channelRecord.opportunity = opportunity
    channelRecord.createdBy = com.next.User.findByUsername("zz")
    println(com.next.User.findByUsername("zz"))
    channelRecord.endTime = new Date()
    println(channelRecord.applySeq)
    if (channelRecord.validate())
    {
        println "fangfazhong"
        channelRecord.save flush: true
    }
    else
    {
        println channelRecord.errors
        println("cuowu")
    }
    println resps1.HEAD.ErrorMsg.text()
    return reXml1
}
package com.next

class Opportunity
{
    String serialNumber
    //订单号
    String idNumber
    //借款身份证
    String fullName
    //姓名
    String cellphone
    //借款人电话
    String maritalStatus = "未婚"
    //婚姻状况

    // String spouseFullName
    // //配偶姓名
    // String spouseIdNumber
    // //配偶身份证
    // String spouseCellphone
    // //配偶电话

    // String mortgagorFullName
    // //抵押人姓名
    // String mortgagorIdNumber
    // //抵押人身份证
    // String mortgagorCellphone
    // //抵押人电话
    // String mortgagorMaritalStatus = "未婚"
    // //抵押人婚姻状况
    // String mortgagorSpouseIdNumber
    // //抵押人配偶身份证
    // String mortgagorSpouseCellphone
    // //抵押人配偶电话

    Double maximumAmountOfCredit = 0
    //最大受信额度
    Double actualAmountOfCredit = 0
    //实际受信额度
    Double requestedAmount = 0
    //申请金额 or 申请展期金额
    Double loanAmount = 0
    //授信金额
    Double actualLoanAmount = 0
    //（实际）放款金额
    Double commission = 0
    //佣金
    Integer loanDuration = 0
    //申请贷款期限 or 申请展期期限
    Integer actualLoanDuration = 0
    //实际贷款期限,以月为单位

    /************************************************** Collateral **************************************************/

    AssetType assetType
    City city
    String district
    //区县
    String address
    String floor
    //楼层
    // String    orientation = "南北"
    String orientation
    //朝向
    // Double    area        = 0
    Double area
    //建筑面积

    String building
    // 楼栋信息
    String totalFloor
    // 总楼层
    String roomNumber
    // 户号
    // Integer        numberOfLivingRoom    = 0
    // // 居室
    // Integer        numberOfReceptionRoom = 0
    // // 厅
    HouseType houseType
    SpecialFactors specialFactors

    Date protectionStartTime
    //保护期开始时间
    Date protectionEndTime
    //保护期结束时间

    Double unitPrice = 0
    // 房产单价
    MortgageType mortgageType
    //抵押类型
    MortgageCertificateType mortgageCertificateType
    //抵押凭证类型
    TypeOfFirstMortgage typeOfFirstMortgage
    //首次抵押类型
    Double firstMortgageAmount = 0
    //一抵金额
    Double secondMortgageAmount = 0
    //二抵金额
    String propertySerialNumber
    //房产证编号

    /************************************************** Collateral **************************************************/

    Date createdDate
    Date modifiedDate

    String status = "Pending"

    CauseOfFailure causeOfFailure
    //失败原因
    String descriptionOfFailure
    //失败原因说明

    String memo
    //备注

    OpportunityStage stage
    OpportunityType type
    OpportunitySubtype subtype

    InterestPaymentMethod interestPaymentMethod
    //付息方式
    PrincipalPaymentMethod principalPaymentMethod
    //本金支付方式
    CommissionPaymentMethod commissionPaymentMethod
    //返佣方式
    Double monthlyInterest = 0
    //月息 基础利率 + 代扣（月） + 服务费（月）+ 风险利率（月）
    Double ompositeMonthlyInterest
    //综合月息（不包括意向金） 一资性费用/期数 + 所有月收费项目
    Double commissionRate = 0
    //佣金比率
    Double dealRate = 0
    //成交利率

    Contact lender
    //借款人 lender.level.name

    PropertyAdditionalInformation propertyAdditionalInformation
    //附加的房加信息 不建议使用

    SortedSet contacts
    SortedSet interest

    Territory territory

    Product product

    Integer period = 0
    //期数

    Date startTime
    //借款开始时间
    Date endTime
    //借款结束时间

    Integer interestNumberOfMonths = 0

    ProductAccount productAccount

    Double serviceCharge = 0
    //借款服务费
    String externalId
    //合同号
    Boolean notarizingStatus = false
    //是否公证
    Boolean mortgagingStatus = false
    //是否抵押
    // String interviewResult
    // //面谈结果
    // String chiefAuditorOpinion
    // //主审意见
    Double advancePayment
    //意向金

    LoanApplicationProcessType loanApplicationProcessType
    //报单方式

    Double monthOfAdvancePaymentOfInterest = 0
    //上扣息月份数
    Integer lastPayMonthes = 1
    //下扣息月份数

    Date actualLendingDate
    //实际放款时间
    Date estimatedLendingDate
    //预计放款时间

    Boolean isTest = false

    Date actuaRepaymentDate
    //实际还款日期
    
    Date finalRepaymentDate
    //最终还款日期

    Date dateOfMortgage
    //抵押时间

    Date dateOfNotarization
    //公证时间

    Boolean complianceChecking = false
    //合规检查

    static belongsTo = [contact: Contact, user: User, modifiedBy: User, account: Account, parent: Opportunity]

    // static hasMany = [activities: Activity, contacts: OpportunityContact, attachments: Attachments]

    static hasMany = [contacts: OpportunityContact, attachments: Attachments, collaterals: Collateral, bills: Bills, interest: OpportunityProduct, bankAccounts: OpportunityBankAccount, contracts: OpportunityContract, comments: OpportunityComments, channelRecords: ChannelRecord]

    //contacts: 抵押人／贷款／经纪人
    //attachments: 所有附件
    //collaterals: 所有抵押物
    //bills: 还款计划／帐单
    //interest: 所有费用

    OpportunityAction lastAction

    MortgageReleasingType mortgageReleasingType
    //解押类型

    Date importDate
    //导入时间
    Date externalModifiedDate
    //外部修改时间

    //风控新界面
    String cBCState
    //借款征信评级
    String rejected
    //是否命中大数据拒单项
    String descriptionOfRejection
    //命中大数据说明
    String execution
    //是否涉诉，被执行
    String descriptionOfExecution
    //涉诉，被执行说明
    String loanUsage
    //借款用途
    String otherLoanUsage
    //其他借款用途说明
    String repaymentSource
    //还款来源
    String otherRepaymentSource
    //其他还款来源说明
    String advantages
    //优势
    String disadvantages
    //劣势
    String additionalComment
    //补充说明
    String interviewRequired
    //是否要求面审
    String notarizationMatters
    //公证事项
    String mortgageCertification
    //抵押凭证
    String signedDocuments
    //签署文件
    String mortgageMaterials
    //收押材料
    String otherIssues
    //其他
    String prePaymentAdditionalComment
    //放款前要求补充说明
    String notarizations
    //权利人入库单
    String valuationType
    //估值类型

    Date jqDate
    //结清时间
    Date jqModifiedDate
    //结清修改时间
    String jqStatus
    //结清状态

    String rongShuStatus
    //融数审批状态
    Date  rongShuApprovalDate
    static constraints = {
        serialNumber nullable: true, blank: true
        maritalStatus inList: ["未婚", "已婚", "再婚", "离异", "丧偶"]
        maritalStatus maxSize: 8
        status inList: ["Pending", "Failed", "Completed"]

        jqDate nullable:true,black:true
        jqStatus nullable: true,blank: true,inList: ["未结清","自动结清","手动结清","已结清"],maxSize: 64
        jqModifiedDate nullable:true,black:true
        // orientation inList: ["东", "南", "西", "北", "东西", "南北", "东南", "东北", "西南", "西北"]

        protectionStartTime nullable: true, blank: true
        protectionEndTime nullable: true, blank: true

        startTime nullable: true, blank: true
        endTime nullable: true, blank: true

        // user nullable: true, blank: true
        contact nullable: true, blank: true
        modifiedBy nullable: true, blank: true

        propertySerialNumber nullable: true, blank: true
        idNumber nullable: true, blank: true
        fullName nullable: true, blank: true

        lender nullable: true, blank: true
        cellphone nullable: true, blank: true

        stage nullable: true, blank: true
        firstMortgageAmount nullable: true, blank: true
        secondMortgageAmount nullable: true, blank: true

        unitPrice nullable: true, blank: true
        commission nullable: true, blank: true
        memo nullable: true, blank: true

        building maxSize: 128, nullable: true, blank: true
        totalFloor nullable: true, blank: true
        roomNumber nullable: true, blank: true
        causeOfFailure nullable: true, blank: true

        interestPaymentMethod nullable: true, blank: true
        commissionPaymentMethod nullable: true, blank: true

        mortgageType nullable: true, blank: true
        mortgageCertificateType nullable: true, blank: true
        typeOfFirstMortgage nullable: true, blank: true
        assetType nullable: true, blank: true
        specialFactors nullable: true, blank: true
        orientation nullable: true, blank: true
        area nullable: true, blank: true

        createdDate nullable: true, blank: true
        modifiedDate nullable: true, blank: true

        propertyAdditionalInformation nullable: true, blank: true

        houseType nullable: true, blank: true
        city nullable: true, blank: true
        district nullable: true, blank: true
        address nullable: true, blank: true
        floor nullable: true, blank: true

        territory nullable: true, blank: true

        product nullable: true, blank: true

        principalPaymentMethod nullable: true, blank: true

        productAccount nullable: true, blank: true
        externalId nullable: true, blank: true //, unique: true
        notarizingStatus nullable: true, blank: true
        mortgagingStatus nullable: true, blank: true
        // interviewResult nullable: true, blank: true
        // chiefAuditorOpinion nullable: true, blank: true
        advancePayment nullable: true, blank: true

        type nullable: true, blank: true
        subtype nullable: true, blank: true
        descriptionOfFailure maxSize: 1024, nullable: true, blank: true
        loanApplicationProcessType nullable: true, blank: false
        parent nullable: true, blank: true

        lastAction nullable: true, blank: true

        actualLendingDate nullable: true, blank: true
        estimatedLendingDate nullable: true, blank: true

        actuaRepaymentDate nullable: true, blank: true
        finalRepaymentDate nullable: true, blank: true
        
        mortgageReleasingType nullable: true, blank: true
        ompositeMonthlyInterest nullable: true, blank: true

        importDate nullable: true, blank: true
        externalModifiedDate nullable: true, blank: true
        dateOfMortgage nullable: true, blank: true
        dateOfNotarization nullable: true, blank: true
        valuationType inList:["fast","accuracy"] , nullable: true, blank: true,maxSize: 32
        complianceChecking nullable: true, blank: true

        channelRecords nullable: true, blank: true

        //融数
        rongShuStatus nullable: true, blank: true, maxSize: 16 ,inList: ["通过","未通过"]
        //融数审批状态
        rongShuApprovalDate nullable: true, blank: true
        //风控新界面
        cBCState inList: ["正常", "不良", "恶劣","优质", "次优", "疑难"], nullable: true, blank: true
        rejected inList: ["是", "否"], nullable: true, blank: true
        descriptionOfRejection maxSize: 1024, nullable: true, blank: true
        execution inList: ["是", "否"], nullable: true, blank: true
        descriptionOfExecution maxSize: 1024, nullable: true, blank: true
        loanUsage inList: ["资金周转", "消费", "装修", "企业经营", "支付保证金", "过桥", "归还上家贷款", "进货", "扩大经营", "购买原材料", "支付房租", "其他"], maxSize: 1024, nullable: true, blank: true
        otherLoanUsage maxSize: 1024, nullable: true, blank: true
        repaymentSource inList: ["企业经营收入", "保证金退换", "房屋租金", "银行贷款", "转单", "股票", "基金到期", "朋友还款", "其他"], maxSize: 1024, nullable: true, blank: true
        otherRepaymentSource maxSize: 1024, nullable: true, blank: true
        advantages inList: ["抵押率低", "还款意愿强", "抵押物地理位置优越流通变现强", "抵押物为重点学区房", "借款人增信资料多", "其他"], maxSize: 1024, nullable: true, blank: true
        disadvantages inList: ["房龄老", "抵押人年龄偏大", "抵押率高", "未提供还款来源", "大头小尾", "所属行业高危", "面谈配合度差", "转单业务我司贷款高于上家", "其他"], maxSize: 1024, nullable: true, blank: true
        additionalComment maxSize: 1024, nullable: true, blank: true
        interviewRequired inList: ["是", "否"], nullable: true, blank: true
        notarizationMatters nullable: true, blank: true
        mortgageCertification nullable: true, blank: true
        signedDocuments nullable: true, blank: true
        mortgageMaterials nullable: true, blank: true
        otherIssues maxSize: 1024, nullable: true, blank: true
        prePaymentAdditionalComment maxSize: 1024,nullable: true,blank: true
        notarizations maxSize: 256,nullable: true,blank: true
        lastPayMonthes nullable: true, blank: true
    }

    def beforeInsert()
    {
        if (!user && contact && contact.user)
        {
            user = contact.user
        }

        String charset = (('A'..'Z') + ('0'..'9')).join()
        String s1 = org.apache.commons.lang.RandomStringUtils.random(3, charset)
        String s2 = org.apache.commons.lang.RandomStringUtils.random(3, charset)
        String s3 = org.apache.commons.lang.RandomStringUtils.random(3, charset)
        serialNumber = "${s1}-${s2}-${s3}"

        if (!stage)
        {
            stage = OpportunityStage.findByCode("01")
        }

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"))
        Calendar calendar = Calendar.getInstance()
        createdDate = calendar.getTime()
        modifiedDate = calendar.getTime()

        if (!type)
        {
            type = OpportunityType.findByCode('10')
        }
    }

    def beforeUpdate()
    {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"))
        Calendar calendar = Calendar.getInstance()
        modifiedDate = calendar.getTime()
    }

    def afterUpdate()
    {
        if (product)
        {
            def temp = account
            while (temp)
            {
                productAccount = ProductAccount.findByAccountAndProduct(temp, product)
                if (productAccount)
                {
                    break
                }
                else
                {
                    temp = temp?.parent
                }
            }
        }
        /*if (actualAmountOfCredit)
        {
            if (collaterals)
            {
                collaterals?.each {
                    if (it?.totalPrice > 0)
                    {
                        if (it?.mortgageType?.name in ['一抵', '一抵转单'])
                        {
                            it.loanToValue = actualAmountOfCredit / it?.totalPrice
                            it.loanToValue = it.loanToValue * 100
                        }
                        else if (it?.mortgageType?.name in ['二抵', '二抵转单'])
                        {
                            if (it?.firstMortgageAmount>=0)
                            {
                                it.loanToValue = (actualAmountOfCredit + it?.firstMortgageAmount) / it?.totalPrice
                                it.loanToValue = it.loanToValue * 100
                            }
                        }
                        it.save()
                    }
                }
            }
        }*/
    }

    def afterInsert()
    {
        propertyAdditionalInformation = new PropertyAdditionalInformation()
    }

    static mapping = {
        contacts(lazy: false)
        interest(lazy: false)
    }
}

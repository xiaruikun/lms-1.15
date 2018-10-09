package com.next

class Contact
{
    boolean enabled = true

    /** **************************************************************************************************/

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    String type

    String fullName
    String cellphone
    String maritalStatus = "未婚"
    Boolean cellphoneHasVerified = false
    String verifiedCode

    String password

    City city

    String idNumber

    String bankName
    String bankAccount

    String avatar

    String userCode
    String openId

    String appSessionId

    ContactLevel level

    Date lastLoginTimestamp = new Date()

    String externalId

    // Industry industry
    // //行业
    // String company
    // //公司
    // String companyCode
    // //工商&机构代码

    ContactProfession profession
    //职业
    Country country
    //国籍
    ContactIdentityType identityType
    //身份证件类型

    //规则引擎
    Integer queryTimes
    //12个月内央行征信查询次数(贷款审批)
    Integer queryTimesOther
    //12个月内央行征信查询次数(其它查询)
    Integer continuousOverdue
    //贷款连续逾期
    Integer accumulativeOverdue
    //贷款累计逾期
    Boolean currentOverdue = false
    //贷款当前逾期
    String loanState
    //贷款五级分类
    String loanType
    //非正常分类贷款类型
    Double loanAmount
    //非正常分类贷款金额
    String creditCardStatus
    //贷记卡账户状态
    Double creditCardLimit
    //贷记卡授信额度
    Double guaranteedAmount = 0
    //最大担保金额
    Double currentOverdueAmount = 0
    //当前逾期金额
    String guaranteeState
    //对外担保五级分类

    //风控新界面
    Double income
    //收入
    String propertyOwner
    //是否为抵押人
    String personalProperty
    //个人资产
    String overdueRecentTwoYears
    //2年内逾期次数
    Double totalLoanBalance
    //当前总贷款余额
    String guaranteeStatus
    //对外担保情况
    String guaranteeType
    //对外担保类型
    Double guaranteeBalance
    //对外担保余额(万元)
    String relationOfGuarantor
    //被担保人与借款人关系

    String overdueRecent
    //逾期次数

    static belongsTo = [account: Account, user: User]

    // static hasMany = [messages: Message, certificates: ContactLoginCertificate, loginHistory: ContactLoginHistory]
    //static hasMany = [certificates: ContactLoginCertificate, attachments: Attachments]
    static hasMany = [certificates: ContactLoginCertificate, companies: Company, contactJudgementRecord: ContactJudgementRecord]

    static constraints = {
        fullName blank: true, nullable: true
        cellphone blank: true, nullable: true
        cellphone matches: /^1\d{10}$/
        city blank: true, nullable: true
        maritalStatus inList: ["未婚", "已婚", "再婚", "离异", "丧偶"]
        maritalStatus maxSize: 8
        type blank: true, nullable: true
        type inList: ["Agent", "Client"]
        account blank: true, nullable: true
        idNumber blank: true, nullable: true
        verifiedCode blank: true, nullable: true
        user blank: true, nullable: true
        password blank: true, nullable: true
        bankName blank: true, nullable: true
        bankAccount blank: true, nullable: true
        avatar blank: true, nullable: true
        userCode blank: true, nullable: true
        openId blank: true, nullable: true
        level blank: true, nullable: true
        appSessionId blank: true, nullable: true, size: 0..256

        externalId maxSize: 32, blank: true, nullable: true //, unique: true
        //        company maxSize: 128, nullable: true, blank: true
        //        companyCode maxSize: 128, nullable: true, blank: true

        // industry nullable: true, blank: true
        profession nullable: true, blank: true
        country nullable: true, blank: true
        identityType nullable: true, blank: true
        //规则引擎
        queryTimes nullable: true, blank: true
        queryTimesOther nullable: true, blank: true
        continuousOverdue nullable: true, blank: true
        accumulativeOverdue nullable: true, blank: true
        loanState inList: ["正常", "关注", "次级", "可疑", "损失"]
        loanState nullable: true, blank: true, maxSize: 8
        loanType inList: ["房产抵押", "信用", "其它"]
        loanType nullable: true, blank: true, maxSize: 20
        loanAmount nullable: true, blank: true
        creditCardStatus inList: ["正常", "止付", "冻结", "呆账", "黑户"]
        creditCardStatus nullable: true, blank: true, maxSize: 8
        creditCardLimit nullable: true, blank: true
        guaranteedAmount nullable: true, blank: true
        currentOverdue nullable: true, blank: true
        guaranteeState inList: ["正常", "关注", "次级", "可疑", "损失"]
        guaranteeState nullable: true, blank: true, maxSize: 8
        currentOverdueAmount nullable: true, blank: true

        //风控新界面
        income nullable: true, blank: true
        propertyOwner inList: ["是", "否"], nullable: true, blank: true, maxSize: 32
        personalProperty maxSize: 1024, nullable: true, blank: true
        overdueRecentTwoYears maxSize: 128, nullable: true, blank: true
        totalLoanBalance nullable: true, blank: true
        guaranteeStatus inList: ["有", "无"], nullable: true, blank: true
        guaranteeType inList: ["信用卡担保", "贷款担保"], nullable: true, blank: true, maxSize: 32
        guaranteeBalance nullable: true, blank: true
        relationOfGuarantor nullable: true, blank: true

        overdueRecent maxSize: 128, nullable: true, blank: true
    }

    def beforeInsert()
    {
        if (userCode)
        {
            user = User.findByCode(userCode)
        }
    }

    def beforeUpdate()
    {
        if (userCode)
        {
            user = User.findByCode(userCode)
        }
        modifiedDate = new Date()
    }
}

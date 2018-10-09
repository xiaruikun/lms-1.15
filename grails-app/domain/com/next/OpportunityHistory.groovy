package com.next

class OpportunityHistory
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

    Double maximumAmountOfCredit = 0
    //最大受信额度
    Double actualAmountOfCredit = 0
    //实际受信额度
    Double requestedAmount = 0
    //申请金额
    Double loanAmount = 0
    //授信金额
    Double actualLoanAmount = 0
    //（实际）放款金额
    Double commission = 0
    //佣金
    Integer loanDuration = 0
    //贷款期限,以月为单位
    Integer actualLoanDuration = 0
    //实际贷款期限,以月为单位

    /** **************************************************************************************************/

    AssetType assetType
    City city
    String district
    //区县
    String address
    String floor
    //楼层
    // String orientation = "南北"
    String orientation
    //朝向
    // Double area = 0
    Double area
    //建筑面积

    String building
    // 楼栋信息
    String totalFloor
    // 总楼层
    String roomNumber
    // 户号
    // Integer numberOfLivingRoom = 0
    // 居室
    // Integer numberOfReceptionRoom = 0
    // 厅
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
    //首资抵押类型
    Double firstMortgageAmount = 0
    //一抵金额
    Double secondMortgageAmount = 0
    //二抵金额
    String propertySerialNumber
    //房产证编号

    /** **************************************************************************************************/

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
    //每月利息
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

    Double advancePayment
    //意向金

    LoanApplicationProcessType loanApplicationProcessType
    //报单方式

    Double monthOfAdvancePaymentOfInterest = 0
    //上扣息月份数

    Date actualLendingDate
    //实际放款时间
    Date estimatedLendingDate
    //预计放款时间

    Boolean isTest = false
    Date actuaRepaymentDate
    //实际还款日期

    Date dateOfMortgage
    //抵押时间

    Date dateOfNotarization
    //公证时间

    Boolean complianceChecking = false
    //合规检查

    static belongsTo = [contact: Contact, user: User, modifiedBy: User, account: Account, parent: Opportunity]

    static hasMany = [activities: Activity, contacts: OpportunityContact, attachments: Attachments]

    OpportunityAction lastAction

    MortgageReleasingType mortgageReleasingType
    //解押类型

    Date importDate
    //导入时间
    Date externalModifiedDate
    //外部修改时间

    static constraints =
        {
            serialNumber nullable: true, blank: true
            maritalStatus inList: ["未婚", "已婚", "再婚", "离异", "丧偶"]
            maritalStatus maxSize: 8
            status inList: ["Pending", "Failed", "Completed"]

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

            mortgageReleasingType nullable: true, blank: true
            ompositeMonthlyInterest nullable: true, blank: true

            importDate nullable: true, blank: true
            externalModifiedDate nullable: true, blank: true

            dateOfMortgage nullable: true, blank: true
            dateOfNotarization nullable: true, blank: true

            complianceChecking nullable: true, blank: true
        }
}

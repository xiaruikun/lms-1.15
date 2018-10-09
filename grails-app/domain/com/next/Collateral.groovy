package com.next

class Collateral
{
    String assetType
    String projectName
    //小区
    City city
    String district
    //区县
    String address
    String floor
    //楼层
    String orientation = "南北"
    //朝向
    Double area = 0
    //建筑面积
    String building = 0
    //栋
    String unit
    //单元
    String totalFloor
    //总楼层
    String roomNumber
    //户号
    // Integer numberOfLivingRoom    = 0 //居室
    // Integer numberOfReceptionRoom = 0 //厅
    String houseType
    String specialFactors
    Double unitPrice = 0
    Double totalPrice = 0

    String externalId

    String status

    String reasonOfPriceAdjustment
    Double appliedTotalPrice = 0

    Date requestStartTime
    Date requestEndTime

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    CollateralProjectType projectType
    //立项类型x
    Date buildTime
    //建成时间
    Double loanToValue = 0
    //抵押率
    String propertySerialNumber
    //房产证编号
    TypeOfFirstMortgage typeOfFirstMortgage
    //首次抵押类型
    Double firstMortgageAmount = 0
    //一抵金额
    Double secondMortgageAmount = 0
    //二抵金额
    MortgageType mortgageType
    //抵押类型

    CollateralRegion region

    Double atticArea = 0
    // 楼阁（跃层）面积

    String postcode

    // static hasMany = [prices: Price]

    //产调

    String propertyOwnershipInvestigationStatus = "成功"
    //产调状态
    String propertyOwnership = "个人"
    //产权归属
    Boolean propertySealedup = false
    //是否有查封记录
    Boolean propertySealedupReason = false
    //是否为查封他人房产而提供财产担保导致查封
    Date propertySealedupDate
    //查封日期
    Boolean newHouse = false
    //是否新房
    Date propertyCertificateHoldDate
    //持证时间

    //外访预警
    Boolean seventyYearsElder = false
    //抵押物内是否有70岁以上老人
    Boolean specialPopulation = false
    //抵押物内是否有卧床不起、精神障碍等特殊人群
    Boolean propertyStructure = false
    //抵押物格局是否被破坏
    Integer partitionNumber = 0
    //隔断数目
    Boolean propertyLivingCondition = true
    //抵押物是否具备居住使用条件
    Boolean marksOfFire = false
    //抵押物内有火灾情况
    Boolean topFloorWithWaterLeakage = false
    //抵押物是顶层有漏水痕迹
    Boolean noiseEnvironment = false
    //抵押物噪音较大（临街、临高速、临城铁）
    Boolean tubeShapedApartment = false
    //抵押物为筒子楼
    Boolean housingDemolition = false
    //抵押物有拆迁可能
    Boolean actualUsageIsOffice = false
    //抵押物立项为住宅实际为办公
    Boolean mostOfFloorsAreOffices = false
    //抵押物立项为住宅实际为住宅但整栋楼或多数楼层为办公
    Boolean undisclosedTenant = false
    //抵押物有租户未披露
    Boolean basementOrHighVoltage = false
    //抵押物为半地下、地下室或紧邻高压线
    Boolean dangerousAnimal = false
    //抵押物内有大型动物
    Boolean doubtfulMarriageStatus = false
    //借款人婚姻状况存疑

    //抵押物其它情况

    String businessSophistication
    //商业成熟度
    String traffic
    //交通情况
    String hospital
    //医院
    String schoolDistrictType = "市重点"
    //学区类型
    String kindergarten
    //幼儿园
    String primarySchool
    //小学
    String middleSchool
    //初中
    String highSchool
    //高中
    Integer numberOfBanks
    //银行数量
    Boolean supermarket = true
    //是否有超市
    Boolean farmersMarket = true
    //是否有农贸市场
    String landAgency
    //经纪公司
    String residentialGreen
    //小区绿化
    String parkingSpace
    //停车位
    String buildingAppreance
    //楼体外观
    Integer numberOfElevators
    //电梯数量
    Boolean gymEquipment = true
    //健身器材
    String communitySize
    //社区规模
    String houseUsageStats = "自住"
    //房屋使用状态
    String decoration
    //装修情况

    //其它
    Integer landUsageTerm
    //土地使用年限
    String houseOrigin = "购置"
    //二手房来源
    String tenantType = "无"
    //租户类型
    Community community
    //社区

    User createdBy
    User modifiedBy

    Double fastUnitPrice = 0
    //外访值

    //风控新界面所需字段
    String mortgageStatus
    //审批时抵押状态
    String additionalComment
    //其他补充
    String mortgageProperty
    //抵押物权属

    static belongsTo = [opportunity: Opportunity]

    static constraints = {
        address maxSize: 256, nullable: false, blank: false
        totalFloor nullable: false, blank: false
        // numberOfReceptionRoom nullable: false, blank: false
        // numberOfLivingRoom nullable: false, blank: false
        unit nullable: true, blank: false
        building maxSize: 128, nullable: false, blank: false
        area nullable: false, blank: false
        projectName maxSize: 128
        orientation inList: ["东", "南", "西", "北", "东西", "南北", "东南", "东北", "西南", "西北", '未知']
        orientation maxSize: 8
        houseType inList: ['普通住宅', '独栋', '联排', '双拼', '叠拼', '独栋别墅', '联排别墅', '双拼别墅', '叠拼别墅', '商业', '办公', '公寓', '未知','其他'], maxSize: 32
        specialFactors inList: ['无', '复式', 'LOFT', '跃层', '一层赠送', '临湖', '楼王', '临街', '顶楼带阁楼', '看海', '一层赠送半地下', '未知'], nullable: true, blank: true, maxSize: 32
        assetType inList: ['住宅', '商品房', '央产房', '经济适用房', '按经济适用房管理', '优惠价房改房', '标准价房改房', '回迁房', '军产房', '校产房', '其他', '成本价房改房', '商业办公', '房改房', '未知','公寓','别墅','限价房'], maxSize: 32
        externalId nullable: true, blank: true
        status inList: ['Pending', 'Completed', 'Failed']
        reasonOfPriceAdjustment maxSize: 1024, nullable: true, blank: true
        requestStartTime nullable: true, blank: true
        requestEndTime nullable: true, blank: true
        projectType nullable: true, blank: true
        buildTime nullable: true, blank: true
        propertySerialNumber nullable: true, blank: true
        mortgageType nullable: true, blank: true
        typeOfFirstMortgage nullable: true, blank: true
        firstMortgageAmount nullable: true, blank: true
        secondMortgageAmount nullable: true, blank: true
        region nullable: true, blank: true
        atticArea nullable: true, blank: true
        postcode nullable: true, blank: false, maxSize: 18
        //规则引擎
        propertyOwnershipInvestigationStatus inList: ["成功", "失败", "异常"]
        propertyOwnershipInvestigationStatus nullable: true, blank: true, maxSize: 8
        propertyOwnership inList: ["个人", "公司", "未明"]
        propertyOwnership nullable: true, blank: true, maxSize: 8
        propertySealedup nullable: true, blank: true
        propertySealedupReason nullable: true, blank: true
        propertySealedupDate nullable: true, blank: true
        newHouse nullable: true, blank: true
        propertyCertificateHoldDate nullable: true, blank: true

        landUsageTerm inList: [70, 50, 40]
        landUsageTerm nullable: true, blank: true
        houseOrigin inList: ["购置", "继承", "分割", "赠与", "产权无争议", "全额付款", "更换产证"]
        houseOrigin nullable: true, blank: true, maxSize: 16
        houseUsageStats inList: ["自住", "空置", "出借", "出租"]
        houseUsageStats nullable: true, blank: true, maxSize: 8
        tenantType inList: ["个人", "公司", "无"]
        tenantType nullable: true, blank: true, maxSize: 8
        propertySealedupDate nullable: true, blank: false
        propertyCertificateHoldDate nullable: true, blank: false
        businessSophistication inList: ["成熟商业", "非成熟商业", "无商业"]
        businessSophistication nullable: true, blank: true, maxSize: 16
        traffic inList: ["地铁、公交", "公交", "无交通"]
        traffic nullable: true, blank: true, maxSize: 16
        hospital inList: ["三甲", "二类", "社区", "无"]
        hospital nullable: true, blank: true, maxSize: 8
        schoolDistrictType inList: ["市重点", "区重点", "普通学区", "无"]
        schoolDistrictType nullable: true, blank: true, maxSize: 16
        kindergarten nullable: true, blank: true
        primarySchool nullable: true, blank: true
        middleSchool nullable: true, blank: true
        highSchool nullable: true, blank: true
        landAgency inList: ["三家（含）以上", "三家以下", "无大型中介", "无中介"]
        landAgency nullable: true, blank: true, maxSize: 16
        residentialGreen inList: ["优", "良", "无"]
        residentialGreen nullable: true, blank: true, maxSize: 8
        parkingSpace inList: ["地上、地下", "仅地上", "仅地下"]
        parkingSpace nullable: true, blank: true, maxSize: 16
        buildingAppreance inList: ["外观新", "外观一般", "外观旧"]
        buildingAppreance nullable: true, blank: true, maxSize: 16
        communitySize inList: ["10栋以上", "6-9栋", "4-5栋", "1-3栋"]
        communitySize nullable: true, blank: true, maxSize: 16
        decoration inList: ["豪装", "精装", "简装", "毛坯"]
        decoration nullable: true, blank: true, maxSize: 8
        community nullable: true, blank: true
        numberOfBanks nullable: true, blank: true
        numberOfElevators nullable: true, blank: true

        createdBy nullable: true, blank: true
        modifiedBy nullable: true, blank: true

        fastUnitPrice nullable: true, blank: true

        //风控新界面
        mortgageStatus inList: ["有", "无"]
        mortgageStatus nullable: true, blank: true, maxSize: 8
        additionalComment maxSize: 1024, nullable: true, blank: true
        mortgageProperty maxSize: 1024, nullable: true, blank: true
    }

    String toString()
    {
        "${projectName}${building}${unit}${roomNumber}"
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
        if (unitPrice && area)
        {
            totalPrice = unitPrice * area / 10000
        }

        //backup
        def CollateralAuditTrail = new CollateralAuditTrail()
        CollateralAuditTrail.assetType = assetType
        CollateralAuditTrail.projectName = projectName
        CollateralAuditTrail.city = city
        CollateralAuditTrail.district = district
        CollateralAuditTrail.address = address
        CollateralAuditTrail.floor = floor
        CollateralAuditTrail.orientation = orientation
        CollateralAuditTrail.area = area
        CollateralAuditTrail.building = building
        CollateralAuditTrail.unit = unit
        CollateralAuditTrail.totalFloor = totalFloor
        CollateralAuditTrail.roomNumber = roomNumber
        CollateralAuditTrail.houseType = houseType
        CollateralAuditTrail.specialFactors = specialFactors
        CollateralAuditTrail.unitPrice = unitPrice
        CollateralAuditTrail.totalPrice = totalPrice
        CollateralAuditTrail.externalId = externalId
        CollateralAuditTrail.status = status
        CollateralAuditTrail.reasonOfPriceAdjustment = reasonOfPriceAdjustment
        CollateralAuditTrail.appliedTotalPrice = appliedTotalPrice
        CollateralAuditTrail.requestStartTime = requestStartTime
        CollateralAuditTrail.requestEndTime = requestEndTime
        CollateralAuditTrail.projectType = projectType
        CollateralAuditTrail.buildTime = buildTime
        CollateralAuditTrail.loanToValue = loanToValue
        CollateralAuditTrail.propertySerialNumber = propertySerialNumber
        CollateralAuditTrail.typeOfFirstMortgage = typeOfFirstMortgage
        CollateralAuditTrail.firstMortgageAmount = firstMortgageAmount
        CollateralAuditTrail.secondMortgageAmount = secondMortgageAmount
        CollateralAuditTrail.mortgageType = mortgageType
        CollateralAuditTrail.opportunity = opportunity
        CollateralAuditTrail.region = region
        CollateralAuditTrail.atticArea = atticArea
        CollateralAuditTrail.postcode = postcode
        CollateralAuditTrail.propertyOwnershipInvestigationStatus = propertyOwnershipInvestigationStatus
        CollateralAuditTrail.propertyOwnership = propertyOwnership
        CollateralAuditTrail.propertySealedup = propertySealedup
        CollateralAuditTrail.propertySealedupReason = propertySealedupReason
        CollateralAuditTrail.propertySealedupDate = propertySealedupDate
        CollateralAuditTrail.newHouse = newHouse
        CollateralAuditTrail.propertyCertificateHoldDate = propertyCertificateHoldDate
        CollateralAuditTrail.seventyYearsElder = seventyYearsElder
        //抵押物内是否有70岁以上老人
        CollateralAuditTrail.specialPopulation = specialPopulation
        //抵押物内是否有卧床不起、精神障碍等特殊人群
        CollateralAuditTrail.propertyStructure = propertyStructure
        //抵押物格局是否被破坏
        CollateralAuditTrail.partitionNumber = partitionNumber
        //隔断数目
        CollateralAuditTrail.propertyLivingCondition = propertyLivingCondition
        //抵押物是否具备居住使用条件
        CollateralAuditTrail.marksOfFire = marksOfFire
        //抵押物内有火灾情况
        CollateralAuditTrail.topFloorWithWaterLeakage = topFloorWithWaterLeakage
        //抵押物是顶层有漏水痕迹
        CollateralAuditTrail.noiseEnvironment = noiseEnvironment
        //抵押物噪音较大（临街、临高速、临城铁）
        CollateralAuditTrail.tubeShapedApartment = tubeShapedApartment
        //抵押物为筒子楼
        CollateralAuditTrail.housingDemolition = housingDemolition
        //抵押物有拆迁可能
        CollateralAuditTrail.actualUsageIsOffice = actualUsageIsOffice
        //抵押物立项为住宅实际为办公
        CollateralAuditTrail.mostOfFloorsAreOffices = mostOfFloorsAreOffices
        //抵押物立项为住宅实际为住宅但整栋楼或多数楼层为办公
        CollateralAuditTrail.undisclosedTenant = undisclosedTenant
        //抵押物有租户未披露
        CollateralAuditTrail.basementOrHighVoltage = basementOrHighVoltage
        //抵押物为半地下、地下室或紧邻高压线
        CollateralAuditTrail.dangerousAnimal = dangerousAnimal
        //抵押物内有大型动物
        CollateralAuditTrail.doubtfulMarriageStatus = doubtfulMarriageStatus
        //借款人婚姻状况存疑

        //抵押物其它情况

        CollateralAuditTrail.businessSophistication = businessSophistication
        //商业成熟度
        CollateralAuditTrail.traffic = traffic
        //交通情况
        CollateralAuditTrail.hospital = hospital
        //医院
        CollateralAuditTrail.schoolDistrictType = schoolDistrictType
        //学区类型
        CollateralAuditTrail.kindergarten = kindergarten
        //幼儿园
        CollateralAuditTrail.primarySchool = primarySchool
        //小学
        CollateralAuditTrail.middleSchool = middleSchool
        //初中
        CollateralAuditTrail.highSchool = highSchool
        //高中
        CollateralAuditTrail.numberOfBanks = numberOfBanks
        //银行数量
        CollateralAuditTrail.supermarket = supermarket
        //是否有超市
        CollateralAuditTrail.farmersMarket = farmersMarket
        //是否有农贸市场
        CollateralAuditTrail.landAgency = landAgency
        //经纪公司
        CollateralAuditTrail.residentialGreen = residentialGreen
        //小区绿化
        CollateralAuditTrail.parkingSpace = parkingSpace
        //停车位
        CollateralAuditTrail.buildingAppreance = buildingAppreance
        //楼体外观
        CollateralAuditTrail.numberOfElevators = numberOfElevators
        //电梯数量
        CollateralAuditTrail.gymEquipment = gymEquipment
        //健身器材
        CollateralAuditTrail.communitySize = communitySize
        //社区规模
        CollateralAuditTrail.houseUsageStats = houseUsageStats
        //房屋使用状态
        CollateralAuditTrail.decoration = decoration
        //装修情况
        CollateralAuditTrail.landUsageTerm = landUsageTerm
        //土地使用年限
        CollateralAuditTrail.houseOrigin = houseOrigin
        //二手房来源
        CollateralAuditTrail.tenantType = tenantType

        CollateralAuditTrail.community = community
        CollateralAuditTrail.parent = this
        CollateralAuditTrail.createdBy = modifiedBy
        CollateralAuditTrail.save()
    }

    def afterInsert()
    {
        Double totalLoanAmount = 0
        Double totalArea = 0
        Double totalFirstMortgageAmount = 0
        Double totalSecondMortgageAmount = 0
        def collaterals = Collateral.findAllByOpportunity(opportunity)
        collaterals?.each {
            if (it?.unitPrice && it?.area)
            {
                totalLoanAmount += it?.unitPrice * it?.area
            }

            if (it?.area)
            {
                totalArea += it?.area
            }

            if (it?.firstMortgageAmount)
            {
                totalFirstMortgageAmount += it?.firstMortgageAmount
            }
            if (it?.secondMortgageAmount)
            {
                totalSecondMortgageAmount += it?.secondMortgageAmount
            }
        }

        if (totalArea > 0)
        {
            opportunity.unitPrice = totalLoanAmount / totalArea
        }
        opportunity.loanAmount = totalLoanAmount / 10000
        opportunity.firstMortgageAmount = totalFirstMortgageAmount
        opportunity.secondMortgageAmount = totalSecondMortgageAmount

        opportunity.save()
    }

    def afterUpdate()
    {
        Double totalLoanAmount = 0
        Double totalArea = 0
        Double totalFirstMortgageAmount = 0
        Double totalSecondMortgageAmount = 0
        def collaterals = Collateral.findAllByOpportunity(opportunity)
        collaterals?.each {
            if (it?.unitPrice && it?.area)
            {
                totalLoanAmount += it?.unitPrice * it?.area
            }

            if (it?.area)
            {
                totalArea += it?.area
            }

            if (it?.firstMortgageAmount)
            {
                totalFirstMortgageAmount += it?.firstMortgageAmount
            }
            if (it?.secondMortgageAmount)
            {
                totalSecondMortgageAmount += it?.secondMortgageAmount
            }
            /*if (it?.totalPrice > 0)
            {
                if (it?.mortgageType?.name in ['一抵', '一抵转单'])
                {
                    it.loanToValue = opportunity.actualAmountOfCredit / it?.totalPrice
                    it.loanToValue = it.loanToValue * 100
                }
                else if (it?.mortgageType?.name in ['二抵', '二抵转单'])
                {
                    if (it?.firstMortgageAmount>=0)
                    {
                        it.loanToValue = (opportunity.actualAmountOfCredit + it?.firstMortgageAmount) / it?.totalPrice
                        it.loanToValue = it.loanToValue * 100
                    }
                }
                it.save()
            }*/
        }

        if (totalArea > 0)
        {
            opportunity.unitPrice = totalLoanAmount / totalArea
        }
        opportunity.loanAmount = totalLoanAmount / 10000
        opportunity.firstMortgageAmount = totalFirstMortgageAmount
        opportunity.secondMortgageAmount = totalSecondMortgageAmount

        opportunity.save()
    }
}

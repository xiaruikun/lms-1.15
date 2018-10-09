package com.next

class Leads
{
    Double loanAmount = 0
    Double unitPrice = 0

    /** **************************************************************************************************/

    String projectName
    City city
    String district
    String address
    String floor
    String orientation = "南北"
    Double area = 0
    String building = "0"
    Integer unit = 0
    String totalFloor
    String roomNumber
    Integer numberOfLivingRoom = 0
    Integer numberOfReceptionRoom = 0
    HouseType houseType
    SpecialFactors specialFactors
    AssetType assetType
    Double appliedTotalPrice = 0

    /** **************************************************************************************************/

    String openId

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static constraints = {
        projectName nullable: true, blank: true
        city nullable: true, blank: true
        district nullable: true, blank: true
        floor nullable: true, blank: true
        roomNumber nullable: true, blank: true
        address nullable: true, blank: true
        totalFloor nullable: true, blank: true
        unit nullable: true, blank: true
        building nullable: true, blank: true
        area nullable: true, blank: true
        orientation inList: ["东", "南", "西", "北", "东西", "南北", "东南", "东北", "西南", "西北"]
        orientation nullable: true, blank: true
        numberOfLivingRoom nullable: true, blank: true
        numberOfReceptionRoom nullable: true, blank: true
        houseType nullable: true, blank: true
        specialFactors nullable: true, blank: true
        loanAmount nullable: true, blank: true
        unitPrice nullable: true, blank: true
        assetType nullable: true, blank: true
        appliedTotalPrice nullable: true, blank: true
    }
}
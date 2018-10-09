package com.next

class PropertyAdditionalInformation
{
    String decoration = '毛坯'
    Integer numberOfTenant = 0
    Integer numberOfElderlyPeople = 0
    Integer numberOfSegments = 0

    static constraints = {
        decoration inList: ['豪华装修', '精装修', '一般装修', '简装', '毛坯']
    }
}

package com.next

class District
{
    String code
    String name

    Integer daysOfOtherRights = 0

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [city: City]

    static hasMany = [addresses: Address, community: Community]

    static constraints = {
        code maxSize: 16, nullable: true
        name maxSize: 32
        daysOfOtherRights nullable: true, blank: true
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }

    String toString()
    {
        name
    }
}

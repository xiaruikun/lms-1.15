package com.next

class Address
{
    String name

    String address
    String trainStations
    String busStations
    String tellphone
    String openingHours

    AddressType type

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [district: District]

    static constraints = {
        trainStations nullable: true, blank: true
        busStations nullable: true, blank: true
        openingHours nullable: true, blank: true
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

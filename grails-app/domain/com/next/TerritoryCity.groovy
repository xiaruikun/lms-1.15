package com.next

class TerritoryCity
{
    Date startTime
    Date endTime

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    Territory territory
    City city

    static belongsTo = [territory: Territory]

    static constraints = {
        city unique: ['territory']
    }

    static mapping = {
        // id composite: ['territory', 'city']
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}

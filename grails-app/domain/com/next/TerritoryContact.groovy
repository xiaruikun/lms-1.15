package com.next

class TerritoryContact
{
    Date startTime
    Date endTime

    Date createdDate = new Date()
    Date modifiedDate = new Date()
    Territory territory
    Contact contact

    static belongsTo = [territory: Territory]

    static constraints = {
        contact unique: ['territory']
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }

    static mapping =
        {
            // id composite: ['territory', 'contact']
        }
}

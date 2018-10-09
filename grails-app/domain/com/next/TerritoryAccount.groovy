package com.next

class TerritoryAccount
{
    Date startTime
    Date endTime

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    Territory territory
    Account account

    static belongsTo = [territory: Territory]

    static mapping = {
        // id composite: ['territory', 'account']
    }

    static constraints = {
        account unique: ['territory']
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}

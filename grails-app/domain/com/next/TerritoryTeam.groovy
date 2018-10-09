package com.next

class TerritoryTeam
{
    Date startTime
    Date endTime
    User user

    OpportunityLayout opportunityLayout

    static belongsTo = [territory: Territory]

    //    static hasMany = [notifications: TerritoryTeamNotification, roles: TerritoryTeamRole]

    static constraints = {
        user unique: ['territory']
        opportunityLayout nullable: true, blank: true
    }

    static mapping = {
        // id composite: ['territory', 'user']
    }

    def beforeUpdate()
    {
        // modifiedDate = new Date()
    }
}

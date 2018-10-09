package com.next

class TerritoryRole
{
    User user
    TeamRole teamRole
    OpportunityStage stage

    static belongsTo = [territory: Territory]

    static constraints = {
        user unique: ['territory', 'teamRole', 'stage']
    }

    static mapping = {
        // id composite: ['territory', 'user']
    }
}

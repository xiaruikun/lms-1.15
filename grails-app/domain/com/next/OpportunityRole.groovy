package com.next

class OpportunityRole
{
    TeamRole teamRole
    User user
    OpportunityStage stage
    OpportunityLayout opportunityLayout

    static belongsTo = [opportunity: Opportunity]

    static constraints = {
        user unique: ['opportunity', 'teamRole', 'stage']
        opportunityLayout nullable: true, blank: true
    }

    static mapping =
        {
            // id composite: ['opportunity', 'user', 'stage']
        }
}

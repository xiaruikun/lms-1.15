package com.next

class OpportunityTeam
{
    User user

    OpportunityLayout opportunityLayout

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [opportunity: Opportunity]

    static mapping = {
        cache true
        // id composite: ['opportunity', 'user']
    }

    static constraints = {
        user unique: ['opportunity']
        opportunityLayout nullable: true, blank: true
    }
}

package com.next

class TerritoryNotification
{
    OpportunityStage stage
    User user

    MessageTemplate messageTemplate

    Boolean toManager = false

    static belongsTo = [territory: Territory]

    static constraints = {
        messageTemplate unique: ['territory', 'user', 'stage']
        toManager nullable: true
    }

    static mapping = {
        // id composite: ['territory', 'user', 'stage']
    }
}

package com.next

class OpportunityNotification
{
    OpportunityStage stage
    User user

    MessageTemplate messageTemplate

    Boolean toManager = false
    String cellphone

    static belongsTo = [opportunity: Opportunity]

    static constraints = {
        // messageTemplate unique: ['opportunity', 'user', 'stage']
        toManager nullable: true
        user nullable: true, blank: true
        cellphone maxSize: 128, nullable: true, blank: true
    }

    static mapping =
        {
            // id composite: ['opportunity', 'user', 'stage']
        }
}

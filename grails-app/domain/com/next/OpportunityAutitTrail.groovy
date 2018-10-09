package com.next

class OpportunityAutitTrail
{
    OpportunityStage stage
    Date createdDate = new Date()
    String log

    static belongsTo = [opportunity: Opportunity]

    static constraints = {
        log maxSize: 2048
    }
}

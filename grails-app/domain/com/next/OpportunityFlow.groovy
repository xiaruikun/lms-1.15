package com.next

class OpportunityFlow
{
    Integer executionSequence
    OpportunityStage stage
    Boolean canReject = true
    Date startTime
    Date endTime
    String comments
    OpportunityLayout opportunityLayout
    Document document
    Boolean processed = false

    static belongsTo = [opportunity: Opportunity]

    static hasMany = [conditions: OpportunityFlowCondition, nextStages: OpportunityFlowNextStage, events: OpportunityEvent]

    static constraints = {
        //        executionSequence unique: ['territory', 'stage'], min: 1, max: 20
        executionSequence unique: ['opportunity'], min: 1, max: 10000
        stage unique: ['opportunity']
        startTime nullable: true, blank: true
        endTime nullable: true, blank: true
        comments blank: true, nullable: true
        comments type: "text"
        opportunityLayout nullable: true, blank: true
        document nullable: true, blank: true
    }

    static mappedBy = [nextStages: 'flow', conditions: 'flow']

    static mapping = {
        // id composite: ['opportunity', 'stage']
    }
}

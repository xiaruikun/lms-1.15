package com.next

class TerritoryFlow
{
    Integer executionSequence
    OpportunityStage stage
    Territory territory
    Boolean canReject = true

    OpportunityLayout opportunityLayout

    static belongsTo = [territory: Territory]

    static hasMany = [conditions: TerritoryFlowCondition, nextStages: TerritoryFlowNextStage]

    static constraints = {
        executionSequence unique: ['territory'], min: 1, max: 10000
        stage unique: ['territory']
        opportunityLayout nullable: true, blank: true
    }

    static mappedBy = [nextStages: 'flow']

    static mapping = {
        // id composite: ['territory', 'stage']
    }
}

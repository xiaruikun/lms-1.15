package com.next

class OpportunityWorkflowStage
{
    Integer executionSequence
    OpportunityStage stage
    Boolean canReject = true
    OpportunityLayout opportunityLayout
    Document document

    static belongsTo = [workflow: OpportunityWorkflow]

    static hasMany = [conditions: OpportunityWorkflowStageCondition, nextStages: OpportunityWorkflowStageNextStage, events: OpportunityWorkflowEvent]

    static constraints = {
        executionSequence unique: ['workflow'], min: 1, max: 10000
        stage unique: ['workflow']
        opportunityLayout nullable: true, blank: true
        document nullable: true, blank: true
    }

    static mappedBy = [nextStages: 'stage', conditions: 'stage']

    static mapping = {
        // id composite: ['territory', 'stage']
    }
}

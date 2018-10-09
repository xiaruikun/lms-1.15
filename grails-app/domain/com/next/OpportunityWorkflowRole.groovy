package com.next

class OpportunityWorkflowRole
{
    User user
    TeamRole teamRole
    OpportunityStage stage
    Document document
    OpportunityLayout opportunityLayout

    static belongsTo = [workflow: OpportunityWorkflow]

    static constraints = {
        user unique: ['teamRole', 'workflow', 'stage']
        document nullable: true, blank: true
        opportunityLayout nullable: true, blank: true
    }

    static mapping = {
        // id composite: ['territory', 'user']
    }
}

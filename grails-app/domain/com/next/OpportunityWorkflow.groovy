package com.next

class OpportunityWorkflow
{
    String name
    Boolean active = false
    OpportunityType opportunityType

    // static belongsTo = [stage: OpportunityWorkflowStage]

    static constraints = {
        name maxSize: 64, unique: true, nullable: false
    }
}

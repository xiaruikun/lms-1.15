package com.next

class OpportunityWorkflowStageNextStage
{
    OpportunityWorkflowStage nextStage

    static belongsTo = [stage: OpportunityWorkflowStage]

    static constraints = {
        nextStage unique: ['stage']
    }
}

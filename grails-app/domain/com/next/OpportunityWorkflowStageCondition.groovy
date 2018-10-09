package com.next

class OpportunityWorkflowStageCondition
{
    String condition
    // condition 'opportunity.area > 30'
    String message
    String log

    Integer executeSequence = 0

    Component component

    OpportunityWorkflowStage nextStage

    static belongsTo = [stage: OpportunityWorkflowStage]

    static constraints = {
        condition type: "text", unique: ['stage'], blank: true, nullable: true
        log maxSize: 2048, nullable: true, blank: true
        message maxSize: 256, blank: true, nullable: true
        component nullable: true, blank: true
        nextStage nullable: true, blank: true
    }

    static mapping = {
        condition column: '`check_condition`'
    }
}

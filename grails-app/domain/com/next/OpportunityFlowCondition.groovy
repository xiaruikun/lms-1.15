package com.next

class OpportunityFlowCondition
{
    String condition
    String message
    String log

    Integer executeSequence = 0

    Component component

    OpportunityFlow nextStage

    static belongsTo = [flow: OpportunityFlow]

    static constraints = {
        condition type: "text", unique: ['flow'], blank: true, nullable: true
        log maxSize: 2048, nullable: true, blank: true
        message maxSize: 2048, blank: true, nullable: true
        component nullable: true, blank: true
        nextStage nullable: true, blank: true
    }

    static mapping = {
        condition column: '`check_condition`'
    }
}

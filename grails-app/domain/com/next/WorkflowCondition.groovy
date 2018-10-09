package com.next

class WorkflowCondition
{
    Integer executeSequence = 0
    String message
    String log

    Component component

    WorkflowStage nextStage

    static belongsTo = [stage: WorkflowStage]

    static constraints = {
        log maxSize: 2048, nullable: true, blank: true
        component nullable: true, blank: true
        nextStage nullable: true, blank: true
    }

    static mapping = {}
}

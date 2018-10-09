package com.next

class WorkflowInstanceCondition
{

    Integer executeSequence = 0
    String message
    String log

    Component component

    WorkflowInstanceStage nextStage

    static belongsTo = [stage: WorkflowInstanceStage]

    static constraints = {
        log maxSize: 2048, nullable: true, blank: true
        component nullable: true, blank: true
        nextStage nullable: true, blank: true
    }

    static mapping = {}
}

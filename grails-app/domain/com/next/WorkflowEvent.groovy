package com.next

class WorkflowEvent
{
    Boolean isSynchronous = true
    Integer executeSequence = 0
    String log
    Date startTime
    Date endTime

    Component component

    static belongsTo = [stage: WorkflowStage]

    static constraints = {
        log type: "text", nullable: true, blank: true
        startTime nullable: true, blank: true
        endTime nullable: true, blank: true
        component nullable: true, blank: true
    }

    static mapping = {
        // script type: "text"
    }
}

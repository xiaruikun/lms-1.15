package com.next

class OpportunityWorkflowEvent
{
    String name
    Boolean isSynchronous = true
    Integer executeSequence = 0
    String script
    String log
    Date startTime
    Date endTime
    Document document

    Component component

    static belongsTo = [stage: OpportunityWorkflowStage]

    static constraints = {
        // name maxSize: 32, unique: ['']
        name maxSize: 32, unique: ['stage']
        script type: "text", nullable: true, blank: true
        log type: "text", nullable: true, blank: true
        startTime nullable: true, blank: true
        endTime nullable: true, blank: true
        document nullable: true, blank: true
        component nullable: true, blank: true
    }

    static mapping = {
        // script type: "text"
    }
}

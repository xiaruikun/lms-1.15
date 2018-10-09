package com.next

class OpportunityEvent
{
    String name
    Boolean isSynchronous = true
    Integer executeSequence = 0
    String script
    String log
    Date startTime
    Date endTime

    Component component

    static belongsTo = [stage: OpportunityFlow]

    static constraints = {
        // name maxSize: 32, unique: ['']
        name maxSize: 32, unique: ['stage']
        script type: "text", nullable: true, blank: true
        log type: "text", nullable: true, blank: true
        startTime nullable: true, blank: true
        endTime nullable: true, blank: true
        component nullable: true, blank: true
    }

    static mapping = {
        // script type: "text"
    }
}

package com.next

class WorkflowStage
{
    Integer executionSequence
    String name
    Boolean canReject = true

    static belongsTo = [workflow: Workflow]

    static hasMany = [conditions: WorkflowCondition, events: WorkflowEvent]

    static constraints = {
        executionSequence unique: ['workflow'], min: 1, max: 10000
        name unique: ['workflow']
    }

    static mappedBy = [conditions: 'stage']

    static mapping = {
        // id composite: ['territory', 'stage']
    }
}

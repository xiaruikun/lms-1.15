package com.next

class WorkflowInstanceStage
{
    Integer executionSequence
    String name
    Boolean canReject = true
    WorkflowInstance instance

    //static belongsTo = [instance: WorkflowInstance]

    static hasMany = [conditions: WorkflowInstanceCondition, events: WorkflowInstanceEvent]

    static constraints = {
        executionSequence unique: ['instance'], min: 1, max: 10000
        name unique: ['instance']
    }

    static mappedBy = [conditions: 'stage']
}

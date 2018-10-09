package com.next

class WorkflowUser
{
    Position position
    WorkflowAuthority authority
    static belongsTo = [stage: WorkflowStage]

    static constraints = {
        // user unique: ['stage']
    }

    static mapping = {
        // id composite: ['territory', 'user']
    }
}

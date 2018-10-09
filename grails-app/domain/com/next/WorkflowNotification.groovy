package com.next

class WorkflowNotification
{
    Position position

    MessageTemplate messageTemplate

    Boolean toManager = false
    String cellphone

    static belongsTo = [stage: WorkflowStage]

    static constraints = {
        // messageTemplate unique: ['user', 'workflow', 'stage']
    }

    static mapping = {
        // id composite: ['territory', 'user', 'stage']
        user nullable: true, blank: true
        cellphone maxSize: 128, nullable: true, blank: true
    }
}

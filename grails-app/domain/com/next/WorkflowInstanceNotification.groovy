package com.next

class WorkflowInstanceNotification
{
    User user

    Boolean toManager = false

    MessageTemplate messageTemplate
    String cellphone

    static belongsTo = [stage: WorkflowInstanceStage]

    static constraints = {
        // messageTemplate unique: ['user', 'workflow', 'stage']
        user nullable: true, blank: true
        cellphone maxSize: 128, nullable: true, blank: true
    }
}

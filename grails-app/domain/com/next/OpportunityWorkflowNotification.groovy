package com.next

class OpportunityWorkflowNotification
{
    User user
    Boolean toManager = false

    MessageTemplate messageTemplate
    OpportunityStage stage

    String cellphone

    static belongsTo = [workflow: OpportunityWorkflow]

    static constraints = {
        // messageTemplate unique: ['user', 'workflow', 'stage']
        toManager nullable: true
        user nullable: true, blank: true
        cellphone maxSize: 128, nullable: true, blank: true
    }

    static mapping = {
        // id composite: ['territory', 'user', 'stage']
    }
}

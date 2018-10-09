package com.next

class WorkflowInstance
{
    String name

    Opportunity opportunity

    Workflow workflow

    WorkflowInstanceStage stage

    WorkflowInstanceStatus status

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static constraints = {
        name maxSize: 64, unique: true, nullable: false
        opportunity nullable: true, blank: true

        stage nullable: true, blank: false
        status nullable: true, blank: false
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}

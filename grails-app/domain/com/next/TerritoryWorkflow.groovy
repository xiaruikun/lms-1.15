package com.next

// Ralf

class TerritoryWorkflow
{
    Territory territory
    Workflow workflow

    Date createdDate = new Date()

    static constraints = {
        workflow unique: ['territory']
    }
}

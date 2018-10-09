package com.next

class TerritoryOpportunityWorkflow
{
    Territory territory
    OpportunityWorkflow workflow

    static constraints = {
        workflow unique: ['territory']
    }
}

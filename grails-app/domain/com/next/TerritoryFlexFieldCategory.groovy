package com.next

class TerritoryFlexFieldCategory
{
    static belongsTo = [flexFieldCategory: FlexFieldCategory, territory: Territory, opportunityType: OpportunityType]

    static constraints = {
        flexFieldCategory unique: ['territory']
        opportunityType nullable: true, blank: false
    }
}

package com.next

class OpportunityFlexFieldCategory
{
    SortedSet fields
    static belongsTo = [opportunity: Opportunity, flexFieldCategory: FlexFieldCategory]
    static hasMany = [fields: OpportunityFlexField]
    static constraints = {}

    static mapping = {
        fields(lazy: false)
    }
}

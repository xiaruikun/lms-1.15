package com.next

class TerritoryFlowCondition
{
    String condition
    // condition 'opportunity.area > 30'
    String message

    static belongsTo = [flow: TerritoryFlow]

    static constraints = {
        condition type: "text", unique: ['flow']
        message maxSize: 2048, blank: true, nullable: true
    }

    static mapping = {
        condition column: '`check_condition`'
    }
}

package com.next

class OpportunityStage
{
    String code
    String name

    // String nextCode

    // Boolean confirmed = true
    // Boolean rejected = true
    // Boolean cancel = false

    // static hasMany = [assignment: OpportunityAssignment]

    OpportunityStageCategory category

    String description

    Boolean active = true

    static belongsTo = [type: OpportunityType]

    static constraints = {
        code unique: true
        code size: 2..4

        name unique: true
        name size: 2..16

        description maxSize: 32, nullable: true

        type nullable: true, blank: false
        // nextCode size: 2..2, nullable: true, blank: true
        category nullable: true, blank: false
    }

    String toString()
    {
        if (description)
        {
            description
        }
        else
        {
            name
        }
    }
}

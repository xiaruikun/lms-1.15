package com.next

class OpportunityType
{
    String code
    String name

    // String nextCode

    // Boolean confirmed = true
    // Boolean rejected = true
    // Boolean cancel = false

    // static hasMany = [assignment: OpportunityAssignment]

    static hasMany = [stages: OpportunityStage, subtypes: OpportunitySubtype]

    static constraints = {
        code unique: true
        code size: 2..2

        name unique: true
        name size: 2..16

        // nextCode size: 2..2, nullable: true, blank: true
    }

    String toString()
    {
        "${code} ${name}"
    }
}

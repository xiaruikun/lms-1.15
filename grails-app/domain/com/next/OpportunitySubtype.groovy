package com.next

class OpportunitySubtype
{
    String name

    static belongsTo = [type: OpportunityType]

    static constraints = {
        name unique: ['type']
        name size: 2..32
    }

    String toString()
    {
        name
    }
}

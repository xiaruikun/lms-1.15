package com.next

class OpportunityStageCategory
{
    String name

    Boolean active = true

    static constraints = {
        name maxSize: 32, unqiue: true
    }
}

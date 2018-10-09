package com.next

class OpportunityLayout
{
    String name
    String description

    Boolean active = true

    static constraints = {
        name maxSize: 64, unique: true
        description maxSize: 256, nullable: true, blank: true
    }
}

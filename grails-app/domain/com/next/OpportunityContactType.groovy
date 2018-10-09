package com.next

class OpportunityContactType
{
    String name

    static constraints = {
        name size: 3..16, unique: true
    }

    static mapping = {
        autoTimestamp true
        cache true
    }

    String toString()
    {
        name
    }
}

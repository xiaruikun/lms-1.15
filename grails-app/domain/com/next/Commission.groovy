package com.next

class Commission
{
    String status

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [opportunity: Opportunity]

    static constraints =
        {}
}

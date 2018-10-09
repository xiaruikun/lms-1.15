package com.next

class OpportunityComments
{
    OpportunityAction action
    String comment
    User user

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [opportunity: Opportunity]

    static constraints = {
        comment type: "text"
        action nullable: true, blank: true
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}

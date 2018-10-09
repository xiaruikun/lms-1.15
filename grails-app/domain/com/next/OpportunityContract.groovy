package com.next

class OpportunityContract
{
    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [opportunity: Opportunity, contract: Contract]

    static constraints = {
        contract unique: ['opportunity']
    }
}

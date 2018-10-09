package com.next

class OpportunityExternalDataset
{
    static belongsTo = [opportunity: Opportunity, dataset: ExternalDataset]

    static constraints = {}
}

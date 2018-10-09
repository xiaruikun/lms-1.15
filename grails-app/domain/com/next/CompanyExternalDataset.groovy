package com.next

class CompanyExternalDataset
{
    static belongsTo = [company: Company, dataset: ExternalDataset]
    static constraints = {}
}

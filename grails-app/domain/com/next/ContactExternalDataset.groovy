package com.next

class ContactExternalDataset
{
    static belongsTo = [contact: Contact, dataset: ExternalDataset]
    static constraints = {}
}

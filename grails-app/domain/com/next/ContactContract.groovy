package com.next

class ContactContract
{
    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [contact: Contact, contract: Contract]

    static constraints = {
        contract unique: ['contact']
    }
}

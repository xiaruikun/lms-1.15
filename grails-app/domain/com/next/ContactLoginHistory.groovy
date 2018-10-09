package com.next

class ContactLoginHistory
{
    String ip

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [contact: Contact]

    static constraints = {
        ip nullable: true, blank: true
    }
}

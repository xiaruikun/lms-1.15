package com.next

class ContactLoginCertificate
{
    String type

    String externalId

    Date lastLoginTime = new Date()

    Date createdDate = new Date()

    static belongsTo = [contact: Contact]

    static constraints = {
        externalId unique: true
    }

    def beforeUpdate()
    {
        lastLoginTime = new Date()
    }
}

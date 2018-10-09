package com.next

class AccountContact
{
    Date hiredate
    Date leavedate

    String accountExternalId
    String contactExternalId

    static belongsTo = [account: Account, contact: Contact]

    static constraints = {
        leavedate nullable: true, blank: true
        accountExternalId nullable: true, blank: true
        contactExternalId nullable: true, blank: true
    }
}

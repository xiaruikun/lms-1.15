package com.next

class ContactTeam
{
    TeamRole teamRole

    // String type = "User"

    static mapping = {
        autoTimestamp true
        cache true
    }
    static belongsTo = [contact: Contact, user: User]

    static constraints = {
        // type size: 4..16, inList: ['Manager', 'User']
    }
}

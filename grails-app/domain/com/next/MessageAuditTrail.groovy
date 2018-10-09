package com.next

class MessageAuditTrail
{
    String level
    String log

    // static belongsTo = [message: Message]

    static constraints =
        {
            log maxSize: 5000
            level inList: ['Info', 'Error']
        }
}

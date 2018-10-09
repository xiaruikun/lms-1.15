package com.next

class ExternalDataReceiverAutitTrail
{
    Date createdDate = new Date()
    Date startTime
    Date endTime
    String log

    User createdBy

    static belongsTo = [receiver: ExternalDataReceiver]

    static constraints = {
        startTime nullable: true, blank: true
        endTime nullable: true, blank: true
        log type: "text"
    }
}

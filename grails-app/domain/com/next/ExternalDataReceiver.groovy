package com.next

class ExternalDataReceiver
{
    String name
    Boolean active

    String script

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    User createdBy
    User modifiedBy

    static hasMany = [auditTrails: ExternalDataReceiverAutitTrail, messages: ExternalDataReceiverMessage]

    static constraints = {
        createdBy nullable: true, blank: true
        modifiedBy nullable: true, blank: true
        script type: "text"
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}

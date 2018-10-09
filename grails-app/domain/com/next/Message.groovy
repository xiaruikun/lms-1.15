package com.next

class Message
{
    String cellphone

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    String platform
    String ip
    String text

    String status = "Pending"
    String type = 'SMS'

    String returnCode
    String returnMessage

    static constraints = {
        status inList: ["Pending", "Succesful", "Failed"]
        cellphone nullable: true, blank: true
        ip nullable: true, blank: true
        returnMessage nullable: true, blank: true
    }
}

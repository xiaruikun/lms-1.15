package com.next

class Activity
{
    Date startTime
    Date endTime

    Date actualStartTime
    Date actualEndTime

    String description

    ActivityType type
    ActivitySubtype subtype

    String status = "Pending"

    Double longitude = 0
    Double latitude = 0
    String city
    String address
    //地址
    String name

    static belongsTo = [opportunity: Opportunity, user: User, contact: Contact, assignedTo: User, parent: Activity]

    static constraints = {
        status inList: ["Pending", "Delayed", "Completed", "Canceled"]

        user nullable: true, blank: true

        description nullable: true, blank: true

        actualStartTime nullable: true, blank: true
        actualEndTime nullable: true, blank: true

        assignedTo nullable: true, blank: true

        longitude nullable: true, blank: true
        latitude nullable: true, blank: true
        city nullable: true, blank: true
        address nullable: true, blank: true

        parent nullable: true, blank: true
        contact nullable: true, blank: true
        name nullable: true, blank: true, maxSize: 32
    }
}

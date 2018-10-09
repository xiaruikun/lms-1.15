package com.next

class Reporting
{
    User manager
    User user

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    Date startTime = new Date()
    Date endTime = new Date()

    static mapping = {
        cache true
    }

    static constraints =
        {
            startTime nullable: true
            endTime nullable: true
        }
}

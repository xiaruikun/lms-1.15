package com.next

class ActivitySubtype
{
    String name

    static belongsTo = [type: ActivityType]

    static constraints = {
        name maxSize: 32, unique: ['type']
    }
}

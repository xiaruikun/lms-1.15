package com.next

class ActivityType
{
    String name

    static hasMany = [subtypes: ActivitySubtype]

    static constraints = {
        name maxSize: 32, unique: true
    }
}

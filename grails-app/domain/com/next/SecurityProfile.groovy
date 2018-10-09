package com.next

class SecurityProfile
{
    String name

    static hasMany = [items: SecurityProfileItem]

    static constraints = {
        name maxSize: 128, unique: true
    }
}

package com.next

class ContactIdentityType
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

package com.next

class ContactProfession
{
    String name

    static constraints = {
        name maxSize: 128, unique: true
    }
}

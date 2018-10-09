package com.next

class ContactLevel
{
    String name
    String description
    Boolean active = true

    static constraints = {
        name unique: true, size: 1..16
        description nullable: true, blank: true, size: 0..2048
    }

    String toString()
    {
        name
    }
}

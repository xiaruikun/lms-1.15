package com.next

class Country
{
    String name

    static constraints = {
        name maxSize: 128, unique: true
    }
}

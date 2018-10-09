package com.next

class ExternalDataProvider
{
    String name

    static constraints = {
        name maxSize: 128, unique: true
    }
}

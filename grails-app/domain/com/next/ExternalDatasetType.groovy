package com.next

class ExternalDatasetType
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

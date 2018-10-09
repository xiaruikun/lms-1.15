package com.next

class FlexFieldCategory
{
    String name
    SortedSet fields
    static hasMany = [fields: FlexField]
    static constraints = {
        name maxSize: 64, unique: true
    }
    static mapping = {
        fields(lazy: false)
    }

    String toString()
    {
        name
    }

}

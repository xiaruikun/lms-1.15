package com.next

class BusinessUnit
{
    String name

    static constraints = {
        name unqiue: true, size: 2..16
    }

    String toString()
    {
        name
    }
}

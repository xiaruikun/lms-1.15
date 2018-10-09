package com.next

class TypeOfFirstMortgage
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

package com.next

class MortgageReleasingType
{
    String name

    static constraints = {
        name maxSize: 64, unique: true
    }
}

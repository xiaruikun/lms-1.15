package com.next

class MortgageType
{
    String name

    static constraints = {
        name maxSize: 64, unique: true
    }
}

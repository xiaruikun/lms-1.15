package com.next

class RepaymentMethod
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

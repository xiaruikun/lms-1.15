package com.next

class PrincipalPaymentMethod
{
    String name
    String desription

    static constraints = {
        name size: 3..16, unique: true
        desription size: 0..256, blank: true, nullable: true
    }

    String toString()
    {
        name
    }
}

package com.next

class CommissionPaymentMethod
{
    String name
    String description

    static constraints = {
        name size: 3..16, unique: true
        description size: 0..256, blank: true, nullable: true
    }

    String toString()
    {
        name
    }
}

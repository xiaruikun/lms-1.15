package com.next

class AccountType
{
    String name

    static constraints = {
        name maxSize: 32, unqiue: true
    }

    String toString()
    {
        name
    }
}

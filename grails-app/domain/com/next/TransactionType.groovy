package com.next

class TransactionType
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

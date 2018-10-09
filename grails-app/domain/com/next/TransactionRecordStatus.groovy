package com.next

class TransactionRecordStatus
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

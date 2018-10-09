package com.next

class BillsStatus
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

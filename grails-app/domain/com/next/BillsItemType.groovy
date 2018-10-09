package com.next

class BillsItemType
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

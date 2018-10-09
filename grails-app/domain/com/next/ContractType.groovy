package com.next

class ContractType
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

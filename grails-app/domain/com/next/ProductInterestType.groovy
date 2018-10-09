package com.next

class ProductInterestType
{
    String name
    Boolean isUsed = true

    static constraints = {
        name maxSize: 32, unique: true
        isUsed nullable: true,blank: true
    }
}

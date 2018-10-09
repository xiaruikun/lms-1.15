package com.next

class AddressType
{
    String name

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static constraints = {
        name unqiue: true, size: 2..16
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }

    String toString()
    {
        name
    }
}

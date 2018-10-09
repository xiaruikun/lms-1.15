package com.next

class Product
{
    String name

    Boolean active
    // ProductCategory category

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static constraints = {
        name maxSize: 32, unique: true
        active nullable: true
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

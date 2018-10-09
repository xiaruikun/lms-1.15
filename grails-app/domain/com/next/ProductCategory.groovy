package com.next

class ProductCategory
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

package com.next

class CollateralRegion
{

    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

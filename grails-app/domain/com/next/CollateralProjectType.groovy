package com.next

class CollateralProjectType
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}

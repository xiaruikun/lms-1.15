package com.next

class ComponentType
{
    String name

    static constraints = {
        name maxSize: 32, unqiue: true
    }
}

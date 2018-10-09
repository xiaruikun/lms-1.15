package com.next

class Position
{
    String name

    static constraints = {
        name unique: true, size: 2..16
    }
}

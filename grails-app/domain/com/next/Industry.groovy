package com.next

class Industry
{
    String name

    static constraints = {
        name maxSize: 64, unique: true
    }
}

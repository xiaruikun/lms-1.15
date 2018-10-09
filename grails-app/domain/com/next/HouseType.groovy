package com.next

class HouseType
{
    String name

    static constraints =
        {
            name maxSize: 32, unique: true
        }
}

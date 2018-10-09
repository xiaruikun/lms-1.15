package com.next

class Department
{
    String name

    static constraints = {
        name unique: true, size: 2..16
    }

    String toString()
    {
        name
    }
}

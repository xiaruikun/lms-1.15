package com.next

class TeamRole
{
    String name

    static constraints = {
        name unique: true, size: 2..16
    }
}

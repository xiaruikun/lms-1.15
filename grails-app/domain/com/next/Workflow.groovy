package com.next

class Workflow
{
    String name
    Boolean active = false

    static constraints = {
        name maxSize: 64, unique: true, nullable: false
    }
}

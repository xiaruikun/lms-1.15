package com.next

class CauseOfFailure
{
    String name
    String description

    static constraints = {
        name unqiue: true, size: 2..32
        description nullable: true, blank: true, size: 2..256
    }
}

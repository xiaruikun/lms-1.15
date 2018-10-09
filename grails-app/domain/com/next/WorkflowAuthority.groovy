package com.next

class WorkflowAuthority
{
    String name
    static constraints = {
        name maxSize: 32, unqiue: true
    }
}

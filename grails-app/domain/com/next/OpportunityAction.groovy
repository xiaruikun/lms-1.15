package com.next

class OpportunityAction
{
    String name

    static constraints = {
        name maxSize: 32, unqiue: true
    }
}

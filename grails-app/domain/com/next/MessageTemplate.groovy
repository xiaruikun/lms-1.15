package com.next

class MessageTemplate
{
    String name
    String text

    String template

    static constraints = {
        name nullable: true
        text maxSize: 512
        text nullable: true, blank: true
        template maxSize: 512, nullable: true, blank: true
    }

    String toString()
    {
        name
    }
}

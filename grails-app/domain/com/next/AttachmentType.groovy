package com.next

class AttachmentType
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }

    String toString()
    {
        name
    }
}

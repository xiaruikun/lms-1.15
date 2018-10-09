package com.next

class AppConfigurationKey
{
    String name

    static constraints = {
        name maxSize: 64, unqiue: true
    }
}

package com.next

class PropertyValuationProvider
{
    String code
    String name
    String apiUrl

    // static hasMany = [parameters: PropertyValuationProviderParameters]

    static constraints = {
        code maxSize: 16, unique: true
        name maxSize: 32, unqiue: true
        apiUrl maxSize: 256
    }
}
